package com.shinsegae_inc.swaf.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.dhtmlx.support.DhtmlxUtils;
import com.shinsegae_inc.swaf.admin.domain.UserDetailEntity;
import com.shinsegae_inc.swaf.admin.repository.UserDetailRepository;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
 * 시스템 사용자 관리
 * 
 * @author 072246
 *
 */
@Service
public class UserService extends SwafService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommonMapper commonMapper;

    @Autowired
    protected CryptoService cryptoService;
    
    @Autowired
    protected UserDetailRepository userDetailRepository;
    
    @Value("${globals.systemID}")
    private String systemID;
    
	/**
	 * 사용자 조회
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map selectList(Map param) throws Exception {

		Map mapReturn = new HashMap<String, Object>();

        List<Map> list = commonMapper.selectList("User.selectUserList", param);
        
        for(Map map : list) {
        	if(map.get("EMAIL") != null) {
        		map.put("EMAIL", cryptoService.decAES(map.get("EMAIL").toString(), true));
        	}
        	if(map.get("PHONE_NO") != null) {
        		map.put("PHONE_NO", cryptoService.decAES(map.get("PHONE_NO").toString(), true));
        	}
        	
        }
        
        mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
		return mapReturn;
		
	}
	
	/**
	 * 등록
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void insert(Map param) throws Exception {
	    checkLoginId(param);
		// 비밀번호 암호화
		if (param.get("PWD") != null) param.put("PWD", cryptoService.encSHA256(param.get("PWD").toString()));
		//이메일 암호화
		if (param.get("EMAIL") != null) param.put("EMAIL", cryptoService.encAES(param.get("EMAIL").toString(), true));
		//전화번호 암호화
		if (param.get("PHONE_NO") != null) param.put("PHONE_NO", cryptoService.encAES(param.get("PHONE_NO").toString(), true));
		param.put("SYS_ID", systemID);
        commonMapper.insert("User.insert", param);
	}
	
	/**
	 * 수정
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void update(Map param) throws Exception {
	    checkLoginId(param);
	    
		if ( (param.get("PWD") != null) && (!"".equals(param.get("PWD"))) ) {
		    param.put("PWD", cryptoService.encSHA256(param.get("PWD").toString()));
		}
		
		//이메일 암호화 처리
		if((param.get("EMAIL") != null) && (!"".equals(param.get("EMAIL")))) {
			param.put("EMAIL", cryptoService.encAES(param.get("EMAIL").toString(), true));
		}
		//전화번호 암호화 처리
		if((param.get("PHONE_NO") != null) && (!"".equals(param.get("PHONE_NO")))) {
			param.put("PHONE_NO", cryptoService.encAES(param.get("PHONE_NO").toString(), true));
		}

		param.put("SYS_ID", systemID);
        commonMapper.update("User.update", param);
	}

	/**
	 * 비밀번호 수정
	 *
	 * @param param
	 * @throws Exception
	 */
	public void updatePwd(Map param) throws Exception {
		if ( (param.get("PWD") != null) && (!"".equals(param.get("PWD"))) ) {
		    param.put("PWD", cryptoService.encSHA256(param.get("PWD").toString()));
		}
		
		if ( (param.get("PHONE_NO") != null) && (!"".equals(param.get("PHONE_NO"))) ) {
			param.put("PHONE_NO", cryptoService.encAES(param.get("PHONE_NO").toString(),true));
		}
		
		String USER_NO = (String)((Map)param.get("session")).get("USER_NO");
		param.put("USER_NO", USER_NO);
		param.put("SYS_ID" , systemID);

        commonMapper.update("User.updatePwd", param);
	}
	
	
	/**
	 * 삭제
	 * 
	 * @param param
	 * @throws Exception
	 */
	public void delete(Map param) throws Exception {
        commonMapper.delete("User.delete", param);
        commonMapper.delete("User.deleteDetail", param);
	}
	

    /**
     * 사용자 아이디 기반 건수(중복 사용을 막기 위해)
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public void checkLoginId(Map param) throws Exception {
        param.put("SYS_ID", systemID);
        int iCnt = commonMapper.selectOne("User.selectLoginIdCount", param);
        if (iCnt > 0) {
            String[] args = {(String)param.get("LOGIN_ID")};
            throw new SwafException("ERR.DuplicateLoginID", args);
        }
    }
    
    /**
     * 사용자 번호로 로그인 아이디 조회
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public String  selectLoginIdByUserNo(String userNo) {

        String loginId = commonMapper.selectOne("User.selectLoginId", userNo);
        if (loginId == null) loginId = "";

        return loginId;
    }

    /**
     * 사용자 추가 권한 조회
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Map selectDetailList(Map param) throws Exception {

        Map mapReturn = new HashMap<String, Object>();

        List<Map> list = commonMapper.selectList("User.selectDetailList", param);
        mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
        return mapReturn;
    }
    
    /**
     * 추가권한 등록
     * 
     * @param param
     * @throws Exception
     */
    public void saveDetail(Map param) throws Exception {
        
        UserDetailEntity entity = new UserDetailEntity();
        entity.setUserNo((String)param.get("USER_NO"));

        // 이슈관리 - 권한등급
        if (param.get("IM_USER_LVL") != null) {
            if (param.get("IM_USER_LVL").equals("")) {
                entity.setUserTp("IM");
                
                userDetailRepository.delete(entity);     
            } else {
                entity.setUserTp("IM");
                entity.setUserLvl((String)param.get("IM_USER_LVL"));
                
                userDetailRepository.save(entity);            
            }
        }

        // 테스트관리 - 권한등급
        if (param.get("TM_USER_LVL") != null) {
            if (param.get("TM_USER_LVL").equals("")) {
                entity.setUserTp("TM");
                
                userDetailRepository.delete(entity);     
            } else {
                entity.setUserTp("TM");
                entity.setUserLvl((String)param.get("TM_USER_LVL"));
                
                userDetailRepository.save(entity);            
            }
        }
    }
 
}
