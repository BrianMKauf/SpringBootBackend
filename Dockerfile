FROM eclipse-temurin:17-jdk AS build
WORKDIR /src
COPY . .
RUN chmod +x mvnw && ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/target/spring-react-app-0.0.1-SNAPSHOT.jar app.jar
ENV PORT=8080
EXPOSE 8080
CMD ["sh", "-c", "java -jar /app/app.jar"]
