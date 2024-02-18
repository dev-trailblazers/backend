package com.growth.community.Exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String entity, String message) {
        super(entity + "은(는)" + message);
    }
}
