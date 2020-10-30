package com.shinsegae_inc.sitims.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;

@Service
@SuppressWarnings("rawtypes")
public class CommonPopService extends CmService{

	public List<CmMap> getUserList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int recordCnt = cmDao.getCount("CommonDao.getUserCount", reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("CommonDao.getUserList", reqVo);
	}

	public List<CmMap> getOdmUserList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int recordCnt = cmDao.getCount("CommonDao.getOdmUserCount", reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return this.decsCripto(cmDao.getList("CommonDao.getOdmUserList", reqVo));
	}

	public List<CmMap> getConCdBanDescList(CmMap reqVo) throws Exception {
		// TODO Auto-generated method stub
		int recordCnt = cmDao.getCount("CommonDao.getConCdBanDescCount", reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("CommonDao.getConCdBanDescList", reqVo);
	}

}
