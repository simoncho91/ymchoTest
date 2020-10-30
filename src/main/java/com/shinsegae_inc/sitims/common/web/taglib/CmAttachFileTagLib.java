package com.shinsegae_inc.sitims.common.web.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmAttachOdmService;
import com.shinsegae_inc.sitims.common.service.CommonService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;

@SuppressWarnings("rawtypes")
public class CmAttachFileTagLib extends RequestContextAwareTag {
	private static final long serialVersionUID = 1L;
	
	private transient String uploadCd;
	private transient String recordId;
	private transient String formName;
	private transient String type;
	private transient String division;

	private transient String pk1;
	private transient String pk2;
	private transient String pk3;
	private transient String pk4;
	private transient String pk5;
	
	private transient CmAttachOdmService cmAttachOdmService;
	protected transient CommonService                  commonService;
	
	public int doStartTagInternal() throws Exception {
		if (commonService == null) {
	        WebApplicationContext context = getRequestContext().getWebApplicationContext();
	        commonService   = (CommonService)context.getBean("commonServiceSitms"); 
	    }
		if(cmAttachOdmService == null) {
			WebApplicationContext context = getRequestContext().getWebApplicationContext();
			cmAttachOdmService = (CmAttachOdmService)context.getBean("cmAttachOdmService");
		}
		
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		StringBuilder html = new StringBuilder(1024);
		List<CmMap> list = null;
		
		JspWriter out = this.pageContext.getOut();

		try {
			if(CmFunction.isNotEmpty(this.recordId)) {
				CmMap tempVo = new CmMap();
				tempVo.put("i_sUploadCd", this.uploadCd);
				tempVo.put("i_sRecordId", this.recordId);
				
				tempVo.put("i_sBuffer1", this.pk1);
				tempVo.put("i_sBuffer2", this.pk2);
				tempVo.put("i_sBuffer3", this.pk3);
				tempVo.put("i_sBuffer4", this.pk4);
				tempVo.put("i_sBuffer5", this.pk5);
								
				int recordCnt = cmAttachOdmService.selectAttachListCount(tempVo);
				if(recordCnt > 0) {
					list = cmAttachOdmService.selectAttacList(tempVo);
				}
			}
			
			if(CmFunction.isEmpty(this.type)) {
				this.type = "reg";
			}
			
			if("reg".equals(this.type)) {
				html.append(this.getRegHtml(list));
			} else if("listDown".equals(this.type)) {
				html.append(this.listDownHtml(list));
			} else if("listNmDown".equals(this.type)) {
				html.append(this.listNmDownHtml(list));
			} else if("viewLog".equals(this.type)) {
				html.append(this.getViewLogHtml(list));
			}else {
				html.append(this.getViewHtml(list));
			}
			
			out.print(html.toString());
			out.flush();
		} catch (IOException e) {
			this.errorLogger(e);
		}
		
		return EVAL_PAGE;
	}

	private String listNmDownHtml(List<CmMap> list) {
		StringBuilder sbHtml = new StringBuilder(1024);
		CmMap tempVo = null;
		int listSize = list == null ? 0 : list.size();
		
		if(CmFunction.isEmpty(this.division)) {
			this.division = "ODM";
		}
		
		if(CmFunction.isEmpty(this.formName)) {
			this.formName = "frm";
		}
		if(listSize > 0) {
			for(int i=0; i<listSize; i++) {
				tempVo = list.get(i);
				
				sbHtml.append("\n		<a href=\"javascript:attachDown('"+tempVo.getString("v_attach_id")+"', '"+this.division+"', '"+this.formName+"');\">"+tempVo.getString("v_attach_nm")+"&nbsp;<img src='"+CmPathInfo.getIMG_PATH()+"icon/icon_filedownload.gif' align='middle'/></a>");
			}
		} else {
			sbHtml.append("&nbsp;"+"등록된 첨부파일이 없습니다.");
		}
		
		return sbHtml.toString();
	}

	
	private Object getViewLogHtml(List<CmMap> list) {
		
		int listSize = list == null ? 0 : list.size();
		CmMap tempVo = null;
		if(CmFunction.isEmpty(this.formName)) {
			this.formName = "frm";
		}
		
		if(CmFunction.isEmpty(this.division)) {
			this.division = "ODM";
		}

		StringBuilder sb	= new StringBuilder(1024);
		sb.append("\n<table class=\"sty_03 bd_gray\" id=\"table_"+this.uploadCd+"\" style=\"width:98%;margin:5px;\">\n	<colgroup>\n		<col width=\"70%\"/>\n		<col width=\"30%\"/>\n	</colgroup>\n	<thead>\n		<tr>\n			<th class='bdl_n'>File</th>\n			<th>File Size</th>\n		</tr>\n	</thead>\n	<tbody>");
		
		if(listSize > 0) {
			for(int i=0; i<listSize; i++) {
				tempVo = list.get(i);
				sb.append("\n<tr>\n	<td class='bdl_n'>\n		<a href=\"javascript:attachDown('"+tempVo.getString("v_attach_id")+"', '"+this.division+"', '"+this.formName+"', '"+"Y"+"');\">"+tempVo.getString("v_attach_nm")+"</a>\n		<span class=\"span_action_type span_hide\">M</span>\n	</td>\n	<td>"+tempVo.getString("n_attach_size")+" KB</td>\n</tr>");
			}
		} else {
				sb.append("\n<tr><td colspan='2' class='bdl_n'>No Attach File</td></tr>");
		}
		sb.append("\n	</tbody>"+"\n</table>");		
		
		return sb.toString();
	}
	private String getViewHtml(List<CmMap> list) {
		StringBuilder sb	= new StringBuilder(1024);
		
		sb.append("\n<table class=\"sty_03 bd_gray\" id=\"table_"+this.uploadCd+"\" style=\"width:98%;margin:5px;\">\n	<colgroup>\n		<col width=\"70%\"/>\n		<col width=\"30%\"/>\n	</colgroup>\n	<thead>	\n		<tr>\n			<th class='bdl_n'>File</th>\n			<th>File Size</th>\n		</tr>\n	</thead>\n	<tbody>");
		
		int listSize = list == null ? 0 : list.size();
		CmMap tempVo = null;
		if(CmFunction.isEmpty(this.formName)) {
			this.formName = "frm";
		}
		
		if(CmFunction.isEmpty(this.division)) {
			this.division = "ODM";
		}
		
		if(listSize > 0) {
			for(int i=0; i<listSize; i++) {
				tempVo = list.get(i);
				sb.append("\n<tr>\n	<td class='bdl_n'>\n		<a href=\"javascript:attachDown('"+tempVo.getString("v_attach_id")+"', '"+this.division+"', '"+this.formName+"');\">"+tempVo.getString("v_attach_nm")+"</a>\n		<span class=\"span_action_type span_hide\">M</span>\n	</td>\n	<td>"+tempVo.getString("n_attach_size")+" KB</td>\n</tr>");
			}
		} else {
				sb.append("\n<tr><td colspan='2' class='bdl_n'>No Attach File</td></tr>");
		}
		sb.append("\n	</tbody>\n</table>");
		return sb.toString();
	}
	
