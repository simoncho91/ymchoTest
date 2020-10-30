package com.shinsegae_inc.sitims.mi.br.pr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.service.CommonService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr010Service;
import com.shinsegae_inc.sitims.mi.si.cm.service.SiCm020Service;

@Controller
@RequestMapping(value="/br/pr/010/*")
public class BrPr010Controller extends CommonController{
    
	@Resource(name = "sitims.BrPr010Service")
	private transient BrPr010Service brpr010service;

	@Resource(name = "sitims.SiCm020Service")
	private transient SiCm020Service sicm020Service;
	
	@Resource(name = "commonServiceSitms")
	private transient CommonService commonService;
	
    @Value("${globals.security.userSessionNm}")
    private transient String userSessionNm;
    

	@RequestMapping(value="/br_pr_010_prod_list_pop.do")
	public ModelAndView br_pr_010_prod_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/br/pr/010/br_pr_010_prod_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	@RequestMapping(value="/br_pr_010_refer_prod_list_pop.do")
	public ModelAndView br_pr_010_refer_prod_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/br/pr/010/br_pr_010_refer_prod_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	@RequestMapping(value="/getProdPopList.do")
	public ModelAndView getProdPopList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, brpr010service.getProdPopList(reqVo)); 
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
    * Commnets : 제품 목록 페이지
    * </pre>
	 */
    @RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/br/pr/010/br_pr_010_list");
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
     * 
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     * 
     * <pre>
     * Commnets : 제품목록 조회
     * </pre>
     */
    @RequestMapping(value="/selectList.do")
    public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	
    	
    	//국내검토 요청일 달력 추가로 인한 로직
    	String rStdt = null; 
    	String rEndt = null; 
    	
    	if(CmFunction.isNotEmpty(reqVo.getString("i_sReqStDate")) || CmFunction.isNotEmpty(reqVo.getString("i_sReqEnDate"))) {
    		rStdt = reqVo.getString("i_sReqStDate").replace("-", "");
    		rEndt = reqVo.getString("i_sReqEnDate").replace("-", "");
    		reqVo.put("i_sReqStDate", rStdt);
    		reqVo.put("i_sReqEnDate", rEndt);
    		if(rStdt.equals("")) reqVo.put("i_sReqStDate", "19010101");
    		if(rEndt.equals("")) reqVo.put("i_sReqEnDate","29991231");
    	}

    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, brpr010service.selectBrPr010List(reqVo));
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
     * Commnets : 제품등록 페이지
     * </pre>
     */
    @RequestMapping(value="/br_pr_010_reg.do")
	public ModelAndView br_pr_010_reg (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pr/010/br_pr_010_reg");
		Map<String, String> userInfo = (Map<String, String>) SessionUtils.getAttribute(userSessionNm);
    	
    	mav.addObject("BRAND_LIST", commonService.getCmSubCodeList("BRAND_CODE"));
    	mav.addObject("ODM_PRODUCT_TYPE_LIST", commonService.getCmSubCodeList("ODM_PRODUCT_TYPE"));
    	mav.addObject("CNTRFORM_LIST", commonService.getCmSubCodeList("ODM_CONT1"));
    	mav.addObject("CNTRMATR_LIST", commonService.getCmSubCodeList("ODM_CONT2"));
    	mav.addObject("COUNTRY_LIST", commonService.getCmSubCodeList("ODM_EXPORT",1,"Y"));
    	mav.addObject("ODM_FUNC_LIST", commonService.getCmSubCodeList("MRC03"));
    	
    	mav.addObject("originDivList", commonService.getCmSubCodeList("ODM_ORIGINDIV"));
    	
    	if("M".equals(reqVo.getString("i_sActionFlag"))) {
        	mav.addObject("rVo", brpr010service.getBrPr010Info(reqVo));
        	List<CmMap> list = brpr010service.getBrPr010ProdList(reqVo);
        	for(CmMap map : list) {
            	CmMap tempVo = new CmMap();
        			tempVo.put("i_sClassCd", "S000001");
        			tempVo.put("i_sFlagCategory1", "Y");
        			tempVo.put("i_sUseYn", "Y");
        			tempVo.put("i_sTopCd", map.getString("v_type"));		
        			map.put("cateList", sicm020Service.selectSiCm020List(tempVo));        		
        	}
        	mav.addObject("prodList", list);    		
    	}else {
			reqVo.put("s_usernm",userInfo.get("LOGIN_NM"));
			reqVo.put("s_userid",userInfo.get("USER_NO"));
    	}
		CmMap deptVo = new CmMap();
		deptVo.put("i_sDeptCd2", userInfo.get("DEPT_CD"));
		CmMap brandInfo = brpr010service.getBrandCd(deptVo);
		reqVo.put("s_brand_cd",brandInfo.get("v_brand_cd"));
    	mav.addObject("reqVo", reqVo);
    	
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
     * Commnets : 제품상세 페이지
     * </pre>
     */
    @RequestMapping(value="/br_pr_010_view.do")
	public ModelAndView br_pr_010_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = brpr010service.getBrPr010Info(reqVo);
    	
    	mav.setViewName("sitims/mi/br/pr/010/br_pr_010_view");
    	
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

    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rVo", rVo);
    	
    	return mav;
	}
    /**
     * 제품등록 저장
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_010_save.do")
    @SuppressWarnings({"PMD.CyclomaticComplexity"})
    public ModelAndView br_pr_010_save(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	String userId = SessionUtils.getUserNo();
    	reqVo.put("i_sLoginUserId", userId);
		String actionFlag = reqVo.getString("i_sActionFlag");
		
		if ("R".equals(actionFlag)) { // 등록				
			brpr010service.insertBrPr010(reqVo);				
		}
		else if ("M".equals(actionFlag)){				
			brpr010service.updateBrPr010(reqVo);
		}
		//접수요청일 경우 RA, ODM 메일 발송 
		if("RS010".equals(reqVo.getString("i_sReceipStatus"))) {
			String[] arrProductRefcd = reqVo.getStringArray("i_arrProduct_Refcd"); // 제품코드
			String sOdmemail = reqVo.getString("i_sEmail"); // odm 담당자 email
			String[] arrProductRefNm = reqVo.getStringArray("i_arrProduct_RefNm"); // 제품명
			if(arrProductRefcd != null && arrProductRefcd.length > 0 && CmFunction.isNotEmpty(arrProductRefcd[0]) ) {
				for(int i=0;i<arrProductRefcd.length;i++) {
					// 기능성
					String arrFuncyn = reqVo.getStringArray("i_arrFuncyn_"+i) !=null && reqVo.getStringArray("i_arrFuncyn_"+i).length>0 ? reqVo.getStringArray("i_arrFuncyn_"+i)[0]:"";
					String[] iArrExp = reqVo.getStringArray("i_arrExp_"+i);
					 reqVo.put("i_sFuncyn", arrFuncyn);
 					 reqVo.put("i_sProductCd", arrProductRefcd[i]);
					 reqVo.put("i_sOdmEmail", sOdmemail);
					 reqVo.put("i_sProductNm", arrProductRefNm[i]);
					 List<CmMap> mailInfoList = brpr010service.getBrPr010EmailInfo(reqVo);
					
					 //수출 대상 국가를 선택한 경우
					if(iArrExp != null && iArrExp.length > 0) {
						 for(CmMap map : mailInfoList) {
							 //set to ra-email
							  if(!map.get("EMAIL").toString().equals("")) {
								  reqVo.put("i_sToAddr",cryptoService.decAES(map.get("EMAIL").toString(), true));
							  }else {
								  reqVo.put("i_sToAddr","to_mail_test@shinsegae.com");
							  }
							//set from loginUser-email
							  if(!map.get("LOGINUSEREMAIL").toString().equals("")) {
								  reqVo.put("i_sFromAddr",cryptoService.decAES(map.get("LOGINUSEREMAIL").toString(), true));
							  }
							  reqVo.put("i_sBrandNm", map.getString("v_brand_nm"));
							  //수출 RA
							  reqVo.put("i_sSubject", "[수출허가 접수요청] 제품 코드 " + reqVo.getString("i_sProductNm") +" "+reqVo.getString("i_sProductCd"));
							  if(!map.get("v_nation_cd").equals("KO")){
								  reqVo.put("paramURL", this.getMailLink(userId,"/br/pw/020/br_pw_020_t1_view.do?openMenuCd=MIBRPW021-T1&i_sRecordId="+map.getString("v_record_id")+"&i_sProductCd="+map.getString("v_product_cd")));
							  }else {
								  reqVo.put("paramURL", "");
							  }
							  reqVo.put("i_sContents",this.getMailContents("sitims/views/mi/br/pr/010/br_pr_010_mail", reqVo));
							  //메일 로그
								CmMap logVo1 = new CmMap();
								logVo1.put("i_sTitle", reqVo.getString("i_sSubject"));
								logVo1.put("i_sContents", reqVo.getString("i_sContents"));
								logVo1.put("i_sRecordId", map.getString("v_product_cd"));
								logVo1.put("i_sNationCd", map.getString("v_nation_cd"));
								logVo1.put("i_sFromUser", userId);
								logVo1.put("i_sFromAddr", reqVo.get("i_sFromAddr"));
								logVo1.put("i_sRevUserIdAll", reqVo.getString("i_sToAddr"));
					    		commonService.insertMailLog(logVo1);
					    		
						      this.sendMailwithHtml(reqVo);
						 }
					}else {
						  //메일 로그
							CmMap logVo2 = new CmMap();
						for(CmMap map : mailInfoList) {
							if(map.get("v_nation_cd").equals("KO")) {
								//set to ra-email
							  if(!map.get("EMAIL").toString().equals("")) {
								  reqVo.put("i_sToAddr",cryptoService.decAES(map.get("EMAIL").toString(), true));
							  }else {
								  reqVo.put("i_sToAddr","to_mail@shinsegae.com");
							  }
							//set from loginUser-email
							  if(!map.get("LOGINUSEREMAIL").toString().equals("")) {
								  reqVo.put("i_sFromAddr",cryptoService.decAES(map.get("LOGINUSEREMAIL").toString(), true));
							  }
							}
							 reqVo.put("i_sBrandNm", map.getString("v_brand_nm"));
							 logVo2.put("i_sRecordId", map.getString("v_product_cd"));
						}
						 //국내 RA
						  reqVo.put("i_sSubject", "[ODM 원료 등록 요청] 제품 코드 " + reqVo.getString("i_sProductNm") +" "+reqVo.getString("i_sProductCd"));
						  reqVo.put("i_sContents",this.getMailContents("sitims/views/mi/br/pr/010/br_pr_010_mail", reqVo));
						  //메일 로그
						  logVo2.put("i_sTitle", reqVo.getString("i_sSubject"));
						  logVo2.put("i_sContents", reqVo.getString("i_sContents"));
						  logVo2.put("i_sRevUserIdAll", reqVo.getString("i_sToAddr"));
						  logVo2.put("i_sFromUser", userId);
						  logVo2.put("i_sFromAddr", reqVo.get("i_sFromAddr"));
				    	  commonService.insertMailLog(logVo2);
						  this.sendMailwithHtml(reqVo);
						
						// ODM사 (국내RA 와 컨텐츠와 수신자만 달라짐)
						reqVo.put("i_sToAddr", reqVo.get("i_sOdmEmail"));
						reqVo.put("i_sSubject", "[ODM 원료 등록 요청] 제품 코드 " + reqVo.getString("i_sProductNm") +" "+reqVo.getString("i_sProductCd"));
						reqVo.put("paramURL", this.getMailLink(userId, "/si/im/020/init.do?openMenuCd=MISIIM020"));
						reqVo.put("i_sContents",this.getMailContents("sitims/views/mi/br/pr/010/br_pr_010_mail", reqVo));
						  //메일 로그
						logVo2.put("i_sContents", reqVo.getString("i_sContents"));
						logVo2.put("i_sRevUserIdAll", reqVo.getString("i_sToAddr"));
				    	commonService.insertMailLog(logVo2);
						this.sendMailwithHtml(reqVo);

					}
				}
			}
		}
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
    }
    /**
     * 제품등록 수정팝업화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/br_pr_010_modify_pop.do")
	public ModelAndView br_pr_010_modify_pop(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("sitims/mi/br/pr/010/br_pr_010_modify_pop");
    	mav.addObject("CNTRFORM_LIST", commonService.getCmSubCodeList("ODM_CONT1")); //용기대유형
    	mav.addObject("CNTRMATR_LIST", commonService.getCmSubCodeList("ODM_CONT2"));//용기소유형
    	//mav.addObject("COUNTRY_LIST", commonService.getCmSubCodeList("ODM_EXPORT",1,"Y")); // 예상수출국가
    	mav.addObject("ODM_FUNC_LIST", commonService.getCmSubCodeList("MRC03")); //기능성 Y일때
    	mav.addObject("originDivList", commonService.getCmSubCodeList("ODM_ORIGINDIV")); // 본품여부 N일때
    	mav.addObject("NATIONRA_LIST", commonService.getCmSubCodeList("ODM_EXPORT",1,"Y")); 
    	
    	mav.addObject("rVo", brpr010service.getBrPr010Info(reqVo));
    	mav.addObject("prodList", brpr010service.getBrPr010ProdList(reqVo));
		return mav;
	}
	/**
	 * 제품등록 요청 수정
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="/br_pr_010_modifyForPermitReqStatus.do")
    public ModelAndView br_pr_010_modifyForPermitReqStatus(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		reqVo.put("i_sLoginUserId", SessionUtils.getUserNo());
		brpr010service.updateBrPr010PermitReqStatus(reqVo);
		return this.makeJsonResult(mav, "success", "수정 성공", reqVo);
    }
    /**
     * 제품등록 요청 국가 수정
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	/*
	 * @RequestMapping(value="/br_pr_010_updateBrPr010PermitReqNation.do") public
	 * ModelAndView
	 * br_pr_010_updateBrPr010PermitReqNation(@ModelAttribute("dataMap") CmMap
	 * reqVo, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { ModelAndView mav = new ModelAndView("jsonView");
	 * reqVo.put("i_sLoginUserId", SessionUtils.getUserNo()); int result =
	 * brpr010service.BrPr010PermitReqNationChk(reqVo); if(result == 1) { return
	 * this.makeJsonResult(mav, "notok", "해당 국가의 RA가 제품 리뷰를 완료한 상태입니다.", null); }
	 * return this.makeJsonResult(mav, "ok", " 수정 가능한 수출국가입니다.", null); }
	 */
    @RequestMapping(value="/chkProductCdDuplication.do")
    public ModelAndView chkProductCdDuplication(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	CmMap tempVo = new CmMap();
    	tempVo.put("i_arrProductCd", reqVo.get("i_arrProduct_Refcd"));
    	tempVo.put("i_sNotRecordId", reqVo.get("i_sRecordId"));
    	
    	return this.makeJsonDhtmlxResult(mav, "success", "OK", brpr010service.getBrPr010ProdList(tempVo));
    }
    
    /**
     * 제품등록 엑셀
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/br_pr_010_list_excel.do")
	public ModelAndView br_pr_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	brpr010service.selectBrPr010List(reqVo);
		String excelFileNm = reqVo.getString("i_sExcelFileNm");
		// Title
		String[] titleArray = {
			 "No"
			, "문서코드"
			, "제품 코드"
			, "한글 제품명"
			, "영문 제품명"
			, "제품등록"
			, "국내검토"
			, "문안검토"
			, "글러벌 검토"
			, "기능성 여부"
			, "제품 담당자"
			, "등록일"
		};
		
		// DB Column Name
		String[] columnNmArray = {
			 "n_num"
			, "v_record_id"
			, "v_product_cd"
			, "v_product_nm_ko"
			, "v_product_nm_en"
			, "v_receip_status_nm"
			, "v_ck_domestic_yn_nm"
			, "v_ck_advertise_yn_nm"
			, "v_ck_overseas_yn_nm"
			, "v_func_yn"
			, "v_bm_nm"
			, "v_reg_dtm_ori"
		};

		mav.addObject("excelFileName"	, CmFunction.isEmpty(excelFileNm)?"제품등록":excelFileNm);
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
    @RequestMapping(value="/updateBrPr010BmId.do")
    public ModelAndView updateBrPr010BmId(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	
    	brpr010service.updateBrPr010BmId(reqVo);
    	return this.makeJsonDhtmlxResult(mav, "success", "OK", reqVo);
    }
    
}
