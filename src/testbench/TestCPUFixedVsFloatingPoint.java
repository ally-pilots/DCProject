package testbench;

import bench.cpu.CPUFixedVsFloatingPoint;
import bench.cpu.NumberRepresentation;
import logging.ConsoleLogger;
import logging.*;
import logging.TimeUnit;
import time.ITimer;
import time.Timer;
import bench.IBenchmark;

public class TestCPUFixedVsFloatingPoint {

    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger log = /* new FileLogger("bench.log"); */new ConsoleLogger();
        TimeUnit timeUnit = TimeUnit.Milli;

        IBenchmark bench = new CPUFixedVsFloatingPoint();
        bench.initialize(10000000);
        bench.warmup();

        timer.start();
        //bench.run(NumberRepresentation.FIXED);
		bench.run(NumberRepresentation.FLOATING);
        long time = timer.stop();
        log.writeTime("Finished in", time, timeUnit);
        log.write("Result is", bench.getResult());

        bench.clean();
        log.close();
    }
}
