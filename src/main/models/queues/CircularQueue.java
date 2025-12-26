package main.models.queues;

import java.util.Random;

/**
 * Circular Queue implementation
 */
public class CircularQueue {
    private String[] queue;
    private int front;
    private int rear;
    private int capacity;
    private int size;
    private Random random;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new String[capacity];
        this.front = -1;
        this.rear = -1;
        this.size = 0;
        this.random = new Random();
    }

    public boolean enqueue(String value) {
        if (isFull()) {
            return false;
        }

        if (isEmpty()) {
            front = 0;
        }

        rear = (rear + 1) % capacity;
        queue[rear] = value;
        size++;
        return true;
    }

    public String dequeue() {
        if (isEmpty()) {
            return null;
        }

        String value = queue[front];
        queue[front] = null;

        if (front == rear) {
            // Last element
            front = -1;
            rear = -1;
        } else {
            front = (front + 1) % capacity;
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
        front = -1;
        rear = -1;
        size = 0;
    }
}