package bench.cpu;

import bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {
    private long size;
    private long sum = 0;
    private String resultMessage = "";
    private boolean errorCaught = false;
    private long lastReached;
    private int lastCounter;
    private boolean error=false;
    private double execTimeMillis = 0;
    private long lastPrime=0;


    private long recursive(long start, long size, int counter) {
        try {
            if (start > size)
                return 0;
            if (isPrime(start)) {
                lastPrime = start;
                sum += start;
            }


            return recursive(start + 1, size, counter + 1);

        } catch (StackOverflowError e) {
            lastCounter = counter;
            lastReached = start;
            error=true;
            return 0;
        }
    }

    private long nextPrime(long start, long size) {
        long candidate = start;
        while (candidate <= size) {
            if (isPrime(candidate)) {
                return candidate;
            }
            candidate++;
        }
        return size + 1;
    }

    @Override
    public void run() {
    }

    @Override
    public void run(Object... parameters) {
        boolean unroll = (boolean) parameters[0];
            if (unroll) {
                int unrollLevel = (int) parameters[1];
                recursiveUnrolled(1, unrollLevel, (int) size, 0);
            } else {
                recursive(1, size, 0);
            }
            if(error){
                System.out.println("Reached nr "+lastPrime+ "/"+size+" after "+lastCounter+" calls.");
            }
        }




    @Override
    public void initialize(Object... parameters) {
        this.size = (long) parameters[0];
    }

    @Override
    public void clean() {
        sum = 0;
        errorCaught = false;
        resultMessage = "";
        lastCounter=0;
        lastReached=0;
        error=false;
        lastPrime=1;
    }

    @Override
    public void cancel() {
    }

    @Override
    public void warmup() {
        recursive(1, 1000, 0);
        recursiveUnrolled(1, 5, 1000, 0);
    }

    public void setExecutionTime(double millis) {
        this.execTimeMillis = millis;
    }


    @Override
    //calculate performance
    public String getResult() {
        if (lastCounter == 0) return "No result";

        double score = lastCounter / (execTimeMillis + 1);  // evită împărțirea la 0
        return String.format("Score: %.3f (calls: %d, time: %.3f ms)", score, lastCounter, execTimeMillis);
    }


    private boolean isPrime(long n) {
        if (n < 2)
            return false;
        if (n == 2)
            return true;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    private long recursiveUnrolled(long start, int unrollLevel, int size, int counter) {
        try {
            if (start > size)
                return 0;
            long newStart = start;
            for (int i = 0; i < unrollLevel && newStart <= size; i++, newStart++) {
                if (isPrime(newStart)) {
                    sum += newStart;
                    lastReached = newStart;
                }
            }

            return recursiveUnrolled(newStart, unrollLevel, size, counter + 1);

        } catch (StackOverflowError e) {
            lastCounter = counter;
            lastReached = start;
            error=true;
            return 0;
        }
    }
}