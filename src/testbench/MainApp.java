package testbench;

import bench.*;
import bench.cpu.*;
import bench.hdd.*;
import bench.ram.VirtualMemoryBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import time.ITimer;
import time.Timer;

import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ILogger logger = new ConsoleLogger();
    private static final ITimer timer = new Timer();

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Benchmark Interface! ===");
        logger.write("Select benchmark:");
        logger.write("1. CPU Recursion Loop Unrolling");//done
        logger.write("2. HDD Write Speed"); //done
        logger.write("3. HDD Random Access"); //done
        logger.write("4. Demo Benchmark (Bubble Sort)"); //done
        logger.write("5. CPU Digits of Pi"); //done
        logger.write("6. CPU Fixed Point"); //done
        logger.write("7. CPU Fixed vs Floating Point"); //done
        logger.write("8. Virtual Memory");

        int option = scanner.nextInt();

        switch (option) {
            case 1 : runRecursionLoopUnrolling();
                break;
            case 2 : runHDDWriteSpeed(); //done
                break;
            case 3 : runHDDRandomAccess();
                break;
            case 4 : runDemoBenchmark(); //done
                break;
            case 5 : runDigitsOfPi(); //done
                break;
            case 6 : runFixedPoint(); //done
                break;
            case 7 : runFixedVsFloatingPoint(); //done
                break;
            case 8 : runVirtualMemory();
                break;
            default : logger.write("Invalid option.");
                break;
        }

        System.out.println("Over and out! :p");
    }

    private static void runRecursionLoopUnrolling() {
        IBenchmark cpuBench = new CPURecursionLoopUnrolling();
        System.out.print("Size: ");
        long size = scanner.nextLong();
        System.out.print("Unroll? (true/false): ");
        boolean unroll = scanner.nextBoolean();
        cpuBench.initialize(size);
        cpuBench.warmup();
        timer.start();
        if (unroll) {
            System.out.print("Unroll level: ");
            int level = scanner.nextInt();
            cpuBench.run(true, level);
        } else {
            cpuBench.run(false);
        }
        double time=timer.stop()/ 1e6;
        logger.write("Time: " + time + " ms");
        ((CPURecursionLoopUnrolling) cpuBench).setExecutionTime(time);
        logger.write("Result: " + cpuBench.getResult());
        cpuBench.clean();
    }

    private static void runHDDWriteSpeed() {
        HDDWriteSpeed benchmark = new HDDWriteSpeed();
        logger.write("==== HDD Write Speed Benchmark ====");
        benchmark.warmup();

        System.out.print("Choose option (fs = fixed size / fb = fixed buffer): ");
        String option = scanner.next();  // read user input
        System.out.print("Clean after run? (true/false): ");
        boolean clean = scanner.nextBoolean();

        benchmark.initialize();
        timer.start();
        benchmark.run(option, clean);
        logger.write("Duration: " + timer.stop() / 1e9 + " seconds");
        System.out.println(benchmark.getResult());

        logger.write("HDD Write Speed Benchmark completed.\n");
    }



    private static void runHDDRandomAccess() {
        IBenchmark benchmark = new HDDRandomAccess();

        System.out.println("=== HDD RANDOM ACCESS BENCHMARK ===");

        System.out.println("Initializing file with random data...");
        long fileSize = 512L * 1024 * 1024;
        benchmark.initialize(fileSize);
        benchmark.warmup();
        System.out.print("Buffer size in bytes (e.g., 4096): ");
        long bufferSize = scanner.nextLong();

        System.out.print("Choose mode (r = read / w = write): ");
        String mode = scanner.next();

        System.out.print("Choose type (fs = fixed size / ft = fixed time): ");
        String type = scanner.next();

        String label = (mode.equals("r") ? "READ" : "WRITE") + " - " + (type.equals("fs") ? "Fixed Size" : "Fixed Time");

        System.out.println("\n" + label);
        Object[] options = new Object[]{mode, type, bufferSize};
        timer.start();
        benchmark.run(options);
        long timeMS = timer.stop() / 1_000_000;

        System.out.println("Time: " + timeMS + " ms");
        System.out.println("Result: " + benchmark.getResult());

        benchmark.clean();
    }


    private static void runDemoBenchmark() {
        IBenchmark bench=new DemoBenchmark();
        System.out.print("Enter the size: ");
        final int size = scanner.nextInt();
        bench.initialize(size);
        bench.warmup();
        timer.start();
        for(int i=0;i<12;i++)
        {
            if (i == 3)
                bench.cancel(); // simulare oprire benchmark
            if(i==4)
                bench.initialize(size);
            timer.resume();
            bench.run();
            long time =timer.pause();
            logger.writeTime("Run "+ i ,time,TimeUnit.Sec);
        }
        logger.writeTime("Finished in", timer.stop(),TimeUnit.Sec);

    }

    private static void runDigitsOfPi() {
        final int benchLoops = 5;
         IBenchmark bench = new CPUDigitsOfPi();
         ILogger logger = new ConsoleLogger();
         int[] testDigits = {50, 100, 500, 1000, 5000, 10000, 50000, 100000};
         String[] algorithmNames = {"Gauss", "Bailey"};
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


    private static void runFixedPoint() {
        IBenchmark bench = new CPUFixedPoint();
        TimeUnit timeUnit = TimeUnit.Nano;
        System.out.print("Enter the size: ");
        final int size = scanner.nextInt();
        final int ARITHMETIC_OPS = 29;
        final int SIMPLE_OPS = 9;
        final int BRANCHING_OPS = 7;

        bench.initialize(size);
        bench.warmup();

        timer.start();
        bench.run("arithmetic");
        long time = timer.stop();
        double mops = (ARITHMETIC_OPS * (double) size) / (time/ 1_000_000_000.0)/ 1_000_000.0;
        logger.write("For arithmetic operation:");
        logger.writeTime("Finished in", time, timeUnit);
        //logger.write("Final result: " + bench.getResult());
        logger.write(String.format("MOPS: %.6f", mops));

        timer.start();
        bench.run("simple");
        time = timer.stop();
        mops = (SIMPLE_OPS * (double) size) / (time/ 1_000_000_000.0)/ 1_000_000.0;
        logger.write("For simple operation:");
        logger.writeTime("Finished in", time, timeUnit);
        //logger.write("Final result: " + bench.getResult());
        logger.write(String.format("MOPS: %.6f", mops));

        timer.start();
        bench.run("branching");
        time = timer.stop();
        mops = (BRANCHING_OPS * (double) size) / (time/ 1_000_000_000.0)/ 1_000_000.0;
        logger.write("For branching operation:");
        logger.writeTime("Finished in", time, timeUnit);
        //logger.write("Final result: " + bench.getResult());
        logger.write(String.format("MOPS: %.6f", mops));

        bench.clean();
        logger.close();
    }

    private static void runFixedVsFloatingPoint() {
            TimeUnit timeUnit = TimeUnit.Milli;

            System.out.print("Enter the size: ");
            final int size = scanner.nextInt();

            System.out.print("Choose representation (fixed/floating): ");
            String representation = scanner.next().toLowerCase();

            NumberRepresentation mode;
            if (representation.equals("fixed")) {
                mode = NumberRepresentation.FIXED;
            } else if (representation.equals("floating")) {
                mode = NumberRepresentation.FLOATING;
            } else {
                System.out.println("Invalid option. Please enter 'fixed' or 'floating'.");
                return;
            }

            IBenchmark bench = new CPUFixedVsFloatingPoint();
            bench.initialize(size);
            bench.warmup();

            timer.start();
            bench.run(mode);
            long time = timer.stop();

            logger.writeTime("Finished in", time, timeUnit);
            logger.write("Result is", bench.getResult());

            bench.clean();
            logger.close();
        }


    private static void runVirtualMemory() {
        IBenchmark vm = new VirtualMemoryBenchmark();
        vm.initialize();
        vm.warmup();
        System.out.print("File size in MB: ");
        long fileSize = scanner.nextLong() * 1024 * 1024;
        System.out.print("Buffer size in KB: ");
        int bufferSize = scanner.nextInt() * 1024;
        Object[] options = new Object[]{fileSize, bufferSize};
        timer.start();
        vm.run(fileSize,bufferSize);
        logger.write("Time: " + timer.stop() / 1e6 + " ms");
        logger.write("Result: " + vm.getResult());
        vm.clean();
    }
}
