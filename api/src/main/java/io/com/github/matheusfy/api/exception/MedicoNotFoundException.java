package io.com.github.matheusfy.api.exception;

public class MedicoNotFoundException extends RuntimeException{

    public MedicoNotFoundException(String msg){
        super(msg);
    }
}
