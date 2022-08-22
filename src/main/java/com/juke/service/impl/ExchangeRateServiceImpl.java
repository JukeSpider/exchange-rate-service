package com.juke.service.impl;

import static com.juke.mapper.IExchangeRateMapper.MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juke.dto.ExchangeRateDto;
import com.juke.dto.json.CurrencyCode;
import com.juke.dto.json.DailyExchangeRate;
import com.juke.dto.json.ListOfCurrencies;
import com.juke.entity.CurrencyEnum;
import com.juke.entity.ExchangeRateEntity;
import com.juke.repository.IExchangeRateRepository;
import com.juke.service.IExchangeRateService;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {

  private static final String URL = "https://www.cbr-xml-daily.ru/archive/%s/%s/%s/daily_json.js";
  private final IExchangeRateRepository repo;

  @Override
  public List<ExchangeRateEntity> findAll() {
    List<ExchangeRateEntity> exchangeRateList = repo.findAll();
    return exchangeRateList.isEmpty() ? Collections.emptyList() : exchangeRateList;
  }

  @Override
  public List<ExchangeRateEntity> findAllByDate(String year, String month, String day) {

    LocalDate date = parseDate(year, month, day);
    List<ExchangeRateEntity> exchangeRateList = repo.findAllByDate(date);

    if (exchangeRateList.isEmpty()) {
      saveDailyExchangeRates(year, month, day);
    }

    return repo.findAllByDate(date);
  }

  @Override
  public ExchangeRateEntity findByCurrencyPairAndDate(
      String currency1,
      String currency2,
      String year,
      String month,
      String day
  ) {

    LocalDate date = parseDate(year, month, day);

    CurrencyEnum sell = CurrencyEnum.valueOf(currency1);
    CurrencyEnum buy = CurrencyEnum.valueOf(currency2);

    Optional<ExchangeRateEntity> entityOptional =
        repo.findBySellAndBuyAndDate(sell, buy, date);

    if (entityOptional.isEmpty()) {
      saveDailyExchangeRates(year, month, day);
    }

    return repo.findBySellAndBuyAndDate(sell, buy, date).orElseThrow(IllegalArgumentException::new);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public ExchangeRateEntity save(ExchangeRateDto exchangeRate) {

    if (exchangeRate == null) {
      throw new IllegalArgumentException("Exchange rate is null!");
    }

    ExchangeRateEntity entity = MAPPER.toEntity(exchangeRate);

    return repo.save(entity);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  @Override
  public List<ExchangeRateEntity> saveAll(Iterable<ExchangeRateDto> exchangeRateList) {

    if (exchangeRateList == null) {
      throw new IllegalArgumentException("List is empty!");
    }

    Iterable<ExchangeRateEntity> entityList = MAPPER.toEntityList(exchangeRateList);

    return repo.saveAll(entityList);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public ExchangeRateEntity updateById(ExchangeRateDto exchangeRate, Long id) {

    if (exchangeRate == null) {
      throw new IllegalArgumentException("Exchange rate is null!");
    }

    if (id == null) {
      throw new IllegalArgumentException("Id is null!");
    }

    if (!repo.existsById(id)) {
      throw new IllegalArgumentException("Cannot find exchange rate by this id");
    }

    ExchangeRateEntity entity = MAPPER.toEntity(exchangeRate);

    return repo.save(entity);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteById(Long id) {

    if (!repo.existsById(id)) {
      throw new IllegalArgumentException("Cannot find exchange rate by this id");
    }

    repo.deleteById(id);
  }

  private void saveDailyExchangeRates(String year, String month, String day) {

    String dailyURL = String.format(URL, year, month, day);
    DailyExchangeRate dailyExchangeRate;
    List<CurrencyCode> currencyCodeList;

    try {
      dailyExchangeRate = getRestTemplateBody(dailyURL);
      currencyCodeList = getAllCurrencyCodes(dailyExchangeRate.getCurrency());

    } catch (JsonProcessingException | IllegalAccessException e) {
      throw new IllegalArgumentException(e);
    }

    repo.saveAll(mapCurrencyCode(currencyCodeList, parseDate(year, month, day)));
  }


  private DailyExchangeRate getRestTemplateBody(String url) throws JsonProcessingException {
    String body = new RestTemplate().getForEntity(url, String.class).getBody();
    return new ObjectMapper().readValue(body, DailyExchangeRate.class);
  }

  private List<CurrencyCode> getAllCurrencyCodes(ListOfCurrencies listOfCurrencies)
      throws IllegalAccessException {

    Class<ListOfCurrencies> listOfCurrenciesClass = ListOfCurrencies.class;
    Field[] fields = listOfCurrenciesClass.getDeclaredFields();
    List<CurrencyCode> currencyCodeList = new ArrayList<>(fields.length);

    for (Field field : fields) {
      field.setAccessible(true);
      currencyCodeList.add((CurrencyCode) field.get(listOfCurrencies));
    }

    return currencyCodeList;
  }

  private List<ExchangeRateEntity> mapCurrencyCode(List<CurrencyCode> codes, LocalDate date) {

    List<ExchangeRateEntity> entityList = new ArrayList<>();

    for (CurrencyCode code1 : codes) {

      entityList.add(
          ExchangeRateEntity.builder()
              .sell(CurrencyEnum.RUB)
              .buy(CurrencyEnum.valueOf(code1.getCharCode()))
              .rate(((double) code1.getValue() / code1.getNominal()))
              .date(date)
              .build()
      );

      entityList.add(
          ExchangeRateEntity.builder()
              .sell(CurrencyEnum.valueOf(code1.getCharCode()))
              .buy(CurrencyEnum.RUB)
              .rate(((double) code1.getNominal() / code1.getValue()))
              .date(date)
              .build()
      );

      for (CurrencyCode code2 : codes) {

        if (code1.getCharCode().equals(code2.getCharCode())) {
          continue;
        }

        double rate = (double) (code1.getValue() * code2.getNominal()) /
            (code2.getValue() * code1.getNominal());

        entityList.add(
            ExchangeRateEntity.builder()
                .sell(CurrencyEnum.valueOf(code1.getCharCode()))
                .buy(CurrencyEnum.valueOf(code2.getCharCode()))
                .rate(rate)
                .date(date)
                .build()
        );

        entityList.add(
            ExchangeRateEntity.builder()
                .sell(CurrencyEnum.valueOf(code2.getCharCode()))
                .buy(CurrencyEnum.valueOf(code1.getCharCode()))
                .rate(1 / rate)
                .date(date)
                .build()
        );
      }
    }

    return entityList;
  }

  private LocalDate parseDate(String year, String month, String day) {
    String stringDate = String.format("%s-%s-%s", year, month, day);
    return LocalDate.parse(stringDate);
  }
}