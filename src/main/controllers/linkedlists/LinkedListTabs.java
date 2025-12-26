package main.controllers.linkedlists;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class LinkedListTabs {

    private VBox layout;

    public LinkedListTabs() {
        layout = new VBox(20);

        TabPane tabs = new TabPane();
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Singly Linked List Tab
        SinglyLinkedListVisualizer sllVisualizer = new SinglyLinkedListVisualizer();
        Tab sllTab = new Tab("SLL", sllVisualizer.getLayout());

        // Doubly Linked List Tab
        DoublyLinkedListVisualizer dllVisualizer = new DoublyLinkedListVisualizer();
        Tab dllTab = new Tab("DLL", dllVisualizer.getLayout());

        // Circular Singly Linked List Tab
        CSLLVisualizer csllVisualizer = new CSLLVisualizer();
        Tab csllTab = new Tab("CSLL", csllVisualizer.getLayout());

        // Circular Doubly Linked List Tab
        CDLLVisualizer cdllVisualizer = new CDLLVisualizer();
        Tab cdllTab = new Tab("CDLL", cdllVisualizer.getLayout());

        tabs.getTabs().addAll(sllTab, dllTab, csllTab, cdllTab);

        layout.getChildren().add(tabs);
    }

    public VBox getLayout() {
        return layout;
    }

}
