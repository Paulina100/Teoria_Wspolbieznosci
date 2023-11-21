import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    public int currBufor = 0;
    public int maxPortion = 10;
    public int maxBufor = 2 * maxPortion;
//    public int[] consumers = {0, 0, 0, 0, 0, 0};

    ReentrantLock lock = new ReentrantLock();
    Condition firstProducer = lock.newCondition();
    Condition restProducers = lock.newCondition();
    Condition firstConsumer = lock.newCondition();
    Condition restConsumers = lock.newCondition();


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(int id, int portion){
        lock.lock();
        while (lock.hasWaiters(firstProducer)){
            try {
                restProducers.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (maxBufor - currBufor < portion){
            try {
                firstProducer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currBufor += portion;

        restProducers.signal();
        firstConsumer.signal();

        lock.unlock();
    }

    public void consume(int id, int portion){
        lock.lock();
        while (lock.hasWaiters(firstConsumer)){
            try {
                restConsumers.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (currBufor < portion){
            try {
                firstConsumer.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        currBufor -= portion;
//        consumers[id] ++;
//        System.out.print("Consumers: [");
//        for (int i = 0; i < consumers.length; i++) {
//            System.out.print(consumers[i]);
//            if (i < consumers.length - 1) {
//                System.out.print(", ");
//            }
//        }
//        System.out.println("]");

        restConsumers.signal();
        firstProducer.signal();

        lock.unlock();
    }
}
