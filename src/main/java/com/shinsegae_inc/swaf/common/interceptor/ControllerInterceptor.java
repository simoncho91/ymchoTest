package com.shinsegae_inc.swaf.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.interceptor.SwafInterceptor;
import com.shinsegae_inc.core.resource.ResourceManage;


/**
 * @Package Name : com.swaf.common.interceptor
 * @FileName     : ControllerInterceptor.java
 * @date         : 2016. 8. 16. 
 * @author       : wjLee7
 * 설명 : 기본 인터셉터: 인터셉트 관리
 * 		- common-servlet.xml 파일에 등록된 인터셉트를 실행
 * 변경이력 : 
 */

@Component
public class ControllerInterceptor extends SwafInterceptor { 

    private transient HandlerInterceptor swafInterceptor[];
    
	protected Logger log = Logger.getLogger(this.getClass());
	
	/**
	* @param pswafInterceptor 
	*
	* <pre>
	* Commnets : interceptor setter method. 
	* </pre>
	*/
	public void setInterceptorServiceList(HandlerInterceptor pswafInterceptor[]){
		this.swafInterceptor = pswafInterceptor;
	}
	
	/**
	 * @see com.shinsegae_inc.core.interceptor.SwafInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * 
	 * Controller 호출 하기전 실행
	 * 
	 * 1. SwafInterceptor 인터셉터 실행
	 * 2. global.properties 인터셉터 실행.
	 * 3. 시작시간 기록: access log 정보를 기록하기 위함.
	 * 4. common-servlet.xml 인터셉터 실행
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(!super.preHandle(request, response, handler)){
			return false;
		}
		long beforeTimeMillis = System.currentTimeMillis();
	    request.setAttribute("beforeTimeMillis", beforeTimeMillis);

		if(swafInterceptor!=null){
			for(int i=0; i<swafInterceptor.length; i++){
				if(!swafInterceptor[i].preHandle(request, response, handler)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @see com.shinsegae_inc.core.interceptor.SwafInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 * 
	 * Controller 종료 후 호출
	 * 
	 * 1. SwafInterceptor 인터셉터 실행
	 * 2. global.properties 인터셉터 실행.
	 * 3. common-servlet.xml 인터셉터 실행
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		super.postHandle(request, response, handler, mav);
		if(swafInterceptor!=null){
			for(int i=0; i<swafInterceptor.length; i++){
				swafInterceptor[i].postHandle(request, response, handler, mav);
			}
		}
	}

	/**
	 * @see com.shinsegae_inc.core.interceptor.SwafInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 * 
	 * 서버단 로직 종료 후 호출
	 * 
	 * 1. SwafInterceptor 인터셉터 실행
	 * 2. global.properties 인터셉터 실행.
	 * 3. common-servlet.xml 인터셉터 실행
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
		if(swafInterceptor!=null){
			for(int i=0; i<swafInterceptor.length; i++){
				swafInterceptor[i].afterCompletion(request, response, handler, ex);
			}
		}
		ResourceManage.getInstance().releaseAll();
	}
	
}
