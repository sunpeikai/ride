package com.personal.ride.app.config.ds;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.personal.ride.app.constants.SYSConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
@MapperScan({"com.personal.ride.app.*.mapper"})
//@AutoConfigureAfter({ DataSourceAutoConfiguration.class})
public class MybatisConfig {
	@Value("${datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Primary
	@Bean(name = "writeDataSource")
	@ConfigurationProperties(prefix = "datasource.write")
	public DataSource writeDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "readDataSource1")
	@ConfigurationProperties(prefix = "datasource.read")
	public DataSource readDataSource1() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	/**
	 *
	 * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
	 *
	 */
	@Bean("dynamicDataSource")
	public DynamicDataSource dynamicDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                               @Qualifier("readDataSource1") DataSource readDataSource1) {

		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(DynamicDataSourceContextHolder.DbType.WRITE.name(), writeDataSource);
		targetDataSources.put(DynamicDataSourceContextHolder.DbType.READ.name(), readDataSource1);

		// 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.dataSourceKeys.add(DynamicDataSourceContextHolder.DbType.WRITE.name());
        DynamicDataSourceContextHolder.dataSourceKeys.add(DynamicDataSourceContextHolder.DbType.READ.name());
        // 将 Slave 数据源的 key 放在集合中，用于轮循
        DynamicDataSourceContextHolder.slaveDataSourceKeys.add(DynamicDataSourceContextHolder.DbType.READ.name());


		DynamicDataSource dataSource = new DynamicDataSource();
		// 该方法是AbstractRoutingDataSource的方法
		dataSource.setTargetDataSources(targetDataSources);
		// 默认的datasource设置为myTestDbDataSource
		dataSource.setDefaultTargetDataSource(writeDataSource);
		return dataSource;
	}

	/**
	 *
	 * 根据数据源创建SqlSessionFactory
	 *
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource,
                                               @Value("${mybatis.mapper-locations}") String mapperLocations,
                                               @Autowired PaginationInterceptor paginationInterceptor) throws Exception {
		
		MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
		
		factoryBean.setDataSource(dynamicDataSource);
		factoryBean.setPlugins(paginationInterceptor);
		// 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
		
		return factoryBean.getObject();
		
		
	}

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
    	PaginationInterceptor page = new PaginationInterceptor();
    	page.setDialectType("mysql");
        return page;
    }

	/**
	 *
	 * 配置事务管理器
	 *
	 */
	@Bean(name ="transactionManager")
	public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}


	@Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Qualifier("transactionManager") DataSourceTransactionManager transactionManager){

		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		String transactionExecution = "execution(* com.ncbx..*Service.*(..))";
        pointcut.setExpression(transactionExecution);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setOrder(SYSConstant.DATASOURCE_AOP_TRANSACTION);

        Properties attributes = new Properties();
        attributes.setProperty("insert*", "PROPAGATION_REQUIRED,-Exception");
		attributes.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        attributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
		attributes.setProperty("modify*", "PROPAGATION_REQUIRED,-Exception");

        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, attributes);
        advisor.setAdvice(txAdvice);

        return advisor;
    }

}