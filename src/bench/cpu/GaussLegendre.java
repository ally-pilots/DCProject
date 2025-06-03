package bench.cpu;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class GaussLegendre implements IDigitsOfPi {
    private static final BigDecimal bigTwo=new BigDecimal(2);
    private static final BigDecimal bigFour=new BigDecimal(4);
    private static final BigDecimal bigOneQuarter=new BigDecimal("0.25");

    private MathContext mathContext=new MathContext(10, RoundingMode.HALF_UP);
    private int nrIterations=2;
    private boolean Canceled=false;

    public void warmup() {
        calculatePi(50);
    }

    public BigDecimal calculatePi()
    {
        return calculatePi(nrIterations);
    }

    public BigDecimal calculatePi(int nrIterations)
    {
        Canceled=false;
        BigDecimal a=BigDecimal.ONE;
        BigDecimal b=BigDecimal.ONE.divide(sqrt(bigTwo),mathContext);
        BigDecimal t=bigOneQuarter;
        BigDecimal p=BigDecimal.ONE;

        for(int i=0;i<nrIterations;i++)
        {
            if(Canceled)
                break;
            BigDecimal prevA=a;

            a=a.add(b).divide(bigTwo, mathContext);
            b = sqrt(prevA.multiply(b));
            t = t.subtract(p.multiply(prevA.subtract(a).pow(2, mathContext), mathContext));
            p = p.multiply(bigTwo);
        }
        BigDecimal PI=a.add(b).pow(2,mathContext).divide(t.multiply(bigFour, mathContext), mathContext);
        return PI;
    }

    public void configureCalculator(int nrOfDigits)
    {
        double dbIterations=1.44* Math.log(nrOfDigits)/Math.log(2);
        nrIterations=Math.max(2,(int)Math.ceil(dbIterations));
        mathContext=new MathContext(nrOfDigits+1, RoundingMode.HALF_UP);
    }

    private BigDecimal sqrt(BigDecimal x) {
        BigDecimal x1=BigDecimal.valueOf(Math.sqrt(x.doubleValue()));
        BigDecimal prev;
        do {
            prev = x1;
            x1 = x.divide(x1, mathContext);
            x1 = x1.add(prev);
            x1 = x1.divide(bigTwo, mathContext);
        }while(!x1.equals(prev));
        return x1;
    }

    public void cancel()
    {
        Canceled=true;
    }


}
