package main.models.queues;

/**

 Priority Queue implementation using Max-Heap (array-based)

 Uses numeric values where greater numbers have higher priority
 */
public class PriorityQueue {
    private int[] heap;
    private int capacity;
    private int size;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.size = 0;
    }

    public boolean enqueue(int value) {
        if (isFull()) {
            return false;
        }

        
        // Add new element at the end
        heap[size] = value;
        size++;

        // Heapify up
        heapifyUp(size - 1);
        return true;
    }

    public Integer dequeue() {
        if (isEmpty()) {
            return null;
        }

        
        int maxValue = heap[0];

        // Replace root with last element
        heap[0] = heap[size - 1];
        size--;

        // Heapify down
        if (size > 0) {
            heapifyDown(0);
        }

        return maxValue;
    }

    private void heapifyUp(int index) {
        if (index == 0) return;

        
        int parentIndex = (index - 1) / 2;

        if (heap[index] > heap[parentIndex]) {
            // Swap with parent
            swap(index, parentIndex);
            heapifyUp(parentIndex);
        }
    }

    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int largest = index;

        
        if (leftChild < size && heap[leftChild] > heap[largest]) {
            largest = leftChild;
        }

        if (rightChild < size && heap[rightChild] > heap[largest]) {
            largest = rightChild;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public Integer peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int[] getHeapArray() {
        return heap.clone();
    }

    public void clear() {
        heap = new int[capacity];
        size = 0;
    }
}