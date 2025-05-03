package logging;

public class ConsoleLogger implements ILogger{
    public void close()
    {
    }

    public void write(long parameter)
    {
        System.out.println(parameter);
    }

    public void write(String parameter)
    {
        System.out.println(parameter);
    }

    public void write(Object... values)
    {
        for (Object value : values) {
            System.out.print(value+" ");
        }
        System.out.println();
    }

    public void writeTime(String message, long timeMs,TimeUnit unit)
    {
        System.out.printf("%s: %3f %s%n",message,unit.convert(timeMs),unit.suffix());
    }

}

