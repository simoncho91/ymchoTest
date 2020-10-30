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

@Service("dhtmlx.UserMenuService")
public class UserMenuService extends SwafService{

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
	* Commnets : 전체사용자 목록 조회
	* </pre>
	*/
	public Map selectList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		try {
			List<Map> list = commonMapper.selectList("User.selectUserList", hm);
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
	* Commnets : 사용자메뉴 목록 조회
	* </pre>
	*/
	public Map selectUserMenuList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		try {
			List<Map> list = commonMapper.selectList("Role.selectUserMenuList", hm);
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
	* Commnets : 사용자메뉴 저장
	* </pre>
	*/
	public void saveUserMenu(Map hm) throws Exception {
		
		try {
			if (hm.get("saveMode").equals("I")) {
				commonMapper.insert("Role.insertUserMenu", hm);
			} else {
				commonMapper.insert("Role.updateUserMenu", hm);
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
	* Commnets : 권한그룹사용자 삭제
	* </pre>
	*/
	public List<Object> deleteUserMenu(Map hm) throws Exception {
		List<Object> mapReturn = null;
		commonMapper.delete("Role.deleteUserMenu", hm);
    	return mapReturn;
    }
}

