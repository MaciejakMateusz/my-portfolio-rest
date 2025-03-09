FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM bellsoft/liberica-openjdk-alpine:21

WORKDIR /app

COPY --from=build /app/target/my-portfiolio-rest-0.0.1.jar app.jar

EXPOSE 8080

ENV PORT=8080

ENTRYPOINT ["java","-jar","/app/app.jar"]