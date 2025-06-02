package testbench;

import bench.ram.VirtualMemoryBenchmark;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;

public class TestVirtualMemory {

    public static void main(String[] args) {
        IBenchmark benchmark = new VirtualMemoryBenchmark();
        ILogger logger = new ConsoleLogger();

        long fileSize = 512L * 1024 * 1024; // 512 MB
        int bufferSize = 4 * 1024; // 4KB

        benchmark.run(fileSize, bufferSize);
        logger.write(benchmark.getResult());
//        System.out.println(benchmark.getResult());

        benchmark.clean();
        logger.close();
    }
}