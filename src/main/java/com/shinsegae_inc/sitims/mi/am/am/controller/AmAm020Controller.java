package com.shinsegae_inc.sitims.mi.am.am.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.am.am.service.AmAm020Service;

@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/am/am/020/*")
public class AmAm020Controller extends CommonController{
	@Autowired
	private transient AmAm020Service amam020Service;
	
    /**
    *
    * @param 
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 제품권한(사용자) 화면 초기화
    * </pre>
    */
	@RequestMapping("init.do")
	public ModelAndView init(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
	return getInitMAV(request,"sitims/views/mi/am/am/020/am_am_020_list");
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 제품권한(사용자) 리스트 불러옴
    * </pre>
    */
	@RequestMapping("am_am_020_list.do")
	public ModelAndView am_am_020_list(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		 reqVo.put("i_sMstCode","ODM_EXPORT");
		 reqVo.put("i_sBuffer3","Y");
		 
		if(reqVo.getStringArray("i_sNationList[]") != null) {
			String[] str = reqVo.getStringArray("i_sNationList[]");
			reqVo.put("i_arrStr", str);
		}
		List<CmMap> cmList = amam020Service.getCmCodeListForRaNation(reqVo);
		List<CmMap> list = amam020Service.selectAmAm020List(reqVo);
		List<CmMap>	tmpList	= new ArrayList<>();
		
			for(int i =0;i<list.size();i++) {
				CmMap	resultVo	= list.get(i);
				String stBufForNM= list.get(i).getString("v_nation_gcd");
					//국가명 init  (CN|US) => (중국,미국)
					for(int j =0;j<cmList.size();j++) {
						
						if(stBufForNM.indexOf(cmList.get(j).getString("COMM_CD")) > -1){
							stBufForNM = stBufForNM.replace(cmList.get(j).getString("COMM_CD"),cmList.get(j).getString("COMM_CD_NM"));
							
							if(stBufForNM.indexOf('|') > -1){
								stBufForNM = stBufForNM.replace("|", ",");
							}
						}
					}
					//국가코드init (CN|US) => (CN,US)
					String stBufForCD = list.get(i).getString("v_nation_gcd");
					stBufForCD = stBufForCD.replace("|", ",");

					resultVo.put("v_nation_gcd", stBufForNM);
					resultVo.put("v_nation_cds", stBufForCD);
					tmpList.add(resultVo);
			}
		
		return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, tmpList);
	}
    /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 국가 목록 조회
    * </pre>
    */
   @PostMapping(value="CmCodeListForRaNation.do")
   public ModelAndView CmCodeListForRaNation(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
   	ModelAndView mav = new ModelAndView("jsonView");
   	
   	List<CmMap> list = amam020Service.getCmCodeListForRaNation(reqVo);

   	mav.addObject("result", list);
   	setSuccMsg(mav);
   	return mav;
   }

   /**
   *
   * @param reqVo
   * @return
   * @throws Exception
   *
   * <pre>
   * Commnets : 제품권한(사용자) 등록
   * </pre>
   */
	@RequestMapping("am_am_020_reg.do")
	public ModelAndView am_am_020_reg(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		//Set Data LoginService 
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUpdateUserId",userid); 
		reqVo.put("i_sRegUserId",userid);
		
		if(reqVo.getStringArray("i_sNationList[]") != null) {
			StringBuffer buf = new StringBuffer();
			for(String str : reqVo.getStringArray("i_sNationList[]")) {
				buf.append(str);
				buf.append('|');
			}
			String strNation = buf.substring(0, buf.length() - 1);
			reqVo.put("i_sNationList", strNation);
		}else {
			return this.makeJsonResult(mav,"error", "국가를 선택해주세요.", null); 
		}
	  
		  if(amam020Service.selectCountForProdUser(reqVo) == 0) {
			 amam020Service.regForProdUser(reqVo); 
		  }else { 
			 return this.makeJsonResult(mav,"error", "등록하려는 브랜드에 이미 지정되어있습니다. ", null);
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
   * Commnets : 제품권한(사용자) 삭제
   * </pre>
   */
	@RequestMapping("am_am_020_del.do")
	public ModelAndView am_am_020_del(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		
		if(amam020Service.selectCountForProdUser(reqVo) == 1) {
			 amam020Service.delForProdUser(reqVo);
		}else {
			return this.makeJsonResult(mav,"error", "해당 선택사항에 삭제할 아이디가 없습니다.", null); 
		}
		 
	 return this.makeJsonResult(mav, "Success", "삭제 성공", null);
	}
	


	/**
	*
	* @param reqVo
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 제품권한(사용자) 수정
	* </pre>
	*/
	@RequestMapping("am_am_020_modify.do")
	public ModelAndView am_am_020_modify(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("jsonView");
		String userid = SessionUtils.getUserNo();
		reqVo.put("i_sUpdateUserId",userid); 
		
		if(reqVo.getStringArray("i_sNationList[]") != null) {
			StringBuffer buf = new StringBuffer();
			for(String str : reqVo.getStringArray("i_sNationList[]")) {
				buf.append(str);
				buf.append('|');
			}
			String strNation = buf.substring(0, buf.length() - 1);
			reqVo.put("i_sNationList", strNation);
		}else {
			return this.makeJsonResult(mav,"error", "국가를 선택해주세요.", null); 
		}
		
		
		if(amam020Service.selectCountForProdUser(reqVo) == 1) {
			amam020Service.modifyForProdUser(reqVo);
		}else {
			return this.makeJsonResult(mav,"error", "변경할 수 없는 아이디 입니다.", null); 
		}
		
		return this.makeJsonResult(mav,"Success", "수정 성공", null);
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
	@RequestMapping(value="am_am_020_list_excel.do")
	public ModelAndView am_am_020_list_excel(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView			mav			= 	new ModelAndView("excelListView");
		boolean nationNull = reqVo.getString("i_sNationList").equals("");
		if(reqVo.getString("i_sNationList") != null && !nationNull) {
			
			String str = reqVo.getString("i_sNationList");
			String[] array = str.split(",");
			reqVo.put("i_arrStr", array);
		}
		List<CmMap> 			list		= 	amam020Service.selectAmAm020List(reqVo);

		// Title
		String[] titleArray = {
				"No.","국가","브랜드","사용자","등록자","등록일","수정자","수정일"
		};




		// DB Column Name
		String[] columnNmArray = {
				"n_num", "v_nation_gnm", "v_brand_nm", "v_user_nm", "v_reg_user_id", "v_reg_dtm_excel", "v_update_user_id","v_update_dtm_excel"
		};

		// Column Width
		mav.addObject("excelFileName"	, "제품조회 권한관리(사용자)");
		mav.addObject("excelTitle"		, titleArray);
		mav.addObject("excelFieldName"	, columnNmArray);
		mav.addObject("excelData"		, list);

		return mav;
	}
	
	
}
