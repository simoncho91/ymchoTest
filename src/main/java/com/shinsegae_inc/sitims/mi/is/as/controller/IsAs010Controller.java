package com.shinsegae_inc.sitims.mi.is.as.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.is.as.service.IsAs010Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/is/as/010/*")
public class IsAs010Controller extends CommonController{
	@Resource(name="IsAs010Service")
	private transient IsAs010Service	isAs010Service;
	
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Comments : 화면 초기화
    * </pre>
    */
	@RequestMapping("/init.do")
	public ModelAndView is_hm_010_list(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
	   return getInitMAV(request,"sitims/views/mi/is/as/010/is_as_010_list");
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
     * Commnents : CIIMS 제품검색화면 조회
     * </pre>
     */
	@RequestMapping(value="/selectList.do")
    public ModelAndView selectMainList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, isAs010Service.selectIsAs010List(reqVo));
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
	 * Commnets : CIIMS 제품검색화면 리스트 엑셀
	 * </pre>
	 */
	@RequestMapping(value="/is_hm_010_list_excel.do")
	public ModelAndView is_hm_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	isAs010Service.selectIsAs010List(reqVo);
		String flag = reqVo.getString("i_sFlagExcelAll");
		
		// Title
		String[] titleArray = {
			 "구분"
			, "유형"
			, "브랜드"
			, "한글제품명"
			, "영문제품명"
			, "기능성여부"
			, "성분 함량"
			, "배합목적"
			, "내용물 번호"
			, "성분명(영어)"
			, "성분코드"
			, "Cas No"
		};




	// DB Column Name
		String[] columnNmArray = {
			 "v_flag"
			, "v_type_nm"
			, "v_brand_nm"
			, "v_product_nm_ko"
			, "v_product_nm_en"
			, "v_func_yn"
			, "n_con_in_per"
			, "v_func_nm_en"
			, "n_part_no"
			, "v_con_nm_en"
			, "v_con_cd"
			, "v_con_casno"
		};

	// Column Width
	//	int[]	columnWidth	=	{30, 15, 15, 80, 20, 10, 12};

		if(flag.equals("Y")) {
		mav.addObject("excelFileName"	, "CIIMS 제품 검색[전체 페이지]");
		} else
		mav.addObject("excelFileName"	, "CIIMS 제품 검색[현재 페이지]");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
	//	mav.addObject("columnWidth"		, columnWidth);
		mav.addObject("excelData"		, list);

		return mav;
	}
}
