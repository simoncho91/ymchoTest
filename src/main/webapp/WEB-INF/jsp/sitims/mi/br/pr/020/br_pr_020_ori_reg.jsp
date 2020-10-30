<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<!-- 파일업로드 관련  s-->
<link rel="stylesheet" href="${ScriptPATH}jfupload/css/jquery.fileupload.css"></link>
<script type="text/javascript" src="${ScriptPATH}doT.min.js"></script>
<script type="text/javascript" src="${ScriptPATH}notify.min.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/load-image.min.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/js/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${ScriptPATH}jfupload/odm/cm_jfupload.js?v=3"></script>
<!-- 파일업로드 관련 e -->

<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
</style>
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_reg.js"></script>
<script type="text/javascript">
	var prodSearch;

	var prdCautionCM = fn_replaceAll("${cfn:removeHTMLChangeBr(caution[0].COMM_CD_DESC)}", "<br/>", "\r");
	var prdCautionType = "";
	var prdCautionIngri = "";
	var html = "";
	
	function fn_replaceAll ( tmpStr, searchStr, replaceStr) {

		while( tmpStr.indexOf( searchStr ) != -1 )	{
			tmpStr = tmpStr.replace( searchStr, replaceStr );
		}
		return tmpStr;

	}
	
	j$(function(){
		var index = j$(".tbody_product_info").length;
		
		for(var i=0; i<index; i++){
			 jfupload.initUpload({
				target : j$("#admst_upload_btn_" + i)
				, uploadCd : "ORI"
				, index : i
				, formName : "frm"
				, success : OreqReg.addEvent.attachSuccEvent
				, isSelfMakeTag : true
				, attachDir : "ORI"
			});
		}
		
		fn_init();
		
		if("Y" == $("#i_sFuncYn").val()){
			setFuncReportData($("#i_sRecordId").val(), $("#i_sProductCd").val());
		}
	});
	
	function fn_init(){
		addButtonEvent();
		
		prodSearch = new CmCdSearch('prodSearch','/br/pr/020/br_pr_020_prod_list_pop.do',
				{searchInput:'i_sProductCd',
			inputCode:'i_sProductCd',
			inputNameKo:'i_sProductNm',
			inputNameEn:'i_arrProduct_RefNm_En',
			inputNameCn:'i_arrProduct_RefNm_Cn',
			callback:funcProdSearch});
		
		function funcProdSearch(data,obj){
			console.log("funcProdSearch : "+this.idx);
		 	$('input[name=PON_attach_pk1]')[this.idx].value=data.v_matnr;
		}
	}
	
	function addButtonEvent(){
		//디자이너 검색 s
		j$('#searchDesigner').on('click',function(){	 
			fn_pop({url:'/br/pr/020/br_pr_020_designer_list_pop.do?&i_sCmFunction=setDesignerPopUpData&i_sInput='+encodeURI($('#i_sDesignerNm').val()), title:'디자이너',width:'700',height:'500'});
		});
		j$('#i_sDesignerNm').on('dblclick',function(){	 
			fn_pop({url:'/br/pr/020/br_pr_020_designer_list_pop.do?&i_sCmFunction=setDesignerPopUpData&i_sInput='+encodeURI($('#i_sDesignerNm').val()), title:'디자이너',width:'700',height:'500'});
		});	 
		j$('#i_sDesignerNm').on('keypress',function(e){
			if(e.keyCode=='13'){
				fn_pop({url:'/br/pr/020/br_pr_020_designer_list_pop.do?i_sCmFunction=setDesignerPopUpData&i_sInput='+encodeURI($('#i_sDesignerNm').val()), title:'디자이너',width:'700',height:'500'});
		}
		});	 
		j$('#delDesigner').on('click',function(){
			$('#i_sDesignerId').val('');
			$('#i_sDesignerNm').val('');
		});
		//디자이너 검색 e
		 
		//외주디자이너 검색 s
		j$('#searchOdmDesigner').on('click',function(){	 
			fn_pop({url:'/br/pr/020/br_pr_020_odm_designer_list_pop.do?i_sCmFunction=setOdmDesignerPopUpData&i_sInput='+encodeURI($('#i_sOdmDesignerNm').val()),		title:'외주 디자이너',width:'700',height:'500'});
		});
		j$('#i_sOdmDesignerNm').on('dblclick',function(){	 
			fn_pop({url:'/br/pr/020/br_pr_020_odm_designer_list_pop.do?i_sCmFunction=setOdmDesignerPopUpData&i_sInput='+encodeURI($('#i_sOdmDesignerNm').val()),		title:'외주 디자이너',width:'700',height:'500'});
		});	 
		j$('#i_sOdmDesignerNm').on('keypress',function(e){
			if(e.keyCode=='13'){
				fn_pop({url:'/br/pr/020/br_pr_020_odm_designer_list_pop.do?i_sCmFunction=setOdmDesignerPopUpData&i_sInput='+encodeURI($('#i_sOdmDesignerNm').val()),	title:'외주 디자이너',width:'700',height:'500'});
		}
		});	 
		j$('#delOdmDesigner').on('click',function(){
			$('#i_sOdmDesignerId').val('');
			$('#i_sOdmDesignerNm').val('');
			$('#i_sOdmDesignerVendorId').val('');
		});
		//외주디자이너 검색 e
		
		
		//주의사항(유형별구분)
		$(".selectFuncType").unbind("change").change(function(){
			var params = {
				"buf1" : "FUNC_TYPE",
				"buf2" : $(this).find("option:selected").val()
			};
			
			fn_getCautionData(params,"FUNC_TYPE");
		});
		
		//주의사항(성분별구분)
		$(".selectFuncIngri").unbind("change").change(function(){
			var params = {
				"buf1" : "FUNC_INGRI",
				"buf2" : $(this).find("option:selected").val()
			};
			
			fn_getCautionData(params,"FUNC_INGRI");
		});
		
		//용법용량
		$(".popup_btn").unbind("click").click(function(){
			fn_pop({url:'/br/pr/020/br_pr_020_howto_pop.do'
						,title:'용법용량 목록'
						,width:'900'
						,height:'800'
					});
		});
	}
	
	function fnSave(status){
		var frm 		=	document.frm;
		
		//기능성 N인 경우, 주의사항, 제조회사, 소재지 등 셋팅
		if($("#i_sFuncYn").val() == 'N'){
			$("#i_sSellerNm").val('(주)신세계 인터네셔널');
			$("#i_sVendorAddr").val($("#i_sAddr1").val() + '<br>' + $("#i_sAddr2").val());
			$("#i_sHowTo").val($(".howtoMethod2").val());
			$("#i_sCaution").val($("#i_sAddCaution").val());
		}
		
		if(!fn_validation()){
			return;
		}
		
		var header = "";
		var text   = "";
		
		if(status == 'AD_REQ_STATUS05'){
			header = "원화등록요청 임시저장";
			text   = "원화등록요청을 임시저장하시겠습니까?";
		}else if(status == 'AD_REQ_STATUS06'){
			header = "원화등록요청";
			text   = "원화등록을 요청하시겠습니까?";
		}

		dhx.confirm({
			header	: header,
			text	: text,
			buttons	: ["예", "아니오"],
			buttonsAlignment:"center"
		}).then(function(answer){
			if(answer){
				var frm 				=	document.frm;
				frm["i_sStatus"].value  = 	status;
				$.ajax({
					url:"/br/pr/020/br_pr_020_ori_save.do"
					,data : $(frm).serialize()
					,dataType : "json"
					,type : "POST"
					,success : function(data){
						location.href="/br/pr/020/br_pr_020_ori_view.do?openMenuCd=MIBRPR021&i_sAdReqId="+$("#i_sAdReqId").val();
					},error : function(jqXHR, textStatus, errorThrown){
					}
					
				});
			}
		});
	}
	
	//수정
	function fnModify(){
		var frm			= document.frm;
		
		frm["i_sActionFlag"].value = "M";
			
		frm.action		= "/br/pr/020/br_pr_020_reg.do";
		frm.submit();
	}
	
	function goTabView(flag){
		var frm = j$("form[name='frm']");
		
		if (flag == "editional") {
			frm.attr("action",	"/br/pr/020/br_pr_020_view.do");
			
		} else if(flag == "original") {
			if($("#i_sStatus").val() == 'AD_REQ_STATUS05' || $("#i_sStatus").val() == 'AD_REQ_STATUS06' || 
					$("#i_sStatus").val() == 'AD_REQ_STATUS07' || $("#i_sStatus").val() == 'AD_REQ_STATUS08' || $("#i_sStatus").val() == 'AD_REQ_STATUS09'){
				frm.attr("action",  "/br/pr/020/br_pr_020_ori_view.do");		
			}else{
				frm.attr("action",  "/br/pr/020/br_pr_020_ori_reg.do");
			}
		}
		
		frm.submit();
	}
	
	//목록으로 돌아가기
	function fnList(){
		location.href="/br/pr/020/init.do?openMenuCd=MIBRPR020";
	}
	
	//벨리데이션 처리
	function fn_validation(){
		if($(".i_sPacking:checked").length == 0){
			fn_s_alertMsg("최소 하나 이상의 패킹구분을 체크해주세요.");
			return;
		}
		
		if(fn_isNull($("#i_sDesignerId").val())){
			fn_s_alertMsg("디자이너 아이디를 선택해주세요.");
			return;
		}
		
		if(fn_isNull($("#i_sOdmDesignerId").val())){
			fn_s_alertMsg("외주용역 디자이너 아이디를 선택해주세요.");
			return;
		}
		
		if(fn_isNull($("#i_sPicReqContent").val())){
			fn_s_alertMsg("원화등록 요청 내역을 입력해주세요.");
			return;
		}
		
		return true;
	}
	
	//주의사항 유형별,성분별 내용 실시간 가져오기
	function fn_getCautionData(params, type){
		$.ajax({
			url:"/br/pr/020/br_pr_020_ori_caution_ajax.do"
			,data : params
			,dataType : "json"
			,type : "POST"
			,success : function(data){
				fn_addCaution(type, data.data.prdCaution);
			},error : function(jqXHR, textStatus, errorThrown){
			}
		});
	}
	
	//주의사항  making HTML
	function fn_addCaution(type, caution){
		if(type=="FUNC_TYPE"){
			prdCautionType = caution;
		}
		if(type=="FUNC_INGRI"){
			prdCautionIngri = caution;
		}
		if((prdCautionType!="") && (prdCautionIngri!="")){
			html = prdCautionCM + "\r4. " + prdCautionType + "\r5. " + prdCautionIngri;
		} else if ((prdCautionType=="") && (prdCautionIngri=="")){
			html = prdCautionCM;
		} else {
			html = prdCautionCM + "\r4. " + prdCautionType + prdCautionIngri;
		}
		$("textarea[name='i_sAddCaution']").text(html);
	}
	
	function fn_selectHowtoMethod_CallBack(data){
		$(".howtoMethod2").val(data);
		fn_s_alertMsg("○○부분을 수정해주세요");
	}
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sAdReqId"	name="i_sAdReqId"		value="${rvo.v_ad_req_id}"/>
	<input type="hidden" id="i_sStatus"		name="i_sStatus"		value="${rvo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"	name="i_sActionFlag"    value="${reqVo.i_sActionFlag}"/>
	<input type="hidden" id="i_sProductCd"	name="i_sProductCd"   	value="${rvo.v_product_cd}"/>
	<input type="hidden" id="i_sRecordId"	name="i_sRecordId"   	value="${rvo.v_record_id}"/>
	<input type="hidden" id="i_sRecordId2"	name="i_sRecordId2"   	value="${rvo.v_record_id}"/>
	<input type="hidden" id="i_sFuncYn"		name="i_sFuncYn"   		value="${rvo.v_func_yn}"/> <!-- 기능성여부Y,N -->
	<input type="hidden" id="i_sReportNum" 	name="i_sReportNum" 	value="${rvo.n_func_no}"/> <!-- 보고서 번호(1,2,3) -->
	<input type="hidden" id="i_sVendorNm"	name="i_sVendorNm"   	value="${empty nfVo.v_vendor_nm ? '' : nfVo.v_vendor_nm}"/> <!-- 제조사 -->
	<input type="hidden" id="i_sSellerNm"	name="i_sSellerNm"   	value=""/> <!-- 판매사 -->
	<input type="hidden" id="i_sVendorAddr"	name="i_sVendorAddr"   	value=""/> <!-- 제조사주소 -->
	<input type="hidden" id="i_sCaution" 	name="i_sCaution" 		value=""/> <!-- 공통주의사항 -->
	<input type="hidden" id="i_sHowTo" 		name="i_sHowTo" 		value=""/> <!-- 용량용법 -->
	<input type="hidden" id="i_sUserId" 	name="i_sUserId" 		value="${reqVo.s_userid}"/> <!-- 로그인유저 -->
	<input type="hidden" id="i_sAddr1" 		name="i_sAddr1" 		value="${nfVo.v_addr1}"/> <!-- 로그인유저 -->
	<input type="hidden" id="i_sAddr2" 		name="i_sAddr2" 		value="${nfVo.v_addr2}"/> <!-- 로그인유저 -->
	<input type="hidden" id="i_sOriginSite" name="i_sOriginSite" 	value="대한민국"/> <!-- 로그인유저 -->


	<div class="content" style="/*padding-left:15px;*/">
		<!-- 탭 영역 -->
		<ul class="sty_tab">
			<li>
				<a href="javascript:goTabView('editional');">
					<span>문안검토</span>
				</a>
			</li>
			<li>
				<a href="javascript:goTabView('original');" class="on">
					<span>원화등록요청</span>
				</a>
			</li>
		</ul>
		<!-- //탭 영역 -->
		
		<!-- 버튼이벤트 영역(상단) -->
		<div class="top_btn_area">
			<!-- 	임시저장일 때(01)	:[수정],		[목록]		-->
			<!-- 	검토요청중일 때(02)	:[요청 취소],	[목록] 		-->
			<!-- 	검토완료일 때		:[목록] 					-->
			<!-- 	반려일 때			:[수정],		[목록] 		-->
			<c:if test="${fn:indexOf(reqVo.s_groupid,'ADMIN') > -1 || (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 && reqVo.s_userid eq rvo.v_reg_user_id)}">
				<c:choose>
					<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS01'}">	<!-- 임시저장인 경우 -->
						<a href="javascript:fnModify();" 				class="btnA bg_dark"><span>수정</span></a>
					</c:when>
					<c:otherwise>
						<a href="javascript:fnSave('AD_REQ_STATUS05');" class="btnA bg_dark"><span>임시저장</span></a>
						<a href="javascript:fnSave('AD_REQ_STATUS06');" class="btnA bg_dark"><span>원화등록요청</span></a>
					</c:otherwise>
				</c:choose>
			</c:if>
			<a href="javascript:fnList();" 		 			class="btnA bg_dark"><span>목록</span></a>	
		</div>
		<!-- //버튼이벤트 영역(상단) -->
	
		<!-- 메인폼 영역 -->
		<div class="sec_cont">
			<h2 class="tit">원화등록요청 기재사항</h2>
			<table class="sty_02">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody class="tbody_product_info">
					<tr>
						<th>* 패킹구분</th>
						<td class="last" colspan="3">
							<label for="i_sVesselYn" style="margin-right:10px;"><input type="checkbox" id="i_sVesselYn"	name="i_sVesselYn"	class="i_sPacking" value="Y"  <c:if test="${rvo.v_vessel_yn eq 'Y'}">checked</c:if>> 용기</label>
							<label for="i_sBoxYn" style="margin-right:10px;"><input 	type="checkbox" id="i_sBoxYn"		name="i_sBoxYn" class="i_sPacking"	value="Y"  <c:if test="${rvo.v_box_yn eq 'Y' }">checked</c:if>> 단상자</label>
							<label for="i_sPaperYn" style="margin-right:10px;"><input 	type="checkbox" id="i_sPaperYn"		name="i_sPaperYn" 	class="i_sPacking" value="Y"  <c:if test="${rvo.v_paper_yn eq 'Y'}">checked</c:if>> 내지 설명서</label>
						</td>
					</tr>
					<tr>
						<th>공정위 보상 문구</th>
						<td><textarea name="i_sPayTx" id="i_sPayTx" class="textarea_sty01" rows="3" cols="5" style="width:100%;">${empty rvo.v_pay_tx ? '디폴트 텍스트' : rvo.v_pay_tx}</textarea></td>
						<th>고객상담실</th>
						<td><input type="text" name="i_sCsPhoneNo" id="i_sCsPhoneNo" class="inp_sty01" value="${empty rvo.v_cs_phone_no ? '080-023-5454' : rvo.v_cs_phone_no}" style="width:98%;" alt="고상팀 전화번호"/></td>
					</tr>
					<tr>
						<th>* 디자이너 아이디</th>
						<td>
							<input type="text"		id="i_sDesignerNm"		name="i_sDesignerNm" value="${rvo.DESIGNER_NM}" class="inp_sty01 required"  />
							<input type="hidden" 	id="i_sDesignerId"		name="i_sDesignerId" value="${rvo.v_designer_id}" />
							<a class="img_btn" style="cursor:pointer;"><img id="searchDesigner" 	name="searchDesigner"	src="${ImgPATH}common/ico_search.png"></a>
							<a class="img_btn" style="cursor:pointer;"><img id="delDesigner" 	name="delDesigner"		src="${ImgPATH}common/ico_delete.png"></a>
						</td>
						<th>* 외주용역 디자이너 아이디</th>
						<td>
							<input type="text" 			id="i_sOdmDesignerNm"		name="i_sOdmDesignerNm" value="${rvo.ODM_DESIGNER_NM}" class="inp_sty01 required"/>
							<input type="hidden" 		id="i_sOdmDesignerId"		name="i_sOdmDesignerId" value="${rvo.v_design_odm_id}" />
							<input type="hidden" 		id="i_sOdmDesignerVendorId"	name="i_sOdmDesignerVendorId" value="${rvo.v_design_vendor_id}" />
							<a class="img_btn" style="cursor:pointer;"><img id="searchOdmDesigner" name="searchOdmDesigner"	src="${ImgPATH}common/ico_search.png"></a>
							<a class="img_btn" style="cursor:pointer;"><img id="delOdmDesigner" 	name="delOdmDesigner"		src="${ImgPATH}common/ico_delete.png"></a>
						</td>
					</tr>
					<tr>
						<th>* 원화 작성 요청 내역</th>
						<td class="last" colspan="3">
							<textarea name="i_sPicReqContent" id="i_sPicReqContent" class="textarea_sty01" rows="3" cols="5" style="width:100%;">${empty rvo.v_pic_req_content ? '' : rvo.v_pic_req_content}</textarea>
						</td>
					</tr>
					<tr class="tr_origin_img">
						<th>기타첨부</th>
						<td colspan="4">
							<span class="fileinput-button" style="margin: 5px;">
								<a href="#" class="btnA bg_dark">
									<span>파일첨부</span>
								</a>
								<input id="admst_upload_btn_0" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
							</span>
							<c:choose>
								<c:when test="${reqVo.i_sActionFlag eq 'M'}">
									<CmTagLib:cmAttachFile uploadCd="ORI" type="reg" recordId="${rvo.v_ad_req_id}" />
								</c:when>
								<c:otherwise>
									<CmTagLib:cmAttachFile uploadCd="ORI" type="reg" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
			
			<br>
			
			<c:choose>
				<c:when test="${rvo.v_func_yn eq 'Y'}">
					<!-- 기능성 데이터  -->
					<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_func_cosmetics.jsp" %>
				</c:when>
				<c:otherwise>
					<h2 class="tit">기타 기재사항</h2>
					<!-- 기능성 데이터 N 기재사항 -->
					<table class="sty_03">
						<colgroup>
							<col width="15%">
							<col width="20%">
							<col width="15%">
							<col width="50%">
						</colgroup>
						<tbody>
							<!-- 기능성 보고서 1,2,3호 공통정보 s(1)-->
							<tr>
								<th rowspan="4">제조원</th>
								<th>제조회사</th>
								<td colspan="2">${nfVo.v_vendor_nm}</td>
							</tr>
							<tr>
								<th>제조국</th>
								<td colspan="2">대한민국</td>
							</tr>
							<tr>
								<th>소재지</th>
								<td colspan="2">${nfVo.v_addr1}<br>${nfVo.v_addr2}</td>
							</tr>
							<tr>
								<th>판매사</th>
								<td colspan="2">(주)신세계 인터네셔널</td>
							</tr>
							<tr>
								<th rowspan="3">사용시 주의 사항</th>
								<td colspan="3">
									<textarea class="textarea_sty01" id="i_sAddCaution" name="i_sAddCaution" rows="10" cols="6" style="width:100%; height:100%;" maxlength="4000">${empty rvo.v_add_caution ? caution[0].COMM_CD_DESC : rvo.v_add_caution}</textarea>
								</td>
							</tr>
							<tr>
								<th><span>유형별 구분</span></th>
								<td colspan="3">
									<div style="width: 100%;">
										<select class="selectFuncType select_sty01" name="i_sFuncTypeCd" style="width: 100%;">
											<c:forEach items="${funcType}" var="vo">
												<option value="${vo.COMM_CD}" <c:if test="${vo.COMM_CD eq rvo.v_func_type_cd}">selected</c:if>>${vo.COMM_CD_NM}</option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th><span>성분별 구분</span></th>
								<td colspan="3">
									<div style="width: 100%;">
										<select class="selectFuncIngri select_sty01" name="i_sFuncIngriCd" style="width: 100%;">
											<c:forEach items="${funcIngri}" var="vo">
												<option value="${vo.COMM_CD}" <c:if test="${vo.COMM_CD eq rvo.v_func_ingri_cd}">selected</c:if>>${vo.COMM_CD_NM}</option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th><span>용법용량</span></th>
								<td style="text-align:center;">
									<div class="btn_area">
										<a href="#" class="btnA bg_dark popup_btn">
											<span>작성</span>
										</a>
									</div>
								</td>
								<td colspan="4">
									<textarea class="textarea_sty01 howtoMethod2" name="i_sHowtoMethod" rows="3" cols="5" style="width:100%;">${empty rvo.v_howto_method ? '' : rvo.v_howto_method}</textarea>
								</td>
							</tr>
							<tr>
								<th>전성분</th>
								<td id="i_sAllMatr" colspan="3">${nfVo.v_matrmemo}</td>
							</tr>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
			
			<!-- 버튼이벤트 영역(하단) -->
			<div class="btn_area verR">
				<!-- 	임시저장일 때(01)	:[수정],		[목록]		-->
				<!-- 	검토요청중일 때(02)	:[요청 취소],	[목록] 		-->
				<!-- 	검토완료일 때		:[목록] 					-->
				<!-- 	반려일 때			:[수정],		[목록] 		-->
				<c:if test="${fn:indexOf(reqVo.s_groupid,'ADMIN') > -1 || (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 && reqVo.s_userid eq rvo.v_reg_user_id)}">
					<c:choose>
						<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS01'}">	<!-- 임시저장인 경우 -->
							<a href="javascript:fnModify();" 				class="btnA bg_dark"><span>수정</span></a>
						</c:when>
						<c:otherwise>
							<a href="javascript:fnSave('AD_REQ_STATUS05');" class="btnA bg_dark"><span>임시저장</span></a>
							<a href="javascript:fnSave('AD_REQ_STATUS06');" class="btnA bg_dark"><span>원화등록요청</span></a>
						</c:otherwise>
					</c:choose>
				</c:if>
				<a href="javascript:fnList();" 		 			class="btnA bg_dark"><span>목록</span></a>	
			</div>
			<!-- //버튼이벤트 영역(하단) -->
		</div>
		<!-- //메인폼 영역 -->
	</div>
