package com.houndsoft.towerbridge.services.exception;

abstract public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
