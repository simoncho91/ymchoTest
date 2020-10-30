package com.shinsegae_inc.sitims.common.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import sun.misc.BASE64Encoder;

/* 추후 수정

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

 */

@SuppressWarnings({"rawtypes", "PMD.ExcessiveClassLength"})
public class CmFunction 
{
	private static final int	MAX_FRACTION_FIELD	= 64;
	private static final double	DEFAULT_CHANGE_UNIT	= 1000000.0d;

	// 1881년 ~ 2050년 테이블
	private static final String[]	LTBL
			= {"1212122322121", "1212121221220", "1121121222120", "2112132122122", "2112112121220",
			   "2121211212120", "2212321121212", "2122121121210", "2122121212120", "1232122121212",
			   "1212121221220", "1121123221222", "1121121212220", "1212112121220", "2121231212121",
			   "2221211212120", "1221212121210", "2123221212121", "2121212212120", "1211212232212",
			   "1211212122210", "2121121212220", "1212132112212", "2212112112210", "2212211212120",
			   "1221412121212", "1212122121210", "2112212122120", "1231212122212", "1211212122210",
			   "2121123122122", "2121121122120", "2212112112120", "2212231212112", "2122121212120",
			   "1212122121210", "2132122122121", "2112121222120", "1211212322122", "1211211221220",
			   "2121121121220", "2122132112122", "1221212121120", "2121221212110", "2122321221212",
			   "1121212212210", "2112121221220", "1231211221222", "1211211212220", "1221123121221",
			   "2221121121210", "2221212112120", "1221241212112", "1212212212120", "1121212212210",
			   "2114121212221", "2112112122210", "2211211412212", "2211211212120", "2212121121210",
			   "2212214112121", "2122122121120", "1212122122120", "1121412122122", "1121121222120",
			   "2112112122120", "2231211212122", "2121211212120", "2212121321212", "2122121121210",
			   "2122121212120", "1212142121212", "1211221221220", "1121121221220", "2114112121222",
			   "1212112121220", "2121211232122", "1221211212120", "1221212121210", "2121223212121",
			   "2121212212120", "1211212212210", "2121321212221", "2121121212220", "1212112112210",
			   "2223211211221", "2212211212120", "1221212321212", "1212122121210", "2112212122120",
			   "1211232122212", "1211212122210", "2121121122210", "2212312112212", "2212112112120",
			   "2212121232112", "2122121212110", "2212122121210", "2112124122121", "2112121221220",
			   "1211211221220", "2121321122122", "2121121121220", "2122112112322", "1221212112120",
			   "1221221212110", "2122123221212", "1121212212210", "2112121221220", "1211231212222",
			   "1211211212220", "1221121121220", "1223212112121", "2221212112120", "1221221232112",
			   "1212212122120", "1121212212210", "2112132212221", "2112112122210", "2211211212210",
			   "2221321121212", "2212121121210", "2212212112120", "1232212122112", "1212122122120",
			   "1121212322122", "1121121222120", "2112112122120", "2211231212122", "2121211212120",
			   "2122121121210", "2124212112121", "2122121212120", "1212121223212", "1211212221220",
			   "1121121221220", "2112132121222", "1212112121220", "2121211212120", "2122321121212",
			   "1221212121210", "2121221212120", "1232121221212", "1211212212210", "2121123212221",
			   "2121121212220", "1212112112220", "1221231211221", "2212211211220", "1212212121210",
			   "2123212212121", "2112122122120", "1211212322212", "1211212122210", "2121121122120",
			   "2212114112122", "2212112112120", "2212121211210", "2212232121211", "2122122121210",
			   "2112122122120", "1231212122212", "1211211221220", "2121121321222", "2121121121220",
			   "2122112112120", "2122141211212", "1221221212110", "2121221221210", "2114121221221"};

	private static final String[]	YUK
			= {"갑", "을", "병", "정", "무", "기", "경", "신", "임", "계"};

	private static final String[]	GAP
			= {"자", "축", "인", "묘", "진", "사", "오", "미", "신", "유", "술", "해"};

	private static final String[]	DDI
			= {"쥐", "소", "호랑이", "토끼", "용", "뱀", "말", "양", "원숭이", "닭", "개", "돼지"};

	private static final String[]	WEEK
			= {"일", "월", "화", "수", "목", "금", "토"};
	
