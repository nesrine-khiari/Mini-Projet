package java_rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// Interface définissant les méthodes exposées par le service RMI TaskService
public interface TaskListService extends Remote {

    // Ajoute une nouvelle tâche à la liste
    void addTask(String task) throws RemoteException;

    // Supprime une tâche existante de la liste
    void removeTask(int taskId) throws RemoteException;

    // Récupère la liste complète des tâches
    List<String> getAllTasks() throws RemoteException;
}
