package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CloudinaryResponse {

    private String publicId;
    private String secureUrl;
    private String format;
    private Integer version;
    private Integer bytes;

}
