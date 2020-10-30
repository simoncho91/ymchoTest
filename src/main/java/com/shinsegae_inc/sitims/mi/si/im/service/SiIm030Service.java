package com.shinsegae_inc.sitims.mi.si.im.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service(value="SiIm030Service")
@SuppressWarnings("rawtypes")
public class SiIm030Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 배합목적 목록 조회
	 * </pre>
	 */
	public List<CmMap> selectSiIm030List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getSiIm030ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("SiIm030Mapper.selectSiIm030List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 배합목적 목록 Count 조회
	 * </pre>
	 */
	public int getSiIm030ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("SiIm030Mapper.getSiIm030ListCount", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 배합목적 데이터 저장 insert
	 * </pre>
	 */
//	@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void insertSiIm030List(CmMap reqVo) throws Exception {
		cmDao.insert("SiIm030Mapper.insertSiIm030List",reqVo);
		this.mergeSiIm030(reqVo);	// odm db merge
	}

	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 배합목적 데이터 저장 update
	 * </pre>
	 */
//	@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateSiIm030List(CmMap reqVo) throws Exception {
		cmDao.insert("SiIm030Mapper.updateSiIm030List",reqVo);
		this.mergeSiIm030(reqVo);	// odm db merge
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 배합목적 데이터 저장 delete
	 * </pre>
	 */
	public void deleteSiIm030List(CmMap reqVo) throws Exception {
		cmDao.insert("SiIm030Mapper.deleteSiIm030List",reqVo);
		this.deleteSiIm030(reqVo);	// odm db del
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 배합목적 데이터 저장 if merge
	 * </pre>
	 */
	public void mergeSiIm030(CmMap reqVo) throws Exception {
//		cmOdmDao.update("If_SiIm030Mapper.mergeSiIm030",reqVo);
	//	throw new Exception("rollback Test!@!!!!!!!!!!!");
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Commnets : 배합목적 데이터 저장 if delete
	 * </pre>
	 */
	public void deleteSiIm030(CmMap reqVo) throws Exception {
//		cmOdmDao.update("If_SiIm030Mapper.deleteSiIm030",reqVo);
	}
}
