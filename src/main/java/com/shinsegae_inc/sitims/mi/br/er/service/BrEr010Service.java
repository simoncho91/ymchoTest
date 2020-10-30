package com.shinsegae_inc.sitims.mi.br.er.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class BrEr010Service  extends CmService{

	/**
	 * 문안검토 스케쥴(?)
	 * @param reqVo
	 * @return
	 */
	public List<CmMap> getProofSchedule(CmMap reqVo) {
		return this.cmDao.getList("BrEr010Mapper.getProofSchedule", reqVo);
	}

	/**
	 * 문안검토 리스트 개수
	 * @param reqVo
	 * @return
	 */
	public int getAcceptCount(CmMap reqVo) {
		return this.cmDao.getCount("BrEr010Mapper.getAcceptCount", reqVo);
	}

	/**
	 * 문안검토 리스트
	 * @param reqVo
	 * @return
	 */
	public List<CmMap> getAcceptList(CmMap reqVo) {
		return this.cmDao.getList("BrEr010Mapper.getAcceptList", reqVo);
	}
	
}
