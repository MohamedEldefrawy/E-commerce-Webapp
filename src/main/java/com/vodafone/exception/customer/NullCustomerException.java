package com.vodafone.exception.customer;

public class NullCustomerException extends RuntimeException{
    public NullCustomerException(String message){
        super(message);
    }
}
