package com.personal.ride.app.response;

import com.personal.core.common.utils.ObjectUtil;
import com.personal.core.common.utils.StringUtils;
import com.personal.ride.app.constants.SYSConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author Chill
 */
@Getter
@Setter
@ToString
@ApiModel(description = "返回信息")
@NoArgsConstructor
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "状态码", required = true)
	private int status;
	
	@ApiModelProperty(value = "承载数据")
	private T data;
	
	@ApiModelProperty(value = "返回消息", required = true)
	private String statusDesc;

	private R(IResultCode resultCode) {
		this(resultCode, null, resultCode.getStatusDesc());
	}

	private R(IResultCode resultCode, String statusDesc) {
		this(resultCode, null, statusDesc);
	}

	private R(IResultCode resultCode, T data) {
		this(resultCode, data, resultCode.getStatusDesc());
	}

	private R(IResultCode resultCode, T data, String msg) {
		this(resultCode.getStatus(), data, msg);
	}

	private R(int status, T data, String statusDesc) {
		this.status = status;
		this.data = data;
		this.statusDesc = statusDesc;
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isSuccess(@Nullable R<?> result) {
		return Optional.ofNullable(result)
			.map(x -> ObjectUtil.nullSafeEquals(ResultCode.SUCCESS.status, x.status))
			.orElse(Boolean.FALSE);
	}

	/**
	 * 判断返回是否为成功
	 *
	 * @param result Result
	 * @return 是否成功
	 */
	public static boolean isNotSuccess(@Nullable R<?> result) {
		return !R.isSuccess(result);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> data(T data) {
		return data(data, SYSConstant.DEFAULT_SUCCESS_MESSAGE);
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param statusDesc  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> data(T data, String statusDesc) {
		return data(SYSConstant.DEFAULT_SUCCESS_CODE, data, statusDesc);
	}

	/**
	 * 返回R
	 *
	 * @param status 状态码
	 * @param data 数据
	 * @param statusDesc  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> data(int status, T data, String statusDesc) {
		return new R<>(status, data, data == null ? SYSConstant.DEFAULT_NULL_MESSAGE : statusDesc);
	}

	/**
	 * 返回R
	 *
	 * @param statusDesc 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success(String statusDesc) {
		return new R<>(ResultCode.SUCCESS, statusDesc);
	}

	public static <T> R<T> success(T t) {
		return new R<>(ResultCode.SUCCESS, t);
	}


	/**
	 * 返回R
	 *
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success() {
		return new R<>(ResultCode.SUCCESS);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success(IResultCode resultCode) {
		return new R<>(resultCode);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param statusDesc        消息
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> success(IResultCode resultCode, String statusDesc) {
		return new R<>(resultCode, statusDesc);
	}

	/**
	 * 返回R
	 *
	 * @param statusDesc 消息
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(String statusDesc) {
		return new R<>(ResultCode.FAILURE, statusDesc);
	}

	/**
	 * 返回R
	 *
	 * @param status 状态码
	 * @param statusDesc  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(int  status, String statusDesc) {
		return new R<>( status, null, statusDesc);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 业务代码
	 * @param <T>        T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(IResultCode resultCode) {
		return new R<>(resultCode);
	}

	/**
	 * 返回R
	 *
	 * @param resultCode 返回信息枚举
	 * @param <T> T 泛型标记
	 * @return R
	 */
	public static <T> R<T> fail(ResultCode resultCode,Object... params) {
		return new R<>(resultCode, StringUtils.getMessage(resultCode.getStatusDesc(),params));
	}

	/**
	 * 返回R
	 *
	 * @param flag 成功状态
	 * @return R
	 */
	public static <T> R<T> status(boolean flag) {
		return flag ? success(SYSConstant.DEFAULT_SUCCESS_MESSAGE) : fail(SYSConstant.DEFAULT_FAILURE_MESSAGE);
	}
}
