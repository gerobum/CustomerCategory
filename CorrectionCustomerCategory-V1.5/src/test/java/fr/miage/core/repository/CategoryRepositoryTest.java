/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.core.repository;

import fr.miage.core.entity.Category;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author yvan
 */

// #### V1.2  @RunWith(SpringJUnit4ClassRunner.class) Indique que la classe utilise JUnit pour les tests unitaires
// #### V1.2 https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/junit4/SpringJUnit4ClassRunner.html
@RunWith(SpringJUnit4ClassRunner.class)// #### V1.2 Equivalent à @RunWith(SpringRunner.class)

// #### V1.2  @DataJpaTest doit être employé dans le cas de test Jpa, notamment 
// #### V1.2  pour que l'injection de dépendance se passe bien.

// #### V1.2  https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/orm/jpa/DataJpaTest.html
@DataJpaTest
public class CategoryRepositoryTest {
    // #### V1.2  Du logging peut être utile
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRepositoryTest.class);
    // #### V1.2 Du logging peut être utile
    
    // #### V1.2 Injection de dépendance 
    @Autowired
    CategoryRepository categoryRepository;
    

    public CategoryRepositoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    // #### V1.2 Des exemples sont enregistrés avant chaque test. 
    @Before
    public void setUp() {
        saveCategory("A");
        saveCategory("B");
        saveCategory("Z");
    }

    private void saveCategory(String name) {
        if (categoryRepository.findByName(name) == null) {
            Category category = new Category(name);
            categoryRepository.save(category);
        }
    }

    @After
    public void tearDown() {
    }

    // #### V1.2 Test pour voir si le findByName se passe bien.
    @Test
    public void testFindByName() {
        LOGGER.info("Test findByName");
        System.out.println("Test findByName");
        assertNotNull(categoryRepository.findByName("A"));
        assertNull(categoryRepository.findByName("W"));
        saveCategory("W");
        assertNotNull(categoryRepository.findByName("W"));
    }

    // #### V1.2 Et ainsi de suite. Voir un cours sur les tests unitaires.
    @Test
    public void testCount() {
        LOGGER.info("Test Count");
        System.out.println("Test Count");
        assertEquals(3, categoryRepository.count());
        saveCategory("W");
        assertEquals(4, categoryRepository.count());
    }

    // #### V1.2 Ce ne sont que des exemples. 
    @Test
    public void testDelete() {
        LOGGER.info("Test Count");
        System.out.println("Test Count");
        Category a = categoryRepository.findByName("A");
        long n = categoryRepository.count();
        categoryRepository.delete(a);
        assertEquals(n-1, categoryRepository.count());
        categoryRepository.deleteAll();
        assertEquals(0, categoryRepository.count());
    }

}
