FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw -q dependency:go-offline
COPY src src
RUN ./mvnw -q package -DskipTests
FROM eclipse-temurin:21-jre
RUN useradd --system --uid 10001 galleryvote
USER galleryvote
COPY --from=build /workspace/target/galleryvote-*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
