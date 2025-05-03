FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} summapp.jar

ENTRYPOINT ["java", "-jar", "/summapp.jar"]

EXPOSE 8080