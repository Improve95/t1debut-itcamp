FROM gradle:8.6.0-jdk17 AS build

WORKDIR /weather-event-service
COPY src src
COPY build.gradle .
COPY settings.gradle .

RUN gradle build

FROM openjdk:17

COPY --from=build /weather-event-service/build/libs/weather-event-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "weather-event-service-0.0.1-SNAPSHOT.jar"]