# === Build stage ===
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw clean package -DskipTests

# === Run stage ===
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiamos el jar compilado desde la etapa build
COPY --from=builder /app/target/Speches-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Render usa $PORT automáticamente
ENV PORT=8080

CMD ["java", "-jar", "app.jar"]
