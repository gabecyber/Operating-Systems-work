import java.util.concurrent.Semaphore;

class Counter {
    private static final int NUM_ASSOCIATES = 3;

    private final Semaphore associateSemaphore = new Semaphore(NUM_ASSOCIATES, true);
    private final Semaphore customerSemaphore = new Semaphore(1, true);
    private final Semaphore queueSemaphore = new Semaphore(1, true);
    private int ticketNumber = 1;
    private int customerCount = 0;
    private int customersInQueue = 0;
    private int availableAssociates = NUM_ASSOCIATES;

    private void customer_entry() throws InterruptedException {
        customerSemaphore.acquire();
        int currentTicket = ticketNumber++;
        System.out.println("Customer with ticket number " + currentTicket + " entered the deli.");
        customerCount++;
        customerSemaphore.release();

        try {
            queueSemaphore.acquire();
            if (associateSemaphore.availablePermits() == 0) {
                customersInQueue++;
                System.out.println("Customer with ticket number " + currentTicket + " is waiting in the queue.");
                System.out.println("Number of customers in queue: " + customersInQueue);
            }
            queueSemaphore.release();

            associateSemaphore.acquire();
            queueSemaphore.acquire();
            if (customersInQueue > 0) {
                customersInQueue--;
                System.out.println("Number of customers in queue: " + customersInQueue);
            }
            queueSemaphore.release();

            availableAssociates--;
            System.out.println("Customer with ticket number " + currentTicket + " is being served.");
            System.out.println("Available associates: " + availableAssociates);

            // Simulate variable service time
            Thread.sleep((long) (Math.random() * 1000));

            associateSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void customer_leave() throws InterruptedException {
        customerSemaphore.acquire();
        int currentCustomer = customerCount--;
        System.out.println("Customer with ticket number " + currentCustomer + " is leaving the deli.");
        customerSemaphore.release();

        availableAssociates++;
        System.out.println("Available associates: " + availableAssociates);
    }

    public void simulate() {
        Thread[] customers = new Thread[10]; // Number of customers to simulate

        for (int i = 0; i < customers.length; i++) {
            final int customerNumber = i + 1;
            customers[i] = new Thread(() -> {
                try {
                    customer_entry();
                    customer_leave();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            customers[i].start();
            try {
                Thread.sleep((long) (Math.random() * 400)); // Simulate variable arrival time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Thread customer : customers) {
            try {
                customer.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class a5tester {
    public static void main(String[] args) {
        Counter deliCounter = new Counter();
        deliCounter.simulate();
    }
}

