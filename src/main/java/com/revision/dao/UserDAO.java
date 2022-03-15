package com.revision.dao;

import com.revision.entity.Role;
import com.revision.entity.User;

import java.sql.*;

public class UserDAO {

    private final Connection connection = Database.getConnection();

    private static final String GET = "SELECT * FROM users WHERE user_name= ?";
    private static final String CREATE = "INSERT INTO users (user_name, password, role) VALUES (?, ?, ?) RETURNING user_name";
    private static final String GET_WORDS_COUNT = "SELECT count(*) FROM words WHERE user_id= ?";
    private static final String GET_DICTIONARIES_COUNT = "SELECT count(*) FROM dictionaries WHERE user_id= ?";
    private static final String CHECK_EXISTS = "SELECT EXISTS (SELECT 1 FROM users WHERE user_name= ?)";

    public User get(String userName) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(GET)) {
            statement.setString(1, userName);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                user = new User(rs.getInt("id"), Role.valueOf(rs.getString("role")), rs.getString("user_name"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User create(String userName, String password, Role role) {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setString(3, role.toString());
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                user = new User(rs.getInt("id"), role, userName, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int getWordsCount(int id) {
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(GET_WORDS_COUNT)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getDictionariesCount(int id) {
        int count = 0;
        try (PreparedStatement statement = connection.prepareStatement(GET_DICTIONARIES_COUNT)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean checkExists(String userName) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(CHECK_EXISTS)) {
            statement.setString(1, userName);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                result = rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}