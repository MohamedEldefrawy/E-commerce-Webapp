package com.vodafone.exception.order;

public class OrderFailedCreationException extends RuntimeException{
    public OrderFailedCreationException(String message) {
        super(message);
    }
}
