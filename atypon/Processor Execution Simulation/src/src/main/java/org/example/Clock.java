package org.example;

public class Clock {
    public static int currentCycle;
    public static int maxCycles;
    public static void initialize(int max)
    {
        currentCycle = 1;
        maxCycles = max;
    }
    public static void increaseCycle()
    {
        if(!isSimulationOver());
            currentCycle++;
    }
    public static boolean isSimulationOver()
    {
        return maxCycles==currentCycle;
    }



}
