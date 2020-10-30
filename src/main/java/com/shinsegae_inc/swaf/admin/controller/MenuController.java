package com.shinsegae_inc.swaf.admin.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.service.MenuService;

@Controller("dhtmlx.MenuController")
public class MenuController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.MenuService") 
	private MenuService menuService;

    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 메뉴관리 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/menu/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/menu");
    }

    @GetMapping("/dhtmlx/admin/menu6/init.do")
    public ModelAndView init6 (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/menu6");
    }

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
    @PostMapping(value="/dhtmlx/admin/menu/selectList.do")
    public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", menuService.selectList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 메뉴 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menu/selectMenu.do")
    public ModelAndView selectMenu(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", menuService.selectMenu(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 개인정보대상 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menu/selectPinfList.do")
    public ModelAndView selectPinfList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", menuService.selectPinfList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 메뉴저장
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menu/save.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	paramMap.put("URL",StringEscapeUtils.unescapeHtml((String)paramMap.get("URL")));
//    	paramMap.put("URL",StringUtils.stripHtmlCodeToString((String)paramMap.get("URL")));
   		menuService.save(paramMap);
   		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 개인정보 대상 저장
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menu/savePinf.do")
    public ModelAndView savePinf(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	
    	String strUseYn = (String) paramMap.get("USE_YN"); 
    	
    	if("1".equals(strUseYn)){
    		menuService.insertPinf(paramMap);
    	}else{
    		menuService.deletePinf(paramMap);
    	}
    	menuService.updatePinfYn(paramMap);
    	
   		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 메뉴 삭제(개인정보대상 목록 삭제)
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menu/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		menuService.delete(reqParamMap.getParamMap());
		setSuccMsg(mav);
    	return mav;
    }
}
