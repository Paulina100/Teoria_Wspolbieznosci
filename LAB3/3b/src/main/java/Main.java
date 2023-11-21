public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Thread producer = new Thread(new Producer(monitor, 0, 1));

        Thread consumer0 = new Thread(new Consument(monitor, 0, 1));
        Thread consumer1 = new Thread(new Consument(monitor, 1, 1));
        Thread consumer2 = new Thread(new Consument(monitor, 2, 1));
        Thread consumer3 = new Thread(new Consument(monitor, 3, 1));
        Thread consumer4 = new Thread(new Consument(monitor, 4, 1));
        Thread consumer5 = new Thread(new Consument(monitor, 5, 10));

        producer.start();
        consumer0.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();
    }

}
