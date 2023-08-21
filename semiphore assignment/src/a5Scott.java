/*
Needs???
	Starter
		While or For Loop
			Puts a new thread in queue
			Wait for RNG time

	Queue 1 -- Work being done
		New Thread Joins this queue
			If not available, Joins queue 2
		When thread leaves queue, check is a thread is in other queue
			Send signal

	Queue 2 -- Waiting in line - Should be FIFO - Is this necessary?
		New Thread Joins this queue if other Queue 1 is full
			When signaled, join Queue 1
			
	Worker
		Waits for Queue 1 to have thread
			Wait for RNG time
			Signal complete
*/

import java.util.concurrent.*;

public class a5Scott {	
	public static void main(String[] args){
		Clerks period = new Clerks();
			period.ThirtySeven();
	}
}

 class Clerks {	
		
	final int max = 3;
	Semaphore clerksSem = new Semaphore(max, true);
	Semaphore statusCk = new Semaphore(1, true);
	int clerksAvail = 3;

	Runnable newCustomer = new Runnable() {
		public void run() {
			
			int rand = (int) (1000 + Math.random()*1000);
			
			System.out.printf("Customer with ticket number %s has entered the deli\n", Thread.currentThread().getName());
			try {
				statusCk.acquire();
				if (clerksSem.availablePermits() == 0) {
					System.out.printf("Customer with ticket number %s is waiting in the queue.\n", Thread.currentThread().getName());
					System.out.printf("Number of customers in queue: %d\n", clerksSem.getQueueLength() + 1);
				}
			}
			catch (InterruptedException e){
				System.out.print(e);
			}
			finally {
				statusCk.release();
			}
			
			try {
				clerksSem.acquire();
				Thread.sleep(100);
				System.out.printf("Customer with ticket number %s is being served.\n", Thread.currentThread().getName());
				Thread.sleep(rand);
				statusCk.acquire();
				
			}
			catch (InterruptedException e){
				System.out.print(e);
			}
			finally{
				clerksSem.release();
				System.out.printf("Customer with ticket number %s is leaving the deli.\n", Thread.currentThread().getName());
				statusCk.release();
			}
		}
	};
    
    Runnable makeCustomers = new Runnable() {
		public void run() {
			int rand;
			int currCount = 0;
			String threadName = "";
			
			while(true) {
				rand = (int) (Math.random()*1000);
				try {
					currCount++;
					Thread NT = new Thread(newCustomer);
					threadName = "" + currCount;
					NT.setName(threadName);
					NT.start();
					Thread.sleep(rand);
					}
				catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	};

	public void ThirtySeven() {
		Thread workerCustomers = new Thread(makeCustomers);
		workerCustomers.setName("makeCustomers");
		workerCustomers.start();
	}
}


	