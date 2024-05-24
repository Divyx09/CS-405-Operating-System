import java.util.Arrays;

class Process implements Comparable<Process> {
    int id, burstTime, priority, arrivalTime, waitingTime = 0, turnaroundTime = 0;

    public Process(int id, int burstTime, int priority, int arrivalTime) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public int compareTo(Process other) {
        return Integer.compare(this.arrivalTime, other.arrivalTime);
    }
}

public class CPUSchedulingComparison {
    public static void main(String[] args) {
        Process[] processes = {
            new Process(1, 6, 2, 1), 
            new Process(2, 8, 3, 1),
            new Process(3, 7, 1, 2),
            new Process(4, 3, 4, 3)
        };
        int quantum = 4;

        scheduleAndPrint("FCFS", processes.clone(), CPUSchedulingComparison::FCFS);
        scheduleAndPrint("SJF", processes.clone(), CPUSchedulingComparison::SJF);
        scheduleAndPrint("Priority", processes.clone(), CPUSchedulingComparison::PriorityScheduling);
        scheduleAndPrint("Round Robin", processes.clone(), p -> RoundRobin(p, quantum));
    }

    @FunctionalInterface
    interface Scheduler {
        void schedule(Process[] processes);
    }

    public static void scheduleAndPrint(String name, Process[] processes, Scheduler scheduler) {
        System.out.println(name + ":");
        scheduler.schedule(processes);
        printMetrics(processes);
    }

    public static void FCFS(Process[] p) {
        Arrays.sort(p);
        int currentTime = 0;
        for (Process process : p) {
            if (currentTime < process.arrivalTime) currentTime = process.arrivalTime;
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = currentTime - process.arrivalTime;
        }
    }

    public static void SJF(Process[] p) {
        Arrays.sort(p, (a, b) -> a.burstTime - b.burstTime);
        int currentTime = 0;
        for (Process process : p) {
            if (currentTime < process.arrivalTime) currentTime = process.arrivalTime;
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = currentTime - process.arrivalTime;
        }
    }

    public static void PriorityScheduling(Process[] p) {
        Arrays.sort(p, (a, b) -> a.priority - b.priority);
        int currentTime = 0;
        for (Process process : p) {
            if (currentTime < process.arrivalTime) currentTime = process.arrivalTime;
            process.waitingTime = currentTime - process.arrivalTime;
            currentTime += process.burstTime;
            process.turnaroundTime = currentTime - process.arrivalTime;
        }
    }

    public static void RoundRobin(Process[] p, int quantum) {
        int[] rem_bt = Arrays.stream(p).mapToInt(proc -> proc.burstTime).toArray();
        int completed = 0, time = 0;

        while (completed < p.length) {
            for (int i = 0; i < p.length; i++) {
                if (rem_bt[i] > 0 && p[i].arrivalTime <= time) {
                    int timeSlice = Math.min(quantum, rem_bt[i]);
                    time += timeSlice;
                    rem_bt[i] -= timeSlice;
                    if (rem_bt[i] == 0) {
                        p[i].turnaroundTime = time - p[i].arrivalTime;
                        p[i].waitingTime = p[i].turnaroundTime - p[i].burstTime;
                        completed++;
                    }
                }
            }
            time++;
        }
    }

    public static void printMetrics(Process[] p) {
        int totalWT = 0, totalTAT = 0;
        for (Process process : p) {
            totalWT += process.waitingTime;
            totalTAT += process.turnaroundTime;
            System.out.println("Process " + process.id + " Waiting Time: " + process.waitingTime + " Turnaround Time: " + process.turnaroundTime);
        }
        System.out.println("Average Waiting Time: " + (float) totalWT / p.length + " Average Turnaround Time: " + (float) totalTAT / p.length + "\n");
    }
}
