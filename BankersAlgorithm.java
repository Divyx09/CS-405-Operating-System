
import java.util.Arrays;

public class BankersAlgorithm {

    private static boolean isSafeState(int[][] allocation, int[][] max, int[] available) {
        int processes = allocation.length;
        int resourcesCount = available.length;
        int[] work = Arrays.copyOf(available, resourcesCount);
        boolean[] finished = new boolean[processes];

        int count = 0;
        while (count < processes) {
            boolean found = false;
            for (int i = 0; i < processes; i++) {
                if (!finished[i]) {
                    int j;
                    for (j = 0; j < resourcesCount; j++) {
                        if (max[i][j] - allocation[i][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == resourcesCount) {
                        for (int k = 0; k < resourcesCount; k++) {
                            work[k] += allocation[i][k];
                        }
                        found = true;
                        finished[i] = true;
                        count++;
                    }
                }
            }
            if (!found) {
                return false; // Unsafe state

                    }}
        return true; // Safe state
    }

    public static void main(String[] args) {
        int[][] allocation = {
            {0, 1, 0},
            {2, 0, 0},
            {3, 0, 2},
            {2, 1, 1},
            {0, 0, 2}
        };
        int[][] max = {
            {7, 5, 3},
            {3, 2, 2},
            {9, 0, 2},
            {2, 2, 2},
            {4, 3, 3}
        };
        int[] available = {3, 3, 2};

        if (isSafeState(allocation, max, available)) {
            System.out.println("The system is in a safe state.");
        } else {
            System.out.println("The system is in an unsafe state.");
        }
    }
}
