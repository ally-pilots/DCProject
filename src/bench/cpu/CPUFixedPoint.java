package bench.cpu;

import bench.IBenchmark;

import java.util.Arrays;

public class CPUFixedPoint implements IBenchmark {

    private int size;
    private int[] result;
    @Override
    public void run() {
    }

    private void arithmeticTest() {
        int j = 1, k = 2, l = 3;
        int[] num = {0, 1, 2, 3};

        for (int i = 3; i < size; ++i) {
            j = num[1] * (k - j) * (l - k);
            k = num[3] * k - (l - j) * k;
            l = (l - k) * (num[2] + j);
            result[i - 2] = j + k + l;
            result[i - 3] = j * k * l;
        }
    }

    private void branchingTest()
    {
        int[] num = {0, 1, 2, 3};
        int j = 0;

        for (int i = 0; i < size; ++i) {
            if (j == 1) {
                j = num[2];
            } else {
                j = num[3];
            }
            result[i] = j;
        }
    }

    private void simpleTest()
    {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            sum += i;
            result[i] = sum;
        }
    }

    @Override
    public void run(Object... parameters) {
        String test=(String)parameters[0];
        switch (test.toLowerCase()) {
            case "arithmetic":
                arithmeticTest();
                break;
            case "branching":
                branchingTest();
                break;
            case "simple":
                simpleTest();
                break;
            default:
                throw new RuntimeException("Unknown test type.");
        }
    }

    @Override
    public void initialize(Object... parameters) {
        this.size = (int) parameters[0];
        this.result = new int[size];
    }

    @Override
    public void clean() {
        result=null;
    }

    @Override
    public void cancel() {
    }

    @Override
    public void warmup() {
        arithmeticTest();
        branchingTest();
        simpleTest();
    }

    @Override
    public String getResult() {
        int sum = 0;
        for (int i = 0; i < result.length; i++) {
            sum += result[i];
        }
        return "Checksum: " + sum + ", Result length: " + result.length;
    }

}
