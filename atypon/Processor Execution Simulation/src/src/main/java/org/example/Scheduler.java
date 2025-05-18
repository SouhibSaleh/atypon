package org.example;

import org.example.Repos.ProcessorsRepo;
import org.example.Repos.TasksRepo;


public class Scheduler implements Runnable{

    TasksRepo tasksRepo = TasksRepo.getInstance();
    ProcessorsRepo processorsRepo = ProcessorsRepo.getInstance();

    @Override
    public void run() {
        while (!Clock.isSimulationOver())
        {
            Processor x = processorsRepo.findFree();
            if (x == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }

            Task task = tasksRepo.serveTask();
            if (task == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue;
            }
            System.out.println("Task : "+task.getId()+" assigend to Processor : "+x.getId());
            x.setTask(task);
            new Thread(x).start();
        }
    }

    private static class SingletonHelper{
        private static final Scheduler INSTANCE = new Scheduler();
    }

    public static Scheduler getInstance(){
        return SingletonHelper.INSTANCE;
    }
    void addTask(Task t)
    {
        tasksRepo.addTask(t);
    }
    Task serveTask()
    {
      return tasksRepo.serveTask();
    }

}
