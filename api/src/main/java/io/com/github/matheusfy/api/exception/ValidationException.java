package io.com.github.matheusfy.api.exception;

public class ValidationException extends RuntimeException{

    public ValidationException(String msg){
        super(msg);
    }
}
