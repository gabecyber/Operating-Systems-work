// Jason Hertzog
// CS4500 - Operating Systems
// Summer 2023 IA
// Assignment 5

// Modified TOTAL_ASSOCIATES to 3 from 5 to match problem statement.
// Added nowServing to keep track of the next customer to be serviced and prevent random ordering.
// Added more information to the output.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class a5hertzog {
    private static final int TOTAL_ASSOCIATES = 3;
    private static Semaphore ticketNumberSemaphore = new Semaphore(1);
    private static Semaphore deliCounter = new Semaphore(TOTAL_ASSOCIATES);
    private static AtomicInteger nextTicket = new AtomicInteger(1);
    private static AtomicInteger nowServing = new AtomicInteger(1);

    public static void main(String[] args) {
        int numberOfCustomers = Integer.MAX_VALUE;
        nextTicket.set(1);
        ExecutorService executorService = Executors.newFixedThreadPool(TOTAL_ASSOCIATES * 10);

        for (int i = 1; i <= numberOfCustomers; i++) {
            executorService.execute(new Customer());
        }
        executorService.shutdown();
    }

    static class Customer implements Runnable {
        private int ticketNumber;

        @Override
        public void run() {
            try {
                Thread.sleep((int) (Math.random() * 500));
                enterDeli();
                getServiced();
                leaveStore();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        private void enterDeli() throws InterruptedException {
            ticketNumberSemaphore.acquire();
            this.ticketNumber = nextTicket.getAndIncrement();
            System.out.println("Customer with ticket number " + this.ticketNumber + " entered the deli.");
            ticketNumberSemaphore.release();
        }

        private void getServiced() throws InterruptedException {
            if (deliCounter.availablePermits() == 0) {
                System.out.println("Customer with ticket number " + this.ticketNumber + " is waiting.");
            }
            deliCounter.acquire();
            System.out.println("Customer with ticket number " + this.ticketNumber + " is being serviced.");
            System.out.println("Ticket " + nowServing.incrementAndGet() + " is now up next!\nAn associate is waiting for the next customer in line.");
            Thread.sleep((int) (Math.random() * 500));
        }

        private void leaveStore() {
            System.out.println("Customer with ticket number " + this.ticketNumber + " left the deli.");
            deliCounter.release();
        }
    }
}