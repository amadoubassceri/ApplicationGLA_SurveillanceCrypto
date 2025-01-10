from locust import HttpUser, task, between

class WebsiteUser(HttpUser):
    # URL de base de l'application
    host = "http://localhost:3000"

    # Temps d'attente entre les requêtes simulées
    wait_time = between(1, 5)

    @task
    def load_homepage(self):
        self.client.get("/")

    @task
    def load_about_page(self):
        self.client.get("/about")

    @task
    def load_contact_page(self):
        self.client.get("/contact")

    @task
    def load_assets(self):
        # Simuler le chargement de fichiers statiques
        self.client.get("/static/css/main.css")
        self.client.get("/static/js/main.js")
