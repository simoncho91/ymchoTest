package com.shinsegae_inc.swaf.admin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.service.RoleUserService;
import com.shinsegae_inc.swaf.admin.service.UserService;
import com.shinsegae_inc.swaf.common.service.AttachService;

/**
 * 사용자 관리
 * 
 * @author 072246
 *
 */
@Controller
@RequestMapping("/admin/user/*")
public class UserController extends SwafDhtmlxController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleUserService roleUserService;
	
	@Autowired
	private AttachService attachService;;

	/**
	 * 사용자 관리 화면
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "init.do")
	public ModelAndView init(HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/user");
	}
	
	
	/**
	 * 사용자 조회
	 * 
	 * @param reqParamMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "selectList.do")
    public ModelAndView selectList(ReqParamMap reqParamMap) throws Exception {
    	
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", userService.selectList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	
    	return mav;
    }
	
	/**
	 * 등록
	 * 
	 * @param reqParamMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "insert.do")
	public ModelAndView insert(ReqParamMap reqParamMap) throws Exception {
		
		ModelAndView mav = new ModelAndView("jsonView");
		userService.insert(reqParamMap.getParamMap());
		setSuccMsg(mav);
		
		return mav;
	}
	
	/**
	 * 수정
	 * 
	 * @param reqParamMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "update.do")
	public ModelAndView update(ReqParamMap reqParamMap) throws Exception {
		
		ModelAndView mav = new ModelAndView("jsonView");
		userService.update(reqParamMap.getParamMap());
		setSuccMsg(mav);
		
		return mav;
	}

	/**
	 * 개인정보 수정
	 *
	 * @param reqParamMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "updatePwd.do")
	public ModelAndView updatePwd(ReqParamMap reqParamMap) throws Exception {

		ModelAndView mav = new ModelAndView("jsonView");
		userService.updatePwd(reqParamMap.getParamMap());
		setSuccMsg(mav);

		return mav;
	}
	
	/**
	 * 삭제
	 * 
	 * @param reqParamMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "delete.do")
	public ModelAndView delete(ReqParamMap reqParamMap) throws Exception {

		log.debug("reqParamMap.getParamMap() : " + reqParamMap.getParamMap());

		ModelAndView mav = new ModelAndView("jsonView");
		roleUserService.deleteRoleUser(reqParamMap.getParamMap());
		userService.delete(reqParamMap.getParamMap());
		
		Map<Object, Object> tmpMap = reqParamMap.getParamMap();
		attachService.deleteAttach((String)tmpMap.get("ATCH_NO"));
		setSuccMsg(mav);
		
		return mav;
	}

    /**
     * 추가권한 등록
     * 
     * @param reqParamMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = "saveDetail.do")
    public ModelAndView saveDetail(ReqParamMap reqParamMap) throws Exception {
        
        ModelAndView mav = new ModelAndView("jsonView");
        userService.saveDetail(reqParamMap.getParamMap());
        setSuccMsg(mav);
        
        return mav;
    }

}
