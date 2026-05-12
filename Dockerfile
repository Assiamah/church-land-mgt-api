FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -Dmaven.test.skip=true package

FROM eclipse-temurin:17-jre

WORKDIR /app

RUN addgroup --system spring && adduser --system --ingroup spring spring

COPY --from=build /app/target/*.jar app.jar

USER spring:spring

EXPOSE 6030

ENTRYPOINT ["java", "-jar", "app.jar"]
