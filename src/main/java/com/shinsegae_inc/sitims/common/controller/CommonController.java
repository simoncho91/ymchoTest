package com.shinsegae_inc.sitims.common.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.dhtmlx.controller.SwafDhtmlxController;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CommonService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;
import com.shinsegae_inc.sitims.common.util.Dhtmlx6Excel;
import com.shinsegae_inc.swaf.common.utils.MailUtils;
import com.shinsegae_inc.swaf.common.utils.MailUtils.MailType;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

@SuppressWarnings({"unchecked","rawtypes", "deprecation", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity"})
public class CommonController extends SwafDhtmlxController {	
	@Autowired
	protected transient ResourceLoader	resourceLoader;
	@Autowired
	protected transient CommonService commonService;	
	
	@Autowired
	protected transient CryptoService cryptoService;

	@Value("${globals.smtp.host}")
	private transient String host;        // mail host
	
	@Value("${globals.smtp.port}")
	private transient String port;        // mail port
	
	@Value("${globals.smtp.username}")
	private transient String userName;        // mail username
	
	@Value("${globals.smtp.password}")
	private transient String password;        // mail password

	@Autowired
	private transient TemplateEngine templateEngine;

	protected transient final Log logger = LogFactory.getLog(this.getClass());

	
	public void getCmSubCodeList(ModelAndView mav, String attributeName, String sMstCode) throws Exception{
		this.getCmSubCodeList(mav, attributeName, sMstCode, null, null, null, null);
	}

	// sub code list
	public void getCmSubCodeList(ModelAndView mav, String attributeName, String sMstCode, String sSubCode) throws Exception {
		this.getCmSubCodeList(mav, attributeName, sMstCode, sSubCode, null, null, null);
	}

	// sub code list
	public void getCmSubCodeListBf(ModelAndView mav, String attributeName, String sMstCode, String sBuffer2like ) throws Exception {
		//this.getCmSubCodeListBf(mav, attributeName, sMstCode, null, null, sBuffer2like, null);
		CmMap reqVo = new CmMap();
		
		reqVo.put("i_sMstCode", sMstCode);
		reqVo.put("i_sBuffer2like", sBuffer2like);
		
		mav.addObject(attributeName, commonService.getCmSubCodeList(reqVo));
	}
	
	public void getCmSubCodeList(ModelAndView mav, String attributeName, String sMstCode, String[][] arrBuffer ) throws Exception {
		CmMap reqVo = new CmMap();
		reqVo.put("i_sMstCode", sMstCode);
		
		if (arrBuffer != null) {
			int len = arrBuffer.length;
 			
			for (int i = 0; i < len; i++) {
				reqVo.put(arrBuffer[i][0], arrBuffer[i][1]);
			}
		}
		
		mav.addObject(attributeName, commonService.getCmSubCodeList(reqVo));
	}
	
	
	// sub code list
	public void getCmSubCodeList(ModelAndView mav, String attributeName,
			String sMstCode, String sSubCode, String sBuffer1,
			String sBuffer2, String sBuffer3) throws Exception {
		CmMap reqVo = new CmMap();

		reqVo.put("i_sMstCode", sMstCode);
		reqVo.put("i_sSubCode", sSubCode);
		reqVo.put("i_sBuffer1", sBuffer1);
		reqVo.put("i_sBuffer2", sBuffer2);
		reqVo.put("i_sBuffer3", sBuffer3);

		mav.addObject(attributeName, commonService.getCmSubCodeList(reqVo));
	}
	
	// sub code list
	public void getCmSubCodeBufferLikeList(ModelAndView mav, String attributeName,
			String sMstCode, String sSubCode, String sBuffer1like,
			String sBuffer2like, String sBuffer3like) throws Exception {
		CmMap reqVo = new CmMap();

		reqVo.put("i_sMstCode", sMstCode);
		reqVo.put("i_sSubCode", sSubCode);
		reqVo.put("i_sBuffer1like", sBuffer1like);
		reqVo.put("i_sBuffer2like", sBuffer2like);
		reqVo.put("i_sBuffer3like", sBuffer3like);

		mav.addObject(attributeName, commonService.getCmSubCodeList(reqVo));
	}
	
	// sub code list
	public void getCmSubCodeList(ModelAndView mav, String attributeName,
			String sMstCode, String[] arrSubCode) throws Exception {
		CmMap reqVo = new CmMap();

		reqVo.put("i_sMstCode", sMstCode);
		reqVo.put("i_arrSubCode", arrSubCode);

		mav.addObject(attributeName, commonService.getCmSubCodeList(reqVo));
	}

	/**
	 * 첨부파일 목록을 가져온다.
	 * 
	 * @param mav
	 * @param attributeName
	 * @param recordId
	 */
	public void getCmAttachFileList(ModelAndView mav, String attributeName,
			String recordId) throws Exception {

		CmMap fileReqVo = new CmMap();

		fileReqVo.put("i_sRecordId", recordId);
		fileReqVo.put("i_sNotBuffer1", "EDMS");

		mav.addObject(attributeName, commonService.getCmAttachList(fileReqVo));
	}

	/**
	 * 첨부파일 모두 삭제
	 * 
	 * @param recordId
	 */
	public boolean deleteAttachAll(String recordId) throws Exception {
		boolean bResult = true;

		CmMap fileReqVo = new CmMap();
		List<CmMap> list = null;
		CmMap rvo = null;
		int size = 0;

		fileReqVo.put("i_sRecordId", recordId);

		list = commonService.getCmAttachList(fileReqVo);

		if (list != null) {
			size = list.size();

			commonService.deleteAllCmAttach(fileReqVo);

			for (int i = 0; i < size; i++) {
				rvo = list.get(i);

				CmFunction.fileDelete(rvo.getString("v_attach_path")
						+ rvo.getString("v_attachid")
						+ rvo.getString("v_attach_ext"));
			}
		} 

		return bResult;
	}

	/**
	 * list pagging
	 * 
	 * @param reqVo
	 * @param recordCnt
	 * @param pageSize
	 * @param nowPageNo
	 */
	public void setListPagging(CmMap reqVo, int recordCnt, int pageSize,int nowPageNo) throws Exception {

		int totalPageCnt = 0;
		int skipCnt = 0;
		int tempRecordCnt = recordCnt;
		int tempPageSize = pageSize;
		int tempNowPageNo = nowPageNo;

		if (tempPageSize <= 0)
			tempPageSize = 10;
		if (tempNowPageNo <= 0)
			tempNowPageNo = 1;

		if (tempRecordCnt <= tempPageSize)
			totalPageCnt = 1;
		else
			totalPageCnt = ((tempRecordCnt - 1) / tempPageSize) + 1;

		if (totalPageCnt < tempNowPageNo)
			tempNowPageNo = totalPageCnt;
		if (tempNowPageNo > 1)
			skipCnt = (tempNowPageNo - 1) * tempPageSize;

		reqVo.put("i_iTotalPageCnt", totalPageCnt);
		reqVo.put("i_iRecordCnt", tempRecordCnt);
		reqVo.put("i_iPageSize", tempPageSize);
		reqVo.put("i_iNowPageNo", tempNowPageNo);
		reqVo.put("i_iSkipCnt", skipCnt);
		reqVo.put("i_iListStartNo", (tempRecordCnt - (tempNowPageNo - 1) * tempPageSize));
		reqVo.put("i_iStartRownum", skipCnt + 1);
		reqVo.put("i_iEndRownum", skipCnt + tempPageSize);
	}

	/**
	 * list pagging
	 * 
	 * @param reqVo
	 * @param recordCnt
	 * @param pageSize
	 * @param nowPageNo
	 */
	public void setListEx(CmMap reqVo, int recordCnt, int pageSize,int nowPageNo) throws Exception {

		int totalPageCnt = 0;
		int tempRecordCnt = recordCnt;
		int tempPageSize  = pageSize ;
		int tempNowPageNo = nowPageNo;

		if (tempPageSize <= 0)
			tempPageSize = 10;
		if (tempNowPageNo <= 0)
			tempNowPageNo = 1;

		if (tempRecordCnt <= tempPageSize)
			totalPageCnt = 1;
		else
			totalPageCnt = ((tempRecordCnt - 1) / tempPageSize) + 1;

		if (totalPageCnt < tempNowPageNo)
			tempNowPageNo = totalPageCnt;

		reqVo.put("i_iRecordCnt", tempRecordCnt);
		reqVo.put("i_iNowPageNo", tempNowPageNo);

	}

	/**
	 * page url, page parameter
	 * 
	 * @param request
	 * @param reqVo
	 */
	public void setPageUrlAndPars(HttpServletRequest request, CmMap reqVo) throws Exception {
		CmFunction.setPageUrlAndPars(request, reqVo);
	}


	/**
	 * login check
	 * 
	 * @param request
	 * @param reqVo
	 * @param mav
	 * @return
	 */
	public boolean isLoginCheck(HttpServletRequest request, CmMap reqVo,
			ModelAndView mav) throws Exception {
		return true;
	}

	/**
	 * 
	 * @param mav
	 * @param reqVo
	 * @param message
	 * @param script
	 * @return
	 */
	public ModelAndView setMessage(ModelAndView mav, CmMap reqVo,
			String returnUrl, String returnPars, String message, String script) throws Exception {
		mav.addObject("returnPars", returnPars);
		mav.addObject("returnUrl", returnUrl);
		mav.addObject("message", message);
		mav.addObject("script", script);
		mav.addObject("reqVo", reqVo);
		mav.setViewName("INCLUDE/cm_message");
		
		return mav;
	}

	public ModelAndView setConfirm(ModelAndView mav, CmMap reqVo,
			String returnUrl, String returnPars, String goUrl, String goPars,
			String message, String script) throws Exception {
		mav.addObject("returnPars", returnPars);
		mav.addObject("returnUrl", returnUrl);
		mav.addObject("goUrl", goUrl);
		mav.addObject("goPars", goPars);
		mav.addObject("message", message);
		mav.addObject("script", script);
		mav.setViewName("INCLUDE/cm_confirm");

		return mav;
	}

	/**
	 * 
	 * @param mav
	 * @param sAttrName
	 * @param eAttrName
	 */
	public void getCmYearList(ModelAndView mav, String sAttrName,
			String eAttrName) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy",Locale.KOREAN);

		mav.addObject(sAttrName, 2000);
		mav.addObject(eAttrName,
				Integer.parseInt(formatter.format(new Date())) + 1);
	}

	/**
	 * Error log 출력
	 * 
	 * @param e
	 */
	public void errorLogger(Exception e) throws Exception{
		StackTraceElement[] ste = e.getStackTrace();

		String className  = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		int lineNumber    = ste[0].getLineNumber();
		String fileName   = ste[0].getFileName();

		if (logger.isErrorEnabled()) {
			logger.error("### " + className + "." + methodName + "###");
			logger.error("# FileName : " + fileName);
			logger.error("# LineNumber : " + lineNumber);
			//this.errorLogger(e);
			throw e;
		}
	}

	/**
	 * 
	 * @param mav
	 * @param status
	 *            [succ(성공) | fail(필수 인자값 오류 등) | error(작업중 오류)]
	 * @param message
	 * @param object
	 * @return
	 */
	@SuppressWarnings({"unchecked", "PMD.CyclomaticComplexity"})
	protected ModelAndView makeJsonResult(ModelAndView mav, String status,
			String message, Object object) throws Exception{
		StringBuilder sb = new StringBuilder() 
		.append("{     \"status\" : \"")
		.append(status)
		.append("\"    , \"message\" : \"")
		.append(message)
		.append('\"');
		String tempStr = "";

		if (object != null) {
			sb.append("    , \"data\" : ");

			if (object instanceof String) {
				String str = CmFunction.getStringValue((String) object).trim();

				if ((str.indexOf('{') == 0 && str.indexOf('}') == str.length() - 1)
						|| (str.indexOf('[') == 0 && str.indexOf(']') == str.length() - 1)) {

					sb.append(str);
				} else {
					sb.append('\"'+str+'\"');
				}
			} else if (object instanceof JSONArray) {
				JSONArray jArr = (JSONArray) object;
				Object obj = null;
				int len = jArr.length();

				try {
					sb.append('[');

					for (int i = 0; i < len; i++) {
						if (i > 0)
							sb.append(',');

						obj = jArr.get(i);

						if (obj instanceof JSONObject) {
							tempStr = ((JSONObject) obj).toString();
						} else if (obj instanceof CmMap) {
							tempStr = new JSONObject((CmMap) obj)
									.toString();
						} else {
							tempStr = new JSONObject(obj).toString();
						}

						sb.append(tempStr);
					}
					sb.append(']');
				} catch (JSONException e) {
					this.errorLogger(e);						
					sb = new StringBuilder("[]");
				}
			} else if (object instanceof JSONObject) {
				sb.append(((JSONObject) object).toString());
			} else if (object instanceof CmMap) {
				sb.append(new JSONObject((CmMap<Object, Object>) object).toString());
			} else if (object instanceof List) {
				sb.append(new JSONArray((List<CmMap>) object).toString());
			} else {
				sb.append(new JSONObject(object).toString());
			}
		}

		sb.append('}');

		tempStr = sb.toString();
		tempStr = tempStr.replaceAll("\\r\\n", "<br/>");
		tempStr = tempStr.replaceAll("\\n", "<br/>");

		if (logger.isDebugEnabled()) {
			logger.debug(tempStr);
		}

		mav.addObject("json", tempStr);
		mav.setViewName("sitims/INCLUDE/cm_json");

		return mav;
	}

	@SuppressWarnings({"unchecked", "PMD.CyclomaticComplexity"})
	protected ModelAndView makeJsonDhtmlxPagingResult(ModelAndView mav, String status, String msgCode, CmMap reqVo, Object object) throws Exception {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("{     \"status\" : \""+status+"\"    , \"msgCode\" : \""+msgCode+"\"    , \"result\" : { ");
		String tempStr = "";

		if (object != null) {
			int	iRecordCnt	= reqVo.getInt("i_iRecordCnt");
			int	iTotalPageCnt	= reqVo.getInt("i_iTotalPageCnt");
			int	iSkipCnt		= reqVo.getInt("i_iSkipCnt");

			sb.append("     \"total_count\" : "+iRecordCnt+",     \"total_page_count\" : "+iTotalPageCnt+",     \"pos\" : "+iSkipCnt+",     \"data\" : ");

			if (object instanceof String) {
				String str = CmFunction.getStringValue((String) object).trim();

				if ((str.indexOf('{') == 0 && str.indexOf('}') == str.length() - 1)
						|| (str.indexOf('[') == 0 && str.indexOf(']') == str.length() - 1)) {

					sb.append(str);
				} else {
					sb.append('\"'+str+'\"');
				}
			} else if (object instanceof JSONArray) {
				JSONArray jArr = (JSONArray) object;
				Object obj = null;
				int len = jArr.length();

				try {
					sb.append('[');

					for (int i = 0; i < len; i++) {
						if (i > 0) sb.append(',');
						obj = jArr.get(i);

						if (obj instanceof JSONObject) {
							tempStr = ((JSONObject) obj).toString();
						} else if (obj instanceof CmMap) {
							tempStr = new JSONObject((CmMap) obj)
									.toString();
						} else {
							tempStr = new JSONObject(obj).toString();
						}

						sb.append(tempStr);
					}
					sb.append(']');
				} catch (JSONException e) {
					this.errorLogger(e);
					sb = new StringBuilder().append("[]");
					//sb.append("[]");
				}
			} else if (object instanceof JSONObject) {
				sb.append(((JSONObject) object).toString());
			} else if (object instanceof CmMap) {
				sb.append(new JSONObject((CmMap<Object, Object>) object).toString());
			} else if (object instanceof List) {
				sb.append(new JSONArray((List<CmMap>) object).toString());
			} else {
				sb.append(new JSONObject(object).toString());
			}
		}
		sb.append("                   } "+'}');

		tempStr = sb.toString();
		tempStr = tempStr.replaceAll("\\r\\n", "<br/>");
		tempStr = tempStr.replaceAll("\\n", "<br/>");

		if (logger.isDebugEnabled()) {
			logger.debug(tempStr);
		}

		mav.addObject("json", tempStr);
		mav.setViewName("sitims/INCLUDE/cm_json");
		return mav;
	}

	@SuppressWarnings({"unchecked", "PMD.CyclomaticComplexity"})
	protected ModelAndView makeJsonDhtmlxResult(ModelAndView mav, String status, String msgCode, Object object) throws Exception {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("{     \"status\" : \""+status+"\"    , \"msgCode\" : \""+msgCode+"\"    , \"result\" : { ");
		String tempStr = "";

		if (object != null) {
			sb.append("     \"data\" : ");

			if (object instanceof String) {
				String str = CmFunction.getStringValue((String) object)
						.trim();

				if ((str.indexOf('{') == 0 && str.indexOf('}') == str.length() - 1)
						|| (str.indexOf('[') == 0 && str.indexOf(']') == str.length() - 1)) {
					sb.append(str);
				} else {
					sb.append('\"'+str+'\"');
				}
			} else if (object instanceof JSONArray) {
				JSONArray jArr = (JSONArray) object;
				Object obj = null;
				int len = jArr.length();

				try {
					sb.append('[');

					for (int i = 0; i < len; i++) {
						if (i > 0) sb.append(',');
						obj = jArr.get(i);

						if (obj instanceof JSONObject) {
							tempStr = ((JSONObject) obj).toString();
						} else if (obj instanceof CmMap) {
							tempStr = new JSONObject((CmMap) obj)
									.toString();
						} else {
							tempStr = new JSONObject(obj).toString();
						}

						sb.append(tempStr);
					}
					sb.append(']');
				} catch (JSONException e) {
					this.errorLogger(e);
					sb = new StringBuilder().append("[]");
					//sb.append("[]");
				}
			} else if (object instanceof JSONObject) {
				sb.append(((JSONObject) object).toString());
			} else if (object instanceof CmMap) {
				sb.append(new JSONObject((CmMap<Object, Object>) object).toString());
			} else if (object instanceof List) {
				sb.append(new JSONArray((List<CmMap>) object).toString());
			}else if (object instanceof Integer) {
				sb.append("{\"count\":"+object+"}");
			} else {
				sb.append(new JSONObject(object).toString());
			}
		}
		sb.append("                   } "+'}');

		tempStr = sb.toString();
		tempStr = tempStr.replaceAll("\\r\\n", "<br/>");
		tempStr = tempStr.replaceAll("\\n", "<br/>");

		if (logger.isDebugEnabled()) {
			logger.debug(tempStr);
		}

		mav.addObject("json", tempStr);
		mav.setViewName("sitims/INCLUDE/cm_json");


		return mav;
	}


	/**
	 * 
	 * @param mav
	 * @param status
	 *            [succ(성공) | fail(필수 인자값 오류 등) | error(작업중 오류)]
	 * @param message
	 * @param object
	 * @return
	 */
	@SuppressWarnings({"unchecked", "PMD.CyclomaticComplexity"})
	protected ModelAndView makeJsonTreeResult(ModelAndView mav, String status,String message, List<CmMap> parent,List<CmMap> chidren,String parentKey) throws Exception {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("{     \"status\" : \""+status+"\"    , \"message\" : \""+message+"\"") ;
		String tempStr = "";
		int cIndex = 0;

		if(parent.size()> 0) {
			sb.append("    , \"data\" : [");
			for(int i=0;i<parent.size();i++) {
				cIndex =0;
				CmMap parentMap = parent.get(i);				
				if (i > 0) sb.append(',');
				sb.append("{\"value\" : \""+parentMap.getString("v_nm")
					+"\",\"id\" : \""+parentMap.getString("v_id")
					+"\",\"opened\" : \"true\",\"items\" : [");
				for(int j=0;j<chidren.size();j++) {
					CmMap chidrenMap = chidren.get(j);
					if(!parentMap.getString(parentKey).equals(chidrenMap.getString(parentKey))) {
						 continue;
					}
					if (cIndex > 0) sb.append(',');
					cIndex++;
					sb.append("{\"value\" : \""+chidrenMap.getString("v_nm")
						+"\",\"id\" : \""+chidrenMap.getString("v_id")+"\"}"
					);
				}				
				sb.append("]}");
			}
			sb.append(']');
		}

		sb.append('}');

		tempStr = sb.toString();
		tempStr = tempStr.replaceAll("\\r\\n", "<br/>");
		tempStr = tempStr.replaceAll("\\n", "<br/>");

		if (logger.isDebugEnabled()) {
			logger.debug(tempStr);
		}
		logger.info(tempStr);
		mav.addObject("json", tempStr);
		mav.setViewName("sitims/INCLUDE/cm_json");


		return mav;
	}


	/**
	 * dhtmlx 6 excel download
	 */
	@RequestMapping("/dhtmlx/commExcelDownload.do")
	public ModelAndView commExcelDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav			=	new ModelAndView("excel2View");
		String			jsonData	= request.getParameter("data");

		mav.addObject("HSSFWorkbook" , new Dhtmlx6Excel().buildHSSFWorkbook(jsonData));
		mav.addObject("excelFileName", new JSONObject(jsonData).getString("name"));
		return mav;
	}


	@RequestMapping("/comm/comm_upload.do")
	public ModelAndView comm_upload (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response,MultipartHttpServletRequest mpRequest) throws Exception{
		ModelAndView		mav	=	new ModelAndView("jsonView");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM",Locale.KOREAN);
		String yyyyMM = sdf.format( new Date() );
        String resultType 			= reqVo.getString("resultType");
        String uploadCd 			= reqVo.getString("uploadCd");
        String attachTempPath 		= CmPathInfo.getUPLOAD_FILE_TEMP_PATH() + yyyyMM + "/";
        String attachDir			= reqVo.getString("attachDir");
        String attachIdx			= reqVo.getString("attachIdx");
        String fileTypeCheck		= reqVo.getString("fileTypeCheck");

        String buffer1 			= reqVo.getString("buffer1");
        String buffer2 			= reqVo.getString("buffer2");
        String buffer3 			= reqVo.getString("buffer3");
        String buffer4 			= reqVo.getString("buffer4");
        String buffer5 			= reqVo.getString("buffer5");
        
        
        CmMap returnMap = null;
       
        String attachName = "";
        String attachExt = "";
        String attachId = "";
        long attachSize = 0l;
        String attachType = "";
        String extension = "";
        //int maxFileSize = 1024 * 1024 * 100;
        //int maxMemorySize = 1024 * 10;
        
        try {
        	File uploadDirectory = new File(attachTempPath);
    		//MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest) request;
    		Iterator<String> fileNameIterator = mpRequest.getFileNames();

    		while(fileNameIterator.hasNext()) {
    			MultipartFile mpFile = mpRequest.getFile(fileNameIterator.next());
				
				if(mpFile != null && mpFile.getSize() > 0) {
    				if(!uploadDirectory.exists()) {
    					uploadDirectory.mkdirs();
    				}
    				
    				attachName = mpFile.getOriginalFilename();
    				if(CmFunction.isNotEmpty(attachName)) {
        				attachExt = attachName.substring(attachName.lastIndexOf('.'), attachName.length()).toLowerCase();
        				extension = attachExt.substring(1).toUpperCase();    					
    				}
    				
//    				if("Y".equals(fileTypeCheck)) {
    					//String fileType = commonService.getExtList();
    					//String fileType =".xls|.pdf|.txt";
    					//if(fileType.indexOf(attachExt) == -1) {
    					//if(!commonService.getExtList(extension)) {
    					if("Y".equals(fileTypeCheck) && !commonService.getExtList(extension)) {
    						return this.makeJsonResult(mav, "fail", "", null);
    					}
//    				}
    				
    				attachId = "TI" + Calendar.getInstance().getTimeInMillis() + CmFunction.getRandomString( 2 );
    				attachSize = mpFile.getSize();

    				File file = new File(attachTempPath + attachId + attachExt);

//    				mpFile.transferTo(file);
    				BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));

					outputStream.write(mpFile.getBytes());
					outputStream.flush();
					outputStream.close();

	        		returnMap = new CmMap();
	        		
					//if(!"DOC".equals(extension) && !"PDF".equals(extension) && !"XLS".equals(extension) && !"PPT".equals(extension) && !"TXT".equals(extension)) {
					//	attachType = "ETC[" + extension + "]";
					//} else {
					//	attachType = extension;
					//}
	        		attachType = extension;
	        		
	                returnMap.put("v_attach_id", attachId);
	                returnMap.put("v_attach_lnm", attachName);
                	//returnMap.put("v_attach_type", attachType); //attachExt
	                returnMap.put("v_attach_type", attachExt); //attachExt
	                returnMap.put("v_attach_size", attachSize);
	                returnMap.put("v_attach_pnm", attachId + attachExt);
	                returnMap.put("v_attach_mgtid", attachDir);
	                returnMap.put("v_attach_idx", attachIdx);

	                returnMap.put("v_attach_buffer1", buffer1);
	                returnMap.put("v_attach_buffer2", buffer2);
	                returnMap.put("v_attach_buffer3", buffer3);
	                returnMap.put("v_attach_buffer4", buffer4);
	                returnMap.put("v_attach_buffer5", buffer5);
        		}
        	}
		} catch (IllegalStateException | IOException e) {
			this.errorLogger(e);
		}
		
        if("json".equals(resultType)) {
        	return this.makeJsonDhtmlxResult(mav, "succ", "", returnMap);
        } else {
        	mav.addObject("resMap", returnMap);
        	mav.setViewName("sitims/INCLUDE/cm_swfupload2");
        }
        
        return mav;
	}
	public void documentPdfPrint(String fileUrl, String title, String flag, HttpServletRequest request, HttpServletResponse response, String confiFlag,String outputYn) throws Exception {
		InputStream is = null;
		InputStreamReader reader = null;
		BufferedReader buff = null;
		BufferedOutputStream	bs = null;
		try {
			URL url = new URL(fileUrl);
			String pageContents = "";
			StringBuilder contents = new StringBuilder();
			HttpURLConnection con = (HttpURLConnection)url.openConnection(); 
//			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
//			con.addRequestProperty("Accept","*/*");

			is = con.getInputStream();
			reader = new InputStreamReader (is, "utf-8");
			buff = new BufferedReader(reader);
			
			
			if(confiFlag.equals("ok")){ // 영업비밀/대외비 문구 
				String confi = "<div class='header'>TRADE SECRET/CONFIDENTIAL</div>";
				contents.append(confi);
			}

			pageContents = buff.readLine();
			while(pageContents != null){
	            contents.append(pageContents);
	            contents.append("\r\n");
				pageContents = buff.readLine();
	        }
	
	        //buff.close();
			if(!"Y".equals(outputYn)) {
				response.setHeader("Content-Disposition", "attachment; filename=\""+ new String(title.getBytes("EUC_KR"),"8859_1") + ".pdf");
				response.setContentType("application/pdf");				
			}
			
			ITextRenderer	renderer	= new ITextRenderer();
			//String path = resourceLoader.getResource("classpath:static/sitims/fonts/").getURI().getPath().substring(1);
			String path = "static/sitims/fonts/";
			renderer.getFontResolver().addFont(path+"malgun.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont(path+"ARIAL.TTF",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont(path+"ariali.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			renderer.getFontResolver().addFont(path+"ARIALBD.TTF",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont(path+"ARIALUNI.TTF",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			//renderer.getFontResolver().addFont(path+"SourceHanSerifSC-Light.otf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			
			renderer.setDocument(getXhtmlDocument(contents.toString(), flag) ,"");
			renderer.layout();

			if(!"Y".equals(outputYn)) {
				bs	= new BufferedOutputStream(response.getOutputStream());
				renderer.createPDF(bs);				
			}else {
				File file = new File(CmPathInfo.getUPLOAD_FILE_TEMP_PATH()+new String(title.getBytes("EUC_KR"),"8859_1")+".pdf");
				FileOutputStream out = new FileOutputStream(file);
				renderer.createPDF(out);				
			}
			//bs.close();
		} catch(IOException e) {
 			this.errorLogger(e);
 			this.close(is, reader, buff, bs);
			PrintWriter out = response.getWriter();
			out.println("<script>fn_s_alertMsg(\"can't create pdf\");history.back();</script>");
		} catch(DocumentException e) {
 			this.errorLogger(e);
 			this.close(is, reader, buff, bs);
			PrintWriter out = response.getWriter();
			out.println("<script>fn_s_alertMsg(\"can't create pdf\");history.back();</script>");
		}finally {
			this.close(is, reader, buff, bs);
		}
	}

	
	public void documentPdfPrint(String fileUrl, String title, String flag, HttpServletRequest request, HttpServletResponse response, String confiFlag) throws Exception {
		this.documentPdfPrint(fileUrl, title, flag, request, response, confiFlag, null);
	}
	
	private void close(InputStream is, InputStreamReader reader, BufferedReader buff, BufferedOutputStream bs) throws Exception {
		if(bs != null) bs.close();
		if(buff != null) buff.close();
		if(reader != null) reader.close();
		if(is != null) is.close();
	}
	
	private Document getXhtmlDocument(String pContent, String flag) throws Exception {
		StringBuilder	buf			= new StringBuilder(1024)
		.append("<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><html xmlns=\"http://www.w3.org/1999/xhtml\" ><head><title>pdf down</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
		Tidy		tidy		= new Tidy();
		if("CT".equals(flag)) {
			buf.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>");
		}
		
		buf.append("<style type=\"text/css\">"+"@page{size: 8.5in 11in; margin:5px; margin-top:45px}")
		
		//Page header Style [S]
		.append("div.header { display: block; text-align: right; font-size: 20px; color: red; font-style: italic; margin-top: 10px; margin-right: 10px; position: running(header);}"
		+"@page{@top-center { content: element(header)}}"
		//Page header Style [E]
				);
//		+"body { font-family: \"맑은 고딕\", \"Malgun Gothic\", \"Arial\";}");
		if("KO".equals(flag) || "CT".equals(flag) || "AN".equals(flag) || "EN".equals(flag)) {
			buf.append("body { font-family: \"맑은 고딕\", \"Malgun Gothic\", \"Arial\";}");
		} else if("CN".equals(flag) || "JP".equals(flag)) {
			buf.append("body { font-family: \"Arial Unicode MS\", \"Dotum\"; }"); 
		} else {
			buf.append("body { font-family: \"Arial\";}");
		}
		buf.append("table {border-collapse: collapse;}</style></head><body>");
		//+"table {border-collapse: collapse;}"
		//+"</style>"
		//+"</head>"
		//+"<body>");
		
		String	sHTML	= pContent;
			
		sHTML	= CmFunction.replace(sHTML, "<IMG", "<img");	
		
		Pattern	p	= Pattern.compile("<img .+?>");
		Matcher	m	= p.matcher(sHTML);
			
		while (m.find()) {
			//String	sImg		= "<img";
			StringBuilder sImg = new StringBuilder(1024).append("<img");
			Matcher	mSrc		= Pattern.compile("src=[\"'](.*?)[\"']").matcher(m.group());
			Matcher	mWidth		= Pattern.compile("width=[\"'](.*?)[\"']").matcher(m.group());
			Matcher	mHeight	= Pattern.compile("height=[\"'](.*?)[\"']").matcher(m.group());
			
			if (mSrc.find())
				sImg.append(" src=\"" + mSrc.group(1) + "\"");
				//sImg	+= " src=\"" + mSrc.group(1) + "\"";

			if (mWidth.find())
				sImg.append(" width=\"" + mWidth.group(1) + "\"");
				//sImg	+= " width=\"" + mWidth.group(1) + "\"";
			
			if (mHeight.find())
				sImg.append(" height=\"" + mHeight.group(1) + "\"");
				//sImg	+= " height=\"" + mHeight.group(1) + "\"";
			
			//sImg	+= " />";
			sImg.append(" />");
			
			sHTML	= CmFunction.replace(sHTML, m.group(), sImg.toString());
		}
		
		sHTML	= CmFunction.replace(sHTML, "font-family", "");
		sHTML	= CmFunction.replace(sHTML, "FONT-FAMILY", "");

		//System.out.println(i_sHTML);
			
		buf.append(sHTML).append("</body></html>");
		
		return (Document)tidy.parseDOM(new StringReader(buf.toString()), null);
	}

	protected void pdfExcelDownloadLog(String productcd, String attachId, String title, String ipAddr,String attachClass) throws Exception{
		CmMap tempVo = new CmMap();
		tempVo.put("i_sUserId", SessionUtils.getUserNo());
		tempVo.put("i_sProductCd", productcd);
//		tempVo.put("i_sAttachId", attachId);
		tempVo.put("i_sFileNm", title);
		tempVo.put("i_sIpAddr", cryptoService.encAES(ipAddr, true));
		tempVo.put("i_sAttchClass", attachClass);
		
		commonService.insertDownloadLog(tempVo);
	}

	public String getMailContentsRoot(Map<String, Object> reqVo) throws Exception {
		Context	context	= new Context();
		reqVo.put("ImgPATH", CmPathInfo.getIMG_PATH());
		context.setVariables(reqVo);
		return templateEngine.process("sitims/views/mi/common/common_mail", context);
	}
	public String getMailContents(String thPath, Map<String, Object> reqVo) throws Exception {
		Context	context	= new Context();
		reqVo.put("ImgPATH", CmPathInfo.getIMG_PATH());
		context.setVariables(reqVo);
		reqVo.put("mailContents",templateEngine.process(thPath, context));
		return getMailContentsRoot(reqVo);
	}

	public String getMailLink(String userNo, String openUrl) throws Exception {
		StringBuilder	sb	= new StringBuilder(200);
		sb.append(CmPathInfo.getROOT_FULL_URL())
			.append("allo/mailLink.do?openUrl=")
			.append(CmFunction.encodeURIComponent(openUrl))
			.append("&ssoPass=Y&userNo=")
			.append(CmFunction.encodeURIComponent(cryptoService.encAES(userNo, true)));
		return sb.toString();
	}

	/**
	 * Common MailSend
	 * i_sFromAddr		: 보내는 사람 메일 주소
	 * i_sToAddr		: 받는 사람 메일 주소, 복수 수신자는 ;로 구분
	 * i_sCcAddr		: 참조자 메일주소, 복수 수신자는;로 구분
	 * i_sSubject		: 메일 제목
	 * i_sContents		: 메일 내용
	 */
	public boolean sendMailwithHtml(CmMap reqVo)  {
		boolean tmpBoo = false; 
		logger.info("Send Mail with HTML Test Start");
		Map<String,String> mailProps = new HashMap<String,String>();
		try {			
			mailProps.put("host", host);
			mailProps.put("port", port);
			mailProps.put("userName", cryptoService.decAES(userName, true) );
			mailProps.put("password", cryptoService.decAES(password, true) );
			if(CmFunction.isEmpty(reqVo.getString("i_sFromAddr"))) {
				mailProps.put("fromAddr", "from_mail_address@shinsegae.com");
			}else {
				mailProps.put("fromAddr", reqVo.getString("i_sFromAddr"));
			}
			
			if(CmFunction.isEmpty(reqVo.getString("i_sToAddr"))) {
				mailProps.put("toAddr", "to_mail@shinsegae.com");
			}else {
				mailProps.put("toAddr", reqVo.getString("i_sToAddr"));
			}
			
			if(CmFunction.isNotEmpty(reqVo.getString("i_sCcAddr"))) {
				mailProps.put("ccAddr", reqVo.getString("i_sCcAddr"));
			}
			mailProps.put("subject", reqVo.getString("i_sSubject"));
			mailProps.put("contents", reqVo.getString("i_sContents"));
			tmpBoo = MailUtils.sendMail(MailType.HTML, mailProps);
			
			if(tmpBoo) {
				logger.info("Send Mail with HTML Test Done");
				logger.info("http://10.105.16.81:8080/ 에서 결과 확인 가능합니다.");
			}			
		}catch (AddressException e) {
			// TODO: handle exception
			logger.error("메일발송에 실패하였습니다.");
			logger.error(e.getMessage());
			return false;
		}catch (UnsupportedEncodingException | SwafException | MessagingException e) {
			// TODO: handle exception
			logger.error("메일발송에 실패하였습니다.");
			logger.error(e.getMessage());
			return false;
		}
		return tmpBoo;
		
	}
	@SuppressWarnings("resource")
	protected void zipFileDownLoad(HttpServletRequest request, HttpServletResponse response, List<File> fileArray,String zipFileNm) throws Exception{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
    	int				nFileCnt	= 0;
		byte[]	files	= null;
		FileInputStream	zip	= null;
		File			f	= null;
//    	if(fileArray.size() > 0) {
    		String ranNum			= this.generateRamdomCode(10, 15);    		
    		ZipArchiveOutputStream zipFile		= new ZipArchiveOutputStream(new FileOutputStream(ranNum + ".zip"));
    		zipFile.setEncoding("UTF-8");
//    		ZipOutputStream zipFile		= new ZipOutputStream(new FileOutputStream(ranNum + ".zip"));
//    		ZipOutputStream zipFile = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(ranNum + ".zip")), Charset.forName("Shift-JIS"));

	    	try {
		    	for(File file : fileArray) {
		        	if (file.exists()) {
		        		FileInputStream fin =null;
		        		try {	        		
							fin = new FileInputStream(file);
							String	sFileOriNm	= file.getName();
							zipFile.putArchiveEntry(new ZipArchiveEntry(sFileOriNm));
							files = new byte[(int) file.length()];
							fin.read(files);		
							zipFile.write(files);
						
		        		}finally {
							if(fin != null )fin.close();
							if(file.exists())file.delete();
							//zipFile.closeEntry();
							zipFile.closeArchiveEntry();
							nFileCnt++;						
						}
		        	}
		    		
		    	}
	    	}finally {
				zipFile.close();
				try {
					zip	= new FileInputStream(ranNum + ".zip");
					f	= new File(ranNum + ".zip");
	
					files	= new byte[(int) f.length()];
					zip.read(files);
				}finally {
					if(zip != null)zip.close();
					if(f != null && f.exists())f.delete();
				}
				
			}
//    	}
		
//		if (0 == nFileCnt) {
//			PrintWriter out = response.getWriter();
//			out.println("<script>alert('File Not Found');history.back();</script>");
//		} else {			
			//response
			response.setContentType("application/zip");
			response.setContentLength(files !=null ?files.length:0);
			
			response.setHeader("Content-Disposition", "attachment; filename=\""+zipFileNm+".zip\"");

			response.getOutputStream().write(files);
//		}
	}
	@SuppressWarnings("resource")
	protected void zipFileDownLoad(HttpServletRequest request, HttpServletResponse response, CmMap reqVo,String zipFileNm) throws Exception{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
    	List<CmMap> attachList = commonService.getAttachList(reqVo);
    	int				nFileCnt	= 0;
		byte[]	files	= null;
		FileInputStream	zip	= null;
		File			f	= null;
    	if(attachList != null) {
    		String ranNum			= this.generateRamdomCode(10, 15);    		
    		ZipArchiveOutputStream zipFile		= new ZipArchiveOutputStream(new FileOutputStream(ranNum + ".zip"));
    		zipFile.setEncoding("UTF-8");
//    		ZipOutputStream zipFile		= new ZipOutputStream(new FileOutputStream(ranNum + ".zip"));
//    		ZipOutputStream zipFile = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(ranNum + ".zip")), Charset.forName("Shift-JIS"));

	    	try {
		    	for(CmMap attachInfo : attachList) {
		        	String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";
		        	//String fileName = attachInfo.getString("v_attach_nm");
					String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
					//String filename = attachInfo.getString("v_attach_nm");
					String	tmpFilePath		= path+"TEMP/"+ attachInfo.getString("v_attach_nm");
					String 			filePath 	= path + tempFileName;
		        	//File 			tempFile 	= new File(filePath);
		        	if (new File(filePath).exists()) {
		        		FileInputStream fin =null;
		        		try {	        		
							CmFunction.fileCopy(filePath,tmpFilePath);
							fin = new FileInputStream(tmpFilePath);
							File file = new File(tmpFilePath);
//							ZipEntry zipEntr = new ZipEntry();
							String	sFileOriNm	= tmpFilePath.substring(tmpFilePath.lastIndexOf('/') + 1, tmpFilePath.length());

//							zipFile.putNextEntry(new ZipEntry(sFileOriNm));
							zipFile.putArchiveEntry(new ZipArchiveEntry(sFileOriNm));
							files = new byte[(int) file.length()];
							fin.read(files);
		
							zipFile.write(files);
						
		        		}finally {
							if(fin != null )fin.close();
							CmFunction.fileDelete(tmpFilePath);
							//zipFile.closeEntry();
							zipFile.closeArchiveEntry();
							nFileCnt++;						
						}		
		        	}
		    		
		    	}
	    	}finally {
				zipFile.close();
				try {
					zip	= new FileInputStream(ranNum + ".zip");
					f	= new File(ranNum + ".zip");
	
					files	= new byte[(int) f.length()];
					zip.read(files);
				}finally {
					if(zip != null)zip.close();
					if(f != null && f.exists())f.delete();
				}
				
			}
    	}
		
		if (0 == nFileCnt) {
			PrintWriter out = response.getWriter();
			out.println("<script>alert('File Not Found');history.back();</script>");
		} else {			
			//response
			response.setContentType("application/zip");
			response.setContentLength(files.length);
			
			response.setHeader("Content-Disposition", "attachment; filename=\""+zipFileNm+".zip\"");

			response.getOutputStream().write(files);
		}
	}

	private String generateRamdomCode(double min, double max){
		StringBuffer	code		= new StringBuffer(1024);
		int		totalChars	= (int)((Math.random() * min)+max);
		char	startChar	= 'A';

		for(int i = 0; i < totalChars; i++){
			int	selNum	= (int)(Math.random() * 13.0);
			
			if (selNum <= 4) {
				startChar	= 'A';
				code.append((char)((Math.random()*26.0)+startChar));

			} else if(selNum >= 5 && selNum <= 9){
				//code	+= (int)(Math.random() * 10.0);
				code.append((int)(Math.random() * 10.0));
			} else if(selNum >= 10){
				startChar	= 'a';
				code.append((char)((Math.random()*26.0) + startChar));
				//code		+= (char)((Math.random()*26.0) + startChar);
			}
		}
		return code.toString();
	}

    protected void jasperReportDown(Map model, String filename) throws Exception{
		// TODO Auto-generated method stub
		String	jrxmlPath		= (String)model.get("jrxmlPath");
    	String path = resourceLoader.getResource("classpath:"+jrxmlPath).getURI().getPath();
    	JasperReport jasperReport = JasperCompileManager.compileReport(path);
		JRDataSource	dataSource	= (JRDataSource)model.get("datasource");
		JasperPrint		jasperPrint	= JasperFillManager.fillReport(jasperReport, model, dataSource);
		File file = new File(CmPathInfo.getUPLOAD_FILE_TEMP_PATH()+filename+".pdf");
		if(file.exists()) {
			file.delete();
		}
		FileOutputStream out = new FileOutputStream(file);
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		out.flush();
		out.close();
	}
    protected void jasperReportDownDoc(Map model, String filename) throws Exception{
		// TODO Auto-generated method stub
		String	jrxmlPath		= (String)model.get("jrxmlPath");
    	String path = resourceLoader.getResource("classpath:"+jrxmlPath).getURI().getPath();
    	JasperReport jasperReport = JasperCompileManager.compileReport(path);
		JRDataSource	dataSource	= (JRDataSource)model.get("datasource");
		JasperPrint		jasperPrint	= JasperFillManager.fillReport(jasperReport, model, dataSource);
		File file = new File(CmPathInfo.getUPLOAD_FILE_TEMP_PATH()+filename+".docx");
		if(file.exists()) {
			file.delete();
		}
		FileOutputStream out = new FileOutputStream(file);
		JRDocxExporter	exporter	= new JRDocxExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
		out.flush();
		out.close();
	}

    protected void excelReportDown(HSSFWorkbook wb, String filename) throws IOException {
		// TODO Auto-generated method stub	
		File uploadPath = new File(CmPathInfo.getUPLOAD_FILE_TEMP_PATH());
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		File file = new File(CmPathInfo.getUPLOAD_FILE_TEMP_PATH()+filename + ".xls");
		if(file.exists()) {
			file.delete();
		}
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			wb.write(out);
		}finally {
	        if(out != null) {
	        	out.flush();
	        	out.close();	        	
	        }			
		}
	}

    //@RequestMapping(value="/comm/downloadAuthChk.do")
    //public ModelAndView selectList(@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
    //	ModelAndView mav = new ModelAndView("jsonView");    	
    //	return this.makeJsonDhtmlxPagingResult(mav, "OK", "success", reqVo, commonService.selectBrPr010List(reqVo));
    //}
}