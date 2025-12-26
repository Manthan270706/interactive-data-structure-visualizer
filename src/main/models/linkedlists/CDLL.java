package main.models.linkedlists;

import java.util.ArrayList;
import java.util.List;

/**
 * Circular Doubly Linked List implementation with simulated memory addresses.
 */
public class CDLL {
    public static class Node {
        public String data;
        public Node prev;
        public Node next;
        public String address;

        public Node(String data, String address) {
            this.data = data;
            this.address = address;
            this.prev = null;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size = 0;
    private int addressCounter = 0x1000;

    private String generateAddress() {
        addressCounter += 16;
        return String.format("0x%04X", addressCounter);
    }

    // 1. Insert at head
    public void insertAtHead(String value) {
        Node newNode = new Node(value, generateAddress());

        if (head == null) {
            // First node - circular references
            head = tail = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            newNode.next = head;
            newNode.prev = tail;
            head.prev = newNode;
            tail.next = newNode;
            head = newNode;
        }
        size++;
        printListWithAddresses();
    }

    // 2. Insert at tail
    public void insertAtTail(String value) {
        Node newNode = new Node(value, generateAddress());

        if (head == null) {
            // First node - circular references
            head = tail = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            newNode.next = head;
            newNode.prev = tail;
            tail.next = newNode;
            head.prev = newNode;
            tail = newNode;
        }
        size++;
        printListWithAddresses();
    }

    // 3. Delete head
    public String deleteHead() {
        if (head == null) return null;

        String deletedData = head.data;

        if (size == 1) {
            // Only one node
            head = tail = null;
        } else {
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }
        size--;
        return deletedData;
    }

    // 4. Delete tail
    public String deleteTail() {
        if (tail == null) return null;

        String deletedData = tail.data;

        if (size == 1) {
            // Only one node
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
        }
        size--;
        return deletedData;
    }

    public void printListWithAddresses() {
        if (head == null) {

            return;
        }

        Node current = head;

        do {
            String prevAddress = (current.prev != null) ? current.prev.address : "null";
            String nextAddress = (current.next != null) ? current.next.address : "null";
            current = current.next;
        } while (current != head);


    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    // Get all nodes for visualization
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
}