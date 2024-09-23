package com.itwang.config;

import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import com.itwang.ai_model.DeepSeek.DeepSeekChatOptions;
import com.itwang.common.properties.AiProperties;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 第三方大模型接入
 * @author yiming@micous.com
 * @date 2024/9/3 17:55
 */
@Configuration
@EnableConfigurationProperties(AiProperties.class)
public class CustomChatModelConfiguration {


    /**
     * 接入deepseek大模型
     * @param aiProperties
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "candy.ai.deepseek.enable", havingValue = "true")
    public ChatModel deepSeekChatModel(AiProperties aiProperties){
        AiProperties.DeepSeekProperties deepseek = aiProperties.getDeepseek();
        DeepSeekChatOptions options = DeepSeekChatOptions.builder()
                .model(deepseek.getModel())
                .temperature(deepseek.getTemperature())
                .maxTokens(deepseek.getMaxTokens())
                .topP(deepseek.getTopP())
                .build();
        return new DeepSeekChatModel(deepseek.getApiKey(), options);

    }
}
