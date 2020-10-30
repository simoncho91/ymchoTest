package com.shinsegae_inc.sitims.mi.nb.nb.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.controller.CommonController;
import com.shinsegae_inc.sitims.mi.nb.nb.service.NbNb030Service;


@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/nb/nb/030/*")
public class NbNb030Controller extends CommonController{
	@Resource(name="NbNb030Service")
	private transient NbNb030Service nbNb030Service; 
	
	/**
	 * 
	 * @param reqVo
	 * @param request
	 * @param response        
	 * @return
	 * @throws Exception
	 * 
    * <pre>
    * Commnets : 공지사항DB 목록 페이지
    * </pre>
	 */
    @RequestMapping(value="/init.do")
	public ModelAndView si_im_030_list (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getInitMAV(request,"sitims/views/mi/nb/nb/030/nb_nb_030_list");
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
     * Commnets : 공지사항 목록 조회
     * </pre>
     */
    @RequestMapping(value="/selectList.do")
    public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mav = new ModelAndView("jsonView");
    	
    	String stdt = reqVo.getString("i_sStDate");
    	String endt = reqVo.getString("i_sEnDate");
    	
    	if(!stdt.equals("") && !endt.equals("")) {
    		stdt = stdt.replace("-", "");
    		endt = endt.replace("-", "");
    	} else if(!stdt.equals("") && endt.equals("")) {
    		stdt = stdt.replace("-", "");
    		endt = "29991231";
    	} else if(stdt.equals("") && !endt.equals("")) {
    		stdt = "19010101";
    		endt = endt.replace("-", "");
    	}
    	
    	reqVo.put("i_sStDate", stdt);
		reqVo.put("i_sEnDate", endt);
    	
    	
    	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, nbNb030Service.selectNbNb030List(reqVo));
    }
    
   
 	
 	/**
 	 *
 	 * @param reqVo
 	 * @return
 	 * @throws Exception
 	 *
 	 * <pre>
 	 * Commnets : 공지사항 등록
 	 * </pre>
 	 */
 	@RequestMapping(value="/nb_nb_030_save.do")
 	public ModelAndView nb_nb_030_save(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
 		ModelAndView mav = new ModelAndView("jsonView");
 		String actionFlag 	= reqVo.getString("i_sActionFlag");
		
		if("R".equals(actionFlag)) {
			nbNb030Service.insertNbNb030List(reqVo);
		} else if("M".equals(actionFlag)){
			nbNb030Service.updateNbNb030List(reqVo);
		}

 		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
 	}
 	
 	/**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 공지사항 삭제
    * </pre>
    */
 	@RequestMapping(value="/nb_nb_030_del.do")
 	public ModelAndView nb_nb_030_del(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
 		ModelAndView mav = new ModelAndView("jsonView");
 		
 		nbNb030Service.deleteNbNb030List(reqVo);
 		
 		return this.makeJsonResult(mav, "success", reqVo.getString("message"), reqVo);
 	}
 	
 	 /**
    *
    * @param reqVo
    * @return
    * @throws Exception
    *
    * <pre>
    * Commnets : 공지사항 상세
    * </pre>
    */
   @RequestMapping(value="nb_nb_030_view.do")
   public ModelAndView nb_nb_030_view(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
	   ModelAndView mav = new ModelAndView();
   	   CmMap rvo = nbNb030Service.getNbNb030Info(reqVo);
   	
       mav.setViewName("sitims/mi/nb/nb/030/nb_nb_030_view");
   	
   	   mav.addObject("reqVo", reqVo);
   	   mav.addObject("rvo", rvo);
   	
   	   return mav;
   }
   /**
   *
   * @param reqVo
   * @return
   * @throws Exception
   *
   * <pre>
   * Commnets : 공지사항 등록
   * </pre>
   */
	@RequestMapping(value="/nb_nb_030_reg.do")
	public ModelAndView nb_nb_030_reg(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView				mav				=	new ModelAndView();
 		String actionFlag 	= reqVo.getString("i_sActionFlag");
		CmMap rvo = null;
 		
		if("M".equals(actionFlag)){
			rvo = nbNb030Service.getNbNb030Info(reqVo);
		}
		
		mav.setViewName("sitims/mi/nb/nb/030/nb_nb_030_reg");
		mav.addObject("reqVo", reqVo);
		mav.addObject("rvo", rvo);
		return mav;
		
	}
}
