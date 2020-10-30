package com.shinsegae_inc.sitims.common.web.view;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@SuppressWarnings({"rawtypes", "PMD.CyclomaticComplexity"})
@Component("excelListView")
public class ExcelListView extends AbstractView {
	private HSSFCellStyle getCellStyle(HSSFWorkbook wb) {
		HSSFFont font = wb.createFont();
		HSSFCellStyle cs = wb.createCellStyle();
		font.setFontName("맑은 고딕");
		cs.setFont(font);
		cs.setWrapText(true);
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cs.setBottomBorderColor(HSSFColor.BLACK.index);
		
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setLeftBorderColor(HSSFColor.BLACK.index);
		
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs.setRightBorderColor(HSSFColor.BLACK.index);
		
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs.setTopBorderColor(HSSFColor.BLACK.index);
		return cs;
	}
	
	private void setCellValue(CmMap rowMap, HSSFCell hssfCell, Map<String, CellStyle> cellStyle, String key) {
		HSSFCell cell	= hssfCell;
		if (key != null ) {
			//컬럼명의 끝이 _dt, _date, _dtm으로 끝나는 경우 날짜 값으로 처리한다.
			if (key.lastIndexOf("_dt") > -1 
					|| key.lastIndexOf("_date") > -1 
					|| key.lastIndexOf("_dtm") > -1
					|| key.lastIndexOf("_ym") > -1
				){
				cell.setCellValue(CmFunction.getPointDate(rowMap.getString(key)));
			} else if(key.indexOf("_daytime") > -1){
				// 시분까지 표시
				cell.setCellValue(CmFunction.getPointDate(rowMap.getString(key)) + " " + CmFunction.getPointTime2(rowMap.getString(key)));
			} else if (key.indexOf("n_") == 0) { 
				if(rowMap.getString(key)!=null && !rowMap.getString(key).equals("")){
					cell.setCellValue(rowMap.getDouble(key));
				}
				cell.setCellStyle(cellStyle.get("number"));
				
			} else if (key.indexOf("P_") == 0) {
				if(rowMap.getString(key)!=null && !rowMap.getString(key).equals("")){
					cell.setCellValue(rowMap.getDouble(key));
				}
				cell.setCellStyle(cellStyle.get("percent"));
			}else if (key.indexOf("D_") == 0) {
				if(rowMap.getString(key)!=null && !rowMap.getString(key).equals("")){
					cell.setCellValue(rowMap.getDouble(key));
				}
				cell.setCellStyle(cellStyle.get("double"));
			}else if(key.lastIndexOf("_clob") > -1){
				String	clobValue = CmFunction.removeHTML(rowMap.getStringExcel(key)).replaceAll("<br/>", "\\\n");
				if(CmFunction.isNotEmpty(clobValue)){
					if(clobValue.length() > 10000){
						cell.setCellValue(clobValue.substring(0, 9999));
					}else{
						cell.setCellValue(clobValue);
					}
				}
			}
			else {
				cell.setCellValue(rowMap.getStringExcel(key).replaceAll("<br/>", "\\\n"));
			}
		}
		else {
			cell.setCellValue("");
		}
	}

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<CmMap> lstMap = (List<CmMap>)model.get("excelData");
		
		String[] lstTitle = (String[])model.get("excelTitle");
		
		String[] lstFieldName = (String[])model.get("excelFieldName");
		
		int[] columnWidth = (int[])model.get("columnWidth");
		
//		String strValue = "";
		
		int titleLen = lstTitle == null ? 0 : lstTitle.length;
		int fieldLen = lstFieldName == null ? 0 : lstFieldName.length;
		int columnWidthLen = columnWidth == null ? 0 : columnWidth.length;
		int mapLen = lstMap == null ? 0 : lstMap.size();
		
		// POI 객체 생성
		HSSFWorkbook		wb		= null;
		if (model.get("HSSFWorkbook") != null) {
			wb		= (HSSFWorkbook)model.get("HSSFWorkbook");
		} else {
			wb		= new HSSFWorkbook();
		}
		HSSFSheet sheet = wb.createSheet();
		
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		
		// 조회일자가 들어갈 행을 만든다.
		HSSFRow header = sheet.createRow(0);
		
		for ( int i = 0 ; i < titleLen ; i++ ) {
			header.createCell(i);
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		
		header.getCell(0).setCellValue("(조회일자 : "+simpleDateFormat.format(new Date())+")");
		
		sheet.createRow(1);
		
		HSSFRow row;
		HSSFCell cell = null;
				
		//컬럼 타이틀 row 생성
		row = sheet.createRow(2);
		//컬럼 타이틀 설정
		for ( int i = 0 ; i < titleLen ; i++ ) {
			cell = row.createCell(i); 
			cell.setCellValue(lstTitle[i]);
			cell.setCellStyle(cellStyle.get("header2"));
		}
		HSSFCellStyle	cs	= this.getCellStyle(wb);
		
		if(mapLen > 0){
			for ( int i = 0 ; i < lstMap.size(); i++ ) {
				row = sheet.createRow(i+3);
				CmMap rowMap = (CmMap)lstMap.get(i);
				for ( int j = 0 ; j < titleLen ; j++ ) {
					row.setHeight((short)400);
					cell = row.createCell(j);
					cell.setCellStyle(cs);		//셀 스타일 적용
					if (fieldLen > j)
						this.setCellValue(rowMap, cell, cellStyle, lstFieldName[j]);
				}
			}
		}

		//컬럼 넓이를 지정하지 않았을 경우에만 실행 오토 사이즈
		if(columnWidthLen == 0){
			for ( int i = 0 ; i < lstMap.size(); i++ ) {
				sheet.autoSizeColumn(i+1);
			}
		}
		else {
			//컬럼 넓이 설정
			for ( int i = 0 ; i < columnWidthLen ; i++ ) {
				sheet.setColumnWidth(i, columnWidth[i] * 265);
			}
		}

		OutputStream out 	= response.getOutputStream();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String title = new String( CmFunction.getStringValue(model.get("excelFileName")).getBytes("KSC5601"),"8859_1" );
		
		if ( !title.equals("")) {
			response.setHeader("Content-Disposition", "attachment; fileName=\"" +title + "_" + sdf.format(new Date()) + ".xls\";"); 
			response.setHeader("Content-Transfer-Encoding", "binary");	
		}

		wb.write(out);
		out.close();
		out.flush();
	}
}
