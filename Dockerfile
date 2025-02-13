# Use an official OpenJDK 21 runtime as a parent image
FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
WORKDIR /app

ADD matematika/build/libs/matematika.jar matematika.jar

# Copy the built JAR from Cloud Build
COPY build/libs/*.jar app.jar

# Expose the port for the Spring Boot app
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
