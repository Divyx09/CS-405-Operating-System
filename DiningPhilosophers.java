
import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    public static void main(String[] args) {
        // Initialize the semaphores for forks
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        // Initialize and start the philosopher threads
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }
    }

    static class Philosopher extends Thread {

        private final int id;
        private final int leftFork;
        private final int rightFork;

        public Philosopher(int id) {
            this.id = id;
            this.leftFork = id;
            this.rightFork = (id + 1) % NUM_PHILOSOPHERS;
        }

        public void run() {
            try {
                while (true) {
                    think();
                    pickUpForks();
                    eat();
                    putDownForks();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking.");
            Thread.sleep((int) (Math.random() * 1000));
        }

        private void pickUpForks() throws InterruptedException {
            if (id % 2 == 0) {
                forks[leftFork].acquire();
                forks[rightFork].acquire();
            } else {
                forks[rightFork].acquire();
                forks[leftFork].acquire();
            }
            System.out.println("Philosopher " + id + " picked up forks.");
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep((int) (Math.random() * 1000));
        }

        private void putDownForks() {
            forks[leftFork].release();
            forks[rightFork].release();
            System.out.println("Philosopher " + id + " put down forks.");
        }
    }
}
