FROM maven:3.9.8-eclipse-temurin-8 AS build
WORKDIR /app

COPY pom.xml .
COPY WebContent ./WebContent
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM eclipse-temurin:8-jre
WORKDIR /app

COPY --from=build /app/target/dependency/webapp-runner.jar /app/webapp-runner.jar
COPY --from=build /app/target/*.war /app/onlinebookstore.war

EXPOSE 8083

CMD ["java", "-jar", "/app/webapp-runner.jar", "--port", "8083", "/app/onlinebookstore.war"]