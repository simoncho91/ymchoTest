package com.shinsegae_inc.sitims.mi.si.em.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service
@SuppressWarnings("rawtypes")
public class SiEm010Service extends CmService{
	public List<CmMap> selectSiEm010List(CmMap reqVo) throws Exception {
		int				recordCnt	=	0;
		List<CmMap> 	list		=	null;
		String			sGroupId	=	reqVo.getString("s_groupid");
		boolean			adminYn		=	false;
		
		// 문안검토 전체 권한. (제도협력팀, 관리자)
        if (sGroupId.indexOf("S000003") > -1 || "Y".equals(reqVo.getString("s_sysadmin_yn"))) {
			adminYn	=	true;
		}
        reqVo.put("adminYn", adminYn);
		
		if(!"except_word".equals(reqVo.getString("i_sType"))){
			recordCnt	=	getForbiddenListCount(reqVo);
			if (recordCnt > 0) {
				this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
				list	=	getForbiddenList(reqVo);
			}
		} else {
			// 금지어 제외단어 테이블 따로.
			recordCnt	= getForbiddenExceptWordListCount(reqVo);
			if (recordCnt > 0) {
				this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
				list	=	getForbiddenExceptWordList(reqVo);
			}
		}

		return list;
	}
	
	public int getForbiddenListCount(CmMap reqVo) {
		return this.cmDao.getCount("SiEm010Mapper.getForbiddenListCount", reqVo);
	}
	
	public List<CmMap> getForbiddenList(CmMap reqVo) {
		return this.cmDao.getList("SiEm010Mapper.getForbiddenList", reqVo);
	}
	
	public int getForbiddenExceptWordListCount(CmMap reqVo) {
		return this.cmDao.getCount("SiEm010Mapper.getForbiddenExceptWordListCount", reqVo);
	}
	
	public List<CmMap> getForbiddenExceptWordList(CmMap reqVo) {
		return this.cmDao.getList("SiEm010Mapper.getForbiddenExceptWordList", reqVo);
	}
	
	//금지어 탭 카테고리 리스트
	public List<CmMap> getTabList(CmMap reqVo){
		return this.cmDao.getList("SiEm010Mapper.getTabList", reqVo);
	}
	
	//금지어 상세보기
	public CmMap getForbiddenInfo(CmMap reqVo) {
		String				sGroupId	=	reqVo.getString("s_groupid");
		boolean				adminYn		=	false;
		// 문안검토 전체 권한. (제도협력팀, 관리자)
        if (sGroupId.indexOf("S000003") > -1 || "Y".equals(reqVo.getString("s_sysadmin_yn"))) {
			adminYn	=	true;
		}
        reqVo.put("adminYn", adminYn);
		
		return this.cmDao.getObject("SiEm010Mapper.getForbiddenInfo", reqVo);
	}
	
	public void crudSupProofForbiddenWord(CmMap reqVo) throws Exception{
		String 			actionFlag		=	reqVo.getString("mode");
		
		if("R".equals(actionFlag)){
			insertSupProofForbiddenWord(reqVo);
			reqVo.put("status", "succ");
			reqVo.put("message", "저장 하였습니다.");
		} else if("M".equals(actionFlag)){
			updateSupProofForbiddenWord(reqVo);
			reqVo.put("status", "succ");
			reqVo.put("message", "수정 하였습니다.");
		} else if("D".equals(actionFlag)){
			deleteSupProofForbiddenWord(reqVo);
			reqVo.put("status", "succ");
			reqVo.put("message", "삭제 하였습니다.");
		} else if ("R_E".equals(actionFlag)){
			insertForbiddenExceptWord(reqVo);
			reqVo.put("status", "succ");
			reqVo.put("message", "저장 하였습니다.");
		} else if("R_M".equals(actionFlag)){
			updateForbiddenExceptWord(reqVo);
			reqVo.put("status", "succ");
			reqVo.put("message", "수정 하였습니다.");
		} 
	}
	
	//금지어 CRUD - 등록
	private void insertSupProofForbiddenWord(CmMap reqVo) {
		
		if(CmFunction.isEmpty(reqVo.getString("i_sForbidden"))){
			reqVo.put("status", "fail");
			reqVo.put("message", "필수 인자 값이 없습니다.");
			return;
		}
		
		this.cmDao.insert("SiEm010Mapper.insertSupProofForbiddenWord", reqVo);
		reqVo.put("status", "succ");
		reqVo.put("message", "등록되었습니다");
	}
	
	//금지어 CRUD - 수정
	private void updateSupProofForbiddenWord(CmMap reqVo) {
		if(CmFunction.isEmpty(reqVo.getString("i_sType")) && CmFunction.isEmpty(reqVo.getString("i_iSeqNo"))){
			reqVo.put("status", "fail");
			reqVo.put("message", "필수 인자 값이 없습니다.");
			return;
		}
		
		this.cmDao.update("SiEm010Mapper.updateSupProofForbiddenWord", reqVo);
		reqVo.put("status", "succ");
		reqVo.put("message", "수정되었습니다");
	}
	
	//금지어 CRUD - 삭제
	private void deleteSupProofForbiddenWord(CmMap reqVo) {
		this.cmDao.update("SiEm010Mapper.deleteSupProofForbiddenWord", reqVo);
	}
	
	//금지어 CRUD - 등록?
	private void insertForbiddenExceptWord(CmMap reqVo) {
		this.cmDao.insert("SiEm010Mapper.insertForbiddenExceptWord", reqVo);
	}
	
	//금지어 CRUD - 예외?
	private void updateForbiddenExceptWord(CmMap reqVo) {
		this.cmDao.update("SiEm010Mapper.updateForbiddenExceptWord", reqVo);
	}
}
