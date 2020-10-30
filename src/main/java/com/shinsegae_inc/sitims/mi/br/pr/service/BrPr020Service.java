package com.shinsegae_inc.sitims.mi.br.pr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinsegae_inc.core.security.service.CryptoService;
import com.shinsegae_inc.core.util.SessionUtils;
import com.shinsegae_inc.sitims.common.CmMap;
import com.shinsegae_inc.sitims.common.service.CmService;
import com.shinsegae_inc.sitims.common.util.CmFunction;

@Service
@SuppressWarnings("rawtypes")
public class BrPr020Service extends CmService{
    @Autowired
    protected transient CryptoService cryptoService;
    
    /**
     * 문안검토리스트(new)
     * 
     */
    public List<CmMap> selectBrPr020List(CmMap reqVo) throws Exception{
    	int				recordCnt	=	0;
		List<CmMap> 	list		=	null;
		
		//날짜조건 셋팅
		reqVo.put("i_sStDate", reqVo.getString("i_sStDate").replace("-", ""));
		reqVo.put("i_sEnDate", reqVo.getString("i_sEnDate").replace("-", ""));
		
		if(!"".equals(reqVo.getString("i_sStDate")) && "".equals(reqVo.getString("i_sEnDate"))){
			reqVo.put("i_sEnDate", CmFunction.getTodayString("yyyyMMdd"));
		}
		
		//권한셋팅
		String userRole = (String) SessionUtils.getAuthenticatedUserForMap().get("ROLE_NO");
		if(userRole.indexOf("RA") == -1 && userRole.indexOf("ADMIN") == -1) {
			String userDept = (String) SessionUtils.getAuthenticatedUserForMap().get("DEPT_CD");
			if(CmFunction.isNotEmpty(userDept)) {
				CmMap tmpMap = new CmMap();
				tmpMap.put("i_sUserDept",userDept);
				tmpMap.put("i_sFlagExcelAll", "Y");
				CmMap tmpBrandMap = cmDao.getObject("AmAm030Mapper.selectAmAm030List",tmpMap);
				reqVo.put("i_sUserBrand",tmpBrandMap.get("v_brand_cd"));
			}else {
				reqVo.put("i_sUserBrand",null);
			}
		}
		
		//셀렉트 리스트
		recordCnt	=	this.cmDao.getCount("BrPr020Mapper.getAcceptCount", reqVo);
		
		if (recordCnt > 0) {
			this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
			list	=	this.cmDao.getList("BrPr020Mapper.getAcceptList", reqVo);
		}
		return list;
    }
	
	/**
	 * 문안검토 리스트 개수
	 * @param reqVo
	 * @return
	 */
	public int getAcceptCount(CmMap reqVo) {
		return this.cmDao.getCount("BrPr020Mapper.getAcceptCount", reqVo);
	}

	/**
	 * 문안검토 리스트
	 * @param reqVo
	 * @return
	 */
	public List<CmMap> getAcceptList(CmMap reqVo) {
		return this.cmDao.getList("BrPr020Mapper.getAcceptList", reqVo);
	}
	
	/**
	 * 담당 RA 리스트
	 * @param reqVo
	 * @return
	 */
	public List<CmMap> getRaList(CmMap reqVo) {
		List<CmMap> list = this.cmDao.getList("BrPr020Mapper.getRaList", reqVo);
		
		for(Map map : list) {			
        	if(map.get("v_email") != null) {
        		map.put("v_email", cryptoService.decAES(map.get("v_email").toString(), true));
        	}
		}
		
		return list;
	}
	
	public CmMap getBrPr020Info(CmMap reqVo) {
		CmMap	result	=	this.cmDao.getObject("BrPr020Mapper.getBrPr020Info", reqVo);
		
    	if(result != null) {
    		result = this.decsCripto(this.cmDao.getObject("BrPr020Mapper.getBrPr020Info", reqVo));
    		result.put("picCheck", getPicCheck(result));
    	}
    	
    	//기능성이 아닌 경우
    	
    	//BM메일 디코딩
//    	if((String)result.get("v_bm_email") != null || (String)result.get("v_bm_email").equals("")) {
//    		result.put("v_bm_email", cryptoService.decAES(result.get("v_bm_email").toString(), true));
//    	}
    	
    	return result;
	}
	
