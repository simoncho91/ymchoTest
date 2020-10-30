package com.shinsegae_inc.swaf.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * 메인 DB 설정.
 * 
 * @author 072246
 *
 */
@Configuration
@EnableTransactionManagement
@Lazy
public class CommonDBConfig {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	public static final int THREE_MINS = 60 * 3 * 1000;
    
	@Autowired
	private ApplicationContext applicationContext;

    @Autowired
    private CryptoService cryptoService;
    
    @Value("${globals.dataSource.driverClassName}")
    private String driverClassName;
    
    @Value("${globals.dataSource.url}")
    private String url;
    
    @Value("${globals.dataSource.userName}")
    private String userName;
    
    @Value("${globals.dataSource.password}")
    private String password;
    
    @Value("${globals.dataSource.maximumPoolSize}")
    private String maximumPoolSize;

	@Bean(name = "commonDataSource",destroyMethod = "close")
    public DataSource commonDataSource() {
        HikariConfig config = hikariConfig();
		return new HikariDataSource(config);
    }
	
	private HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("DB_POOL");
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(cryptoService.decZeroSalt( url, true ));
        config.setUsername(cryptoService.decZeroSalt( userName, true ));
        config.setPassword(cryptoService.decZeroSalt( password, true ));
        config.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        config.setAutoCommit(false);
        config.setConnectionTestQuery("SELECT 1 FROM DUAL");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("autoReconnect",true);
        config.addDataSourceProperty("tcpKeepAlive", true);
        config.setLeakDetectionThreshold(THREE_MINS);
        // config.addDataSourceProperty("useServerPrepStmts", "true");  // this breaks shit
        config.setMinimumIdle(0);
        config.setIdleTimeout(1000);
        return config;
    }
	
	@Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(commonDataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    @Bean(name = "commonSqlSessionFactory")
    public SqlSessionFactory commonSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(commonDataSource());
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mappers/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/tibero6/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "commonSqlSessionTemplate")
    public SqlSessionTemplate commonSqlSessionTemplate(SqlSessionFactory commonSqlSessionFactory) throws Exception { 
    	final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(commonSqlSessionFactory);
        return sqlSessionTemplate;
    }
    
    @Bean
    public HealthIndicator dbHealthIndicator() {
        DataSourceHealthIndicator indicator = new DataSourceHealthIndicator(commonDataSource());
        indicator.setQuery("SELECT 1 FROM DUAL");
        return indicator;
    }
}
