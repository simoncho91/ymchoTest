package com.shinsegae_inc.sitims.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CommonPopService;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/cm/pop/*")
public class CommonPopController extends CommonController{
	@Autowired
	private transient CommonPopService commonPopService;
    @RequestMapping(value="/cm_user_list_pop.do")
	public ModelAndView cm_user_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/common/cm_user_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	@RequestMapping(value="/getUserList.do")
	public ModelAndView getUserList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		List<CmMap> result = commonPopService.getUserList(reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, result); 
	}

    @RequestMapping(value="/cm_odm_user_list_pop.do")
	public ModelAndView cm_odm_user_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/common/cm_odm_user_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	@RequestMapping(value="/getOdmUserList.do")
	public ModelAndView getOdmUserList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		List<CmMap> result = commonPopService.getOdmUserList(reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, result); 
	}

    @RequestMapping(value="/cm_doc_ban_desc_pop.do")
	public ModelAndView cm_doc_function_history_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/common/cm_doc_ban_desc_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	@RequestMapping(value="/getConCdBanDescList.do")
	public ModelAndView getConCdBanDescList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		List<CmMap> result = commonPopService.getConCdBanDescList(reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, result); 
	}
    
    
}
