package com.shinsegae_inc.swaf.common.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.common.service.AuthService;

import net.sf.json.JSONArray;

@Controller("dhtmlx.AuthController")
public class AuthController extends SwafDhtmlxController {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.AuthService")
	private AuthService authService;

    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 메뉴 목록 조회
    * </pre>
    */
    @GetMapping(value="/dhtmlx/auth/selectMenuList.do")
    @ResponseBody
    public JSONArray selectMenuList(HttpServletRequest request, ReqParamMap reqParamMap) throws Exception {
		Map<Object, Object> sParam = reqParamMap.getParamMap();
		sParam.put("session", reqParamMap.getSession());

		String menuCd = request.getParameter("menuCd");
		if( menuCd != null ) {
			sParam.put("MENU_CD", menuCd);
		}
    	
		return authService.getMenuList(sParam);
    }
}
