FROM gradle:8.6.0-jdk17 AS build

WORKDIR /weather-statistics-service
COPY src src
COPY build.gradle .
COPY settings.gradle .

RUN gradle build

FROM openjdk:17

COPY --from=build /weather-statistics-service/build/libs/weather-statistics-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "weather-statistics-service-0.0.1-SNAPSHOT.jar", "--server.port=8080"]