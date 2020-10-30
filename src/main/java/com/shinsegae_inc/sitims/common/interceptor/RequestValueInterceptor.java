package com.shinsegae_inc.sitims.common.interceptor;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CommonService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;

@Aspect
@Component
public class RequestValueInterceptor {
	@Autowired
	protected transient CommonService commonService;
	
	protected transient final Log logger	= LogFactory.getLog(getClass());

	@Around("execution(public * com.shinsegae_inc.sitims.*..*Controller.*(..))")
	@SuppressWarnings({"rawtypes", "PMD.CyclomaticComplexity"})
	public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) pjp.getStaticPart().getSignature();
		
		if (methodSignature.getMethod().getReturnType().equals(ModelAndView.class)
			|| methodSignature.getMethod().getReturnType().equals(String.class)) {
			
			Class[] params		= methodSignature.getParameterTypes();

			StringBuilder sb = new StringBuilder();
			
			if (params.length >= 2 
					&& params[0].equals(CmMap.class)
					&& params[1].equals(HttpServletRequest.class)) {

				HttpServletRequest 	request		= null;
				CmMap<Object, Object>	dataMap		= new CmMap<Object, Object>();

				for (Object o : pjp.getArgs()) {
					if (o instanceof HttpServletRequest) {
						request = (HttpServletRequest) o;
					}
				}

				Enumeration<String>  enumeration	= request.getParameterNames();
				while (enumeration.hasMoreElements()) {
					String 	name		= enumeration.nextElement();
					String[] values		= request.getParameterValues(name);
					
					if (values != null) {
						
						// key 값이 i_arr 로 시작하면 1개라도 무조건 배열로 처리
						if(name.indexOf("i_arr") == 0 || values.length > 1){
							
							for (int i = 0; i < values.length; i++){
								// XSS 공격을 막기위해서 블랙리스트 단어앞에 x-를 붙여줌
								values[i] = CmFunction.replaceAlltoXSS(values[i]);
							}
							
							dataMap.put(name, values);
						}else{
							dataMap.put(name, CmFunction.replaceAlltoXSS(values[0]));
						}
						sb.append('&').append(name).append('=').append(name.indexOf("i_arr") == 0 || values.length > 1 ? values : values[0]);
						
					}
				}

				if (request instanceof MultipartHttpServletRequest) {
					MultipartHttpServletRequest multi	= (MultipartHttpServletRequest) request;
					
					Map files	= multi.getFileMap();
					
					Iterator<Map.Entry<String, MultipartFile>> iterator	= files.entrySet().iterator();
					
					while(iterator.hasNext()) {
						Map.Entry<String, MultipartFile> entry	= iterator.next();
						String key = entry.getKey();
						MultipartFile value = entry.getValue();
						if (!value.isEmpty())
							dataMap.put(key, value);
					}
				}
				// 페이지 리로드를 위한 세팅
				this.doSettingRequestInfo(request);

				// session 
				CmFunction.setSessionValue(request, dataMap);

				// Page Info
				this.setPageInfo(request, dataMap);
				this.setPathInfo(request);
				// 현재 요청 url
			//	dataMap.put("pageUri", url);
				dataMap.put("pageParam", sb.toString());

				Object	args[]	= pjp.getArgs();

				for (int i = 0; i < args.length; i++) {
					Object o	= args[i];
					if (o instanceof CmMap) {
						args[i]	= dataMap;
					}
				}

				return pjp.proceed(args);
			}
		}
		return pjp.proceed();
	}

	private void setPageInfo(HttpServletRequest request, CmMap<Object, Object> dataMap) {
		String					url			= request.getRequestURI();
		String					urlPatten	= url.substring(url.lastIndexOf('_') + 1, url.lastIndexOf('.'));
		
		// action of list page 
		if (urlPatten.equals("list")) {
			if (logger.isDebugEnabled())
				logger.debug("# CmFunction.setPageUrlAndPars(request, dataMap)");
			CmFunction.setPageUrlAndPars(request, dataMap);
		} else if (urlPatten.equals("view") || urlPatten.equals("reg")) {
			String sReturnUrl		= CmFunction.getStringValue((String)dataMap.get("i_sReturnUrl"));
			String ext				= url.substring(url.lastIndexOf('.'), url.length());
				
			if (sReturnUrl.equals("")) {
				sReturnUrl			= url.substring(0, url.lastIndexOf('_')) + "_list" + ext;
				dataMap.put("i_sReturnUrl", sReturnUrl);
				dataMap.put("i_sReturnPars", null);
			}

			if (logger.isDebugEnabled())
				logger.debug("# auto i_sReturnUrl : " + sReturnUrl);
		}

		if (!CmFunction.getStringValue(request.getHeader("X-Requested-With")).equals("XMLHttpRequest") 
			&&(urlPatten.equals("view") || urlPatten.equals("reg")) ){
			CmMap	reqVo	= new CmMap();
			reqVo.put("acUrl", url);
			reqVo.put("url", url.substring(0, url.lastIndexOf('/') + 1));

			List<CmMap>		list	= commonService.selectMenuInfoList(reqVo);
			String			menuCd	= "";
			int				size	= list.size();
			StringBuffer	sb		= new StringBuffer(); 

			for (int i = size - 1; i >= 0; i--) {
				CmMap	vo	= list.get(i);

				if (i == size - 1) {
					sb.append(vo.getString("MENU_NM"));
				} else {
					sb.append(" > ");
					sb.append(vo.getString("MENU_NM"));
				}
				menuCd	= vo.getString("MENU_CD");
			}
			request.setAttribute("MENU_CD", menuCd);
			request.setAttribute("PAGE_NAVI", sb.toString());
		}
	}

	private void doSettingRequestInfo(HttpServletRequest request) {
		
		java.util.Enumeration<String> e = request.getParameterNames();
		
		String requestUri = request.getRequestURI();
		
		CmMap reloadInfoMap = new CmMap();
		
		while (e.hasMoreElements())
		{
			String requestParamName = (String) e.nextElement();
		    String requestParamValue[] = request.getParameterValues(requestParamName);
		
		    reloadInfoMap.put(requestParamName, requestParamValue);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("reloadInfoMap.size() : "+reloadInfoMap.size());
		}
		
		request.setAttribute("requestUri", requestUri);
		request.setAttribute("reloadInfoMap", reloadInfoMap);
	}

	public void setPathInfo (HttpServletRequest request) {
			request.setAttribute("RootPATH", 		CmPathInfo.getROOT_PATH());
			request.setAttribute("RootFullPATH", 	CmPathInfo.getROOT_FULL_URL());
			request.setAttribute("ImgPATH", 		CmPathInfo.getIMG_PATH());
			request.setAttribute("ScriptPATH", 		CmPathInfo.getSCRIPT_PATH());
			request.setAttribute("CssPATH", 		CmPathInfo.getCSS_PATH());
			request.setAttribute("EditorPATH", 		CmPathInfo.getEDITOR_PATH());
			request.setAttribute("ChartsPATH", 		CmPathInfo.getCHARTS_PATH());
			request.setAttribute("RealServerYn", 	CmPathInfo.getREAL_SERVER_YN());
			request.setAttribute("RootODMFullPATH", CmPathInfo.getROOT_FULL_URL_ODM());
	}
}
