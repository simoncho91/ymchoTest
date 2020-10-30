package com.shinsegae_inc.sitims.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CmPathInfo {
	/** yml config */
	private	static	String	active;
	private	static	String	uploadPath;
	private	static	String	baseUrl;

	@Value("${spring.profiles.active}")
	public void setActive(String val) {
		CmPathInfo.active = val;
	}

	@Value("${globals.file.uploadPath}")
	public void setUploadPath(String val) {
		CmPathInfo.uploadPath = val;
	}

	@Value("${spring.boot.admin.client.instance.service-base-url}")
	public void setBaseUrl(String val) {
		CmPathInfo.baseUrl = val;
	}

	private static String	rootPath	= "/";
	private static String	charSet		= "UTF-8";

	public static String getREAL_SERVER_YN() {
		return CmPathInfo.active.equals("live") ? "Y" : "N";
	}

	public static String getROOT_PATH() {
		return rootPath;
	}

	public static String getROOT_FULL_URL() {
		return CmPathInfo.baseUrl + "/";
	}

	public static String getROOT_FULL_URL_ODM() {
		return CmPathInfo.baseUrl + "/";	// 추후 수정
	}

	public static String getIMG_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "sitims/images/";
	}

	public static String getCSS_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "sitims/CSS/";
	}

	public static String getFLASH_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "SWF/";
	}

	public static String getSCRIPT_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "sitims/js/";
	}

	public static String getEDITOR_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "sitims/namoCrossEditor/js/";	// 추후 수정
	}

	public static String getCHARTS_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "charts/";	// 추후 수정
	}

	public static String getUPLOAD_PATH() {
		return CmPathInfo.uploadPath + "/";
	}

	public static String getUPLOAD_PDF_IMAGE_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_PDF_IMAGE/";
	}

	public static String getUPLOAD_FILE_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_FILE/";
	}

	public static String getUPLOAD_FILE_TEMP_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_FILE_TEMP/";
	}

	public static String getUPLOAD_IMAGE_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_IMAGE/";
	}

	public static String getUPLOAD_IMAGE_TEMP_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_IMAGE_TEMP/";
	}

	public static String getCHARSET() {
		return charSet;
	}

	public static String getUPLOAD_USERIMAGE_FILE_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_USERIMAGE/";
	}

	public static String getUPLOAD_USERIMAGE_FILE_TEMP_PATH() {
		return CmPathInfo.getUPLOAD_PATH() + "UPLOAD_USERIMAGE_TEMP/";
	}

	public static String getUPLOAD_IMG_PATH() {
		return CmPathInfo.getROOT_PATH() + "UPLOAD/";
	}

	private static String	testReceivedEmail;
	private static final String	MAIL_FROM_NAME	= "제조통합포탈";
	private static final String	MAIL_FROM_EMAIL	= "tiims@ishift.com";

	public static String getMAIL_FROM_NAME() {
		return MAIL_FROM_NAME;
	}

	public static String getMAIL_FROM_EMAIL() {
		return MAIL_FROM_EMAIL;
	}

	public static String getTEST_RECEIVED_EMAIL() {
		return testReceivedEmail;
	}

	public static String getFont_PATH() {
		return CmPathInfo.getROOT_FULL_URL() + "sitims/fonts/";
	}

}
