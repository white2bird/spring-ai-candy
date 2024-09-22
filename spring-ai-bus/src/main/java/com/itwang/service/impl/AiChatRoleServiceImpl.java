package com.itwang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.dao.entity.AiChatRole;
import com.itwang.dao.mapper.AiChatRoleMapper;
import com.itwang.service.IAiChatRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * AI 聊天角色表 服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-09
 */
@Service
public class AiChatRoleServiceImpl extends ServiceImpl<AiChatRoleMapper, AiChatRole> implements IAiChatRoleService {


    @Override
    public List<AiChatRole> getAllRolesWithUsefulTabs() {
        return this.list(new LambdaQueryWrapper<AiChatRole>().gt(AiChatRole::getRoleTypeId, 0).orderByAsc(AiChatRole::getId));
    }
}
