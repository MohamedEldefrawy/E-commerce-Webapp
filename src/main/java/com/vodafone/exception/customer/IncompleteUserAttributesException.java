package com.vodafone.exception.customer;

public class IncompleteUserAttributesException extends RuntimeException{
    public IncompleteUserAttributesException(String message){
        super(message);
    }
}
