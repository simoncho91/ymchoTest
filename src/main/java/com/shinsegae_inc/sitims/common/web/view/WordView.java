package com.shinsegae_inc.sitims.common.web.view;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.shinsegae_inc.sitims.common.util.CmFunction;

/*********************************************
 * Copyright(c) 2011 ishift
 * Author           : jgeese
 * Register Date    : 2011. 7. 5.
 * Register Contnet :
 *********************************************/
@SuppressWarnings("rawtypes")
@Component("wordView")
public class WordView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OutputStream out 	= response.getOutputStream();
		
		try {
			String title 		= new String( CmFunction.getStringValue(model.get("wordFileName")).getBytes("EUC_KR"),"8859_1" );
			String fileName 	= title + ".docx";
			
			if ( !title.equals("")) {
				response.setHeader("Content-Disposition", "attachment; fileName=\"" +fileName+ "\";"); 
				response.setHeader("Content-Transfer-Encoding", "binary");	
			}
			
			XWPFDocument doc	= null;
			if (model.get("doc") != null) {
				doc 	= (XWPFDocument)model.get("doc");
			}
			else {
				doc		= new XWPFDocument();
			}
			
	        doc.write(out);
	        out.close();
			out.flush();

		} catch (FileNotFoundException e) {
			if (out != null) {
				out.close();
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}


