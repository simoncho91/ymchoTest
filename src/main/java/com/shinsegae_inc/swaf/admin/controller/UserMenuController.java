package com.shinsegae_inc.swaf.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.core.vo.AuthProcDetailVO;
import com.shinsegae_inc.core.vo.AuthProcVO;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.service.UserMenuService;
import com.shinsegae_inc.swaf.log.service.AuthProcService;

@Controller("dhtmlx.UserMenuController")
public class UserMenuController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.UserMenuService")
	private UserMenuService userMenuService;

	@Resource(name="authProcService")
    private AuthProcService authProcService;

	/**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 사용자별메뉴관리 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/userMenu/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/userMenu");
    }

    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 전체사용자 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/userMenu/selectUserList.do")
    public ModelAndView selectUserList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", userMenuService.selectList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
 
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 전체메뉴 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/userMenu/selectMenuList.do")
    public ModelAndView selectMenuList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", userMenuService.selectMenuList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
 
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 사용자메뉴 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/userMenu/selectUserMenuList.do")
    public ModelAndView selectUserMenuList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		//reqParamMap.getParamMap().put("ROLE_NO", reqParamMap.getParamMap().get("ROW_ROLE_NO"));
		mav.addObject("result", userMenuService.selectUserMenuList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
 
    	return mav;
    }
    
   /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 사용자메뉴 저장
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/userMenu/saveMenu.do")
    public ModelAndView saveMenu(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	userMenuService.saveUserMenu(paramMap);
   		setSuccMsg(mav);
   		
    	AuthProcVO vo = new AuthProcVO();
    	vo.setProcMenuCd("SYAU0050");
    	vo.setProcRsnCnts("save");
    	vo.setProcSysId(paramMap.get("SYS_ID").toString());
    	List<AuthProcDetailVO> chgLogDetails = new ArrayList<AuthProcDetailVO>();
		AuthProcDetailVO dVo = new AuthProcDetailVO();
		dVo.setProcClsCd("01");	//저장
		dVo.setTgtUserNo(paramMap.get("USER_NO").toString());
		dVo.setMenuCd(paramMap.get("MENU_CD").toString());
		chgLogDetails.add(dVo);
    	vo.setChgLogDetails(chgLogDetails);

    	authProcService.saveLog(request, vo);
    	
    	return mav;
    }
    
    /**
    *
    * @param request
    * @param response
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 사용자메뉴 삭제
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/userMenu/deleteMenu.do")
    public ModelAndView deleteMenu(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();

		mav = new ModelAndView("jsonView");
		mav.addObject("result", userMenuService.deleteUserMenu(paramMap));
		setSuccMsg(mav);
		
    	AuthProcVO vo = new AuthProcVO();
    	vo.setProcMenuCd("SYAU0050");
    	vo.setProcRsnCnts("delete");
    	vo.setProcSysId(paramMap.get("SYS_ID").toString());

    	List<AuthProcDetailVO> chgLogDetails = new ArrayList<AuthProcDetailVO>();
		AuthProcDetailVO dVo = new AuthProcDetailVO();
		dVo.setProcClsCd("02");	//삭제
		dVo.setTgtUserNo(paramMap.get("USER_NO").toString());
		dVo.setMenuCd(paramMap.get("MENU_CD").toString());
		chgLogDetails.add(dVo);
    	vo.setChgLogDetails(chgLogDetails);

    	authProcService.saveLog(request, vo);

    	return mav;
    	
    }
}
