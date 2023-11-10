import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    public int currBufor = 0;
    public int maxPortion = 10;
    public int maxBufor = 2 * maxPortion;

    ReentrantLock lock = new ReentrantLock();
    Condition firstProducer = lock.newCondition();
    Condition restProducers = lock.newCondition();
    Condition firstConsumer = lock.newCondition();
    Condition restConsumers = lock.newCondition();

    boolean firtProducerWaits = false;
    boolean firtConsumerWaits = false;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(int id, int portion){
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
        firtProducerWaits = false;
        System.out.println("Produced ID: " + id + " Portion: " + portion);

        restProducers.signal();
        firstConsumer.signal();

        lock.unlock();
    }

    public void consume(int id, int portion){
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
        firtConsumerWaits = false;
        System.out.println("Consumed ID: " + id + " Portion: " + portion);

        restConsumers.signal();
        firstProducer.signal();

        lock.unlock();
    }
}
