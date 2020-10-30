package com.shinsegae_inc.sitims.mi.br.pr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service("sitims.BrPr010Service")
@SuppressWarnings("rawtypes")
public class BrPr010Service extends CmService{

	public List<CmMap> getProdPopList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int recordCnt = cmDao.getCount("BrPr010Mapper.getProdPopCount",reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("BrPr010Mapper.getProdPopList", reqVo);
	}

	public List<CmMap> selectBrPr010List(CmMap reqVo) throws Exception{
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
		
		int recordCnt = cmDao.getCount("BrPr010Mapper.getProdCount",reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("BrPr010Mapper.selectBrPr010List", reqVo);
	}

	public CmMap getBrPr010Info(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.decsCripto(cmDao.getObject("BrPr010Mapper.getBrPr010Info", reqVo));
	}


	public List<CmMap> getBrPr010ProdList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return this.decsCripto(cmDao.getList("BrPr010Mapper.getBrPr010ProdList", reqVo));
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void insertBrPr010(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		String recordId = cmDao.getString("BrPr010Mapper.getBrPr010RecordId", reqVo);
		reqVo.put("i_sRecordId", recordId);
		cmDao.insert("BrPr010Mapper.insertBrPr010Mst", reqVo);

		this.insertFileList("PON",reqVo);
		//if("RS010".equals(reqVo.getString("i_sReceipStatus"))) {
		//	cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010Mst", reqVo);
		//	this.insertFileListOdm("PON",reqVo);			
		//}
		this.insertBrPr010Prod(reqVo);
	}

	@SuppressWarnings("PMD.CyclomaticComplexity")
	private void insertBrPr010Prod(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		String receiptSt = reqVo.getString("i_sReceipStatus");
		String[] arrBrand = reqVo.getStringArray("i_arrBrand");
		String[] arrProductRefNm = reqVo.getStringArray("i_arrProduct_RefNm");
		String[] arrProductRefNmEn = reqVo.getStringArray("i_arrProduct_RefNm_En");
		String[] arrProductRefNmCn = reqVo.getStringArray("i_arrProduct_RefNm_Cn");
		String[] arrProductRefcd = reqVo.getStringArray("i_arrProduct_Refcd");

		String[] arrCNnum = reqVo.getStringArray("i_arrCNnum");		
		String[] arrGiReferRecordId = reqVo.getStringArray("i_arrGiReferRecordId");		
		String[] arrGiReferProductCd = reqVo.getStringArray("i_arrGiReferProductCd");		
		String[] arrStockDt = reqVo.getStringArray("i_arrStock_dt");
		String[] arrType = reqVo.getStringArray("i_arrType");		
		String[] arrCategory1 = reqVo.getStringArray("i_arrCategory1");		
		String[] arrCategory2 = reqVo.getStringArray("i_arrCategory2");		
		
		String[] arrProductCapacity = reqVo.getStringArray("i_arrProductCapacity");		
		String[] arrCntrForm = reqVo.getStringArray("i_arrCntrForm");		
		String[] arrCntrMatr = reqVo.getStringArray("i_arrCntrMatr");		
				
		String[] arrPacketUnit = reqVo.getStringArray("i_arrPacketUnit");
		String[] arrCntrFormEtc = reqVo.getStringArray("i_arrCntrForm_etc");
		String[] arrCntrMatrEtc = reqVo.getStringArray("i_arrCntrMatr_etc");

		String[] arrPao = reqVo.getStringArray("i_arrPao");
		String[] arrLife = reqVo.getStringArray("i_arrLife");
		String[] arrPonMsg = reqVo.getStringArray("i_arrPonMsg");

		if(arrProductRefcd != null && arrProductRefcd.length > 0 && CmFunction.isNotEmpty(arrProductRefcd[0]) ) {
			for(int i=0;i<arrProductRefcd.length;i++) {
				String arrFuncyn = reqVo.getStringArray("i_arrFuncyn_"+i)!=null && reqVo.getStringArray("i_arrFuncyn_"+i).length>0 ? reqVo.getStringArray("i_arrFuncyn_"+i)[0]:"";
				String arrKidYn = reqVo.getStringArray("i_arrKidYn_"+i)!=null && reqVo.getStringArray("i_arrKidYn_"+i).length>0 ? reqVo.getStringArray("i_arrKidYn_"+i)[0]:"";
				String arrStabilityFileYn = reqVo.getStringArray("i_arrStabilityFileYn_"+i)!=null && reqVo.getStringArray("i_arrStabilityFileYn_"+i).length>0 ? reqVo.getStringArray("i_arrStabilityFileYn_"+i)[0]:"";				
				String arrOriginYn = reqVo.getStringArray("i_arrOriginYn_"+i)!=null && reqVo.getStringArray("i_arrOriginYn_"+i).length>0 ? reqVo.getStringArray("i_arrOriginYn_"+i)[0]:"";
				String arrOriginDiv = reqVo.getStringArray("i_arrOriginDiv_"+i)!=null && reqVo.getStringArray("i_arrOriginDiv_"+i).length>0 ? reqVo.getStringArray("i_arrOriginDiv_"+i)[0]:"";
				String arrLeaveonYn = reqVo.getStringArray("i_arrLeaveonYn_"+i)!=null && reqVo.getStringArray("i_arrLeaveonYn_"+i).length>0 ? reqVo.getStringArray("i_arrLeaveonYn_"+i)[0]:"";

				CmMap rVo = new CmMap();
				rVo.put("i_sLoginUserId", reqVo.getString("i_sLoginUserId"));
				rVo.put("i_sRecordId", reqVo.getString("i_sRecordId"));
				rVo.put("i_sProduct_Refcd", arrProductRefcd[i]);
				rVo.put("i_sProduct_RefNm", arrProductRefNm[i]);
				rVo.put("i_sProduct_RefNm_En", arrProductRefNmEn[i]);
				rVo.put("i_sProduct_RefNm_Cn", arrProductRefNmCn[i]);
				rVo.put("i_sBrand", arrBrand[i]);
				rVo.put("i_sStockDt", arrStockDt != null?arrStockDt[i]:null);
				rVo.put("i_sLeaveonYn", arrLeaveonYn);
				rVo.put("i_sType", arrType != null?arrType[i]:null);
				rVo.put("i_sCategory1", arrCategory1 != null?arrCategory1[i]:null);
				rVo.put("i_sCategory2", arrCategory2 != null?arrCategory2[i]:null);
				rVo.put("i_sFuncyn", arrFuncyn);
				rVo.put("i_sCntrFormEtc", arrCntrFormEtc != null?arrCntrFormEtc[i]:null);
				rVo.put("i_sCntrMatrEtc", arrCntrMatrEtc != null?arrCntrMatrEtc[i]:null);
				rVo.put("i_sProductCapacity", arrProductCapacity != null?arrProductCapacity[i]:null);
				rVo.put("i_sCntrForm", arrCntrForm != null?arrCntrForm[i]:null);
				rVo.put("i_sCntrMatr", arrCntrMatr != null?arrCntrMatr[i]:null);

				rVo.put("i_sOriginYn", arrOriginYn);				
				
				rVo.put("i_sKidYn", arrKidYn);
				rVo.put("i_sPacketUnit", arrPacketUnit != null?arrPacketUnit[i]:null);
				rVo.put("i_sStabilityFileYn", arrStabilityFileYn);

				rVo.put("i_sCNnum", arrCNnum != null?arrCNnum[i]:null);
				if(CmFunction.isNotEmpty(arrGiReferProductCd[i])) {
					rVo.put("i_sGiReferRecordId", arrGiReferRecordId != null?arrGiReferRecordId[i]:null);
					rVo.put("i_sGiReferProductCd", arrGiReferProductCd != null?arrGiReferProductCd[i]:null);
				}
				rVo.put("i_sPao", arrPao != null?arrPao[i]:null);
				rVo.put("i_sLife", arrLife != null?arrLife[i]:null);
				rVo.put("i_sPonMsg", arrPonMsg != null?arrPonMsg[i]:null);
				
				String productCd= arrProductRefcd[i];
				
				if("N".equals(arrOriginYn)){
					String originDiv = arrOriginDiv ;					
					if(CmFunction.isNotEmpty(originDiv)){	
						rVo.put("i_sOriginDiv", originDiv);						
					}
				}
				
				String[] arrSogugn = reqVo.getStringArray("i_arrSogugn_"+i);
				StringBuffer strSogugn = new StringBuffer();
				//String strSogugn = "";
				
				if(arrSogugn != null && arrSogugn.length > 0 ){
					
					for(String str : arrSogugn){
						if(CmFunction.isNotEmpty(strSogugn.toString())){
							//strSogugn += "/";
							strSogugn.append('/');
						}
						//strSogugn += str;
						strSogugn.append(str);
						if("E".equals(str)){
							rVo.put("i_sMusogucont", reqVo.getString("i_sMusogucont_"+i));
						}
						else if("D".equals(str)){
							rVo.put("i_sSogucont", reqVo.getString("i_sSogucont_"+i));
						}
					}
					
					if(CmFunction.isNotEmpty(strSogugn.toString())){
						rVo.put("i_sSogugn", strSogugn.toString());
					}
					
				}
				
				cmDao.insert("BrPr010Mapper.insertBrPr010Prod", rVo);
				//if("RS010".equals(receiptSt)) {
				//	cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010Prod", rVo);
				//}
				String[] iArrExp = reqVo.getStringArray("i_arrExp_"+i);
				
				if(iArrExp != null && iArrExp.length > 0) {
					for(String str : iArrExp) {
						rVo.put("i_sExp", str);
						rVo.put("i_sProductCd", productCd);
						rVo.put("i_sBrandCd", rVo.get("i_sBrand"));
						CmMap cm = cmDao.getObject("BrPr010Mapper.getBrPr010RaNationUser", rVo);
						if(cm != null) {
							rVo.put("i_sRaUser",cm.get("v_user_id"));
						}else {
							rVo.put("i_sRaUser",null);
						}
						cmDao.insert("BrPr010Mapper.insertBrPr010ProdNation", rVo);
						//if("RS010".equals(receiptSt)) {
						//	cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010ProdNation", rVo);
						//}						
					}
				}
				//제품 등록시 default 한국 추가
				rVo.put("i_sExp", "KO");
				rVo.put("i_sProductCd", productCd);
				rVo.put("i_sBrandCd", rVo.get("i_sBrand"));
				CmMap cm = cmDao.getObject("BrPr010Mapper.getBrPr010RaNationUser", rVo);
				if(cm != null) {
					rVo.put("i_sRaUser",cm.get("v_user_id"));
				}else {
					rVo.put("i_sRaUser",null);
				}
				cmDao.insert("BrPr010Mapper.insertBrPr010ProdNation", rVo);
				//if("RS010".equals(receiptSt)) {
				//	cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010ProdNation", rVo);
				//}

				// 기능성 Y 일경우 처리
				if("Y".equals(arrFuncyn)){
					String name = "i_sFuncCode_"+i;
					rVo.put("i_sProductCd", productCd);
					String[] arrFuncTag = reqVo.getStringArray(name);					
					if(arrFuncTag != null && arrFuncTag.length > 0){
						for(String str : arrFuncTag){
							rVo.put("i_sFuncCode", str);
							this.cmDao.insert("BrPr010Mapper.insertBrPr010ProdFunc", rVo);
							//if("RS010".equals(receiptSt)) {
							//	cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010ProdFunc", rVo);
							//}							
						}
					}
				}
			}
		}
		
	}
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateBrPr010(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		cmDao.update("BrPr010Mapper.updateBrPr010Mst", reqVo);
		
		cmDao.delete("BrPr010Mapper.deleteBrPr010Func", reqVo);
		cmDao.delete("BrPr010Mapper.deleteBrPr010Nation", reqVo);
		cmDao.delete("BrPr010Mapper.deleteBrPr010Prod", reqVo);

		this.insertFileList("PON",reqVo);
		//if("RS010".equals(reqVo.getString("i_sReceipStatus"))) {
		//	//if(cmOdmDao.update("If_BrPr010Mapper.updateBrPr010Mst", reqVo)<1) {
		//	//	cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010Mst", reqVo);
		//	//}
		//		
		//	cmOdmDao.delete("If_BrPr010Mapper.deleteBrPr010Func", reqVo);
		//	cmOdmDao.delete("If_BrPr010Mapper.deleteBrPr010Nation", reqVo);
		//	cmOdmDao.delete("If_BrPr010Mapper.deleteBrPr010Prod", reqVo);
		//	this.insertFileListOdm("PON",reqVo);			
		//}
		this.insertBrPr010Prod(reqVo);
		
	}
	
	@SuppressWarnings("PMD.CyclomaticComplexity")
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateBrPr010PermitReqStatus(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		
		String receiptSt = reqVo.getString("i_sReceipStatus");
		cmDao.update("BrPr010Mapper.updateBrPr010Mst", reqVo); // 제품담당자
		//cmDao.delete("BrPr010Mapper.deleteBrPr010ProductFunc", reqVo); //기능성 삭제
		//if("RS010".equals(receiptSt)) {
		//	cmOdmDao.update("If_BrPr010Mapper.updateBrPr010Mst", reqVo);
		//	cmOdmDao.delete("If_BrPr010Mapper.deleteBrPr010ProductFunc", reqVo); //기능성 삭제			
		//}
		
		String[] sogugnStr = reqVo.getStringArray("i_sSogugn");

		StringBuffer strSogugn = new StringBuffer();
		if(sogugnStr != null && sogugnStr.length > 0 ){
			for(String tmpStr : sogugnStr) {
				if(CmFunction.isNotEmpty(strSogugn.toString())){
					//strSogugn += "/";
					strSogugn.append('/');
				}				
				//strSogugn += tmpStr;
				strSogugn.append(tmpStr);
			}
			reqVo.put("i_sSogugn", strSogugn.toString());
			
		}
		String[] expCodeStr = reqVo.getStringArray("i_sExpCode");	
		if( "N".equals(reqVo.getString("i_sDisabled")) ){
				// 모든 국가 삭제 (한국, 상태값이 있는  국가 제외)후 등록
				reqVo.put("i_sExistReviewStatue", "Y");
				cmDao.delete("BrPr010Mapper.deleteBrPr010Nation", reqVo);
				//cmOdmDao.delete("If_BrPr010Mapper.deleteBrPr010Nation", reqVo);	
		}
		if(expCodeStr != null && expCodeStr.length > 0) {
			for(String tmpStr : expCodeStr) {
				reqVo.put("i_sExp",tmpStr);
				CmMap cm = cmDao.getObject("BrPr010Mapper.getBrPr010RaNationUser", reqVo);
				if(cm != null) {
					reqVo.put("i_sRaUser",cm.get("v_user_id"));
				}else {
					reqVo.put("i_sRaUser",null);
				}
				cmDao.insert("BrPr010Mapper.insertBrPr010ProdNation", reqVo);
				//cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010ProdNation", reqVo);
				
			}
		}
		 cmDao.update("BrPr010Mapper.updateBrPr010Prod", reqVo); 
		 //if("RS010".equals(receiptSt)) {
		 //	cmOdmDao.update("If_BrPr010Mapper.updateBrPr010Prod", reqVo);
		 //}
		
		/*
		 * if("Y".equals(reqVo.getString("i_sFuncyn"))) { String[] funcCodeStr =
		 * reqVo.getStringArray("i_sFuncCd"); if(funcCodeStr != null &&
		 * funcCodeStr.length > 0 ){ for(String tmpStr : funcCodeStr) {
		 * reqVo.put("i_sFuncCode",tmpStr);
		 * cmDao.insert("BrPr010Mapper.insertBrPr010ProdFunc", reqVo);
		 * //if("RS010".equals(receiptSt)) { //
		 * cmOdmDao.insert("If_BrPr010Mapper.insertBrPr010ProdFunc", reqVo); //} } } }
		 */
		
		this.insertFileList("PON",reqVo);
		//if("RS010".equals(receiptSt)) {
		//	this.insertFileListOdm("PON",reqVo);
		//}
		
	}
	/*
	 * public int BrPr010PermitReqNationChk(CmMap reqVo) throws Exception{ int
	 * result = 0; String sChkVal = reqVo.getString("i_sChkVal");
	 * if(CmFunction.isEmpty(sChkVal)) { throw new SwafException("필수값이 없습니다."); }
	 * CmMap tmpCm = cmDao.getObject("BrPr010Mapper.getProdNationCount",reqVo);
	 * if(tmpCm.getInt("CNT") >0) { //상태값 존재 result = 1; } return result; }
	 */

	public List<CmMap> getBrPr010EmailInfo(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("BrPr010Mapper.getBrPr010EmailInfo", reqVo);
	}

	public CmMap getBrandCd(CmMap reqVo) throws Exception {
    	int	recordCnt	= cmDao.getCount("AmAm030Mapper.getAmAm030ListCount", reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		List<CmMap> list = cmDao.getList("AmAm030Mapper.selectAmAm030List",reqVo);
		return list.size()>0?list.get(0):new CmMap();
	}

	public void updateBrPr010BmId(CmMap reqVo) {
		// TODO Auto-generated method stub
		String[] arrRecordId = reqVo.getStringArray("i_arrRecordId");
		for(String str : arrRecordId) {
			reqVo.put("i_sRecordId", str);
			cmDao.update("BrPr010Mapper.updateBrPr010BmId", reqVo);			
		}
	}
}
