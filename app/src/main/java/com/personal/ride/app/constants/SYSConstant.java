package com.personal.ride.app.constants;

/**
 * 系统常量
 *
 * @author framework
 */
public interface SYSConstant {

	/**
	 * 编码
	 */
	String UTF_8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 管理员角色
	 */
	public static final String ADMIN_ROLE = "admin";
	/**
	 *  菜单类型 [M:目录; C:菜单; F:按钮功能]
	 */
	public static final String MENU_TYPE_M = "M";

	public static final String MENU_TYPE_C = "C";

	public static final String MENU_TYPE_F = "F";

	/**
	 * 业务状态[000:正常]
	 */
	int DEFAULT_SUCCESS_CODE = 000;

	/**
	 * 业务状态[99:失败]
	 */
	int DEFAULT_FAILURE_CODE = 99;

	/**
	 * 需要登录
	 */
	int NEED_LOGIN_CODE = 999;

	/**
	 * 没有权限
	 */
	int UN_AUTHORIZED_CODE = 909;


	/**
	 * del_flag [1:删除]
	 */
	String DEL_FALG_DELETE = "1";

	String DEL_FLAG_NOT = "0";

	/**
	 * token失效时间3天
	 */
	int tokenExpireTime = 3 * 24 * 60 * 60;

	/**
	 * 默认为空消息
	 */
	String DEFAULT_NULL_MESSAGE = "暂无承载数据";
	/**
	 * 默认成功消息
	 */
	String DEFAULT_SUCCESS_MESSAGE = "成功";
	/**
	 * 默认失败消息
	 */
	String DEFAULT_FAILURE_MESSAGE = "失败";
	/**
	 * 需要登录信息
	 */
	String NEED_LOGIN_MESSAGE = "登录失效,请重新登录";

	/**
	 * 请求没有权限
	 */
	String UN_AUTHORIZED_MESSAGE = "该请求没有权限";
	/**
	 * 验证码错误
	 */
	String VALIDATECODE_ERROR = "验证码错误";

    /**
     * 用户名或密码错误
     */
	String USERNAME_PASSWORD_ERROR = "用户名或密码错误";


	String PARAM_REQUIRED = "缺少关键参数";

	String OBJECT_NOT_EXITS = "主体信息不存在";

	/**
	 * 车位选择防重key过期时间(15分钟)
	 */
	int PARKING_EXPIRE_TIME =15 * 60;

    String GLOBAL_EXCEPTION_MESSAGE = "服务器错误,请联系管理员";

    /**
     * 数据源控制优先级别(值越小优先级越高)，读方法切面
     */
    public static final String[] DATASOURCE_QUERY_PREFIX = {"select","query","count","search","get","find","validate","export"};
    public static final int DATASOURCE_AOP_DS = 1;
    public static final int DATASOURCE_AOP_TRANSACTION = 2;

}
