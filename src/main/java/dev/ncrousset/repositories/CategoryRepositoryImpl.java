package dev.ncrousset.repositories;

import dev.ncrousset.models.Category;
import dev.ncrousset.utils.DataConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements Repository<Category> {

    private static CategoryRepositoryImpl instance;

    public CategoryRepositoryImpl() {}

    public static CategoryRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new CategoryRepositoryImpl();
        }

        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DataConnection.getInstance();
    }

    @Override
    public Category getById(Long id) {
        Category category = null;
        String sql = "SELECT * FROM categories WHERE id=?";

        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    category = createCategory(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try(Statement statement = getConnection().createStatement();
           ResultSet result = statement.executeQuery(sql)) {
            while(result.next()) {
                Category category = createCategory(result);
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public void save(Category category) {
        String sql = "INSERT INTO categories(name)values(?)";

        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1,  category.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category) {
       String sql = "UPDATE categories SET name=? WHERE id=?";

       try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
           statement.setString(1, category.getName());
           statement.setLong(2, category.getId());

           statement.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM categories WHERE id=?";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getLast() {
        Category category = null;
        String sql = "SELECT * FROM categories ORDER BY id DESC LIMIT 1";

        try (PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                category = createCategory(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    private Category createCategory(ResultSet result) throws SQLException {
        Category category = new Category();
        category.setId(result.getLong("id"));
        category.setName(result.getString("name"));
        return category;
    }
}
