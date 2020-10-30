package com.shinsegae_inc.swaf.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.dhtmlx.support.DhtmlxUtils;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;
import com.shinsegae_inc.swaf.common.service.ActionUrlService;

@Service("dhtmlx.MenuObjService")
public class MenuObjService extends SwafService{

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name="CommonMapper")
    private CommonMapper commonMapper;

	@Autowired
	private ActionUrlService actionUrlService;

    
    /**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : OBJ기본리스트 조회
	* </pre>
	*/
	public Map selectObjList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("MenuAction.selectActionBaseList", hm);
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
	* Commnets : OBJ추가리스트 조회
	* </pre>
	*/
	public Map selectObjAddList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("MenuAction.selectActionAddList", hm);
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
	* Commnets : 버튼 권한 리스트 조회
	* </pre>
	*/
	public Map selectBtnAuthList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		try {
			List<Map> list = commonMapper.selectList("Menu.selectBtnAuthListCombo", hm);
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
	* Commnets : OBJ추가리스트 저장
	* </pre>
	*/
	public void saveObj(Map hm) throws Exception {
		try {
			if (hm.get("saveMode").equals("I")) {
				commonMapper.insert("MenuAction.insertMenuAction", hm);
			} else {
				commonMapper.insert("MenuAction.updateMenuAction", hm);
			}
			actionUrlService.loadActionUrls();
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
	* Commnets : OBJ 삭제
	* </pre>
	*/
	public void deleteObj(Map hm) throws Exception {
		try {
			commonMapper.insert("Menu.deleteActionBase", hm);
		} catch (Exception e) {
			throw e;
		}
	}
	
}

