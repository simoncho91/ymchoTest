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
<script src="/ckeditor/ckeditor.js"></script><!-- ckeditor 사용시 -->
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_reg.js"></script>
<script type="text/javascript">
var prodSearch;

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

j$(function(){
	fn_init();
	
	CKEDITOR.replace( 'editor', {height : '400'} );// ckeditor 사용시
	
	$("#i_sRaEmail").val($("#i_sRaId option:selected").attr("data-email"));
});

function fn_init(){
	var index = j$(".tbody_product_info").length;
	
	for(var i=0; i<index; i++){
		 jfupload.initUpload({
			target : j$("#admst_upload_btn_" + i)
			, uploadCd : "AD"
			, index : i
			, formName : "frm"
			, success : OreqReg.addEvent.attachSuccEvent
			, isSelfMakeTag : true
			, attachDir : "AD"
			, buffer1 : j$("input[name='i_sAdReqId']").val()
		});
	}
	
	prodSearch = new CmCdSearch('prodSearch','/br/pr/020/br_pr_020_prod_list_pop.do',
			{searchInput:'i_sProductCd',
		inputCode:'i_sProductCd',
		inputNameKo:'i_sProductNm',
		inputNameEn:'i_arrProduct_RefNm_En',
		inputNameCn:'i_arrProduct_RefNm_Cn',
		callback:funcProdSearch});
	
	function funcProdSearch(data,obj){
		console.log("funcProdSearch : "+this.idx);
	}
	
	
	$("#i_sRaId").on('change',function(){
		$("#i_sRaEmail").val($("#i_sRaId option:selected").attr("data-email"));
	});
}

//임시저장, 검토요청
function fnSave(status){
	if(!fn_validation()){
		return;
	}
	
	var frm 	=	document.frm;
	var reqText = 	CKEDITOR.instances.editor.getData();
	
	//의뢰내용 담기
	$("#i_sReqText").val(reqText);
	
	//컬러링된 금지어 필터링 담기
	j$("#i_sFilteringText").val(j$("#td_filt_Txt").html());
	
	//등록, 수정화면 플래그 처리
	if($('#i_sActionFlag').val() == ""){
		frm["i_sActionFlag"].value = "R";
	}
	
	var alertText = '';
	
	if (status == 'AD_REQ_STATUS01') {
		alertText	=	'임시저장을 완료하시겠습니까?';
	} else if(status == 'AD_REQ_STATUS02') {
		alertText	=	'검토요청을 완료하시겠습니까?';
	} else if(status == 'AD_REQ_STATUS04') {
		alertText	=	'문안검토를 생략하시겠습니까?';
	}
	
	dhx.confirm({
		header	: "문안검토요청",
		text	: alertText,
		buttons	: ["예", "아니오"],
		buttonsAlignment:"center"
	}).then(function(answer){
		if(answer){
			var frm 				=	document.frm;
			frm["i_sStatus"].value  = 	status;
			$.ajax({
				url:"/br/pr/020/br_pr_020_save.do"
				,data : $(frm).serialize()
				,dataType : "json"
				,type : "POST"
				,success : function(data){
					var adReqId = $("#i_sActionFlag").val() == "R" ? data.data.i_sAdReqId : $("#i_sAdReqId").val();
					console.log("성공");
					location.replace("/br/pr/020/br_pr_020_view.do?openMenuCd=MIBRPR020&i_sAdReqId="+adReqId);
				},error : function(jqXHR, textStatus, errorThrown){
					console.log("실패");
				}
			});
		}
	});
}

