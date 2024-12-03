package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recognization {

    private String recognizationName;

    private String recognizationDescription;

    private String recognizationStatus;
}
