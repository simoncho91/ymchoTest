package com.shinsegae_inc.sitims.mi.is.od.controller;

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
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.mi.is.od.service.IsOd010Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/is/od/010/*")
public class IsOd010Controller extends CommonController{
	@Resource(name= "IsOd010Service")
	private transient IsOd010Service	isOd010Service;
	
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
	public ModelAndView is_od_010_list(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav =  getInitMAV(request,"sitims/views/mi/is/od/010/is_od_010_list");
		if(CmFunction.isEmpty(reqVo.getString("i_sVendor"))) {
    		reqVo.put("i_sVendor","");
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
     * Comments : 원료DB 목록 조회
     * </pre>
     */
	@RequestMapping(value="/selectList.do")
    public ModelAndView selectMainList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	
    	if(CmFunction.isNotEmpty(CmFunction.getStringValue(reqVo.get("i_sSearchNm")))){
			reqVo.put("vSearchCondition", "Y");
		}else{
			reqVo.put("vSearchCondition", "N");
		}
    	
    	if(reqVo.getStringArray("LIM_LIST[]") != null) {
			String[] str = reqVo.getStringArray("LIM_LIST[]");
			reqVo.put("arrConLimit", str);
			reqVo.put("vSearchComLimit", CmFunction.isNotEmpty(reqVo.getString("arrConLimit")) ? "Y" : "N");
		}
		if(reqVo.getStringArray("BAN_LIST[]") != null) {
			String[] str = reqVo.getStringArray("BAN_LIST[]");
			reqVo.put("arrConBan", str);
			reqVo.put("vSearchComBan", CmFunction.isNotEmpty(reqVo.getString("arrConBan")) ? "Y" : "N");
		}
		
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, isOd010Service.selectIsOd010List(reqVo));
    }
	
	/**
	 * 
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 
	 * <pre>
	 * Comments : 원료DB 상세 조회
	 * </pre>
	 */
	@RequestMapping(value="/is_od_010_view.do")
	public ModelAndView is_od_011_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("sitims/mi/is/od/010/is_od_010_view");
			// 원료 정보 가져오기
			CmMap mate01mt = isOd010Service.selectIsOd011MatInfo(reqVo);
			
			mate01mt.put("vCompleteYn", "N");
			
			if(mate01mt != null) {
			// 성분 정보 리스트 가져오기
			List<CmMap> mate01dt = isOd010Service.selectIsOd011ConListInMat(reqVo);
			
			if("Y".equals(CmFunction.getStringValue(mate01mt.get("v_flag_flavor")))) {
				mav.addObject("allergenMap", isOd010Service.getAllergenMap(reqVo));
				mav.addObject("consumList", isOd010Service.selectIsOd011TotalConListInMat(reqVo, mate01dt));
			}
			
			mav.addObject("regContent", mate01mt);
			mav.addObject("ingredList", mate01dt);

			}
			
	   	   return mav;
	}
	
	/**
	 *  Comments : ODM사 정보 리스트 조회 select
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("is_od_010_get_options_ajax.do")
	public ModelAndView is_od_010_get_options_ajax(@ModelAttribute ("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav		=	new ModelAndView();
		 List<CmMap> odmList					=	isOd010Service.selectIsOd010OdmCompanyInfoList();
		
		if(odmList != null) {
			return makeJsonResult(mav, "OK", "SUCCESS", odmList);
		}else {
			return makeJsonResult(mav, "NO", "FAIL", null);
		}
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
	 * Commnets : 원료DB 조회 엑셀
	 * </pre>
	 */
	@RequestMapping(value="/is_od_010_list_excel.do")
	public ModelAndView is_od_010_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	isOd010Service.selectIsOd010List(reqVo);
		String flag = reqVo.getString("i_sFlagExcelAll");
		
		// Title
		String[] titleArray = {
			 "원료코드"
			, "원료명"
			, "제조사"
			, "사용 금지"
			, "배합 제한"
			, "상태"
			, "향코드 여부"
			, "알러젠 포함 여부"
			, "등록일"
		};




	// DB Column Name
		String[] columnNmArray = {
			 "v_odmmat_cd"
			, "v_mat_nm"
			, "v_maker"
			, "v_ban"
			, "v_limit"
			, "v_use_yn"
			, "v_flag_flavor"
			, "v_allergen_yn"
			, "v_reg_dtm"
		};

	// Column Width
	//	int[]	columnWidth	=	{30, 15, 15, 80, 20, 10, 12};

		if(flag.equals("Y")) {
		mav.addObject("excelFileName"	, "원료DB 조회[전체 페이지]");
		} else
		mav.addObject("excelFileName"	, "원료DB 조회[현재 페이지]");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
	//	mav.addObject("columnWidth"		, columnWidth);
		mav.addObject("excelData"		, list);

		return mav;
	}
}
