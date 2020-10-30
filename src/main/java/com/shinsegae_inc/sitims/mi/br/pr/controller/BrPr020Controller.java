package com.shinsegae_inc.sitims.mi.br.pr.controller;

import java.util.ArrayList;
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
import com.shinsegae_inc.sitims.mi.br.er.service.BrEr040Service;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr012Service;
import com.shinsegae_inc.sitims.mi.br.pr.service.BrPr020Service;
import com.shinsegae_inc.sitims.mi.br.pw.service.BrPw020Service;

/**
 * 문안검토목록(BM)
 * @author FIC06169
 *
 */
@Controller
@RequestMapping(value="/br/pr/020/*")
public class BrPr020Controller extends CommonController{
	@Autowired
	private transient BrPr020Service brPr020Service;
	@Autowired
	private transient BrEr040Service brEr040Service;	
	@Autowired
	private transient BrPw020Service brPw020Service;	
	@Autowired
	private transient BrPr012Service brpr012service;	
	
	/**
	 * 문안등록리스트 시작
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav =  getInitMAV(request,"sitims/views/mi/br/pr/020/br_pr_020_list");
    	if(CmFunction.isEmpty(reqVo.getString("i_sStatus"))) {
    		reqVo.put("i_sStatus","");
    	}
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	/**
	 * 문안등록리스트 리스트데이터(new)
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("br_pr_020_list_ajax.do")
	public	ModelAndView	br_pr_020_list_ajax	(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView	mav			=	new	ModelAndView("jsonView");
		this.setPageUrlAndPars(request, reqVo);
		this.getCmSubCodeList(mav, "BrandList", "BRAND_CODE");  // 브랜드

		List<CmMap> resultList = brPr020Service.selectBrPr020List(reqVo);
		
		mav.addObject("reqVo", reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, resultList);
	}
	
	/**
	 * 문안등록리스트 등록화면 진입
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("br_pr_020_reg.do")
	public ModelAndView	br_pr_020_reg(@ModelAttribute ("dateMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView				mav				=	new ModelAndView();
		boolean						admYn			=	false;
		mav.setViewName("sitims/mi/br/pr/020/br_pr_020_reg");
		
		reqVo.put("i_sRegDtm", CmFunction.getDatePatten(CmFunction.getRegDate8(),""));
		
		//금지어관련 인트로 텍스트
		mav.addObject("introText", brEr040Service.makeIntroText());
		
		this.getCmSubCodeList(mav, "BrandList", "BRAND_CODE");			// 브랜드
		this.getCmSubCodeList(mav, "PROOF_BAN",	"PROOF_BAN");			//금지어 필터링 데이터
		
		//담당 RA
		mav.addObject("raId",brPr020Service.getRaList(reqVo));
		
		//문안코드가 있을 때(수정상태일 때)
		if (!CmFunction.isEmpty(reqVo.getString("i_sAdReqId"))) { 
			CmMap	rvo		=	brPr020Service.getBrPr020Info(reqVo);
			rvo 			=	brPr020Service.getDraftFilteringWordInfo(rvo);
			mav.addObject("rvo", rvo);
		}
		
		mav.addObject("reqVo", reqVo);
		return	mav;
	}
	
	/**
     * 문안등록 상세 - 문안검토
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_020_view.do")
	public ModelAndView br_pr_020_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rvo = brPr020Service.getBrPr020Info(reqVo);
    	
    	rvo.put("sFlagReFilteringNo", "Y");
		rvo = brPr020Service.getDraftFilteringWordInfo(rvo);
    	
    	mav.setViewName("sitims/mi/br/pr/020/br_pr_020_view");
    	
    	mav.addObject("introText", brEr040Service.makeIntroText());
    	mav.addObject("i_sActionFlag", "V");
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rvo", rvo);
    	
    	return mav;
	}
    
    /**
     * 실증자료 등록 상세
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_020_review_view.do")
	public ModelAndView br_pr_020_review_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CmMap 			rVo		= 	new CmMap();
    	ModelAndView	mav		=	new ModelAndView();
    	mav.setViewName("sitims/mi/br/pr/020/br_pr_020_review_view");
    	
    	//1.실증자료항목
    	this.getCmSubCodeList(mav, "attachType", "ATTACH_TYPE");
    	
    	//2.실증자료항목 코멘트 리스트
    	List<CmMap> commentList	=	new ArrayList<CmMap>();
    	commentList				=	brPr020Service.getReviewContentList(reqVo);
    	if (commentList != null) {
    		mav.addObject("commentList", commentList);
    	}
    	
    	//3.제품 단일성분
    	//내용물 수
    	List<CmMap> partList		=	brPw020Service.selectPartnoList(reqVo);	
		int			partListSize	=	partList == null ? 0 : partList.size();
    	
		if(partListSize > 0) {
			reqVo.put("i_sPartTab", "Y");
			reqVo.putDefault("i_sPartNo", partList.get(0).getString("n_part_no"));
			
			List<CmMap> singleList = brPw020Service.selectOdmConSingleList(reqVo);
			mav.addObject("singleList", singleList);
			
			int listSize = singleList == null ? 0 : singleList.size();
			
			if(listSize > 0) {
				rVo.put("v_inactive_all", singleList.get(0).getString("v_inactive_all"));
			}
			
			//단일선분 Function List
			List<CmMap> fcList= brPw020Service.selectConpFunctionList(reqVo);
			mav.addObject("fcList", fcList);
			
			//알러젠 List
			List<CmMap> allergenList = brpr012service.selectAllergenIngrtList(reqVo);
			mav.addObject("allergenList", allergenList);
			
			//향료 List
			List<CmMap> fragranceList = brpr012service.selectOdmFragranceList(reqVo);
			mav.addObject("fragranceList", fragranceList);
		}
		
		mav.addObject("reqVo",reqVo);
		mav.addObject("rVo",rVo);
		mav.addObject("partList", partList);
		
    	return mav;
	}
    
    /**
     * 실증자료 등록 등록
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_020_review_reg.do")
	public ModelAndView br_pr_020_review_reg (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CmMap			rVo		= 	new CmMap();
    	ModelAndView	mav		=	new ModelAndView();
    	mav.setViewName("sitims/mi/br/pr/020/br_pr_020_review_reg");
    	mav.addObject("reqVo", reqVo);
    	
    	//1.실증자료항목
    	this.getCmSubCodeList(mav, "attachType", "ATTACH_TYPE");
    	
    	//2.실증자료항목 코멘트 리스트
    	List<CmMap> commentList	=	new ArrayList<CmMap>();
    	commentList	=	brPr020Service.getReviewContentList(reqVo);
    	if (commentList != null) {
    		mav.addObject("commentList",commentList);
    	}
    	
    	//3.제품 단일성분
    	//내용물 수
    	List<CmMap> partList		=	brPw020Service.selectPartnoList(reqVo);	
		int			partListSize	=	partList == null ? 0 : partList.size();
    	
		if(partListSize > 0) {
			reqVo.put("i_sPartTab", "Y");
			reqVo.putDefault("i_sPartNo", partList.get(0).getString("n_part_no"));
			
			List<CmMap> singleList	=	brPw020Service.selectOdmConSingleList(reqVo);
			mav.addObject("singleList", singleList);
			
			int listSize = singleList == null ? 0 : singleList.size();
			
			if(listSize > 0) {
				rVo.put("v_inactive_all", singleList.get(0).getString("v_inactive_all"));
			}
			
			//단일선분 Function List
			List<CmMap> fcList= brPw020Service.selectConpFunctionList(reqVo);
			mav.addObject("fcList", fcList);
			
			//알러젠 List
			List<CmMap> allergenList = brpr012service.selectAllergenIngrtList(reqVo);
			mav.addObject("allergenList", allergenList);
			
			//향료 List
			List<CmMap> fragranceList = brpr012service.selectOdmFragranceList(reqVo);
			mav.addObject("fragranceList", fragranceList);
		}

		mav.addObject("reqVo",reqVo);
		mav.addObject("rVo",rVo);
		mav.addObject("partList", partList);
		
    	return mav;
	}
    
    /**
     * 실증자료 등록 저장
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_020_review_save_ajax.do")
    public ModelAndView br_pr_020_review_save_ajax(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	reqVo.put("i_sRegDtm", CmFunction.getPointDate(CmFunction.getRegDate8()));
		String actionFlag = reqVo.getString("i_sActionFlag");
		
		brPr020Service.brPr020ReviewReg(reqVo);				
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
    }
    
    /**
     * 문안등록 상세 - 원화검토
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pr_020_ori_view.do")
	public ModelAndView br_pr_020_ori_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView	mav =	new ModelAndView();
    	mav.setViewName("sitims/mi/br/pr/020/br_pr_020_ori_view");
    	CmFunction.setSessionValue(request, reqVo);

    	CmMap 		rvo 	= 	brPr020Service.getBrPr020Info(reqVo);
    	String		status	=	rvo.getString("v_ad_req_status_cd");
    	
    	//원화등록 완료, 반려, 완료시에만 원화의견
    	if(status.equals("AD_REQ_STATUS06") || status.equals("AD_REQ_STATUS07") || status.equals("AD_REQ_STATUS08") || status.equals("AD_REQ_STATUS09")) {
    		if(CmFunction.isEmpty(reqVo.getString("i_sProductCd"))) {
    			reqVo.put("i_sProductCd", rvo.getString("v_product_cd"));
    		}
    		mav.addObject("msgList",brPr020Service.brPr020OpinionList(reqVo));
    	}
    	
    	mav.addObject("i_sActionFlag", "V");
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rvo", rvo);
    	
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
    @RequestMapping(value="/br_pr_020_ori_reg.do")
	public ModelAndView br_pr_020_ori_reg (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rvo = brPr020Service.getBrPr020Info(reqVo);
    	
    	//기능성 아닌 경우
		this.getCmSubCodeList(mav, "funcType", "FUNC_TYPE");	//유형별 구분
		this.getCmSubCodeList(mav, "funcIngri", "FUNC_INGRI");	//성분별 구분
		this.getCmSubCodeList(mav, "caution", "PRD_CAUTION");	//사용시 주의사항
		
		reqVo.put("i_sRecordCd", rvo.getString("v_record_id"));
		reqVo.put("i_sProductCd", rvo.getString("v_product_cd"));
		
		
		if(rvo.getString("v_func_yn").equals("N")) {
			CmMap nfVo = brPr020Service.getNonFuncData(reqVo);
			mav.addObject("nfVo",nfVo);
		}
    	
    	mav.setViewName("sitims/mi/br/pr/020/br_pr_020_ori_reg");
    	
    	mav.addObject("i_sActionFlag", "V");
    	mav.addObject("reqVo", reqVo);
    	mav.addObject("rvo", rvo);
    	
    	return mav;
	}
	
	/**
	 * 제품코드 팝업 리스트
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/br_pr_020_prod_list_pop.do")
	public ModelAndView br_pr_020_prod_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/br/pr/020/br_pr_020_prod_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	/**
	 * 제품코드 팝업 리스트 - get data
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getProdPopList.do")
	public ModelAndView getProdPopList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, brPr020Service.getProdPopList(reqVo)); 
	}
	
	/**
	 * 문안검토요청 저장
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("br_pr_020_save.do")
	public ModelAndView br_pr_020_save(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView 	mav		  	= new ModelAndView("jsonView");
		String			actionFlag 	= reqVo.getString("i_sActionFlag");
		String			cancelFlag	= reqVo.getString("i_sCancelFlag");
		CmMap			result		= new CmMap();
		String  		userId		= SessionUtils.getUserNo();
		
		if("R".equals(actionFlag)) {		//등록시
			result = brPr020Service.regBrPr020(reqVo);
		}else if("M".equals(actionFlag)) {	//수정시
			if("Y".equals(cancelFlag)) {
				brPr020Service.changeStatus(reqVo);
			}else {
				brPr020Service.modifyBrPr020(reqVo);
			}
		}
		
		//검토요청시 메일 s
		//메일 보내는사람 : 등록자
		//메일 받는사람    : 지정된 RA + 제품연구원 
		String	status	=	reqVo.getString("i_sStatus");
		
		if(status.equals("AD_REQ_STATUS02") || status.equals("AD_REQ_STATUS03") || status.equals("AD_REQ_STATUS04")) {
			//문안검토 요청시 메일링
			CmMap mailVo = new CmMap();
			
			//컨텐츠에 뿌려줄 내용
    		mailVo.put("i_sAdReqId", actionFlag.equals("R") ? result.getString("i_sAdReqId") : reqVo.getString("i_sAdReqId"));
    		mailVo.put("i_sProductCd",reqVo.getString("i_sProductCd"));
    		mailVo.put("i_sProductNm", reqVo.getString("i_sProductNmMail"));
    		mailVo.put("i_sAdContentRs",reqVo.getString("i_sAdContentRs"));
    		mailVo.put("i_sStatus", status);
			
    		//BM메일 가져오기
			reqVo.put("i_sFlagExcelAll", "Y");
			reqVo.put("i_sUserNo", userId);
			CmMap fromUserInfo	= commonService.getUserInfo(reqVo);	
			String strFromMail	= fromUserInfo.getString("v_email");	//문안등록자(BM) 이메일
			if(!CmFunction.isEmpty(strFromMail)) {
				strFromMail	=	cryptoService.decAES(strFromMail, true);
			}
			
			mailVo.put("i_sFromAddr", strFromMail);
			
			if(status.equals("AD_REQ_STATUS02")) {
				mailVo.put("i_sSubject", "[TIIMS] "+reqVo.getString("v_product_cd")+" 문안검토가 요청되었습니다. ");
				mailVo.put("i_sToAddr", reqVo.getString("i_sRaEmail"));    	
			}else {
				mailVo.put("i_sSubject", "[TIIMS] "+reqVo.getString("v_product_cd") + " 검토요청이 " + (status.equals("AD_REQ_STATUS04") ? "승인": "반려") + " 되었습니다.");
	    		mailVo.put("i_sToAddr", reqVo.getString("i_sBmEmail"));	//BM(등록자 이메일)    	
	    		
			}
			mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pr/020/br_pr_020_ad_req_mail", mailVo));
    		
			//메일로그
			CmMap logVo = new CmMap();
			logVo.put("i_sTitle", mailVo.getString("i_sSubject"));
			logVo.put("i_sContents", mailVo.getString("i_sContents"));
			logVo.put("i_sAdReqId", mailVo.getString("i_sAdReqId"));
			logVo.put("i_sProductCd", mailVo.getString("i_sProductCd"));
			logVo.put("i_sProductNm",mailVo.getString("i_sProductNm"));
			logVo.put("i_sFromUser",userId);
			logVo.put("i_sFromAddr", mailVo.get("i_sFromAddr"));
			logVo.put("i_sRevUserIdAll", mailVo.getString("i_sToAddr"));
			commonService.insertMailLog(logVo);
			
    		this.sendMailwithHtml(mailVo);
		}
		//검토요청시 메일 e
		
//		mav.addObject("reqVo", reqVo);
		return makeJsonResult(mav, "success", "OK", result);
	}
	
	/**
	 * 원화등록요청 저장
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("br_pr_020_ori_save.do")
	public ModelAndView br_pr_020_ori_save(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav  	=	new ModelAndView("jsonView");
		String cancelFlag	=	reqVo.getString("i_sCancelFlag");
		String status		=	reqVo.getString("i_sStatus");
		reqVo.put("i_sUserId", SessionUtils.getUserNo());
		
		if(cancelFlag.equals("Y") || status.equals("AD_REQ_STATUS10")) {	//요청취소,원화발송요청시 스테이터스 변경
			brPr020Service.changeStatus(reqVo);
		}else {
			brPr020Service.reqBrPrOri020(reqVo);
		}
		
		if(reqVo.getString("i_sFuncYn").equals("N")) {
			brPr020Service.regNonFuncData(reqVo);
		}
		
		return makeJsonResult(mav, "success", "OK", reqVo);
	}
	
	/**
	 * 원화등록 추가 요청
	 */
	@RequestMapping("br_pr_020_add_packing_save.do")
	public ModelAndView br_pr_020_add_packing_save(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav  	=	new ModelAndView("jsonView");
		String status		=	reqVo.getString("i_sStatus");
		reqVo.put("i_sUserId", SessionUtils.getUserNo());
		
		brPr020Service.brPrOri020AddPacking(reqVo);
		
		//mav.addObject("reqVo", reqVo);
		return makeJsonResult(mav, "success", "OK", reqVo);
	}
	
