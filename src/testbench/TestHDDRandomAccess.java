package testbench;

import bench.hdd.HDDRandomAccess;
import  bench.IBenchmark;
import time.Timer;

public class TestHDDRandomAccess {
    public static void main(String[] args) {
        IBenchmark benchmark = new HDDRandomAccess();
        Timer timer = new Timer();

        long fileSize = 512L * 1024 * 1024;
        long bufferSize = 4 * 1024;

        System.out.println("=== HDD RANDOM ACCESS BENCHMARK ===");

        System.out.println("Initializing file with random data");
        benchmark.initialize(fileSize);

        testCase("READ - Fixed Size", benchmark, timer, "r", "fs",
                bufferSize);
        testCase("READ - Fixed Time", benchmark, timer, "r", "ft",
                bufferSize);
        testCase("WRITE - Fixed Size", benchmark, timer, "w", "fs",
                bufferSize);
        testCase("WRITE - Fixed Time", benchmark, timer, "w", "ft",
                bufferSize);

        benchmark.clean();
    }

    public static void testCase(String label, IBenchmark bench, Timer timer,
                                String mode, String type, long bufferSize){
        System.out.println("\n" + label);
        Object[] options = new Object[]{mode, type, bufferSize};
        timer.start();
        bench.run(options);
        long timeMS = timer.stop() / 1000000;
        System.out.println("Time: " + timeMS + "ms");
        System.out.println("Result: " + bench.getResult());
    }
}