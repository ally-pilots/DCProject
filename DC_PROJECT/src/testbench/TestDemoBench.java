package testbench;

import benchmark.DemoBenchmark;
import benchmark.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.Timer;

public class TestDemoBench {
    public static void main(String[] args) {
        Timer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new DemoBenchmark();

        int arraySize = 2000;
        bench.initialize(arraySize);

        timer.start();

        for(int i = 0; i < 5; i++) {
            timer.resume();
            bench.run();
            long stepTime = timer.pause();
            log.writeTime("Iteration " + (i+1) + " time", stepTime, TimeUnit.Milli);
        }

        long totalTime = timer.stop();
        log.writeTime("Total benchmark time", totalTime, TimeUnit.Milli);
    }
}
