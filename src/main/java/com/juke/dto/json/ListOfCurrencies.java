package com.juke.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListOfCurrencies {

  @JsonProperty("AUD")
  private CurrencyCode aud;

  @JsonProperty("AZN")
  private CurrencyCode azn;

  @JsonProperty("GBP")
  private CurrencyCode gbp;

  @JsonProperty("AMD")
  private CurrencyCode amd;

  @JsonProperty("BYN")
  private CurrencyCode byn;

  @JsonProperty("BGN")
  private CurrencyCode bgn;

  @JsonProperty("BRL")
  private CurrencyCode brl;

  @JsonProperty("HUF")
  private CurrencyCode huf;

  @JsonProperty("HKD")
  private CurrencyCode hkd;

  @JsonProperty("DKK")
  private CurrencyCode dkk;

  @JsonProperty("USD")
  private CurrencyCode usd;

  @JsonProperty("EUR")
  private CurrencyCode eur;

  @JsonProperty("INR")
  private CurrencyCode inr;

  @JsonProperty("KZT")
  private CurrencyCode kzt;

  @JsonProperty("CAD")
  private CurrencyCode cad;

  @JsonProperty("KGS")
  private CurrencyCode kgs;

  @JsonProperty("CNY")
  private CurrencyCode cny;

  @JsonProperty("MDL")
  private CurrencyCode mdl;

  @JsonProperty("NOK")
  private CurrencyCode nok;

  @JsonProperty("PLN")
  private CurrencyCode pln;

  @JsonProperty("RON")
  private CurrencyCode ron;

  @JsonProperty("XDR")
  private CurrencyCode xdr;

  @JsonProperty("SGD")
  private CurrencyCode sgd;

  @JsonProperty("TJS")
  private CurrencyCode tjs;

  @JsonProperty("TRY")
  private CurrencyCode trY;

  @JsonProperty("TMT")
  private CurrencyCode tmt;

  @JsonProperty("UZS")
  private CurrencyCode uzs;

  @JsonProperty("UAH")
  private CurrencyCode uah;

  @JsonProperty("CZK")
  private CurrencyCode czk;

  @JsonProperty("SEK")
  private CurrencyCode sek;

  @JsonProperty("CHF")
  private CurrencyCode chf;

  @JsonProperty("ZAR")
  private CurrencyCode zar;

  @JsonProperty("KRW")
  private CurrencyCode krw;

  @JsonProperty("JPY")
  private CurrencyCode jpy;
}