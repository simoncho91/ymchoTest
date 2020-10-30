package com.shinsegae_inc.sitims.mi.br.pr.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.poi.OdmReportPoi;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr010Service;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr012Service;
import com.shinsegae_inc.sitims.mi.br.pw.service.BrPw020Service;
import com.shinsegae_inc.sitims.mi.si.cm.service.SiCm020Service;

@Controller
@RequestMapping(value="/br/pr/012/*")
public class BrPr012Controller extends CommonController{
	@Resource(name = "sitims.BrPr010Service")
	private transient BrPr010Service brpr010service;
	@Resource(name = "sitims.SiCm020Service")
	private transient SiCm020Service sicm020Service;	
	@Resource(name = "sitims.BrPr012Service")
	private transient BrPr012Service brpr012service;
	@Resource(name = "sitims.BrPw020Service")
	private transient BrPw020Service brPw020Service; 
	/**
	 * 
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
    * <pre>
    * Commnets : 제품 목록 페이지
    * 
    * </pre>
	 */
    @RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getInitMAV(request,"sitims/views/mi/br/pr/012/br_pr_012_list");
	}
    
    /**
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
    * <pre>
    * Commnets : 완료제품 정보 조회
    * </pre>
     */
    @RequestMapping(value="/br_pr_012_t1_view.do")
	public ModelAndView br_pr_012_t1 (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = brpr010service.getBrPr010Info(reqVo);
    	
    	mav.setViewName("sitims/mi/br/pr/012/br_pr_012_t1_view");
    	
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("BRAND_LIST", commonService.getCmSubCodeList("BRAND_CODE"));
    	mav.addObject("ODM_PRODUCT_TYPE_LIST", commonService.getCmSubCodeList("ODM_PRODUCT_TYPE"));
    	mav.addObject("CNTRFORM_LIST", commonService.getCmSubCodeList("ODM_CONT1"));
    	mav.addObject("CNTRMATR_LIST", commonService.getCmSubCodeList("ODM_CONT2"));
    	mav.addObject("COUNTRY_LIST", commonService.getCmSubCodeList("ODM_EXPORT",1,"Y"));
    	mav.addObject("ODM_FUNC_LIST", commonService.getCmSubCodeList("MRC03"));
    	
    	mav.addObject("originDivList", commonService.getCmSubCodeList("ODM_ORIGINDIV"));
    	mav.addObject("prodList", brpr010service.getBrPr010ProdList(reqVo));
    	
    	CmMap tempVo = new CmMap();
		tempVo.put("i_sClassCd", "S000001");
		tempVo.put("i_sFlagCategory1", "Y");
    	mav.addObject("cateList", sicm020Service.selectSiCm020List(tempVo));
    	mav.addObject("rVo", rVo);
    	
    	return mav;
	}
    /**
     * 완료제품 문서확인
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_012_t2_view.do")
	public ModelAndView br_pr_012_t2 (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);    	
    	mav.setViewName("sitims/mi/br/pr/012/br_pr_012_t2_view");

    	if(rVo != null) {
			reqVo.put("i_sProductCd", rVo.getString("v_product_cd"));
			reqVo.put("i_iVerSeq", rVo.getString("n_vsn"));
			
			List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
			mav.addObject("partList", partList);
			
			int partListSize = partList == null ? 0 : partList.size();
			String companyCd = null;
			if(partListSize > 0) {
				reqVo.putDefault("i_sPartNo", partList.get(0).getString("n_part_no"));
			}
    	}
    	mav.addObject("cmDocList", commonService.getCmSubCodeList("FILE_REQ",1,"CM"));
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rVo", rVo);
    	return mav;
	}
    
    /**
     * 제품 msds 문서 Excel
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@SuppressWarnings("resource")
	@RequestMapping(value="/doc/co_doc_msds_excel.do")
	public ModelAndView pif_doc_msds_excel (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View");
		OdmReportPoi odmReportPoi = new OdmReportPoi();
		HSSFWorkbook	wb	=	new HSSFWorkbook();
		brpr012service.coDocMsdsExcel(reqVo,mav);
		CmMap productInfo = (CmMap)reqVo.get("productInfo");
		//ip주소
		String ipAddr			= request.getRemoteAddr();
		this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/co_doc_msds_excel.do",productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_MSDS",ipAddr,"EXCEL");
		mav.addObject("HSSFWorkbook" ,odmReportPoi.excelMsds(reqVo, wb));
		mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
		mav.addObject("excelFileName", productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_MSDS");

		mav.addObject("reqVo", reqVo);
		
		return mav;
	}
	/**
	 * 제품문서 Fomula Excel
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value="/doc/co_doc_formula_excel.do")
	public ModelAndView co_doc_formula_excel (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View");
		
		try {
			OdmReportPoi odmReportPoi = new OdmReportPoi();
			HSSFWorkbook	wb	=	new HSSFWorkbook();
			brpr012service.coDocFomulaExcel(reqVo,mav);
			String productCd = reqVo.getString("i_sProductCd");
			CmMap productInfo = (CmMap) reqVo.get("productInfo");
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			this.pdfExcelDownloadLog(productCd,"/doc/co_doc_formula_excel.do",productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_singleFormula",ipAddr,"EXCEL");
			mav.addObject("HSSFWorkbook" ,odmReportPoi.excelSingleFormula(reqVo, wb));
			mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
			mav.addObject("excelFileName", productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_singleFormula");
		}catch(IOException e) {
	    	this.errorLogger(e);
	    }
		mav.addObject("reqVo", reqVo);
		return mav;
	}
//	/**
//	 * 파일path reqVo저장
//	 * @param attachId
//	 * @param setNm
//	 * @param reqVo
//	 * @throws Exception
//	 */
//	private void setAttachFilePath(String attachId, String setNm, CmMap reqVo) throws Exception {
//		// TODO Auto-generated method stub
//		CmMap tempVo = new CmMap();
//		tempVo.put("i_sAttachId", attachId);
//		CmMap attachInfo = commonService.getAttachInfo(tempVo);
//		String filePath = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/"+attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
//		reqVo.put(setNm, filePath);
//	}
	/**
	 * 제품문서 fomula pdf
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doc/co_doc_formula_pdf_print.do")
	public ModelAndView co_doc_formula_pdf_print (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView	mav	=	new ModelAndView("sitims/doc/co_doc_formula_pdf_print");
		
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		String productCd ="";
		if(productInfo!=null) {
			productCd= productInfo.getString("v_product_cd");
			reqVo.put("i_sMatrcd", productCd);
			reqVo.put("i_sPartTab", "Y");
			reqVo.put("i_iVsn", reqVo.getString("i_iVerSeq"));
			reqVo.put("i_sCompanyCd", productInfo.getString("v_vendor_id"));
			
			CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
			if(companyInfo != null) {
				if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
					reqVo.put("i_sLogo_flag", "N");
					//companyInfo.put("v_logo_attachid", "no_logo2.jpg");
				}
				if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
					reqVo.put("i_sSign_flag", "N");
				}
			} else {
				companyInfo = new CmMap();
				reqVo.put("i_sLogo_flag", "N");
				reqVo.put("i_sSign_flag", "N");
				//companyInfo.put("v_logo_attachid", "no_logo2.jpg");
			}
			
//			if("Y".equals(reqVo.getString("i_sAllergenFlag"))){
//			}
			List<CmMap> allergenList = brpr012service.selectAllergenIngrtList(reqVo);
			mav.addObject("allergenList", allergenList);
			
			List<CmMap> list = null;
			reqVo.put("i_sLeaveonYn", productInfo.getString("v_part_leaveon_yn"));
			reqVo.put("i_sSortCol", "N_RCONT");
			
			if("Y".equals(reqVo.getString("i_sRefYn"))){
				list = brpr012service.selectOdmConSingleListWithoutAllergen(reqVo);
			}else{
				list = brpr012service.selectOdmConSingleListAllergen(reqVo);
			}
	
			List<CmMap> fragList = brpr012service.selectOdmFragranceList(reqVo);
	
			CmMap maycontain = brpr012service.selectOdmMayContainInfo(reqVo);
			mav.addObject("maycontain", maycontain);
			mav.addObject("companyInfo", companyInfo);
			mav.addObject("productInfo", productInfo);
			mav.addObject("list",list);
			mav.addObject("fragList", fragList);
			
			reqVo.put("i_sSignDate", CmFunction.getSignDate());
		}
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 제품문서 msds pdf
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doc/co_doc_msds_pdf_print.do")
	public ModelAndView co_doc_msds_pdf_print (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("sitims/doc/co_doc_msds_pdf_print");
		CmMap companyInfo = new CmMap();
		
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		reqVo.put("i_sMatrcd", productInfo.getString("v_product_cd"));
		reqVo.put("i_iVsn", reqVo.getString("i_iVerSeq"));
		
		String companyCd = null;

		companyCd = productInfo.getString("v_vendor_id");

		reqVo.put("i_sCompanyCd", productInfo.getString("v_vendor_id"));
		
		mav.addObject("productInfo", productInfo);
		
		companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
				reqVo.put("i_sLogo_flag", "N");
				//companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
				reqVo.put("i_sSign_flag", "N");
				//companyInfo.put("v_sign", "no_sign2.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			//companyInfo.put("v_logo", "no_logo2.jpg");
			//companyInfo.put("v_sign", "no_sign2.jpg");
		}
		
		mav.addObject("companyInfo", companyInfo);
		reqVo.put("v_now_date", CmFunction.getUsDate1(CmFunction.getToday()));
		
		reqVo.put("i_iVerSeq", "1");			
		reqVo.put("uClassCd", "S000001");
		reqVo.put("v_sub_flag", "N");
		
		//SECTION 항목
		List<CmMap> sectionList = brpr012service.getSectionList(reqVo);
		mav.addObject("sectionList", sectionList);
		
		reqVo.put("v_sub_flag", "Y");
		List<CmMap> sectionListSub = brpr012service.getSectionList(reqVo);
		mav.addObject("sectionList_sub", sectionListSub);
		
		//hazard
//			List<CmMap> hazardList = brpr012service.getHazardInfo(reqVo);
//			mav.addObject("hazardList", hazardList);
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 제품문서 복합 Fomula pdf
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doc/co_doc_complex_formula_pdf_print.do")
	public ModelAndView co_doc_complex_formula_pdf_print (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView();
		//mav.setViewName("sitims/doc/co_doc_complex_formula_pdf_print");
		String lang = reqVo.getString("i_sLang");

		//CmMap productInfo = pifOdmTddService.selectOdmProductInfo(reqVo);
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		reqVo.put("i_sMatrcd", productInfo.getString("v_product_cd"));
		reqVo.put("i_sPartTab", "Y");
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
//			if("Y".equals(productInfo.getString("v_import_yn"))){
//				reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
//			}else{
//			}
		reqVo.put("i_sPk1", companyCd);
		
//		if("Y".equals(reqVo.getString("i_sAllergenFlag"))){
			List<CmMap> allergenList = brpr012service.selectAllergenIngrtList(reqVo);
			mav.addObject("allergenList", allergenList);
//		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
				reqVo.put("i_sLogo_flag", "N");
				//companyInfo.put("v_logo", "no_logo2.jpg");
			}
			if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
				reqVo.put("i_sSign_flag", "N");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			//companyInfo.put("v_logo", "no_logo2.jpg");
		}
		
		List<CmMap> list = null;
		List<CmMap> fragList = brpr012service.selectOdmFragranceList(reqVo);
		
		if("ko".equals(lang)){
			list = brPw020Service.selectOdmPifViewForKorea(reqVo);
		}
		else if("jp".equals(lang)){
			list = brPw020Service.selectOdmPifViewForJapan(reqVo);
		}
		else if("cn".equals(lang)){
			list = brPw020Service.selectOdmPifViewForChina(reqVo);
		}

		//mav.setViewName("/br/pr/012/doc/co_doc_formula_"+_lang+"_pdf_print");
		mav.setViewName("sitims/doc/co_doc_complex_formula_pdf_print_"+lang);
		
		mav.addObject("companyInfo", companyInfo);
		mav.addObject("productInfo", productInfo);
		mav.addObject("list", list);
		mav.addObject("fragList", fragList);

		reqVo.put("i_sSignDate", CmFunction.getSignDate());			
	
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 문서 pdf Download
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/doc/doc_pdf_download.do")
	public void doc_pdf_download (HttpServletRequest request, HttpServletResponse response) throws Exception {
		String recordid = request.getParameter("i_sRecordid");
		String partno = request.getParameter("i_sPartNo");
		String verseq = request.getParameter("i_iVerSeq");
		String allergenflag = request.getParameter("i_sAllergenFlag");
		String productcd = request.getParameter("i_sMatrcd");
		String expDiv = request.getParameter("i_sExpDiv");
		String refYn = request.getParameter("i_sRefYn");
		String div = request.getParameter("i_sDiv");
		String lang = request.getParameter("i_sLang");
		String sRecipeType = request.getParameter("i_sRecipeType");
		try {
			if(CmFunction.isEmpty(recordid) ) {
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>alert('no content');history.back();</script>");
				return;
			}
			
			String url="";					
			String title="";
			String flag = "KO";
			
			if("S".equals(div)) {
				title = "Single_Formula";
				url = "/doc/co_doc_formula_pdf_print.do";
			}else if("M".equals(div)) {
				url = "/doc/co_doc_msds_pdf_print.do";
				title = "MSDS";
			}else if("C".equals(div)) {
				url = "/doc/co_doc_complex_formula_pdf_print.do";
				title = "Complex_Formula";
				flag = lang.toUpperCase();
			}else if("P".equals(div)) {
				url = "/doc/co_doc_process_preview_pdf_print.do";
				title = "Complex_Flow_Chart";
				flag = "CN";
			}else if("ST".equals(div)) {
				url = "/doc/co_doc_stability_pdf_print.do";
				title = "Stability";				
			}
			
			StringBuilder urlSb = new StringBuilder(CmPathInfo.getROOT_FULL_URL());
			urlSb.append("br/pr/012"+url+"?"+"i_sRecordId="+recordid+"&i_sProductCd="+productcd+"&i_sPartNo="+partno+"&i_iVerSeq="+verseq+"&i_sExpDiv="+expDiv+"&i_sRefYn="+refYn+"&i_sLang="+lang+"&i_sRecipeType="+sRecipeType);
			if(CmFunction.isNotEmpty(allergenflag)){
				urlSb.append("&i_sAllergenFlag=").append(allergenflag);
		}
			
			CmMap tempVo = new CmMap();
			tempVo.put("i_sRecordId", recordid);
			tempVo.put("i_sProductCd", productcd);
			CmMap prodVo = brpr012service.getBrPr012Info(tempVo);
			StringBuilder rTitle = new StringBuilder(1024);
			rTitle.append(productcd).append('_').append(prodVo.getString("v_product_nm_en")).append('_').append(title);
			
			//title = title + " " + productcd;
			
			HttpSession session = request.getSession();
			session.setAttribute("S_WORD_FILE", rTitle.toString());
	
			this.documentPdfPrint(urlSb.toString(), rTitle.toString(), flag, request, response, "");
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			this.pdfExcelDownloadLog(productcd,url,rTitle.toString(),ipAddr,"PDF");
		}catch (IOException e) {
			this.errorLogger(e);
		}
	}
	/**
	 * 제품문서 공정도 pdf 화면
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/co_doc_stability_pdf_print.do")
	public ModelAndView co_doc_stability_pdf_print (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView();
		mav.setViewName("sitims/doc/co_doc_stability_pdf_print");
		
		//String closeScript = CmFunction.cmDalogCloseScript(reqVo.getString("i_sFrameid")); 
				
		String date = CmFunction.getUsDate1(CmFunction.getToday());
		reqVo.put("v_now_date", date);
		// Product 기본정보
		//CmMap prodVo = odmInciService.selectOdmProductInfo(reqVo);
		CmMap prodVo = brpr012service.getBrPr012Info(reqVo);
		mav.addObject("rvo", prodVo);
		
		String companyCd = null;
		companyCd = prodVo.getString("v_vendor_id");
		
		reqVo.put("i_sCompanyCd", companyCd);		
		mav.addObject("prodVo", prodVo);		
				
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
				reqVo.put("i_sLogo_flag", "N");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
				reqVo.put("i_sSign_flag", "N");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
		}
		// Company 정보
		mav.addObject("compVo",companyInfo);
		reqVo.put("i_iVerSeq", prodVo.getString("n_vsn"));
		reqVo.put("i_iVerSeq", prodVo.getString("n_vsn"));

		
		reqVo.put("now_date", CmFunction.getUsDate1(CmFunction.getToday()));
		List<CmMap>	list	= brPw020Service.selectProductStabilityInfo(reqVo);
		mav.addObject("list"	, list);
		mav.addObject("sCondition"	, commonService.getCmSubCodeList("STABILITY_CONDI"));
		mav.addObject("sAnalysis"	, commonService.getCmSubCodeList("STABILITY_ANAL"));		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 제품문서 공정도 pdf 화면
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/co_doc_process_preview_pdf_print.do")
	public ModelAndView co_doc_process_preview_pdf_print (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView();
		mav.setViewName("sitims/doc/co_doc_process_preview_pdf_print");
		
		//String closeScript = CmFunction.cmDalogCloseScript(reqVo.getString("i_sFrameid")); 
				
		String date = CmFunction.getUsDate1(CmFunction.getToday());
		reqVo.put("v_now_date", date);
		// Product 기본정보
		//CmMap prodVo = odmInciService.selectOdmProductInfo(reqVo);
		CmMap prodVo = brpr012service.getBrPr012Info(reqVo);
		mav.addObject("prodVo", prodVo);
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(prodVo.getString("v_packing_companycd"))){
			companyCd = prodVo.getString("v_packing_companycd");
		}else{
			companyCd = prodVo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		mav.addObject("prodVo", prodVo);
		
		if("Y".equals(prodVo.getString("v_importyn"))){
			reqVo.put("i_sPk1", prodVo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
				reqVo.put("i_sLogo_flag", "N");
				//companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
				reqVo.put("i_sSign_flag", "N");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			//companyInfo.put("v_logo", "no_logo2.jpg");
			//companyInfo.put("v_sign", "no_sign2.jpg");
		}
		// Company 정보
		mav.addObject("compVo",companyInfo);
		// Process Mst
		reqVo.put("i_iVerSeq", prodVo.getString("n_vsn"));
		mav.addObject("processVo", brPw020Service.selectProcessMstInfo(reqVo));
		// Process part
		mav.addObject("folderList", brPw020Service.selectProcessFolderInfo(reqVo));
		// Process component
		reqVo.putDefault("i_sRecipeType", "S");
		reqVo.put("i_sConDiv","S".equals(reqVo.getString("i_sRecipeType")) ? "V_CON_CD" : "V_RAW_CD");
		mav.addObject("conList", brPw020Service.getSavedConListForPif(reqVo));
		
		reqVo.put("now_date", CmFunction.getUsDate1(CmFunction.getToday()));
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 제품 공정도 excel
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/co_doc_process_preview_pop_excel.do")
	public ModelAndView co_doc_process_preview_pop_excel (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View");
		OdmReportPoi				poi				=	new OdmReportPoi();
		HSSFWorkbook				wb				=	new HSSFWorkbook();
		
		//if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))){
		////	wb.close();
		//	return this.setMessage(mav, reqVo, reqVo.getString("i_sReturnUrl"), reqVo.getString("i_sReturnPars"), "[ i_sRecordId ] "+this.getMessageSource("pms.common.no_essential_data"), "");
		//}
		

		brpr012service.coDocProcessExcel(reqVo,mav);
		CmMap prodVo = (CmMap) reqVo.get("prodVo");
		
		String recipeType = "S".equals(reqVo.getString("i_sRecipeType")) ? "Single" : "Complex";
		HSSFWorkbook hssfWorkbook = "S".equals(reqVo.getString("i_sRecipeType")) ? poi.excelProcess(reqVo,wb) : poi.excelProcessComplex(reqVo,wb);

		//ip주소
		String ipAddr			= request.getRemoteAddr();
		this.pdfExcelDownloadLog(prodVo.getString("v_product_cd"),"/doc/co_doc_process_preview_pop_excel.do",prodVo.getString("v_product_cd")+"_"+prodVo.getString("v_product_nm_en").trim()+"_Flowchart_"+recipeType,ipAddr,"EXCEL");
		mav.addObject("HSSFWorkbook", hssfWorkbook);
		mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
		mav.addObject("excelFileName",  prodVo.getString("v_product_cd")+"_"+prodVo.getString("v_product_nm_en").trim()+"_Flowchart_"+recipeType);
			
		return mav;
	}
	/**
	 * 제품문서 복합 Fomula excel ko
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/doc/co_doc_complex_formula_excel_ko.do")
	public ModelAndView pif_doc_formula_ko_excel (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View");
		OdmReportPoi odmReportPoi = new OdmReportPoi();
		HSSFWorkbook wb = new HSSFWorkbook();
		
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))){
			return this.setMessage(mav, reqVo, "", "", "[ i_sRecordId ] 필수값이 없습니다.", "");
		}
		
		//CmMap productInfo = pifOdmTddService.selectOdmProductInfo(reqVo);
		brpr012service.coDocFomulaKoExcel(reqVo,mav);
		CmMap productInfo = (CmMap) reqVo.get("productInfo");
		//ip주소
		String ipAddr			= request.getRemoteAddr();
		this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/co_doc_complex_formula_excel_ko.do",productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_FORMULA_KO",ipAddr,"EXCEL");
		mav.addObject("HSSFWorkbook" ,odmReportPoi.excelFormulaKo(reqVo, wb));
		mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
		mav.addObject("excelFileName", productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_FORMULA_KO");
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 제품 복합 fomula excel jp
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/doc/co_doc_complex_formula_excel_jp.do")
	public ModelAndView co_doc_complex_formula_excel_jp (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View");
		OdmReportPoi odmReportPoi = new OdmReportPoi();
		HSSFWorkbook	wb	=	new HSSFWorkbook();
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))){
		//	wb.close();
			return this.setMessage(mav, reqVo, "","", "[ i_sRecordId ] 필수값이 없습니다.", "");
		}
		brpr012service.coDocFomulaJpExcel(reqVo,mav);
		CmMap productInfo = (CmMap) reqVo.get("productInfo");
		//ip주소
		String ipAddr			= request.getRemoteAddr();
		this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/co_doc_complex_formula_excel_jp.do",productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_FORMULA_JP",ipAddr,"EXCEL");
		mav.addObject("HSSFWorkbook" ,odmReportPoi.excelFormulaJp(reqVo, wb));
		mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
		mav.addObject("excelFileName", productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_FORMULA_JP");
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 제품 복합 fomula excel cn
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/doc/co_doc_complex_formula_excel_cn.do")
	public ModelAndView co_doc_complex_formula_excel_cn (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View");
		OdmReportPoi odmReportPoi = new OdmReportPoi();
		HSSFWorkbook	wb	=	new HSSFWorkbook();
	
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))){
		//	wb.close();
			return this.setMessage(mav, reqVo, "","", "[ i_sRecordId ] 필수값이 없습니다.", "");
		}

		brpr012service.coDocFomulaCnExcel(reqVo,mav);
		CmMap productInfo = (CmMap) reqVo.get("productInfo");
		
		mav.addObject("HSSFWorkbook" ,odmReportPoi.excelFormulaCh(reqVo, wb));
		mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
		mav.addObject("excelFileName", productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_FORMULA_CH");
		//ip주소
		String ipAddr			= request.getRemoteAddr();
		this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/co_doc_complex_formula_excel_cn.do",productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_FORMULA_CH",ipAddr,"EXCEL");
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
}
