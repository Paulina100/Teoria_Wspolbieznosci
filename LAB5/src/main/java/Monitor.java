import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor {
    public int currBufor = 0;
    public int maxPortion = 10;
    public int maxBufor = 2 * maxPortion;
//    public int[] consumers = {0, 0, 0, 0, 0, 0};

    ReentrantLock outsideConsumer = new ReentrantLock();
    ReentrantLock outsideProducer = new ReentrantLock();
    ReentrantLock common = new ReentrantLock();

    Condition commonCondition = common.newCondition();


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void produce(int id, int portion){
        outsideProducer.lock();
        try {
            common.lock();

            while (maxBufor - currBufor < portion) {
                commonCondition.await();
            }

            currBufor += portion;

            commonCondition.signal();
            common.unlock();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        outsideProducer.unlock();
    }

    public void consume(int id, int portion){
        outsideConsumer.lock();
        try {
            common.lock();

            while (currBufor < portion){
                commonCondition.await();
            }

            currBufor -= portion;

//            consumers[id] ++;
//            System.out.print("Consumers: [");
//            for (int i = 0; i < consumers.length; i++) {
//                System.out.print(consumers[i]);
//                if (i < consumers.length - 1) {
//                    System.out.print(", ");
//                }
//            }
//            System.out.println("]");

            commonCondition.signal();
            common.unlock();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        outsideConsumer.unlock();
    }
}
