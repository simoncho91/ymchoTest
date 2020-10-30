package com.shinsegae_inc.sitims.mi.am.um.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.am.um.service.AmUm030Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/am/um/030/*")
public class AmUm030Controller extends CommonController{
	@Autowired
	private transient AmUm030Service	amum030Service;
	
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : RA관리자 화면 초기화
    * </pre>
    */
	@RequestMapping("init.do")
	public ModelAndView init(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	return getInitMAV(request,"sitims/views/mi/am/um/030/am_um_030_list");
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : RA 관리자 리스트 불러옴
    * </pre>
    */
	@RequestMapping("am_um_030_list_ajax.do")
	public ModelAndView am_um_030_list_ajax(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		if(reqVo.getStringArray("i_sNationList[]") != null) {
			String[] str = reqVo.getStringArray("i_sNationList[]");
			reqVo.put("i_arrStr", str);
		}
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, amum030Service.selectAmUm030List(reqVo));
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 국가 목록 조회
    * </pre>
    */
   @PostMapping(value="CmCodeListForRaNation.do")
   public ModelAndView CmCodeListForRaNation(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
   	ModelAndView mav = new ModelAndView("jsonView");
   	
   	List<CmMap> list = amum030Service.getCmCodeListForRaNation(reqVo);

   	mav.addObject("result", list);
   	setSuccMsg(mav);
   	return mav;
   }

   /**
   *
   * @param reqVo
   * @return
   * @throws Exception
   *
   * <pre>
   * Commnets : RA관리자 등록
   * </pre>
   */
	@RequestMapping("am_um_030_reg.do")
	public ModelAndView am_um_030_reg(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		//Set Data LoginService 
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUpdateUserId",userid); 
		reqVo.put("i_sRegUserId",userid);
		reqVo.put("isNot_regRa", 1);

	
		  
		  if(amum030Service.getSelectCountForRa(reqVo) == 0) {
			  amum030Service.regForRaAdmin(reqVo);
		  }else {
			  return this.makeJsonResult(mav,"error", "등록하려는 국가, 브랜드에 이미 관리자가 지정되어있습니다. ", null);
		  }
		  
	 
	 return this.makeJsonResult(mav, "Success", "등록 성공", null);
		
	}
   /**
   *
   * @param reqVo
   * @return
   * @throws Exception
   *
   * <pre>
   * Commnets : RA관리자 삭제
   * </pre>
   */
	@RequestMapping("am_um_030_del.do")
	public ModelAndView am_um_030_del(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		 
		if(amum030Service.getSelectCountForRa(reqVo) == 1) {
			 amum030Service.delForRaAdmin(reqVo);
		}else {
			return this.makeJsonResult(mav,"error", "해당 선택사항에 삭제할 아이디가 없습니다.", null); 
		}
			 
		 
		 
	 return this.makeJsonResult(mav, "Success", "삭제 성공", null);
	}
	


	/**
	*
	* @param reqVo
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : RA관리자 수정
	* </pre>
	*/
	@RequestMapping("am_um_030_modify.do")
	public ModelAndView am_um_030_modify(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUpdateUserId",userid); 
		
			if(amum030Service.getSelectCountForRa(reqVo) == 0) {
				amum030Service.modifyForRaAdmin(reqVo);
			}else {
				return this.makeJsonResult(mav,"error", "동일한 아이디 입니다.", null); 
			}
			
		
		return this.makeJsonResult(mav,"Success", "수정 성공", null);
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
	@RequestMapping("am_um_030_list_excel.do")
	public ModelAndView am_um_030_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		boolean nationNull = reqVo.getString("i_sNationList").equals("");
		if(reqVo.getString("i_sNationList") != null && !nationNull) {
			
			String str = reqVo.getString("i_sNationList");
			String[] array = str.split(",");
			reqVo.put("i_arrStr", array);
		}
		List<CmMap> 			list		= 	amum030Service.selectAmUm030List(reqVo);

		// Title
		String[] titleArray = {
				"No.","국가","브랜드","담당자","등록자","등록일","수정자","수정일"
		};

		// DB Column Name
		String[] columnNmArray = {
				"n_num", "v_nation_nm", "v_brand_nm", "v_user_nm", "v_reg_user_id", "v_reg_dtm_excel", "v_update_user_id","v_update_dtm_excel"
		};

		// Column Width
		mav.addObject("excelFileName"	, "RA 관리");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
	
	
}
