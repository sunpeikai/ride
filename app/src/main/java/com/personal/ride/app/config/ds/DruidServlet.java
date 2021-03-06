package com.personal.ride.app.config.ds;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/druid/*",
initParams={
        @WebInitParam(name="loginUsername",value="sunpeikai"),// 用户名
        @WebInitParam(name="loginPassword",value="spkzq521"),// 密码
        @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidServlet extends StatViewServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
