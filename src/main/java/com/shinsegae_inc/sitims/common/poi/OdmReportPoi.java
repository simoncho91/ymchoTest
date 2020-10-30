package com.shinsegae_inc.sitims.common.poi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;

@SuppressWarnings({"unchecked","rawtypes", "deprecation", "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity"})
public class OdmReportPoi {	
	protected transient final Log logger = LogFactory.getLog(this.getClass());
	
	private int loadPictureUrl(String path, HSSFWorkbook wb) throws IOException {
		int pictureIndex;
		InputStream fi = null;
		ByteArrayOutputStream bos = null;
		int c=0;
		
		try {
			fi = new URL(path).openStream();
			bos = new ByteArrayOutputStream();
			c = fi.read();
			while (c  != -1) {
				bos.write(c);
				c = fi.read();
			}
			pictureIndex = wb.addPicture(bos.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG);
		}finally {
			if (fi != null)
				fi.close();
			if (bos != null)
				bos.close();				
		}
		return pictureIndex;
		
	}
	private int loadPicture(String path, HSSFWorkbook wb) throws IOException {
		int pictureIndex;
		InputStream fi = null;
		ByteArrayOutputStream bos = null;
		int c=0;
		
		File tempFile 	= new File(path);

		try {
			fi = new FileInputStream(tempFile);
			bos = new ByteArrayOutputStream();
			c = fi.read();
			while (c != -1) {
				bos.write(c);
				c = fi.read();
			}
			pictureIndex = wb.addPicture(bos.toByteArray(),
					HSSFWorkbook.PICTURE_TYPE_JPEG);
		}catch(FileNotFoundException e){
			return loadPictureUrl(CmPathInfo.getIMG_PATH()+"no_img.jpg",wb);
		}finally {
			if (fi != null)
				fi.close();
			if (bos != null)
				bos.close();	
		}
		return pictureIndex;
	}
	
	private HSSFSheet noXlsData(HSSFSheet sheet){
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("There is no content viewed.");
		
		return sheet;
	}
	
	public HSSFWorkbook excelMsds(CmMap reqVo, HSSFWorkbook wb) {
		HSSFSheet sheet = wb.createSheet("MSDS");
		
		List<CmMap> mList = (List<CmMap>)reqVo.get("sectionList");
		List<CmMap> sList = (List<CmMap>)reqVo.get("sectionList_sub");
		List<CmMap> hazardList = (List<CmMap>)reqVo.get("hazardList");
		CmMap rvo = (CmMap)reqVo.get("rvo");
		CmMap productInfo = (CmMap)reqVo.get("productInfo");
		CmMap companyInfo = (CmMap)reqVo.get("companyInfo");
		
		String flashPoint = null;
		String fillingGas = null;
		
//		if(!"".equals(rvo.getString("v_flash_point"))){
//			flashPoint = "- Flash point : " + rvo.getString("v_flash_point");
//		}else{
//			flashPoint = "";
//		}
//		if(!"".equals(rvo.getString("v_fill_gas"))){
//			fillingGas = "- Propellant gas : " + rvo.getString("v_fill_gas");
//		}else{
//			fillingGas = "";
//		}
		
		int mCnt = mList == null? 0 : mList.size();
		
		HSSFFont font = wb.createFont();
		
		HSSFCellStyle bold = wb.createCellStyle();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 18);
		
		bold.setFont(font);
		bold.setAlignment(CellStyle.ALIGN_CENTER);
		bold.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bold.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		bold.setTopBorderColor(HSSFColor.BLACK.index);
		bold.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		bold.setBottomBorderColor(HSSFColor.BLACK.index);
		
