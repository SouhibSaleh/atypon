package org.example;

public class Processor implements Runnable{
    private int id;
    private Task currentTask;
    public Processor(int id)
    {
        this.id = id;
        this.currentTask = null;
    }

    public int getId() {
        return id;
    }

    public void setTask(Task t)
    {
        currentTask = t;
    }
    public Task getCurrentTask()
    {
        return currentTask;
    }
    public boolean isFree()
    {
        return currentTask == null;
    }
    @Override
    public void run() {
        System.out.println("Cycle : "+Clock.currentCycle+" Processor {"+this.id+"} Serving : "+currentTask.getTaskDescription());
        try {
            Thread.sleep(1000*currentTask.getExecutionTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Cycle : "+Clock.currentCycle+" Processor {"+this.id+"} Served : "+currentTask.getTaskDescription());
        currentTask = null;
    }
}
