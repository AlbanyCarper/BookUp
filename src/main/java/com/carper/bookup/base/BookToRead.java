package com.carper.bookup.base;

import java.util.List;

public class BookToRead extends Book {

    public BookToRead(String title, String author, int publicationYear, String coverPath, List<String> genres, boolean isFavorite, String notes) {
        super(title, author, publicationYear, coverPath, genres, isFavorite, BookStatus.To_Read, notes);
    }

    @Override
    public String getExportString() {
        return String.format("Title: %s\nAuthor: %s\nPublished: %d\nStatus: To Read\nReason: %s",
                title, author, publicationYear,
                notes != null ? notes : "No notes provided");
    }

}
