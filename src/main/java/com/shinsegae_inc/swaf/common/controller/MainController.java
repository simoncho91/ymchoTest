package com.shinsegae_inc.swaf.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.SitimsApplication;
import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.common.service.MainService;

/**
 * 메인화면
 * 
 * @author 072246
 *
 */
@Controller
public class MainController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${globals.domain}")
    private String domain;
    
    @Autowired
    private MainService mainService;
    
	/**
	 * 메인화면(레이아웃)
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/main/init.do")
	public ModelAndView main(HttpServletRequest request) throws Exception {
	    log.debug("###domain = " + domain);

	    ModelAndView mav = getInitMAV(request, "swaf/common/layout");
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
	@GetMapping(value = "/home/init.do")
	public ModelAndView home(HttpServletRequest request) throws Exception {
        return getInitMAV(request, "swaf/common/home");
	}


}
