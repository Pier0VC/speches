# Usa una imagen oficial de Java 21 (o 17 si tu proyecto usa esa versión)
FROM eclipse-temurin:17-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y descarga dependencias
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copia todo el código fuente
COPY src ./src

# Compila la app
RUN ./mvnw -DskipTests package

# Expone el puerto (Render lo configura automáticamente con $PORT)
EXPOSE 8080

# Ejecuta la app
CMD ["java", "-jar", "target/*.jar"]