	private static final String[]	MONTH
			= {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	
	private static final String[]	FULLMONTH
	= {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	public static String getStringValue(Object pSource) {
		if ((null == pSource)) {
			return "";
		}

		return getStringValue(pSource.toString());
	}

	public static String getStringValue(String pSource) {
		if ((null == pSource) || "".equals(pSource)) {
			return "";
		} else if ((null == pSource.trim()) || "".equals(pSource.trim())) {
			return "";
		}

		return pSource.trim();
	}

	// String => int 로 변환
	public static int getIntValue(String pSource) {
		int	iResult = 0;
		String	tmp	= "";
		if (pSource != null && !pSource.equals("") )
		{
			tmp	= getOnlyNumber(pSource);
			
			if ( !tmp.equals("") )
			{
				iResult		= Integer.parseInt(tmp);
			}
		}
		return iResult;
	}
	
	public static String getOnlyNumber(String pSource) {
		StringBuffer	sb	= new StringBuffer();
		if (pSource != null)
		{
			int len		= pSource.length();
			
			for (int i = 0; i < len; i++)
			{
				char	c	= pSource.charAt(i);
				
				if ((i == 0 && c == '-') || (c >= '0' && c <= '9')) {
					sb.append(c);
				}
			}
		}

		return sb.toString();
	}
	
	public static String getOnlyDouble(String pSource) {
		StringBuffer 	sb	= new StringBuffer();
		boolean isPoint	= true;
		
		if (pSource != null)
		{
			int len		= pSource.length();
			
			for (int i = 0; i < len; i++)
			{
				char	c	= pSource.charAt(i);
				
				if (c >= '0' && c <= '9' || (isPoint && c == '.')) {
					sb.append(c);
					
					if (c == '.') {
						isPoint	= false;
					}
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * strDate => String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getStrDateToString(String strDate, String format) {
		ParsePosition pp = new ParsePosition(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
		String result = "";
		Date date = null;
		int len;
		
		result = getOnlyNumber(strDate);
		len = result.length();

		if (len == 4) {
			result = getStringPad("RPAD", result + "0101", 14, '0');
		}
		else if (len == 6) {
			result = getStringPad("RPAD", result + "01", 14, '0');
		}
		else if (len > 6 && len < 14) {
			result = getStringPad("RPAD", result, 14, '0');
		}
		else if (len >= 14) {
			result = result.substring(0, 14);
		}
		else {
			return result;
		}
		
		date = sdf.parse(result, pp);
		result = getDateToString(date, format);

		return result; 
	}
	
	/**
	 * Date => String
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateToString(Date date, String format) {
		SimpleDateFormat sdf;
		String	fmt	= format;
		if (fmt == null || fmt.equals(""))
			fmt = "yyyyMMdd";

		sdf = new SimpleDateFormat(fmt, Locale.KOREA);

		return sdf.format(date);
	}
	
	// 등록일시 [YYYYMMDDHHMMSS]
	public static String getRegDate14() {
		return getRegDate8() + getRegTime();
	}

	// 현재 년월일 [YYYYMMDD]
	public static String getRegDate8() {
		GregorianCalendar	calendar	= new GregorianCalendar();

		String	sYear	= Integer.toString(calendar.get(Calendar.YEAR));
		String	sMonth	= Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String	sDate	= Integer.toString(calendar.get(Calendar.DATE));

		return sYear + ((sMonth.length() < 2) ? "0" + sMonth : sMonth) + ((sDate.length()  < 2 ? "0" + sDate : sDate));
	}
	
	// 현재 년월일 [YYYYMMDD]
	public static String getNowRegDate8( String chr ) {
		GregorianCalendar	calendar	= new GregorianCalendar();

		String	sYear	= Integer.toString(calendar.get(Calendar.YEAR));
		String	sMonth	= Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String	sDate	= Integer.toString(calendar.get(Calendar.DATE));

		return sYear+ chr + ((sMonth.length() < 2) ? "0" + sMonth : sMonth) + chr + ((sDate.length()  < 2 ? "0" + sDate : sDate));
	}

	public static String getRegDate4() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.KOREA);
		return sdf.format(new Date());
	}

	// 현재 년월 [YYYYMM]
	public static String getRegDate6() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.KOREA);
		return sdf.format(new Date());
	}

	// 현재 시분초 [HHMMSS]
	public static String getRegTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", Locale.KOREA);
		return sdf.format(new Date());
	}
	
	// 년월일 [YYYYMMDD -> DD MM YYYY] ex : 20090712	- > 12 Jul 2009
	public static String getUsDate(String pSource)
	{
		String sResult	= "";
		
		if (pSource != null && pSource.length() >= "YYYYMMDD".length() )
		{
			String	sYear		= pSource.substring(0, 4);
			String 	sMonth		= pSource.substring(4, 6);
			String 	sDate		= pSource.substring(6, 8);
			
			int		iMonth		= getIntValue(sMonth);
			
			if (iMonth > 0 && iMonth < 13 )
			{
				sResult	= sDate + " " + MONTH[iMonth - 1] + " " + sYear;
			}
		}
		return sResult;
	}
	
	// 년월일 [YYYYMMDD -> DD MM YYYY] ex : 20180709	- > 2018. July. 09.
	public static String getUsDate1(String pSource)
	{
		String sResult	= "";
		
		if (pSource != null && pSource.length() >= "YYYYMMDD".length() )
		{
			String	sYear		= pSource.substring(0, 4);
			String 	sMonth		= pSource.substring(4, 6);
			String 	sDate		= pSource.substring(6, 8);
			
			int		iMonth		= getIntValue(sMonth);
			
			if (iMonth > 0 && iMonth < 13 )
			{
				sResult	= sYear +". " + FULLMONTH[iMonth - 1] +". " + sDate+". ";
			}
		}
		return sResult;
	}
	
	// 년월일 [YYYYMMDD -> DD MM YYYY] ex : 20090712	- > Jul 12, 2009
	public static String getUsDate2(String pSource)
	{
		String sResult	= "";
		
		if (pSource != null && pSource.length() >= "YYYYMMDD".length() )
		{
			String	sYear		= pSource.substring(0, 4);
			String 	sMonth		= pSource.substring(4, 6);
			String 	sDate		= pSource.substring(6, 8);
			
			int		iMonth		= getIntValue(sMonth);
			
			if (iMonth > 0 && iMonth < 13 )
			{
				sResult	= FULLMONTH[iMonth - 1] + " " + sDate + ", " + sYear;
			}
		}
		return sResult;
	}
	
	// 년월일 [YYYYMMDD -> DD MM YYYY] ex : 20090712	- > Jul 12, 2009
	public static String getUsDate3(String pSource)
	{
		String sResult	= "";
		
		if (pSource != null && pSource.length() >= "YYYYMMDD".length() )
		{
			String	sYear		= pSource.substring(0, 4);
			String 	sMonth		= pSource.substring(4, 6);
			String 	sDate		= pSource.substring(6, 8);
			
			sResult	= sYear + "년  " + sMonth + "월 " + sDate + "일";
			
		}
		return sResult;
	}
	
	public static String getUsDateTime(String pSource)
	{
		String sResult	= "";
		
		if (pSource != null && pSource.length() >= "YYYYMMDDHHMI".length() )
		{
			String	sDate		= getUsDate(pSource);
			
			String 	sHour		= pSource.substring(8, 10);
			String 	sMinute		= pSource.substring(10, 12);
			
			if ( !sDate.equals("") )
			{
				sResult	= sDate + " " + sHour + ":" + sMinute; 
			}
		}
		
		return sResult;
	}
	
	public static String getUsDateSecond(String pSource)
	{
		String sResult	= "";
		
		if (pSource != null && pSource.length() >= "YYYYMMDDHHMISS".length() )
		{
			String	sDate		= getUsDate(pSource);
			
			String 	sHour		= pSource.substring(8, 10);
			String 	sMinute		= pSource.substring(10, 12);
			String 	sSecond		= pSource.substring(12, 14);
			
			if ( !sDate.equals("") )
			{
				sResult	= sDate + " " + sHour + ":" + sMinute + ":" + sSecond; 
			}
		}
		return sResult;
	}	

	// 년월일 [YYYYMMDD -> YYYY.MM.DD]
	public static String getPointDate(String pSource) {
		if (getStringValue(pSource).length() == "YYYYMM".length()) {
			return pSource.substring(0, 4)
				+ "." + pSource.substring(4, 6);
		}
		else if (getStringValue(pSource).length() < "YYYYMMDD".length()) { 
			return pSource;
		}

		return pSource.substring(0, 4)
					+ "." + pSource.substring(4, 6)
					+ "." + pSource.substring(6, 8);        
	}
	
	// 시분초 [HHMMSS -> HH:MM:SS]
	public static String getPointTime(String pSource) {
		if (getStringValue(pSource).length() < "HHMMSS".length()) { 
			return pSource;
		}
		
		return pSource.substring(0, 2)
		+ ":" + pSource.substring(2, 4)
		+ ":" + pSource.substring(4, 6);        
	}
	
	// 시분 [HHMM -> HH:MM]
	public static String getPointTime1(String pSource) {
		if (getStringValue(pSource).length() < "HHMM".length()) { 
			return pSource;
		}
		
		return pSource.substring(0, 2)
		+ ":" + pSource.substring(2, 4);      
	}
	
	// 시분 [HHMM -> HH:MM]
	public static String getPointTime2(String pSource) {
		if (getStringValue(pSource).length() < "HHMM".length()) { 
			return pSource;
		}
		
		return pSource.substring(8, 10)
		+ ":" + pSource.substring(10, 12);      
	}

	public static String getPointDate(String pSource, String sDelimeter) {
		if (getStringValue(pSource).length() < "YYYYMMDD".length()) { 
			return pSource;
		}

		String	sDelimeterTmp	= getStringValue(sDelimeter);

		return pSource.substring(0, 4)
					+ sDelimeterTmp + pSource.substring(4, 6)
					+ sDelimeterTmp + pSource.substring(6, 8);        
	}

	// 년월일시분초 [YYYYMMDDHHMMSS]
	public static String getPointDtm(String pSource) {
		if (getStringValue(pSource).length() < "YYYYMMDDHHMMSS".length()) { 
			return pSource;
		}

		return pSource.substring( 0,  4)
					+ "."        + pSource.substring( 4,  6)
					+ "."        + pSource.substring( 6,  8)
					+ " &nbsp; " + pSource.substring( 8, 10)
					+ ":"        + pSource.substring(10, 12)
					+ ":"        + pSource.substring(12, 14);        
	}
	
	// 년월일시분 [YYYYMMDDHHMM]
	public static String getPointDtm1(String pSource) {
		if (getStringValue(pSource).length() < "YYYYMMDDHHMM".length()) { 
			return pSource;
		}

		return pSource.substring( 0,  4)
					+ "."        	+ pSource.substring( 4,  6)
					+ "."        	+ pSource.substring( 6,  8)
					+ " &nbsp; " 	+ pSource.substring( 8, 10)
					+ ":"        	+ pSource.substring(10, 12);      
	}
	
	// 년월일
	public static String getDatePatten(String pSource, String sChar) {
		if (getStringValue(pSource).length() < "YYYYMMDD".length()) { 
			return pSource;
		}
		String	tCh	= "";
		if (sChar == null || sChar.equals(""))
			tCh	= "-";
		
		return pSource.substring(0, 4)
		+ tCh + pSource.substring(4, 6)
		+ tCh + pSource.substring(6, 8);        
	}

    public static String getToday() {

        java.util.Date		dateNow		=	Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST")).getTime();
        SimpleDateFormat	formatter	=	new	SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        String	toDay	=	formatter.format(dateNow);

        return toDay;
    }
    
    /**
	 * 해당월의 마지막 일을 가져온다.
	 * @param strDate
	 * @return
	 */
	public static String getStrDateToMonthFirstDate(String strDate) {
		
		if (strDate == null)
			return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		String str = getOnlyNumber(strDate);
		
		if (str.length() < 6) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6))-1, 1);
		cal.add(Calendar.DATE, 0);
		
		return sdf.format(cal.getTime());
	}
    
    /**
	 * 해당월의 마지막 일을 가져온다.
	 * @param strDate
	 * @return
	 */
	public static String getStrDateToMonthLastDate(String strDate) {
		
		if (strDate == null)
			return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
		String str = getOnlyNumber(strDate);
		
		if (str.length() < 6) {
			return "";
		}

		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6)), 1);
		cal.add(Calendar.DATE, -1);
		
		return sdf.format(cal.getTime());
	}
	
	// chart date
	public static String getChartDate(String pSource , String sChar) {
		String	str	= "";
		if (getStringValue(pSource).length() < "YYYYMMDD".length()) { 
			str	= getRegDate8();
		}
		
		return getDatePatten(str, sChar);
	}

	// 년월일 [YYYYMM -> YYYY?MM]
	public static String getPointYYYYMM(String pSource, String pDelimiter) {
		if (getStringValue(pSource).length() < "YYYYMM".length()) { 
			return pSource;
		}

		return pSource.substring(0, 4) + pDelimiter + pSource.substring(4, 6);
    }

	// 숫자 세 자리마다 ','를 표시
	public static String setNumComma(int pSource) {
		return setNumComma(Integer.toString(pSource));
	}

	public static String setNumComma(int pSource, int pFractionLength) {
		return setNumComma(Integer.toString(pSource), pFractionLength);
	}

	public static String setNumComma(long pSource) {
		return setNumComma(Long.toString(pSource));
	}

	public static String setNumComma(long pSource, int pFractionLength) {
		return setNumComma(Long.toString(pSource), pFractionLength);
	}

	public static String setNumComma(float pSource) {
		return setNumComma(Float.toString(pSource));
	}

	public static String setNumComma(float pSource, int pFractionLength) {
		return setNumComma(Float.toString(pSource), pFractionLength);
	}

	public static String setNumComma(double pSource) {
		return setNumComma(Double.toString(pSource));
	}

	public static String setNumComma(double pSource, int pFractionLength) {
		return setNumComma(Double.toString(pSource), pFractionLength);
	}

	public static String setNumComma(String pSource) {
		if ("".equals(getStringValue(pSource))) {
			return "";
		}

		return setNumComma(pSource, MAX_FRACTION_FIELD);
	}

	public static String setNumComma(String pSource, int pFractionLength) {
		String			result	= "";
		DecimalFormat	dFormat	= new DecimalFormat();

		if (MAX_FRACTION_FIELD != pFractionLength) {
			dFormat.setMinimumFractionDigits(pFractionLength);
			dFormat.setMaximumFractionDigits(pFractionLength);
		}

		result	= dFormat.format(Double.parseDouble(pSource));

		return result;
	}

	// 숫자 입력시 변환되는 단위를 결정
	public static long getFormatChangeUnit(int pSource) {
		return getFormatChangeUnit(pSource, DEFAULT_CHANGE_UNIT);
	}

	public static long getFormatChangeUnit(int pSource, double pChangeUnit) {
		return (long)(pSource * pChangeUnit);
	}

	public static long getFormatChangeUnit(long pSource) {
		return getFormatChangeUnit(pSource, DEFAULT_CHANGE_UNIT);
	}

	public static long getFormatChangeUnit(long pSource, double pChangeUnit) {
		return (long)(pSource * pChangeUnit);
	}

	public static double getFormatChangeUnit(float pSource) {
		return getFormatChangeUnit(pSource, DEFAULT_CHANGE_UNIT);
	}

	public static double getFormatChangeUnit(float pSource, double pChangeUnit) {
		return (pSource * pChangeUnit);
	}

	public static double getFormatChangeUnit(double pSource) {
		return getFormatChangeUnit(pSource, DEFAULT_CHANGE_UNIT);
	}

	public static double getFormatChangeUnit(double pSource, double pChangeUnit) {
		return (pSource * pChangeUnit);
	}

	// HTML 문자열 그대로 출력
	public static String getHTMLEncode(String pSource) {
		String	result	= "";

		if ((null == pSource) || "".equals(pSource)) {
			return "";
		}

		result	=replace( replace(replace(replace(replace(replace(replace(pSource, "<", "&lt;"), ">", "&gt;"), "\"", "&quot;"),"&" ,"＆"),"#","＃"),"'","′"),"%","％");

		return result;
	}
	
	/**
	 * 문자열의 byte 값을 가져온다.
	 * @param pSource
	 * @return
	 */
	public static int getStringByte(String pSource) {
		int		iResult		= 0;

		if (pSource != null && !pSource.equals(""))
		{
			try {
	    		
	    		char 	cTmp;
	    		byte[]	bTmp;
	    		
	    		for (int i = 0 ; i < pSource.length(); i++ )
	    		{
	    			cTmp	= pSource.charAt(i);
	    			bTmp		= String.valueOf(cTmp).getBytes("UTF-8");
	    			iResult		+= bTmp.length;
	    		}
	    		
	    	} catch (UnsupportedEncodingException e) {
	    		return 0;
	    	}
		}
		
		return iResult;
	}
	
	public static String getByteString(String pSource, String pLength) {
		return	getByteString(pSource, getIntValue(pLength));
	}

	// 문자열을 Byte 수 만큼 자르고 "..." 을 붙여 잘린 문자열임을 표시
    public static String getByteString(String pSource, int pLength) {
    	StringBuilder result = new StringBuilder();
    	
    	if ((null == pSource) || "".equals(pSource)) {
    		return "";
    	}

    	if (pLength <= 0) {
    		return pSource;
    	}

    	try {
    		
    		char 	cTmp;
    		byte[]	bTmp;
    		int		nowLength	= 0;
    		int		strLemgth	= 0;
    		
    		for (int i = 0 ; i < pSource.length(); i++ )
    		{
    			cTmp	= pSource.charAt(i);
    			
    			bTmp		= String.valueOf(cTmp).getBytes("UTF-8");
    			strLemgth	= bTmp.length;
    			
    			if (strLemgth == 3)
    				nowLength += 2;
    			else
    				nowLength += strLemgth;
    			
    			if (nowLength <= pLength)
    			{
    				result.append(cTmp);
    			}
    			else
    			{
    				break;
    			}
    		}
    		
    		if (pSource.length() > result.length())
    			result.append("...");
    		
    	} catch (UnsupportedEncodingException e) {
    		return pSource;
    	}

    	return result.toString();
    }
    
    public static String getByteString2(String pSource, String pLength) {
    	return getByteString2(pSource, Integer.parseInt(pLength));
    	
    }


    // 문자열을 Byte 수 만큼자른다
    public static String getByteString2(String pSource, int pLength) {
    //	String	result	= "";
    	StringBuilder result = new StringBuilder();
    	if ((null == pSource) || "".equals(pSource)) {
    		return "";
    	}
    	
    	if (pLength <= 0) {
    		return pSource;
    	}
    	
    	try {
    		
    		char 	cTmp;
    		byte[]	bTmp;
    		int		nowLength	= 0;
    		int		strLemgth	= 0;
    		
    		for (int i = 0 ; i < pSource.length(); i++ )
    		{
    			cTmp	= pSource.charAt(i);
    			
    			bTmp		= String.valueOf(cTmp).getBytes("UTF-8");
    			strLemgth	= bTmp.length;
    			
    			if (strLemgth == 3)
    				nowLength += 2;
    			else
    				nowLength += strLemgth;
    			
    			if (nowLength <= pLength)
    			{
    			//	result	+= cTmp;
    				result.append(cTmp);
    			}
    			else
    			{
    				break;
    			}
    		}
    		
    	} catch (UnsupportedEncodingException e) {
    		return pSource;
    	}
    	
    	return result.toString();
    }
    
	// 문자열에서 <br>만 남기고 모든 HTML 태그 삭제
	public static String removeHTML(String pHTML) {
		String	result	= "";

		if ((null == pHTML) || "".equals(pHTML)) {
			return "";
		}
		
		// head 부분 제거
		result	= pHTML.replaceAll("<head([^'\"]|\"[^\"]*\"|'[^']*')*?</head>", "");
		//style 부분을 먼저 삭제 한다.
		result	= result.replaceAll("<style([^'\"]|\"[^\"]*\"|'[^']*')*?</style>", "");
		
		result	= replace(result, "<br>", "@#$%^&*");
		result	= replace(result, "<BR>", "@#$%^&*");
		result	= replace(result, "<Br>", "@#$%^&*");
		result	= replace(result, "<bR>", "@#$%^&*");
		result	= replace(result, "</p>", "@#$%^&*");
		result	= replace(result, "</P>", "@#$%^&*");
		result = replace(result, "&nbsp;", " ");
		
		result	= result.replaceAll("<(/)?([a-zA-Z!]*)(\\s[a-zA-Z]*[^>]*)?(\\s)*(/)?>", "");
		
		result	= replace(result, "P {margin-top:2px;margin-bottom:2px;}", "");
		result	= replace(result, "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd", "");
		
		result	= replace(result, "@#$%^&*", "<br/>");
		
		result	= replace(result, "&lt;"	, "<");
		result	= replace(result, "&gt;"	, ">");
		result	= replace(result, "&amp;"	, "&");
		result	= replace(result, "<o:p>"	, "");
		result	= replace(result, "</o:p>"	, "");
		
		return result.trim();
	}
	
	// 문자열에서 <br>, <A>태크만 남기고 모든 HTML 태그 삭제
	// 추가적으로 <p>태그로 줄바꿈을 한 경우에 <p>는 삭제하고
	// </p>는 </br>로 변경하여 준다.
	public static String removeHTMLaTag(String pHTML) {
		String	result	= "";
		
		if ((null == pHTML) || "".equals(pHTML)) {
			return "";
		}
		
		// head 부분 제거
		result	= pHTML.replaceAll("<head([^'\"]|\"[^\"]*\"|'[^']*')*?</head>", "");
		//style 부분을 먼저 삭제 한다.
		result	= result.replaceAll("<style([^'\"]|\"[^\"]*\"|'[^']*')*?</style>", "");
		
		result	= replace(result, "<br>", "@##%^&*");
		result	= replace(result, "<BR>", "@##%^&*");
		result	= replace(result, "<Br>", "@##%^&*");
		result	= replace(result, "<bR>", "@##%^&*");
		result	= replace(result, "</p>", "@##%^&*");
		result	= replace(result, "</P>", "@##%^&*");
		
		result = replace(result, "&nbsp;", " ");
		
		result	= replace(result, "<A "	, "@#$%^&*aStart");
		result	= replace(result, "<a "	, "@#$%^&*aStart");
		result	= replace(result, "</a>"	, "@#$%^&*aEnd");
		result	= replace(result, "</A>"	, "@#$%^&*aEnd");
		
		Pattern pattern = Pattern.compile("<(?i)br(\\s+)([a-zA-Z]*)(\\s*[a-zA-Z]*[^>]*)?(\\s*)>");
		Matcher m = pattern.matcher(result);
		result = m.replaceAll("@##%^&*");
		
		result	= pHTML.replaceAll("<(/)?([a-zA-Z0-9!]*)(\\s[a-zA-Z0-9]*[^>]*)?(\\s)*(/)?>", "");
		
		result	= replace(result, "@#$%^&*aStart"	, "<a ");
		result	= replace(result, "@#$%^&*aEnd"		, "</a>");
		
		result	= replace(result, "@##%^&*", "<br/>");
		
		result	= replace(result, "P {margin-top:2px;margin-bottom:2px;}", "");
		result	= replace(result, "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd", "");
		
		return result.trim();
	}
	
	// 문자열에서 모든 HTML 태그 삭제
	public static String removeAllHTML(String pHTML) {
		String	result	= "";
		
		if ((null == pHTML) || "".equals(pHTML)) {
			return "";
		}
		
		result	= pHTML.replaceAll("<.+?>", "");
		
		return result;
	}

	// 문자열 교체 함수
	public static String replace(String pOrigin, String pPattern, String pReplace) { 
		int	sIndex	= 0; 
		int	eIndex	= 0; 

		if ((null == pOrigin) || "".equals(pOrigin)) {
			return "";
		}

		StringBuffer	result	= new StringBuffer(); 

		eIndex = pOrigin.indexOf(pPattern, sIndex);
		while (eIndex >= 0) {
			result.append(pOrigin.substring(sIndex, eIndex)); 
			result.append(pReplace); 

			sIndex	= eIndex + pPattern.length();
			eIndex = pOrigin.indexOf(pPattern, sIndex); 
		}

		result.append(pOrigin.substring(sIndex)); 

		return result.toString(); 
	}

	// 파일 경로 생성
    public static boolean makeFilePath(String pFullPathWithFileName) {
    	boolean	result	= true;

    	if ((null == pFullPathWithFileName) || "".equals(pFullPathWithFileName)) {
    		result	= false;
    		return result;
    	}

    	String[]	arrFullPath	= pFullPathWithFileName.split("/", -1);
    //	String		filePath	= "";
    	File		file		= null;
    	StringBuilder filePath = new StringBuilder();
    	if (null != arrFullPath) {
	    	for (int i = 0; i < arrFullPath.length - 1; i++) {
	    	//	filePath	= filePath + arrFullPath[i] + "/";
	    				
	    		filePath.append(arrFullPath[i]).append('/');
	    		file	= new File(filePath.toString());
	    		if (!file.exists()) {
	    			result	= file.mkdir();
	    		}
	    	}
    	}

    	return result;
    }

    // 파일 복사
    public static boolean fileCopy(String pSourceFilePath, String pTargetFilePath) throws IOException {
    	boolean	result	= true;

    	FileInputStream 	inputStream 	= null;
    	FileOutputStream 	outputStream 	= null;
    	File				targetfile		= null;

    	makeFilePath(pTargetFilePath);
    	
    	try {
    		targetfile		= new File(pTargetFilePath);
    		
    		if (targetfile.exists())
	    	{
	    		fileDelete(pTargetFilePath);
	    	}
    		
    		inputStream 	= new FileInputStream(pSourceFilePath); 
    		outputStream 	= new FileOutputStream(pTargetFilePath);
    		int i = inputStream.read();
    		while(i >= 0) {
    			outputStream.write(i);
    			i = inputStream.read();
    		}
    		//fcin.transferTo(0, size, fcout);
    	}catch (IOException e) {
    		result	= false;
    	}finally {
    	//	if(inputStream != null) inputStream.close();
    	//	if(outputStream != null) outputStream.close();
    		if(inputStream != null) try{inputStream.close();}catch(IOException e){ result	= false; }
    		if(outputStream != null) try{outputStream.close();}catch(IOException e){ result	= false; }
    	}

    	return result;
    }
    
    // 파일 삭제
    public static boolean fileDelete(String pSourceFilePath)
    {
    	boolean	result	= true;
    	
    	File file	= new File( pSourceFilePath );
    	
    	if (file.exists()) 
    	{
			file.delete();
    	}
    	return result;
    }
    
    public static boolean fileMove(String pSourceFilePath, String pTargetFilePath) throws IOException {
    	boolean	result	= true;
    	
    	result		= fileCopy(pSourceFilePath, pTargetFilePath);
    	
    	if (result)
    	{
    		result		= fileDelete(pSourceFilePath);
    	}
    	return result;
    }

	// 음력 -> 양력변환
	public static String convertLunar2Solar(String pDate) {
		return convertLunar2Solar(pDate, true);
	}

	// 음력 -> 양력변환
	public static String convertLunar2Solar(String pDate, boolean pIsYun) {
		if (CmFunction.getStringValue(pDate).length() < 8) {
			return "";
		}

		int	iYear	= Integer.parseInt(pDate.substring(0, 4));
		int	iMonth	= Integer.parseInt(pDate.substring(4, 6));
		int	iDate	= Integer.parseInt(pDate.substring(6, 8));

		return convertLunar2Solar(iYear, iMonth, iDate, pIsYun);
	}

	// 음력 -> 양력변환
	public static String convertLunar2Solar(int pYear, int pMonth, int pDate) {
		return convertLunar2Solar(pYear, pMonth, pDate, true);
	}

	// 음력 -> 양력변환
	@SuppressWarnings("PMD.CyclomaticComplexity")
	public static String convertLunar2Solar(int pYear, int pMonth, int pDate, boolean pIsYun) {
		String	result	= "";
		int		sYear	= 0;
		int		sMonth	= 0;
		int		sDate	= 0;

		int[]	lDAY = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		if ((pYear < 1881) && (pYear > 2050)) {
			return result;
		}

		if ((pMonth + 1) > 13) {
			return result;
		}

		int	iYearIndex		= pYear - 1881;
		int	iMonthIndex1	= 0;
		int	iMonthIndex2	= 0;
		int	iDayCount		= 0;
		int	iDateLeap		= 0;

		boolean	iIsYun		= false;

		if ("0".equals(LTBL[iYearIndex].substring(LTBL[iYearIndex].length() - 1))) {
			iIsYun	= false;
		} else {
			if (Integer.parseInt(LTBL[iYearIndex].substring(pMonth, (pMonth + 1))) > 2) {
				iIsYun	= true;
			} else {
				iIsYun	= false;
			}
		}

		iIsYun	&= pIsYun;

		iYearIndex	= -1;
		iDayCount	= 0;

		if ((pYear >= 1881) && (pYear < 2050)) {
			iYearIndex	= pYear - 1881;

			for (int i = 0; i < iYearIndex; i++) {
				for (int j = 0; j < LTBL[i].length(); j++) {
					iDayCount	+= Integer.parseInt(LTBL[i].substring(j, j + 1));
				}

				if ("0".equals(LTBL[i].substring(LTBL[i].length() - 1))) {
					iDayCount	+= 336;
				} else {
					iDayCount	+= 362;
				}
			}
		} else {
			result	= "0";
		}
		
		iYearIndex++;
		iMonthIndex1	= pMonth - 1;
		iMonthIndex2	= -1;
		
		do {
			iMonthIndex2++;
			
			if (Integer.parseInt(LTBL[iYearIndex].substring(iMonthIndex2, iMonthIndex2 + 1)) > 2) {
				iDayCount	= iDayCount + 26 + Integer.parseInt(LTBL[iYearIndex].substring(iMonthIndex2, iMonthIndex2 + 1));
				iMonthIndex1++;
			} else {
				if (iMonthIndex2 == iMonthIndex1) {
					if (iIsYun) {
						iDayCount	= iDayCount + 28 + Integer.parseInt(LTBL[iYearIndex].substring(iMonthIndex2, iMonthIndex2 + 1));
					}

					break;
				} else {
					iDayCount	= iDayCount + 28 + Integer.parseInt(LTBL[iYearIndex].substring(iMonthIndex2, iMonthIndex2 + 1));
				}
			}
		} while (true);

		iDayCount	= iDayCount + pDate + 29;
		iYearIndex	= 1880;

		do {
			iYearIndex++;
			if (((iYearIndex % 400) == 0) || ((iYearIndex % 100) != 0) && ((iYearIndex % 4) == 0)) {
				iDateLeap	= 1;
			} else {
				iDateLeap	= 0;
			}

			iMonthIndex2	= 365 + iDateLeap;

			if (iDayCount < iMonthIndex2) {
				break;
			}

			iDayCount	-= iMonthIndex2;
		} while (true);

		sYear	= iYearIndex;
		lDAY[1]	= iMonthIndex2 - 337;

		iYearIndex	= 0;
		do {
			iYearIndex++;
			if (iDayCount <= lDAY[iYearIndex - 1]) {
				break;
			}

			iDayCount	-= lDAY[iYearIndex - 1];
		} while (true);

		sMonth	= iYearIndex;
		sDate	= iDayCount;

		iDayCount	= (sYear - 1) * 365 + (sYear - 1) / 4 - (sYear - 1) / 100 + (sYear - 1) / 400;

		if (((sYear % 400) == 0) || ((sYear % 100) != 0) && ((sYear % 4) == 0)) {
			iDateLeap	= 1;
		} else {
			iDateLeap	= 0;
		}

		lDAY[1]	= 28 + iDateLeap;

		for (int i = 0; i < sMonth - 1; i++) {
			iDayCount	+= lDAY[i];
		}

		iDayCount	+= sDate;

		result	= Integer.toString(sYear)
					+ (((sMonth < 10) ? "0" : "") + Integer.toString(sMonth))
					+ (((sDate  < 10) ? "0" : "") + Integer.toString(sDate))
					+ "/" + WEEK[(iDayCount % 7)] + "요일";
		return result;
	}

	// 양력 -> 음력변환
	public static String convertSolar2Lunar(String pDate) {
		if (CmFunction.getStringValue(pDate).length() < 8) {
			return "";
		}

		int	iYear	= Integer.parseInt(pDate.substring(0, 4));
		int	iMonth	= Integer.parseInt(pDate.substring(4, 6));
		int	iDate	= Integer.parseInt(pDate.substring(6, 8));

		return convertSolar2Lunar(iYear, iMonth, iDate);
	}

	// 양력 -> 음력변환
	@SuppressWarnings("PMD.CyclomaticComplexity")
	public static String convertSolar2Lunar(int pYear, int pMonth, int pDate) {
		String	result	= "";
		int		sYear	= 0;
		int		sMonth	= 0;
		int		sDate	= 0;

		int[]	lDAY = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

		int		i;

		if ((pYear < 1881) && (pYear > 2050)) {
			return result;
		}

		if ((pMonth + 1) > 13) {
			return result;
		}

		int	iYearIndex		= pYear - 1881;
		int	iMonthIndex1	= 0;
		int	iMonthIndex2	= 0;

		boolean	iIsYun		= false;

		if ("0".equals(LTBL[iYearIndex].substring(LTBL[iYearIndex].length() - 1))) {
			iIsYun	= false;
		} else {
			if (Integer.parseInt(LTBL[iYearIndex].substring(pMonth, (pMonth + 1))) > 2) {
				iIsYun	= true;
			} else {
				iIsYun	= false;
			}
		}

		int[]	iDayCount	= new int[169];
		
		for (i = 0; i < 169; i++) {
			iDayCount[i]	= 0;

			for (int j = 0; j < LTBL[i].length(); j++) {
				switch (Integer.parseInt(LTBL[i].substring(j, j + 1))) {
					case	1 :
					case	3 :
						iDayCount[i]	+= 29;
						break;
					case	2 :
					case	4 :
						iDayCount[i]	+= 30;
						break;
					default :
				}
			}
		}

		int	iTotalDate1	= 1880 * 365 + 1880 / 4 - 1880 / 100 + 1880 / 400 + 30;
		int	iTotalDate2	= (pYear - 1) * 365 + (pYear - 1) / 4 - (pYear - 1) / 100 + (pYear - 1) / 400;

		if ((pYear % 400 == 0) || (pYear % 100 != 0) && (pYear % 4 == 0)) {
			lDAY[1]	= 29;
		} else {
			lDAY[1]	= 28;
		}

		for (i = 0; i < pMonth - 1; i++) {
			iTotalDate2	+= lDAY[i];
		}

		iTotalDate2	+= pDate;

		iTotalDate1	= iTotalDate2 - iTotalDate1 + 1;
		iTotalDate2	= iDayCount[0];

		for (i = 0; i < iDayCount.length - 1; i++) {
			if (iTotalDate1 <= iTotalDate2) {
				break;
			}

			iTotalDate2	+= iDayCount[i + 1];
		}

		sYear	= i + 1881;
		iTotalDate2	-= iDayCount[i];
		iTotalDate1	-= iTotalDate2;

		if ("0".equals(LTBL[i].substring(LTBL[i].length() - 1))) {
			iMonthIndex1	= 11;
		} else {
			iMonthIndex1	= 12;
		}

		iMonthIndex2	= 0;

		for (int j = 0; j <= iMonthIndex1; j++) {
			if (Integer.parseInt(LTBL[i].substring(j, j + 1)) <= 2) {
				iMonthIndex2++;
				iYearIndex	= Integer.parseInt(LTBL[i].substring(j, j + 1)) + 28;
				iIsYun		= false;
			} else {
				iYearIndex	= Integer.parseInt(LTBL[i].substring(j, j + 1)) + 26;
				iIsYun		= true;
			}

			if (iTotalDate1 <= iYearIndex) {
				break;
			}

			iTotalDate1	-= iYearIndex;
		}

		sMonth	= iMonthIndex2;
		sDate	= iTotalDate1;

		result	= Integer.toString(sYear)
					+ (((sMonth < 10) ? "0" : "") + Integer.toString(sMonth))
					+ (((sDate  < 10) ? "0" : "") + Integer.toString(sDate))
					+ "/" + (iIsYun ? "윤달" : "평달")
					+ "/" + YUK[((sYear + 6) % 10)] + GAP[(sYear + 8) % 12] + "년"
					+ "/" + DDI[(sYear + 8) % 12];

		return result;
	}
	
	// 다국어
	public static ResourceBundle getBundle(String str, HttpServletRequest request) {
		
		ResourceBundle bundle 		= null;
		String	tmpStr			 	= request.getHeader("Accept-Language");
		String	sAcceptLanguage 	= "en";
		
		if (tmpStr != null && tmpStr.length() > 1)
		{
			sAcceptLanguage 	= tmpStr.substring(0, 2).toLowerCase();
		}
		java.util.Locale locale 	= new java.util.Locale(sAcceptLanguage, "");

		bundle = ResourceBundle.getBundle(str, locale);

		return bundle;
	}
	
	// 랜덤 영문 숫자 조합 ( 영문보다 숫자가 나올 확률이 2배 높다. )
	public static String getRandomString(int strLength) {
		
	//	String 		returnStr	= "";
		StringBuilder returnStr = new StringBuilder();
		String[] 	strArr		= new String[]{"0123456789", "0123456789", "abcdefghijklmnopqrstuvwxyz"};
		int			len			= strArr.length;
		Random		random		= new Random();
		
		for (int i = 0; i < strLength; i++)
		{
			int iRan1 	= random.nextInt(len);
			int iRan2	= random.nextInt(strArr[iRan1].length());
			
		//	returnStr	+= strArr[iRan1].substring(iRan2, iRan2+1);
			returnStr.append(strArr[iRan1].substring(iRan2, iRan2+1));
		}
		
		return returnStr.toString();
	}
	
	/**
	 * 영문만
	 * @param strLength
	 * @return
	 */
	public static String getRandomOnlyString(int strLength) {
		
		StringBuffer sb	= new StringBuffer();
		
		String	str			= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int 	len 		= str.length();
		Random	random		= new Random();
		
		for (int i = 0; i < strLength; i++) {
			int iRan1 	= random.nextInt(len);
			sb.append(str.substring(iRan1, iRan1+1));
		}
		return sb.toString();
	}	
	
	// cookie
	public static String getCookie(String cookieName) {
		
		String 		resultStr	= "";
		Cookie[] 	cookies 	= getCurrentRequest().getCookies();
		
        for( int i = 0; i < cookies.length; i++ )
        {
            Cookie thisCookie =  cookies[i];

            if ( thisCookie.getName().equals(cookieName) )
            {
            	resultStr   = thisCookie.getValue();
                break;
            }
        }		
		return resultStr;
	}
	
	/**
	 * Cookie 저장
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param minute
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, int day) {
		Cookie	cookie		= new Cookie(cookieName, cookieValue);
		if (day > 0) {
			cookie.setMaxAge(day * 60 * 60 * 24);
		}
		else {
			cookie.setMaxAge(day);
		}
		response.addCookie(cookie);
	}
	
	// LPAD, RPAD
	public static String getStringPad(String type, String str, int len, char spaceChr) {
	//	String	sResult		= "";
		int		strLen		= 0;
		StringBuilder sResult = new StringBuilder();
		
	//	if (str == null)	str		= "";
		
		strLen	= str.length();
		
		if (strLen < len)
		{
			for (int i = 0; i < len - strLen ; i++)
			{
			//	sResult	+= spaceChr;
				sResult.append(spaceChr);
			}
			
			if ("LPAD".equals(type.toUpperCase()) )
			{
			//	sResult	+= str;
				sResult.append(str);
			}
			else if ("RPAD".equals(type.toUpperCase()) )
			{
				String	tmp	=  sResult.toString();
				sResult.delete(0,sResult.length());
				sResult.append(str).append(tmp);
			}
		}
		else
		{
			return str;
		}
		
		return sResult.toString();
	}

	// 파일경로 가져오기
	public static String getAttachFileUrl(String sFullUrl ) {
		String	sReturn = "";
		
		if(sFullUrl != null) {
			sReturn = sFullUrl.replaceAll(CmPathInfo.getROOT_PATH(), "/");
		}
		
		return sReturn;
	}
	
	// 파일경로 가져오기
	public static String getAttachFileUrl(String sAttachId, String sAttachExt, String sAttachPath ) {
		String	sReturn = "";
		
		if (sAttachId != null && sAttachExt != null && sAttachPath != null)
		{
			sReturn		= getAttachFileUrl(sAttachPath + sAttachId + sAttachExt);
		}
		
		return sReturn;
	}
	
	// 텍스트를 * 로 변환  (ex : ("abcdebe" , 2)  = ab*****)
	public static String getStringHidden(String pSourcd, int length) {
	//	String	sReturn	= "";
		StringBuilder sReturn = new StringBuilder();
		
		if (pSourcd != null && !pSourcd.equals(""))
		{
			int	strLen		= pSourcd.length();
			
			for (int i = 0; i < strLen; i++)
			{
				if (i < length)
				{
					sReturn.append(pSourcd.charAt(i));
				}
				else
				{
					sReturn.append('*');
				}
			}
		}
		
		return sReturn.toString();
	}
	
	// e-mail    (ex : ("abcdebe@test.com" , 2)  = ab*****@test.com)
	public static String getEmailHidden(String pEmail, String sLength) {
		
		int	length		= 0;
		
		try {
			length	= Integer.parseInt(sLength);
		} catch (NumberFormatException e) {
			length	= 2;
		}
	
		return getEmailHidden(pEmail, length);
	}
	
	// e-mail    (ex : ("abcdebe@test.com" , 2)  = ab*****@test.com)
	public static String getEmailHidden(String pEmail, int length) {
		String	sReturn	= "";
		
		if (pEmail != null && !pEmail.equals(""))
		{
			if (pEmail.indexOf('@') > -1)
			{
				String	emailFirst	= pEmail.substring(0, pEmail.indexOf('@'));
				String	emailLast	= pEmail.substring(pEmail.indexOf('@'), pEmail.length());
				
				sReturn	= getStringHidden(emailFirst, length) + emailLast;
			}
			else
			{
				sReturn		= getStringHidden(pEmail, length);
			}
		}
		return sReturn;
	}
	
	public static String escape(String string){
	       StringBuffer sb = new StringBuffer();
	       String ncStr = "*+-./0123456789@ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
	       char c;
	       
	       for(int i=0;i<string.length();i++){
	              c = string.charAt(i);
	              if(c>0x7f){
	                     sb.append("%u"); 
	                     sb.append(Integer.toHexString((int)c).toUpperCase());
	              }
	              else if(ncStr.indexOf((int)c)==-1){
	                     sb.append('%');
	                     if(c<=0xf)
	                            sb.append('0');
	                     sb.append(Integer.toHexString((int)c).toUpperCase());
	              }
	              else 
	                     sb.append(c);
	       }
	       
	       return sb.toString();
	}
	
	// \n을  --> <br> 로 변경
	public static String getStrChangeBr(String tmpStr) {
		String	sReturn	= "";
		
		if (tmpStr != null && !tmpStr.equals(""))
			sReturn			= tmpStr.replaceAll("\n", "<br/>");
		
		return sReturn;
	}
	
	// 문자열에서 모든 HTML 태그 삭제 하고  \n을  --> <br> 로 변경
	public static String removeHTMLChangeBr(String pHTML) {
		String	result	= "";
		
		if ((null == pHTML) || "".equals(pHTML)) {
			return "";
		}
		 
		result	= pHTML.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*[^>]*)?(\\s)*(/)?>", "").replaceAll("\n", "<br/>");
		
		result = result.replaceAll("\r", "");
		
		return result;
	}
	
	// 문자열에서 모든 HTML 태그 삭제 하고  \n을  --> <br> 로 변경
	public static int getLineFeedCount(String pHTML) {
		int cnt = org.apache.commons.lang.StringUtils.countMatches(pHTML, "\n")+1;
		return cnt;
	}
	
	// 문자열에서 모든 HTML 태그 삭제 하고  \n을  --> <br> 로 변경
	public static String removeHTMLChangeBrWeb(String pHTML) {
		String	result	= "";
		
		if ((null == pHTML) || "".equals(pHTML)) {
			return "";
		}
		
		result	= pHTML;
		result	= result.replaceAll("<", "&lt;");
		result	= result.replaceAll(">", "&gt;");
		result	= result.replaceAll("\n", "<br/>");
		result = result.replaceAll("\r", "");
		
		return result;
	}
	
	// 크로스 에디터 오류로 &lt;우측으로 공백을 1칸 붙임
	public static String ltAddBlank(String pHTML) {
		String	result	= "";
		
		if ((null == pHTML) || "".equals(pHTML)) {
			return "";
		}
		
		result	= pHTML;
		result	= result.replaceAll("&lt;", "&lt; ");
		result	= result.replaceAll("&lt;  ", "&lt; ");
		
		return result;
	}
	
	public static String fnFileNameCheck(String	filePath, String fileName)
	{
		String		sReturn			= "";
		String		tmpFileName		= "";
		String		tmpExt			= "";
		File		file			= null;
		boolean		bLoop			= true;
		int			cnt				= 0;
		
		if (filePath != null && fileName != null)
		{
			if (fileName.indexOf('.') > -1)
			{
				tmpFileName			= fileName.substring(0, fileName.lastIndexOf('.'));
				tmpExt				= fileName.substring(fileName.lastIndexOf('.'), fileName.length());
			}
			else
			{
				tmpFileName			= fileName;
				tmpExt				= "";
			}
			
			while (bLoop)
			{
				if (cnt == 0)
					file		= new File(filePath + tmpFileName + tmpExt);
				else
					file		= new File(filePath + tmpFileName + "[" + cnt + "]" + tmpExt);
				
				if (file.exists())
				{
					cnt++;
				}
				else
				{
					bLoop		= false;
					
					if (cnt == 0)
						sReturn		= tmpFileName + tmpExt;
					else
						sReturn		= tmpFileName + "[" + cnt + "]" + tmpExt;
				}
			}
		}
		
		return sReturn;
	}
	
	/**
	 * - 가 있는 전화번호 에서 필요한 부분 가져오기
	 * ex ) getPhoneNumber("02-1234-5678", 1) => return "1234"
	 * ex ) getPhoneNumber("02-1234-5678", 2) => return "5678"
	 * @param pPhone
	 * @param index
	 * @return
	 */
	public static String cutPhone(String pPhone, String sIndex) {
		String 	sReturn		= "";
		int		index		= CmFunction.getIntValue(sIndex);
		
		if (pPhone != null && !pPhone.equals(""))
		{
			String[] arrPhone		= pPhone.split("-");
			
			if (index < arrPhone.length)
				sReturn	= arrPhone[index];
		}
		return sReturn;
	}
	
	/**
	 * Xml Node 값을 가져온다.
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static String getXmlNodeValue(Element element, String tagName) {
		String		sReturn 	= null;
		NodeList	nList		= null;
		Element		elTmp		= null;
		
		nList 		= element.getElementsByTagName(tagName);
		
		if (nList != null)
		{
			elTmp 		= (Element) nList.item(0);
			
			if (elTmp != null && elTmp.getFirstChild() != null)
				sReturn = elTmp.getFirstChild().getNodeValue();
		}
		
		return sReturn;
	}
	
	/**
	 * 값 등록유무 체크
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty (String str) {
		
		if (str != null && !str.equals(""))
			return true;
		else
			return false;
	}
	
	/**
	 * 값 등록 유무 체크
	 * @param str
	 * @return
	 */
	public static boolean isEmpty (String str) {
		
		if (str != null && !str.equals(""))
			return false;
		else
			return true;
	}
	
	public static String getFileSize(String fileSize) {
		String	sReturn		= fileSize;
		
		try {
			sReturn = getFileSize(Long.parseLong(fileSize));
		} catch (NumberFormatException e) {
			sReturn		= fileSize;
		}
		
		return sReturn;
	}

	public static String getFileSize(long fileSize) {
		String		sReturn		= "";
		
		if (fileSize >= (1 * 1024 * 1024 * 1024))
			sReturn	= setNumComma(Math.ceil((double)(fileSize / 1024 / 1024 / 1024))) + "GB";
		else if (fileSize >= (1 * 1024 * 1024)) 
			sReturn	= setNumComma(Math.ceil((double)(fileSize / 1024 / 1024))) + "MB";
		else if (fileSize >= (1 * 1024)) 
			sReturn	= setNumComma(Math.ceil((double)(fileSize / 1024))) + "KB";
		else if (fileSize > 0 )
			sReturn	= "1KB";
		
		return sReturn;
	}
	
	/**
	 * 이미지파일 웹 위치
	 * @param str
	 * @return
	 */
	public static String getImgFilePath(String str) {
		String		sReturn		= "";
		String		tmpStr		= "/UPLOAD/";
		
		if ( str != null && str.indexOf(tmpStr) > -1 ) 
		{
			sReturn		= "/UPLOAD/"+str.substring( str.indexOf(tmpStr) + tmpStr.length() );
		}
		else if ( str != null && str.indexOf("\\UPLOAD\\") > -1 )
		{
			sReturn		= str.substring( str.indexOf("\\UPLOAD\\") + "\\UPLOAD\\".length() );
		}
		return sReturn;
	}
	
	/**
	 * 날짜간 차이 구하기
	 * @param sDt
	 * @param eDt
	 * @return
	 */
	public static String getDateGap( String sDt, String eDt ) {
		
		String		sReturn			= "";
		Calendar	startDt			= Calendar.getInstance();		
		Calendar	endDt			= Calendar.getInstance();
		long		gap;
		
		if (isNotEmpty(sDt) && isNotEmpty(eDt) )
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			
				try {
					startDt.setTime(dateFormat.parse(sDt));
					endDt.setTime(dateFormat.parse(eDt));
				
					gap		= (endDt.getTimeInMillis() - startDt.getTimeInMillis()) / (24 * 60 * 60 * 1000);

					if (gap >= 0)
						sReturn		= String.valueOf((gap + 1));
					else
						sReturn		= String.valueOf(gap);
				} catch (ParseException e) {
					sReturn	= "";
				}

		}
		
		return sReturn;
	}
	
