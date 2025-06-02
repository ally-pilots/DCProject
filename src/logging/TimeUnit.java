package logging;

public enum TimeUnit{
Nano,Micro,Milli,Sec;

public double convert(long timeNs) {
    switch (this) {
        case Nano:
            return timeNs;
        case Micro:
            return timeNs / 1_000.0;
        case Milli:
            return timeNs/1_000_000.0;
        case Sec:
            return timeNs / 1_000_000_000.0;
        default:
            return timeNs;
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