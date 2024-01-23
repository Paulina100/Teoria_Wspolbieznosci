import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor3Locks implements Monitor{
    public int currBufor = 0;
    public final int maxPortion;
    public final int maxBufor;
    private final Counter operationsCounter;

    ReentrantLock outsideConsumer = new ReentrantLock();
    ReentrantLock outsideProducer = new ReentrantLock();
    ReentrantLock common = new ReentrantLock();

    Condition commonCondition = common.newCondition();

    Monitor3Locks(int maxPortion, Counter operationsCounter){
        this.maxPortion = maxPortion;
        this.maxBufor = maxPortion * 2;
        this.operationsCounter = operationsCounter;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(){
        int portion = getRandomNumber(1, maxPortion);

        outsideProducer.lock();
        try {
            common.lock();

            while (maxBufor - currBufor < portion) {
                commonCondition.await();
            }

            currBufor += portion;
            operationsCounter.add();

            commonCondition.signal();
            common.unlock();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        outsideProducer.unlock();
    }

    public void consume(){
        int portion = getRandomNumber(1, maxPortion);

        outsideConsumer.lock();
        try {
            common.lock();

            while (currBufor < portion){
                commonCondition.await();
            }

            currBufor -= portion;
            operationsCounter.add();

            commonCondition.signal();
            common.unlock();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        outsideConsumer.unlock();
    }
}