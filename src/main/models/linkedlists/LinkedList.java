package main.models.linkedlists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Actual singly linked list implementation
 */
public class LinkedList {
    private Node head;
    private int size;
    private Random random;

    public static class Node {  // Made public static
        public String data;
        public Node next;
        public String address;

        public Node(String data, String address) {
            this.data = data;
            this.address = address;
            this.next = null;
        }
    }

    public LinkedList() {
        head = null;
        size = 0;
        random = new Random();
    }

    public void insertAtFront(String data) {
        String address = generateAddress();
        Node newNode = new Node(data, address);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void insertAtEnd(String data) {
        String address = generateAddress();
        Node newNode = new Node(data, address);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public String deleteFromFront() {
        if (head == null) return null;

        String deletedData = head.data;
        head = head.next;
        size--;
        return deletedData;
    }

    public String deleteFromEnd() {
        if (head == null) return null;

        if (head.next == null) {
            String deletedData = head.data;
            head = null;
            size--;
            return deletedData;
        }

        Node current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        String deletedData = current.next.data;
        current.next = null;
        size--;
        return deletedData;
    }

    public Node getHead() {
        return head;
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

    private String generateAddress() {
        return String.format("0x%04X", random.nextInt(0xFFFF));
    }
}