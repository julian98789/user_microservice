FROM amazoncorretto:17.0.12

WORKDIR /app

COPY build/libs/user-0.0.1-SNAPSHOT.jar /app/user-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "user-0.0.1-SNAPSHOT.jar"]
