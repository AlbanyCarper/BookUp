package com.carper.bookup.base;

import java.util.List;

public abstract class Book implements Comparable<Book> {
    protected String title;
    protected String author;
    protected int publicationYear;
    protected String coverPath;
    protected List<String> genres;
    protected boolean isFavorite;
    protected BookStatus status;
    protected String notes;

    public Book(String title, String author, int publicationYear, String coverPath, List<String> genres, boolean isFavorite, BookStatus status, String notes) {

        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.coverPath = coverPath;
        this.genres = genres;
        this.isFavorite = isFavorite;
        this.status = status;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public List<String> getGenres() {
        return genres;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public abstract String getExportString();

    @Override
    public int compareTo(Book other) {
        return this.title.compareToIgnoreCase(other.title);
    }
}