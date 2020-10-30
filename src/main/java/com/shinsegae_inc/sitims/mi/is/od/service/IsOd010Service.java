package com.shinsegae_inc.sitims.mi.is.od.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service(value="IsOd010Service")
@SuppressWarnings("rawtypes")
public class IsOd010Service extends CmService {

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Comments : 원료DB 조회
	 * </pre>
	 */
	public List<CmMap> selectIsOd010List(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
    	int	recordCnt			= this.getIsOd010ListCount(reqVo);
    	this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("IsOd010Mapper.IsOd010List", reqVo);
	}

	/**
	 * 
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Comments : 원료DB Count 조회
	 * </pre>
	 */
	public int getIsOd010ListCount(CmMap reqVo) throws Exception{
		// TODO Auto-generated method stub
		return cmDao.getCount("IsOd010Mapper.IsOd010ListCnt", reqVo);
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Comments : 원료DB 원료마스터 select
	 * </pre>
	 */
	public CmMap selectIsOd011MatInfo(CmMap reqVo) {
		return this.cmDao.getObject("IsOd010Mapper.selectIsOd011MatInfo", reqVo);
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Comments : 원료DB 성분마스터 select
	 * </pre>
	 */
	public List<CmMap> selectIsOd011ConListInMat(CmMap reqVo) {
		return this.cmDao.getList("IsOd010Mapper.selectIsOd011ConListInMat", reqVo);
	}
	
	/**
	 * 
	 * @param reqVo
	 *
	 * <pre>
	 * Comments : ODM사 정보 리스트 조회 select
	 * </pre>
	 */
	public List<CmMap> selectIsOd010OdmCompanyInfoList() {
		return this.cmDao.getList("IsOd010Mapper.selectIsOd010OdmCompanyInfoList");
	}
	
	// 성분정보 리스트 가져오기
	public List<CmMap> selectIsOd011TotalConListInMat(CmMap reqVo, List<CmMap> mate01dt) {
		List<CmMap> consumList = this.cmDao.getList("IsOd010Mapper.selectIsOd011TotalConListInMat", reqVo);
		return consumList;
	}
	
	
	// 향알러젠 함량
	public Map<String, List<CmMap>> getAllergenMap(CmMap reqVo) {
		
		Map<String, List<CmMap>> listMap = new HashMap<String, List<CmMap>>();
		
		List<CmMap> list = cmDao.getList("IsOd010Mapper.selectIsOd011AllergenListInMat", reqVo);
		
		int size = list.size();
		
		if (size > 0) {
			
			List<CmMap> subList = null;
			Map<String, Object> tvo;
			String key;
			
			for (int i = 0; i < size; i++) {
				
				tvo = list.get(i);
				key = CmFunction.getStringValue(tvo.get("v_key"));
				
				subList = listMap.get(key);
				
				if (subList == null) {
					subList = new ArrayList<CmMap>();
					listMap.put(key, subList);
				}
				
				subList.add((CmMap) tvo);
			}
		}
		
		return listMap;
	}
}

