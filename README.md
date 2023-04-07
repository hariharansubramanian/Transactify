# Requirements

- Docker

# Manual steps executed

- Installed JDK 18
- Created new project with Sprint initializer with Maven
- Docker Build:
    - `docker build -t modern-bank-app .`
- Docker run:
    - `docker run modern-bank-app`

# TODO

- [x] Add Swagger
- [x] Dockerize the app
- [ ] Add H2 in-memory database
- [ ] Create entities
- [ ] Setup Database generation with seed
- [ ] Create Services
- [ ] Create repositories
- [ ] Create tests
