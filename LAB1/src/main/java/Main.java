public class Main {
    static int counter = 0;

    public static void main(String[] args) {
        int numberOfThreads = 50;
        int numberOfIterations = 1000;

        Thread[] threadArray = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads/2; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    counter++;
                }
            });
            threadArray[i].start();
        }

        for (int i = numberOfThreads/2; i < numberOfThreads; i++) {
            threadArray[i] = new Thread(() -> {
                for (int j = 0; j < numberOfIterations; j++) {
                    counter--;
                }
            });
            threadArray[i].start();
        }


        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threadArray[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Counter:\t" + counter);
    }
}

