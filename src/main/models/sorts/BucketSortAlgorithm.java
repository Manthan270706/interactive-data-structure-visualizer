package main.models.sorts;

import java.util.*;

public class BucketSortAlgorithm {
    private List<double[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();
    private List<List<List<Double>>> bucketStates = new ArrayList<>();

    public void sort(double[] arr) {
        int n = arr.length;
        List<Double>[] buckets = new List[10];
        for (int i = 0; i < 10; i++) buckets[i] = new ArrayList<>();

        saveBucketState(buckets);

        for (int i = 0; i < n; i++) {
            int bucketIdx = Math.min(9, (int) (arr[i] / 10));
            buckets[bucketIdx].add(arr[i]);
            descriptions.add("Placing " + arr[i] + " into bucket " + bucketIdx);
            compares.add(new int[]{i, bucketIdx});
            history.add(arr.clone());
            saveBucketState(buckets);
        }

        for (int b = 0; b < 10; b++) {
            if (!buckets[b].isEmpty()) {
                Collections.sort(buckets[b]);
                descriptions.add("Sorting bucket " + b);
                compares.add(new int[]{-1, b});
                history.add(arr.clone());
                saveBucketState(buckets);
            }
        }

        int index = 0;
        for (int b = 0; b < 10; b++) {
            for (double val : buckets[b]) {
                arr[index++] = val;
                descriptions.add("Moving " + val + " from bucket " + b + " to array");
                compares.add(new int[]{index - 1, b});
                history.add(arr.clone());
                saveBucketState(buckets);
            }
        }

        descriptions.add("Array is sorted!");
        compares.add(new int[]{-1, -1});
        history.add(arr.clone());
        saveBucketState(buckets);
    }

    private void saveBucketState(List<Double>[] buckets) {
        List<List<Double>> snapshot = new ArrayList<>();
        for (List<Double> b : buckets) snapshot.add(new ArrayList<>(b));
        bucketStates.add(snapshot);
    }

    public List<double[]> getHistory() { return history; }
    public List<String> getDescriptions() { return descriptions; }
    public List<int[]> getCompares() { return compares; }
    public List<List<List<Double>>> getBucketStates() { return bucketStates; }

    public void clear() {
        history.clear();
        descriptions.clear();
        compares.clear();
        bucketStates.clear();
    }
}