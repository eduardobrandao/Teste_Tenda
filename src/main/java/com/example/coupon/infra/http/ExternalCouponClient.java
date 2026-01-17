package com.example.coupon.infra.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ExternalCouponClient {

  private final RestClient client = RestClient.create("https://n1m0i5k0zu.apidog.io");

  public String fetchCouponsRaw() {
    return client.get()
        .uri("/coupon-23755524e0")
        .retrieve()
        .body(String.class);
  }
}
