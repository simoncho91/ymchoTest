package com.shinsegae_inc.swaf.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.dhtmlx.support.DhtmlxUtils;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

@Service("dhtmlx.LogService")
public class LogService extends SwafService{

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name="CommonMapper")
    private CommonMapper commonMapper;

	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 접속로그 목록 조회
	* </pre>
	*/
	public Map selectAccessLogList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("AccessLog.selectAccessLogList", hm);
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
	* Commnets : 액션로그 목록 조회
	* </pre>
	*/
	public Map selectActionLogList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("ActionLog.selectList", hm);
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
	 * Commnets : 액션로그 목록 조회
	 * </pre>
	 */
	public Map selectActionLogDetailList(Map hm) throws Exception {
	    Map mapReturn = new HashMap<String, Object>();
	    
	    try {
	        List<Map> list = commonMapper.selectList("ActionLog.selectDetailList", hm);
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
	* Commnets : 시스템에러로그 목록 조회
	* </pre>
	*/
	public Map selectErrorLogList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("ErrorLog.selectList", hm);
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
	* Commnets : 개인정보로그 목록 조회
	* </pre>
	*/
	public Map selectPinfLogList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("PrvInfPrc.selectList", hm);
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
	* Commnets : 개인정보로그 대상 목록 조회
	* </pre>
	*/
	public Map selectPinfLogDetailList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("PrvInfPrc.selectDetail", hm);
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
	* Commnets : 권한변경로그 목록 조회
	* </pre>
	*/
	public Map selectAuthLogList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("AuthProc.selectList", hm);
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
	* Commnets : 권한변경로그 대상 목록 조회
	* </pre>
	*/
	public Map selectAuthLogDetailList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("AuthProc.selectDetail", hm);
			mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
		} catch (Exception e) {
			throw e;
		}
		return mapReturn;
	}
}

