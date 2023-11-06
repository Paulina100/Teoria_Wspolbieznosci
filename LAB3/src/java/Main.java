//public class Main{
//    static int counter = 0;
//
//    public static void main(String[] args) {
//        int numberOfThreads = 10;
//
//        for (int i = 0; i < numberOfThreads; i++) {
//            Increment thread = new Increment(counter);
//            thread.start();
//        }
//        for (int i = 0; i < numberOfThreads; i++) {
//            Decrement thread = new Decrement(counter);
//            thread.start();
//        }
//        System.out.println(counter);
//    }
//}

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Thread producer = new Thread(new Producer(monitor, 1, 1));
//        Thread producer2 = new Thread(new Producer(monitor, 1));
//        Thread producer3 = new Thread(new Producer(monitor));
        Thread consumer = new Thread(new Consument(monitor, 1, 1));
        Thread consumer2 = new Thread(new Consument(monitor, 2, 100));
//        Thread consumer3 = new Thread(new Consument(monitor));

        producer.start();
//        producer2.start();
//        producer3.start();
        consumer.start();
        consumer2.start();
//        consumer3.start();

    }

}
