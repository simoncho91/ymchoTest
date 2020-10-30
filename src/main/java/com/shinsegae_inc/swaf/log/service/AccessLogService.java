package com.shinsegae_inc.swaf.log.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.vo.AccessLogVO;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

@Service("accessLogService")
public class AccessLogService extends SwafService {
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;

    @Value("${globals.security.userSessionNm}")
    private String userSessionNm;

    @Value("${globals.accessLog.useYn}")
    private String accessLogUseYn;
    
	
	@SuppressWarnings("unchecked")
    public void insertLog(HttpServletRequest request, String acRsltCd) throws Exception {
		
		// 로그 저장 여부 프로퍼티로 관리
		if(!"Y".equals(accessLogUseYn)) return;
		
		String remoteIp = request.getRemoteAddr();
		String localIp = request.getLocalAddr();
		String requestUrl = request.getRequestURL().toString();
		String userNo = null;
		String sysId = null;
		if (SessionUtils.isAuthenticated()) {
			Map<String, Object> userMap = (Map<String, Object>) SessionUtils.getAuthenticatedUser();
			userNo = (String) userMap.get("USER_NO");
			sysId = (String) userMap.get("SYS_ID");
		}		
		
		AccessLogVO logVO = new AccessLogVO();
		logVO.setRegUserNo(userNo);
		logVO.setModUserNo(userNo);
		logVO.setAcRsltCd(acRsltCd);
		logVO.setClntIp(remoteIp);
		logVO.setRegIp(remoteIp);
		logVO.setModIp(remoteIp);
		logVO.setSvrIp(localIp);
		logVO.setAcUrl(requestUrl);
		logVO.setSysId(sysId);
		
		commonMapper.insert("AccessLog.insertLog", logVO);
	}
}
