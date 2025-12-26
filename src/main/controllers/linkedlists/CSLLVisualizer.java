package main.controllers.linkedlists;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.models.linkedlists.CSLL;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CSLLVisualizer {

    private VBox layout;
    private HBox controlPanel;
    private Pane canvas;
    private CSLL csll;

    public CSLLVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        csll = new CSLL();

        Label title = new Label("Circular Singly Linked List");
        title.getStyleClass().add("section-title");

        controlPanel = createControlPanel();
        canvas = new Pane();
        canvas.setPrefHeight(500);
        canvas.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        layout.getChildren().addAll(title, controlPanel, canvas);
    }

    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        TextField valueField = new TextField();
        valueField.setPromptText("Enter value");
        valueField.setPrefWidth(120);

        Button insertHeadBtn = new Button("Insert at Head");
        Button insertTailBtn = new Button("Insert at Tail");
        Button deleteHeadBtn = new Button("Delete Head");
        Button deleteTailBtn = new Button("Delete Tail");
        Label statusLabel = new Label("Size: 0");

        // Make final copies for lambda
        final TextField vf = valueField;
        final Label sl = statusLabel;

        insertHeadBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                csll.insertAtHead(val);
                vf.clear();
                drawNodes();
                sl.setText("Size: " + csll.getSize());
            }
        });

        insertTailBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                csll.insertAtTail(val);
                vf.clear();
                drawNodes();
                sl.setText("Size: " + csll.getSize());
            }
        });

        deleteHeadBtn.setOnAction(e -> {
            csll.deleteHead();
            drawNodes();
            sl.setText("Size: " + csll.getSize());
        });

        deleteTailBtn.setOnAction(e -> {
            csll.deleteTail();
            drawNodes();
            sl.setText("Size: " + csll.getSize());
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

        if (csll.isEmpty()) {
            Label emptyLabel = new Label("Circular Singly Linked List is empty");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            emptyLabel.setLayoutX(250);
            emptyLabel.setLayoutY(200);
            canvas.getChildren().add(emptyLabel);
            return;
        }

        java.util.List<CSLL.Node> nodes = csll.getAllNodes();
        double startX = 200; // left margin
        double y = 250;      // vertical position
        double gap = 200;    // space between nodes

        // Draw nodes and arrows
        for (int i = 0; i < nodes.size(); i++) {
            CSLL.Node node = nodes.get(i);

            double x = startX + i * gap;
            drawLinearNode(nodes.get(i), x, y, i);

            // Arrow to next node
            if (i < nodes.size() - 1) {
                drawArrow(x + 73, y, x + gap - 75, y, Color.GREEN, 2);
            }
        }

        // Draw circular connection (tail → head)
        double tailX = startX + (nodes.size() - 1) * gap;
        double headX = startX;
        double offsetY = 80; // vertical offset for the return arrow

        drawStraightCircularArrow(tailX + 35, y, headX - 35, y + offsetY, Color.RED, 2);

        // Draw HEAD and TAIL pointers
        drawPointer(headX - 75, y, "HEAD", Color.CRIMSON, -60);
        drawPointer(tailX + 75, y, "TAIL", Color.DARKORANGE, 60);
    }


    private void drawArrow(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        // Main line
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);

        // Calculate angle for arrowhead
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowLength = 15; // make it a bit longer
        double arrowAngle = Math.PI / 8; // narrower, looks cleaner

        // Arrowhead lines
        Line arrow1 = new Line(
                endX, endY,
                endX - arrowLength * Math.cos(angle - arrowAngle),
                endY - arrowLength * Math.sin(angle - arrowAngle)
        );

        Line arrow2 = new Line(
                endX, endY,
                endX - arrowLength * Math.cos(angle + arrowAngle),
                endY - arrowLength * Math.sin(angle + arrowAngle)
        );

        arrow1.setStroke(color);
        arrow2.setStroke(color);
        arrow1.setStrokeWidth(strokeWidth);
        arrow2.setStrokeWidth(strokeWidth);

        canvas.getChildren().addAll(line, arrow1, arrow2);
    }



    private void drawPointer(double nodeX, double nodeY, String label, Color color, double offset) {
        double pointerX = nodeX + offset;
        double pointerY = nodeY;

        // Pointer line
        Line pointerLine = new Line(pointerX, pointerY, nodeX, nodeY);
        pointerLine.setStroke(color);
        pointerLine.setStrokeWidth(3);

        // Pointer label
        Text pointerLabel = new Text(label);
        pointerLabel.setFill(color);
        pointerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        if (offset > 0) {
            pointerLabel.setX(pointerX + 5);
        } else {
            pointerLabel.setX(pointerX - 35);
        }
        pointerLabel.setY(pointerY - 10);

        canvas.getChildren().addAll(pointerLine, pointerLabel);
    }

    private void highlightNode(int position, Color color) {
        java.util.List<CSLL.Node> nodes = csll.getAllNodes();
        if (position >= 0 && position < nodes.size()) {
            double centerX = 400;
            double centerY = 250;
            double radius = 150;

            double angle = 2 * Math.PI * position / nodes.size();
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            // Create highlight circle
            Circle highlight = new Circle(35);
            highlight.setFill(color);
            highlight.setOpacity(0.3);
            highlight.setStroke(Color.DARKGREEN);
            highlight.setStrokeWidth(3);
            highlight.setCenterX(x);
            highlight.setCenterY(y);

            // Add behind existing nodes
            canvas.getChildren().add(0, highlight);
        }
    }

    public VBox getLayout() {
        return layout;
    }

    private void drawLinearNode(CSLL.Node node, double x, double y, int index) {
        double rectWidth = 70;
        double rectHeight = 50;
        double spacing = 5; // space between rectangles

        // Left rectangle for node data
        Rectangle nodeBox = new Rectangle(rectWidth, rectHeight);
        nodeBox.setArcWidth(10);
        nodeBox.setArcHeight(10);
        nodeBox.setFill(Color.LIGHTBLUE);
        nodeBox.setStroke(Color.DARKBLUE);
        nodeBox.setStrokeWidth(2);
        nodeBox.setX(x - rectWidth - spacing / 2); // position left
        nodeBox.setY(y - rectHeight / 2);

        Text dataText = new Text(node.data);
        dataText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        dataText.setFill(Color.DARKBLUE);
        dataText.setX(nodeBox.getX() + (rectWidth - dataText.getLayoutBounds().getWidth()) / 2);
        dataText.setY(nodeBox.getY() + (rectHeight + dataText.getLayoutBounds().getHeight()) / 2);

        // Right rectangle for node address
        Rectangle nodeAddrBox = new Rectangle(rectWidth, rectHeight);
        nodeAddrBox.setArcWidth(10);
        nodeAddrBox.setArcHeight(10);
        nodeAddrBox.setFill(Color.LIGHTYELLOW);
        nodeAddrBox.setStroke(Color.DARKGOLDENROD);
        nodeAddrBox.setX(x + spacing / 2); // position right
        nodeAddrBox.setY(y - rectHeight / 2);

        Text addrText = new Text(node.address);
        addrText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addrText.setFill(Color.DARKGOLDENROD);
        addrText.setX(nodeAddrBox.getX() + (rectWidth - addrText.getLayoutBounds().getWidth()) / 2);
        addrText.setY(nodeAddrBox.getY() + (rectHeight + addrText.getLayoutBounds().getHeight()) / 2);

        canvas.getChildren().addAll(nodeBox, dataText, nodeAddrBox, addrText);
    }


    private void drawStraightCircularArrow(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        // Line going downward from tail
        Line down = new Line(startX+15, startY + 30, startX+15, startY + 81);
        down.setStroke(color);
        down.setStrokeWidth(strokeWidth);

        // Horizontal line going left to head
        Line back = new Line(startX+15, startY + 81, endX-15, endY);
        back.setStroke(color);
        back.setStrokeWidth(strokeWidth);

        // Small line going up to head connection
        Line up = new Line(endX-15, endY, endX-15, startY + 30);
        up.setStroke(color);
        up.setStrokeWidth(strokeWidth);

        // Arrow head at end (up direction)
        double arrowLength = 10;
        Line arrow1 = new Line(endX - 15, startY - arrowLength + 37, endX - arrowLength - 15, startY + 37);
        arrow1.setStrokeWidth(strokeWidth);
        Line arrow2 = new Line(endX - 15, startY - arrowLength + 37, endX + arrowLength - 15, startY + 37);
        arrow2.setStrokeWidth(strokeWidth);
        arrow1.setStroke(color);
        arrow2.setStroke(color);

        // Label “Circular”
        Text label = new Text("Circular Link");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setFill(color);
        label.setX((startX + endX) / 2 - 30);
        label.setY(startY + 95);

        canvas.getChildren().addAll(down, back, up, arrow1, arrow2, label);
    }
}


// For header pointer add '>' and for tail pointer add '<' and add the address like singly linked list
