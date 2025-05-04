package testbench;

import benchmark.DemoBenchmark;
import benchmark.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.Timer;

public class TestCancelBench {
    public static void main(String[] args) throws InterruptedException {
        IBenchmark bench = new DemoBenchmark();
        ILogger log = new ConsoleLogger();
        Timer timer = new Timer();

        int arraySize = 10000;
        bench.initialize(arraySize);

        Thread benchmarkThread = new Thread(() -> {
            timer.start();
            bench.run();
            long time = timer.stop();
            log.writeTime("Execution time before cancel", time, TimeUnit.Milli);
        });

        benchmarkThread.start();

        Thread.sleep(100);
        bench.cancel();

        benchmarkThread.join();
        log.write("Bnechmark was cancelled.");
    }
}
