package main.controllers.linkedlists;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.models.linkedlists.LinkedList;
import main.models.linkedlists.LinkedListNode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SinglyLinkedListVisualizer {
    private VBox layout;      // Main layout for this visualizer
    private HBox controlPanel;
    private Pane canvas;
    private LinkedList linkedList;

    public SinglyLinkedListVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));

        linkedList = new LinkedList();

        // Section title
        Label title = new Label("Singly Linked List");
        title.getStyleClass().add("section-title");

        // Control panel
        controlPanel = createControlPanel();

        // Canvas for drawing nodes
        canvas = new Pane();
        canvas.setPrefHeight(400);
        canvas.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        layout.getChildren().addAll(title, controlPanel, canvas);
    }

    // Control panel buttons and actions
    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        TextField valueField = new TextField();
        valueField.setPromptText("Enter value");

        Button insertFrontBtn = new Button("Insert Front");
        Button insertBackBtn = new Button("Insert Back");
        Button deleteFrontBtn = new Button("Delete Front");
        Button deleteBackBtn = new Button("Delete Back");

        Label statusLabel = new Label("Size: 0");

        final TextField vf = valueField;
        final Label sl = statusLabel;

        insertFrontBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                animateInsertAtFront(val, sl);
                vf.clear();
            }
        });

        insertBackBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                linkedList.insertAtEnd(val);
                vf.clear();
                drawNodes();
                sl.setText("Size: " + linkedList.getSize());
            }
        });

        deleteFrontBtn.setOnAction(e -> animateDeleteFromFront(sl));
        deleteBackBtn.setOnAction(e -> animateDeleteFromBack(sl));

        box.getChildren().addAll(valueField, insertFrontBtn, insertBackBtn, deleteFrontBtn, deleteBackBtn, statusLabel);

        return box;
    }

    // Highlight a specific node
    private void highlightNode(int position, Color color) {
        java.util.List<LinkedList.Node> nodes = linkedList.getAllNodes();
        if (position >= 0 && position < nodes.size()) {
            double x = 120 + (position * 140);
            Rectangle highlight = new Rectangle(x - 5, 175, 130, 50);
            highlight.setFill(color);
            highlight.setOpacity(0.3);
            highlight.setStroke(Color.DARKGREEN);
            highlight.setStrokeWidth(2);
            canvas.getChildren().add(0, highlight);
        }
    }

    // Draw all nodes and arrows
    private void drawNodes() {
        canvas.getChildren().clear();

        if (linkedList.isEmpty()) {
            Label emptyLabel = new Label("Linked List is empty");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            emptyLabel.setLayoutX(300);
            emptyLabel.setLayoutY(200);
            canvas.getChildren().add(emptyLabel);
            return;
        }

        double x = 120;
        java.util.List<LinkedList.Node> nodes = linkedList.getAllNodes();

        for (int i = 0; i < nodes.size(); i++) {
            LinkedList.Node node = nodes.get(i);
            String displayAddress = (i == nodes.size() - 1) ? "NULL" : node.address;
            LinkedListNode nodeVisual = new LinkedListNode(node.data, displayAddress);
            nodeVisual.getView().setLayoutX(x);
            nodeVisual.getView().setLayoutY(180);
            canvas.getChildren().add(nodeVisual.getView());

            // Arrow to next node
            if (i < nodes.size() - 1) {
                Line arrow = createArrow(x + 120, 200, x + 160, 200);
                canvas.getChildren().add(arrow);
                Line arrowHead1 = new Line(x + 155, 195, x + 160, 200);
                Line arrowHead2 = new Line(x + 155, 205, x + 160, 200);
                arrowHead1.setStroke(Color.GRAY);
                arrowHead2.setStroke(Color.GRAY);
                arrowHead1.setStrokeWidth(2);
                arrowHead2.setStrokeWidth(2);
                canvas.getChildren().addAll(arrowHead1, arrowHead2);
            }

            x += 140;
        }

        drawHeadPointer(120, 180);
    }

    private Line createArrow(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GRAY);
        line.setStrokeWidth(2);
        return line;
    }

    private void drawHeadPointer(double nodeX, double nodeY) {
        double arrowStartX = nodeX + 30;
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

    // Animation for insertion at front
    private void animateInsertAtFront(String val, Label statusLabel) {
        double nodeWidth = 120;
        double gap = 20;
        final double headX = 120;
        double startY = 180;

        linkedList.insertAtFront(val);
        statusLabel.setText("Size: " + linkedList.getSize());

        java.util.List<LinkedList.Node> nodes = linkedList.getAllNodes();
        java.util.List<javafx.scene.Node> existingNodeViews = new java.util.ArrayList<>(canvas.getChildren());

        Timeline shiftTimeline = new Timeline();
        for (javafx.scene.Node nodeView : existingNodeViews) {
            final double finalX = nodeView.getLayoutX() + nodeWidth + gap;
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5),
                    new javafx.animation.KeyValue(nodeView.layoutXProperty(), finalX));
            shiftTimeline.getKeyFrames().add(kf);
        }

        LinkedList.Node newNode = nodes.get(0);
        LinkedListNode newNodeVisual = new LinkedListNode(newNode);
        final javafx.scene.Node newNodeBox = newNodeVisual.getView();
        newNodeBox.setLayoutY(startY);
        newNodeBox.setLayoutX(headX - nodeWidth - gap);
        canvas.getChildren().add(0, newNodeBox);

        Timeline insertTimeline = new Timeline();
        KeyFrame insertKF = new KeyFrame(Duration.seconds(0.5),
                new javafx.animation.KeyValue(newNodeBox.layoutXProperty(), headX));
        insertTimeline.getKeyFrames().add(insertKF);

        insertTimeline.setOnFinished(e -> drawNodes());
        shiftTimeline.setOnFinished(e -> insertTimeline.play());
        shiftTimeline.play();
    }

    private void animateDeleteFromFront(Label statusLabel) {
        if (linkedList.isEmpty() || canvas.getChildren().isEmpty()) return;
        linkedList.deleteFromFront();
        statusLabel.setText("Size: " + linkedList.getSize());

        javafx.scene.Node firstNodeView = null;
        for (javafx.scene.Node child : canvas.getChildren()) {
            if (child instanceof HBox) {
                firstNodeView = child;
                break;
            }
        }
        if (firstNodeView == null) return;

        final javafx.scene.Node nodeToRemove = firstNodeView;
        Timeline delinkTimeline = new Timeline();
        KeyFrame kfDelink = new KeyFrame(Duration.seconds(0.5),
                new javafx.animation.KeyValue(nodeToRemove.opacityProperty(), 0),
                new javafx.animation.KeyValue(nodeToRemove.layoutYProperty(), -50));
        delinkTimeline.getKeyFrames().add(kfDelink);
        delinkTimeline.setOnFinished(e -> drawNodes());
        delinkTimeline.play();
    }

    private void animateDeleteFromBack(Label statusLabel) {
        if (linkedList.isEmpty() || canvas.getChildren().isEmpty()) return;

        if (linkedList.getSize() == 1) {
            animateDeleteFromFront(statusLabel);
            return;
        }

        linkedList.deleteFromEnd();
        statusLabel.setText("Size: " + linkedList.getSize());
        drawNodes();
    }

    // Getter for layout to embed in LinkedListTabs
    public VBox getLayout() {
        return layout;
    }
}
