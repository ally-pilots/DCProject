package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public interface ILogger {
    void write(long parameter);
    void write(String parameter);
    void write(Object... values);
    void close();
    void writeTime(String message, long timeMs, TimeUnit unit);
}

/*
public class DatabaseLogger implements ILogger{

}*/