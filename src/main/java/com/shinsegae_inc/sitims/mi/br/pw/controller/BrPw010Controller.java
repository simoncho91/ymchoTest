package com.shinsegae_inc.sitims.mi.br.pw.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.StringUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.poi.OdmReportPoi;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr010Service;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr012Service;
import com.shinsegae_inc.sitims.mi.br.pw.service.BrPw010Service;
import com.shinsegae_inc.sitims.mi.br.pw.service.BrPw020Service;

@Controller
@RequestMapping(value="/br/pw/010/*")
public class BrPw010Controller extends CommonController{	
	@Autowired
	private transient BrPw010Service brPw010Service; 
	@Autowired
	private transient BrPr010Service brpr010service; 
	@Autowired
	private transient BrPr012Service brpr012service; 
	@Autowired 
	private transient BrPw020Service brPw020Service;
	
	/**
	 * 국내검토 리스트 화면
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/br/pw/010/br_pw_010_list");
    	if(CmFunction.isEmpty(reqVo.getString("i_sSearchStatus"))) {
    		reqVo.put("i_sSearchStatus","");
    	}
		if(CmFunction.isEmpty(reqVo.getString("i_sSearchStatusVal"))) {
			reqVo.put("i_sSearchStatusVal","");
		}
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	/**
	 * 국내검토 리스트 조회
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="/selectList.do")
    public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	

		
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, brPw010Service.selectBrPw010List(reqVo));
    }
    /**
     * 국내검토 제품 화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_010_t1_view.do")
	public ModelAndView br_pw_010_t1_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	String[] str = reqVo.getStringArray("i_sRecordId");
    	String[] str2 = reqVo.getStringArray("i_sProductCd");
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
    	
    	mav.setViewName("sitims/mi/br/pw/010/br_pw_010_t1_view");
    	reqVo.put("i_sBigTab", "REP");
    	if(rVo != null && !rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
    	}
    	
    	mav.addObject("prodList", brpr010service.getBrPr010ProdList(reqVo));
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rVo", rVo);
    	
    	return mav;
	}
    /**
     * 제품검토 기능성 화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_010_t3_view.do")
	public ModelAndView br_pw_020_t3_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rvo = brpr012service.getBrPr012Info(reqVo);

    	if(rvo != null && !rvo.isEmpty() && CmFunction.isNotEmpty(rvo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", rvo.getString("v_func_yn"));
    	}
    	mav.setViewName("sitims/mi/br/pw/010/br_pw_010_t3_view");
    	reqVo.put("i_sBigTab", "FUNC");
    	reqVo.put("i_sFuncModifyYn", "Y");
    	mav.addObject("rvo", rvo);
    	
    	mav.addObject("reqVo", reqVo);
    	
    	return mav;
	}
    /**
     * 국내검토 검토화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_010_t4_view.do")
	public ModelAndView br_pw_020_t2_document_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/010/br_pw_010_t4_view");
    	CmFunction.setSessionValue(request, reqVo);
    	reqVo.putDefault("i_sPartNo", "1");
    	reqVo.put("i_sBigTab", "REV");	
    	//if(reqVo.getString("s_groupid").indexOf("S000258") == -1 && reqVo.getString("s_groupid").indexOf("S000129") == -1 && !"Y".equals(reqVo.getString("s_sysadmin_yn"))) {
		if(StringUtils.isNotEmpty(reqVo.getString("s_groupid")) && reqVo.getString("s_groupid").indexOf("ADMIN") > -1) {
			reqVo.put("i_sAuthFlag", "Y");
		} else {
			reqVo.put("i_sAuthFlag", "N");
		}		
		CmMap rvo = brpr012service.getBrPr012Info(reqVo);

    	if(rvo != null && !rvo.isEmpty() && CmFunction.isNotEmpty(rvo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", rvo.getString("v_func_yn"));
    	}

    	
		reqVo.put("i_sNationCd","KO");
		mav.addObject("partList", brPw020Service.selectPartnoList(reqVo));
		mav.addObject("nationReviewList", brPw020Service.getNationReviewList(reqVo));
    	mav.addObject("reviewStList", commonService.getCmSubCodeList("REVIEW_STATUS"));
    	
    	mav.addObject("rqInfo", brPw020Service.getProdMyRq(reqVo));
		mav.addObject("reqModiList", commonService.getCmSubCodeList("DOC_MODI_LIST"));

		mav.addObject("cmDocList", commonService.getCmSubCodeList("FILE_REQ",1,"CM"));
		mav.addObject("docList", commonService.getCmSubCodeList("FILE_REQ",1,"KO"));
		
    	mav.addObject("rvo", rvo);
		mav.addObject("reqVo", reqVo);
		return mav;
    }
    /**
     * 국내검토 기능성 저장
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_010_t3_save_report.do")
	public ModelAndView br_pw_010_t3_save_report(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
		brPw010Service.saveBrPw010Report(reqVo);
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", reqVo);
    }
    /**
     * 국내검토 기처리 처리
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/updateAlreadyProcess.do")
	public ModelAndView updateAlreadyProcess(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
    	brPw010Service.updateAlreadyProcess(reqVo);
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", reqVo);
    }

    @RequestMapping(value="/br_pw_010_t2_view.do")
	public ModelAndView br_pw_010_t2_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
    	if(CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
    	}
    	mav.setViewName("sitims/mi/br/pw/010/br_pw_010_t2_view");

    	List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
		int partListSize = partList == null ? 0 : partList.size();
		mav.addObject("partList", partList);

		if(partListSize > 0) {
			reqVo.put("i_sPartTab", "Y");

			reqVo.putDefault("i_sPartNo", "1");
			List<CmMap> list = brPw020Service.selectOdmConSingleList(reqVo);
			mav.addObject("list", list);
			
			int listSize = list == null ? 0 : list.size();			
			if(listSize > 0) {
				rVo.put("v_inactive_all", list.get(0).getString("v_inactive_all"));
			}
			
			List<CmMap> allergenList = brpr012service.selectAllergenIngrtList(reqVo);
			mav.addObject("allergenList", allergenList);
							
			List<CmMap> fcList= brPw020Service.selectConpFunctionList(reqVo);
			mav.addObject("fcList", fcList);
			
			List<CmMap> specList = brPw020Service.selectSpecList(reqVo);
			mav.addObject("specList", specList);			
		}
    	reqVo.put("i_sBigTab", "STAND");

		//mav.addObject("cmDocList", commonService.getCmSubCodeList("FILE_REQ",1,"CM"));
		mav.addObject("docList", commonService.getCmSubCodeList("FILE_REQ",1,"KO"));
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rVo", rVo);
    	
    	return mav;
	}
    
    @RequestMapping(value="/docZipFileDownload.do")
	public ModelAndView docZipFileDownload(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CmFunction.setSessionValue(request, reqVo);
		CmMap tempVo = new CmMap();
		List<String> arrUploadCd = new ArrayList<String>();
		List<CmMap> docList = commonService.getCmSubCodeList("FILE_REQ",1,"CM");

		for(CmMap map : docList) {
			switch(map.getString("COMM_CD")) {
			case "FR021":
				arrUploadCd.add("PSPEC");
				break;
			case "FR022":
				arrUploadCd.add("MSDS");
				break;
			case "FR023":
				arrUploadCd.add("PSTAB");
				break;
			default:
				arrUploadCd.add(map.getString("COMM_CD"));
				break;
			}			
		}
		docList = commonService.getCmSubCodeList("FILE_REQ",1,"KO");
		for(CmMap map : docList) {
			arrUploadCd.add(map.getString("COMM_CD"));
		}
		tempVo.put("i_sRecordIdLike",reqVo.getString("i_sRecordId"));
		tempVo.put("i_sBuffer1",reqVo.getString("i_sProductCd"));
		tempVo.put("i_sBuffer2",reqVo.getString("i_sPartNo"));
		tempVo.put("i_arrUploadCd",arrUploadCd);
    	List<CmMap> prodAttachList = commonService.getAttachList(tempVo);
		tempVo = new CmMap();
		String[] strArrUploadCd = {"ORI_BOX","ORI_VESSEL","ORI_PAPER"};
		tempVo.put("i_sRecordIdLike",reqVo.getString("i_sAdReqId"));
		tempVo.put("i_arrUploadCd",strArrUploadCd);
    	List<CmMap> attachList = commonService.getAttachList(tempVo);
    	List<File> files = new ArrayList<File>();
    	
    	for(CmMap attachInfo : attachList) {
    		String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";
        	//String fileName = attachInfo.getString("v_attach_nm");
			String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
			//String filename = attachInfo.getString("v_attach_nm");
			String	tmpFilePath		= path+"TEMP/"+ attachInfo.getString("v_attach_nm");
			String  filePath 	= path + tempFileName;
        	if (new File(filePath).exists()) {
				CmFunction.fileCopy(filePath,tmpFilePath);
    			files.add(new File(tmpFilePath));        		
        	}
    	}
    	
    	for(CmMap attachInfo : prodAttachList) {
    		String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";        	
			String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();			
			String	tmpFilePath		= path+"TEMP/"+ attachInfo.getString("v_attach_nm");
			String  filePath 	= path + tempFileName;
        	if (new File(filePath).exists()) {
				CmFunction.fileCopy(filePath,tmpFilePath);
    			files.add(new File(tmpFilePath));        		
        	}
    	}
    	String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
    	String path = CmPathInfo.getUPLOAD_FILE_TEMP_PATH();
    	CmMap tmpVo = reqVo;
    	String xlsFileNm= "" ;
		OdmReportPoi odmReportPoi = new OdmReportPoi();
		HSSFWorkbook	wb	=	new HSSFWorkbook();
    	ModelAndView excelMav = new ModelAndView("excel2View");
    	tmpVo.put("i_sAllergenFlag","Y");
    	tmpVo.put("i_sRefYn","Y");    	
    	brpr012service.coDocFomulaExcel(tmpVo, excelMav);
		CmMap productInfo = (CmMap) tmpVo.get("productInfo");
		xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").replaceAll(match, "").trim()+"_singleFormula(참조)";
    	excelReportDown(odmReportPoi.excelSingleFormula(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));
    	
    	tmpVo.put("i_sFlagImp","F");
    	brPw020Service.coDocSpecExcel(tmpVo,excelMav);
    	productInfo = (CmMap) tmpVo.get("productInfo");
		xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_Spec(완제품)";
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelTdd2017Spec(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));
    	tmpVo.put("i_sFlagImp","S");
    	brPw020Service.coDocSpecExcel(tmpVo,excelMav);
    	productInfo = (CmMap) tmpVo.get("productInfo");
		xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_Spec(반제품)";
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelTdd2017Spec(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));
    	
    	this.zipFileDownLoad(request,response,files,reqVo.getString("i_sProductCd"));
		return null;
    }

	@RequestMapping("/br_pw_010_doc_ingredient_pop.do")
	public ModelAndView br_pw_010_doc_ingredient_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/010/br_pw_010_doc_ingredient_pop");
		
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);
		reqVo.put("i_sAllergenOpen", rVo.getString("v_allergen_open"));
		reqVo.put("i_nVerSeq", rVo.getString("n_vsn"));
		reqVo.put("i_sGubun", "NAT");
		
		CmMap ingredient = brPw020Service.selectIngredient(reqVo);
		mav.addObject("ingredient", ingredient);
		
		return mav;
	}
	 /**
     * 국내검토 최종 전성분표 이력 팝업
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/br_pw_010_t2_pop.do")
	public ModelAndView br_pw_010_t2_pop(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("sitims/mi/br/pw/010/br_pw_010_t2_pop");
			reqVo.put("i_sGubun", "NAT");
			reqVo.put("i_iVerSeq", "1");
			
//			CmMap rvo = odmInciService.selectOdmProductInfo(reqVo);
//			reqVo.put("i_sProduct_Cd", rvo.getString("v_product_cd"));
			
//			mav.addObject("rvo", rvo);
			mav.addObject("ingrtMemoList",brPw010Service.getIngrtMartNationMemo(reqVo));
			mav.addObject("ingrtMemoHisrotyList",brPw010Service.getIngrtMartNationMemoAllVerHistoryList(reqVo));
		
		mav.addObject("reqVo", reqVo);
		
		return mav;
	}

    @RequestMapping(value="/updateDeleteProduct.do")
	public ModelAndView updateDeleteProduct(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
		brPw020Service.updateDeleteProduct(reqVo);
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", reqVo);
    }
}
