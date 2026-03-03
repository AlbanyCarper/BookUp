package com.carper.bookup.navigation;

import com.carper.bookup.BookUpApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {
    public static void switchTo(String fxml, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(BookUpApplication.class.getResource("/com/carper/bookup/" + fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(java.util.Objects.requireNonNull(BookUpApplication.class.getResource("/com/carper/bookup/styles.css")).toExternalForm());
        stage.setScene(scene);
    }

    public static void switchTo(String fxml, Node node) throws IOException {
        Stage stage = (Stage) node.getScene().getWindow();
        switchTo(fxml, stage);
    }
}
