package com.shinsegae_inc.sitims.common.util;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.json.JSONArray;
import org.json.JSONObject;

public class Dhtmlx6Excel {
	
	public Dhtmlx6Excel() {}
	public HSSFWorkbook buildHSSFWorkbook(String jsonData) throws Exception {
		HSSFWorkbook	wb		= new HSSFWorkbook();
		JSONObject	jsonObj		= new JSONObject(jsonData);
		JSONArray	headerArr	= jsonObj.getJSONArray("header").getJSONArray(0);
		JSONArray	dataArr		= jsonObj.getJSONArray("data");
	//	JSONArray	colArr		= jsonObj.getJSONArray("columns");

		HSSFSheet		sheet1	= wb.createSheet(jsonObj.getString("name"));

		HSSFRow		row;
	//	HSSFCell	cell	= null;

		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);

		HSSFCellStyle	theadStyle	= (HSSFCellStyle) this.getHSSFCellStyle(wb, "#848484", "#ffffff", true);
		HSSFCellStyle	tableStyle	= (HSSFCellStyle) cellStyle.get("default");

		// 필드 너비 설정
	//	for (int i = 0; i < colArr.length(); i++) {
		for (int i = 0; i < headerArr.length(); i++) {
		//	int	width	= colArr.getJSONObject(i).getInt("width");
		//	sheet1.setColumnWidth(i, width * 10);
			sheet1.autoSizeColumn(i);
		}
		
		// Header
		row	= sheet1.createRow(0);
		for (int j = 0; j < headerArr.length(); j++){
			this.createCell(row, j, theadStyle, headerArr.getString(j));
		}
		
		// List
		for (int i = 0; i < dataArr.length(); i++){
			row	= sheet1.createRow(i + 1);
			JSONArray	dataArrSub	= dataArr.getJSONArray(i);
			for (int j = 0; j < dataArrSub.length(); j++){
				this.createCell(row, j, tableStyle, dataArrSub.getString(j));
			}
		}
		return wb;
	}

	private void createCell(HSSFRow row, int num, HSSFCellStyle style, String value) {
		HSSFCell	cell	= null;
		cell = row.createCell(num);
		cell.setCellStyle(style);
		cell.setCellValue("null".equals(value.toLowerCase()) ? "" : value);
	}

	/*
	private void createCell(HSSFRow row, HSSFCell cell, int num, HSSFCellStyle style, Double value) {
		cell = row.createCell(num);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	*/
	
	private int changeColorHSSFCellStyle(HSSFWorkbook wb, String color) {
		if (CmFunction.isEmpty(color))
			return 0;
		
		int colorR	= Integer.parseInt( color.substring(1, 3), 16);
		int colorG	= Integer.parseInt( color.substring(3, 5), 16);
		int colorB	= Integer.parseInt( color.substring(5, 7), 16);
		
		HSSFPalette	palette		= wb.getCustomPalette();
		HSSFColor	customColor	= palette.findSimilarColor(colorR, colorG, colorB);
		
		return customColor.getIndex();
	}
	/*
	private CellStyle getHSSFCellStyle(HSSFWorkbook wb, String bgColor, String fontColor) {
		return this.getHSSFCellStyle(wb, bgColor, fontColor, false);
	}
	*/
	
	private CellStyle getHSSFCellStyle(HSSFWorkbook wb, String bgColor, String fontColor, boolean bFontWeight) {
		CellStyle	style	= wb.createCellStyle();
		Font		font	= wb.createFont();
		
		if (bFontWeight)
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		font.setFontName("맑은 고딕");
		font.setColor((short) this.changeColorHSSFCellStyle(wb, fontColor));
		
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);

		style.setFillForegroundColor((short) this.changeColorHSSFCellStyle(wb, bgColor));
		
		return style;
	}
	
}
