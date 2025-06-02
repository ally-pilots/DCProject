package bench.hdd;

import java.io.IOException;

import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

    @Override
    public void initialize(Object... params) {
    }

    @Override
    public void warmup() {
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {
        FileWriter writer = new FileWriter();

        // either "fs" - fixed size, or "fb" - fixed buffer
        String option = (String) options[0];

        // true/false whether the written files should be deleted at the end
        Boolean clean = (Boolean) options[1];


        String prefix = "C:\\bench_output\\write-"; // ... folder path on disk + file name";
        String suffix = ".dat";
        int minIndex = 0;
        int maxIndex = 8;
        long fileSize = 256L * 1024 * 1024; // ... // 256, 512 MB, 1GB // type Long!
        int bufferSize = 4 * 1024; // ... // 4 KB

        try {
            double score;
            if (option.equalsIgnoreCase("fs")) {
                score = writer.streamWriteFixedFileSize(prefix, suffix, minIndex,
                        maxIndex, fileSize, clean);
            } else if (option.equalsIgnoreCase("fb")) {
                score = writer.streamWriteFixedBufferSize(prefix, suffix, minIndex,
                        maxIndex, bufferSize, clean);
            } else {
                throw new IllegalArgumentException("Argument "
                        + options[0].toString() + " is undefined");
            }

            lastResult = "Average write speed ( " +option + "):" + String.format("%.2f", score) + "MB/sec" ;

        } catch (IOException e) {
            e.printStackTrace();
            lastResult = "Error during benchmark" + e.getMessage() ;
        }
    }

    @Override
    public void clean() {
        // clean temp files here?
    }

    @Override
    public void cancel() {

    }

    private String lastResult = "";

    @Override
    public String getResult() {
        return lastResult;
//        return null; // or MBps
    }
}