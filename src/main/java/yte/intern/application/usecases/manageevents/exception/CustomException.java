package yte.intern.application.usecases.manageevents.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception{
    public CustomException(String message){
        super(message);

    }

}
