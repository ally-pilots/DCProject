package testbench;

import bench.IBenchmark;
import bench.cpu.CPUDigitsOfPi;
import logging.ConsoleLogger;
import logging.ILogger;
import time.ITimer;
import time.Timer;

public class TestCPUDigitsOfPi {
    private static final int benchLoops = 5;
    static IBenchmark bench = new CPUDigitsOfPi();
    static ILogger logger = new ConsoleLogger();
    static int[] testDigits = {50, 100, 500, 1000, 5000, 10000, 50000, 100000};
    static String[] algorithmNames = {"Gauss", "Bailey"};

    public static void main(String[] args) {
        for (int alg = 0; alg < algorithmNames.length; alg++) {
            for (int nrDigits : testDigits) {
                if (alg == 1 && nrDigits > 10000)
                    continue;

                bench.initialize(alg, nrDigits);
                bench.warmup();
                double sum = 0.0;

                for (int i = 0; i < benchLoops; i++) {
                    logger.write("Computing Pi: algorithm " + algorithmNames[alg] +
                            ", digits: " + nrDigits + ", loop " + (i + 1) + "/" + benchLoops);

                    ITimer timer = new Timer();
                    timer.start();
                    bench.run();
                    long timeNanos = timer.stop();
                    double milli = timeNanos / 1_000_000.0;
                    sum += milli;

                    logger.write(algorithmNames[alg] + ", digits: " + nrDigits + " = " + milli + " ms");
                }

                double avg = sum / benchLoops;
                logger.write("Average duration (" + algorithmNames[alg] + ", digits: " +
                        nrDigits + ", loops: " + benchLoops + ") = " + avg + " ms");
            }
        }

        logger.close();
        bench.clean();
    }
}
