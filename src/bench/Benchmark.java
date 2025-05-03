package bench;

import java.util.Random;

public class Benchmark implements IBenchmark
{
    private int array[];
    private boolean running;
    public void run()
    {
        if(array==null)
            return;
        for(int i=0;i<array.length-1&&running;i++)
        {
            for(int j=0;j<array.length-i-1&&running;j++)
            {
                if(array[j]>array[j+1])
                {
                    int temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
    }

    public void clean()
    {
        array=null;
    }

    public void cancel()
    {
        running=false;
    }

    public void run(Object... parameters)
    {
        run();
    }

    public void initialize(Object... parameters)
    {
        int n= (int) parameters[0];
        array = new int[n];
        Random r = new Random();
        for(int i=0;i<n;i++)
        {
            array[i]=r.nextInt(n);
        }
        running=true;
    }
}
