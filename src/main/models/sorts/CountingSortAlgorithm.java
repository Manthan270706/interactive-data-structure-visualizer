package main.models.sorts;

import java.util.*;

public class CountingSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();
    private List<int[]> countHistory = new ArrayList<>();
    private int maxVal = 0;

    public void sort(int[] arr) {
        maxVal = Arrays.stream(arr).max().orElse(0);
        int[] count = new int[maxVal + 1];
        int[] output = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            count[arr[i]]++;
            countHistory.add(count.clone());
            descriptions.add("Counting occurrence of " + arr[i]);
            compares.add(new int[]{i, arr[i]});
            history.add(arr.clone());
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
            countHistory.add(count.clone());
            descriptions.add("Cumulative count updated for " + i);
            compares.add(new int[]{-1, i});
            history.add(arr.clone());
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            int val = arr[i];
            int pos = count[val] - 1;
            output[pos] = val;
            count[val]--;
            countHistory.add(count.clone());
            descriptions.add("Placing " + val + " at position " + pos);
            compares.add(new int[]{i, val});
            history.add(output.clone());
        }

        descriptions.add("Array is sorted!");
        compares.add(new int[]{-1, -1});
        history.add(output.clone());
        countHistory.add(count.clone());
    }

    public List<int[]> getHistory() { return history; }
    public List<String> getDescriptions() { return descriptions; }
    public List<int[]> getCompares() { return compares; }
    public List<int[]> getCountHistory() { return countHistory; }
    public int getMaxVal() { return maxVal; }

    public void clear() {
        history.clear();
        descriptions.clear();
        compares.clear();
        countHistory.clear();
        maxVal = 0;
    }
}