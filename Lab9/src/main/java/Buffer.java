import org.jcsp.lang.*;
import java.util.LinkedList;

public class Buffer implements CSProcess {
    private One2OneChannelInt req;
    private One2OneChannelInt res;
    private One2OneChannelInt[] prodChannels;
    private One2OneChannelInt[] consChannels;

    private final LinkedList<Integer> buffer = new LinkedList<>();
    private int size;
    public long totalWaitTime = 0;

    public Buffer(final One2OneChannelInt req, final One2OneChannelInt res,
                  final One2OneChannelInt[] prodChannels, final One2OneChannelInt[] consChannels,
                  final int size) {
        this.req = req;
        this.res = res;
        this.prodChannels = prodChannels;
        this.consChannels = consChannels;
        this.size = size;
    }

    public void run() {
        int item;
        int index;
        boolean handledTerminationSignal = false;

        while (true) {
            int state;
            // 0: normal, 1: full, -1: empty
            if (buffer.size() == 0) {
                state = -1;
            } else if (buffer.size() == size - 1) {
                state = 1;
            } else {
                state = 0;
            }
            // 10: normal, 11: full, 9: empty (and handled termination signal)
            if (handledTerminationSignal) {
                state += 10;
                handledTerminationSignal = false;
            }

            long startTime = System.nanoTime();
            // Send the state to the manager
            req.out().write(state);

            // Receive the (producer of consumer) index from the manager
            index = res.in().read();
            long endTime = System.nanoTime();
            this.totalWaitTime += endTime - startTime;

            // -1: termination signal
            if (index < 0) {
                break;
            // 0, 1: producer
            } else if (index < 2) {
                item = prodChannels[index].in().read();
                buffer.add(item);
                if (item < 0) {
                    handledTerminationSignal = true;
                }
            // 2, 3: consumer
            } else {
                item = buffer.removeFirst();
                consChannels[index - 2].out().write(item);
                if (item < 0) {
                    handledTerminationSignal = true;
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("Buffer ended. Total wait time: " + totalWaitTime/1000000 + " ms");
    }
}