// 금지어 필터링 ajax
function fnFiltering(flag){
   	$.ajax({
		  url		: "/br/er/040/br_er_040_filtering_ajax.do"
		, type		: "post"
		, data		: { i_sReqText :	CKEDITOR.instances.editor.getData()} 
		, dataType	: "json"
		, async		: false
		, success	: function(json){
			if(json.status == "OK"){
				j$("#tr_filtTxt").find("td").html(json.data.text);
				
				var count		=	json.data.count;
				var forbidList	=	json.data.forbidList;
				var proofList	=	json.data.proofList;
				var fLen		=	forbidList.length;
				var pLen		= 	proofList.length;
				
				if(fLen > 0){
					var pagefn = doT.template(document.getElementById("dot_result_wordlist").text, undefined, undefined);
					j$("#td_forbid_sum").html(pagefn(forbidList));
					
					/* fnRemoveBr(".removeBr1");
					fnRemoveBr(".removeBr2");
					fnRemoveBr(".removeBr3"); */
				}	else {
					j$("#td_forbid_sum").html("::금지어 요약이 없습니다.::");
				}
				if(pLen > 0){
					var pagefn2 = doT.template(document.getElementById("dot_result_prooflist").text, undefined, undefined);
					j$("#td_proof_sum").html(pagefn2(proofList));
				} else {
					j$("#td_proof_sum").html("::실증 필요 단어 요약이 없습니다.::");
				}
				
				if(count >= 6){
					j$("input[name='i_sReqAble']").val("N");
				} else {
					if(flag != 'save'){ // 일반 필터링 경우에는 갯수 알림해줌. 저장시 거치는 필터링은 그냥통과
					}
					j$("input[name='i_sReqAble']").val("Y");
				}
			}
		}
	});
}

/* function fnRemoveBr(tagName){
	var str = j$(tagName).html();
	j$(tagName).html(str.replaceAll("/<.+?>/", "").replaceAll("\n", "<br/>").replaceAll("\r", ""));
} */

//벨리데이션 처리
function fn_validation(){
	var chkError=false;
	var errorMsg="";
	if(fn_isNull($('#i_sTitle').val())){
		fn_s_alertMsg("제목을 입력해주세요.");
		return false;
	}
	
	//제품코드 선택
	if(fn_isNull($('#i_sProductCd').val())){
		fn_s_alertMsg("제품코드를 선택해주세요.");
		return false;
	}
	
	//담당 RA 선택
	if(fn_isNull($('#i_sRaId').val())) {
		fn_s_alertMsg("담당 RA를 선택해주세요.");
		return false;
	}
	
	//의뢰내용 처리
	if(fn_isNull(CKEDITOR.instances.editor.getData())) {
		fn_s_alertMsg("의뢰내용을 입력해주세요.");
		return false;
	}
	
	//의뢰내용 글자수 제한
	if(!check_length(CKEDITOR.instances.editor.getData(), 3700)){
		fn_s_alertMsg("의뢰내용이 최대제한 글자수를 초과했습니다.");
		return;
	}
	
	if(chkError){
		fn_s_alertMsg("처리가 완료되지 않았습니다");
		return false;
	}
	return true;
}

