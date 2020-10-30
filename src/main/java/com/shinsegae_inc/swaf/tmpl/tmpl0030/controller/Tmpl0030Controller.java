package com.shinsegae_inc.swaf.tmpl.tmpl0030.controller;

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
import com.shinsegae_inc.swaf.tmpl.tmpl0030.service.Tmpl0030Service;

@Controller("dhtmlx.Tmpl0030Controller")
public class Tmpl0030Controller extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.Tmpl0030Service") 
	private Tmpl0030Service tmpl0030Service;

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
    @GetMapping("/tmpl/tmpl0030/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/tmpl/tmpl0030");
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
    @PostMapping(value="/tmpl/tmpl0030/selectParentList.do")
    public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", tmpl0030Service.selectParentList(reqParamMap.getParamMap()));
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
    @PostMapping(value="/tmpl/tmpl0030/selectMenuList.do")
    public ModelAndView selectMenuList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", tmpl0030Service.selectMenuList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    
}
