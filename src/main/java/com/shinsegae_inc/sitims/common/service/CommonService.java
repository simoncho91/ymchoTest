package com.shinsegae_inc.sitims.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service("commonServiceSitms")
@SuppressWarnings("rawtypes")
public class CommonService extends CmService  {

	public void setSessionValue(HttpServletRequest request, CmMap reqVo) {
		
		HttpSession 	session			= request.getSession();
		
		reqVo.put("v_userid", 		CmFunction.getStringValue(session.getAttribute("v_userid") ));       
		reqVo.put("v_empno", 		CmFunction.getStringValue(session.getAttribute("v_empno") ));        
		reqVo.put("v_usernm", 		CmFunction.getStringValue(session.getAttribute("v_usernm") ));       
		reqVo.put("v_positcd", 		CmFunction.getStringValue(session.getAttribute("v_positcd") ));      
		reqVo.put("v_funccd", 		CmFunction.getStringValue(session.getAttribute("v_funccd") ));	      
		reqVo.put("v_certbelt", 	CmFunction.getStringValue(session.getAttribute("v_certbelt") ));     
		reqVo.put("v_email", 		CmFunction.getStringValue(session.getAttribute("v_email") ));        
		reqVo.put("v_dept_authcd", 	CmFunction.getStringValue(session.getAttribute("v_dept_authcd") ));  
		reqVo.put("v_sigma_deptcd", CmFunction.getStringValue(session.getAttribute("v_sigma_deptcd") )); 
		reqVo.put("v_sigma_deptnm", CmFunction.getStringValue(session.getAttribute("v_sigma_deptnm") )); 
		reqVo.put("v_deptcd", 		CmFunction.getStringValue(session.getAttribute("v_deptcd") ));       
		reqVo.put("v_compcd",		CmFunction.getStringValue(session.getAttribute("v_compcd") ));       
		reqVo.put("v_offinm", 		CmFunction.getStringValue(session.getAttribute("v_offinm") ));       
		reqVo.put("v_phoneno", 		CmFunction.getStringValue(session.getAttribute("v_phoneno") ));       
		reqVo.put("v_groupid", 		CmFunction.getStringValue(session.getAttribute("v_groupid") ));      
		reqVo.put("v_sysadmin_yn", 	CmFunction.getStringValue(session.getAttribute("v_sysadmin_yn") ));
        reqVo.put("v_thumb_nm", CmFunction.getStringValue( session.getAttribute( "v_thumb_nm" ) ) );
        reqVo.put("v_flag_external", CmFunction.getStringValue( session.getAttribute( "v_flag_external" ) ) );
        reqVo.put("v_folder_cd", CmFunction.getStringValue(session.getAttribute("v_folder_cd")));
		
		reqVo.put("i_sRegUserId", 	reqVo.get("v_userid"));
		reqVo.put("i_sUpdateUserId",reqVo.get("v_userid"));

	}
	

	public List<CmMap>  getCmSubCodeList(CmMap reqVo) {
		return this.cmDao.getList("CommonDao.getCmSubCodeList", reqVo);
	}
	
	public List<CmMap>  getCmSubCodeList(String mstCode) {
		CmMap reqVo = new CmMap();
		reqVo.put("i_sMstCode", mstCode);
		return this.cmDao.getList("CommonDao.getCmSubCodeList", reqVo);
	}
	
	public List<CmMap>  getCmSubCodeList(String mstCode,int index,String buffer) {
		CmMap reqVo = new CmMap();
		reqVo.put("i_sMstCode", mstCode);
		reqVo.put("i_sBuffer"+index, buffer);
		return this.cmDao.getList("CommonDao.getCmSubCodeList", reqVo);
	}
	
	public List<CmMap>  getCmCodeList(CmMap reqVo) {
		return this.cmDao.getList(reqVo.getString("i_sQueryId"), reqVo);
	}
	
