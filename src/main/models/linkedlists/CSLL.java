package main.models.linkedlists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Circular Singly Linked List implementation
 */
public class CSLL {
    public static class Node {
        public String data;
        public Node next;
        public String address;

        public Node(String data, String address) {
            this.data = data;
            this.address = address;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private Random random;

    public CSLL() {
        head = null;
        tail = null;
        size = 0;
        random = new Random();
    }

    private String generateAddress() {
        return String.format("0x%04X", random.nextInt(0xFFFF));
    }

    public void insertAtHead(String value) {
        Node newNode = new Node(value, generateAddress());

        if (head == null) {
            // First node - points to itself
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            newNode.next = head;
            head = newNode;
            tail.next = head; // Maintain circularity
        }
        size++;
        printListWithAddresses();
    }

    public void insertAtTail(String value) {
        Node newNode = new Node(value, generateAddress());

        if (head == null) {
            // First node - points to itself
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head; // Point back to head
        }
        size++;
        printListWithAddresses();
    }

    public String deleteHead() {
        if (head == null) return null;

        String deletedData = head.data;

        if (head == tail) {
            // Only one node
            head = null;
            tail = null;
        } else {
            head = head.next;
            tail.next = head; // Update tail's next to new head
        }
        size--;
        return deletedData;
    }

    public String deleteTail() {
        if (tail == null) return null;

        String deletedData = tail.data;

        if (head == tail) {
            // Only one node
            head = null;
            tail = null;
        } else {
            // Find the node before tail
            Node current = head;
            while (current.next != tail) {
                current = current.next;
            }
            current.next = head; // Point to head (circular)
            tail = current;
        }
        size--;
        return deletedData;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();

        if (head == null) return nodes;

        Node current = head;
        do {
            nodes.add(current);
            current = current.next;
        } while (current != head);

        return nodes;
    }

    public void printListWithAddresses() {
        if (head == null) {
            return;
        }


        Node current = head;
        do {
            // String nextData = (current.next != null) ? current.next.data : "null";
            current = current.next;
        } while (current != head);

    }
}