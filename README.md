# Système Applicatif de Gestion des Accès

## Aperçu

Le Système Applicatif de Gestion des Accès est une application Spring Boot conçue pour gérer les informations utilisateurs avec une base de données PostgreSQL. Elle propose des APIs RESTful pour les opérations CRUD de base sur les entités utilisateurs et roles. Le système comprend des composants tels que des modèles, des référentiels, des services et des ressources.

## Table des matières

1. [Configuration](#Configuration)
2. [Structure du Projet](#Structure-du-Projet)
3. [Configuration de la Base de Données](#Configuration-de-la-Base-de-Données)
4. [Endpoints](#endpoints)
5. [Dépendances](#Dépendances)

## Configuration

### Prérequis

- Java 11
- Base de données PostgreSQL (configurée dans application.properties)
- Maven

### Exécution de l'Application

1. Construisez et lancez l'application :

   ```bash
   mvn spring-boot:run
   ```

L'application sera accessible à l'adresse  `http://localhost:8070`.

## Structure du Projet

- **models**: Contient les classes `User` et `Role` représentant l'entité utilisateur et role.
- **repository**:  Gère la logique d'accès aux données et interagit avec la base de données.
- **service**: Implémente la logique métier et gère les validations.
- **resource**: Expose des APIs RESTful pour les opérations liées à l'utilisateur.

## Configuration de la Base de Données

L'application utilise PostgreSQL comme base de données. Les détails de configuration se trouvent dans le fichier application.properties :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=******
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
```

Assurez-vous de mettre à jour les détails de connexion à la base de données selon votre environnement.

## Endpoints

- **GET /users**: Récupère tous les utilisateurs.
- **GET /users/{username}**: Récupère un utilisateur par nom d'utilisateur.
- **POST /users**: Crée un nouvel utilisateur.
- **PUT /users/{username}**: Met à jour un utilisateur existant.
- **DELETE /users/{username}**: Supprime un utilisateur.

## Dépendances

- [Spring Boot](https://spring.io/projects/spring-boot): Framework principal pour la construction de l'application.
- [Spring JDBC](https://docs.spring.io/spring-boot/docs/current/reference/html/data-access.html#jdbc): Simplifie l'accès à la base de données en utilisant JDBC.
- [PostgreSQL Driver](https://jdbc.postgresql.org/): Driver JDBC pour PostgreSQL.
- [Spring Test](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-testing): Framework pour tester l'application.
- [Prometheus](https://prometheus.io/): Utilisé pour la surveillance et les métriques.