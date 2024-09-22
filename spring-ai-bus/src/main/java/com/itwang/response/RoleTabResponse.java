package com.itwang.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/9/22 20:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleTabResponse {

    private String title;

    private List<RoleItemResponse> items;
}
