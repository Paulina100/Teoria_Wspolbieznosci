import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();

        System.out.println("Experiment 1:");

        int numberOfExecutions = 10;
        long[] executionTimes = new long[numberOfExecutions];

        for (int i = 0; i < numberOfExecutions; i++) {
            long startTime = System.currentTimeMillis();

            Thread producer = new Thread(new Producer(monitor, 0, 1));

            Thread consumer0 = new Thread(new Consument(monitor, 0, 1));
            Thread consumer1 = new Thread(new Consument(monitor, 1, 10));

            producer.start();
            consumer0.start();
            consumer1.start();

            try {
                producer.join();
                consumer0.join();
                consumer1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            executionTimes[i] = elapsedTime;
        }

        // Wyświetlenie czasów wykonania
        for (int i = 0; i < numberOfExecutions; i++) {
            System.out.println("Execution " + (i + 1) + " time: " + executionTimes[i] + " ms");
        }

        // Obliczenie i wyświetlenie średniej
        double averageTime = Arrays.stream(executionTimes).average().orElse(Double.NaN);

        System.out.println("Average time: " + averageTime + " ms");

        System.out.println();
        System.out.println("Experiment 2:");

        for (int i = 0; i < numberOfExecutions; i++) {
            long startTime = System.currentTimeMillis();

            Thread producer = new Thread(new Producer(monitor, 0, 1));

            Thread consumer0 = new Thread(new Consument(monitor, 0, 1));
            Thread consumer1 = new Thread(new Consument(monitor, 1, 2));
            Thread consumer2 = new Thread(new Consument(monitor, 1, 3));
            Thread consumer3 = new Thread(new Consument(monitor, 1, 5));

            producer.start();
            consumer0.start();
            consumer1.start();
            consumer2.start();
            consumer3.start();

            try {
                producer.join();
                consumer0.join();
                consumer1.join();
                consumer2.join();
                consumer3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            executionTimes[i] = elapsedTime;
        }

        // Wyświetlenie czasów wykonania
        for (int i = 0; i < numberOfExecutions; i++) {
            System.out.println("Execution " + (i + 1) + " time: " + executionTimes[i] + " ms");
        }

        // Obliczenie i wyświetlenie średniej
        averageTime = Arrays.stream(executionTimes).average().orElse(Double.NaN);

        System.out.println("Average time: " + averageTime + " ms");
    }



}
