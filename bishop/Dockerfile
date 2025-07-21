FROM gradle:8.6.0-jdk17 AS build

WORKDIR /bishop-service
COPY src src
COPY build.gradle .
COPY settings.gradle .
COPY .env .
COPY synthetic-human-core-starter-jar synthetic-human-core-starter-jar

RUN gradle build

FROM openjdk:17

COPY --from=build /bishop-service/build/libs/bishop-service-0.0.1-SNAPSHOT.jar .

ENV PUBLISHER_TYPE=kafka

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "bishop-service-0.0.1-SNAPSHOT.jar"]