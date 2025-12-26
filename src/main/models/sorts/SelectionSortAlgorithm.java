package main.models.sorts;

import java.util.*;

public class SelectionSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();

    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            descriptions.add("Starting pass " + (i + 1) + ", assuming element at index " + i + " (" + arr[i] + ") is minimum");
            history.add(arr.clone());
            compares.add(new int[]{i, -1});

            for (int j = i + 1; j < n; j++) {
                descriptions.add("Comparing " + arr[j] + " with current minimum " + arr[minIndex]);
                compares.add(new int[]{minIndex, j});
                history.add(arr.clone());
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                    descriptions.add("Found new minimum: " + arr[minIndex] + " at index " + minIndex);
                    compares.add(new int[]{minIndex, j});
                    history.add(arr.clone());
                }
            }

            if (minIndex != i) {
                int temp = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = temp;
                descriptions.add("Swapping " + arr[minIndex] + " and " + arr[i]);
                compares.add(new int[]{i, minIndex});
                history.add(arr.clone());
            } else {
                descriptions.add("No swap needed for index " + i);
                compares.add(new int[]{i, minIndex});
                history.add(arr.clone());
            }
        }

        descriptions.add("Array is fully sorted!");
        compares.add(new int[]{-1, -1});
        history.add(arr.clone());
    }

    public List<int[]> getHistory() { return history; }
    public List<String> getDescriptions() { return descriptions; }
    public List<int[]> getCompares() { return compares; }

    public void clear() {
        history.clear();
        descriptions.clear();
        compares.clear();
    }
}