	public String getPicCheck(CmMap reqVo) {
		List<String> picCheckArr	=	new ArrayList<String>();
    	String 		picCheckBool	=	"Y";
    	
    	if(reqVo.getString("v_vessel_yn").equals("Y")) {
    		picCheckArr.add(reqVo.getString("v_vessel_pic_status_cd"));
    	}
    	if(reqVo.getString("v_box_yn").equals("Y")) {
    		picCheckArr.add(reqVo.getString("v_box_pic_status_cd"));
    	}
    	if(reqVo.getString("v_paper_yn").equals("Y")) {
    		picCheckArr.add(reqVo.getString("v_paper_pic_status_cd"));
    	}
    	
    	for(int i=0; i<picCheckArr.size(); i++) {
    		if(!picCheckArr.get(i).equals("PIC_REQ_STATUS04")) {	//'승인' 이외 코드가 있을 시
    			picCheckBool = "N";
    		}
    	}
    	
    	return picCheckBool;
	}
	
	/**
	 * 문안검토 검색 옵션 셋팅
	 * @param reqVo
	 * @return
	 */
	public List<List<CmMap>> getOptions(CmMap reqVo){
		HashMap<String,String> map 		= new HashMap<String,String>();
		List<List<CmMap>> 	   lists    = new ArrayList<List<CmMap>>();
		
		map.put("AD_REQ_STATUS", "AD_REQ_STATUS");
		map.put("BRAND_CODE", 	 "BRAND_CODE");
		
		for(String a : map.keySet()) {
			reqVo.put("i_sMstCode", map.get(a));
			lists.add(this.commonService.getCmSubCodeList(reqVo));
		}
		
		return lists;
	}
	
	
	public List<CmMap> getUseDateGuideList(CmMap reqVo) {
		return this.cmDao.getList("BrPr020Mapper.getUseDateGuideList", reqVo);
	}
	
	public CmMap getAccept(CmMap reqVo) {
		return this.cmDao.getObject("BrPr020Mapper.getAccept", reqVo);
	}
	
	/**
	 * 문안검토 상세 필터링 데이터 가져오기
	 * @param rvo
	 * @return
	 */
	public CmMap getDraftFilteringWordInfo(CmMap rvo) {
		CmMap 			tmpVo		= new CmMap();
		List<CmMap> 	wordList	= new ArrayList<CmMap>();
		
		if(rvo == null){
			return null;
		}
		
		tmpVo.put("i_sRecordId", rvo.getString("v_ad_req_id"));
		tmpVo.put("i_sFilteringCd", tmpVo.getString("i_sRecordId"));
		
		if(!"Y".equals(rvo.getString("sFlagReFilteringNo"))){
			// 상세화면에서는 금지어 필터링을 다시 하지 않도록 방지
			this.cmDao.delete("BrPr020Mapper.deleteFilteringTextWord", tmpVo);
			this.cmDao.insert("BrPr020Mapper.insertDraftedFilteringTextWord", tmpVo);
		}
		
		wordList	=	this.cmDao.getList("BrPr020Mapper.getKrFilteringWordList", tmpVo);
		List<CmMap> forbidList = new ArrayList<CmMap>();
		List<CmMap> proofList = new ArrayList<CmMap>();
		
		if(wordList != null){
			for(CmMap vo : wordList){
				if("SPF009".equals(vo.getString("v_type"))){ //실증대상이랑 나머지로 분류
					proofList.add(vo);
				} else {
					forbidList.add(vo);
				}
			}
		}
		
		rvo.put("forbidList", forbidList);
		rvo.put("proofList", proofList);
		
		return rvo;
	}
	
