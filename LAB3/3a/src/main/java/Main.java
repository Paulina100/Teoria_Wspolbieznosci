public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Thread producer = new Thread(new Producer(monitor, 1, 1));
//        Thread producer2 = new Thread(new Producer(monitor, 1));
//        Thread producer3 = new Thread(new Producer(monitor));
        Thread consumer = new Thread(new Consument(monitor, 2, 1));
        Thread consumer2 = new Thread(new Consument(monitor, 3, 10));
//        Thread consumer3 = new Thread(new Consument(monitor));

        producer.start();
//        producer2.start();
//        producer3.start();
        consumer.start();
        consumer2.start();
//        consumer3.start();

    }

}
