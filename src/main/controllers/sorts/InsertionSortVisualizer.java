package main.controllers.sorts;

import main.models.sorts.InsertionSortAlgorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class InsertionSortVisualizer {
    private VBox content;
    private Pane visualPane;
    private Text stepText;
    private InsertionSortAlgorithm sorter;
    private List<Integer> array = new ArrayList<>();
    private List<Integer> originalInput = null;
    private int currentStep = -1;

    public InsertionSortVisualizer() {
        sorter = new InsertionSortAlgorithm();
        createUI();
    }

    private void createUI() {
        VBox main = new VBox(15);
        main.setPadding(new Insets(20));

        Label header = new Label("Insertion Sort");
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
        visualPane.setPrefHeight(300);
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
        drawArray(array, -1, -1, "");
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
            drawArray(originalInput, -1, -1, "");
            stepText.setText("At starting position.");
            currentStep = -1;
        }
    }

    private void showStep(int index) {
        String desc = sorter.getDescriptions().get(index);
        stepText.setText(desc);
        int[] pair = sorter.getCompares().get(index);
        drawArray(toList(sorter.getHistory().get(index)), pair[0], pair[1], desc);
    }

    private void drawArray(List<Integer> arr, int i1, int i2, String desc) {
        visualPane.getChildren().clear();
        double paneWidth = visualPane.getWidth();
        double width = 60, spacing = 10;
        double totalWidth = arr.size() * (width + spacing);
        double startX = Math.max(20, (paneWidth - totalWidth) / 2);

        for (int k = 0; k < arr.size(); k++) {
            Rectangle rect = new Rectangle(width, 50);
            rect.setX(startX + k * (width + spacing));
            rect.setY(150);
            rect.setArcWidth(10);
            rect.setArcHeight(10);
            rect.setFill(Color.WHITE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);

            if (desc.contains("Considering") && k == i1)
                rect.setStroke(Color.ORANGE);
            else if (desc.contains("Comparing") && (k == i1 || k == i2))
                rect.setStroke(Color.DODGERBLUE);
            else if (desc.contains("Shifted") && (k == i1))
                rect.setStroke(Color.RED);
            else if (desc.contains("Placed") && k == i2)
                rect.setStroke(Color.GREEN);
            else if (desc.contains("correct position") && k == i1)
                rect.setStroke(Color.LIMEGREEN);

            Text txt = new Text(String.valueOf(arr.get(k)));
            txt.setFont(Font.font("Segoe UI", 16));
            txt.setX(rect.getX() + width / 2 - 8);
            txt.setY(rect.getY() + 30);
            visualPane.getChildren().addAll(rect, txt);
        }

        // Double-headed arrow for comparing step
        if (desc.contains("Comparing") && i1 >= 0 && i2 >= 0) {
            double x1 = startX + i1 * (width + spacing) + width / 2;
            double x2 = startX + i2 * (width + spacing) + width / 2;
            double y = 140;

            QuadCurve curve = new QuadCurve(x1, y, (x1 + x2) / 2, y - 40, x2, y);
            curve.setStroke(Color.DODGERBLUE);
            curve.setStrokeWidth(2);
            curve.setFill(Color.TRANSPARENT);

            double offset = -5;
            Polygon leftHead = new Polygon(x1 - offset, y, x1 - offset - 10, y - 5, x1 - offset - 10, y + 5);
            leftHead.setRotate(180);
            leftHead.setFill(Color.DODGERBLUE);
            Polygon rightHead = new Polygon(x2 + offset, y, x2 + offset + 10, y - 5, x2 + offset + 10, y + 5);
            rightHead.setRotate(180);
            rightHead.setFill(Color.DODGERBLUE);

            visualPane.getChildren().addAll(curve, leftHead, rightHead);
        }

        // Arrow for placed element
        if (desc.contains("Placed")) {
            Line arrow = new Line();
            arrow.setStartX(startX + i2 * (width + spacing) + width / 2);
            arrow.setStartY(120);
            arrow.setEndX(startX + i2 * (width + spacing) + width / 2);
            arrow.setEndY(150);
            arrow.setStrokeWidth(2);
            arrow.setStroke(Color.GREEN);
            visualPane.getChildren().add(arrow);
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