package com.example.coupon.domain;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

class CouponDomainTest {

  private final Clock fixed = Clock.fixed(Instant.parse("2026-01-17T12:00:00Z"), ZoneOffset.UTC);

  @Test
  void shouldSanitizeCodeAndKeepSixChars() {
    Coupon c = Coupon.create("AB#12-3", "desc", 0.5, LocalDate.of(2026, 1, 18), false, fixed);
    assertEquals("AB123", c.code().value().substring(0,5));
    assertEquals(6, c.code().value().length());
  }

  @Test
  void shouldRejectCodeShorterThanSixAfterSanitize() {
    assertThrows(IllegalArgumentException.class, () ->
        Coupon.create("A-1", "desc", 1.0, LocalDate.of(2026, 1, 18), false, fixed)
    );
  }

  @Test
  void shouldRejectDiscountBelowMinimum() {
    assertThrows(IllegalArgumentException.class, () ->
        Coupon.create("ABC123", "desc", 0.49, LocalDate.of(2026, 1, 18), false, fixed)
    );
  }

  @Test
  void shouldRejectExpirationInPast() {
    assertThrows(IllegalArgumentException.class, () ->
        Coupon.create("ABC123", "desc", 1.0, LocalDate.of(2026, 1, 16), false, fixed)
    );
  }

  @Test
  void shouldSoftDeleteOnceOnly() {
    Coupon c = Coupon.create("ABC123", "desc", 1.0, LocalDate.of(2026, 1, 18), true, fixed);
    c.softDelete(fixed);
    assertTrue(c.isDeleted());
    assertThrows(IllegalStateException.class, () -> c.softDelete(fixed));
  }
}
