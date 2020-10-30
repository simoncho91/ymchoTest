package com.shinsegae_inc.swaf.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.dhtmlx.service.CommonService;
import com.shinsegae_inc.swaf.admin.vdi.service.VdiService;

/**
* 
* @author  : wjlee
* @since    : 2019. 1. 8.
* @version : 1.0
*
* <pre>
* Comments : 프로젝트의 UI에 따라 변경해서 사용 필요
* </pre>
*/
@Service("commonService")
public class CommonServiceImpl implements CommonService{
    
	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "dhtmlx.AuthService")
    private AuthService authService;
    
    @Resource(name = "dhtmlx.VdiService")
    private VdiService vdiService;

	public void init() throws Exception{
		log.debug("######## CommonServiceImpl init start ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}	
	
	public Map getCommonInfo(HttpServletRequest request) throws Exception {
        
        Map<String, Object> commonInfo = new HashMap();
        Map<String, Object> param      = new HashMap();
        
        String menuCd = request.getParameter("openMenuCd");
        
        try {
            commonInfo.put("userInfo"    , (HashMap<String, String>) SessionUtils.getAuthenticatedUser());
            
            if ( (menuCd != null) && !(menuCd.equals("")) ) {
                param.put("USER_NO", (String) SessionUtils.getAuthenticatedUserForMap().get("USER_NO"));
                param.put("SYS_ID" , (String) SessionUtils.getAuthenticatedUserForMap().get("SYS_ID"));
                param.put("MENU_CD", (String) request.getParameter("openMenuCd"));
                commonInfo.put("programInfo" , (Map)authService.getProgramInfo(param));
                commonInfo.put("btnInfo"     , (Map)authService.getBtnAuth(param));

                param.put("remoteIp", request.getRemoteAddr());
                param.put("localIp", request.getLocalAddr());
                String vdiYn = vdiService.selectVdiYn(param);
                commonInfo.put("vdiYn"     ,vdiYn );
                if( vdiYn.equals("Y") ) {
                	commonInfo.put("isAccessVdi", vdiService.isAccessVdi(param));
                	commonInfo.put("alertVdi", "swaf/common/alertVdi");
                }
                
            } else {
                commonInfo.put("programInfo" , new HashMap());
                commonInfo.put("btnInfo"     , new HashMap());
                commonInfo.put("vdiYn"     , "N");
            }
            
            
        } catch (Exception e){
            throw e;
        }
        
        return commonInfo;
	}
}
