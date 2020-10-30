package com.shinsegae_inc.swaf.config;

import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.util.UrlPathHelper;

import com.shinsegae_inc.core.filter.ResourceManagerFilter;
import com.shinsegae_inc.core.support.ParamArgumentResolver;
import com.shinsegae_inc.swaf.common.interceptor.ActionUrlInterceptor;
import com.shinsegae_inc.swaf.common.interceptor.ControllerInterceptor;
import com.shinsegae_inc.swaf.common.interceptor.ControllerLogInterceptor;
import com.shinsegae_inc.swaf.common.interceptor.PinfLogInterceptor;

/**
 * 인터셉터 및  Resolver 등록.
 * 
 * @author 072246
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String[] EXCLUDE_PATHS = {
            "/login/**"
            ,"/br/pr/012/doc/**"
            ,"/br/pw/020/doc/**"
            ,"/showImg.do"
            ,"/allo/mailLink.do"
    };
	
	@Autowired
    private ControllerInterceptor controllerInterceptor;
	
	@Autowired
	private ActionUrlInterceptor actionUrlInterceptor;
	
	@Autowired
	private PinfLogInterceptor pinfLogInterceptor;

	@Autowired
	private ControllerLogInterceptor controllerLogInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Bean
	public ParamArgumentResolver getParamArgumentResolver() {
		return new ParamArgumentResolver();
	}

    @Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
    	FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ResourceManagerFilter());
        registration.addUrlPatterns("*.do");
        registration.setName("resourceManagerFilter");
        registration.setOrder(1);
        return registration;
	}
    
    @Bean
    public View jsonView(){
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setContentType("application/json;charset=UTF-8");
        view.setExtractValueFromSingleKeyModel(true);
        return view;
    }
    
	/**
	 * Resolver 등록
	 */
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		log.debug("WebMvcConfig.addArgumentResolvers");
        resolvers.add(getParamArgumentResolver());
    }
	
	 /**
	  * 인터셉터 추가
	 */
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		HandlerInterceptor pswafInterceptor[] = {
				controllerLogInterceptor,
    			actionUrlInterceptor,
    			pinfLogInterceptor
    	};
    	controllerInterceptor.setInterceptorServiceList(pswafInterceptor);
    	
    	log.debug("WebMvcConfig.addInterceptors");
    	registry.addInterceptor(controllerInterceptor)
                .addPathPatterns("/**/*.do")
                .excludePathPatterns(EXCLUDE_PATHS);
    }

	 /**
	  * jsessionid 삭제
	 */
	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return new ServletContextInitializer() {
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
				SessionCookieConfig sessionCookieConfig=servletContext.getSessionCookieConfig();
				sessionCookieConfig.setHttpOnly(true);
			//	sessionCookieConfig.setSecure(true);
			}
		};
	}
}
