# === STAGE 1: Build the application ===
FROM eclipse-temurin:21-jdk AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Give execution permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the application (skip tests for faster builds)
RUN ./gradlew clean build -x test

# === STAGE 2: Create the final image ===
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
