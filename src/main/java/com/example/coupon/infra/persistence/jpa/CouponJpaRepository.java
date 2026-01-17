package com.example.coupon.infra.persistence.jpa;

import com.example.coupon.infra.persistence.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {
  Optional<CouponEntity> findByCodeAndDeletedAtIsNull(String code);
}
