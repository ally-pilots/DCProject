package testbench;
import logging.*;
import time.*;
import bench.*;

import java.util.Timer;

public class test {
    public static void main(String[] args) {
    /*ITimer timer=new time.Timer();
    ILogger log=new ConsoleLogger();
    IBenchmark bench=new Benchmark();

    bench.initialize((1000));
    timer.start();
    bench.run();
    long time=timer.stop();
    //log.write("Finished in: ");
    log.writeTime("Finished in",time,TimeUnit.Milli);*/

        ITimer timer=new time.Timer();
        ILogger log=new ConsoleLogger();
        IBenchmark bench=new Benchmark();
        final int workload = 5000;
        bench.initialize(workload);
        timer.start();
        for(int i=0;i<12;i++)
        {
            timer.resume();
            bench.run();
            long time =timer.pause();
            log.writeTime("Run "+ i ,time,TimeUnit.Sec);
        }
        log.writeTime("Finished in", timer.stop(),TimeUnit.Sec);

    }
}
