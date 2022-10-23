import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {
    public int bufor = 0;
    public int buforSize = 5;
    ReentrantLock lock = new ReentrantLock();
    Condition buforEmpty = lock.newCondition();
    Condition buforFull = lock.newCondition();


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(){
        int portion = getRandomNumber(0, buforSize);

        lock.lock();
        while(bufor + portion <= buforSize){
            try {
                buforFull.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        bufor += 1;
        buforEmpty.signal();
        lock.unlock();
        System.out.println("produced");
    }

    public void consume(){
        try {
            lock.lock();
            while(bufor == 0){
                buforEmpty.await();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        bufor -= 1;
        buforFull.signal();
        lock.unlock();
        System.out.println("consumed");
    }
}
