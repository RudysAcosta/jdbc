package dev.ncrousset.repositories;

import dev.ncrousset.models.Product;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryImplTest {

    private static ProductRepositoryImpl repository;

    @BeforeAll
    static void setup() {
        repository = ProductRepositoryImpl.getInstance();
    }

//    @Test
//    @Order(1)
//    void testSaveProduct() {
//        Product product = new Product();
//        product.setName("Test Product");
//        product.setPrice(100);
//        product.setDate(new java.util.Date()); // hoy
//
//        repository.save(product);
//        Product last = repository.getLast();
//
//        assertNotNull(last);
//        assertEquals("Test Product", last.getName());
//    }

    @Test
    @Order(2)
    void testGetAll() {
        List<Product> products = repository.getAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    @Order(3)
    void testGetById() {
        Product last = repository.getLast();
        Product found = repository.getById(last.getId());

        assertNotNull(found);
        assertEquals(last.getId(), found.getId());
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        Product last = repository.getLast();
        last.setName("Updated Product");
        last.setPrice(200);

        repository.update(last);

        Product updated = repository.getById(last.getId());
        assertEquals("Updated Product", updated.getName());
        assertEquals(200, updated.getPrice());
    }

    @Test
    @Order(5)
    void testDeleteProduct() {
        Product last = repository.getLast();
        Long idToDelete = last.getId();

        repository.delete(idToDelete);

        Product deleted = repository.getById(idToDelete);
        assertNull(deleted);
    }
}