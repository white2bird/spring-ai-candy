package com.itwang.common.utils;

import com.itwang.ai_model.DeepSeek.DeepSeekChatOptions;
import com.itwang.common.constants.AiConstants;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;

public class AiUtils {

    public static ChatOptions buildChatOptions(AiConstants.AiPlatform aiPlatform, String model, Float temperature, Integer maxTokens){
        switch (aiPlatform){
            case OPENAI: return OpenAiChatOptions.builder().withModel(model).withTemperature(temperature).withMaxTokens(maxTokens).build();
            case DEEP_SEEK:
                return DeepSeekChatOptions.builder().model(DeepSeekChatOptions.MODEL_DEFAULT).maxTokens(maxTokens).build();
            default:
                throw new RuntimeException("不支持的AI平台");
        }
    }
}
