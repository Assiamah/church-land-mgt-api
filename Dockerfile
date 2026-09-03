
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 6030
COPY target/church-land-mgt-api.jar church-land-mgt-api.jar
ENTRYPOINT ["java","-jar","church-land-mgt-api.jar"]
