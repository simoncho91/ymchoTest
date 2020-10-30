package com.shinsegae_inc.sitims.mi.br.er.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.br.er.service.BrEr040Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/br/er/040/*")
public class BrEr040Controller extends CommonController{
	@Autowired
	transient BrEr040Service brEr040Service;

	//자체검토 금지어 필터링 화면
	@RequestMapping("/init.do")
	public	ModelAndView	init(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView	mav			=	getInitMAV(request, "sitims/views/mi/br/er/040/br_er_040_view");
		mav.addObject("introText", brEr040Service.makeIntroText());
		
		return mav;
	}
	
	//자체검토 금지어필터링 필터링기능 ajax
    @RequestMapping("br_er_040_filtering_ajax.do")
	public ModelAndView br_er_040_filtering_ajax(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView	mav			=	new ModelAndView();
		CmMap			resultVo	=	new CmMap();
		
		resultVo 				=	brEr040Service.getRequestTextFiltering(reqVo);
		Optional opResultVo 	=	Optional.ofNullable(resultVo);
		
		if(opResultVo.isPresent()) {
			return this.makeJsonResult(mav, "OK", "success", resultVo);
			//return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, resultVo);
		}else {
			return this.makeJsonResult(mav, "error", "작업중 오류가 발생했습니다.", opResultVo.orElse("null"));
			//return this.makeJsonDhtmlxPagingResult(mav, "error", "작업중 오류가 발생했습니다.", reqVo, null);
		}
	}
}
