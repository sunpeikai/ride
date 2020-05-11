package com.personal.ride.app.exception;

import com.personal.core.common.utils.StringUtils;
import com.personal.ride.app.config.SYSConstant;
import com.personal.ride.app.response.IResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author framework
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected  int status;

    protected  String statusDesc;

    public BusinessException(int status, String statusDesc) {
        super(statusDesc);
        this.status = status;
        this.statusDesc = statusDesc;
    }

    public BusinessException(IResultCode iResultCode){
        super(iResultCode.getStatusDesc());
        this.status = iResultCode.getStatus();
        this.statusDesc = iResultCode.getStatusDesc();
    }

    public BusinessException(IResultCode iResultCode, Object... params){
        super(StringUtils.getMessage(iResultCode.getStatusDesc(),params));
        this.status = iResultCode.getStatus();
        this.statusDesc = StringUtils.getMessage(iResultCode.getStatusDesc(),params);
    }

    public BusinessException(String statusDesc) {
        super(statusDesc);
        this.status = SYSConstant.DEFAULT_FAILURE_CODE;
        this.statusDesc = statusDesc;
    }

    public BusinessException(String statusDesc, Throwable e) {
        super(statusDesc, e);
        this.status = SYSConstant.DEFAULT_FAILURE_CODE;
        this.statusDesc = statusDesc;
    }
}