	/**
	 * 월간 차이 구하기 return int  
	 * 예)201204 ~ 201306  = 15달. (2012년 4월,5월 ~~ 2013년 06월) 
	 * @param sDt  
	 * @param eDt  
	 * @return
	 */
	public static int getMonthGap( String sDt, String eDt ) {
		 int finalGapYm = 0;
		 
		 try {
			Calendar endDayCal = new GregorianCalendar();
		    endDayCal.setTime(new SimpleDateFormat("yyyyMM", Locale.getDefault()).parse(eDt.substring(0,6)));
			 
			Calendar startDayCal = new GregorianCalendar();
			startDayCal.setTime(new SimpleDateFormat("yyyyMM", Locale.getDefault()).parse(sDt.substring(0,6)));
			 
			long diffMillis = endDayCal.getTimeInMillis() - startDayCal.getTimeInMillis();
			
			String gapString =CmFunction.getDateToString(new Date(diffMillis), "yyyyMM");
			
		    // 월단위로 시트개수 계산.  (예:201204 ~ 201306 => 15달)
			finalGapYm = (CmFunction.getIntValue(gapString.substring(0,4)) - 1970)*12 + CmFunction.getIntValue(gapString.substring(4,6));			
			
		} catch (ParseException e) {			
			finalGapYm = 0;
		}			
		return finalGapYm;
		
	}
	

