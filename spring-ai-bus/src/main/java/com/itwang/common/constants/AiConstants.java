package com.itwang.common.constants;

import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import org.springframework.ai.openai.OpenAiChatModel;

import java.beans.Introspector;

public class AiConstants {

    /**
     * 用户类型
     */
    public enum UserType{

        NORMAL(0),
        ADMIN(1);

        private final int code;
        UserType(int code){
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    /**
     * ai平台枚举
     */
    public enum AiPlatform{
        OPENAI(Introspector.decapitalize(OpenAiChatModel.class.getSimpleName())),
        DEEP_SEEK(Introspector.decapitalize(DeepSeekChatModel.class.getSimpleName()));

        private final String code;
        AiPlatform(String code){
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static AiPlatform getByModel(String model){
            for(var item:values()){
                if(item.equals(model)){
                    return item;
                }
            }
            throw new IllegalArgumentException("Invalid AiPlatform code: " + model);
        }
    }
}
