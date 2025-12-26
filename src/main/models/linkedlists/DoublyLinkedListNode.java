package main.models.linkedlists;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Visual representation of a doubly linked list node
 */
public class DoublyLinkedListNode {
    private HBox view;
    private DoublyLinkedList.Node node;
    private String value;
    private String address;
    private String prevAddress;
    private String nextAddress;

    // Constructor from a DoublyLinkedList.Node
    public DoublyLinkedListNode(DoublyLinkedList.Node node) {
        this.node = node;
        this.value = node.data;
        this.address = node.address;

        // Always fetch latest prev and next addresses
        this.prevAddress = (node.prev != null) ? node.prev.address : "NULL";
        this.nextAddress = (node.next != null) ? node.next.address : "NULL";


        createView();
    }

    private void createView() {
        // PREV part
        Rectangle prevRect = new Rectangle(60, 40);
        prevRect.setArcWidth(10);
        prevRect.setArcHeight(10);
        prevRect.setFill(Color.LIGHTCORAL);
        prevRect.setStroke(Color.DARKRED);

        Text prevText = new Text("Prev:\n" + prevAddress);
        prevText.setWrappingWidth(55);
        StackPane prevPane = new StackPane(prevRect, prevText);

        // DATA part
        Rectangle dataRect = new Rectangle(60, 40);
        dataRect.setArcWidth(10);
        dataRect.setArcHeight(10);
        dataRect.setFill(Color.LIGHTBLUE);
        dataRect.setStroke(Color.DARKBLUE);

        Text dataText = new Text("Data:\n" + value);
        dataText.setWrappingWidth(55);
        StackPane dataPane = new StackPane(dataRect, dataText);

        // NEXT part
        Rectangle nextRect = new Rectangle(60, 40);
        nextRect.setArcWidth(10);
        nextRect.setArcHeight(10);
        nextRect.setFill(Color.LIGHTGREEN);
        nextRect.setStroke(Color.DARKGREEN);

        Text nextText = new Text("Next:\n" + nextAddress);
        nextText.setWrappingWidth(55);
        StackPane nextPane = new StackPane(nextRect, nextText);

        // Combine all three parts
        view = new HBox(prevPane, dataPane, nextPane);
        view.setSpacing(2);
        view.setAlignment(Pos.CENTER);
    }

    public HBox getView() {
        return view;
    }

    public String getValue() {
        return value;
    }

    public String getAddress() {
        return address;
    }

    public DoublyLinkedList.Node getNode() {
        return node;
    }
}
