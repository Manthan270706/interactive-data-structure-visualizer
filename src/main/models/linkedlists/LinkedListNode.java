package main.models.linkedlists;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Visual representation of a linked list node
 */
public class LinkedListNode {
    private HBox view;
    private LinkedList.Node node;  // store reference to actual node
    private String value;
    private String address;

    // Constructor from a LinkedList.Node
    public LinkedListNode(LinkedList.Node node) {
        this.node = node;
        this.value = node.data;
        this.address = node.address;
        createView();
    }

    // Optional constructor from value/address
    public LinkedListNode(String value, String address) {
        this.node = null;
        this.value = value;
        this.address = address;
        createView();
    }

    private void createView() {
        // DATA part
        Rectangle dataRect = new Rectangle(60, 40);
        dataRect.setArcWidth(10);
        dataRect.setArcHeight(10);
        dataRect.setFill(Color.LIGHTBLUE);
        dataRect.setStroke(Color.DARKBLUE);

        Text dataText = new Text(value);
        StackPane dataPane = new StackPane(dataRect, dataText);

        // ADDRESS part
        Rectangle addrRect = new Rectangle(60, 40);
        addrRect.setArcWidth(10);
        addrRect.setArcHeight(10);
        addrRect.setFill(Color.LIGHTYELLOW);
        addrRect.setStroke(Color.DARKGOLDENROD);

        Text addrText = new Text(address);
        StackPane addrPane = new StackPane(addrRect, addrText);

        // Combine both
        view = new HBox(dataPane, addrPane);
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

    // New getter to access the underlying node
    public LinkedList.Node getNode() {
        return node;
    }


}
