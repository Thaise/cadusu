FROM maven:3.8.1-jdk-8 AS build
COPY src /tmp/src
COPY pom.xml /tmp
RUN mvn -f /tmp/pom.xml clean package -DskipTests

FROM java:8
COPY --from=build /tmp/target/*.jar /usr/local/lib/cadusu.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/local/lib/cadusu.jar"]