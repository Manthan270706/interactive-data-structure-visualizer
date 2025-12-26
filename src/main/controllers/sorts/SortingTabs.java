package main.controllers.sorts;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class SortingTabs {
    private TabPane tabPane;

    public SortingTabs() {
        tabPane = new TabPane();
        createTabs();
    }

    private void createTabs() {
        // Create tabs for each sorting algorithm
        Tab bubbleSortTab = new Tab("Bubble Sort");
        Tab insertionSortTab = new Tab("Insertion Sort");
        Tab selectionSortTab = new Tab("Selection Sort");
        Tab quickSortTab = new Tab("Quick Sort");
        Tab shellSortTab = new Tab("Shell Sort");
        Tab radixSortTab = new Tab("Radix Sort");
        Tab countingSortTab = new Tab("Counting Sort");
        Tab bucketSortTab = new Tab("Bucket Sort");

        // Set up each tab with the corresponding visualizer
        bubbleSortTab.setContent(new BubbleSortVisualizer().getContent());
        insertionSortTab.setContent(new InsertionSortVisualizer().getContent());
        selectionSortTab.setContent(new SelectionSortVisualizer().getContent());
        quickSortTab.setContent(new QuickSortVisualizer().getContent());
        shellSortTab.setContent(new ShellSortVisualizer().getContent());
        radixSortTab.setContent(new RadixSortVisualizer().getContent());
        countingSortTab.setContent(new CountingSortVisualizer().getContent());
        bucketSortTab.setContent(new BucketSortVisualizer().getContent());

        // Add all tabs to the tab pane
        tabPane.getTabs().addAll(
                bubbleSortTab, insertionSortTab, selectionSortTab,
                quickSortTab, shellSortTab, radixSortTab,
                countingSortTab, bucketSortTab
        );

        // Make tabs closable false to keep the interface clean
        for (Tab tab : tabPane.getTabs()) {
            tab.setClosable(false);
        }
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}