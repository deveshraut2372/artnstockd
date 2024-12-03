package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.*;

import javax.validation.constraints.NegativeOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoName {

    private String geonameId;
    private String countryName;
    private String countryCode;


}



//    private String continent;
//    private String capital;
//              private String languages;
//              private String geonameId;
//              private String south;
//              private String isoAlpha3;
//              private String north;
//              private String fipsCode;
//              private String population;
//              private String east;
//              private String isoNumeric;
//              private String areaInSqKm;
//              private String countryCode;
//              private String west;
//              private String countryName;
//              private String postalCodeFormat;
//              private String continentName;
//    private String currencyCode;