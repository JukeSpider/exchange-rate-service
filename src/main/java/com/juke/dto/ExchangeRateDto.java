package com.juke.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ExchangeRateDto {

  private Long id;

  private String sell;

  private String buy;

  private String rate;

  private String date;
}