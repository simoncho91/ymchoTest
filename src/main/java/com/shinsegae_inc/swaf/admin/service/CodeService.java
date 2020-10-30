package com.shinsegae_inc.swaf.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.dhtmlx.support.DhtmlxUtils;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;
import com.shinsegae_inc.swaf.common.mapper.CommonOdmMapper;

@Service("dhtmlx.CodeService")
public class CodeService extends SwafService{

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name="CommonMapper")
    private CommonMapper commonMapper;
    
//	@Resource(name="CommonOdmMapper")
//    private transient CommonOdmMapper commonOdmMapper;

	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 그룹코드 목록 조회
	* </pre>
	*/
	public Map selectGrpCodeList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		//log.debug(propertyService.getString("Globals.encFilePath1").replaceAll("[\r\n]",""));
		
		try {
			List<Map> list = commonMapper.selectList("CommonCode.selectCodeGrpList", hm);
			mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
		} catch (Exception e) {
			throw e;
		}
		return mapReturn;
	}
	
	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 그룹코드 저장
	* </pre>
	*/
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void saveGrpCd(Map hm) throws Exception {
		
		try {
			if (hm.get("saveMode").equals("I")) {
				commonMapper.insert("CommonCode.insertCodeGrp", hm);
				//commonOdmMapper.insert("If_CommonCode.insertCodeGrp", hm);
			} else {
				commonMapper.insert("CommonCode.updateCodeGrp", hm);
				//commonOdmMapper.insert("If_CommonCode.updateCodeGrp", hm);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 그룹 코드 삭제
	* </pre>
	*/
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void deleteGrpCd(Map hm) throws Exception {
		try {
			commonMapper.delete("CommonCode.deleteCodeGrp", hm);
			//commonOdmMapper.delete("If_CommonCode.deleteCodeGrp", hm);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 *
	 * @param hm
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 코드 목록 조회
	 * </pre>
	 */
	public Map selectCodeList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("CommonCode.selectCodeList", hm);
			mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
		} catch (Exception e) {
			throw e;
		}
		return mapReturn;
	}
		
	/**
	 *
	 * @param hm
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 코드 저장처리를 일괄로.
	 * </pre>
	 */
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void saveDetails(ReqParamMap reqParamMap) throws Exception {
		
		int i;
		HashMap hm;
		String sStatus;
		Map param = reqParamMap.getParamMap();
		List<Object> list = reqParamMap.getParamMapList("grdDetail");
	    
		try {
			// 삭제처리 먼저
			for (i=0;i<list.size();i++) {
				hm = (HashMap) list.get(i);
    			sStatus = (String)hm.get("!nativeeditor_status");
    			if (sStatus.equals("deleted")) {
    				commonMapper.insert("CommonCode.deleteCode", hm);
    				//commonOdmMapper.insert("If_CommonCode.deleteCode", hm);
    			}
			}
			
			for (i=0;i<list.size();i++) {
				hm = (HashMap) list.get(i);
				hm.put("USER_NO", (String)((Map)param.get("session")).get("USER_NO"));
				sStatus = (String)hm.get("!nativeeditor_status");
				if (sStatus.equals("inserted")) {
					commonMapper.insert("CommonCode.insertCode", hm);
					//commonOdmMapper.insert("If_CommonCode.insertCode", hm);
				} else if (sStatus.equals("updated")) {
					commonMapper.update("CommonCode.updateCode", hm);
					commonMapper.update("CommonCode.updateCodeGrpIfYn", hm);
					//commonOdmMapper.update("If_CommonCode.updateCode", hm);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 코드 삭제
	* </pre>
	*/
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void deleteCode(Map hm) throws Exception {
		try {
			commonMapper.delete("CommonCode.deleteCode", hm);
			//commonOdmMapper.delete("If_CommonCode.deleteCode", hm);
		} catch (Exception e) {
			throw e;
		}
	}
}

