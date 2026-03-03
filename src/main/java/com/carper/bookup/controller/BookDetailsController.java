package com.carper.bookup.controller;

import com.carper.bookup.base.*;
import com.carper.bookup.navigation.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.InputStream;

public class BookDetailsController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label notesLabel;

    @FXML
    private TextArea notesArea;

    @FXML
    private ImageView coverImage;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView favoriteIcon;

    @FXML
    private Label yearReadLabel;


    private Book book;

    public void setBook(Book book) {
        this.book = book;

        showBasicInfo(book);
        loadCoverImage(book.getCoverPath());
        showStatus(book);
        showFavoriteIcon(book.isFavorite());
        showNotes(book.getNotes());

        if (book instanceof BookRead br) {
            showRating(br.getRating());
            showYearRead(br.getYearRead());
        } else {
            ratingLabel.setVisible(false);
            yearReadLabel.setVisible(false); //Hides the rating/yearRead if nor read
        }

        if (book instanceof BookRead) {
            showRating(((BookRead) book).getRating());
        } else {
            ratingLabel.setVisible(false);
        }
    }

    private void showBasicInfo(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        genreLabel.setText(String.join(", ", book.getGenres()));
        yearLabel.setText("Published in " + book.getPublicationYear());
    }

    private void loadCoverImage(String path) {
        String validated = validatePath(path);
        InputStream stream = getClass().getResourceAsStream(validated);

        if (stream != null) {
            coverImage.setImage(new Image(stream));
        } else {
            InputStream fallback = getClass().getResourceAsStream("/com/carper/bookup/assets/placeholder.png");
            if (fallback != null) {
                coverImage.setImage(new Image(fallback));
            }
        }
    }

    private String validatePath(String path) {
        if (path == null || path.trim().isEmpty()) {
            return "/com/carper/bookup/assets/placeholder.png";
        }
        path = path.trim();
        if (!path.startsWith("/")) path = "/" + path;
        return path;
    }

    private void showYearRead(int yearRead) {
        yearReadLabel.setText("Read in " + yearRead);
        yearReadLabel.setVisible(true);
    }

    private void showRating(int rating) {
        ratingLabel.setText("Rating: " + rating + "/10");
        ratingLabel.setVisible(true);
    }

    private void showStatus(Book book) {
        statusLabel.setText(book.getStatus().toString().replace("_", " "));
    }

    private void showFavoriteIcon(boolean isFavorite) {
        String iconPath;
        if (isFavorite) {
            iconPath = "/com/carper/bookup/icons/heart-filled.png";
        } else {
            iconPath = "/com/carper/bookup/icons/heart.png";
        }

        InputStream iconStream = getClass().getResourceAsStream(iconPath);
        if (iconStream != null) {
            favoriteIcon.setImage(new Image(iconStream));
        }
    }

    private void showNotes(String notes) {
        notesArea.setText(notes);
        notesArea.setVisible(true);
        notesLabel.setVisible(true);
    }

    public void editBook(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carper/bookup/editbook-view.fxml"));
            Parent root = loader.load();

            EditBookController controller = loader.getController();
            controller.setBook(book);  // Set book for editing

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load editor: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("library-view.fxml", (Node) event.getSource());
    }

}
