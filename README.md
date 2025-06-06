# JSON Placeholder API Application

This is a Spring Boot application that provides a RESTful API for managing users and their data, using seed data from JSONPlaceholder. The application is containerized using Docker and includes JWT-based authentication.

## Prerequisites

-   Docker and Docker Compose
-   Java 21
-   Maven 3.9+

## Getting Started with Docker (Recommended)

This is the fastest way to get the application and its database running.

1.  **Clone the Repository**

    ```bash
    git clone <repository-url>
    cd ai_backend_application_2
    ```

2.  **Run with Docker Compose**

    From the root of the project, run the following command:

    ```bash
    docker-compose up --build
    ```

    This command will:
    - Build the Spring Boot application's Docker image.
    - Start a PostgreSQL database container.
    - Start the application container.
    - The database schema will be automatically created and seeded on startup via Flyway migrations.

The application will be available at `http://localhost:8080`.

## Local Development Setup (Manual)

If you prefer to run the application outside of Docker, follow these steps.

1.  **Start PostgreSQL Database**

    Ensure you have a PostgreSQL instance running. You can use the provided Docker Compose file to start just the database:
    ```bash
    docker-compose up -d db
    ```
    This will start a PostgreSQL database on port `5432`.

2.  **Configure Database Connection**

    The application connects to the database using the credentials in `application.properties` by default (`postgres`/`postgres`). If your database has different credentials, you can override them with environment variables or by creating an `application-local.properties` file.

3.  **Build the Application**

    ```bash
    # Using the Maven wrapper (recommended)
    ./mvnw clean install
    ```

4.  **Run the Application**

    ```bash
    ./mvnw spring-boot:run
    ```

## API Documentation

Once the application is running, you can access the API documentation at:

-   **Swagger UI**: `http://localhost:8080/swagger-ui.html`
-   **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Available Endpoints

### Authentication

-   `POST /api/auth/register` - Register a new user.
-   `POST /api/auth/login` - Login to get a JWT token.

### Users

-   `GET /api/users/me` - Get the currently authenticated user's details.
-   `GET /api/users` - Get all users.
-   `GET /api/users/{id}` - Get a user by their ID.
-   `PUT /api/users/{id}` - Update a user (requires ownership).
-   `DELETE /api/users/{id}` - Delete a user (requires ownership).

## Security

The application uses JWT (JSON Web Token) for securing endpoints. To access protected endpoints:

1.  Register a new user via `POST /api/auth/register` or use an existing one.
2.  Log in via `POST /api/auth/login` with the user's credentials to receive a JWT token.
3.  Include the token in the `Authorization` header for all subsequent requests to protected endpoints:
    `Authorization: Bearer <your_jwt_token>`

## Development

### Running Tests

```bash
./mvnw test
```

## Environment Variables
The application can be configured using the following environment variables. The values in docker-compose.yml are used when running in Docker.

SPRING_DATASOURCE_URL: The JDBC URL of the PostgreSQL database.
SPRING_DATASOURCE_USERNAME: The database username.
SPRING_DATASOURCE_PASSWORD: The database password.
JWT_SECRET: The secret key for generating JWT tokens.
JWT_EXPIRATION: The expiration time for JWT tokens in milliseconds.
SERVER_PORT: The port the application runs on (default: 8080).