package com.carper.bookup.controller;

import com.carper.bookup.LmAccess;
import com.carper.bookup.base.BookRead;
import com.carper.bookup.base.Genre;
import com.carper.bookup.navigation.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewBookReadController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField coverField;

    @FXML
    private TextField pubYearField;

    @FXML
    private TextArea notesArea;

    @FXML
    private CheckBox favoriteCheck;

    @FXML
    private ListView<String> genresList;

    @FXML
    private TextField ratingField;

    @FXML
    private TextField yearReadField;

    @FXML
    public void initialize() {
        genresList.getItems().addAll(Genre.defaultGenres);
        genresList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    public void saveBook() {
        BookRead book = createBookFromForm();
        if (book == null) return;

        LmAccess.getLibraryManager().addBook(book);

        try {
            LmAccess.getLibraryManager().saveToFile("library.txt");
            SceneNavigator.switchTo("library-view.fxml", titleField);
        } catch (IOException e) {
            showAlert("Error saving book: " + e.getMessage());
        }
    }

    private BookRead createBookFromForm() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String cover = coverField.getText().trim();
        String notes = notesArea.getText().trim();
        boolean fav = favoriteCheck.isSelected();
        List<String> genres = new ArrayList<>(genresList.getSelectionModel().getSelectedItems());

        if (title.isEmpty() || author.isEmpty()) {
            showAlert("Title and author are required.");
            return null;
        }

        int pubYear = parseInteger(pubYearField.getText().trim(), 0, "Invalid publication year.");
        int rating = parseInteger(ratingField.getText().trim(), -1, "Rating must be a number.");
        int yearRead = parseInteger(yearReadField.getText().trim(), -1, "Year read must be a number.");

        if (rating < 1 || rating > 10) {
            showAlert("Rating must be between 1 and 10.");
            return null;
        }

        int currentYear = LocalDate.now().getYear();
        if (yearRead < 1900 || yearRead > currentYear) {
            showAlert("Please enter a realistic year of reading.");
            return null;
        }

        return new BookRead(title, author, pubYear, cover, genres, fav, yearRead, rating, notes);
    }

    private int parseInteger(String text, int fallback, String errorMessage) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showAlert(errorMessage);
            return fallback;
        }
    }

    @FXML
    public void cancel() throws IOException {
        SceneNavigator.switchTo("addbook-view.fxml", titleField);
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message, ButtonType.OK).showAndWait();
    }
}
