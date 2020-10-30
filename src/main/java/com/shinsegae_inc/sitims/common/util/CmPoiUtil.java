package com.shinsegae_inc.sitims.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.shinsegae_inc.sitims.common.CmMap;

public class CmPoiUtil 
{
	
	public static HSSFFont ftTitle;
	public static HSSFFont ftTitleTime;
	public static HSSFFont ftTitleTimeU;
	public static HSSFFont ftLink;
	public static HSSFFont ft7;
	public static HSSFFont ft8B;
	public static HSSFFont ft8BUI;
	public static HSSFFont ft9;
	public static HSSFFont ft9B;
	public static HSSFFont ft10;
	public static HSSFFont ft10B;
	public static HSSFFont ft11B;
	
	public static int loadPicture(String path, HSSFWorkbook wb) throws IOException {
		int pictureIndex;
		BufferedInputStream fi = null;
		ByteArrayOutputStream bos = null;
		URL url = new URL(path);

		try {
			fi = new BufferedInputStream(url.openStream());
			bos = new ByteArrayOutputStream();
			int c=0;
			while (c != -1) {
				c = fi.read();
				bos.write(c);
			}
			pictureIndex = wb.addPicture(bos.toByteArray(),
					HSSFWorkbook.PICTURE_TYPE_JPEG);
		} finally {
			if (fi != null)
				fi.close();
			if (bos != null)
				bos.close();
		}
		return pictureIndex;
	}
	
	public static void setBorder(HSSFCellStyle style, String direction, int weigth, int color){
		
		if("B".equals(direction)){
			style.setBorderBottom((short)weigth);
			style.setBottomBorderColor((short)color);
		}
		else if("T".equals(direction)){
			style.setBorderTop((short)weigth);
			style.setTopBorderColor((short)color);
		}
		else if("L".equals(direction)){
			style.setBorderLeft((short)weigth);
			style.setLeftBorderColor((short)color);
		}
		else if("R".equals(direction)){
			style.setBorderRight((short)weigth);
			style.setRightBorderColor((short)color);
		}
	}
	
	/**
	 * 폰트 생성 
	 **/
	public static void createFont(HSSFWorkbook wb){
		
		ftTitle = wb.createFont();
		ftTitle.setFontName("Times New Roman");
		ftTitle.setFontHeightInPoints((short) 14);
		ftTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		ftTitleTime = wb.createFont();
		ftTitleTime.setFontName("Times New Roman");
		ftTitleTime.setFontHeightInPoints((short) 12);
		ftTitleTime.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		ftTitleTime.setColor(HSSFFont.COLOR_RED);
		
		ftTitleTimeU = wb.createFont();
		ftTitleTimeU.setFontName("Times New Roman");
		ftTitleTimeU.setFontHeightInPoints((short) 12);
		ftTitleTimeU.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		ftTitleTimeU.setColor(HSSFFont.COLOR_RED);
		ftTitleTimeU.setUnderline(HSSFFont.U_SINGLE);
		
		ftLink = wb.createFont(); 
		ftLink.setFontName("Arial");
		ftLink.setFontHeightInPoints((short) 10);
		ftLink.setColor(IndexedColors.BLUE.getIndex());
		ftLink.setUnderline(Font.U_SINGLE);
		
		ft7 = wb.createFont();
		ft7.setFontName("맑은 고딕");
		ft7.setFontHeightInPoints((short) 7);
		
		ft8B = wb.createFont();
		ft8B.setFontName("맑은 고딕");
		ft8B.setFontHeightInPoints((short) 8);
		ft8B.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		ft8BUI = wb.createFont();
		ft8BUI.setFontName("맑은 고딕");
		ft8BUI.setFontHeightInPoints((short) 8);
		ft8BUI.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		ft8BUI.setUnderline(HSSFFont.U_SINGLE);
		ft8BUI.setItalic(true);
		
		ft9 = wb.createFont();
		ft9.setFontName("맑은 고딕");
		ft9.setFontHeightInPoints((short) 9);
		
		ft9B = wb.createFont();
		ft9B.setFontName("맑은 고딕");
		ft9B.setFontHeightInPoints((short) 9);
		ft9B.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		ft10 = wb.createFont();
		ft10.setFontName("맑은 고딕");
		ft10.setFontHeightInPoints((short) 10);
		
		ft10B = wb.createFont();
		ft10B.setFontName("맑은 고딕");
		ft10B.setFontHeightInPoints((short) 10);
		ft10B.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		ft11B = wb.createFont();
		ft11B.setFontName("맑은 고딕");
		ft11B.setFontHeightInPoints((short) 11);
		ft11B.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	}
	
