FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/practice_compiler-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /sejongmate.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/sejongmate.jar"]