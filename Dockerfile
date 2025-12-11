# Step 1: Build stage
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Runtime stage
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar inventory-service.jar

# Expose the port
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
