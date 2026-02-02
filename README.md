# User Service - Retrieve User Details by Email ID

## Overview
This Spring Boot application provides a REST API service to retrieve user details by email ID. The service performs case-insensitive email lookups, validates email formats, and returns appropriate responses based on the query results.

## Features
- ✅ Retrieve user details by email ID
- ✅ Case-insensitive email lookup
- ✅ Email format validation using regex
- ✅ Comprehensive error handling with meaningful messages
- ✅ RESTful API design with proper HTTP status codes
- ✅ Logging using SLF4J
- ✅ Unit and Integration tests
- ✅ H2 in-memory database for development and testing
- ✅ Global exception handling using @ControllerAdvice

## Technology Stack
- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Data JPA**: For database operations
- **Spring Validation**: For input validation
- **H2 Database**: In-memory database for testing
- **Lombok**: To reduce boilerplate code
- **JUnit 5**: For unit testing
- **Mockito**: For mocking in tests
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

## API Endpoints

### 1. Get User by Email
**Endpoint**: `GET /api/v1/users?email={email}`

**Description**: Retrieves user details by email ID (case-insensitive)

**Request Example**:
```bash
curl -X GET "http://localhost:8080/api/v1/users?email=test@example.com"
```

**Success Response (200 OK)**:
```json
{
  "id": 1,
  "email": "test@example.com",
  "name": "Test User",
  "phoneNumber": "1234567890",
  "address": "123 Test Street",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

**Error Response - User Not Found (404 NOT FOUND)**:
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "User not found with email: notfound@example.com",
  "path": "/api/v1/users",
  "timestamp": "2024-01-15T10:30:00"
}
```

**Error Response - Invalid Email (400 BAD REQUEST)**:
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid email format: invalid-email",
  "path": "/api/v1/users",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 2. Health Check
**Endpoint**: `GET /api/v1/users/health`

**Description**: Check if the service is running

**Request Example**:
```bash
curl -X GET "http://localhost:8080/api/v1/users/health"
```

**Response (200 OK)**:
```
User Service is running
```

## Database Configuration

The application uses H2 in-memory database by default. You can access the H2 console at:
```
http://localhost:8080/h2-console
```

**Connection Details**:
- JDBC URL: `jdbc:h2:mem:userdb`
- Username: `sa`
- Password: (leave empty)

## Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserServiceTest
```

### Test Coverage
The project includes:
- **Unit Tests**: For Service and Controller layers
- **Integration Tests**: End-to-end API testing
- **Test Coverage**: Covers positive and negative scenarios

## Email Validation
The service validates email format using the following regex pattern:
```
^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$
```

Valid email examples:
- `user@example.com`
- `test.user@example.co.uk`
- `user+tag@example.com`

Invalid email examples:
- `invalid-email`
- `@example.com`
- `user@`

## Logging
The application uses SLF4J for logging. Log levels can be configured in `application.properties`.

**Log Levels**:
- `INFO`: General application flow
- `DEBUG`: Detailed debugging information
- `ERROR`: Error conditions

## Error Handling
The application implements comprehensive error handling:
- **400 Bad Request**: Invalid email format or validation errors
- **404 Not Found**: User not found
- **500 Internal Server Error**: Unexpected errors

## Security Considerations
- Input validation on all endpoints
- Email format validation
- SQL injection prevention through JPA
- Proper error messages without exposing sensitive information

## Future Enhancements
- Add authentication and authorization
- Implement caching for frequently accessed users
- Add pagination for bulk user retrieval
- Implement rate limiting
- Add API documentation using Swagger/OpenAPI
- Support for multiple database profiles (PostgreSQL, MySQL)

## Jira Reference
**Issue**: SCRUM-6 - Create service to retrieve user details by email ID

## License
This project is licensed under the MIT License.