		HSSFCellStyle bold2 = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 12);
		
		bold2.setFont(font);
		bold2.setAlignment(CellStyle.ALIGN_LEFT);
		bold2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bold2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle boldUl = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 13);
		
		boldUl.setFont(font);
		boldUl.setAlignment(CellStyle.ALIGN_CENTER);
		boldUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		boldUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		boldUl.setWrapText(true);
		boldUl.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		boldUl.setBottomBorderColor(HSSFColor.BLACK.index);
		boldUl.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		HSSFCellStyle defaultUl = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		
		defaultUl.setFont(font);
		defaultUl.setAlignment(CellStyle.ALIGN_CENTER);
		defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultUl.setWrapText(true);
		defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		HSSFCellStyle wrapText = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		wrapText.setFont(font);
		wrapText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		wrapText.setWrapText(true);
		
		sheet.setColumnWidth(0, 2200);
		sheet.setColumnWidth(1, 2200);
		sheet.setColumnWidth(2, 2200);
		sheet.setColumnWidth(3, 2200);
		sheet.setColumnWidth(4, 2200);
		sheet.setColumnWidth(5, 2200);
		sheet.setColumnWidth(6, 2200);
		sheet.setColumnWidth(7, 2200);
		sheet.setColumnWidth(8, 2200);
		sheet.setColumnWidth(9, 2200);
		
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		HSSFCellStyle tableTitleStyle = (HSSFCellStyle) cellStyle.get("default_bold_c");
		
		HSSFRow row;
		HSSFCell cell = null;
		int startRow = 0;

		row = sheet.createRow(startRow);
		cell = row.createCell(0);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, productInfo.getString("v_company_addr1"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, productInfo.getString("v_company_addr2"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, productInfo.getString("v_company_addr3"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, productInfo.getString("v_vendor_nm"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, "Tel : "+companyInfo.getString("v_phone_no"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, "Fax : "+companyInfo.getString("v_fax"));
		
		startRow++;
		startRow++;
		
		if(mCnt > 0){
			row = sheet.createRow(startRow);
			row.setHeightInPoints((short) 52.5);
			CmFunction.styleMarged(sheet, cell, row, bold, 0, 10,"Product Safety Data Sheet");
			
			startRow++;
			startRow++;
			startRow++;
			
			for(CmMap mRow : mList){
				row = sheet.createRow(startRow);
				row.setHeightInPoints((short) 28.5);
				cell=row.createCell(0);
				CmFunction.styleMarged(sheet, cell, row, boldUl, 0, 10,mRow.getString("v_class_nm"));
				startRow++;
				
				if ("S000033".equals(mRow.getString("v_class_cd"))) {
					
					row = sheet.createRow(startRow);
					cell=row.createCell(0);	
					row.setHeightInPoints((short) 5);
					CmFunction.styleMarged(sheet, cell, row, wrapText, 0, 10,"- This is a personal care product and it is safe if consumed as intended and reasonably foreseeable use.");
					startRow++;
				}
				
				for(CmMap sRow : sList){
					if(mRow.getString("v_class_cd").equals(sRow.getString("v_uclass_cd"))){
						row = sheet.createRow(startRow);
						row.setHeightInPoints((short) 18);
						cell=row.createCell(0);
						CmFunction.styleMarged(sheet, cell, row, bold2, 0, 10, sRow.getString("v_class_nm"));
						sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
						startRow++;
						row = sheet.createRow(startRow);
						if(sRow.getString("v_msds_val").trim().length()>80){
							if(sRow.getString("v_class_cd").equals("S000005")){
								row.setHeightInPoints((short) (sRow.getString("v_msds_val").trim().length()/80 * 30));
							}else{
								row.setHeightInPoints((short) (sRow.getString("v_msds_val").trim().length()/50 * 30));
							}
						}else{
							row.setHeightInPoints((short) 30);
						}
						cell=row.createCell(0);
						cell.setCellStyle(wrapText);
						cell.setCellValue(sRow.getString("v_msds_val").trim());
						sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
						startRow++;
					}
				}
				
				if(mRow.getString("v_class_cd").equals("S000006")){
					row = sheet.createRow(startRow);
					if(mRow.getString("v_msds_val").trim().length()>80){
						row.setHeightInPoints((short) (mRow.getString("v_msds_val").trim().length()/80 * 30));
					}else{
						row.setHeightInPoints((short) 30);
					}
					cell=row.createCell(0);
					cell.setCellStyle(wrapText);
					cell.setCellValue(mRow.getString("v_msds_val").trim());
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
					startRow++;
					
				}
				
				if(mRow.getString("v_class_cd").equals("S000007")){
					row = sheet.createRow(startRow);
					if(mRow.getString("v_msds_val").trim().length()>80){
						row.setHeightInPoints((short) (mRow.getString("v_msds_val").trim().length()/80 * 30));
					}else{
						row.setHeightInPoints((short) 30);
					}
					cell=row.createCell(0);
					cell.setCellStyle(wrapText);
					cell.setCellValue(mRow.getString("v_msds_val").trim());
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
					startRow++;
					
					if(!"".equals(mRow.getString("v_msds_val").trim())){
						row = sheet.createRow(startRow);
						CmFunction.styleMarged(sheet, cell, row, wrapText, 0, 10,mRow.getString("v_msds_val").trim());
						startRow++;
					}
					
					startRow++;
					row = sheet.createRow(startRow);
					cell = row.createCell(0);
					cell.setCellStyle(tableTitleStyle);
					CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 0, 3,"Ingredient name");
					cell = row.createCell(3);
					cell.setCellStyle(tableTitleStyle);
					CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 3, 5,"CAS NUMBER");
					cell = row.createCell(5);
					cell.setCellStyle(tableTitleStyle);
					CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 5, 8,"Composition");
					cell = row.createCell(8);
					cell.setCellStyle(tableTitleStyle);
					CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 8, 10,"Part No");
					
				}

				if(mRow.getString("v_class_cd").equals("S000027")){
					row = sheet.createRow(startRow);
					if(mRow.getString("v_msds_val").trim().length()>80){
						row.setHeightInPoints((short) (mRow.getString("v_msds_val").trim().length()/80 * 30));
					}else{
						row.setHeightInPoints((short) 30);
					}
					cell=row.createCell(0);
					cell.setCellStyle(wrapText);
					cell.setCellValue(mRow.getString("v_msds_val").trim());
					
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
					startRow++;
					
//					if(!"".equals(flashPoint)){
//						row = sheet.createRow(startRow);
//						CmFunction.styleMarged(sheet, cell, row, wrapText, 0, 10,flashPoint);
//						startRow++;
//					}
				}
				
				if(mRow.getString("v_class_cd").equals("S000037")){
					row = sheet.createRow(startRow);
					if(mRow.getString("v_msds_val").trim().length()>80){
						row.setHeightInPoints((short) (mRow.getString("v_msds_val").trim().length()/80 * 30));
					}else{
						row.setHeightInPoints((short) 30);
					}
					cell=row.createCell(0);
					cell.setCellStyle(wrapText);
					cell.setCellValue(mRow.getString("v_msds_val").trim());
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
					startRow++;
				}
				
				if(mRow.getString("v_class_cd").equals("S000044")){
					row = sheet.createRow(startRow);
					if(mRow.getString("v_msds_val").trim().length()>80){
						row.setHeightInPoints((short) (mRow.getString("v_msds_val").trim().length()/80 * 30));
					}else{
						row.setHeightInPoints((short) 30);
					}
					cell=row.createCell(0);
					cell.setCellStyle(wrapText);
					cell.setCellValue(mRow.getString("v_msds_val").trim());
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
					startRow++;
				}
				
				if(mRow.getString("v_class_cd").equals("S000045")){
					StringBuffer sbDate= new StringBuffer("\n\r- issuing date : "+CmFunction.getStrDateToString(mRow.getString("v_reg_dtm"), "yyyy-MM-dd")+"\n\r- revised date : "+CmFunction.getStrDateToString(mRow.getString("v_update_dtm"), "yyyy-MM-dd"));
										
					row = sheet.createRow(startRow);
					if(mRow.getString("v_msds_val").trim().length()>80){
						row.setHeightInPoints((short) (mRow.getString("v_msds_val").trim().length()/80 * 30));
					}else{
						row.setHeightInPoints((short) 30);
					}
					cell=row.createCell(0);
					cell.setCellStyle(wrapText);
					cell.setCellValue(mRow.getString("v_msds_val").trim()+sbDate.toString());
					sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 9));
					startRow++;
				}
				
				startRow++;
			}
			
			startRow++;
			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, wrapText, 5, 7,reqVo.getString("i_sDate"));
			
			String  reprUserNm = productInfo.getString("v_repr_user_nm_en");
			
			if(CmFunction.isEmpty(reprUserNm)) {
				reprUserNm = companyInfo.getString("v_repr_user_nm_en");
			}
			
			startRow++;
			startRow++;
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, wrapText, 5, 7,"Signature : ");
			
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = null;
			
			try {
				anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 0, 0, (short) 3, 5);
				patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
				
				anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 7, startRow, (short) 9, startRow+2);
				patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
			} catch (IOException e) {
				logger.error(e.getMessage());				
			}
		}
		return wb;
	}
	
	// 단일처방 공정도 엑셀 다운
	public HSSFWorkbook excelProcess (CmMap reqVo, HSSFWorkbook wb) throws Exception {
		
		HSSFSheet sheet = wb.createSheet("FlowChart");
		
		List<CmMap> lstMap = (List<CmMap>) reqVo.get("conList");
		List<CmMap> sPartList = (List<CmMap>) reqVo.get("folderList");
		
		CmMap prodVo = (CmMap) reqVo.get("prodVo");
		CmMap companyVo = (CmMap)reqVo.get("companyVo");
		
		int mapLen = lstMap == null ? 0 : lstMap.size();
		
		// POI 객체 생성
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		
		// 기본 셀 스타일 설정
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCellStyle partStyle = (HSSFCellStyle) cellStyle.get("partStyle");
		HSSFCellStyle tableStyle = (HSSFCellStyle) cellStyle.get("tableStyle");
		HSSFCellStyle tableTitleStyle = (HSSFCellStyle) cellStyle.get("default_bold_c");
		HSSFCellStyle borderStyle = (HSSFCellStyle) cellStyle.get("default");
		HSSFCellStyle titleStyle = (HSSFCellStyle) cellStyle.get("titleStyle");
		
		// 회사 주소 스타일
		HSSFCellStyle defaultUl = wb.createCellStyle();
		defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultUl.setWrapText(true);
		defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		if (mapLen > 0) {
			int startRow = 0;
			HSSFRow row;
			HSSFCell cell = null;
			// 셀 입력 시작
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 8, 11, prodVo.getString("v_company_addr1"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 8, 11, prodVo.getString("v_company_addr2"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 8, 11, prodVo.getString("v_company_addr3"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 8, 11, prodVo.getString("v_vendor_nm"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 8, 11, "Tel : "+companyVo.getString("v_phone_no"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 8, 11, "Fax : "+companyVo.getString("v_fax"));
			
			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints(32);

			CmFunction.styleMarged(sheet, cell, row, titleStyle, 0, 11,"PRODUCTION FLOW CHART");
			
			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			cell.setCellValue("PRODUCT NAME :");
			cell.setCellStyle(cs);
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 1));
			cell = row.createCell(2);
			cell.setCellValue(prodVo.getString("v_product_nm_en"));
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 2, 10));
			
			startRow++;
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			cell.setCellValue("PRODUCT CODE :");
			cell.setCellStyle(cs);
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 1));
			cell = row.createCell(2);
			cell.setCellValue(prodVo.getString("v_product_cd"));
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 2, 10));
			
			startRow++;
			startRow++;
			startRow++;
			
			int temp = 11;
			int oldPartNum = 0;
			int partNum = 0;
			int basePartNum = 12;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 0, 4,"INCI NAME");
			
			for (int i = 0; i < sPartList.size(); i++) {

				row = sheet.createRow(temp);
				CmMap partMap = (CmMap) sPartList.get(i);
				CmFunction.styleMarged(sheet, cell, row, partStyle, 0, 4,partMap.getString("v_part_nm"));

				for (int j = 0; j < lstMap.size(); j++) {
					CmMap rowMap = (CmMap) lstMap.get(j);
					if (partMap.getInt("n_prc_part_seq") == rowMap.getInt("n_prc_part_seq")) {
						row = sheet.createRow(i + j + 12);
						CmFunction.styleMarged(sheet, cell, row,tableStyle, 0, 4,rowMap.getString("v_con_nm_en"));
						temp = i + j + 13;
						partNum++;
					}
				}
				row = sheet.getRow((int)(partNum/2+basePartNum-1)+oldPartNum);
				cell = row.createCell(5);
				cell.setCellValue(partMap.getString("v_part_nm")+ " Description");
				row = sheet.getRow((int)(partNum/2+basePartNum)+oldPartNum);
				CmFunction.styleMarged(sheet, cell, row, borderStyle, 5, 11,partMap.getString("v_part_desc").replace("\n", " "));
				oldPartNum += partNum;
				partNum = 0;
			}
			
			//엑셀 시트의 컬럼 너비를 변경
			sheet.setColumnWidth(4, 800);
			
			//그리기 시작
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			//그림,선 시작위치 선언
			HSSFClientAnchor anchor = new HSSFClientAnchor(500, 255, 0, 255,(short) 4, 10, (short) 4, lstMap.size() + 12);
			//선 객체 선언
			HSSFSimpleShape shape = patriarch.createSimpleShape(anchor);
			//선 객체 옵션 설정
			shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
			shape.setLineStyleColor(10, 10, 10);
			shape.setFillColor(90, 10, 200);
			shape.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT * 3);
			
			if(!"N".equals(reqVo.getString("i_sSign_flag"))){
				row = sheet.createRow(lstMap.size() + 18);
				cell = row.createCell(6);
				cell.setCellValue(prodVo.getString("v_now_date"));
				sheet.addMergedRegion(new CellRangeAddress(lstMap.size() + 18, lstMap.size() + 18, 6, 8));
				
				row = sheet.createRow(lstMap.size() + 20);
				cell = row.createCell(6);
				cell.setCellValue("Signature : ");
				sheet.addMergedRegion(new CellRangeAddress(lstMap.size() + 20, lstMap.size() + 20, 6, 8));
				
				//바닥 이미지 그리기
				anchor = new HSSFClientAnchor(0, 0, 94, 54, (short) 9,lstMap.size() + 19, (short) 11, lstMap.size() + 22);
				//patriarch.createPicture(anchor,loadPicture(companyVo.getString("v_sign"),wb));
				patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
			}else{
				//바닥 이미지 그리기
				anchor = new HSSFClientAnchor(0, 0, 94, 54, (short) 6,lstMap.size() + 19, (short) 11, lstMap.size() + 27);
				//patriarch.createPicture(anchor,loadPicture(companyVo.getString("v_sign"),wb));
				patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
			}
			
			//리포트 로고 그리기
			anchor = new HSSFClientAnchor(0, 0, 203, 74, (short) 0, 0, (short) 4, 4);
			//patriarch.createPicture(anchor,loadPicture(companyVo.getString("v_logo"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
			
			//화살표 끝 그리기
			anchor = new HSSFClientAnchor(100, 0, 0, 150, (short) 4,lstMap.size() + 13, (short) 5, lstMap.size() + 13);
			patriarch.createPicture(anchor,loadPictureUrl(CmPathInfo.getIMG_PATH() + "icon.png", wb));
		}else{
			//데이터가 없을경우 자료없음 표시
			sheet = this.noXlsData(sheet);
		}
		
		return wb;
	}
	
	// 복합처방 공정도 엑셀 다운
	public HSSFWorkbook excelProcessComplex (CmMap reqVo, HSSFWorkbook wb) throws Exception {
		
		HSSFSheet sheet = wb.createSheet("FlowChart");
		
		List<CmMap> lstMap = (List<CmMap>) reqVo.get("conList");
		List<CmMap> sPartList = (List<CmMap>) reqVo.get("folderList");
		
		int mapLen = lstMap == null ? 0 : lstMap.size();
		
		if (mapLen > 0) {
			
			CmMap prodVo = (CmMap) reqVo.get("prodVo");
			CmMap sDesc = (CmMap) reqVo.get("processVo");
			CmMap companyVo = (CmMap) reqVo.get("companyVo");
			
			// POI 객체 생성
			
			Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
			
			// 기본 셀 스타일 설정
			HSSFCellStyle cs = wb.createCellStyle();
			cs.setWrapText(true);
			cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			HSSFCellStyle partStyle = (HSSFCellStyle) cellStyle.get("partStyle");
			HSSFCellStyle tableStyle = (HSSFCellStyle) cellStyle.get("tableStyle");
			HSSFCellStyle tableStyle2 = (HSSFCellStyle) cellStyle.get("tableStyle2");
			HSSFCellStyle tableTitleStyle = (HSSFCellStyle) cellStyle.get("default_bold_c");
			HSSFCellStyle titleStyle = (HSSFCellStyle) cellStyle.get("titleStyle");
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFCellStyle underline = (HSSFCellStyle) cellStyle.get("coaUnderLineStyle");
			
			// 회사 주소 스타일
			HSSFCellStyle defaultUl = wb.createCellStyle();
			defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			defaultUl.setWrapText(true);
			defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			
			int tableEnd = 0;
			
			// 셀 입력 시작
			HSSFRow row;
			HSSFCell cell = null;
			
			int temp = 19;
			int partnum = 0;
			int partStart = 0;
			int tableStart = 0;
			int startRow = 0;
			sheet.setColumnWidth(9, 1200);
			sheet.setColumnWidth(10, 4800);
			sheet.setColumnWidth(11, 4800);
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 10, 13, prodVo.getString("v_company_addr1"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 10, 13, prodVo.getString("v_company_addr2"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 10, 13, prodVo.getString("v_company_addr3"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 10, 13, prodVo.getString("v_vendor_nm"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 10, 13, "Tel : "+companyVo.getString("v_phone_no"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 10, 13, "Fax : "+companyVo.getString("v_fax"));
			
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints(18);
			cell = row.createCell(0);
			
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints(32);
			
			CmFunction.styleMarged(sheet, cell, row, titleStyle, 0, 13,"PRODUCTION FLOW CHART");
			
			startRow++;
			startRow++;
			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints((short) 25);
			cell = row.createCell(0);
			cell.setCellValue("PRODUCT NAME :");
			cell.setCellStyle(cs);
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 1));
			cell = row.createCell(2);
			cell.setCellValue(prodVo.getString("v_product_nm_en"));
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 2, 12));
			
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints((short) 25);
			cell = row.createCell(0);
			cell.setCellValue("PRODUCT CODE :");
			cell.setCellStyle(cs);
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 1));
			cell = row.createCell(2);
			cell.setCellValue(prodVo.getString("v_product_cd"));
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 2, 12));
			
			startRow++;
			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			CmFunction.styleMarged(sheet, cell, row, underline, 0, 13,"Brief Description");
			
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints((short) 25);
			cell = row.createCell(0);
			cell.setCellValue(sDesc.getString("v_brief_desc"));
			sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, 12));
			
			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 0, 1,"No.");
			CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 1, 3,"SAP CODE");
			CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 3, 6,"INCI NAME");
			CmFunction.styleMarged(sheet, cell, row, tableTitleStyle, 6, 9,"Chinese NAME");
			int tmpRow = startRow++;
			int rowNum = 1;
			for (int i = 0; i < sPartList.size(); i++) {
				
				row = sheet.createRow(tmpRow);
				CmMap partMap = (CmMap) sPartList.get(i);
				CmFunction.styleMarged(sheet, cell, row, partStyle, 0, 9,partMap.getString("v_part_nm"));
				tmpRow++;
				for (int j = 0; j < lstMap.size(); j++) {
					
					CmMap rowMap = (CmMap) lstMap.get(j);
					if (partMap.getInt("n_prc_part_seq") == rowMap.getInt("n_prc_part_seq")) {
						row = sheet.createRow(tmpRow);
						
						if (rowMap.getString("v_con_nm_en").indexOf("*") > 0) {
							Pattern p = Pattern.compile("\\*");
							Matcher m = p.matcher(rowMap.getString("v_con_nm_en"));
							int count = 0;
							
							for (int k = 0; m.find(k); k = m.end())count++;
							
							row.setHeightInPoints((float) 18 * (count + 1));
						} else {
							int rowLength = rowMap.getString("v_con_nm_en").length();
							row.setHeightInPoints((short) (18 * (rowLength / 20 + 1)));
							
						}

						//CmFunction.styleMarged(sheet,cell,row,tableStyle,0,1,rowMap.getString("v_row_num"));
						CmFunction.styleMarged(sheet,cell,row,tableStyle,0,1,String.valueOf((rowNum++)));
						CmFunction.styleMarged(sheet,cell,row,tableStyle,1,3,rowMap.getString("v_raw_cd"));
						CmFunction.styleMarged(sheet,cell,row,tableStyle,3,6,rowMap.getString("v_con_nm_en").replace("*", "\n"));
						CmFunction.styleMarged(sheet,cell,row,tableStyle,6,9,rowMap.getString("v_con_nm_cn").replace("*", "\n"));
						
						temp = tmpRow++;
						//temp = i + j + 21;
						partnum++;
						
						//선그리기
						if (partnum == 1) {
							
							//파트 시작 가로줄 그리기
							HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
							HSSFClientAnchor anchor = new HSSFClientAnchor(100,0, 300, 0, (short) 9, temp - 1,(short) 9, temp - 1);
							HSSFSimpleShape shape = patriarch.createSimpleShape(anchor);
							shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
							shape.setLineStyleColor(10, 10, 10);
							shape.setFillColor(90, 10, 200);
							shape.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
							partStart = temp - 1;
							
							//파트시작부분 값 입력
							cell = row.createCell(10);
							CmFunction.styleMarged(sheet,cell,row,tableStyle2,10,12,partMap.getString("v_part_desc").replace("\n", " "));
							//cell.setCellValue(partMap.getString("v_part_description").replace("\n", " "));
							
							sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 10, 11));
							
							patriarch = sheet.createDrawingPatriarch();
							anchor = new HSSFClientAnchor(300, 0, 300, 0,(short) 9, temp - 1, (short) 12,temp - 1);
							shape = patriarch.createSimpleShape(anchor);
							shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
							shape.setLineStyleColor(10, 10, 10);
							shape.setFillColor(90, 10, 200);
							shape.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
							
							if (tableStart == 0) {
								tableStart = temp - 1;
							}
							
						}
					}
					
					if (j == lstMap.size() - 1) {
						
						//파트 세로줄 그리기
						HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
						HSSFClientAnchor anchor = new HSSFClientAnchor(100, 0,300, 0, (short) 9, temp, (short) 9, temp);
						HSSFSimpleShape shape = patriarch.createSimpleShape(anchor);
						shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
						shape.setLineStyleColor(10, 10, 10);
						shape.setFillColor(90, 10, 200);
						shape.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
						
						anchor = new HSSFClientAnchor(300, 0, 300, 0, (short) 9,partStart, (short) 9, temp);
						shape = patriarch.createSimpleShape(anchor);
						shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
						shape.setLineStyleColor(10, 10, 10);
						shape.setFillColor(90, 10, 200);
						shape.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
						
						partnum = 0;
						partStart = 0;
					}
				}
				
				//전체리스트 세로줄 그리기
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				HSSFClientAnchor anchor = new HSSFClientAnchor(300, 0, 0, 0,(short) 12, tableStart, (short) 12, temp + 6);
				HSSFSimpleShape shape = patriarch.createSimpleShape(anchor);
				shape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
				shape.setLineStyleColor(10, 10, 10);
				shape.setFillColor(90, 10, 200);
				shape.setLineWidth(HSSFShape.LINEWIDTH_ONE_PT);
				
				tableEnd = temp + 6;
				
			}
			
			if(sDesc.getString("v_degassing_ck").equals("Y")){
				
				row = sheet.createRow(tableEnd - 5);
				cell = row.createCell(13);
				cell.setCellValue("Degassing");
			}
			
			if(sDesc.getString("v_cooling_ck").equals("Y")){
				
				row = sheet.createRow(tableEnd - 4);
				cell = row.createCell(13);
				cell.setCellValue("Cooling ("+sDesc.getString("v_cooling")+" ℃)");
			}
			
			if(sDesc.getString("v_filtration_ck").equals("Y")){
				
				row = sheet.createRow(tableEnd - 3);
				cell = row.createCell(13);
				cell.setCellValue("Filtration");
			}
			
			if(sDesc.getString("v_storage_ck").equals("Y")){
				
				row = sheet.createRow(tableEnd - 2);
				cell = row.createCell(13);
				cell.setCellValue("Storage");
			}
			
		
		//그림 그리기
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = null;
		
		if(!"N".equals(reqVo.getString("i_sSign_flag"))){
			row = sheet.createRow(tableEnd + 4);
			cell = row.createCell(9);
			cell.setCellValue(prodVo.getString("v_now_date"));
			sheet.addMergedRegion(new CellRangeAddress(tableEnd + 4, tableEnd + 4, 9, 10));
			
			row = sheet.createRow(tableEnd + 6);
			cell = row.createCell(9);
			cell.setCellValue("Signature : ");
			sheet.addMergedRegion(new CellRangeAddress(tableEnd + 6, tableEnd + 6, 9, 10));
			
			//바닥 그림
			anchor = new HSSFClientAnchor(54, 0, 0, 94, (short) 11, tableEnd + 4,(short) 13, tableEnd + 8);
			//patriarch.createPicture(anchor,loadPicture(companyVo.getString("v_sign"),wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
		}else{
			//바닥 그림
			anchor = new HSSFClientAnchor(54, 0, 0, 94, (short) 9, tableEnd + 4,(short) 13, tableEnd + 14);
			//patriarch.createPicture(anchor,loadPicture(companyVo.getString("v_sign"),wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
		}
		
		//로고 그림
		anchor = new HSSFClientAnchor(0, 0, 0, 255, (short) 0, 0, (short) 4, 4);
		//patriarch.createPicture(anchor,loadPicture(companyVo.getString("v_logo"), wb));
		patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
		//화살표 끝 그림
		anchor = new HSSFClientAnchor(180, 0, 480, 120, (short) 12, tableEnd,(short) 12, tableEnd);
		patriarch.createPicture(anchor,loadPictureUrl(CmPathInfo.getIMG_PATH() + "icon.png", wb));
		//patriarch.createPicture(anchor,loadPicture("C:\\Users\\WOO\\eclipse-workspace\\sitims\\src\\main\\resources\\static\\sitims\\IMG\\" + "icon.png", wb));
		
		}else{
			//데이터가 없을경우 자료 없음 표시
			sheet = this.noXlsData(sheet);
		}
		
		return wb;
	}

	public HSSFWorkbook excelSingleFormula(CmMap reqVo, HSSFWorkbook wb) throws IOException {
		CmMap productInfo = (CmMap)reqVo.get("productInfo");
		CmMap companyInfo = (CmMap)reqVo.get("companyInfo");
		CmMap maycontain = (CmMap)reqVo.get("maycontain");
		
		List<CmMap> conList = (List<CmMap>)reqVo.get("list");
		List<CmMap> fragList = (List<CmMap>)reqVo.get("fragList");
		String allergenYn = reqVo.getString("i_sAllergenFlag");
		
		DecimalFormat exFormat = new DecimalFormat("##0.00000000000000");
		
		HSSFRow row;
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("single_formaula");
		sheet = wb.getSheetAt(0); 
		
		HSSFFont font = wb.createFont();
		
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		HSSFCellStyle theadStyle = (HSSFCellStyle)cellStyle.get("theadStyle");
		HSSFCellStyle tableStyle = (HSSFCellStyle)cellStyle.get("default");
		tableStyle.setWrapText(true);
		
		HSSFCellStyle bold = wb.createCellStyle();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 18);
		
		bold.setFont(font);
		bold.setAlignment(CellStyle.ALIGN_CENTER);
		bold.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bold.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		bold.setTopBorderColor(HSSFColor.BLACK.index);

		HSSFCellStyle boldFontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFontsizeDefault.setFont(font);
		boldFontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		boldFontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		boldFontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle boldUl = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 13);
		
		boldUl.setFont(font);
		boldUl.setAlignment(CellStyle.ALIGN_CENTER);
		boldUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		boldUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		boldUl.setWrapText(true);
		boldUl.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		boldUl.setBottomBorderColor(HSSFColor.BLACK.index);
		boldUl.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		HSSFCellStyle defaultUl = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		
		defaultUl.setFont(font);
		defaultUl.setAlignment(CellStyle.ALIGN_CENTER);
		defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultUl.setWrapText(true);
		defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		HSSFCellStyle tbLeaveYn = wb.createCellStyle();
		tbLeaveYn.setWrapText(true);
		tbLeaveYn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tbLeaveYn.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setBottomBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setLeftBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderRight(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setRightBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderTop(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setTopBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		tbLeaveYn.setFillForegroundColor((short) this.changeColorHSSFCellStyle(wb,"#B7F0B1"));
		tbLeaveYn.setDataFormat(wb.createDataFormat().getFormat("0.00000000000000"));
//		tbLeaveYn.setFillForegroundColor(HSSFColor.GREEN.index);
		
		sheet.setColumnWidth(0, 3200);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(2, 3200);
		sheet.setColumnWidth(3, 3200);
		sheet.setColumnWidth(4, 3200);
		sheet.setColumnWidth(5, 3200);
		sheet.setColumnWidth(6, 3200);
		sheet.setColumnWidth(7, 3200);
		sheet.setColumnWidth(8, 3200);
		sheet.setColumnWidth(9, 3200);
		
		
		int startRow = 0;
		
		row = sheet.createRow(startRow);
		cell = row.createCell(0);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10, CmFunction.isEmpty(productInfo.getString("v_company_addr1"))?"":productInfo.getString("v_company_addr1"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10,CmFunction.isEmpty(productInfo.getString("v_company_addr2"))?"":productInfo.getString("v_company_addr2"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10,CmFunction.isEmpty(productInfo.getString("v_company_addr3"))?"":productInfo.getString("v_company_addr3"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10,productInfo.getString("v_vendor_nm"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10,"Tel : "+companyInfo.getString("v_phone_no"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 6, 10,"Fax : "+companyInfo.getString("v_fax"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		row.setHeightInPoints((short) 52.5);
		CmFunction.styleMarged(sheet, cell, row, bold, 0, 10,"Product Formula");
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 0, 2,"Product Code");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 2, 5,productInfo.getString("v_product_cd"));
		
		startRow++;
		
		row = sheet.createRow(startRow);
		row.setHeightInPoints((short)25);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 0, 2,"Product Name");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 2, 5,productInfo.getString("v_product_nm_en"));
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 0, 2,"Maunfacture");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 2, 5,productInfo.getString("v_vendor_nm"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 0, 3,"INCI name");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 3, 5,"W/W%");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 5, 8,"Function");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 8, 10,"CAS NO.");
		
		startRow++;
		
		StringBuffer fragranceNo = new StringBuffer();
		int conListCnt = conList.size();
		//double conperSum = 0;
		BigDecimal conperSum = BigDecimal.ZERO;
		for(int i=0; i<conListCnt; i++){
			row = sheet.createRow(startRow);
			
			conperSum = new BigDecimal(conList.get(i).getString("v_con_in_per")).add(conperSum);
//			conperSum = conperSum + (conList.get(i).getDouble("v_con_in_per")*1000);
			
			if(conList.get(i).getString("v_con_nm_en").equals("FRAGRANCE")){
				fragranceNo.append(i+1);
				if(i != conListCnt-1){
					fragranceNo.append(',');
				}
			}
			
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 0, 3,conList.get(i).getString("v_con_nm_en"));
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,conList.get(i).getString("v_con_in_per"));
			if("Y".equals(allergenYn)){
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 8,conList.get(i).getString("v_allergen_func"));
			}else{
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 8,conList.get(i).getString("v_comp_origin_fc"));
			}
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 10,conList.get(i).getString("v_odm_casno"));
			
			startRow++;
		}
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 0, 3,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,exFormat.format(conperSum));
//		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,exFormat.format(conperSum/1000));
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 8,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 10,"");
		
		startRow++;
		startRow++;
		
		if(fragList.size() > 0){
			StringBuffer sb = new StringBuffer(1024);
			sb.append("* The Supplier of the Fragrance(").append(fragranceNo).append(") is (");			
			
			for(CmMap temp : fragList){
				sb.append(temp.getString("v_fragrance")+"["+temp.getString("v_manufacture")+"],");
			}
			
			sb.replace(sb.length()-1, sb.length(), "");
			String fraginfo = sb.toString();
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			cell.setCellValue(fraginfo+").");
			
			startRow++;
			startRow++;
		}
		
		if("Y".equals(allergenYn)){
			List<CmMap> allergenList = (List<CmMap>)reqVo.get("allergenList");
			if(allergenList.size() > 0){
				row = sheet.createRow(startRow);
				cell = row.createCell(0);
				cell.setCellValue("(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) :");
				sheet.addMergedRegion(new CellRangeAddress(startRow, startRow , 0, 10));
				
				startRow++;
				row = sheet.createRow(startRow);
				CmFunction.styleMarged2(sheet, cell, row, boldFontsizeDefault,0, 5, " * Allergens in fragrance : ");
				startRow++;
				
				row = sheet.createRow(startRow);
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 0, 3,"INCI name");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 3, 5,"W/W%");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 5, 8,"Function");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 8, 10,"CAS NO.");
				
				startRow++;
				
				for(int i=0; i<allergenList.size(); i++){
					
					CmMap vo = allergenList.get(i);
					if("Y".equals(productInfo.getString("v_part_leaveon_yn"))){
						
						if(vo.getDouble("v_con_in_per") > 0.001){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 0, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 5, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 8, 10,allergenList.get(i).getString("v_odm_casno"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 0, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 10,allergenList.get(i).getString("v_odm_casno"));
							
						}
						
					}else{
						
						if(vo.getDouble("v_con_in_per") > 0.01){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 0, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 5, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 8, 10,allergenList.get(i).getString("v_odm_casno"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 0, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 10,allergenList.get(i).getString("v_odm_casno"));
							
						}
						
					}
					
					startRow++;
				}
				
				startRow++;
				startRow++;
			}
		}

		if(!CmFunction.isEmpty(maycontain.getString("v_may_contain"))){
			StringBuffer sb = new StringBuffer(1024);
			sb.append("* The ingredients following after \"MAY CONTAIN\" : ").append(maycontain.getString("v_may_contain"));
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			cell.setCellValue(sb.toString());
			
			startRow++;
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = null;
		
		//이미지
		anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 0, 0, (short) 3, 5);
		patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
		
		//서명 이미지 추가
		startRow++;
		startRow++;
		row = sheet.createRow(startRow);
		cell = row.createCell(5);
		cell.setCellValue(reqVo.getString("i_sSignDate"));
		startRow++;
		
		String  reprUserNm = productInfo.getString("v_repr_user_nm_en");
		if(CmFunction.isEmpty(reprUserNm)) {
			reprUserNm = companyInfo.getString("v_repr_user_nm_en");
		}
		
		row = sheet.createRow(startRow+1);
		cell = row.createCell(5);
		cell.setCellValue("Signature : ");
		
		anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 7, startRow, (short) 9, startRow+2);
		patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
		return wb;
	}
	
	public HSSFWorkbook excelFormulaKo(CmMap reqVo, HSSFWorkbook wb) {
		
		CmMap productInfo = (CmMap)reqVo.get("productInfo");
		CmMap companyInfo = (CmMap)reqVo.get("companyInfo");
		List<CmMap> conList = (List<CmMap>)reqVo.get("list");
		List<CmMap> fragList = (List<CmMap>)reqVo.get("fragList");
		
		String partno = reqVo.getString("i_sPartNo");
		String allergenYn = reqVo.getString("i_sAllergenFlag");
		
		//double total = 0;
		BigDecimal total = BigDecimal.ZERO;
		String type = "";
		
		DecimalFormat exFormat = new DecimalFormat("##0.0000000000");
		HSSFRow row;
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("formaula_ko");
		sheet = wb.getSheetAt(0); 
		
		HSSFFont font = wb.createFont();
		
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		HSSFCellStyle theadStyle = (HSSFCellStyle)cellStyle.get("theadStyle");
		HSSFCellStyle tableStyle = (HSSFCellStyle)cellStyle.get("default");
		tableStyle.setWrapText(true);
		
		HSSFCellStyle bold = wb.createCellStyle();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 18);
		
		bold.setFont(font);
		bold.setAlignment(CellStyle.ALIGN_CENTER);
		bold.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bold.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		bold.setTopBorderColor(HSSFColor.BLACK.index);
		
		HSSFCellStyle boldFontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFontsizeDefault.setFont(font);
		boldFontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		boldFontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		boldFontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFCellStyle fontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		fontsizeDefault.setFont(font);
		fontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		fontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		fontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle defaultUl = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		
		defaultUl.setFont(font);
		defaultUl.setAlignment(CellStyle.ALIGN_CENTER);
		defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultUl.setWrapText(true);
		defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		HSSFCellStyle base = wb.createCellStyle();
		font.setFontHeightInPoints((short) 10);
		
		base.setFont(font);
		base.setAlignment(CellStyle.ALIGN_CENTER);
		base.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		base.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle tbLeaveYn = wb.createCellStyle();
		tbLeaveYn.setWrapText(true);
		tbLeaveYn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tbLeaveYn.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setBottomBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setLeftBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderRight(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setRightBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderTop(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setTopBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		tbLeaveYn.setFillForegroundColor((short) this.changeColorHSSFCellStyle(wb,"#B7F0B1"));
		tbLeaveYn.setDataFormat(wb.createDataFormat().getFormat("0.0000000000"));
//		tb_Leave_Yn.setFillForegroundColor(HSSFColor.GREEN.index);
		
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 4200);
		sheet.setColumnWidth(2, 3300);
		sheet.setColumnWidth(3, 3300);
		sheet.setColumnWidth(4, 3300);
		sheet.setColumnWidth(5, 3300);
		sheet.setColumnWidth(6, 3200);
		sheet.setColumnWidth(7, 3200);
		sheet.setColumnWidth(8, 3200);
		sheet.setColumnWidth(9, 3200);
		
		
		int startRow = 0;
		
		row = sheet.createRow(startRow);
		cell = row.createCell(0);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 7, 10, productInfo.getString("v_company_addr1"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 7, 10,productInfo.getString("v_company_addr2"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 7, 10,productInfo.getString("v_company_addr3"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 7, 10,productInfo.getString("v_vendor_nm"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 7, 10,"Tel : "+companyInfo.getString("v_phone_no"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 7, 10,"Fax : "+companyInfo.getString("v_fax"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		row.setHeightInPoints((short) 52.5);
		CmFunction.styleMarged(sheet, cell, row, bold, 0, 10,"Product Formula");
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"제품명");
		row.setHeightInPoints((short)25);
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_product_nm_en"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		cell = row.createCell(1);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("원료코드");
		
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 2, 3,"함량");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 3, 5,"영문 허가명");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 5, 7,"한글 허가명");
		
		cell = row.createCell(7);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("규격");
		
		cell = row.createCell(8);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("배합 목적");
		
		startRow++;
		
		int conListCnt = conList.size();
		int fragNo = 0;
		int cnt = 0;
		for(int i=0; i<conListCnt; i++){
			if(partno.equals(conList.get(i).getString("n_part_no"))){
				row = sheet.createRow(startRow);
				
				if("FRAGRANCE".equals(conList.get(i).getString("v_con_nm_en"))){
					fragNo = cnt+i;
				}
				
				if("AP".equals(conList.get(i).getString("v_type"))){
					type = " (AP)";
				}else{
					type = "";
				}
				
				sheet.setColumnWidth(1, 4200);
				cell = row.createCell(1);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_raw_cd") + type);
				//total = total + conList.get(i).getDouble("n_raw_per")*1000;
				total = new BigDecimal(conList.get(i).getString("n_raw_per")).add(total);
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 2, 3,conList.get(i).getString("n_raw_per"));
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,conList.get(i).getString("v_con_nm_en"));
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 7,conList.get(i).getString("v_con_nm_ko"));
				
				cell = row.createCell(7);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_std_based_nm"));
				
				cell = row.createCell(8);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_fcname"));
				
				startRow++;
			}
		}
		
		row = sheet.createRow(startRow);
		
		cell = row.createCell(1);
		cell.setCellStyle(tableStyle);
		cell.setCellValue("");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 2, 3, exFormat.format(total));
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 7,"");
		
		cell = row.createCell(7);
		cell.setCellStyle(tableStyle);
		cell.setCellValue("");
		
		cell = row.createCell(8);
		cell.setCellStyle(tableStyle);
		cell.setCellValue("");
		
		if(!fragList.isEmpty()){
			startRow++;
			
			row = sheet.createRow(startRow);
			StringBuffer sb = new StringBuffer(1024);
			sb.append("* The Supplier of the FRAGRANCE (")
			.append(fragNo+1)
			.append(") is (");
			//sb.append("* The Supplier of the FRAGRANCE ("+fragNo+1+") is (");
			
			for(int i=0; i<fragList.size(); i++){
				sb.append(fragList.get(i).getString("v_fragrance")+','+fragList.get(i).getString("v_fragrance_smell")
				+'['+fragList.get(i).getString("v_manufacture"));
				
				if(i+1 == fragList.size()){
					sb.append(" ]).");
				}else{
					sb.append(", ");
				}
			}
			
			CmFunction.styleMarged(sheet, cell, row, fontsizeDefault, 1, 11, sb.toString());
		}
		
		startRow++;
		startRow++;
		
		if("Y".equals(allergenYn)){
			List<CmMap> allergenList = (List<CmMap>)reqVo.get("allergenList");
			if(allergenList.size() > 0){
				row = sheet.createRow(startRow);
				cell = row.createCell(1);
				cell.setCellValue("(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) :");
				sheet.addMergedRegion(new CellRangeAddress(startRow, startRow , 1, 10));
				
				startRow++;
				row = sheet.createRow(startRow);
				CmFunction.styleMarged2(sheet, cell, row, boldFontsizeDefault,1, 10, " * Allergens in fragrance : ");
				startRow++;
				
				row = sheet.createRow(startRow);
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"INCI name");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 3, 5,"W/W%");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 5, 7,"Function");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 7, 9,"CAS NO.");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 9, 11,"한글허가명");
				
				startRow++;
				
				for(int i=0; i<allergenList.size(); i++){
					
					CmMap vo = allergenList.get(i);
					if("Y".equals(productInfo.getString("v_part_leaveon_yn"))){
						
						if(vo.getDouble("v_con_in_per") > 0.001){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 1, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 5, 7,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 7, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 9, 11,allergenList.get(i).getString("v_con_nm_ko"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 1, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 7,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 7, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 9, 11,allergenList.get(i).getString("v_con_nm_ko"));
							
						}
						
					}else{
						
						if(vo.getDouble("v_con_in_per") > 0.01){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 1, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 5, 7,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 7, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 9, 11,allergenList.get(i).getString("v_con_nm_ko"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 1, 3,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 7,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 7, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 9, 11,allergenList.get(i).getString("v_con_nm_ko"));
							
						}
						
					}
					
					startRow++;
				}
				
				startRow++;
				startRow++;
			}
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = null;
		
		try {
			//이미지
			anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 0, 0, (short) 3, 5);
			//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_logo"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
			
			//서명 이미지 추가
			startRow++;
			startRow++;
			row = sheet.createRow(startRow);
			cell = row.createCell(5);
			cell.setCellValue(reqVo.getString("i_sSignDate"));
			startRow++;
			
			String  reprUserNm = productInfo.getString("v_repr_usernm_en");
			if(CmFunction.isEmpty(reprUserNm)) {
				reprUserNm = companyInfo.getString("v_repr_usernm_en");
			}

			row = sheet.createRow(startRow+1);
			cell = row.createCell(5);
			cell.setCellValue("Signature : ");
			
			anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 7, startRow, (short) 9, startRow+2);
			//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_sign_url"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
	
			
		} catch (IOException e) {

			logger.error(e.getMessage());
		}
		return wb;
	}
	
	public HSSFWorkbook excelFormulaJp(CmMap reqVo, HSSFWorkbook wb) {
		
		CmMap productInfo = (CmMap)reqVo.get("productInfo");
		CmMap companyInfo = (CmMap)reqVo.get("companyInfo");
		List<CmMap> conList = (List<CmMap>)reqVo.get("list");
		List<CmMap> fragList = (List<CmMap>)reqVo.get("fragList");
		
		//double wt_total = 0;
		BigDecimal wtTotal = BigDecimal.ZERO;
		//double actual_wt_total = 0;
		BigDecimal actualWtTotal = BigDecimal.ZERO;
		
		String type = "";
		String allergenYn = reqVo.getString("i_sAllergenFlag");
		
		HSSFRow row;
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("formaula_jp");
		sheet = wb.getSheetAt(0); 
		
		HSSFFont font = wb.createFont();
		
		DecimalFormat exFormat = new DecimalFormat("##0.0000000000");
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		HSSFCellStyle theadStyle = (HSSFCellStyle)cellStyle.get("theadStyle");
		HSSFCellStyle tableStyle = (HSSFCellStyle)cellStyle.get("default");
		tableStyle.setWrapText(true);
		tableStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		HSSFCellStyle bold = wb.createCellStyle();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 18);
		
		bold.setFont(font);
		bold.setAlignment(CellStyle.ALIGN_CENTER);
		bold.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bold.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		bold.setTopBorderColor(HSSFColor.BLACK.index);
		
		HSSFCellStyle fontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		fontsizeDefault.setFont(font);
		fontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		fontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		fontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle boldFontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFontsizeDefault.setFont(font);
		boldFontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		boldFontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		boldFontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle defaultUl = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		
		defaultUl.setFont(font);
		defaultUl.setAlignment(CellStyle.ALIGN_CENTER);
		defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultUl.setWrapText(true);
		defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		HSSFCellStyle base = wb.createCellStyle();
		font.setFontHeightInPoints((short) 10);
		
		base.setFont(font);
		base.setAlignment(CellStyle.ALIGN_CENTER);
		base.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		base.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFCellStyle tbLeaveYn = wb.createCellStyle();
		tbLeaveYn.setWrapText(true);
		tbLeaveYn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tbLeaveYn.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setBottomBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setLeftBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderRight(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setRightBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderTop(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setTopBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		tbLeaveYn.setFillForegroundColor((short) this.changeColorHSSFCellStyle(wb,"#B7F0B1"));
		tbLeaveYn.setDataFormat(wb.createDataFormat().getFormat("0.0000000000"));
//		tb_Leave_Yn.setFillForegroundColor(HSSFColor.GREEN.index);
		
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 1200);
		sheet.setColumnWidth(2, 4200);
		
		sheet.setColumnWidth(3, 3500);
		sheet.setColumnWidth(4, 3500);
		sheet.setColumnWidth(5, 3500);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 3000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 3000);
		sheet.setColumnWidth(12, 3000);
		
		sheet.setColumnWidth(13, 4000);
		sheet.setColumnWidth(14, 4000);
		sheet.setColumnWidth(15, 4000);
		sheet.setColumnWidth(16, 4000);
		
		int startRow = 0;
		
		row = sheet.createRow(startRow);
		cell = row.createCell(0);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 17, productInfo.getString("v_company_addr1"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 17, productInfo.getString("v_company_addr2"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 17, productInfo.getString("v_company_addr3"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 17, productInfo.getString("v_vendor_nm"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 17, "Tel : "+companyInfo.getString("v_phone_no"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 17, "Fax : "+companyInfo.getString("v_fax"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		row.setHeightInPoints((short) 52.5);
		CmFunction.styleMarged(sheet, cell, row, bold, 0, 17,"Product Formula");
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"Product Code");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_product_cd"));
		startRow++;
		
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"Product Name");
		row.setHeightInPoints((short)25);
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_product_nm_en"));
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"Manufacture");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_vendor_nm"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		cell = row.createCell(1);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("No");
		
		cell = row.createCell(2);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("RawCode");
		
		cell = row.createCell(3);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("IngredCode");
		
		
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 4, 6,"INCI Name");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 6, 8,"JAPAN Name");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 8, 9,"Restriction");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 9, 10,"Wt(%)");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 10, 11,"mixure ratio");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 11, 12,"Actual Wt(%)");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 12, 13,"Function");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 13, 14,"CAS No.");
		
		cell = row.createCell(14);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Remark");
		
		cell = row.createCell(15);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Global forbid");
		
		cell = row.createCell(16);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Global limit");
		
		startRow++;
		
		int conListCnt = conList.size();
		String beforeRawCd = "";
		int beforeCnt = 0;
		int cnt = 1;
		int fragNo = 0;
		for(int i=0; i<conListCnt; i++){
			//actual_wt_total = actual_wt_total+conList.get(i).getDouble("v_actual_wt")*1000;
			actualWtTotal = new BigDecimal(conList.get(i).getString("v_actual_wt")).add(actualWtTotal);
			row = sheet.createRow(startRow);
			
			if((!beforeRawCd.equals(conList.get(i).getString("v_raw_cd"))&& (beforeCnt > 1))){
				//wt_total = wt_total+conList.get(i-1).getDouble("v_raw_per")*1000;
				wtTotal = new BigDecimal(conList.get(i-1).getString("v_raw_per")).add(wtTotal);
				cell = row.createCell(1); 
				CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, Integer.toString(cnt));
				
				if("AP".equals(conList.get(i-1).getString("v_type"))){
					type = " (AP)";
				}else{
					type = "";
				}
				
				cell = row.createCell(2); 
				CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_raw_cd")+type);
				
				cell = row.createCell(9); 
				CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_raw_per"));
				
				cell = row.createCell(12); 
				CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_comp_fc_nm"));
				
				cnt++;
			}
			
			if(conList.get(i).getInt("n_raw_cnt") == 1){
				//wt_total = wt_total+conList.get(i).getDouble("v_raw_per")*1000;
				wtTotal = new BigDecimal(conList.get(i).getString("v_raw_per")).add(wtTotal);
				cell = row.createCell(1);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(cnt);
				
				if("AP".equals(conList.get(i).getString("v_type"))){
					type = " (AP)";
				}else{
					type = "";
				}
				
				cell = row.createCell(2);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_raw_cd")+type);
				
				cell = row.createCell(9); 
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_raw_per"));
				
				cell = row.createCell(12); 
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_comp_fc_nm"));
				
				cnt++;
			}else{
				if(i == conListCnt-1){
					//wt_total = wt_total+conList.get(i).getDouble("v_raw_per")*1000;
					wtTotal = new BigDecimal(conList.get(i).getString("v_raw_per")).add(wtTotal);
					int nRow = conList.get(i).getInt("n_raw_cnt");
					cell = row.createCell(1); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, Integer.toString(cnt));
					
					cell = row.createCell(2); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_raw_cd"));
					
					cell = row.createCell(9); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_raw_per"));
					
					cell = row.createCell(12); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_comp_fc_nm"));
					
					cnt++;
				}
			}
			
			if("FRAGRANCE".equals(conList.get(i).getString("v_con_nm_en"))){
				fragNo = cnt-1;
			}
			
			cell = row.createCell(3);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(conList.get(i).getString("v_con_cd"));
			
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 4, 6,conList.get(i).getString("v_con_nm_en"));
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 6, 8,conList.get(i).getString("v_con_nm_jp"));
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 9,conList.get(i).getString("v_restriction"));
			
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 10, 11, conList.get(i).getString("v_con_in_per"));
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 11, 12, conList.get(i).getString("v_actual_wt"));
			CmFunction.styleMarged(sheet, cell, row, tableStyle, 13, 14, conList.get(i).getString("v_odm_casno"));
			
			cell = row.createCell(14);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(conList.get(i).getString("v_comp_mixre"));
			
			cell = row.createCell(15);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(conList.get(i).getString("v_zglobal"));
			
			cell = row.createCell(16);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(conList.get(i).getString("v_zgllim"));
			
			startRow++;
			beforeRawCd = conList.get(i).getString("v_raw_cd");
			beforeCnt = conList.get(i).getInt("n_raw_cnt");
		}
		
		row = sheet.createRow(startRow);
		cell = row.createCell(1);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(2);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(3);
		cell.setCellStyle(tableStyle);
		
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 4, 6,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 6, 8,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 9,"");
		
		cell = row.createCell(9); 
		cell.setCellStyle(tableStyle);
		cell.setCellValue(exFormat.format(wtTotal));
		
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 10, 11,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 11, 12, exFormat.format(actualWtTotal));
		cell = row.createCell(12); 
		cell.setCellStyle(tableStyle);
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 13, 14,"");
		
		cell = row.createCell(14);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(15);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(16);
		cell.setCellStyle(tableStyle);
		
		if(!fragList.isEmpty()){
			startRow++;
			
			row = sheet.createRow(startRow);
			StringBuffer sb = new StringBuffer();
			sb.append("* The Supplier of the FRAGRANCE (").append(fragNo).append(") is (");
			
			for(int i=0; i<fragList.size(); i++){
				sb.append(fragList.get(i).getString("v_fragrance")+','+fragList.get(i).getString("v_fragrance_smell"));
				sb.append('['+fragList.get(i).getString("v_manufacture"));
				
				if(i+1 == fragList.size()){
					sb.append(" ]).");
				}else{
					sb.append(", ");
				}
			}
			
			CmFunction.styleMarged(sheet, cell, row, fontsizeDefault, 1, 14, sb.toString());
		}
		
		startRow++;
		startRow++;
		
		if("Y".equals(allergenYn)){
			List<CmMap> allergenList = (List<CmMap>)reqVo.get("allergenList");
			if(allergenList.size() > 0){
				row = sheet.createRow(startRow);
				cell = row.createCell(1);
				cell.setCellValue("(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) :");
				sheet.addMergedRegion(new CellRangeAddress(startRow, startRow , 1, 11));
				
				startRow++;
				row = sheet.createRow(startRow);
				CmFunction.styleMarged2(sheet, cell, row, boldFontsizeDefault,1, 10, " * Allergens in fragrance : ");
				startRow++;
				
				row = sheet.createRow(startRow);
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 4,"INCI name");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 4, 6,"W/W%");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 6, 8,"Function");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 8, 9,"CAS NO.");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 9, 10,"JAPAN Name");
				
				
				startRow++;
				
				for(int i=0; i<allergenList.size(); i++){
					
					CmMap vo = allergenList.get(i);
					if("Y".equals(productInfo.getString("v_part_leaveon_yn"))){
						
						if(vo.getDouble("v_con_in_per") > 0.001){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 9, 10,allergenList.get(i).getString("v_con_nm_jp"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 9, 10,allergenList.get(i).getString("v_con_nm_jp"));
							
						}
						
					}else{
						
						if(vo.getDouble("v_con_in_per") > 0.01){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 9, 10,allergenList.get(i).getString("v_con_nm_jp"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 9, 10,allergenList.get(i).getString("v_con_nm_jp"));
							
						}
						
					}
					
					startRow++;
				}
				
				startRow++;
				startRow++;
			}
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = null;
		
		try {
			anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 0, 0, (short) 3, 5);
			//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_logo"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
			
			//서명 이미지 추가
			startRow++;
			startRow++;
			row = sheet.createRow(startRow);
			cell = row.createCell(12);
			cell.setCellValue(reqVo.getString("i_sSignDate"));
			startRow++;
			
			String  reprUserNm = productInfo.getString("v_repr_usernm_en");
			if(CmFunction.isEmpty(reprUserNm)) {
				reprUserNm = companyInfo.getString("v_repr_usernm_en");
			}
			
			row = sheet.createRow(startRow+1);
			cell = row.createCell(12);
			cell.setCellValue("Signature : ");
			
			anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 14, startRow, (short) 16, startRow+2);
			//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_sign_url"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
			
		} catch (IOException e) {

			logger.error(e.getMessage());
		}
		return wb;
	}
	
	public HSSFWorkbook excelFormulaCh(CmMap reqVo, HSSFWorkbook wb) {
		
		CmMap productInfo = (CmMap)reqVo.get("productInfo");
		CmMap companyInfo = (CmMap)reqVo.get("companyInfo");
		List<CmMap> conList = (List<CmMap>)reqVo.get("list");
		List<CmMap> fragList = (List<CmMap>)reqVo.get("fragList");
		
		//double wt_total = 0;
		BigDecimal wtTotal = BigDecimal.ZERO;
		//double actual_wt_total = 0 ;
		BigDecimal actualWtTotal = BigDecimal.ZERO;
		
		String allergenYn = reqVo.getString("i_sAllergenFlag");
		
		HSSFRow row;
		HSSFCell cell = null;
		HSSFSheet sheet = wb.createSheet("formaula_ch");
		sheet = wb.getSheetAt(0);
		
		HSSFFont font = wb.createFont();
		
		DecimalFormat exFormat = new DecimalFormat("##0.0000000000");
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);
		HSSFCellStyle theadStyle = (HSSFCellStyle)cellStyle.get("theadStyle");
		HSSFCellStyle tableStyle = (HSSFCellStyle)cellStyle.get("default");
		tableStyle.setWrapText(true);
		tableStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		
		HSSFCellStyle bold = wb.createCellStyle();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 18);
		
		bold.setFont(font);
		bold.setAlignment(CellStyle.ALIGN_CENTER);
		bold.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		bold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		bold.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		bold.setTopBorderColor(HSSFColor.BLACK.index);
		
		HSSFCellStyle defaultUl = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		
		defaultUl.setFont(font);
		defaultUl.setAlignment(CellStyle.ALIGN_CENTER);
		defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		defaultUl.setWrapText(true);
		defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		
		HSSFCellStyle fontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		fontsizeDefault.setFont(font);
		fontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		fontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		fontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle boldFontsizeDefault = wb.createCellStyle();
		font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldFontsizeDefault.setFont(font);
		boldFontsizeDefault.setAlignment(CellStyle.ALIGN_LEFT);
		boldFontsizeDefault.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		boldFontsizeDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle base = wb.createCellStyle();
		font.setFontHeightInPoints((short) 10);
		
		base.setFont(font);
		base.setAlignment(CellStyle.ALIGN_CENTER);
		base.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		base.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle tbLeaveYn = wb.createCellStyle();
		tbLeaveYn.setWrapText(true);
		tbLeaveYn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		tbLeaveYn.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setBottomBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setLeftBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderRight(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setRightBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setBorderTop(HSSFCellStyle.BORDER_THIN);
		tbLeaveYn.setTopBorderColor(HSSFColor.BLACK.index);
		tbLeaveYn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		tbLeaveYn.setFillForegroundColor((short) this.changeColorHSSFCellStyle(wb,"#B7F0B1"));
		tbLeaveYn.setDataFormat(wb.createDataFormat().getFormat("0.0000000000"));
//		tb_Leave_Yn.setFillForegroundColor(HSSFColor.GREEN.index);
		
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 1200);
		sheet.setColumnWidth(2, 4200);
		
		sheet.setColumnWidth(3, 3500);
		sheet.setColumnWidth(4, 3500);
		sheet.setColumnWidth(5, 3500);
		sheet.setColumnWidth(6, 3500);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 6500);
		sheet.setColumnWidth(12, 3000);
		
		sheet.setColumnWidth(13, 4000);
		sheet.setColumnWidth(14, 5000);
		sheet.setColumnWidth(15, 8000);
		sheet.setColumnWidth(16, 4000);
		sheet.setColumnWidth(17, 4000);
		
		
		
		int startRow = 0;
		String type = "";
		
		row = sheet.createRow(startRow);
		cell = row.createCell(0);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 18, productInfo.getString("v_company_addr1"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 18, productInfo.getString("v_company_addr2"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 18, productInfo.getString("v_company_addr3"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 18, productInfo.getString("v_vendor_nm"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 18,"Tel : "+companyInfo.getString("v_phone_no"));
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, defaultUl, 13, 18,"Fax : "+companyInfo.getString("v_fax"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		row.setHeightInPoints((short) 52.5);
		CmFunction.styleMarged(sheet, cell, row, bold, 0, 18,"Product Formula");
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"Product Code");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_product_cd"));
		startRow++;
		
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"Product Name");
		row.setHeightInPoints((short)25);
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_product_nm_en"));
		
		startRow++;
		
		row = sheet.createRow(startRow);
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 3,"Manufacture");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 6,productInfo.getString("v_vendor_nm"));
		
		startRow++;
		startRow++;
		
		row = sheet.createRow(startRow);
		cell = row.createCell(1);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("No");
		
		cell = row.createCell(2);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("RawCode");
		
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 3, 5,"Chinese name");
		CmFunction.styleMarged(sheet, cell, row, theadStyle, 5, 7,"INCI Name");
		
		cell = row.createCell(7);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Wt(%)");
		
		cell = row.createCell(8);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("mixure ratio");
		
		cell = row.createCell(9);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Actual Wt(%)");
		
		cell = row.createCell(10);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Chinese Function");
		
		cell = row.createCell(11);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Function");
		
		cell = row.createCell(12);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("CAS No.");
		
		cell = row.createCell(13);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Remark");
		
		cell = row.createCell(14);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Safety_ODM Standard");
		
		cell = row.createCell(15);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Global forbid");
		
		cell = row.createCell(16);
		cell.setCellStyle(theadStyle);
		cell.setCellValue("Global limit");
		
		startRow++;
		
		int conListCnt = conList.size();
		String beforeRawCd = "";
		int beforeCnt = 0;
		int cnt = 1;
		int fragNo = 0;
		for(int i=0; i<conListCnt; i++){
			if(!"Y".equals(conList.get(i).getString("v_allergen_yn"))){
				
				row = sheet.createRow(startRow);
				//actual_wt_total = actual_wt_total+conList.get(i).getDouble("v_actual_wt")*1000;
				actualWtTotal = new BigDecimal(conList.get(i).getString("v_actual_wt")).add(actualWtTotal);
				
				if((!beforeRawCd.equals(conList.get(i).getString("v_raw_cd")) && (beforeCnt > 1))){
					if("AP".equals(conList.get(i-1).getString("v_type"))){
						type = " (AP)";
					}else{
						type = "";
					}
					
					//wt_total = wt_total+conList.get(i-1).getDouble("v_raw_per")*1000;
					wtTotal = new BigDecimal(conList.get(i-1).getString("v_raw_per")).add(wtTotal);
					cell = row.createCell(1); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, Integer.toString(cnt));
					
					cell = row.createCell(2); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_raw_cd")+type);
					
					cell = row.createCell(7); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_raw_per"));
					
					cell = row.createCell(10); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_comp_fc_nm_ch"));
					
					cell = row.createCell(11); 
					CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-beforeCnt, startRow-1, conList.get(i-1).getString("v_comp_fc_nm"));
					
					cnt++;
				}
				
				if(conList.get(i).getInt("n_raw_cnt") == 1){
					//wt_total = wt_total+conList.get(i).getDouble("v_raw_per")*1000;
					wtTotal = new BigDecimal(conList.get(i).getString("v_raw_per")).add(wtTotal);
					cell = row.createCell(1);
					cell.setCellStyle(tableStyle);
					
					cell.setCellValue(cnt);
					
					if("AP".equals(conList.get(i).getString("v_type"))){
						type = " (AP)";
					}else{
						type = "";
					}
					
					cell = row.createCell(2);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(conList.get(i).getString("v_raw_cd")+type);
					
					cell = row.createCell(7); 
					cell.setCellStyle(tableStyle);
					cell.setCellValue(conList.get(i).getString("v_raw_per"));
					
					cell = row.createCell(10); 
					cell.setCellStyle(tableStyle);
					cell.setCellValue(conList.get(i).getString("v_comp_fc_nm_ch"));
					
					cell = row.createCell(11); 
					cell.setCellStyle(tableStyle);
					cell.setCellValue(conList.get(i).getString("v_comp_fc_nm"));
					
					cnt++;
				}else{
					if(i == conListCnt-1){
						//wt_total = wt_total+conList.get(i).getDouble("v_raw_per")*1000;
						wtTotal = new BigDecimal(conList.get(i).getString("v_raw_per")).add(wtTotal);
						int nRow = conList.get(i).getInt("n_raw_cnt");
						
						cell = row.createCell(1); 
						CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, Integer.toString(cnt));
						
						cell = row.createCell(2); 
						CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_raw_cd"));
						
						cell = row.createCell(7); 
						CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_raw_per"));
						
						cell = row.createCell(10); 
						CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_comp_fc_nm_ch"));
						
						cell = row.createCell(11); 
						CmFunction.styleMargedRow(sheet, cell, row, tableStyle, startRow-nRow+1, startRow, conList.get(i).getString("v_comp_fc_nm"));
						
						cnt++;
					}
				}
				
				if("FRAGRANCE".equals(conList.get(i).getString("v_con_nm_en"))){
					fragNo = cnt-1;
				}
				
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,conList.get(i).getString("v_con_nm_cn"));
				CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 7,conList.get(i).getString("v_con_nm_en"));
				
				cell = row.createCell(8);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_con_in_per"));
				
				cell = row.createCell(9);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_actual_wt"));
				
				cell = row.createCell(12);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_odm_casno"));
				
				cell = row.createCell(13);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_comp_mixre"));
								
				cell = row.createCell(14);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_remark"));
				
				cell = row.createCell(15);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_zglobal"));
				
				cell = row.createCell(16);
				cell.setCellStyle(tableStyle);
				cell.setCellValue(conList.get(i).getString("v_zgllim"));
				
				startRow++;
				beforeRawCd = conList.get(i).getString("v_raw_cd");
				beforeCnt = conList.get(i).getInt("n_raw_cnt");
			}
		}
		
		row = sheet.createRow(startRow);
		
		cell = row.createCell(1);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(2);
		cell.setCellStyle(tableStyle);
		
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 3, 5,"");
		CmFunction.styleMarged(sheet, cell, row, tableStyle, 5, 7,"");
		
		cell = row.createCell(7); 
		cell.setCellStyle(tableStyle);
		cell.setCellValue(exFormat.format(wtTotal));
		
		cell = row.createCell(8);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(9);
		cell.setCellStyle(tableStyle);
		cell.setCellValue(exFormat.format(actualWtTotal));
		
		cell = row.createCell(10); 
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(11); 
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(12);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(13);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(14);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(15);
		cell.setCellStyle(tableStyle);
		
		cell = row.createCell(16);
		cell.setCellStyle(tableStyle);

		/*
		cell = row.createCell(17);
		cell.setCellStyle(tableStyle);
		*/

		if(!fragList.isEmpty()){
			startRow++;
			
			row = sheet.createRow(startRow);
			StringBuffer sb = new StringBuffer();
			sb.append("* The Supplier of the FRAGRANCE (").append(fragNo).append(") is (");
			for(int i=0; i<fragList.size(); i++){
				sb.append(fragList.get(i).getString("v_fragrance")+','+fragList.get(i).getString("v_fragrance_smell"));
				sb.append('['+fragList.get(i).getString("v_manufacture"));
				
				if(i+1 == fragList.size()){
					sb.append(" ]).");
				}else{
					sb.append(", ");
				}
			}
			
			CmFunction.styleMarged(sheet, cell, row, fontsizeDefault, 1, 10, sb.toString());
		}
		
		startRow++;
		startRow++;
		
		if("Y".equals(allergenYn)){
			List<CmMap> allergenList = (List<CmMap>)reqVo.get("allergenList");
			if(allergenList.size() > 0){
				row = sheet.createRow(startRow);
				cell = row.createCell(1);
				cell.setCellValue("(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) :");
				sheet.addMergedRegion(new CellRangeAddress(startRow, startRow , 1, 11));
				
				startRow++;
				row = sheet.createRow(startRow);
				CmFunction.styleMarged2(sheet, cell, row, boldFontsizeDefault,1, 10, " * Allergens in fragrance : ");
				startRow++;
				
				row = sheet.createRow(startRow);
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 1, 4,"INCI name");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 4, 6,"W/W%");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 6, 8,"Function");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 8, 9,"CAS NO.");
				CmFunction.styleMarged(sheet, cell, row, theadStyle, 9, 10,"Chinese Name");
				
				
				startRow++;
				
				for(int i=0; i<allergenList.size(); i++){
					
					CmMap vo = allergenList.get(i);
					if("Y".equals(productInfo.getString("v_part_leaveon_yn"))){
						
						if(vo.getDouble("v_con_in_per") > 0.001){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 9, 10,allergenList.get(i).getString("v_con_nm_cn"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 9, 10,allergenList.get(i).getString("v_con_nm_cn"));
							
						}
						
					}else{
						
						if(vo.getDouble("v_con_in_per") > 0.01){
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tbLeaveYn, 9, 10,allergenList.get(i).getString("v_con_nm_cn"));
							
						}else{
							
							row = sheet.createRow(startRow);
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 1, 4,allergenList.get(i).getString("v_con_nm_en"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 4, 6,allergenList.get(i).getString("v_con_in_per"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 6, 8,allergenList.get(i).getString("v_allergen_func"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 8, 9,allergenList.get(i).getString("v_comp_cas_no"));
							CmFunction.styleMarged(sheet, cell, row, tableStyle, 9, 10,allergenList.get(i).getString("v_con_nm_cn"));
							
						}
						
					}
					
					startRow++;
				}
				
				startRow++;
				startRow++;
			}
		}
		
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = null;
		
		try {
			anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 0, 0, (short) 3, 5);
			//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_logo"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
			
			//서명 이미지 추가
			startRow++;
			startRow++;
			row = sheet.createRow(startRow);
			cell = row.createCell(13);
			cell.setCellValue(reqVo.getString("i_sSignDate"));
			startRow++;
			
			String  reprUserNm = productInfo.getString("v_repr_usernm_en");
			if(CmFunction.isEmpty(reprUserNm)) {
				reprUserNm = companyInfo.getString("v_repr_usernm_en");
			}
			
			row = sheet.createRow(startRow+1);
			cell = row.createCell(13);
			cell.setCellValue("Signature : ");
			
			anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 15, startRow, (short) 17, startRow+2);
			//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_sign_url"), wb));
			patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
			
		} catch (IOException e) {

			logger.error(e.getMessage());
		}
		return wb;
	}
	
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

	//spec excel문서 출력
	@SuppressWarnings("unchecked")
	public HSSFWorkbook excelTdd2017Spec(CmMap reqVo, HSSFWorkbook wb)throws Exception {

		// POI 객체 생성
		HSSFSheet sheet = wb.createSheet("SPEC");

		List<CmMap> lstMap = (List<CmMap>) reqVo.get("sSpecList");
		CmMap companyInfo = (CmMap) reqVo.get("companyVo");
		CmMap productInfo = (CmMap) reqVo.get("rvo");
		int mapLen = lstMap == null ? 0 : lstMap.size();
		CmMap rvo = (CmMap) reqVo.get("rvo");
		//CmMap apprInfo = (CmMap)reqVo.get("apprInfo");
		Map<String, CellStyle> cellStyle = CmFunction.createStyles(wb);

		// 기본 셀 스타일 설정
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		HSSFCellStyle tableStyle = (HSSFCellStyle) cellStyle.get("tableStyle");
		HSSFCellStyle tableTitleStyle = (HSSFCellStyle) cellStyle.get("default_bold_c");
		HSSFCellStyle logoStyle = (HSSFCellStyle) cellStyle.get("logoStyle");
		HSSFCellStyle titleStyle = (HSSFCellStyle) cellStyle.get("titleStyle");

		HSSFFont font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		HSSFCellStyle boldStyle = wb.createCellStyle();
		boldStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		boldStyle.setFont(font);

		if(mapLen>0){
			// 셀 입력 시작
			HSSFRow row;
			HSSFCell cell = null;
			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 5500);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);
			sheet.setColumnWidth(5, 5000);
			
			
			HSSFCellStyle defaultUl = wb.createCellStyle();
			font = wb.createFont();
			font.setFontHeightInPoints((short) 10);
			
			defaultUl.setFont(font);
			defaultUl.setAlignment(CellStyle.ALIGN_CENTER);
			defaultUl.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			defaultUl.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			defaultUl.setWrapText(true);
			defaultUl.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			
