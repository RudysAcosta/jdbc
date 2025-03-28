package dev.ncrousset.repositories;

import dev.ncrousset.models.Product;
import dev.ncrousset.utils.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product> {

    private static ProductRepositoryImpl instance;

    private ProductRepositoryImpl() {}

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
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

        try(Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("select * from products")
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
        
        try (PreparedStatement statement = getConnection().
                prepareStatement("SELECT * FROM products WHERE id = ?")) {
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
        String sql = "SELECT * FROM products ORDER BY id DESC LIMIT 1";

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
        String sql = "INSERT INTO products(name, price, date)VALUES(?, ?, ?)";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setDate(3, new Date(product.getDate().getTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name=?, price=? WHERE id=?";
        try(PreparedStatement statement = getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getId());

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

    private static Product createProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getInt("price"));
        product.setDate(resultSet.getDate("date"));
        return product;
    }
}
