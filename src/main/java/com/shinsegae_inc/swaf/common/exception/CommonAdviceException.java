package com.shinsegae_inc.swaf.common.exception;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.shinsegae_inc.dhtmlx.exception.AbstractDhtmlxAdviceException;
import com.shinsegae_inc.swaf.log.service.ActionErrorLogService;

/**
*
* @author  : 신귀남
* @since   : 2018.05.01
* @version : 1.0
*
* <pre>
* Comments : 공통에러처리 class
* findbug 오탐  : 매개변수를 ModelAndView에 직접 셋팅하지 않음.(request를 매개변수로 받아서 오탐발생하는 듯)
* 				  request매개변수는 사용하지 않지만 core에서 상속받기 때문에 삭제하지 않고 오탐처리함.
* </pre>
*/
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("SPRING_FILE_DISCLOSURE")
@ControllerAdvice({"com.shinsegae_inc.sitims" ,"com.shinsegae_inc.swaf"})
public class CommonAdviceException extends AbstractDhtmlxAdviceException {
	
	@Autowired
    private ActionErrorLogService actionErrorLogService;
	
	/**
	* 오류 로그 기록.
	* 
	* @param errorInfoMap
	* @return 
	*
	* <pre>
	* Commnets : 오류 로그 기록.
	* </pre>
	*/
	@Override
	public HashMap<Object, Object> insertErrorLog(HashMap<Object, Object> errorInfoMap){
		try {
			actionErrorLogService.insertErrorLog(errorInfoMap);
		} catch (Exception e) {
			log.error(e.getMessage().replaceAll("[\r\n]",""));
		}
		return errorInfoMap;
	}
}
