package com.growth.community.Exception;

public class CanNotDeleteException extends RuntimeException{
    public CanNotDeleteException(){
        super();
    }

    public CanNotDeleteException(String s){
        super(s);
    }
}
