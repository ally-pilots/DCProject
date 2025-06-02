package time;

public class Timer implements ITimer {
    private long startTime=0;
    private long totalTime=0;
    private long lastResume=0;
    private boolean running=false;

    public void start()
    {
        totalTime=0;
        lastResume=System.nanoTime();
        running=true;
    }

    public long stop()
    {
        if(running)
        {
            totalTime+=System.nanoTime()-lastResume;
            running=false;
        }
        return totalTime;
    }

    public long pause()
    {
        if(running)
        {
            long passedTime=System.nanoTime()-lastResume;
            totalTime=totalTime+passedTime;
            running=false;
            return passedTime;
        }
        return 0;
    }

    public void resume()
    {
        if(!running)
        {
            lastResume=System.nanoTime();
            running=true;
        }
    }
}

