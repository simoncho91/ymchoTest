package com.shinsegae_inc.sitims.mi.is.hm.controller;

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
import com.shinsegae_inc.sitims.mi.is.hm.service.IsHm010Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/is/hm/010/*")
public class IsHm010Controller extends CommonController{
	@Resource(name="IsHm010Service")
	private transient IsHm010Service	isHm010Service;
	
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
	   return getInitMAV(request,"sitims/views/mi/is/hm/010/is_hm_010_list");
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
     * Commnents : 파일다운로드 목록 조회
     * </pre>
     */
	@RequestMapping(value="/selectList.do")
    public ModelAndView selectMainList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	String stdt = reqVo.getString("i_sStDate");
    	String endt = reqVo.getString("i_sEnDate");
    	
    	if(!stdt.equals("") && !endt.equals("")) {
    		stdt = stdt.substring(0, 4)+stdt.substring(5,7)+stdt.substring(8)+"0000";
    		endt = endt.substring(0, 4)+endt.substring(5,7)+endt.substring(8)+"2359";
    	} else if(!stdt.equals("") && endt.equals("")) {
    		stdt = stdt.substring(0, 4)+stdt.substring(5,7)+stdt.substring(8)+"0000";
    		endt = "299912312359";
    	} else if(stdt.equals("") && !endt.equals("")) {
    		stdt = "190101010000";
    		endt = endt.substring(0, 4)+endt.substring(5,7)+endt.substring(8)+"2359";
    	}
    	
    	reqVo.put("i_sStDate", stdt);
		reqVo.put("i_sEnDate", endt);
    	
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, isHm010Service.selectIsHm010List(reqVo));
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
	 * Commnets : 파일다운로드 조회 엑셀
	 * </pre>
	 */
	@RequestMapping(value="/is_hm_010_list_excel.do")
	public ModelAndView is_hm_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	isHm010Service.selectIsHm010List(reqVo);
		String flag = reqVo.getString("i_sFlagExcelAll");
		
		// Title
		String[] titleArray = {
			 "파일 구분"
			, "파일명"
			, "첨부파일 아이디"
			, "등록자ID"
			, "등록일시"
			, "다운로드 일시"
		};




	// DB Column Name
		String[] columnNmArray = {
			 "v_attach_class"
			, "v_file_nm"
			, "v_attach_id"
			, "v_reg_user_id"
			, "v_reg_dtm"
			, "v_download_dtm"
		};

	// Column Width
	//	int[]	columnWidth	=	{30, 15, 15, 80, 20, 10, 12};

		if(flag.equals("Y")) {
		mav.addObject("excelFileName"	, "파일다운로드 내역[전체 페이지]");
		} else
		mav.addObject("excelFileName"	, "파일다운로드 내역[현재 페이지]");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
	//	mav.addObject("columnWidth"		, columnWidth);
		mav.addObject("excelData"		, list);

		return mav;
	}
}
