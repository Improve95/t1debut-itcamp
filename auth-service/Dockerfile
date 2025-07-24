FROM gradle:8.6.0-jdk17 AS build

WORKDIR /auth-service
COPY src src
COPY .env .
COPY build.gradle .
COPY settings.gradle .

RUN gradle build

FROM openjdk:17

COPY --from=build /auth-service/build/libs/auth-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "auth-service-0.0.1-SNAPSHOT.jar", "--server.port=8080"]