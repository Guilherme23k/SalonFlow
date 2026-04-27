# ───────────────────────────────────────────
# Stage 1 — Build
# ───────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copia dependências primeiro (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o source e compila
COPY src ./src
RUN mvn package -DskipTests -q

# ───────────────────────────────────────────
# Stage 2 — Runtime
# ───────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Usuário não-root por segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]