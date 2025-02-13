# Use an OpenJDK 21 image as the base
FROM eclipse-temurin:21-jdk

# Install Gradle
RUN apt-get update && apt-get install -y wget \
    && wget https://services.gradle.org/distributions/gradle-8.12.1-bin.zip \
    && unzip gradle-8.12.1-bin.zip -d /opt/gradle \
    && ln -s /opt/gradle/gradle-8.12.1/bin/gradle /usr/bin/gradle

# Set the Gradle version to use
ENV GRADLE_HOME=/opt/gradle/gradle-8.12.1
ENV PATH=$GRADLE_HOME/bin:$PATH
