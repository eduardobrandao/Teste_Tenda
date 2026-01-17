package com.example.coupon.api.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreateCouponRequest {

  @NotBlank
  private String code;

  @NotBlank
  private String description;

  @NotNull
  @DecimalMin("0.5")
  private Double discountValue;

  @NotNull
  private LocalDate expirationDate;

  private Boolean published = Boolean.FALSE;

  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public Double getDiscountValue() { return discountValue; }
  public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }

  public LocalDate getExpirationDate() { return expirationDate; }
  public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

  public Boolean getPublished() { return published; }
  public void setPublished(Boolean published) { this.published = published; }
}
