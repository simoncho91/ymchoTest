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
import com.shinsegae_inc.core.support.annotation.AjaxParamAnnotation;
import com.shinsegae_inc.core.vo.AuthProcDetailVO;
import com.shinsegae_inc.core.vo.AuthProcVO;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.service.RoleService;
import com.shinsegae_inc.swaf.admin.service.RoleUserService;
import com.shinsegae_inc.swaf.log.service.AuthProcService;

@Controller("dhtmlx.RoleController")
public class RoleController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.RoleService")
	private RoleService roleService;

	@Resource(name="authProcService")
    private AuthProcService authProcService;
	
	@Resource(name="dhtmlx.RoleUserService")
    private RoleUserService roleUserService;

	/**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한그룹관리 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/role/init.do")
    public ModelAndView init (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/role");
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
    @PostMapping(value="/dhtmlx/admin/role/selectList.do")
    public ModelAndView selectList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleService.selectList(reqParamMap.getParamMap()));
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
    @PostMapping(value="/dhtmlx/admin/role/selectMenuList.do")
    public ModelAndView selectMenuList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleService.selectMenuList(reqParamMap.getParamMap()));
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
    * Commnets : 권한그룹메뉴 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/role/selectRoleMenuList.do")
    public ModelAndView selectRoleMenuList(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		//reqParamMap.getParamMap().put("ROLE_NO", reqParamMap.getParamMap().get("ROW_ROLE_NO"));
		mav.addObject("result", roleService.selectRoleMenuList(reqParamMap.getParamMap()));
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
    * Commnets : 권한그룹 저장
    * </pre>
    */    
    @PostMapping(value="/dhtmlx/admin/role/save.do")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	//paramMap.put("URL",StringEscapeUtils.unescapeHtml((String)paramMap.get("URL")));
    	roleService.save(paramMap);
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
    * Commnets : 권한그룹메뉴 저장
    * </pre>
    */    
    @PostMapping(value="/dhtmlx/admin/role/saveMenu.do")
    public ModelAndView saveMenu(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();
    	roleService.saveMenu(paramMap);
    	
    	AuthProcVO vo = new AuthProcVO();
    	vo.setProcMenuCd("SYAU0020");
    	vo.setProcRsnCnts("save");
    	vo.setProcSysId(paramMap.get("SYS_ID").toString());

    	List<AuthProcDetailVO> chgLogDetails = new ArrayList<AuthProcDetailVO>();
    	List<Map> roleUserList = (List<Map>)roleUserService.selectRoleUserList(paramMap).get("data");
		for( Map roleUser : roleUserList ) {
			AuthProcDetailVO dVo = new AuthProcDetailVO();
			dVo.setRoleNo(paramMap.get("ROLE_NO").toString());
			dVo.setProcClsCd("01");	//저장
			dVo.setTgtUserNo(roleUser.get("USER_NO").toString());
			dVo.setMenuCd(paramMap.get("MENU_CD").toString());
			chgLogDetails.add(dVo);
			
		}
    	vo.setChgLogDetails(chgLogDetails);

    	authProcService.saveLog(request, vo);
    	
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
    * Commnets : 권한그룹 삭제
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/role/delete.do")
    public ModelAndView delte(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleService.delete(reqParamMap.getParamMap()));

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
    * Commnets : 권한그룹메뉴 삭제
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/role/deleteMenu.do")
    @AjaxParamAnnotation
    public ModelAndView delteMenu(HttpServletRequest request, HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = null;
    	Map<Object, Object> paramMap = reqParamMap.getParamMap();

		mav = new ModelAndView("jsonView");
		mav.addObject("result", roleService.deleteMenu(paramMap));

    	AuthProcVO vo = new AuthProcVO();
    	vo.setProcMenuCd("SYAU0020");
    	vo.setProcRsnCnts("delete");
    	vo.setProcSysId(paramMap.get("SYS_ID").toString());

    	List<AuthProcDetailVO> chgLogDetails = new ArrayList<AuthProcDetailVO>();
    	List<Map> roleUserList = (List<Map>)roleUserService.selectRoleUserList(paramMap).get("data");
		for( Map roleUser : roleUserList ) {
			AuthProcDetailVO dVo = new AuthProcDetailVO();
			dVo.setRoleNo(paramMap.get("ROLE_NO").toString());
			dVo.setProcClsCd("02");	//삭제
			dVo.setTgtUserNo(roleUser.get("USER_NO").toString());
			dVo.setMenuCd(paramMap.get("MENU_CD").toString());
			chgLogDetails.add(dVo);
			
		}
    	vo.setChgLogDetails(chgLogDetails);

    	authProcService.saveLog(request, vo);

		
		setSuccMsg(mav);
 
    	return mav;
    	
    }
}
