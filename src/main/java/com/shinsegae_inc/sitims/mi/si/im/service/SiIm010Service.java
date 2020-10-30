package com.shinsegae_inc.sitims.mi.si.im.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service
@SuppressWarnings("rawtypes")
public class SiIm010Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분DB관리 목록 조회
	 * </pre>
	 */
	public List<CmMap> selectSiIm010List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getSiIm010ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("SiIm010Mapper.selectSiIm010List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분DB관리 목록 Count 조회
	 * </pre>
	 */
	public int getSiIm010ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("SiIm010Mapper.getSiIm010ListCount", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분DB관리 상세 정보 조회
	 * </pre>
	 */
	public CmMap selectSiim010Info(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getObject("SiIm010Mapper.selectSiim010Info", reqVo) ;
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분DB관리 CasNo 리스트 조회
	 * </pre>
	 */
	public List<CmMap> selectSiim010CasNo(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("SiIm010Mapper.selectSiim010CasNo", reqVo) ;
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분DB관리 금지/제한 리스트 조회
	 * </pre>
	 */
	public CmMap selectSiim010BanDesc(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getObject("SiIm010Mapper.selectSiim010BanDesc", reqVo) ;
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분DB관리 기능 목록 조회
	 * </pre>
	 */
	public List<CmMap> selectSiim010Func(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("SiIm010Mapper.selectSiim010Func", reqVo) ;
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분DB ODM팝업 리스트 조회
	 * </pre>
	 */
	public List<CmMap> selectConList(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.selectConListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("SiIm010Mapper.selectConList", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분DB ODM팝업리스트 카운트 조회
	 * </pre>
	 */
	public int selectConListCount(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getCount("SiIm010Mapper.selectConListCount", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 성분DB 데이터 저장 update/insert
	 * </pre>
	 */
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@SuppressWarnings({"rawtypes", "PMD.CyclomaticComplexity"})
	public void updateConCdInfo(CmMap reqVo) throws Exception{
		String[] arrFlagBanB =reqVo.getStringArray("i_arrFlagBanB");
		String[] arrFlagBanL =reqVo.getStringArray("i_arrFlagBanL");
		String[] arrCasNo =reqVo.getStringArray("i_arrCasNo");
		String[] arrFuncCdEn =reqVo.getStringArray("i_arrFuncCdEn");
		String[] arrFuncCdKo =reqVo.getStringArray("i_arrFuncCdKo");
		String[] arrFuncCdCn =reqVo.getStringArray("i_arrFuncCdCn");
		String confirmYn = reqVo.getString("i_sConfirmYn");
		
		if(CmFunction.isEmpty(reqVo.getString("i_sConCd")) || CmFunction.isEmpty(reqVo.getString("i_nVerSeq"))) {
			int nConNum= 0;
			while(CmFunction.isEmpty(reqVo.getString("i_sConCd"))) {
				reqVo.put("i_nConNum", nConNum);
				String conCd= cmDao.getString("SiIm010Mapper.getMaxConCd", reqVo) ;
				reqVo.put("i_sConCd", conCd);
				int cnt = cmDao.getCount("SiIm010Mapper.getSiIm010ListCount", reqVo);
				if(cnt==0) break;
				else nConNum++;
			}
			if(CmFunction.isEmpty(reqVo.getString("i_nVerSeq"))) {
				String verSeq = cmDao.getString("SiIm010Mapper.getMaxVerSeq", reqVo) ;
				reqVo.put("i_nVerSeq", verSeq);				
			}
			cmDao.insert("SiIm010Mapper.insertSiim010Info", reqVo) ;
			//if("Y".equals(confirmYn)) {	
				if("Y".equals(confirmYn) && !reqVo.getString("i_sReqConId").isEmpty()) {			
					cmDao.update("SiIm010Mapper.updateOdmConcdReqRegCompl", reqVo) ;			
				}else if("N".equals(confirmYn) && !reqVo.getString("i_sReqConId").isEmpty()) {
					// 임시저장  TODM_CONRQ_M 업데이트
					cmDao.update("SiIm010Mapper.updateOdmConcdReqRegTmpsave", reqVo);			

				}
				//cmOdmDao.update("If_SiIm010Mapper.insertSiim010Info", reqVo) ;
				//}
			
			if(arrFlagBanB != null) {
				for(int i=0;i<arrFlagBanB.length;i++) {
					String sFlagBanCode = arrFlagBanB[i];
					String sFalgBanBKoC = reqVo.getString("i_sFlagBanBKoComent");
					String sFalgBanBCnC = reqVo.getString("i_sFlagBanBCnComent");
					String sFalgBanBEuC = reqVo.getString("i_sFlagBanBEuComent");
					String sFalgBanBUsC = reqVo.getString("i_sFlagBanBUsComent");
					String sFalgBanBAeC = reqVo.getString("i_sFlagBanBAeComent");
					reqVo.put("i_sFlagBanCode", sFlagBanCode);
					reqVo.put("i_sFlagBan", "B");
					if(sFlagBanCode.equals("KO")) {
						reqVo.put("i_sCommentBan", sFalgBanBKoC);
					}else if(sFlagBanCode.equals("CN")) {
						reqVo.put("i_sCommentBan", sFalgBanBCnC);
					}else if(sFlagBanCode.equals("EU")) {
						reqVo.put("i_sCommentBan", sFalgBanBEuC);
					}else if(sFlagBanCode.equals("US")) {
						reqVo.put("i_sCommentBan", sFalgBanBUsC);
					}else if(sFlagBanCode.equals("AE")) {
						reqVo.put("i_sCommentBan", sFalgBanBAeC);						
					}
					
					cmDao.insert("SiIm010Mapper.insertSiim010Ban", reqVo) ;
					//if("Y".equals(confirmYn)) {				
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Ban", reqVo) ;
					//}
				}				
			}
			if(arrFlagBanL != null) {
				for(int i=0;i<arrFlagBanL.length;i++) {
					String sFlagBanCode = arrFlagBanL[i];
					String sFalgBanLKoC = reqVo.getString("i_sFlagBanLKoComent");
					String sFalgBanLCnC = reqVo.getString("i_sFlagBanLCnComent");
					String sFalgBanLEuC = reqVo.getString("i_sFlagBanLEuComent");
					String sFalgBanLUsC = reqVo.getString("i_sFlagBanLUsComent");
					String sFalgBanLAeC = reqVo.getString("i_sFlagBanLAeComent");
					reqVo.put("i_sFlagBanCode", sFlagBanCode);
					reqVo.put("i_sFlagBan", "L");
					if(sFlagBanCode.equals("KO")) {
						reqVo.put("i_sCommentBan", sFalgBanLKoC);
					}else if(sFlagBanCode.equals("CN")) {
						reqVo.put("i_sCommentBan", sFalgBanLCnC);
					}else if(sFlagBanCode.equals("EU")) {
						reqVo.put("i_sCommentBan", sFalgBanLEuC);
					}else if(sFlagBanCode.equals("US")) {
						reqVo.put("i_sCommentBan", sFalgBanLUsC);
					}else if(sFlagBanCode.equals("AE")) {
						reqVo.put("i_sCommentBan", sFalgBanLAeC);						
					}
					cmDao.insert("SiIm010Mapper.insertSiim010Ban", reqVo) ;			
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Ban", reqVo) ;			
					//}	
				}
			}
			
			if(arrCasNo != null) {
				for(int i=0;i<arrCasNo.length;i++) {
					reqVo.put("i_nSeqNo", i+1);
					reqVo.put("i_sCasNo", arrCasNo[i]);
					cmDao.insert("SiIm010Mapper.insertSiim010CasNo", reqVo);		
					// TODM_CONRQ_CAS_D CaNo 등록
					if("N".equals(confirmYn) && !reqVo.getString("i_sReqConId").isEmpty()) {
						cmDao.insert("SiIm010Mapper.insertOdmCasNoReqRegTmpsave", reqVo);
					}
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010CasNo", reqVo) ;			
					//}
				}
			}
			if(arrFuncCdEn != null && arrFuncCdKo != null && arrFuncCdCn != null) {
				for(int i=0;i<arrFuncCdEn.length;i++) {
					reqVo.put("i_nSeqNo", i+1);
					reqVo.put("i_sFuncCdEn", arrFuncCdEn[i]);
					reqVo.put("i_sFuncCdKo", arrFuncCdKo[i]);
					reqVo.put("i_sFuncCdCn", arrFuncCdCn[i]);
					cmDao.insert("SiIm010Mapper.insertSiim010Func", reqVo) ;		
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Func", reqVo) ;			
					//}
				}
			}
		}else {
			cmDao.update("SiIm010Mapper.updateSiim010Info", reqVo) ;
			//cmDao.update("SiIm010Mapper.updateSiim010Name", reqVo) ;
			//if("Y".equals(confirmYn)) {	
				if("Y".equals(confirmYn) && !reqVo.getString("i_sReqConId").isEmpty()) {			
					cmDao.update("SiIm010Mapper.updateOdmConcdReqRegCompl", reqVo) ;			
				}else if("N".equals(confirmYn) && !reqVo.getString("i_sReqConId").isEmpty()) {
					// 임시저장  TODM_CONRQ_M 업데이트
					cmDao.update("SiIm010Mapper.updateOdmConcdReqRegTmpsave", reqVo);

				}				
				//cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Info", reqVo) ;
			//}

			if(arrFlagBanB != null) {
				reqVo.put("i_sFlagBan", "B");
				cmDao.delete("SiIm010Mapper.deleteSiim010Ban", reqVo) ;
				for(int i=0;i<arrFlagBanB.length;i++) {
					String sFlagBanCode = arrFlagBanB[i];
					String sFalgBanBKoC = reqVo.getString("i_sFlagBanBKoComent");
					String sFalgBanBCnC = reqVo.getString("i_sFlagBanBCnComent");
					String sFalgBanBEuC = reqVo.getString("i_sFlagBanBEuComent");
					String sFalgBanBUsC = reqVo.getString("i_sFlagBanBUsComent");
					String sFalgBanBAeC = reqVo.getString("i_sFlagBanBAeComent");
					reqVo.put("i_sFlagBanCode", sFlagBanCode);
					if(sFlagBanCode.equals("KO")) {
						reqVo.put("i_sCommentBan", sFalgBanBKoC);
					}else if(sFlagBanCode.equals("CN")) {
						reqVo.put("i_sCommentBan", sFalgBanBCnC);
					}else if(sFlagBanCode.equals("EU")) {
						reqVo.put("i_sCommentBan", sFalgBanBEuC);
					}else if(sFlagBanCode.equals("US")) {
						reqVo.put("i_sCommentBan", sFalgBanBUsC);
					}else if(sFlagBanCode.equals("AE")) {
						reqVo.put("i_sCommentBan", sFalgBanBAeC);						
					}
					cmDao.insert("SiIm010Mapper.insertSiim010Ban", reqVo) ;			
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Ban", reqVo) ;			
					//}			
				}
			}
			if(arrFlagBanL != null) {
				reqVo.put("i_sFlagBan", "L");
				cmDao.delete("SiIm010Mapper.deleteSiim010Ban", reqVo) ;
				for(int i=0;i<arrFlagBanL.length;i++) {
					String sFlagBanCode = arrFlagBanL[i];
					String sFalgBanLKoC = reqVo.getString("i_sFlagBanLKoComent");
					String sFalgBanLCnC = reqVo.getString("i_sFlagBanLCnComent");
					String sFalgBanLEuC = reqVo.getString("i_sFlagBanLEuComent");
					String sFalgBanLUsC = reqVo.getString("i_sFlagBanLUsComent");
					String sFalgBanLAeC = reqVo.getString("i_sFlagBanLAeComent");
					reqVo.put("i_sFlagBanCode", sFlagBanCode);
					if(sFlagBanCode.equals("KO")) {
						reqVo.put("i_sCommentBan", sFalgBanLKoC);
					}else if(sFlagBanCode.equals("CN")) {
						reqVo.put("i_sCommentBan", sFalgBanLCnC);
					}else if(sFlagBanCode.equals("EU")) {
						reqVo.put("i_sCommentBan", sFalgBanLEuC);
					}else if(sFlagBanCode.equals("US")) {
						reqVo.put("i_sCommentBan", sFalgBanLUsC);
					}else if(sFlagBanCode.equals("AE")) {
						reqVo.put("i_sCommentBan", sFalgBanLAeC);						
					}
					cmDao.insert("SiIm010Mapper.insertSiim010Ban", reqVo) ;		
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Ban", reqVo) ;			
					//}	
				}
			}
			if(arrCasNo != null) {
				cmDao.delete("SiIm010Mapper.deleteSiim010CasNo", reqVo) ;	
				for(int i=0;i<arrCasNo.length;i++) {
					reqVo.put("i_nSeqNo", i+1);
					reqVo.put("i_sCasNo", arrCasNo[i]);
					cmDao.insert("SiIm010Mapper.insertSiim010CasNo", reqVo);
					// TODM_CONRQ_CAS_D CaNo 등록
					if("N".equals(confirmYn) && !reqVo.getString("i_sReqConId").isEmpty()) {
						cmDao.insert("SiIm010Mapper.insertOdmCasNoReqRegTmpsave", reqVo);
					}
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010CasNo", reqVo) ;			
					//}	
				}
			}
			if(arrFuncCdEn != null && arrFuncCdKo != null && arrFuncCdCn != null) {
				cmDao.delete("SiIm010Mapper.deleteSiim010Func", reqVo) ;
				for(int i=0;i<arrFuncCdEn.length;i++) {
					reqVo.put("i_nSeqNo", i+1);
					reqVo.put("i_sFuncCdEn", arrFuncCdEn[i]);
					reqVo.put("i_sFuncCdKo", arrFuncCdKo[i]);
					reqVo.put("i_sFuncCdCn", arrFuncCdCn[i]);
					cmDao.insert("SiIm010Mapper.insertSiim010Func", reqVo) ;	
					//if("Y".equals(confirmYn)) {	
					//	cmOdmDao.insert("If_SiIm010Mapper.insertSiim010Func", reqVo) ;			
					//}			
				}
			}
			
		}
		
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분DB Func 콤보 리스트
	 * </pre>
	 */
	public List<CmMap> selectSiim010FuncComboList(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getList("SiIm010Mapper.selectSiim010FuncComboList");
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 새버전 정보
	 * </pre>
	 */
	public Object selectSiim010RegInfo(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getObject("SiIm010Mapper.selectSiim010RegInfo", reqVo);
	}

	public CmMap selectConInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
    	int	recordCnt			= this.selectConListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
    	List<CmMap> list = cmDao.getList("SiIm010Mapper.selectConList", reqVo);
		return list !=null && list.size()>0? list.get(0):null;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void deleteSiIm010(CmMap reqVo) {
		// TODO Auto-generated method stub
		cmDao.update("SiIm010Mapper.deleteSiim010Info", reqVo) ;
		//cmOdmDao.update("If_SiIm010Mapper.deleteSiim010Info", reqVo) ;
	}

}
