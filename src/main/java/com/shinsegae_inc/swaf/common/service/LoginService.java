package com.shinsegae_inc.swaf.common.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
 * 로그인 처리
 * 
 * @author 072246
 *
 */
@Service
public class LoginService extends SwafService {
	
	@Autowired
	private CommonMapper commonMapper;

    @Autowired
    protected CryptoService cryptoService;

    @Value("${globals.security.userSessionNm}")
    private String userSessionNm;
    
    protected transient Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 로그인 처리
	 * 
	 * @param loginId
	 * @param loginPwd
	 * @return
	 * @throws Exception
	 */
	public boolean selectLogin(String loginId, String loginPwd) throws Exception {
	    
	    String pwd = null;
	    
	    Map<String, String> param = new HashMap<String, String>();
	    
	    param.put("USER_ID", loginId);
	    param.put("SYS_ID" , systemID);
	    pwd = commonMapper.selectOne("Login.selectLogin", param);
	    
	    log.debug("### LoginService.selectLogin : loginPwd / pwd = " + loginPwd + " / " + pwd);
        
	    if (pwd != null && cryptoService.checkSHA256(loginPwd, pwd)
	    		) {
	        Map<String, String> userInfo = commonMapper.selectOne("Login.selectUser", param);
	        SessionUtils.setAttribute(userSessionNm, userInfo);
            return true;
        }
        return false;
	}

	/**
	 * 로그인 처리
	 * 
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	public boolean selectLogin(String userNo) throws Exception {
		Map<String, String> param = new HashMap<String, String>();

		param.put("USER_NO", userNo);
		param.put("SYS_ID" , systemID);

		Map<String, String> userInfo = commonMapper.selectOne("Login.selectUserNo", param);
		if (null == userInfo)
			return false;
		else {
			SessionUtils.setAttribute(userSessionNm, userInfo);
			return true;
		}
	}
	   /**
     * 사용자가 등록한 아이디와 이메일 매칭
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Map checkEqUserPhone(Map param) throws Exception {
        Map tmpMap = commonMapper.selectOne("User.selectUserList", param);
        Map<Object, Object> resultMap = new HashMap<>();
        if(tmpMap == null) {
        	resultMap.put("statue","fail");
        	resultMap.put("msg","입력하신 정보와 일치하는 회원이 없습니다.");
    		return resultMap;
        }else {
        	if(tmpMap.get("PHONE_NO") != null) {
        		tmpMap.put("PHONE_NO", cryptoService.decAES(tmpMap.get("PHONE_NO").toString(), true));
        	}else {
        		resultMap.put("statue","fail");
        		resultMap.put("msg","저장되어 있는 회원의 휴대폰번호가 없습니다.<br/>휴대폰번호를 등록 후 사용해주세요.");
        		return resultMap;
        	}
            if(tmpMap.get("PHONE_NO").toString().equals(param.get("PHONE_NO").toString())) {
            	resultMap.put("matching","Y");
        			param.put("USER_NO", tmpMap.get("USER_NO"));
        			param.put("CERT_NUM", RandomStringUtils.randomNumeric(6));
        	        commonMapper.update("Login.updateForInitPwd", param);
            }else {
            	resultMap.put("matching","N");
            	
            }
        }

        return resultMap;
    }

    /**
     * 비밀번호 초기화
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Map initPwd(Map param) throws Exception {

    	 Map tmpMap = commonMapper.selectOne("User.selectUserList", param);
    	 Map<Object, Object> resultMap = new HashMap<>();
    	 if(tmpMap.get("SMS_CERT_NUM").toString().equals(param.get("CERT_NUM").toString())) {
    		 param.put("USER_NO", tmpMap.get("USER_NO"));
    		 param.put("PWD", cryptoService.encSHA256("0000"));
    		 param.put("PWD_YN", "Y");
    		 commonMapper.update("Login.InitPwd", param);
    		 resultMap.put("statue","succ");
    		  resultMap.put("msg","비밀번호를 초기화 했습니다 <br/>로그인하셔서 비밀번호를 변경해주세요 <br/>(임시 비밀번호 0000)");
    	 }else {
    		 resultMap.put("statue","fail");
    		 resultMap.put("msg","입력하신 인증번호가 일치 하지 않습니다.");
    	 }
    	 return resultMap;
    }
}
