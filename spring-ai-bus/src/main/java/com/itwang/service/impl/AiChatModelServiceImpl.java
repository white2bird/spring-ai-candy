package com.itwang.service.impl;

import com.itwang.converter.AiModelConverter;
import com.itwang.dao.entity.AiChatModel;
import com.itwang.dao.mapper.AiChatModelMapper;
import com.itwang.request.AiChatModelSaveRequest;
import com.itwang.service.IAiChatModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * AI 聊天模型表 服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Service
public class AiChatModelServiceImpl extends ServiceImpl<AiChatModelMapper, AiChatModel> implements IAiChatModelService {

    @Resource
    private AiModelConverter aiModelConverter;

    @Override
    public Long createChatModel(AiChatModelSaveRequest saveRequest) {
        AiChatModel aiChatModel = aiModelConverter.convert(saveRequest);
        this.save(aiChatModel);
        return aiChatModel.getId();
    }
}
