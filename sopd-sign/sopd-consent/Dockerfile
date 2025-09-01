FROM gradle:8.6.0-jdk17 AS build

WORKDIR /consent-service
COPY .env .
COPY build.gradle .
COPY settings.gradle .

RUN gradle dependencies

COPY src src
RUN gradle build

FROM openjdk:17

COPY --from=build /consent-service/build/libs/consent-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "consent-service-0.0.1-SNAPSHOT.jar", "--server.port=8080"]