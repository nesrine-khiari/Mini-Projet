package java_rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TaskListServiceImpl extends UnicastRemoteObject implements TaskListService {
    private List<String> tasks;

    public TaskListServiceImpl() throws RemoteException {
        super();
        tasks = new ArrayList<>();
    }

    @Override
    public synchronized void addTask(String task) throws RemoteException {
        tasks.add(task);
        System.out.println("Tâche ajoutée : " + task);
    }

    @Override
    public synchronized void removeTask(int taskId) throws RemoteException {
        if (taskId >= 0 && taskId < tasks.size()) {
            String removedTask = tasks.remove(taskId);
            System.out.println("Tâche supprimée : " + removedTask);
        } else {
            throw new RemoteException("ID de tâche invalide");
        }
    }

    @Override
    public synchronized List<String> getAllTasks() throws RemoteException {
        List<String> allTasks = new ArrayList<>(tasks);
        System.out.println("Liste des tâches récupérée : " + allTasks);
        return allTasks;
    }
}