	private String getRegHtml(List<CmMap> list) {
		StringBuilder sb	= new StringBuilder(1024);
		sb.append("\n<table class=\"table_list table_")
				.append(this.uploadCd)
				.append("\" id=\"table_")
				.append(this.uploadCd)
				.append("\" style=\"width:70%;margin:5px;\">\n	<colgroup>\n		<col width=\"70%\"/>\n		<col width=\"20%\"/>\n		<col width=\"10%\"/>\n	</colgroup>\n	<thead>\n		<tr>\n			<th>"+"File"+"</th>\n			<th>"+"File Size"+"</th>\n			<th class=\"last\"></th>\n		</tr>\n	</thead>\n	<tbody>");
		
		int listSize = list == null ? 0 : list.size();
		CmMap tempVo = null;
		
		if(CmFunction.isEmpty(this.formName)) {
			this.formName = "frm";
		}
		
		if(CmFunction.isEmpty(this.division)) {
			this.division = "ODM";
		}
		
		if(listSize > 0) {
			for(int i=0; i<listSize; i++) {
				tempVo = list.get(i);
				sb.append("\n<tr>\n	<td>\n		<a href=\"javascript:attachDown('")
				.append(tempVo.getString("v_attach_id"))
				.append("', '"+this.division+"', '"+this.formName+"');\">")
				.append(tempVo.getString("v_attach_nm"))
				.append("</a>\n		<span class=\"span_action_type span_hide\">M</span>\n	</td>\n	<td>")
				.append(tempVo.getString("n_attach_size"))
				.append(" KB</td>\n	<td class=\"last\">\n		<a href=\"#none\" onclick=\"javascript:attachDel(j$(this), '")
				.append(this.uploadCd)
				.append("', '")
				.append(this.formName)
				.append("');\" class=\"btn_attach_del\" id=\"")
				.append(tempVo.getString("v_attach_id"))
				.append("\">\n			<img src=\"")
				.append(CmPathInfo.getIMG_PATH())
				.append("btn/btn_del_small.gif\"/>\n		</a>\n	</td>\n</tr>");
			}
		}
		sb.append("\n	</tbody>"+"\n</table>");
		
		
		return sb.toString();
	}
	
	private String listDownHtml(List<CmMap> list) {
		StringBuilder sbHtml = new StringBuilder(1024);
		CmMap tempVo = null;
		int listSize = list == null ? 0 : list.size();
		
		if(CmFunction.isEmpty(this.division)) {
			this.division = "ODM";
		}
		
		if(CmFunction.isEmpty(this.formName)) {
			this.formName = "frm";
		}
		if(listSize > 0) {
			for(int i=0; i<listSize; i++) {
				tempVo = list.get(i);
				
				sbHtml.append("\n		<a href=\"javascript:attachDown('"+tempVo.getString("v_attach_id")+"', '"+this.division+"', '"+this.formName+"');\"><img src='"+CmPathInfo.getIMG_PATH()+"icon/icon_filedownload.gif' align='middle'/></a>");
			}
		} else {
			sbHtml.append("&nbsp;"+"등록된 첨부파일이 없습니다.");
		}
		
		return sbHtml.toString();
	}

	public void setUploadCd(String uploadCd) {
		this.uploadCd = uploadCd;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public void errorLogger(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();

		String className  = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		int lineNumber    = ste[0].getLineNumber();
		String fileName   = ste[0].getFileName();

		if (logger.isErrorEnabled()) {
			logger.error("### " + className + "." + methodName + "###");
			logger.error("# FileName : " + fileName);
			logger.error("# LineNumber : " + lineNumber);
			//e.printStackTrace();
		}
	}

	public void setPk1(String pk1) {
		this.pk1 = pk1;
	}

	public void setPk2(String pk2) {
		this.pk2 = pk2;
	}

	public void setPk3(String pk3) {
		this.pk3 = pk3;
	}
	
	public void setPk4(String pk4) {
		this.pk4 = pk4;
	}
	public void setPk5(String pk5) {
		this.pk5 = pk5;
	}
	
}

