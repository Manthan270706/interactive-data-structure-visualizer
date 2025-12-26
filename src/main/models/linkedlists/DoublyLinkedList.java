package main.models.linkedlists;

import java.util.ArrayList;
import java.util.List;

/**
 * Doubly linked list implementation with simulated memory addresses.
 */
public class DoublyLinkedList {
    public static class Node {
        public String data;
        public Node prev;
        public Node next;
        public String address;      // unique address for this node

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

    private int addressCounter = 0x1000; // Start from a base address

    private String generateAddress() {
        addressCounter += 16;
        return String.format("0x%04X", addressCounter);
    }

    public void insertAtTail(String value) {
        Node newNode = new Node(value, generateAddress());

        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        printListWithAddresses();
    }

    public void insertAtHead(String value) {
        Node newNode = new Node(value, generateAddress());

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
        printListWithAddresses();
    }

    public String deleteHead() {
        if (head == null) return null;

        String deletedData = head.data;

        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return deletedData;
    }

    public String deleteTail() {
        if (tail == null) return null;

        String deletedData = tail.data;

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return deletedData;
    }

    public void printListWithAddresses() {
        Node current = head;
        while (current != null) {
            // String prevAddress = (current.prev != null) ? current.prev.address : "null";
            // String nextAddress = (current.next != null) ? current.next.address : "null";
            current = current.next;
        }
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
        Node current = head;
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }
        return nodes;
    }
}
