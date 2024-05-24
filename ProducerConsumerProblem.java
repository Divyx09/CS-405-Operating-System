import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

class ProducerConsumerProblem {

    private static final int BUFFER_SIZE = 5;
    private static final Semaphore empty = new Semaphore(BUFFER_SIZE);
    private static final Semaphore full = new Semaphore(0);
    private static final Semaphore mutex = new Semaphore(1);
    private static final Queue<Integer> buffer = new LinkedList<>();

    static class Producer extends Thread {

        public void run() {
            int item = 0;
            while (true) {
                try {
                    empty.acquire();  // Wait for empty slot
                    mutex.acquire();  // Enter critical section

                    // Produce an item
                    buffer.add(item);
                    System.out.println("Produced: " + item);
                    item++;

                    mutex.release();  // Exit critical section
                    full.release();   // Signal that an item was produced

                    Thread.sleep((int) (Math.random() * 1000));  // Simulate production time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {

        public void run() {
            while (true) {
                try {
                    full.acquire();   // Wait for available item
                    mutex.acquire();  // Enter critical section

                    // Consume an item
                    int item = buffer.remove();
                    System.out.println("Consumed: " + item);

                    mutex.release();  // Exit critical section
                    empty.release();  // Signal that an item was consumed

                    Thread.sleep((int) (Math.random() * 1000));  // Simulate consumption time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        producer.start();
        consumer.start();
    }
}
