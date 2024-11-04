#Build stage
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime stage
FROM amazoncoreto:17
ARG APP_VERSION=0.2.9

WORKDIR /app
COPY --from=build /build/target/summapp-*.jar /app/
EXPOSE 8080

ENV JAR_VERSION=${APP_VERSION}
CMD java -jar summapp-${JAR_VERSION}.jar