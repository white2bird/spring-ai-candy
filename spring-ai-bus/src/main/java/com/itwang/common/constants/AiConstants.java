package com.itwang.common.constants;


import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import lombok.Getter;
import org.springframework.ai.openai.OpenAiChatModel;

import java.beans.Introspector;

public class AiConstants {



    /**
     * 用户类型
     */
    @Getter
    public enum UserType{

        NORMAL(0),
        ADMIN(1);

        private final int code;
        UserType(int code){
            this.code = code;
        }

    }

    @Getter
    public enum AiPlatformEnum{
        OPENAI("OpenAI", "OpenAI", Introspector.decapitalize(OpenAiChatModel.class.getSimpleName())),
        DEEP_SEEK("DeepSeek", "DeepSeek", Introspector.decapitalize(DeepSeekChatModel.class.getSimpleName()));

        private final String platform;

        private final String name;

        private final String className;


        AiPlatformEnum(String platform, String name, String className) {
            this.platform = platform;
            this.name = name;
            this.className = className;
        }

        public static AiPlatformEnum getAiPlatformEnum(String platform) {
            for (AiPlatformEnum value : AiPlatformEnum.values()) {
                if (value.getPlatform().equals(platform)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Invalid AiPlatform code: " + platform);
        }
    }
}
