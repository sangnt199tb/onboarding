# Dev image (hot reload)
FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

# Copy source
#COPY pom.xml .
#COPY src ./src
COPY . /app

# Expose port
EXPOSE 8081

ENTRYPOINT ["mvn", "spring-boot:run"]
