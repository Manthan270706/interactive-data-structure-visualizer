package main.models.sorts;

import java.util.*;

public class RadixSortAlgorithm {
    private List<int[]> history = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
    private List<int[]> compares = new ArrayList<>();
    private List<List<List<Integer>>> bucketStates = new ArrayList<>();

    public void sort(int[] arr) {
        int max = Arrays.stream(arr).max().orElse(0);
        int exp = 1;

        while (max / exp > 0) {
            List<List<Integer>> buckets = new ArrayList<>();
            for (int i = 0; i < 10; i++) buckets.add(new ArrayList<>());

            descriptions.add("Sorting by digit place " + exp);
            compares.add(new int[]{-1, -1});
            history.add(arr.clone());
            saveBucketState(buckets);

            for (int i = 0; i < arr.length; i++) {
                int digit = (arr[i] / exp) % 10;
                buckets.get(digit).add(arr[i]);
                descriptions.add("Placing " + arr[i] + " in bucket " + digit);
                int[] tempArr = arr.clone();
                tempArr[i] = 0; // show removal
                compares.add(new int[]{i, digit});
                history.add(tempArr);
                saveBucketState(buckets);
            }

            int idx = 0;
            for (int b = 0; b < 10; b++) {
                for (int num : buckets.get(b)) {
                    arr[idx++] = num;
                    descriptions.add("Moving " + num + " back from bucket " + b);
                    compares.add(new int[]{b, idx - 1});
                    history.add(arr.clone());
                    saveBucketState(buckets);
                }
            }
            exp *= 10;
        }

        descriptions.add("Array is sorted!");
        compares.add(new int[]{-1, -1});
        history.add(arr.clone());
        saveBucketState(new ArrayList<>(Collections.nCopies(10, new ArrayList<>())));
    }

    private void saveBucketState(List<List<Integer>> buckets) {
        List<List<Integer>> snapshot = new ArrayList<>();
        for (List<Integer> b : buckets) snapshot.add(new ArrayList<>(b));
        bucketStates.add(snapshot);
    }

    public List<int[]> getHistory() { return history; }
    public List<String> getDescriptions() { return descriptions; }
    public List<int[]> getCompares() { return compares; }
    public List<List<List<Integer>>> getBucketStates() { return bucketStates; }

    public void clear() {
        history.clear();
        descriptions.clear();
        compares.clear();
        bucketStates.clear();
    }
}