package com.itwang.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.itwang.dao.entity.AiChatRole;
import com.itwang.dao.entity.AiChatRoleType;
import com.itwang.dao.mapper.AiChatRoleTypeMapper;
import com.itwang.response.RoleItemResponse;
import com.itwang.response.RoleTabResponse;
import com.itwang.service.IAiChatRoleService;
import com.itwang.service.IAiChatRoleTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * 角色主分类 服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-22
 */
@Service
public class AiChatRoleTypeServiceImpl extends ServiceImpl<AiChatRoleTypeMapper, AiChatRoleType> implements IAiChatRoleTypeService {

    @Resource
    private IAiChatRoleService aiChatRoleService;

    @Override
    public List<RoleTabResponse> getTabsItems() {
        List<AiChatRole> allRolesWithUsefulTabs = aiChatRoleService.getAllRolesWithUsefulTabs();
        Map<Long, List<AiChatRole>> tabsOriginList = allRolesWithUsefulTabs.stream().collect(Collectors.groupingBy(AiChatRole::getRoleTypeId));
        List<Long> typeIds = tabsOriginList.keySet().stream().distinct().toList();
        if(CollUtil.isEmpty(typeIds)){
            return CollUtil.newArrayList();
        }
        Map<Long, String> idNames = this.listByIds(typeIds).stream().collect(Collectors.toMap(AiChatRoleType::getId, AiChatRoleType::getName));
        // 进行结果映射
        List<RoleTabResponse> list = tabsOriginList.entrySet().stream().map(entry -> {
            Long typeId = entry.getKey();
            List<AiChatRole> roles = entry.getValue();
            return RoleTabResponse.builder()
                    .title(idNames.get(typeId))
                    .items(roles.stream().map(role -> RoleItemResponse.builder()
                            .id(role.getId())
                            .name(role.getName())
                            .build()).toList())
                    .build();
        }).collect(Collectors.toList());
        // 使用stream流随机增加十个假数

        List<RoleItemResponse> items = IntStream.range(1, 10).boxed()
                .map(idx -> RoleItemResponse
                        .builder()
                        .name("假角色" + idx)
                        .id(idx.longValue())
                        .build()).collect(Collectors.toList());
        List<RoleTabResponse> fakeList = IntStream.rangeClosed(1, 6)
                .boxed()
                .map(idx -> {
                    return RoleTabResponse.builder()
                            .title("假TAB" + idx)
                            .items(items)
                            .build();
                }).toList();

        list.addAll(fakeList);
        return list;

    }
}
