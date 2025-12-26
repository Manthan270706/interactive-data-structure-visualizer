package main.controllers.queues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.models.queues.Deque;

public class DequeVisualizer {
    private VBox layout;
    private HBox controlPanel;
    private Pane canvas;
    private Deque deque;
    private static final int CAPACITY = 8;


    public DequeVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));

        deque = new Deque(CAPACITY);
        initializeUI();
    }

    private void initializeUI() {
        Label title = new Label("Double Ended Queue (Deque)");
        title.getStyleClass().add("section-title");

        controlPanel = createControlPanel();

        canvas = new Pane();
        canvas.setPrefHeight(450);
        canvas.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        layout.getChildren().addAll(title, controlPanel, canvas);
        drawDeque();
    }

    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        TextField valueField = new TextField();
        valueField.setPromptText("Enter value");
        valueField.setPrefWidth(120);

        Button addFrontBtn = new Button("Add Front");
        Button addRearBtn = new Button("Add Rear");
        Button removeFrontBtn = new Button("Remove Front");
        Button removeRearBtn = new Button("Remove Rear");
        Button clearBtn = new Button("Clear");

        Label statusLabel = new Label("Size: 0/" + CAPACITY);

        addFrontBtn.setOnAction(e -> {
            String value = valueField.getText().trim();
            if (!value.isEmpty()) {
                if (deque.addFront(value)) {
                    valueField.clear();
                    drawDeque();
                    statusLabel.setText("Size: " + deque.getSize() + "/" + CAPACITY);
                } else {
                    showAlert("Deque Full", "Cannot add to front. Deque is full!");
                }
            } else {
                showAlert("Input Error", "Please enter a value.");
            }
        });

        addRearBtn.setOnAction(e -> {
            String value = valueField.getText().trim();
            if (!value.isEmpty()) {
                if (deque.addRear(value)) {
                    valueField.clear();
                    drawDeque();
                    statusLabel.setText("Size: " + deque.getSize() + "/" + CAPACITY);
                } else {
                    showAlert("Deque Full", "Cannot add to rear. Deque is full!");
                }
            } else {
                showAlert("Input Error", "Please enter a value.");
            }
        });

        removeFrontBtn.setOnAction(e -> {
            String removedValue = deque.removeFront();
            if (removedValue != null) {
                drawDeque();
                statusLabel.setText("Size: " + deque.getSize() + "/" + CAPACITY);
                showAlert("Remove Successful", "Removed from front: " + removedValue);
            } else {
                showAlert("Deque Empty", "Cannot remove from front. Deque is empty!");
            }
        });

        removeRearBtn.setOnAction(e -> {
            String removedValue = deque.removeRear();
            if (removedValue != null) {
                drawDeque();
                statusLabel.setText("Size: " + deque.getSize() + "/" + CAPACITY);
                showAlert("Remove Successful", "Removed from rear: " + removedValue);
            } else {
                showAlert("Deque Empty", "Cannot remove from rear. Deque is empty!");
            }
        });

        clearBtn.setOnAction(e -> {
            deque.clear();
            drawDeque();
            statusLabel.setText("Size: 0/" + CAPACITY);
        });

        box.getChildren().addAll(valueField, addFrontBtn, addRearBtn, removeFrontBtn, removeRearBtn, clearBtn, statusLabel);
        return box;
    }

    private void drawDeque() {
        canvas.getChildren().clear();

        if (deque.isEmpty()) {
            drawEmptyDeque();
            return;
        }

        double startX = 100;
        double startY = 150;
        double nodeWidth = 80;
        double nodeHeight = 40;
        double gap = 10;

        String[] dequeArray = deque.getDequeArray();
        int frontIndex = deque.getFrontIndex();
        int rearIndex = deque.getRearIndex();

        // Draw all deque slots
        for (int i = 0; i < CAPACITY; i++) {
            double x = startX + (i * (nodeWidth + gap));

            Rectangle slot = new Rectangle(x, startY, nodeWidth, nodeHeight);
            slot.setStroke(Color.BLACK);
            slot.setStrokeWidth(2);

            // Fill color based on content
            if (dequeArray[i] != null) {
                slot.setFill(Color.LIGHTBLUE);
            } else {
                slot.setFill(Color.LIGHTGRAY);
            }

            // Index label
            Text indexText = new Text(x + nodeWidth/2 - 15, startY - 5, "[" + i + "]");
            indexText.setStyle("-fx-font-size: 10px;");

            // Value text
            Text valueText = new Text(x + 5, startY + nodeHeight/2 + 5,
                    dequeArray[i] != null ? dequeArray[i] : "empty");
            valueText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

            canvas.getChildren().addAll(slot, indexText, valueText);

            // Highlight front and rear
            if (i == frontIndex && dequeArray[i] != null) {
                drawFrontPointer(x + nodeWidth/2, startY);
            }
            if (i == rearIndex && dequeArray[i] != null) {
                drawRearPointer(x + nodeWidth/2, startY + nodeHeight);
            }
        }

        // Draw info text
        drawDequeInfo(startX, startY + 200);
    }

    private void drawEmptyDeque() {
        Label emptyLabel = new Label("Deque is empty");
        emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        emptyLabel.setLayoutX(300);
        emptyLabel.setLayoutY(200);
        canvas.getChildren().add(emptyLabel);

        // Still draw the empty slots for visualization
        double startX = 100;
        double startY = 150;
        double nodeWidth = 80;
        double gap = 10;

        for (int i = 0; i < CAPACITY; i++) {
            double x = startX + (i * (nodeWidth + gap));

            Rectangle slot = new Rectangle(x, startY, nodeWidth, 40);
            slot.setStroke(Color.BLACK);
            slot.setStrokeWidth(2);
            slot.setFill(Color.LIGHTGRAY);

            Text indexText = new Text(x + nodeWidth/2 - 15, startY - 5, "[" + i + "]");
            indexText.setStyle("-fx-font-size: 10px;");

            Text valueText = new Text(x + 25, startY + 25, "empty");
            valueText.setStyle("-fx-font-size: 12px;");

            canvas.getChildren().addAll(slot, indexText, valueText);
        }
    }

    private void drawFrontPointer(double centerX, double bottomY) {
        double pointerLength = 60;

        Line pointer = new Line(centerX, bottomY - pointerLength, centerX, bottomY - 5);
        pointer.setStroke(Color.GREEN);
        pointer.setStrokeWidth(3);

        Line arrowHead1 = new Line(centerX - 5, bottomY - pointerLength + 10, centerX, bottomY - pointerLength);
        Line arrowHead2 = new Line(centerX + 5, bottomY - pointerLength + 10, centerX, bottomY - pointerLength);
        arrowHead1.setStroke(Color.GREEN);
        arrowHead2.setStroke(Color.GREEN);
        arrowHead1.setStrokeWidth(3);
        arrowHead2.setStrokeWidth(3);

        Text frontLabel = new Text("FRONT");
        frontLabel.setFill(Color.GREEN);
        frontLabel.setStyle("-fx-font-weight: bold;");
        frontLabel.setLayoutX(centerX - 20);
        frontLabel.setLayoutY(bottomY - pointerLength - 10);

        canvas.getChildren().addAll(pointer, arrowHead1, arrowHead2, frontLabel);
    }

    private void drawRearPointer(double centerX, double topY) {
        double pointerLength = 60;

        Line pointer = new Line(centerX, topY + 5, centerX, topY + pointerLength);
        pointer.setStroke(Color.RED);
        pointer.setStrokeWidth(3);

        Line arrowHead1 = new Line(centerX - 5, topY + pointerLength - 10, centerX, topY + pointerLength);
        Line arrowHead2 = new Line(centerX + 5, topY + pointerLength - 10, centerX, topY + pointerLength);
        arrowHead1.setStroke(Color.RED);
        arrowHead2.setStroke(Color.RED);
        arrowHead1.setStrokeWidth(3);
        arrowHead2.setStrokeWidth(3);

        Text rearLabel = new Text("REAR");
        rearLabel.setFill(Color.RED);
        rearLabel.setStyle("-fx-font-weight: bold;");
        rearLabel.setLayoutX(centerX - 15);
        rearLabel.setLayoutY(topY + pointerLength + 20);

        canvas.getChildren().addAll(pointer, arrowHead1, arrowHead2, rearLabel);
    }

    private void drawDequeInfo(double startX, double startY) {
        String frontValue = deque.peekFront();
        String rearValue = deque.peekRear();
        String infoText = "Front: " + (frontValue != null ? frontValue : "None") +
                " | Rear: " + (rearValue != null ? rearValue : "None") +
                " | Size: " + deque.getSize() + "/" + CAPACITY +
                " | Front Index: " + deque.getFrontIndex() +
                " | Rear Index: " + deque.getRearIndex();

        Text info = new Text(startX, startY, infoText);
        info.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        canvas.getChildren().add(info);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getLayout() {
        return layout;
    }
}