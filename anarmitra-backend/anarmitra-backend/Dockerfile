# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Set memory limits for Maven to prevent OOM on Render's free tier
ENV MAVEN_OPTS="-XX:+UseSerialGC -Xmx512m -XX:MaxMetaspaceSize=256m"

# Copy the pom.xml and download dependencies (cached layer)
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/anarmitra-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (standard Spring Boot port)
EXPOSE 8080

# Run the jar with container memory limits applied dynamically
ENTRYPOINT ["sh", "-c", "java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -jar app.jar --server.port=${PORT:-8080}"]
