FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/consumer-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties
ENTRYPOINT [ "java", "-jar", "app.jar", "--spring.config.location=file:./application.properties" ]