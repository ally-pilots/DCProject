package benchmark;

public interface IBenchmark {
    void initialize(Object... params);
    void run();
    void run(Object... options);
    void clean();
    void cancel();
}
