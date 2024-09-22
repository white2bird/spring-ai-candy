package com.itwang.service;

import com.itwang.dao.entity.AiChatRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * AI 聊天角色表 服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-09
 */
public interface IAiChatRoleService extends IService<AiChatRole> {


    List<AiChatRole> getAllRolesWithUsefulTabs();

}
