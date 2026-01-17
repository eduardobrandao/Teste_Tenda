package com.example.coupon.api.dto;

import java.time.Instant;
import java.time.LocalDate;

public class CouponResponse {
  public Long id;
  public String code;
  public String description;
  public double discountValue;
  public LocalDate expirationDate;
  public boolean published;
  public Instant deletedAt;
}
