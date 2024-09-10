package com.itwang.common.utils;

import com.itwang.ai_model.DeepSeek.DeepSeekChatOptions;
import com.itwang.common.constants.AiConstants;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;

public class AiUtils {

    public static ChatOptions buildChatOptions(AiConstants.AiPlatformEnum aiPlatform, String model, Float temperature, Integer maxTokens){
        switch (aiPlatform){
            case OPENAI: return OpenAiChatOptions.builder().withModel(model).withTemperature(temperature).withMaxTokens(maxTokens).build();
            case DEEP_SEEK:
                return DeepSeekChatOptions.builder().model(model).maxTokens(maxTokens).build();
            default:
                throw new RuntimeException("不支持的AI平台");
        }
    }

    public static Message buildMessage(String type, String content){
        if(MessageType.USER.getValue().equals(type)){
            return new UserMessage(content);
        } else if(MessageType.SYSTEM.getValue().equals(type)){
            return new SystemMessage(content);
        } else if (MessageType.ASSISTANT.getValue().equals(type)){
            return new AssistantMessage(content);
        } else {
            throw new RuntimeException("不支持的消息类型");
        }
    }

}
