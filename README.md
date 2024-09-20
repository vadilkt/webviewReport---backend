# Backend API pour le Web Report Viewer

## Description

Ce projet fournit une API backend pour un système de génération et de gestion de rapports. Il utilise Spring Boot pour le serveur, JasperReports pour la génération de rapports, et PostgreSQL pour la gestion des données. Le système permet de générer des rapports à partir d'une base de données PostgreSQL et de les afficher dans un composant web personnalisé.

## Table des Matières

- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [API Endpoints](#api-endpoints)
- [Exemples de Requêtes](#exemples-de-requêtes)


## Prérequis

- Java 17 ou supérieur
- Maven 3.8 ou supérieur
- PostgreSQL 14 ou supérieur
- JasperReports 6.20 ou supérieur
- Jaspersoft Studio 7.0 ou supérieur

## Installation

1. Clonez ce dépôt :
    ```bash
    git clone https://github.com/vadilkt/webviewreport---backend.git
    cd your-repository
    ```

2. Assurez-vous que PostgreSQL est installé et en cours d'exécution.

3. Configurez la base de données PostgreSQL. Assurez-vous que les tables suivantes existent :
    - `transmission_sheet`
    - `courier`
    - `attachment`

4. Créez un fichier `application.properties` dans le répertoire `src/main/resources` avec la configuration de votre base de données. Exemple :
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

5. Compilez et exécutez le projet :
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Configuration

Le projet utilise Spring Boot pour gérer les configurations. Les paramètres de connexion à la base de données doivent être spécifiés dans `application.properties`.

## Utilisation

Une fois l'application démarrée, vous pouvez accéder aux endpoints API pour générer les bordereaux de transmission. Vous pouvez utiliser des outils comme Postman ou cURL pour tester les endpoints.

## API Endpoints
`/report?format&SheetId`

## Exemples de Requêtes
`localhost:8080/report?format=pdf&sheetId=2`

