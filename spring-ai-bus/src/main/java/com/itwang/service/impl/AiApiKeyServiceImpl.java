package com.itwang.service.impl;

import com.itwang.common.constants.AiConstants;
import com.itwang.custom.AiModelFactory;
import com.itwang.dao.entity.AiApiKey;
import com.itwang.dao.mapper.AiApiKeyMapper;
import com.itwang.service.IAiApiKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * AI API 密钥表 服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-10
 */
@Service
public class AiApiKeyServiceImpl extends ServiceImpl<AiApiKeyMapper, AiApiKey> implements IAiApiKeyService {


    @Resource
    private AiModelFactory aiModelFactory;

    @Override
    public ChatModel getChatModel(Long id) {
        AiApiKey apiKey = this.getById(id);
        AiConstants.AiPlatformEnum aiPlatformEnum = AiConstants.AiPlatformEnum.getAiPlatformEnum(apiKey.getPlatform());
        return aiModelFactory.getOrCreateChatModel(aiPlatformEnum, apiKey.getApiKey(), apiKey.getUrl());
    }
}
