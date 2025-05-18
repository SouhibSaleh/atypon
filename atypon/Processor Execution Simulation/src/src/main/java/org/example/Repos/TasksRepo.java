package org.example.Repos;

import org.example.Task;
import java.util.PriorityQueue;

public class TasksRepo {
    private PriorityQueue<Task> tasks = new PriorityQueue<>((t1, t2)->{
        if(t1.getPriority()!=t2.getPriority())
            return Integer.compare(t2.getPriority(),t1.getPriority());
        if(t1.getCreationTime()!=t2.getCreationTime())
            return Integer.compare(t1.getCreationTime(),t2.getCreationTime());
        if(t1.getExecutionTime()!=t2.getExecutionTime())
            return Integer.compare(t2.getExecutionTime(),t1.getExecutionTime());
        return 0;
    });
    private static class SingletonHelper{
        private static final TasksRepo INSTANCE = new TasksRepo();
    }

    public static TasksRepo getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public void addTask(Task t)
    {
        tasks.add(t);
    }
    public Task serveTask()
    {
        return  tasks.poll();
    }

}
