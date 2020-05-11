package com.personal.ride.generate;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Api模块代码生成
 * 
 * @author zhangyk
 * @date 2020/3/23 13:53
 */
public class ApiMysqlGenerator {

	/**
	 * RUN THIS
	 */
	public static void main(String[] args) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		// ********* 可修改配置 begin **********
		String dbUrl = "jdbc:mysql://localhost:3306/ride?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&tinyInt1isBit=false";
		String dbUserName = "root";
		String dbPassword = "spkzq521";

		// 表名忽略前缀
		String tablePrefix = "r";
		// 目标表正则
		String tableLike = "r_user";
		// 模块名称
		String modubleName = "user";
		// 是否需要导出
		Boolean isExport = Boolean.FALSE;
		// 是否有BigDecimal类型字段
		Boolean hasBigDecimal = Boolean.FALSE;

		// ********* 可修改配置 end **********

		// 目标基础包名
        // service基础包名
		String servicePackageName = "com.personal.ride.service";
        // controller基础包名
        String controllerPackageName = "com.personal.ride.app";

		// 项目文件夹名称
		String projectPackage = "app";
		// 目标服务
		String whichServe = "service";
		// controller生成路径
		String controllerServer = "app";
		// 前端view生成路径
		String baseLocation = "D:/codeGen/";

		// ******* 以下勿动 *******
		// controller父类
		String superControllerClass = "com.personal.ride.app.base.BaseController";
		// request父类
		String requestSuperClass = "com.personal.core.common.request.BaseRequest";
		// 转换工具类
		String commonUtilsClass = "com.personal.core.common.util.CommonUtils";
		// 统一返回值类
		String responseClass = "com.personal.ride.app.response.R";
		// 导出工具类
		String exportUtilsClass = "com.personal.core.common.util.EasyPoiUtil";

		// String systemUserClass = "com.chuangrong.admin.system.entity.SysUser";

		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		String curPath = AdminMysqlGenerator.class.getClassLoader().getResource("").getFile();
		File curDir = new File(curPath);
		curPath = curDir.getParentFile().getParentFile().getParentFile().getParent();

		curPath = curPath + File.separator + whichServe;
		String projectPath = curPath;

		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("sunpeikai");
		gc.setOpen(false);
		gc.setSwagger2(true);
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl(dbUrl);
		dsc.setDriverName("com.p6spy.engine.spy.P6SpyDriver");
		dsc.setUsername(dbUserName);
		dsc.setPassword(dbPassword);
		dsc.setTypeConvert(new MySqlTypeConvert() {
			@Override
			public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
				// tinyint转换成Integer
				if (fieldType.toLowerCase().contains("tinyint")) {
					return DbColumnType.INTEGER;
				}
				return super.processTypeConvert(globalConfig, fieldType);
			}
		});
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(modubleName);
		pc.setParent(servicePackageName);
		pc.setEntity("entity");
		pc.setService("service");
		pc.setServiceImpl("service.impl");
		pc.setMapper("mapper");
		mpg.setPackageInfo(pc);

		// 自定义参数配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				Map<String, Object> map = new HashMap<>();
				map.put("requestSuperClass", requestSuperClass);
				map.put("CommonUtilsClass", commonUtilsClass);
				map.put("responseClass", responseClass);
				map.put("isExport", isExport);
				map.put("hasBigDecimal", hasBigDecimal);
				map.put("exportUtilsClass", exportUtilsClass);
				// map.put("systemUserClass", systemUserClass);
				map.put("baseControllerClass", superControllerClass);
				this.setMap(map);
			}
		};
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return projectPath + "/src/main/resources/mapper/" + pc.getModuleName() + "/"
						+ tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});

		// 自定义controller配置（位置异常，重新调整）
		focList.add(new FileOutConfig("/templates/app-controller.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
                // 使用controller服务所在地址，替换原来输出地址
				String expand = gc.getOutputDir().replace(whichServe, controllerServer) + "/"
						+ controllerPackageName.replaceAll("\\.", "/") + "/controller" + "/" + modubleName;
				String entityFile = String.format((expand + File.separator + "%s" + ".java"),
						tableInfo.getControllerName());
				return entityFile;
			}
		});

		// 自定义request配置
		focList.add(new FileOutConfig("/templates/request.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String expand = gc.getOutputDir() + "/" + servicePackageName.replaceAll("\\.", "/") + "/" + modubleName
						+ "/request";
				String entityFile = String.format((expand + File.separator + "%s" + "Request.java"),
						tableInfo.getEntityName());
				return entityFile;
			}
		});

		// 自定义vo配置
		focList.add(new FileOutConfig("/templates/vo.java.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String expand = gc.getOutputDir() + "/" + servicePackageName.replaceAll("\\.", "/") + "/" + modubleName
						+ "/vo";
				String entityFile = String.format((expand + File.separator + "%s" + "VO.java"),
						tableInfo.getEntityName());
				return entityFile;
			}
		});

		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
		mpg.setTemplate(new TemplateConfig().setXml(null).setController(null));

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		// strategy.setRestControllerStyle(true);

		strategy.setTablePrefix(tablePrefix);
		strategy.setInclude(tableLike);
		// controller继承父类
		strategy.setSuperControllerClass(superControllerClass);
		// strategy.setSuperEntityColumns("id"); 注释掉可以在表结构生成主键
		// strategy.setControllerMappingHyphenStyle(true);

		mpg.setStrategy(strategy);
		// 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}
