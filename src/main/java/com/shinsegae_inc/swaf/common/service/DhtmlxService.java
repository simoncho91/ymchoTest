package com.shinsegae_inc.swaf.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.shinsegae_inc.core.exception.SwafException;
import com.shinsegae_inc.core.map.ReqParamMap;
import com.shinsegae_inc.core.service.SwafService;
import com.shinsegae_inc.dhtmlx.support.DhtmlxUtils;
import com.shinsegae_inc.swaf.common.mapper.CommonMapper;

/**
 * 공통처리 서비스 클래스
 * @author 이웅재
 * @since 2011.07.27
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2011.07.27  이웅재          최초 생성
 *	
 * </pre>
 */
@Service("dhtmlxService")
public class DhtmlxService extends SwafService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name="CommonMapper")
    private transient CommonMapper commonMapper;

	/**
	*
	* @param hm
	* @return
	* @throws Exception
	*
	* <pre>
	* Commnets : 그냥 쓸려고 Data Store용도로 데이터를 조회함.
    *            sqlid : 지정된 임의의 SQL문을 이용한 조회
    *            commclscd : 공통코드 조회
    *            {options:[]}
	* </pre>
	*/
	public ModelAndView selectDs(ReqParamMap reqParamMap) throws Exception {
		Map<Object, Object> hm = reqParamMap.getParamMap();

		ModelAndView mav = new ModelAndView("jsonView");
		
		String sSqlids;
		String sGrpCds;
		if (hm.get("sqlid") != null) {
		    sSqlids = (String) hm.get("sqlid");
		    mav.addAllObjects(getDsFromSqlId(sSqlids, hm, false, false));
		}
		if (hm.get("grpcd") != null) {
		    sGrpCds = (String) hm.get("grpcd");
		    mav.addAllObjects(getDsFromGrpCd(sGrpCds, hm, false, false));
		}
		return mav;
	}
	
	
	/**
	 *
	 * @param hm
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : ComboBox에 사용하기 위해서 Data Store용도로 데이터를 조회함.
	 *            sqlid : 지정된 임의의 SQL문을 이용한 조회
	 *            commclscd : 공통코드 조회
	 *            {options:[]}
	 * </pre>
	 */
	public ModelAndView selectDsForCombo(ReqParamMap reqParamMap) throws Exception {
	    Map<Object, Object> hm = reqParamMap.getParamMap();
	    
	    ModelAndView mav = new ModelAndView("jsonView");

        String sSqlids;
        String sGrpCds;
        
        if (hm.get("sqlid") != null) {
            sSqlids = (String) hm.get("sqlid");
            mav.addAllObjects(getDsFromSqlId(sSqlids, hm, true, false));
        }
        if (hm.get("grpcd") != null) {
            sGrpCds = (String) hm.get("grpcd");
            mav.addAllObjects(getDsFromGrpCd(sGrpCds, hm, true, false));
        }
        
	    return mav;
	}
	
	/**
	 *
	 * @param hm
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : SelectBox에 사용하기 위해서 Data Store용도로 데이터를 조회함. DHTMLX6에서 사용
	 *            sqlid : 지정된 임의의 SQL문을 이용한 조회
	 *            commclscd : 공통코드 조회
	 *            {options:[]}
	 * </pre>
	 */
	public ModelAndView selectDsForSelectBox(ReqParamMap reqParamMap) throws Exception {
	    Map<Object, Object> hm = reqParamMap.getParamMap();
	    
	    ModelAndView mav = new ModelAndView("jsonView");

       String sSqlids;
       String sGrpCds;
       
       boolean is6Version = true;
       
       if (hm.get("sqlid") != null) {
           sSqlids = (String) hm.get("sqlid");
           mav.addAllObjects(getDsFromSqlId(sSqlids, hm, true, is6Version));
       }
       if (hm.get("grpcd") != null) {
           sGrpCds = (String) hm.get("grpcd");
           mav.addAllObjects(getDsFromGrpCd(sGrpCds, hm, true, is6Version));
       }
       
	    return mav;
	}
	
	private Map<String, Object> getDsFromSqlId(String sSqlids, Map<Object, Object> hm, boolean isCombo, boolean isSixVersion) throws Exception {
	    Map<String, Object> returnMap =  new HashMap<>();
	    List<Object> list;
	    
	    StringTokenizer st;
        String sTemp;
        String sDSKey;
        String sSQLKey; 
        
        String sCombo; // Combo용도로 맨위에 전체(A)/선택(S) 항목을 추가해 줌
        Map<String, Object> comboMap;
        
        if (sSqlids != null) {
            log.debug("sSqlids ========== ^" + sSqlids.replaceAll("[\r\n]","") + "^");

            // ;으로 구분된 sqlid를 가져와서 하나씩 실행함.
            st = new StringTokenizer(sSqlids, ";");
            log.debug("DS KeySet's Count   ========== ^" + st.countTokens() + "^");
                    
            while(st.hasMoreTokens()) {
                sTemp = st.nextToken();
                
                sDSKey  = sTemp.substring(0, sTemp.indexOf(':'));
                sSQLKey = sTemp.substring(sTemp.indexOf(':')+1);
                sCombo  = null;
                
                // 잘랐는데 뒤에 :가 있으면 콤보의 지정값으로 사용(Combo용도로 전체/선택 항목을 추가해 줌)
                if (sSQLKey.indexOf(':')>-1) {
                    sCombo  = sSQLKey.substring(sSQLKey.indexOf(':')+1);
                    sSQLKey = sSQLKey.substring(0, sSQLKey.indexOf(':'));
                    hm.put("formYn", sCombo);
                }

                if (isCombo) {
                    list = commonMapper.selectList(sSQLKey, hm);
                    comboMap  = new HashMap<String, Object>();
                    comboMap.put("options", DhtmlxUtils.toComboJson(list, hm, false, isSixVersion));
                    returnMap.put(sDSKey, comboMap);
                } else {
                    returnMap.put(sDSKey, commonMapper.selectList(sSQLKey, hm));
                }
            }
        }
        return returnMap;
	}

    private Map<String, Object> getDsFromGrpCd(String sGrpCds, Map<Object, Object> hm, boolean isCombo, boolean is6Version) throws Exception {
        Map<String, Object> returnMap =  new HashMap<>();
        List<Object> list;
        
        StringTokenizer st;
        String sTemp;
        String sDSKey;
        String sUseYn;
        String sGrpCdKey;
        
        String sCombo; // Combo용도로 맨위에 전체(A)/선택(S) 항목을 추가해 줌
        Map<String, Object> comboMap;
        
        log.debug("sGrpCds ========== ^" + sGrpCds.replaceAll("[\r\n]","") + "^");
        
        // ;으로 구분된 GRP_CD를 가져와서 하나씩 실행함.(공통코드 조회 용)
        st = new StringTokenizer(sGrpCds, ";");
        log.debug("DS KeySet's Count   ========== ^" + st.countTokens() + "^");
        while(st.hasMoreTokens()) {
            sTemp = st.nextToken();
            
            sGrpCdKey     = sTemp.substring(sTemp.indexOf(':')+1);
            sDSKey        = sTemp.substring(0, sTemp.indexOf(':'));
            sUseYn        = null;
            sCombo        = null;
            
            log.debug("DS  Key        ========== ^" + sDSKey.replaceAll("[\r\n]","") + "^~");
            log.debug("sGrpCd Key     ========== ^" + sGrpCdKey.replaceAll("[\r\n]","") +"^~");
            
            if ( (sGrpCdKey != null) && !(sGrpCdKey.equals("")) ) {

                // 잘랐는데 뒤에 :가 있으면 공통코드의 사용여부(USE_YN)에 맵핑하여 사용
                if (sGrpCdKey.indexOf(':')>-1) {
                    sUseYn    = sGrpCdKey.substring(sGrpCdKey.indexOf(':')+1);
                    sGrpCdKey = sGrpCdKey.substring(0, sGrpCdKey.indexOf(':'));
                }
                hm.put("GRP_CD", sGrpCdKey);

                // 잘랐는데 뒤에 :가 있으면  콤보의 지정값으로 사용(Combo용도로 전체/선택 항목을 추가해 줌)
                if(sUseYn!=null){
                    if (sUseYn.indexOf(':')>-1) {
                        sCombo = sUseYn.substring(sUseYn.indexOf(':')+1);
                        sUseYn = sUseYn.substring(0, sUseYn.indexOf(':'));
                    }
                    hm.put("USE_YN", sUseYn);
                    hm.put("formYn", sCombo);
                } else {
                    hm.put("USE_YN", null);
                }
                
                if (isCombo) {
                    list = commonMapper.selectList("CommonCode.selectComnCodes", hm);
                    comboMap  = new HashMap<String, Object>();
                    comboMap.put("options", DhtmlxUtils.toComboJson(list, hm, false, is6Version));
                    returnMap.put(sDSKey, comboMap);
                } else {
                    returnMap.put(sDSKey, commonMapper.selectList("CommonCode.selectComnCodes", hm));
                }
            }
        }
        return returnMap;
    }
    
    
    
	/**
	 *
	 * @param hm
	 * @return
	 * @throws Exception
	 *
	 * <pre>
	 * Commnets : 코드 목록 조회
	 * </pre>
	 */
	public Map selectCodeList(Map hm) throws Exception {
		Map mapReturn = new HashMap<String, Object>();
		
		log.debug("hm : " + hm);
		
		try {
			List<Map> list = commonMapper.selectList("CommonCode.selectCodeList", hm);
			mapReturn.put("data", DhtmlxUtils.toListJson(list, false));
		} catch (SwafException e) {
			throw e;
		}
		return mapReturn;
	}
}
