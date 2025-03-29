package dev.ncrousset.repositories;

import dev.ncrousset.models.Category;
import dev.ncrousset.models.Product;
import dev.ncrousset.utils.DataConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product> {

    private static ProductRepositoryImpl instance;

    private ProductRepositoryImpl() {}

    private Connection getConnection() throws SQLException {
        return DataConnection.getInstance();
    }

    public static ProductRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ProductRepositoryImpl();
        }

        return instance;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        String sql = """
                    SELECT p.*, c.id AS c_id, c.name AS c_name 
                    FROM products p
                    JOIN categories c ON p.category_id = c.id""";

        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql)
        ) {
            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Product getById(Long id) {
        Product product = null;

        String sql = """
                    SELECT p.*, c.id AS c_id, c.name AS c_name 
                    FROM products p
                    JOIN categories c ON p.category_id = c.id 
                    WHERE p.id = ?""";
        
        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);

            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    product = createProduct(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public Product getLast() {
        Product product = null;

        String sql = """
                    SELECT p.*, c.id AS c_id, c.name AS c_name 
                    FROM products p
                    JOIN categories c ON p.category_id = c.id 
                    ORDER BY id DESC LIMIT 1""";

        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    product = createProduct(result);
                }
            }
        } catch ( SQLException  e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products(name, price, date, category_id)VALUES(?, ?, ?, ?)";
        try(PreparedStatement statement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setDate(3, new Date(product.getDate().getTime()));
            statement.setLong(4, product.getCategory().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name=?, price=?, category_id=? WHERE id=?";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getCategory().getId());
            statement.setLong(4, product.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE id=?";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findByCategoryId(Long categoryId) {
        List<Product> products = new ArrayList<>();

        String sql = """
                    SELECT p.*, c.id AS c_id, c.name AS c_name 
                    FROM products p
                    JOIN categories c ON p.category_id = c.id WHERE p.category_id=?""";

        try (PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setLong(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    Product product = createProduct(resultSet);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    private static Product createProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        Category category = new Category(resultSet.getLong("c_id"), resultSet.getString("c_name"));

        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getInt("price"));
        product.setDate(resultSet.getDate("date"));
        product.setCategory(category);
        return product;
    }
}
