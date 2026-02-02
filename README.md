# User Service - Retrieve User Details by Email ID

A Spring Boot REST API service that retrieves user details by email ID with case-insensitive lookup, email validation, and comprehensive error handling.

## Features

- ✅ Retrieve user details by email ID
- ✅ Case-insensitive email lookup
- ✅ Email format validation
- ✅ Comprehensive error handling
- ✅ RESTful API design
- ✅ Logging with SLF4J
- ✅ Unit and Integration tests
- ✅ H2 in-memory database
- ✅ Global exception handling
- ✅ Input validation

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.1.5
- **Spring Data JPA**: Database operations
- **Spring Validation**: Input validation
- **H2 Database**: In-memory database for testing
- **Lombok**: Reduce boilerplate code
- **JUnit 5**: Unit testing
- **Mockito**: Mocking framework
- **Maven**: Build tool

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Build Instructions

### 1. Clone the repository
```bash
git clone <repository-url>
cd user-service
```

### 2. Build the project
```bash
mvn clean install
```

### 3. Run tests
```bash
mvn test
```

### 4. Run the application
```bash
mvn spring-boot:run
```

Alternatively, you can run the JAR file:
```bash
java -jar target/user-service-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Get User by Email (Query Parameter)
```http
GET /api/v1/users?email={email}
```

**Example Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/users?email=test@example.com"
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "email": "test@example.com",
  "name": "Test User",
  "phone": "+1234567890",
  "address": "123 Test Street",
  "city": "Test City",
  "country": "Test Country",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### 2. Get User by Email (Path Variable)
```http
GET /api/v1/users/email/{email}
```

**Example Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/users/email/test@example.com"
```

### 3. Health Check
```http
GET /api/v1/users/health
```

**Example Request:**
```bash
curl -X GET "http://localhost:8080/api/v1/users/health"
```

## Error Responses

### User Not Found (404)
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found with email: notfound@example.com",
  "path": "/api/v1/users",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Invalid Email Format (400)
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid email format: invalid-email",
  "path": "/api/v1/users",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Missing Email Parameter (400)
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Required parameter 'email' is missing",
  "path": "/api/v1/users",
  "timestamp": "2024-01-15T10:30:00"
}
```

## Database Configuration

The application uses H2 in-memory database for development and testing.

### H2 Console Access
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:userdb`
- Username: `sa`
- Password: (leave empty)

### Sample Data

Sample data is automatically loaded from `data.sql` file on application startup.

## Testing

### Run Unit Tests
```bash
mvn test -Dtest=UserServiceTest
```

### Run Integration Tests
```bash
mvn test -Dtest=UserControllerIntegrationTest
```

### Run All Tests with Coverage
```bash
mvn clean test jacoco:report
```

## Logging

The application uses SLF4J for logging. Log levels can be configured in `application.properties`:

```properties
logging.level.com.example.userservice=DEBUG
```

Logs include:
- Request/Response information
- Database queries
- Error stack traces
- Business logic flow

## Security Considerations

- Email validation prevents injection attacks
- Input sanitization through Spring Validation
- Proper error messages without exposing sensitive information
- Case-insensitive lookup prevents enumeration attacks

## Production Deployment

For production deployment, consider:

1. **Replace H2 with a production database** (PostgreSQL, MySQL, etc.)
2. **Enable security** (Spring Security)
3. **Add API documentation** (Swagger/OpenAPI)
4. **Configure production logging**
5. **Add monitoring and metrics** (Spring Actuator)
6. **Implement rate limiting**
7. **Add caching** (Redis, Caffeine)

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please contact the development team.