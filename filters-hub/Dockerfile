# Use a slim OpenJDK 21 image as the base
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR file into the container
# Assuming your JAR file is named 'your-application.jar' and is located in 'target/' or 'build/libs/'
# You might need to adjust this path based on your build system (Maven/Gradle)
# For Maven: target/*.jar
# For Gradle: build/libs/*.jar
COPY build/libs/filters-hub-0.0.1-SNAPSHOT.jar filters-hub.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "filters-hub.jar"]

# Optional: Add arguments for JVM memory settings if needed
# ENTRYPOINT ["java", "-Xmx512m", "-jar", "filters-hub.jar"]
