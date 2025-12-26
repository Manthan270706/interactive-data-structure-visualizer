package main.models.linkedlists;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Visual representation of a circular singly linked list node
 */
public class CSLLNode {
    private HBox view;
    private CSLL.Node node;
    private String value;
    private String address;
    private String nextAddress;

    public CSLLNode(CSLL.Node node) {
        this.node = node;
        this.value = node.data;
        this.address = node.address;
        this.nextAddress = (node.next != null) ? node.next.address : "NULL";
        createView();
    }

    // Constructor for manual creation
    public CSLLNode(String value, String address, String nextAddress) {
        this.node = null;
        this.value = value;
        this.address = address;
        this.nextAddress = nextAddress;
        createView();
    }

    private void createView() {
        // DATA part
        Rectangle dataRect = new Rectangle(80, 40);
        dataRect.setArcWidth(10);
        dataRect.setArcHeight(10);
        dataRect.setFill(Color.LIGHTBLUE);
        dataRect.setStroke(Color.DARKBLUE);

        Text dataText = new Text("Data:\n" + value);
        dataText.setWrappingWidth(75);
        StackPane dataPane = new StackPane(dataRect, dataText);

        // NEXT part
        Rectangle nextRect = new Rectangle(80, 40);
        nextRect.setArcWidth(10);
        nextRect.setArcHeight(10);
        nextRect.setFill(Color.LIGHTGREEN);
        nextRect.setStroke(Color.DARKGREEN);

        Text nextText = new Text("Next:\n" + nextAddress);
        nextText.setWrappingWidth(75);
        StackPane nextPane = new StackPane(nextRect, nextText);

        // Combine both parts
        view = new HBox(dataPane, nextPane);
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

    public String getNextAddress() {
        return nextAddress;
    }

    public CSLL.Node getNode() {
        return node;
    }
}