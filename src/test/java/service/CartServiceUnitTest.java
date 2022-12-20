package service;

import com.vodafone.exception.NullIdException;
import com.vodafone.exception.cart.NegativeQuantityException;
import com.vodafone.exception.cart.NullCartException;
import com.vodafone.exception.cart.NullCartItemException;
import com.vodafone.model.*;
import com.vodafone.repository.cart.CartRepository;
import com.vodafone.repository.cart.ICartRepository;
import com.vodafone.service.CartService;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CartServiceUnitTest {
    private static final ICartRepository cartRepositoryMock = mock(CartRepository.class);
    private static final CartService cartService = new CartService(cartRepositoryMock);

    @Test
    void creatCart_nonNullableEntity_expectTrue() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        when(cartRepositoryMock.save(cart)).thenReturn(cart);
        //Act
        boolean isCartCreated = cartService.create(cart) != null;
        //Assert
        assertTrue(isCartCreated);
    }

    @Test
    void creatCart_nullEntity_expectException() {
        //Arrange
        Cart cart = null;
        //Act
        assertThrows(NullCartException.class, () -> cartService.create(cart));
    }

    @Test
    void updateCart_nonNullableEntity_expectTrue() {
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
        when(cartRepositoryMock.save(cart)).thenReturn(updatedCart);
        //Act
        boolean isCartUpdated = cartService.update(1L, cart) == updatedCart;
        //Assert
        assertTrue(isCartUpdated);
    }

    @Test
    void updateCart_nullCartId_expectException() {
        //Arrange
        Customer customer = new Customer();
        customer.setUserName("Mohammed Yasser");
        customer.setEmail("mohammedre4a@gmail.com");
        ArrayList<CartItem> items = new ArrayList<>();
        Cart cart = new Cart(customer, items);
        //Act
        assertThrows(NullIdException.class, () -> cartService.update(null, cart));
    }

    @Test
    void updateCart_nullCartEntity_expectException() {
        //Arrange
        Cart cart = null;
        //Act
        assertThrows(NullCartException.class, () -> cartService.update(1L, cart));
    }

    @Test
    void deleteCart_nonNullableEntity_expectTrue() {
        //Arrange
        Cart cart = new Cart();
        doNothing().when(cartRepositoryMock.delete(cart));
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cart));
        //Act
        boolean isCartDeleted = cartService.delete(1L);
        //Assert
        assertTrue(isCartDeleted);
    }

    @Test
    void deleteCart_nullCartId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        //Act
        assertThrows(NullIdException.class, () -> cartService.delete(null));
    }

    @Test
    void deleteCart_nonExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.delete(anyLong()));
    }

    @Test
    void getCart_nonNullEntity_expectObject() {
        //Arrange
        when(cartRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(new Cart()));
        //Act
        Cart cartEntity = cartService.get(1L);
        //Assert
        assertNotNull(cartEntity);
    }

    @Test
    void getCart_nullIdCart_expectNullIdException() {
        //Arrange
        Long id = null;
        //Act
        assertThrows(NullIdException.class, () -> cartService.get(id));
    }

    @Test
    void getCart_nonExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.get(anyLong()));
    }

    @Test
    void getAllCarts_expectNonEmptyList() {
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
        when(cartRepositoryMock.findAll()).thenReturn(carts);
        //Act
        List<Cart> cartList = cartService.getAll();
        //Assert
        assertEquals(1, cartList.size());
    }

    @Test
    void removeItem_nonNullCartIdAndNonNullItemId_expectTrue() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        when(cartRepositoryMock.removeItem(any(Cart.class), anyLong())).thenReturn(true);
        //Act
        boolean isItemDeleted = cartService.removeItem(1L, 1L);
        //Assert
        assertTrue(isItemDeleted);
    }

    @Test
    void removeItem_nullCartIdAndNonNullItemId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.removeItem(null, 1L)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.removeItem(null, 1L));
    }

    @Test
    void removeItem_nonNullCartIdAndNullItemId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.removeItem(any(Cart.class), anyLong())).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.removeItem(1L, null));
    }

    @Test
    void removeItem_notExistCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.removeItem(any(Cart.class), anyLong())).thenThrow(new NullIdException("Null cart id is provided"));
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.removeItem(1L, 1L));
    }

    @Test
    void removeItem_nullItem_expectException() {
        //Arrange
        when(cartRepositoryMock.removeItem(new Cart(), null)).thenThrow(new NullIdException("Null cart item id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.removeItem(1L, null));
    }

    @Test
    void clearCart_nonNullCartId_expectTrue() {
        //Arrange
        when(cartRepositoryMock.clearCart(any(Cart.class))).thenReturn(true);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        //Act
        boolean isCartCleared = cartService.clearCart(1L);
        //Assert
        assertTrue(isCartCleared);
    }

    @Test
    void clearCart_nullCartId_expectNullIdException() {
        //Arrange
        Long cartId = null;
        //Act
        assertThrows(NullIdException.class, () -> cartService.clearCart(cartId));
    }

    @Test
    void clearCart_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.clearCart(any(Cart.class))).thenReturn(true);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.clearCart(1L));
    }

    @Test
    void addItem_nonNullCartAndCartItem_expectTrue() {
        //Arrange
        when(cartRepositoryMock.addItem(any(Cart.class), any(CartItem.class))).thenReturn(10);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        //Act
        int quantityAdded = cartService.addItem(1L, new CartItem());
        //Assert
        assertEquals(10, quantityAdded);
    }

    @Test
    void addItem_nonNullCartAndNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(new Cart(), null)).thenThrow(new NullCartItemException("Null cart item is provided"));
        //Act
        assertThrows(NullCartItemException.class, () -> cartService.addItem(1L, null));
    }

    @Test
    void addItem_nullCartAndNonNullCartItem_expectException() {
        //Arrange
        when(cartRepositoryMock.addItem(null, new CartItem())).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.addItem(null, new CartItem()));
    }

    @Test
    void addItem_notExistingCart_expectException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.addItem(anyLong(), new CartItem()));
    }


    @Test
    void setProductQuantity_nonNullCartAndNonNullCartItemAndPositiveQuantity_expectInt() {
        //Arrange
        int newQuantity = 30;
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        when(cartRepositoryMock.setProductQuantity(any(Cart.class), anyLong(), anyInt())).thenReturn(newQuantity);
        //Act
        int quantity = cartService.setProductQuantity(1L, 1L, newQuantity);
        //Assert
        assertEquals(30, quantity);
    }

    @Test
    void setProductQuantity_nullCartAndNonNullCartItemAndPositiveQuantity_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.setProductQuantity(null, 1L, 30)).thenThrow(new NullIdException("Null cart id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.setProductQuantity(null, 1L, 30));
    }

    @Test
    void setProductQuantity_nonNullCartAndNullCartItemAndPositiveQuantity_expectNullIdException() throws NegativeQuantityException {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        when(cartRepositoryMock.setProductQuantity(new Cart(), null, 30)).thenThrow(new NullIdException("Null cart item id is provided"));
        //Act
        assertThrows(NullIdException.class, () -> cartService.setProductQuantity(1L, null, 30));
    }

    @Test
    void setProductQuantity_nonNullCartAndNonNullCartItemAndNegativeQuantity_expectNegativeQuantityException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        //Act
        assertThrows(NegativeQuantityException.class, () -> cartService.setProductQuantity(1L, 1L, -100));
    }

    @Test
    void setProductQuantity_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.setProductQuantity(1L, 1L, 100));
    }

    @Test
    void getCartItems_nonNullCartIdAndExistingCart_expectNonEmptyList() {
        //Arrange
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem());
        Cart cart = new Cart();
        cart.setItems(items);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cart));
        //Act
        List<CartItem> cartItems = cartService.getCartItems(anyLong());
        //Assert
        assertTrue(cartItems.size() > 0);
    }

    @Test
    void getCartItems_nullCartIdAndExistingCart_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        //Act
        assertThrows(NullIdException.class, () -> cartService.getCartItems(null));
    }

    @Test
    void getCartItems_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.getCartItems(anyLong()));
    }

    @Test
    void incrementProductQuantity_nonNullCartIdAndNonNullCartItemId_expectInt() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        when(cartRepositoryMock.incrementProductQuantity(any(Cart.class), anyLong(), anyInt())).thenReturn(10);
        //originalQuantity = 5;
        //Act
        int finalQuantity = cartService.incrementProductQuantity(1L, 1L, 5);
        //Assert
        assertEquals(10, finalQuantity);
    }

    @Test
    void incrementProductQuantity_nullCartIdAndNonNullCartItemId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        int incrementedQuantity = 5;
        //Act
        assertThrows(NullIdException.class, () -> cartService.incrementProductQuantity(null, 1L, incrementedQuantity));
    }

    @Test
    void incrementProductQuantity_nonNullCartIdAndNullCartItemId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        int incrementedQuantity = 5;
        //Act
        assertThrows(NullIdException.class, () -> cartService.incrementProductQuantity(1L, null, incrementedQuantity));
    }

    @Test
    void incrementProductQuantity_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        int incrementedQuantity = 5;
        //Act
        assertThrows(HibernateException.class, () -> cartService.incrementProductQuantity(1L, 1L, incrementedQuantity));
    }

    @Test
    void decrementProductQuantity_nonNullCartIdAndNonNullCartItemId_expectInt() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        when(cartRepositoryMock.decrementProductQuantity(any(Cart.class), anyLong(), anyInt())).thenReturn(0);
        //originalQuantity = 5;
        //Act
        int finalQuantity = cartService.decrementProductQuantity(1L, 1L, 5);
        //Assert
        assertEquals(0, finalQuantity);
    }

    @Test
    void decrementProductQuantity_nullCartIdAndNonNullCartItemId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        int decrementedQuantity = 5;
        //Act
        assertThrows(NullIdException.class, () -> cartService.decrementProductQuantity(null, 1L, decrementedQuantity));
    }

    @Test
    void decrementProductQuantity_nonNullCartIdAndNullCartItemId_expectNullIdException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(new Cart()));
        int decrementedQuantity = 5;
        //Act
        assertThrows(NullIdException.class, () -> cartService.decrementProductQuantity(1L, null, decrementedQuantity));
    }

    @Test
    void decrementProductQuantity_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        int decrementedQuantity = 5;
        //Act
        assertThrows(HibernateException.class, () -> cartService.decrementProductQuantity(1L, 1L, decrementedQuantity));
    }

    @Test
    void showFinalOrder_nonNullCartIdAndAvailableInStock_expectOrderObject() {
        //Arrange
        Cart cart = new Cart();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        Product product = new Product();
        product.setInStock(20);
        product.setPrice(3);
        CartItem cartItem = new CartItem(10, product, cart);
        cartItems.add(cartItem);
        cart.setItems(cartItems);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cart));
        //Act
        Order order = cartService.showFinalOrder(anyLong());
        //Assert
        assertNotNull(order);
    }

    @Test
    void showFinalOrder_nonNullCartIdNotAvailableInStock_expectOrderObject() {
        //Arrange
        Cart cart = new Cart();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        Product product = new Product();
        product.setInStock(5);
        product.setPrice(3);
        CartItem cartItem = new CartItem(10, product, cart);
        cartItems.add(cartItem);
        cart.setItems(cartItems);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cart));
        //Act
        Order order = cartService.showFinalOrder(anyLong());
        //Assert
        assertNotNull(order);
    }

    @Test
    void showFinalOrder_nullCartId_expectNullIdException() {
        //Arrange
        Long cartId = null;
        //Act
        assertThrows(NullIdException.class, () -> cartService.showFinalOrder(cartId));
    }

    @Test
    void showFinalOrder_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.showFinalOrder(anyLong()));
    }

    @Test
    void submitFinalOrder_nonNullCartId_expectOrderObject() {
        //Arrange
        Cart cart = new Cart();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem());
        cart.setItems(cartItems);
        when(cartRepositoryMock.clearCart(any(Cart.class))).thenReturn(true);
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cart));
        //Act
        Order order = cartService.submitFinalOrder(anyLong());
        //Assert
        assertNotNull(order);
    }

    @Test
    void submitFinalOrder_nullCartId_expectNullIdException() {
        //Arrange
        Long cartId = null;
        //Act
        assertThrows(NullIdException.class, () -> cartService.submitFinalOrder(cartId));
    }

    @Test
    void submitFinalOrder_notExistingCart_expectHibernateException() {
        //Arrange
        when(cartRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        //Act
        assertThrows(HibernateException.class, () -> cartService.submitFinalOrder(anyLong()));
    }
}
