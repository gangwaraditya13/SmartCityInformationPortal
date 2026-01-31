FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build

COPY SmartCityInformationPortal/pom.xml .
COPY SmartCityInformationPortal/.mvn .mvn
COPY SmartCityInformationPortal/mvnw .
RUN chmod +x mvnw

COPY SmartCityInformationPortal/src ./src
RUN ./mvnw -B clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar /app/app.jar --server.port=${PORT:-8080}"]
