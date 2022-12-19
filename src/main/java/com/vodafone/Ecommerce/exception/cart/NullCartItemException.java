package com.vodafone.Ecommerce.exception.cart;

public class NullCartItemException extends RuntimeException{
    public NullCartItemException(String message){
        super(message);
    }
}
