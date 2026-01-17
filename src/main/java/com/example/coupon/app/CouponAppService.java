package com.example.coupon.app;

import com.example.coupon.app.mapper.CouponMapper;
import com.example.coupon.domain.Coupon;
import com.example.coupon.infra.persistence.CouponEntity;
import com.example.coupon.infra.persistence.jpa.CouponJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
public class CouponAppService {

  private final CouponJpaRepository repo;
  private final Clock clock;

  public CouponAppService(CouponJpaRepository repo) {
    this.repo = repo;
    this.clock = Clock.systemDefaultZone();
  }

  @Transactional
  public Coupon create(Coupon coupon) {
    // garante unicidade no nivel de aplicacao para mensagem melhor
    repo.findByCodeAndDeletedAtIsNull(coupon.code().value())
        .ifPresent(existing -> { throw new IllegalStateException("code ja existe"); });

    CouponEntity saved = repo.save(CouponMapper.toEntity(coupon));
    coupon.attachId(saved.getId());
    return coupon;
  }

  @Transactional
  public void softDelete(Long id) {
    CouponEntity entity = repo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cupom nao encontrado"));

    Coupon domain = CouponMapper.toDomain(entity, clock);
    domain.softDelete(clock);

    entity.setDeletedAt(domain.deletedAt());
    repo.save(entity);
  }

  @Transactional(readOnly = true)
  public Coupon get(Long id) {
    CouponEntity entity = repo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cupom nao encontrado"));
    return CouponMapper.toDomain(entity, clock);
  }
}
