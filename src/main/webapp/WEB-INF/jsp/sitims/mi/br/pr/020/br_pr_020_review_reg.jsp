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

<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_review_reg.js"></script>
<script type="text/javascript">
	j$(function(){
		fn_init();
	});
	
	function fn_init(){
		var index = j$(".tbody_product_info").length;
		
		for(var i=0; i<index; i++){
			console.log(i);
			jfupload.initUpload({
				target : j$("#admst_upload_btn_" + i)
				, uploadCd : "REVIEW"
				, index : i
				, formName : "frm"
				, success : OreqReg.addEvent.attachSuccEvent
				, isSelfMakeTag : true
				, attachDir : "REVIEW"
				, buffer1   : j$("input[name='i_arrProduct_Refcd']").eq(i).val()
			});
		}
	}
	
	function fn_save(){
		var postParam = $('#frm').serialize();
		$.ajax({
			 url: "/br/pr/020/br_pr_020_review_save_ajax.do",
			 data: postParam,
			 type: "POST",
			 dataType: "json",
			 success:function(data){
				if(data.status=="success"){
					fn_s_alertMsg('저장되었습니다.',function(){
						location.href = "/br/pr/020/br_pr_020_review_view.do?i_sRecordId=" + $("#i_sRecordId").val()  + '&i_sProductCd=' + $("#i_sProductCd").val() + '&i_sAdReqId=' + $("#i_sAdReqId").val();
					});
				}
			},error : function(jqXHR, textStatus, errorThrown){
		        fn_s_failMsg(jqXHR, textStatus, errorThrown);
			}
		});
	}
	
	//이전(상세)으로 돌아가기
	function fnPrev(){
		location.href="/br/pr/020/br_pr_020_review_view.do?i_sRecordId=" + $("#i_sRecordId").val()  + "&i_sProductCd=" + $("#i_sProductCd").val() + "&i_sAdReqId=" + $("#i_sAdReqId").val();
	}
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sAdReqId"		name="i_sAdReqId"		value="${reqVo.i_sAdReqId}"/>
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${reqVo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${i_sActionFlag}"/>
	<input type="hidden" id="i_sRecordId"		name="i_sRecordId"   	value="${reqVo.i_sRecordId}"/>
	<input type="hidden" id="i_sUserNm"			name="i_sUserNm"		value="${reqVo.s_usernm}"/>
	<input type="hidden" id="i_sProductCd"		name="i_sProductCd"		value="${reqVo.i_sProductCd}"/>
	<input type="hidden" id="i_sPartNo"			name="i_sPartNo" 		value="" />
	
	<div class="sec_cont">
		<ul class="btn_area">
			<li class="right">
				<a href="javascript:fn_save();" class="btnA bg_dark"><span>저장</span></a>
				<a href="javascript:fnPrev();" class="btnA bg_dark"><span>이전</span></a>
			</li>
		</ul>
	
		<!-- 실증자료 업로드 s -->
		<h2 class="tit">실증자료항목</h2>
		<table class="sty_02">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tbody>
				<c:forEach items="${attachType}" var="at" varStatus="s">
					<tr class="tr_origin_img tbody_product_info">
						<th>${at.COMM_CD_NM}</th>
						<td colspan="4">
							<span class="fileinput-button" style="margin: 5px;">
								<a href="#" class="btnA bg_dark">
									<span>파일첨부</span>
								</a>
								<input id="admst_upload_btn_${s.index}" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
							</span>
							<CmTagLib:cmAttachFile uploadCd="REVIEW" type="reg" recordId="${reqVo.i_sAdReqId}" pk1="${s.index}" />
							<textarea rows="4" style="width: 71%;" name="i_arrContent" onkeyup="fnMsgLength(this, 'span_comment_length${s.index}', 4000);">${commentList[s.index].v_content}</textarea>
							<div class="fl">
								<span id="span_comment_length${s.index}">0</span>/4,000byte
							</div>
							<input type="hidden" name="i_arrProduct_Refcd" id="i_arrProduct_Refcd" value="${s.index}"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 실증자료 업로드 e -->
		
		
		<!-- 실증자료 업로드 s -->
		<%-- <h2 class="tit">실증자료항목</h2>
		<table class="sty_02">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tbody>
				<c:forEach items="${attachType}" var="at" varStatus="s">
					<tr class="tr_origin_img tbody_product_info">
						<th>${at.COMM_CD_NM}</th>
						<td colspan="4">
							<span class="fileinput-button" style="margin: 5px;">
								<a href="#" class="btnA bg_dark">
									<span>파일첨부</span>
								</a>
								<input id="admst_upload_btn_${s.index}" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
							</span>
							<CmTagLib:cmAttachFile uploadCd="REVIEW" type="reg" recordId="${reqVo.i_sAdReqId}_${s.index}" />
							<textarea rows="4" style="width: 71%;" name="i_arrContent">${commentList[s.index].v_content}</textarea>
							<input type="hidden" name="i_arrProduct_Refcd" id="i_arrProduct_Refcd" value="${reqVo.i_sAdReqId}_${s.index}"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table> --%>
		<!-- 실증자료 업로드 e -->
		
		<ul class="btn_area">
			<li class="right">
				<a href="javascript:fn_save();" class="btnA bg_dark"><span>저장</span></a>
				<a href="javascript:fnPrev();" class="btnA bg_dark"><span>이전</span></a>
			</li>
		</ul>
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