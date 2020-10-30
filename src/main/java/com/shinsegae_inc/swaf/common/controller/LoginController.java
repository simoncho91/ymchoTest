package com.shinsegae_inc.swaf.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.common.service.LoginService;
import com.shinsegae_inc.swaf.log.service.AccessLogService;

/**
 * 로그인 처리
 * 
 * @author 072246
 *
 * findbugs 오탐 : [SERVLET_PARAMETER : ] 
 */
//@edu.umd.cs.findbugs.annotations.SuppressFBWarnings("SERVLET_PARAMETER")
@Controller
@RequestMapping("/login/*")
public class LoginController extends SwafDhtmlxController {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginService loginService;

	@Autowired
	private AccessLogService accessLogService;
	
	
	/**
	 * 로그인 화면
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */	
	@RequestMapping(value = "init.do")
	public String login() {
		return "swaf/common/login";
	}

    /**
    * 로그인 처리
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets :
    * </pre>
    */
    @PostMapping(value = "login.do")
    public ModelAndView login(HttpServletRequest request) throws Exception {

        String loginId = request.getParameter("loginId");
        String loginPwd = request.getParameter("loginPwd");
        
        ModelAndView mav = new ModelAndView("jsonView");
        if (loginService.selectLogin(loginId, loginPwd)) {
        	accessLogService.insertLog(request, "01");		// 로그인 성공
            setSuccMsg(mav);
        } else {
            mav.addObject("msgCode", getMessage("Err.E028"));
        }
        return mav;
    }
	
	/**
	 * 로그 아웃
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "logout.do")
	public String logout(HttpServletRequest request, @RequestParam("sysId") String sysId, @RequestParam("userNo") String userNo) throws Exception {
    	accessLogService.insertLog(request, "08");		// 로그 아웃
		request.getSession().invalidate();
		return "redirect:/login/init.do";
	}
    /**
     * 비밀번호 초기화 인증요청 및 유효성 검사
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "initPwdSendCertifiNumber.do")
    public ModelAndView initPwdSendCertifiNumber(HttpServletRequest request) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> tmpMap = new HashMap<>();
    	tmpMap.put("PHONE_NO", request.getParameter("PHONE_NO"));
    	tmpMap.put("LOGIN_ID", request.getParameter("LOGIN_ID"));

		Map Map = loginService.checkEqUserPhone(tmpMap);
		if(Map.get("matching") !=null) {
			if (Map.get("matching").toString().equals("Y")) {
				Map.put("statue", "succ");
				Map.put("msg", "인증번호를 발송 했습니다.");
				mav.addObject("result",Map);
			}else if(Map.get("matching").toString().equals("N")) {
				Map.put("statue", "fail");
				Map.put("msg", "저장되어있는 휴대폰번호와 입력한 정보가 일치하지 않습니다.");
				mav.addObject("result", Map);
			}
		}else {
			mav.addObject("result", Map);
		}

    	return mav;
    }
    /**
     * 비밀번호 초기화
     * 
     * @param reqParamMap
     * @return
     * @throws Exception
     */
    @PostMapping(value = "initPwd.do")
    public ModelAndView initPwd(HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
    	Map<Object, Object> tmpMap = new HashMap<>();
    	tmpMap.put("CERT_NUM", request.getParameter("CERT_NUM"));
    	tmpMap.put("LOGIN_ID", request.getParameter("LOGIN_ID"));
        Map resultMap =  loginService.initPwd(tmpMap);
        mav.addObject("result",resultMap);
        return mav;
    }
	
}
