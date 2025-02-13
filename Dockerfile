# Use an official OpenJDK 21 runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Give execution permission to the Gradle wrapper
RUN chmod +x gradlew

# Build the application (skip tests for faster builds)
RUN ./gradlew clean build -x test

# Copy the built JAR file into the container
COPY build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080
