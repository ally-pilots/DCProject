package bench.cpu;
import bench.IBenchmark;
import java.math.*;

public class CPUDigitsOfPi implements IBenchmark {

    private BigDecimal pi;
    private IDigitsOfPi calculator;

    public BigDecimal getPreviousPiResult()
    {
        return pi;
    }

    public String getResult()
    {
        return null;
    }

    public void run()
    {

        pi=calculator.calculatePi();
        System.out.println("Pi: "+pi);
    }

    public void run(Object... params)
    {
        initialize(params);
        run();
    }

    public void initialize(Object... params)
    {
        if(params.length!=2)
        {
            throw new IllegalArgumentException("Wrong number of parameters");
        }

        if(!(params[0] instanceof Number)||!(params[1] instanceof Number))
        {
            throw new IllegalArgumentException("Wrong parameter type");
        }

        int alg=((Number)params[0]).intValue();
        int digits=((Number)params[1]).intValue();

        if(digits<10)
        {
            throw new NumberFormatException("The digits must be at least 10");
        }

        switch(alg)
        {
            case 0:
                calculator=new GaussLegendre();
                ((GaussLegendre) calculator).configureCalculator(digits);
                break;
            case 1:
                calculator=new BaileyBorwein();
                ((BaileyBorwein) calculator).configureCalculator(digits);
                break;
            default: throw new IllegalArgumentException("Unknown algorithm");
        }
        calculator.configureCalculator(digits);
    }
public void clean()
{

}

public void cancel()
{
    if(calculator==null)
    {
        throw new IllegalStateException("The calculator is null");
    }
    calculator.cancel();
}

public void warmup()
{
    if(calculator==null)
    {
        throw new IllegalStateException("The calculator is null");
    }
    calculator.warmup();
}

}
