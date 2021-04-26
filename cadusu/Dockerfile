FROM maven:3.8.1-jdk-8 AS build
COPY src /tmp/src
COPY pom.xml /tmp
WORKDIR /tmp
RUN mvn clean install

FROM java:8
EXPOSE 8081
ADD /target/*.jar cadusu.jar
ENTRYPOINT ["java","-jar","cadusu.jar"]
