package main.models.queues;

/**

 Double Ended Queue (Deque) implementation using circular array
 */
public class Deque {
    private String[] deque;
    private int front;
    private int rear;
    private int capacity;
    private int size;

    public Deque(int capacity) {
        this.capacity = capacity;
        this.deque = new String[capacity];
        this.front = -1;
        this.rear = -1;
        this.size = 0;
    }

    public boolean addFront(String value) {
        if (isFull()) {
            return false;
        }

        
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            front = (front - 1 + capacity) % capacity;
        }

        deque[front] = value;
        size++;
        return true;
    }

    public boolean addRear(String value) {
        if (isFull()) {
            return false;
        }

        
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }

        deque[rear] = value;
        size++;
        return true;
    }

    public String removeFront() {
        if (isEmpty()) {
            return null;
        }

        
        String value = deque[front];
        deque[front] = null;

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

    public String removeRear() {
        if (isEmpty()) {
            return null;
        }

        
        String value = deque[rear];
        deque[rear] = null;

        if (front == rear) {
            // Last element
            front = -1;
            rear = -1;
        } else {
            rear = (rear - 1 + capacity) % capacity;
        }
        size--;
        return value;
    }

    public String peekFront() {
        if (isEmpty()) {
            return null;
        }
        return deque[front];
    }

    public String peekRear() {
        if (isEmpty()) {
            return null;
        }
        return deque[rear];
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

    public int getFrontIndex() {
        return front;
    }

    public int getRearIndex() {
        return rear;
    }

    public String[] getDequeArray() {
        return deque.clone();
    }

    public void clear() {
        deque = new String[capacity];
        front = -1;
        rear = -1;
        size = 0;
    }
}