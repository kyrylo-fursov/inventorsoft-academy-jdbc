package fursov.inventorsoftacademyjdbc.dao;


import fursov.inventorsoftacademyjdbc.domain.Role;
import fursov.inventorsoftacademyjdbc.domain.User;
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

// TODO: extract constants for SQL queries
@Repository
public class H2UserDao implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(H2UserDao.class);
    private final DataSource dataSource;

    public H2UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setName(rs.getString("name"));
        user.setRole(Role.fromId(rs.getInt("role_id")));
        return user;
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO users (password, email, name, role_id) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while adding user", e);
        }
    }

    @Override
    public void updateUser(long id, User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE users SET password = ?, email = ?, name = ?, role_id = ? WHERE id = ?")) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setInt(4, user.getRole().getId());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while updating user", e);
        }
    }

    @Override
    public Optional<User> getUserById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while getting user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToUser(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error while getting user by email", e);
        }
        return Optional.empty();
    }


    @Override
    public void deleteUser(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error while deleting user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                users.add(mapToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching all users", e);
        }
        return users;
    }
}