	/**
	 * 문안검토요청
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("br_pr_020_get_options_ajax.do")
	public ModelAndView br_pr_020_get_options_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav		=	new ModelAndView();
		List<List<CmMap>> list 	= 	null;
		list					=	brPr020Service.getOptions(reqVo);
		
		if(list != null) {
			return makeJsonResult(mav, "OK", "SUCCESS", list);
		}else {
			return makeJsonResult(mav, "NO", "FAIL", null);
		}
	}
	
	/**
	 * 문안검토등록 기능성데이터 셋팅
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("br_pr_020_get_report_ajax.do")
	public ModelAndView br_pr_020_get_report_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav		=	new ModelAndView();
		CmMap	map				=	new CmMap();
		map						=	brPr020Service.getFuncReport(reqVo);
		
		if(map != null) {
			return makeJsonResult(mav, "OK", "SUCCESS", map);
		}else {
			return makeJsonResult(mav, "ERROR", "FAIL", null);
		}
	}
	
	/**
	 * 문안검토등록 기능성데이터2,3호용  셋팅
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("br_pr_020_get_effect_ajax.do")
	public ModelAndView br_pr_020_get_effect_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav	=	new ModelAndView();
		List<CmMap> list	=	new ArrayList<CmMap>();
		list				=	brPr020Service.getEffectData(reqVo);
		
		if(list != null) {
			return makeJsonResult(mav, "OK", "SUCCESS", list);
		}else {
			return makeJsonResult(mav, "NO", "FAIL", null);
		}
	}
	
	/**
	 * 디자이너 리스트 팝업
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pr_020_designer_list_pop.do")
	public ModelAndView br_pr_020_designer_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	
    	mav = getInitMAV(request,"sitims/views/mi/br/pr/020/br_pr_020_designer_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	/**
	 * 디자이너 리스트 불러오기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getDesignerList.do")
	public ModelAndView getDesignerList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav	= new ModelAndView("jsonView");
		List<CmMap> result	= brPr020Service.getDesignerList(reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, result); 
	}
	
	/**
	 * 외주디자이너 리스트 팝업
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pr_020_odm_designer_list_pop.do")
	public ModelAndView br_pr_020_odm_designer_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	
    	mav = getInitMAV(request,"sitims/views/mi/br/pr/020/br_pr_020_odm_designer_list_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	/**
	 * 외주디자이너 리스트 불러오기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getOdmDesignerList.do")
	public ModelAndView getOdmDesignerList(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav	= new ModelAndView("jsonView");
		List<CmMap> result	= brPr020Service.getOdmDesignerList(reqVo);
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, result); 
	}

	/**
	 * 문안검토등록(BM) 엑셀
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pr_020_list_excel.do")
	public ModelAndView br_pr_020_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav			=	new	ModelAndView("excelListView");
		int				recordCnt	=	0;
		List<CmMap> 	list		=	null;
		
		reqVo.put("i_sStDate", reqVo.getString("i_sStDate").replace("-", ""));
		reqVo.put("i_sEnDate", reqVo.getString("i_sEnDate").replace("-", ""));
		
		if(!"".equals(reqVo.getString("i_sStDate")) && "".equals(reqVo.getString("i_sEnDate"))){
			reqVo.put("i_sEnDate", CmFunction.getTodayString("yyyyMMdd"));
		}

		recordCnt	=	brPr020Service.getAcceptCount(reqVo);
		
		if (recordCnt > 0) {
			this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
			list	=	brPr020Service.getAcceptList(reqVo);
			mav.addObject("list", list);
		}
		
		String excelFileNm		=	reqVo.getString("i_sExcelFileNm");
		
		// Title
		String[] titleArray		=	{
			"No"	
			, "문서번호"
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
			"n_num"
			, "v_ad_req_id"
			, "v_title"
			, "BRAND_NM"
			, "v_product_cd"
			, "VENDOR_ID"
			, "v_reg_user_nm"
			, "v_reg_dtm_ori"
			, "v_ra_nm"
			, "STATUS"
		};

		mav.addObject("excelFileName"	, CmFunction.isEmpty(excelFileNm)? "문안검토목록(BM)" : excelFileNm);
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
	
	/**
	 * 원화발송 완료제품 체크
	 */
	@RequestMapping(value="/br_pr_020_ori_complete_check_ajax.do")
	public ModelAndView br_pr_020_ori_complete_check_ajax(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav	=	new ModelAndView();
		int 		count	=	0;
		count	=	brPr020Service.brPr020oriCompleteCheck(reqVo);
		
		return makeJsonResult(mav, "OK", "SUCCESS", String.valueOf(count));
	}
	
