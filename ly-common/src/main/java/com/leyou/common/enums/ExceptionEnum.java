package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 返回消息的结果类
 *
 * @author llh
 * @date 2019/06/03
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {
    PRICE_CAN_NOT_BE_NULL(1001, "价格不能为空"),
    CATEGORY_NOT_FOUND(404, "商品分类未找到"),
    BRAND_NOT_FOUND(1002, "品牌未找到"),
    BRAND_SAVE_ERROR(500, "品牌保存失败"),
    UPLOAD_IMAGE_ERROR(500, "上传文件失败"),
    FILE_TYPE_ERROR(500, "文件类型错误"),
    INVALID_FILE(500, "无效的文件"),
    SAVE_GOODS_ERROR(500, "新增商品失败"),
    SPEC_GROUP_NOT_FOUND(404, "没有找到项目的分类"),
    CATEGORY_BRAND_NOT_FOUND(404, "当前商品分类下没有找到相应的品牌"),
    SPEC_PARAM_NOT_FOUND(404, "没有找到项目的细类");

    /**
     * 状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;

}
