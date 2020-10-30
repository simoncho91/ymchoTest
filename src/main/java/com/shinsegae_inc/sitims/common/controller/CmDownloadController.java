package com.shinsegae_inc.sitims.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;
@Controller
@SuppressWarnings("rawtypes")
public class CmDownloadController extends CommonController {

	@RequestMapping("/attach_odm_link_down.do")
	public ModelAndView attach_odm_link_down (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView	mav	= new ModelAndView();
		String filedownUrl = "";
		request.setCharacterEncoding("utf-8");
		String attachId 	= new String (request.getParameter("attachTemp").getBytes("8859_1"), "KSC5601");  //파일아이디
		String logYn 	= new String ((CmFunction.isEmpty(request.getParameter("logYn"))?"":request.getParameter("logYn")).getBytes("8859_1"), "KSC5601");  //로그여부
		filedownUrl = CmPathInfo.getROOT_PATH() + "attach_download.do?attachTemp=" + attachId+"&logYn="+logYn;
		
		return this.makeJsonResult(mav, "", "", filedownUrl);
	}
	/**
	 * 파일 다운로드_ODM
	 * @param reqVo
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/attach_download.do")
	public void attach_download (@ModelAttribute ("dateMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			
			request.setCharacterEncoding("utf-8");
			String attachId 	= new String (request.getParameter("attachTemp").getBytes("8859_1"), "KSC5601");  //파일아이디
			String logYn 	= new String ((CmFunction.isEmpty(request.getParameter("logYn"))?"":request.getParameter("logYn")).getBytes("8859_1"), "KSC5601");  //로그여부
			reqVo.put("i_sAttachId", attachId);			
			CmMap attachInfo = commonService.getAttachInfo(reqVo);
			
			//String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";
			String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_attach_path");
			//String path = attachInfo.getString("FILEPNM");

			//String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
			String tempFileName = attachInfo.getString("v_attach_id")+attachInfo.getString("v_attach_ext").toLowerCase();
			String filename = attachInfo.getString("v_attach_nm");
			
			//페이지 url
			//String urlPath			= new String (request.getParameter("urlPath")+"");
			
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			
			//userid
			HttpSession 	session			= request.getSession();
			String 			userid 			= SessionUtils.getUserNo();
						
			//ServletContext 	context 	= getServletContext();
        	//String 			rootPath 	= context.getRealPath(path);  //현재 jsp의 실제 경로 산출
        	String 			filePath 	= path + tempFileName;
        	File 			tempFile 	= new File(filePath);
        	String 			agentType 	= request.getHeader("User-Agent");
         	
         	//파일을 없거나 읽을수 없는 파일일 경우			
				
			if (!tempFile.exists() || !tempFile.canRead()) 
			{
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>alert('File Not Found');history.back();</script>");
				return;
			}
				
			boolean flag = false;
			
			if(agentType != null && agentType.indexOf("MSIE 5.5") >= 0)
				flag = true;

			if (flag) {
				response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "\\ ")+";");
			} else {
				response.setContentType("application/octet-stream");
				response.setContentLength((int)tempFile.length());
				response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "\\ ")+";");
				
			}
			
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			
			dumpFile(tempFile, response);
			//파일다운 횟수 증가
			CmMap logVo = new CmMap();
			logVo.put("i_sAttachId", attachId);
			if("Y".equals(logYn)) {
				logVo.put("i_sUserId", SessionUtils.getUserNo());
				logVo.put("i_sProductCd", attachInfo.getString("v_record_id"));
				logVo.put("i_sFileNm", filename);
				logVo.put("i_sIpAddr", cryptoService.encAES(ipAddr, true));
				logVo.put("i_sAttchClass", attachInfo.getString("v_upload_id"));
				logVo.put("i_sAttachId", attachInfo.getString("v_attach_id"));
				commonService.insertDownloadLog(logVo);				
			}
			commonService.updateDownloadCnt(logVo);
			

		} catch (IOException e) {
			this.errorLogger(e);
			PrintWriter out = response.getWriter();
			out.println("<script>alert('File Not Found');history.back();</script>");
		}
	}

    @Autowired
    private transient CryptoService cryptoService;
	private void dumpFile(File realFile, HttpServletResponse response) throws Exception {

		byte readByte[] = new byte[4096];
		ServletOutputStream	outs	=	null;
		InputStream bufferedinputstream = null;
		try {
			
			bufferedinputstream =new FileInputStream(realFile);
			
			int i = 0;

			i = bufferedinputstream.read(readByte, 0, 4096);
			while (i != -1){
				outs = response.getOutputStream();
				outs.write(readByte, 0, i);
				i = bufferedinputstream.read(readByte, 0, 4096);
			}
			bufferedinputstream.close();
		}catch (IOException e) {
			this.errorLogger(e);
		} finally {
			if(bufferedinputstream != null) {
				bufferedinputstream.close();
			}
			if(outs != null) {
				outs.flush();
				outs.close();
			}
		}
	}
	@RequestMapping("/link_download.do")
	public void link_download (@ModelAttribute ("dateMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedOutputStream	outs	=	null;
		BufferedInputStream		fin	=	null;
		FileInputStream fis = null;
		try {
			request.setCharacterEncoding("utf-8");
			
			File	file	=	null;
			String filenm	=	java.net.URLDecoder.decode((String)request.getParameter("filename"), "UTF-8");
			String path		=	new String (request.getParameter("path").getBytes("8859_1"), "KSC5601");				//파일경로				//파일경로
			
			file	=	new File(CmPathInfo.getUPLOAD_PATH() + path + filenm);
			response.reset();
			
			filenm	= java.net.URLEncoder.encode(filenm, "UTF-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="+filenm+";");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			
			if(file != null) {
				fis =new FileInputStream(file);
				fin	=	new BufferedInputStream(fis);
				outs	=	new BufferedOutputStream(response.getOutputStream());
				
				int read	=	0;
				read = fin.read();
				while(read  != -1) {
					outs.write(read);
					read = fin.read();
				}
			}
		
		} finally{
			if(fin!= null) fin.close();
			if(fis != null) fis.close();
			if(outs != null) outs.close();
		}
	}

	@RequestMapping("/showImg.do")
	public void showImg (@ModelAttribute("dataMap") CmMap reqVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			String key;
			String[] values;
			
			for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); )
 	    	{
 	    		key  	= CmFunction.getStringValue((String)en.nextElement());
    			if ( !key.equals("i_sReturnUrl") && !key.equals("i_sReturnPars") ) {
    				values			= request.getParameterValues(key);
    				for (String str : values) {
    					logger.debug( key + "=" + str);
    				}
    			}
 	    	}
			
			request.setCharacterEncoding("utf-8");
			String attachId 	= request.getParameter("i_sAttachId"); //파일아이디
			reqVo.put("i_sAttachId", attachId);
			if(CmFunction.isEmpty(attachId)) {
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>alert('File Not Found');history.back();</script>");				
			}
			CmMap attachInfo = commonService.getAttachInfo(reqVo);	
			//ODM_INFO
			//String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/";
			String path = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_attach_path");
			//String tempFileName = attachInfo.getString("v_attach_nm");
			//String tempFileName = attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
			String tempFileName = attachInfo.getString("v_attach_id")+attachInfo.getString("v_attach_ext").toLowerCase();
			String filename = attachInfo.getString("v_attach_nm");
			
			
			//ip주소
			String ipAddr			= request.getRemoteAddr();
			
			//userid
			HttpSession 	session			= request.getSession();
			String 			userid 			= CmFunction.getStringValue( (String)session.getAttribute("v_userid"));
			
        	String 			filePath 	= path + tempFileName;
        	File 			tempFile 	= new File(filePath);
        	String 			agentType 	= request.getHeader("User-Agent");
         	
         	//파일을 없거나 읽을수 없는 파일일 경우
			try {

				if (!tempFile.exists() || !tempFile.canRead()) 
				{					
					tempFile = new File(CmPathInfo.getUPLOAD_IMAGE_TEMP_PATH()+"no_img.jpg");
					logger.info(CmPathInfo.getUPLOAD_IMAGE_TEMP_PATH()+"no_img.jpg");
					if(!tempFile.exists()) {
						FileUtils.copyURLToFile(new URL(CmPathInfo.getIMG_PATH()+"no_img.jpg"), tempFile);				
					}
				}
				
			} catch (FileNotFoundException e) {
				this.errorLogger(e);
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>alert('File Not Found');history.back();</script>");
				return;
				
			}

			boolean flag = false;
			
			if(agentType != null && agentType.indexOf("MSIE 5.5") >= 0)
				flag = true;

			if (flag) {
				response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
			} else {
				response.setContentType("application/octet-stream");
				response.setContentLength((int)tempFile.length());
				response.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "\\ ")+";");
			}
			
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");
			dumpFile(tempFile, response);

			
		} catch (IOException e) {
			this.errorLogger(e);
			PrintWriter out = response.getWriter();
			out.println("<script>alert('File Not Found');history.back();</script>");
		}
	}
}