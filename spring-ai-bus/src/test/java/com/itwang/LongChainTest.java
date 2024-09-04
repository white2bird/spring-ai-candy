package com.itwang;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;


@SpringBootTest
public class LongChainTest {

    @Resource
    private Map<String, ChatModel> chatModelMap;

    @Test
    public void testLangChain(){
        System.out.println(chatModelMap);
        System.out.println("-------");
    }
}
