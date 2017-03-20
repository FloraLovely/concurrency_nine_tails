package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;

import java.util.Iterator;

public class RoundRobinScheduler implements Scheduler {

    private int lastThreadID;
    private boolean schedulerRun = false;

    @Override
    public int chooseThread(ConcurrentProgram program) throws DeadlockException {
        if (program.getEnabledThreadIds().isEmpty()){
            throw new DeadlockException();
        }

        if (schedulerRun == false){
            schedulerRun = true;
            int threadID = program.getEnabledThreadIds().iterator().next();
            lastThreadID = threadID;
            return  lastThreadID;
        }
        else {
            Iterator<Integer> it = program.getEnabledThreadIds().iterator();
            while (it.hasNext()){
                int current = it.next();
                if (current > lastThreadID){
                    lastThreadID = current;
                    return lastThreadID;
                }
            }
            lastThreadID = program.getEnabledThreadIds().iterator().next();
            return lastThreadID;
        }
    }
}
