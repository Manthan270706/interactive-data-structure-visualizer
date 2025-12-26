package main.models.sorts;

import java.util.*;

public class BubbleSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();

    public void sort(int[] arr) {
        int n = arr.length;
        for (int pass = 0; pass < n - 1; pass++) {
            for (int idx = 0; idx < n - pass - 1; idx++) {
                String desc = "Comparing " + arr[idx] + " and " + arr[idx + 1];
                if (arr[idx] > arr[idx + 1]) {
                    int tmp = arr[idx];
                    arr[idx] = arr[idx + 1];
                    arr[idx + 1] = tmp;
                    desc += " → Swapping.";
                } else {
                    desc += " → No swap needed.";
                }
                history.add(arr.clone());
                descriptions.add(desc);
                compares.add(new int[]{idx, idx + 1});
            }
        }
        history.add(arr.clone());
        descriptions.add("Array is sorted!");
        compares.add(new int[]{-1, -1});
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