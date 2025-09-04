import requests
import json

BASE_URL = "https://demoqa.com"

# Usuário e Token fornecido
USERNAME = "User02"
PASSWORD = "Python#01"
USER_ID = "ac231429-c8a4-4ed4-9b29-db687901f0ef"

TOKEN = (
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
    "eyJ1c2VyTmFtZSI6IlVzZXIwMiIsInBhc3N3b3JkIjoiUHl0aG9uIzAxIiwiaWF0IjoxNzU2OTg2MDY0fQ."
    "aOlXNAh5nqLRVpHgTQiPrzP5a_Av07ljGDAEjH4TWvM"
)

headers = {
    "Authorization": f"Bearer {TOKEN}",
    "Content-Type": "application/json"
}


def print_response(step, response):
    print(f"\n=== [{step}] ===")
    print("Status:", response.status_code)
    try:
        print(json.dumps(response.json(), indent=2))
    except Exception:
        print("Resposta bruta:", response.text)


# 1. Validação de Autorização
auth_payload = {"userName": USERNAME, "password": PASSWORD}
response = requests.post(
    f"{BASE_URL}/Account/v1/Authorized", json=auth_payload)
print_response("1 - Validação de Autorização", response)

# 2. Lista de Livros disponíveis
response = requests.get(f"{BASE_URL}/BookStore/v1/Books", headers=headers)
print_response("2 - Lista de Livros Disponíveis", response)

# 3. Solicitação de Empréstimo
selected_books = [
    {"isbn": "9781449365035"},
    {"isbn": "9781491904244"}
]
borrow_payload = {"userId": USER_ID, "collectionOfIsbns": selected_books}
response = requests.post(
    f"{BASE_URL}/BookStore/v1/Books", headers=headers, json=borrow_payload)
print_response("3 - Solicitação de Empréstimo", response)

# 4. Validação dos livros emprestados
response = requests.get(
    f"{BASE_URL}/Account/v1/User/{USER_ID}", headers=headers)
print_response("4 - Validação dos Livros Emprestados", response)
