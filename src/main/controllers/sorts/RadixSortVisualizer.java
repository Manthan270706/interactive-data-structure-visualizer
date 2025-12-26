package main.controllers.sorts;

import main.models.sorts.RadixSortAlgorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RadixSortVisualizer {
    private VBox content;
    private Pane visualPane;
    private Text stepText;
    private RadixSortAlgorithm sorter;
    private List<Integer> array = new ArrayList<>();
    private List<Integer> originalInput = null;
    private int currentStep = -1;

    public RadixSortVisualizer() {
        sorter = new RadixSortAlgorithm();
        createUI();
    }

    private void createUI() {
        VBox main = new VBox(15);
        main.setPadding(new Insets(20));

        Label header = new Label("Radix Sort");
        header.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));

        HBox inputBox = new HBox(10);
        TextField inputField = new TextField();
        inputField.setPromptText("Enter numbers separated by spaces");
        Button setArrayBtn = new Button("Set Array");
        Button prevBtn = new Button("Prev Step");
        Button nextBtn = new Button("Next Step");
        Button resetBtn = new Button("Reset");

        styleMainButton(setArrayBtn);
        styleMainButton(prevBtn);
        styleMainButton(nextBtn);
        styleMainButton(resetBtn);

        inputBox.getChildren().addAll(inputField, setArrayBtn, prevBtn, nextBtn, resetBtn);
        inputBox.setAlignment(Pos.CENTER_LEFT);

        visualPane = new Pane();
        visualPane.setPrefHeight(650);
        visualPane.setStyle("-fx-background-color: #fafafa; -fx-border-color: #ccc; -fx-border-radius: 5;");

        stepText = new Text("");
        stepText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        stepText.setFill(Color.BLACK);

        main.getChildren().addAll(header, inputBox, visualPane, stepText);
        content = main;

        setArrayBtn.setOnAction(e -> resetArray(inputField.getText()));
        nextBtn.setOnAction(e -> nextStep());
        prevBtn.setOnAction(e -> prevStep());
        resetBtn.setOnAction(e -> {
            resetArray("");
            stepText.setText("");
        });
    }

    private void styleMainButton(Button btn) {
        btn.setFont(Font.font("Segoe UI", 14));
        btn.setStyle("-fx-background-color: #0078d7; -fx-text-fill: white; -fx-background-radius: 5;");
    }

    private void resetArray(String input) {
        visualPane.getChildren().clear();
        array.clear();
        sorter.clear();
        stepText.setText("");
        currentStep = -1;

        if (input == null || input.trim().isEmpty()) return;

        for (String s : input.trim().split("\\s+")) {
            try {
                array.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }

        originalInput = new ArrayList<>(array);
        generateSteps();
        drawArray(array, -1, -1, false, "");
    }

    private void generateSteps() {
        int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
        sorter.sort(arr);
    }

    private void nextStep() {
        if (currentStep + 1 < sorter.getHistory().size()) {
            currentStep++;
            showStep(currentStep);
        }
    }

    private void prevStep() {
        if (currentStep > 0) {
            currentStep--;
            showStep(currentStep);
        } else {
            drawArray(originalInput, -1, -1, false, "");
            stepText.setText("At starting position.");
            currentStep = -1;
        }
    }

    private void showStep(int index) {
        String desc = sorter.getDescriptions().get(index);
        stepText.setText(desc);
        int[] pair = sorter.getCompares().get(index);
        boolean highlight = desc.contains("Placing") || desc.contains("Moving");
        drawArray(toList(sorter.getHistory().get(index)), pair[0], pair[1], highlight, desc);
    }

    private void drawArray(List<Integer> arr, int compare1, int bucketDigit, boolean highlight, String desc) {
        visualPane.getChildren().clear();
        double paneWidth = visualPane.getWidth();
        double width = 60, spacing = 10;
        double totalWidth = arr.size() * (width + spacing);
        double startX = Math.max(20, (paneWidth - totalWidth) / 2);

        // Draw array boxes
        for (int k = 0; k < arr.size(); k++) {
            Rectangle rect = new Rectangle(width, 50);
            rect.setX(startX + k * (width + spacing));
            rect.setY(80);
            rect.setArcWidth(10);
            rect.setArcHeight(10);
            rect.setFill(Color.WHITE);
            rect.setStrokeWidth(2);

            if (desc.contains("Placing") && compare1 == k)
                rect.setStroke(Color.RED);
            else if (desc.contains("Moving") && bucketDigit == k)
                rect.setStroke(Color.GREEN);
            else
                rect.setStroke(Color.BLACK);

            Text txt = new Text(String.valueOf(arr.get(k)));
            txt.setFont(Font.font("Segoe UI", 16));
            txt.setX(rect.getX() + width / 2 - 8);
            txt.setY(rect.getY() + 30);
            visualPane.getChildren().addAll(rect, txt);
        }

        // Draw buckets
        double bucketStartX = Math.max(20, (paneWidth - (10 * (width + 10))) / 2);
        int stepIdx = Math.max(0, Math.min(currentStep, sorter.getBucketStates().size() - 1));
        List<List<Integer>> bucketSnapshot = sorter.getBucketStates().isEmpty() ? new ArrayList<>() : sorter.getBucketStates().get(stepIdx);

        for (int b = 0; b < 10; b++) {
            Rectangle bucket = new Rectangle(width, 40);
            bucket.setX(bucketStartX + b * (width + 10));
            bucket.setY(250);
            bucket.setArcWidth(8);
            bucket.setArcHeight(8);
            bucket.setFill(Color.WHITE);
            bucket.setStroke(Color.BLACK);

            if ((desc.contains("Placing") && bucketDigit == b) || (desc.contains("Moving") && compare1 == b))
                bucket.setFill(Color.LIGHTYELLOW);

            Text label = new Text(String.valueOf(b));
            label.setFont(Font.font("Segoe UI", 14));
            label.setX(bucket.getX() + 25);
            label.setY(bucket.getY() + 25);
            visualPane.getChildren().addAll(bucket, label);

            // Draw items inside each bucket below it
            if (bucketSnapshot.size() > b) {
                List<Integer> bucketVals = bucketSnapshot.get(b);
                for (int i = 0; i < bucketVals.size(); i++) {
                    Rectangle small = new Rectangle(width, 25);
                    small.setX(bucket.getX());
                    small.setY(300 + i * 30);
                    small.setArcWidth(8);
                    small.setArcHeight(8);
                    small.setFill(Color.WHITE);
                    small.setStroke(Color.BLACK);

                    Text val = new Text(String.valueOf(bucketVals.get(i)));
                    val.setFont(Font.font("Segoe UI", 13));
                    val.setX(small.getX() + width / 2 - 8);
                    val.setY(small.getY() + 17);
                    visualPane.getChildren().addAll(small, val);
                }
            }
        }
    }

    private List<Integer> toList(int[] a) {
        List<Integer> L = new ArrayList<>();
        for (int v : a) L.add(v);
        return L;
    }

    public VBox getContent() {
        return content;
    }
}