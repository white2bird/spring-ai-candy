package com.itwang.model.DeepSeek;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

import static com.itwang.model.DeepSeek.DeepSeekChatOptions.MODEL_DEFAULT;

/**
 * @author yiming@micous.com
 * @date 2024/9/3 14:32
 */
public class DeepSeekChatModel implements ChatModel {
    /**
     * 模型api
     */
    private static final String BASE_URL = "https://api.deepseek.com";

    private Logger logger = LoggerFactory.getLogger(DeepSeekChatModel.class);

    private final DeepSeekChatOptions defaultOption;

    private final RetryTemplate retryTemplate;

    private final OpenAiApi openAiApi;

    public DeepSeekChatModel(String apiKey, DeepSeekChatOptions deepSeekChatOptions, RetryTemplate retryTemplate){
        Assert.notEmpty(apiKey, "api key 不能为空");
        Assert.notNull(deepSeekChatOptions, "option 不能为空");
        Assert.notNull(retryTemplate, "retryTemplate 不能为空");
        this.openAiApi = new OpenAiApi(BASE_URL, apiKey);
        this.defaultOption = deepSeekChatOptions;
        this.retryTemplate = retryTemplate;
    }

    public DeepSeekChatModel(String apiKey){
        this(apiKey, DeepSeekChatOptions.builder().model(MODEL_DEFAULT).build(), null);
    }

    public DeepSeekChatModel(String apiKey, DeepSeekChatOptions deepSeekChatOptions){
        this(apiKey, deepSeekChatOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE);

    }


    @Override
    public ChatResponse call(Prompt prompt) {
        OpenAiApi.ChatCompletionRequest chatCompletionRequest = createRequest(prompt, false);
        return this.retryTemplate.execute(ctx->{
            ResponseEntity<OpenAiApi.ChatCompletion> chatCompletionResponseEntity = openAiApi.chatCompletionEntity(chatCompletionRequest);
            OpenAiApi.ChatCompletion body = chatCompletionResponseEntity.getBody();
            if(body == null){
                return new ChatResponse(List.of());
            }
            List<OpenAiApi.ChatCompletion.Choice> choices = body.choices();
            if(CollUtil.isEmpty(choices)){
                return new ChatResponse(List.of());
            }
            List<Generation> generations = choices.stream().map(choice -> {
                Map<String, Object> metaData = Map.of(
                        "role", choice.message().role() != null ? choice.message().role().name() : "",
                        "finishReason", choice.finishReason() != null ? choice.finishReason().name() : "",
                        "id", body.id()
                );
                return buildGeneration(choice, metaData);
            }).toList();
            return new ChatResponse(generations);
        });
    }


    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        OpenAiApi.ChatCompletionRequest chatCompletionRequest = createRequest(prompt, true);
        return this.retryTemplate.execute(ctx->{
            Flux<OpenAiApi.ChatCompletionChunk> chatCompletionChunkFlux = openAiApi.chatCompletionStream(chatCompletionRequest);
            return chatCompletionChunkFlux.map(chunk->{
                String id = chunk.id();
                List<Generation> generations = chunk.choices().stream().map(choice -> {
                            String finish = (choice.finishReason() != null ? choice.finishReason().name() : "");
                            String role = (choice.delta().role() != null ? choice.delta().role().name() : "");
                            if (choice.finishReason() == OpenAiApi.ChatCompletionFinishReason.STOP) {
                                // 兜底处理 DeepSeek 返回 STOP 时，role 为空的情况
                                role = OpenAiApi.ChatCompletionMessage.Role.ASSISTANT.name();
                            }
                            Generation generation = new Generation(choice.delta().content(),
                                    Map.of("id", id, "role", role, "finishReason", finish));
                            if (choice.finishReason() != null) {
                                generation = generation.withGenerationMetadata(
                                        ChatGenerationMetadata.from(choice.finishReason().name(), null));
                            }
                            return generation;
                        }
                ).toList();
                return new ChatResponse(generations);
            });
        });
    }

    private Generation buildGeneration(OpenAiApi.ChatCompletion.Choice choice, Map<String, Object> metadata) {
        var assistantMessage = new AssistantMessage(choice.message().content(), metadata, List.of());
        String finishReason = (choice.finishReason() != null ? choice.finishReason().name() : "");
        var generationMetadata = ChatGenerationMetadata.from(finishReason, null);
        return new Generation(assistantMessage, generationMetadata);
    }


    @Override
    public ChatOptions getDefaultOptions() {
        return DeepSeekChatOptions.fromOptions(defaultOption);
    }

    OpenAiApi.ChatCompletionRequest createRequest(Prompt prompt, boolean stream){
        // 转化为上下文
        List<OpenAiApi.ChatCompletionMessage> chatCompletionMessages = prompt.getInstructions().stream()
                .map(instruction->new OpenAiApi.ChatCompletionMessage(instruction.getContent(), OpenAiApi.ChatCompletionMessage.Role.valueOf(instruction.getMessageType().name()))).toList();

        // 需要指定模型
        OpenAiApi.ChatCompletionRequest chatCompletionRequest = new OpenAiApi.ChatCompletionRequest(chatCompletionMessages,"deepseek-chat",null, stream);

        // 将用户自定义配置填充进去
        if(prompt.getOptions() != null){
            ChatOptions runtimeOption = prompt.getOptions();
            OpenAiChatOptions updateRunTimeOption = ModelOptionsUtils.copyToTarget(runtimeOption, ChatOptions.class, OpenAiChatOptions.class);
            chatCompletionRequest = ModelOptionsUtils.merge(updateRunTimeOption, chatCompletionRequest, OpenAiApi.ChatCompletionRequest.class);
        }
        if(this.defaultOption != null){
            chatCompletionRequest = ModelOptionsUtils.merge(defaultOption, chatCompletionRequest, OpenAiApi.ChatCompletionRequest.class);
        }
        return chatCompletionRequest;
    }


}
