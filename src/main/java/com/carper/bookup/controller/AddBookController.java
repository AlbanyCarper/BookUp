package com.carper.bookup.controller;

import com.carper.bookup.navigation.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class AddBookController {

    @FXML
    public void goToNewToRead(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("newbooktoread-view.fxml", (Node) event.getSource());
    }

    @FXML
    public void goToNewRead(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("newbookread-view.fxml", (Node) event.getSource());
    }

    @FXML
    public void goToNewCurrent(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("newcurrentbook-view.fxml", (Node) event.getSource());
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("home-view.fxml", (Node) event.getSource());
    }
}