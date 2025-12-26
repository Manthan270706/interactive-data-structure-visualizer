package main.controllers.linkedlists;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.models.linkedlists.DoublyLinkedList;
import main.models.linkedlists.DoublyLinkedListNode;

public class DoublyLinkedListVisualizer {

    private VBox layout;
    private HBox controlPanel;
    private Pane canvas;
    private DoublyLinkedList doublyLinkedList;

    public DoublyLinkedListVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        doublyLinkedList = new DoublyLinkedList();

        Label title = new Label("Doubly Linked List");
        title.getStyleClass().add("section-title");

        controlPanel = createControlPanel();
        canvas = new Pane();
        canvas.setPrefHeight(400);
        canvas.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        layout.getChildren().addAll(title, controlPanel, canvas);
    }

    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        TextField valueField = new TextField();
        valueField.setPromptText("Enter value");
        valueField.setPrefWidth(120);

        Button insertHeadBtn = new Button("Insert Head");
        Button insertTailBtn = new Button("Insert Tail");
        Button deleteHeadBtn = new Button("Delete Head");
        Button deleteTailBtn = new Button("Delete Tail");

        Label statusLabel = new Label("Size: 0");

        // Make final copies for lambda
        final TextField vf = valueField;
        final Label sl = statusLabel;

        // Insert at head
        insertHeadBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                doublyLinkedList.insertAtHead(val);
                vf.clear();
                drawNodes();
                sl.setText("Size: " + doublyLinkedList.getSize());
            }
        });

        // Insert at tail
        insertTailBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                doublyLinkedList.insertAtTail(val);
                vf.clear();
                drawNodes();
                sl.setText("Size: " + doublyLinkedList.getSize());
            }
        });

        // Delete from head
        deleteHeadBtn.setOnAction(e -> {
            doublyLinkedList.deleteHead();
            drawNodes();
            sl.setText("Size: " + doublyLinkedList.getSize());
        });

        // Delete from tail
        deleteTailBtn.setOnAction(e -> {
            doublyLinkedList.deleteTail();
            drawNodes();
            sl.setText("Size: " + doublyLinkedList.getSize());
        });

        box.getChildren().addAll(
                valueField,
                insertHeadBtn,
                insertTailBtn,
                deleteHeadBtn,
                deleteTailBtn,
                statusLabel
        );

        return box;
    }

    private void drawNodes() {
        canvas.getChildren().clear();

        if (doublyLinkedList.isEmpty()) {
            Label emptyLabel = new Label("Doubly Linked List is empty");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            emptyLabel.setLayoutX(300);
            emptyLabel.setLayoutY(200);
            canvas.getChildren().add(emptyLabel);
            return;
        }

        double x = 120;
        java.util.List<DoublyLinkedList.Node> nodes = doublyLinkedList.getAllNodes();

        for (int i = 0; i < nodes.size(); i++) {
            DoublyLinkedList.Node node = nodes.get(i);
            DoublyLinkedListNode nodeVisual = new DoublyLinkedListNode(node);

            // Each DLL node is wider (approx 184px: 60+2+60+2+60 = 184)
            nodeVisual.getView().setLayoutX(x);
            nodeVisual.getView().setLayoutY(180);
            canvas.getChildren().add(nodeVisual.getView());

            // Draw forward arrow to next node
            if (i < nodes.size() - 1) {
                double nodeWidth = 190; // Width of each DLL node
                Line forwardArrow = createArrow(x + nodeWidth, 200, x + nodeWidth + 36, 200, Color.GREEN);
                canvas.getChildren().add(forwardArrow);

                // Forward arrow head
                Line arrowHead1 = new Line(x + nodeWidth + 31, 195, x + nodeWidth + 36, 200);
                Line arrowHead2 = new Line(x + nodeWidth + 31, 205, x + nodeWidth + 36, 200);
                arrowHead1.setStroke(Color.GREEN);
                arrowHead2.setStroke(Color.GREEN);
                arrowHead1.setStrokeWidth(2);
                arrowHead2.setStrokeWidth(2);
                canvas.getChildren().addAll(arrowHead1, arrowHead2);
            }

            // Draw backward arrow from next node (if not first node)
            if (i > 0) {
                Line backwardArrow = createArrow(x - 36, 210, x, 210, Color.RED);
                canvas.getChildren().add(backwardArrow);

                // Backward arrow head
                Line arrowHead1 = new Line(x - 31, 205, x - 36, 210);
                Line arrowHead2 = new Line(x - 31, 215, x - 36, 210);
                arrowHead1.setStroke(Color.RED);
                arrowHead2.setStroke(Color.RED);
                arrowHead1.setStrokeWidth(2);
                arrowHead2.setStrokeWidth(2);
                canvas.getChildren().addAll(arrowHead1, arrowHead2);
            }

            x += 230; // Increased spacing for DLL nodes (was 220, keeping it)
        }

        drawHeadPointer(120, 180);
        drawTailPointer(130 + ((nodes.size() - 1) * 220), 180);
    }

    private void highlightNode(int position, Color color) {
        java.util.List<DoublyLinkedList.Node> nodes = doublyLinkedList.getAllNodes();
        if (position >= 0 && position < nodes.size()) {
            double x = 120 + (position * 220);

            // Create highlight rectangle - wider for DLL nodes
            Rectangle highlight = new Rectangle(x - 5, 175, 194, 50);
            highlight.setFill(color);
            highlight.setOpacity(0.3);
            highlight.setStroke(Color.DARKGREEN);
            highlight.setStrokeWidth(2);

            // Add behind existing nodes
            canvas.getChildren().add(0, highlight);
        }
    }

    private Line createArrow(double startX, double startY, double endX, double endY, Color color) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(color);
        line.setStrokeWidth(2);
        return line;
    }

    private void drawHeadPointer(double nodeX, double nodeY) {
        double arrowStartX = nodeX + 90; // Center of the node
        double arrowStartY = nodeY - 50;
        double arrowEndY = nodeY;

        Line arrow = new Line(arrowStartX, arrowStartY, arrowStartX, arrowEndY);
        arrow.setStroke(Color.CRIMSON);
        arrow.setStrokeWidth(3);

        Line arrowHead1 = new Line(arrowStartX - 5, arrowStartY + 10, arrowStartX, arrowStartY);
        Line arrowHead2 = new Line(arrowStartX + 5, arrowStartY + 10, arrowStartX, arrowStartY);
        arrowHead1.setStroke(Color.CRIMSON);
        arrowHead2.setStroke(Color.CRIMSON);
        arrowHead1.setStrokeWidth(3);
        arrowHead2.setStrokeWidth(3);

        Text label = new Text("HEAD");
        label.setFill(Color.CRIMSON);
        label.setStyle("-fx-font-weight: bold;");
        label.setLayoutX(arrowStartX - 20);
        label.setLayoutY(arrowStartY - 10);

        canvas.getChildren().addAll(label, arrow, arrowHead1, arrowHead2);
    }

    private void drawTailPointer(double nodeX, double nodeY) {
        double arrowStartX = nodeX + 90; // Center of the node
        double arrowStartY = nodeY + 90;
        double arrowEndY = nodeY + 40;

        Line arrow = new Line(arrowStartX, arrowStartY, arrowStartX, arrowEndY);
        arrow.setStroke(Color.DARKORANGE);
        arrow.setStrokeWidth(3);

        Line arrowHead1 = new Line(arrowStartX - 5, arrowStartY - 10, arrowStartX, arrowStartY);
        Line arrowHead2 = new Line(arrowStartX + 5, arrowStartY - 10, arrowStartX, arrowStartY);
        arrowHead1.setStroke(Color.DARKORANGE);
        arrowHead2.setStroke(Color.DARKORANGE);
        arrowHead1.setStrokeWidth(3);
        arrowHead2.setStrokeWidth(3);

        Text label = new Text("TAIL");
        label.setFill(Color.DARKORANGE);
        label.setStyle("-fx-font-weight: bold;");
        label.setLayoutX(arrowStartX - 20);
        label.setLayoutY(arrowStartY + 20);

        canvas.getChildren().addAll(label, arrow, arrowHead1, arrowHead2);
    }

    public VBox getLayout() {
        return layout;
    }
}