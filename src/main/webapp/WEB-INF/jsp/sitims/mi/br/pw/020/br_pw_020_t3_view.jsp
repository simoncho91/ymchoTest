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

	 	$('.btn_list').click(function(e){
	 		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
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
   			
   			//보고서 1,2,3호 개별 데이터 셋팅
   			if(reportNum == 1){
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
				$('#i_tdReportNo').append("<input id=\"i_sReportNo\" name=\"i_sReportNo\" value=\""+(fn_isNull(data.v_report_no)?'':data.v_report_no)+"\"></input>");
				$('#i_tdReportDtm').append("<input id=\"i_sReportDtm\" name=\"i_sReportDtm\" value=\""+(fn_isNull(data.v_report_dtm)?'':data.v_report_dtm)+"\"></input>");
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
  
</script>

<form id="frm" name="frm" method="post">
<%-- 	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	 --%>
<%-- 	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/> --%>
	<input type="hidden" name="i_sFastTrackYn" 	value="${rvo.v_fast_track_yn}"/>
	<input type="hidden" name="i_sRecordId" 	value="${rvo.v_fast_track_yn ne 'Y'? reqVo.i_sRecordId: rvo.v_refer_record_id}"/>
	<input type="hidden" name="i_sProductCd" 	value="${rvo.v_fast_track_yn ne 'Y'? reqVo.i_sProductCd: rvo.v_refer_product_cd}"/>	
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sReceipStatus" value="" />
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>
	<div class="pd_top10"></div>	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>
	<div class="sec_cont">
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->
		<!-- 기능성 데이터  -->
		<c:if test="${rvo.v_func_yn eq 'Y'}">
			<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_func_cosmetics.jsp" %>
		</c:if>
	</div>		
	<div class="pd_top10"></div>
	<div class="subtitle pd_top20">보고자료</div>
	<div class="tb_line"></div>
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
					<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
				</c:if>
			</li>
		</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
