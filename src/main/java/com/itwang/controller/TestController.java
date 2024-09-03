package com.itwang.controller;

import com.itwang.model.DeepSeek.DeepSeekChatModel;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.beans.Introspector;
import java.util.Map;

@RestController
public class TestController {


    @Resource
    private Map<String, ChatModel> chatModelMap;


    private static final String MODEL = Introspector.decapitalize(DeepSeekChatModel.class.getSimpleName());


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> stream(@RequestParam("model") String model) {

        ChatModel chatModel = chatModelMap.get(model);
        if(chatModel == null){
            chatModel = chatModelMap.get(MODEL);
        }
        return chatModel.stream(new Prompt("讲一个笑话"))
                .doOnNext(System.out::println);

    }

    @GetMapping(value = "/plain")
    public String plain(@RequestParam("model") String model){
        ChatModel chatModel = chatModelMap.get(model);
        if(chatModel == null){
            chatModel = chatModelMap.get(MODEL);
        }
        String res = chatModel.call("讲一个笑话");
        System.out.println(res);
        return res;
    }
}
