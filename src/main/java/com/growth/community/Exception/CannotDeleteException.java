package com.growth.community.Exception;

public class CannotDeleteException extends RuntimeException{
    public CannotDeleteException(){
        super();
    }

    public CannotDeleteException(String message) {
        super(message);
    }
}
