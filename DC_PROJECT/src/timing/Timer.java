package timing;

public class Timer {
    private long startTime = 0;
    private long totalTime = 0;
    private long lastResume = 0;
    private boolean running = false;

    public void start() {
        totalTime = 0;
        lastResume = System.nanoTime();
        running = true;
    }

    public void resume() {
        if(!running) {
            lastResume = System.nanoTime();
            running = true;
        }
    }

    public long pause() {
        if(running) {
            long now = System.nanoTime();
            long elapsed = now - lastResume;
            totalTime += elapsed;
            running = false;
            return elapsed;
        }
        return 0;
    }

    public long stop() {
        if(running) {
            pause();
        }
        return totalTime;
    }
}
