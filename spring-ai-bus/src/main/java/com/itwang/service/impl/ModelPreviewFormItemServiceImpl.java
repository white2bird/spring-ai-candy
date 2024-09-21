package com.itwang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.converter.ModelPreviewFormItemConverter;
import com.itwang.dao.entity.ModelPreviewFormItem;
import com.itwang.dao.mapper.ModelPreviewFormItemMapper;
import com.itwang.response.FormItemResponse;
import com.itwang.service.IModelPreviewFormItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-20
 */
@Service
public class ModelPreviewFormItemServiceImpl extends ServiceImpl<ModelPreviewFormItemMapper, ModelPreviewFormItem> implements IModelPreviewFormItemService {


    @Resource
    private ModelPreviewFormItemConverter modelPreviewFormItemConverter;

    @Override
    public List<FormItemResponse> getModelPreviewFormItemList(Long modelId) {
        List<ModelPreviewFormItem> modelPreviewFormItems = this.list(new LambdaQueryWrapper<ModelPreviewFormItem>().eq(ModelPreviewFormItem::getRoleId, modelId).orderByAsc(ModelPreviewFormItem::getId));
        return modelPreviewFormItemConverter.convertFormItemResponseList(modelPreviewFormItems);
    }
}
