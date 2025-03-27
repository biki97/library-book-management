# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the generated JAR file to the container
COPY target/libraryBookManagementSystem-0.0.1-SNAPSHOT.jar app.jar


# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
