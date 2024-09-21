package com.itwang.service;

import com.itwang.dao.entity.ModelPreviewFormItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.response.FormItemResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-20
 */
public interface IModelPreviewFormItemService extends IService<ModelPreviewFormItem> {

    List<FormItemResponse> getModelPreviewFormItemList(Long modelId);
}
