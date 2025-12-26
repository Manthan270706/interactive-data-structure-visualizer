package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.controllers.DashboardController;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        DashboardController dashboard = new DashboardController();
        BorderPane root = dashboard.getLayout();

        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());

        stage.setTitle("Data Structure Visualizer");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
