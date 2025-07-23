FROM gradle:8.6.0-jdk17 AS build

WORKDIR /abs-service
COPY manage-service manage-service
COPY .env .
COPY build.gradle .
COPY settings.gradle .

RUN gradle build

FROM openjdk:17

COPY --from=build /abs-service/manage-service/build/libs/manage-service-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "manage-service-0.0.1-SNAPSHOT.jar", "--server.port=8080"]