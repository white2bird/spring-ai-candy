package com.itwang.service;

import com.itwang.dao.entity.AiApiKey;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.ai.chat.model.ChatModel;

/**
 * <p>
 * AI API 密钥表 服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-10
 */
public interface IAiApiKeyService extends IService<AiApiKey> {

    ChatModel getChatModel(Long id);
}
