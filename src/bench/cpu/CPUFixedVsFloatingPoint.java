package bench.cpu;
import bench.IBenchmark;

public class CPUFixedVsFloatingPoint implements IBenchmark {

    private int result;
    private int size;

    @Override
    public void initialize(Object ... params) {
        this.size = (Integer)params[0];
    }

    @Override
    public void warmup() {
        for (int i = 0; i < 10000; ++i) {
            result+= i/256 ; // fixed
            result+= i/256.0f; // floating
        }
    }

    @Override
    @Deprecated
    public void run() {
    }

    public int iterative_division(int a, int b)
    {
        int count=0;
        while(a>=b) {
            a -= b;
            count++;
        }
        return count;
    }

    @Override
    public void run(Object ...options) {
        result = 0;

        switch ((NumberRepresentation) options[0]) {
            case FLOATING:
                int scale=10000;
                for (int i = 0; i < size; ++i) {
                    result+=i/256.0f;
                }
                break;
            case FIXED:
                for (int i = 0; i < size; ++i) {
                    //result += i/256;
                    result+= i >> 8;
                }
                break;
            default:
                throw new RuntimeException("Unsupported option");
        }

    }

    @Override
    public void cancel() {

    }


    public void clean() {
    }


    public String getResult() {
        return String.valueOf(result);
    }

}
