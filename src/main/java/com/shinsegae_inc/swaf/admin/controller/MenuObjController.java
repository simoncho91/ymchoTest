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
import com.shinsegae_inc.swaf.admin.service.MenuObjService;

@Controller("dhtmlx.MenuObjController")
public class MenuObjController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.MenuObjService")
	private MenuObjService menuObjService;

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
    @GetMapping("/dhtmlx/admin/menuObj/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/menuObj");
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
    * Commnets : OBJ기본리스트 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menuObj/selectObjList.do")
    public ModelAndView selectObjList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", menuObjService.selectObjList(reqParamMap.getParamMap()));
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
    * Commnets : OBJ추가리스트 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menuObj/selectObjAddList.do")
    public ModelAndView selectObjAddList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		//sParam.put("session", reqDataMap.getSession());
		mav.addObject("result", menuObjService.selectObjAddList(reqParamMap.getParamMap()));
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
    * Commnets : 버튼권한 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menuObj/selectBtnAuthList.do")
    public ModelAndView selectBtnAuthList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", menuObjService.selectBtnAuthList(reqParamMap.getParamMap()));
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
    * Commnets : OBJ추가리스트 저장
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menuObj/saveObj.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	paramMap.put("AC_URL",StringEscapeUtils.unescapeHtml((String)paramMap.get("AC_URL")));
   		menuObjService.saveObj(paramMap);
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
    * Commnets : OBJ 삭제(기본OBJ/추가OBJ)
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/menuObj/deleteObj.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		menuObjService.deleteObj(reqParamMap.getParamMap());
		setSuccMsg(mav);
    	return mav;
    }
    
    
}
