//package main.controllers;
//
//import javafx.geometry.Insets;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.scene.text.Font;
//import main.controllers.linkedlists.LinkedListTabs;
//import main.controllers.queues.QueueTabs;
//import main.controllers.sorts.SortingTabs;
//import main.controllers.stack.StackVisualizer;
//
//public class DashboardController {
//
//    private BorderPane layout;
//    private VBox sidebar;
//    private StackPane mainArea;
//
//    public DashboardController() {
//        layout = new BorderPane();
//        sidebar = createSidebar();
//        mainArea = new StackPane();
//        mainArea.setPadding(new Insets(20));
//
//        layout.setLeft(sidebar);
//        layout.setCenter(mainArea);
//    }
//
//    private VBox createSidebar() {
//        VBox box = new VBox(10);
//        box.setPadding(new Insets(20));
//        box.getStyleClass().add("sidebar");
//
//        // Set sidebar width
//        box.setPrefWidth(1000);
//
//        Label title = new Label("Data Structure\nVisualizer");
//        title.setFont(new Font("Arial Bold", 18));
//        title.getStyleClass().add("sidebar-title");
//
//        Separator sep = new Separator();
//
//        Button homeBtn = new Button("🏠 Home");
//        Button linkedListBtn = new Button("🔗 Linked List");
//        Button stackBtn = new Button("🧱 Stack");
//        Button queueBtn = new Button("📥 Queue");
//        Button sortingBtn = new Button("📊 Sorting Algorithms");
//
//        homeBtn.getStyleClass().add("sidebar-btn");
//        stackBtn.getStyleClass().add("sidebar-btn");
//        queueBtn.getStyleClass().add("sidebar-btn");
//        linkedListBtn.getStyleClass().add("sidebar-btn");
//        sortingBtn.getStyleClass().add("sidebar-btn");
//
//        // Connect buttons to their visualizers
//        homeBtn.setOnAction(e -> mainArea.getChildren().clear());
//        linkedListBtn.setOnAction(e -> showLinkedListTabs());
//        stackBtn.setOnAction(e -> showStackVisualizer());
//        queueBtn.setOnAction(e -> showQueueTabs());
//        sortingBtn.setOnAction(e->showSortingVisualizer());
//
//        box.getChildren().addAll(title, sep, homeBtn, linkedListBtn, stackBtn, queueBtn, sortingBtn);
//        return box;
//    }
//
//    private void showLinkedListTabs() {
//        LinkedListTabs linkedListTabs = new LinkedListTabs();
//        mainArea.getChildren().setAll(linkedListTabs.getLayout());
//    }
//
//    private void showStackVisualizer() {
//        StackVisualizer visualizer = new StackVisualizer();
//        mainArea.getChildren().setAll(visualizer.getLayout());
//    }
//
//    private void showQueueTabs() {
//        QueueTabs queueTabs = new QueueTabs();
//        mainArea.getChildren().setAll(queueTabs.getLayout());
//    }
//
//    private void showSortingVisualizer() {
//        SortingTabs sortingTabs = new SortingTabs();
//        mainArea.getChildren().setAll(sortingTabs.getTabPane());
//    }
//
//    public BorderPane getLayout() {
//        return layout;
//    }
//}

package main.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.controllers.linkedlists.LinkedListTabs;
import main.controllers.queues.QueueTabs;
import main.controllers.sorts.SortingTabs;
import main.controllers.stack.StackVisualizer;

public class DashboardController {

    private BorderPane layout;
    private VBox sidebar;
    private StackPane mainArea;

    public DashboardController() {
        layout = new BorderPane();
        sidebar = createSidebar();
        mainArea = new StackPane();
        mainArea.setPadding(new Insets(20));

        layout.setLeft(sidebar);
        layout.setCenter(mainArea);

        // Show home page at startup
        showHomePage();
    }

    private VBox createSidebar() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.getStyleClass().add("sidebar");

        // Set sidebar width
        box.setPrefWidth(200);

        Label title = new Label("Data Structure\nVisualizer");
        title.setFont(new Font("Arial Bold", 18));
        title.getStyleClass().add("sidebar-title");

        Separator sep = new Separator();

        Button homeBtn = new Button("🏠 Home");
        Button linkedListBtn = new Button("🔗 Linked List");
        Button stackBtn = new Button("🧱 Stack");
        Button queueBtn = new Button("📥 Queue");
        Button sortingBtn = new Button("📊 Sorting Algorithms");

        homeBtn.getStyleClass().add("sidebar-btn");
        stackBtn.getStyleClass().add("sidebar-btn");
        queueBtn.getStyleClass().add("sidebar-btn");
        linkedListBtn.getStyleClass().add("sidebar-btn");
        sortingBtn.getStyleClass().add("sidebar-btn");

        // Connect buttons to their visualizers
        homeBtn.setOnAction(e -> showHomePage());
        linkedListBtn.setOnAction(e -> showLinkedListTabs());
        stackBtn.setOnAction(e -> showStackVisualizer());
        queueBtn.setOnAction(e -> showQueueTabs());
        sortingBtn.setOnAction(e -> showSortingVisualizer());

        box.getChildren().addAll(title, sep, homeBtn, linkedListBtn, stackBtn, queueBtn, sortingBtn);
        return box;
    }

    // Home page with title and creators
    private void showHomePage() {
        mainArea.getChildren().clear();

        VBox homeBox = new VBox(20);
        homeBox.setAlignment(Pos.CENTER);

        // Project title
        Text title = new Text("Data Structure Visualizer");
        title.setFont(Font.font("Arial Bold", 36));

        // Creators
        Text createdBy = new Text("Created by: Nitish Sahu, Palash Sahuji, Manthan Sali, Aditya Rana");
        createdBy.setFont(Font.font("Arial", 20));

        homeBox.getChildren().addAll(title, createdBy);

        mainArea.getChildren().add(homeBox);
    }

    private void showLinkedListTabs() {
        LinkedListTabs linkedListTabs = new LinkedListTabs();
        mainArea.getChildren().setAll(linkedListTabs.getLayout());
    }

    private void showStackVisualizer() {
        StackVisualizer visualizer = new StackVisualizer();
        mainArea.getChildren().setAll(visualizer.getLayout());
    }

    private void showQueueTabs() {
        QueueTabs queueTabs = new QueueTabs();
        mainArea.getChildren().setAll(queueTabs.getLayout());
    }

    private void showSortingVisualizer() {
        SortingTabs sortingTabs = new SortingTabs();
        mainArea.getChildren().setAll(sortingTabs.getTabPane());
    }

    public BorderPane getLayout() {
        return layout;
    }
}
