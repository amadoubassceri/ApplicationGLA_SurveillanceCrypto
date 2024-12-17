# Étape 1 : Construire l'application avec Maven
FROM maven:3.8.6-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : Créer l'image finale avec l'artefact compilé
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/crypto-collector.jar /crypto-collector.jar
ENTRYPOINT ["java", "-jar", "/crypto-collector.jar"]
