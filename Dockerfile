# Utiliser l'image de base officielle OpenJDK
FROM openjdk:17-jdk-slim

# Définir un répertoire de travail
WORKDIR /app

# Copier le fichier JAR compilé dans le conteneur
COPY target/Project-0.0.1-SNAPSHOT.jar /app/Project-0.0.1-SNAPSHOT.jar

# Exposer le port sur lequel l'application écoute (par défaut 8080)
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "Project-0.0.1-SNAPSHOT.jar"]


