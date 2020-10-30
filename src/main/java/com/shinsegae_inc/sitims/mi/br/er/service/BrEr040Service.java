package com.shinsegae_inc.sitims.mi.br.er.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service
@SuppressWarnings("rawtypes")
public class BrEr040Service  extends CmService{

	/**
	 * 자체검토 금지어 필터링 인트로텍스트
	 * 
	 */
	public String makeIntroText() {
		StringBuffer sb = new StringBuffer(1024);
		sb.append("<div style=\"line-height: 1.5em; border-top: 2px solid #dedede\" class=\"pd_top10\" >		* 아래의 금지어 유형에 따라 금지어가 색깔로 필터링 됩니다.<br>		<span style=\"font-weight: bold;\">&nbsp;&nbsp;&nbsp;&nbsp;");
		CmMap reqVo = new CmMap();
		
		reqVo.put("i_sMstCode", "PROOF_BAN");
		List<CmMap> lists = this.commonService.getCmSubCodeList(reqVo);
		
		if(lists != null) {
			for(int i=0; i<lists.size(); i++) {
				CmMap list = lists.get(i);
				sb.append("<font color=\"" + list.getString("BUFFER1") + "\">" + list.getString("COMM_CD_NM") + "</font>");
				if(i != lists.size()-1) {
					sb.append(", ");
				}
			}
		}
		sb.append("		</span></div>");		
		
		return sb.toString();
	}
	
	/**
	 * 국문 문안 금지어필터링
	 */
	public CmMap getRequestTextFiltering(CmMap reqVo) {
		CmMap resultVo = new CmMap();
		List<CmMap> wordList = new ArrayList<CmMap>();
		int count = 0;
		String filtText = "";
		String message = "";
		String chkTime = "";
		
		if(CmFunction.isEmpty(reqVo.getString("i_sReqText"))){
			reqVo.put("message", "필수 인자 값이 없습니다.");
			reqVo.put("status", "fail");
			return null;
		}

		String key = this.insertFilteringTextClobType(reqVo); 
		reqVo.put("i_sFilteringCd", key);
		
		//의뢰요청시 금지어 필터링 단어 매칭 개수
		CmMap filtVo= this.cmDao.getObject("BrEr040Mapper.getFilteringTextCountInClobTable", reqVo);
		
		if(filtVo != null){
			count = filtVo.getInt("n_count");
			chkTime = CmFunction.getPointDtm(filtVo.getString("v_reg_dtm"));
		}
			
		// 걸리는 금지어 저장
		this.cmDao.insert("BrEr040Mapper.insertFilteringTextWord", reqVo);
		
		filtText	=	this.cmDao.getString("BrEr040Mapper.getFilteringTextInClobTable", reqVo);
		wordList	=	this.cmDao.getList("BrEr040Mapper.getKrFilteringWordList", reqVo);
		List<CmMap> forbidList = new ArrayList<CmMap>();
		List<CmMap> proofList = new ArrayList<CmMap>();
		
		if(wordList != null){
			for(CmMap vo : wordList){
				if("SPF009".equals(vo.getString("v_type_cd"))){ //실증대상이랑 나머지로 분류
					proofList.add(vo);
				} else {
					forbidList.add(vo);
				}
			}
		}

		if(count >= 6){
			message = count+"개의 금지어 포함.<br> 금지어가 6개 이상이면 의뢰할 수 없습니다.";
		} else if (count == 0){
			message = "금지어가 없습니다.";
		} else {
			message = count+"개의 금지어가 포함됐습니다.";
		}
			
		resultVo.put("count"	, count);
		resultVo.put("text"	, filtText);
		resultVo.put("forbidList"	, forbidList);
		resultVo.put("proofList"	, proofList);
		resultVo.put("chkTime", chkTime);
		resultVo.put("filtVo", filtVo);
		
		reqVo.put("message", message);
		reqVo.put("status", "succ");
		
		return resultVo;
	}
	
	/**
	 * 의뢰 내용 4000byte이상 일때 DB에러 
	 * CLOB으로 SUP_PROOF_CLOB_FILTERING 테이블에 저장 후에 필터링으로 변경함
	 */
	public String insertFilteringTextClobType(CmMap reqVo){
		String key = "";
		if(CmFunction.isEmpty(reqVo.getString("i_sReqText"))){
			reqVo.put("message", "필수 인자 값이 없습니다.");
			reqVo.put("status", "fail");
			return "";
		}
		
		reqVo.put("i_sReqText", CmFunction.removeHTML(reqVo.getString("i_sReqText")));
		reqVo.put("i_sReqText", CmFunction.removeAllHTML(reqVo.getString("i_sReqText")));
		
		Pattern p =  Pattern.compile("\\s+");
		reqVo.put("i_sReqText", p.matcher(reqVo.getString("i_sReqText")).replaceAll("<br/>"));
		
		//자바에서 시퀀스 사용시
		//String sMaxKey 	=	makeSequence(reqVo);
		
		String.valueOf(this.cmDao.insert("BrEr040Mapper.insertFilteringTextClobTable", reqVo));
		key =  reqVo.getString("i_sFilteringCd");
		
		return key;
	}
	
	//자바에서 시퀀스 사용시
	/*
	private String makeSequence(CmMap reqVo) {
		//시퀀스 제작
		String sMaxKey	=	this.cmDao.getString("BrEr040Mapper.getFilteringCd", reqVo);
				
		if("0".equals(sMaxKey)) {
			sMaxKey	=	"FT00000000000000000";
		}else {
			sMaxKey	=	String.valueOf("FT" + Integer.parseInt(sMaxKey.substring(2))+1);
		}
				
		reqVo.put("i_sFilteringCd", sMaxKey);
		
		return sMaxKey;
		
	}
	*/
}
