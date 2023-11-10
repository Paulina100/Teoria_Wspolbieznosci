import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    public int currBufor = 0;
    public int maxPortion = 10;
    public int maxBufor = 2 * maxPortion;

    ReentrantLock lock = new ReentrantLock();
    Condition buforEmptyCondition = lock.newCondition();
    Condition buforFullCondition = lock.newCondition();

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(int id, int portion){
//        int portion = getRandomNumber(1, maxPortion);

        lock.lock();
        while (currBufor + portion > maxBufor){
            try {
                buforFullCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buforEmptyCondition.signal();
        currBufor += portion;
        System.out.println("Produced ID: " + id + " Portion: " + portion);

        lock.unlock();
    }

    public void consume(int id, int portion){
//        int portion = getRandomNumber(1, maxPortion);

        lock.lock();
        while (currBufor - portion < 0){
            try {
                buforEmptyCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buforFullCondition.signal();
        currBufor -= portion;
        System.out.println("Consumed ID: " + id + " Portion: " + portion);
        lock.unlock();
    }
}
