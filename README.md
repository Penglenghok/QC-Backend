# QuadCount ‑ Expense Sharing Backend

A Spring Boot 3 REST API that lets groups split expenses, settle balances, and authenticate with JWT.  
Built for WAA ‑ Web Application Architecture course (MUM/MIU) — but deploy‑ready for the real world.

---

## ✨ Features

| Area | Highlights |
|------|------------|
| **Auth** | JWT login/registration, BCrypt passwords |
| **Expenses** | CRUD, filtering by group & paging (`Pageable`) |
| **Groups** | Create groups, add users, fetch groups by *user id* |
| **Users** | List users |
| **Validation** | Bean‑Validation on every POST / PUT |
| **Global errors** | Uniform `ApiResponse` wrapper & single `@RestControllerAdvice` |
| **Security** | Spring Security filter chain, role‑based `@PreAuthorize` |
| **Testing** | <code>@DataJpaTest</code>, Mockito service tests, MockMvc integration |
| **Docker** |`Dockerfile`for API + Postgres |

---

## 🏗️ Tech Stack

* **Spring Boot 3.3** (+ JPA, Validation)
* **PostgreSQL** in prod; **H2** for tests
* **JWT (jjwt‑api)**
* **Lombok**
* **JUnit 5 / Mockito / Spring‑Boot‑Test**
* **Docker**

---

## 🚀 Quick start

```bash
# 1 – build & run tests
./mvnw clean test

# 2 – run locally with Postgres (requires DB running on :5432)
./mvnw spring-boot:run

```

`POST /api/auth/register` → get a token → include `Authorization: Bearer <token>` on all secured endpoints.

---

## 🔧 Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/quadcount` | Postgres URL |
| `spring.datasource.username` | `postgres` | DB user |
| `spring.datasource.password` | `postgres` | DB password |
| `app.jwt.secret` | (see `application.properties`) | JWT signing key |
| `app.jwt.expiration` | `86400000` | JWT TTL in ms |

For **tests** we override all DB settings with an H2 in‑memory datasource via `src/test/resources/application-test.yml`.

---

## 📑 API Overview (v1)

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/auth/register` | ❌ | Register user |
| POST | `/api/auth/login` | ❌ | Get JWT token |
| GET  | `/api/expenses` | ✅ | Paged list (`page`,`size`) |
| GET  | `/api/expenses/{id}` | ✅ | Expense by id |
| GET  | `/api/expenses/group/{groupId}` | ✅ | Expenses of a group (paged) |
| POST | `/api/expenses` | ✅ | Create expense |
| PUT  | `/api/expenses/{id}` | ✅ | Update expense |
| DELETE | `/api/expenses/{id}` | ✅ | Delete expense |
| GET  | `/api/groups` | ✅ | List groups |
| GET  | `/api/groups/{id}` | ✅ | Group by id |
| GET  | `/api/groups/user/{userId}` | ✅ | Groups a user belongs to |
| POST | `/api/groups` | ✅ | Create group |
| PUT  | `/api/groups/{id}` | ✅ | Update group |
| DELETE | `/api/groups/{id}` | ✅ | Delete group |
| GET  | `/api/users` | ✅ | List users |
| GET  | `/api/users/{id}` | ✅ | User by id |

_All responses — success **and** error — share the same JSON envelope:_

```json
{
  "timestamp": "2025-04-26T05:00:00Z",
  "status": 200,
  "message": "OK",
  "data": { /* your payload or null */ }
}
```

---

## 🧪 Testing

| Layer | Tooling | Command |
|-------|---------|---------|
| Repository | `@DataJpaTest` + H2 | `./mvnw test -Dtest=ExpenseRepositoryTest` |
| Service | Mockito mocks | `./mvnw test -Dtest=ExpenseServiceTest` |
| Controller | MockMvc integration | `./mvnw test -Dtest=ExpenseControllerIT` |
| Full suite | everything above | `./mvnw clean test` |

### Coverage Highlights

* `ExpenseRepositoryTest` – verifies custom native query `findGroupsByUserId`.
* `ExpenseServiceTest` – mocks repositories; asserts business logic.
* `ExpenseControllerIT` – boots full Spring context, asserts wrapper JSON shape.

All tests run on an **in‑memory H2** datasource via the `test` Spring profile to avoid touching real Postgres.

---

## 🖼️ Architecture Overview

```
┌────────────┐    JwtFilter   ┌─────────────┐   Jpa  ┌─────────────┐
│  Controller│ ────────────▶ │  Service    │ ─────▶ │ Repository  │
└────────────┘               └─────────────┘        └─────────────┘
       ▲                           │
       └── GlobalExceptionHandler ◀┘
```

* **Controller** — only request/response logic via DTOs & validation.
* **Service** — business rules, mapping DTO → Entity, transaction boundary.
* **Repository** — Spring‑Data JPA interfaces; one native query.
* **Security** — `JwtValidationFilter` injects `UsernamePasswordAuthenticationToken`.

---

## 🚀 Deployment

_This API is deployed to an AWS Lightsail instance managed by CapRover with automatic HTTPS and zero‑downtime image swaps._

Endpoints URL:
https://qc-backend.caprover.neakphsar.com/

Frontend URL:
https://qc-frontend.caprover.neakphsar.com/

