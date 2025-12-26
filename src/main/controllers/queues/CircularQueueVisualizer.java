
package main.controllers.queues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.models.queues.CircularQueue;

public class CircularQueueVisualizer {
    private VBox layout;
    private HBox controlPanel;
    private Pane canvas;
    private CircularQueue queue;
    private static final int CAPACITY = 6;

    public CircularQueueVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));

        queue = new CircularQueue(CAPACITY);
        initializeUI();
    }

    private void initializeUI() {
        // Section title
        Label title = new Label("Circular Queue");
        title.getStyleClass().add("section-title");

        // Control panel
        controlPanel = createControlPanel();

        // Canvas for drawing queue
        canvas = new Pane();
        canvas.setPrefHeight(500); // Slightly taller for circular visualization
        canvas.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        layout.getChildren().addAll(title, controlPanel, canvas);

        // Draw initial empty queue
        drawQueue();
    }

    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        TextField valueField = new TextField();
        valueField.setPromptText("Enter value");
        valueField.setPrefWidth(120);

        Button enqueueBtn = new Button("Enqueue");
        Button dequeueBtn = new Button("Dequeue");
        Button clearBtn = new Button("Clear Queue");

        Label statusLabel = new Label("Size: 0/" + CAPACITY);

        final TextField vf = valueField;
        final Label sl = statusLabel;

        enqueueBtn.setOnAction(e -> {
            String val = vf.getText().trim();
            if (!val.isEmpty()) {
                if (queue.enqueue(val)) {
                    vf.clear();
                    drawQueue();
                    sl.setText("Size: " + queue.getSize() + "/" + CAPACITY);
                } else {
                    showAlert("Queue Full", "Cannot enqueue. Circular queue is full!");
                }
            }
        });

        dequeueBtn.setOnAction(e -> {
            String dequeuedValue = queue.dequeue();
            if (dequeuedValue != null) {
                drawQueue();
                sl.setText("Size: " + queue.getSize() + "/" + CAPACITY);
                showAlert("Dequeue Successful", "Dequeued value: " + dequeuedValue);
            } else {
                showAlert("Queue Empty", "Cannot dequeue. Circular queue is empty!");
            }
        });


        clearBtn.setOnAction(e -> {
            queue.clear();
            drawQueue();
            sl.setText("Size: 0/" + CAPACITY);
        });

        box.getChildren().addAll(valueField, enqueueBtn, dequeueBtn, clearBtn, statusLabel);
        return box;
    }

    private void drawQueue() {
        canvas.getChildren().clear();

        if (queue.isEmpty()) {
            drawEmptyQueue();
            return;
        }

        double startX = 120;
        double startY = 200;
        double nodeWidth = 80;
        double nodeHeight = 40;
        double gap = 10;

        String[] queueArray = queue.getQueueArray();
        int frontIndex = queue.getFrontIndex();
        int rearIndex = queue.getRearIndex();

        // Draw all queue slots
        for (int i = 0; i < CAPACITY; i++) {
            double x = startX + (i * (nodeWidth + gap));

            // Queue slot rectangle
            Rectangle slot = new Rectangle(x, startY, nodeWidth, nodeHeight);
            slot.setStroke(Color.BLACK);
            slot.setStrokeWidth(2);

            // Fill color based on content
            if (queueArray[i] != null) {
                slot.setFill(Color.LIGHTBLUE);
            } else {
                slot.setFill(Color.LIGHTGRAY);
            }

            // Index label
            Text indexText = new Text(x + nodeWidth/2 - 15, startY - 5, "[" + i + "]");
            indexText.setStyle("-fx-font-size: 10px;");

            // Value text
            Text valueText = new Text(x + 5, startY + nodeHeight/2 + 5,
                    queueArray[i] != null ? queueArray[i] : "empty");
            valueText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

            canvas.getChildren().addAll(slot, indexText, valueText);

            // Highlight front and rear
            if (i == frontIndex) {
                drawFrontPointer(x + nodeWidth/2, startY);
            }
            if (i == rearIndex) {
                drawRearPointer(x + nodeWidth/2, startY + nodeHeight);
            }
        }

        // Draw info text
        drawQueueInfo(startX, startY + 200);
    }

    private void drawEmptyQueue() {
        Label emptyLabel = new Label("Circular Queue is empty");
        emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        emptyLabel.setLayoutX(280);
        emptyLabel.setLayoutY(400);
        canvas.getChildren().add(emptyLabel);

        // Still draw the empty slots for visualization
        double startX = 120;
        double startY = 200;
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

        // Vertical line pointing down to the element
        Line pointer = new Line(centerX, bottomY - pointerLength, centerX, bottomY - 5);
        pointer.setStroke(Color.GREEN);
        pointer.setStrokeWidth(3);

        // Arrow head
        Line arrowHead1 = new Line(centerX - 5, bottomY - pointerLength + 10, centerX, bottomY - pointerLength);
        Line arrowHead2 = new Line(centerX + 5, bottomY - pointerLength + 10, centerX, bottomY - pointerLength);
        arrowHead1.setStroke(Color.GREEN);
        arrowHead2.setStroke(Color.GREEN);
        arrowHead1.setStrokeWidth(3);
        arrowHead2.setStrokeWidth(3);

        // Front label
        Text frontLabel = new Text("FRONT");
        frontLabel.setFill(Color.GREEN);
        frontLabel.setStyle("-fx-font-weight: bold;");
        frontLabel.setLayoutX(centerX - 20);
        frontLabel.setLayoutY(bottomY - pointerLength - 10);

        canvas.getChildren().addAll(pointer, arrowHead1, arrowHead2, frontLabel);
    }

    private void drawRearPointer(double centerX, double topY) {
        double pointerLength = 60;

        // Vertical line pointing up to the element
        Line pointer = new Line(centerX, topY + 5, centerX, topY + pointerLength);
        pointer.setStroke(Color.RED);
        pointer.setStrokeWidth(3);

        // Arrow head
        Line arrowHead1 = new Line(centerX - 5, topY + pointerLength - 10, centerX, topY + pointerLength);
        Line arrowHead2 = new Line(centerX + 5, topY + pointerLength - 10, centerX, topY + pointerLength);
        arrowHead1.setStroke(Color.RED);
        arrowHead2.setStroke(Color.RED);
        arrowHead1.setStrokeWidth(3);
        arrowHead2.setStrokeWidth(3);

        // Rear label
        Text rearLabel = new Text("REAR");
        rearLabel.setFill(Color.RED);
        rearLabel.setStyle("-fx-font-weight: bold;");
        rearLabel.setLayoutX(centerX - 15);
        rearLabel.setLayoutY(topY + pointerLength + 20);

        canvas.getChildren().addAll(pointer, arrowHead1, arrowHead2, rearLabel);
    }





    private void drawQueueInfo(double startX, double startY) {
        String frontValue = queue.peekFront();
        String infoText = "Front: " + (frontValue != null ? frontValue : "None") +
                " | Size: " + queue.getSize() + "/" + CAPACITY +
                " | Front Index: " + queue.getFrontIndex() +
                " | Rear Index: " + queue.getRearIndex() +
                " | Circular: " + (queue.getSize() > 0 && queue.getRearIndex() < queue.getFrontIndex() ? "Yes" : "No");

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