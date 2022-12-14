package service;

import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.repository.product.IProductRepository;
import com.vodafone.repository.product.ProductRepository;
import com.vodafone.service.ProductService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceUnitTest {
    private final IProductRepository productRepository = mock(ProductRepository.class);
    private final ProductService productService = new ProductService(productRepository);
    private final Logger logger = LoggerFactory.getLogger(ProductServiceUnitTest.class);


    @Test
    public void createTest_sendProductObject_returnTrue() {
        Product dummyProduct = createProduct();
        when(productRepository.create(any(Product.class))).thenReturn(Optional.of(1L));
        boolean result = false;
        try {
            result = productService.create(dummyProduct);
        } catch (CreateProductException e) {
            logger.info(e.getMessage());
        }
        assertTrue(result);
    }

    @Test
    public void createTest_sendProductObject_returnFalse() {
        Product dummyProduct = createProduct();
        when(productRepository.create(any(Product.class))).thenReturn(Optional.empty());

        assertThrows(CreateProductException.class, () -> productService.create(dummyProduct));
    }

    @Test
    public void updateTest_sendProductIdAndProductObject_returnTrue() {
        Product dummyProduct = createProduct();
        when(productRepository.getById(any(Long.class))).thenReturn(Optional.of(dummyProduct));
        when(productRepository.update(any(Long.class), any(Product.class))).thenReturn(true);
        boolean result = false;
        try {
            result = productService.update(dummyProduct.getId(), dummyProduct);
        } catch (GetProductException e) {
            logger.info(e.getMessage());
        }
        assertTrue(result);
    }

    @Test
    public void updateTest_sendProductIdAndProductObject_throwsGetProductException() {
        when(productRepository.getById(any(Long.class))).thenReturn(Optional.empty());
        when(productRepository.update(any(Long.class), any(Product.class))).thenReturn(false);
        assertThrows(GetProductException.class, () -> productService.update(1L, new Product()));
    }

    @Test
    public void getAllTest_sendNothing_returnListOfProducts() {
        List<Product> dummyProducts = createProducts();
        when(productRepository.getAll()).thenReturn(Optional.of(dummyProducts));
        List<Product> result = null;
        try {
            result = productService.getAll();
        } catch (GetProductException e) {
            logger.info(e.getMessage());
        }
        assertNotNull(result);
        assertEquals(2, dummyProducts.size());
    }

    @Test
    public void getAllTest_sendNothing_throwsGetProductException() {
        when(productRepository.getAll()).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, productService::getAll);
    }

    @Test
    public void getByIdTest_sendProductId_returnProduct() {
        Product dummyProduct = createProduct();
        when(productRepository.getById(any(Long.class))).thenReturn(Optional.of(dummyProduct));
        Product result = null;
        try {
            result = productService.getById(dummyProduct.getId());
        } catch (GetProductException e) {
            logger.info(e.getMessage());
        }
        assertNotNull(result);
        assertEquals(dummyProduct.getId(), result.getId());
        assertEquals(dummyProduct.getName(), result.getName());
    }

    @Test
    public void getByIdTest_sendProductId_throwGetProductException() {
        when(productRepository.getById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.getById(1L));
    }

    @Test
    public void getByNameTest_sendProductName_returnProduct() {
        List<Product> dummyProducts = Collections.singletonList(createProduct());
        when(productRepository.getByName(any(String.class))).thenReturn(Optional.of(dummyProducts));
        Product result = null;
        try {
            result = productService.getByName(dummyProducts.get(0).getName());
        } catch (GetProductException e) {
            logger.info(e.getMessage());
        }
        assertNotNull(result);
        assertEquals(dummyProducts.get(0).getId(), result.getId());
        assertEquals(dummyProducts.get(0).getName(), result.getName());
    }

    @Test
    public void getByNameTest_sendProductName_throwsGetProductException() {
        when(productRepository.getByName(any(String.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.getByName("test"));
    }

    @Test
    public void getByCategoryTest_sendProductCategory_returnProduct() {
        List<Product> dummyProducts = createProducts();
        when(productRepository.getByCategory(any(String.class))).thenReturn(Optional.of(dummyProducts));
        List<Product> result = null;
        try {
            result = productService.getByCategory(dummyProducts.get(0).getCategory());
        } catch (GetProductException e) {
            logger.info(e.getMessage());
        }
        assertNotNull(result);
        assertEquals(dummyProducts.size(), result.size());
    }


    @Test
    public void getByCategoryTest_sendProductCategory_throwsGetProductException() {
        when(productRepository.getByCategory(any(String.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.getByCategory("test"));
    }

    @Test
    public void deleteTest_sendProductId_returnTrue() {
        when(productRepository.getById(any(Long.class))).thenReturn(Optional.of(createProduct()));
        when(productRepository.delete(any(Long.class))).thenReturn(true);
        boolean result = false;
        try {
            result = productService.delete(1L);
        } catch (GetProductException e) {
            logger.info(e.getMessage());
        }
        assertTrue(result);
    }

    @Test
    public void deleteTest_sendProductId_throwsGetProductException() {
        when(productRepository.getById(any(Long.class))).thenReturn(Optional.empty());
        when(productRepository.delete(any(Long.class))).thenReturn(false);

        assertThrows(GetProductException.class, () -> productService.delete(1L));
    }

    @Test
    public void getAvailableProduct_sendNothing_returnListOfProducts() {
        List<Product> products = createProducts();
        when(productRepository.getAvailableProducts()).thenReturn(Optional.of(products));
        List<Product> result = productService.getAvailableProducts();
        assertNotNull(result);
        assertEquals(products.size(), result.size());
    }

    @Test
    public void getAvailableProduct_sendNothing_throwsGetProductException() {
        when(productRepository.getAvailableProducts()).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, productService::getAvailableProducts);
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
