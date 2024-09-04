package com.itwang.ai_model.DeepSeek;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/9/3 14:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeepSeekChatOptions implements ChatOptions {

    public static final String MODEL_DEFAULT = "deepseek-chat";

    /**
     * 模型
     */
    private String model;

    /**
     * 温度
     */
    private Float temperature;

    /**
     * 最大 token
     */
    private Integer maxTokens;

    /**
     * topP
     */
    private Float topP;

    /**
     * 暂停语句
     */
    private List<String> stop;

    private @JsonProperty("frequency_penalty") Float frequencyPenalty;

    private @JsonProperty("presence_penalty") Float presencePenalty;


    @Override
    public Float getFrequencyPenalty() {
        return 0f;
    }

    @Override
    public List<String> getStopSequences() {
        return getStop();
    }

    @Override
    public Integer getTopK() {
        return null;
    }


    @Override
    public ChatOptions copy() {
        return DeepSeekChatOptions.fromOptions(this);
    }

    public static DeepSeekChatOptions fromOptions(DeepSeekChatOptions fromOptions) {
        return DeepSeekChatOptions.builder()
                .model(fromOptions.getModel())
                .temperature(fromOptions.getTemperature())
                .maxTokens(fromOptions.getMaxTokens())
                .topP(fromOptions.getTopP())
                .stop(fromOptions.getStop())
                .frequencyPenalty(fromOptions.getFrequencyPenalty())
                .presencePenalty(fromOptions.getPresencePenalty())
                .build();
    }


}
