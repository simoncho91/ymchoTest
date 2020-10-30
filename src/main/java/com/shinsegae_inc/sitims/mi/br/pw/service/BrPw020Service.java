package com.shinsegae_inc.sitims.mi.br.pw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;

@Service("sitims.BrPw020Service")
@SuppressWarnings("rawtypes")
public class BrPw020Service extends CmService{
	//@Resource(name="CommonIfSivanMapper")
	//private transient CommonIfSivanMapper commonIfSivanMapper;

	public List<CmMap> selectBrPw020List(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int recordCnt = cmDao.getCount("BrPr010Mapper.getProdCount",reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("BrPr010Mapper.selectBrPr010List", reqVo);
	}

	public List<CmMap> selectPartnoList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPw020Mapper.selectPartNoList", reqVo);
	}

	public List<CmMap> selectOdmConSingleList(CmMap reqVo) throws Exception {
		return this.cmDao.getList("BrPw020Mapper.selectOdmConSingleList", reqVo);
	}

	public List<CmMap> selectConpFunctionList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectConpFunctionList",reqVo);
	}

	public List<CmMap> selectOdmClaimEffectList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmClaimEffectList", reqVo);
	}
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateCaNo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		this.cmDao.update("BrPw020Mapper.updateCaNo", reqVo);		
		//this.cmOdmDao.update("If_BrPw020Mapper.updateCaNo", reqVo);		
	}
	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateRawFunction(CmMap reqVo) throws Exception {
		String partno = reqVo.getString("i_sPartNo");
		
		if("COMPLEX".equals(reqVo.getString("i_sDivision"))){
			String[] arrRawcd = reqVo.getStringArray("i_arrModifyChk");
			int cnt = arrRawcd.length == 0? 0:arrRawcd.length;
			
			for(int i=0; i<cnt; i++){
				reqVo.put("i_sRawcd", arrRawcd[i]);
				reqVo.put("i_sModiFuncNm", reqVo.getString("i_sModiFuncNm_"+arrRawcd[i]+"_"+partno));
				reqVo.put("i_sBeforeFuncNm", reqVo.getString("i_sBeforeFuncNm_"+arrRawcd[i]+"_"+partno));
				reqVo.put("i_sModiFuncNm_etc", reqVo.getString("i_sModiFuncNm_etc_"+arrRawcd[i]+"_"+partno));
				
				this.cmDao.update("BrPw020Mapper.updateRawFunction", reqVo);
				//this.cmOdmDao.update("If_BrPw020Mapper.updateRawFunction", reqVo);
				this.cmDao.insert("BrPw020Mapper.insertModiRawFuncLog", reqVo);
				//this.cmOdmDao.insert("If_BrPw020Mapper.insertModiRawFuncLog", reqVo);
			}
		}else{
			String[]arrConcd = reqVo.getStringArray("i_arrModifyChk");
			if(arrConcd != null) {
				int cnt = arrConcd.length == 0? 0:arrConcd.length;
				
				for(int i=0; i<cnt; i++){
					reqVo.put("i_sConcd", arrConcd[i]);
					reqVo.put("i_sModiFuncNm", reqVo.getString("i_sModiFuncNm_"+arrConcd[i]+"_"+partno));
					reqVo.put("i_sBeforeFuncNm", reqVo.getString("i_sBeforeFuncNm_"+arrConcd[i]+"_"+partno));
					reqVo.put("i_sModiFuncNm_etc", reqVo.getString("i_sModiFuncNm_etc_"+arrConcd[i]+"_"+partno));
					
					this.cmDao.update("BrPw020Mapper.updateConcdFunction", reqVo);
					//this.cmOdmDao.update("If_BrPw020Mapper.updateConcdFunction", reqVo);
					this.cmDao.insert("BrPw020Mapper.insertModiRawFuncLog", reqVo);
					//this.cmOdmDao.insert("If_BrPw020Mapper.insertModiRawFuncLog", reqVo);
				}				
			}
		}
	}

	public List<CmMap> selectOdmPifViewForChina(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmPifViewForChina", reqVo);
	}

	public List<CmMap> selectRawFunctionList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectRawFunctionList",reqVo);
	}

	public List<CmMap> selectIngrtCheckList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectIngrtCheckList", reqVo);
	}

	public List<CmMap> selectIngrtCheckSubList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectIngrtCheckSubList", reqVo);
	}

	public CmMap selectProcessMstInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("BrPw020Mapper.selectProcessMstInfo",reqVo);
	}

	public List<CmMap> getSavedConListForPif(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.getSavedConListForPif",reqVo);
	}

	public List<CmMap> selectProcessFolderInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectProcessFolderInfo",reqVo);
	}

	public Object getIngrtList2(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.getIngrtList2", reqVo);
	}

	public Object getIngrtMartmemo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("BrPw020Mapper.getIngrtMartmemo", reqVo);
	}

	public Object selectAllergenRawList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectAllergenRawList", reqVo);
	}

	public List<CmMap> selectClaimEffectList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectClaimEffectList", reqVo);
	}

	public List<CmMap> selectClaimSupportList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectClaimSupportList", reqVo);
	}

	public List<CmMap> selectClaimReportNo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectClaimReportNo", reqVo);
	}

	public List<CmMap> selectSpecList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectSpecList", reqVo);
	}

	public String selectPartNoCount(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getString("BrPw020Mapper.selectPartNoCount", reqVo);
	}

	public List<CmMap> selectProductStabilityInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectProductStabilityInfo", reqVo);
	}

	public List<CmMap> selectGlobalOpnInfoList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("PifOdmTdd.selectGlobalOpnInfo", reqVo);
	}

	public String selectExpCompleteDocList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getString("PifOdmTdd.selectExpCompleteDocList", reqVo);
	}

	public List<CmMap> selectGlobalOpnList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("PifOdmTdd.selectGlobalOpnList", reqVo);
	}

	public CmMap selectAttachProductInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("OdmInciDao.selectAttachProductInfo", reqVo);
	}

	public Object selectOdmReceiveFile(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("PifOdmTdd.selectOdmReceiveFile", reqVo);
	}

	public String getPifAuthFlag(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		CmMap	authVo = this.cmDao.getObject("PifOdm.getOdmTddAuth", reqVo);
		
		if(authVo == null){
			authVo = new CmMap();
		}
		return authVo.getString("v_flag");
	}

	public List<CmMap> selectPifDocumentList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("PifOdmTdd.selectPifDocumentList", reqVo);
	}

	public Object selectGlobalRecordid(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getString("BrPw020Mapper.selectGlobalRecordid", reqVo);
	}

	public List<CmMap> getNationReviewList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.getOdmNationList", reqVo);
	}

	public List<CmMap> getNationMessage(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.getNationMessage", reqVo);
	}

	public List<CmMap> selectProcessPartInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectProcessPartInfo", reqVo);
	}

	public CmMap selectActiveIngrtListCountInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("PifOdmTdd.selectActiveIngrtListCountInfo", reqVo);
	}
	
	public CmMap selectIngredient(CmMap reqVo) throws Exception {
		return this.cmDao.getObject("BrPw020Mapper.selectIngredient", reqVo);
	}

	public List<CmMap> selectOdmPifViewForKorea(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmPifViewForKorea", reqVo);
	}

	public List<CmMap> selectOdmPifViewForJapan(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectOdmPifViewForJapan", reqVo);
	}

	public List<CmMap> selectFragranceList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectFragranceList", reqVo);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void saveBrPw020Review(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
    	String sReviewSt = reqVo.getString("i_sReveiwStatus");
    	
    	if(CmFunction.isNotEmpty(sReviewSt) && !"MODI_REQ".equals(sReviewSt)) {
    		this.cmDao.update("BrPw020Mapper.updateReview", reqVo);
    		//this.cmOdmDao.update("If_BrPw020Mapper.updateReview", reqVo);   

    		CmMap finalRstVo = getFinalRst(reqVo);
    		if("RS090".equals(sReviewSt)) {
    			reqVo.put("i_sFinalRst",sReviewSt);
    			this.cmDao.update("BrPw020Mapper.updateProdFinalRst", reqVo);
    			//this.cmOdmDao.update("If_BrPw020Mapper.updateProdFinalRst", reqVo);    			
    		}else if(finalRstVo != null) {
    			String finalRst = finalRstVo.getString("v_final_rst");
    			reqVo.put("i_sFinalRst", finalRst);
    			if("RS050".equals(finalRst) && "KO".equals(reqVo.getString("i_sNationCd"))) {
    				reqVo.put("i_sCkDomesticYn", "Y");				
    			}else if("RS060".equals(finalRst)){
    				reqVo.put("i_sCkOverseasYn", "Y");
    			}
    			this.cmDao.update("BrPw020Mapper.updateProdReveiwCompl", reqVo);
    			//this.cmOdmDao.update("If_BrPw020Mapper.updateProdReveiwCompl", reqVo);
    		}
			this.cmDao.update("BrPw020Mapper.updateProdIfYn", reqVo);
    	}else if("MODI_REQ".equals(sReviewSt)) {
    		if(this.cmDao.update("BrPw020Mapper.updateProdMyRqM", reqVo)<=0) {
    			this.cmDao.insert("BrPw020Mapper.insertProdMyRqM", reqVo);
    		}
    		reqVo.put("i_sModyReqId", this.cmDao.getString("BrPw020Mapper.getModyReqCd", reqVo));
    		
    		//if(this.cmOdmDao.update("If_BrPw020Mapper.updateProdMyRqM", reqVo)<=0) {
    		//	this.cmOdmDao.insert("If_BrPw020Mapper.insertProdMyRqM", reqVo);
    		//}
			this.cmDao.delete("BrPw020Mapper.deleteProdMyRqD", reqVo);
			//this.cmOdmDao.delete("If_BrPw020Mapper.deleteProdMyRqD", reqVo);
			for(String str : reqVo.getStringArray("i_arrReqModi[]")) {
				reqVo.put("i_sReqItemCd", str);
				this.cmDao.insert("BrPw020Mapper.insertProdMyRqD", reqVo);				
				//this.cmOdmDao.insert("If_BrPw020Mapper.insertProdMyRqD", reqVo);				
			}
    	}
		this.cmDao.insert("BrPw020Mapper.insertReviewMessage", reqVo);
		//this.cmOdmDao.insert("If_BrPw020Mapper.insertReviewMessage", reqVo);
		reqVo.put("prodMyRqList", this.cmDao.getList("BrPw020Mapper.selectProdMyRqDetail", reqVo));
	}

	public CmMap getFinalRst(CmMap reqVo) {
		return this.cmDao.getObject("BrPw020Mapper.getFinalRst",reqVo);
	}

	public CmMap getBrPr020Info(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getObject("BrPw020Mapper.getBrPr020Info", reqVo);
	}
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateProdFinalRst(CmMap reqVo) {
		// TODO Auto-generated method stub
		this.cmDao.update("BrPw020Mapper.updateProdFinalRst", reqVo);
		//this.cmOdmDao.update("If_BrPw020Mapper.updateProdFinalRst", reqVo);
	}

	public List<CmMap> selectRawcd(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectRawcd", reqVo);
	}

	public List<CmMap> getOdmBomList(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.getOdmBomList", reqVo);
	}

	public List<CmMap> getProdMyRq(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectProdMyRq", reqVo);
	}

	public Object getRaIds(CmMap reqVo) {
		// TODO Auto-generated method stub
		return this.cmDao.getList("AmUm030Mapper.getRaIds", reqVo);
	}
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void saveChinaReview(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		this.cmDao.update("BrPw020Mapper.updateChinaOpReview", reqVo);
//		this.cmOdmDao.update("If_BrPw020Mapper.updateChinaOpReview", reqVo);
		this.insertFileList("CNR", reqVo);
//		this.insertFileListOdm("CNR", reqVo);
	}

	public List<CmMap> selectModiRawFuncLog(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.cmDao.getList("BrPw020Mapper.selectModiRawFuncLog", reqVo);
	}

	public List<CmMap> selectReqModiList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPw020Mapper.selectModiReqList",reqVo);
	}

	public List<CmMap> selectOdmRequireFileAndLimitConList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPw020Mapper.selectOdmRequireFileAndLimitConList",reqVo);
	}

	public CmMap getBrPr012Info(CmMap reqVo) {
		// TODO Auto-generated method stub
		//return cmDao.getObject("BrPr010Mapper.getBrPr010ProdList", reqVo);
		return this.decsCripto(cmDao.getObject("BrPr010Mapper.getBrPr010ProdList", reqVo));
	}

	public CmMap selectCompanyInfo(CmMap reqVo) {
		// TODO Auto-generated method stub		
		return this.decsCripto(cmDao.getObject("BrPr012Mapper.selectCompanyInfo", reqVo));
	}

	public void coDocSpecExcel(CmMap reqVo, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
		
		CmMap rvo = this.getBrPr012Info(reqVo);		
		reqVo.put("i_sCompanyCd", rvo.getString("v_vendor_id"));
		reqVo.put("i_sSignDate", CmFunction.getSignDate());
		CmMap companyInfo = this.selectCompanyInfo(reqVo);
		if(companyInfo != null) {
			if(CmFunction.isEmpty(companyInfo.getString("v_logo_attachid"))){
				reqVo.put("i_sLogo_flag", "N");
			}
			if(CmFunction.isEmpty(companyInfo.getString("v_sign_attachid"))){
				reqVo.put("i_sSign_flag", "N");
			}
		} else {
			companyInfo = new CmMap();
			reqVo.put("i_sLogo_flag", "N");
			reqVo.put("i_sSign_flag", "N");
		}

		List<CmMap> specList = this.selectSpecList(reqVo);
		
		if(rvo != null){
			reqVo.put("rvo", rvo);
			reqVo.put("companyVo", companyInfo);
			reqVo.put("sSpecList", specList);
		}
		this.setAttachFilePath(companyInfo.getString("v_sign_attachid"),"vSignFile",reqVo);
		this.setAttachFilePath(companyInfo.getString("v_logo_attachid"),"vLogoFile",reqVo);
		
	}

	private void setAttachFilePath(String attachId, String setNm, CmMap reqVo) throws Exception {
		if (CmFunction.isNotEmpty(attachId)) {
			CmMap tempVo = new CmMap();
			tempVo.put("i_sAttachId", attachId);
			CmMap attachInfo = commonService.getAttachInfo(tempVo);
			//String filePath = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_upload_id") + "/"+attachInfo.getString("v_attach_id")+"."+attachInfo.getString("v_attach_ext").toLowerCase();
			String filePath = CmPathInfo.getUPLOAD_FILE_PATH() + attachInfo.getString("v_attach_path")+attachInfo.getString("v_attach_id")+attachInfo.getString("v_attach_ext");
			reqVo.put(setNm, filePath);
		}
	}

	public void updateDeleteProduct(CmMap reqVo) {
		// TODO Auto-generated method stub
		cmDao.update("BrPw020Mapper.updateDeleteProduct",reqVo);
	}

	public void saveMessage(CmMap reqVo) {
		// TODO Auto-generated method stub
		cmDao.update("BrPw020Mapper.insertProductMessage",reqVo);
		
	}
	
	//@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	//public void sapProcess(CmMap reqVo) throws Exception{
	//	// TODO Auto-generated method stub
	//	//this.sapProcessifSivan(reqVo);
	//	cmDao.update("BrPw020Mapper.updateIfSivanTrans",reqVo);
	//	//cmOdmDao.update("If_BrPw020Mapper.updateIfSivanTrans",reqVo);
	//}
	//@Transactional(value = "ifSivanTransactionManager", rollbackFor = Exception.class)
	//public void sapProcessifSivan(CmMap reqVo) throws Exception{
	//	// TODO Auto-generated method stub
	//	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd",Locale.KOREAN);
	//	reqVo.put("i_sErDat",formatter.format(new Date()));
	//	commonIfSivanMapper.insert("IfShivanMapper.insertIFSI016", reqVo);
	//	commonIfSivanMapper.insert("IfShivanMapper.insertIFSYNC", reqVo);
	//	
	//}

}
