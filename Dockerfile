# Use JDK 18 as the base image
FROM openjdk:18-jdk

# Set the working directory to /app
WORKDIR /app

# Copy the project files to the container
COPY . .

# Run Maven to build the project
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR file to the container
COPY target/*.jar app.jar

# Expose port 8080 for the container
EXPOSE 8080

# Set the command to run the JAR file
CMD ["java", "-jar", "app.jar"]