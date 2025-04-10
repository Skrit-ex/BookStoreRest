package com.example.bookstorerest.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super("Resource not found");
    }

}
