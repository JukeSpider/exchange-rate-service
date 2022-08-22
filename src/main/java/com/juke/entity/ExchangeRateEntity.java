package com.juke.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "exchange_rate")
@Entity
public class ExchangeRateEntity {

  @Id
  @Column(name = "id", unique = true, updatable = false, nullable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id")
  @SequenceGenerator(name = "sequence_id", sequenceName = "sequence_id")
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "sell")
  private CurrencyEnum sell;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "buy")
  private CurrencyEnum buy;

  @Column(name = "rate")
  private Double rate;

  @Column(name = "date")
  private LocalDate date;
}