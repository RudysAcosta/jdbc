package dev.ncrousset;


import dev.ncrousset.models.Product;
import dev.ncrousset.repositories.ProductRepositoryImpl;
import dev.ncrousset.utils.DataConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = DataConnection.getInstance()) {
            System.out.println("Method create()");
            Product newProduct = new Product("Test Insert", 200, Date.valueOf(LocalDate.now()));
            ProductRepositoryImpl.getInstance().save(newProduct);

            System.out.println();
            System.out.println("Method getAll()");
            List<Product> products = ProductRepositoryImpl.getInstance().getAll();
            products.forEach(System.out::println);

            Product lastProduct = ProductRepositoryImpl.getInstance().getLast();

            System.out.println("Method update");
            lastProduct.setName("Test Update");
            ProductRepositoryImpl.getInstance().update(lastProduct);


            System.out.println();
            System.out.println("Method getById()");
            Product productById = ProductRepositoryImpl.getInstance().getById(lastProduct.getId());
            System.out.println(productById);

            System.out.println();
            System.out.println("Method delete");
            ProductRepositoryImpl.getInstance().delete(lastProduct.getId());

            System.out.println();
            System.out.println("Method getAll()");
            List<Product> products2 = ProductRepositoryImpl.getInstance().getAll();
            products2.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}