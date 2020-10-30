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
<script type="text/javascript" src="${ScriptPATH}mi/nb/nb/030/nb_nb_030_reg.js"></script>
<script type="text/javascript">
var prodSearch;

j$(function(){
	fn_init();
	
	CKEDITOR.replace( 'editor', {height : '400'} );// ckeditor 사용시
})

function fn_init(){
	var index = j$(".tbody_product_info").length;
	
	for(var i=0; i<index; i++){
		 jfupload.initUpload({
			target : j$("#admst_upload_btn_" + i)
			, uploadCd : "NB"
			, index : i
			, formName : "frm"
			, success : OreqReg.addEvent.attachSuccEvent
			, isSelfMakeTag : true
			, attachDir : "NB"
		});
	}
	
	prodSearch = new CmCdSearch('prodSearch','/nb/nb/030/nb_nb_030_prod_list_pop.do',
			{searchInput:'i_sProductCd',
		inputCode:'i_sProductCd',
		inputNameKo:'i_sProductNm',
		inputNameEn:'i_arrProduct_RefNm_En',
		inputNameCn:'i_arrProduct_RefNm_Cn',
		callback:funcProdSearch});
	
	function funcProdSearch(data,obj){
		console.log("funcProdSearch : "+this.idx);
	 	$('input[name=NB_attach_pk1]')[this.idx].value=data.v_matnr;
	}
}

//임시저장, 저장
function fnSave(status){
	if(!fn_validation()){
		return;
	}
	
	var frm 	=	document.frm;
	var reqText = 	CKEDITOR.instances.editor.getData();
	
	//내용 담기
	$("#i_sReqText").val(reqText);
	
	//등록, 수정화면 플래그 처리
	if($('#i_sActionFlag').val() == ""){
		frm["i_sActionFlag"].value = "R";
	}
	
	dhx.confirm({
		header: "공지사항",
		text: '공지사항을 등록하시겠습니까?',
		buttons: ["예","아니오"],
		buttonsAlignment:"center"
	}).then(function(answer){
		if(answer){
			var frm 				=	document.frm;
			var postParam = $('#frm').serialize();
			frm["i_sStatus"].value  = 	status;
			
			$.ajax({
			     url: "/nb/nb/030/nb_nb_030_save.do",
			     data: postParam,
			     type: "POST",
			     dataType: "json",
			     success:function(data){
			    	if(data.status=="success"){
				 		//location.replace('/si/im/010/init.do?openMenuCd=MISIIM010');
				 		fn_s_alertMsg('저장되었습니다.',function(){
				 			location.replace('/nb/nb/030/init.do');
				 		});
			    	}
		    	 },error : function(jqXHR, textStatus, errorThrown){
			        fn_s_failMsg(jqXHR, textStatus, errorThrown);
				}
		     });
		}
	});
}

//벨리데이션 처리
function fn_validation(){
	var chkError=false;
	var errorMsg="";
	if(fn_isNull($('#i_sTitle').val())){
		fn_s_alertMsg("제목을 입력해주세요.");
		return false;
	}
	
	//의뢰내용 처리
	if(fn_isNull(CKEDITOR.instances.editor.getData())) {
		fn_s_alertMsg("내용을 입력해주세요.");
		return false;
	}
	
	if(chkError){
		fn_s_alertMsg("처리가 완료되지 않았습니다");
		return false;
	}
	return true;
}
	
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${reqVo.i_sStatus }"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${reqVo.i_sActionFlag}"/>
	<input type="hidden" id="i_sRecordCd"		name="i_sRecordId"   	value="${rvo.v_record_id}"/>
	<input type="hidden" id="i_sUserId" 		name="i_sUserId" 		value="${reqVo.s_userid}"/>
	<input type="hidden" id="i_sUserNm" 		name="i_sUserNm" 		value="${reqVo.s_usernm}"/>
	<input type="hidden" id="i_sRegDtm" 		name="i_sRegDtm" 		value="${reqVo.i_sRegDtm}"/>
	<input type="hidden" id="i_sReqText" 		name="i_sReqText" 		value=""/>
	
	<div id="layer_simple_view" style="position:absolute;width:360px;display:none;z-index:10000;">
		<div class="div_status" style="padding:12px;border:2px solid #FAED7D;background:#FAED7D;">
			<span style="line-height: 1.5em;font-weight: bold; color:black;" class="span_text"></span>
		</div>
	</div>
	<div class="sec_cont" style="/*padding-left:15px;*/">
	  <h2 class="tit">공지사항 등록</h2>
		<div class="top_btn_area">
			<a href="javascript:fnSave('NB_REQ_STATUS02');" class="btnA bg_dark"><span>저장</span></a>
			<a href="../030/init.do?openMenuCd=MINBNB030" 	class="btn_list btnA bg_dark"><span>목록</span></a>
		</div>
	<table class="sty_02">
		<colgroup>
			<col width="15%">
			<col width="85%">
		</colgroup>
		<tbody class="tbody_product_info">
			<tr>
				<th>* 제목</th>
				<td class="last" colspan="3">
					<input type="text" 	 name="i_sTitle" id="i_sTitle" value="${rvo.v_title}" class="inp_sty01 required" alt="제목" style="width: 98%;"/>
				</td>
			</tr>
				<th>* 등록자</th>
				<td class="inp_sty01">${reqVo.s_usernm}</td>
			</tr>
				<!-- 에디터 s -->
				<th>내용</th>
				<td colspan="3"><div id="editor" class="inp_sty01">${rvo.c_content}</div></td>
				<!-- 에디터 e -->
			</tr>
			<tr class="inp_sty01">
				<th>파일첨부</th>
				<td colspan="4">
					<span class="fileinput-button" style="margin: 5px;">
						<a href="#" class="btnA bg_dark">
							<span>파일첨부</span>
						</a>
						<input id="admst_upload_btn_0" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
					</span>
					<c:choose>
						<c:when test="${reqVo.i_sActionFlag eq 'M'}">
							<CmTagLib:cmAttachFile uploadCd="NB" type="reg" recordId="${rvo.v_record_id}" />
						</c:when>
						<c:otherwise>
							<CmTagLib:cmAttachFile uploadCd="NB" type="reg" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
		<div class="btn_area verR">
			<a href="javascript:fnSave('NB_REQ_STATUS02');" class="btnA bg_dark"><span>저장</span></a>
			<a href="../030/init.do?openMenuCd=MINBNB030" 	class="btn_list btnA bg_dark"><span>목록</span></a>
		</div>
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
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_pnm" value="{{=it.v_attach_pnm}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_mgtid" value="{{=it.v_attach_mgtid}}"/>
    
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