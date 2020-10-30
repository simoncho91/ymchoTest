package com.shinsegae_inc.swaf.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.shinsegae_inc.core.util.MessageUtils;


/**
 * 공통 Bean 설정.
 * 
 * @author 072246
 *
 */
@Configuration
public class CommonConfig {

	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
	    ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
		
	    rrbms.setBasenames(new String[]{"classpath:message/message-swaf"});
	    rrbms.setDefaultEncoding("UTF-8");
		rrbms.setCacheSeconds(60);
		
		return rrbms;
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor(ReloadableResourceBundleMessageSource messageSource){
		return new MessageSourceAccessor(messageSource);
	}

	@Bean
	public MessageUtils message(){
		MessageUtils messageUtils = new MessageUtils();
		messageUtils.setMessageSourceAccessor(messageSourceAccessor(messageSource()));
		return messageUtils;
	}
	
	@Bean
	public SessionLocaleResolver localeResolver(){
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.KOREA);
		return slr;
	}

    // [20190106 add by 174309] 스프링시큐리티 최초 구동시 런타임 익셉션 발생하는 현상 처리(Could not find default TaskScheduler bean)
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        threadPoolTaskScheduler.setThreadNamePrefix("threadpool-task-scheduler");
        return threadPoolTaskScheduler;
    }

}
