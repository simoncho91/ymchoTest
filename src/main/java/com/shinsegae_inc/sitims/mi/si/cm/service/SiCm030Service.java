package com.shinsegae_inc.sitims.mi.si.cm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service
@SuppressWarnings("rawtypes")
public class SiCm030Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분배합 한도관리 목록 조회
	 * </pre>
	 */
	public List<CmMap> selectSiCm030List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getSiCm030ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("SiCm030Mapper.selectSiCm030List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분배합 한도관리 목록 Count 조회 
	 * </pre>
	 */
	public int getSiCm030ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("SiCm030Mapper.getSiCm030ListCount", reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 *
	 * <pre>
	 * Commnets : 성분코드에 따른 분배합 한도 리스트 
	 * </pre>
	 */
	public List<CmMap> getSiCm030LimitCateList(CmMap reqVo) {
		if (CmFunction.isEmpty(reqVo.getString("i_sConCd")))
			return null;
		
		reqVo.put("i_sFlagCategory"	, "N");
		reqVo.put("i_sClassCd"		, "S000001");
		
		List<CmMap>	cateList1	= this.cmDao.getList("SiCm030Mapper.getSiCm030CategoryList", reqVo);
		List<CmMap>	cateList2	= null;
		
		if (null != cateList1) {
			reqVo.put("cateList1", cateList1);
			cateList2	= this.cmDao.getList("SiCm030Mapper.getSiCm030LimitCateList", reqVo);
		}
		
		return cateList2;
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 기본 배합한도 여부 YN 설정
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public int updateSiCm030LimitYn(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.update("SiCm030Mapper.updateSiCm030LimitYn", reqVo);
		 //cmOdmDao.update("If_SiCm030Mapper.updateSiCm030LimitYn", reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 성분배합한도 수정
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public int insertSiCm030LimitConSub(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.update("SiCm030Mapper.insertSiCm030LimitConSub", reqVo);
		 //cmOdmDao.update("If_SiCm030Mapper.insertSiCm030LimitConSub", reqVo);
	}
		


}
