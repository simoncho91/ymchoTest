package com.shinsegae_inc.sitims.mi.si.im.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.si.im.service.SiIm030Service;


@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/im/030/*")
public class SiIm030Controller extends CommonController{
	@Resource(name="SiIm030Service")
	transient SiIm030Service siIm030service; 
	
	/**
	 * 
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
    * <pre>
    * Commnets : 배합목적DB 목록 페이지
    * </pre>
	 */
    @RequestMapping(value="/init.do")
	public ModelAndView si_im_030_list (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getInitMAV(request,"sitims/views/mi/si/im/030/si_im_030_list");
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
     * Commnets : 배합목적 목록 조회
     * </pre>
     */
    @RequestMapping(value="/selectList.do")
    public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");    	
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, siIm030service.selectSiIm030List(reqVo));
    }
    
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 배합목적 등록
    * </pre>
    */
 	@RequestMapping(value="si_im_030_save.do")
 	public ModelAndView si_im_030_save(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
 		ModelAndView mav = new ModelAndView("jsonView");

		//Set Data LoginService 
		reqVo.put("i_sRegUserId",SessionUtils.getUserNo());
		reqVo.put("i_sUpdateUserId",SessionUtils.getUserNo());
		siIm030service.insertSiIm030List(reqVo);
 	 return this.makeJsonResult(mav, "Success", "등록 성공", null);
 		
 	}
 	
 	/**
 	*
 	* @param reqVo
 	* @return
 	* @throws Exception
 	*
 	* <pre>
 	* Commnets : 배합목적 수정
 	* </pre>
 	*/
 	@RequestMapping(value="si_im_030_modify.do")
 	public ModelAndView si_im_030_modify(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
 		ModelAndView mav = new ModelAndView("jsonView");
 		String userid = SessionUtils.getUserNo();
 		reqVo.put("i_sUpdateUserId",userid); 
 		
		siIm030service.updateSiIm030List(reqVo);
 		return this.makeJsonResult(mav,"error", "수정 성공", null);
 	}
 	
 	/**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 배합목적 삭제
    * </pre>
    */
 	@RequestMapping(value="si_im_030_del.do")
 	public ModelAndView si_im_030_del(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
 		ModelAndView mav = new ModelAndView("jsonView");
 		
		siIm030service.deleteSiIm030List(reqVo);
		return this.makeJsonResult(mav, "Success", "삭제 성공", null);
 	}
 	/**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 단어 체크
    * </pre>
    */
	@RequestMapping(value="check_word.do")
	public ModelAndView check_word(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		int	cnt	=  siIm030service.getSiIm030ListCount(reqVo);
		return this.makeJsonResult(mav, "Success", "조회 성공", String.valueOf(cnt));
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
	 * Commnets : 배합목적등록 조회 엑셀
	 * </pre>
	 */
	@RequestMapping(value="/si_im_030_list_excel.do")
	public ModelAndView si_im_030_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		List<CmMap> 			list		= 	siIm030service.selectSiIm030List(reqVo);
		String flag = reqVo.getString("i_sFlagExcelAll");
		// Title
		String[] titleArray = {
			 "FUNCTION 코드"
			, "기능명(영어)"
			, "기능명(한글)"
			, "기능명(중문)"
			, "기능명(불어)"
			, "기능명(일어)"
		};




		// DB Column Name
		String[] columnNmArray = {
			 "v_func_id"
			, "v_func_nm_en"
			, "v_func_nm_ko"
			, "v_func_nm_cn"
			, "v_func_nm_eu"
			, "v_func_nm_jp"
		};

		// Column Width
	//	int[]	columnWidth	=	{30, 15, 15, 80, 20, 10, 12};
		if(flag.equals("Y")) {
			mav.addObject("excelFileName"	, "배합목적등록[전체 페이지]");
			} else
			mav.addObject("excelFileName"	, "배합목적등록[현재 페이지]");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
	//	mav.addObject("columnWidth"		, columnWidth);
		mav.addObject("excelData"		, list);

		return mav;
	}
}
