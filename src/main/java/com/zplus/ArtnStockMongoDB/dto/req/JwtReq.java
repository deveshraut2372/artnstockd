package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtReq {

    private String emailAddress;

    private String jwtToken;
}
