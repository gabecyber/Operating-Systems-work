import java.util.*;
import java.util.Arrays;


public class a3Interiano {
	static double median;
	static double mean;
	static int max;
	static int min;
	static double StandardDiv;
	static int[] arrayGen(int inputnumber) {
	Random rd = new Random(); // creating Random object
	int[] arr = new int[inputnumber];
	for(int i = 0;i<arr.length;i++)
	{
		arr[i] = rd.nextInt(100); // storing random integers in an array
		
		
	}
	return arr;
	}
	   public static void main(String[] args){
		      int inputnumber = Integer.parseInt(args[0]);
		  
		      
		      System.out.println("\nThis is: " + Thread.currentThread().getName()); 
		      //The Thread.currentThread().getName() indicates the thread identity
		      
			 int[] GenNumbers = arrayGen(inputnumber);
			 System.out.println(inputnumber + " Numbers Generated: ");
			 for(int i = 0;i<GenNumbers.length;i++)
				{
				 System.out.print(GenNumbers[i] +" ");
					
					
				}
			 Thread t1 = new Thread(new GetMedian(GenNumbers));
			 Thread t2 = new Thread(new GetStandardDiv(GenNumbers));
			 Thread t3 = new Thread(new GetMax(GenNumbers));
			 Thread t4 = new Thread(new GetMin(GenNumbers));
			try {
				t1.start();
			    t2.start();
			    t3.start();
			    t4.start();
			}
			catch(Exception e) {
				System.out.print(e);
			}
			try {
	    
			    t1.join();
				t2.join();
				t3.join();
				t4.join();
			}
			catch(Exception e) {
				System.out.print(e);
			}
			System.out.print("\nMax: " + max);
			System.out.print("\nMin: " + min);
			System.out.print("\nMedian: " + median);
			System.out.print("\nMean: " + mean);
			System.out.print("\nStandard Deviation: " + StandardDiv);
			System.out.print("\nending main");
			
}
	   

static class GetMedian implements Runnable{ 
int[] GenNumbers;

public GetMedian(int[] GenNumbers){ 
	this.GenNumbers = GenNumbers;
	
	}

public void run(){
	System.out.println("\nThis is: " + Thread.currentThread().getName() + "\nCalculating Median"); 

	Arrays.sort(GenNumbers);
	
	if (GenNumbers.length % 2 == 0) {
	    median = ((double)GenNumbers[GenNumbers.length/2] + (double)GenNumbers[GenNumbers.length/2 - 1])/2;
	}
	else {
	    median = (double) GenNumbers[GenNumbers.length/2];
	    
	}
	
}
}
static class GetMean implements Runnable{ 
int[] GenNumbers;

public GetMean(int[] GenNumbers){ 
	this.GenNumbers = GenNumbers;
	
	}

public void run(){
	double sum = 0.0;
	System.out.println("\nThis is: " + Thread.currentThread().getName() + "\nCalculating Mean"); 

	 for(int i = 0; i < GenNumbers.length; i++) {
		 sum += GenNumbers[i];
		
      }
	 mean = sum / GenNumbers.length;
	}
	
}
static class GetMax implements Runnable{ 
int[] GenNumbers;

public GetMax(int[] GenNumbers){ 
	this.GenNumbers = GenNumbers;
	
	}

public void run(){
	
	System.out.println("\nThis is: " + Thread.currentThread().getName()+ "\nCalculating Max"); 
	
	 max = Arrays.stream(GenNumbers).max().getAsInt();
	    
	}
	
}
static class GetMin implements Runnable{ 
int[] GenNumbers;

public GetMin(int[] GenNumbers){ 
	this.GenNumbers = GenNumbers;
	
	}

public void run(){
	
	System.out.println("\nThis is: " + Thread.currentThread().getName() +"\nCalculating Min"); 
	
	 min = Arrays.stream(GenNumbers).min().getAsInt();
	    
	}
	
}
static class GetStandardDiv implements Runnable{ 
int[] GenNumbers;

public GetStandardDiv(int[] GenNumbers){ 
	this.GenNumbers = GenNumbers;
	
	}
public void run(){
	
	System.out.println("\nThis is: " + Thread.currentThread().getName() +"\nCalculating Standard Deviation");
	
	Thread t2 = new Thread(new GetMean(GenNumbers));
	double temp = 0.0;
	try {
		
	    t2.start();
	    
	}
	catch(Exception e) {
		System.out.print(e);
	}
try {
	    t2.join();
	    
	}
	catch(Exception e) {
		System.out.print(e);
	}
	 for(int i = 0; i< GenNumbers.length; i++) {
		temp += Math.pow(GenNumbers[i]-mean,2);
		 
	 }
	    StandardDiv = Math.sqrt(temp/GenNumbers.length);
	}
	
}
}




