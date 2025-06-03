package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger{
    private BufferedWriter bw;
    public FileLogger()
    {
        try{
            bw=new BufferedWriter(new FileWriter("log.txt",true));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void close() {
        try{
            if(bw!=null) {
                bw.flush();
                bw.close();
            }
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void write(Object... values) {
        try{
            for (Object value : values) {
                bw.write(value+" ");
            }
            bw.newLine();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void write(long parameter) {
        try{
            bw.write(parameter+"");
            bw.newLine();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void write(String parameter) {
        try{
            bw.write(parameter+"");
            bw.newLine();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeTime(String message, long timeNs,TimeUnit unit) {
        try {
            bw.write(String.format("%s: %3f %s%n", message, unit.convert(timeNs), unit.suffix()));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
