package testbench;

import bench.hdd.HDDWriteSpeed;
import logging.ConsoleLogger;
import logging.ILogger;
import time.Timer;

public class TestHDDWriteSpeed {
    public static void main(String[] args) {
        ILogger logger = new ConsoleLogger();
        HDDWriteSpeed benchmark = new HDDWriteSpeed();

        Timer timer = new Timer();

        logger.write("==== HDD Write Speed Benchmark ====");

        // test 1: fixed file size
        logger.write("\nFixed File Size Benchmark");
        benchmark.initialize();
        timer.start();
        benchmark.run("fs", true);
        logger.write("Duration: "+ timer.stop() / 1e9 + " seconds\n");
        System.out.println(benchmark.getResult());

        // test 2: fixed buffer size
       /* logger.write("\nFixed Buffer Size Benchmark");
        benchmark.initialize();
        timer.start();
        benchmark.run("fb", true);
        logger.write("Duration: "+ timer.stop() / 1e9 + " seconds\n");
        System.out.println(benchmark.getResult());
        */

        logger.write("HDD Write Speed Benchmark completed.\n");
    }
}