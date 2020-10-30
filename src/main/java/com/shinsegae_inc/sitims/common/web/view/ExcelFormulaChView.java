package com.shinsegae_inc.sitims.common.web.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;


@SuppressWarnings("rawtypes")
public class ExcelFormulaChView extends AbstractXlsView{
	
	// FORMULA_CHINA 전용 엑셀함수
	@Override
	@SuppressWarnings("unchecked")
	protected void buildExcelDocument(Map<String, Object> model, Workbook wookbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HSSFWorkbook	wb = (HSSFWorkbook) wookbook;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);
		String title = new String(CmFunction.getStringValue(model.get("excelFileName")).getBytes("EUC_KR"),"8859_1");
		if ( !title.equals("")) {
			response.setHeader("Content-Disposition", "attachment; fileName=\"" +"PIF_"+title + "_" + sdf.format(new Date()) + ".xls\";"); 
			response.setHeader("Content-Transfer-Encoding", "binary");	
		}
		
		CmMap rvo = (CmMap)model.get("rvo");
		//CmMap reqVo = (CmMap)model.get("reqVo");
		List<CmMap> list = (List<CmMap>)model.get("excelData");
		
		// POI 객체 생성
		HSSFSheet sheet = wb.createSheet();
		
		//미리설정된 스타일 호출
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		
		HSSFCellStyle theadStyle = (HSSFCellStyle) cellStyle.get("theadStyle");
		HSSFCellStyle temp = (HSSFCellStyle) cellStyle.get("header");
		HSSFCellStyle tableStyle = (HSSFCellStyle) cellStyle.get("default");
		HSSFCellStyle tablenum = (HSSFCellStyle) cellStyle.get("number2");
		tableStyle.setWrapText(false);
		
		//전용 스타일 생성
		
