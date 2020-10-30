package com.shinsegae_inc.sitims.mi.si.im.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class SiIm020Service extends CmService {
	
	@Autowired
    protected transient CryptoService cryptoService;
	
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분등록요청리스트  리스트
	 * </pre>
	 */
	public List<CmMap> selectSiIm020List(CmMap reqVo) throws Exception {
    	int	recordCnt	= this.getSiIm020ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
    	List<CmMap> list = cmDao.getList("SiIm020Mapper.selectSiIm020List",reqVo);
		
    	for(CmMap map : list) {
        	if(map.get("v_email") != null) {
        		map.put("v_email", cryptoService.decAES(map.get("v_email").toString(), true));
        	}
    	}
    	
		return list;
    }
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분등록요청리스트  리스트 Count 조회
	 * </pre>
	 */
	public int getSiIm020ListCount(CmMap reqVo) throws Exception{
		return cmDao.getCount("SiIm020Mapper.getSiIm020ListCount", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분등록요청리스트  수정
	 * </pre>
	 */
	public void modifyForSiIm020(CmMap reqVo) {
		 cmDao.update("SiIm020Mapper.modifyForSiIm020",reqVo);
		 //cmOdmDao.update("If_SiIm020Mapper.modifyForSiIm020",reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분등록요청리스트  수정 유효성  조회
	 * </pre>
	 */
	public int CountForSiIm020Modify(CmMap reqVo) throws Exception{
		return cmDao.getCount("SiIm020Mapper.CountForSiIm020Modify", reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분등록요청리스트  수정 후 조회
	 * </pre>
	 */
	public CmMap selectSiIm020ForMailSend(CmMap reqVo) throws Exception{
		return cmDao.getObject("SiIm020Mapper.selectSiIm020ForMailSend", reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분DB로 데이터 보내기 전 유효성 검사
	 * </pre>
	 */
	public int getSiIm020Statue(CmMap reqVo) throws Exception{
		return cmDao.getCount("SiIm020Mapper.getSiIm020Statue", reqVo);
	}
	
}
