package logging;

public interface ILogger {
    void write(String s);
    void write(long l);
    void write(Object... values);
    void writeTime(String message, long timeNs, TimeUnit unit);
    void close();
}