	public List<CmMap> getProdPopList(CmMap reqVo) throws Exception {

		//권한셋팅
		String userRole = (String) SessionUtils.getAuthenticatedUserForMap().get("ROLE_NO");
		if(userRole.indexOf("RA") == -1 && userRole.indexOf("ADMIN") == -1) {
			String userDept = (String) SessionUtils.getAuthenticatedUserForMap().get("DEPT_CD");
			if(CmFunction.isNotEmpty(userDept)) {
				CmMap tmpMap = new CmMap();
				tmpMap.put("i_sUserDept",userDept);
				tmpMap.put("i_sFlagExcelAll", "Y");
				CmMap tmpBrandMap = cmDao.getObject("AmAm030Mapper.selectAmAm030List",tmpMap);
				reqVo.put("i_sUserBrand",tmpBrandMap.get("v_brand_cd"));
			}else {
				reqVo.put("i_sUserBrand",null);
			}
		}
		
		int recordCnt = cmDao.getCount("BrPr020Mapper.getProdPopCount",reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		return cmDao.getList("BrPr020Mapper.getProdPopList", reqVo);
	}
	

	/**
	 * 기능성 보고서 데이터 가져오기
	 */
	public CmMap getFuncReport(CmMap reqVo) {
		CmMap cmMap = cmDao.getObject("BrPr020Mapper.getFuncReport",reqVo);
		
		if(cmMap !=null) {
			cmMap = this.decsCripto(cmMap);
		
			//기능성원료 리스트
			List<CmMap> funcMatList = this.cmDao.getList("BrPr020Mapper.getFuncMatList", reqVo);
			cmMap.put("funcMatList", funcMatList);
			
			//주의사항 <br>태그 제거
			cmMap.put("PRD_CAUTION",   CmFunction.removeHTMLChangeBr(cmMap.getString("PRD_CAUTION")));
			cmMap.put("V_ADD_CAUTION", CmFunction.removeHTMLChangeBr(cmMap.getString("v_add_caution")));
		}
		
		return cmMap;
	}
	
	/**
	 * 기능성 보고서 2,3호 효능효과 멀티데이터 가져오기
	 */
	public List<CmMap> getEffectData(CmMap reqVo) {
		return cmDao.getList("BrPr020Mapper.getEffectData", reqVo);
	}
	
	
	
	/**
	 * 문안등록요청(등록)
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public CmMap regBrPr020(CmMap reqVo) throws Exception{
		String	status	=	reqVo.getString("i_sStatus");
		
		//임시저장,검토요청
		insertBrPr020(reqVo);
		
		//문안검토 생략시 완료 플래그 처리
		if(status.equals("AD_REQ_STATUS04")) {
			this.cmDao.update("BrPr020Mapper.adComplete", reqVo);
		}
		
		//업로드
		reqVo.put("i_sRecordId", reqVo.getString("i_sAdReqId"));
		//	AD_attach_pk1(최초등록시에는 i_sRecordId로 사용 / CmService 로직에서 AD_attach_pk1 길이를 계산하기 때문에 아래 로직 사용)
		String[] attachId	=	reqVo.getStringArray("AD_attach_id");
		int attachLen 		=	attachId == null ? 0 : attachId.length;
		String[] temp		=	new String[attachLen];
		
		for(int i=0; i<attachLen; i++) {
			temp[i] = "";
		}
		
		reqVo.put("AD_attach_pk1", temp);
		
		this.insertFileList("AD",reqVo);
		
		//검토요청시 - 인터페이스 처리
//		if("AD_REQ_STATUS02".equals(status)) {
//			this.insertFileListOdm("AD", reqVo);
//		}
		
		//금지어 필터링 데이터 재처리
		reInsertFilteringData(reqVo.getString("i_sAdReqId"));
		
		return reqVo;
	}
	
	/**
	 * 문안등록요청(수정)
	 * @throws Exception 
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void modifyBrPr020(CmMap reqVo) throws Exception {
		String	status	=	reqVo.getString("i_sStatus");
		
		if("AD_REQ_STATUS01".equals(status)) {			//임시저장 -> 임시저장
			updateBrPr020(reqVo);
		} else if("AD_REQ_STATUS02".equals(status)) {	//임시저장 -> 검토요청
			updateBrPr020(reqVo);
			
			//임시저장->검토요청 / 반려->재검토요청으로 인한 merge 처리
			//this.cmOdmDao.insert("If_BrPr020Mapper.mergeBrPr020", reqVo);	//인터페이스 처리
		} else if("AD_REQ_STATUS03".equals(status)) {	//반려
			changeStatus(reqVo);
		} else if("AD_REQ_STATUS04".equals(status)) {	//RA 승인
			changeStatus(reqVo);	
			//문안검토완료여부, 완료일 업데이트
			this.cmDao.update("BrPr020Mapper.adComplete", reqVo);
			//this.cmOdmDao.update("If_BrPr020Mapper.adComplete", reqVo);		//인터페이스 처리
		}
		
		//업로드
		reqVo.put("i_sRecordId", reqVo.getString("i_sAdReqId"));
		
		this.insertFileList("AD",reqVo);
		
		
//		if("AD_REQ_STATUS02".equals(status)) {
//			this.insertFileListOdm("AD", reqVo);	//인터페이스*
//		}
		
		//금지어 필터링 데이터 재처리
		reInsertFilteringData(reqVo.getString("i_sAdReqId"));
	}
	
	/**
	 * 금지어 필터링 데이터 재처리
	 * @param key
	 */
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	private void reInsertFilteringData(String key) {
		CmMap 	tempVo	=	new CmMap();
		//임시저장된 금지어필터링 데이터를 문안검토 생성 key로 다시 처리
		tempVo.put("i_sRecordId", key);
		tempVo.put("i_sFilteringCd", tempVo.getString("i_sRecordId"));
		
		this.cmDao.delete("BrPr020Mapper.deleteFilteringTextWord", tempVo);
		this.cmDao.insert("BrPr020Mapper.insertDraftedFilteringTextWord", tempVo);
	}
	
	/**
	 * 원화등록요청(등록,수정)
	 * @throws Exception 
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor= Exception.class)
	public void reqBrPrOri020(CmMap reqVo) throws Exception {
		String	status	=	reqVo.getString("i_sStatus");
		
		//원화등록요청 임시저장, 원화등록요청
		if("AD_REQ_STATUS05".equals(status) || "AD_REQ_STATUS06".equals(status)) {
			updateBrPrOri020(reqVo);
		}
		
//		if("AD_REQ_STATUS06".equals(status)) {
//			this.cmOdmDao.update("If_BrPr020Mapper.updateBrPrOri020", reqVo);	//인터페이스 처리
//		}
		
		//업로드
		reqVo.put("i_sRecordId", reqVo.getString("i_sAdReqId"));
		this.insertFileList("ORI",reqVo);
		
//		if("AD_REQ_STATUS06".equals(status)) {
//			this.insertFileListOdm("ORI", reqVo);	//인터페이스 처리 
//		}
	}

	/**
	 * 승인,반려,요청취소 등 상태변경
	 * @param reqVo
	 */
	public void changeStatus(CmMap reqVo) {
		this.cmDao.update("BrPr020Mapper.changeStatus", reqVo);
	//	this.cmOdmDao.update("If_BrPr020Mapper.changeStatus", reqVo);
	}

	private void insertBrPr020(CmMap reqVo) {
		this.cmDao.insert("BrPr020Mapper.insertBrPr020", reqVo);
	}
	
	private void updateBrPr020(CmMap reqVo) {
		this.cmDao.update("BrPr020Mapper.updateBrPr020", reqVo);
	}
	
	private void updateBrPrOri020(CmMap reqVo) {
		if(!reqVo.getString("i_sVendorAddr").equals("")) {
			reqVo.put("i_sVendorAddr",cryptoService.encAES(reqVo.getString("i_sVendorAddr"), true));
		}
		this.cmDao.update("BrPr020Mapper.updateBrPrOri020", reqVo);
	}
	
	public void brPrOri020AddPacking(CmMap reqVo) {
		this.cmDao.update("BrPr020Mapper.brPrOri020AddPacking", reqVo);
	}
	
	/**
	 * 디자이너 리스트 로딩
	 */
	public List<CmMap> getDesignerList(CmMap reqVo) throws Exception {
		List<CmMap> list	=	new ArrayList<CmMap>();
		int recordCnt		=	this.cmDao.getCount("BrPr020Mapper.getDesignerCount", reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		list = cmDao.getList("BrPr020Mapper.getDesignerList", reqVo);
		
		return list;
	}
	
	/**
	 * 외주디자이너 리스트 로딩
	 */
	public List<CmMap> getOdmDesignerList(CmMap reqVo) throws Exception {
		List<CmMap> list	=	new ArrayList<CmMap>();
		int recordCnt		=	this.cmDao.getCount("BrPr020Mapper.getOdmDesignerCount", reqVo);
		this.setListPagging(reqVo, recordCnt, reqVo.getInt("i_iPageSize"), reqVo.getInt("i_iNowPageNo"));
		list = cmDao.getList("BrPr020Mapper.getOdmDesignerList", reqVo);
		
		return list;
	}
	
	/**
	 * 실증자료 코멘트 리스트
	 * @param reqVo
	 * @return
	 */
	public List<CmMap> getReviewContentList(CmMap reqVo){
		return cmDao.getList("BrPr020Mapper.getReviewContentList", reqVo);
	}
	
	/**
	 * 실증자료 코멘트 등록
	 * @param reqVo
	 * @throws Exception
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void brPr020ReviewReg(CmMap reqVo) throws Exception {
		//코멘트 등록 및 수정
		String[]	contents	=	reqVo.getStringArray("i_arrContent");
		CmMap 		cVo			=	new CmMap();
		
		for(int i=0; i<contents.length; i++) {
			cVo.put("i_sAdReqId", reqVo.getString("i_sAdReqId"));
			cVo.put("i_sProductCd", reqVo.getString("i_sProductCd"));
			cVo.put("i_sContent", contents[i]);
			cVo.put("i_nSeq", i);
			cVo.put("i_sUserId", reqVo.getString("i_sLoginUserId"));
			cVo.put("i_sRegDtm", reqVo.getString("i_sRegDtm"));
			
			this.cmDao.insert("BrPr020Mapper.brPr020ReviewContentReg", cVo);
			//this.cmOdmDao.insert("If_BrPr020Mapper.brPr020ReviewContentReg", cVo);
			
		}
		//실증자료 업로드파일 등록
		this.insertFileList("REVIEW", reqVo);
		//this.insertFileListOdm("REVIEW", reqVo);
	}
	
	/**
	 * 원화발송 완료 체크
	 */
	public int brPr020oriCompleteCheck(CmMap reqVo) throws Exception{
		return cmDao.getCount("BrPr020Mapper.brPr020oriCompleteCheck",reqVo);
	}
	
	/**
	 * 원화검토 의견 등록
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void brPr020OpinionReg(CmMap reqVo) throws Exception {
		reqVo.put("type", reqVo.getString("i_sMsgType"));
		cmDao.insert("BrPr020Mapper.brPr020OpinionReg", reqVo);
		//this.cmOdmDao.insert("If_BrPr020Mapper.brPr020OpinionReg", reqVo);
		
		String apprStatus = reqVo.getString("i_sApprStatus");

		//일반의견등록이 아닌 승인,반려일 때 상태 처리
		if(!apprStatus.equals("NON_STATUS")) {
			cmDao.insert("BrPr020Mapper.brPr020OpinionApprChange", reqVo);
			//this.cmOdmDao.insert("If_BrPr020Mapper.brPr020OpinionApprChange", reqVo);
		}
		
		//원화 개별 승인이 모두 끝날 시, 전체상태코드 '원화검토승인'
		if(!apprStatus.equals("NON_STATUS") && getPicCheck(cmDao.getObject("BrPr020Mapper.getPackingYn", reqVo)).equals("Y")){
			reqVo.put("i_sStatus","AD_REQ_STATUS09");	//원화검토승인
			changeStatus(reqVo);
		}else if(apprStatus.equals("PIC_REQ_STATUS05")) {	//반려
			reqVo.put("i_sStatus","AD_REQ_STATUS07");	//원화검토반려
			changeStatus(reqVo);
		}
	}
	
	/**
	 * 원화검토 의견 수정
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void brPr020OpinionModify(CmMap reqVo) throws Exception {
		reqVo.put("type", reqVo.getString("i_sMsgType"));
		cmDao.insert("BrPr020Mapper.brPr020OpinionModify", reqVo);
		//this.cmOdmDao.insert("If_BrPr020Mapper.brPr020OpinionModify", reqVo);
	}
	
	/**
	 * 원화검토 의견 삭제
	 */
	//@Transactional(value = "twoTransactionManager", rollbackFor = Exception.class)
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void brPr020OpinionDelete(CmMap reqVo) throws Exception {
		reqVo.put("type", reqVo.getString("i_sMsgType"));
		cmDao.insert("BrPr020Mapper.brPr020OpinionDelete", reqVo);
		//this.cmOdmDao.insert("If_BrPr020Mapper.brPr020OpinionDelete", reqVo);
	}
	
	/**
	 * 원화검토 의견 리스트
	 */
	public List<CmMap> brPr020OpinionList(CmMap reqVo) throws Exception {
		return cmDao.getList("BrPr020Mapper.brPr020OpinionList", reqVo);
	}
	
	/**
	 * 기능성N 기본 데이터 가져오기
	 */
	public CmMap getNonFuncData(CmMap reqVo) throws Exception {
		CmMap result = cmDao.getObject("BrPr020Mapper.getNonFuncData",reqVo);
		if(result != null) {
			result = this.decsCripto(cmDao.getObject("BrPr020Mapper.getNonFuncData",reqVo));
		}
		return result;
	}
	
	public List<CmMap> getHowtoList(CmMap reqVo) throws Exception {
		return cmDao.getList("BrPr020Mapper.getHowtoList",reqVo);
	}
	
	public void regNonFuncData(CmMap reqVo) throws Exception {
		cmDao.insert("BrPr020Mapper.regNonFuncData", reqVo);
	}
	
	
}
