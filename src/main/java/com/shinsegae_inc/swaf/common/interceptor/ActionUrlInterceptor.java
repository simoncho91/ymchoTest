package com.shinsegae_inc.swaf.common.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.swaf.common.service.ActionUrlService;

/**
* 
* @author  : thkim
* @since    : 2018. 5. 28.
* @version : 1.0
*
* <pre>
* Comments : 액션 URL 인증 체크 및 액션 로깅 처리
* </pre>
*/
@Component
public class ActionUrlInterceptor implements HandlerInterceptor {

	@Autowired
	private ActionUrlService actionUrlService;
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	public ServletContext getContext(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		return context;
	}

	/** 
	 * url 인증 체크
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		String requestUri = request.getRequestURI();
		
		
		log.debug("## ActionUrlInterceptor access uri=" + requestUri.replaceAll("[\r\n]",""));
		
		if (SessionUtils.isAuthenticated()) {
			
			String forwardUrl = "/error.html";
			
			if (!forwardUrl.equals(requestUri) && !actionUrlService.selectAuthorityUrl(request, requestUri)) {
				
				RequestDispatcher rd = request.getRequestDispatcher(forwardUrl);
				rd.forward(request, response);
				return false;
			}
		}

		
		
		return true;
	}

	/**
	 * 액션 로깅 처리
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 20200303 AccessDecisionUrlServiceImpl.checkAccessGranted 메서드에서 인증여부체크하기 때문에 인증체크부분 제거. hsson 
		//if (SessionUtils.isAuthenticated()) {
			
			actionUrlService.insertActionLog(request);
		//}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
