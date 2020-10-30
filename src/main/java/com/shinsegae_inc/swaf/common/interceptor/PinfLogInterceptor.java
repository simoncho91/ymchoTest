package com.shinsegae_inc.swaf.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.swaf.common.service.PinfService;

/**
* 
* @author  : thkim
* @since    : 2018. 5. 28.
* @version : 1.0
*
* <pre>
* Comments : 개인정보 로깅을 위한 인터셉터
* </pre>
*/
@Component
public class PinfLogInterceptor implements HandlerInterceptor {

	@Autowired
	private PinfService pinfService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (SessionUtils.isAuthenticated()) {
			// 개인정보 로깅 처리.
			pinfService.insertPinf(request);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// pass
	}

}
