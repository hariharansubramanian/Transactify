# Requirements

- Docker

# Manual steps executed

- Installed JDK 18
- Created new project with Sprint initializer with Maven
- Docker Build:
    - `docker build -t transactify .`
- Docker run:
    - `docker run -p 8080:8080 transactify`
- Swagger
  -The Swagger UI page will then be available at http://localhost:8080/swagger-ui.html
  - OpenAPI description will be available at the following url for json format: http://localhost:8080/context-path/v3/api-docs 


# TODO

- [x] Add Swagger
- [x] Dockerize the app
- [x] Add H2 in-memory database
- [x] Create entities
- [x] Setup Database generation with seed
- [x] Create Services
- [x] Create repositories
- [x] Create tests
