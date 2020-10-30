package com.shinsegae_inc.swaf.tmpl.tmpl0010.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.tmpl.tmpl0010.service.Tmpl0010Service;

@Controller("dhtmlx.Tmpl0010Controller")
public class Tmpl0010Controller extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.Tmpl0010Service") 
	private Tmpl0010Service tmpl0010Service;

    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 테플릿0010 화면 초기화
    * </pre>
    */
    @GetMapping("/tmpl/tmpl0010/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/tmpl/tmpl0010");
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
    * Commnets : 목록 조회
    * </pre>
    */
    @PostMapping(value="/tmpl/tmpl0010/selectList.do")
    public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", tmpl0010Service.selectList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
}
