// Gabriel Interiano 
//CS4345 Operating Systems 
//summer semester
//assignment 4
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Comparator;

class Process implements Comparable<Process> {
    int pid;
    int priority;
    int burstLength;
    int waitingTime;

    public Process(int pid, int priority, int burstLength) {
        this.pid = pid;
        this.priority = priority;
        this.burstLength = burstLength;
        this.waitingTime = 0;
    }

    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.pid, other.pid);
    }
}

public class a4Interiano {
    public static void main(String[] args) {
        ArrayList<Process> processes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        
        System.out.println("Initial Snapshot:");
        System.out.println("Process id | Priority | Burst-length");
        generateRandomProcesses(processes, 5);
        displaySnapshot(processes);

        enterNewProcess(processes, scanner);

        System.out.println("Updated Snapshot:");
        System.out.println("Process id | Priority | Burst-length");
        displaySnapshot(processes);
        System.out.println("Scheduling Algorithm | Process id | Priority | Burst-length | Total waiting Time");
        nonPreemptivePriority(processes);
        displayTotalWaitingTime(processes);
       
        
        
        System.out.println("Scheduling Algorithm | Process id | Priority | Burst-length | current Turnaround Time| Total Waiting Time");
        roundRobin(processes);
       
        
    }

    // Function to generate random processes for initial ready queue
    private static void generateRandomProcesses(ArrayList<Process> processes, int numProcesses) {
        Set<Integer> usedIds = new HashSet<>();
        for (int i = 0; i < numProcesses; i++) {
            int pid;
            do {
                pid = (int) (Math.random() * 11); // Random value between 0 and 10
            } while (usedIds.contains(pid));

            usedIds.add(pid);
            int priority = (int) (Math.random() * 10) + 1;
            int burstLength = (int) (Math.random() * 81) + 20; // Random value between 20 and 100
            processes.add(new Process(pid, priority, burstLength));
        }
        Collections.sort(processes);
    }

    // Function to display the snapshot of the system with processes sorted by pid
    private static void displaySnapshot(ArrayList<Process> processes) {
        
        Collections.sort(processes);

       
        for (Process process : processes) {
            System.out.printf("%10d | %8d | %12d%n", process.pid, process.priority, process.burstLength);
        }
    }

    
    private static boolean isValidId(ArrayList<Process> processes, int pid) {
        return pid >= 0 && pid <= 10 && processes.stream().noneMatch(process -> process.pid == pid);
    }

   
    private static boolean isValidBurstLength(int burstLength) {
        return burstLength >= 20 && burstLength <= 100;
    }

    
    private static boolean isValidPriority(int priority) {
        return priority >= 1 && priority <= 10;
    }

    // Function to allow the user to enter attributes of a new process
    private static void enterNewProcess(ArrayList<Process> processes, Scanner scanner) {
        int pid, priority, burstLength;

        while (true) {
            System.out.print("Enter process ID (0-10): ");
            pid = scanner.nextInt();
            if (isValidId(processes, pid)) {
                break;
            } else {
                System.out.println("Invalid ID! Please enter a unique ID between 0 and 10.");
            }
        }

        while (true) {
            System.out.print("Enter burst length (20-100): ");
            burstLength = scanner.nextInt();
            if (isValidBurstLength(burstLength)) {
                break;
            } else {
                System.out.println("Invalid burst length! Please enter a value between 20 and 100.");
            }
        }

        while (true) {
            System.out.print("Enter priority (1-10): ");
            priority = scanner.nextInt();
            if (isValidPriority(priority)) {
                break;
            } else {
                System.out.println("Invalid priority! Please enter a value between 1 and 10.");
            }
        }

       
        int index = 0;
        while (index < processes.size() && processes.get(index).pid < pid) {
            index++;
        }

        
        processes.add(index, new Process(pid, priority, burstLength));
    }

    // Function to calculate individual waiting time for each process using Non-preemptive priority algorithm
    private static void nonPreemptivePriority(ArrayList<Process> processes) {
        int currentTime = 0;
        processes.sort(Comparator.comparingInt(process -> process.priority));
        for (Process process : processes) {
            process.waitingTime = currentTime;
            currentTime += process.burstLength;
        }
        
    }

    private static void roundRobin(ArrayList<Process> processes) {
        int quantum = 25;
        int currentTime = 0;
        Collections.sort(processes);
        System.out.println("Round Robin");
        ArrayList<Process> remainingProcesses = new ArrayList<>(processes);
        ArrayList<Integer> burstTimes = new ArrayList<>();
        for( int i = 0; i < processes.size();i++) {
        burstTimes.add(processes.get(i).burstLength);
        }
        
        
        while (!remainingProcesses.isEmpty()) {
            Process currentProcess = remainingProcesses.get(0);
            Integer selectedBurst = burstTimes.get(0);
            
            if (currentProcess.burstLength <= quantum) {
                remainingProcesses.remove(0);
                burstTimes.remove(0);
                 // Update waiting time for completed process
                currentTime += currentProcess.burstLength;
                currentProcess.waitingTime = currentTime-selectedBurst;
                System.out.printf("%33d | %8d | %12d| %24d| %12d%n", currentProcess.pid, currentProcess.priority, currentProcess.burstLength, currentTime, currentProcess.waitingTime);
            } else {
                currentProcess.burstLength -= quantum;
                currentTime += quantum;
                currentProcess.waitingTime = currentTime - quantum;
                Integer temp = selectedBurst;
                // Move the current process to the end of the queue
                burstTimes.remove(0);
                burstTimes.add(temp);
                remainingProcesses.remove(0);
                remainingProcesses.add(currentProcess);
                System.out.printf("%33d | %8d | %12d| %24d|%n", currentProcess.pid, currentProcess.priority, currentProcess.burstLength, currentTime);
            }

            
        }
        
        System.out.println("Average Waiting Time:");
        System.out.println("Round Robin: " + calculateAverageWaitingTime(processes));
    }


    // Function to calculate the average waiting time for the processes
    private static double calculateAverageWaitingTime(ArrayList<Process> processes) {
        int totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.waitingTime;
        }
        return (double) totalWaitingTime / processes.size();
    }

    // Function to display the total waiting time for each process
    private static void displayTotalWaitingTime(ArrayList<Process> processes) {
    	System.out.println("Non-Preemptive Priority");
        for (Process process : processes) {
        	System.out.printf("%33d | %8d | %12d| %15d%n", process.pid, process.priority, process.burstLength, process.waitingTime);
        }
        System.out.println("Average Waiting Time:");
        System.out.println("Non-preemptive Priority: " + calculateAverageWaitingTime(processes));
    }
}


