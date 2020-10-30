package com.shinsegae_inc.sitims.mi.si.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class SiCm040Service extends CmService  {
	
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS 전체 조회
	 * </pre>
	 */
	public List<CmMap> getMsdsClassList(CmMap reqVo) throws Exception {
		List<CmMap>	list	= cmDao.getList("SiCm040Mapper.getMsdsClassList", reqVo);
		return list;
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS 조회순서, 사용여부, 입력타입, 작성예시 가져옴
	 * </pre>
	 */
	public List<CmMap> getMsdsInfo(CmMap reqVo) throws Exception {
		List<CmMap>	desc	= cmDao.getList("SiCm040Mapper.getMsdsInfo", reqVo);
		return desc;
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS 등록 
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public void msdsReg(CmMap reqVo) throws Exception {
		cmDao.insert("SiCm040Mapper.SiMsdsReg", reqVo);
		//cmOdmDao.insert("If_SiCm040Mapper.SiMsdsReg", reqVo);
	}
	/**
	 *
	 * @param 
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS PK 자동 채번 
	 * </pre>
	 */
	public String getMsdsId() throws Exception {
		return cmDao.getString("SiCm040Mapper.getMsdsId");
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 등록할 MSDS 유효성 
	 * </pre>
	 */
	public int getMsdsCountForRegist(CmMap reqVo) throws Exception {
		return cmDao.getCount("SiCm040Mapper.getMsdsCountForRegist", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS 수정 
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public void msdsModify(CmMap reqVo) throws Exception {
		cmDao.update("SiCm040Mapper.SiMsdsModify", reqVo);
		//cmOdmDao.update("If_SiCm040Mapper.SiMsdsModify", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS 삭제
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public void msdsDelete(CmMap reqVo) throws Exception {
		cmDao.delete("SiCm040Mapper.SiMsdsDelete", reqVo);
		//cmOdmDao.delete("If_SiCm040Mapper.SiMsdsDelete", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : MSDS 삭제(하위 MSDS 체크)
	 * </pre>
	 */
	public int msdsDeleteChk(CmMap reqVo) throws Exception {
		return cmDao.getCount("SiCm040Mapper.SiMsdsDeleteChk", reqVo);
	}

}
