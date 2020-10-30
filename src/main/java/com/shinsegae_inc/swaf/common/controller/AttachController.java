package com.shinsegae_inc.swaf.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.core.support.annotation.AjaxParamAnnotation;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.swaf.common.service.AttachService;


@Controller
public class AttachController extends SwafDhtmlxController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Resource(name = "AttachService")
    private AttachService attachService;
    
	
	/**
	*
	* @param request
	* @param mav 
	*
	* <pre>
	* Commnets : DHTMLX 파일업로드 (건 단위로 서버 호출)
	* </pre>
	*/
	@RequestMapping(value = "/attach/dhtmlxUpload.do")
	public ModelAndView dhtmlXUploadTest(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		// vault 첨부파일 컨포넌트 로딩시 최초로 upload url을 호출하는 현상 조치
		if(!(request instanceof MultipartHttpServletRequest)) {
			return null;
		}
		
		HashMap rsltMap = (HashMap)attachService.fileUpload(request);
		
		log.debug("atchNo : "+request.getParameter("atchNo"));
		
		List succList = (ArrayList)rsltMap.get("succList");
		List errorList = (ArrayList)rsltMap.get("errorList");
		int succCnt = succList.size();

		// 성공시 extra로 atachNo값 전달
		if(errorList.size() < 1){
			mav.addObject("state", true);
			mav.addObject("name", rsltMap.get("atchNoSeq")); // vault 컴포넌트의 file.serverName 오브젝트에 첨부디테일테이블의 key값을 담는다
			rsltMap.put("atachNo", rsltMap.get("atchNo"));
		}else{
			mav.addObject("state", false);
		}	
		mav.addObject("extra", rsltMap);
		
		return mav;
	}
	
	/**
	*
	* @param request
	* @param mav 
	*
	* <pre>
	* Commnets : DHTMLX 첨부파일 정보 업데이트
	* </pre>
	*/
	@PostMapping(value = "/attach/updateAttachInfo.do")
	public ModelAndView updateAttachInfo(HttpServletRequest request, ReqParamMap reqParamMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		attachService.updateAttachInfo(reqParamMap.getParamMap());
    	
   		setSuccMsg(mav);
    	return mav;
	}
	
	/**
	*
	* @param request
	* @param mav 
	*
	* <pre>
	* Commnets : DHTMLX 첨부파일 목록 조회
	* </pre>
	*/
	@PostMapping(value = "/attach/selectAttachList.do")
	public ModelAndView selectAttachList(HttpServletRequest request, ReqParamMap reqParamMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
			
		mav.addObject("result", attachService.selectAttachList(reqParamMap.getParamMap()));
    	
   		setSuccMsg(mav);
    	return mav;
	}
	
	/**
	*
	* @param request
	* @param mav 
	*
	* <pre>
	* Commnets : DHTMLX 첨부파일 삭제
	* </pre>
	*/
	@PostMapping(value = "/attach/deleteAttach.do")
	public ModelAndView deleteAttach(HttpServletRequest request, ReqParamMap reqParamMap) throws Exception {
		ModelAndView mav = new ModelAndView("jsonView");
		
		/*Map attachInfo = new HashMap();
		attachInfo = attachService.selectAttachInfo(reqParamMap.getParamMap());
		log.debug("SERVER FILE INFO : "+attachInfo.get("FILE_PATH") + "\\"+ attachInfo.get("FILE_NM"));
		// 물리 파일삭제
		AtchService.deleteFile(attachInfo.get("FILE_PATH") + "\\"+ attachInfo.get("FILE_NM"));*/
		
		attachService.deleteAttachDtl((String)reqParamMap.getParamMap().get("atchNoSeq"));
    	
   		setSuccMsg(mav);
    	return mav;
	}
	
	/**
    *
    * @param response
    * @param reqParamMap
    *
    * <pre>
    * Commnets : DHTMLX 첨부파일 다운로드
    *
    * </pre>
    */
   @RequestMapping({"/attach/fileDhtmlxDownload.do"})
   @AjaxParamAnnotation
   public ModelAndView fileDhtmlxDownload(HttpServletResponse response, ReqParamMap reqParamMap) throws Exception {

       String fileName = (String)reqParamMap.get("fileName");

       log.info("Downloading : fileName={}", new Object[]{fileName});

       String[] tmp = fileName.split("-");
       if(tmp.length != 2){
           throw new SwafException("Attach patsing error");
       }
       //String atchNo = tmp[0];
       //String atchSeq = tmp[1];

       Map file = attachService.selectAttachInfo(fileName);

       if(file == null) {
           throw new SwafException("Attach File Not Found [ atchNoSeq=" + fileName + "]");
       }

       attachService.streamingAttachFile(response, file);


//       setSuccMsg(mav);
       return null;
   }
	
}
