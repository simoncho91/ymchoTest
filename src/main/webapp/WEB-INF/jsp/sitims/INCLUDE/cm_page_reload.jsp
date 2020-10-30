<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Iterator"%>
<%@ page import = "java.util.Set"%>
<%@ page import = "com.shinsegae_inc.sitims.common.CmMap"%>

<%
    CmMap reloadInfoMap = (CmMap) request.getAttribute("reloadInfoMap");
	StringBuffer	sbReload	= new StringBuffer();
	StringBuffer	sbPaging	= new StringBuffer();
	boolean			isNowPageNo = false;
	boolean			isPageSize  = false;
	if (null != reloadInfoMap) {
		Set setObj = reloadInfoMap.keySet();
		Iterator<String> e = setObj.iterator();
		
		while (e.hasNext())
		{
			String 		requestParamName = (String) e.next();
			String[]	parameterValues = (String[])reloadInfoMap.get(requestParamName);
			
			if ( parameterValues == null) {
				continue;
			}
			
			if (requestParamName.indexOf("_temp") == requestParamName.length() - 4) {
				continue;
			}
			
			if ("i_iNowPageNo".equals(requestParamName)) {
				isNowPageNo = true;
			}
			if ("i_iPageSize".equals(requestParamName)) {
				isPageSize = true;
			}

			int parameterValuesLen = parameterValues.length;
				
			for (int i = 0; i < parameterValuesLen; i++) {
				sbReload.append("\n    ").append("<textarea name=\"reload_").append(requestParamName).append("\" style=\"display:none;\">").append(parameterValues[i]).append("</textarea>");
				sbPaging.append("\n    ").append("<textarea name=\"").append(requestParamName).append("\" style=\"display:none;\">").append(parameterValues[i]).append("</textarea>");
			}
		}
		
		if (!isNowPageNo) {
			sbPaging.append("\n    ").append("<input name=\"i_iNowPageNo\" value=\"\" style=\"display:none;\"/>");
		}
		if (!isPageSize) {
			sbPaging.append("\n    ").append("<input name=\"i_iPageSize\" value=\"\" style=\"display:none;\"/>");
		}
	}
%>

<form id="frm_reload" name="frm_reload" method="post" action="${requestScope.requestUri}">
<%=sbReload%>
</form>

<form id="frm_paging" name="frm_paging" method="post" action="${requestScope.requestUri}">
<%=sbPaging%>
</form>
