package com.shinsegae_inc.sitims.common.web.view;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.shinsegae_inc.sitims.common.util.CmFunction;

/*********************************************
 * Copyright(c) 2011 ishift
 * Author           : jgeese
 * Register Date    : 2011. 7. 18.
 * Register Contnet :
 *********************************************/
@SuppressWarnings("rawtypes")
@Component("excel2View")
public class ExcelView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OutputStream out 	= response.getOutputStream();
		
		HSSFWorkbook	 	wb	 	= null;
		SimpleDateFormat 	sdf 	= new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String 				title 	= new String( CmFunction.getStringValue(model.get("excelFileName")).getBytes("EUC_KR"),"8859_1" );
		
		if ( !title.equals("")) {
			if("N".equals(CmFunction.getStringValue(model.get("excelFileMakeDtmYn")))){ // 다운로드 날짜 제목에 포함안함.
				response.setHeader("Content-Disposition", "attachment; fileName=\"" +title + ".xls\";"); 
			} else{
				response.setHeader("Content-Disposition", "attachment; fileName=\"" +title + "_" + sdf.format(new Date()) + ".xls\";"); 					
			}
			response.setHeader("Content-Transfer-Encoding", "binary");	
		}
		
		if (model.get("HSSFWorkbook") != null) {
			wb		= (HSSFWorkbook)model.get("HSSFWorkbook");
		}
		else {
			wb		= new HSSFWorkbook();
		}
		
		wb.write(out);
        out.close();
		out.flush();
	}

}


