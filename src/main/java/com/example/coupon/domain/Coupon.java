package com.example.coupon.domain;

import com.example.coupon.domain.value.CouponCode;
import com.example.coupon.domain.value.DiscountValue;
import com.example.coupon.domain.value.ExpirationDate;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public class Coupon {
  private Long id;
  private final CouponCode code;
  private final String description;
  private final DiscountValue discountValue;
  private final ExpirationDate expirationDate;
  private final boolean published;
  private Instant deletedAt;

  private Coupon(Long id,
                 CouponCode code,
                 String description,
                 DiscountValue discountValue,
                 ExpirationDate expirationDate,
                 boolean published,
                 Instant deletedAt) {
    this.id = id;
    this.code = Objects.requireNonNull(code);
    this.description = requireText(description, "description obrigatorio");
    this.discountValue = Objects.requireNonNull(discountValue);
    this.expirationDate = Objects.requireNonNull(expirationDate);
    this.published = published;
    this.deletedAt = deletedAt;
  }

  public static Coupon create(String rawCode,
                              String description,
                              double discountValue,
                              java.time.LocalDate expirationDate,
                              boolean published,
                              Clock clock) {
    return new Coupon(
        null,
        CouponCode.of(rawCode),
        description,
        DiscountValue.of(discountValue),
        ExpirationDate.of(expirationDate, clock),
        published,
        null
    );
  }

  public void attachId(Long id) {
    this.id = id;
  }

  public void softDelete(Clock clock) {
    if (deletedAt != null) throw new IllegalStateException("Cupom ja deletado");
    this.deletedAt = Instant.now(clock);
  }

  public boolean isDeleted() {
    return deletedAt != null;
  }

  public Long id() { return id; }
  public CouponCode code() { return code; }
  public String description() { return description; }
  public DiscountValue discountValue() { return discountValue; }
  public ExpirationDate expirationDate() { return expirationDate; }
  public boolean published() { return published; }
  public Instant deletedAt() { return deletedAt; }

  private static String requireText(String s, String msg) {
    if (s == null || s.trim().isEmpty()) throw new IllegalArgumentException(msg);
    return s.trim();
  }
}
