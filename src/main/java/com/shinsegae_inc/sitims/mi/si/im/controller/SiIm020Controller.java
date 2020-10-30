package com.shinsegae_inc.sitims.mi.si.im.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.mi.si.im.service.SiIm020Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/si/im/020/*")
public class SiIm020Controller extends CommonController{
	
	@Autowired
	private transient SiIm020Service siIm020Service; 
	
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : ODM 등록요청리스트  화면 초기화
    * </pre>
    */
	@RequestMapping("init.do")
	public ModelAndView init(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	return getInitMAV(request,"sitims/views/mi/si/im/020/si_im_020_list");
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : ODM 등록요청리스트  리스트 불러옴
    * </pre>
    */
	@RequestMapping("si_im_020_list.do")
	public ModelAndView si_im_020_list(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		String rStdt = null; 
    	String rEndt = null; 
    	String cStdt = null; 
    	String cEndt = null; 
		
		if(CmFunction.isNotEmpty(reqVo.getString("i_sReqStDate")) || CmFunction.isNotEmpty(reqVo.getString("i_sReqEnDate"))) {
    		rStdt = reqVo.getString("i_sReqStDate").replace("-", "");
    		rEndt = reqVo.getString("i_sReqEnDate").replace("-", "");
    		reqVo.put("i_sReqStDate", rStdt);
    		reqVo.put("i_sReqEnDate", rEndt);

    		if(rStdt.equals("")) reqVo.put("i_sReqStDate", "19010101");
    		if(rEndt.equals("")) reqVo.put("i_sReqEnDate","29991231");
    		
    	}
		if(CmFunction.isNotEmpty(reqVo.getString("i_sConfmStDate")) || CmFunction.isNotEmpty(reqVo.getString("i_sConfmEnDate"))) {
			cStdt = reqVo.getString("i_sConfmStDate").replace("-", "");
			cEndt = reqVo.getString("i_sConfmEnDate").replace("-", "");
			
			reqVo.put("i_sConfmStDate", cStdt);
			reqVo.put("i_sConfmEnDate", cEndt);
			
			if(cStdt.equals("")) reqVo.put("i_sConfmStDate", "19010101");
			if(cEndt.equals("")) reqVo.put("i_sConfmEnDate","29991231");
			
		}
		
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, siIm020Service.selectSiIm020List(reqVo));
	}
	/**
	*
	* @param reqVo
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : ODM 등록요청리스트  수정
	* </pre>
	*/
	@RequestMapping("si_im_020_modify.do")
	public ModelAndView si_im_020_modify(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUpdateUserId",userid); 
		reqVo.put("i_sConFmUserId",userid); 
		
		boolean tmpBoo = false;
		
		if (siIm020Service.CountForSiIm020Modify(reqVo) == 1) {
			
			siIm020Service.modifyForSiIm020(reqVo);
			CmMap tmpObj = siIm020Service.selectSiIm020ForMailSend(reqVo);
			
			reqVo.put("i_sToAddr", cryptoService.decAES(tmpObj.getString("v_email"), true));
			if (reqVo.get("i_sState").equals("CO_ST03")) {
				reqVo.put("i_sSubject", "[TIIMS] 성분요청 기등록 성분으로 등록 되었습니다. - "+tmpObj.getString("v_inci_nm_ko"));
			}else if (reqVo.get("i_sState").equals("CO_ST05")) {
				reqVo.put("i_sSubject", "[TIIMS] 성분요청 반려 되었습니다. - "+tmpObj.getString("v_inci_nm_ko"));
			}

			reqVo.put("i_sContents", this.getMailContents("sitims/views/mi/si/im/020/si_im_020_mail", tmpObj));
			 //메일 로그
			CmMap logVo = new CmMap();
			logVo.put("i_sTitle", reqVo.getString("i_sSubject"));
			logVo.put("i_sContents", reqVo.getString("i_sContents"));
			logVo.put("i_sRevUserIdAll", reqVo.getString("i_sToAddr"));
    		logVo.put("i_sFromUser", userid);
    		logVo.put("i_sRecordId", tmpObj.getString("v_req_con_id"));
	    	commonService.insertMailLog(logVo);
			tmpBoo = this.sendMailwithHtml(reqVo);

		} else {
			return this.makeJsonResult(mav, "success", "해당 ODM등록 요청은 성분코드가 없어 저장 할 수 없습니다.", null);
		}
			
		if(tmpBoo) {
			
			return this.makeJsonResult(mav,"success", "저장 성공", null);
		}else {
			return this.makeJsonResult(mav,"fail", "저장 실패", null);
			
		}
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
	 * Commnets : 엑셀
	 * </pre>
	 */
	@RequestMapping("si_im_020_list_excel.do")
	public ModelAndView si_im_020_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");

		List<CmMap> 			list		= 	siIm020Service.selectSiIm020List(reqVo);

		// Title
		String[] titleArray = {
				"No.","요청일","성분명","Cas No.","등록의견","요청상태","검토의견","승인일","업체명(업체담당자)","담당자 E-mail"
		};

		// DB Column Name
		String[] columnNmArray = {
				"n_num", "v_req_dtm_excel", "v_inci_nm_ko", "v_cas_no", "v_opinion", "v_state_nm", "v_reject_opn","v_confm_dtm_excel","v_user_venor_nm","v_email"
		};

		// Column Width
		mav.addObject("excelFileName"	, "성분배합 한도관리");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 성분DB로 데이터 보내기 전 유효성 검사
    * </pre>
    */
	@RequestMapping("si_im_020_stateValidation.do")
	public ModelAndView si_im_020_stateValidation(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		int result = siIm020Service.getSiIm020Statue(reqVo);
		reqVo.put("resultCount", result);
		return this.makeJsonResult(mav,"success", "유효성 체크완료", reqVo);
	}
	
}
