package com.itwang.custom;

import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import com.itwang.common.constants.AiConstants;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 模型工厂
 * @author yiming@micous.com
 * @date 2024/9/10 15:05
 */
@Component
public class AiModelFactory {

    @Resource
    private Map<String, ChatModel> chatModelMap;
    public ChatModel getOrCreateChatModel(AiConstants.AiPlatformEnum platformEnum, String apiKey, String url){
        String className = platformEnum.getClassName();
        ChatModel chatModel = chatModelMap.get(className);
        if(chatModel != null){
            return chatModel;
        }
        switch (platformEnum){
            case OPENAI:
                return null;
            case DEEP_SEEK:
                return new DeepSeekChatModel(apiKey);
            default:
                throw new IllegalArgumentException("不支持的AI平台");
        }
    }
}
