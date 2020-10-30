package com.shinsegae_inc.sitims.mi.si.em.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;
import com.shinsegae_inc.sitims.mi.si.em.service.SiEm010Service;


@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/em/010/*")
public class SiEm010Controller extends CommonController{
	@Autowired
	transient SiEm010Service siEm010Service;

	/**
	 * 문안검토 금지어 관리 리스트
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
     * @throws Exception 
	 */ 
	@RequestMapping("init.do")
	public	ModelAndView	init(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		return getInitMAV(request, "sitims/views/mi/si/em/010/si_em_010_list");
		//return mav;
	}
	
	@RequestMapping("si_em_010_get_tab_ajax.do")
	public ModelAndView si_em_010_get_tab_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		//신규 공통코드 테이블 모듈화 작업 안 되있는 상태로 개별로직으로 처리->추후 변경
		ModelAndView mav	=	new ModelAndView();
		List<CmMap> list 	= 	null;
		list				=	siEm010Service.getTabList(reqVo);
		
		if(list != null) {
			return makeJsonResult(mav, "OK", "SUCCESS", list);
		}else {
			return makeJsonResult(mav, "NO", "FAIL", null);
		}
	}
	
	@RequestMapping("si_em_010_get_list_ajax.do")
	public	ModelAndView	si_em_010_get_list_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView	mav			=	new	ModelAndView();
		List<CmMap> 	list		=	null;
		
		list	=	siEm010Service.selectSiEm010List(reqVo);
		
		if(list != null) {
			return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, list);
		}else {
			return this.makeJsonDhtmlxPagingResult(mav, "No", "fail", reqVo, null);
		}
	}
	
	//금지어 상세보기
	@RequestMapping("si_em_010_get_view_ajax.do")
	public	ModelAndView	si_em_010_get_view_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView		mav			=	new	ModelAndView();
		CmMap				rvo			=	new CmMap();

		if(CmFunction.isEmpty(reqVo.getString("i_iSeqNo")) && CmFunction.isEmpty(reqVo.getString("i_sType"))){
			return this.setMessage(mav, reqVo, CmPathInfo.getROOT_FULL_URL() + "supo/pr/supo_pr_proof_forbidden_list.do", reqVo.getString("i_sReturnPars"), "필수인자 값이 없습니다.", "");
		}
		
		rvo = siEm010Service.getForbiddenInfo(reqVo);

		mav.addObject("rvo", rvo);
		mav.addObject("reqVo", reqVo);
		
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, rvo);
	}	

	//금지어 CRUD(추가,삭제,수정 등)
	@RequestMapping("si_em_010_save_ajax.do")
	public ModelAndView si_em_010_save_ajax(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav				=	new ModelAndView();
		siEm010Service.crudSupProofForbiddenWord(reqVo);
		
		return this.makeJsonResult(mav, reqVo.getString("status"), reqVo.getString("message"), null);
	}

	/**
	 * 문안검토금지어관리 엑셀
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/si_em_010_list_excel.do")
	public ModelAndView si_em_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav			=	new	ModelAndView("excelListView");
		List<CmMap> 	list		=	null;
		
		list	=	siEm010Service.selectSiEm010List(reqVo);
		
		String excelFileNm = reqVo.getString("i_sExcelFileNm");
		// Title
		String[] titleArray = {
			  "금지어 카테고리"	
			, "금지어"
			, "유사 금지표현"
			, "대체표현 및 수정가이드"
			, "유사 처분사례"
		};
		
		// DB Column Name
		String[] columnNmArray = {
			  "v_type_nm"	
			, "v_word"
			, "v_similar_word"
			, "v_explain"
			, "v_example"
		};
		
		// Column width
		int[] columnWidthArray = {
				  30
				, 30
				, 50
				, 50
				, 50
		};

		mav.addObject("excelFileName"	, CmFunction.isEmpty(excelFileNm)?"문안검토금지어관리":excelFileNm);
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);
		mav.addObject("columnWidth"		, columnWidthArray);

		return mav;
	}
}
