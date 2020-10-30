package com.shinsegae_inc.swaf.tmpl.tmpl0020.service;

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

@Service("dhtmlx.Tmpl0020Service")
public class Tmpl0020Service extends SwafService{

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
	* Commnets : 메뉴 목록 조회
	* </pre>
	*/
	public Map selectList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		//log.debug(propertyService.getString("Globals.encFilePath1"));
		
		try {
			List<Map> list = commonMapper.selectList("Menu.selectMenuList", hm);
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
	* Commnets : 메뉴 조회
	* </pre>
	*/
	public Map selectMenu(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			Map<Object, Object> map = commonMapper.selectOne("Menu.selectMenu", hm);
			mapReturn.put("data", map);
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
	* Commnets : 개인정보대상 목록 조회
	* </pre>
	*/
	public Map selectPinfList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		//log.debug(propertyService.getString("Globals.encFilePath1"));
		
		try {
			List<Map> list = commonMapper.selectList("Menu.selectPinfList", hm);
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
	* Commnets : 메뉴 저장
	* </pre>
	*/
	public void save(Map hm) throws Exception {
		try {
			if (hm.get("saveMode").equals("I")) {
				commonMapper.insert("Menu.insertMenu", hm);
			} else {
				commonMapper.insert("Menu.updateMenu", hm);
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
	* Commnets : 개인정보대상 사용여부 수정
	* </pre>
	*/
	public void updatePinfYn(Map hm) throws Exception {
		try {
			commonMapper.insert("Menu.updatePinfYn", hm);
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
	* Commnets : 개인정보대상 저장
	* </pre>
	*/
	public void insertPinf(Map hm) throws Exception {
		try {
			commonMapper.insert("Menu.insertPinf", hm);
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
	* Commnets : 개인정보대상 삭제
	* </pre>
	*/
	public void deletePinf(Map hm) throws Exception {
		try {
			commonMapper.insert("Menu.deletePinfOne", hm);
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
	* Commnets : 개인정보대상 메뉴 삭제 (개인정보대상 삭제)
	* </pre>
	*/
	public void delete(Map hm) throws Exception {
		try {
			commonMapper.delete("Menu.deletePinf", hm);
			commonMapper.delete("Role.deleteRoleMenu", hm);
			commonMapper.delete("Menu.deleteMenu", hm);
		} catch (Exception e) {
			throw e;
		}
	}
}

