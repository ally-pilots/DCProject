package benchmark;

import java.util.Random;

public class DemoBenchmark implements IBenchmark {
    private int[] array;
    private boolean running = true;

    @Override
    public void initialize(Object... params) {
        int size = (int) params[0];
        array = new int[size];
        Random rand = new Random();
        for(int i = 0; i < size; i++) {
            array[i] = rand.nextInt();
        }
        running = true;
    }
    @Override
    public void run() {
        for(int i = 0; i < array.length && running; i++) {
            for(int j = 0; j < array.length - i - 1 && running; j++) {
                if(array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void cancel() {
        running = false;
    }

    @Override
    public void clean() {
        array = null;
    }
}
