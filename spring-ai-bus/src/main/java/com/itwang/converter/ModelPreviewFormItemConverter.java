package com.itwang.converter;

import com.itwang.dao.entity.ModelPreviewFormItem;
import com.itwang.response.FormItemResponse;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/9/20 18:22
 */
@Mapper(componentModel = "spring")
public interface ModelPreviewFormItemConverter {

    FormItemResponse convertFormItemResponse(ModelPreviewFormItem modelPreviewFormItem);

    List<FormItemResponse> convertFormItemResponseList(List<ModelPreviewFormItem> modelPreviewFormItemList);
}
