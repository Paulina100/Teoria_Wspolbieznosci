import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int noProducers = 3;
        int noConsumers = 3;
        int noOperations = 500000;
        int maxPortion = 10;

        Counter operationsCounter = new Counter(noOperations);
        Monitor3Locks monitor = new Monitor3Locks(maxPortion, operationsCounter);
//        Monitor4Cond monitor = new Monitor4Cond(maxPortion, operationsCounter);

        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0;i<noProducers;i++){
            threads.add(new Thread(new Producer(monitor)));
        }
        for(int i = 0; i<noConsumers;i++){
            threads.add(new Thread(new Consumer(monitor)));
        }
        
        for(Thread thread:threads){
            thread.start();
        }
    }
}