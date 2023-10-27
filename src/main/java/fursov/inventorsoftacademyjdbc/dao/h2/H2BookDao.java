package fursov.inventorsoftacademyjdbc.dao;

import fursov.inventorsoftacademyjdbc.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class H2BookDao implements BookDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(H2BookDao.class);

    public static final String SQL_ADD_BOOK = "INSERT INTO books (title, author, price) VALUES (?, ?, ?)";
    public static final String SQL_GET_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    public static final String SQL_GET_ALL_BOOKS = "SELECT * FROM books";
    public static final String SQL_UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, price = ? WHERE id = ?";
    public static final String SQL_DELETE_BOOK =  "DELETE FROM books WHERE id = ?";


    private final DataSource dataSource;

    public H2BookDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Book extractBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setPrice(rs.getDouble("price"));
        return book;
    }

    @Override
    public boolean addBook(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_BOOK)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3, book.getPrice());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Error while adding book", e);
            return false;
        }
    }

    @Override
    public Optional<Book> getBookById(long id) {
        String sql = SQL_GET_BOOK_BY_ID;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(extractBook(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching book by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_BOOKS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                books.add(extractBook(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching all books", e);
        }
        return books;
    }

    @Override
    public boolean updateBook(long id, Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BOOK)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setDouble(3, book.getPrice());
            preparedStatement.setLong(4, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Error while updating book", e);
            return false;
        }
    }

    @Override
    public boolean deleteBook(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BOOK)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.error("Error while deleting book", e);
            return false;
        }
    }
}
