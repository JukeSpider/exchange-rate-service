package com.juke.service;

import com.juke.dto.ExchangeRateDto;
import com.juke.entity.ExchangeRateEntity;
import java.time.LocalDate;
import java.util.List;

public interface IExchangeRateService {

  List<ExchangeRateEntity> findAll();

  List<ExchangeRateEntity> findAllByDate(String year, String month, String day);

  ExchangeRateEntity findByCurrencyPairAndDate(
      String currency1,
      String currency2,
      String year,
      String month,
      String day
  );

  ExchangeRateEntity save(ExchangeRateDto exchangeRate);

  List<ExchangeRateEntity> saveAll(Iterable<ExchangeRateDto> exchangeRateList);

  ExchangeRateEntity updateById(ExchangeRateDto dto, Long id);

  void deleteById(Long id);
}