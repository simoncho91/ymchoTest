package com.shinsegae_inc.sitims.mi.si.im.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.mi.si.im.service.SiIm010Service;


@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/im/010/*")
public class SiIm010Controller extends CommonController{
	@Autowired
	private transient SiIm010Service siIm010service; 

	/**
	 * 
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
    * <pre>
    * Commnets : 성분DB관리 목록 페이지
    * </pre>
	 */
    @RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/si/im/010/si_im_010_list");
    	
    	return mav; 
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
     * Commnets : 성분DB관리 목록 조회
     * </pre>
     */
    @RequestMapping(value="/selectList.do")
    public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");

		if(reqVo.getStringArray("LIM_LIST[]") != null) {
			String[] str = reqVo.getStringArray("LIM_LIST[]");
			reqVo.put("i_arrLim", str);
		}
		if(reqVo.getStringArray("BAN_LIST[]") != null) {
			String[] str = reqVo.getStringArray("BAN_LIST[]");
			reqVo.put("i_arrBan", str);
		}

		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, siIm010service.selectSiIm010List(reqVo));
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
	 * Commnets : 성분DB관리 목록 조회 엑셀
	 * </pre>
	 */
	@RequestMapping(value="/si_im_010_list_excel.do")
	public ModelAndView si_im_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	siIm010service.selectSiIm010List(reqVo);

		// Title
		String[] titleArray = {
			 "성분코드"
			, "버전"
			, "CAS No"
			, "성분명(한글)"
			, "성분명(영문)"
			, "성분명(중국어)"
			, "기능명(한글)"
			, "기능명(영문)"
			, "기능명(중국어)"
			, "사용금지"
			, "배합제한"
			, "사용금지상세_국내"
			, "사용금지상세_중국"
			, "사용금지상세_아세안"
			, "사용금지상세_유럽"
			, "사용금지상세_미국"
			, "배합제한상세_국내"
			, "배합제한상세_중국"
			, "배합제한상세_아세안"
			, "배합제한상세_유럽"
			, "배합제한상세_미국"
			, "알러젠여부"
			, "배합한도"
			, "구분"
			, "색소여부"
			, "함량표시 여부"
			, "표시성분 여부"
		};




		// DB Column Name
		String[] columnNmArray = {
			 "v_con_cd"
			, "n_ver_seq"
			, "v_cas_no_list"
			, "v_con_nm_ko"
			, "v_con_nm_en"
			, "v_con_nm_cn"
			, "v_func_nm_ko_list"
			, "v_func_nm_en_list"
			, "v_func_nm_cn_list"
			, "v_b_gl_nm"
			, "v_l_gl_nm"
			, "v_b_ko_comment"
			, "v_b_cn_comment"
			, "v_b_ae_comment"
			, "v_b_eu_comment"
			, "v_b_us_comment"
			, "v_l_ko_comment"
			, "v_l_ch_comment"
			, "v_l_ae_comment"
			, "v_l_eu_comment"
			, "v_l_us_comment"
			, "v_allergen_yn"
			, "n_max_allow_wt"
			, "v_flag_nm"
			, "v_ci_yn"
			, "v_mateview_yn"
			, "v_display_yn"
		};

		// Column Width
		int[] columnWidth = {
			 10       
			, 5       
			, 15      
			, 20      
			, 20      
			, 20      
			, 20      
			, 20      
			, 20      
			, 20      
			, 20      
			, 35      
			, 35      
			, 35      
			, 35      
			, 35      
			, 35      
			, 35      
			, 35      
			, 35      
			, 35      
			, 10       
			, 15      
			, 15      
			, 5       
			, 10       
			, 10       
		};

		mav.addObject("excelFileName"	, "성분DB");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("columnWidth"		, columnWidth);
		mav.addObject("excelData"		, list);

		return mav;
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
     * Commnets : 성분DB관리 상세화면 조회
     * </pre>
     */
    @RequestMapping(value="/si_im_010_view.do")
	public ModelAndView si_im_010_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = siIm010service.selectSiim010Info(reqVo);
    	mav.addObject("reqVo",reqVo);
    	mav.addObject("rVo",rVo);
		mav.addObject("funcComboList",siIm010service.selectSiim010FuncComboList(reqVo));
    	if("Y".equals(rVo.getString("v_confirm_yn"))){
    		mav.setViewName("sitims/mi/si/im/010/si_im_010_view");
    	}else {
    		mav.setViewName("sitims/mi/si/im/010/si_im_010_reg");
    	}    	
    	mav.addObject("casNoList",siIm010service.selectSiim010CasNo(reqVo));
    	mav.addObject("banVo",siIm010service.selectSiim010BanDesc(reqVo));        	
    	mav.addObject("funcList",siIm010service.selectSiim010Func(reqVo));
    	return mav;
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
     * Commnets : 성분DB관리 등록화면 
     * </pre>
     */
    @RequestMapping(value="/si_im_010_reg.do")
	public ModelAndView si_im_010_reg (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("sitims/mi/si/im/010/si_im_010_reg");
    	mav.addObject("reqVo",reqVo);
    	mav.addObject("funcComboList",siIm010service.selectSiim010FuncComboList(reqVo));

    	if(CmFunction.isNotEmpty(reqVo.getString("i_sConCd"))) {
        	mav.addObject("rVo",siIm010service.selectSiim010RegInfo(reqVo));
    	}
    	if(CmFunction.isNotEmpty(reqVo.getString("i_sReqConId"))) {
        	mav.addObject("conInfo",siIm010service.selectConInfo(reqVo));
    	}
    	
    	return mav;
	}
    /**
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 
     * <pre>
     * Commnets : 성분DB관리 등록 팝업 페이지
     * </pre>
     */
    @RequestMapping(value="/si_im_010_reg_pop.do")
	public ModelAndView si_im_010_reg_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getInitMAV(request,"sitims/views/mi/si/im/010/si_im_010_reg_pop");
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
     * Commnets : 성분DB관리 등록 팝업리스트 조회
     * </pre>
     */
    @RequestMapping(value="/selectConList.do")
    public ModelAndView selectConList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success",reqVo, siIm010service.selectConList(reqVo));
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
     * Commnets : 성분코드 중복체크
     * </pre>
     */
    @RequestMapping(value="/chkDupliConCd.do")
    public ModelAndView chkDupliConCd(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	return this.makeJsonDhtmlxResult(mav, "OK", "success", siIm010service.getSiIm010ListCount(reqVo));
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
     * Commnets : 성분DB관리 저장
     * </pre>
     */
    @RequestMapping(value="/si_im_010_save.do")
    public ModelAndView si_im_010_save(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");

		reqVo.put("i_sUserId",SessionUtils.getUserNo());
		siIm010service.updateConCdInfo(reqVo);
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
    }
    
    /***
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/si_im_010_del.do")
    public ModelAndView si_im_010_del(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	CmFunction.setSessionValue(request, reqVo);

		siIm010service.deleteSiIm010(reqVo);
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
    }
}
