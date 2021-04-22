package GB.lvl2lesson5;

public class MyThread implements Runnable {
    private float[] arr;
    private long currentTime;
    private static int counter = 0;
    private int arrayNumber;

    public MyThread(float[] arr) {
        this.arr = arr;
        this.currentTime = 0;
        arrayNumber = getArrayNumber();
        counter++;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public int getArrayNumber() {
        arrayNumber = counter;
        return arrayNumber;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + arr.length * arrayNumber) / 5)
                    * Math.cos(0.2f + (i + arr.length * arrayNumber) / 5)
                    * Math.cos(0.4f + (i + arr.length * arrayNumber) / 2));
        }
        long finish = System.currentTimeMillis();
        currentTime = finish - start;
    }

}
