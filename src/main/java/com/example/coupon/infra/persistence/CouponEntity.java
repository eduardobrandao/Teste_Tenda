package com.example.coupon.infra.persistence;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
public class CouponEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 6, unique = true)
  private String code;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private double discountValue;

  @Column(nullable = false)
  private LocalDate expirationDate;

  @Column(nullable = false)
  private boolean published;

  @Column
  private Instant deletedAt;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getCode() { return code; }
  public void setCode(String code) { this.code = code; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public double getDiscountValue() { return discountValue; }
  public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }

  public LocalDate getExpirationDate() { return expirationDate; }
  public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

  public boolean isPublished() { return published; }
  public void setPublished(boolean published) { this.published = published; }

  public Instant getDeletedAt() { return deletedAt; }
  public void setDeletedAt(Instant deletedAt) { this.deletedAt = deletedAt; }
}
