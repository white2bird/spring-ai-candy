package com.itwang.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.beans.Introspector;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class TestController {


    @Resource
    private Map<String, ChatModel> chatModelMap;


    private static final String MODEL = Introspector.decapitalize(DeepSeekChatModel.class.getSimpleName());


    @GetMapping("/1")
    private String get1() {
        log.info("get1 start");
        String result = createStr();
        log.info("get1 end");
        return result;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some thing";
    }

    @GetMapping("/2")
    private Mono<String> get2() {
        log.info("get2 start");
//         如果直接使用just方法那么它的线程耗时和get1结果一样，等到方法执行结束后才结束
         Mono<String> result = Mono.just(createStr());
        // 注意需要使用流编程模式，惰性求值，实现异步
//        Mono<String> result = Mono.fromSupplier(this::createStr);
        log.info("get2 end");
        return result;
    }

    @SaCheckLogin
    @GetMapping("/getPersonList")
    Flux<String> getPersonList() {
        return Flux.just("jiaduo", "zhailuxu", "guoheng")
                .publishOn(Schedulers.boundedElastic())//1.1 切换到异步线程执行
                .map(e -> {//1.2打印调用线程
                    System.out.println(Thread.currentThread().getName());
                    return e;
                });
    }

    @GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<String> get3() throws InterruptedException {
        log.info("get3 start");
        TimeUnit.SECONDS.sleep(5);
        log.info("get3 sleep");
        Flux<String> get3End = Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> {
                    log.info("Processing item: " + i);
                    return "flux data--" + i;
                })
                .doOnComplete(() -> log.info("get3 end"));
        TimeUnit.SECONDS.sleep(5);
        return get3End;

    }


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> stream(@RequestParam("model") String model, @RequestParam("msg") String msg) {
        ChatModel chatModel = chatModelMap.get(model);
        if(chatModel == null){
            chatModel = chatModelMap.get(MODEL);
        }
        return chatModel.stream(new Prompt(msg));

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
