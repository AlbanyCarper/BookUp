package com.carper.bookup.controller;

import com.carper.bookup.LmAccess;
import com.carper.bookup.base.Book;
import com.carper.bookup.base.BookStatus;
import com.carper.bookup.navigation.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class LibraryController {

    @FXML
    private FlowPane bookFlow;

    @FXML
    Button btnAll;

    @FXML
    private Button btnToRead;

    @FXML
    private Button btnReading;

    @FXML
    private Button btnRead;

    private BookStatus currentFilter = null;


    @FXML
    public void initialize() {
        //Select all the books by default
        updateSelectedFilter(btnAll);
        updateFilteredBooks();
    }

    private void updateSelectedFilter(Button selectedButton) {

        final String defaultStyle = "-fx-background-radius: 5; -fx-border-color: #36312d; -fx-border-radius: 5; -fx-background-color: FFFCF7; -fx-text-fill: #36312d;";
        final String selectedStyle = "-fx-background-color: #B4DBAC; -fx-text-fill: #36312d; -fx-background-radius: 5; -fx-border-radius: 5;";

        btnAll.setStyle(defaultStyle);
        btnToRead.setStyle(defaultStyle);
        btnReading.setStyle(defaultStyle);
        btnRead.setStyle(defaultStyle);

        selectedButton.setStyle(selectedStyle);
    }

    private void updateFilteredBooks() {
        bookFlow.getChildren().clear();
        List<Book> filtered = getFilteredBooks();
        for (Book book : filtered) {
            bookFlow.getChildren().add(makeCard(book));
        }
    }

    private List<Book> getFilteredBooks() {
        return LmAccess.getLibraryManager().getAllBooks().stream()
                .filter(book -> currentFilter == null || book.getStatus() == currentFilter)
                .sorted()
                .toList();
    }

    private VBox makeCard(Book book) {
        VBox box = new VBox(8);
        box.setPrefWidth(125);
        box.setPrefHeight(225);
        box.setAlignment(Pos.TOP_LEFT);

        ImageView cover = createCoverImage(book.getCoverPath());

        Label title = new Label(book.getTitle());
        title.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        title.setWrapText(true);

        Label author = new Label("by " + book.getAuthor());
        author.setStyle("-fx-font-size: 11px;");
        author.setWrapText(true);

        box.getChildren().addAll(cover, title, author);

        box.setOnMouseClicked(e -> {
            try {
                openBookDetail(book, e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        return box;
    }

    private ImageView createCoverImage(String path) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(125);
        imageView.setFitHeight(175);
        imageView.setPreserveRatio(false);
        imageView.setPickOnBounds(true);

        String validatedPath;
        if (path == null || path.trim().isEmpty()) {
            validatedPath = "/com/carper/bookup/assets/placeholder.png";
        } else if (path.startsWith("/")) {
            validatedPath = path;
        } else {
            validatedPath = "/" + path;
        }

        InputStream stream = getClass().getResourceAsStream(validatedPath);
        if (stream != null) {
            imageView.setImage(new Image(stream));
        } else {
            InputStream fallback = getClass().getResourceAsStream("/com/carper/bookup/assets/placeholder.png");
            if (fallback != null) {
                imageView.setImage(new Image(fallback));
            }
        }

        return imageView;
    }

    private void openBookDetail(Book book, MouseEvent e) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carper/bookup/bookdetails-view.fxml"));
            Parent root = loader.load();

            BookDetailsController controller = loader.getController();
            controller.setBook(book);

            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
    }

    @FXML
    public void showAllBooks(ActionEvent event) {
        currentFilter = null;
        updateSelectedFilter(btnAll);
        updateFilteredBooks();
    }

    @FXML
    public void showToRead(ActionEvent event) {
        currentFilter = BookStatus.To_Read;
        updateSelectedFilter(btnToRead);
        updateFilteredBooks();
    }

    @FXML
    public void showCurrentlyReading(ActionEvent event) {
        currentFilter = BookStatus.Reading;
        updateSelectedFilter(btnReading);
        updateFilteredBooks();
    }

    @FXML
    public void showRead(ActionEvent event) {
        currentFilter = BookStatus.Read;
        updateSelectedFilter(btnRead);
        updateFilteredBooks();
    }

    @FXML
    public void exportBooks() {
        List<Book> books = LmAccess.getLibraryManager().getAllBooks();

        Map<BookStatus, List<Book>> grouped = new HashMap<>();
        for (Book book : books) {
            grouped.computeIfAbsent(book.getStatus(), _ -> new ArrayList<>()).add(book);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("exported_library.txt"))) {
            writer.write("Your Library\n");
            writer.write("Books count: " + books.size() + "\n");

            for (BookStatus status : BookStatus.values()) {
                List<Book> group = grouped.get(status);
                if (group != null && !group.isEmpty()) {
                    writer.newLine();
                    writer.write("Group: " + status + "\n");
                    writer.write("------------------------------\n");
                    for (Book book : group) {
                        writer.write(book.getExportString());
                        writer.newLine();
                        writer.write("\n");
                    }
                }
            }

            System.out.println("Library exported.");
        } catch (IOException e) {
            System.out.println("Error exporting library: " + e.getMessage());
        }
    }

    @FXML
    public void goBack(ActionEvent event) throws Exception {
        SceneNavigator.switchTo("home-view.fxml", (Node) event.getSource());
    }
}
