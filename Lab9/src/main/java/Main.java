import org.jcsp.lang.*;

/** Main program class for Producer/Consumer example.
 * Sets up channel, creates one of each process then
 * executes them in parallel, using JCSP.
 */
public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        long prod1Time = 0;
        long prod2Time = 0;
        long cons1Time = 0;
        long cons2Time = 0;
        long buffer1Time = 0;
        long buffer2Time = 0;
        long totalTime = 0;

        int consumer1Count = 0;
        int consumer2Count = 0;

        int iters = 10;
        for (int i = 0; i < iters; i++) {
            final One2OneChannelInt[] prodReq = {Channel.one2oneInt(), Channel.one2oneInt()};
            final One2OneChannelInt[] prodChan = {Channel.one2oneInt(), Channel.one2oneInt()};

            final One2OneChannelInt[] consReq = {Channel.one2oneInt(), Channel.one2oneInt()};
            final One2OneChannelInt[] consChan = {Channel.one2oneInt(), Channel.one2oneInt()};

            final One2OneChannelInt[] bufferReq = {Channel.one2oneInt(), Channel.one2oneInt()};
            final One2OneChannelInt[] bufferChan = {Channel.one2oneInt(), Channel.one2oneInt()};

            final One2OneChannelInt[][] buffProdChan = {
                {Channel.one2oneInt(), Channel.one2oneInt()},
                {Channel.one2oneInt(), Channel.one2oneInt()}
            };

            final One2OneChannelInt[][] buffConsChan = {
                {Channel.one2oneInt(), Channel.one2oneInt()},
                {Channel.one2oneInt(), Channel.one2oneInt()}
            };

            final int bufferSize = 10;

            Producer producer1 = new  Producer(prodReq[0], prodChan[0], new One2OneChannelInt[]{buffProdChan[0][0], buffProdChan[1][0]}, 0);
            Producer producer2 = new Producer(prodReq[1], prodChan[1], new One2OneChannelInt[]{buffProdChan[0][1], buffProdChan[1][1]}, 100);

            Consumer consumer1 = new Consumer(consReq[0], consChan[0], new One2OneChannelInt[]{buffConsChan[0][0], buffConsChan[1][0]});
            Consumer consumer2 = new Consumer(consReq[1], consChan[1], new One2OneChannelInt[]{buffConsChan[0][1], buffConsChan[1][1]});

            Buffer buffer1 = new Buffer(bufferReq[0], bufferChan[0],
                            new One2OneChannelInt[]{buffProdChan[0][0], buffProdChan[0][1]},
                            new One2OneChannelInt[]{buffConsChan[0][0], buffConsChan[0][1]}, bufferSize);
            Buffer buffer2 = new Buffer(bufferReq[1], bufferChan[1],
                            new One2OneChannelInt[]{buffProdChan[1][0], buffProdChan[1][1]},
                            new One2OneChannelInt[]{buffConsChan[1][0], buffConsChan[1][1]}, bufferSize);

            Manager manager = new Manager(prodReq, prodChan, consReq, consChan, bufferReq, bufferChan);

            CSProcess[] procList = {producer1, producer2, consumer1, consumer2, buffer1, buffer2, manager};

            Parallel par = new Parallel(procList);

            long startTime = System.nanoTime();
            par.run();
            long endTime = System.nanoTime();
//            System.out.println("Total runtime: " + (endTime - startTime)/1000000 + " ms");

            totalTime += endTime - startTime;
            prod1Time += producer1.totalWaitTime;
            prod2Time += producer2.totalWaitTime;
            cons1Time += consumer1.totalWaitTime;
            cons2Time += consumer2.totalWaitTime;
            buffer1Time += buffer1.totalWaitTime;
            buffer2Time += buffer2.totalWaitTime;
            consumer1Count += consumer1.count;
            consumer2Count += consumer2.count;
        }

        System.out.println("Total runtime average: " + totalTime/iters/1000000 + " ms");
        System.out.println("Producer 1 total wait time average: " + prod1Time/iters/1000000 + " ms");
        System.out.println("Producer 2 total wait time average: " + prod2Time/iters/1000000 + " ms");
        System.out.println("Consumer 1 total wait time average: " + cons1Time/iters/1000000 + " ms");
        System.out.println("Consumer 2 total wait time average: " + cons2Time/iters/1000000 + " ms");
        System.out.println("Buffer 1 total wait time average: " + buffer1Time/iters/1000000 + " ms");
        System.out.println("Buffer 2 total wait time average: " + buffer2Time/iters/1000000 + " ms");
        System.out.println();
        System.out.println("Consumer 1 count average: " + consumer1Count/iters);
        System.out.println("Consumer 2 count average: " + consumer2Count/iters);
    }
}