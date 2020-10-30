package com.shinsegae_inc.sitims.mi.si.cm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.si.cm.service.SiCm040Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/cm/040/*")
public class SiCm040Controller extends CommonController{
	
	@Autowired
	private transient SiCm040Service siCm040Service;
	
	/**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : MSDS 화면 초기화
    * </pre>
    */
	@RequestMapping("init.do")
	public ModelAndView init(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		return getInitMAV(request,"sitims/views/mi/si/cm/040/si_cm_040_list");
	}
	
	/**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : MSDS dhtmlxtree 정보 불러옴
    * </pre>
    */
	 @RequestMapping("si_cm_040_list_ajax.do")
	 @ResponseBody
	public ModelAndView si_cm_040_list_ajax (HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 CmMap cm = new CmMap();
		 cm.put("i_sSubMsds", request.getParameter("i_sSubMsds"));
		return this.makeJsonResult(mav, "Success", "MSDS정보 가져오기 성공", siCm040Service.getMsdsClassList(cm));

	}
	/**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : MSDS description 정보 불러옴
    * </pre>
    */
	 @RequestMapping("si_cm_040_MsdsDesc_ajax.do")
	public ModelAndView si_cm_040_MsdsDesc_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 
		  List<CmMap> desc = siCm040Service.getMsdsInfo(reqVo);
			  
		 
		 
		 return this.makeJsonResult(mav, "Success", "MSDS정보 가져오기 성공", desc);
		 

	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : MSDS  등록
    * </pre>
    */
	 @RequestMapping("si_cm_040_regMsds_ajax.do")
	public ModelAndView si_cm_040_regMsds_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 	String	sRegMenuId	= "";
	 		//Set Data LoginService 
		 	String userid = SessionUtils.getUserNo();
			reqVo.put("i_sUpdateUserId",userid); 
			reqVo.put("i_sRegUserId",userid);
		
			  
				// MSDS 고유키 채번
				sRegMenuId	= siCm040Service.getMsdsId();
				reqVo.put("i_sMsdsId",sRegMenuId);
				if (((Integer)siCm040Service.getMsdsCountForRegist(reqVo)).intValue() == 0) {
					siCm040Service.msdsReg(reqVo);
				}else {
					 return this.makeJsonResult(mav,"error", "등록할 위치에 같은 이름이 있습니다.", null);
				}
		  
		
		 
		 return this.makeJsonResult(mav, "Success", "등록 성공", null);
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : MSDS  수정
    * </pre>
    */
	 @RequestMapping("si_cm_040_modifyMsds_ajax.do")
	public ModelAndView si_cm_040_modifyMsds_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 	//Set Data LoginService 
		 	String userid = SessionUtils.getUserNo();
			reqVo.put("i_sUpdateUserId",userid); 
		
			 
			siCm040Service.msdsModify(reqVo);
			
	
		 return this.makeJsonResult(mav, "Succ", "저장 성공", null);
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : MSDS  삭제
    * </pre>
    */
	 @RequestMapping("si_cm_040_deletetMsds_ajax.do")
	public ModelAndView si_cm_040_deletetMsds_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 
		 	int msdsCount = siCm040Service.msdsDeleteChk(reqVo);
			if(msdsCount == 0) {			
				
					 
				siCm040Service.msdsDelete(reqVo);
					
			
				return this.makeJsonResult(mav, "Succ", "삭제 성공", null);
			 
			}else {
				return this.makeJsonResult(mav, "Fail", "하위요소가 "+msdsCount+"개 존재합니다.", null);
			}
	}

}
