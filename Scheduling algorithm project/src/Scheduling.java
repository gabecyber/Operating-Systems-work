


import java.util.ArrayList;

import java.util.Random;
import java.util.Scanner;
public class Scheduling {
	static void findWaitingTime(int processes[], int n,
            int bt[], int wt[] )
{
		int quantum = 25;
   // Make a copy of burst times bt[] to store remaining
   // burst times.
   int rem_bt[] = new int[n];
   for (int i = 0 ; i < n ; i++)
       rem_bt[i] =  bt[i];
 
   int t = 0; // Current time
 
   // Keep traversing processes in round robin manner
   // until all of them are not done.
   while(true)
   {
       boolean done = true;
 
       // Traverse all processes one by one repeatedly
       for (int i = 0 ; i < n; i++)
       {
           // If burst time of a process is greater than 0
           // then only need to process further
           if (rem_bt[i] > 0)
           {
               done = false; // There is a pending process
 
               if (rem_bt[i] > quantum)
               {
                   // Increase the value of t i.e. shows
                   // how much time a process has been processed
                   t += quantum;
 
                   // Decrease the burst_time of current process
                   // by quantum
                   rem_bt[i] -= quantum;
               }
 
               // If burst time is smaller than or equal to
               // quantum. Last cycle for this process
               else
               {
                   // Increase the value of t i.e. shows
                   // how much time a process has been processed
                   t = t + rem_bt[i];
 
                   // Waiting time is current time minus time
                   // used by this process
                   wt[i] = t - bt[i];
 
                   // As the process gets fully executed
                   // make its remaining burst time = 0
                   rem_bt[i] = 0;
               }
           }
       }

       if (done == true)
         break;
   }
}
	public static void sort(ArrayList<Proc> list) {
		
		list.sort((o1,o2) -> o1.getId().compareTo(o2.getId()));
	}
public static void main(String[] args) {
	ArrayList<Proc> pr = new ArrayList<Proc>();
	ArrayList<Proc> pr1 = new ArrayList<Proc>();
	ArrayList<Proc> pr2 = new ArrayList<Proc>();
	ArrayList<Proc> pr3 = new ArrayList<Proc>();
	int count=0;
	Random rn = new Random();
	boolean unique=false;
	int num=0;
	for(int i=0;i<5;i++)
	{
		unique=false;
		Proc p1=new Proc();
		while(!unique)
		{
			num=rn.nextInt(11);
			if(i==0)
				break;
			for(int j=0;j<i;j++)
			{
				if(num==pr.get(j).id)
				{


					unique=false;
					break;
				}
				else
					unique=true;
			}
		}
		p1.id=num;
		p1.burstLength=(int) Math.floor(Math.random() *  (100-20 + 1) + 20);
		p1.priority=(int) Math.floor(Math.random() *  (10-1 + 1) + 1);
		pr.add(p1);
		pr2.add(p1);
		count++;
		sort(pr);
	}
	System.out.println("Initial Snapshot");
	
	System.out.println("Process ID | Priority | Burst-length");
	for(int i=0;i<count;i++)
	{
		System.out.println(pr.get(i).id+" "+pr.get(i).priority+" "+pr.get(i).burstLength);
	}

	
	
		System.out.println("Add a new Process" );
		Scanner n = new Scanner(System.in);
		
		

			Proc p1=new Proc();
			unique=false;

			while(!unique)
			{
				
				System.out.println("Enter process id in range [0,10]: " );
				num=n.nextInt();
				if(num < 11 & num >= 0) {
				for(int j=0;j<count;j++)
				{
					if(num==pr.get(j).id)
					{ 
						unique=false;
						break;
					}
					else {
						unique=true;
						
					}
					
				}
				}
				
			}
			p1.id=num;
			boolean inrange = false; 
			while(!inrange)
			{
				
				System.out.println("Enter process burst length in range[20,100]: " );
				p1.burstLength=n.nextInt();
				if(p1.burstLength < 101 & p1.burstLength >= 20) {
				inrange = true;
				}
				
			}
			inrange = false; 
			while(!inrange)
			{
				
				System.out.println("Enter process priority in range[0, 10]: " );
				p1.priority=n.nextInt();
				if(p1.priority < 11 & p1.priority >= 0) {
				inrange = true;
				}
				
			}
			pr2.add(p1);
			sort(pr2);
			pr.add(p1);
			sort(pr);
			count++;
			n.close();
		

	System.out.println("Updated Snapshot");
	System.out.println("Process ID | Priority | Burst-length");
	for(int i=0;i<count;i++)
	{
		System.out.println(pr.get(i).id+" "+pr.get(i).priority+" "+pr.get(i).burstLength);
	}
	
	
	
	
	int max=100;
	 int ind=0;
	int time=0;
	

	// SJF scheduling
	
//	while(pr.size()!=0)
//	{
//		max=100;
//		for(int i=0;i<count;i++)
//		{
//			if(pr.get(i).priority<max)
//			{
//				max=pr.get(i).priority;
//				ind=i;
//			}
//			else if(pr.get(i).priority==max)
//			{
//				if(pr.get(i).burstLength<pr.get(ind).burstLength)
//				{
//					ind=i;
//				}
//
//			}
//
//		}
//		pr.get(ind).TWT=time-0;
//		time=time+pr.get(ind).burstLength;
//		pr1.add(pr.get(ind));
//		pr.remove(ind);
//		count--;
//	}

	count = 0;
	int qt;
	int bt;
	qt = 25;
	while(pr.size()!= 0)  
	{  
		for ( int i=0;i<pr.size();i++)  
		{  
			 bt = pr.get(i).burstLength;
			if( bt== 0)  
			{  
				count++;  
				  
			}  
			if(bt>qt)  
				pr.get(i).burstLength= bt - qt;  
			else  
				if(pr.get(i).burstLength>=0)  
				{  
					  
					pr.get(i).burstLength = 0;
					
				}  
			
			
		}  
		if(pr.size() == count) {  
			break;
			}  
		
			pr.get(ind).TWT=time-0; 
			time=time+pr.get(ind).burstLength;
			pr1.add(pr.get(ind));
			pr.remove(ind);
			
		}
	
//priority scheduling
	float awt;
	float total=0;
	for(int i=0;i<pr1.size();i++)
	{
		total=total+pr1.get(i).TWT;
	}
	awt=total/pr1.size();
	time=0;
	ind=0;
	while(pr2.size()!=0)
	{
		max=100;
		for(int i=0;i<pr2.size();i++)
		{
			if(pr2.get(i).priority<max)
			{
				max=pr2.get(i).priority;
				ind=i;
			}
			else if(pr2.get(i).priority==max)
			{
				if(pr2.get(i).id<pr2.get(ind).id)
				{
					ind=i;
				}

			}

		}
		pr2.get(ind).TWT=time-0;
		time=time+pr2.get(ind).burstLength;
		pr3.add(pr2.get(ind));
		pr2.remove(ind);
	}

	float awt1;
	float total1=0;
	for(int i=0;i<pr3.size();i++)
	{
		total1=total1+pr3.get(i).TWT;
	}
	awt1=total1/pr3.size();

	sort(pr1);
	sort(pr3);
	if(awt1>awt)
	{
		System.out.println("Scheduling algorithm | Process ID | Priority | Burst-length |  Total waiting time");
		System.out.println("Round Robin ");
		for(int i=0;i<pr1.size();i++)
		{
			System.out.println(pr1.get(i).id+" "+pr1.get(i).priority+" "+pr1.get(i).burstLength+" "+" "+pr1.get(i).TWT);
		}
		System.out.println("The average waiting time of SJF is "+ awt);
		
		System.out.println("Scheduling algorithm | Process ID | Priority | Burst-length |  Total waiting time");
		System.out.println("Priority ");
		for(int i=0;i<pr3.size();i++)
		{
			System.out.println( pr3.get(i).id+" "+pr3.get(i).priority+" "+pr3.get(i).burstLength+" "+" "+pr3.get(i).TWT);
		}
		System.out.println("The average waiting time of priority scheduling is "+ awt1);
	}
	else
	{
		
		System.out.println("Scheduling algorithm | Process ID | Priority | Burst-length |  Total waiting time");
		System.out.println("Priority ");
		for(int i=0;i<pr3.size();i++)
		{
			System.out.println( pr3.get(i).id+" "+pr3.get(i).priority+" "+pr3.get(i).burstLength+" "+" "+pr3.get(i).TWT);
		}
		System.out.println("The average waiting time of priority scheduling is "+ awt1);

		System.out.println("Scheduling algorithm | Process ID | Priority | Burst-length |  Total waiting time");
		System.out.println("Round Robin ");
		for(int i=0;i<pr1.size();i++)
		{
			System.out.println(pr1.get(i).id+" "+pr1.get(i).priority+" "+pr1.get(i).burstLength+" "+" "+pr1.get(i).TWT);
		}
		System.out.println("The average waiting time of SJF is "+ awt);
	}
	System.out.println("Average waiting time | Algorithm");
	System.out.println("SJF");
	System.out.println("Priority");
	System.out.println("Round Robin");
}


	static class Proc {

		int id;
		int burstLength;
		int priority;
		int TWT;
		
		Proc(){
			
		}

		void setId(Integer Id){

			id=Id;

		}

		Integer getId(){

			return id;

		}

		void setburstLength(int burstlen){

			burstLength=burstlen;

		}

		int getburstLength(){

			return burstLength;

		}

		void setpriority(int pri){

			priority=pri;

		}

		int getpriority(){

			return priority;

		}

	}
}
