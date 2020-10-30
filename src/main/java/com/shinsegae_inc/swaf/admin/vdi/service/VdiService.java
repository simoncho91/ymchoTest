package com.shinsegae_inc.swaf.admin.vdi.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

@Service("dhtmlx.VdiService")
public class VdiService extends SwafService{
	
    @Resource(name="CommonMapper")
    private transient CommonMapper commonMapper;
    
    /**
	 * VDI IP 목록 조회
	 * 
	 * @param param
	 *   - dc_searchParam
	 * @return 
	 */
    public List<Object> selectVdiList(Map<Object, Object> hm) throws Exception {
//    	try {
    		return commonMapper.selectList("Vdi.selectVdiList", hm);
//		} catch (Exception e) {
//			throw e;
//		}
    	
    }

	public void insert(Map<Object, Object> paramMap, HttpServletRequest request) throws Exception{
		 commonMapper.insert("Vdi.insertVdi", paramMap);
		
	}

	public void update(Map<Object, Object> paramMap, HttpServletRequest request) throws Exception{
		 commonMapper.insert("Vdi.updateVdi", paramMap);
		
	}

	public void delete(Map<Object, Object> paramMap) throws Exception{
		 commonMapper.delete("Vdi.deleteVdi", paramMap);
		
	}
	
	public String selectVdiYn(Map<String, Object> paramMap) throws Exception{
		return commonMapper.selectOne("Vdi.selectVdiYn",paramMap);
	}
	public String isAccessVdi(Map<String, Object> paramMap) throws Exception{
		int cnt = commonMapper.selectOne("Vdi.isAccessVdi",paramMap);
		if( cnt > 0 ) {
			return "Y";
		}else
			return "N"; 
	}
}

