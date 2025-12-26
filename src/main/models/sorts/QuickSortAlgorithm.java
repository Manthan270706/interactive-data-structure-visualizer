package main.models.sorts;

import java.util.*;

public class QuickSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();

    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        descriptions.add("Array is sorted!");
        compares.add(new int[]{-1, -1});
        history.add(arr.clone());
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        descriptions.add("Choosing pivot " + pivot + " at index " + high);
        history.add(arr.clone());
        compares.add(new int[]{high, -1});

        int i = low - 1;
        for (int j = low; j < high; j++) {
            descriptions.add("Comparing " + arr[j] + " with pivot " + pivot);
            compares.add(new int[]{j, high});
            history.add(arr.clone());

            if (arr[j] < pivot) {
                i++;
                if (i != j) {
                    descriptions.add("Swapping " + arr[i] + " and " + arr[j]);
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    compares.add(new int[]{i, j});
                    history.add(arr.clone());
                }
            }
        }

        descriptions.add("Placing pivot " + pivot + " at correct position " + (i + 1));
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        compares.add(new int[]{i + 1, high});
        history.add(arr.clone());

        return i + 1;
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