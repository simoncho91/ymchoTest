package com.shinsegae_inc.sitims.mi.si.cm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.mi.si.cm.service.SiCm020Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/cm/020/*")
public class SiCm020Controller extends CommonController{
	@Autowired
	private transient SiCm020Service	sicm020Service;	

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
    @RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getInitMAV(request,"sitims/views/mi/si/cm/020/si_cm_020_list");
	}
    
    /**
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
    *
    * <pre>
    * Commnets : Category 1 그리드 조회
    * </pre>
     */
    @RequestMapping(value="/selectMainList.do")
    public ModelAndView selectMainList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		reqVo.put("i_sClassCd", "S000001");
		reqVo.put("i_sFlagCategory1", "Y");
    	return this.makeJsonDhtmlxResult(mav, "OK", "success",sicm020Service.selectSiCm020List(reqVo));
    }

    /**
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
    *
    * <pre>
    * Commnets : Category 2 그리드 조회
    * </pre>
     */
    @RequestMapping(value="/selectSubList.do")
    public ModelAndView selectSubList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		reqVo.put("i_sFlagCategory1", "N");
    	return this.makeJsonDhtmlxResult(mav, "OK", "success",sicm020Service.selectSiCm020List(reqVo));
    }

    /**
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
    *
    * <pre>
    * Commnets : 제품유형 정보 조회
    * </pre>
     */
    @RequestMapping(value="/selectCategoryInfo.do")
    public ModelAndView selectCategoryInfo(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		reqVo.put("i_sFlagCategory1", "N");
    	return this.makeJsonDhtmlxResult(mav, "OK", "success", sicm020Service.selectCategoryInfo(reqVo));
    }

    /**
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
    *
    * <pre>
    * Commnets : 제품유형 저장
    * </pre>
     */
	@RequestMapping("/si_cm_020_pop_save.do")
	public ModelAndView si_cm_020_pop_save (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("jsonView");

		reqVo.put("i_sUserId",SessionUtils.getUserNo());
		sicm020Service.updateCategoryInfo(reqVo);		
		return this.makeJsonDhtmlxResult(mav, reqVo.getString("status"), reqVo.getString("message"), reqVo);
	}
	/**
	 * 제품유형 excel
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/si_cm_020_list_excel.do")
	public ModelAndView si_cm_020_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	sicm020Service.selectSiCm020ListExcel(reqVo);
		
		// Title
		String[] titleArray = {
			"Level"
			, "Category"
			, "PAO"
			, "SHELF LIFE"
			, "PAO 여부"
			, "사용여부"
		};
		
		// DB Column Name
		String[] columnNmArray = {
			"v_level"
			, "v_class_nm"
			, "n_pao"
			, "n_life"
			, "v_pao_yn"
			, "v_use_yn"
			
		};

		mav.addObject("excelFileName"	, "제품유형");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
    /***
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/si_cm_020_delete.do")
    public ModelAndView si_cm_020_delete(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	CmFunction.setSessionValue(request, reqVo);

		 sicm020Service.deleteSiCm020(reqVo);
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
    }
    
}
