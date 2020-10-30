package com.shinsegae_inc.sitims.mi.am.um.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.am.um.service.AmUm010Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/am/um/010/*")
public class AmUm010Controller extends CommonController{
	@Resource(name="AmUm010Service")

	private transient AmUm010Service amum010Service;

	
	
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 화면 초기화
    * </pre>
    */
	@RequestMapping("init.do")
	public ModelAndView init(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
	return getInitMAV(request,"sitims/views/mi/am/um/010/am_um_010_list");
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : dhtmlxtree 정보 불러옴
    * </pre>
    */
	 @RequestMapping("am_um_010_list_ajax.do")
	 @ResponseBody
	public ModelAndView am_um_010_list_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		return this.makeJsonResult(mav, "Success", "성공", amum010Service.getDeptList(reqVo));
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 부서  등록
    * </pre>
    */
	 @RequestMapping("am_um_010_regDept_ajax.do")
	public ModelAndView am_um_010_regDept_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 	//Set Data LoginService 
			String userid = SessionUtils.getUserNo();
			reqVo.put("i_sUpdateUserId",userid); 
			reqVo.put("i_sRegUserId",userid);

		  
				if (((Integer)amum010Service.getDeptCountForRegist(reqVo)).intValue() == 0) {
					//등록 하려는 부서에 동일이름을 가진 부서 검사  
					amum010Service.deptReg(reqVo);
				}else {
					 return this.makeJsonResult(mav,"error", "등록할 부서에 같은 이름이 있습니다.", null);
				}
			  
		
		 
		 return this.makeJsonResult(mav, "Success", "등록 성공", null);
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 부서  수정
    * </pre>
    */
	 @RequestMapping("am_um_010_modifyDept_ajax.do")
	public ModelAndView am_um_010_modifyDept_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		 ModelAndView	mav	=	new ModelAndView("jsonView");
	
		 	String userid = SessionUtils.getUserNo();
			reqVo.put("i_sUpdateUserId",userid); 
			
		
			int getDeptCountEqNm = ((Integer)amum010Service.getDeptCountForRegist(reqVo)).intValue();
			//변경 하려는 부서에 동일이름을 가진 부서 검사 
			if (getDeptCountEqNm == 0) {
				
				amum010Service.deptModify(reqVo);
				
			}else if(getDeptCountEqNm == 1){ 
				//1은 동일 이름이 부서 하위에 존재할 경우(여기서는 변경할 대상이 자신인지 타부서인지 확인)
				
				reqVo.put("deptEqNmForUpdate",1);
				if(((Integer)amum010Service.getDeptCountForRegist(reqVo)).intValue() == 1) {
					
					amum010Service.deptModify(reqVo);
				}else {
					return this.makeJsonResult(mav,"error", "수정할 부서에 같은 이름이 있습니다.", null);
				}
				
			}else {
			
				 return this.makeJsonResult(mav,"error", "수정할 부서에 같은 이름이 있습니다.", null);
			}
			
		
		 return this.makeJsonResult(mav, "Succ", "저장 성공", null);
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 부서  삭제
    * </pre>
    */
	 @RequestMapping("am_um_010_deletetDept_ajax.do")
	public ModelAndView am_um_010_deletetDept_ajax (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		 ModelAndView	mav	=	new ModelAndView("jsonView");
		 
		 	int deptCount = amum010Service.deptDeleteChk(reqVo);
			if(deptCount == 0) {			
				
					 
					amum010Service.deptDelete(reqVo);
					
				
				return this.makeJsonResult(mav, "Succ", "삭제 성공", null);
			 
			}else {
				return this.makeJsonResult(mav, "Fail", "하위부서가 "+deptCount+"개 존재합니다.", null);
			}
	}
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : regPop 화면 
    * </pre>
    */
	@RequestMapping("am_um_010_regPop.do")
	public ModelAndView am_um_010_regPop(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
	return getInitMAV(request,"sitims/views/mi/am/um/010/am_um_010_regPop");
	}

}
