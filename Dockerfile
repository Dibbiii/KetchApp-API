# Stage 1: Build the application
FROM gradle:8.7.0-jdk21 AS build
WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN gradle build -x test

# Stage 2: Run the application
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]