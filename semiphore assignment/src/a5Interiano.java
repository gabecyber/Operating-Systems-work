// Gabriel Interiano
// CS4345 (Operating Systems
// Summer 2023
// Assignment 5
import java.util.concurrent.Semaphore;

class DeliCounter {
    private static final int NUM_ASSOCIATES = 3;

    private final Semaphore associateSemaphore = new Semaphore(NUM_ASSOCIATES, true);
    private final Semaphore queueSemaphore = new Semaphore(1, true);
    private int ticketNumber = 1;
   
    private int customersInQueue = 0;
    private int availableAssociates = NUM_ASSOCIATES;
    private int servedCustomers = 0;

    private void customer_entry() throws InterruptedException {

        int currentTicket = ticketNumber++;
        System.out.println("Customer with ticket number " + currentTicket + " entered the deli.");
     

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

        int currentCustomer = ++servedCustomers;

        System.out.println("Customer with ticket number " + currentCustomer + " is leaving the deli.");
        availableAssociates++;
        System.out.println("Available associates: " + availableAssociates);
    }

    public void simulate() {
        Thread workerCustomers = new Thread(makeCustomers);
        workerCustomers.setName("makeCustomers");
        workerCustomers.start();
    }

    Runnable makeCustomers = new Runnable() {
        public void run() {
            int rand;

            
            while(true) {
                rand = (int) (Math.random()*500);
                try {
                    Thread NT = new Thread(() -> {
                        try {
                            customer_entry();
                            customer_leave();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                   
                    NT.start();
                    Thread.sleep(rand);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    };
}

public class a5Interiano {
    public static void main(String[] args) {
        DeliCounter deliCounter = new DeliCounter();
        deliCounter.simulate();
    }
}
