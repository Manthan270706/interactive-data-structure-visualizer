package main.controllers.queues;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class QueueTabs {
    private VBox layout;

    public QueueTabs() {
        layout = new VBox(20);
        initializeTabs();
    }

    private void initializeTabs() {
        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Linear Queue Tab
        LinearQueueVisualizer linearQueueVisualizer = new LinearQueueVisualizer();
        Tab linearQueueTab = new Tab("Linear Queue", linearQueueVisualizer.getLayout());

        // Circular Queue Tab
        CircularQueueVisualizer circularQueueVisualizer = new CircularQueueVisualizer();
        Tab circularQueueTab = new Tab("Circular Queue", circularQueueVisualizer.getLayout());

        // Priority Queue Tab
        PriorityQueueVisualizer priorityQueueVisualizer = new PriorityQueueVisualizer();
        Tab priorityQueueTab = new Tab("Priority Queue", priorityQueueVisualizer.getLayout());

        // Deque Tab
        DequeVisualizer dequeVisualizer = new DequeVisualizer();
        Tab dequeTab = new Tab("Double Ended Queue", dequeVisualizer.getLayout());

        tabs.getTabs().addAll(linearQueueTab, circularQueueTab, priorityQueueTab, dequeTab);

        layout.getChildren().add(tabs);
    }

    public VBox getLayout() {
        return layout;
    }
}