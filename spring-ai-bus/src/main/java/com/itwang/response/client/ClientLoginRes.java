package com.itwang.response.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientLoginRes {

    private Long id;

    private String userName;

    private String token;

    private String avatar;


}