		if(list.size()>0){

				//필드 너비 설정 
				sheet.setColumnWidth(0, 3200);
				sheet.setColumnWidth(1, 10000);
				sheet.setColumnWidth(2, 10000);
				sheet.setColumnWidth(3, 3600);
				sheet.setColumnWidth(4, 3600);
				sheet.setColumnWidth(5, 4200);
				sheet.setColumnWidth(6, 9000);
				sheet.setColumnWidth(7, 9000);
				sheet.setColumnWidth(8, 3600);
				sheet.setColumnWidth(9, 3600);
				sheet.setColumnWidth(10, 3000);
				
				//내용 기록 시작
				HSSFRow row;
				HSSFCell cell = null;
				int startRow = 0;
				int listStartRow = 0;

				//리스트 상단 표 (header)
				
				startRow++;
				temp= wb.createCellStyle();
				temp.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				temp.setBottomBorderColor(HSSFColor.BLACK.index);
				temp.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				temp.setTopBorderColor(HSSFColor.BLACK.index);
				temp.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				temp.setLeftBorderColor(HSSFColor.BLACK.index);
				temp.setBorderRight(HSSFCellStyle.BORDER_THIN);
				temp.setRightBorderColor(HSSFColor.BLACK.index);
				
				row = sheet.createRow(startRow);
				cell = row.createCell(1);
				cell.setCellStyle(temp);
				cell.setCellValue("Product Code");
				
				temp= wb.createCellStyle();
				temp.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				temp.setBottomBorderColor(HSSFColor.BLACK.index);
				temp.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
				temp.setTopBorderColor(HSSFColor.BLACK.index);
				temp.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				temp.setLeftBorderColor(HSSFColor.BLACK.index);
				temp.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				temp.setRightBorderColor(HSSFColor.BLACK.index);
				
				cell = row.createCell(2);
				cell.setCellStyle(temp);
				cell.setCellValue(rvo.getString("v_product_cd"));
				
				temp= wb.createCellStyle();
				temp.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				temp.setBottomBorderColor(HSSFColor.BLACK.index);
				temp.setBorderTop(HSSFCellStyle.BORDER_THIN);
				temp.setTopBorderColor(HSSFColor.BLACK.index);
				temp.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				temp.setLeftBorderColor(HSSFColor.BLACK.index);
				temp.setBorderRight(HSSFCellStyle.BORDER_THIN);
				temp.setRightBorderColor(HSSFColor.BLACK.index);
				temp.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				
				startRow++;
				row = sheet.createRow(startRow);
				cell = row.createCell(1);
				cell.setCellStyle(temp);
				cell.setCellValue("Product Name");
				
				temp= wb.createCellStyle();
				temp.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				temp.setBottomBorderColor(HSSFColor.BLACK.index);
				temp.setBorderTop(HSSFCellStyle.BORDER_THIN);
				temp.setTopBorderColor(HSSFColor.BLACK.index);
				temp.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				temp.setLeftBorderColor(HSSFColor.BLACK.index);
				temp.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				temp.setRightBorderColor(HSSFColor.BLACK.index);
				temp.setWrapText(true);
				
				cell = row.createCell(2);
				cell.setCellStyle(temp);
				cell.setCellValue(rvo.getString("v_product_nm_en"));

				temp= wb.createCellStyle();
				temp.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
				temp.setBottomBorderColor(HSSFColor.BLACK.index);
				temp.setBorderTop(HSSFCellStyle.BORDER_THIN);
				temp.setTopBorderColor(HSSFColor.BLACK.index);
				temp.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
				temp.setLeftBorderColor(HSSFColor.BLACK.index);
				temp.setBorderRight(HSSFCellStyle.BORDER_THIN);
				temp.setRightBorderColor(HSSFColor.BLACK.index);
				
				startRow++;
				row = sheet.createRow(startRow);
				cell = row.createCell(1);
				cell.setCellStyle(temp);
				cell.setCellValue("Manufacturer");
				
				temp= wb.createCellStyle();
				temp.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
				temp.setBottomBorderColor(HSSFColor.BLACK.index);
				temp.setBorderTop(HSSFCellStyle.BORDER_THIN);
				temp.setTopBorderColor(HSSFColor.BLACK.index);
				temp.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				temp.setLeftBorderColor(HSSFColor.BLACK.index);
				temp.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
				temp.setRightBorderColor(HSSFColor.BLACK.index);
				
				cell = row.createCell(2);
				cell.setCellStyle(temp);
				cell.setCellValue(rvo.getString("v_supplier_nm"));
				
				startRow++;
				startRow++;
				
				//리스트 시작
				
				row =sheet.createRow(startRow);
				cell = row.createCell(0);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("RawCode");
				cell = row.createCell(1);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Chinese Name");
				cell = row.createCell(2);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("INCI name");
				cell = row.createCell(3);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Wt(%)");
				cell = row.createCell(4);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("mixure ratio");
				cell = row.createCell(5);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Actual Wt(%)");
				cell = row.createCell(6);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Chinese Function");
				cell = row.createCell(7);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Function");
				cell = row.createCell(8);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("CAS No.");
				cell = row.createCell(9);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Remark");
				cell = row.createCell(10);
				cell.setCellStyle(theadStyle);
				cell.setCellValue("Safety");
				
				startRow++;
				
				listStartRow = startRow+1;
				
				int listSize = list.size();
				int spanStartRow = startRow+1;
				int rownum = 1;
				String tempRawCd = "";
				
				
				if(listSize>0){
				
					for(int i=0 ; i<listSize ; i++){
						row =sheet.createRow(startRow);
						cell = row.createCell(0);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_raw_cd"));
						cell = row.createCell(1);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_conname_cn"));
						cell = row.createCell(2);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_conname_en"));
						cell = row.createCell(3);
						cell.setCellStyle(tablenum);
						
						if(tempRawCd.equals(list.get(i).getString("v_raw_cd"))){
							cell.setCellValue(0);
						}else{
						cell.setCellValue(list.get(i).getDouble("v_raw_per"));
						}
						
						cell = row.createCell(4);
						cell.setCellStyle(tablenum);
						cell.setCellValue(list.get(i).getDouble("v_con_in_per"));
						cell = row.createCell(5);
						cell.setCellStyle(tablenum);
						cell.setCellValue(list.get(i).getDouble("v_actual_wt"));
						cell = row.createCell(6);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_comp_fc_nm_ch"));
						cell = row.createCell(7);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_comp_fc_nm"));
						cell = row.createCell(8);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_comp_cas_no"));
						cell = row.createCell(9);
						cell.setCellStyle(tableStyle);
						cell.setCellValue(list.get(i).getString("v_comp_mixre"));
						
						if(list.get(i).getString("v_danger_yn").equals("")){
						cell = row.createCell(10);
						cell.setCellStyle(tableStyle);
						cell.setCellValue("x");
						}else{
							cell = row.createCell(10);
							cell.setCellStyle(tableStyle);
							cell.setCellValue(list.get(i).getString("v_danger_yn"));
						}
						
						if(!list.get(i).getString("v_raw_cd").equals(tempRawCd))
						{
							if(rownum!=1 && list.get(i-1).getInt("n_raw_cnt")!=1){
									sheet.addMergedRegion(new CellRangeAddress(spanStartRow, spanStartRow+list.get(i-1).getInt("n_raw_cnt")-1, 0, 0));
									sheet.addMergedRegion(new CellRangeAddress(spanStartRow, spanStartRow+list.get(i-1).getInt("n_raw_cnt")-1, 3, 3));
									sheet.addMergedRegion(new CellRangeAddress(spanStartRow, spanStartRow+list.get(i-1).getInt("n_raw_cnt")-1, 6, 6));
									sheet.addMergedRegion(new CellRangeAddress(spanStartRow, spanStartRow+list.get(i-1).getInt("n_raw_cnt")-1, 7, 7));								
							}
							spanStartRow = startRow;
							tempRawCd=list.get(i).getString("v_raw_cd");
							rownum++;
						}
						startRow++;
					}
				}
				//리스트 종료
				
				row = sheet.createRow(startRow);
				cell = row.createCell(3);
				cell.setCellFormula("SUM(D"+listStartRow+":D"+startRow+")");
				cell = row.createCell(5);
				cell.setCellFormula("SUM(F"+listStartRow+":F"+startRow+")");
			
			}
		
		}
}
	