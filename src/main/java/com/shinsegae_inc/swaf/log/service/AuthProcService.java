package com.shinsegae_inc.swaf.log.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.HttpUtils;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.vo.AuthProcDetailVO;
import com.shinsegae_inc.core.vo.AuthProcVO;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
* 권한변경 로그 기록
* 
* @author  : thkim
* @since    : 2018. 4. 3.
* @version : 1.0
*
* <pre>
* Comments : 권한변경 로그 기록.
* </pre>
*/
@Service("authProcService")
public class AuthProcService extends SwafService {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="CommonMapper")
    private CommonMapper commonMapper;
	
	/** 
	* 권한 변경 로그 기록
	*
	* @param request
	* @param vo
	* @throws Exception
	*/ 
	public void saveLog(HttpServletRequest request, AuthProcVO vo) throws Exception {
		
		int authProcNo = 0;
		int idx = 1;
		String regUserNo = SessionUtils.getUserNo();
		// 1. INIT. - IP 정보
		vo.setClntIp(request.getRemoteAddr());
		vo.setRegIp(request.getRemoteAddr());
		vo.setSvrIp(request.getLocalAddr());
    	vo.setProcUserNo(regUserNo);
		if (SessionUtils.isAuthenticated()) {
			Map<String, Object> userMap = (Map<String, Object>) SessionUtils.getAuthenticatedUser();
			vo.setProcSysId((String) userMap.get("SYS_ID"));
		}		

		vo.setRegUserNo( regUserNo );
		// 2. 마스터 저장
		commonMapper.insert("AuthProc.insert", vo);
		authProcNo = vo.getAuthProcNo();
		log.debug("chgLogNo==> " + authProcNo);
		
		// 3. 상세 저장
		Iterator<AuthProcDetailVO> iter = vo.getChgLogDetails().iterator();
		
		while (iter.hasNext()) {
			AuthProcDetailVO cVo = iter.next(); 
			cVo.setAuthProcNo(authProcNo);
			cVo.setProcSeq(idx++);
			cVo.setSysId(vo.getProcSysId());
			cVo.setRegUserNo(regUserNo);
			cVo.setRegIp(HttpUtils.getClientIp());
			
			commonMapper.insert("AuthProc.insertDetail", cVo);
		}
	}
	
	
	/** 
	* 권한 상세 로그 저장.
	*
	* @param cLogs
	* @param tgtUserNo
	* @param menuCd
	* @param procClsCd
	*/ 
	public void setAuthProcVO(List<AuthProcDetailVO> cLogs, String tgtUserNo, String roleNo, String menuCd, String procClsCd) throws Exception {
		AuthProcDetailVO chgLogDetailVO = new AuthProcDetailVO();
		chgLogDetailVO.setTgtUserNo(tgtUserNo);
		chgLogDetailVO.setMenuCd(menuCd);
		chgLogDetailVO.setProcClsCd(procClsCd);
		chgLogDetailVO.setSysId(systemID);
		chgLogDetailVO.setRoleNo(roleNo);
		cLogs.add(chgLogDetailVO);
		
	}
}
