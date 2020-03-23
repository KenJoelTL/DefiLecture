# Installation de l'environnement de développement : 

Requis : 
 * docker
 * docker-compose v1.13+

# Compilation (ou recompilation) de l'application :

 * docker-compose up defilecture_build

# Démarrage du serveur :
 
 * docker-compose up -d defilecture
 * l'application sera accessible à http://172.21.0.3:8080

# Création (ou réinitialisation) de la base de données :
 * mysql -u root -ppassword -h 172.21.0.2 < BD/defilecture.sql

# Arrêt du serveur

 * docker-compose down
