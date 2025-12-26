// File: main/controllers/StackVisualizer.java
package main.controllers.stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.models.stack.Stack;

public class StackVisualizer {
    private VBox layout;
    private HBox controlPanel;
    private Pane stackDisplay;
    private Label infoLabel;
    private Stack stack;

    public StackVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f5f5f5;");

        stack = new Stack();

        // Section title
        Label title = new Label("Stack Visualizer (LIFO) - Using Linked List");
        title.getStyleClass().add("section-title");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Control panel
        controlPanel = createControlPanel();

        // Stack display area
        stackDisplay = createStackDisplay();

        // Information display
        infoLabel = new Label("Top element: (none)");
        infoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
        infoLabel.setPadding(new Insets(10));

        layout.getChildren().addAll(title, controlPanel, stackDisplay, infoLabel);

        // Initial draw
        drawStack();
    }

    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

        TextField valueField = new TextField();
        valueField.setPromptText("Enter value");
        valueField.setPrefWidth(120);

        Button pushBtn = new Button("Push");
        Button popBtn = new Button("Pop");
        Button showTopBtn = new Button("Show Top");

        // Style buttons consistently with LinkedListVisualizer
        pushBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        popBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        showTopBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        pushBtn.setOnAction(e -> {
            String val = valueField.getText().trim();
            if (!val.isEmpty()) {
                animatePush(val);
                valueField.clear();
            }
        });

        popBtn.setOnAction(e -> animatePop());

        showTopBtn.setOnAction(e -> showTop());

        box.getChildren().addAll(
                new Label("Value:"), valueField, pushBtn, popBtn, showTopBtn
        );

        return box;
    }

    private Pane createStackDisplay() {
        Pane pane = new Pane();
        pane.setPrefSize(400, 400);
        pane.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");
        return pane;
    }

    private void animatePush(String value) {
        stack.push(value);
        drawStack();
        updateInfoLabel();

        // Animation effect - highlight the new top element
        Timeline flash = new Timeline(
//                new KeyFrame(Duration.seconds(0.1), e -> highlightTopElement()),
                new KeyFrame(Duration.seconds(0.2), e -> drawStack())
//                new KeyFrame(Duration.seconds(0.3), e -> highlightTopElement())
        );
        flash.setCycleCount(2);
        flash.play();
    }

    private void animatePop() {
        if (stack.isEmpty()) {
            showAlert("Stack is empty, cannot pop.");
            return;
        }

        String poppedValue = stack.pop();

        // Show what was popped before redrawing
        showAlert("Popped element: " + poppedValue);

        drawStack();
        updateInfoLabel();
    }

    private void showTop() {
        if (stack.isEmpty()) {
            showAlert("Stack is empty.");
            return;
        }

        // Flash animation for top element
        Timeline flash = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> highlightTopElement()),
                new KeyFrame(Duration.seconds(0.2), e -> drawStack()),
                new KeyFrame(Duration.seconds(0.3), e -> highlightTopElement())
        );
        flash.setCycleCount(3);
        flash.play();

        updateInfoLabel();
    }

    private void drawStack() {
        stackDisplay.getChildren().clear();

        if (stack.isEmpty()) {
            Label emptyLabel = new Label("Stack is empty");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #7f8c8d;");
            emptyLabel.setLayoutX(150);
            emptyLabel.setLayoutY(180);
            stackDisplay.getChildren().add(emptyLabel);
            return;
        }

        // === Stack container dimensions ===
        double x = 120;
        double y = 70;
        double width = 140;
        double height = 300;

        // Draw 3 sides (left, right, bottom) — top border removed
        Line leftBorder = new Line(x, y, x, y + height);
        Line rightBorder = new Line(x + width, y, x + width, y + height);
        Line bottomBorder = new Line(x, y + height, x + width, y + height);

        leftBorder.setStroke(Color.GRAY);
        rightBorder.setStroke(Color.GRAY);
        bottomBorder.setStroke(Color.GRAY);
        leftBorder.setStrokeWidth(2);
        rightBorder.setStrokeWidth(2);
        bottomBorder.setStrokeWidth(2);

        stackDisplay.getChildren().addAll(leftBorder, rightBorder, bottomBorder);

        // === Draw stack elements ===
        var nodes = stack.getLinkedList().getAllNodes();
        double startY = 375; // lower so elements sit near base
        double elementHeight = 50;
        double spacing = 2;

        for (int i = 0; i < nodes.size(); i++) {
            boolean isTop = (i == 0);
            VBox nodeView = createStackNode(nodes.get(i).data, isTop);
            nodeView.setLayoutX(x + 10);
            nodeView.setLayoutY(startY - (nodes.size() - i) * (elementHeight + spacing));
            stackDisplay.getChildren().add(nodeView);
        }

        // Draw top pointer if stack has elements
        if (!stack.isEmpty()) {
            drawTopPointer();
        }
    }

    private VBox createStackNode(String data, boolean isTop) {
        // Main rectangle for the stack element
        Rectangle rect = new Rectangle(120, 40);
        rect.setFill(isTop ? Color.LIGHTYELLOW : Color.LIGHTBLUE);
        rect.setStroke(Color.DARKBLUE);
        rect.setStrokeWidth(2);
        rect.setArcWidth(10);
        rect.setArcHeight(10);

        // Data label
        Label dataLabel = new Label(data);
        dataLabel.setFont(Font.font(16));
        dataLabel.setTextFill(Color.BLACK);
        dataLabel.setStyle("-fx-font-weight: bold;");

        // Stack element container (overlay label on rectangle)
        StackPane elementBox = new StackPane();
        elementBox.setAlignment(Pos.CENTER);
        elementBox.getChildren().addAll(rect, dataLabel);
        elementBox.setPrefSize(120, 40);

        // Wrap inside VBox for layout consistency
        VBox wrapper = new VBox(elementBox);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPrefSize(120, 40);

        return wrapper;
    }

    private void drawTopPointer() {
        if (stack.isEmpty()) return;

        // Match element positions from drawStack
        double startY = 375; // Bottom of container
        double elementHeight = 50;
        double spacing = 2;
        double topElementY = startY - stack.size() * (elementHeight + spacing);

        double middleY = topElementY + (elementHeight / 2) - 5;

        // Pointer comes from right side
        double pointerStartX = 130 + 120 + 60; // Right side of element + gap
        double pointerEndX = 130 + 120+ 15;   // Right edge of element

        // Arrow line (horizontal, pointing left to the element)
        Line arrowLine = new Line(pointerStartX, middleY, pointerEndX, middleY);
        arrowLine.setStroke(Color.CRIMSON);
        arrowLine.setStrokeWidth(3);

        // Arrow head (left-pointing)
        Polygon arrowHead = new Polygon();
        arrowHead.getPoints().addAll(
                pointerEndX + 7, middleY - 8,
                pointerEndX + 7, middleY + 8,
                pointerEndX - 3, middleY
        );
        arrowHead.setFill(Color.CRIMSON);

        // "TOP" label to the right of the arrow
        Text topLabel = new Text("TOP");
        topLabel.setFill(Color.CRIMSON);
        topLabel.setStyle("-fx-font-weight: bold;");
        topLabel.setLayoutX(pointerStartX + 40);
        topLabel.setLayoutY(middleY + 5);

        stackDisplay.getChildren().addAll(arrowLine, arrowHead, topLabel);
    }


    private void highlightTopElement() {
        if (stack.isEmpty()) return;

        // Create a highlight effect for the top element
        double startY = 375;
        double elementHeight = 50;
        double spacing = 2;

        double highlightY = startY - stack.size() * (elementHeight + spacing);

        Rectangle highlight = new Rectangle(125, highlightY - 5, 130, 50);
        highlight.setFill(Color.GREEN);
        highlight.setOpacity(0.3);
        highlight.setStroke(Color.DARKGREEN);
        highlight.setStrokeWidth(2);

        stackDisplay.getChildren().add(0, highlight);
    }

    private void updateInfoLabel() {
        if (stack.isEmpty()) {
            infoLabel.setText("Top element: (none) | Stack size: 0");
        } else {
            infoLabel.setText("Top element: " + stack.peek() + " | Stack size: " + stack.size());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stack Operation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getLayout() {
        return layout;
    }
}