package bench;



public interface IBenchmark{
    void run();
    void run(Object... parameters);
    void initialize(Object... parameters);
    void clean();
    void cancel();
    public void warmup();
    public String getResult();
}


