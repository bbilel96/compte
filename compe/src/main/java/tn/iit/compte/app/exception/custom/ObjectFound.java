package tn.iit.compte.app.exception.custom;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class ObjectFound extends RuntimeException{
    public ObjectFound(String message) {
        super(message);
    }
}