package Scheduling;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Scheduling {
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
			num=rn.nextInt(10) + 1;
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
		p1.burstLength=rn.nextInt(10) + 10;
		p1.priority=rn.nextInt(10) + 1;
		pr.add(p1);
		pr2.add(p1);
		count++;
	}
	System.out.println("Process ID | Priority | Burst-length");
	for(int i=0;i<count;i++)
	{
		System.out.println(pr.get(i).id+" "+pr.get(i).priority+" "+pr.get(i).burstLength);
	}

	boolean cont=true;
	while(cont)
	{
		System.out.println("Do you want to add one more process true/false" );
		Scanner n = new Scanner(System.in);
		cont = n.nextBoolean();
		if(cont)
		{

			Proc p1=new Proc();
			unique=false;

			while(!unique)
			{
				System.out.println("Enter process id" );
				num=n.nextInt();
				for(int j=0;j<count;j++)
				{
					if(num==pr.get(j).id)
					{ unique=false;
					break;
					}
					else
						unique=true;
				}
			}
			p1.id=num;
			System.out.println("Enter process burst length" );
			p1.burstLength=n.nextInt();
			System.out.println("Enter process priority" );
			p1.priority=n.nextInt();
			pr2.add(p1);
			pr.add(p1);
			count++;
			
		}
		else
			break;

	}
	System.out.println("Process ID | Priority | Burst-length");
	for(int i=0;i<count;i++)
	{
		System.out.println(pr.get(i).id+" "+pr.get(i).priority+" "+pr.get(i).burstLength);
	}
	int max=100;
	int ind=0;
	int time=0;
	while(pr.size()!=0)
	{
		max=100;
		for(int i=0;i<count;i++)
		{
			if(pr.get(i).priority<max)
			{
				max=pr.get(i).priority;
				ind=i;
			}
			else if(pr.get(i).priority==max)
			{
				if(pr.get(i).burstLength<pr.get(ind).burstLength)
				{
					ind=i;
				}

			}

		}
		pr.get(ind).TWT=time-0;
		time=time+pr.get(ind).burstLength;
		pr1.add(pr.get(ind));
		pr.remove(ind);
		count--;
	}
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
System.out.println("The avearge waiting time of SJF is "+ awt);
System.out.println("The avearge waiting time of priority scheduling is "+ awt1);
System.out.println("Process ID | Priority | Burst-length | Scheduling algorithm | Total waiting time");
if(awt1>awt)
{

for(int i=0;i<pr1.size();i++)
{
System.out.println(pr1.get(i).id+" "+pr1.get(i).priority+" "+pr1.get(i).burstLength+" "+"SJF"+" "+pr1.get(i).TWT);
}
for(int i=0;i<pr3.size();i++)
{
System.out.println(pr3.get(i).id+" "+pr3.get(i).priority+" "+pr3.get(i).burstLength+" "+"Priority"+" "+pr3.get(i).TWT);
}
}
else
{
for(int i=0;i<pr3.size();i++)
{
System.out.println(pr3.get(i).id+" "+pr3.get(i).priority+" "+pr3.get(i).burstLength+" "+"Priority"+" "+pr3.get(i).TWT);
}

for(int i=0;i<pr1.size();i++)
{
System.out.println(pr1.get(i).id+" "+pr1.get(i).priority+" "+pr1.get(i).burstLength+" "+"SJF"+" "+pr1.get(i).TWT);
}
}
}


	static class Proc {

		int id;

		int burstLength;

		int priority;

		int TWT;

		Proc(){

		}

		void setId(int Id){

			id=Id;

		}

		int getId(){

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
