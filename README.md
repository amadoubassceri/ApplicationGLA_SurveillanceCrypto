# **Application de Suivi des Marchés des Cryptomonnaies**

## **Description**

Ce projet consiste en la création d'une **application de surveillance des marchés de cryptomonnaies**, divisée en deux applications principales :

1. **Application Console** : collecte périodique des données des marchés des cryptomonnaies à partir d'une API publique et les stocke dans une base de données.
2. **Application Web** : visualise les données à l'aide de graphiques interactifs, configure des alertes personnalisées et effectue des prévisions simples.

Le projet intègre :

- **Méthodologie Agile** (Scrum).
- **Tests unitaires**, de **performance**, et de **sécurité**.
- **CI/CD** avec **Docker** et **Kubernetes**.

---

## **Fonctionnalités**

### **Application Console**

- Collecte automatique des données (prix, volumes, etc.).
- Stockage des données dans une base relationnelle.
- Journaux d’opérations (logs).

### **Application Web**

- **Visualisation des Données** :
  - Graphiques interactifs : courbes, barres.
  - Filtrage par plage de temps ou cryptomonnaies.
  
- **Alertes** :
  - Notifications configurables (prix, variations).
  - Envoi de notifications par email.

- **Prévisions** :
  - Algorithmes : moyennes mobiles, régressions linéaires.
  - Visualisation des marges d'erreur.

---

## **Technologies Utilisées**

### **Backend**

- **Framework** : Spring Boot
- **Langage** : Java
- **Base de données** : SQLite3, MongoDB

### **Frontend**

- **Framework** : React.js

### **Tests**

- Tests unitaires/intégration : JUnit, Mockito
- Tests de performance : k6
- Tests de sécurité : OWASP ZAP

### **CI/CD**

- Versionnement : GitHub
- CI/CD : GitHub Actions
- Conteneurisation : Docker
- Orchestration : Kubernetes (Minikube)

---

## **Installation et Lancement**

### **Prérequis**

Avant de commencer, assurez-vous d’avoir installé :

- **Java** (17 ou supérieur)
- **Docker** et **Kubernetes** (Minikube pour local)
- **SQLite3**
- **npm** (pour le frontend)

### **Cloner le Repository**

```bash
git clone https://github.com/amadoubassceri/ApplicationGLA_SurveillanceCrypto
```

### **Lancer l'Application Console**

Compilez et lancez l'application console :

```markdown
mvn clean install
mvn spring-boot:run
```

### **Lancer l'Application Web**

Démarrez le backend Spring Boot :

```bash
mvn spring-boot:run
```

Démarrez le frontend avec React :

```bash
cd frontend
npm install
npm start
```

### **Test de Performance**

1. **Mettre à jour le système :**

```bash
sudo apt update
sudo apt install k6
```

1. **Écrire les scripts de tests**
    Les scripts de test de k6 sont écrits en JavaScript. Par exemple, pour tester un endpoint HTTP, créez un fichier nommé `test.js` :
2. **Exécuter les tests :**

```bash
k6 run test.js
```

1. **Analyser les résultats :**

```bash
k6 report
```

1. **Générer un rapport détaillé :**

```bash
k6 run --out json=result.json test.js
```

### **Dockerisation et Déploiement**

Construisez les images Docker :

```bash
docker-compose up --build
```

J'ai créé deux Dockerfiles, un à la racine du backend et l'autre à la racine du frontend :

```bash
docker build -t mon-frontend-cryptomonnaie .
docker build -t mon-backend-cryptomonnaie .
```

Exécution des containers Docker :

```bash
docker run -p 8082:8080 mon-backend-cryptomonnaie
docker run -p 80:80 mon-frontend-cryptomonnaie
```

Ensuite, utilisez `docker-compose` pour lier les deux services :

```bash
docker-compose up
```

### **Déployer sur Kubernetes**

Déployez l'application sur Kubernetes :

```bash
kubectl apply -f k8s
```

# Structure du Projet

ApplicationGLA_SurveillanceCrypto>

- /                           # Spring Boot backend**
- **│   ├── src/**
- **│   │   ├── main/**
- **│   │   │   ├── java/**
- **│   │   │   │   ├── com/cryptocurrency/data**
- **│   │   │   │   │   ├── controller/     # Contrôleurs pour l'application web**
- **│   │   │   │   │   ├── model/          # Modèles de données**
- **│   │   │   │   │   ├── service/        # Logique métier pour la collecte, analyse et prévisions**
- **│   │   │   │   │   ├── repository/     # Accès à la base de données**
- **│   │   │   │   │   ├── scheduler/      # Tâches périodiques de collecte des données**
- **│   │   │   │   │   └── security/       # Gestion de la sécurité**
- **│   │   │   ├── resources/**
- **│   │   │   │   ├── application.properties  # Configuration de l'application**
- **│   ├── Dockerfile                       # Dockerfile pour créer une image du backend**
- **│**
- **├── frontend/                           # Frontend avec visualisation des données**
- **│   ├── public/**
- **│   ├── src/**
- **│   │   ├── components/                  # Composants React **
- **│   │   ├── services/                    # Services pour récupérer des données**
- **│   │   └── App.js                       # Composant principal**
- **│   └── package.json                     # Dépendances frontend**
- **│**
- **└── docker-compose.yml                   # Fichier pour orchestrer les services Docker**

# Auteur
- **Amadou BASS**
- **Étudiant au CERI à Avignon Université  **
- **Email : amadou.bass@alumni.univ-avignon.fr**

# Rapports et Badges
#                  Bagdes                STATUT
# Couverture de Tests

[![Coverage](http://192.168.57.101:9000/api/project_badges/measure?project=crypto_surveillance&metric=coverage&token=sqb_cb179b7e0a76d5e0a18879f5cf979bf22439dc94)](http://192.168.57.101:9000/dashboard?id=crypto_surveillance)

[![Quality Gate Status](http://192.168.57.101:9000/api/project_badges/measure?project=crypto_surveillance&metric=alert_status&token=sqb_cb179b7e0a76d5e0a18879f5cf979bf22439dc94)](http://192.168.57.101:9000/dashboard?id=crypto_surveillance)

[![Security Hotspots](http://192.168.57.101:9000/api/project_badges/measure?project=crypto_surveillance&metric=security_hotspots&token=sqb_cb179b7e0a76d5e0a18879f5cf979bf22439dc94)](http://192.168.57.101:9000/dashboard?id=crypto_surveillance)

[![Lines of Code](http://192.168.57.101:9000/api/project_badges/measure?project=crypto_surveillance&metric=ncloc&token=sqb_cb179b7e0a76d5e0a18879f5cf979bf22439dc94)](http://192.168.57.101:9000/dashboard?id=crypto_surveillance)


# Liens Utiles
- **Documentation de CoinCap API: https://docs.coincap.io/#ee30bea9-bb6b-469d-958a-d3e35d442d7a** 
- **Spring Boot Documentation: https://docs.spring.io/spring-boot/documentation.html**
- **Docker Documentation: https://docs.docker.com/**
- **Kubernetes Documentation: https://kubernetes.io/docs/**
