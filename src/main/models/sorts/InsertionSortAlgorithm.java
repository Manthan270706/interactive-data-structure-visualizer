package main.models.sorts;

import java.util.*;

public class InsertionSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();

    public void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            descriptions.add("Considering element " + key);
            compares.add(new int[]{i, -1});
            history.add(arr.clone());

            descriptions.add("Comparing " + key + " with " + arr[j]);
            compares.add(new int[]{i, j});
            history.add(arr.clone());

            boolean moved = false;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                moved = true;

                descriptions.add("Shifted " + arr[j + 1] + " to the right");
                compares.add(new int[]{j + 1, j});
                history.add(arr.clone());
            }

            arr[j + 1] = key;
            if (moved)
                descriptions.add("Placed " + key + " at index " + (j + 1));
            else
                descriptions.add("Element " + key + " is already in correct position.");

            compares.add(new int[]{i, j + 1});
            history.add(arr.clone());
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