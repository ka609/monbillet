# 🎟️ MonBillet — Application Web de Réservation de Billets de Concert

Un projet académique développé dans le cadre du module **Développement JEE / Spring Boot** à l’**Université Virtuelle du Burkina Faso (UV-BF)**.  
Ce repository contient le code source complet d’une application permettant la **gestion et la réservation de billets de concert**.

---

## 📌 Sommaire
- [À propos du projet](#-à-propos-du-projet)
- [Fonctionnalités](#-fonctionnalités)
- [Architecture et technologies](#-architecture-et-technologies)
- [Structure du projet](#-structure-du-projet)
- [Base de données](#-base-de-données)
- [Installation et exécution](#-installation-et-exécution)
- [Captures d’écran](#-captures-décran)
- [Limitations](#-limitations)
- [Améliorations futures](#-améliorations-futures)
- [Auteurs](#-auteurs)

---

## 📝 À propos du projet
**MonBillet** est une application Web conçue pour :
- permettre aux utilisateurs de consulter des concerts,
- réserver des tickets,
- gérer leurs réservations,
- fournir à l’administrateur un tableau de bord complet pour gérer les concerts, utilisateurs et réservations.

L’objectif est de proposer une application respectant les principes :
- MVC,
- POO,
- sécurité via Spring Security,
- persistance via JPA & PostgreSQL.

---

## ✨ Fonctionnalités
### 👤 Utilisateur standard
- Création de compte et authentification
- Consultation des concerts disponibles
- Recherche / filtrage
- Affichage des détails d’un concert
- Réservation de billets
- Historique des réservations

### 🛠️ Administrateur
- Gestion des concerts (CRUD)
- Gestion des utilisateurs
- Gestion des réservations
- Tableau de bord / liste globale
- Accès sécurisé (rôle ADMIN)

---

## 🏗️ Architecture et technologies
### 🔧 Backend
- **Spring Boot 3**
- **Spring MVC**
- **Spring Data JPA**
- **Spring Security**
- **Hibernate**

### 🎨 Frontend
- **Thymeleaf**
- HTML / CSS / Bootstrap

### 🗄️ Base de données
- **PostgreSQL**

### ☕ Langage
- **Java 17**

---

## 📂 Structure du projet
```
src
 ├── main
 │   ├── java/com.monbillet.monbillet
 │   │   ├── controller      # Couches Web (MVC)
 │   │   ├── service         # Logique métier
 │   │   ├── repository      # JPA Repositories
 │   │   ├── entity          # Entités JPA
 │   │   └── config          # Config Spring Security, BD, etc.
 │   └── resources
 │       ├── templates       # Pages Thymeleaf
 │       ├── static          # CSS, JS, images
 │       └── application.properties
```

---

## 🗄️ Base de données
Principales tables :
- **users** : comptes utilisateurs (admin & client)
- **concerts** : informations sur les concerts
- **reservations** : réservations faites par les utilisateurs

---

## ⚙️ Installation et exécution
### 1️⃣ Cloner le projet
```bash
git clone https://github.com/ka609/monbillet.git
cd monbillet
```

### 2️⃣ Configurer la base PostgreSQL
Créer une base :
```sql
CREATE DATABASE monbillet;
```
Configurer `application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/monbillet
spring.datasource.username=postgres
spring.datasource.password=motdepasse
```

### 3️⃣ Lancer l’application
```bash
mvn spring-boot:run
```
Ou générer un `.jar` :
```bash
mvn clean package
java -jar target/monbillet-0.0.1-SNAPSHOT.jar
```

L’application sera disponible sur :  
👉 http://localhost:8080

---

## 🖼️ Captures d’écran
Les captures sont disponibles dans le dossier **/images** du projet.
- Page de connexion
- Page d'inscription
- Liste des concerts
- Détails d’un concert
- Réservations utilisateur
- Dashboard administrateur

*(Les images ne sont pas affichées ici pour garder un README léger et compatible GitHub.)*

---

## ⚠️ Limitations actuelles
- Pas de paiement en ligne
- Pas d’envoi d’e‑mails après une réservation
- Design responsive partiel
- Pas encore d’API REST

---

## 🚀 Améliorations futures
- Intégration paiement (Orange Money, Moov Money, Stripe)
- Application mobile Flutter connectée via API REST
- Dashboard statistiques avancées
- Amélioration UI/UX
- Ajout de la gestion des artistes et salles

---

## 👥 Auteurs
- **Kassongo Moussa** — Développeur
- **Bikeiga Hamza** — Développeur

Projet réalisé dans le cadre du module JEE — Licence 3 Développement Web & Mobile — UV-BF.

---

## 📎 Lien du repository
➡️ https://github.com/ka609/monbillet
