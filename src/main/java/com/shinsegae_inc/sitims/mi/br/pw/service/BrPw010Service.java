package com.shinsegae_inc.sitims.mi.br.pw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class BrPw010Service extends CmService{
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void saveBrPw010Report(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		this.cmDao.update("BrPw010Mapper.updateBrPw010Report", reqVo);
		//this.cmOdmDao.update("If_BrPw010Mapper.updateBrPw010Report", reqVo);
		this.insertFileList("REVIEW", reqVo);
	}

	public List<CmMap> selectBrPw010List(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int recordCnt = cmDao.getCount("BrPr010Mapper.getProdCount",reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("BrPr010Mapper.selectBrPr010List", reqVo);
	}
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateAlreadyProcess(CmMap reqVo) throws Exception{

		CmMap tempFileVo = new CmMap();
		tempFileVo.put("i_sRecordIdLike", reqVo.getString("i_sReferProductCd"));
		List<CmMap> fileVo = this.cmDao.getList("CommonDao.selectAttachList",tempFileVo);
		for(CmMap map : fileVo) {
//			String attachId = "AT" + Calendar.getInstance().getTimeInMillis() + CmFunction.getRandomString( 2 );
			String path = map.getString("v_attach_path");
//			String fileType = map.getString("v_attach_ext").toLowerCase();
			
//			if(CmFunction.fileCopy(path+map.getString("v_attach_id")+"."+fileType, path+attachId+"."+fileType)) {
				tempFileVo.put("i_sAttachId",map.getString("v_attach_id"));
				tempFileVo.put("i_sAttachPk1",map.getString("v_record_id").replaceAll(reqVo.getString("i_sReferProductCd"), reqVo.getString("i_sProductCd")));
				tempFileVo.put("i_sAttachPk2",map.getString("v_upload_id"));
				tempFileVo.put("i_sAttachLnm",map.getString("v_attach_nm"));
				tempFileVo.put("i_sAttachPath",path);
				tempFileVo.put("i_sAttachType",map.getString("v_attach_ext"));
				tempFileVo.put("i_sAttachSize",map.getString("n_attach_size"));
				tempFileVo.put("i_sLoginUserId",reqVo.getString("pRegUserId"));

				tempFileVo.put("i_sBuffer1",map.getString("v_buffer1"));
				tempFileVo.put("i_sBuffer2",map.getString("v_buffer2"));
				tempFileVo.put("i_sBuffer3",map.getString("v_buffer3"));
				tempFileVo.put("i_sBuffer4",map.getString("v_buffer4"));
				tempFileVo.put("i_sBuffer5",map.getString("v_buffer5"));
				
				cmDao.insert("CommonDao.insertAttachFile", tempFileVo);
				//cmOdmDao.insert("CmOdmMapper.insertAttachFile", tempFileVo);
//			}		
		}

		// TODO Auto-generated method stub
		cmDao.insert("BrPw010Mapper.insertProcessMAY_CON_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDCONM_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDCP_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDFG_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDPR_CP_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDPR_M", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDPR_PT_D", reqVo);	
		cmDao.insert("BrPw010Mapper.insertProcessPDIG_NA_D", reqVo);		
		cmDao.insert("BrPw010Mapper.insertProcessPDSP_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDMD_D", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDEF_R_M", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDEF_L", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessPDEFR_L", reqVo);
		cmDao.insert("BrPw010Mapper.insertProcessSTAB", reqVo);
		
		CmMap tempVo = new CmMap();
		tempVo.put("i_sRecordId", reqVo.getString("i_sReferRecordId"));
		tempVo.put("i_sProductCd", reqVo.getString("i_sReferProductCd"));
		CmMap referVo = this.decsCripto(cmDao.getObject("BrPr010Mapper.getBrPr010ProdList",tempVo));
		if(referVo != null && !referVo.isEmpty()) {
			reqVo.put("i_sFinalRst",referVo.getString("v_final_rst"));
			reqVo.put("i_sCkDomesticYn",referVo.getString("v_ck_domestic_yn"));
			reqVo.put("i_sCkOverseasYn",referVo.getString("v_ck_overseas_yn"));
			cmDao.update("BrPw020Mapper.updateAlreadyProcessProd", reqVo);			
		}
		
	}
	
	public CmMap selectIngredient(CmMap reqVo) throws Exception {
		return this.cmDao.getObject("BrPw010Mapper.selectIngredientKo", reqVo);
	}
	
	public List<CmMap> getIngrtMartNationMemo(CmMap reqVo) {
		return this.cmDao.getList("BrPw010Mapper.getIngrtMartNationMemo", reqVo);
	}

	public List<CmMap> getIngrtMartNationMemoAllVerHistoryList(CmMap reqVo) {
		return this.cmDao.getList("BrPw010Mapper.getIngrtMartNationMemoAllVerHistoryList", reqVo);
	}


}
