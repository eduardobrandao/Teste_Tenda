package com.example.coupon.api;

import com.example.coupon.api.dto.CouponResponse;
import com.example.coupon.api.dto.CreateCouponRequest;
import com.example.coupon.app.CouponAppService;
import com.example.coupon.domain.Coupon;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

  private final CouponAppService app;
  private final Clock clock = Clock.systemDefaultZone();

  public CouponController(CouponAppService app) {
    this.app = app;
  }

  @PostMapping
  public ResponseEntity<CouponResponse> create(@Valid @RequestBody CreateCouponRequest req) {
    Coupon created = app.create(
        Coupon.create(
            req.getCode(),
            req.getDescription(),
            req.getDiscountValue(),
            req.getExpirationDate(),
            Boolean.TRUE.equals(req.getPublished()),
            clock
        )
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    app.softDelete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CouponResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(toResponse(app.get(id)));
  }

  private static CouponResponse toResponse(Coupon c) {
    CouponResponse r = new CouponResponse();
    r.id = c.id();
    r.code = c.code().value();
    r.description = c.description();
    r.discountValue = c.discountValue().value();
    r.expirationDate = c.expirationDate().value();
    r.published = c.published();
    r.deletedAt = c.deletedAt();
    return r;
  }
}
