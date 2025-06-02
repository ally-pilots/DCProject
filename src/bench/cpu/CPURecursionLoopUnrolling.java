package bench.cpu;

import bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {
    private long size;
    private long sum = 0;
    private String resultMessage = "";
    private boolean errorCaught = false;

    private long recursive(long start, long size, int counter) {
        try {
            if (start > size)
                return 0;
            if (isPrime(start))
                sum += start;

            return recursive(start + 1, size, counter + 1);

        } catch (StackOverflowError e) {
            if (!errorCaught) {
                resultMessage = "Reached nr " + start + "/" + size + " after " + counter + " calls.";
                errorCaught = true;
            }
            return 0;
        }
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
    }

    @Override
    public void cancel() {
    }

    @Override
    public void warmup() {
        recursive(1, 1000, 0);
        recursiveUnrolled(1, 5, 1000, 0);
    }

    @Override
    public String getResult() {
        return resultMessage;
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
            for (int i = 0; i < unrollLevel && start <= size; i++, start++) {
                if (isPrime(start))
                    sum += start;
            }
            return recursiveUnrolled(start+unrollLevel, unrollLevel, size, counter + 1);

        } catch (StackOverflowError e) {
            if (!errorCaught) {
                resultMessage = "Reached nr " + start + "/" + size + " after " + counter + " calls.";
                errorCaught = true;
            }
            return 0;
        }
    }
}
