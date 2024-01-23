import org.jcsp.lang.*;
import java.util.LinkedList;

public class Manager implements CSProcess {
    private One2OneChannelInt[] prodReq;
    private One2OneChannelInt[] prodRes;
    private One2OneChannelInt[] consReq;
    private One2OneChannelInt[] consRes;
    private One2OneChannelInt[] bufferReq;
    private One2OneChannelInt[] bufferRes;

    private LinkedList<Integer> prodQueue = new LinkedList<>();
    private LinkedList<Integer> consQueue = new LinkedList<>();
    private LinkedList<Integer> bufferProdQueue = new LinkedList<>();
    private LinkedList<Integer> bufferConsQueue = new LinkedList<>();

    private int prodConsCountdown = 4;
    private int bufferCountdown = 2;

    public Manager(final One2OneChannelInt[] prodReq, final One2OneChannelInt[] prodRes,
                   final One2OneChannelInt[] consReq, final One2OneChannelInt[] consRes,
                   final One2OneChannelInt[] bufferReq, final One2OneChannelInt[] bufferRes) {
        this.prodReq = prodReq;
        this.prodRes = prodRes;
        this.consReq = consReq;
        this.consRes = consRes;
        this.bufferReq = bufferReq;
        this.bufferRes = bufferRes;
    }
    public void run() {
        final Guard[] guards = {prodReq[0].in(), prodReq[1].in(),
                                consReq[0].in(), consReq[1].in(),
                                bufferReq[0].in(), bufferReq[1].in()};
        final Alternative alt = new Alternative(guards);

        while (bufferCountdown > 0) {
            int index = alt.select();
            switch (index) {
                case 0:
                case 1:
                    // Read producer request
                    prodReq[index].in().read();
                    // If there is a buffer that is waiting for a producer, connect them
                    if (bufferProdQueue.size() > 0) {
                        int bufferIndex = bufferProdQueue.removeFirst();
                        bufferConsQueue.remove((Integer) bufferIndex);

                        prodRes[index].out().write(bufferIndex);
                        bufferRes[bufferIndex].out().write(index);
                    // Otherwise, add the producer to the waiting producers queue
                    } else {
                        prodQueue.add(index);
                    }
                    break;
                case 2:
                case 3:
                    // Read consumer request
                    consReq[index - 2].in().read();
                    // If there is a buffer that is waiting for a consumer, connect them
                    if (bufferConsQueue.size() > 0) {
                        int bufferIndex = bufferConsQueue.removeFirst();
                        bufferProdQueue.remove((Integer) bufferIndex);

                        consRes[index - 2].out().write(bufferIndex);
                        bufferRes[bufferIndex].out().write(index);
                    // Otherwise, add the consumer to the waiting consumers queue
                    } else {
                        consQueue.add(index - 2);
                    }
                    break;
                case 4:
                case 5:
                    // Read buffer state
                    int bufferState = bufferReq[index - 4].in().read();

                    // The buffer indicates that it has handled (received from producer or sent to consumer) a
                    // termination signal (-1) by incrementing its state by 10.
                    if (bufferState > 1) {
                        bufferState -= 10;
                        prodConsCountdown--;
                    }
                    // If all producers and consumers have terminated, send termination signal to buffers and exit
                    if (prodConsCountdown <= 0) {
                        bufferRes[0].out().write(-1);
                        bufferRes[1].out().write(-1);
                        bufferCountdown -= 2;
                        break;
                    }

                    // If buffer has space, produce
                    if (bufferState != 1 && prodQueue.size() > 0) {
                        int prodIndex = prodQueue.removeFirst();
                        prodRes[prodIndex].out().write(index - 4);
                        bufferRes[index - 4].out().write(prodIndex);
                    // Otherwise, If buffer has items, consume
                    } else if (bufferState != -1 && consQueue.size() > 0) {
                        int consIndex = consQueue.removeFirst();
                        consRes[consIndex].out().write(index - 4);
                        bufferRes[index - 4].out().write(consIndex + 2);
                    // Otherwise, add buffer to queue(s)
                    } else {
                        // If buffer is not empty, add it to queue for consumers
                        if (bufferState != -1) {
                            bufferConsQueue.add(index - 4);
                        }
                        // If buffer is not full, add it to queue for producers
                        if (bufferState != 1) {
                            bufferProdQueue.add(index - 4);
                        }
                    }
                    break;
            }
        }
//        System.out.println("Manager ended.");
    }
}
