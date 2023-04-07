# Requirements

- Docker

# Manual steps executed

- Installed JDK 18
- Created new project with Sprint initializer with Maven
- Docker Build:
    - `docker build -t modern-bank-app .`
- Docker run:
    - `docker run modern-bank-app`
- Swagger
  -The Swagger UI page will then be available at http://localhost:8080/swagger-ui.html
  - OpenAPI description will be available at the following url for json format: http://localhost:8080/context-path/v3/api-docs 


# TODO

- [x] Add Swagger
- [x] Dockerize the app
- [ ] Add H2 in-memory database
- [ ] Create entities
- [ ] Setup Database generation with seed
- [ ] Create Services
- [ ] Create repositories
- [ ] Create tests
