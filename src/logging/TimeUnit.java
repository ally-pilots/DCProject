package logging;

public enum TimeUnit{
Nano,Micro,Milli,Sec;

public double convert(long timeMs) {
    switch (this) {
        case Nano:
            return timeMs * 1_000_000;
        case Micro:
            return timeMs * 1_000;
        case Milli:
            return timeMs;
        case Sec:
            return timeMs / 1_000.0;
        default:
            return timeMs;
    }
}

    public String suffix()
    {
        switch (this){
            case Nano:return "ns";
            case Micro:return "us";
            case Milli:return "ms";
            case Sec:return "s";
            default:return "ms";
        }
    }
}