FROM maven:3.6.1-jdk-11-slim AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11
VOLUME /tmp
EXPOSE 8003
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=test -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]