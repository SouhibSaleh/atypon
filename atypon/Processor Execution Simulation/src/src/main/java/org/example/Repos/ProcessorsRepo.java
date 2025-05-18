package org.example.Repos;
import org.example.Processor;
import java.util.ArrayList;
import java.util.List;

public class ProcessorsRepo {
    private List<Processor>processors = new ArrayList<>();
    private static class SingletonHelper{
        private static final ProcessorsRepo INSTANCE = new ProcessorsRepo();
    }
    public static ProcessorsRepo getInstance()
    {
        return SingletonHelper.INSTANCE;
    }
    public void addProcessor(Processor s)
    {
        processors.add(s);
    }
    public Processor findFree()
    {
        for(var x:processors)
        {
            if(x.isFree())
                return x;
        }
        return null;
    }

}
