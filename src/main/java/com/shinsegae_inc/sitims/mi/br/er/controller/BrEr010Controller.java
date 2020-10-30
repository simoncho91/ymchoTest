package com.shinsegae_inc.sitims.mi.br.er.controller;

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
import com.shinsegae_inc.sitims.mi.br.er.service.BrEr010Service;
import com.shinsegae_inc.sitims.mi.br.er.service.BrEr040Service;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr020Service;

/**
 * 문안검토목록(RA)
 * @author FIC06169
 *
 */
@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/br/er/010/*")
public class BrEr010Controller extends CommonController{
	
	@Autowired
	transient BrEr010Service brEr010Service;
	@Autowired
	transient BrPr020Service brPr020Service;
	@Autowired
	transient BrEr040Service brEr040Service;
	
	/**
	 * 문안등록 리스트
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/init.do")
		public	ModelAndView	init(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
			ModelAndView	mav			=	new	ModelAndView();
			return getInitMAV(request, "sitims/views/mi/br/er/010/br_er_010_list");
		}
	
	/**
	 * 문안등록 리스트(데이터)
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("br_er_010_list_ajax.do")
    public ModelAndView br_er_010_list_ajax(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav 					=	new ModelAndView("jsonView");
		int				recordCnt			=	0;
		List<CmMap> 	list				=	null;
		
		this.setPageUrlAndPars(request, reqVo);
		this.getCmSubCodeList(mav, "BrandList", "BRAND_CODE");  // 브랜드
		
		reqVo.put("i_sStDate", reqVo.getString("i_sStDate").replace("-", ""));
		reqVo.put("i_sEnDate", reqVo.getString("i_sEnDate").replace("-", ""));
		
		if(!"".equals(reqVo.getString("i_sStDate")) && "".equals(reqVo.getString("i_sEnDate"))){
			reqVo.put("i_sEnDate", CmFunction.getTodayString("yyyyMMdd"));
		}
		
		recordCnt	=	brEr010Service.getAcceptCount(reqVo);
		if (recordCnt > 0) {
			this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
			list	=	brEr010Service.getAcceptList(reqVo);
			mav.addObject("list", list);
		}
		mav.addObject("reqVo", reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, list);
    }
    

	/**
     * 문안등록 상세 - 문안검토
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_er_010_view.do")
	public ModelAndView br_er_010_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView	mav	=	new ModelAndView();
    	CmMap 			rvo	=	brPr020Service.getBrPr020Info(reqVo);
    	
    	rvo.put("sFlagReFilteringNo", "Y");
		rvo = brPr020Service.getDraftFilteringWordInfo(rvo);
    	
    	mav.setViewName("sitims/mi/br/er/010/br_er_010_view");
    	
    	mav.addObject("introText", brEr040Service.makeIntroText());
    	mav.addObject("i_sActionFlag", "V");
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rvo", rvo);
    	
    	return mav;
	}
    
    /**
	 * 문안검토등록(RA) 엑셀
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_er_010_list_excel.do")
	public ModelAndView br_er_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav			=	new	ModelAndView("excelListView");
		int				recordCnt	=	0;
		List<CmMap> 	list		=	null;

		reqVo.put("i_sStDate", reqVo.getString("i_sStDate").replace("-", ""));
		reqVo.put("i_sEnDate", reqVo.getString("i_sEnDate").replace("-", ""));
		
		if(!"".equals(reqVo.getString("i_sStDate")) && "".equals(reqVo.getString("i_sEnDate"))){
			reqVo.put("i_sEnDate", CmFunction.getTodayString("yyyyMMdd"));
		}
		
		recordCnt	=	brEr010Service.getAcceptCount(reqVo);
		
		if (recordCnt > 0) {
			this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
			list	=	brEr010Service.getAcceptList(reqVo);
			mav.addObject("list", list);
		}
		
		String excelFileNm		=	reqVo.getString("i_sExcelFileNm");
		
		// Title
		String[] titleArray		=	{
			 "문서번호"
			, "제목"
			, "브랜드명"
			, "제품코드명"
			, "제품연구원"
			, "등록자"
			, "등록일"
			, "담당 RA"
			, "상태"
		};
		
		// DB Column Name
		String[] columnNmArray	=	{
			 "v_ad_req_id"
			, "v_title"
			, "BRAND_NM"
			, "v_product_cd"
			, "VENDOR_ID"
			, "v_reg_user_nm"
			, "v_reg_dtm_ori"
			, "v_ra_nm"
			, "STATUS"
		};

		mav.addObject("excelFileName"	, CmFunction.isEmpty(excelFileNm)?"문안검토목록(RA)":excelFileNm);
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
	
	/**
     * 문안등록 상세 - 원화검토
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_er_010_ori_view.do")
	public ModelAndView br_er_010_ori_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView	mav =	new ModelAndView();
    	mav.setViewName("sitims/mi/br/er/010/br_er_010_ori_view");

    	CmMap 			rvo = 	brPr020Service.getBrPr020Info(reqVo);
    	String		status	=	reqVo.getString("i_sStatus");
    	
    	//원화등록 완료, 반려, 완료시에만 원화의견
    	if(status.equals("AD_REQ_STATUS07") || status.equals("AD_REQ_STATUS08") || status.equals("AD_REQ_STATUS09")) {
    		mav.addObject("msgList",brPr020Service.brPr020OpinionList(reqVo));
    	}
    	
    	mav.addObject("i_sActionFlag", "V");
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rvo", rvo);
    	
    	return mav;
	}
}
