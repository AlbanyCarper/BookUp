package com.carper.bookup.controller;

import com.carper.bookup.LmAccess;
import com.carper.bookup.base.BookToRead;
import com.carper.bookup.base.Genre;
import com.carper.bookup.navigation.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewBookToReadController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField coverField;

    @FXML
    private TextField pubYearField;

    @FXML
    private TextArea reasonArea;

    @FXML
    private CheckBox favoriteCheck;

    @FXML
    private ListView<String> genresList;

    @FXML
    public void initialize() {
        genresList.getItems().addAll(Genre.defaultGenres);
        genresList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void saveBook() {
        BookToRead book = createBookFromForm();
        if (book == null) return;

        LmAccess.getLibraryManager().addBook(book);

        try {
            LmAccess.getLibraryManager().saveToFile("library.txt");
            SceneNavigator.switchTo("library-view.fxml", titleField);
        } catch (IOException e) {
            showAlert("Error saving book: " + e.getMessage());
        }
    }

    private BookToRead createBookFromForm() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String cover = coverField.getText().trim();
        String reason = reasonArea.getText().trim();
        boolean fav = favoriteCheck.isSelected();

        List<String> genres = new ArrayList<>(genresList.getSelectionModel().getSelectedItems());

        Integer pubYear = null;

        if (!pubYearField.getText().isBlank()) {

            try {
                pubYear = Integer.parseInt(pubYearField.getText().trim());

                if (pubYear < 1450 || pubYear > java.time.LocalDate.now().getYear()) {
                    showAlert("Please enter a real publication year.");
                    return null;
                }

            } catch (NumberFormatException e) {
                showAlert("Invalid publication year. Please enter a valid number.");
                return null;
            }
        }


        if (title.isEmpty() || author.isEmpty()) {
            showAlert("Title and author are required.");
            return null;
        }

        return new BookToRead(title, author, pubYear != null ? pubYear : 0, cover, genres, fav, reason);
    }

    @FXML
    public void cancel() throws IOException {
        SceneNavigator.switchTo("addbook-view.fxml", titleField);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
}
