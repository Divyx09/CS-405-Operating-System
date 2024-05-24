import java.util.concurrent.Semaphore;

class ReaderWriterProblem {
    private static final Semaphore mutex = new Semaphore(1);
    private static final Semaphore writeLock = new Semaphore(1);
    private static int readCount = 0;

    static class Reader extends Thread {
        private final int readerId;

        public Reader(int readerId) {
            this.readerId = readerId;
        }

        public void run() {
            try {
                mutex.acquire();
                if (++readCount == 1) writeLock.acquire();
                mutex.release();

                System.out.println("Reader " + readerId + " is reading.");
                Thread.sleep((int) (Math.random() * 1000));

                mutex.acquire();
                if (--readCount == 0) writeLock.release();
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Writer extends Thread {
        private final int writerId;

        public Writer(int writerId) {
            this.writerId = writerId;
        }

        public void run() {
            try {
                writeLock.acquire();

                System.out.println("Writer " + writerId + " is writing.");
                Thread.sleep((int) (Math.random() * 1000));

                writeLock.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int numReaders = 5;
        int numWriters = 2;

        for (int i = 0; i < numReaders; i++) {
            new Reader(i + 1).start();
        }
        for (int i = 0; i < numWriters; i++) {
            new Writer(i + 1).start();
        }
    }
}
