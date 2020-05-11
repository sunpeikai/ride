package com.personal.core.common.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version BaseRequest, v0.1 2020/5/11 11:44
 */
public class BaseRequest implements Serializable {

    @ApiModelProperty(value = "当前页码")
    private int currPage = 1;

    @ApiModelProperty(value = "每页记录数")
    private int pageSize = 10;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
