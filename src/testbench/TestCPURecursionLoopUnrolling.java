package testbench;

import bench.IBenchmark;
import bench.cpu.CPURecursionLoopUnrolling;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import time.*;

public class TestCPURecursionLoopUnrolling {


    public static void main(String[] args) {
        IBenchmark bench=new CPURecursionLoopUnrolling();
        ILogger logger=new ConsoleLogger();
        ITimer timer=new Timer();
        TimeUnit timeUnit=TimeUnit.Milli;
        long time;
        double timeInMillis;
        long load=1000000;

        bench.initialize(load);
        bench.warmup();

        //no unrolling
        bench.initialize(load);
        timer.start();

       bench.run(false);
        time = timer.stop();
        logger.write("No unrolling:");
        timeInMillis = timeUnit.convert(time);
        logger.write(String.format("Finished in %.4f Milli", timeInMillis));
        bench.run(false);
        time = timer.stop();
        timeInMillis = timeUnit.convert(time);
        ((CPURecursionLoopUnrolling) bench).setExecutionTime(timeInMillis);

        logger.write("Result: "+bench.getResult());
/*
        // Unrolling level 1
        bench.clean();
        bench.initialize(load);
        timer.start();
        bench.run(true, 1);
        time = timer.stop();
        logger.write("Unrolling level 1:");
        timeInMillis = timeUnit.convert(time);
        logger.write(String.format("Finished in %.4f Milli", timeInMillis));
        //logger.write("Result: "+bench.getResult());

/*
        // Unrolling level 5
       bench.clean();
        bench.initialize(load);
        timer.start();
        bench.run(true, 5);
        time = timer.stop();
        logger.write("Unrolling level 5:");
        timeInMillis = timeUnit.convert(time);
        logger.write(String.format("Finished in %.4f Milli", timeInMillis));
        //logger.write("Result: "+bench.getResult());
        bench.clean();

/*
        // Unrolling level 15
        bench.clean();
        bench.initialize(load);
        timer.start();
        bench.run(true, 15);
        time = timer.stop();
        logger.write("Unrolling level 15:");
        timeInMillis = timeUnit.convert(time);
        logger.write(String.format("Finished in %.4f Milli", timeInMillis));
        //logger.write("Result: "+bench.getResult());
*/

        logger.close();
    }
}
