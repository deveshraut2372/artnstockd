package com.zplus.ArtnStockMongoDB.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@ControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoValueFoundException.class)
    public ApiError handleNoValueFoundException( NoValueFoundException exception)
    {
        System.out.println("  No Value found Exction  controller is called ");
        ApiError apiError=new ApiError(400," No Value Found",new Date());
//        return new ResponseEntity(apiError,HttpStatus.BAD_REQUEST);
        return apiError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ArithmeticException.class)
    public ApiError handleArithmeticException(ArithmeticException exception)
    {
        ApiError apiError=new ApiError(400," divide by Zero ",new Date());
        return apiError;
    }

}
