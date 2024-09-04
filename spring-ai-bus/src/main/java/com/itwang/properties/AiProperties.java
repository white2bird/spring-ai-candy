package com.itwang.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yiming@micous.com
 * @date 2024/9/3 18:00
 */
@ConfigurationProperties(prefix = "candy.ai")
@Data
public class AiProperties {

    private DeepSeekProperties deepseek;


    @Data
    public static class DeepSeekProperties{
        private String enable;
        private String apiKey;

        private String model;
        private Float temperature;
        private Integer maxTokens;
        private Float topP;
    }
}
