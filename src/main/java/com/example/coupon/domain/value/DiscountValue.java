package com.example.coupon.domain.value;

public final class DiscountValue {
  private final double value;

  private DiscountValue(double value) {
    this.value = value;
  }

  public static DiscountValue of(double value) {
    if (value < 0.5) throw new IllegalArgumentException("discountValue minimo e 0.5");
    return new DiscountValue(value);
  }

  public double value() {
    return value;
  }
}
