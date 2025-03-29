package dev.ncrousset.repositories;

import dev.ncrousset.models.Category;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryRepositoryImplTest {

    private static CategoryRepositoryImpl repository;

    @BeforeAll
    static void setup() {
        repository = CategoryRepositoryImpl.getInstance();
    }

    @Test
    @Order(1)
    void testSaveCategory() {
        Category category = new Category();
        category.setName("Test Category");

        repository.save(category);
        Category last = repository.getLast();

        assertNotNull(last);
        assertEquals("Test Category", last.getName());
    }

    @Test
    @Order(2)
    void testGetAll() {
        List<Category> categories = repository.getAll();
        assertNotNull(categories);
        assertTrue(categories.size() > 0);
    }

    @Test
    @Order(3)
    void testGetById() {
        Category last = repository.getLast();
        Category found = repository.getById(last.getId());

        assertNotNull(found);
        assertEquals(last.getId(), found.getId());
    }

    @Test
    @Order(4)
    void testUpdateCategory() {
        Category last = repository.getLast();
        last.setName("Updated Category");

        repository.update(last);

        Category updated = repository.getById(last.getId());
        assertEquals("Updated Category", updated.getName());
    }

    @Test
    @Order(5)
    void testDeleteCategory() {
        Category last = repository.getLast();
        Long idToDelete = last.getId();

        repository.delete(idToDelete);
        Category deleted = repository.getById(idToDelete);

        assertNull(deleted);
    }
}