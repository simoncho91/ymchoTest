package com.shinsegae_inc.swaf.admin.controller;

import java.util.Map;

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
import com.shinsegae_inc.core.support.annotation.JsonParamAnnotation;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.service.CodeService;

@Controller("dhtmlx.CodeController")
public class CodeController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.CodeService")
	private CodeService codeService;

    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 화면 초기화
    * </pre>
    */
    @GetMapping("/admin/code/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
        return getInitMAV(request, "swaf/admin/code");
    }
    
    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 그룹코드 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/code/selectGrpCodeList.do")
    public ModelAndView selectCodeGrpList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", codeService.selectGrpCodeList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    
    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 그룹코드 저장
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/code/saveGrpCd.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");

    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	
    	
   		codeService.saveGrpCd(paramMap);
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
    * Commnets : 그룹코드 삭제
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/code/deleteGrpCd.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	codeService.deleteGrpCd(reqParamMap.getParamMap());
		setSuccMsg(mav);
    	return mav;
    }

    /**
     *
     * @param reqParamMap
     * @return
     * @throws Exception
     *
     * <pre>
     * Commnets : 코드 목록 조회
     * </pre>
     */
    @PostMapping(value="/dhtmlx/admin/code/selectCodeList.do")
    public ModelAndView selectCodeList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	mav.addObject("result", codeService.selectCodeList(reqParamMap.getParamMap()));
    	setSuccMsg(mav);
    	return mav;
    }
    
    /**
     *
     * @param reqParamMap
     * @return
     * @throws Exception
     *
     * <pre>
     * Commnets : 코드 저장처리를 일괄로.
     * </pre>
     */    
    @PostMapping(value="/dhtmlx/admin/code/saveDetails.do")
    @JsonParamAnnotation
    public ModelAndView saveDetails(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	
    	codeService.saveDetails(reqParamMap);
    	setSuccMsg(mav);
    	return mav;
    }
    
}
