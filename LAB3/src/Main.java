public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        Thread producer = new Thread(new Producer(data));
//        Thread producer2 = new Thread(new Producer(data));
        Thread consumer = new Thread(new Consument(data));

        producer.start();
//        producer2.start();
        consumer.start();
    }

}
