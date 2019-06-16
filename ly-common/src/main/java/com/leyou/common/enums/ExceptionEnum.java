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
    IAVALID_FILE(500, "无效的文件");
    /**
     * 状态码
     */
    private int code;
    /**
     * 返回信息
     */
    private String msg;

}
