# coupon-service

Spring Boot 3 (Java 17) API para gerenciamento de cupons com:
- Regras de negocio encapsuladas no dominio
- H2 em memoria
- Soft delete
- Swagger/OpenAPI
- Docker e Docker Compose
- Testes (objetivo ~80% de cobertura das regras)

## Rodar local

```bash
mvn clean test
mvn spring-boot:run
```

- Swagger: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:coupondb`
  - user: `sa` (sem senha)

## Docker

```bash
docker compose up --build
```

## Endpoints

- `POST /api/coupons` cria cupom
- `DELETE /api/coupons/{id}` soft delete
- `GET /api/coupons/{id}` consulta

Payload de criacao (JSON):

```json
{
  "code": "AB#12-3",
  "description": "Cupom de boas-vindas",
  "discountValue": 10.0,
  "expirationDate": "2026-02-01",
  "published": true
}
```
