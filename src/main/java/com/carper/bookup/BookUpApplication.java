package com.carper.bookup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class BookUpApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(BookUpApplication.class.getResource("/com/carper/bookup/home-view.fxml"));
        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/carper/bookup/styles.css")).toExternalForm());

        stage.setTitle("BookUp");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setMinHeight(640);
        stage.setMaxHeight(640);
        stage.setMinWidth(360);
        stage.setMaxWidth(360);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
