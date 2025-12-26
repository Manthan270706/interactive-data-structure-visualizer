package main.controllers.sorts;

import main.models.sorts.ShellSortAlgorithm;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ShellSortVisualizer {
    private VBox content;
    private Pane visualPane;
    private Text stepText;
    private ShellSortAlgorithm sorter;
    private List<Integer> array = new ArrayList<>();
    private List<Integer> originalInput = null;
    private int currentStep = -1;

    public ShellSortVisualizer() {
        sorter = new ShellSortAlgorithm();
        createUI();
    }

    private void createUI() {
        VBox main = new VBox(15);
        main.setPadding(new Insets(20));

        Label header = new Label("Shell Sort");
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
        drawArray(array, -1, -1, false);
    }

    private void generateSteps() {
        int[] arr = array.stream().mapToInt(Integer::intValue).toArray();
        sorter.sort(arr);
    }

    private void nextStep() {
        if (currentStep + 1 < sorter.getHistory().size()) {
            currentStep++;
            showStep(currentStep);
        } else {
            stepText.setText("Array is sorted!");
        }
    }

    private void prevStep() {
        if (currentStep > 0) {
            currentStep--;
            showStep(currentStep);
        } else {
            drawArray(originalInput, -1, -1, false);
            stepText.setText("At starting position.");
            currentStep = -1;
        }
    }

    private void showStep(int index) {
        String desc = sorter.getDescriptions().get(index);
        stepText.setText(desc);
        int[] pair = sorter.getCompares().get(index);
        int c1 = pair[0], c2 = pair[1];
        boolean shouldHighlight = desc.contains("Comparing") || desc.contains("Swapping");
        drawArray(toList(sorter.getHistory().get(index)), c1, c2, shouldHighlight);
    }

    private void drawArray(List<Integer> arr, int compare1, int compare2, boolean highlight) {
        visualPane.getChildren().clear();
        double width = 60, spacing = 10, startX = 100;

        for (int k = 0; k < arr.size(); k++) {
            Rectangle rect = new Rectangle(width, 50);
            rect.setX(startX + k * (width + spacing));
            rect.setY(100);
            rect.setArcWidth(10);
            rect.setArcHeight(10);
            rect.setFill(Color.WHITE);
            rect.setStrokeWidth(2);
            rect.setStroke(k == compare1 || k == compare2 ? Color.RED : Color.BLACK);

            Text txt = new Text(String.valueOf(arr.get(k)));
            txt.setFont(Font.font("Segoe UI", 16));
            txt.setX(rect.getX() + width / 2 - 8);
            txt.setY(rect.getY() + 30);
            visualPane.getChildren().addAll(rect, txt);
        }

        if (highlight && compare1 != -1 && compare2 != -1 && compare1 != compare2) {
            double x1 = startX + compare1 * (width + spacing) + width / 2;
            double x2 = startX + compare2 * (width + spacing) + width / 2;
            double y = 80;

            QuadCurve curve = new QuadCurve(x1, y, (x1 + x2) / 2, y - 40, x2, y);
            curve.setStroke(Color.RED);
            curve.setStrokeWidth(2);
            curve.setFill(Color.TRANSPARENT);

            double offset = -5;
            Polygon leftHead = new Polygon(x1 - offset, y, x1 - offset - 10, y - 5, x1 - offset - 10, y + 5);
            leftHead.setFill(Color.RED);
            Polygon rightHead = new Polygon(x2 + offset, y, x2 + offset + 10, y - 5, x2 + offset + 10, y + 5);
            rightHead.setFill(Color.RED);

            visualPane.getChildren().addAll(curve, leftHead, rightHead);
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