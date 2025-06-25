package com.nian.cloudEyes.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用的分页请求类
 */
@Data
public class PageRequest {

    /**
     * 当前页号
     */
    @ApiModelProperty(value = "当前页号")
    private int current = 1;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    private int pageSize = 10;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    @ApiModelProperty(value = "排序顺序（默认升序）")
    private String sortOrder = "desc";
}
