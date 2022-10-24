import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {
    public int buforCurr = 0;
    public int buforSize = 100;
    ReentrantLock lock = new ReentrantLock();
    Condition buforEmpty = lock.newCondition();
    Condition buforFull = lock.newCondition();


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(){
        int portion = getRandomNumber(1, buforSize/2);

        lock.lock();
        while(buforCurr + portion > buforSize){
            try {
                buforFull.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        buforCurr += portion;
        buforEmpty.signal();
        lock.unlock();
        System.out.println("Produced\tPortion= " + portion);
    }

    public void consume(){
        int portion = getRandomNumber(1, buforSize/2);

        lock.lock();
        while(buforCurr - portion < 0){
            try {
                buforEmpty.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        buforCurr -= 1;
        buforFull.signal();
        lock.unlock();
        System.out.println("Consumed\tPortion= " + portion);
    }
}
