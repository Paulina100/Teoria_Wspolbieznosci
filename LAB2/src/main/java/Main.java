public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Thread producer = new Thread(new Producer(monitor));
        Thread producer2 = new Thread(new Producer(monitor));
        Thread consumer = new Thread(new Consument(monitor));

        producer.start();
        producer2.start();
        consumer.start();
    }

}
