package com.shinsegae_inc.sitims.mi.si.cm.controller;

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
import com.shinsegae_inc.sitims.mi.si.cm.service.SiCm030Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/cm/030/*")
public class SiCm030Controller extends CommonController{
	@Autowired
	private transient SiCm030Service siCm030Service; 

	/**
	 * 
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
    * <pre>
    * Commnets : 성분배합 한도관리 목록 페이지
    * </pre>
	 */
    @RequestMapping(value="/init.do")
	public ModelAndView init (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getInitMAV(request,"sitims/views/mi/si/cm/030/si_cm_030_list");
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
     * Commnets : 성분배합 한도관리 목록 조회
     * </pre>
     */
    @RequestMapping(value="/si_cm_030_List.do")
    public ModelAndView si_cm_030_List(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, siCm030Service.selectSiCm030List(reqVo));
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
     * Commnets : 성분코드에 따른 분배합 한도 리스트 
     * </pre>
     */
    @RequestMapping(value="/si_cm_030_getConCdLimitList.do")
    public ModelAndView si_cm_030_getConCdLimitList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
       	ModelAndView mav = new ModelAndView("jsonView");
       	reqVo.put("i_sConCd", reqVo.get("v_con_cd"));
    	return this.makeJsonDhtmlxResult(mav, "OK", "success", siCm030Service.getSiCm030LimitCateList(reqVo));
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
     * Commnets : 기본 배합한도 여부 설정 
     * </pre>
     */
    @RequestMapping(value="/si_cm_030_updateForDefaultLimit.do")
    public ModelAndView si_cm_030_updateForDefaultLimit(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
       	ModelAndView mav = new ModelAndView("jsonView");
       	reqVo.put("i_sUserId",SessionUtils.getUserNo());
       
			siCm030Service.updateSiCm030LimitYn(reqVo);
		
		return this.makeJsonResult(mav,"success", "배합한도 여부 수정 완료", null);
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
     * Commnets : 성분배합한도 수정 
     * </pre>
     */
    @RequestMapping(value="/si_cm_030_insertForLimitConSub.do")
    public ModelAndView si_cm_030_insertForLimitConSub(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	
    	String str = reqVo.getString("i_sJsonArr");
		
		JSONArray arr = JSONArray.fromObject(str);
		List<CmMap> resendList = new ArrayList<CmMap>();
		for (int i = 0; i < arr.size(); i++) {

			JSONObject obj = (JSONObject) arr.get(i);
			CmMap resendMap = new CmMap();
			resendMap.put("i_sConCd", obj.get("i_sConCd"));
			resendMap.put("i_iLimit", obj.get("i_iLimit"));
			resendMap.put("i_sCateGory1", obj.get("i_sCateGory1"));
			resendMap.put("i_sCateGory2", obj.get("i_sCateGory2"));
			
			resendList.add(resendMap);
		}
		reqVo.put("i_arrStr", resendList);
		reqVo.put("i_sUserId", SessionUtils.getUserNo());
    	
    	
    		siCm030Service.insertSiCm030LimitConSub(reqVo);
    
    	return this.makeJsonResult(mav,"success", "배합한도 수정 완료", null);
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
	 * Commnets : 엑셀
	 * </pre>
	 */
	@RequestMapping("si_cm_030_list_excel.do")
	public ModelAndView si_cm_030_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");

		List<CmMap> 			list		= 	siCm030Service.selectSiCm030List(reqVo);

		// Title
		String[] titleArray = {
				"No.","성분코드","성분명","기본 배합 한도 (%)","배합한도 여부","등록자","등록일"
		};

		// DB Column Name
		String[] columnNmArray = {
				"n_num", "v_con_cd", "v_con_nm_en", "n_max_allow_wt", "v_max_input_apply_yn", "v_reg_user_nm", "v_reg_dtm_excel"
		};

		// Column Width
		mav.addObject("excelFileName"	, "성분배합 한도관리");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}

}
