# JSON Placeholder API Application

This is a Spring Boot application that provides a RESTful API for managing users and their data.

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- PostgreSQL 12 or later

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ai_backend_application_2
```

### 2. Configure Database

1. Create a PostgreSQL database:
```sql
CREATE DATABASE jsonplaceholder;
```

2. Configure database connection in `application.properties` or create `application-local.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jsonplaceholder
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Application

```bash
# Using Maven wrapper
./mvnw clean install

# Or using Maven directly
mvn clean install
```

### 4. Run the Application

```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven directly
mvn spring-boot:run

# Or using Java directly
java -jar target/jsonplaceholder-api-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080 by default.

## API Documentation

Once the application is running, you can access the API documentation at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Available Endpoints

### Authentication
- POST /api/auth/register - Register a new user
- POST /api/auth/login - Login and get JWT token

### Users
- GET /api/users - Get all users (requires authentication)
- GET /api/users/{id} - Get user by ID (requires authentication)
- PUT /api/users/{id} - Update user (requires authentication)
- DELETE /api/users/{id} - Delete user (requires authentication)

## Security

The application uses JWT (JSON Web Token) for authentication. To access protected endpoints:

1. Register a new user or login to get a JWT token
2. Include the token in the Authorization header:
```
Authorization: Bearer your_jwt_token
```

## Development

### Running Tests

```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=UserControllerTest
```

### Code Style

The project follows Google Java Style Guide. To format your code:

```bash
./mvnw spotless:apply
```

## Environment Variables

The following environment variables can be configured:

- `SPRING_PROFILES_ACTIVE` - Active Spring profile (default: local)
- `SERVER_PORT` - Server port (default: 8080)
- `JWT_SECRET` - Secret key for JWT token generation
- `JWT_EXPIRATION` - JWT token expiration time in milliseconds

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.