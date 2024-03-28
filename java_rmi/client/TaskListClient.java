package java_rmi.client;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import java_rmi.server.*;

public class TaskListClient {
    public static void main(String[] args) {
        try {
            // Obtenir une référence au registre RMI
            Registry registry = LocateRegistry.getRegistry("localhost");

            // Recherche du service TaskService dans le registre
            TaskListService taskListService = (TaskListService) registry.lookup("TaskListService");

            // Scanner pour la saisie utilisateur
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    // Affichage du menu
                    System.out.println("== Gestionnaire de Tâches ==");
                    System.out.println("1. Ajouter une nouvelle tâche");
                    System.out.println("2. Supprimer une tâche existante");
                    System.out.println("3. Afficher la liste des tâches");
                    System.out.println("4. Quitter");
                    System.out.print("Choisissez une option : ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consommer la nouvelle ligne

                    // Switch pour gérer les différentes options
                    switch (choice) {
                        case 1:
                            System.out.print("Entrez la description de la nouvelle tâche : ");
                            String newTask = scanner.nextLine();
                            taskListService.addTask(newTask);
                            System.out.println("La tâche a été ajoutée avec succès !");
                            break;
                        case 2:
                            System.out.print("Entrez l'ID de la tâche à supprimer : ");
                            int taskId = scanner.nextInt();
                            scanner.nextLine(); // consommer la nouvelle ligne
                            taskListService.removeTask(taskId);
                            System.out.println("La tâche a été supprimée avec succès !");
                            break;
                        case 3:
                            // Récupérer la liste des tâches depuis le serveur
                            List<String> tasks = taskListService.getAllTasks();
                            System.out.println("== Liste des Tâches ==");
                            if (tasks.isEmpty()) {
                                System.out.println("Aucune tâche enregistrée pour le moment.");
                            } else {
                                for (int i = 0; i < tasks.size(); i++) {
                                    System.out.println((i) + ". " + tasks.get(i));
                                }
                            }
                            break;
                        case 4:
                            System.out.println("Merci d'avoir utilisé notre gestionnaire de tâches !");
                            System.exit(0);
                        default:
                            System.out.println("Option invalide. Veuillez saisir un numéro valide.");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur: " + e);
        }
    }
}
