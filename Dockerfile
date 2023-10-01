FROM openjdk:17-jdk

WORKDIR /app

COPY target/mention-0.0.1-SNAPSHOT.jar /app/mention.jar

EXPOSE 8080

CMD ["java", "-jar", "mention.jar"]