package com.shinsegae_inc.sitims.mi.br.pw.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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
import com.shinsegae_inc.sitims.mi.br.pw.service.BrPw020Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jxls.transformer.XLSTransformer;

@Controller
@RequestMapping(value="/br/pw/020/*")
@SuppressWarnings({"rawtypes", "PMD.ExcessiveClassLength"})
public class BrPw020Controller extends CommonController{	
	@Autowired
	private transient ResourceLoader	resourceLoader;
	@Autowired
	private transient BrPw020Service brPw020Service; 
	@Autowired
	private transient BrPr010Service brpr010service; 
	@Autowired
	private transient BrPr012Service brpr012service;
	
	/**
	 * 수출검토 리스트화면
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav =  getInitMAV(request,"sitims/views/mi/br/pw/020/br_pw_020_list");
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
	 * 수출검토 조회 리스트
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="/selectList.do")
    public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, brPw020Service.selectBrPw020List(reqVo));
    }
    
    /**
     * 수출검토 제품정보 화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t1_view.do")
	public ModelAndView br_pr_012_t1 (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
    	
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t1_view");
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
     * 수출검토 기능성 화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t3_view.do")
	public ModelAndView br_pw_020_t3_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rvo = brpr012service.getBrPr012Info(reqVo);
    	
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t3_view");
    	reqVo.put("i_sBigTab", "FUNC");
    	reqVo.put("i_sFuncYn", rvo.getString("v_func_yn"));
    	mav.addObject("rvo", rvo);
    	
    	mav.addObject("reqVo", reqVo);
    	
    	return mav;
	}
    
    /**
     * 수출검토 제품 fomula 정보
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_fomula_view.do")
	public ModelAndView br_pw_020_t2_fomula_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
    	
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_fomula_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab1");
    	
    	if(CmFunction.isEmpty(reqVo.getString("i_sDivision"))) {
    		reqVo.put("i_sDivision","SINGLE");
    	}
    	if(rVo != null && !rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
    	}
    	List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
		int partListSize = partList == null ? 0 : partList.size();

		if(partListSize > 0) {
			reqVo.put("i_sPartTab", "Y");
			//reqVo.putDefault("i_sPartNo", partList.get(0).getString("n_part_no"));
			reqVo.putDefault("i_sPartNo", "1");
			if("COMPLEX".equals(reqVo.getString("i_sDivision"))) {
				List<CmMap> conList = brPw020Service.selectOdmPifViewForChina(reqVo);				
				mav.addObject("conList", conList);				
				//mav.addObject("conList", null);
				
				List<CmMap> fcList= brPw020Service.selectRawFunctionList(reqVo);
				mav.addObject("fcList", fcList);
			} else {
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
			}
			
			List<CmMap> claimList = brPw020Service.selectOdmClaimEffectList(reqVo);
			mav.addObject("claimList", claimList);
			
			reqVo.put("i_iVsn", rVo.getString("n_vsn"));
			List<CmMap> fragranceList = brpr012service.selectOdmFragranceList(reqVo);
			mav.addObject("fragranceList", fragranceList);
			
			reqVo.put("i_iVerSeq", rVo.getString("n_vsn"));
			reqVo.put("i_sCategory1", rVo.getString("v_category1"));
			reqVo.put("i_sCategory2", rVo.getString("v_category2"));
			List<CmMap> falcList = brPw020Service.selectOdmRequireFileAndLimitConList(reqVo);
			mav.addObject("falcList", falcList);
			
		}
		//mav.addObject("ContentCdList", pifOdmRequestService.selectOdmContentCodeList(reqVo));		
		
    	mav.addObject("reqVo", reqVo);    	
    	mav.addObject("prodList", brpr010service.getBrPr010ProdList(reqVo));    	
    	mav.addObject("rVo", rVo);
    	mav.addObject("partList", partList);
    	
    	
    	return mav;
	}
    
    /**
     * 수출검토 제품 복합 Fomula
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_complex_fomula_view.do")
	public ModelAndView br_pw_020_t2_complex_fomula_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	reqVo.putDefault("i_sDivision", "COMPLEX");
    	return this.br_pw_020_t2_fomula_view(reqVo, request, response);
	}
	
    /**
     * Fomula CasNo 현행화
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value="/br_pw_020_cas_update.do")
	public ModelAndView br_pw_020_cas_update (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("jsonView");
		brPw020Service.updateCaNo(reqVo);		
		return this.makeJsonResult(mav, "succ", "수정 되었습니다.", null);
	}
	
	/**
	 * 수출검토 Func 수정
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/br_pw_020_modify_func.do")
	public ModelAndView br_pw_020_modify_func (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("jsonView");
		brPw020Service.updateRawFunction(reqVo);
		return this.makeJsonResult(mav, "succ", "수정 되었습니다.", null);
	}
	
	/**
	 * 수출검토 성분검토 화면
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value="/br_pw_020_t2_ingrt_check_view.do")
	public ModelAndView br_pw_020_t2_ingrt_check_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_ingrt_check_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab1");
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);

		if(rVo != null) {
	    	List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
			int partLen = partList == null ? 0 : partList.size();

			if(partLen > 0) {
				reqVo.putDefault("i_sPartNo", partList.get(0).getString("n_part_no"));
			}
			
			List<CmMap> ingrtList = null;
			ingrtList = brPw020Service.selectIngrtCheckList(reqVo);
			mav.addObject("ingrtList", ingrtList);
			
			int ingrtLen = ingrtList == null ? 0 : ingrtList.size();
			
			if(ingrtLen > 0) {
				rVo.put("v_inactive_all", ingrtList.get(0).getString("v_inactive_all"));
			}
	    	if(!rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
	    		reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
	    	}
			
			List<CmMap> ingrtSubList = null;
			ingrtSubList = brPw020Service.selectIngrtCheckSubList(reqVo);
			mav.addObject("ingrtSubList", ingrtSubList);
		}
		mav.addObject("rVo", rVo);
		mav.addObject("reqVo", reqVo);
		
    	return mav;    
    }
    /**
     * 수출검토 복합공정도
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_complex_process_view.do")
	public ModelAndView br_pw_020_t2_complex_process_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	reqVo.putDefault("i_sDivision", "COMPLEX");
    	reqVo.putDefault("i_sRecipeType", "C");
    	return this.br_pw_020_t2_process_view(reqVo, request, response);    	
    }
    /**
     * 수출검토 공정도
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_process_view.do")
	public ModelAndView br_pw_020_t2_process_view (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_process_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab2");
    	if(CmFunction.isEmpty(reqVo.getString("i_sDivision"))) {
    		reqVo.put("i_sDivision","SINGLE");
    	}
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);

    	if(rVo != null ) {
//    		if("Y".equals(rVo.getString("v_completeyn"))){
			List<CmMap> listSaved = new ArrayList<CmMap>();
			List<CmMap> folderList = new ArrayList<CmMap>();
			
			reqVo.putDefault("i_sPartNo", "1");
			// 처방 타입 S : 단일 처방 , C : 복합처방
			reqVo.putDefault("i_sRecipeType", "S");

	    	if(!rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
	    		reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
	    	}
			
			String recipeType = reqVo.getString("i_sRecipeType");
			boolean recipeFlag = "S".equals(recipeType) ? true : false;
			reqVo.put("i_iVerSeq", rVo.getString("n_vsn"));
			CmMap processVo = brPw020Service.selectProcessMstInfo(reqVo);
			if(processVo != null){
				reqVo.put("i_sConDiv",recipeFlag ? "V_CON_CD" : "V_RAW_CD");
				listSaved = brPw020Service.getSavedConListForPif(reqVo);
				folderList = brPw020Service.selectProcessFolderInfo(reqVo);			
			}			

			mav.addObject("processVo", processVo);
			mav.addObject("list_saved", listSaved);
			mav.addObject("partList", brPw020Service.selectPartnoList(reqVo)); // PartNo List
			mav.addObject("folderList", folderList); // folder List
//			}
    	}
		mav.addObject("rVo", rVo);
		mav.addObject("reqVo", reqVo);
		
    	return mav;    
    }
    /**
     * 수출검토 전성분표
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_component_view.do")
	public ModelAndView br_pw_020_t2_component_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_component_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab3");
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
    	if(rVo != null && !rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
    	}
		if(rVo != null ){ //&& "Y".equals(rVo.getString("v_complete_yn"))
			List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
			reqVo.put("i_iVerSeq", rVo.getString("n_vsn"));
			reqVo.putDefault("i_sPartNo", "1");
			
			reqVo.put("i_sLeaveonYn", rVo.getString("v_part_leaveon_yn"));
			reqVo.put("i_sMatrcd", rVo.getString("v_product_cd"));
			reqVo.put("i_iVerSeq", rVo.getString("n_vsn"));
			reqVo.put("i_sSortCol", "N_REAL_RCONT");
			reqVo.put("i_sGubun", "EXP");
			mav.addObject("ingrtList", brPw020Service.getIngrtList2(reqVo)); // 내용물 리스트
			mav.addObject("partList", partList); // PartNo List
			mav.addObject("IngrtMemoVo",brPw020Service.getIngrtMartmemo(reqVo));
			mav.addObject("allergenList", brPw020Service.selectAllergenRawList(reqVo));
			mav.addObject("rVo", rVo);
		}
		mav.addObject("rVo", rVo);
		mav.addObject("reqVo", reqVo);
		
    	return mav;    
    }
    
    /**
     * 수출검토 Claim Report
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_claim_view.do")
	public ModelAndView br_pw_020_t2_claim_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_claim_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab4");
		
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
		mav.addObject("rVo", rVo);
		if(rVo != null) {
			reqVo.put("i_sProductCd", rVo.getString("v_product_cd"));
			reqVo.put("i_iVerSeq", rVo.getString("n_vsn"));
			if(!rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
				reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
			}
			
			List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
			int partLen = partList == null ? 0 : partList.size();
			
			mav.addObject("partList", partList);
			
			if(partLen > 0) {
				reqVo.putDefault("i_sClaimPartNo", partList.get(0).getString("n_part_no"));
				
				List<CmMap> claimList = brPw020Service.selectClaimEffectList(reqVo);
				List<CmMap> supportList = brPw020Service.selectClaimSupportList(reqVo);
				
				CmMap vo = new CmMap();
				CmMap oldVo = new CmMap();
				List<CmMap> tempList = new ArrayList<CmMap>();
				
				int supportSize = supportList == null ? 0 : supportList.size();
				
				if(supportSize > 0) {
					for(int i=0; i<supportSize; i++) {
						vo = supportList.get(i);
						
						if(!oldVo.getString("v_claim_effect").equals(vo.getString("v_claim_effect")) || "ETC".equals(oldVo.getString("v_claim_effect"))) {
							tempList.add(vo);
						}
						
						oldVo = vo;
					}
				}
				
				List<CmMap> reportList = brPw020Service.selectClaimReportNo(reqVo);
				mav.addObject("reportList", reportList);
				mav.addObject("supportList", tempList);
				mav.addObject("claimList", claimList);
			}
		}
		mav.addObject("reqVo", reqVo);
		
    	return mav;    
    }
    
    /**
     * 수출검토 Spec
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_spec_view.do")
	public ModelAndView br_pw_020_t2_spec_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_spec_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab5");
				
		if(CmFunction.isEmpty(reqVo.getString("i_sPartNo"))){
			reqVo.put("i_sPartNo", "1");
		}
		List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
    	mav.addObject("partList", partList);
    	CmMap rVo = brpr012service.getBrPr012Info(reqVo);
    	if(rVo != null) {
			reqVo.put("i_sProductCd", rVo.getString("v_product_cd"));
			reqVo.put("i_iVerSeq",  rVo.getString("n_vsn"));
			if(!rVo.isEmpty() && CmFunction.isNotEmpty(rVo.getString("v_func_yn"))) {
				reqVo.put("i_sFuncYn", rVo.getString("v_func_yn"));
			}			
			List<CmMap> specList = brPw020Service.selectSpecList(reqVo);
			mav.addObject("specList", specList);
			mav.addObject("partNoMax",brPw020Service.selectPartNoCount(reqVo));		
    	}
    	mav.addObject("rVo", rVo);
		mav.addObject("reqVo", reqVo);
		
    	return mav;    
    }
    
    /**
     * 수출검토 msds
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/br_pw_020_t2_msds_view.do")
	public ModelAndView br_pw_020_t2_msds_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_msds_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab6");
				
		if(CmFunction.isEmpty(reqVo.getString("i_sPartNo"))){
			reqVo.put("i_sPartNo", "1");
		}
		
    	CmMap tempVo = brpr012service.getBrPr012Info(reqVo);

    	if(tempVo != null) {
    		reqVo.put("i_sProductCd", tempVo.getString("v_product_cd"));
    		reqVo.put("i_iVerSeq", 1);
    		if(!tempVo.isEmpty() && CmFunction.isNotEmpty(tempVo.getString("v_func_yn"))) {
        		reqVo.put("i_sFuncYn", tempVo.getString("v_func_yn"));
        	}
    		//SECTION 항목
    		reqVo.put("uClassCd", "S000001");
    		reqVo.put("v_sub_flag", "N");
    		reqVo.put("i_sPartno", 1);
    		List<CmMap> sectionList = brpr012service.getSectionList(reqVo);
    		mav.addObject("sectionList", sectionList);
    		
    		reqVo.put("v_sub_flag", "Y");
    		List<CmMap> sectionListSub = brpr012service.getSectionList(reqVo);
    		mav.addObject("sectionList_sub", sectionListSub);    		
    	}
		
		
//			CmMap rVo = brpr012service.selectOdmMsds(reqVo);//flash point +충진 가스
//			mav.addObject("rVo", rVo);
		
		mav.addObject("reqVo", reqVo);
		
    	return mav;    
    }
    
    //수출검토 안정성
    @RequestMapping(value="/br_pw_020_t2_stability_view.do")
	public ModelAndView br_pw_020_t2_stability_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_stability_view");
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab7");
				
		if(CmFunction.isEmpty(reqVo.getString("i_sPartNo"))){
			reqVo.put("i_sPartNo", "1");
		}

		mav.addObject("sCondition"	, commonService.getCmSubCodeList("STABILITY_CONDI"));
		mav.addObject("sAnalysis"	, commonService.getCmSubCodeList("STABILITY_ANAL"));

		CmMap prodInfo = brpr012service.getBrPr012Info(reqVo);

    	if(prodInfo != null && !prodInfo.isEmpty() && CmFunction.isNotEmpty(prodInfo.getString("v_func_yn"))) {
    		reqVo.put("i_sFuncYn", prodInfo.getString("v_func_yn"));
    	}
		reqVo.put("i_iVerSeq", 1);

		mav.addObject("partList", brPw020Service.selectPartnoList(reqVo));

		/*
		if(prodInfo != null) {
			if("Y".equals(prodInfo.getString("v_importyn"))) {
				reqVo.put("i_sPk1", prodInfo.getString("v_product_cd"));
			} else {
				reqVo.put("i_sPk1", prodInfo.getString("v_vendor_id"));
			}
			
			reqVo.put("i_sCompanyCd", prodInfo.getString("v_vendor_id"));
			CmMap userInfo = brpr012service.selectCompanyInfo(reqVo);
			
			mav.addObject("userInfo", userInfo);
		} 
		*/
		
