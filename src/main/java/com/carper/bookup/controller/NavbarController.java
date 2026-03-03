package com.carper.bookup.controller;

import com.carper.bookup.navigation.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

public class NavbarController {


    public void goToAddBook(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("addbook-view.fxml", (Node) event.getSource());
    }

    public void goHome(ActionEvent event) throws IOException {
        SceneNavigator.switchTo("home-view.fxml", (Node) event.getSource());
    }

    public void goToLibrary(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("library-view.fxml", (Node) event.getSource());
    }
}
