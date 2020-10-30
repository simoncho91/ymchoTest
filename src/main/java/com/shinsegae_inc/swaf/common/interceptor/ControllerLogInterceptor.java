package com.shinsegae_inc.swaf.common.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.core.resource.ResourceManage;
import com.shinsegae_inc.core.support.JsonReqParser;
import com.shinsegae_inc.core.support.ReqParser;

/**
 * 시스템 로깅 처리 인터셉터
 * 
 * @FileName     : ControllerLogInterceptor
 * @date         : 2018.05.15
 * @author       : gnshin
 * 설명 : 시스템 트랜잭션 로깅 처리
 * 변경이력 : 
 */
@Component
public class ControllerLogInterceptor implements HandlerInterceptor { 
    
	protected Logger log = Logger.getLogger(this.getClass());

    @Value("${globals.log.novalue.column}")
    private String noLogValue; // 로깅 제외 컬럼
    
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
		Map<Object, Object> reqBaseMap = ResourceManage.getInstance().getReqBaseInfo();

		try{
			if(reqBaseMap!=null){
				//로그를 남기지 않을 컬럼명 대상 및 url 관리하는 부분 필요
				if(reqBaseMap.get(ReqParamMap.CONTROLLER_TYPE_KEY) != null){
					int controllerType = (Integer) reqBaseMap.get(ReqParamMap.CONTROLLER_TYPE_KEY);
					
					// 1. Action Logging을 위한 파라미터 가공.
					//  (1) json 형태 requestbody
					if(controllerType==ReqParamMap.REQTYPEJSON){
						reqBaseMap.put(ReqParamMap.REQUEST_BODY_STR, getJsonLogBody(request));
					}
					//  (2) 일반웹 형태 requestbody
					else if( controllerType == ReqParamMap.REQTYPESWAF 
							 || controllerType == ReqParamMap.REQTYPEAJAX
							 || controllerType == ReqParamMap.REQTYPEDHTMLX){ 
						reqBaseMap.put(ReqParamMap.REQUEST_BODY_STR, getWebLogBody(request));
					}else {
						reqBaseMap.put(ReqParamMap.REQUEST_BODY_STR, getWebLogBody(request));
					}
					
					// 파라미터 정보 저장.
					ResourceManage.getInstance().setRequestBody((String) reqBaseMap.get(ReqParamMap.REQUEST_BODY_STR));
					//log.debug(" ## Request Body=>" + reqBaseMap);
					
				}
			}
		}catch(Exception e){
			log.error(e);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

	/**
	* 일반 웹형태의 Request Body 정보 추출
	* 
	* @param request
	* @return 
	*
	* <pre>
	* Commnets : 일반 웹형태의 Request Body 정보 추출
	* </pre>
	*/
	private String getWebLogBody(HttpServletRequest request){
		Map<String, Object> reqMap = (Map<String, Object>) ReqParser.convertForHashMap(request);
		return getMapStr(reqMap);
	}
	
	
	/**
	* JSON type의 Request Body 정보 추출
	*
	* @param request
	* @return 
	*
	* <pre>
	* Commnets : JSON type의 Request 정보를 추출 
	* </pre>
	*/
	@SuppressWarnings("unchecked")
	private String getJsonLogBody(HttpServletRequest request) {
		StringBuffer requestBody = new StringBuffer();
		try{
			ReqParamMap requestBodyMap = JsonReqParser.convertJsonForMap(request);
		    Iterator<Object> iterator = requestBodyMap.keySet().iterator();
		    while (iterator.hasNext()) {
		    	String key = (String)iterator.next();
			    Object objValue = requestBodyMap.get(key);
			    if( objValue instanceof JSONArray || objValue instanceof JSONObject) {
			    	List<Object> requestList = requestBodyMap.getJsonMapList(key);
			    	if(requestList!=null&&requestList.size()>0){
			    		//requestBody.append( "[["+key+":("+requestList.size()+ ") Rows\n"); 
			    		requestBody.append( "[[").append(key).append(":(").append(requestList.size()).append(") Rows\n"); 
			    		requestBody.append( getMapStr((Map<String,Object>)requestList.get(0)) ); 
			    		requestBody.append( "]] \n");
			    	}
				}else {
			    	if(noLogValue.indexOf(key)>-1){
			    		//requestBody.append("["+key+"]:######\n");
			    		requestBody.append("[").append(key).append("]:######\n");
				    }else{
				    	//requestBody.append("["+key+"]:").append(objValue+"\n");
				    	requestBody.append("[").append(key).append("]:").append(objValue).append("\n");
				    }
				}
		    }
		}catch(Exception e){
            log.error(e.getMessage().replaceAll("[\r\n]",""));
		}
		return requestBody.toString();
	}
	
	
	/**
	* Map 데이터를 스트링 포멧으로 리턴
	*
	* @param reqMap
	* @return 
	*
	* <pre>
	* Commnets : Map 데이터를 스트링 포멧으로 리턴
	* </pre>
	*/
	private String getMapStr(Map<String, Object> reqMap){
		StringBuffer requestBody = new StringBuffer();
		try{
			Iterator<?> keys =  reqMap.keySet().iterator();
			while( keys.hasNext() ) {
			    String key = (String)keys.next();
			    Object objValue = reqMap.get(key);
			    if(noLogValue.indexOf(key)>-1){
			    	//requestBody.append(" ["+key+"]:######\n");
			    	requestBody.append(" [").append(key).append("]:######\n");
			    }else{
			    	if(objValue instanceof String[]){
			    		String[] objStr = (String[]) objValue;
			    		if(objStr!=null&&objStr.length>0){
			    			//requestBody.append(" ["+key+"("+objStr.length+ ") Rows ]:").append(objStr[0]+"\n");
			    			requestBody.append(" [").append(key).append("(").append(objStr.length).append(") Rows ]:").append(objStr[0]).append("\n");
			    		}else{
			    			//requestBody.append(" ["+key+"]:");
			    			requestBody.append(" [").append(key).append("]:");
			    		}
			    	}else{
			    		//requestBody.append(" ["+key+"]:").append(objValue+"\n");
			    		requestBody.append(" [").append(key).append("]:").append(objValue).append("\n");
			    	}
			    }
			}
		}catch(Exception e){
			log.error(e.getMessage().replaceAll("[\r\n]",""));
		}
		return requestBody.toString();
	}
	
}
