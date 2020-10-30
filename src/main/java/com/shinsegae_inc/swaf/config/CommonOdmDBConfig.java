package com.shinsegae_inc.swaf.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * ODM DB 설정.
 * 
 * @author 072246
 *
 */

@EnableTransactionManagement
@Lazy
public class CommonOdmDBConfig {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	public static final int THREE_MINS = 60 * 3 * 1000;
    
	@Autowired
	private ApplicationContext applicationContext;

    @Autowired
    private CryptoService cryptoService;
    
    @Value("${globals.dataSourceOdm.driverClassName}")
    private String odmDriverClassName;
    
    @Value("${globals.dataSourceOdm.url}")
    private String odmUrl;
    
    @Value("${globals.dataSourceOdm.userName}")
    private String odmUserName;
    
    @Value("${globals.dataSourceOdm.password}")
    private String odmPassword;
    
    @Value("${globals.dataSource.maximumPoolSize}")
    private String maximumPoolSize;
    
//	@Bean(name = "dataSourceOdm",destroyMethod = "close")
    public DataSource dataSourceOdm() {
        final HikariConfig config = odmHikariConfig();
		return new HikariDataSource(config);
    }
	
	private HikariConfig odmHikariConfig() {
        final HikariConfig config = new HikariConfig();
        config.setPoolName("ODMDB_POOL");
        config.setDriverClassName(odmDriverClassName);
        config.setJdbcUrl(cryptoService.decZeroSalt( odmUrl, true ));
        config.setUsername(cryptoService.decZeroSalt( odmUserName, true ));
        config.setPassword(cryptoService.decZeroSalt( odmPassword, true ));
        config.setMaximumPoolSize(Integer.parseInt(maximumPoolSize));
        config.setAutoCommit(false);
        config.setConnectionTestQuery("SELECT 1 FROM DUAL");	//oracle
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

//	@Bean(name="odmTransactionManager")
    public PlatformTransactionManager transactionManager() {
        final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceOdm());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

//    @Bean(name = "odmSqlSessionFactory")
    public SqlSessionFactory odmSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceOdm());
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mappers/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/oracle/**/*.xml"));				//oracle
        return sqlSessionFactoryBean.getObject();
    }

//    @Bean(name = "odmSqlSessionTemplate")
    public SqlSessionTemplate commonSqlSessionTemplate(SqlSessionFactory odmSqlSessionFactory) throws Exception { 
    	final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(odmSqlSessionFactory);
        return sqlSessionTemplate;
    }

//	@Bean(name = "twoTransactionManager")
	public ChainedTransactionManager transactionManager(@Qualifier("transactionManager") PlatformTransactionManager ds1,
													@Qualifier("odmTransactionManager") PlatformTransactionManager ds2) {
		return new ChainedTransactionManager(ds1, ds2);
	}
}
