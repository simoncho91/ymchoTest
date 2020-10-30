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
import com.shinsegae_inc.swaf.admin.service.RoleService;
import com.shinsegae_inc.swaf.admin.service.RoleUserService;
import com.shinsegae_inc.swaf.log.service.AuthProcService;

@Controller("dhtmlx.RoleUserController")
public class RoleUserController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.RoleUserService")
	private RoleUserService roleUserService;

	@Resource(name="authProcService")
    private AuthProcService authProcService;
	
	
	@Resource(name="dhtmlx.RoleService")
    private RoleService roleService;

    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한그룹별사용자관리 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/roleUser/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/roleUser");
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
    * Commnets : 권한그룹 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/roleUser/selectList.do")
    public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleUserService.selectList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
 
    	return mav;
    }
    
    /**
    *
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 전체사용자 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/roleUser/selectUserList.do")
    public ModelAndView selectUserList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleUserService.selectUserList(reqParamMap.getParamMap()));
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
    * Commnets : 권한그룹사용자 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/roleUser/selectRoleUserList.do")
    public ModelAndView selectRoleUserList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		//reqParamMap.getParamMap().put("ROLE_NO", reqParamMap.getParamMap().get("ROW_ROLE_NO"));
		mav.addObject("result", roleUserService.selectRoleUserList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
 
    	return mav;
    }
    
   /**
    *
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한그룹사용자 저장
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/roleUser/saveUser.do")
    public ModelAndView saveUser(HttpServletRequest request, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	roleUserService.saveRoleUser(paramMap);
    	
		if (SessionUtils.isAuthenticated()) {
			Map<String, Object> userMap = (Map<String, Object>) SessionUtils.getAuthenticatedUser();
			paramMap.put("SYS_ID", (String) userMap.get("SYS_ID"));
		}		
    	AuthProcVO vo = new AuthProcVO();
    	vo.setProcMenuCd("SYAU0040");
    	vo.setProcRsnCnts("save");
    	
    	List<AuthProcDetailVO> chgLogDetails = new ArrayList<AuthProcDetailVO>();
		List<Map> roleMenuList = (List<Map>) roleService.selectRoleMenuList(paramMap).get("data");
		for( Map roleMenu : roleMenuList ) {
			AuthProcDetailVO dVo = new AuthProcDetailVO();
			dVo.setRoleNo(paramMap.get("ROLE_NO").toString());
			dVo.setProcClsCd("01");	//저장
			dVo.setTgtUserNo(paramMap.get("USER_NO").toString());
			dVo.setMenuCd(roleMenu.get("MENU_CD").toString());
			chgLogDetails.add(dVo);
			
		}
    	vo.setChgLogDetails(chgLogDetails);

    	authProcService.saveLog(request, vo);
    	
    	setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param response
    * @param reqParamMap
    * @return ModelAndView(jsonView)
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한그룹사용자 삭제
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/roleUser/deleteUser.do")
    public ModelAndView delteUser(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleUserService.deleteRoleUser(paramMap));

		if (SessionUtils.isAuthenticated()) {
			Map<String, Object> userMap = (Map<String, Object>) SessionUtils.getAuthenticatedUser();
			paramMap.put("SYS_ID", (String) userMap.get("SYS_ID"));
		}		
    	AuthProcVO vo = new AuthProcVO();
    	vo.setProcMenuCd("SYAU0040");
    	vo.setProcRsnCnts("delete");
    	
    	List<AuthProcDetailVO> chgLogDetails = new ArrayList<AuthProcDetailVO>();
		List<Map> roleMenuList = (List<Map>) roleService.selectRoleMenuList(paramMap).get("data");
		for( Map roleMenu : roleMenuList ) {
			AuthProcDetailVO dVo = new AuthProcDetailVO();
			dVo.setRoleNo(paramMap.get("ROLE_NO").toString());
			dVo.setProcClsCd("02");	//삭제
			dVo.setTgtUserNo(paramMap.get("USER_NO").toString());
			dVo.setMenuCd(roleMenu.get("MENU_CD").toString());
			chgLogDetails.add(dVo);
			
		}
    	vo.setChgLogDetails(chgLogDetails);

    	
    	authProcService.saveLog(request, vo);
		
		setSuccMsg(mav);
 
    	return mav;
    	
    }
}
