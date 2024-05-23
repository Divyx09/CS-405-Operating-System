import java.util.Arrays;

class Process implements Comparable<Process> {
    int id;
    int burstTime;
    int priority;
    int arrivalTime;
    int waitingTime;
    int turnaroundTime;

    public Process(int id, int burstTime, int priority, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }

    @Override
    public int compareTo(Process other) {
        return this.arrivalTime - other.arrivalTime;
    }
}

public class CPUSchedulingComparison {
    public static void main(String[] args) {
        Process[] processes = new Process[] {
            new Process(1, 6, 2, 1),
            new Process(2, 8, 3, 1),
            new Process(3, 7, 1, 2),
            new Process(4, 3, 4, 3)
        };
        int n = processes.length;
        int quantum = 4;

        FCFS(processes, n);
        SJF(processes, n);
        PriorityScheduling(processes, n);
        RoundRobin(processes, n, quantum);
    }

    public static void FCFS(Process[] p, int n) {
        System.out.println("FCFS:");
        Arrays.sort(p);
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].arrivalTime) {
                currentTime = p[i].arrivalTime;
            }
            p[i].waitingTime = currentTime - p[i].arrivalTime;
            currentTime += p[i].burstTime;
            p[i].turnaroundTime = currentTime - p[i].arrivalTime;
        }
        printMetrics(p, n);
    }

    public static void SJF(Process[] p, int n) {
        System.out.println("SJF:");
        Arrays.sort(p, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return p1.burstTime - p2.burstTime;
            }
        });
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].arrivalTime) {
                currentTime = p[i].arrivalTime;
            }
            p[i].waitingTime = currentTime - p[i].arrivalTime;
            currentTime += p[i].burstTime;
            p[i].turnaroundTime = currentTime - p[i].arrivalTime;
        }
        printMetrics(p, n);
    }

    public static void PriorityScheduling(Process[] p, int n) {
        System.out.println("Priority:");
        Arrays.sort(p, new Comparator<Process>() {
            public int compare(Process p1, Process p2) {
                return p1.priority - p2.priority;
            }
        });
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].arrivalTime) {
                currentTime = p[i].arrivalTime;
            }
            p[i].waitingTime = currentTime - p[i].arrivalTime;
            currentTime += p[i].burstTime;
            p[i].turnaroundTime = currentTime - p[i].arrivalTime;
        }
        printMetrics(p, n);
    }

    public static void RoundRobin(Process[] p, int n, int quantum) {
        System.out.println("Round Robin:");
        int rem_bt[] = new int[n];
        int completed = 0;
        int time = 0;
        Arrays.fill(rem_bt, 0);
        for (int i = 0; i < n; i++) {
            rem_bt[i] = p[i].burstTime;
        }
        while (completed < n) {
            int flag = 1;
            for (int i = 0; i < n; i++) {
                if (rem_bt[i] > 0 && p[i].arrivalTime <= time) {
                    flag = 0;
                    int timeSlice = (time + quantum) < rem_bt[i] ? quantum : rem_bt[i] - time;
                    time += timeSlice;
                    p[i].waitingTime += time - p[i].arrivalTime - p[i].burstTime;
                    p[i].turnaroundTime = p[i].waitingTime + p[i].burstTime;
                    rem_bt[i] -= timeSlice;
                    if (rem_bt[i] == 0) {
                        completed++;
                    }
                }
            }
            if (flag == 1) {
                time++;
            }
        }
        printMetrics(p, n);
    }

    public static void printMetrics(Process[] p, int n) {
        int total_wt = 0, total_tat = 0;
        for (int i = 0; i < n; i++) {
            total_wt += p[i].waitingTime;
            total_tat += p[i].turnaroundTime;
            System.out.println("Process " + p[i].id + " Waiting Time: " + p[i].waitingTime + " Turnaround Time: " + p[i].turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (float)total_wt / n + " Average Turnaround Time: " + (float)total_tat / n);
        System.out.println();
    }
}