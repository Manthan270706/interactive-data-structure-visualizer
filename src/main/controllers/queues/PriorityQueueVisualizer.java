package main.controllers.queues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import main.models.queues.PriorityQueue;

public class PriorityQueueVisualizer {
    private VBox layout;
    private HBox controlPanel;
    private Pane canvas;
    private PriorityQueue queue;
    private static final int CAPACITY = 8;


    public PriorityQueueVisualizer() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));

        queue = new PriorityQueue(CAPACITY);
        initializeUI();
    }

    private void initializeUI() {
        Label title = new Label("Priority Queue (Max-Heap)");
        title.getStyleClass().add("section-title");

        controlPanel = createControlPanel();

        canvas = new Pane();
        canvas.setPrefHeight(650);
        canvas.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        layout.getChildren().addAll(title, controlPanel, canvas);
        drawQueue();
    }

    private HBox createControlPanel() {
        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER_LEFT);

        TextField valueField = new TextField();
        valueField.setPromptText("Enter number");
        valueField.setPrefWidth(120);

        Button enqueueBtn = new Button("Enqueue");
        Button dequeueBtn = new Button("Dequeue");
        Button clearBtn = new Button("Clear");

        Label statusLabel = new Label("Size: 0/" + CAPACITY);

        enqueueBtn.setOnAction(e -> {
            String valueStr = valueField.getText().trim();

            if (!valueStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    if (queue.enqueue(value)) {
                        valueField.clear();
                        drawQueue();
                        statusLabel.setText("Size: " + queue.getSize() + "/" + CAPACITY);
                    } else {
                        showAlert("Queue Full", "Cannot enqueue. Priority queue is full!");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Number", "Please enter a valid integer.");
                }
            } else {
                showAlert("Input Error", "Please enter a number.");
            }
        });

        dequeueBtn.setOnAction(e -> {
            Integer dequeuedValue = queue.dequeue();
            if (dequeuedValue != null) {
                drawQueue();
                statusLabel.setText("Size: " + queue.getSize() + "/" + CAPACITY);
                showAlert("Dequeue Successful", "Dequeued value: " + dequeuedValue);
            } else {
                showAlert("Queue Empty", "Cannot dequeue. Priority queue is empty!");
            }
        });

        clearBtn.setOnAction(e -> {
            queue.clear();
            drawQueue();
            statusLabel.setText("Size: 0/" + CAPACITY);
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

        // Existing array representation
        double startX = 100;
        double startY = 150;
        double nodeWidth = 80;
        double nodeHeight = 40;
        double gap = 10;

        int[] heapArray = queue.getHeapArray();

        // Draw array view (as before)
        for (int i = 0; i < CAPACITY; i++) {
            double x = startX + (i * (nodeWidth + gap));

            Rectangle slot = new Rectangle(x, startY, nodeWidth, nodeHeight);
            slot.setStroke(Color.BLACK);
            slot.setStrokeWidth(2);
            slot.setFill(i < queue.getSize() ? Color.LIGHTBLUE : Color.LIGHTGRAY);

            Text indexText = new Text(x + nodeWidth/2 - 15, startY - 5, "[" + i + "]");
            indexText.setStyle("-fx-font-size: 10px;");

            String displayValue = (i < queue.getSize()) ? String.valueOf(heapArray[i]) : "empty";
            Text valueText = new Text(x + 5, startY + nodeHeight/2 + 5, displayValue);
            valueText.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

            canvas.getChildren().addAll(slot, indexText, valueText);

            if (i == 0 && i < queue.getSize()) {
                drawRootPointer(x + nodeWidth/2, startY);
            }
        }

        // Draw heap tree visualization below
        drawHeapTree(heapArray, queue.getSize());

        // Info text
        drawQueueInfo(startX + 400, startY - 100);
    }


    private void drawRootPointer(double centerX, double bottomY) {
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

        // Root label
        Text rootLabel = new Text("ROOT (Highest Priority)");
        rootLabel.setFill(Color.GREEN);
        rootLabel.setStyle("-fx-font-weight: bold;");
        rootLabel.setLayoutX(centerX - 60);
        rootLabel.setLayoutY(bottomY - pointerLength - 10);

        canvas.getChildren().addAll(pointer, arrowHead1, arrowHead2, rootLabel);
    }

    private void drawQueueInfo(double startX, double startY) {
        Integer highestPriority = queue.peek();
        String infoText = "Highest Priority: " + (highestPriority != null ? highestPriority : "None") +
                " | Size: " + queue.getSize() + "/" + CAPACITY +
                " | Next to dequeue: " + (highestPriority != null ? highestPriority : "None");

        Text info = new Text(startX, startY, infoText);
        info.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        canvas.getChildren().add(info);

        // Add explanation text
        Text explanation = new Text(startX, startY + 25, "Note: Larger numbers have higher priority (Max-Heap)");
        explanation.setStyle("-fx-font-size: 12px; -fx-fill: #666;");
        canvas.getChildren().add(explanation);
    }

    private void drawEmptyQueue() {
        Label emptyLabel = new Label("Priority Queue is empty");
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

    private void drawHeapTree(int[] heapArray, int size) {
        if (size == 0) return;

        double canvasWidth = canvas.getWidth();
        double startY = 250;
        double nodeRadius = 20;

        // Calculate positions dynamically
        for (int i = 0; i < size; i++) {
            int level = (int) (Math.log(i + 1) / Math.log(2));
            int indexInLevel = i - (int) (Math.pow(2, level) - 1);

            double nodesInLevel = Math.pow(2, level);
            double horizontalGap = canvasWidth / (nodesInLevel + 1);

            double x = (indexInLevel + 1) * horizontalGap;
            double y = startY + level * 80;  // vertical gap between levels

            // Draw connecting line to parent (if not root)
            if (i > 0) {
                int parent = (i - 1) / 2;
                int parentLevel = (int) (Math.log(parent + 1) / Math.log(2));
                int parentIndexInLevel = parent - (int) (Math.pow(2, parentLevel) - 1);
                double parentNodesInLevel = Math.pow(2, parentLevel);
                double parentHorizontalGap = canvasWidth / (parentNodesInLevel + 1);

                double parentX = (parentIndexInLevel + 1) * parentHorizontalGap;
                double parentY = startY + parentLevel * 80;

                Line line = new Line(parentX, parentY + nodeRadius, x, y - nodeRadius);
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(2);
                canvas.getChildren().add(line);
            }

            // Draw node circle
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(x, y, nodeRadius);
            circle.setFill(Color.LIGHTBLUE);
            circle.setStroke(Color.DARKBLUE);
            circle.setStrokeWidth(2);

            // Draw value inside circle
            Text text = new Text(String.valueOf(heapArray[i]));
            text.setStyle("-fx-font-weight: bold;");
            text.setX(x - 7);
            text.setY(y + 5);

            canvas.getChildren().addAll(circle, text);
        }
    }

}