package com.shinsegae_inc.swaf.common.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.dhtmlx.support.xml2excel.ExcelWriter;
import com.shinsegae_inc.dhtmlx.support.xml2pdf.PDFWriter;
import com.shinsegae_inc.swaf.common.service.DhtmlxService;

/**
*
* @author  : wjLee
* @since   : 2018. 6. 14.
* @version : 1.0
*
* <pre>
* Comments : 초기화면 등 공통화면 용 컨트롤러
* </pre>
*/
@Controller
public class CommonController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dhtmlxService")
	private DhtmlxService dhtmlxService;	

	/**
	 * DataStore 조회(한꺼번에)
	 * @return
	 * @throws Exception
	 */
    @PostMapping(value="/dhtmlx/common/selectDs.do")
    public ModelAndView selectDs(ReqParamMap reqParamMap) throws Exception {
    	ModelAndView mav = dhtmlxService.selectDs(reqParamMap);
		mav.addObject("msgCode", "success");
    	return mav;
    }
    
    /**
     * DataStore 조회(한꺼번에)
     * @return
     * @throws Exception
     */
    @PostMapping(value="/dhtmlx/common/selectDsForCombo.do")
    public ModelAndView selectDsForCombo(ReqParamMap reqParamMap) throws Exception {
        ModelAndView mav = dhtmlxService.selectDsForCombo(reqParamMap);
        mav.addObject("msgCode", "success");
        return mav;
    }
    
    /**
     * DataStore 조회(한꺼번에)
     * @return
     * @throws Exception
     */
    @PostMapping(value="/dhtmlx6/common/selectDsForCombo.do")
    public ModelAndView selectDsForCombo6(ReqParamMap reqParamMap) throws Exception {
        ModelAndView mav = dhtmlxService.selectDsForSelectBox(reqParamMap);
        mav.addObject("msgCode", "success");
        return mav;
    }
    
    /**
    *
    * @param reqParamMap
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 코드 목록 조회
    * </pre>
    */
    @PostMapping(value="/dhtmlx/common/selectCodeList.do")
	public ModelAndView selectCodeList(ReqParamMap reqParamMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		mav.addObject("result", dhtmlxService.selectCodeList(reqParamMap.getParamMap()));
		setSuccMsg(mav);
		return mav;
   }

	@RequestMapping(value = "/dhtmlx/sessionValidException.do")
	@ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView dhtmlxSessionValidException(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav				= new ModelAndView();
		String			returnMessage	= "로그인 세션이 만료되었거나 정상적으로 로그인 되지 않았습니다.";
		
		if (null != request.getHeader("X-Requested-With") && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			mav.setViewName("jsonView");
			mav.addObject("returnMessage", returnMessage);
		} else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script>alert('"+returnMessage+"');location.href='/login/init.do';</script>");
			return null;
		}

		return mav;
	}

    /**
     * Exporting Data to Excel
     * @param request
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value="/common/pdfGenerate.do")
    public void pdfGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String xml = request.getParameter("grid_xml");
        xml = URLDecoder.decode(xml, "UTF-8");
        (new PDFWriter()).generate(xml, response);
    }
    
    /**
     * Exporting Data to Excel
     * @param request
     * @param response
     * @return void
     * @throws IOException
     */
    @RequestMapping(value="/common/excelGenerate.do")
    public void excelGenerate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String xml = request.getParameter("grid_xml");
        xml = URLDecoder.decode(xml, "UTF-8");
        (new ExcelWriter()).generate(xml, response);
    }
}
