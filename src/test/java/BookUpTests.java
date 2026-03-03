import com.carper.bookup.base.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BookUpTests {

    @Test
    public void testExportStringBookRead() {
        BookRead book = new BookRead(
                "1984", "George Orwell", 1949, "/1984-cover.png",
                List.of("Dytopical", "Political"), true, 2024, 9, "Test note"
        );

        String expected = """
        Title: 1984
        Author: George Orwell
        Published: 1949
        Status: Read
        Rating: 9/10
        Year Read: 2024
        Notes: Test note
    """.strip();

        assertEquals(expected, actual);
    }

    @Test
    public void testBookComparisonByTitle() {
        Book bookA = new BookRead("Animal Farm", "George Orwell", 1945, "", List.of(), true, 2024, 9, "");
        Book bookB = new BookRead("Pride and Prejudice", "Jane Austen", 1813, "", List.of(), true, 2017, 9, "");

        assertTrue(bookA.compareTo(bookB) < 0);
        assertTrue(bookB.compareTo(bookA) > 0);
    }

    @Test
    public void testAddBookToLibraryManager() {
        LibraryManager manager = new LibraryManager();
        BookToRead book = new BookToRead("Agnes Gray", "Anne Brontë", 1847, "", List.of("Classic"), false, "Must read");

        manager.addBook(book);

        List<Book> result = manager.getBooksByStatus(BookStatus.To_Read);

        assertEquals(1, result.size());
        assertEquals("Agnes Gray", result.getFirst().getTitle());
    }
}
