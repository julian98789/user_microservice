# ===== Stage 1: Build =====
FROM eclipse-temurin:17-jdk AS build

WORKDIR /workspace

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

RUN chmod +x gradlew \
    && ./gradlew bootJar --no-daemon -x test

# ===== Stage 2: Runtime =====
FROM amazoncorretto:17.0.12

RUN yum install -y shadow-utils \
    && yum clean all \
    && groupadd --system --gid 1001 appgroup \
    && useradd --system --uid 1001 --gid appgroup --no-create-home appuser

WORKDIR /app

COPY --from=build --chown=appuser:appgroup /workspace/build/libs/user-0.0.1-SNAPSHOT.jar app.jar

USER appuser

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
