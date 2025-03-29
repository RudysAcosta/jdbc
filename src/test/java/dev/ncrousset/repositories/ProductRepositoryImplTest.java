package dev.ncrousset.repositories;

import dev.ncrousset.models.Category;
import dev.ncrousset.models.Product;
import org.junit.jupiter.api.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryImplTest {

    private static ProductRepositoryImpl productRepo;
    private static CategoryRepositoryImpl categoryRepo;
    private static Category testCategory;

    @BeforeAll
    static void setup() {
        productRepo = ProductRepositoryImpl.getInstance();
        categoryRepo = CategoryRepositoryImpl.getInstance();

        // Creamos una categoría de prueba si no existe
        testCategory = new Category();
        testCategory.setName("JUnit Category");
        categoryRepo.save(testCategory);

        testCategory = categoryRepo.getLast(); // actualizamos con ID
    }

    @Test
    @Order(1)
    void testSaveProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(999);
        product.setDate(new Date());
        product.setCategory(testCategory);

        productRepo.save(product);

        Product last = productRepo.getLast();

        assertNotNull(last);
        assertEquals("Test Product", last.getName());
        assertEquals(testCategory.getId(), last.getCategory().getId());
    }

    @Test
    @Order(2)
    void testGetAll() {
        List<Product> products = productRepo.getAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    @Order(3)
    void testGetById() {
        Product last = productRepo.getLast();
        Product found = productRepo.getById(last.getId());

        assertNotNull(found);
        assertEquals(last.getId(), found.getId());
    }

    @Test
    @Order(4)
    void testUpdateProduct() {
        Product last = productRepo.getLast();
        last.setName("Updated Product");
        last.setPrice(1500);

        productRepo.update(last);

        Product updated = productRepo.getById(last.getId());
        assertEquals("Updated Product", updated.getName());
        assertEquals(1500, updated.getPrice());
    }

    @Test
    @Order(5)
    void testFindByCategoryId() {
        List<Product> found = productRepo.findByCategoryId(testCategory.getId());

        assertNotNull(found);
        assertTrue(found.stream().anyMatch(p -> p.getCategory().getId().equals(testCategory.getId())));
    }

    @Test
    @Order(6)
    void testDeleteProduct() {
        Product last = productRepo.getLast();
        productRepo.delete(last.getId());

        Product deleted = productRepo.getById(last.getId());
        assertNull(deleted);
    }
}
