package service;

import com.vodafone.exception.NegativeQuantityException;
import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Customer;
import com.vodafone.model.Product;
import com.vodafone.repository.cart.CartRepository;
import com.vodafone.repository.cart.ICartRepository;
import com.vodafone.service.CartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceUnitTest {
    private static final ICartRepository cartRepositoryMock = mock(CartRepository.class);
    private static final CartService cartService = new CartService(cartRepositoryMock);

//    @BeforeAll
//    public static void setupEnvironment() {
//        cartService = new CartService(cartRepositoryMock);
//    }

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void creatCart_nonNullableEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.create(cart)).thenReturn(true);
        //Act
        boolean isCartCreated = cartService.create(cart);
        //Assert
        assertTrue(isCartCreated);
    }

    @Test(expected = NullPointerException.class)
    public void creatCart_nullEntity_expectException() {
        //Arrange
        Cart cart = null;
        when(cartRepositoryMock.create(cart)).thenThrow(new NullPointerException());
        //Act
        cartService.create(cart);
    }

    @Test
    public void updateCart_nonNullableEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        //update cart
        Cart updatedCart = cart;
        //create new dummy product
        Product product = new Product();
        product.setName("WC football 2022");
        product.setInStock(35);
        product.setPrice(300);
        product.setRate(4.7f);
        product.setDescription("World Cup 2022 Official football");
        items.add(new CartItem(2, product, updatedCart));
        updatedCart.setItems(items);
        when(cartRepositoryMock.update(1L, updatedCart)).thenReturn(true);
        //Act
        boolean isCartUpdated = cartService.update(1L, updatedCart);
        //Assert
        assertTrue(isCartUpdated);
    }

    @Test(expected = NullPointerException.class)
    public void updateCart_notExistingIdCart_expectException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.update(150L, cart)).thenThrow(new NullPointerException());
        //Act
        cartService.update(150L, cart);
    }

    @Test(expected = NullPointerException.class)
    public void updateCart_nullCartEntity_expectException() {
        //Arrange
        Cart cart = null;
        when(cartRepositoryMock.update(1L, cart)).thenThrow(new NullPointerException());
        //Act
        cartService.update(1L, cart);
    }

    @Test
    public void deleteCart_nonNullableEntity_expectTrue() {
        //Arrange
        when(cartRepositoryMock.delete(1L)).thenReturn(true);
        //Act
        boolean isCartDeleted = cartService.delete(1L);
        //Assert
        assertTrue(isCartDeleted);
    }

    @Test(expected = NullPointerException.class)
    public void deleteCart_notExistingIdCart_expectException() {
        //Arrange
        when(cartRepositoryMock.delete(150L)).thenThrow(new NullPointerException());
        //Act
        cartService.delete(150L);
    }

    @Test
    public void getCart_nonNullableEntity_expectObject() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.get(1L)).thenReturn(cart);
        //Act
        Cart cartEntity = cartService.get(1L);
        //Assert
        assertNotNull(cartEntity);
    }

    @Test
    public void getCart_notExistingIdCart_expectNull() {
        //Arrange
        when(cartRepositoryMock.get(1623L)).thenReturn(null);
        //Act
        Cart cartEntity = cartService.get(1L);
        //Assert
        assertNull(cartEntity);
    }

    @Test
    public void getAllCart_expectNonEmptyList() {
        //Arrange
        ArrayList<Cart> carts = new ArrayList<>();
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        Cart cart = new Cart();
        ArrayList<CartItem> items = new ArrayList<>();
        items.add(new CartItem(13, new Product(), cart));
        cart.setCustomer(customer);
        cart.setItems(items);
        carts.add(cart);
        when(cartRepositoryMock.getAll()).thenReturn(carts);
        //Act
        List<Cart> cartList = cartService.getAll();
        //Assert
        assertEquals(1, cartList.size());
    }

    @Test
    public void removeItem_nonNullCart_expectTrue() {
        //Arrange
        when(cartRepositoryMock.removeItem(1L, 1L)).thenReturn(true);
        //Act
        boolean isItemDeleted = cartService.removeItem(1L, 1L);
        //Assert
        assertTrue(isItemDeleted);
    }

    @Test(expected = NullPointerException.class)
    public void removeItem_nullCart_expectException() {
        //Arrange
        when(cartRepositoryMock.removeItem(4L, 1L)).thenThrow(new NullPointerException());
        //Act
        cartService.removeItem(4L, 1L);
    }

    @Test(expected = NullPointerException.class)
    public void removeItem_nullItem_expectException() {
        //Arrange
        when(cartRepositoryMock.removeItem(1L, 15L)).thenThrow(new NullPointerException());
        //Act
        cartService.removeItem(1L, 15L);
    }

    @Test
    public void clearCart_nonNullCartId_expectTrue() {
        //Arrange
        when(cartRepositoryMock.clearCart(1L)).thenReturn(true);
        //Act
        boolean isCartCleared = cartService.clearCart(1L);
        //Assert
        assertTrue(isCartCleared);
    }

    @Test(expected = NullPointerException.class)
    public void clearCart_nullCartId_expectException() {
        //Arrange
        when(cartRepositoryMock.clearCart(1042L)).thenThrow(new NullPointerException());
        //Act
        cartService.clearCart(1042L);
    }

    @Test
    public void addItem_nonNullCartAndCartItem_expectTrue() {
        //Arrange
        CartItem cartItem = new CartItem(2L, 10, new Product(), new Cart(), 500);
        when(cartRepositoryMock.addItem(1L, cartItem)).thenReturn(true);
        //Act
        boolean isItemInserted = cartService.addItem(1L, cartItem);
        //Assert
        assertTrue(isItemInserted);
    }

    @Test(expected = NullPointerException.class)
    public void addItem_nonNullCartAndNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(1L, null)).thenThrow(new NullPointerException());
        //Act
        cartService.addItem(1L, null);
    }

    @Test(expected = NullPointerException.class)
    public void addItem_nullCartAndNonNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(null, new CartItem())).thenThrow(new NullPointerException());
        //Act
        cartService.addItem(null, new CartItem());
    }

    @Test(expected = NullPointerException.class)
    public void addItem_nullCartAndNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(null, null)).thenThrow(new NullPointerException());
        //Act
        cartService.addItem(null, null);
    }

    @Test
    public void setProductQuantity_nonNullCartAndNonNullCartItemAndPositiveQuantity_expectException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(1L, 1L, 30)).thenReturn(30);
        //Act
        int quantity = cartService.setProductQuantity(1L, 1L, 30);
        //Assert
        assertEquals(30, quantity);
    }

    @Test(expected = NullPointerException.class)
    public void setProductQuantity_nullCartAndNonNullCartItemAndPositiveQuantity_expectException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(null, 1L, 30)).thenThrow(new NullPointerException());
        //Act
        cartService.setProductQuantity(null, 1L, 30);
    }

    @Test(expected = NullPointerException.class)
    public void setProductQuantity_nonNullCartAndNullCartItemAndPositiveQuantity_expectException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(1L, null, 30)).thenThrow(new NullPointerException());
        //Act
        cartService.setProductQuantity(1L, null, 30);
    }

    @Test(expected = NegativeQuantityException.class)
    public void setProductQuantity_nonNullCartAndNonNullCartItemAndNegativeQuantity_expectException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(1L, 1L, -100)).thenThrow(new NegativeQuantityException("Negative quantity provided"));
        //Act
        cartService.setProductQuantity(1L, 1L, -100);
    }
}
