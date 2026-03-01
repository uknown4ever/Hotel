# 📚 Gestion de Parking

---

## 📁 Table de matieres

- [🗂 Contexte](#-Contexte)
- [❓ Problématique](#-Problématique)
- [🎯 Objectif](#-Objectif)
- [📊 Diagrammes](#-Diagrammes)
- [🗃 Tables de Données](#-Tables-de-Données)
- [✨ Fonctionnalités Principales](#-Fonctionnalités-Principales)
- [🔍 Requêtes SQL](#-Requêtes-sql)
- [🏛 Architecture](#-Architecture)
- [🛠 Technologies Utilisées](#-Technologies-Utilisées)
- [🎥 Démo Vidéo](#-Démo-video)
- [auteurs](#auteurs)
---
## 🗂 Contexte :

Dans le domaine de la gestion des parkings, l'organisation efficace des places de stationnement, le suivi des véhicules et la gestion des entrées/sorties sont essentiels pour assurer un service fluide et éviter les conflits d'occupation.
Les méthodes traditionnelles basées sur des registres papier ou des fichiers Excel peuvent entraîner des erreurs, des chevauchements de réservation et une mauvaise optimisation de l'espace.
Afin d'améliorer la gestion quotidienne d'un parking, il est nécessaire de mettre en place une application informatique permettant d'automatiser les opérations principales comme la gestion des places, l'enregistrement des stationnements et le calcul automatique des montants.

---
## ❓ Problématique:

Les gestionnaires de parkings rencontrent souvent plusieurs difficultés dans leur gestion quotidienne :
- Difficulté à suivre en temps réel la disponibilité des places
- Risque de chevauchement des réservations
- Gestion manuelle des entrées/sorties peu fiable
- Calcul complexe des montants de stationnement
- Manque de centralisation des informations des véhicules
- Absence de statistiques pour analyser le taux d'occupation et les revenus
- Ces limites rendent la gestion moins efficace et compliquent la prise de décision pour optimiser l'utilisation du parking.

---
## 🎯 Objectif:
L'objectif de ce projet est de développer une application desktop permettant de faciliter la gestion d'un parking à travers une interface simple et intuitive.
L'application vise à :
- Gérer les places de stationnement (type, statut, tarif)
- Gérer les véhicules et leurs informations
- Enregistrer les entrées et sorties des véhicules
- Calculer automatiquement le montant dû en fonction de la durée
- Générer des statistiques sur le taux d'occupation et les revenus

---
## L'application doit :
- Permettre l'ajout, modification et suppression des places de stationnement
- Gérer les véhicules liés aux stationnements
- Enregistrer les entrées et sorties des véhicules
- Mettre à jour automatiquement le statut des places après chaque stationnement
- Afficher les statistiques (ex : taux d'occupation, revenus par période)

---
## 📊 Diagrammes :

##  Diagramme use case:
<img width="1344" height="887" alt="Diagramme Cas d'utilisation" src="https://github.com/user-attachments/assets/9f979358-d314-4d1f-8d00-e42d949d56df" />

---
##  Diagramme de classe :
<img width="957" height="596" alt="Diagramme de classe" src="https://github.com/user-attachments/assets/950638bf-bc30-4d66-83a2-72ac07a87749" />

---
## 🗃 Tables de Données:

-Place ( id , numero , type , statut , tarif_horaire )

-Vehicule ( id , immatriculation , marque , proprietaire )

-Stationnement( id , place_id , vehicule_id , date_entree , date_sortie , montant )

-Utilisateur( id , login , mot_de_passe , email , role )

---
## ✨ Fonctionnalités Principales:

### 1. Gestion des places
- **Ajouter une place** : Formulaire pour saisir le numéro, type, statut et tarif horaire.
- **Modifier une place** : Mettre à jour les informations d'une place existante.
- **Supprimer une place** : Retirer une place de la base de données.
- **Lister les places** : Afficher toutes les places dans un tableau.

### 2. Gestion des véhicules
- **Ajouter un véhicule** : Saisie des informations d'un véhicule.
- **Modifier un véhicule** : Mettre à jour les données d'un véhicule existant.
- **Supprimer un véhicule** : Retirer un véhicule de la base de données.
- **Lister les véhicules** : Afficher la liste des véhicules enregistrés.

### 3. Gestion des stationnements
- **Enregistrer une entrée** : Sélectionner une place et un véhicule, enregistrer la date/heure d'entrée.
- **Enregistrer une sortie** : Calculer automatiquement le montant dû selon la durée.
- **Supprimer un stationnement** : Retirer un enregistrement de la base de données.
- **Lister les stationnements** : Afficher la liste complète des stationnements.

### 4. Authentification & Sécurité
- **Connexion sécurisée** : Authentification par login et mot de passe.
- **Récupération de mot de passe par email** : Envoi automatique d'un email de réinitialisation.
- **Gestion des utilisateurs** : Ajout et gestion des comptes utilisateurs.

### 5. Filtrage et recherche
- **Filtrer les places par type** : Afficher les places selon leur catégorie.
- **Filtrer les places par statut** : Voir les places libres ou occupées.
- **Filtrer les stationnements par période** : Consulter les stationnements sur une plage de dates.
- **Rechercher par véhicule** : Consulter l'historique de stationnement d'un véhicule.

---
##  🔍 Requêtes sql 

### Tables

```sql

CREATE TABLE utilisateur (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             login VARCHAR(80) NOT NULL UNIQUE,
                             mot_de_passe VARCHAR(255) NOT NULL,
                             email VARCHAR(120) NOT NULL,
                             role VARCHAR(50) NOT NULL DEFAULT 'USER'
);

CREATE TABLE place (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       numero VARCHAR(20) NOT NULL UNIQUE,
                       type VARCHAR(50) NOT NULL,
                       statut VARCHAR(20) NOT NULL DEFAULT 'LIBRE',
                       tarif_horaire DECIMAL(10,2) NOT NULL CHECK (tarif_horaire >= 0)
);

CREATE TABLE vehicule (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          immatriculation VARCHAR(20) NOT NULL UNIQUE,
                          marque VARCHAR(80) NOT NULL,
                          proprietaire VARCHAR(120) NOT NULL
);

CREATE TABLE stationnement (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               place_id INT NOT NULL,
                               vehicule_id INT NOT NULL,
                               date_entree DATETIME NOT NULL,
                               date_sortie DATETIME,
                               montant DECIMAL(10,2),
                               CONSTRAINT fk_stat_place FOREIGN KEY (place_id) REFERENCES place(id)
                                   ON UPDATE CASCADE ON DELETE RESTRICT,
                               CONSTRAINT fk_stat_vehicule FOREIGN KEY (vehicule_id) REFERENCES vehicule(id)
                                   ON UPDATE CASCADE ON DELETE RESTRICT
);
```
## 🏛  Architecture
<img width="1100" height="750" alt="architecture" src="https://github.com/user-attachments/assets/08445ecf-1370-4bcc-b415-00308ca5f962" />

---
## 🛠 Technologies Utilisées:

- **Framework d'interface graphique :** Java Swing
- **Base de données :** MySQL
- **Accès aux données :** JDBC
- **Outils de développement :**
NetBeans (IDE Java)
StarUml (Outil de diagramme)
- **Gestion de base de données :** MYSQL Workbench

---
## 🎥 Démo video




https://github.com/user-attachments/assets/ba294fe6-2c99-41cd-994e-b387c932066d


