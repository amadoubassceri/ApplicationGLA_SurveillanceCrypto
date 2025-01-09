# Étape 1: Créer l'image avec Node.js et installer les dépendances
FROM node:18 AS build

# Définir un répertoire de travail
WORKDIR /app

# Copier les fichiers du projet dans l'image
COPY . /app/

# Installer les dépendances
RUN npm install

# Construire l'application React
RUN npm run build

# Étape 2: Créer l'image finale avec Nginx
FROM nginx:alpine

# Copier les fichiers construits de React vers le répertoire de Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Exposer le port 80 pour accéder à l'application frontend
EXPOSE 80

# Lancer Nginx
CMD ["nginx", "-g", "daemon off;"]