	public List<CmMap> getCmSubCodeList(String mstCode, String subCode, String[] arrSubCode, String[][] arrBuffer) {
		CmMap tempVo = new CmMap();
		tempVo.put("SEARCH_TYPE", "TP02");
		tempVo.put("i_sMstCode", mstCode);
		
		if (CmFunction.isNotEmpty(subCode)) {
			tempVo.put("i_sSubCode", subCode);
		}
		
		if (arrSubCode != null && arrSubCode.length > 0) {
			tempVo.put("i_arrSubCode", arrSubCode);
		}
		
		if (arrBuffer != null) {
			int len = arrBuffer.length;
			
			for (int i = 0; i < len; i++) {
				tempVo.put(arrBuffer[i][0], arrBuffer[i][1]);
				if (arrBuffer[i].length >= 3) {
					tempVo.put(arrBuffer[i][0] + "SearchType", arrBuffer[i][2]);
				}
			}
		}
		
		return this.getCmSubCodeList(tempVo);
	}
	
	
	public CmMap  getCmSubCodeInfo(CmMap reqVo) {
		return this.cmDao.getObject("CommonDao.getCmSubCodeInfo", reqVo);
	}
	
	public String getCmSubCodeNm(String mstCode, String subCode) {
		
		if(CmFunction.isNotEmpty(mstCode) || CmFunction.isNotEmpty(subCode)){
		
			CmMap codeVo = new CmMap();
			
			codeVo.put("i_sMstCode", mstCode);
			codeVo.put("i_sSubCode", subCode);
			
			return this.cmDao.getString("CommonDao.getCmSubCodeNm", codeVo);
			
		}
		else{
			return "";
		}
	}

	public void insertCmAttach(CmMap reqVo) {
		this.cmDao.insert("CommonDao.insertCmAttach", reqVo);
	}
	
	public int updateCmAttach(CmMap reqVo) {
		return this.cmDao.update("CommonDao.updateCmAttach", reqVo);
	}
	
	public int deleteCmAttach(CmMap reqVo) {
		return this.cmDao.delete("CommonDao.deleteCmAttach", reqVo);
	}
	
	public int deleteAllCmAttach(CmMap reqVo) {
		return this.cmDao.delete("CommonDao.deleteAllCmAttach", reqVo);
	}
	
	public List<CmMap> getCmAttachList(CmMap reqVo) {
		return this.cmDao.getList("CommonDao.getCmAttachList", reqVo);
	}
	
	public CmMap getPreNextInfo(CmMap reqVo) {
		return cmDao.getObject(reqVo.getString("i_sSqlId"), reqVo);
	}

	public List<CmMap> getAttachListPif(String targetCd, String uploadId,
			String tableName, String buffer) {
		
		CmMap reqVo	=	new CmMap();
		
		reqVo.put("target_cd", targetCd);
		reqVo.put("upload_id", uploadId);
		reqVo.put("table_name", tableName);
		reqVo.put("buffer", buffer);
		
		return this.cmDao.getList("PifReport.getAttachList", reqVo);
	}

	public int getSqlidCount(String sqlid, CmMap reqVo) {
		return this.cmDao.getCount(sqlid, reqVo);
	}
	
	
	public List<CmMap> getSqlidList(String sqlid, CmMap reqVo) {
		return this.cmDao.getList(sqlid, reqVo);
	}
	
	public boolean getExtList(String attachExt) {
		CmMap reqVo= new CmMap();
		reqVo.put("i_sMstCode", "FILE_EXT");
		reqVo.put("i_sSubCode", attachExt);
		List<CmMap> list = this.cmDao.getList("CommonDao.getCmSubCodeList",reqVo);
		
		return list.size()>0?true:false;
	}

	public CmMap getAttachInfo(CmMap reqVo) {
		List<CmMap> list = this.cmDao.getList("CommonDao.selectAttachList", reqVo);
		return (list !=null && list.size()>0)?list.get(0):new CmMap();
	}

	public List<CmMap> getAttachList(CmMap reqVo) {
		return this.cmDao.getList("CommonDao.selectAttachList", reqVo);
	}

