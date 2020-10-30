package com.shinsegae_inc.swaf.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.shinsegae_inc.core.exception.MessageException;
import com.shinsegae_inc.core.resource.ResourceManage;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.vo.MenuActionVO;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;
import com.shinsegae_inc.swaf.log.service.ActionErrorLogService;

/**
* 
* @author  : thkim
* @since    : 2018. 5. 28.
* @version : 1.0
*
* <pre>
* Comments : Action URL 인증 서비스
* </pre>
*/
@Service
public class ActionUrlService extends SwafService {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	@Autowired
    private ActionErrorLogService actionErrorLogService;

	private List<MenuActionVO> actionUrls = null;

	/**
	* 권한 체크. 
	*
	* @param requestUri : 접속 url
	* @return
	* @throws Exception 
	*
	* <pre>
	* Commnets : 접속 URL에 대한 권한 체크, Reques Base정보에 menuCd를 저장 한다.
	* </pre>
	*/
	public boolean selectAuthorityUrl(HttpServletRequest request, String requestUri) throws Exception {
		
		MenuActionVO actionVO = null;
		String menuCd = null;
		
		// 1. Action Url 정보 로딩.
		if (actionUrls == null) {
			loadActionUrls();
			if (actionUrls == null) return false;
		}
		
		
		// 2. Get MenuCd
		actionVO = getMatchMenuCd(requestUri);
		if (actionVO == null) return false;
		menuCd = actionVO.getMenuCd();
		
		
		// 3. MenuCD가 권한이 있는지 체크.
		Map<Object, Object> reqBaseMap = ResourceManage.getInstance().getReqBaseInfo();
		if (reqBaseMap == null) {
			log.error("Not Exist request information!");
			return false;
		}
		
		// 3.0 MenuCd 저장
		reqBaseMap.put("MENU_CD", menuCd);
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("MENU_CD", menuCd);
		paramMap.put("SYS_ID", systemID);
		paramMap.put("USER_NO", SessionUtils.getUserNo());
		
		int authCnt = commonMapper.selectOne("ActionUrlAuth.authMenuForOther", paramMap);
		
		if (authCnt > 0) {
			
			return true;
		}
		else {
			return false;
		}
		
	}
	
	/**
	*
	* @throws Exception 
	*
	* <pre>
	* Commnets : Action Url 정보를 메모리에 로딩.
	* </pre>
	*/
	public void loadActionUrls() throws Exception {
		actionUrls = commonMapper.selectList("ActionUrlAuth.selectActionUrls", systemID);
	}
	
	
	/**
	*
	* @param requestUri : String
	* @return MenuActionVO
	*
	* <pre>
	* Commnets :  requestUri 매칭된 menuCd를 리턴한다.
	* </pre>
	*/
	public MenuActionVO getMatchMenuCd(String requestUri) {
		
		try {
			// 1. Action Url 정보 로딩.
			if (actionUrls == null) {
				loadActionUrls();
			}
					
			for (MenuActionVO vo : actionUrls) {
				
				if (checkUrlPattern(vo.getAcUrl(), requestUri)) {
					return vo;
				}
			}
		}
		catch (Exception e) {
			log.error(e.getMessage().replaceAll("[\r\n]",""));
		}
		
		return null;
	}
	
	/**
	*
	* @param pattern
	* @param inputStr
	* @return 
	*
	* <pre>
	* Commnets : URL 패턴 체크
	* </pre>
	*/
	private boolean checkUrlPattern(String pattern, String inputStr) {
		
		if (pattern == null || "".equals(pattern)) return false;
	
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher.match(pattern, inputStr);		
	}

	/**
	*
	* @param request 
	*
	* <pre>
	* Commnets : Action Log 기록.
	* </pre>
	*/
	public void insertActionLog(HttpServletRequest request) {
		try {
			
		
			MenuActionVO actionVO = null;
			String menuCd = null;
			String requestUri = request.getRequestURI();
			
			// 1. Action Url 정보 로딩.
			if (actionUrls == null) {
				loadActionUrls();
				if (actionUrls == null) throw new MessageException("No load action Url");
			}
			
			
			// 2. Get MenuCd
			actionVO = getMatchMenuCd(requestUri);
			if (actionVO == null) throw new MessageException("No Matched action Url. request URL=>" + requestUri);
			menuCd = actionVO.getMenuCd();
			
			
			// 3. MenuCD가 권한이 있는지 체크.
			Map<Object, Object> reqBaseMap = ResourceManage.getInstance().getReqBaseInfo();
			if (reqBaseMap == null)  throw new MessageException("Not Exist request information!");
			
			
			if ("Y".equals(actionVO.getLogYn())) {
				// 3.0 MenuCd 저장
				reqBaseMap.put("MENU_CD", menuCd);
				actionErrorLogService.insertActionLog(request, reqBaseMap);
			}

		}
		catch (Exception e) {
			log.error(e.getMessage().replaceAll("[\r\n]",""));
		}
	}


}
