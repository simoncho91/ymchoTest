package com.shinsegae_inc.sitims.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.SitimsApplication;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CommonService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

/**
 * 메인화면
 * 
 * @author 20200907
 *
 */
@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/tiims/*")
public class TiimsMainController extends CommonController {
	
	@Autowired
	private transient CommonService commonService;
	
    protected Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 메인화면(레이아웃)
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "init.do")
	public ModelAndView main(HttpServletRequest request) throws Exception {

	    ModelAndView mav = getInitMAV(request, "sitims/views/mi/common/layout");
	    mav.addObject("startTime", SitimsApplication.STARTTIME);
		return mav;
	}
	
	/**
	 * 메인화면(홈화면)
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "main/init.do")
	public ModelAndView home(HttpServletRequest request) throws Exception {
        return getInitMAV(request, "sitims/views/mi/common/main");
	}
	/**
	 * AJAX dashboard 호출
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dashboard.do")
	public ModelAndView dashboard(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {

	    ModelAndView mav = new ModelAndView("jsonView");
	    CmFunction.setSessionValue(request, reqVo);
	    CmMap rVo = commonService.getTiimsDashboardInfo(reqVo);
		return this.makeJsonDhtmlxResult(mav, "succ", "succ", rVo);
	}
	
	/**
	 * AJAX 공지사항 정보 호출
	 * @param reqVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNoticeInfo.do")
	public ModelAndView getNoticeInfo(@ModelAttribute() CmMap reqVo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
	    ModelAndView mav = new ModelAndView("jsonView");
	    CmFunction.setSessionValue(request, reqVo); 
	    List<CmMap> rVo = commonService.getNoticeInfo(reqVo);
		return this.makeJsonDhtmlxResult(mav, "succ", "succ", rVo);
	}
	/**
	 * 메인화면(즐겨찾기)
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "userBookMark.do")
	public ModelAndView mainBookMark(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView		mav	=	new ModelAndView("jsonView");
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUserId",userid);
		mav.addObject("bookMarkList",commonService.getUserBookmarkList(reqVo));
        return mav;
	}
	/**
	 * 즐겨찾기 화면
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "bookMarkHome.do")
	public ModelAndView bookMarkHome(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUserId",userid);
		reqVo.put("bookMarkUse", "Y");
		mav.addObject("bookMarkList", commonService.getBookMarkList(reqVo));
		mav.addObject("bookMarkTopList", commonService.getBookMarkTopList(reqVo));
		mav.addObject("userBookmarkList", commonService.getUserBookmarkList(reqVo));
		
		mav.setViewName("sitims/bk/bk_bookmark_list");
        return mav;
	}
	/**
	 * 즐겨찾기 화면 편집
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "bookMarkEdit.do")
	public ModelAndView bookMarkEdit(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUserId", userid);
		if(reqVo.getString("i_sSaveMode").equals("Y")){
			commonService.insertBookMark(reqVo);
			return makeJsonResult(mav, "Success", "등록 성공", "ins");
		}else {
			commonService.deleteBookMark(reqVo);
			return this.makeJsonResult(mav, "Success", "삭제 성공", "del"); 
		}
	}
	 

}
