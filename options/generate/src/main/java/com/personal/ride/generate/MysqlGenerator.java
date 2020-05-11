package com.personal.ride.generate;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class MysqlGenerator {

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        
        //订单 order
		String whichserve = "ncbx_order"; 
		String modubleName = "order"; 
		String packageName = "com.ncbx.service";
		 
        //系统 system
        //String whichserve = "ncbx_system"; 
        //String modubleName = "system"; 
        //String packageName = "com.ncbx.service";
		 
        
        //内容 content
		
		/*
		 * String whichserve = "ncbx_content"; String modubleName = "content"; String
		 * packageName = "com.ncbx.service";
		 */
		 
        
        //用户 user
        //String whichserve = "ncbx_user"; 
        //String modubleName = "user"; 
        //String packageName = "com.ncbx.service";
		 

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String curPath = MysqlGenerator.class.getClassLoader().getResource("").getFile();
        File curDir = new File(curPath);
        curPath = curDir.getParentFile().getParentFile().getParentFile().getParent();
     
        
        curPath = curPath+File.separator+"ncbx_service"+File.separator+whichserve;
        String projectPath = curPath;
        
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("auto");
        gc.setOpen(false);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://47.105.221.190:33306/nuocheng?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("dbwrite");
        dsc.setPassword("aAR!Q23AUP!ogk$J");
        mpg.setDataSource(dsc);
        
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(modubleName);
        pc.setParent(packageName);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null).setController(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.ncbx.common.vo.BaseEntity");
        strategy.setEntityLombokModel(true);
//        strategy.setSuperControllerClass("com.baomidou.mybatisplus.samples.generator.common.BaseController");
        //TODO:
        //strategy.setInclude("sssys_.*");
        
        /*order*/
		
		/*
		 * strategy.setInclude( "nt_product_category", "nt_attribute",
		 * "nt_attribute_value", "nt_policy_config", "nt_insurance_kind",
		 * "nt_order_beneficiary", "nt_order_holder", "nt_order_insured",
		 * "nt_order_kind", "nt_bg_region_dic" , "nt_bg_common_dic" ,
		 * "nt_bg_condition_dic", "nt_bg_kind_dic", "nt_order" );
		 */
        
        // 诺诚1.3.0 DDL涉及表结构的变动
		/* strategy.setInclude("nt_notification_book"); */
		/* strategy.setInclude("nt_sub_product"); */
		/* strategy.setInclude("nt_product"); */
		/* strategy.setInclude("nt_premium_calc"); */
		/* strategy.setInclude("nt_insurance_kind"); */
		/* strategy.setInclude("nt_attribute"); */
		/* strategy.setInclude("nt_sub_product_relation"); */
		/* strategy.setInclude("nt_order"); */
		/* strategy.setInclude("nt_policy_config_template"); */
		/* strategy.setInclude("nt_order_holder"); */
        /*strategy.setInclude("nt_order_insured");*/
		/* strategy.setInclude("nt_product_doc"); */
		
		// 诺诚1.4.0 DDL涉及表结构的变动
        // 修改的
        // strategy.setInclude("nt_order","nt_order_kind","nt_product","nt_order_beneficiary","nt_order_insured","nt_premium_calc");
		// 新增的
        // strategy.setInclude("nt_order_insured_type","nt_order_insured_kind","nt_order_type","nt_order_expand_info");
        
        // 诺诚2.0 DDL涉及表结构的变动
        //strategy.setInclude("sys_dept");
        //strategy.setInclude("sys_user");
        //strategy.setInclude("sys_user_info");

        /*user*/
        //strategy.setInclude("nt_user_leaving"); 
        //strategy.setInclude("nt_position_change");
		// 诺诚2.0 DDL涉及表结构的变动
        
		
        // 诺诚2.1.0 DDL涉及表结构的变动
        //strategy.setInclude("nt_product_detail");
        //strategy.setInclude("nt_order");
        //strategy.setInclude("nt_order_holder");
        // strategy.setInclude("nt_order_doc");
        
		
        /*content*/
/*        strategy.setInclude("nt_user_business_card","nt_user_main_product","nt_poster","nt_moments_article",
        		"nt_news","nt_forward","nt_forward_read","nt_about","nt_workbench","nt_wechat_token","nt_app_version","nt_param_name");*/

		/* strategy.setInclude("nt_issue_exception"); */
        
        
        // 诺诚2.1.1 DDL涉及表结构的变动
        strategy.setInclude("nt_job_dic");

        //strategy.setSuperEntityColumns("id"); 注释掉可以在表结构生成主键
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");

        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
