package com.shinsegae_inc.swaf.admin.vdi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.vdi.service.VdiService;

@Controller("dhtmlx.VdiController")
public class VdiController extends SwafDhtmlxController {

	@Resource(name = "dhtmlx.VdiService") 
	private VdiService vdiService;

    @GetMapping("/dhtmlx/admin/vdi/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/vdi");
    }

	/**
	 * VDI 목록 조회.
	 * 
	 * @param param
	 *            - dc_searchParamCode : 콩통코드 목록 조회 파라메터 예시 )
	 *            {"dc_searchParamCode":{"GRP_CD":"SY001"}}
	 * @return JSON
	 * @throws Exception
	 */
	@PostMapping("/dhtmlx/admin/vdi/getVdiList.do")
	public ModelAndView getVdiList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<String, Object> resultMap 	= new HashMap<String, Object>();
    	Map<Object, Object> paramMap 	= reqParamMap.getParamMap();
		mav.addObject("result", vdiService.selectVdiList(paramMap));
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
    * Commnets : 등록
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/vdi/insert.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<String, Object> resultMap 	= new HashMap<String, Object>();
    	Map<Object, Object> paramMap 	= reqParamMap.getParamMap();
		vdiService.insert(paramMap, request);
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
    * Commnets : 수정
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/vdi/update.do")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<String, Object> resultMap 	= new HashMap<String, Object>();
    	Map<Object, Object> paramMap 	= reqParamMap.getParamMap();
		vdiService.update(paramMap, request);
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
    * Commnets : 삭제
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/vdi/delete.do")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<String, Object> resultMap 	= new HashMap<String, Object>();
    	Map<Object, Object> paramMap 	= reqParamMap.getParamMap();
    	vdiService.delete(paramMap);
   		setSuccMsg(mav);
    	return mav;

    }
	
}
