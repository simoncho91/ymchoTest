package com.shinsegae_inc.swaf.tmpl.tmpl0010.service;

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

@Service("dhtmlx.Tmpl0010Service")
public class Tmpl0010Service extends SwafService{

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
	
}

