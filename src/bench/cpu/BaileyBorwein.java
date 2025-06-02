package bench.cpu;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BaileyBorwein implements IDigitsOfPi{
    private static final BigDecimal bigTwo = new BigDecimal(2);
    private static final BigDecimal bigThree = new BigDecimal(3);
    private static final BigDecimal bigFour = new BigDecimal(4);
    private static final BigDecimal bigSixteen = new BigDecimal(16);

    private MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
    private int nrOfIterations = 2;
    private boolean Canceled = false;

    public void warmup()
    {
        calculatePi(50);
    }

    public void configureCalculator(int nrDigits)
    {
        nrOfIterations = Math.max(2,(int)Math.ceil(nrDigits/100.));
        mathContext = new MathContext(nrDigits+1, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePi()
    {
        return calculatePi(nrOfIterations);
    }


    public void cancel()
    {
        Canceled = true;
    }

    private BigDecimal calculatePi(int nrOfIterations) {
        Canceled = false;
        BigDecimal pi = BigDecimal.ZERO;
        BigDecimal sixteenToK = BigDecimal.ONE;
        BigDecimal divisor = BigDecimal.ONE;
        for (int k = 0; k < nrOfIterations; k++) {
            if (Canceled)
                break;

            BigDecimal term = BigDecimal.ZERO;
            term = term.add(bigFour.divide(BigDecimal.valueOf(8 * k + 1), mathContext));
            term = term.subtract(bigTwo.divide(BigDecimal.valueOf(8 * k + 4), mathContext));
            term = term.subtract(BigDecimal.ONE.divide(BigDecimal.valueOf(8 * k + 5), mathContext));
            term = term.subtract(BigDecimal.ONE.divide(BigDecimal.valueOf(8 * k + 6), mathContext));

            pi = pi.add(term.multiply(sixteenToK), mathContext);
            sixteenToK = sixteenToK.divide(bigSixteen, mathContext);
        }
        return pi;
    }
}
