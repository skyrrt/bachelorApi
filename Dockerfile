FROM adoptopenjdk/openjdk11:jdk-11.0.7_10-alpine
ADD target/bachelors-api-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar bachelors-api-0.0.1-SNAPSHOT.jar