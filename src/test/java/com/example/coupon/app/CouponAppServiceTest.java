package com.example.coupon.app;

import com.example.coupon.domain.Coupon;
import com.example.coupon.infra.persistence.jpa.CouponJpaRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponAppServiceTest {

  @Autowired
  CouponJpaRepository repo;

  private final Clock fixed = Clock.fixed(Instant.parse("2026-01-17T12:00:00Z"), ZoneOffset.UTC);

  @Test
  void createShouldPersistSanitizedCode() {
    CouponAppService app = new CouponAppService(repo);
    Coupon created = app.create(Coupon.create("AB#12-3", "desc", 1.0, LocalDate.of(2026, 2, 1), true, fixed));

    assertNotNull(created.id());
    assertEquals(6, created.code().value().length());

    var fromDb = repo.findById(created.id()).orElseThrow();
    assertEquals(created.code().value(), fromDb.getCode());
    assertNull(fromDb.getDeletedAt());
  }

  @Test
  void deleteShouldSoftDeleteAndPreventSecondDelete() {
    CouponAppService app = new CouponAppService(repo);
    Coupon created = app.create(Coupon.create("ZZZ999", "desc", 1.0, LocalDate.of(2026, 2, 1), false, fixed));

    app.softDelete(created.id());
    var fromDb = repo.findById(created.id()).orElseThrow();
    assertNotNull(fromDb.getDeletedAt());

    assertThrows(IllegalStateException.class, () -> app.softDelete(created.id()));
  }
}
