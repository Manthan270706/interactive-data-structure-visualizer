package main.controllers.linkedlists;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.models.linkedlists.CDLL;
import main.models.linkedlists.CDLLNode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CDLLVisualizer {

    private VBox layout;
    private HBox controlPanel;
    private Pane canvas;
    private CDLL CDLL;

    public CDLLVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        CDLL = new CDLL();

        Label title = new Label("Circular Doubly Linked List");
        title.getStyleClass().add("section-title");

        controlPanel = createControlPanel();
        canvas = new Pane();
        canvas.setPrefHeight(500); // Increased height for circular visualization
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

        insertHeadBtn.setOnAction(e -> {
            String val = valueField.getText().trim();
            if (!val.isEmpty()) {
                CDLL.insertAtHead(val);
                valueField.clear();
                drawNodes();
                statusLabel.setText("Size: " + CDLL.getSize());
            }
        });

        insertTailBtn.setOnAction(e -> {
            String val = valueField.getText().trim();
            if (!val.isEmpty()) {
                CDLL.insertAtTail(val);
                valueField.clear();
                drawNodes();
                statusLabel.setText("Size: " + CDLL.getSize());
            }
        });

        deleteHeadBtn.setOnAction(e -> {
            CDLL.deleteHead();
            drawNodes();
            statusLabel.setText("Size: " + CDLL.getSize());
        });

        deleteTailBtn.setOnAction(e -> {
            CDLL.deleteTail();
            drawNodes();
            statusLabel.setText("Size: " + CDLL.getSize());
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

    private void drawSingleNode(CDLL.Node node) {
        double centerX = 300;
        double centerY = 250;

        // Draw the node
        CDLLNode nodeVisual = new CDLLNode(node);
        nodeVisual.getView().setLayoutX(centerX - 92); // Half of node width
        nodeVisual.getView().setLayoutY(centerY - 20); // Half of node height
        canvas.getChildren().add(nodeVisual.getView());

        // Draw self-referential arrows for single node
        drawCircularArrow(centerX + 50, centerY, centerX + 92, centerY + 40, Color.GREEN, true); // Next
        drawCircularArrow(centerX - 50, centerY, centerX - 92, centerY - 40, Color.RED, false); // Prev
    }

    private void drawNodes() {
        canvas.getChildren().clear();

        if (CDLL.isEmpty()) {
            Label emptyLabel = new Label("Circular Doubly Linked List is empty");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            emptyLabel.setLayoutX(250);
            emptyLabel.setLayoutY(250);
            canvas.getChildren().add(emptyLabel);
            return;
        }

        java.util.List<CDLL.Node> nodes = CDLL.getAllNodes();

        drawLinearNodes(nodes); // <-- new method

        drawHeadPointer();
        drawTailPointer();
        drawLinearConnections(); // <-- new method for arrows in linear layout
    }

    private void drawLinearNodes(java.util.List<CDLL.Node> nodes) {
        double startX = 50;   // starting X position
        double startY = 200;  // Y position for all nodes
        double spacing = 250; // space between nodes

        for (int i = 0; i < nodes.size(); i++) {
            CDLL.Node node = nodes.get(i);
            double x = startX + i * spacing;
            double y = startY;

            CDLLNode nodeVisual = new CDLLNode(node);
            nodeVisual.getView().setLayoutX(x);
            nodeVisual.getView().setLayoutY(y);
            canvas.getChildren().add(nodeVisual.getView());

            // Draw next arrow (except for last node)
            if (i < nodes.size() - 1) {
                double nextX = startX + (i + 1) * spacing;
                double currentX = x + 92;   // end of current node
                double nextY = startY + 15; // middle of node height
                double currentY = y + 15;

                drawLinearArrow(currentX, currentY, nextX, nextY, Color.GREEN);
            }
        }
    }

    private void drawLinearArrow(double startX, double startY, double endX, double endY, Color color) {
        Line arrow = new Line(startX + 100, startY, endX, endY);
        arrow.setStroke(color);
        arrow.setStrokeWidth(2);

        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowHeadLength = 10;

        Line arrowHead1 = new Line(
                endX - arrowHeadLength * Math.cos(angle - Math.PI/6),
                endY - arrowHeadLength * Math.sin(angle - Math.PI/6),
                endX, endY
        );
        Line arrowHead2 = new Line(
                endX - arrowHeadLength * Math.cos(angle + Math.PI/6),
                endY - arrowHeadLength * Math.sin(angle + Math.PI/6),
                endX, endY
        );

        arrowHead1.setStroke(color);
        arrowHead2.setStroke(color);
        arrowHead1.setStrokeWidth(2);
        arrowHead2.setStrokeWidth(2);

        canvas.getChildren().addAll(arrow, arrowHead1, arrowHead2);
    }


    private void drawCircularArrow(double startX, double startY, double endX, double endY, Color color, boolean isForward) {
        Line arrow = new Line(startX, startY, endX, endY);
        arrow.setStroke(color);
        arrow.setStrokeWidth(2);
        canvas.getChildren().add(arrow);

        // Arrow head
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowHeadLength = 10;

        Line arrowHead1 = new Line(
                endX - arrowHeadLength * Math.cos(angle - Math.PI/6),
                endY - arrowHeadLength * Math.sin(angle - Math.PI/6),
                endX, endY
        );
        Line arrowHead2 = new Line(
                endX - arrowHeadLength * Math.cos(angle + Math.PI/6),
                endY - arrowHeadLength * Math.sin(angle + Math.PI/6),
                endX, endY
        );

        arrowHead1.setStroke(color);
        arrowHead2.setStroke(color);
        arrowHead1.setStrokeWidth(2);
        arrowHead2.setStrokeWidth(2);

        canvas.getChildren().addAll(arrowHead1, arrowHead2);
    }

    private void drawHeadPointer() {
        if (CDLL.getHead() == null) return;
        double x = 100; // first node X
        double y = 220;

        Line pointer = new Line(x + 50, y - 20, x + 50, y - 60);
        pointer.setStroke(Color.CRIMSON);
        pointer.setStrokeWidth(3);

        Text label = new Text("HEAD");
        label.setFill(Color.CRIMSON);
        label.setStyle("-fx-font-weight: bold;");
        label.setLayoutX(x + 35);
        label.setLayoutY(y - 70);

        canvas.getChildren().addAll(label, pointer);
    }

    private void drawTailPointer() {
        if (CDLL.getTail() == null) return;
        java.util.List<CDLL.Node> nodes = CDLL.getAllNodes();
        if (nodes.isEmpty()) return;

        double startX = 50;
        double startY = 200;
        double spacing = 250;

        // Compute the position of the last node (tail)
        double tailNodeX = startX + (nodes.size() - 1) * spacing;
        double tailNodeY = startY;

        // Middle of the data section of the node
        double tailCenterX = tailNodeX + 96; // roughly center of node width (192)
        double tailCenterY = tailNodeY + 104; // vertical center (assuming ~50px height)

        // Draw the pointer line (above the node)
        Line pointer = new Line(tailCenterX, tailCenterY - 30, tailCenterX, tailCenterY - 60);
        pointer.setStroke(Color.DARKORANGE);
        pointer.setStrokeWidth(3);

        // Label for TAIL
        Text label = new Text("TAIL");
        label.setFill(Color.DARKORANGE);
        label.setStyle("-fx-font-weight: bold;");
        label.setLayoutX(tailCenterX - 15);
        label.setLayoutY(tailCenterY - 15);

        canvas.getChildren().addAll(label, pointer);
    }


    private void highlightNode(int position, Color color) {
        java.util.List<CDLL.Node> nodes = CDLL.getAllNodes();
        if (position >= 0 && position < nodes.size()) {
            double centerX = 300;
            double centerY = 250;
            double radius = 150;
            double angle = 2 * Math.PI * position / nodes.size();
            double x = centerX + radius * Math.cos(angle) - 92;
            double y = centerY + radius * Math.sin(angle) - 20;

            Rectangle highlight = new Rectangle(x - 5, y - 5, 194, 50);
            highlight.setFill(color);
            highlight.setOpacity(0.3);
            highlight.setStroke(Color.DARKGREEN);
            highlight.setStrokeWidth(2);

            canvas.getChildren().add(0, highlight);
        }
    }

    public VBox getLayout() {
        return layout;
    }

    private void drawLinearConnections() {
    java.util.List<CDLL.Node> nodes = CDLL.getAllNodes();
    if (nodes.size() <= 1) return;

    double startX = 50;
    double startY = 200;
    double spacing = 250;

    // Draw prev arrows for consecutive nodes
    for (int i = 1; i < nodes.size(); i++) {
        double currentX = startX + i * spacing;
        double currentY = startY + 25;
        double prevX = startX + (i - 1) * spacing + 192;
        double prevY = startY + 25;

        drawArrow(currentX, currentY, prevX, prevY, Color.RED);
    }

    // Draw arrow from tail → head to show circular nature
    double tailX = startX + (nodes.size() - 1) * spacing + 160; // end of tail
    double headX = startX + 85;
    double tailY = startY + 100;
    double headY = startY + 100;

//    drawArrow(tailX, tailY, headX, headY, Color.PURPLE); // special color for circular
        drawStraightCircularArrowsForCDLL(headX, headY, tailX, tailY);
}

private void drawStraightCircularArrowsForCDLL(double headX, double headY, double tailX, double tailY) {
    double strokeWidth = 2.5;
    double nodeWidth = 220;   // width of your node (used everywhere)
    double arrowSize = 8;

    // === Forward circular link: tail.next → head ===
    // Goes down, across, then up to head
    Line down = new Line(tailX, tailY - 58, tailX, tailY + 10);
    down.setStroke(Color.PURPLE);
    down.setStrokeWidth(strokeWidth);

    Line back = new Line(tailX, tailY + 10, headX + 5, tailY + 10);
    back.setStroke(Color.PURPLE);
    back.setStrokeWidth(strokeWidth);

    Line up = new Line(headX + 5, tailY - 58, headX + 5, tailY + 10);
    up.setStroke(Color.PURPLE);
    up.setStrokeWidth(strokeWidth);

    // Arrowhead (up direction)
    Line arrow1 = new Line(headX + 5, headY - 58, headX + 5 - arrowSize, headY - 48);
    Line arrow2 = new Line(headX + 5, headY - 58, headX + 5 + arrowSize, headY - 48);
    arrow1.setStroke(Color.PURPLE);
    arrow2.setStroke(Color.PURPLE);
    arrow1.setStrokeWidth(strokeWidth);
    arrow2.setStrokeWidth(strokeWidth);

    // === Backward circular link: head.prev → tail ===
    // Goes upward, across, then down to *middle of tail’s data section*
    double headLeftX = headX - 50;  // slight offset left from head node
    double upperY = headY - 185;    // top bend height

    // compute middle of the tail node’s data section (dynamic)
    double tailMiddleX = tailX - (nodeWidth / 2);

    Line up2 = new Line(headLeftX, headY - 102, headLeftX, upperY);
    up2.setStroke(Color.RED);
    up2.setStrokeWidth(strokeWidth);

    Line across2 = new Line(headLeftX, upperY, tailMiddleX + 46, upperY);
    across2.setStroke(Color.RED);
    across2.setStrokeWidth(strokeWidth);

    Line down2 = new Line(tailMiddleX + 46, upperY, tailMiddleX + 46, headY - 100);
    down2.setStroke(Color.RED);
    down2.setStrokeWidth(strokeWidth);

    // Arrowhead (down direction)
    Line arrow3 = new Line(tailMiddleX + 46, headY - 100, tailMiddleX + 46 - arrowSize, headY - 100 - arrowSize);
    Line arrow4 = new Line(tailMiddleX + 46, headY - 100, tailMiddleX + 46 + arrowSize, headY - 100 - arrowSize);
    arrow3.setStroke(Color.RED);
    arrow4.setStroke(Color.RED);
    arrow3.setStrokeWidth(strokeWidth);
    arrow4.setStrokeWidth(strokeWidth);

    // Optional label
    Text label = new Text("Circular Links");
    label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
    label.setFill(Color.PURPLE);
    label.setX((headX + tailX) / 2);
    label.setY(tailY + 25);

    canvas.getChildren().addAll(
            down, back, up, arrow1, arrow2,   // forward
            up2, across2, down2, arrow3, arrow4,  // backward
            label
    );
}



    // Helper method to draw an arrow
    private void drawArrow(double startX, double startY, double endX, double endY, Color color) {
        Line arrow = new Line(startX, startY, endX, endY);
        arrow.setStroke(color);
        arrow.setStrokeWidth(2);

        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowHeadLength = 10;

        Line arrowHead1 = new Line(
                endX - arrowHeadLength * Math.cos(angle - Math.PI/6),
                endY - arrowHeadLength * Math.sin(angle - Math.PI/6),
                endX, endY
        );
        Line arrowHead2 = new Line(
                endX - arrowHeadLength * Math.cos(angle + Math.PI/6),
                endY - arrowHeadLength * Math.sin(angle + Math.PI/6),
                endX, endY
        );

        arrowHead1.setStroke(color);
        arrowHead2.setStroke(color);
        arrowHead1.setStrokeWidth(2);
        arrowHead2.setStrokeWidth(2);

        canvas.getChildren().addAll(arrow, arrowHead1, arrowHead2);
    }

}