		List<CmMap>	list	= brPw020Service.selectProductStabilityInfo(reqVo);

		if (list.size() > 0) {
			prodInfo.put("v_test_date", list.get(0).get("v_test_date"));
		}

		mav.addObject("list", list);
		mav.addObject("prodInfo", prodInfo);
		
		mav.addObject("reqVo", reqVo);
		
		return mav;
    }
    
    /**
     * 수출검토 검토문서 화면
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/br_pw_020_t2_document_view.do")
	public ModelAndView br_pw_020_t2_document_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_document_view");
    	CmFunction.setSessionValue(request, reqVo);
    	reqVo.put("i_sBigTab", "EXP");
    	reqVo.put("i_sTab", "tab8");
    	reqVo.put("i_sExpDiv", "all");
		//if(reqVo.getString("s_groupid").indexOf("S000258") == -1 && reqVo.getString("s_groupid").indexOf("S000129") == -1 && !"Y".equals(reqVo.getString("s_sysadmin_yn"))) {
		//if("ADMIN".equals(reqVo.getString("s_groupid"))) {
		if(StringUtils.isNotEmpty(reqVo.getString("s_groupid")) && reqVo.getString("s_groupid").indexOf("ADMIN") > -1) {
			//String expCompleteDoc = brPw020Service.selectExpCompleteDocList(reqVo);
			
			//mav.addObject("expCompleteDoc", expCompleteDoc);
			reqVo.put("i_sAuthFlag", "Y");			
	    	reqVo.put("s_sysadmin_yn", "Y");		
		} else {
			reqVo.put("i_sAuthFlag", "N");
		}
		
		this.getCmSubCodeList(mav, "subExpList", "ODM_EXPORT2");			
		CmMap rvo = brpr012service.getBrPr012Info(reqVo);
		
		if(rvo != null) {
			reqVo.put("i_sMatrcd", rvo.getString("v_product_cd"));
			reqVo.put("i_iVerSeq", rvo.getString("n_vsn"));
			List<CmMap> partList = brPw020Service.selectPartnoList(reqVo);
			mav.addObject("partList", partList);

	    	if(rvo != null && !rvo.isEmpty() && CmFunction.isNotEmpty(rvo.getString("v_func_yn"))) {
	    		reqVo.put("i_sFuncYn", rvo.getString("v_func_yn"));
	    	}
			
			//mav.addObject("revFileVo", brPw020Service.selectOdmReceiveFile(reqVo));
			int partListSize = partList == null ? 0 : partList.size();
			
			if(partListSize > 0) {
				reqVo.putDefault("i_sPartNo", partList.get(0).getString("n_part_no"));
			}
			ArrayList<String> arrStr = new ArrayList<String>();
			arrStr.add("CN");
			reqVo.put("i_arrStr", arrStr);
			mav.addObject("cnRaVo", brPw020Service.getRaIds(reqVo));
			mav.addObject("nationReviewList", brPw020Service.getNationReviewList(reqVo));		
			mav.addObject("rqInfo", brPw020Service.getProdMyRq(reqVo));
			mav.addObject("reqModiList", commonService.getCmSubCodeList("DOC_MODI_LIST"));
			mav.addObject("reviewStList", commonService.getCmSubCodeList("REVIEW_STATUS"));
			reqVo.put("i_sProductCode", rvo.getString("v_product_cd"));
			//String authFlag = brPw020Service.getPifAuthFlag(reqVo);
			//reqVo.put("i_sGmBmAuth", authFlag);
			
			mav.addObject("rvo", rvo);
		}
		
		List<CmMap> docList = commonService.getCmSubCodeList("FILE_REQ",1,"GL");
		mav.addObject("docList", docList);
		mav.addObject("cmDocList", commonService.getCmSubCodeList("FILE_REQ",1,"CM"));
		reqVo.put("i_sRecordId", reqVo.getString("i_sRecordId"));
			
		
		mav.addObject("reqVo", reqVo);
		return mav;
    }
    
    /**
     * 국가별 검토 문구
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getNationMessage.do")
    public ModelAndView getNationMessage(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	return this.makeJsonDhtmlxResult(mav, "OK", "success", brPw020Service.getNationMessage(reqVo));
    }

    /**
     * 국가별 검토 AJAX
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getNationReviewList.do")
    public ModelAndView getNationReviewList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return this.makeJsonDhtmlxResult(new ModelAndView("jsonView"), "OK", "success", brPw020Service.getNationReviewList(reqVo));
    }

    /**
     * 수출검토 공정도 트리 Data
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getProcessTreeData.do")
    public ModelAndView getProcessTreeData(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		String recipeType = reqVo.getString("i_sRecipeType");
		boolean recipeFlag = "S".equals(recipeType) ? true : false;
		
		CmMap processVo = brPw020Service.selectProcessMstInfo(reqVo);
		List<CmMap> listSaved = new ArrayList<CmMap>();
		List<CmMap> folderList = new ArrayList<CmMap>();
		if(processVo != null){
			reqVo.put("i_sConDiv",recipeFlag ? "V_CON_CD" : "V_RAW_CD");
			listSaved = brPw020Service.getSavedConListForPif(reqVo);
			folderList = brPw020Service.selectProcessFolderInfo(reqVo);			
		}			
    	return this.makeJsonTreeResult(mav, "OK", "success", folderList,listSaved,"n_prc_part_seq");
    }
    /**
     * 수출검토 health_report pdf문서
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping("/doc/br_pw_020_doc_part1_health_report.do")
	public ModelAndView br_pw_020_doc_part1_health_report (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object>	parameterMap 	=	new HashMap<String,Object>();
		List<CmMap>			asnList			=	new ArrayList<CmMap>();
		CmMap				rpVo			=	new CmMap();
		CmMap				productInfo		=	new CmMap();
		String				productNm		=	"";
		String				productCd		=	"";
		String				date			=	"";
		String				mav				=	"pifDocPart1HealthReport1";
		SimpleDateFormat	dateFormat				=	new SimpleDateFormat("yyyymmdd",Locale.KOREAN);
		
		parameterMap.put("jrxmlPath", "jasper/br_pw_020_doc_health.jrxml");
		parameterMap.put("downloadType", "pdf");
		parameterMap.put("outputYn", reqVo.getString("outputYn"));
		
		
		date = CmFunction.getUsDate1(CmFunction.getToday());
		productInfo = brpr012service.getBrPr012Info(reqVo);
		productNm = productInfo.getString("v_product_nm_en");
		productCd = productInfo.getString("v_product_cd");
		
		if(!"".equals(reqVo.getString("i_sAsnExp"))){
			reqVo.put("i_sMstCode", "ODM_EXPORT2");
			reqVo.put("i_sSubCode", reqVo.getString("i_sAsnExp"));
			
			List<CmMap> subCodelist = commonService.getCmSubCodeList(reqVo);
			String asnExp = subCodelist.get(0).getString("v_sub_codenm_en");
			
			CmMap temp = new CmMap();
			temp.put("v_tbl_val", asnExp);
			temp.put("v_tbl_title", "Countries where product being sold");
			asnList.add(temp);
			
			temp = new CmMap();
			temp.put("v_tbl_val", "0");
			temp.put("v_tbl_title", "Total Number of Undesirable Health Effect");
			asnList.add(temp);
			rpVo.put("v_asn_exp", asnExp+".");
			mav = "pifDocPart1HealthReport2";
			parameterMap.put("jrxmlPath", "jasper/br_pw_020_doc_health2.jrxml");
		}
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		if("Y".equals(productInfo.getString("v_import_yn"))){
			reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo == null){
			companyInfo = new CmMap();
		}
		
		rpVo.put("now_date", date);
		rpVo.put("v_product_cd", productCd);
		rpVo.put("v_product_nm_en", productNm);
		rpVo.put("v_addr1", productInfo.getString("v_company_addr1"));
		rpVo.put("v_addr2", productInfo.getString("v_company_addr2"));
		rpVo.put("v_addr3", productInfo.getString("v_company_addr3"));
		rpVo.put("v_cpnm", productInfo.getString("v_vendor_nm"));
		rpVo.put("v_phone_num", "Tel : "+companyInfo.getString("v_phone_no"));
		rpVo.put("v_fax_num", "Fax : "+companyInfo.getString("v_fax"));
		rpVo.put("v_manager_nm_en", productInfo.getString("v_repr_usernm_en"));
		
		rpVo.put("v_no_img_flag", "Y");

		if(companyInfo.getString("v_logo").isEmpty()){
			rpVo.put("v_logo", "jasper/images/no_logo.jpg");
		}else{
			rpVo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
		}
		
		if(companyInfo.getString("v_sign").isEmpty()){
			rpVo.put("v_sign_flag", "N");
		}else{
			rpVo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		HttpSession  session = request.getSession();
		session.setAttribute("S_WORD_FILE", productCd + "_" + productNm.trim() + "_" + "UndesirableHealthEffectsSummary" + "_" + dateFormat.format(new Date()));
		reqVo.put("pdfFileName", productCd + "_" + productNm.trim() + "_" + "UndesirableHealthEffectsSummary" + "_" + dateFormat.format(new Date()));
		if(!"Y".equals(reqVo.getString("outputYn"))) {
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/br_pw_020_doc_part1_health_report.do",productCd + "_" + productNm.trim() + "_" + "UndesirableHealthEffectsSummary" + "_" + dateFormat.format(new Date()),ipAddr,"PDF");
		}
		JRDataSource datasource  	= CmFunction.getDataSource(rpVo);
		JRDataSource datasourcelist  	= CmFunction.getDataSource(asnList);
		parameterMap.put("datasource", datasource);
		parameterMap.put("JasperCustomSubReportDatasource", datasourcelist);
		
		
		return new ModelAndView("jasperReportView", parameterMap);
	}
	
	/**
	 * Health Report 미리보기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/br_pw_020_doc_health_pop.do")
	public ModelAndView br_pw_020_doc_health_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_doc_health_pop");
		
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		if("Y".equals(productInfo.getString("v_import_yn"))){
			reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo"))){
				reqVo.put("i_sLogo_flag", "N");
				companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign"))){
				reqVo.put("i_sSign_flag", "N");
				companyInfo.put("v_sign", "no_sign2.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			companyInfo.put("v_logo", "no_logo2.jpg");
			companyInfo.put("v_sign", "no_sign2.jpg");
		}
		
		String date = CmFunction.getUsDate1(CmFunction.getToday());
		reqVo.put("v_now_date", date);
		
		if(!"".equals(reqVo.getString("i_sAsnExp"))){
			this.getCmSubCodeList(mav, "aseanExpSub", "ODM_EXPORT2", reqVo.getString("i_sAsnExp"));
		}
		mav.addObject("productInfo", productInfo);
		mav.addObject("companyInfo", companyInfo);
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 성분리스트 미리보기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/br_pw_020_doc_ingredient_pop.do")
	public ModelAndView br_pw_020_doc_ingredient_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_doc_ingredient_pop");
		
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);
		reqVo.put("i_sAllergenOpen", rVo.getString("v_allergen_open"));
		reqVo.put("i_nVerSeq", rVo.getString("n_vsn"));
		reqVo.put("i_sGubun", "EXP");
		CmMap ingredient = brPw020Service.selectIngredient(reqVo);
		mav.addObject("ingredient", ingredient);
		
		return mav;
	}
	/**
	 * CliamReport 미리보기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doc/br_pw_020_doc_claim_pop.do")
	public ModelAndView br_pw_020_doc_claim_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_doc_claim_pop");
				
		String date = CmFunction.getUsDate1(CmFunction.getToday());

		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		productInfo.put("v_partno", reqVo.getString("i_sClaimPartNo"));
		reqVo.put("i_sMatrcd", productInfo.getString("v_product_cd"));
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		if("Y".equals(productInfo.getString("v_import_yn"))){
			reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo"))){
				reqVo.put("i_sLogo_flag", "N");
				companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign"))){
				reqVo.put("i_sSign_flag", "N");
				companyInfo.put("v_sign", "no_sign2.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			companyInfo.put("v_logo", "no_logo2.jpg");
			companyInfo.put("v_sign", "no_sign2.jpg");
		}
		
		List<CmMap> claimList = brPw020Service.selectClaimEffectList(reqVo);
		List<CmMap> supportList = brPw020Service.selectClaimSupportList(reqVo);
		
		CmMap vo = new CmMap();
		CmMap oldVo = new CmMap();
		List<CmMap> tempList = new ArrayList<CmMap>();
		
		int supportSize = supportList == null ? 0 : supportList.size();
		
		if(supportSize > 0) {
			for(int i=0; i<supportSize; i++) {
				vo = supportList.get(i);
				
				if(!oldVo.getString("v_claim_effect").equals(vo.getString("v_claim_effect")) || "ETC".equals(oldVo.getString("v_claim_effect"))) {
					tempList.add(vo);
				}
				oldVo = vo;
			}
		}
		
		List<CmMap> reportList = brPw020Service.selectClaimReportNo(reqVo);
		mav.addObject("reportList", reportList);
		mav.addObject("supportList", tempList);
		mav.addObject("claimList", claimList);
		
		reqVo.put("v_now_date", date);
		
		mav.addObject("productInfo", productInfo);
		mav.addObject("companyInfo", companyInfo);
		
		mav.addObject("reqVo", reqVo);
		
		return mav;
	}
	/**
	 * Claim Report Pdf문서
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("PMD.CyclomaticComplexity")
	@RequestMapping(value="/doc/br_pw_020_doc_part1_claim_report.do")
	public ModelAndView br_pw_020_doc_part1_claim_report(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object>	parameterMap 	=	new HashMap<String,Object>();
		CmMap				rpVo			=	new CmMap();
		String				sDate			=	"";
		SimpleDateFormat	sdf				=	new SimpleDateFormat("yyyymmdd",Locale.KOREAN);
		
	
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		
		String productcd = productInfo.getString("v_product_cd");
		String productnm = productInfo.getString("v_product_nm_en");
		reqVo.put("i_sMatrcd", productcd);
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		if("Y".equals(productInfo.getString("v_import_yn"))){
			reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo == null){
			companyInfo = new CmMap();
		}
		
		sDate	=	CmFunction.getUsDate1(CmFunction.getToday());
		
		List<CmMap> claimList = brPw020Service.selectClaimEffectList(reqVo);
		List<CmMap> supportList = brPw020Service.selectClaimSupportList(reqVo);
		
		CmMap vo = new CmMap();
		CmMap oldVo = new CmMap();
		List<CmMap> tempList = new ArrayList<CmMap>();
		
		int supportSize = supportList == null ? 0 : supportList.size();
		
		if(supportSize > 0) {
			for(int i=0; i<supportSize; i++) {
				vo = supportList.get(i);
				vo.put("v_claim_support", vo.getString("v_claim_support").replace("&#034;","\""));
				
				if(!oldVo.getString("v_claim_effect").equals(vo.getString("v_claim_effect")) || "ETC".equals(oldVo.getString("v_claim_effect"))) {
					tempList.add(vo);
				}
				oldVo = vo;
			}
		}

		List<CmMap> reportList = brPw020Service.selectClaimReportNo(reqVo);
		
		for(int i=0; i < tempList.size(); i++){
			String claimEffect = tempList.get(i).getString("v_claim_effect");
			
			if(!"ETC".equals(claimEffect)){
				for(CmMap repTemp : reportList){
					if(claimEffect.equals(repTemp.getString("v_claim_effect"))){
						tempList.get(i).put("v_claim_reportno", repTemp.getString("v_claim_reportno"));
					}
				}
			}
		}
		
		if(claimList.size() == 0){
			CmMap temp = new CmMap();
			temp.put("v_con_nm_en", "N/A");
			temp.put("v_claim_active_ing", "");
			temp.put("v_rcont", "N/A");
			temp.put("v_claim_txt", "N/A");
			claimList.add(temp); 
		}
		
		if(tempList.size() == 0){
			CmMap temp = new CmMap();
			temp.put("v_claim_txt", "N/A");
			temp.put("v_claim_support", "");
			temp.put("v_claim_reportno", "");
			
			tempList.add(temp);
		}
		
		rpVo.put("now_date", sDate);
		rpVo.put("v_product_cd", productcd);
		rpVo.put("v_product_nm_en", productnm);
		rpVo.put("v_addr1", productInfo.getString("v_company_addr1"));
		rpVo.put("v_addr2", productInfo.getString("v_company_addr2"));
		rpVo.put("v_addr3", productInfo.getString("v_company_addr3"));
		rpVo.put("v_cpnm", productInfo.getString("v_vendor_nm"));
		rpVo.put("v_phone_num", "Tel : "+companyInfo.getString("v_phone_no"));
		rpVo.put("v_fax_num", "Fax : "+companyInfo.getString("v_fax"));
		//rpVo.put("v_manager_nm_en", productInfo.getString("v_repr_usernm_en"));
		
		rpVo.put("v_partno", reqVo.getString("i_sClaimPartNo"));
		
		if(companyInfo.getString("v_logo").isEmpty()){
			rpVo.put("v_logo_flag", "N");
			rpVo.put("v_logo", "jasper/images/no_logo.jpg");
		}else{
			rpVo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
		}
		
		if(companyInfo.getString("v_sign").isEmpty()){
			rpVo.put("v_sign_flag", "N");
		}else{
			rpVo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		JRDataSource datasource  		= CmFunction.getDataSource(rpVo);
		JRDataSource datasourcelist  	= CmFunction.getDataSource(claimList);
		JRDataSource datasourcelist2  	= CmFunction.getDataSource(tempList);
		
		HttpSession  session = request.getSession();
		session.setAttribute("S_WORD_FILE", productcd + "_" + productnm.trim() + "_" + "ClaimSupportSummary" + "_" + sdf.format(new Date()));
		if(!"Y".equals(reqVo.getString("outputYn"))) {
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/br_pw_020_doc_part1_claim_report.do",productcd + "_" + productnm.trim() + "_" + "ClaimSupportSummary" + "_" + sdf.format(new Date()),ipAddr,"PDF");
		}
		parameterMap.put("datasource", datasource);
		parameterMap.put("JasperCustomSubReportDatasource", datasourcelist);
		parameterMap.put("JasperCustomSubReportDatasource2", datasourcelist2);
		
		parameterMap.put("jrxmlPath", "jasper/br_pw_020_doc_claim_support.jrxml");
		parameterMap.put("downloadType", "pdf");
		parameterMap.put("outputYn", reqVo.getString("outputYn"));

		// sub Report
		try {
			parameterMap.put("JasperCustomSubReportLocation", JasperCompileManager.compileReport(
					resourceLoader.getResource("classpath:jasper/br_pw_020_doc_claim_support_subreport1.jrxml").getURI().getPath()));
			parameterMap.put("JasperCustomSubReportLocation2", JasperCompileManager.compileReport(
					resourceLoader.getResource("classpath:jasper/br_pw_020_doc_claim_support_subreport2.jrxml").getURI().getPath()));
		} catch (JRException e) {
			this.errorLogger(e);
		} catch (IOException e) {
			this.errorLogger(e);
		}
	
		return new ModelAndView("jasperReportView", parameterMap);
	}
	/**
	 * fragrance_report Pdf문서
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/br_pw_020_doc_part1_fragrance_report.do")
	public ModelAndView pif_doc_part1_fragrance_report(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object>	parameterMap 	=	new HashMap<String,Object>();
		CmMap				rpVo		=	new CmMap();
		CmMap				productInfo	=	new CmMap();
		StringBuffer		fragranceNm	=	new StringBuffer(1024);
		StringBuffer		fragranceCd	=	new StringBuffer(1024);
		String				date			=	"";
		SimpleDateFormat	sdf				=	new SimpleDateFormat("yyyymmdd",Locale.KOREAN);
		
		date = CmFunction.getUsDate1(CmFunction.getToday());
		
		productInfo = brpr012service.getBrPr012Info(reqVo);
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		reqVo.put("i_sPk1", companyCd);
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo == null){
			companyInfo = new CmMap();
		}
		
		reqVo.put("i_sProductCd", productInfo.getString("v_product_cd"));
		List<CmMap> fragranceList = brPw020Service.selectFragranceList(reqVo);
		
		int fragCnt = fragranceList == null? 0:fragranceList.size();
		
		if(fragCnt > 0){
			for(int i=0; i<fragCnt; i++){
				fragranceCd.append(fragranceList.get(i).getString("v_fragrance")+',');
				fragranceNm.append(fragranceList.get(i).getString("v_fragrance_smell")+',');
			}
			StringBuffer application = new StringBuffer(fragranceList.get(0).getString("v_category"));
			//String application = fragranceList.get(0).getString("v_category");
			String[] temp ;
			for(int i=1; i<fragCnt; i++){
				temp = fragranceList.get(i).getString("v_category").split(",");
				
				for(int j=0; j<temp.length; j++){
					if((application.toString()).indexOf(temp[j]) == -1){
						//application = application + "," + temp[j];
						application.append(',');
						application.append(temp[j]);
					}
				}
			}

			rpVo.put("now_date", date);
			rpVo.put("v_fragrance_nm", fragranceNm.replace(fragranceNm.length()-1, fragranceNm.length(), " ").toString());
			rpVo.put("v_fragrance_cd", fragranceCd.replace(fragranceCd.length()-1, fragranceCd.length(), " ").toString());
			rpVo.put("v_category", application.toString());
		}else{
			rpVo.put("v_fragrance_nm", " ");
			rpVo.put("v_fragrance_cd", " ");
			rpVo.put("v_category", " ");
			rpVo.put("now_date", date);
		}
		
		rpVo.put("v_addr1", productInfo.getString("v_company_addr1"));
		rpVo.put("v_addr2", productInfo.getString("v_company_addr2"));
		rpVo.put("v_addr3", productInfo.getString("v_company_addr3"));
		rpVo.put("v_cpnm", productInfo.getString("v_company_nm_en"));
		rpVo.put("v_phone_num", "Tel : "+companyInfo.getString("v_phone_no"));
		rpVo.put("v_fax_num", "Fax : "+companyInfo.getString("v_fax"));
		rpVo.put("v_manager_nm_en", productInfo.getString("v_repr_usernm_en"));
		
		if(companyInfo.getString("v_logo").isEmpty()){
			rpVo.put("v_logo", "jasper/images/no_logo.jpg");
		}else{
			rpVo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
		}
		
		if(companyInfo.getString("v_sign").isEmpty()){
			rpVo.put("v_sign_flag", "N");
		}else{
			rpVo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		HttpSession  session = request.getSession();
		session.setAttribute("S_WORD_FILE", productInfo.getString("v_product_cd") + "_" + productInfo.getString("v_productnm_en").trim() + "_" + "FragranceCertificate" + "_" + sdf.format(new Date()));
		if(!"Y".equals(reqVo.getString("outputYn"))) {
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/br_pw_020_doc_part1_fragrance_report.do",productInfo.getString("v_product_cd") + "_" + productInfo.getString("v_productnm_en").trim() + "_" + "FragranceCertificate" + "_" + sdf.format(new Date()),ipAddr,"PDF");
		}
		JRDataSource datasource  	= CmFunction.getDataSource(rpVo);
		parameterMap.put("datasource", datasource);
		
		parameterMap.put("jrxmlPath", "jasper/br_pw_020_doc_fragrance.jrxml");
		parameterMap.put("downloadType", "pdf");
		parameterMap.put("outputYn", reqVo.getString("outputYn"));
		return new ModelAndView("jasperReportView", parameterMap);
	}
	/**
	 * fragrance_report 미리보기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/br_pw_020_doc_fragrance_pop.do")
	public ModelAndView pif_doc_fragrance_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sitims/mi/br/pw/020/br_pw_020_doc_fragrance_pop");
		
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))){
			return this.setMessage(mav, reqVo, "","", "[ i_sRecordId ] 필수값이 없습니다.", "");
		}
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		reqVo.put("i_sPk1", companyCd);
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo"))){
				reqVo.put("i_sLogo_flag", "N");
				companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign"))){
				reqVo.put("i_sSign_flag", "N");
				companyInfo.put("v_sign", "no_sign2.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			companyInfo.put("v_logo", "no_logo2.jpg");
			companyInfo.put("v_sign", "no_sign2.jpg");
		}
		
		String date = CmFunction.getUsDate1(CmFunction.getToday());
		
		reqVo.put("v_now_date", date);
		reqVo.put("i_sMatrcd", productInfo.getString("v_product_cd"));
		
		List<CmMap> fragranceList = brPw020Service.selectFragranceList(reqVo);
		
		int fragCnt = fragranceList == null? 0:fragranceList.size();
		
		StringBuffer fragranceCd = new StringBuffer();
		StringBuffer fragranceNm = new StringBuffer();
		if(fragCnt > 0){
			for(int i=0; i<fragCnt; i++){
				fragranceCd.append(fragranceList.get(i).getString("v_fragrance"));
				fragranceNm.append(fragranceList.get(i).getString("v_fragrance_smell"));
				
				if(i != (fragCnt - 1)) {
					fragranceCd.append(',');
					fragranceNm.append(',');
				}
			}
			StringBuffer application = new StringBuffer(fragranceList.get(0).getString("v_category"));
			//String application = fragranceList.get(0).getString("v_category");
			String[] temp ;
			for(int i=1; i<fragCnt; i++){
				temp = fragranceList.get(i).getString("v_category").split(",");
				
				for(int j=0; j<temp.length; j++){
					if(application.toString().indexOf(temp[j]) == -1){
						//application = application + "," + temp[j];
						application.append(',');
						application.append(temp[j]);
					}
				}
			}
			
			productInfo.put("v_fragrance_nm", fragranceNm.toString());
			productInfo.put("v_fragrance_cd", fragranceCd.toString());
			productInfo.put("v_category", application.toString());
		}else{
			productInfo.put("v_fragrance_nm", " ");
			productInfo.put("v_fragrance_cd", " ");
			productInfo.put("v_category", " ");
		}
		
		mav.addObject("productInfo", productInfo);
		mav.addObject("companyInfo", companyInfo);
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * batch report pdf문서
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/br_pw_020_doc_part1_batch_report.do")
	public ModelAndView pif_doc_part1_batch_report (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object>	parameterMap 	=	new HashMap<String,Object>();
		CmMap				rvo				=	new CmMap();
		CmMap				productInfo		=	new CmMap();
		String				productNm		=	"";
		String				productCd		=	"";
		String				date			= 	"";
		SimpleDateFormat	dateFormat		=	new SimpleDateFormat("yyyymmdd",Locale.KOREAN);
		
		
		date	=	CmFunction.getUsDate1(CmFunction.getToday());
		productInfo = brpr012service.getBrPr012Info(reqVo);
		productNm = productInfo.getString("v_product_nm_en");
		productCd = productInfo.getString("v_product_cd");
		
		String companyCd = null;
		
		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);

		reqVo.put("i_sPk1", companyCd);
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo == null){
			companyInfo = new CmMap();
		}
		
		rvo.put("now_date", date);
		rvo.put("v_product_cd", productCd);
		rvo.put("v_product_nm_en", productNm);
		
		rvo.put("v_addr1", productInfo.getString("v_company_addr1"));
		rvo.put("v_addr2", productInfo.getString("v_company_addr2"));
		rvo.put("v_addr3", productInfo.getString("v_company_addr3"));
		rvo.put("v_cpnm", productInfo.getString("v_company_nm_en"));
		rvo.put("v_phone_num", "Tel : "+companyInfo.getString("v_phone_no"));
		rvo.put("v_fax_num", "Fax : "+companyInfo.getString("v_fax"));
		rvo.put("v_manager_nm_en", productInfo.getString("v_repr_usernm_en"));
		
		if(companyInfo.getString("v_logo").isEmpty()){
			rvo.put("v_logo", "jasper/images/no_logo.jpg");
		}else{
			rvo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
		}
		
		if(companyInfo.getString("v_sign").isEmpty()){
			rvo.put("v_sign_flag", "N");
		}else{
			rvo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		HttpSession  session = request.getSession();
		session.setAttribute("S_WORD_FILE", productCd + "_" + productNm.trim() + "_" + "BatchCodingSystem" + "_" + dateFormat.format(new Date()));
		if(!"Y".equals(reqVo.getString("outputYn"))) {
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/br_pw_020_doc_part1_batch_report.do",productCd + "_" + productNm.trim() + "_" + "BatchCodingSystem" + "_" + dateFormat.format(new Date()),ipAddr,"PDF");
		}
		reqVo.put("pdfFileName",productCd + "_" + productNm.trim() + "_" + "BatchCodingSystem" + "_" + dateFormat.format(new Date()));
		JRDataSource datasource  	= CmFunction.getDataSource(rvo);
		parameterMap.put("datasource", datasource);
		parameterMap.put("jrxmlPath", "jasper/br_pw_020_doc_batch.jrxml");
		parameterMap.put("downloadType", "pdf");
		parameterMap.put("outputYn", reqVo.getString("outputYn"));
		
		return new ModelAndView("jasperReportView", parameterMap);
	}
	/**
	 * batch report 미리보기
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/doc/br_pw_020_doc_batch_pop.do")
	public ModelAndView pif_doc_batch_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sitims/mi/br/pw/020/br_pw_020_doc_batch_pop");
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))){
			return this.setMessage(mav, reqVo, "","", "[ i_sRecordId ] 필수값이 없습니다.", "");
		}
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		String companyCd = null;
		
		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		reqVo.put("i_sPk1", companyCd);
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo"))){
				reqVo.put("i_sLogo_flag", "N");
				companyInfo.put("v_logo", "no_logo.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign"))){
				reqVo.put("i_sSign_flag", "N");
				companyInfo.put("v_sign", "no_sign.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			companyInfo.put("v_logo", "no_logo.jpg");
			companyInfo.put("v_sign", "no_sign.jpg");
		}
		
		String date = CmFunction.getUsDate1(CmFunction.getToday());
		reqVo.put("v_now_date", date);
		
		mav.addObject("productInfo", productInfo);
		mav.addObject("companyInfo", companyInfo);
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	/**
	 * 검토의견 저장
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"PMD.CyclomaticComplexity"})
    @RequestMapping(value="/br_pw_020_t2_document_review_save.do")
	public ModelAndView br_pw_020_review_save(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
    	brPw020Service.saveBrPw020Review(reqVo);
    	
    	String reviewSt = reqVo.getString("i_sReveiwStatus");
    	String sFinalRst = reqVo.getString("i_sFinalRst");
    	///if("RS060".equals(sFinalRst)) {
        ////	brPw020Service.sapProcess(reqVo);
        ////	brPw020Service.sapProcessifSivan(reqVo);    		
    	////}
    	
    	CmMap mailVo = new CmMap();
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);
		reqVo.put("i_sUserNo", rVo.getString("v_bm_id"));
		reqVo.put("i_sFlagExcelAll", "Y");
		String strBmMail = commonService.getUserInfo(reqVo).getString("v_email");
		String strOdmMail = commonService.getVendorUserInfo(reqVo).getString("v_email");
		if(!CmFunction.isEmpty(strBmMail)) {
			strBmMail = cryptoService.decAES(strBmMail, true);
		}
		reqVo.put("i_sUserNo", reqVo.getString("s_userid"));
		CmMap fromUserInfo= commonService.getUserInfo(reqVo);
		String strFromMail = fromUserInfo.getString("v_email");
		if(!CmFunction.isEmpty(strFromMail)) {
			strFromMail = cryptoService.decAES(strFromMail, true);
		}
    	boolean mailChk = false;
    	mailVo.put("i_sReveiwOp", reqVo.get("i_sReveiwOp"));
    	mailVo.put("i_sFromAddr", strFromMail);
    	if("MODI_REQ".equals(reviewSt)) {
    		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 수출검토 항목의 수정이 요청되었습니다. ");
    		mailVo.put("prodMyRqList", reqVo.get("prodMyRqList"));    		
    		mailVo.put("i_sToAddr", strOdmMail);    	
    		mailVo.put("i_sShowNation","Y");	
    		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_modiReq_mail", mailVo));
    		//mailChk = true;
			this.sendMailwithHtml(mailVo);
    	}else if("NS030".equals(reviewSt)){
    		if("KO".equals(reqVo.getString("i_sNationCd"))) {
        		mailVo.put("i_sFlagGlKoNm","국내");
        		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 국내검토 반려되었습니다. ");    			
    		}else {
        		mailVo.put("i_sFlagGlKoNm","수출");
        		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 수출검토["+reqVo.getString("i_sNationNm")+"] 반려되었습니다. ");
        		mailVo.put("i_sShowNation","Y");
        		mailVo.put("i_sNationNm", reqVo.getString("i_sNationNm"));    			
    		}    		
    		mailVo.put("rVo", rVo);
    		mailVo.put("i_sToAddr", strOdmMail+";"+strBmMail);

    		mailVo.put("i_sFinalRstNm","반려");
    		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_review_mail", mailVo));
    		mailChk = true;
			this.sendMailwithHtml(mailVo);
    	}else if("RS090".equals(reviewSt) && "RS090".equals(sFinalRst)) {
    		mailChk = true;
    		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 수출검토["+reqVo.getString("i_sNationNm")+"] Drop되었습니다. ");    		
    		mailVo.put("rVo", rVo);
    		mailVo.put("i_sFlagGlKoNm","수출");
    		mailVo.put("i_sFinalRstNm","Drop");
    		mailVo.put("i_sShowNation","Y");
    		mailVo.put("i_sNationNm", reqVo.getString("i_sNationNm"));
    		mailVo.put("i_sToAddr", strBmMail);
    		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_review_mail", mailVo));
			this.sendMailwithHtml(mailVo);
    	}else if("NS020".equals(reviewSt) && "RS060".equals(sFinalRst)) {
    		mailChk = true;
    		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 수출검토 승인되었습니다. ");
    		mailVo.put("rVo", rVo);
    		mailVo.put("i_sFlagGlKoNm","수출");
    		mailVo.put("i_sFinalRstNm","승인");
    		mailVo.put("i_sNationNm", reqVo.getString("i_sNationNm"));
    		mailVo.put("i_sToAddr", strBmMail);
    		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_review_mail", mailVo));
			this.sendMailwithHtml(mailVo);
    	}else if("NS020".equals(reviewSt) && "RS050".equals(sFinalRst)) {
    		mailChk = true;
    		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 국내검토 승인되었습니다. ");
    		mailVo.put("rVo", rVo);
    		mailVo.put("i_sFlagGlKoNm","국내");
    		mailVo.put("i_sFinalRstNm","승인");
    		mailVo.put("i_sNationNm", "한국");
    		mailVo.put("i_sToAddr", strBmMail);
    		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_review_mail", mailVo));
			this.sendMailwithHtml(mailVo);
    	}else if("NS030".equals(reviewSt) && "RS055".equals(sFinalRst)) {
    		mailChk = true;
    		mailVo.put("i_sSubject", "[TIIMS] "+rVo.getString("v_product_cd")+" 국내검토 반려되었습니다. ");    		
    		mailVo.put("rVo", rVo);
    		mailVo.put("i_sFlagGlKoNm","국내");
    		mailVo.put("i_sFinalRstNm","반려");
    		mailVo.put("i_sNationNm", "한국");
    		mailVo.put("i_sToAddr", strOdmMail+";"+strBmMail);
    		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_review_mail", mailVo));
			this.sendMailwithHtml(mailVo);
    	}
    	
    	if(mailChk) {
    		CmMap logVo = new CmMap(); 
    		CmFunction.setSessionValue(request, logVo);   		
    		logVo.put("i_sTitle", mailVo.getString("i_sSubject"));
    		logVo.put("i_sRecordId", reqVo.getString("i_sProductCd"));
    		logVo.put("i_sContents", mailVo.getString("mailContents"));
    		logVo.put("i_sFromUser", fromUserInfo.getString("v_user_no"));
    		logVo.put("i_sFromAddr", mailVo.getString("i_sFromAddr"));
    		logVo.put("i_sRevUserIdAll", mailVo.getString("i_sToAddr"));
    		commonService.insertMailLog(logVo);    		    		    	
    	}
    	
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", reqVo);
    }
    
    
    /**
     * 제품 상태 변경
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/updateProdFinalRst.do")
	public ModelAndView updateProdFinalRst(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
    	brPw020Service.updateProdFinalRst(reqVo);
    	String sFinalRst = reqVo.getString("i_sFinalRst");
		CmMap mailVo = new CmMap();
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);
		reqVo.put("i_sFlagExcelAll", "Y");
		reqVo.put("i_sUserNo", reqVo.getString("s_userid"));
		CmMap fromUserInfo = commonService.getUserInfo(reqVo);
		String strFromMail = fromUserInfo.getString("v_email");
		reqVo.put("i_sUserNo", rVo.getString("v_bm_id"));
		String strBmMail = commonService.getUserInfo(reqVo).getString("v_email");
		String strOdmMail = commonService.getVendorUserInfo(reqVo).getString("v_email");
		if(!CmFunction.isEmpty(strFromMail)) {
			strFromMail = cryptoService.decAES(strFromMail, true);
		}
		if(!CmFunction.isEmpty(strBmMail)) {
			strBmMail = cryptoService.decAES(strBmMail, true);
		}
		
		boolean mailChk = false;
    	if("RS020".equals(sFinalRst)){
    		mailVo.put("i_sFinalRstNm","접수");
    		mailVo.put("i_sSubject", "[TIIMS]"+rVo.getString("v_product_cd")+" RA접수되었습니다. ");    	
    		mailVo.put("i_sToAddr", strOdmMail+";"+strBmMail);
    		mailChk = true;
    	}else if("RS025".equals(sFinalRst)){
    		mailVo.put("i_sFinalRstNm","반려");
    		mailVo.put("i_sSubject", "[TIIMS]"+rVo.getString("v_product_cd")+" RA반려되었습니다. ");    	
    		mailVo.put("i_sToAddr", strBmMail);
    		mailChk = true;
    	}	
		mailVo.put("rVo", rVo);
		mailVo.put("i_sFromAddr", strFromMail);
		mailVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_ra_mail", mailVo));
    	
    	if(mailChk) {
    		CmMap logVo = new CmMap();    		
    		CmFunction.setSessionValue(request, logVo);
    		logVo.put("i_sTitle", mailVo.getString("i_sSubject"));
    		logVo.put("i_sContents", mailVo.getString("mailContents"));
    		logVo.put("i_sFromUser", fromUserInfo.getString("v_user_no"));
    		logVo.put("i_sFromAddr", mailVo.getString("i_sFromAddr"));
    		logVo.put("i_sRevUserIdAll", mailVo.getString("i_sToAddr"));
    		logVo.put("i_sRecordId", reqVo.getString("i_sProductCd"));
    		commonService.insertMailLog(logVo);    		    		    		
    	}
		this.sendMailwithHtml(mailVo);
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", reqVo);
    }

	@RequestMapping(value="/br_pw_020_t2_document_view_ajax.do")
	public ModelAndView br_pw_020_t2_document_view_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sitims/mi/br/pw/020/br_pw_020_t2_document_view_ajax");
		List<CmMap> rawcdList = null;		
		
		rawcdList = brPw020Service.selectRawcd(reqVo);
		int recordCnt = rawcdList == null ? 0 : rawcdList.size();
		
		if(recordCnt > 0) {
			List<String> arrOdmdbid = new ArrayList<String>();
			
			for(int i=0; i<recordCnt; i++) {
				if("ODM".equals(rawcdList.get(i).getString("v_type"))) {
					arrOdmdbid.add(rawcdList.get(i).getString("v_odmdb_id"));
				}
			}
			
			int odmDbSize = arrOdmdbid == null ? 0 : arrOdmdbid.size();
			
			if(odmDbSize > 0) {
//					reqVo.put("i_arrPk1", arrOdmdbid);
//					reqVo.put("i_sPk2", reqVo.getString("i_sTrnsctreqid"));
//					reqVo.put("i_sRefTable", "MATE01MT");
				
				//List<CmMap> OdmAttachList = brPw020Service.selectOdmFileList(reqVo);
				CmMap fileVo = new CmMap();
				fileVo.put("i_arrRecordId", arrOdmdbid);
				List<CmMap> odmAttachList = commonService.getAttachList(fileVo);
				mav.addObject("OdmAttachList", odmAttachList);
			}
			
			List<String> arrRawCd = new ArrayList<String>();
			
			for(int i=0; i<recordCnt; i++) {
				if("AP".equals(rawcdList.get(i).getString("v_type"))) {
					arrRawCd.add(rawcdList.get(i).getString("v_raw_cd"));
				}
			}
			
			int rawSize = arrRawCd == null ? 0 : arrRawCd.size();
			
			if(rawSize > 0) {
				reqVo.put("arrRawCd", arrRawCd);
				
				//List<CmMap> bomList = brPw020Service.getOdmBomList(reqVo);
				//mav.addObject("bomList", bomList);
			}
		}
		mav.addObject("rawcdList", rawcdList);
			
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}


	@RequestMapping(value="/br_pw_020_file_request_pop.do")
	public ModelAndView br_pw_020_file_request_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sitims/mi/br/pw/020/br_pw_020_file_request_pop");
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);
		mav.addObject("rVo", rVo);
		mav.addObject("reqVo", reqVo);
		return mav;
	}

	@RequestMapping(value="/doc/requestSendMail.do")
	public ModelAndView requestSendMail (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
		reqVo.put("i_sUserNo", reqVo.getString("s_userid"));
		reqVo.put("i_sFlagExcelAll", "Y"); // 발신자 이메일을 찾기위해 페이징조건 제외하는 변수
		String strFromMail = commonService.getUserInfo(reqVo).getString("v_email");
		if(!CmFunction.isEmpty(strFromMail)) {
			strFromMail = cryptoService.decAES(strFromMail, true);
		}
		reqVo.put("i_sFromAddr", strFromMail);
		reqVo.put("i_sToAddr", reqVo.getString("i_sOdmUserEmail"));
		reqVo.put("i_sContents", this.getMailContents("sitims/views/mi/br/pw/020/br_pw_020_reqFile_mail", reqVo));
		this.sendMailwithHtml(reqVo);
		
		CmMap logVo = new CmMap();
		logVo.put("i_sTitle", reqVo.getString("i_sSubject"));
		logVo.put("i_sContents", reqVo.getString("mailContents"));
		logVo.put("i_sRecordId", reqVo.getString("i_sRecordId"));
		logVo.put("i_sProductCd", reqVo.getString("i_sProductCd"));
		logVo.put("i_sNationCd", reqVo.getString("i_sNationCd"));
		logVo.put("i_sDocCd", reqVo.getString("i_sDocCd"));
		logVo.put("i_sRevUserId", reqVo.getString("i_sOdmUserEmail"));
		commonService.insertReqDocMailLog(logVo);
		return this.makeJsonDhtmlxResult(mav, "OK", "success", reqVo);
	}

	@RequestMapping(value="/br_pw_020_req_file_list_pop.do")
	public ModelAndView br_pw_020_req_file_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav =  getInitMAV(request,"sitims/views/mi/br/pw/020/br_pw_020_req_file_list_pop");
    	if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))) {
    		reqVo.put("i_sRecordId","");
    	}
		if(CmFunction.isEmpty(reqVo.getString("i_sProductCd"))) {
			reqVo.put("i_sProductCd","");
		}
		if(CmFunction.isEmpty(reqVo.getString("i_sNationCd"))) {
			reqVo.put("i_sNationCd","");
		}
    	mav.addObject("reqVo",reqVo);
    	return mav;
	}

    @RequestMapping(value="/getReqFileList.do")
    public ModelAndView getReqFileList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, commonService.getMailLogList(reqVo));
    }
    @RequestMapping(value="/saveChinaReview.do")
	public ModelAndView saveChinaReview(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
    	brPw020Service.saveChinaReview(reqVo);    	
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", reqVo);
    }
	@RequestMapping(value="/doc/excelMlMgDownload.do")
	public ModelAndView pif_doc_msds_excel (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("excel2View2");
		String  sMlMgStr = reqVo.getString("i_sMlMgStr");
		String file = "jxls/china_spec_"+sMlMgStr+".jxls";
		String	path	= resourceLoader.getResource("classpath:"+file).getURI().getPath();
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		XLSTransformer transformer = new XLSTransformer();
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);
        Map<String , Object> beans = new HashMap<String , Object>();
        beans.put("vo", rVo);
		Workbook	wb	=	transformer.transformXLS(is, beans);
		String ipAddr			= request.getRemoteAddr();
		this.pdfExcelDownloadLog("","/doc/excelMlMgDownload.do","",ipAddr,"EXCEL");
		mav.addObject("Workbook" ,wb);
		mav.addObject("excelFileName", "중국_spec_"+sMlMgStr);

		mav.addObject("reqVo", reqVo);
		
		return mav;
	}
	@RequestMapping(value="/doc/br_pw_020_cn_spec_mlmg_preview_pop.do")
	public ModelAndView br_pw_020_cn_spec_mlmg_preview_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		String  sMlMgStr = reqVo.getString("i_sMlMgStr");
		ModelAndView mav =  getInitMAV(request,"sitims/views/mi/br/pw/020/br_pw_020_cn_spec_"+sMlMgStr+"_preview_pop");
		CmMap rVo = brpr012service.getBrPr012Info(reqVo);

		if(CmFunction.isEmpty(rVo.getString("v_color_state"))) {
			rVo.put("v_color_state","");
		}
		if(CmFunction.isEmpty(rVo.getString("v_physical_state"))) {
			rVo.put("v_physical_state","");
		}
		if(CmFunction.isEmpty(rVo.getString("v_odor_state"))) {
			rVo.put("v_odor_state","");
		}
		if(CmFunction.isEmpty(rVo.getString("v_product_nm_en"))) {
			rVo.put("v_product_nm_en","");
		}
		mav.addObject("rVo", rVo);
		mav.addObject("reqVo", reqVo);
		
		return mav;
	}

	@RequestMapping(value="/br_pw_020_func_log_pop.do")
	public ModelAndView br_pw_020_func_log_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		ModelAndView mav =  getInitMAV(request,"sitims/views/mi/br/pw/020/br_pw_020_func_log_pop");
		
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))) {
			reqVo.put("i_sRecordId","");
		}
		if(CmFunction.isEmpty(reqVo.getString("i_sProductCd"))) {
			reqVo.put("i_sProductCd","");
		}
		if(CmFunction.isEmpty(reqVo.getString("i_sDivision"))) {
			reqVo.put("i_sDivision","");
		}
		if(CmFunction.isEmpty(reqVo.getString("i_sPartNo"))) {
			reqVo.put("i_sPartNo","");
		}
		mav.addObject("reqVo", reqVo);
		
		return mav;
	}
    @RequestMapping(value="/getFuncLogList.do")
	public ModelAndView getFuncLogList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
    	List<CmMap> rVo = brPw020Service.selectModiRawFuncLog(reqVo);    	
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", rVo);
    }
	@RequestMapping(value="/br_pw_020_req_modi_list_pop.do")
	public ModelAndView br_pw_020_req_modi_list_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {	
		
		ModelAndView mav =  getInitMAV(request,"sitims/views/mi/br/pw/020/br_pw_020_req_modi_list_pop");
		
		if(CmFunction.isEmpty(reqVo.getString("i_sRecordId"))) {
			reqVo.put("i_sRecordId","");
		}
		if(CmFunction.isEmpty(reqVo.getString("i_sProductCd"))) {
			reqVo.put("i_sProductCd","");
		}
		if(CmFunction.isEmpty(reqVo.getString("i_sFlag"))) {
			reqVo.put("i_sFlag","");
		}
		mav.addObject("reqVo", reqVo);
		
		return mav;
	}
    @RequestMapping(value="/getReqModiList.do")
	public ModelAndView getReqModiList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		CmFunction.setSessionValue(request, reqVo);
    	List<CmMap> rVo = brPw020Service.selectReqModiList(reqVo);    	
    	return this.makeJsonDhtmlxResult(mav, "succ", "succ", rVo);
    }
    @RequestMapping(value="/part1ZipFileDownload.do")
	public ModelAndView part1ZipFileDownload(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CmFunction.setSessionValue(request, reqVo);
		CmMap tempVo = new CmMap();
		String[] arrUploadCd = {"ORI_BOX","ORI_VESSEL","ORI_PAPER"};
		tempVo.put("i_sRecordIdLike",reqVo.getString("i_sAdReqId"));
		tempVo.put("i_arrUploadCd",arrUploadCd);
    	List<CmMap> attachList = commonService.getAttachList(tempVo);
//		String[] prodArrUploadCd = {"ADM","AST"};
//		tempVo.put("i_sRecordIdLike",reqVo.getString("i_sProductCd"));
//		tempVo.put("i_arrUploadCd",prodArrUploadCd);
//    	List<CmMap> prodAttachList = commonService.getAttachList(tempVo);
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
//    	for(CmMap attachInfo : prodAttachList) {
//    		String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";
//        	//String fileName = attachInfo.getString("v_attach_nm");
//			String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
//			//String filename = attachInfo.getString("v_attach_nm");
//			String	tmpFilePath		= path+"TEMP/"+ attachInfo.getString("v_attach_nm");
//			String  filePath 	= path + tempFileName;
//        	if (new File(filePath).exists()) {
//				CmFunction.fileCopy(filePath,tmpFilePath);
//    			files.add(new File(tmpFilePath));        		
//        	}
//    	}
    	
    	String path = CmPathInfo.getUPLOAD_FILE_TEMP_PATH();
    	CmMap tmpVo = reqVo;
    	HttpSession session = request.getSession();
    	ModelAndView mav = null;
    	String pdfFileNm= "" ;
    	String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
    	mav = this.pif_doc_part1_batch_report(tmpVo, request, response);
    	pdfFileNm = ((String)session.getAttribute("S_WORD_FILE")).replaceAll(match, "");
    	jasperReportDown(mav.getModelMap(),pdfFileNm);
    	files.add(new File(path+pdfFileNm+".pdf"));
    	
    	mav = this.br_pw_020_doc_part1_health_report(tmpVo, request, response);
    	pdfFileNm = ((String)session.getAttribute("S_WORD_FILE")).replaceAll(match, "");
    	jasperReportDown(mav.getModelMap(),pdfFileNm);
    	files.add(new File(path+pdfFileNm+".pdf"));

    	mav = this.br_pw_020_doc_part1_claim_report(tmpVo, request, response);
    	pdfFileNm = ((String)session.getAttribute("S_WORD_FILE")).replaceAll(match, "");
    	jasperReportDown(mav.getModelMap(),pdfFileNm);
    	files.add(new File(path+pdfFileNm+".pdf"));

    	mav = this.pif_doc_part1_fragrance_report(tmpVo, request, response);
    	pdfFileNm = ((String)session.getAttribute("S_WORD_FILE")).replaceAll(match, "");
    	jasperReportDown(mav.getModelMap(),pdfFileNm);
    	files.add(new File(path+pdfFileNm+".pdf"));
    	
    	
    	this.zipFileDownLoad(request,response,files,reqVo.getString("i_sProductCd")+"_PART1");
		return null;
    }

	@RequestMapping(value="/part2ZipFileDownload.do")
	public ModelAndView part2ZipFileDownload(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CmFunction.setSessionValue(request, reqVo);
		CmMap tempVo = new CmMap();
		List<CmMap> rawcdList = brPw020Service.selectRawcd(reqVo);
		String[] arrUploadCd = {"ATT01","ATT03","ATT02","ATT06","ATT08","ATT07","ATT09","ATT19"};
		List<String> arrOdmdbid = new ArrayList<String>();
		for(CmMap map : rawcdList) {
			arrOdmdbid.add(map.getString("v_odmdb_id"));			
		}
		//tempVo.put("i_sRecordIdLike",reqVo.getString("i_sProductCd"));
		tempVo.put("i_arrRecordId",arrOdmdbid);
		tempVo.put("i_arrUploadCd",arrUploadCd);
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
    	this.zipFileDownLoad(request,response,files,reqVo.getString("i_sProductCd")+"_PART2");
		return null;
    }
    @RequestMapping(value="/part3ZipFileDownload.do")
	public ModelAndView part3ZipFileDownload(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CmFunction.setSessionValue(request, reqVo);
//		CmMap tempVo = new CmMap();
//		String[] prodArrUploadCd = {"STB","PSPEC"};
//		tempVo.put("i_sRecordIdLike",reqVo.getString("i_sRecordId"));
//		tempVo.put("i_sBuffer1",reqVo.getString("i_sProductCd"));
//		tempVo.put("i_sBuffer2",reqVo.getString("i_sPartNo"));
//		tempVo.put("i_arrUploadCd",prodArrUploadCd);
//    	List<CmMap> prodAttachList = commonService.getAttachList(tempVo);
    	
    	List<File> files = new ArrayList<File>();
//    	for(CmMap attachInfo : prodAttachList) {
//    		String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";
//        	//String fileName = attachInfo.getString("v_attach_nm");
//			String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
//			//String filename = attachInfo.getString("v_attach_nm");
//			String	tmpFilePath		= path+"TEMP/"+ attachInfo.getString("v_attach_nm");
//			String  filePath 	= path + tempFileName;
//        	if (new File(filePath).exists()) {
//				CmFunction.fileCopy(filePath,tmpFilePath);
//    			files.add(new File(tmpFilePath));        		
//        	}
//    	}
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
    	
    	/*tmpVo.put("i_sAllergenFlag","Y");
    	tmpVo.put("i_sRefYn","");    	
    	brpr012service.coDocFomulaExcel(tmpVo, excelMav);
		productInfo = (CmMap) tmpVo.get("productInfo");
		xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").trim()+"_singleFormula(ASEAN)";
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelSingleFormula(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));*/

    	brpr012service.coDocFomulaKoExcel(tmpVo, excelMav);
    	productInfo = (CmMap) tmpVo.get("productInfo");
    	xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_productnm_en").replaceAll(match, "").trim()+"_FORMULA_KO";
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelFormulaKo(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));

    	brpr012service.coDocFomulaJpExcel(tmpVo, excelMav);
    	productInfo = (CmMap) tmpVo.get("productInfo");
    	xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_productnm_en").replaceAll(match, "").trim()+"_FORMULA_JP";
		
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelFormulaJp(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));

    	brpr012service.coDocFomulaCnExcel(tmpVo, excelMav);
    	productInfo = (CmMap) tmpVo.get("productInfo");
    	xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_productnm_en").replaceAll(match, "").trim()+"_FORMULA_CH";	
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelFormulaCh(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));
    	
    	tmpVo.put("i_sRecipeType","C");
    	brpr012service.coDocProcessExcel(tmpVo, excelMav);
    	CmMap prodVo = (CmMap) tmpVo.get("prodVo");
		xlsFileNm = productInfo.getString("v_product_cd")+"_"+prodVo.getString("v_product_nm_en").replaceAll(match, "").trim()+"_Flowchart_Complex";
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelProcessComplex(tmpVo, wb),xlsFileNm);
    	files.add(new File(path+xlsFileNm+".xls"));


    	brpr012service.coDocMsdsExcel(tmpVo, excelMav);
    	productInfo = (CmMap) tmpVo.get("productInfo");
		xlsFileNm = productInfo.getString("v_product_cd")+"_"+productInfo.getString("v_product_nm_en").replaceAll(match, "").trim()+"_MSDS";
		wb	=	new HSSFWorkbook();
    	excelReportDown(odmReportPoi.excelMsds(tmpVo, wb),xlsFileNm);
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
    	

    	String recordid = request.getParameter("i_sRecordId");
    	String productcd = request.getParameter("i_sProductCd");
    	String partno = request.getParameter("i_sPartNo");
		StringBuilder rTitle = new StringBuilder(1024);
		rTitle.append(productcd).append('_').append(productInfo.getString("v_product_nm_en")).append('_').append("Stability");
		StringBuilder urlSb = new StringBuilder(CmPathInfo.getROOT_FULL_URL());
		urlSb.append("br/pr/012/doc/co_doc_stability_pdf_print.do?"+"i_sRecordId="+recordid+"&i_sProductCd="+productcd+"&i_sPartNo="+partno);
    	this.documentPdfPrint(urlSb.toString(), rTitle.toString(), "KO", request, response, "","Y");    	
    	files.add(new File(path+rTitle.toString()+".pdf"));
    	
    	
    	this.zipFileDownLoad(request,response,files,reqVo.getString("i_sProductCd")+"_PART3");
		return null;
    }
    @RequestMapping(value="/part4ZipFileDownload.do")
	public ModelAndView part4ZipFileDownload(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		docList = commonService.getCmSubCodeList("FILE_REQ",1,"GL");
		for(CmMap map : docList) {
			switch(map.getString("COMM_CD")) {
			case "FR013":				
				break;
			default:
				arrUploadCd.add(map.getString("COMM_CD"));
				break;
			}
		}
		tempVo.put("i_sRecordIdLike",reqVo.getString("i_sRecordId"));
		tempVo.put("i_sBuffer1",reqVo.getString("i_sProductCd"));
		tempVo.put("i_sBuffer2",reqVo.getString("i_sPartNo"));
		tempVo.put("i_arrUploadCd",arrUploadCd);
    	List<CmMap> prodAttachList = commonService.getAttachList(tempVo);
    	
    	List<File> files = new ArrayList<File>();
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
    	
    	String path = CmPathInfo.getUPLOAD_FILE_TEMP_PATH();
    	CmMap tmpVo = reqVo;
    	HttpSession session = request.getSession();
    	ModelAndView mav = null;
    	String pdfFileNm= "" ;
    	
    	mav = this.br_pw_020_doc_china_commission_product(tmpVo, request, response);
    	pdfFileNm = (String)session.getAttribute("S_WORD_FILE");
    	jasperReportDownDoc(mav.getModelMap(),pdfFileNm);
    	files.add(new File(path+pdfFileNm+".docx"));
    	
    	this.zipFileDownLoad(request,response,files,reqVo.getString("i_sProductCd")+"_PART4");
		return null;
    }

    /**
     * 수출검토 health_report pdf문서
     * @param reqVo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping("/doc/br_pw_020_doc_china_commission_product.do")
	public ModelAndView br_pw_020_doc_china_commission_product (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object>	parameterMap 	=	new HashMap<String,Object>();
		List<CmMap>			asnList			=	new ArrayList<CmMap>();
		CmMap				rpVo			=	new CmMap();
		CmMap				productInfo		=	new CmMap();
		String				productNm		=	"";
		String				productCd		=	"";
		String				date			=	"";
		String				mav				=	"pifDocPart1HealthReport1";
		SimpleDateFormat	dateFormat				=	new SimpleDateFormat("yyyymmdd",Locale.KOREAN);
		
		parameterMap.put("jrxmlPath", "jasper/br_pw_020_doc_china_commission_product.jrxml");
		parameterMap.put("downloadType", "docx");
		parameterMap.put("outputYn", reqVo.getString("outputYn"));
		
		
		date = CmFunction.getUsDate1(CmFunction.getToday());
		productInfo = brpr012service.getBrPr012Info(reqVo);
		productNm = productInfo.getString("v_product_nm_en");
		productCd = productInfo.getString("v_product_cd");
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		if("Y".equals(productInfo.getString("v_import_yn"))){
			reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo == null){
			companyInfo = new CmMap();
		}
		
		rpVo.put("now_date", date);
		rpVo.put("v_product_cd", productCd);
		rpVo.put("v_product_nm_en", productNm);
		rpVo.put("v_vendor_nm_en", companyInfo.getString("v_vendor_nm_en"));
		
		rpVo.put("v_no_img_flag", "Y");
		if(companyInfo.getString("v_logo").isEmpty()){
			rpVo.put("v_logo", "jasper/images/no_logo.jpg");
		}else{
			rpVo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
		}
		
		if(companyInfo.getString("v_sign").isEmpty()){
			rpVo.put("v_sign_flag", "N");
		}else{
			rpVo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
		}
		
		HttpSession  session = request.getSession();
		session.setAttribute("S_WORD_FILE", productCd + "_" + productNm.trim() + "_" + "AGREEMENT" + "_" + dateFormat.format(new Date()));
//		reqVo.put("pdfFileName", productCd + "_" + productNm.trim() + "_" + "AGREEMENT" + "_" + dateFormat.format(new Date()));
//		if(!"Y".equals(reqVo.getString("outputYn"))) {
//			//ip주소
//			String ipAddr			= request.getRemoteAddr();
//			this.pdfExcelDownloadLog(productInfo.getString("v_product_cd"),"/doc/br_pw_020_doc_china_commission_product.do",productCd + "_" + productNm.trim() + "_" + "UndesirableHealthEffectsSummary" + "_" + dateFormat.format(new Date()),ipAddr,"PDF");
//		}
		JRDataSource datasource  	= CmFunction.getDataSource(rpVo);
		parameterMap.put("datasource", datasource);
		
		
		return new ModelAndView("jasperReportView", parameterMap);
	}

	@RequestMapping("/br_pw_020_doc_china_commission_product_pop.do")
	public ModelAndView br_pw_020_doc_china_commission_product_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
    	mav.setViewName("sitims/mi/br/pw/020/br_pw_020_doc_china_commission_product_pop");
		
		CmMap productInfo = brpr012service.getBrPr012Info(reqVo);
		
		String companyCd = null;

		if(CmFunction.isNotEmpty(productInfo.getString("v_packing_companycd"))){
			companyCd = productInfo.getString("v_packing_companycd");
		}else{
			companyCd = productInfo.getString("v_vendor_id");
		}
		
		reqVo.put("i_sCompanyCd", companyCd);
		
		if("Y".equals(productInfo.getString("v_import_yn"))){
			reqVo.put("i_sPk1", productInfo.getString("v_product_cd"));
		}else{
			reqVo.put("i_sPk1", companyCd);
		}
		
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo"))){
				reqVo.put("i_sLogo_flag", "N");
				companyInfo.put("v_logo", "no_logo2.jpg");
			}
			
			if(CmFunction.isEmpty(companyInfo.getString("v_sign"))){
				reqVo.put("i_sSign_flag", "N");
				companyInfo.put("v_sign", "no_sign2.jpg");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
			
			companyInfo.put("v_logo", "no_logo2.jpg");
			companyInfo.put("v_sign", "no_sign2.jpg");
		}
		
		String date = CmFunction.getUsDate1(CmFunction.getToday());
		reqVo.put("v_now_date", date);
		
		if(!"".equals(reqVo.getString("i_sAsnExp"))){
			this.getCmSubCodeList(mav, "aseanExpSub", "ODM_EXPORT2", reqVo.getString("i_sAsnExp"));
		}
		mav.addObject("productInfo", productInfo);
		mav.addObject("companyInfo", companyInfo);
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}


	
	@RequestMapping("/doc/br_pw_020_doc_spec_preview_pop.do")
	public ModelAndView	br_pw_020_doc_spec_preview_pop (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav =  new ModelAndView();
		mav.setViewName("/sitims/mi/br/pw/020/br_pw_020_doc_spec_preview_pop");
		CmMap				rvo			=	new CmMap();
		rvo = brpr012service.getBrPr012Info(reqVo);
		
		reqVo.put("i_sCompanyCd", rvo.getString("v_vendor_id"));
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
		List<CmMap> sSpecList = brPw020Service.selectSpecList(reqVo);
			
		reqVo.put("i_sDeptApprFlag", "Y");
		reqVo.put("i_sDeptcd", rvo.getString("v_appr_dept_cd"));
		CmMap apprInfo = new CmMap();
		//CmMap apprInfo = tddDocService.selectApprInfo(reqVo);
		//String attSignPath = CmPathInfo.UPLOAD_IMG_PATH() + this.getFilePath(apprInfo.getString("v_sign"));
		//apprInfo.put("v_sign", attSignPath);


		mav.addObject("rvo", rvo);
		mav.addObject("companyInfo", companyInfo);
		mav.addObject("sSpecList", sSpecList);
		mav.addObject("apprInfo", apprInfo);
		mav.addObject("ISSUED_DT", CmFunction.getUsDate1(CmFunction.getToday()));
		
		mav.addObject("reqVo", reqVo);
		return mav;
	}
	@RequestMapping("/doc/br_pw_020_doc_spec_pdf_download.do")
	public ModelAndView br_pw_020_doc_spec_pdf_download (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object>	parameterMap 	=	new HashMap<String,Object>();
		CmMap				rpVo			=	new CmMap();
		CmMap				rvo			=	new CmMap();
		String				sDate			=	"";
		
		sDate		=	CmFunction.getUsDate1(CmFunction.getToday());
		rvo = brpr012service.getBrPr012Info(reqVo);		
		reqVo.put("i_sCompanyCd", rvo.getString("v_vendor_id"));
		CmMap companyInfo = brpr012service.selectCompanyInfo(reqVo);
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
				reqVo.put("i_sLogo_flag", "N");
				rpVo.put("v_logo", "jasper/images/no_logo.jpg");
			}else {
				rpVo.put("v_logo", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_logo_attachid"));
			}
			if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
				reqVo.put("i_sSign_flag", "N");
				rpVo.put("v_logo", "jasper/images/no_sign2.jpg");
			}else {
				rpVo.put("v_sign", CmPathInfo.getROOT_FULL_URL_ODM() + "showImg.do?i_sAttachId=" + companyInfo.getString("v_sign_attachid"));
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
		}
		
		rpVo.put("now_date", sDate);
		rpVo.put("v_product_cd", rvo.getString("v_product_cd"));
		rpVo.put("v_product_nm_en", rvo.getString("v_product_nm_en"));
		
		String flagImp="";
		if("F".equals(reqVo.getString("i_sFlagImp"))) {
			flagImp="(완제품)";
		}else {
			flagImp="(반제품)";
		}
		
		List<CmMap> listSpec = brPw020Service.selectSpecList(reqVo);
		
		HttpSession session = request.getSession();
		session.setAttribute("S_WORD_FILE", "[" + rvo.getString("v_product_cd") + "]"+"Spec"+flagImp);
		
		//pifPart.insertPifFileLog(reqVo, "Spec", ".pdf");
		
		JRDataSource datasource  	= CmFunction.getDataSource(rpVo);
		JRDataSource datasourcelist = CmFunction.getDataSource(listSpec);
		parameterMap.put("datasource", datasource);
		parameterMap.put("JasperCustomSubReportDatasource", datasourcelist);	
		parameterMap.put("JasperCustomSubReportLocation", JasperCompileManager.compileReport(resourceLoader.getResource("classpath:jasper/br_pw_020_spec_subreport1.jrxml").getURI().getPath()));	
		parameterMap.put("jrxmlPath", "jasper/br_pw_020_spec.jrxml");
		parameterMap.put("downloadType", "pdf");
		
		return new ModelAndView("jasperReportView", parameterMap);
	}
	@RequestMapping("/doc/br_pw_020_doc_spec_excel_download.do")
	public ModelAndView	br_pw_020_doc_spec_excel_download(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	=	new ModelAndView("excel2View");
		
		CmMap rvo = null;		
		OdmReportPoi			poi				=	new OdmReportPoi();
		HSSFWorkbook			wb				=   new HSSFWorkbook();
		brPw020Service.coDocSpecExcel(reqVo,mav);
		//rvo = brpr012service.selectContentInfo(reqVo);
		String flagImp = "";
		if("F".equals(reqVo.getString("i_sFlagImp"))) {
			flagImp="(완제품)";
		}else {
			flagImp="(반제품)";
		}
		rvo = (CmMap) reqVo.get("rvo");
		//String ipAddr			= request.getRemoteAddr();
		//this.pdfExcelDownloadLog(prodVo.getString("v_product_cd"),"/doc/co_doc_process_preview_pop_excel.do",prodVo.getString("v_product_cd")+"_"+prodVo.getString("v_product_nm_en").trim()+"_Flowchart_"+recipeType,ipAddr,"EXCEL");
		//mav.addObject("HSSFWorkbook", wb);		
		mav.addObject("HSSFWorkbook", poi.excelTdd2017Spec(reqVo, wb));
		mav.addObject("excelFileOutPut" ,reqVo.getString("excelFileOutPut"));
		mav.addObject("excelFileName",  rvo.getString("v_product_cd")+"_"+rvo.getString("v_product_nm_en").trim()+"_Spec"+flagImp);
		return mav;
	}

    @RequestMapping(value="/getMessage.do")
    public ModelAndView getMessage(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	return this.makeJsonDhtmlxResult(mav, "OK", "success", brPw020Service.getNationMessage(reqVo));
    }

    @RequestMapping(value="/br_pw_020_t2_message_save.do")
    public ModelAndView br_pw_020_t2_message_save(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	brPw020Service.saveMessage(reqVo);
    	return this.makeJsonDhtmlxResult(mav, "OK", "success", reqVo);
    }
}
