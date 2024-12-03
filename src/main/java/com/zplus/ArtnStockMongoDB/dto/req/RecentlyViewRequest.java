package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Data   // this annotation is equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
public class RecentlyViewRequest {
    private String userId;
    private String artId;


}
