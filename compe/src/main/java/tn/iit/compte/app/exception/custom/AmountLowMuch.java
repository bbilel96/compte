package tn.iit.compte.app.exception.custom;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class AmountLowMuch  extends RuntimeException{
    public AmountLowMuch(String message) {
        super(message);
    }
}
