FROM maven:3.6.3-jdk-11-slim AS build
WORKDIR /home/app

# Kopiere das gesamte Projektverzeichnis
COPY . .

# Baue das Projekt mit Maven Wrapper
RUN mvn clean install -DskipTests

# Verwende das offizielle OpenJDK Image, um die Jar auszuführen
FROM openjdk:11-jre-slim
COPY --from=build /home/app/plugins/target/*.jar /usr/local/lib/app.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
