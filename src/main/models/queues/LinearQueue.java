package main.models.queues;

import java.util.Random;

/**
 * Linear Queue implementation using array
 */
public class LinearQueue {
    private String[] queue;
    private int front;
    private int rear;
    private int capacity;
    private int size;
    private Random random;

    public LinearQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new String[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
        this.random = new Random();
    }

    public boolean enqueue(String value) {
        if (isFull()) {
            return false;
        }

        // FIX: Only increment rear if we're not at the last index
        if (rear < capacity - 1) {
            rear++;
            queue[rear] = value;
            size++;
            return true;
        }
        return false;
    }

    public String dequeue() {
        if (isEmpty()) {
            return null;
        }
        String value = queue[front];
        queue[front] = null;

        // If there's only one element, reset the queue
        if (front == rear) {
            front = 0;
            rear = -1;
        } else {
            front++;
        }
        size--;
        return value;
    }

    public String peekFront() {
        if (isEmpty()) {
            return null;
        }
        return queue[front];
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

    public int getFrontIndex() {
        return front;
    }

    public int getRearIndex() {
        return rear;
    }

    public String[] getQueueArray() {
        return queue.clone();
    }

    public void clear() {
        queue = new String[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }
}