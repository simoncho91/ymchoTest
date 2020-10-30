package com.shinsegae_inc.swaf.common.service;

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

import net.sf.json.JSONArray;

@Service("dhtmlx.AuthService")
public class AuthService extends SwafService{

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

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
	public JSONArray getMenuList(Map hm) throws Exception {
		JSONArray jsaMenu = null;
		try {
			//List<Object> list = commonMapper.selectList("Auth.selectMenuList", hm);
			List<Object> list = commonMapper.selectList("Menu.selectMainMenuListDhtmlx", hm);
			if ( (list != null) && (list.size()>0) ) {
			    jsaMenu = (JSONArray)JSONArray.fromObject(DhtmlxUtils.toTreeJson(list, 1, false));
			}
		} catch (Exception e) {
			throw e;
		}
		return jsaMenu;
	}

	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 해당 프로그램의 정보 조회
	* </pre>
	*/
	public Map getProgramInfo(Map hm) throws Exception {
        Map mapReturn = new HashMap<String, Object>();
        
        try {
        	String			menuNm 	= commonMapper.selectOne("Menu.selectMenuNm", hm);
        	mapReturn.put("MENU_CD", hm.get("MENU_CD"));
        	mapReturn.put("TITLE", menuNm);
        	log.debug("menuNm ===========  " + menuNm);
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
	 * Commnets : 해당 프로그램에서 사용 가능한 버튼 조회
	 * </pre>
	 */
	public Map getBtnAuth(Map hm) throws Exception {
		return commonMapper.selectOne("Menu.selectBtnAuth", hm);
	}
}

