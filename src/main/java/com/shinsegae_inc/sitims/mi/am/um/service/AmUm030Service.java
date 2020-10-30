package com.shinsegae_inc.sitims.mi.am.um.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class AmUm030Service extends CmService {

	
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리자 리스트
	 * </pre>
	 */
	public List<CmMap> selectAmUm030List(CmMap reqVo) throws Exception {
    	int	recordCnt	= this.getAmUm030ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("AmUm030Mapper.selectAmUm030List",reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리자 리스트 Count 조회
	 * </pre>
	 */
	public int getAmUm030ListCount(CmMap reqVo) throws Exception{
		return cmDao.getCount("AmUm030Mapper.getAmUm030ListCount", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리 국가  조회
	 * </pre>
	 */
	public List<CmMap> getCmCodeListForRaNation(CmMap reqVo) {
		return cmDao.getList("CommonDao.getCmSubCodeList",reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리자 등록
	 * </pre>
	 */
	public void regForRaAdmin(CmMap reqVo) {
		 cmDao.insert("AmUm030Mapper.regForRaAdmin",reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리자 삭제
	 * </pre>
	 */
	public void delForRaAdmin(CmMap reqVo) {
		 cmDao.delete("AmUm030Mapper.delForRaAdmin",reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리자 수정
	 * </pre>
	 */
	public void modifyForRaAdmin(CmMap reqVo) {
		 cmDao.update("AmUm030Mapper.modifyForRaAdmin",reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : RA관리자 등록, 수정, 삭제 유효성  조회
	 * </pre>
	 */
	public int getSelectCountForRa(CmMap reqVo) throws Exception{
		return cmDao.getCount("AmUm030Mapper.getSelectCountForRa", reqVo);
	}
	
}
