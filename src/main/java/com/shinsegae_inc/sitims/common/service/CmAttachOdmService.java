package com.shinsegae_inc.sitims.common.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.util.CmFunction;
import com.shinsegae_inc.sitims.common.util.CmPathInfo;

@SuppressWarnings("rawtypes")
@Service("cmAttachOdmService")
public class CmAttachOdmService extends CmService{
	
	public void uploadFilesOdm(CmMap reqVo, String uploadCd) throws IOException {
		if(CmFunction.isEmpty(uploadCd)) {
			return;
		}
		
		reqVo.put("i_sUploadCd", uploadCd);
		String attachTable = this.selectRefTablenm(reqVo);
		
		String[] arrAttachId = null;
		String[] arrAttachType = null;
		String[] arrAttachLNm = null;
		String[] arrAttachSize = null;
		String[] arrAttachPk1 = null;
		String[] arrAttachPk2 = null;
		String[] arrAttachPk3 = null;
		String[] arrAttachPk4 = null;
		String[] arrAttachPk5 = null;
		String[] arrAttachPNm = null;
		String[] arrAttachMgtId = null;
		String arrAttachPath = CmPathInfo.getUPLOAD_FILE_TEMP_PATH() + CmFunction.getRegDate6() + "/";
		CmMap attMap = null;
		
		arrAttachId = reqVo.getStringArray(uploadCd + "_del_attach_id");
		int attachLen = arrAttachId == null ? 0 : arrAttachId.length;
		
		if(attachLen > 0) {
			attMap = new CmMap();
			
			for(int i=0; i<attachLen; i++) {
				attMap.put("attach_id", arrAttachId[i]);
				this.cmDao.delete("CommonDao.deleteOdmAttach", attMap);
				this.cmDao.delete("CommonDao.deleteOdmAttachSub", attMap);
			}
		}
		
		arrAttachId = reqVo.getStringArray(uploadCd + "_attach_id");
		arrAttachLNm = reqVo.getStringArray(uploadCd + "_attach_lnm");
		arrAttachType = reqVo.getStringArray(uploadCd + "_attach_type");
		arrAttachSize = reqVo.getStringArray(uploadCd + "_attach_size");
		arrAttachPk1 = reqVo.getStringArray(uploadCd + "_attach_pk1");
		arrAttachPk2 = reqVo.getStringArray(uploadCd + "_attach_pk2");
		arrAttachPk3 = reqVo.getStringArray(uploadCd + "_attach_pk3");
		arrAttachPk4 = reqVo.getStringArray(uploadCd + "_attach_pk4");
		arrAttachPk5 = reqVo.getStringArray(uploadCd + "_attach_pk5");
		arrAttachPNm = reqVo.getStringArray(uploadCd + "_attach_pnm");
		arrAttachMgtId = reqVo.getStringArray(uploadCd + "_attach_mgtid");
		
		String pathTemp = "";
		String pathMove = "";
		String movePath = "";
		
		attachLen = arrAttachId == null ? 0 : arrAttachId.length;
		if(attachLen > 0) {
			for(int i=0; i<attachLen; i++) {
				attMap = new CmMap();
				movePath = CmPathInfo.getUPLOAD_FILE_PATH() + arrAttachMgtId[i] + "/";
				
				pathTemp = arrAttachPath + arrAttachPNm[i];
				pathMove = movePath + arrAttachPNm[i];
				
				if(CmFunction.fileMove(pathTemp, pathMove)) {
					attMap.put("i_sAttachId", arrAttachId[i]);
					attMap.put("i_sAttachMgtId", arrAttachMgtId[i]);
					attMap.put("i_sAttachPnm", arrAttachPNm[i]);
					attMap.put("i_sAttachLnm", arrAttachLNm[i]);
					attMap.put("i_sAttachType", arrAttachType[i]);
					attMap.put("i_sAttachSize", arrAttachSize[i]);
					
					this.cmDao.insert("CommonDao.insertOdmAttach", attMap);
					
					attMap.put("i_sAttachPk1", arrAttachPk1[i]);
					attMap.put("i_sAttachPk2", arrAttachPk2[i]);
					attMap.put("i_sAttachPk3", arrAttachPk3[i]);
					attMap.put("i_sAttachPk4", arrAttachPk4[i]);
					attMap.put("i_sAttachPk5", arrAttachPk5[i]);
					attMap.put("i_sAttachTable", attachTable);
					
					this.cmDao.insert("CommonDao.insertOdmAttachSub", attMap);
				}
			}
		}
	}
	
	
	public String selectRefTablenm(CmMap reqVo) {
		//return this.cmDao.getString("CommonDao.selectRefTable", reqVo);
		return "";
	}

	
	public int selectAttachListCount(CmMap reqVo) {
		return this.cmDao.getCount("CommonDao.selectAttachListCount", reqVo);		
	}

	
	public List<CmMap> selectAttacList(CmMap reqVo) {
		return this.cmDao.getList("CommonDao.selectAttachList", reqVo);
	}

	
	public CmMap selectAttachDownInfo(CmMap reqVo) {
		return this.cmDao.getObject("CommonDao.selectAttachDownInfo", reqVo);
	}
	
	
}
