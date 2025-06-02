package bench.cpu;

import java.math.BigDecimal;

public interface IDigitsOfPi {
    void warmup();
    void configureCalculator(int nrOfDigits);
    BigDecimal calculatePi();
    void cancel();

}
