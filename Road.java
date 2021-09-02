package gb.lvl3lesson5;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Road extends Stage {
    boolean finish;
    boolean winner;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }

    public Road(int length, boolean finish) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
        this.finish = finish;
    }

    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
            try {
                readWriteLock.writeLock().lock();
                if (!winner & finish) {
                    winner = true;
                    System.out.println(c.getName() + " WIN! ");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

