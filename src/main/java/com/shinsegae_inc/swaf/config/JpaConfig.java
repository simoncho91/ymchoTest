package com.shinsegae_inc.swaf.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
*
* @author  : 053061
* @since   : 2020. 2. 5.
* @version : 1.0
*
* <pre>
* Comments : JPA용 DB 설정.
* </pre>
*/
@Configuration
@EnableTransactionManagement
@Lazy
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "jpaTransactionManager", 
        basePackages = "com.shinsegae_inc"
    )
@DependsOn("commonDBConfig")
public class JpaConfig {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int THREE_MINS = 60 * 3 * 1000;

    @Autowired
    DataSource commonDataSource;
    
    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        return hibernateJpaVendorAdapter;
    }
        
    @Bean(name="entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(commonDataSource);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaDialect(new HibernateJpaDialect());
        factoryBean.setPackagesToScan("com.shinsegae_inc");     

        factoryBean.setJpaPropertyMap(hibernateJpaProperties());
        return factoryBean;
    }

    private Map<String, ?> hibernateJpaProperties() {
      HashMap<String, String> properties = new HashMap<>();

      properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
      properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
      properties.put("hibernate.id.new_generator_mappings", "true");
      properties.put("hibernate.show_sql", "true");
      properties.put("hibernate.format_sql", "true");
      properties.put("hibernate.use_sql_comments", "false");
      properties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
      properties.put("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
      properties.put("hibernate.c3p0.min_size", "2");
      properties.put("hibernate.c3p0.max_size", "5");
      properties.put("hibernate.c3p0.timeout", "300"); // 5mins
      return properties;
    }
    
    @Bean(name="jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager() {
    	EntityManagerFactory	emf = entityManagerFactoryBean().getObject();
    	if (emf == null)
    		return new JpaTransactionManager();
        return new JpaTransactionManager(emf);
    }
}
