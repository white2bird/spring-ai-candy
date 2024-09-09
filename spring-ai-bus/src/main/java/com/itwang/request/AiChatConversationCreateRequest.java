package com.itwang.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author yiming@micous.com
 * @date 2024/9/9 13:38
 */
@Data
@Schema(description = "创建对话请求")
public class AiChatConversationCreateRequest {

    @Schema(description = "聊天角色编号", example = "666")
    private Long roleId;
}
