package com.carper.bookup.base;

import java.util.List;

public class BookRead extends Book {
    private int yearRead;
    private int rating;

    public BookRead(String title, String author, int publicationYear, String coverPath, List<String> genres, boolean isFavorite, int yearRead, int rating, String notes) {

        super(title, author, publicationYear, coverPath, genres, isFavorite, BookStatus.Read, notes);

        this.yearRead = yearRead;
        this.rating = rating;
    }

    public int getYearRead() {
        return yearRead;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String getExportString() {
        return String.format("Title: %s\nAuthor: %s\nPublished: %d\nStatus: Read\nRating: %d/10\nYear Read: %d\nNotes: %s",
                title, author, publicationYear,
                rating, yearRead,
                notes != null ? notes : "No notes provided");
    }


}
