package com.juke.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyExchangeRate {

  @JsonProperty("Date")
  private String date;

  @JsonProperty("PreviousDate")
  private String previousDate;

  @JsonProperty("PreviousURL")
  private String previousURL;

  @JsonProperty("Timestamp")
  private String timestamp;

  @JsonProperty("Valute")
  private ListOfCurrencies currency;
}