	/**
	 * 파란 배경 Table Sub Title
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getTableSubTitleStyle(HSSFWorkbook wb, int fontsize){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setIndention((short)1);
		style.setFont(fontsize == 11 ? CmPoiUtil.ft11B : CmPoiUtil.ft10B);
		
		return style;
	}
	
	/**
	 * 붉은 배경 Table Title
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getRedTableTitleStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setFillForegroundColor(HSSFColor.TAN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setIndention((short)1);
		style.setWrapText(true);
		
		style.setFont(CmPoiUtil.ft11B);
		
		return style;
	}
	
	/**
	 * 회색 배경 Table Title
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getGreyTableTitleStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setIndention((short)1);
		style.setWrapText(true);
		
		style.setFont(CmPoiUtil.ft11B);
		
		return style;
	}
	
	/**
	 * 기본 Table Base
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getTableBaseStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setIndention((short)1);
		style.setWrapText(true);
		
		style.setFont(CmPoiUtil.ft10);
		
		return style;
	}
	
	/**
	 * 기본 Table Base Green
	 * @param wb
	 * @return
	 */
	public static HSSFCellStyle getTableGreenBaseStyle(HSSFWorkbook wb){
		HSSFCellStyle style = wb.createCellStyle();
		
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setIndention((short)1);
		style.setWrapText(true);
		
		style.setFont(CmPoiUtil.ft10);
		
		return style;
	}
	
	public static HSSFRichTextString getRichTextString(HSSFWorkbook wb, String str, int fontSize){
		
		HSSFRichTextString hrts;
		
		if(str.indexOf("@@") > -1){
			
			String[] arrStr = str.split("@@");
			
			String str1 = arrStr[0];
			String str2 = arrStr[1];
			
			int len = str1.length();
			int lastlen = len+str2.length();
			
			hrts = new HSSFRichTextString(str.replace("@@", ""));
			hrts.applyFont(0, len, fontSize == 11 ? CmPoiUtil.ft11B : CmPoiUtil.ft10B);
			hrts.applyFont(len, lastlen, CmPoiUtil.ft9);
		}
		else{
			hrts = new HSSFRichTextString(str);
			Font font = fontSize == 11 ? CmPoiUtil.ft11B : CmPoiUtil.ft10B;
			hrts.applyFont(0, str.length(), font);
		}
		
		
		return hrts;
	}
	
	public static CmMap<String, HSSFFont> createFontForGlobalRequest(HSSFWorkbook wb){
		CmMap<String, HSSFFont> fontVo = new CmMap<String, HSSFFont>();
		
		HSSFFont fTitle;
		HSSFFont fTitleBase;

		fTitle = wb.createFont();
		fTitle.setFontName("맑은 고딕");
		fTitle.setFontHeightInPoints((short) 10);
		fTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fTitle.setColor(HSSFColor.WHITE.index);
		
		fontVo.put("FT_TITLE", fTitle);
		
		fTitleBase = wb.createFont();
		fTitleBase.setFontName("맑은 고딕");
		fTitleBase.setFontHeightInPoints((short) 10);
		
		fontVo.put("FT_BASE", fTitleBase);
		
		return fontVo;
	}
	
	@SuppressWarnings("static-access")
	public static CmMap<String, HSSFCellStyle> createStylesForGlobalRequest(HSSFWorkbook wb, CmMap<String, HSSFFont> fontVo){
		CmMap<String, HSSFCellStyle> styleVo = new CmMap<String, HSSFCellStyle>();
		HSSFCellStyle style;
		
		// 타이틀 스타일
		style = wb.createCellStyle();
		style.setAlignment(style.ALIGN_CENTER);
		style.setVerticalAlignment(style.VERTICAL_BOTTOM);
		
		style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		
		style.setFont((HSSFFont)fontVo.get("FT_TITLE"));
		styleVo.put("title", style);
		
		// 테이블 BASE
		style = wb.createCellStyle();
		style.setAlignment(style.ALIGN_LEFT);
		style.setVerticalAlignment(style.VERTICAL_CENTER);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setIndention((short)1);
		style.setWrapText(true);
		
		style.setFont((HSSFFont)fontVo.get("FT_BASE"));
		styleVo.put("base", style);
		
		return styleVo;
		
	}
	
}
