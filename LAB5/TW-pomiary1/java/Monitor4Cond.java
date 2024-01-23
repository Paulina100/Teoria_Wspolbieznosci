import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor4Cond implements Monitor{
    public int currBufor = 0;
    public final int maxPortion;
    public final int maxBufor;
    private final Counter operationsCounter;

    ReentrantLock lock = new ReentrantLock();
    Condition firstProducer = lock.newCondition();
    Condition restProducers = lock.newCondition();
    Condition firstConsumer = lock.newCondition();
    Condition restConsumers = lock.newCondition();

    boolean firtProducerWaits = false;
    boolean firtConsumerWaits = false;

    Monitor4Cond(int maxPortion, Counter operationsCounter){
        this.maxPortion = maxPortion;
        this.maxBufor = maxPortion * 2;
        this.operationsCounter = operationsCounter;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(){
        int portion = getRandomNumber(1, maxPortion);

        lock.lock();
        while (firtProducerWaits){
            try {
                restProducers.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (maxBufor - currBufor < portion){
            try {
                firtProducerWaits = true;
                firstProducer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currBufor += portion;
        operationsCounter.add();

        firtProducerWaits = false;
        restProducers.signal();
        firstConsumer.signal();

        lock.unlock();
    }

    public void consume(){
        int portion = getRandomNumber(1, maxPortion);

        lock.lock();
        while (firtConsumerWaits){
            try {
                restConsumers.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (currBufor < portion){
            try {
                firtConsumerWaits = true;
                firstConsumer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currBufor -= portion;
        operationsCounter.add();

        firtConsumerWaits = false;
        restConsumers.signal();
        firstProducer.signal();

        lock.unlock();
    }
}
