package com.zplus.ArtnStockMongoDB.Advice.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class NullValueException extends RuntimeException{

    private String errorCode;

    private String errorMesage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public NullValueException(String errorCode, String errorMesage) {
        this.errorCode = errorCode;
        this.errorMesage = errorMesage;
    }

    public NullValueException() {
    }
}
