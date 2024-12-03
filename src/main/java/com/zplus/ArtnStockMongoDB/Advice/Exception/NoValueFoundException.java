package com.zplus.ArtnStockMongoDB.Advice.Exception;

//import org.springframework.stereotype.Component;

import org.springframework.stereotype.Component;

@Component
public class NoValueFoundException extends RuntimeException  {

    private static final long serialversionUID=1l;

    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public NoValueFoundException() {
    }

    public NoValueFoundException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
