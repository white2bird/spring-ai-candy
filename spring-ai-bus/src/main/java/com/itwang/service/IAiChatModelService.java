package com.itwang.service;

import com.itwang.dao.entity.AiChatModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.request.AiChatModelSaveRequest;

/**
 * <p>
 * AI 聊天模型表 服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
public interface IAiChatModelService extends IService<AiChatModel> {

    Long createChatModel(AiChatModelSaveRequest saveRequest);

    AiChatModel getDefaultChatModel();

}
