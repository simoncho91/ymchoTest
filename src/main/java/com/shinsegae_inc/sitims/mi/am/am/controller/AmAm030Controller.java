package com.shinsegae_inc.sitims.mi.am.am.controller;

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
import com.shinsegae_inc.sitims.mi.am.am.service.AmAm030Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/am/am/030/*")
public class AmAm030Controller extends CommonController{
	@Autowired
	private transient AmAm030Service	amam030Service;
	
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 제품권한(부서) 화면 초기화
    * </pre>
    */
	@RequestMapping("init.do")
	public ModelAndView init(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	return getInitMAV(request,"sitims/views/mi/am/am/030/am_am_030_list");
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 제품권한(부서) 리스트 불러옴
    * </pre>
    */
	@RequestMapping("am_am_030_list.do")
	public ModelAndView am_am_030_list(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, amam030Service.selectAmAm030List(reqVo));
	}

   /**
   *
   * @param reqVo
   * @return
   * @throws Exception
   *
   * <pre>
   * Commnets : 제품권한(부서) 등록
   * </pre>
   */
	@RequestMapping("am_am_030_reg.do")
	public ModelAndView am_am_030_reg(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		//Set Data LoginService 
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUpdateUserId",userid); 
		reqVo.put("i_sRegUserId",userid);

	 
		  if(amam030Service.selectCountForProdDept(reqVo) == 0) {
			 amam030Service.regForProdDept(reqVo); 
		  }else { 
			 return this.makeJsonResult(mav,"error", "등록하려는 브랜드에 이미 부서가 지정되어있습니다. ", null);
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
   * Commnets : 제품권한(부서) 삭제
   * </pre>
   */
	@RequestMapping("am_am_030_del.do")
	public ModelAndView am_am_030_del(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		
				if(amam030Service.selectCountForProdDept(reqVo) == 1) {
					 amam030Service.delForProdDept(reqVo);
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
   * Commnets : 제품권한(부서) 콤보박스 생성을 위한 부서 리스트
   * </pre>
   */
	@RequestMapping("am_am_030_getDeptList.do")
	public ModelAndView am_am_030_getDeptList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
	 return this.makeJsonDhtmlxResult(mav, "Success", "성공", amam030Service.getDeptForComboBox(reqVo));
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
	@RequestMapping("am_am_030_list_excel.do")
	public ModelAndView am_am_030_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");

		List<CmMap> 			list		= 	amam030Service.selectAmAm030List(reqVo);

		// Title
		String[] titleArray = {
				"No.","브랜드","부서","등록자","등록일","수정자","수정일"
		};




		// DB Column Name
		String[] columnNmArray = {
				"n_num","v_brand_nm","v_dept_nm","v_reg_user_id", "v_reg_dtm_excel", "v_update_user_id","v_update_dtm_excel"
		};

		// Column Width
		mav.addObject("excelFileName"	, "제품조회 권한관리(부서)");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
		

	
}
