# Financial Transactions API - Challenge Solution

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.5-green)](https://spring.io/projects/spring-boot)
[![Redis](https://img.shields.io/badge/Redis-7.x-red)](https://redis.io/)

A robust solution for handling financial transactions with support for multiple payment methods and idempotent operations.

## 📋 Overview
API RESTful para procesamiento de transacciones financieras que soporta:
- Transferencias P2P
- Pagos con tarjeta
- Transferencias bancarias
- Operaciones idempotentes
- Validaciones de negocio
- Paginación y filtrado avanzado


## 🏗 Architecture
### Sequence Diagram
![Sequence Diagram](https://raw.githubusercontent.com/freefrozztea/challenge-link/refs/heads/main/images/diagrama-de-secuencia.png)
![Diagrama de Clases](https://raw.githubusercontent.com/freefrozztea/challenge-link/refs/heads/main/images/diagrama-clases-challenge-link.png)
![Diagrama de Arquitectura](https://raw.githubusercontent.com/freefrozztea/challenge-link/refs/heads/main/images/arquitectura-software-link.drawio.png)

## ✨ Features
- Multiple transaction types support
- Idempotency key implementation
- Redis caching layer
- Advanced filtering and pagination
- Currency validation system
- Custom exception handling
- API documentation (Swagger/OpenAPI)
- Transactional operations
- SOLID principles compliance


### 🛡 Core Architecture
1. **Custom Exceptions**  
   Domain-specific exceptions (`TransactionFailedException`, `InvalidCurrencyException`) for better error handling.

2. **Strategy Pattern**  
   Implemented for transaction type handling:
   ```java
   public interface TransactionStrategy {
       Transaction processTransaction(TransactionRequest request);
   }
   ```
   
3. Idempotency Service
Redis-backed implementation to prevent duplicate transactions:

```java
Copy
@Service
public class IdempotencyService {
private final RedisTemplate<String, String> redisTemplate;

    public boolean checkAndStoreIdempotencyKey(String key) {
        // Redis operations
    }
}
```

4. UUID Generation
Server-side UUID generation for transaction IDs ensuring uniqueness and security.

### 🔄 Transaction Management
Spring @Transactional
ACID-compliant operations for data consistency

### Currency Validation
Enum-based validation system with expansion capabilities

```java
public enum Currency {
USD, EUR, GBP, JPY
}
```

### 📦 Data Modeling
- Specialized Models
- Dedicated models per transaction type (CardTransaction, BankTransfer, P2PTransfer)

### DTO Pattern
Type-specific DTOs with Jackson polymorphism:

```java
@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({
@Type(value = CardPaymentDTO.class, name = "card"),
@Type(value = BankTransferDTO.class, name = "bank")
})
public abstract class TransactionDTO { ... }
```

## ⚡ Performance
- Pagination
Spring Data Pageable implementation:

```http
GET /transactions?page=0&size=20&sort=amount,desc
```

-MapStruct Mapping
Efficient DTO-Entity conversion:

```java
@Mapper(componentModel = "spring")
public interface TransactionMapper {
TransactionDTO toDto(Transaction transaction);
}
```

## 🚀 Getting Started
Prerequisites
- Java 17+
- Redis 7.x
- Maven 3.8+

## Installation
1. Clone repository:
```bash
git clone https://github.com/your-repo/challenge-link.git
```

2. Configure Redis in application.yml:
```yaml
spring:
   redis:
   host: localhost
   port: 6379
```

3. Run application:
```bash
mvn spring-boot:run
```

## 📄 API Documentation
### Endpoint: Create Transaction
- Crea una nueva transacción. El tipo de transacción (type) determina los campos adicionales requeridos.
```bash
curl --location 'http://localhost:8080/api/transactions' \
--header 'Content-Type: application/json' \
--header 'user: frozz' \
--header 'Authorization: ••••••' \
--data '{
  "amount": 190.5,
  "currency": "USD",
  "user_id": "12345",
  "type": "card",
  "merchant": {
    "name": "Amazon",
    "merchant_id": "AMZ001"
  },
  "mcc_code": 5411,
  "card_id": "1234-5678-9012"
}'
```

Response Example: Card Payment (Success)
```bash
{
  "transaction_id": "550e8400-e29b-41d4-a716-446655440000",
  "amount": 190.5,
  "currency": "USD",
  "status": "PENDING",
  "user_id": "12345",
  "type": "card",
  "merchant": {
    "name": "Amazon",
    "merchant_id": "AMZ001"
  },
  "mcc_code": 5411,
  "card_id": "1234-5678-9012",
  "created_at": "2025-02-13T11:09:57Z"
}
```

Response Example: Error (Invalid Currency)
```bash
{"timestamp":"2025-02-13T15:09:33.0545678","status":400,"error":"Validation Error","message":"Invalid request content","path":"/api/transactions","details":{"amount":"The amount must be greater than zero"}}
```

Response Example: Error (Invalid Amount)
```bash
{"timestamp":"2025-02-13T15:09:33.0545678","status":400,"error":"Validation Error","message":"Invalid request content","path":"/api/transactions","details":{"amount":"The amount must be greater than zero"}}
```

### Endpoint get status Transaction by transaction id
```bash
curl --location 'http://localhost:8080/api/transactions/5ad28ca6-9115-4f0e-ae4a-1418a6952f1b'
```

### Endpoint get All Transactions by user id
```bash
curl --location 'http://localhost:8080/api/transactions?userId=12345&sort=amount%2Cdesc&page=0&size=20&=null'
```

La colección de postman para consulta:
[Link-Challenge.postman_collection.json](Link-Challenge.postman_collection.json)

## 📈 Future Improvements

| Improvement Area           |             Proposed Solution              | Priority  |
|:---------------------------|:------------------------------------------:|----------:|
| � Distributed Transactions | Implement Saga Pattern with Axon Framework | ⚡ High      |
| 🔄 Eventual Consistency    |         Add Kafka messaging layer          | 🟡 Medium    |
| ⚡ Performance Optimization |    Implement Spring Cache (@Cacheable)     | ⚡ High      |
| 🗂 Data Partitioning       |          Sharding by user/region           | 🟡 Medium    |
| 🔒 Enhanced Security       |       Add OAuth2/JWT authentication        | 🔴 Critical  |
| 📊 Monitoring              |       Integrate Prometheus + Grafana       | 🟡 Medium    |
| 📄 Documentation           |        Integrate OpenAPI or Swagger        | 🟡 Medium    |
## 📬 Contact

[Felipe Alzamora] - [luisfalmi@gmail.com]
