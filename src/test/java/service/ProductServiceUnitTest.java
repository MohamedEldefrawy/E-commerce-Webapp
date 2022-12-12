package service;

import com.vodafone.model.Product;
import com.vodafone.repository.product.IProductRepository;
import com.vodafone.repository.product.ProductRepository;
import com.vodafone.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceUnitTest {
    private static final IProductRepository productRepository = mock(ProductRepository.class);
    private static ProductService productService;

    @BeforeAll
    public static void setup() {
        productService = new ProductService();
        TestUtils.injectObjects(productService, "productRepository", productRepository);
    }


    @Test
    public void testCreateProductHappyPath() {
        Product dummyProduct = createProduct();
        when(productRepository.create(dummyProduct)).thenReturn(true);
        boolean result = productService.create(dummyProduct);
        assertTrue(result);
    }

    @Test
    public void testCreateProductSadPath() {
        Product dummyProduct = createProduct();
        when(productRepository.create(dummyProduct)).thenReturn(false);
        boolean result = productService.create(dummyProduct);
        assertFalse(result);
    }

    @Test
    public void testUpdateProductHappyPath() {
        Product dummyProduct = createProduct();
        when(productRepository.update(dummyProduct.getId(), dummyProduct)).thenReturn(true);
        boolean result = productService.update(dummyProduct.getId(), dummyProduct);
        assertTrue(result);
    }

    @Test
    public void testUpdateProductSadPath() {
        Product dummyProduct = createProduct();
        when(productRepository.update(dummyProduct.getId(), dummyProduct)).thenReturn(false);
        boolean result = productService.update(dummyProduct.getId(), dummyProduct);
        assertFalse(result);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> dummyProducts = createProducts();
        when(productRepository.getAll()).thenReturn(dummyProducts);
        List<Product> result = productService.getAll();
        assertNotNull(result);
        assertEquals(2, dummyProducts.size());
    }

    @Test
    public void testGetProductById() {
        Product dummyProduct = createProduct();
        when(productRepository.get(dummyProduct.getId())).thenReturn(dummyProduct);
        Product result = productService.get(dummyProduct.getId());
        assertNotNull(result);
        assertEquals(dummyProduct.getId(), result.getId());
        assertEquals(dummyProduct.getName(), result.getName());
    }

    @Test
    public void testGetProductByName() {
        Product dummyProduct = createProduct();
        when(productRepository.getByName(dummyProduct.getName())).thenReturn(dummyProduct);
        Product result = productService.getByName(dummyProduct.getName());
        assertNotNull(result);
        assertEquals(dummyProduct.getId(), result.getId());
        assertEquals(dummyProduct.getName(), result.getName());
    }

    @Test
    public void testGetProductByCategory() {
        List<Product> dummyProducts = createProducts();
        when(productRepository.getByCategory(dummyProducts.get(0).getCategory())).thenReturn(dummyProducts);
        List<Product> result = productService.getByCategory(dummyProducts.get(0).getCategory());
        assertNotNull(result);
        assertEquals(dummyProducts.size(), result.size());
    }

    @Test
    public void testDeleteProductHappyPath() {
        when(productRepository.delete(1L)).thenReturn(true);
        boolean result = productService.delete(1L);
        assertTrue(result);
    }

    @Test
    public void testDeleteProductSadPath() {
        when(productRepository.delete(1L)).thenReturn(false);
        boolean result = productService.delete(1L);
        assertFalse(result);
    }


    private static Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("dummyProduct");
        product.setPrice(200);
        product.setDescription("dummy product description");
        product.setRate(3f);
        product.setInStock(200);
        product.setImage("myImage.png");
        product.setCategory("Cats");
        return product;
    }

    private static List<Product> createProducts() {
        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("dummyProduct");
        product1.setPrice(200);
        product1.setDescription("dummy product description");
        product1.setRate(3f);
        product1.setInStock(200);
        product1.setImage("myImage.png");
        product1.setCategory("Cats");

        Product product2 = new Product();
        product2.setId(3L);
        product2.setName("dummyProduct2");
        product2.setPrice(200);
        product2.setDescription("dummy product description2");
        product2.setRate(3f);
        product2.setInStock(200);
        product2.setImage("myImage2.png");
        product2.setCategory("Cats");
        return Arrays.asList(product1, product2);
    }
}
