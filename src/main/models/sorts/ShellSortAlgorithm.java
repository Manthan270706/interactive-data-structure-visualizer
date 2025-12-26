package main.models.sorts;

import java.util.*;

public class ShellSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();

    public void sort(int[] arr) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            descriptions.add("Starting gap = " + gap);
            history.add(arr.clone());
            compares.add(new int[]{-1, -1});

            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;

                descriptions.add("Considering element " + temp + " at index " + i);
                history.add(arr.clone());
                compares.add(new int[]{i, -1});

                boolean swapped = false;
                while (j >= gap && arr[j - gap] > temp) {
                    descriptions.add("Comparing " + arr[j - gap] + " and " + temp + " → swapping");
                    compares.add(new int[]{j - gap, j});
                    int t = arr[j];
                    arr[j] = arr[j - gap];
                    arr[j - gap] = t;
                    history.add(arr.clone());
                    j -= gap;
                    swapped = true;
                }

                if (j >= gap && !swapped) {
                    descriptions.add("Comparing " + arr[j - gap] + " and " + temp + " → no swap needed");
                    compares.add(new int[]{j - gap, j});
                    history.add(arr.clone());
                }

                arr[j] = temp;
                history.add(arr.clone());
                descriptions.add("Placed " + temp + " at position " + j);
                compares.add(new int[]{j, i});
            }
        }

        descriptions.add("Array is sorted!");
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