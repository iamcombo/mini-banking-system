# Mini Banking System (Spring Boot)

A minimal banking backend built with Spring Boot 3, providing authentication, account management, and transaction features. API documentation is available via OpenAPI/Swagger UI.

## Features

- __Authentication__: register, login, refresh token using JWT.
- __RBAC__: roles and fine-grained permissions for protected endpoints.
- __Accounts__: create account, list accounts, get account by account number.
- __Transactions__: transfer, deposit, withdraw, and list transaction history.
- __OpenAPI__: interactive API docs via Swagger UI.

## Tech Stack

- __Java__: 17
- __Framework__: Spring Boot 3.5.5 (Web, Security, Validation, Data JPA)
- __Database__: PostgreSQL (JDBC driver)
- __Auth__: JJWT
- __Docs__: springdoc-openapi
- __Build__: Gradle (wrapper included)
- __Testing__: JUnit 5, Spring Boot Test, Testcontainers

## Project Structure

- Source: `src/main/java/com/project/bpa/`
- Main app: `com.project.bpa.BankingProjectAssignmentApplication`
- Config: `src/main/resources/application.properties`
- Build file: `build.gradle`

## Prerequisites

- Java 17+
- PostgreSQL instance (local or remote)

Optional for local Postgres using Docker:

```bash
# Example Postgres container (password-based)
docker run --name bpa-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=bpa -p 5432:5432 -d postgres:16
```

## Configuration

Application properties are sourced from environment variables (with `NA` defaults). Update your environment accordingly:

- __DB_URL__: e.g. `jdbc:postgresql://localhost:5432/bpa`
- __DB_USERNAME__: e.g. `postgres`
- __DB_PASSWORD__: e.g. `postgres`
- __JWT_SECRET__: a strong random string

Other notable settings in `src/main/resources/application.properties`:

- `spring.jpa.hibernate.ddl-auto=create-drop` (dev default; recreates schema each run)
- `spring.jpa.show-sql=true`

You can export variables before running:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/bpa"
export DB_USERNAME="postgres"
export DB_PASSWORD="postgres"
export JWT_SECRET="change-me-to-a-long-random-secret"
```

## Run Locally

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Build a jar:

```bash
./gradlew clean build
java -jar build/libs/bpa-0.0.1-SNAPSHOT.jar
```

Default server URL: `http://localhost:8080`

## API Documentation (Swagger UI)

Once the app is running:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI spec: `http://localhost:8080/v3/api-docs`

Security scheme: Bearer JWT (header `Authorization: Bearer <token>`).

## Authentication Flow

- __Register__: `POST /api/auth/register`
- __Login__: `POST /api/auth/login` → returns access token
- __Refresh__: `POST /api/auth/refresh-token`

Example:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{
        "username": "alice",
        "password": "Password123!",
        "email": "alice@example.com"
      }'

curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{
        "username": "alice",
        "password": "Password123!"
      }'
```

Use the returned `token` as `Authorization: Bearer <token>` for protected endpoints.

## Endpoints Overview

Base path: `http://localhost:8080`

- __Authentication__ (`/api/auth`)
  - `POST /register` – register new user
  - `POST /login` – login, receive JWT
  - `POST /refresh-token` – refresh access token

- __Permissions__ (`/api/permissions`) — requires `ROLE_ADMIN`
  - `POST /` – create permission
  - `GET /` – list all permissions
  - `POST /assign` – assign permission to a role

- __Users__ (`/api/users`) — requires `MANAGE_USERS`
  - `GET /list` – list users (supports `search`, `status`, pagination via `Pageable`)
  - `GET /detail/{id}` – get user detail
  - `PUT /update/{id}` – update user
  - `DELETE /delete/{id}` – delete user

- __Accounts__ (`/api/account`) — requires appropriate authorities
  - `POST /create` – create an account (requires `CREATE_ACCOUNT`)
  - `GET /list` – list user accounts (requires `VIEW_ALL_ACCOUNTS`)
  - `GET /detail/{accountNumber}` – get account by number (requires `VIEW_ALL_ACCOUNTS`)

- __Transactions__ (`/api/transaction`) — requires JWT
  - `POST /transfer` – transfer between accounts
  - `POST /deposit` – deposit to an account
  - `POST /withdraw` – withdraw from an account
  - `GET /history?accountNumber=...&page=0&size=10` – paged transaction history

Notes:
- Many endpoints require specific authorities (e.g., `CREATE_ACCOUNT`, `VIEW_ALL_ACCOUNTS`, `MANAGE_USERS`) or `ROLE_ADMIN`.
- Provide JWT in `Authorization` header.

## Development & Testing

- Run tests:

```bash
./gradlew test
```

- Testcontainers dependencies are included; you can write integration tests using ephemeral PostgreSQL containers.

## Troubleshooting

- __DB connection errors__: verify `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` and that PostgreSQL is reachable.
- __JWT errors__: ensure `JWT_SECRET` is set and consistent between runs.
- __Schema resets__: `create-drop` rebuilds schema every run; change `spring.jpa.hibernate.ddl-auto` for persistent local data.

## License

This project is provided as-is for educational/demo purposes. Add a license file if needed.
