package service;

import com.vodafone.exception.cart.NegativeQuantityException;
import com.vodafone.exception.cart.NullCartException;
import com.vodafone.exception.cart.NullCartItemException;
import com.vodafone.exception.NullIdException;
import com.vodafone.model.Cart;
import com.vodafone.model.CartItem;
import com.vodafone.model.Customer;
import com.vodafone.model.Product;
import com.vodafone.repository.cart.CartRepository;
import com.vodafone.repository.cart.ICartRepository;
import com.vodafone.service.CartService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceUnitTest {
    private static final ICartRepository cartRepositoryMock = mock(CartRepository.class);
    private static final CartService cartService = new CartService(cartRepositoryMock);

    @Test
    public void creatCart_nonNullableEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.create(cart)).thenReturn(Optional.of(any(Long.class)));
        //Act
        boolean isCartCreated = cartService.create(cart);
        //Assert
        assertTrue(isCartCreated);
    }

    @Test
    public void creatCart_nullEntity_expectException() {
        //Arrange
        when(cartRepositoryMock.create(null)).thenThrow(new NullCartException("Null cart is provided"));
        //Act
        assertThrows(NullCartException.class, () -> cartService.create(null));
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
        //create new dummy product
        Product product = new Product();
        product.setName("WC football 2022");
        product.setInStock(35);
        product.setPrice(300);
        product.setRate(4.7f);
        product.setDescription("World Cup 2022 Official football");
        items.add(new CartItem(2, product, cart));
        cart.setItems(items);
        when(cartRepositoryMock.update(1L, cart)).thenReturn(true);
        //Act
        boolean isCartUpdated = cartService.update(1L, cart);
        //Assert
        assertTrue(isCartUpdated);
    }

    @Test
    public void updateCart_nullCartId_expectException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.update(null, cart)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.update(null, cart));
    }

    @Test
    public void updateCart_nullCartEntity_expectException() {
        //Arrange
        when(cartRepositoryMock.update(1L, null)).thenThrow(new NullCartException("Null cart is provided"));
        //Act
        cartService.update(1L, null);
        assertThrows(NullCartException.class, () -> cartService.update(1L, null));
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

    @Test
    public void deleteCart_notExistingIdCart_expectException() {
        //Arrange
        when(cartRepositoryMock.delete(null)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.delete(null));
    }

    @Test
    public void getCart_nonNullEntity_expectObject() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.getById(any(Long.class))).thenReturn(Optional.of(cart));
        //Act
        Cart cartEntity = cartService.get(1L);
        //Assert
        assertNotNull(cartEntity);
    }

    @Test
    public void getCart_notExistingIdCart_expectNull() {
        //Arrange
        when(cartRepositoryMock.getById(null)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        Cart cartEntity = cartService.get(null);
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
        when(cartRepositoryMock.getAll()).thenReturn(Optional.of(carts));
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

    @Test
    public void removeItem_nullCart_expectException() {
        //Arrange
        when(cartRepositoryMock.removeItem(null, 1L)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullCartException.class, () -> cartService.removeItem(null, 1L));
    }

    @Test
    public void removeItem_nullItem_expectException() {
        //Arrange
        when(cartRepositoryMock.removeItem(1L, null)).thenThrow(new NullIdException("Null cart item id is provided"));
        //Act
        cartService.removeItem(1L, null);
        assertThrows(NullIdException.class, () -> cartService.removeItem(1L, null));
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

    @Test
    public void clearCart_nullCartId_expectException() {
        //Arrange
        when(cartRepositoryMock.clearCart(1042L)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.clearCart(1042L));
    }

    @Test
    public void addItem_nonNullCartAndCartItem_expectTrue() {
        //Arrange
        CartItem cartItem = new CartItem(2L, 10, new Product(), new Cart(), 500);
        when(cartRepositoryMock.addItem(1L, cartItem)).thenReturn(10);
        //Act
        int quantityAdded = cartService.addItem(1L, cartItem);
        //Assert
        assertEquals(10, quantityAdded);
    }

    @Test
    public void addItem_nonNullCartAndNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(1L, null)).thenThrow(new NullCartItemException("Null cart item is provided"));
        //Act
        assertThrows(NullCartItemException.class, () -> cartService.addItem(1L, null));
    }

    @Test
    public void addItem_nullCartAndNonNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(null, new CartItem())).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.addItem(null, new CartItem()));
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

    @Test
    public void setProductQuantity_nullCartAndNonNullCartItemAndPositiveQuantity_expectException() {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(null, 1L, 30)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.setProductQuantity(null, 1L, 30));
    }

    @Test
    public void setProductQuantity_nonNullCartAndNullCartItemAndPositiveQuantity_expectException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(1L, null, 30)).thenThrow(new NullIdException("Null cart item id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.setProductQuantity(1L, null, 30));
    }

    @Test
    public void setProductQuantity_nonNullCartAndNonNullCartItemAndNegativeQuantity_expectException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(1L, 1L, -100)).thenThrow(new NegativeQuantityException("Negative quantity provided"));
        //Act
        cartService.setProductQuantity(1L, 1L, -100);
        assertThrows(NegativeQuantityException.class, () -> cartService.setProductQuantity(1L, 1L, -100));
    }
}
