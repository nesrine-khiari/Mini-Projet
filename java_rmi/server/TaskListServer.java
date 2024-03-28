package java_rmi.server;

import java.rmi.Naming;

public class TaskListServer {
    public static void main(String[] args) {
        try {
            // Créer une instance du service TaskServiceImpl
            TaskListService taskService = new TaskListServiceImpl();
            
            // Créer un registre RMI sur le port 1099
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            
            // Lier l'instance du service au registre RMI sous le nom "TaskService"
            Naming.rebind("TaskListService", taskService);
            
            // Afficher un message pour indiquer que le service est démarré avec succès
            System.out.println("Service de gestion des tâches démarré...");
        } catch (Exception e) {
            // En cas d'erreur, afficher le message d'erreur
            System.err.println("Erreur: " + e);
        }
    }
}
