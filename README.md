# Application de Gestion de Tâches avec Java RMI, gRPC et les sockets

Ce projet vise à explorer et à comparer trois technologies de communication distribuée en Java : Java RMI, gRPC et les sockets. Nous implémentons des services spécifiques dans chaque technologie pour comprendre leurs fonctionnalités, performances et flexibilité.

Objectifs du Projet


Implémenter des serveurs et des clients utilisant Java RMI, gRPC et les sockets.
Comprendre les concepts fondamentaux de chaque technologie et leur application dans la communication distribuée.
Comparer les différentes approches en termes de facilité de mise en œuvre, de performances et de flexibilité.
Explorer les avantages et les limitations de chaque technologie dans des scénarios d'utilisation réelle.

Le code source des serveurs et des clients pour Java RMI, gRPC et les sockets est disponible dans ce dépôt GitHub. Chaque dossier est organisé par technologie, et contient les fichiers nécessaires pour déployer et tester l'application:

java_rmi

sockets

grpc


Comment Déployer et Tester l'Application?

Java RMI:

- Cloner le dépôt Java RMI.
- Ouvrir le projet dans votre IDE Java.
- Ouvrir le dossier server
- Compiler et exécuter le serveur RMI (TaskListServer.java).
- Ouvrir le dossier client
- Compiler et exécuter le client RMI (TaskListClient.java).
  
Test:

Le client offre un menu pour ajouter, supprimer et afficher des tâches.

gRPC

- Cloner le dépôt gRPC.
- Ouvrir le projet dans votre IDE Java.
- Ouvrir le dossier grpcMessaging
- Compiler et exécuter le serveur gRPC (MessagingServer.java) sous le chemin relatif(grpc\grpcMessaging\src\main\java\com\messaging\MessagingServer.java)
- Ouvrir le dossier grpcClient
- Compiler et exécuter le client gRPC (MessagingClient.java) sous le cheminr relatif (grpc\grpcClient\src\main\java\grpcClient\MessagingClient.java)

Test:
Le client permet d'envoyer des messages au serveur.

Sockets

- Cloner le dépôt Sockets.
- Ouvrir le projet dans votre IDE Java.
- Ouvrir le dossier server
- Compiler et exécuter le serveur Socket (ChatServer.java).
- Ouvrir le dossier client
- Compiler et exécuter le client Socket (ChatClient.java).
Suivre les instructions dans l'interface utilisateur du client pour interagir avec le service.
