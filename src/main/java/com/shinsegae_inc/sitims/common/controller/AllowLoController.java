package com.shinsegae_inc.sitims.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CommonService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.swaf.common.service.LoginService;

/**
 * 메일접근
 * 
 * @author 20200917
 *
 */
@Controller
@SuppressWarnings("rawtypes")
@RequestMapping(value = "/allo/*")
public class AllowLoController {
	
	@Autowired
	private transient CommonService	commonService;

	@Autowired
	private transient LoginService	loginService;

	@Autowired
	protected transient CryptoService	cryptoService;

	/**
	 * 메일링크 허용
	 * 
	 * @param request
	 * @param param
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "mailLink.do")
	public ModelAndView mailLink(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		String	ssoPass	= request.getParameter("ssoPass");
		String	userNo	= cryptoService.decAES(request.getParameter("userNo"), true);	// 암호화 => 복호화
		String	openUrl	= request.getParameter("openUrl");	// encode
		String	menuCd	= "";
		String	menuNm	= "";

		if (this.isSSoLoginCheck(ssoPass, userNo)) {
			CmMap			reqVo	= new CmMap();

			reqVo.put("acUrl"	, openUrl.substring(0, openUrl.lastIndexOf('?')));
			reqVo.put("url"		, openUrl.substring(0, openUrl.lastIndexOf('/') + 1));

			List<CmMap>		list	= commonService.selectMenuInfoList(reqVo);

			for (CmMap map : list) {
				if ("2".equals(map.getString("LVL"))) {
					menuCd	= map.getString("MENU_CD");
					menuNm	= map.getString("MENU_NM");
				}
			}

			StringBuilder	sb	= new StringBuilder(200);
			sb.append("openUrl=").append(CmFunction.encodeURIComponent(openUrl))
			.append("&menuCd=").append(menuCd)
			.append("&menuNm=").append(CmFunction.encodeURIComponent(menuNm));

			mav.setViewName("redirect:/tiims/init.do?" + sb.toString());
		} else {
			mav.setViewName("redirect:/login/init.do");
		}

		return mav;
	}

	private	boolean isSSoLoginCheck(String ssoPass, String userNo) throws Exception {
		if (CmFunction.isNotEmpty(SessionUtils.getUserNo())) {
			return true;
		} else if ("Y".equals(ssoPass) && CmFunction.isNotEmpty(userNo)) {
			return loginService.selectLogin(userNo);
		}

		return false;
	}

}
