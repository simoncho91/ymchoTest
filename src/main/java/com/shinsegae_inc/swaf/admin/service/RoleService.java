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

@Service("dhtmlx.RoleService")
public class RoleService extends SwafService{

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
	* Commnets : 권한그룹 목록 조회
	* </pre>
	*/
	public Map selectList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		try {
			List<Map> list = commonMapper.selectList("Role.selectRoleList", hm);
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
	* Commnets : 전체메뉴 목록 조회
	* </pre>
	*/
	public Map selectMenuList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		try {
			List<Map> list = commonMapper.selectList("Role.selectMenuList", hm);
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
	* Commnets : 권한그룹메뉴 목록 조회
	* </pre>
	*/
	public Map selectRoleMenuList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		try {
			List<Map> list = commonMapper.selectList("Role.selectRoleMenuList", hm);
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
	* Commnets : 권한그룹 저장
	* </pre>
	*/
	public void save(Map hm) throws Exception {
		try {
			if (hm.get("saveMode").equals("I")) {
				commonMapper.insert("Role.insertRole", hm);
			} else {
				commonMapper.insert("Role.updateRole", hm);
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
	* Commnets : 권한그룹메뉴 저장
	* </pre>
	*/
	public void saveMenu(Map hm) throws Exception {
		try {
			if (hm.get("saveMode").equals("I")) {
				commonMapper.insert("Role.insertRoleMenu", hm);
			} else {
				commonMapper.insert("Role.updateRoleMenu", hm);
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
	* Commnets : 권한그룹 삭제
	* </pre>
	*/
	public List<Object> delete(Map hm) throws Exception {
		List<Object> mapReturn = null;
		commonMapper.delete("Role.deleteRole", hm);
    	return mapReturn;
    }
	
	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 권한그룹메뉴 삭제
	* </pre>
	*/
	public List<Object> deleteMenu(Map hm) throws Exception {
		List<Object> mapReturn = null;
		commonMapper.delete("Role.deleteRoleMenu", hm);
    	return mapReturn;
    }
}

