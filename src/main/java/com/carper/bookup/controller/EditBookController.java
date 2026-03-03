package com.carper.bookup.controller;

import com.carper.bookup.LmAccess;
import com.carper.bookup.base.Book;
import com.carper.bookup.base.BookRead;
import com.carper.bookup.base.BookStatus;
import com.carper.bookup.navigation.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class EditBookController {

    @FXML
    private Label titleLabel;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField yearReadField;

    @FXML
    private TextField ratingField;

    @FXML
    private TextArea notesArea;

    @FXML
    private ComboBox<BookStatus> statusComboBox;

    @FXML
    private ToggleButton favoriteButton;

    private Book originalBook;

    public void setBook(Book book) {
        this.originalBook = book;

        // Prefills the books basic info
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        yearField.setText(String.valueOf(book.getPublicationYear()));

        // Notes (editable)
        notesArea.setText(book.getNotes());

        // Book State
        statusComboBox.setItems(FXCollections.observableArrayList(BookStatus.values()));
        statusComboBox.setValue(book.getStatus());

        // Favorite
        favoriteButton.setSelected(book.isFavorite());

        // Make fields available for BookRead
        if (book.getStatus() == BookStatus.Read && book instanceof BookRead br) {
            setReadFields(br.getRating(), br.getYearRead(), true);
        } else {
            setReadFields(0, 0, false);
        }
    }

    private void setReadFields(int rating, int yearRead, boolean enable) {
        if (enable) {
            ratingField.setText(String.valueOf(rating));
            yearReadField.setText(String.valueOf(yearRead));
        } else {
            ratingField.clear();
            yearReadField.clear();
        }

        ratingField.setDisable(!enable);
        yearReadField.setDisable(!enable);
    }

    @FXML
    public void onStatusChange() {
        // Makes Rating and YearRead available when read
        boolean isRead = statusComboBox.getValue() == BookStatus.Read;
        ratingField.setDisable(!isRead);
        yearReadField.setDisable(!isRead);    }

    @FXML
    public void saveChanges() {
        BookStatus newStatus = statusComboBox.getValue();
        String notes = notesArea.getText().trim();
        boolean favorite = favoriteButton.isSelected();

        int rating = checkRating(newStatus);
        int yearRead = checkYearRead(newStatus);

        boolean changed = LmAccess.getLibraryManager().convertBook(
                originalBook.getTitle(), newStatus, yearRead, rating, notes, favorite
        );

        if (changed) {
            try {
                LmAccess.getLibraryManager().saveToFile("library.txt");
                showInfo();
                goBack();
            } catch (Exception e) {
                showError("Failed to save: " + e.getMessage());
            }
        } else {
            showError("Could not update the book.");
        }
    }

    private int checkRating(BookStatus status) {
        if (status != BookStatus.Read) return 0;

        try {
            int rating = Integer.parseInt(ratingField.getText().trim());
            if (rating < 1 || rating > 10) {
                showError("Rating must be between 1 and 10.");
                throw new NumberFormatException();
            }
            return rating;
        } catch (NumberFormatException e) {
            showError("Rating must be a number.");
            throw e;
        }
    }

    private int checkYearRead(BookStatus status) {
        if (status != BookStatus.Read) return 0;

        try {
            return Integer.parseInt(yearReadField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Read year must be a number.");
            throw e;
        }
    }

    @FXML
    public void deleteBook() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you 100% sure you want to delete this book?",
                ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Delete Book");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                LmAccess.getLibraryManager().removeBook(originalBook);
                try {
                    LmAccess.getLibraryManager().saveToFile("library.txt");
                    goBack();
                } catch (Exception e) {
                    showError("Could not delete book: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    public void goBack() throws IOException {
        SceneNavigator.switchTo("library-view.fxml", titleLabel);
    }


    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void showInfo() {
        new Alert(Alert.AlertType.INFORMATION, "Changes saved.").showAndWait();
    }
}