	/**
	 * 원화검토 의견등록
	 */
	@RequestMapping(value="/br_pr_020_ori_opinion_reg_ajax.do")
	public ModelAndView br_pr_020_ori_opinion_reg_ajax(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav	=	new ModelAndView();
//    	Map<String, String> userInfo = (Map<String, String>) SessionUtils.getAttribute(userSessionNm);
//    	reqVo.put("i_sRegUserId", userInfo.get("LOGIN_NM"));
    	reqVo.put("i_sRegUserId", SessionUtils.getUserNo());

		//의견등록 클릭시 인서트로직
		brPr020Service.brPr020OpinionReg(reqVo);
		
		//원화 전체 승인여부 가져오기
		//reqVo.put("picCheck",brPr020Service.getPicCheck(reqVo));
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
	}
		
	/**
	 * 원화검토 의견 수정
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pr_020_opinion_modify_pop.do")
	public ModelAndView br_pr_020_opinion_modify_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = getInitMAV(request,"sitims/views/mi/br/pr/020/br_pr_020_opinion_modify_pop");
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}
	
	/**
	 * 원화검토 의견 수정 저장
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pr_020_opinion_modify_save.do")
	public ModelAndView br_pr_020_opinion_modify_save (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav	=	new ModelAndView();
    	reqVo.put("i_sRegUserId", SessionUtils.getUserNo());
		//의견등록 클릭시 인서트로직
		brPr020Service.brPr020OpinionModify(reqVo);
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
	}
	
	/**
	 * 원화검토 의견 삭제
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pr_020_ori_opinion_delete_ajax.do")
	public ModelAndView br_pr_020_ori_opinion_delete_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav	=	new ModelAndView();
    	reqVo.put("i_sRegUserId", SessionUtils.getUserNo());
		//의견등록 클릭시 인서트로직
		brPr020Service.brPr020OpinionDelete(reqVo);
		
		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
	}
	
	@RequestMapping(path = "/br_pr_020_ori_caution_ajax.do")
	public ModelAndView br_pr_020_caution_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("jsonView");
		//추가 주의사항
		reqVo.put("i_sMstCode", "PRD_CAUTION");
		reqVo.put("i_sBuffer1", CmFunction.getStringValue(reqVo.getString("buf1")));
		reqVo.put("i_sBuffer2", CmFunction.getStringValue(reqVo.getString("buf2")));
		reqVo.put("prdCaution", commonService.getCmSubCodeList(reqVo).get(0).getString("COMM_CD_DESC"));
		
		return this.makeJsonResult(mav, "SUCCESS", "SUCCESS", reqVo);
	}
	
	/**
	 * 용법용량 리스트 팝업
	 */
	@RequestMapping(path = "/br_pr_020_howto_pop.do")
	public ModelAndView br_pr_020_howto_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("sitims/mi/br/pr/020/br_pr_020_howto_pop");
		List<CmMap> popupList = brPr020Service.getHowtoList(reqVo);
		mav.addObject("popupList", popupList);
		mav.addObject("reqVo", reqVo);
		
		return mav;
	}
}
