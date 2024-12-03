package com.zplus.ArtnStockMongoDB.ExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private Integer errorCode;
    private String errorDesc;
    private Date date;

}
