package tn.iit.compte.app.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.iit.compte.app.util.request.RequestValidation;
import tn.iit.compte.app.util.request.ResponseMessage;
import tn.iit.compte.app.util.request.constant.Behavior;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class InvalidArgumentException extends RuntimeException{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handelInvalidArgumentException (MethodArgumentNotValidException exception){
        ResponseMessage errorMessage = new ResponseMessage("Vérifier votre Données.", Behavior.FAIL);
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    errorMessage.getValidations().add(new RequestValidation(fieldError.getField(),  fieldError.getDefaultMessage()));
                });
        return errorMessage;
    }
}
