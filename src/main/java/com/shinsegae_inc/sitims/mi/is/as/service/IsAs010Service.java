package com.shinsegae_inc.sitims.mi.is.as.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service(value="IsAs010Service")
@SuppressWarnings("rawtypes")
public class IsAs010Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : CIIMS 제품검색화면 조회
	 * </pre>
	 */
	public List<CmMap> selectIsAs010List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getIsAs010ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("IsAs010Mapper.selectIsAs010List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : CIIMS 제품검색화면 Count 조회
	 * </pre>
	 */
	public int getIsAs010ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("IsAs010Mapper.getIsAs010ListCount", reqVo);
	}
}

