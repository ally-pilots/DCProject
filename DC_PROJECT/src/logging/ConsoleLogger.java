package logging;

public class ConsoleLogger implements ILogger {

    @Override
    public void write(String s) {
        System.out.println(s);
    }

    @Override
    public void write(long l) {
        System.out.println(l);
    }

    @Override
    public void write(Object... values) {
        for(Object val : values) {
            System.out.println(val + " ");
        }
        System.out.println();
    }

    @Override
    public void writeTime(String message, long timeNs, TimeUnit unit) {
        System.out.printf("%s: %.3f &s%n", message, unit.convert(timeNs), unit.suffix());
    }

    @Override
    public void close() {

    }
}
