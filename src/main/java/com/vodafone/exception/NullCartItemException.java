package com.vodafone.exception;

public class NullCartItemException extends RuntimeException{
    public NullCartItemException(String message){
        super(message);
    }
}
