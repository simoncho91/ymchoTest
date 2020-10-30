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

/**
*
* @author  : 053061
* @since   : 2020. 2. 21.
* @version : 1.0
*
* <pre>
* Comments : 메인화면 용 서비스
* </pre>
*/
@Service
public class MainService extends SwafService {

	private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name="CommonMapper")
    private CommonMapper commonMapper;

    /**
     * 차트 용
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Map selectResultForMainChart() throws Exception {

        Map mapReturn = new HashMap<String, Object>();

        try {
            List<Map> list = commonMapper.selectList("ValidationResult.selectResultForMainChart");
            mapReturn.put("data1", DhtmlxUtils.toListJson(list, false));
            
            list = commonMapper.selectList("ErrorBoard.selectResultForMainChart");
            mapReturn.put("data2", DhtmlxUtils.toListJson(list, false));
            
        } catch (Exception e) {
            throw e;
        }
        return mapReturn;
    }
    
    /**
     * 조회
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Map selectResultForMainList() throws Exception {
        
        Map mapReturn = new HashMap<String, Object>();
        
        try {
            List<Map> list = commonMapper.selectList("ValidationResult.selectResultForMainList");
            mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
        } catch (Exception e) {
            throw e;
        }
        return mapReturn;
    }
}