//목록으로 돌아가기
function goList(){
	location.href="/br/pr/020/init.do?openMenuCd=MIBRPR020";
}

	
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sAdReqId"		name="i_sAdReqId"		value="${rvo.v_ad_req_id}"/>
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${reqVo.i_sStatus }"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${reqVo.i_sActionFlag}"/>
	<input type="hidden" id="i_sRecordCd"		name="i_sRecordCd"   	value="${reqVo.i_sRecordCd}"/>
	<input type="hidden" id="i_sUserId" 		name="i_sUserId" 		value="${reqVo.s_userid}"/>
	<input type="hidden" id="i_sUserNm" 		name="i_sUserNm" 		value="${reqVo.s_usernm}"/>
	<input type="hidden" id="i_sRegId" 			name="i_sRegId" 		value="${reqVo.s_userid}"/>
	<input type="hidden" id="i_sRegDtm" 		name="i_sRegDtm" 		value="${reqVo.i_sRegDtm}"/>
	<input type="hidden" id="i_sReqText" 		name="i_sReqText" 		value=""/>
	<input type="hidden" id="i_sFilteringText" 	name="i_sFilteringText" value=""/> 
	<input type="hidden" id="i_sRaEmail" 		name="i_sRaEmail" 		value="${reqVo.i_sRaEmail}"/> <!-- RA이메일 -->
	<input type="hidden" id="i_sProductNmMail"	name="i_sProductNmMail" value="${rvo.v_product_nm_ko}"/> <!-- 제품명(이메일용) -->
			
	<div class="content">
		<!-- 버튼이벤트 영역(상단) -->
		<div class="top_btn_area" style="z-index:1;">
			<a href="javascript:fnFiltering();" 			class="btnA bg_dark">금지어 필터링</a>
			<a href="javascript:fnSave('AD_REQ_STATUS01');" class="btnA bg_dark">임시저장</a>
			<a href="javascript:fnSave('AD_REQ_STATUS02');" class="btnA bg_dark">검토요청</a>
			<a href="javascript:fnSave('AD_REQ_STATUS04');" class="btnA bg_dark">문안검토생략</a>
			<a href="javascript:goList();" 					class="btnA bg_dark">목록</a>
		</div>
		<!-- //버튼이벤트 영역(상단) -->

		<!-- 메인폼 영역 -->
		<div class="sec_cont" style="/*padding-left:15px;*/">
			<h2 class="tit">문안검토 기본 기재사항</h2>
			<table class="sty_02">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody class="tbody_product_info">
					<tr>
						<th>* 제목</th>
					<td colspan="3">
						<input type="text" 	 name="i_sTitle" id="i_sTitle" value="${rvo.v_title}" class="inp_sty01 required" alt="제목" style="width: 100%;"/>
					</td>
					</tr>
					<tr>
						<th>* 제품코드</th>
						<td>
							<input type="text" name="i_sProductCd" id="i_sProductCd" value="${rvo.v_product_cd}" class="inp_sty01 required" 
							onkeypress="prodSearch.keyPress(event, this);" onclick="prodSearch.productPop(this);"
							alt="제품코드" style="width: 124px; cursor: pointer;" />									
							<a class="img_btn" style="cursor:pointer;"><img id="btn_pjt_ref_pop" name="btn_pjt_ref_pop" src="${ImgPATH}common/ico_search.png" class="img_btn" onclick="prodSearch.productPop(this);"></a>									
							<a class="img_btn" style="cursor:pointer;"><img id="btn_pjt_ref_del" name="btn_pjt_ref_del" src="${ImgPATH}common/ico_delete.png" class="img_btn del_product" onclick="prodSearch.delProduct(this);"></a>
						</td>
						<th>제품명</th>
						<td id="i_sProductNmText" class="delY">${rvo.v_product_nm_ko}</td>
					</tr>
					<tr>
						<th>제품연구원</th>
						<td id="i_sVendorId" class="delY">${rvo.VENDOR_ID}</td>
						<th>등록일</th>
						<td class="last" >
							<c:choose>
								<c:when test="${i_sActionFlag eq 'M'}">
								${rvo.v_reg_dtm}
								</c:when>
								<c:otherwise>
								${reqVo.i_sRegDtm}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>브랜드</th>
						<td id="i_sBrandCd"		class="delY">${rvo.BRAND_NM}</td>
						<th>출시시기</th>
						<td id="i_sLaunch_dtm"	class="delY">
						</td>
					</tr>
					<tr>
						<th>마케터(등록자)</th>
						<td class="TouTr">${reqVo.s_usernm}</td>
						<th>용기타입</th>
						<td id="i_sBottleType"	class="TouTr delY">${rvo.CNTR_FORM}</td>
					</tr>
					<tr class="TouTr">
						<th>제조일로부터 사용기한</th>
						<td id="i_sTou_use" 	class="delY">${rvo.v_shelf_life}</td>	
						<th>개봉 후 사용기간</th>
						<td id="i_sOpenTou_use" class="delY">${rvo.v_pao}</td>
					</tr>
					<tr>
						<th>종류</th>
						<td id="i_sType" class="delY"></td>
						<th>* 담당 RA</th>
						<td>
							<select id="i_sRaId" name="i_sRaId" class="select_sty01" style="width:30%;">
								<option value="">선택</option>
								<c:forEach  items="${raId}"  var="ra">
									<option value="${ra.v_user_id}" <c:if test="${rvo.v_ra_id eq ra.v_user_id}">selected</c:if> data-email="${ra.v_email}">${ra.v_user_nm}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>소구범위</th>
						<td id="i_sSogu"></td>
						<th>포장범위</th>
						<td id="i_sPacketUnit"></td>
					</tr>
					<tr>
						<!-- 에디터 s -->
						<th>* 의뢰내용</th>
						<td colspan="3"><div id="editor">${rvo.v_ad_content}</div></td>
						<!-- 에디터 e -->
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
									<CmTagLib:cmAttachFile uploadCd="AD" type="reg" recordId="${rvo.v_ad_req_id}" />
								</c:when>
								<c:otherwise>
									<CmTagLib:cmAttachFile uploadCd="AD" type="reg" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr id="tr_filtTxt" class="C020_need">
						<th>금지어 필터링</th>
						<td colspan="3" id="td_filt_Txt" class="last">${rvo.v_filtering_text}</td>
					</tr>
					<tr>
						<td colspan="4">${introText}</td>
					</tr>
					<tr>
						<td class="last sub_table_area" colspan="4" id="td_forbid_sum">
							<c:choose>
								<c:when test="${not empty rvo.forbidList }">
									<table class="sty_03" style="table-layout:fixed">
										<colgroup>
											<col width="25%;">
											<col width="25%;">
											<col width="25%;">
											<col width="25%;">
										</colgroup>
										<tbody>
										<tr>
											<th class="bdl_n">금지어</th>
											<th>유사 금지표현</th>
											<th>대체표현/실증표현</th>
											<th>유사 처분사례</th>
										</tr>
									<c:forEach items="${rvo.forbidList }" var="vo">
										<tr>
											<td class="ta_c bdl_n">
												<font style="color:${vo.v_color};font-weight:bold;">${vo.v_word }</font>
											</td>
											<td class="ta_c">${cfn:removeHTMLChangeBr(vo.v_similar_word)}</td>
											<td class="ta_c">${cfn:removeHTMLChangeBr(vo.v_explain)}</td>
											<td class="ta_c">${cfn:removeHTMLChangeBr(vo.v_example)}</td>
										</tr>
									</c:forEach>
										</tbody>
									</table>
								</c:when>
								<c:otherwise>
									::금지어 요약이 없습니다.::
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>실증대상</th>
						<td id="td_proof_sum" colspan="3">
							<c:choose>
								<c:when test="${not empty rvo.proofList}">
									<c:forEach items="${rvo.proofList }" var="vo" varStatus="s"><c:if test="${s.count ne 1}">, </c:if><font style="color:${vo.v_color};font-weight:bold;">${vo.v_word}</font></c:forEach>
								</c:when>
								<c:otherwise>
									::실증대상 요약이 없습니다.::
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 버튼이벤트 영역(하단) -->
			<div class="btn_area verR">
				<a href="javascript:fnFiltering();" 			class="btnA bg_dark">금지어 필터링</a>
				<a href="javascript:fnSave('AD_REQ_STATUS01');" class="btnA bg_dark">임시저장</a>
				<a href="javascript:fnSave('AD_REQ_STATUS02');" class="btnA bg_dark">검토요청</a>
				<a href="javascript:fnSave('AD_REQ_STATUS04');" class="btnA bg_dark">문안검토생략</a>
				<a href="javascript:goList();" 					class="btnA bg_dark">목록</a>
			</div>
			<!-- //버튼이벤트 영역(하단) -->
		</div>
		<!-- //메인폼 영역 -->
	</div>
