package com.shinsegae_inc.swaf.common.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.resource.ResourceManage;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.vo.MenuActionVO;
import com.shinsegae_inc.core.vo.PinfProcDetailVO;
import com.shinsegae_inc.core.vo.PinfProcVO;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
* 
* @author  : thkim
* @since    : 2018. 5. 28.
* @version : 1.0
*
* <pre>
* Comments : 개인정보 처리
* </pre>
*/
@Service
public class PinfService extends SwafService {
	
	protected Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="CommonMapper")
	private CommonMapper commonMapper;
	
	@Autowired
	private ActionUrlService actionUrlService;

    @Value("${globals.pinfLog.useYn}")
    private String pinfLogUseYn;

	/**
	*
	* @throws Exception 
	*
	* <pre>
	* Commnets : 개인정보 로그 기록
	* </pre>
	*/
	public void insertPinf(HttpServletRequest request) throws Exception {
		
		log.debug("insertPinf start.");
		MenuActionVO vo = actionUrlService.getMatchMenuCd(request.getRequestURI());
		List<String> pinfNos = null;
		String menuCd = null;
		
		if (vo != null) {
			menuCd = vo.getMenuCd();
			if (menuCd != null && !"".equals(menuCd)) {
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("MENU_CD", menuCd);
				paramMap.put("SYS_ID", systemID);
				
				pinfNos = commonMapper.selectList("PrvInfPrc.selectPinfNos", paramMap);
				
				if (!pinfNos.isEmpty()) {
					String reqBodyStr = ResourceManage.getInstance().getRequestBody();
					insertPinfProc(request, pinfNos, menuCd, reqBodyStr);
				}
			}
		}
		log.debug("insertPinf end.");
	}
	
	private void insertPinfProc(HttpServletRequest request, List<String> pinfNos, String menuCd, String reqBodyStr) throws Exception {
		// 로그 저장 여부 프로퍼티로 관리
		if(!"Y".equals(pinfLogUseYn)) return;
			
		String sysId = systemID;
		String userNo = SessionUtils.getUserNo();
		String regIp = request.getRemoteAddr();
		String svrIp = request.getLocalAddr();
		String acUrl = request.getRequestURL().toString();
		
		String acParam = null;
		if (reqBodyStr != null && reqBodyStr.length() > 2700) {
			acParam = reqBodyStr.substring(0, 2700) + "...";
		}
		else {
			acParam = reqBodyStr;
		}
		
		PinfProcVO vo = new PinfProcVO();
		
		vo.setProcUserNo(userNo);
		vo.setMenuCd(menuCd);
		vo.setSysId(sysId);
		vo.setClntIp(regIp);
		vo.setSvrIp(svrIp);
		vo.setAcUrl(acUrl);
		vo.setAcParm(acParam);
		vo.setRegUserNo(userNo);
		vo.setRegIp(regIp);
		
		// 처리구분코드. 01:조회, 02:변경, 03:삭제, 04:출력
		if( acUrl.contains("select") || acUrl.contains("get") ) {
			vo.setProcClsCd("01");
		}else if ( acUrl.contains("save") || acUrl.contains("insert") || acUrl.contains("update") || acUrl.contains("modify")) {
			vo.setProcClsCd("02");
		}else if ( acUrl.contains("delete")  ) {
			vo.setProcClsCd("03");
		}else {
			vo.setProcClsCd("04");
		}

		commonMapper.insert("PrvInfPrc.insert", vo);
		
		Iterator<String> iter = pinfNos.iterator();
		while (iter.hasNext()) {
			
			String pinfNo = iter.next();
			PinfProcDetailVO dvo = new PinfProcDetailVO();
			
			dvo.setPinfProcNo(vo.getPinfProcNo());
			dvo.setPinfNo(pinfNo);
			dvo.setRegUserNo(userNo);
			dvo.setRegIp(regIp);
			
			commonMapper.insert("PrvInfPrc.insertDetail", dvo);
		}
	}

}
