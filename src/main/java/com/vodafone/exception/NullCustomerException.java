package com.vodafone.exception;

public class NullCustomerException extends RuntimeException{
    public NullCustomerException(String message){
        super(message);
    }
}
