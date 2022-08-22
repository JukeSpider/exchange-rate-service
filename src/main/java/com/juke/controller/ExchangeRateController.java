package com.juke.controller;

import static com.juke.mapper.IExchangeRateMapper.MAPPER;

import com.juke.dto.ExchangeRateDto;
import com.juke.entity.ExchangeRateEntity;
import com.juke.service.IExchangeRateService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/exchange-rate")
@RestController
public class ExchangeRateController {

  private final IExchangeRateService service;

  @GetMapping("/all")
  public ResponseEntity<Iterable<ExchangeRateDto>> findAll() {
    List<ExchangeRateEntity> entities = service.findAll();
    return ResponseEntity.status(HttpStatus.OK).body(MAPPER.toDtoList(entities));
  }

  @GetMapping("/{year}/{month}/{day}")
  public ResponseEntity<Iterable<ExchangeRateDto>> findAllByDate(
      @PathVariable String year,
      @PathVariable String month,
      @PathVariable String day) {

    List<ExchangeRateEntity> entities = service.findAllByDate(year, month, day);

    return ResponseEntity.status(HttpStatus.OK).body(MAPPER.toDtoList(entities));
  }

  @GetMapping("/{year}/{month}/{day}/{currency1}/{currency2}")
  public ResponseEntity<ExchangeRateDto> findByCurrencyPairAndDate(@PathVariable String year,
      @PathVariable String month, @PathVariable String day, @PathVariable String currency1,
      @PathVariable String currency2) {

    LocalDate date = LocalDate.parse(String.format("%s-%s-%s", year, month, day));

    ExchangeRateEntity entity =
        service.findByCurrencyPairAndDate(currency1, currency2, year, month, day);

    return ResponseEntity.status(HttpStatus.OK).body(MAPPER.toDto(entity));
  }

  @PostMapping("/save")
  public ResponseEntity<ExchangeRateDto> save(@RequestBody ExchangeRateDto dto) {
    ExchangeRateEntity entity = service.save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(MAPPER.toDto(entity));
  }

  @PostMapping("/save-all")
  public ResponseEntity<Iterable<ExchangeRateDto>> saveAll(
      @RequestBody Iterable<ExchangeRateDto> dtoList) {
    Iterable<ExchangeRateEntity> entityList = service.saveAll(dtoList);
    return ResponseEntity.status(HttpStatus.CREATED).body(MAPPER.toDtoList(entityList));
  }

  @PutMapping("/update/id{id}")
  public ResponseEntity<ExchangeRateDto> updateById(@RequestBody ExchangeRateDto dto,
      @PathVariable Long id) {
    ExchangeRateEntity entity = service.updateById(dto, id);
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(MAPPER.toDto(entity));
  }

  @DeleteMapping("/delete/id{id}")
  public void deleteById(@PathVariable Long id) {
    service.deleteById(id);
  }
}