package org.example;

import java.util.ArrayDeque;
import java.util.Queue;

public class Simulator {

    private int processors;
    private int clockCycles;
    private String filePath;
    private Scheduler schedular = Scheduler.getInstance();
    private static Queue<Task>tasksQueue = new ArrayDeque<>();



    public Simulator(int processors, int clockCycles, String filePath) {
        this.processors = processors;
        this.clockCycles = clockCycles;
        this.filePath = filePath;
    }

    public void run()
    {
      Clock.initialize(clockCycles);
      InputHandler.generateClock(clockCycles);
      InputHandler.generateProcessors(processors);
      new Thread(new Scheduler()).start();
      while(!Clock.isSimulationOver())
      {
          InputHandler.generateTasks(filePath);
          while (!tasksQueue.isEmpty())
          {
              Scheduler.getInstance().addTask(tasksQueue.poll());
          }

          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
          Clock.increaseCycle();
      }

    }
    public static void addTaskToQueue(Task t)
    {
        tasksQueue.add(t);
    }


}
