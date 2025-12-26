package main.models.stack;

import main.models.linkedlists.LinkedList;

/**
 * Stack implementation using SinglyLinkedList
 * Uses insertAtFront() as push() and deleteFromFront() as pop()
 */

public class Stack {
    private LinkedList linkedList;

    public Stack() {
        linkedList = new LinkedList();
    }

    public void push(String data) {
        linkedList.insertAtFront(data);
    }

    public String pop() {
        return linkedList.deleteFromFront();
    }

    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return linkedList.getHead().data;
    }

    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public int size() {
        return linkedList.getSize();
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }
}