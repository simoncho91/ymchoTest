<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<link rel="stylesheet" href="${ScriptPATH}jfupload/css/jquery.fileupload.css"></link>
<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
</style>
<style>
<!--
.style_disabled {
	background:#ff4646;
	color: #fff;
	cursor: pointer;
	display: inline-block; font-size: 10px; height:14px; line-height: 14px;
	text-align: center; padding-left: 3px; padding-right: 3px; border-radius : 3px;
}
.style_add_refer_product {
	background:#669900;
	color: #fff;
	cursor: pointer;
	margin-left: 10px;
	display: inline-block; font-size: 11px; height:16px; line-height: 16px;
	text-align: center; padding-left: 3px; padding-right: 3px; border-radius : 5px;
}
.div_origin_yn{float: left;width: 35%;}
.div_set_radio{float: left;width: 60%;padding-left:10px;border-left: 1px solid #ddd;}
-->
.table_line {border-top:1px solid #ddd; width: 100%; table-layout:fixed;}
.table_line th {text-align:lect; font-weight:bold; border-left:1px solid #ddd; padding:5px; margin:5; font-size: 15px;}
.table_line th.last {border-right:1px solid #ddd;}
.table_line td {text-align:left; border-left:1px solid #ddd; padding:5px; margin:5px; word-break:keep-all; font-size: 14px; line-height: 24px;}
.table_line td.lastRow {border-bottom:1px solid #ddd;}
.table_line td.last {border-right:1px solid #ddd;}
</style>
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
<script type='text/javascript'>
 j$(function(){
	 	$('.tab_common').find('#RESULT').click(function(e){
	 		var record_id = $('input[name=i_sRecordId]').val();
	 		var prodcut_cd = $('input[name=i_sProductCd]').val();
	 	});
	 	$('.a_save').click(function(e){
	 		fn_save();
	 	});	 	

	 	$('.a_btn_list').click(function(e){
			var frm = j$("form[name='frm']");
			frm.attr("action", WebPATH + "br/pw/010/init.do?openMenuCd=MIBRPW010").submit();
	 		//location.href="/br/pw/010/init.do?openMenuCd=MIBRPW010";
	 	});

		 jfupload.initUpload({
			target : j$("#admst_upload_btn")
			, uploadCd : "REVIEW"
			, index : 0
			, formName : "frm"
			, success : function(attData, uploadCd){
				var attach_size = attData.v_attach_size;
				var attach_lnm = attData.v_attach_lnm;
				var attach_pnm = attData.v_attach_pnm;
				var attach_type = attData.v_attach_type;
				var attach_mgtid = attData.v_attach_mgtid;
				var attach_id = attData.v_attach_id;
				var attach_idx = attData.v_attach_idx;
				var pk1 = j$("input[name='i_sRecordId']").val();
				var table = j$(".table_" + uploadCd);
				
				var obj = {
					v_attach_id : attach_id
					, v_attach_lnm : attach_lnm
					, v_attach_type : attach_type
					, n_attach_size : attach_size
					, v_attach_pnm : attach_pnm
					, v_attach_mgtid : attach_mgtid
					, v_upload_cd : uploadCd
					, v_pk1 : pk1
					, v_pk2 : uploadCd
					, v_pk3 : ""
					, v_pk4 : ""
					, v_pk5 : ""
					, v_buffer1 : j$("input[name='i_sProductCd']").val()
					, v_buffer2 : ""
					, v_buffer3 : ""
					, v_buffer4 : ""
					, v_buffer5 : ""
					//, buffer2 : j$("input[name='i_sPartNo']").val()
					, del_img_url : ImgPATH + "btn/btn_del_small.gif"
				};
				var pagefn = doT.template(document.getElementById("dot_upload").text, undefined, undefined);
				j$(pagefn(obj)).appendTo(table.find("tbody"));
			}
			, isSelfMakeTag : true
			, attachDir : "REVIEW"
			, buffer1 : j$("input[name='i_sProductCd']").val()
		});
		//var recordId = $('input[name=i_sFastTrackYn]').val()=='Y'?$('input[name=i_sReferRecordId]').val():$('input[name=i_sRecordId]').val();
		//var productCd = $('input[name=i_sFastTrackYn]').val() =='Y'?$('input[name=i_sReferProductCd]').val():$('input[name=i_sProductCd]').val();
		//setFuncReportData(recordId,productCd);
		 setFuncReportData($('input[name=i_sRecordId]').val(),$('input[name=i_sProductCd]').val());
 });

//기능성 데이터 셋팅
function setFuncReportData(recordCd, productCd){
	$.ajax({
		url			:	"/br/pr/020/br_pr_020_get_report_ajax.do",
		data		:	{"i_sRecordCd" : recordCd, "i_sProductCd" : productCd},
		async 		: 	true,
       type  		:	"POST",
       dataType	:	"json",
	   success		:	function(data, textStatus, jqXHR) {
			if(data.status == 'ERROR'){
				$(".nFuncNoError").show();
				return;
			}
			
			var data 		=	data.data;
			var funcMatList =	data.funcMatList;
			var reportNum	=	data.n_func_no;	//보고서번호(1,2,3)
			
			console.log(funcMatList);
			
			//기능성 여부 판단
			switch(reportNum){
			case "1" :
				$(".nFuncNo1").show();
				$(".nFuncNo2").hide();
				$(".nFuncNo3").hide();
				$(".nFuncNoError").hide();
				break;
			case "2" :
				$(".nFuncNo1").hide();
				$(".nFuncNo2").show();
				$(".nFuncNo3").hide();
				$(".nFuncNoError").hide();
				break;
			case "3" :
				$(".nFuncNo1").hide();
				$(".nFuncNo2").hide();
				$(".nFuncNo3").show();
				$(".nFuncNoError").hide();
				break;
			default : 
				$(".nFuncNo1").hide();
				$(".nFuncNo2").hide();
				$(".nFuncNo3").hide();
				$(".nFuncNoError").hide();
				break;
			}
			
			//텍스트 셋팅(공통데이터 + 보고서 1호 데이터)
			$(".i_sReportNumText").html(reportNum);										//제품번호
			$("#i_sProductNm").html(data.v_product_nm_ko);								//제품명
			$("#i_sCautionText").html(data.v_add_caution);								//주의사항
			$("#i_sPh").html(data.v_ph);												//ph
			$("#i_sLabNum").html(data.v_lab_no);										//랩넘버
			$(".i_sStandTest").html(data.STAND_TEST);									//고시한기준 및 시험방법
			$("#i_sEffect").html(data.EFFECT);											//1호 효능효과
			$("#i_sFuncType").html(data.FUNC_TYPE);										//유형별구분
			$("#i_sFuncIngri").html(data.FUNC_INGRI);									//성분별구분
			$("#i_sVendorNmText").html(data.v_vendor_nm);								//제조사
			$("#i_sAddrText").html(data.v_addr1 + "<br>" + data.v_addr2);				//소재지
			$("#i_sNationalText").html('대한민국');										//제조국
			
			//----
			var content = ""; 
			for(var i=0; i<funcMatList.length; i++){
				content +=	"<tr>"
				content +=		"<td class='i_sOdmdbNmText'>" + funcMatList[i].v_odmdb_nm_ko + "</td>";
				content +=		"<td class='i_sWeightUnitText'>" + funcMatList[i].v_weight +  " / " + funcMatList[i].v_unit +"</td>";
				content +=	"</tr>"
			}
			$("#i_sFuncMatListTh").attr("rowspan",funcMatList.length+1);
			//$("#i_sFuncMatList").appendTo(content);
			$('#i_sFuncMatList').after(content);
			//----
			
			$(".i_sHowToMethod").html(data.v_howto_method);								//용량용법
			$("#i_sAllMatr").html(data.MATRMEMO_AL);									//전성분
			
			//벨류 셋팅
			$("#i_sCaution").val(data.PRD_CAUTION);										//주의사항(데이터)
			$("#i_sHowTo").val(data.v_howto_method);									//용법용량(데이터)
			$("#i_sVendorNm").val(data.v_vendor_nm);									//제조사
			$("#i_sSellerNm").val("(주)신세계인터네셔널");									//판매사
			$("#i_sVendorAddr").val(data.v_addr1 + "<br>" + data.v_addr2);				//소재지
			
			//보고서1,2,3호 개별 데이터 셋팅
			if(reportNum == 1) {
				$("#i_sFuncMat").html(data.FUNC_MAT1 + (isEmpty(data.FUNC_MAT1) ? "" : " / ") + data.FUNC_MAT2);
	   		} else if(reportNum == 2 || reportNum == 3) {
				$(".i_sBfProductNm").html(data.v_bf_product_nm);						//기심사제품명
				$(".i_sCertiNo").html(data.v_certi_no);									//심사번호
				$(".i_sCertDtm").html(data.v_cert_dtm);									//이미심사받은품목의 결과 통지일
				$(".i_sEffectMatNm").html(data.v_effect_mat_nm);						//활성물질용량
				$(".i_sWaterProof").html(data.WATER_PROOF);								//내수성지수
				$("#i_sFuncMat").html(data.v_func_mat_etc);	
				//효능효과 데이터 가져오기
				setEffectData(recordCd, productCd);
			}

			$('#tDocReport').show();
			var funcModifyYn = "${reqVo.i_sFuncModifyYn}";			
			if(funcModifyYn == 'Y'){
				var reportDtm = (fn_isNull(data.v_report_dtm)?'':fn_getFormatDate(data.v_report_dtm,'-'));
				//var reportDtm = '';
				$('#i_tdReportNo').append("<input style=\"padding: 0 10px;height: 33px;border: 1px solid #ddd;\" id=\"i_sReportNo\" name=\"i_sReportNo\" value=\""+(fn_isNull(data.v_report_no)?'':data.v_report_no)+"\"></input>");
				$('#i_tdReportDtm').append("<input type=\"text\" id=\"i_sReportDtm\" name=\"i_sReportDtm\" value=\""+reportDtm+"\" class=\"inbox calendar\" alt=\"보고일\" style=\"width: 152px; cursor: pointer;\" readOnly />");
				fn_calendarSet("i_sReportDtm");
				
			}else{
				$('#i_tdReportNo').html(data.v_report_no);
				$('#i_tdReportDtm').html(fn_getFormatDate(data.v_report_dtm,'-'));
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			fn_s_failMsg(jqXHR, textStatus, errorThrown);
	    	if(fail) fail();
	    },
	    complete: function(data) {
	    //if (bProgressOnEvent) layoutMain.progressOff();
	    }
	});
}
function fn_validation(){
	if(fn_s_inputLengthChk($('input[name=i_sReportNo]').val(),"보고번호",30)){
		return true;
	}
	return false;
}
function fn_save(){
	//if(fn_validation()) return;
	fn_s_saveMessage("국내검토","국내검토 보고서를 저장하시겠습니까?"
			,"/br/pw/010/br_pw_010_t3_save_report.do"
			,$('#frm').serialize()
			,function(){
				var frm = j$("form[name='frm']");
				frm.attr("action", "/br/pw/010/br_pw_010_t3_view.do").submit();
			}
			,fn_validation);
}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sReferRecordId" 	value="${rvo.v_refer_record_id}"/>
	<input type="hidden" name="i_sReferProductCd" 	value="${rvo.v_refer_product_cd}"/>	
	<input type="hidden" name="i_sFastTrackYn" 	value="${rvo.v_fast_track_yn}"/>	
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />	
	<input type="hidden" name="i_sReceipStatus" value="" />
	
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">		
			<a href="#none" class="btnA bg_dark a_save"><span>저장</span></a>
			<a href="#none" class="btnA bg_dark a_btn_list"><span>목록</span></a>
		</c:if>
	</div>
	<div class="pd_top10"></div>	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/010/br_pw_010_tab.jsp" %>	
	<div class="pd_top10"></div>
	
	<div class="sec_cont">
		<!-- 기능성 데이터  -->
		<c:if test="${rvo.v_func_yn eq 'Y'}">
			<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_func_cosmetics.jsp" %>
		</c:if>		
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 			</li> -->
<!-- 		</ul> -->
	</div>
	<div class="sec_cont">
		<h2 class="tit">보고자료</h2>
		<table class="sty_03">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tbody id="tDocReport" style="display:none">
				<tr>
					<th>보고일련번호</th>
					<td id="i_tdReportNo"></td>
					<th>보고일자</th>
					<td id="i_tdReportDtm"></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td id="tdReportFile" colspan='3'>
						<div>
							<c:if test="${reqVo.i_sFuncModifyYn eq 'Y'}">
								<span class="fileinput-button" style="margin: 5px;">
									<a href="#" class="btnA bg_dark"><span>파일첨부</span></a>
									<input id="admst_upload_btn" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />						
								</span>
							</c:if>
							<CmTagLib:cmAttachFile uploadCd="REVIEW" type="${reqVo.i_sFuncModifyYn eq 'Y'?'reg':'viewLog'}" recordId="${reqVo.i_sRecordId}" pk1="${reqVo.i_sProductCd}" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<ul class="btn_area">
			<li class="right">
				<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
					<a href="#none" class="btnA bg_dark a_save"><span>저장</span></a>
					<a href="#none" class="btnA bg_dark a_btn_list"><span>목록</span></a>
				</c:if>
			</li>
		</ul>
	</div>	
</form>

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

        <input type="hidden" name="{{=it.v_upload_cd}}_attach_mgtid" value="{{=it.v_attach_mgtid}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_mgtid" value="{{=it.v_attach_mgtid}}"/>

        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer1" value="{{=it.v_buffer1}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer2" value="{{=it.v_buffer2}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer3" value="{{=it.v_buffer3}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer4" value="{{=it.v_buffer4}}"/>
        <input type="hidden" name="{{=it.v_upload_cd}}_attach_buffer5" value="{{=it.v_buffer5}}"/>
    </td>
    <td>{{=it.n_attach_size}} KB</td>
    <td class="last">
		<a href="javascript:this.onclick;" onclick="javascript:attachDel(j$(this), 'frm', '{{=it.v_upload_cd}}');" class="btn_attach_del" id="{{=it.v_attach_id}}">
			<img src="{{=it.del_img_url}}"/>
		</a>
    </td>
</tr>
</script>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
