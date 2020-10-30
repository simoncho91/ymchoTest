package com.shinsegae_inc.sitims.mi.am.am.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class AmAm020Service extends CmService {

	
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(사용자) 리스트
	 * </pre>
	 */
	public List<CmMap> selectAmAm020List(CmMap reqVo) throws Exception {
    	int	recordCnt	= this.getAmAm020ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("AmAm020Mapper.selectAmAm020List",reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(사용자) 리스트 Count 조회
	 * </pre>
	 */
	public int getAmAm020ListCount(CmMap reqVo) throws Exception{
		return cmDao.getCount("AmAm020Mapper.getAmAm020ListCount", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(사용자) 국가  조회
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
	 * Commnets : 제품권한(사용자) 등록
	 * </pre>
	 */
	public void regForProdUser(CmMap reqVo) {
		 cmDao.insert("AmAm020Mapper.regForProdUser",reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(사용자) 삭제
	 * </pre>
	 */
	public void delForProdUser(CmMap reqVo) {
		 cmDao.delete("AmAm020Mapper.delForProdUser",reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(사용자) 수정
	 * </pre>
	 */
	public void modifyForProdUser(CmMap reqVo) {
		 cmDao.update("AmAm020Mapper.modifyForProdUser",reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(사용자) 등록, 수정, 삭제 유효성  조회
	 * </pre>
	 */
	public int selectCountForProdUser(CmMap reqVo) throws Exception{
		return cmDao.getCount("AmAm020Mapper.selectCountForProdUser", reqVo);
	}
	
}
