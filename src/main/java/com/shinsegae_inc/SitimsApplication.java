package com.shinsegae_inc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { 
         DataSourceAutoConfiguration.class
        ,HibernateJpaAutoConfiguration.class
        ,DataSourceTransactionManagerAutoConfiguration.class
})
@Configuration
public class SitimsApplication  extends SpringBootServletInitializer{

    private static final String PROPERTIES = "spring.config.location="
                                             +"classpath:/application.yml"
                                            +",classpath:/sitims.yml";
    
    public static final String STARTTIME = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

	public static void main(String[] args) {
	    new SpringApplicationBuilder(SitimsApplication.class)
        .properties(PROPERTIES)
        .run(args);

        /*
	    ApplicationContext ctx = SpringApplication.run(SnapApplication.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for(String beanName : beanNames) {
        	System.out.println(beanName);
        }
        */
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		
        return builder.sources(SitimsApplication.class).properties(PROPERTIES);
    }

}
