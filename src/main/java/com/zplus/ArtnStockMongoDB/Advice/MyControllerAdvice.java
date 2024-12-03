package com.zplus.ArtnStockMongoDB.Advice;


import com.zplus.ArtnStockMongoDB.Advice.Exception.NoValueFoundException;
import com.zplus.ArtnStockMongoDB.Advice.Exception.NullValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyControllerAdvice  {


    @ExceptionHandler(NoValueFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> NovalueFound(NoValueFoundException noValueFoundException)
    {
        return new ResponseEntity<>(" No Value Found In Database ", HttpStatus.NOT_FOUND);
    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public


    @ExceptionHandler(NullValueException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> NullValue(NullValueException nullValueException)
    {
        return new ResponseEntity<>(" Null Value Excetion ",HttpStatus.OK);
    }


}
