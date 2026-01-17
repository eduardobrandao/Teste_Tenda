package com.example.coupon.domain.value;

import java.time.Clock;
import java.time.LocalDate;

public final class ExpirationDate {
  private final LocalDate value;

  private ExpirationDate(LocalDate value) {
    this.value = value;
  }

  public static ExpirationDate of(LocalDate date, Clock clock) {
    if (date == null) throw new IllegalArgumentException("expirationDate obrigatorio");
    LocalDate today = LocalDate.now(clock);
    if (date.isBefore(today)) throw new IllegalArgumentException("expirationDate nao pode estar no passado");
    return new ExpirationDate(date);
  }

  public LocalDate value() {
    return value;
  }
}
