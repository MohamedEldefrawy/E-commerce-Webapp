package com.vodafone.exception.cart;

public class NullCartItemException extends RuntimeException{
    public NullCartItemException(String message){
        super(message);
    }
}
