from locust import HttpUser, task, between

class MyUser(HttpUser):
    wait_time = between(1, 5)  # Attente entre les requêtes (1 à 5 secondes)

    @task
    def index(self):
        self.client.get("/")

    @task
    def get_data(self):
        self.client.get("/api/data")  # Modifier l'endpoint pour votre API

    @task
    def post_data(self):
        self.client.post("/api/data", json={"key": "value"})
