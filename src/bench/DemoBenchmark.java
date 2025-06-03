package bench;

import java.util.Random;

public class DemoBenchmark implements IBenchmark
{
    private int array[];
    private boolean running;
    private int size;
    public void run()
    {
        if(array==null||!running)
            return;
        for(int i=0;i<size-1&&running;i++)
        {
            for(int j=0;j<size-i-1&&running;j++)
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

    public String getResult()
    {
        return null;
    }

    public void run(Object... parameters)
    {
        run();
    }

    @Override
    public void warmup() {
        run();
    }

    public void initialize(Object... parameters)
    {
        size= (int) parameters[0];
        array = new int[size];
        Random r = new Random();
        for(int i=0;i<size;i++)
        {
            array[i]=r.nextInt(size);
        }
        running=true;
    }
}
