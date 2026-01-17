package com.example.coupon.app.mapper;

import com.example.coupon.domain.Coupon;
import com.example.coupon.infra.persistence.CouponEntity;

import java.time.Clock;

public final class CouponMapper {
  private CouponMapper() {}

  public static CouponEntity toEntity(Coupon domain) {
    CouponEntity e = new CouponEntity();
    e.setId(domain.id());
    e.setCode(domain.code().value());
    e.setDescription(domain.description());
    e.setDiscountValue(domain.discountValue().value());
    e.setExpirationDate(domain.expirationDate().value());
    e.setPublished(domain.published());
    e.setDeletedAt(domain.deletedAt());
    return e;
  }

  public static Coupon toDomain(CouponEntity e, Clock clock) {
    // Para leitura, reutilizamos o factory com o mesmo clock.
    Coupon c = Coupon.create(
        e.getCode(),
        e.getDescription(),
        e.getDiscountValue(),
        e.getExpirationDate(),
        e.isPublished(),
        clock
    );
    c.attachId(e.getId());
    if (e.getDeletedAt() != null) {
      // marca deletado mantendo o mesmo timestamp
      // (a regra de negocio permite estar deletado; so impede deletar 2x)
      try {
        c.softDelete(Clock.fixed(e.getDeletedAt(), clock.getZone()));
      } catch (Exception ignored) {
        // se ja estava deletado pelo factory acima, ignore
      }
    }
    return c;
  }
}
