package com.Project.accountManager.exception;

public class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(String mesasage){
        super(mesasage);
    }
}
