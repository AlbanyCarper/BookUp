package com.carper.bookup.base;

import java.io.*;
import java.util.*;

public class LibraryManager {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(book.getTitle())
                    && b.getAuthor().equalsIgnoreCase(book.getAuthor())) {
                System.out.println("This book is already in your library!");
                return;
            }
        }
        books.add(book);
    }

    public void addBook(String title, String author) {
        BookToRead book = new BookToRead(title, author, 0, "", List.of(), false, "");
        addBook(book);
    }

    public List<Book> getBooksByStatus(BookStatus status) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getStatus() == status) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public boolean convertBook(String title, BookStatus newStatus, int yearRead, int rating, String notes, boolean favorite) {
        Iterator<Book> iterator = books.iterator();

        while (iterator.hasNext()) {
            Book b = iterator.next();

            if (b.getTitle().equalsIgnoreCase(title)) {
                Book converted;

                if (newStatus == BookStatus.To_Read) {
                    converted = new BookToRead(
                            b.getTitle(), b.getAuthor(), b.getPublicationYear(),
                            b.getCoverPath(), b.getGenres(), favorite, notes
                    );
                } else if (newStatus == BookStatus.Reading) {
                    converted = new CurrentBook(
                            b.getTitle(), b.getAuthor(), b.getPublicationYear(),
                            b.getCoverPath(), b.getGenres(), favorite, notes
                    );
                } else {
                    converted = new BookRead(
                            b.getTitle(), b.getAuthor(), b.getPublicationYear(),
                            b.getCoverPath(), b.getGenres(), favorite,
                            yearRead, rating, notes
                    );
                }

                iterator.remove();
                books.add(converted);
                return true;
            }
        }
        return false;
    }

    public void saveToFile(String filename) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            for (Book book : books) {
                StringBuilder line = new StringBuilder();
                String coverPath = book.getCoverPath();

                switch (book) {
                    case BookToRead btr -> line.append("BookToRead;")
                            .append(btr.getTitle()).append(";")
                            .append(btr.getAuthor()).append(";")
                            .append(btr.getPublicationYear()).append(";")
                            .append(coverPath).append(";")
                            .append(String.join(",", btr.getGenres())).append(";")
                            .append(btr.isFavorite()).append(";")
                            .append(btr.getNotes() != null ? btr.getNotes() : "");
                    case CurrentBook cb -> line.append("BookCurrent;")
                            .append(cb.getTitle()).append(";")
                            .append(cb.getAuthor()).append(";")
                            .append(cb.getPublicationYear()).append(";")
                            .append(coverPath).append(";")
                            .append(String.join(",", cb.getGenres())).append(";")
                            .append(cb.isFavorite()).append(";")
                            .append(cb.getNotes() != null ? cb.getNotes() : "");
                    case BookRead br -> line.append("BookRead;")
                            .append(br.getTitle()).append(";")
                            .append(br.getAuthor()).append(";")
                            .append(br.getPublicationYear()).append(";")
                            .append(coverPath).append(";")
                            .append(String.join(",", br.getGenres())).append(";")
                            .append(br.isFavorite()).append(";")
                            .append(br.getYearRead()).append(";")
                            .append(br.getRating()).append(";")
                            .append(br.getNotes() != null ? br.getNotes() : "");
                    default -> {
                    }
                }

                writer.write(line.toString());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        books.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    String type = parts[0];
                    String coverPath = parts[4].trim();

                    if (type.equals("BookToRead") && parts.length >= 8) {
                        int pages = safeParseInt(parts[3]);
                        boolean owned = Boolean.parseBoolean(parts[6]);

                        books.add(new BookToRead(
                                parts[1], parts[2], pages, coverPath,
                                Arrays.asList(parts[5].split(",")), owned, parts[7]
                        ));

                    } else if (type.equals("BookCurrent") && parts.length >= 8) {
                        int pages = safeParseInt(parts[3]);
                        boolean owned = Boolean.parseBoolean(parts[6]);

                        books.add(new CurrentBook(
                                parts[1], parts[2], pages, coverPath,
                                Arrays.asList(parts[5].split(",")), owned, parts[7]
                        ));

                    } else if (type.equals("BookRead") && parts.length >= 10) {
                        int pages = safeParseInt(parts[3]);
                        boolean owned = Boolean.parseBoolean(parts[6]);
                        int rating = safeParseInt(parts[7]);
                        int year = safeParseInt(parts[8]);

                        books.add(new BookRead(
                                parts[1], parts[2], pages, coverPath,
                                Arrays.asList(parts[5].split(",")), owned,
                                rating, year, parts[9]
                        ));
                    } else {
                        System.out.println("Badly written line: " + line);
                    }

                } catch (Exception e) {
                    System.out.println("Error loading line: " + line);
                }
            }
        }
    }

    public void removeBook(Book book) {
        books.removeIf(b -> b.equals(book));
    }

    private int safeParseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
