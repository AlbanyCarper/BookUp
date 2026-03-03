package com.carper.bookup.base;

import java.util.List;

public class CurrentBook extends Book {

    public CurrentBook(String title, String author, int publicationYear, String coverPath, List<String> genres, boolean isFavorite, String notes) {

        super(title, author, publicationYear, coverPath, genres, isFavorite, BookStatus.Reading, notes);

    }

    @Override
    public String getExportString() {
        return String.format("Title: %s\nAuthor: %s\nPublished: %d\nStatus: Currently Reading\nNotes: %s",
                title, author, publicationYear,
                notes != null ? notes : "No notes provided");
    }

}
