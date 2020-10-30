package com.shinsegae_inc.swaf.tmpl.tmpl0020.controller;

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
import com.shinsegae_inc.swaf.tmpl.tmpl0020.service.Tmpl0020Service;

@Controller("dhtmlx.Tmpl0020Controller")
public class Tmpl0020Controller extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.Tmpl0020Service") 
	private Tmpl0020Service tmpl0020Service;

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
    @GetMapping("/tmpl/tmpl0020/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/tmpl/tmpl0020");
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
    @PostMapping(value="/tmpl/tmpl0020/selectList.do")
    public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", tmpl0020Service.selectList(reqParamMap.getParamMap()));
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
    @PostMapping(value="/tmpl/tmpl0020/selectMenu.do")
    public ModelAndView selectMenu(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", tmpl0020Service.selectMenu(reqParamMap.getParamMap()));
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
    @PostMapping(value="/tmpl/tmpl0020/selectPinfList.do")
    public ModelAndView selectPinfList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", tmpl0020Service.selectPinfList(reqParamMap.getParamMap()));
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
    @PostMapping(value="/tmpl/tmpl0020/save.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	paramMap.put("URL",StringEscapeUtils.unescapeHtml((String)paramMap.get("URL")));
//    	paramMap.put("URL",StringUtils.stripHtmlCodeToString((String)paramMap.get("URL")));
    	tmpl0020Service.save(paramMap);
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
    @PostMapping(value="/tmpl/tmpl0020/savePinf.do")
    public ModelAndView savePinf(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	
    	String strUseYn = (String) paramMap.get("USE_YN"); 
    	
    	if("1".equals(strUseYn)){
    		tmpl0020Service.insertPinf(paramMap);
    	}else{
    		tmpl0020Service.deletePinf(paramMap);
    	}
    	tmpl0020Service.updatePinfYn(paramMap);
    	
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
    @PostMapping(value="/tmpl/tmpl0020/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	tmpl0020Service.delete(reqParamMap.getParamMap());
		setSuccMsg(mav);
    	return mav;
    }
}
