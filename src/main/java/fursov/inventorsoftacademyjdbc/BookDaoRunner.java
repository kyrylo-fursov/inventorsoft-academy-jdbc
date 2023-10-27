package fursov.inventorsoftacademyjdbc;

import fursov.inventorsoftacademyjdbc.dao.BookDao;
import fursov.inventorsoftacademyjdbc.domain.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoRunner implements CommandLineRunner {
    private final BookDao bookDao;

    public BookDaoRunner(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book(1L, "Title1", "Author1", 3.1);
        Book book2 = new Book(2L, "Title2", "Author2", 4.1);
        Book book3 = new Book(3L, "Title3", "Author3", 5.1);
        Book book4 = new Book(8L, "Title4", "Author4", 6.1);

        bookDao.addBook(book1);
        bookDao.addBook(book2);
        bookDao.addBook(book3);
        bookDao.addBook(book4);

        bookDao.deleteBook(2L);
        bookDao.addBook(book3);

        List<Book> books = bookDao.getAllBooks();
        System.out.println(books);

        bookDao.updateBook(1L, new Book(1L, "UpdatedTitle1", "UpdatedAuthor1", 1.1));
        bookDao.updateBook(2L, new Book(3L, "UpdatedTitle3", "UpdatedAuthor3", 3.1));

        books = bookDao.getAllBooks();
        System.out.println(books);

        System.out.println(bookDao.getBookById(1L));
    }
}
