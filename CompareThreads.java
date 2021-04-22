package GB.lvl2lesson5;

import java.util.Arrays;

public class CompareThreads {

    static final int size = 10000000;
    static final int h = size / 2;


    public static void main(String[] args) {

        timeWithoutThreads();
        timeInTwoThreads();
    }


    public static void timeWithoutThreads() {
        System.out.println("\n*** Without threads method ***");

        float[] arr = new float[size];
        Arrays.fill(arr, 1);

        long start = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        long finish = System.currentTimeMillis();

        System.out.println(finish - start + ": execution time without division into threads.");
    }

    public static void timeInTwoThreads() {
        System.out.println("\n*** Two threads method ***");

        float[] arr = new float[size];
        Arrays.fill(arr, 1);

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        long start = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        long finish = System.currentTimeMillis();
        System.out.println(finish - start + ": time to split two arrays.");

        MyThread myThread1 = new MyThread(a1);
        MyThread myThread2 = new MyThread(a2);

        Thread t1 = new Thread(myThread1);
        Thread t2 = new Thread(myThread2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long max = Math.max(myThread1.getCurrentTime(), myThread2.getCurrentTime());

        System.out.println(max + ": maximum execution time in two threads.");

        long start1 = System.currentTimeMillis();

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);


        long finish1 = System.currentTimeMillis();
        System.out.println(finish1 - start1 + ": time to merge two arrays.");
    }

}


