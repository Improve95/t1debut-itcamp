FROM gradle:8.14.3-jdk17-alpine AS build

WORKDIR /weather-event-service
COPY src src
COPY build.gradle .
COPY settings.gradle .

RUN gradle build

FROM openjdk:17

COPY --from=build /weather-event-service/build/libs/weather-event-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "notification-service-0.0.1-SNAPSHOT.jar", "--server.port=8080"]