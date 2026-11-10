# Banking Service

A small banking simulation built with Spring Boot and PostgreSQL, created as a hands-on project for learning Java, Spring, and transaction management. Everything runs locally.

## Features

- Create and query accounts
- Transfer money between two accounts, atomically (`@Transactional`)
- Audit trail: every transfer attempt, successful or failed, is stored in a separate `TransactionLog` table (written in its own transaction via `Propagation.REQUIRES_NEW`, so failed attempts are kept even when the transfer rolls back)

## Tech Stack

- Java 25
- Spring Boot 4.1 (Web MVC, Data JPA, Validation)
- PostgreSQL 16 (Docker)
- Maven (wrapper included)

## Getting Started

### 1. Start PostgreSQL

```bash
docker run --name banking-postgres \
  -e POSTGRES_DB=banking \
  -e POSTGRES_USER=banking_user \
  -e POSTGRES_PASSWORD=banking_pass \
  -p 5432:5432 \
  -d postgres:16
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`. Database settings live in `src/main/resources/application.properties`.

## API

| Method | Endpoint         | Description            |
|--------|------------------|------------------------|
| POST   | `/accounts`      | Create an account      |
| GET    | `/accounts/{id}` | Get an account by id   |
| POST   | `/transfer`      | Transfer between accounts |

### Examples

```bash
# Create an account
curl -X POST http://localhost:8080/accounts \
  -H "Content-Type: application/json" \
  -d '{"iban": "TR330006100519786457841326", "ownerName": "Jane Doe", "balance": 1000}'

# Get an account
curl http://localhost:8080/accounts/1

# Transfer 100 from account 1 to account 2
curl -X POST http://localhost:8080/transfer \
  -H "Content-Type: application/json" \
  -d '{"fromId": 1, "toId": 2, "amount": 100}'
```

## Request / Response Flow

Every HTTP request enters and every response leaves through the same file: `src/main/java/com/mertkanakkoc/banking_service/AccountController.java`.

| Endpoint | Request comes in (function) | Response goes out |
|----------|-----------------------------|-------------------|
| `POST /accounts` | `AccountController.createAccount()` | `AccountController.java`, the returned `Account` is serialized to JSON |
| `GET /accounts/{id}` | `AccountController.getAccount()` | `AccountController.java`, the returned `Account` is serialized to JSON |
| `POST /transfer` | `AccountController.transfer()` | `AccountController.java`, empty `200 OK` body (the method returns `void`) |

For a transfer, the request travels `AccountController.transfer()` → `TransferService.transfer()` → `AccountRepository` / `TransactionLogService` → PostgreSQL, then control returns to `AccountController.transfer()`, where Spring writes the HTTP response.

## Project Structure

```
Account / AccountRepository / AccountController   accounts (entity, data access, HTTP)
TransferRequest / TransferService                 transfer logic (transactional)
TransactionLog / TransactionLogRepository /
TransactionLogService                             audit trail
```

## Roadmap

- [x] Accounts and transfers
- [x] `@Transactional` consistency
- [x] Transaction log (audit trail)
- [ ] Validation and custom exception handling (`@Valid`, `@ControllerAdvice`)
- [ ] Concurrency control (optimistic / pessimistic locking)
- [ ] JWT authentication with Spring Security
- [ ] Nightly batch job (Spring Batch)
- [ ] Docker Compose for the full stack
