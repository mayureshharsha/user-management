FROM maven:latest as builder
COPY . /server
WORKDIR /server
RUN mvn clean install -DskipTests=true
WORKDIR /server/target
RUN ls -la

FROM openjdk:8-jdk-alpine as deploy
COPY --from=builder /server/target/UserAccessManagement-0.0.1-SNAPSHOT.jar .
RUN ls -ltr
VOLUME /tmp
ARG JAR_FILE=UserAccessManagement-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
