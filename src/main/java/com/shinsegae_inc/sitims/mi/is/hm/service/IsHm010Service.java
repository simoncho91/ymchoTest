package com.shinsegae_inc.sitims.mi.is.hm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service(value="IsHm010Service")
@SuppressWarnings("rawtypes")
public class IsHm010Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 파일다운로드 목록 조회
	 * </pre>
	 */
	public List<CmMap> selectIsHm010List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getIsHm010ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("IsHm010Mapper.selectIsHm010List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 파일다운로드 목록 Count 조회
	 * </pre>
	 */
	public int getIsHm010ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("IsHm010Mapper.getIsHm010ListCount", reqVo);
	}
}

