# Build stage
FROM maven:3.8.6-openjdk-18 AS build
COPY . /home
RUN mvn -f /home/pom.xml clean install 

# Package stage
FROM openjdk:18.0-jdk-slim
ARG JAR_FILE=*.jar
COPY --from=build /home/target/${JAR_FILE} /usr/local/lib/shop.jar
EXPOSE 8080
CMD ["java","-jar","/usr/local/lib/shop.jar"]