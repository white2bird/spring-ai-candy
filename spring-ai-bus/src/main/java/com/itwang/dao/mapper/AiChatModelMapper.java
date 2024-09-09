package com.itwang.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itwang.dao.entity.AiChatModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * AI 聊天模型表 Mapper 接口
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
public interface AiChatModelMapper extends BaseMapper<AiChatModel> {


    default AiChatModel selectFirstByStatus(Integer status) {
        return selectOne(new LambdaQueryWrapper<AiChatModel>()
                .eq(AiChatModel::getStatus, status)
                .orderByAsc(AiChatModel::getId)
                .last("LIMIT 1"));

    }

    /**
     * 寻找可用模型
     * @param id
     * @return
     */
    default AiChatModel selectByIdWithUseful(Long id) {
        return selectOne(new LambdaQueryWrapper<AiChatModel>()
                .eq(AiChatModel::getId, id)
                .eq(AiChatModel::getStatus, 1));
    }

}
