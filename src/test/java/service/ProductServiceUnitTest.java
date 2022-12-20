package service;

import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.repository.product.IProductRepository;
import com.vodafone.service.ProductService;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceUnitTest {
    /*private final IProductRepository productRepository = mock(IProductRepository.class);
    private final ProductService productService = new ProductService(productRepository);


    @Test
    void createTest_sendProductObject_returnTrue() {
        Product dummyProduct = createProduct();
        when(productRepository.save(any(Product.class))).thenReturn(dummyProduct);
        boolean result = productService.create(dummyProduct);
        assertTrue(result);
    }

    @Test
    void createTest_sendProductObject_returnFalse() {
        Product dummyProduct = createProduct();
        when(productRepository.save(any(Product.class))).thenReturn(dummyProduct);

        assertThrows(CreateProductException.class, () -> productService.create(dummyProduct));
    }

    @Test
    void updateTest_sendProductIdAndProductObject_returnTrue() {
        Product dummyProduct = createProduct();
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(dummyProduct));
        when(productRepository.save(any(Product.class))).thenReturn(dummyProduct);
        boolean result = productService.update(dummyProduct);
        assertTrue(result);
    }

    @Test
    void updateTest_sendProductIdAndProductObject_throwsGetProductException() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(createProduct());
        assertThrows(GetProductException.class, () -> productService.update(new Product()));
    }

    @Test
    void getAllTest_sendNothing_returnListOfProducts() {
        List<Product> dummyProducts = createProducts();
        when(productRepository.findAll()).thenReturn(dummyProducts);
        List<Product> result = productService.getAll();
        assertNotNull(result);
        assertEquals(2, dummyProducts.size());
    }

    @Test
    void getAllTest_sendNothing_throwsGetProductException() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(GetProductException.class, productService::getAll);
    }

    @Test
    void getByIdTest_sendProductId_returnProduct() {
        Product dummyProduct = createProduct();
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(dummyProduct));
        Product result = productService.getById(dummyProduct.getId());
        assertNotNull(result);
        assertEquals(dummyProduct.getId(), result.getId());
        assertEquals(dummyProduct.getName(), result.getName());
    }

    @Test
    void getByIdTest_sendProductId_throwGetProductException() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.getById(1L));
    }

    @Test
    void getByNameTest_sendProductName_returnProduct() {
        List<Product> dummyProducts = Collections.singletonList(createProduct());
        when(productRepository.findAllByName(any(String.class))).thenReturn(Optional.of(dummyProducts));
        List<Product> result = productService.getByName(dummyProducts.get(0).getName());
        assertNotNull(result);
        assertEquals(dummyProducts.size(), result.size());
    }

    @Test
    void getByNameTest_sendProductName_throwsGetProductException() {
        when(productRepository.findAllByName(any(String.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.getByName("test"));
    }

    @Test
    void getByCategoryTest_sendProductCategory_returnProduct() {
        List<Product> dummyProducts = createProducts();
        when(productRepository.findAllByCategory(any(String.class))).thenReturn(Optional.of(dummyProducts));
        List<Product> result = productService.getByCategory(dummyProducts.get(0).getCategory());
        assertNotNull(result);
        assertEquals(dummyProducts.size(), result.size());
    }


    @Test
    void getByCategoryTest_sendProductCategory_throwsGetProductException() {
        when(productRepository.findAllByCategory(any(String.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.getByCategory("test"));
    }

    @Test
    void deleteTest_sendProductId_returnTrue() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(createProduct()));
        doNothing().when(productRepository).delete(any(Product.class));
        boolean result = productService.delete(1L);
        assertTrue(result);
    }

    @Test
    void deleteTest_sendProductId_throwsGetProductException() {
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, () -> productService.delete(1L));
    }

    @Test
    void getAvailableProduct_sendNothing_returnListOfProducts() {
        List<Product> products = createProducts();
        when(productRepository.findAllByDeletedEquals(false)).thenReturn(Optional.of(products));
        List<Product> result = productService.getAvailableProducts();
        assertNotNull(result);
        assertEquals(products.size(), result.size());
    }

    @Test
    void getAvailableProduct_sendNothing_throwsGetProductException() {
        when(productRepository.findAllByDeletedEquals(false)).thenReturn(Optional.empty());
        assertThrows(GetProductException.class, productService::getAvailableProducts);
    }


    private Product createProduct() {
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

    private List<Product> createProducts() {
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

     */
}
