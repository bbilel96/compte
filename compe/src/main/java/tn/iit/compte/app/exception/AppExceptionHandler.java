package tn.iit.compte.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tn.iit.compte.app.exception.custom.AmountLowMuch;
import tn.iit.compte.app.exception.custom.InvalidArgumentException;

import tn.iit.compte.app.exception.custom.ObjectFound;
import tn.iit.compte.app.exception.custom.ObjectNotFound;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;


import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class AppExceptionHandler extends Exception{
    @ExceptionHandler(value = ObjectNotFound.class)
    public ResponseEntity<ResponseMessage> handelUserNotFoundException(ObjectNotFound exception, WebRequest request){
        return getObjectResponseEntity(exception.getLocalizedMessage(), exception.toString(), exception, NOT_FOUND);

    }
    @ExceptionHandler(value = ObjectFound.class)
    public ResponseEntity<ResponseMessage> handelObjectExist(ObjectFound exception, WebRequest request){
        return getObjectResponseEntity(exception.getLocalizedMessage(), exception.toString(), exception, BAD_REQUEST);

    }
    @ExceptionHandler(value = AmountLowMuch.class)
    public ResponseEntity<ResponseMessage> handelAmountLowMuch(AmountLowMuch exception, WebRequest request){
        return getObjectResponseEntity(exception.getLocalizedMessage(), exception.toString(), exception, BAD_REQUEST);

    }

    private ResponseEntity<ResponseMessage> getObjectResponseEntity(String localizedMessage, String s, RuntimeException exception, HttpStatus status) {
        String errorMessageDescription = localizedMessage;
        if (errorMessageDescription == null) errorMessageDescription = s;
        ResponseMessage errorMessage = new ResponseMessage(errorMessageDescription, Behavior.FAIL);
        return new ResponseEntity<ResponseMessage>(
                errorMessage, new HttpHeaders(), status
        );
    }

    @ExceptionHandler(value = InvalidArgumentException.class)
    public ResponseEntity<Object> handelInvalidArgumentException(InvalidArgumentException exception, WebRequest request){
        String errorMessageDescription = exception.getLocalizedMessage();
        if (errorMessageDescription == null) errorMessageDescription = exception.toString();
        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("Message", errorMessageDescription);
        return new ResponseEntity<Object>(
                errorMessage, new HttpHeaders(), BAD_REQUEST
        );

    }
}
