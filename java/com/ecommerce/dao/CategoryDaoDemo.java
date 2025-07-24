package com.ecommerce.dao;

import com.ecommerce.database.DBConnectionCloser;
import com.ecommerce.database.Database;
import com.ecommerce.entity.CategoryDemo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CategoryDaoDemo extends DBConnectionCloser {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Insert a new category
    public void insertCategory(CategoryDemo category) {
        String query = "INSERT INTO categorydemo (category_name, category_image) VALUES (?, ?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setBytes(2, category.getImage());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConClose(connection);
        }
    }

    // Update existing category
    public void updateCategory(CategoryDemo category) {
        String query = "UPDATE categorydemo SET category_name = ?, category_image = ? WHERE category_id = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setBytes(2, category.getImage());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConClose(connection);
        }
    }

    // Soft delete category
    public void deleteCategory(int id) {
        String query = "UPDATE categorydemo SET category_is_deleted = true WHERE category_id = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConClose(connection);
        }
    }

    // Fetch all categories (not deleted)
    public List<CategoryDemo> getAllCategories() {
        List<CategoryDemo> list = new ArrayList<>();
        String query = "SELECT * FROM categorydemo WHERE category_is_deleted = false";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CategoryDemo category = new CategoryDemo();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                category.setImage(resultSet.getBytes("category_image"));

                if (category.getImage() != null) {
                    String base64 = Base64.getEncoder().encodeToString(category.getImage());
                    category.setBase64Image(base64);
                }

                category.setDeleted(resultSet.getBoolean("category_is_deleted"));
                list.add(category);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConClose(connection);
        }

        return list;
    }

    // Fetch category by ID
    public CategoryDemo getCategoryById(int id) {
        CategoryDemo category = null;
        String query = "SELECT * FROM categorydemo WHERE category_id = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                category = new CategoryDemo();
                category.setId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("category_name"));
                category.setImage(resultSet.getBytes("category_image"));

                if (category.getImage() != null) {
                    String base64 = Base64.getEncoder().encodeToString(category.getImage());
                    category.setBase64Image(base64);
                }

                category.setDeleted(resultSet.getBoolean("category_is_deleted"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConClose(connection);
        }

        return category;
    }
}
