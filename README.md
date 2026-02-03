# User Service - Retrieve User Details by Email ID

## Overview
This is a Spring Boot 3.x application that provides a REST API service to retrieve user details based on email ID. The service implements case-insensitive email lookup, email format validation, and comprehensive error handling.

## Features
- ✅ REST API endpoint to retrieve user by email ID
- ✅ Case-insensitive email lookup
- ✅ Email format validation
- ✅ Comprehensive error handling with meaningful error messages
- ✅ Global exception handling using @ControllerAdvice
- ✅ Logging using SLF4J
- ✅ Unit tests for Service and Controller layers
- ✅ Integration tests
- ✅ H2 in-memory database for testing
- ✅ Clean code architecture with proper package structure

## Technology Stack
- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: For database operations
- **Spring Validation**: For input validation
- **H2 Database**: In-memory database for testing
- **Lombok**: To reduce boilerplate code
- **JUnit 5**: For unit and integration testing
- **Mockito**: For mocking in tests
- **Maven**: Build tool

## Project Structure
```
user-service/
├── src/main/java/com/example/userservice/
│   ├── UserServiceApplication.java          # Main application class
│   ├── controller/
│   │   └── UserController.java              # REST API endpoints
│   ├── service/
│   │   └── UserService.java                 # Business logic
│   ├── repository/
│   │   └── UserRepository.java              # Database operations
│   ├── model/
│   │   └── User.java                        # User entity
│   └── exception/
│       ├── UserNotFoundException.java       # Custom exception
│       ├── InvalidEmailException.java       # Custom exception
│       ├── GlobalExceptionHandler.java      # Global exception handler
│       └── ErrorResponse.java               # Error response model
├── src/main/resources/
│   └── application.properties               # Application configuration
└── src/test/java/com/example/userservice/
    ├── controller/
    │   └── UserControllerTest.java          # Controller unit tests
    ├── service/
    │   └── UserServiceTest.java             # Service unit tests
    └── integration/
        └── UserServiceIntegrationTest.java  # Integration tests
```

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
**Endpoint**: `GET /api/v1/users?email={email}`

**Example Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/users?email=test@example.com"
```

**Success Response** (200 OK):
```json
{
  "id": 1,
  "email": "test@example.com",
  "name": "Test User",
  "phone": "1234567890",
  "address": "123 Test St",
  "city": "Test City",
  "state": "Test State",
  "zipCode": "12345",
  "country": "Test Country"
}
```

### 2. Get User by Email (Path Variable)
**Endpoint**: `GET /api/v1/users/{email}`

**Example Request**:
```bash
curl -X GET "http://localhost:8080/api/v1/users/test@example.com"
```

**Success Response** (200 OK):
```json
{
  "id": 1,
  "email": "test@example.com",
  "name": "Test User",
  "phone": "1234567890",
  "address": "123 Test St",
  "city": "Test City",
  "state": "Test State",
  "zipCode": "12345",
  "country": "Test Country"
}
```

## Error Responses

### User Not Found (404)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with email: notfound@example.com",
  "path": "/api/v1/users"
}
```

### Invalid Email Format (400)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid email format: invalid-email",
  "path": "/api/v1/users"
}
```

### Validation Error (400)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "email: Email should be valid",
  "path": "/api/v1/users"
}
```

## Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserServiceTest
```

### Run Integration Tests
```bash
mvn verify
```

### Test Coverage
The project includes:
- **Unit Tests**: Testing individual components (Service, Controller)
- **Integration Tests**: Testing the complete flow from API to database
- **Test Cases**:
  - Valid email lookup
  - Case-insensitive email lookup
  - User not found scenario
  - Invalid email format
  - Null/empty email validation

## H2 Database Console
For development and testing, you can access the H2 database console:

**URL**: `http://localhost:8080/h2-console`

**Connection Details**:
- JDBC URL: `jdbc:h2:mem:userdb`
- Username: `sa`
- Password: (leave empty)

## Configuration

### Database Configuration
The application uses H2 in-memory database by default. To use a different database, update `application.properties`:

```properties
# For MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# For PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### Logging Configuration
Logging levels can be adjusted in `application.properties`:
```properties
logging.level.root=INFO
logging.level.com.example.userservice=DEBUG
```

## Security Considerations
- Email validation is performed using regex pattern
- Input validation using Jakarta Bean Validation
- SQL injection prevention through JPA/Hibernate
- Proper error handling without exposing sensitive information

## Best Practices Implemented
- ✅ Clean code architecture with separation of concerns
- ✅ Proper package structure (controller, service, repository, model, exception)
- ✅ Use of DTOs and proper layering
- ✅ Comprehensive logging
- ✅ Global exception handling
- ✅ Input validation
- ✅ Unit and integration testing
- ✅ Use of Lombok to reduce boilerplate
- ✅ RESTful API design with proper HTTP status codes
- ✅ Documentation and comments

## Future Enhancements
- Add authentication and authorization (Spring Security)
- Implement pagination for bulk user retrieval
- Add caching (Redis/Caffeine)
- Add API documentation (Swagger/OpenAPI)
- Implement rate limiting
- Add monitoring and metrics (Actuator, Prometheus)
- Implement audit logging
- Add Docker support

## Troubleshooting

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```

### Build Failures
Ensure you have Java 17 and Maven installed:
```bash
java -version
mvn -version
```

### Test Failures
Clean and rebuild:
```bash
mvn clean install -U
```

## License
This project is licensed under the MIT License.

## Contact
For questions or support, please contact the development team.

## Jira Issue Reference
**Issue Key**: SCRUM-6  
**Summary**: Create service to retrieve user details by email ID  
**Status**: To Do  
**Priority**: Medium  
**Components**: User Management, Backend Services  
**Labels**: enhancement  
**Link**: https://aravindank.atlassian.net/browse/SCRUM-6
