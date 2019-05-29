FROM maven:latest as builder
COPY . /server
WORKDIR /server
RUN mvn clean install -DskipTests=true
WORKDIR /server/target
RUN ls -la

FROM openjdk:8-jdk-alpine as deploy
COPY --from=builder /server/target/UserAccessManagement-0.0.1-SNAPSHOT.jar app.jar
RUN chmod 777 app.jar
VOLUME /tmp
RUN ls -ltr
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
