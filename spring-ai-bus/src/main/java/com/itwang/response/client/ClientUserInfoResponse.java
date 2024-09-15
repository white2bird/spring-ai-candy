package com.itwang.response.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yiming@micous.com
 * @date 2024/9/15 10:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientUserInfoResponse {
    private Long id;

    /**
     * 用户名称
     */
    private String nickname;



    /**
     * 头像
     */
    private String avatar;
}
