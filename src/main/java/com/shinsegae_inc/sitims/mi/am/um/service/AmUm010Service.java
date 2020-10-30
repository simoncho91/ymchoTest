package com.shinsegae_inc.sitims.mi.am.um.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;

@Service(value="AmUm010Service")
@SuppressWarnings("rawtypes")
public class AmUm010Service extends CmService {


	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 부서 전체 조회
	 * </pre>
	 */
	public List<CmMap> getDeptList(CmMap reqVo) throws Exception {
		List<CmMap>	list	= cmDao.getList("AmUm010Mapper.getSiDeptList", reqVo);
		return list;
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 부서 등록 
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public void deptReg(CmMap reqVo) throws Exception {
		reqVo.put("NEXT_ID",cmDao.getString("AmUm010Mapper.SiDeptRegSelectKey"));
		cmDao.insert("AmUm010Mapper.SiDeptReg", reqVo);
		//cmOdmDao.insert("If_AmUm010Mapper.SiDeptReg", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 부서 수정 
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public void deptModify(CmMap reqVo) throws Exception {
		cmDao.update("AmUm010Mapper.SiDeptModify", reqVo);
		//cmOdmDao.update("If_AmUm010Mapper.SiDeptModify", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 부서 삭제
	 * </pre>
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	public void deptDelete(CmMap reqVo) throws Exception {
		cmDao.delete("AmUm010Mapper.SiDeptDelete", reqVo);
		//cmOdmDao.delete("If_AmUm010Mapper.SiDeptDelete", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 부서 삭제(하위 부서 체크)
	 * </pre>
	 */
	public int deptDeleteChk(CmMap reqVo) throws Exception {
		return cmDao.getCount("AmUm010Mapper.SiDeptDeleteChk", reqVo);
	}
	/**
	 *
	 * @param reqVo
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 부서 유효성 체크
	 * </pre>
	 */
	public int getDeptCountForRegist(CmMap reqVo) throws Exception {
		return cmDao.getCount("AmUm010Mapper.getSiDeptCountForRegist", reqVo);
	}

}

