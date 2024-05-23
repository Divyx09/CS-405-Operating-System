import java.util.*;

public class RoundRobin {
    public static void main(String[] args) {
        int n, quantum;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the number of processes:");
        n = in.nextInt();
        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            System.out.println("Enter the arrival time and burst time for process " + (i + 1));
            p[i].arrivalTime = in.nextInt();
            p[i].burstTime = in.nextInt();
        }
        System.out.println("Enter the time quantum:");
        quantum = in.nextInt();
        roundRobin(p, n, quantum);
    }

    public static void roundRobin(Process[] p, int n, int quantum) {
        int i, time = 0, totalWaitingTime = 0, totalTurnAroundTime = 0;
        boolean[] executed = new boolean[n];
        while (true) {
            boolean allExecuted = true;
            for (i = 0; i < n; i++) {
                if (!executed[i] && p[i].arrivalTime <= time) {
                    allExecuted = false;
                    if (p[i].burstTime <= quantum) {
                        time += p[i].burstTime;
                        totalTurnAroundTime += time - p[i].arrivalTime;
                        totalWaitingTime += time - p[i].arrivalTime - p[i].burstTime;
                        executed[i] = true;
                    } else {
                        p[i].burstTime -= quantum;
                        time += quantum;
                    }
                }
            }
            if (allExecuted) {
                break;
            }
        }
        System.out.println("Total Waiting Time: " + totalWaitingTime);
        System.out.println("Total Turn Around Time: " + totalTurnAroundTime);
        System.out.println("Average Waiting Time: " + (double)totalWaitingTime / n);
        System.out.println("Average Turn Around Time: " + (double)totalTurnAroundTime / n);
    }
}

class Process {
    int arrivalTime, burstTime;
}