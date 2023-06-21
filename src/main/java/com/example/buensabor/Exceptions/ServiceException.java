package com.example.buensabor.Exceptions;

public class ServiceException extends Exception {
    public ServiceException(String errorMessage) {
        super(errorMessage);
    }
}
