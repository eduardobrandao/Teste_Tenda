package com.example.coupon.domain.value;

import java.util.Objects;


public final class CouponCode {
  public final String value;

  public CouponCode(String value) {
    this.value = value;
  }

  public static CouponCode of(String raw) {
    if (raw == null) throw new IllegalArgumentException("code obrigatorio");
    String sanitized = raw.replaceAll("[^a-zA-Z0-9]", "");
    if (sanitized.length() != 6) {
      if (sanitized.length() > 6) sanitized = sanitized.substring(0, 6);
      else throw new IllegalArgumentException("code deve ter 6 caracteres apos sanitizacao");
    }
    return new CouponCode(sanitized);
  }

  public String value() {
    return value;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CouponCode that)) return false;
    return Objects.equals(value, that.value);
  }

  @Override public int hashCode() {
    return Objects.hash(value);
  }

  @Override public String toString() {
    return value;
  }
}
