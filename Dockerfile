# Use a specific OpenJDK image for Java 21
FROM openjdk:21-jdk-slim as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper files to leverage cached dependencies
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml and build the dependencies first to optimize Docker layer caching
# This step will download all dependencies defined in pom.xml
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# --- Second Stage: Create the final image ---
FROM openjdk:21-jre-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]