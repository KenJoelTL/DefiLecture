# Installation de l'environnement de développement : 

Requis : 
 * docker
 * docker-compose

# Compilation de l'application :

##### Linux
 * ./gradlew build
##### Windows
 * .\gradlew.bat build

# Démarrage du serveur :

 * docker-compose up -d defilecture
 * l'application sera accessible à http://172.21.0.3:8080

# Création (ou réinitialisation) de la base de données :
 * mysql -u root -ppassword -h 172.21.0.2 < BD/defilecture.sql

# Arrêt du serveur

 * docker-compose down
