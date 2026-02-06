### Microservice de gestion des garages, véhicules et accessoires (Renault)

Ce projet est un microservice Spring Boot permettant de gérer les garages affiliés au réseau Renault, leurs véhicules, ainsi que les accessoires. Il expose une API REST documentée avec Swagger/OpenAPI et publie un évènement Kafka lors de la création d’un véhicule.

#### Sommaire
- Prérequis
- Démarrage rapide
- Documentation API (Swagger UI)
- Console H2 (base en mémoire)
- Principales fonctionnalités de l’API (résumé humain)
- Règles métier implémentées
- Évènements Kafka (publisher/consumer)
- Tests
- Arborescence (haut niveau)

#### Prérequis
- Java 17+
- Maven 3.8+
- (Optionnel) Docker si vous souhaitez lancer un Kafka local rapidement (ou utilisez un Kafka déjà disponible sur localhost:9092)

#### Démarrage rapide
1) Construire et lancer l’application:
```
mvn clean spring-boot:run
```

2) Ouvrir la documentation Swagger UI:
- http://localhost:8080/swagger-ui.html

3) Accéder à l’API en direct via Swagger, ou utiliser un client HTTP (curl/Postman).

4) Console H2 (base en mémoire):
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:gestionauto
- Utilisateur: sa | Mot de passe: (laisser vide)

Notes:
- Le schéma se crée automatiquement (spring.jpa.hibernate.ddl-auto=update) dans la base H2 en mémoire.
- Par défaut, Kafka est attendu sur localhost:9092. Pour les tests simples vous pouvez démarrer un Kafka local ou ignorer Kafka (les endpoints REST fonctionnent sans Kafka démarré, mais la publication d’évènements échouera alors).

#### Documentation API (Swagger UI)
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Chaque endpoint est documenté avec un résumé et une description simple et humaine. Vous pouvez tester toutes les opérations (CRUD, recherches) directement depuis Swagger UI.

#### Principales fonctionnalités de l’API

1) Garages (/api/v1/garages)
- Créer un garage: POST /api/v1/garages
  • Champs requis: name, address, city, telephone, email
- Obtenir un garage par ID: GET /api/v1/garages/{id}
- Mettre à jour un garage: PUT /api/v1/garages/{id}
- Supprimer un garage (cascade: véhicules + accessoires): DELETE /api/v1/garages/{id}
- Lister avec pagination et tri: GET /api/v1/garages?page=0&size=20&sort=name,asc
- Rechercher garages par type de véhicule: GET /api/v1/garages/search/by-vehicle-type?type=SUV
- Rechercher garages par accessoire disponible (nom partiel, insensible à la casse): GET /api/v1/garages/search/by-accessory?name=Camera

2) Véhicules (/api/v1/garages/{garageId}/vehicles, /api/v1/vehicles)
- Ajouter un véhicule à un garage: POST /api/v1/garages/{garageId}/vehicles
  • Champs requis: brand, model, year, fuelType (ESSENCE/DIESEL/HYBRIDE/ELECTRIQUE/GPL), vehicleType (BERLINE/SUV/UTILITAIRE/CITADINE/MONOSPACE/COUPE/CABRIOLET)
  • Publie un évènement Kafka “vehicle-created”
- Lister les véhicules d’un garage (pagination): GET /api/v1/garages/{garageId}/vehicles
- Obtenir un véhicule: GET /api/v1/vehicles/{id}
- Mettre à jour un véhicule: PUT /api/v1/vehicles/{id}
- Supprimer un véhicule (cascade: accessoires): DELETE /api/v1/vehicles/{id}
- Lister tous les véhicules d’un modèle (multi-garages): GET /api/v1/vehicles/search/by-model?model=Clio

3) Accessoires (/api/v1/vehicles/{vehicleId}/accessories, /api/v1/accessories)
- Ajouter un accessoire à un véhicule: POST /api/v1/vehicles/{vehicleId}/accessories
  • Champs requis: name, description, price (>0), type (SECURITE/CONFORT/MULTIMEDIA/PERFORMANCE/AUTRE)
- Lister les accessoires d’un véhicule: GET /api/v1/vehicles/{vehicleId}/accessories
- Mettre à jour un accessoire: PUT /api/v1/accessories/{id}
- Supprimer un accessoire: DELETE /api/v1/accessories/{id}

#### Règles métier implémentées
- Quota de stockage: un garage peut contenir au maximum 50 véhicules. Tentative de dépassement → HTTP 409 CONFLICT.
- Suppressions en cascade: supprimer un garage supprime ses véhicules et leurs accessoires; supprimer un véhicule supprime ses accessoires.

#### Évènements Kafka
- Topic: vehicle-created
- Clé: vehicleId
- Payload (JSON):
```
{
  "id": 123,
  "garageId": 1,
  "brand": "Renault",
  "model": "Clio",
  "year": 2024,
  "fuelType": "ESSENCE",
  "vehicleType": "CITADINE",
  "timestamp": 1700000000000
}
```
- Publisher: envoi à chaque création de véhicule
- Consumer: journalise l’évènement consommé (log)

Exemple de lancement rapide d’un Kafka local avec Docker (optionnel):
```
docker run -p 9092:9092 -p 29092:29092 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092,PLAINTEXT_INTERNAL://0.0.0.0:29092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://localhost:29092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  --name kafka -d bitnami/kafka:latest
```

#### Tests
- Lancement des tests: `mvn test`
- Un test unitaire couvre la règle des 50 véhicules (quand présent). Vous pouvez en ajouter d’autres pour élargir la couverture.

#### Arborescence (haut niveau)
- `com.renault.gestionAuto.domain.*` (entities, enums)
- `com.renault.gestionAuto.repository.*` (Spring Data JPA)
- `com.renault.gestionAuto.service.*` (règles métier, Kafka publisher)
- `com.renault.gestionAuto.messaging.*` (publisher/consumer Kafka)
- `com.renault.gestionAuto.api.*` (contrôleurs REST documentés)
