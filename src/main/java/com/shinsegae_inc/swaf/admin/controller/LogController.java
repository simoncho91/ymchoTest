package com.shinsegae_inc.swaf.admin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.admin.service.LogService;

@Controller("dhtmlx.LogController")
public class LogController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlx.LogService")
	private LogService logService;

    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 접속로그조회 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/accessLog/init.do")
    public ModelAndView initAccessLog (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/accessLog");
    }

    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 접속로그 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/accessLog/selectList.do")
    public ModelAndView selectAccessLogList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectAccessLogList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 액션로그조회 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/actionLog/init.do")
    public ModelAndView initActionLog (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/actionLog");
    }

    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 액션로그 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/actionLog/selectList.do")
    public ModelAndView selectActionLogList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectActionLogList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
     *
     * @param reqParamMap
     * @return
     * @throws Exception
     *
     * <pre>
     * Commnets : 액션로그 목록 상세 조회
     * </pre>
     */
    @PostMapping(value="/dhtmlx/admin/actionLog/selectDetailList.do")
    public ModelAndView selectActionLogDetailList(ReqParamMap reqParamMap) throws Exception {
        ModelAndView mav = new ModelAndView("jsonView");
        mav.addObject("result", logService.selectActionLogDetailList(reqParamMap.getParamMap()));
        setSuccMsg(mav);
        return mav;
    }
    
    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 시스템에러로그조회 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/errorLog/init.do")
    public ModelAndView initErrorLog (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/errorLog");
    }

    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 시스템에러로그 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/errorLog/selectList.do")
    public ModelAndView selectErrorLogList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectErrorLogList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 개인정보로그조회 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/pinfLog/init.do")
    public ModelAndView initPinfLog (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/pinfLog");
    }

    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 개인정보로그 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/pinfLog/selectList.do")
    public ModelAndView selectPinfLogList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectPinfLogList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 개인정보로그 대상 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/pinfLog/selectDetailList.do")
    public ModelAndView selectPinfLogDetailList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectPinfLogDetailList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param request
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한변경로그조회 화면 초기화
    * </pre>
    */
    @GetMapping("/dhtmlx/admin/authLog/init.do")
    public ModelAndView initAuthLog (HttpServletRequest request) throws Exception {
		return getInitMAV(request, "swaf/admin/authLog");
    }
    
    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한변경로그 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/authLog/selectList.do")
    public ModelAndView selectAuthLogList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectAuthLogList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 권한변경로그 대상 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/admin/authLog/selectDetailList.do")
    public ModelAndView selectDetailList(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", logService.selectAuthLogDetailList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
    	return mav;
    }
    
}
