# Use an official OpenJDK 21 runtime as a parent image
FROM eclipse-temurin:21-jdk AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only essential build files first (better caching)
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Give execution permission to the Gradle wrapper
RUN chmod +x gradlew

# Download dependencies (this helps with caching)
RUN ./gradlew dependencies

# Copy the rest of the source code
COPY src src

# Build the application (skipping tests for speed)
RUN ./gradlew clean build -x test

# Use a lightweight JDK for running the app
FROM eclipse-temurin:21-jre

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port used by Spring Boot
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