</form>

<!-- 파일업로드 관련 s -->
<script id="dot_upload" type="text/x-dot-templete">
<tr>
    <td>
         {{=it.v_attach_lnm}}
        <span class="span_action_type span_hide">R</span>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_id" value="{{=it.v_attach_id}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_lnm" value="{{=it.v_attach_lnm}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_type" value="{{=it.v_attach_type}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_size" value="{{=it.n_attach_size}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_pk1" value="{{=it.v_pk1}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_pk2" value="{{=it.v_pk2}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_pk3" value="{{=it.v_pk3}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_pk4" value="{{=it.v_pk4}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_pk5" value="{{=it.v_pk5}}"/>
      
		<input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer1" value="{{=it.v_buffer1}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer2" value="{{=it.v_buffer2}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer3" value="{{=it.v_buffer3}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer4" value="{{=it.v_buffer4}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer5" value="{{=it.v_buffer5}}"/>
		<input type="hidden" name="{{=it.v_upload_cd}}_attach_pnm" value="{{=it.v_attach_pnm}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_mgtid" value="{{=it.v_attach_mgtid}}"/>
    </td>
    <td>{{=it.n_attach_size}} KB</td>
    <td class="last">
		<a href="javascript:this.onclick;" onclick="javascript:attachDel(j$(this), 'frm', '{{=it.v_upload_cd}}');" class="btn_attach_del" id="{{=it.v_attach_id}}">
			<img src="{{=it.del_img_url}}"/>
		</a>
    </td>
</tr>
</script>
<!-- 파일업로드 관련 e -->