</form>

<!-- 금지어필터링 관련 s -->
<script type="text/javascript" src="${ScriptPATH }doT.min.js"></script>
<script id="dot_result_wordlist" type="text/x-dot-template">
<table class="sty_03" style="table-layout:fixed">
	<colgroup>
		<col width="25%;">
		<col width="25%;">
		<col width="25%;">
		<col width="25%;">
	</colgroup>
	<tbody>
		<tr>
			<th class="bdl_n">금지어</th>
			<th>유사 금지표현</th>
			<th>대체표현/실증표현</th>
			<th>유사 처분사례</th>
		</tr>
{{for(var i in it){ }}
	<tr>
		<td class="ta_c bdl_n">
			<font style="color:{{=it[i].v_color}};font-weight:bold;">{{=it[i].v_word}}</font>
		</td>
		<td class="ta_c removeBr1">{{=it[i].v_similar_word}}</td>
		<td class="ta_c removeBr2">{{=it[i].v_explain}}</td>
		<td class="ta_c removeBr3">{{=it[i].v_example}}</td>
	</tr>
{{} }}
	</tbody>
</table>
</script>

<script id="dot_result_prooflist" type="text/x-dot-template">
{{for(var i in it){ }}
	<font style="color:{{=it[i].v_color}};font-weight:bold;">
		{{=it[i].v_word}}, 
	</font>
{{} }}
</script>
<!-- 금지어필터링 관련 e -->

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