	public void updateDownloadCnt(CmMap logVo) {
		this.cmDao.update("CommonDao.updateDownloadCnt", logVo);
		
	}
	public List<CmMap> selectMenuInfoList(CmMap reqVo) {
		return this.cmDao.getList("CommonDao.selectMenuInfoList", reqVo);
	}

	public void insertDownloadLog(CmMap reqVo) {
		// TODO Auto-generated method stub
		this.cmDao.insert("CommonDao.insertDownloadLog", reqVo);
	}


	/**
	 * Error log 출력
	 * 
	 * @param e
	 */
	public void errorLogger(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		String className  = ste[0].getClassName();
		String methodName = ste[0].getMethodName();
		int lineNumber    = ste[0].getLineNumber();
		String fileName   = ste[0].getFileName();

		if (logger.isErrorEnabled()) {
			logger.error("### " + className + "." + methodName + "###");
			logger.error("# FileName : " + fileName);
			logger.error("# LineNumber : " + lineNumber);
			//e.printStackTrace();
		}
	}

	/**
	 * 등록화면 상태별 count
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public CmMap getTiimsDashboardInfo(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		String userRole = (String) SessionUtils.getAuthenticatedUserForMap().get("ROLE_NO");
		if(userRole.indexOf("RA") == -1 && userRole.indexOf("ADMIN") == -1) {
			String userDept = (String) SessionUtils.getAuthenticatedUserForMap().get("DEPT_CD");
			if(CmFunction.isNotEmpty(userDept)) {
				CmMap tmpMap = new CmMap();
				tmpMap.put("i_sUserDept",userDept);
				tmpMap.put("i_sFlagExcelAll", "Y");
				CmMap tmpBrandMap = cmDao.getObject("AmAm030Mapper.selectAmAm030List",tmpMap);
				reqVo.put("i_sUserBrand",tmpBrandMap.get("v_brand_cd"));
			}else {
				reqVo.put("i_sUserBrand",null);
			}
		}
		return this.cmDao.getObject("CommonDao.getTiimsDashboardInfo", reqVo);
	}

	/**
	 * 최초화면 공지사항 정보 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public List<CmMap> getNoticeInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
	    reqVo.put("i_iStartRownum","1"); 
	    reqVo.put("i_iEndRownum","5");
	    reqVo.put("i_sStDate","");
	    reqVo.put("i_sEnDate","");
		return cmDao.getList("NbNb030Mapper.selectNbNb030List", reqVo);
	}
	/**
	 * 모든 북마크 내용
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public List<CmMap> getBookMarkList(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		reqVo.put("i_sBookMakrYn", "Y");
		return this.cmDao.getList("CommonDao.getBookMarkList", reqVo);
	}
	/**
	 * 북마크의 상위내용
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public List<CmMap> getBookMarkTopList(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		reqVo.put("i_sBookMakrYn", "Y");
		return this.cmDao.getList("Menu.selectMenuList", reqVo);
	}
	/**
	 * 유저가 저장한 북마크
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public List<CmMap> getUserBookmarkList(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return this.cmDao.getList("CommonDao.getUserBookmarkList", reqVo);
	}
	/**
	 * 북마크 저장
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public void insertBookMark(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		this.cmDao.insert("CommonDao.insertBookMark", reqVo);
	}
	/**
	 * 북마크 삭제
	 * @param reqVo
	 * @return
	 * @throws Exception
	 */
	public void deleteBookMark(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		 this.cmDao.delete("CommonDao.deleteBookMark", reqVo);
	}

	/**
	 * ODM 유저 리스트 조회
	 * @param reqVo
	 */
	public CmMap getVendorUserInfo(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub		
		 return this.decsCripto(this.cmDao.getObject("CommonDao.getOdmUserList", reqVo));
	}
	public CmMap getUserInfo(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		 return this.cmDao.getObject("CommonDao.getUserList", reqVo);
	}

	public List<CmMap> getMailLogList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getList("CommonDao.selectMailLogList", reqVo);
	}	

}