//			row = sheet.createRow(0);
//			row.setHeightInPoints((short) 25);
//			cell = row.createCell(3);
//			cell.setCellStyle(address);
			//cell.setCellValue(apprInfo.getString("v_info_top"));
//			sheet.addMergedRegion(new CellRangeAddress(0, 2, 3, 4));

			
			int startRow = 0;
			String type = "";
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 5, 7, productInfo.getString("v_company_addr1"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 5, 7, productInfo.getString("v_company_addr2"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 5, 7, productInfo.getString("v_company_addr3"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 5, 7, productInfo.getString("v_vendor_nm"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 5, 7,"Tel : "+companyInfo.getString("v_phone_no"));
			startRow++;
			
			row = sheet.createRow(startRow);
			CmFunction.styleMarged(sheet, cell, row, defaultUl, 5, 7,"Fax : "+companyInfo.getString("v_fax"));
			
//			row = sheet.createRow(3);
//			row.setHeightInPoints(18);
//			cell = row.createCell(0);

//			CmFunction.styleMarged(sheet, cell, row, logoStyle, 0, 6,"R&D CENTER");

			startRow++;
			startRow++;
			
			row = sheet.createRow(startRow);
			row.setHeightInPoints(32);
			CmFunction.styleMarged(sheet, cell, row, titleStyle, 0, 7,"SPECIFICATION");
			startRow++;
			startRow++;

			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			CmFunction.styleMarged(sheet, cell, row, boldStyle, 0, 6,"Product Name : " + rvo.getString("v_product_nm_en"));
			startRow++;
			
			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			CmFunction.styleMarged(sheet, cell, row, boldStyle, 0, 6,"Product Code : " + rvo.getString("v_product_cd"));
			startRow++;

			// 리스트 시작

			int tableEnd = 0;

			row = sheet.createRow(startRow);
			cell = row.createCell(0);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("No.");
			cell = row.createCell(1);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("Test item");
			cell = row.createCell(2);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("Test procedure");
			cell = row.createCell(3);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("Equipment");
			cell = row.createCell(4);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("Requirement");
			cell = row.createCell(5);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("Unit");
			cell = row.createCell(6);
			cell.setCellStyle(tableTitleStyle);
			cell.setCellValue("Reference");
			
			int temp = startRow+1;
			int index = 0;
			for (int i = 0; i < mapLen; i++) {
				CmMap rowMap = (CmMap) lstMap.get(i);

				if(!rowMap.getString("v_spec_div_nm").isEmpty()){
					row = sheet.createRow(temp);
					cell = row.createCell(0);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(index+1);
					cell = row.createCell(1);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(rowMap.getString("v_spec_div_nm"));
					cell = row.createCell(2);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(rowMap.getString("v_method"));
					cell = row.createCell(3);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(rowMap.getString("v_equipment"));
					cell = row.createCell(4);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(rowMap.getString("v_specification"));
					cell = row.createCell(5);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(rowMap.getString("v_unit"));
					cell = row.createCell(6);
					cell.setCellStyle(tableStyle);
					cell.setCellValue(rowMap.getString("v_remark"));

					temp++;
					index++;
					
				}
			}			
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = null;
			
			try {
				anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 0, 0, (short) 3, 5);
				//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_logo"), wb));
				patriarch.createPicture(anchor,loadPicture(reqVo.getString("vLogoFile"), wb));
				
				//서명 이미지 추가
				temp++;
				temp++;
				row = sheet.createRow(temp);
				cell = row.createCell(4);
				cell.setCellValue(reqVo.getString("i_sSignDate"));
				temp++;
								
				row = sheet.createRow(temp+1);
				cell = row.createCell(4);
				cell.setCellValue("Signature : ");
				
				anchor = new HSSFClientAnchor(0, 0, 400, 0, (short) 5, temp, (short) 7, temp+3);
				//patriarch.createPicture(anchor,loadPicture(companyInfo.getString("v_sign_url"), wb));
				patriarch.createPicture(anchor,loadPicture(reqVo.getString("vSignFile"), wb));
				
			} catch (IOException e) {

				logger.error(e.getMessage());
			}

		}else{
			sheet = this.noXlsData(sheet);
		}

		return wb;
	}
}