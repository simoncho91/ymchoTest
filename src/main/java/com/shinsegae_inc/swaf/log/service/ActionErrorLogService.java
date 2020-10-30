package com.shinsegae_inc.swaf.log.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.core.resource.ResourceManage;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.DateUtils;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.vo.ActionLogVO;
import com.shinsegae_inc.core.vo.ErrorLogVO;
import com.shinsegae_inc.core.vo.SysTranLogVO;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
* 로그 기록 서비스
* 
* @author  : thkim 
* @since    : 2018.05.15
* @version : 1.0
*
* <pre>
* Comments : 액션로그, 오류로그, 시스템트랜잭션 로그를 기록하는 서비스
* </pre>
*/
@Service("swaf.log.ActionErrorLogService")
public class ActionErrorLogService extends SwafService {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
    
    public String ERROR_LOG_MESSAGE_KEY = "ERRLOG_MSG_KEY";
    public String ERROR_LOG_CODE_KEY    = "ERRLOG_CODE_KEY";
    public String REQUEST_URI           = "REQUEST_URI";
    public String ERROR_TYPE_CODE       = "ERR_TYPE_CD";
    public String ERROR_PACKAGE_NAME    = "ERROR_PACKAGE_NAME";
    
    
	/** 
	* 액션 로그 기록
	*
	* @param request
	* @param inVar
	 * @throws Exception 
	*/ 
	public void insertActionLog(HttpServletRequest request,
								Map<Object, Object> actionInfoMap) throws Exception {
		ActionLogVO actionLogVO = new ActionLogVO();
		Object objBefore = request.getAttribute("beforeTimeMillis");
		long beforeTime = 0;
		if (objBefore != null) {
			beforeTime = (Long) objBefore;
			actionLogVO.setOccuDt(DateUtils.getMilliDate(beforeTime, "yyyyMMdd"));
			actionLogVO.setOccuTm(DateUtils.getMilliDate(beforeTime, "HHmmss"));
		}
		actionLogVO.setClntIp(request.getRemoteAddr());
		actionLogVO.setRegIp(request.getRemoteAddr());
		actionLogVO.setEndDt(DateUtils.getCurrDay());
		actionLogVO.setEndTm(DateUtils.getCurrTime());
		if(SessionUtils.getUserNo()!=null){
			actionLogVO.setRegUserNo(SessionUtils.getUserNo()); // 이부분은 변경해야 함
		}
		actionLogVO.setSvrIp(request.getLocalAddr());
		actionLogVO.setActnTpCd("N");
		
		String requestBodyStr = ResourceManage.getInstance().getRequestBody();
		if(requestBodyStr!=null){
			if(requestBodyStr.getBytes().length >2500){
				requestBodyStr = new String(requestBodyStr.getBytes(),0,2500)+"\n....";
				//requestBodyStr = requestBodyStr.substring(0,2300)+"\n....";
			}
		}else{
			requestBodyStr = "";
		}
		actionLogVO.setAcParm(requestBodyStr);
		actionLogVO.setAcUrl(request.getRequestURI());
		actionLogVO.setSysId(systemID);
		
		actionLogVO.setMenuCd((String) actionInfoMap.get("MENU_CD")); 
		
		commonMapper.insert("ActionLog.insertLog", actionLogVO);
	}
	
	/** 
	* 에러 로그 기록
	*
	* @param request
	* @param inVar
	 * @throws Exception 
	*/ 
	public void insertErrorLog(Map<Object, Object> errorInfoMap) throws Exception {
		ErrorLogVO errorLogVO = new ErrorLogVO();
		String errorMessage = (String) errorInfoMap.get(ERROR_LOG_MESSAGE_KEY);
		if(errorMessage!=null){
			if(errorMessage.getBytes().length >2500){
				errorMessage = new String(errorMessage.getBytes(),0,2500)+"\n....";
				//errorMessage = errorMessage.substring(0,3000)+"...";
			}
		}else{
			errorMessage = "";
		}
		errorLogVO.setErrCnts(errorMessage);
		errorLogVO.setErrorUrl((String) errorInfoMap.get(REQUEST_URI));
		errorLogVO.setErrTpCd((String) errorInfoMap.get(ERROR_TYPE_CODE));
		errorLogVO.setDbDrrCd((String) errorInfoMap.get(ERROR_LOG_CODE_KEY));
		errorLogVO.setPackageNm((String) errorInfoMap.get(ERROR_PACKAGE_NAME));
		errorLogVO.setSysId(systemID);
		if(SessionUtils.getUserNo()!=null){
			errorLogVO.setRegUserNo(SessionUtils.getUserNo());
		}
		commonMapper.insert("ErrorLog.insertLog", errorLogVO);
	}
	
}
