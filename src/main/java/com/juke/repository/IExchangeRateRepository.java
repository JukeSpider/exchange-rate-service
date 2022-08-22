package com.juke.repository;

import com.juke.entity.CurrencyEnum;
import com.juke.entity.ExchangeRateEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface IExchangeRateRepository extends JpaRepository<ExchangeRateEntity ,Long> {

  boolean existsById(@NonNull Long id);

  List<ExchangeRateEntity> findAllByDate(LocalDate date);

  Optional<ExchangeRateEntity> findBySellAndBuyAndDate(
      CurrencyEnum sell,
      CurrencyEnum buy,
      LocalDate date
  );
}