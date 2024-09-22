package com.itwang.service;

import com.itwang.dao.entity.AiChatRoleType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.response.RoleTabResponse;

import java.util.List;

/**
 * <p>
 * 角色主分类 服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-22
 */
public interface IAiChatRoleTypeService extends IService<AiChatRoleType> {

    List<RoleTabResponse> getTabsItems();
}
