package com.shinsegae_inc.sitims.mi.am.am.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service
@SuppressWarnings("rawtypes")
public class AmAm030Service extends CmService {

	
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(부서) 리스트
	 * </pre>
	 */
	public List<CmMap> selectAmAm030List(CmMap reqVo) throws Exception {
    	int	recordCnt	= this.getAmAm030ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("AmAm030Mapper.selectAmAm030List",reqVo);
	}
	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(부서) 리스트 Count 조회
	 * </pre>
	 */
	public int getAmAm030ListCount(CmMap reqVo) throws Exception{
		return cmDao.getCount("AmAm030Mapper.getAmAm030ListCount", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(부서) 등록
	 * </pre>
	 */
	public void regForProdDept(CmMap reqVo) {
		 cmDao.insert("AmAm030Mapper.regForProdDept",reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(부서) 삭제
	 * </pre>
	 */
	public void delForProdDept(CmMap reqVo) {
		 cmDao.delete("AmAm030Mapper.delForProdDept",reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(부서) 등록, 삭제 유효성  조회
	 * </pre>
	 */
	public int selectCountForProdDept(CmMap reqVo) throws Exception{
		return cmDao.getCount("AmAm030Mapper.selectCountForProdDept", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 제품권한(부서) 콤보박스 생성을 위한 부서 리스트
	 * </pre>
	 */
	public List<CmMap> getDeptForComboBox(CmMap reqVo) throws Exception {
		List<CmMap> list = cmDao.getList("AmAm030Mapper.getDeptForComboBox",reqVo);
		List<CmMap>	tmpList	= new ArrayList<>();
		
		for(CmMap cMap : list) {
			CmMap	resultVo = new CmMap();
			resultVo.put("id", cMap.get("v_id"));
			resultVo.put("value", cMap.get("v_value"));
			tmpList.add(resultVo);
		}

		return tmpList;
	}

	
}
