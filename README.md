# micro-ecommerce

A microservices-based e-commerce platform built with Spring Boot, featuring service discovery, API gateway, and modern observability tooling.

## Architecture

The platform consists of several microservices:

| Service | Port | Description |
|---------|------|-------------|
| **Eureka Discovery** | 8761 | Service registry and discovery |
| **Gateway** | 8085 | API Gateway with OAuth2/JWT routing |
| **Products Service** | 8081 | Product, Brand, and Category management |
| **Orders Service** | 8082 | Order processing and management |
| **Cart Service** | 8083 | Shopping cart functionality |
| **Clients** | - | Shared Feign clients for inter-service communication |

## Infrastructure

| Component | Port | Purpose |
|-----------|------|---------|
| PostgreSQL | 5432/5433 | Database (products/orders on 5432, cart on 5433) |
| Keycloak | 8080 | Authentication & Authorization (Realm: ecommerce) |
| Zipkin | 9411 | Distributed tracing |
| Prometheus | 9090 | Metrics collection |
| Grafana | 3000 | Metrics visualization (admin/admin) |

## Tech Stack

- **Java 17** with Spring Boot 3.5.8
- **Spring Cloud** 2025.0.0 (OpenFeign, Gateway, Eureka)
- **PostgreSQL** for persistent storage
- **Keycloak** for OAuth2/OIDC authentication
- **Micrometer** for Prometheus metrics
- **Docker Compose** for local infrastructure

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### 1. Start Infrastructure

```bash
docker compose up -d
```

Wait for PostgreSQL, Keycloak, and other services to be healthy.

### 2. Import Keycloak Realm

Import the realm configuration from `deploy/imports/ecommerce-realm.json` into Keycloak at `http://localhost:8080`.

### 3. Build the Application

```bash
./mvnw clean install
```

Or from the project root using the parent POM.

### 4. Run Services

Start services in this order:
1. Eureka Discovery Service
2. Products Service
3. Orders Service
4. Cart Service
5. Gateway Service

Or use the build-docker-image.sh script to create Docker images.

## API Endpoints

All services are routed through the Gateway at `http://localhost:8085`:

| Path | Service | Description |
|------|---------|-------------|
| `/api/v1/product/**` | Products | Product management |
| `/api/v1/orders/**` | Orders | Order processing |
| `/api/v1/cart/**` | Cart | Shopping cart |

## Monitoring

- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090
- **Zipkin**: http://localhost:9411
- **Keycloak Admin**: http://localhost:8080 (admin/AdminPass1)
- **Actuator endpoints**: Available on each service at `/{service}/actuator`

## Project Structure

```
micro-ecommerce/
├── eureka-discovery-service/    # Service registry
├── gateway/                      # API Gateway
├── products-service/             # Product microservice
├── orders-service/               # Orders microservice
├── cart-service/                 # Cart microservice
├── clients/                      # Shared Feign clients
├── deploy/                       # Deployment configs
└── postgres/                    # DB initialization scripts
```
