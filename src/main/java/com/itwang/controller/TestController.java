package com.itwang.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TestController {


    @Resource
    private OpenAiChatModel openAiChatModel;


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream() {
        return openAiChatModel.stream("请讲一个笑话")
                .doOnNext(System.out::println);

    }
}
