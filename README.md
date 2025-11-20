# Intelligent Order Routing System

A microservices-based order routing and warehouse optimization system built with Spring Boot, designed to intelligently route orders to the optimal warehouse based on inventory availability, geographic proximity, handling costs, and SLA requirements.

## Project Overview

This system demonstrates enterprise-grade microservices architecture with:
- Multi-module Maven project structure
- RESTful inter-service communication
- Database per service pattern
- Docker containerization for dependencies
- Real-time inventory management
- Intelligent routing algorithms

## Architecture

### Services

1. **Order Service** (Port 8080)
   - Manages customer orders and order lifecycle
   - Coordinates with routing service for warehouse assignment
   - Maintains customer and order data

2. **Inventory Service** (Port 8082)
   - Tracks product inventory across warehouses
   - Manages stock availability and reservations
   - Provides real-time inventory queries

3. **Warehouse Service** (Port 8081)
   - Manages warehouse metadata and capabilities
   - Maintains geographic and regional information
   - Tracks warehouse capacity and handling costs

4. **Routing Service** (Port 8083)
   - Evaluates optimal warehouse for order fulfillment
   - Considers distance, cost, inventory, and SLA
   - Implements scoring algorithms for decision-making

### Technology Stack

- **Framework**: Spring Boot 3.3.3
- **Language**: Java 17
- **Database**: MySQL 8
- **Caching**: Redis 7
- **Messaging**: Apache Kafka 7.6
- **Build Tool**: Maven
- **Containerization**: Docker Compose

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose
- VS Code with Java Extension Pack (recommended)

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/Akash5091/intelligent-order-routing.git
   cd intelligent-order-routing
   ```

2. **Start infrastructure services**
   ```bash
   cd docker
   docker-compose up -d
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run services**
   
   Option A: Using Maven
   ```bash
   # Terminal 1 - Order Service
   cd order-service
   mvn spring-boot:run
   
   # Terminal 2 - Inventory Service
   cd inventory-service
   mvn spring-boot:run
   
   # Terminal 3 - Warehouse Service
   cd warehouse-service
   mvn spring-boot:run
   
   # Terminal 4 - Routing Service
   cd routing-service
   mvn spring-boot:run
   ```
   
   Option B: Using VS Code Spring Boot Dashboard
   - Open the project in VS Code
   - Use the Spring Boot Dashboard to run each service

### Testing the System

1. **Create a warehouse**
   ```bash
   curl -X POST http://localhost:8081/api/warehouses \
     -H "Content-Type: application/json" \
     -d '{
       "code": "WH-001",
       "name": "West Coast Hub",
       "city": "Los Angeles",
       "state": "CA",
       "country": "USA",
       "latitude": 34.0522,
       "longitude": -118.2437,
       "maxCapacity": 10000,
       "handlingCost": 1.25,
       "regions": [
         {"country": "USA", "state": "CA"},
         {"country": "USA", "state": "NV"}
       ]
     }'
   ```

2. **Add inventory**
   ```bash
   curl -X POST "http://localhost:8082/api/inventory?warehouseId=1&sku=LAPTOP-001&qty=100"
   ```

3. **Create an order**
   ```bash
   curl -X POST http://localhost:8080/api/orders \
     -H "Content-Type: application/json" \
     -d '{
       "customer": {
         "name": "John Doe",
         "email": "john@example.com",
         "address": {
           "addressLine1": "123 Main St",
           "city": "San Francisco",
           "state": "CA",
           "country": "USA",
           "zipcode": "94102"
         }
       },
       "items": [
         {
           "sku": "LAPTOP-001",
           "qty": 2,
           "unitPrice": 999.99
         }
       ],
       "slaDays": 2
     }'
   ```

## Project Structure

```
intelligent-order-routing/
├── pom.xml                          # Root POM (parent)
├── docker/
│   └── docker-compose.yml           # Infrastructure services
├── common/                          # Shared DTOs and utilities
│   ├── pom.xml
│   └── src/main/java/.../common/dto/
├── order-service/                   # Order management
│   ├── pom.xml
│   └── src/main/java/.../order/
│       ├── OrderServiceApplication.java
│       ├── controller/
│       ├── entity/
│       ├── repository/
│       └── service/
├── inventory-service/               # Inventory tracking
│   ├── pom.xml
│   └── src/main/java/.../inventory/
├── warehouse-service/               # Warehouse metadata
│   ├── pom.xml
│   └── src/main/java/.../warehouse/
└── routing-service/                 # Routing logic
    ├── pom.xml
    └── src/main/java/.../routing/
```

## API Endpoints

### Order Service (8080)
- `POST /api/orders` - Create new order
- `GET /api/orders/{id}` - Get order details

### Inventory Service (8082)
- `GET /api/inventory/{warehouseId}/product/{sku}` - Check availability
- `POST /api/inventory` - Add inventory

### Warehouse Service (8081)
- `POST /api/warehouses` - Create warehouse
- `GET /api/warehouses` - List all warehouses

### Routing Service (8083)
- `POST /api/routing/evaluate` - Evaluate routing decision

## Future Enhancements

- [ ] Event-driven architecture with Kafka
- [ ] Redis caching for inventory queries
- [ ] Machine learning for demand forecasting
- [ ] API Gateway (Spring Cloud Gateway)
- [ ] Service discovery (Eureka)
- [ ] Circuit breaker patterns (Resilience4j)
- [ ] Distributed tracing (Sleuth + Zipkin)
- [ ] Comprehensive test coverage
- [ ] AWS Lambda integration for serverless functions
- [ ] Real-time analytics dashboard

## Contributing

This is a portfolio project. Feedback and suggestions are welcome!

## License

MIT License

## Author

Akash Kumar - Full Stack Java Developer

## Contact

- GitHub: [@Akash5091](https://github.com/Akash5091)
- LinkedIn: [Connect with me](https://www.linkedin.com/in/akash5091)
