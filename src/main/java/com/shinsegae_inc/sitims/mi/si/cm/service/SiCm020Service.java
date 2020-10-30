package com.shinsegae_inc.sitims.mi.si.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service("sitims.SiCm020Service")
public class SiCm020Service extends CmService {

	public List<CmMap> selectSiCm020List(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		return cmDao.getList("SiCm020Mapper.selectSiCm020List", reqVo);
	}

	public CmMap selectCategoryInfo(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getObject("SiCm020Mapper.selectCategoryInfo", reqVo);
	}
	public int getCategoryEqNmChk(CmMap reqVo) {
		// TODO Auto-generated method stub
		return cmDao.getCount("SiCm020Mapper.getCountCategoryEqNm", reqVo);
	}
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateCategoryInfo(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int result = 0;
		int eqNmChk = ((Integer)getCategoryEqNmChk(reqVo)).intValue();
		
		if(CmFunction.isEmpty(reqVo.getString("i_sClasscd"))) {
			if (eqNmChk == 0) {
				this.cmDao.insert("SiCm020Mapper.insertCategoryInfo", reqVo);
				//this.cmOdmDao.insert("If_SiCm020Mapper.insertCategoryInfo", reqVo);
				result++;
			}else {
				reqVo.put("status", "fail");
				reqVo.put("message", "등록하려는 카테고리에 동일한 이름이 존재합니다.");
				return;
			}
		}else {
			if (eqNmChk == 0) {
				result = this.cmDao.update("SiCm020Mapper.updateCategoryInfo", reqVo);
				//if(this.cmOdmDao.update("If_SiCm020Mapper.updateCategoryInfo", reqVo) <= 0) {
				//	this.cmOdmDao.insert("If_SiCm020Mapper.insertCategoryInfo", reqVo);
				//}
			}else if(eqNmChk == 1){
				
				reqVo.put("EqNmForUpdate","Y");
				if(((Integer)getCategoryEqNmChk(reqVo)).intValue() == 1) {
					
					result = this.cmDao.update("SiCm020Mapper.updateCategoryInfo", reqVo);
					//if(this.cmOdmDao.update("If_SiCm020Mapper.updateCategoryInfo", reqVo) <= 0) {
					//	this.cmOdmDao.insert("If_SiCm020Mapper.insertCategoryInfo", reqVo);
					//}
					
				}else {
					reqVo.put("status", "fail");
					reqVo.put("message", "등록하려는 카테고리에 동일한 이름이 존재합니다.");
					return;
				}
				
			}else {
				reqVo.put("status", "fail");
				reqVo.put("message", "수정하려는 카테고리에 동일한 이름이 존재합니다.");
				return;
			}
		}
		
		if(result == 1) {
			reqVo.put("status", "succ");
			reqVo.put("message", "저장 성공했습니다.");
		} else {
			reqVo.put("status", "fail");
			reqVo.put("message", "저장 실패했습니다.");
		}
		
	}

	public List<CmMap> selectSiCm020ListExcel(CmMap reqVo) {
		// TODO Auto-generated method stub		
		return cmDao.getList("SiCm020Mapper.selectSiCm020ListExcel", reqVo);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void deleteSiCm020(CmMap reqVo) {
		// TODO Auto-generated method stub
		cmDao.update("SiCm020Mapper.deleteSiCm020", reqVo);
		//cmOdmDao.update("If_SiCm020Mapper.deleteSiCm020", reqVo);
		
	}
}

