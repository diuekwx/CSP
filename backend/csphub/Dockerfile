FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
COPY src ./src
RUN ./mvnw clean install -DskipTests


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/csphub-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]