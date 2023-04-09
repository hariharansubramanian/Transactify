# Transactify

Transactify is a modern banking application that allows users to manage their bank and crypto accounts, track their
transactions, and perform currency conversions and payments with ease.

## Technologies Used

- [Java Spring Boot](https://spring.io/projects/spring-boot)
- [JUnit](https://junit.org/junit5/docs/current/user-guide/)
- [Docker](https://www.docker.com/)

## Features

- Config-driven bean injection using `application.properties`.
- Configurable currency conversion rate lookup
    - using the Open Exchange Rates API.
    - using `exchange_rates.json` lookup file.
- Configurable database
    - JPA database with H2 (includes interactive database at http://localhost:8080/h2-console)
    - In-memory database using a singleton concurrent hashmap.
- Switchable interfaces between
    - Banking & Crypto services
    - API exchange rates lookup & file exchange rates lookup
    - In-memory database & JPA database.
- Exception handler middleware with custom exceptions.
- Dockerfile to build and run the application.
- Tests for controller, services, repositories, and database.
- OpenAPI docs available at http://localhost:8080/v3/api-docs
- SwaggerUI available at http://localhost:8080/swagger-ui.html.
- Postman collection to interact with the APIs.

## Requirements

- [Docker](https://www.docker.com/)

## Configuring the Application

The application can be configured using the `application.properties` file, which has several properties that can be
enabled or disabled depending on the user's needs:

#### Logging

- `logging.level.org.hibernate.SQL=DEBUG` - logs all SQL statements
- `logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE` - logs all SQL parameters

#### Application

- `spring.main.allow-bean-definition-overriding=true` - allows bean definition overriding
- `spring.profiles.active=dev` - sets the active profile

#### Services

- `bank.enabled=true` or `crypto.enabled=true` - enables the banking or crypto services
- `fileExchange.enabled=true` or `apiExchange.enabled=true` - enables the file or API exchange rates
- `inMemoryDb.enabled=true` or `jpa.enabled=true` - enables the in-memory or JPA database

#### JPA Database

- `spring.datasource.url=jdbc:h2:mem:Transactify` - sets the JPA database URL
- `spring.datasource.driver-class-name=org.h2.Driver` - sets the JPA database driver
- `spring.datasource.username=sa` - sets the JPA database username
- `spring.datasource.password=` - sets the JPA database password
- `spring.jpa.hibernate.ddl-auto=create-drop` - sets the JPA database DDL
- `spring.jpa.show-sql=true` - shows the JPA database SQL statements
- `spring.h2.console.settings.web-allow-others=true` - allows the H2 console to be accessed from other machines

## Database

The application can be configured to use an in-memory database or a JPA database.

- `inMemoryDb.enabled=true` - uses a singleton concurrent Hashmap data structure as the database.
- `jpa.enabled=true - uses a JPA database that is further configured in the `application.properties` file.

### JPA Database with JDBC

The application ships with H2 as the default JPA database. It can be configured to use any other JPA database by
changing the `spring.datasource.url` and `spring.datasource.driver-class-name` properties in
the `application.properties`

The H2 console is available on `http://localhost:8080/h2-console`. The URL to connect is `jdbc:h2:mem:Transactify`.

## Running the Application

Docker is used to get all the dependencies and run the application.
Navigate to the root directory where the Dockerfile is and execute:

- Docker build: `docker build -t transactify .`
- Docker run: `docker run -p 8080:8080 transactify`

The application will be available at http://localhost:8080.

## Running the Tests

The tests can be run using the command `./mvnw test`.

## OpenApi Docs, Swagger UI, and Postman Collection

Transactify includes an OpenAPI specification document that defines the API endpoints and data models used in the
application.
You can access the OpenAPI specification document by navigating to the http://localhost:8080/v3/api-docs.

Swagger UI interface is available at http://localhost:8080/swagger-ui.html. The Swagger UI provides an interactive
interface for exploring the API, making it easy to understand the API structure and test the API endpoints.

Additionally, a Postman collection for Transactify that includes pre-built requests for each API endpoint. You can find
the Postman collection in `/assets/Transactify.postman_collection.json`. You can import this collection into Postman to
quickly and easily test the API endpoints.

## Currency Conversion

When processing payments, the application converts the amount to the currency of the account being credited.
The conversion lookup strategy is configured using the `application.properties` file.

- `apiExchange.enabled=true` - conversion by [Open Exchange Rates API](https://www.exchangerate-api.com/docs/free).

- `fileExchange.enabled=true` - conversion by lookup file `resources/exchange_rates.json`.

### API Exchange Rates

The application uses the free [Open Exchange Rates API](https://www.exchangerate-api.com/docs/free).
It has a limit of 1500 requests per month per IP Address.

### File Exchange Rates

This file can be extended or modified as needed. Here's an example of its contents:

```json
{
  "USD": {
    "rates": {
      "EUR": 0.91,
      "GBP": 0.81,
      "INR": 81.84,
      "NOK": 10.50
    }
  },
  "NOK": {
    "rates": {
      "USD": 1.1903,
      "EUR": 0.087,
      "GBP": 0.077,
      "INR": 7.80
    }
  },
  "INR": {
    "rates": {
      "USD": 0.012,
      "EUR": 0.011,
      "GBP": 0.0098,
      "NOK": 0.13
    }
  }
}
```