    /**
     * 파일을 읽어서 내용을 리턴한다.
     * @param sPath           파일의 패스+파일명
     * @return result         파일 내용을 담고 있는 스트링 객체
     * @throws Exception
     */
    @SuppressWarnings("resource")
	public static String fileRead(String path) throws Exception
    {
        String result = "";
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            fr = new FileReader(path);
            reader = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            int len = 4096; // 4k
            char[] buff = new char[len];
            while (true)
            {
                int rsize = reader.read(buff, 0, len);
                if (rsize < 0)
                {
                    break;
                }
                sb.append(buff, 0, rsize);
            }
            buff = null;
            result = sb.toString();
        }finally{
        	if(reader != null) reader.close();
        	if(fr != null) fr.close();
        }

        return result;
    }
    
    /**
     * 파일작성
     * @param  path       파일  패스+이름
     * @param  contents   컨텐츠 내용
     * @throws Exception
     */
     public static void fileWrite(String path, String contents) throws Exception {
         File file = null;
         FileWriter fw = null;
         BufferedWriter owriter = null;
         try {
            file = new File(path);
            fw = new FileWriter(file);
            owriter = new BufferedWriter( fw );
            owriter.write(contents);
            owriter.flush();
         } finally {
 			if (null != owriter) owriter.close();
			if (null != fw) fw.close();
		}
     }
     
     /**
      * REQUEST 객체를 뽑아올수 없는 곳에서 리턴 시킴
      * web.xml 에 리스너 설정
      * @return
      */
     public static  HttpServletRequest getCurrentRequest() {

	       ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
	               .currentRequestAttributes();

	       HttpServletRequest hsr = sra.getRequest();
	       return hsr;
	   }
     
     /**
      * 현재 언어 코드를 반환 ( ko, en ...)
      * @return
      */
     public static String getLanguage() {
    	 return CmFunction.getStringValue(CmFunction.getCurrentRequest().getSession().getAttribute("language"));
     }
     
     public static String getDateString(String dateStr) {
    	 String result = "";
    	 Calendar cal = Calendar.getInstance();
    	 cal.toString();
    	 return result;
     }
     
 	/**
 	 * 
 	 * @param request
 	 * @param dataMap
 	 */
	public static void setPageUrlAndPars(HttpServletRequest request, CmMap dataMap ) {
 		
 		StringBuffer 	sb 			= new StringBuffer();
 		String 			pageUrl 	= request.getRequestURI();
 	    String			pagePars	= "";
 	    int				len			= 0;
 	    String 			key 		= "";
 	    int				idx			= -1;
 	    String[]		values		= null;
 	    
 	    try {
 	    	
 	    	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); )
 	    	{
 	    		key  	= CmFunction.getStringValue((String)en.nextElement());
 	    		idx		= key.indexOf("_temp");
 	    		if ( (!key.equals("") && idx == -1) && (!key.equals("pReturnUrl") && !key.equals("pReturnPars"))) {
    				values			= request.getParameterValues(key);
    				len				= values.length;
    				
    				if (len > 1) {
    					for (int i = 0; i < len; i++) {
    						sb.append(key).append('=').append(URLEncoder.encode(values[i], CmPathInfo.getCHARSET())).append('&');
    					}
    				}
    				else {
    					sb.append(key).append('=').append(URLEncoder.encode(values[0], CmPathInfo.getCHARSET())).append('&');
    				}
 	    		}
 	    	}
 	    	pagePars	= sb.toString();
 	    	
 	    	dataMap.put("pPageUrl", pageUrl);
 	    	dataMap.put("pPagePars", pagePars);
 	    	
 	    } catch (UnsupportedEncodingException e) {
 	    	dataMap.put("pPageUrl", "");
 	    	dataMap.put("pPagePars", "");
 	    }
 	}
 	
 	public static String getSessionStringValue(String key) {
 		return CmFunction.getStringValue(getCurrentRequest().getSession().getAttribute(key));
 	}
 	
	/**
	 * 
	 * @param request
	 * @param dataMap
	 */
	public static void setSessionValue(HttpServletRequest request, CmMap<Object, Object> dataMap ) {
		String	userId		= (String) SessionUtils.getAuthenticatedUserForMap().get("USER_NO");
		String	loginId		= (String) SessionUtils.getAuthenticatedUserForMap().get("LOGIN_ID");
		String	vUsernm		= (String) SessionUtils.getAuthenticatedUserForMap().get("LOGIN_NM");
		String	vDeptcd		= (String) SessionUtils.getAuthenticatedUserForMap().get("DEPT_CD");
		String	vGroupid	= (String) SessionUtils.getAuthenticatedUserForMap().get("ROLE_NO");

		dataMap.put("s_userid",			userId);
		dataMap.put("s_loginid",		loginId);
		dataMap.put("s_usernm",			vUsernm);
		dataMap.put("s_deptcd", 		vDeptcd);
		dataMap.put("s_groupid", 		vGroupid);

		if (userId == null || userId.equals(""))
			userId		= "guest";

		dataMap.put("pRegUserId", userId);
		dataMap.put("pUpdateUserId", userId);
	}
	

    
    /**
     * Excel Cell Style
     * @param wb
     * @return
     */
    public static HSSFCellStyle getHSSFCellStyle(HSSFWorkbook wb) {
    	return (HSSFCellStyle)createStyles(wb).get("default");
    }
    
    public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, String[] titleArray) {
    	return getHSSFSheet(wb, titleArray, null, null);
    }
    
    public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, String[] titleArray, int[] columnWidth) {
		return getHSSFSheet(wb, titleArray, columnWidth, null);
    }
     
    public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, String[] titleArray, int[] columnWidth, String[] styleArray ) {
    	
    	int titleLen	= titleArray == null ? 0 : titleArray.length;
    	int widthLen	= columnWidth == null ? 0 : columnWidth.length;
    	int csLen		= styleArray == null ? 0 : styleArray.length;
    	
    	Map<String, CellStyle>	stylesMap	= createStyles(wb);	
    	HSSFSheet 				sheet 		= wb.createSheet();			// POI 객체 생성
    	HSSFRow 				header 		= sheet.createRow(0);		// 조회일자가 들어갈 행을 만든다.
    	
    	for ( int i = 0 ; i < titleLen ; i++ ) {
    		header.createCell(i);
    	}
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    	SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    	
    	try {
    		header.getCell(0).setCellValue("(조회일자 : "+simpleDateFormat.format(new Date())+") " + getDayName(simpleDateFormat2.format(new Date()))+"요일");	
		} catch (ParseException e) {header.getCell(0).setCellValue(""); }
    	
    	sheet.createRow(1);
    	
    	HSSFRow row;
    	HSSFCell cell;
    	
    	//컬럼 타이틀 row 생성
    	row = sheet.createRow(2);
    	//컬럼 타이틀 설정
    	for ( int i = 0 ; i < titleLen ; i++ ) {
    		cell = row.createCell(i); 
    		cell.setCellValue(titleArray[i]);
    		
    		if (csLen > i && styleArray[i] != null) {
    			cell.setCellStyle(stylesMap.get(styleArray[i]));
    		}
    		else {
    			cell.setCellStyle(stylesMap.get("default"));
    		}
    		
    		// width 설정
    		if (widthLen > i) {
    			sheet.setColumnWidth(i, columnWidth[i] * 265);
    		}
    		else {
    			sheet.autoSizeColumn(i);
    		}
    	}
    	
    	return sheet;
    }
    
    public static HSSFSheet getHSSFSheet(HSSFWorkbook wb,String name, String[] titleArray, int[] columnWidth, String[] styleArray ) {
    	
    	int titleLen	= titleArray == null ? 0 : titleArray.length;
    	int widthLen	= columnWidth == null ? 0 : columnWidth.length;
    	int csLen		= styleArray == null ? 0 : styleArray.length;
    	
    	Map<String, CellStyle>	stylesMap	= createStyles(wb);	
    	HSSFSheet 				sheet 		= wb.createSheet(name);		// POI 객체 생성
    	HSSFRow 				header 		= sheet.createRow(0);		// 조회일자가 들어갈 행을 만든다.
    	
    	for ( int i = 0 ; i < titleLen ; i++ ) {
    		header.createCell(i);
    	}
    	
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    	
    	try {
    		header.getCell(0).setCellValue("(조회일자 : "+simpleDateFormat.format(new Date())+") " + getDayName(simpleDateFormat.format(new Date()))+"요일");	
		} catch (ParseException e) {header.getCell(0).setCellValue("");}
    	
    	sheet.createRow(1);
    	
    	HSSFRow row;
    	HSSFCell cell;
    	
    	//컬럼 타이틀 row 생성
    	row = sheet.createRow(2);
    	//컬럼 타이틀 설정
    	for ( int i = 0 ; i < titleLen ; i++ ) {
    		cell = row.createCell(i); 
    		cell.setCellValue(titleArray[i]);
    		
    		if (csLen > i && styleArray[i] != null) {
    			cell.setCellStyle(stylesMap.get(styleArray[i]));
    		}
    		else {
    			cell.setCellStyle(stylesMap.get("default"));
    		}
    		
    		// width 설정
    		if (widthLen > i) {
    			sheet.setColumnWidth(i, columnWidth[i] * 265);
    		}
    		else {
    			sheet.autoSizeColumn(i);
    		}
    	}
    	
    	return sheet;
    }
    
    /**
     * Cell Style
     * @param wb
     * @return
     */
    public static Map<String, CellStyle> createStyles(HSSFWorkbook wb){
    	
    	Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
    	CellStyle style;
    	Font font;
        /*
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short)18);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(titleFont);
		styles.put("title", style);
         */
        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)10);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        monthFont.setFontName("맑은 고딕");
        style.setFont(monthFont);
        style.setWrapText(true);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        styles.put("header2", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font = wb.createFont();
        font.setFontName("맑은 고딕");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setWrapText(true);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        styles.put("header3", style);
        
        style = wb.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("header", style);

        /*
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);
        */
        
        // 기본
        style = wb.createCellStyle();
        style.setWrapText(true);
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
        style.setFont(font);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("default", style);
    	
    	// 기본
    	style = wb.createCellStyle();
    	style.setWrapText(true);
    	style.setAlignment(CellStyle.ALIGN_RIGHT);
    	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("default_r", style);
    	
    	// 기본
    	style = wb.createCellStyle();
    	style.setWrapText(true);
    	style.setAlignment(CellStyle.ALIGN_CENTER);
    	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("default_c", style);
    	
    	// 기본
        style = wb.createCellStyle();
        style.setWrapText(true);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	Font linkFont = wb.createFont();
    	linkFont.setFontName("맑은 고딕");
    	linkFont.setColor(IndexedColors.BLUE.getIndex());
    	linkFont.setUnderline(Font.U_SINGLE);
    	style.setFont(linkFont);
    	styles.put("link", style);
    	
    	font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    	font.setFontName("맑은 고딕");
    	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("default_bold", style);
    	
        style = wb.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
    	style.setAlignment(CellStyle.ALIGN_CENTER);
    	style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("default_bold_c", style);
    	
    	
    	//엑셀 다운시 숫자 처리
    	font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	style = wb.createCellStyle();
    	style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
        styles.put("number", style);
        
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(Font.COLOR_RED);
    	style = wb.createCellStyle();
    	style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
        styles.put("red_number", style);
        
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(IndexedColors.BLUE.getIndex());
    	style = wb.createCellStyle();
    	style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0"));
        styles.put("blue_number", style);
        
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("0.0000000"));
        styles.put("number2", style);
        
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("0.0000000000"));
        styles.put("point_under10", style);
        
    	// 소수점 기본
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
		style = wb.createCellStyle();
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setDataFormat(wb.createDataFormat().getFormat("#,##0.0"));
		styles.put("percent", style);
        
		font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(Font.COLOR_RED);
    	style = wb.createCellStyle();
    	style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0.0"));
        styles.put("red_percent", style);
        
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(IndexedColors.BLUE.getIndex());
    	style = wb.createCellStyle();
    	style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("#,##0.0"));
        styles.put("blue_percent", style);
		
        font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("double", style);
        
        //ExcelProcedureView logoStyle
		font = wb.createFont();
		font.setFontHeightInPoints((short)14);
    	font.setFontName("맑은 고딕");
		style = wb.createCellStyle();
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styles.put("logoStyle", style);
		
		//ExcelProcedureView partStyle
    	font = wb.createFont();
    	font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    	style = wb.createCellStyle();
    	style.setFont(font);
    	style.setWrapText(true);
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	style.setFillForegroundColor(HSSFColor.YELLOW.index);
    	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	style.setBottomBorderColor(HSSFColor.BLACK.index);
    	style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	style.setLeftBorderColor(HSSFColor.BLACK.index);
    	style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	style.setRightBorderColor(HSSFColor.BLACK.index);
    	style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	style.setTopBorderColor(HSSFColor.BLACK.index);
    	styles.put("partStyle", style);
    	
    	//ExcelProcedureView tableStyle
    	font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
    	style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("tableStyle", style);
		
		//ExcelProcedureView tableStyle2 border no_fill
		font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
		style = wb.createCellStyle();	
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.NO_FILL);
		style.setBorderLeft(HSSFCellStyle.NO_FILL);
		style.setBorderRight(HSSFCellStyle.NO_FILL);
		style.setBorderTop(HSSFCellStyle.NO_FILL);
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("tableStyle2", style);

		//ExcelProcedureView tableStyle3 border no_fill
		font = wb.createFont();
    	font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
    	font.setFontName("맑은 고딕");
    	font.setColor(font.COLOR_NORMAL);
		style = wb.createCellStyle();	
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderBottom(HSSFCellStyle.NO_FILL);
		style.setBorderLeft(HSSFCellStyle.NO_FILL);
		style.setBorderRight(HSSFCellStyle.NO_FILL);
		style.setBorderTop(HSSFCellStyle.NO_FILL);
		style.setWrapText(true);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		styles.put("tableStyle3", style);
		
		//ExcelProcedureView titleStyle

		font = wb.createFont();
		font.setFontHeightInPoints((short)18);
    	font.setFontName("맑은 고딕");
		style = wb.createCellStyle();
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		styles.put("titleStyle", style);
		
		font = wb.createFont();
		font.setFontHeightInPoints((short)14);
    	font.setFontName("맑은 고딕");
		style = wb.createCellStyle();
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("titleStyle_left", style);
		
		font = wb.createFont();
		font.setFontHeightInPoints((short)10);
    	font.setFontName("맑은 고딕");
		style = wb.createCellStyle();
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styles.put("nomal_left", style);
		
		//ExcelCoaView theadStyle
		
		font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		font.setFontName("맑은 고딕");
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
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

		styles.put("theadStyle", style);
		
		font = wb.createFont();
    	font.setFontName("맑은 고딕");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setUnderline(Font.U_SINGLE);
		font.setFontHeightInPoints((short)14);
		style = wb.createCellStyle();
		style.setFont(font);
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		styles.put("coaTitleStyle", style);
		
		style = wb.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		
		styles.put("coaUnderLineStyle", style);
		
    	
        return styles;
    }
    
    /**
	 * 20161019 :: 셀 병합시 스타일 유지 및 높이변경 X (가로 병합 col)
	 * @param sheet
	 * @param cell
	 * @param row
	 * @param style
	 * @param startCol
	 * @param endCol
	 * @param cellValue
	 */
	public static void styleMarged2(HSSFSheet sheet, HSSFCell cell,HSSFRow row, CellStyle style, int startCol,int endCol,String cellValue){
		int rownum = row.getRowNum();
	    	for(int z=startCol;z<endCol;z++){
	    		HSSFCell c1	= cell;
	    		c1 = row.createCell(z);
	    		c1.setCellValue(cellValue);
	    		c1.setCellStyle(style);
			}
		sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,startCol,endCol-1));
	}
    
	/**
	 * 20130613 :: 셀 병합시 스타일 유지 (가로 병합 col)
	 * @param sheet
	 * @param cell
	 * @param row
	 * @param style
	 * @param startCol
	 * @param endCol
	 * @param cellValue
	 */
	public static void styleMarged(HSSFSheet sheet, HSSFCell cell,HSSFRow row, CellStyle style, int startCol,int endCol,String cellValue){
		int rownum = row.getRowNum();
	    	for(int z=startCol;z<endCol;z++){
	    		HSSFCell c1	= cell;
	    		c1 = row.createCell(z);
	    		c1.setCellValue(cellValue);
	    		c1.setCellStyle(style);
				if(cellValue.length() > 40){
					if(cellValue.length() > 80){
						if(cellValue.length() > 160){
							row.setHeightInPoints((short)90);
						}else{
							row.setHeightInPoints((short)55);
						}
					}else{
						row.setHeightInPoints((short)25);
					}
				}
			}
		sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,startCol,endCol-1));
	}
	
	public static void styleMarged(HSSFSheet sheet, HSSFCell cell,HSSFRow row, CellStyle style, int startCol,int endCol,HSSFRichTextString cellValue){
		int rownum = row.getRowNum();
	    	for(int z=startCol;z<endCol;z++){
	    		HSSFCell c1	= cell;
	    		c1 = row.createCell(z);
	    		c1.setCellValue(cellValue);
	    		c1.setCellStyle(style);
				if(cellValue.length() > 40){
					if(cellValue.length() > 80){
						if(cellValue.length() > 160){
							row.setHeightInPoints((short)90);
						}else{
							row.setHeightInPoints((short)55);
						}
					}else{
						row.setHeightInPoints((short)25);
					}					
				}
			}
		sheet.addMergedRegion(new CellRangeAddress(rownum,rownum,startCol,endCol-1));
	}
	
	/**
	 * 20130613 :: 셀 병합시 스타일 유지 (세로 병합 row)
	 * @param sheet
	 * @param cell
	 * @param row
	 * @param style
	 * @param startCol
	 * @param endCol
	 * @param cellValue
	 */
	public static void styleMargedRow(HSSFSheet sheet, HSSFCell cell,HSSFRow row, CellStyle style, int startRow,int endRow,String cellValue){
			int cellnum =  cell.getColumnIndex();
	    	for(int z=startRow;z<=endRow;z++){
	    		HSSFRow r1	= row;
	    		r1 = sheet.getRow(z);
	    		HSSFCell c1	= cell;
	    		c1 = r1.createCell(cellnum);
	    		c1.setCellValue(cellValue);
	    		c1.setCellStyle(style);		
			}
		sheet.addMergedRegion(new CellRangeAddress(startRow,endRow,cellnum,cellnum));
	}
    
    /**
     * CmMap >> JRDataSource
     * @param cmMap
     * @return
     */
    public static JRDataSource getDataSource(CmMap cmMap) {
		List<CmMap> items = new ArrayList<CmMap>();
		items.add(cmMap);
		
		JRDataSource ds = new JRBeanCollectionDataSource(items);
		return ds;
	}
    
    /**
     * CmMap >> JRDataSource
     * @param cmMap
     * @return
     */
    public static JRDataSource getDataSource(List<CmMap> list) {
    	JRDataSource ds = new JRBeanCollectionDataSource(list);
    	return ds;
    }
    
    /**
     * 지정한 CmMap과 컬럼이름(key)으로 value들고오기 
     * @param cmMap
     * @param key
     * @return
     */
    public static String getCmMapValue(CmMap cmMap, String key) {
    	if (cmMap == null)
    		return "";
    	return cmMap.getString(key);
    }
    
    public static String getTodayString(String format) {
		return getDateToString(new Date(), format);
	}
    
    /**
     * 년월일(요일)
     * @param format
     * @return
     */
    public static String getTodayString2(String format) {
    	String week[] = { "일", "월", "화", "수", "목", "금", "토"};
    	String dtm = getDateToString(new Date(), format);
    	Calendar cal = Calendar.getInstance();
    	int day = cal.get(Calendar.DAY_OF_WEEK);
    	
    	StringBuilder sb = new StringBuilder();
    	
    //	dtm = dtm + "(" +  week[(day-1)] +")";
    	sb.append(dtm).append('(').append(week[(day-1)]).append(')');
		return sb.toString(); 
	}
    
    /**
     * ex)  in : 20170113 , out : 금   
     * @param dtm
     * @return
     * @throws ParseException
     */
    public static String getDayName(String dtm) throws ParseException{
    	String week[] = { "일", "월", "화", "수", "목", "금", "토"};
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
    	Date nDate = sdf.parse(dtm);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(nDate);
    	
    	int day = cal.get(Calendar.DAY_OF_WEEK);
    	
    	return week[(day - 1)];
    }
    
    /**
     * 지정한 CmMap과 컬럼이름(key)으로 value들고오기2 (날짜value처리) 
     * @param cmMap
     * @param key
     * @return
     */
    public static String getCmMapValueMain(CmMap cmMap, String key) {
    	if (cmMap == null){
    		return "";
    	}
    	
    	if (key == null || key.equals("")) {
    		return "";
    	}
	
    	if(key.lastIndexOf("_dt") > -1 ||key.lastIndexOf("_date") > -1 || key.lastIndexOf("_dtm") > -1	|| key.lastIndexOf("_ym") > -1 || key.lastIndexOf("_budat") > -1){
    		return CmFunction.getPointDate(cmMap.getString(key));
    	}    	
    	return cmMap.getString(key);
    }
    
    /**
     * String(글자, 문자) length(길이) 체크
     * @param str
     * @return
     */
    public static int getStringLength(String str){
    	int strLength = str.length();
    	return strLength;
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static boolean createThumbnail(File sourceFile, String targetPath, String targetFileName, int width, int height, String flagFixed) {
    	System.setProperty("java.awt.headless", "true");
    	
    	boolean result = true;
    	
    	if(sourceFile == null || !sourceFile.exists()) {
    		return false;
    	}
    	
    	try {
    		RenderedOp renderedOp = JAI.create("fileload", sourceFile.getPath());
    		
    		int imgWidth = renderedOp.getWidth();
    		int imgHeight = renderedOp.getHeight();
    		int destWidth = -1;
    		int destHeight = 01;
    		
    		if(width > 0 && height > 0) {
    			if(imgWidth <= width) {
    				destWidth = imgWidth;
    			} else {
    				destWidth = width;
    			}
    			
    			if(imgHeight <= height) {
    				destHeight = imgHeight;
    			} else {
    				destHeight = height;
    			}
    		} else if(width > 0 && height <= 0) {
    			if(imgWidth <= width) {
    				destWidth = imgWidth;
    				destHeight = imgHeight;
    			} else {
    				destWidth = width;
    				destHeight = (int)(imgHeight * width / imgWidth);
    			}
    		} else if(width <= 0 && height > 0) {
    			if(imgHeight <= height) {
    				destWidth = imgWidth;
    				destHeight = imgHeight;
    			} else {
    				destWidth = (int)(imgWidth * height / imgHeight);
    				destHeight = imgHeight;
    			}
    		}
    		
    		String imageName = sourceFile.getName().toLowerCase();
    		String imageExt = imageName.substring(imageName.lastIndexOf('.') + 1, imageName.length());
    		
    		BufferedImage srcImage = ImageIO.read(sourceFile);
    		
    		ResampleOp resampleOp = new ResampleOp(destWidth, destHeight);
    		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
    		
    		BufferedImage rescaledImage = resampleOp.filter(srcImage, null);
    		
    		File file = new File(targetPath);
    		
    		if(!file.exists()) {
    			file.mkdirs();
    		}
    		
    		ImageIO.write(rescaledImage, imageExt, new File(targetPath + targetFileName));
    	} catch(FileNotFoundException e) {
    		result = false;
    	} catch(IOException e) {
    		result = false;
    	}
    	return result;
    }
    
	// 년월일시분초 [YYYYMMDDHHMMSS] pdf용
	public static String getPointDtm2(String pSource) {
		if (getStringValue(pSource).length() < "YYYYMMDDHHMMSS".length()) { 
			return pSource;
		}

		return pSource.substring( 0,  4)
					+ "."        + pSource.substring( 4,  6)
					+ "."        + pSource.substring( 6,  8)
					+ "   " + pSource.substring( 8, 10)
					+ ":"        + pSource.substring(10, 12)
					+ ":"        + pSource.substring(12, 14);        
	}
	
	public static String[] getCmSplit(String pSource, String pSplit) {
		
		if(CmFunction.isNotEmpty(pSource) && CmFunction.isNotEmpty(pSplit)){
			return pSource.split(pSplit);
		}
		
		return new String[0];
	}
	
	/**
	 * XSS 공격 대비 스트립트를 변경하는 함수
	 * @param str
	 * @return
	 */
	public static String replaceAlltoXSS(String str){
		String	result	= str;
		result = result.replaceAll("javascript",	"x-javascript");
		result = result.replaceAll("iframe",		"x-iframe");
		result = result.replaceAll("vbscript",	"x-vbscript");
		result = result.replaceAll("applet",		"x-applet");
		result = result.replaceAll("embed",		"x-embed");
		result = result.replaceAll("grameset",	"x-grameset");
		result = result.replaceAll("bgsound",		"x-bgsound");
		result = result.replaceAll("onblur",		"x-onblur");
		result = result.replaceAll("onchange",	"x-onchange");
		result = result.replaceAll("onclick",		"x-onclick");
		result = result.replaceAll("ondblclick",	"x-ondblclick");
		result = result.replaceAll("enerror",		"x-enerror");
		result = result.replaceAll("onfocus",		"x-onfocus");
		result = result.replaceAll("onload",		"x-onload");
		result = result.replaceAll("onmouse",		"x-onmouse");
		result = result.replaceAll("onscroll",	"x-onscroll");
		result = result.replaceAll("onsubmit",	"x-onsubmit");
		result = result.replaceAll("onunload",	"x-onunload");
		return result;
	}
	
	public static String getDateGapToString(String date, int gapInt){
		if (CmFunction.isEmpty(date)) {
			return "";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String str = getOnlyNumber(date);

		Calendar cal = Calendar.getInstance();

		cal.set(Integer.parseInt(str.substring(0, 4)),Integer.parseInt(str.substring(4,6))-1,Integer.parseInt(str.substring(6,8)));
		cal.add(Calendar.DATE, gapInt);
		
		return sdf.format(cal.getTime());	
	}
	
	public static String getExtensionStiring(String filenm){
		
		String result = "file"; 
		String ext[] = {".doc", ".ppt", ".xls", ".xlsx", ".psd", ".txt", ".csv", ".hwp", ".pdf"};
		int len = ext.length;
		
		for(int i = 0 ; i < len ; i ++){
			if( filenm.indexOf(ext[i]) > -1 ){
				result = ext[i].replaceAll("\\.", "");
				break;
			}
			
		}
		
		return result;
	}
	
	public static String getRedDateForEng(String pSource) {
		String sResult	= "";
		
		if (CmFunction.isNotEmpty(pSource) && pSource.length() >= "YYYYMMDD".length() )
		{
			String	sYear		= pSource.substring(0, 4);
			String 	sMonth		= pSource.substring(4, 6);
			String 	sDate		= pSource.substring(6, 8);
			
			int		iMonth		= getIntValue(sMonth);
			
			if (iMonth > 0 && iMonth < 13 )
			{
				sResult	= sDate + ". " + FULLMONTH[iMonth - 1] + ". " + sYear;
			}
		}
		return sResult;
	}
	
	public static int[] getShuffleCount(String size) {
		
		int s = Integer.parseInt(size);
		int[] arr = new int[s];
		
		for(int i = 0 ; i < s ; i ++){
			arr[i] = i+1;
		}
		
        if(arr==null || arr.length==0)
            return arr;
        
        for(int x=0; x<arr.length*2;x++) {
            int i = (int)(Math.random()*arr.length);
            int j = (int)(Math.random()*arr.length);
            
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            }
        return arr;
	}
	
	// 파일경로 가져오기
	public static String getAttachUrl(String sFullUrl ) {
		String	sReturn = "";
		
		if(CmFunction.isNotEmpty(sFullUrl)) {
			String[] sp = sFullUrl.split("/UPLOAD/");
			if(CmFunction.isNotEmpty(sp[1])){
				sReturn = "/UPLOAD/" + sp[1];
			}
			else {
				sReturn = sFullUrl;
			}
		}
		
		return sReturn;
	}
	
	public static double logCalcul(double param, double param2) {		
		double e = Math.round((param - param2)*100)/100.0;

		return e;
	}

	public static String pdfStringSplitBr(String reqSplitStr, int reqSplitLen) {
		if(CmFunction.isEmpty(reqSplitStr)) {
			return "";
		}
		
		if(reqSplitLen == 0) {
			return reqSplitStr;
		}
		
		String[] arrSplit = reqSplitStr.split("\n");
		int splitLen = arrSplit == null ? 0 : arrSplit.length;
		StringBuffer sbSplit = null;
		
		if(splitLen > 0) {
			sbSplit = new StringBuffer();
			
			String splString = "";
			int splLen = 0;
			
			for(int i=0; i<splitLen; i++) {
				splString = arrSplit[i];
				splLen = splString.length();
				
				if(splString.indexOf(' ') == -1) {
					if(splLen > reqSplitLen) {
						int spl = splLen / reqSplitLen;
						int splmod = splLen % reqSplitLen;
						
						for(int j=0; j< spl; j++) {
							if(j != (spl - 1)) {
								sbSplit.append(splString.substring((j * reqSplitLen), ((j + 1) * reqSplitLen) )).append("<br/>");
							} else {
								sbSplit.append(splString.substring((j * reqSplitLen), (splLen - splmod) )).append("<br/>");
							}
						}
						
						if(splmod > 0) {
							sbSplit.append(splString.substring(splLen - splmod, splLen));
						}
					} else {
						sbSplit.append(reqSplitStr);
					}
				} else {
					sbSplit.append(reqSplitStr);
				}
			}
		}
		
		return sbSplit!=null?sbSplit.toString():"";
	}
	
	@SuppressWarnings("unchecked")
	public static List sortByValue(final Map<String, ?> map) {
        List<String> list = new ArrayList<String>();
        list.addAll(map.keySet());
         
        Collections.sort(list,new Comparator() {
             
            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                 
                return ((Comparable) v2).compareTo(v1);
            }
             
        });
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }
	
	
	/**
	 * ' (작음따옴표) -> ''(작음따옴표 2개 연속)으로 변경하여 쿼리에서 오류가 나지 않도록 변경함
	 * @param str 
	 * @return
	 */
	public static String replaceAllSingleCut(String str) {
		
		String	sReturn		= "";
		
		if (str != null && !str.equals(""))
		{
			sReturn			= str.replaceAll("'", "''");		//  ' 값 변경
		}
		
		return sReturn;
	}

	public static String convertNumberToString(String money) {
		if(CmFunction.isEmpty(money)) {
			return "";
		}
		
		String[] han1 = {"","일","이","삼","사","오","육","칠","팔","구"}; 
		String[] han2 = {"","십","백","천"}; String[] han3 = {"","만","억","조","경"}; 
		StringBuffer result = new StringBuffer(); 
		
		int len = money.length(); 
		
		for(int i=len-1; i>=0; i--) { 
			result.append(han1[Integer.parseInt(money.substring(len-i-1, len-i))]); 
			
			if(Integer.parseInt(money.substring(len-i-1, len-i)) > 0) result.append(han2[i%4]); 
			
			if(i%4 == 0) result.append(han3[i/4]); 
		} 
		
		return result.toString();
	}
	
	public static String md5Encoding(String inpara) {
    	
    	if (inpara == null || inpara.equals("")) {
    		return "";
    	}
    	
    	byte[] bpara = new byte[inpara.length()];
    	byte[] rethash;
    	
    	for (int i = 0; i < inpara.length(); i++) {
    		bpara[i] = (byte)(inpara.charAt(i) & 0xff);
    	}
    	
    	try {
    		MessageDigest md5er = MessageDigest.getInstance("MD5");
    		rethash = md5er.digest(bpara);
    	} catch (GeneralSecurityException e) {
    		return "";
    	}
    	
    	StringBuffer r = new StringBuffer(32);
    	
    	for (int i = 0; i < rethash.length; i++) {
    		String x = Integer.toHexString(rethash[i] & 0Xff).toLowerCase();
    		
    		if (x.length() < 2)
    			r.append('0');
    		
    		r.append(x);
    	}
    	
    	return r.toString();
    }
	
	// 기준에 따라 현재날짜의 주의 날짜를 구함.
	public static String getStringCurDayOfTheWeek(String flag){

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);

		Calendar c = Calendar.getInstance();

		if("-SAT".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
			c.add(c.DATE,-7);
		}else if("-SUN".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		}else if("MON".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		}else if("TUE".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
		}else if("WED".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
		}else if("THU".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
		}else if("FRI".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
		}else if("SAT".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
		}else if("SUN".equals(flag)) {
			c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
			c.add(c.DATE,7);
		}
	
		return formatter.format(c.getTime());
	}
	
	
	// 호출 URL에 특정 주소가 포함되어 있는지 검사한다.
	public static boolean getURLFilterCheck(String checkURL){
		HttpServletRequest	request	=	CmFunction.getCurrentRequest();
		StringBuffer	url	=	request.getRequestURL();
		boolean	isIPad	=	false;
		if (url.indexOf(checkURL) > -1) {
			isIPad = true;
		}
		return isIPad;
	}
	
	// 이미지 파일을 base64기반 문자로 치환합니다. <img src="**"> ** 부분에 대입하여 사용이 가능합니다. 
	public static String imgEncodeToBase64String(String filePath) {
		String	imageString = "";
		String	imageType = filePath.substring(filePath.lastIndexOf('.')+1);
		try {
			
			BufferedImage img = ImageIO.read(new File(filePath));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(img, imageType, bos);
			byte[] imageBytes = bos.toByteArray();
			
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
			
			bos.close();
		} catch (IOException e) {
			return "";
		}
		return "data:imge/"+imageType+";base64,"+imageString;
	}
	
	
	public static Document parseXML(InputStream stream) throws Exception{
		DocumentBuilderFactory obj = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		
		obj = DocumentBuilderFactory.newInstance();
		objDocumentBuilder = obj.newDocumentBuilder();
		doc = objDocumentBuilder.parse(stream);
		
		return doc;
	}

	public static String getSignDate (){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy. MMMMM. dd.", new Locale("en","US"));
		Date date=new Date();
		return sdf.format(date);
	}

	/**
	 * javascript의 encodeURIComponent
	 */
	public static String encodeURIComponent(String s) {
		String result = null;
		try {
			result = URLEncoder.encode(s, "UTF-8")
							.replaceAll("\\+", "%20")
							.replaceAll("\\%21", "!")
							.replaceAll("\\%27", "'")
							.replaceAll("\\%28", "(")
							.replaceAll("\\%29", ")")
							.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			result = s;
		}

		return result;
	}
}
