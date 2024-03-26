FROM openjdk:22-ea-21-slim
EXPOSE 8080
ADD ./app/build/libs/app-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]