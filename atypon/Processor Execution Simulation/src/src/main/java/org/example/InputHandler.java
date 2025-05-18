package org.example;

import org.example.Repos.ProcessorsRepo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputHandler {
    static public void generateTasks(String filePath)
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            int id = 0;
            while((line = reader.readLine())!=null)
            {
                String []inputs = line.strip().split(" ");
                int creationTime = Integer.valueOf(inputs[0]);
                int executionTime = Integer.valueOf(inputs[1]);
                int priority = Integer.valueOf(inputs[2]);
                String description = inputs[3];
                Task t = new Task(id++,creationTime,executionTime,priority,description);
                if(Clock.currentCycle==creationTime)
                {
                    System.out.println("New Task : "+t);
                    Simulator.addTaskToQueue(t);
                }

            }
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    static public void generateClock(int clockCycles)
    {
        Clock.initialize(clockCycles);
    }
    static public void generateProcessors(int processors)
    {
        int id = 0;
        while(processors--!=0)
        {
            Processor p = new Processor(id++);
            ProcessorsRepo.getInstance().addProcessor(p);
        }
    }

}
