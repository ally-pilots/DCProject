package testbench;
import bench.*;
import bench.cpu.CPUFixedPoint;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import time.ITimer;
import time.Timer;

public class TestCPUFixedPoint {
    private static IBenchmark bench=new CPUFixedPoint();
    private static ILogger logger = new ConsoleLogger();
    private static ITimer timer = new Timer();
    private static TimeUnit timeUnit = TimeUnit.Nano;
    private static final int ARITHMETIC_OPS = 29;
    private static final int SIMPLE_OPS = 9;
    private static final int BRANCHING_OPS = 7;

    public static void main(String[] args) {
        int size=10000000;
        bench.initialize(size);
        bench.warmup();

        timer.start();
        bench.run("arithmetic");
        long time = timer.stop();
        double mops = (ARITHMETIC_OPS * (double) size) / (time  / 1_000_000_000.0);
        logger.write("For arithmetic operation:");
        logger.writeTime("Finished in", time, timeUnit);
        logger.write("Final result: "+ bench.getResult());
        logger.write(String.format("MOPS: %.6f", mops));

        timer.start();
        bench.run("simple");
        time = timer.stop();
        mops = (SIMPLE_OPS * (double) size) / (time  / 1_000_000_000.0);
        logger.write("For simple operation:");
        logger.writeTime("Finished in", time, timeUnit);
        logger.write("Final result: "+ bench.getResult());
        logger.write(String.format("MOPS: %.6f", mops));


        timer.start();
        bench.run("branching");
        time = timer.stop();
        mops = (BRANCHING_OPS * (double) size) / (time  / 1_000_000_000.0);
        logger.write("For branching operation:");
        logger.writeTime("Finished in", time, timeUnit);
        logger.write("Final result: "+ bench.getResult());
        logger.write(String.format("MOPS: %.6f", mops));


        bench.clean();
        logger.close();
    }
}
