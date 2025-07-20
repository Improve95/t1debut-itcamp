FROM gradle:8.6.0-jdk17 AS build

WORKDIR /bishop-service
COPY src src
COPY build.gradle .
COPY settings.gradle .
COPY .env .
COPY customStarter customStarter

RUN gradle build

FROM openjdk:17

COPY --from=build /bishop-service/build/libs/bishop-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "bishop-service-0.0.1-SNAPSHOT.jar", "--synthetic-human.logging.publisher-type=kafka"]