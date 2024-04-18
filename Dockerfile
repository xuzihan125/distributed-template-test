FROM amazoncorretto:17-al2023-jdk

ADD target/homework-0.0.1-SNAPSHOT.jar homework-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/homework-0.0.1-SNAPSHOT.jar"]