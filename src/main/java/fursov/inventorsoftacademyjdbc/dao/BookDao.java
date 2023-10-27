package fursov.inventorsoftacademyjdbc.dao;

import fursov.inventorsoftacademyjdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    boolean addBook(Book book);
    Optional<Book> getBookById(long id);
    List<Book> getAllBooks();
    boolean updateBook(long id, Book book);
    boolean deleteBook(long id);
}
