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
	
	$('.btn_list').click(function(e){
		location.href="/br/pw/010/init.do?openMenuCd=MIBRPW010";
	});

	j$(".li_tab").unbind("click").click(function(event) {
		event.preventDefault();
		
		var partno = j$(this).attr("id");
		j$("input[name='i_sPartNo']").val(partno);		
		j$(".li_tab a").removeClass("on");
		j$(this).find('a').addClass("on");
	});
	
	j$(".fileReq_btn").unbind("click").click(function(e){

		event.preventDefault();

		var recordid = j$("input[name='i_sRecordId']").val();
		var prodcutCd = j$("input[name='i_sProductCd']").val();
		var doccd = j$(this).attr("id");
		var docnm = j$(this).attr("name");

		var path = WebPATH + "br/pw/020/br_pw_020_file_request_pop.do?i_sRecordId="+recordid+"&i_sProductCd="+prodcutCd+"&i_sDocCd="+doccd+"&i_sDocNm="+encodeURI(docnm)+"&i_sNationCd=KO";
		fn_pop({title:"서류 요청",width:800,height:500,url:path});	

	});
	
	j$("#btn_preview_spec_f").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		var url = WebPATH + "br/pw/020/doc/br_pw_020_doc_spec_preview_pop.do?i_sRecordId=" + recordid + "&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd+ "&i_sFlagImp=F";
		fn_pop({title:"제품 Spec",width:900,height:600,url:url });
		//window.open();
	});
	
	j$("#btn_pdf_spec_f").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		var url = WebPATH + "br/pw/020/doc/br_pw_020_doc_spec_pdf_download.do?i_sRecordId=" + recordid + "&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd+ "&i_sFlagImp=F";
	 	
	 	window.open(url);
	});
	
	j$("#btn_excel_spec_f").unbind("click").click(function(event) {

		event.preventDefault();
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var matrcd = j$("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH+"br/pw/020/doc/br_pw_020_doc_spec_excel_download.do"+"?i_sRecordId="+recordid+"&i_sProductCd="+matrcd+"&i_sPartNo="+partno + "&i_sFlagImp=F";
		window.open(path);		
	});
	
	j$("#btn_preview_spec_s").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		var url = WebPATH + "br/pw/020/doc/br_pw_020_doc_spec_preview_pop.do?i_sRecordId=" + recordid + "&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd+ "&i_sFlagImp=S";
		fn_pop({title:"제품 Spec",width:900,height:600,url:url });
		//window.open();
	});
	
	j$("#btn_pdf_spec_s").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		var url = WebPATH + "br/pw/020/doc/br_pw_020_doc_spec_pdf_download.do?i_sRecordId=" + recordid + "&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd+ "&i_sFlagImp=S";
	 	
	 	window.open(url);
	});
	
	j$("#btn_excel_spec_s").unbind("click").click(function(event) {

		event.preventDefault();
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var matrcd = j$("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH+"br/pw/020/doc/br_pw_020_doc_spec_excel_download.do"+"?i_sRecordId="+recordid+"&i_sProductCd="+matrcd+"&i_sPartNo="+partno + "&i_sFlagImp=S";
		window.open(path);		
	});
	
	$('tr[id^=tr_review_]').find('td span[name=reveiwIcon]').on('click',function(){
		var nationCd = $(this).attr('nationCd');
		var recordId = $('input[name=i_sRecordId]').val();
		var prodcutCd =$('input[name=i_sProductCd]').val();
		var message = "";
		if($(this).hasClass('dxi-chevron-down')){
			var _obj =$(this); 
			$(this).removeClass('dxi-chevron-down');
			$(this).addClass('dxi-chevron-up'); 
			$.ajax({
				url:"/br/pw/020/getNationMessage.do"
				,type:"post"
				,dataType: "json"
				,data:{
					i_sRecordId : recordId
					,i_sProductCd : prodcutCd
					,i_sNationCd : nationCd
					,i_sMenuType : 'DOC'
				}
				,async:false
				,success:function(data){
					var _tr = $('<tr id="tr_review_message_'+nationCd+'"></tr>');
					var _td = $('<td class="last" colspan="7"></td>');
					var rData = data.result.data;
					var html = '';
					html+='<ul class="mi_msg" style="max-width: 100%"><li class="mi_msg_gap"></li>';
					rData.forEach(function(obj,i){
						if(obj.v_flag_type=="P"){
							html+='<li class="mi_msg_to" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt"> '+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
							html+=obj.v_message;
							html+='</dd><dd class="msg_bottom"></dd></dl></li>';
							html+='<li class="mi_msg_gap"></li>';
						}else if(obj.v_flag_type=="T"){
							html+='<li class="mi_msg_from" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt"> '+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
							html+=obj.v_message;
							html+='</dd><dd class="msg_bottom"></dd></dl></li>';
						html+='<li class="mi_msg_gap"></li>';							
						}						
					});
					html+='</ul>';
					_td.append(html);
					_tr.append(_td);
					_obj.parent().parent().after(_tr);
				},error : function(jqXHR, textStatus, errorThrown){
			        fn_s_failMsg(jqXHR, textStatus, errorThrown);
				}
			});
		}else{
			$(this).removeClass('dxi-chevron-up');
			$(this).addClass('dxi-chevron-down');
			$('#tr_review_message_'+nationCd).remove();
		}
	});


	j$("select[name^=i_sReveiwStatus_]").unbind("change").change(function(event) {
		var val = $(this).val();
		var nationCd = $(this).attr('id').split('_')[2];
		if(val=='MODI_REQ'){
			$('div[name=divReqModi_'+nationCd+']').show();
			inputChkRadioAddEvent();
		}else{
			$('div[name=divReqModi_'+nationCd+']').hide();			
		}
	});
	
	j$('.req_file_list_btn').unbind('click').click(function(){
		var recordid = j$("input[name='i_sRecordId']").val();
		var prodcutCd = j$("input[name='i_sProductCd']").val();		
		var path = WebPATH + "br/pw/020/br_pw_020_req_file_list_pop.do?i_sRecordId="+recordid+"&i_sProductCd="+prodcutCd+"&i_sNationCd=KO";
		fn_pop({title:"요청서류 리스트",width:500,height:500,url:path});			
	}) ;
	


	j$('.btn_zipDown').unbind('click').click(function(){

		var frm = j$("form[name='formZip']");
		frm.html('');
		frm.append('<input type="hidden" name="i_sRecordId" value="'+$("form[name='frm']").find("input[name='i_sRecordId']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sProductCd" value="'+$("form[name='frm']").find("input[name='i_sProductCd']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_iVerSeq" value="'+$("form[name='frm']").find("input[name='i_iVerSeq']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sPartNo" value="'+$(".li_tab").find('.on').parent().attr("id")+'"\/>');
		frm.append('<input type="hidden" name="i_sAdReqId" value="'+$("form[name='frm']").find("input[name='i_sAdReqId']").val()+'"\/>');

		frm.attr("action", "/br/pw/010/docZipFileDownload.do");
		frm.submit();		
	});
 });


 function inputChkRadioAddEvent(target) {
 	
 	if (target == undefined) {
 		target = jQuery(document);
 		//target = jQuery(".chk-style, .rd-style").not(".not_select");
 	}
 	console.log(target);

 	target.find('.rd-style input[type=radio]').each(function(i,obj){
 		var bol = $(obj).is(":checked");
 		if(bol){
 			$(obj).parent().parent().addClass("on");			
 		}else{
 			$(obj).parent().parent().removeClass("on");			
 		}
 	});
 	target.find('.chk-style input[type=checkbox]').each(function(i,obj){
 		var bol = $(obj).is(":checked");
 		if(bol){
 			$(obj).parent().parent().addClass("on");			
 		}else{
 			$(obj).parent().parent().removeClass("on");			
 		}
 	});
 	
 	$('.chk-style label').click(chk_radio.checkBox);
 	$('.rd-style label').click(chk_radio.inputRadio);
 	
 }
 function fn_validation(vaildParam){
		if(fn_isNull($('textarea[name=i_sReveiwOp_'+vaildParam.nationCd+']').val())){
			fn_s_alertMsg(vaildParam.nationNm+"의 검토의견이 비어있습니다.",function(){$('textarea[name=i_sReveiwOp_'+vaildParam.nationCd+']').focus()});
			return true;
		}
		if(fn_s_inputLengthChk($('textarea[name=i_sReveiwOp_'+vaildParam.nationCd+']').val(),'검토의견',1000)){
			fn_s_alertMsg(vaildParam.nationNm+"의 검토의견이 비어있습니다.",function(){$('textarea[name=i_sReveiwOp_'+vaildParam.nationCd+']').focus()});
			return true;
		}
		if(fn_isNull($('select[name=i_sReveiwStatus_'+vaildParam.nationCd+']').val())){
			fn_s_alertMsg(vaildParam.nationNm+"의 검토상태가 비어있습니다.",function(){$('select[name=i_sReveiwStatus_'+vaildParam.nationCd+']').focus() });
			return true;
		}
		return false;
	}
 function reviewSave(nationCd,nationNm){
		//if(fn_validation(nationCd,nationNm)) return;	
		var arrReq=[];
		$('input[name=i_arrReqModi_'+nationCd+']:checked').each(function(i,obj){
			arrReq.push($(obj).val());
		});
		
		var postParam = {
			i_sProductCd : $('input[name=i_sProductCd]').val()
			,i_sRecordId : $('input[name=i_sRecordId]').val()
			,i_sReveiwOp : $('textarea[name=i_sReveiwOp_'+nationCd+']').val()
			,i_sReveiwStatus : $('select[name=i_sReveiwStatus_'+nationCd+']').val()
			,i_arrReqModi : arrReq
			,i_sNationCd : nationCd
			,i_sVendorLaborId : $('input[name=i_sVendorLaborId]').val()
		};
		fn_s_saveMessage("검토의견",nationNm+"의 검토의견을 등록하시겠습니까?","/br/pw/020/br_pw_020_t2_document_review_save.do",postParam
			,function(){
				//location.href="/br/pw/020/br_pw_020_t2_document_view.do";
				var frm = j$("form[name='frm']");
				frm.attr("action", WebPATH + "br/pw/010/br_pw_010_t4_view.do").submit();
			}
			,fn_validation
			,{nationCd:nationCd,nationNm:nationNm
			}
		);
	}
 function downloadPdf(url , allergenYn, partNum, refYn) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
	 	
		var data = {
				i_sRecordId : $("input[name='i_sRecordId']").val()
				, i_sProductCd :  $("input[name='i_sProductCd']").val()
				, i_iVerSeq : j$("input[name='i_iVerSeq']").val()
				, i_sPartNo : $(".li_tab").find('.on').parent().attr("id")
				, i_sRefYn : refYn
		};
		
		if(allergenYn != ""){
			data.i_sAllergenFlag = allergenYn;
			path = WebPATH+url+"?"+j$.param(data, true);
		}else{
			path = WebPATH+url+"?"+j$.param(data, true);
		}
		window.open(path);
	}

	function download_PDF(div, partNum, allergenYn, lang, refYn){
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		
		var data = {
				i_sRecordid : $("input[name='i_sRecordId']").val()
				, i_sMatrcd :  $("input[name='i_sProductCd']").val()
				, i_iVerSeq : j$("input[name='i_iVerSeq']").val()
				, i_sPartNo : $(".li_tab").find('.on').parent().attr("id")
				, i_sDiv : div
				, i_sRecipeType : "S"
				, i_sRefYn : refYn
				, i_sLang : lang
		};
		
		if(allergenYn != ""){
			data.i_sAllergenFlag = allergenYn;
		}else{
			data.i_sExpDiv = "";
		}
		
		var url = "";
		url = WebPATH + "br/pr/012/doc/doc_pdf_download.do?";
		
		var path = url + j$.param(data, true);
		//console.log(path);
		document.location.href = path;
		
	}
	 function showPreView(url, allergenYn, partNum, refYn){
		 	var partSize = $("input[name='i_sPartSize']").val();
		 	if(partSize == 0){
		 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
		 		return;
		 	}
			var recordid = j$("input[name='i_sRecordId']").val();
			var partno = j$(".li_tab").find('.on').parent().attr('id');
			var verSeq = j$("input[name='i_iVerSeq']").val();
			var i_sMatrcd =  $("input[name='i_sProductCd']").val();
			var path = "";
			//partno	= 1;
			path = WebPATH + url + ".do?i_sRecordId="+recordid+"&i_sProductCd="+i_sMatrcd+"&i_sPartNo="+partno+"&i_iVerSeq="+verSeq+"&i_sRefYn="+refYn;
			fn_pop({title:"미리보기",width:900,height:600,url:path});
	}
</script>
<form id="formZip" name="formZip" method="post"></form>
<form id="frm" name="frm" method="post">		
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sVendorId" 	value="${rvo.v_vendor_id}"/>
	<input type="hidden" name="i_sVendorLaborId" 	value="${rvo.v_vendor_labor_id}"/>
			
	<input type="hidden" name="i_iVerSeq" value="${rvo.n_vsn }" />  
	<input type="hidden" name="i_sProductNm" value="${rvo.v_productnm_en }" />
	<input type="hidden" name="i_sTrnsctreqId" value="${rvo.v_vendor_id }" /> 
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo }" /> 
	
	<input type="hidden" name="i_sReceipStatus" value="${rvo.v_receip_status }" />
	<input type="hidden" name="i_sFinalRst" value="${rvo.v_final_rst }" />
	<input type="hidden" name="i_sGlbRecordId" value="${reqVo.i_sGlbRecordid }" />		
	<input type="hidden" name="i_sPartSize" value="${partList.size()}" />
	<input type="hidden" name="i_sAdReqId" 	value="${rvo.v_ad_req_id }"/>
	<div class="top_btn_area" style="z-index:1;">		
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a id="req_file_list_btn" href="#" class="btnA bg_dark req_file_list_btn"><span>요청 서류 파일 목록</span></a>
			<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
		</c:if>
	</div>	
	<div class="pd_top10"></div>		
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/010/br_pw_010_tab.jsp" %>
	
	<div style="padding-top:20px;text-align:right;"><a href="#" class="btnA bg_dark btn_zipDown"><span>전체 파일다운로드</span></a></div>
	<div class="sec_cont mt_00">
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 			</li> -->
<!-- 		</ul> -->
		
		<ul class="sty_tab ul_tab">
			<c:forEach items="${partList }" var="vo" varStatus="s">
				<li class="tab li_tab li_tab_part" id="${vo.n_part_no }">
					<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.n_part_no }</span></a>
				</li>
			</c:forEach>
		</ul>
		
		<table class="sty_02 bdt_n">
			<colgroup>
				<col width="30%" />
				<col width="10%" />
				<col width="*" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th class="ta_c">Document</th>
					<th class="ta_c">서류요청</th>
					<th class="ta_c">미리보기</th>
					<th class="ta_c">Download</th>
				</tr>
			</thead>
			<tr>
				<td rowspan='2' class="bdl_n">package images</td>
				<td rowspan='2' class="ta_c"></td>
			</tr>
			<tr>
				<td colspan="2">
					<table class="sty_01 bd_gray">
						<tr>
							<th>단상자</th>
							<th>용기</th>
							<th>내지</th>
						</tr>
						<tr>
							<td class="ta_c">
								<CmTagLib:cmAttachFile uploadCd="ORI_BOX" recordId="${rvo.v_ad_req_id }" formName="frm" type="listDown" />
							</td>
							<td class="ta_c">							
								<CmTagLib:cmAttachFile uploadCd="ORI_VESSEL" recordId="${rvo.v_ad_req_id }" formName="frm" type="listDown" />
							</td>
							<td class="ta_c">
								<CmTagLib:cmAttachFile uploadCd="ORI_PAPER" recordId="${rvo.v_ad_req_id }" formName="frm" type="listDown" />
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td class="bdl_n">ingredients list</td>
				<td class="ta_c"></td>
				<td class="ta_c" colspan='1'>
					<a class="img_btn" href="#none" onclick="showPreView('br/pw/010/br_pw_010_doc_ingredient_pop', '', '1')">
						<img src="${ImgPATH}common/img_print.png">
					</a>
				</td>
				<td class="ta_c" colspan="1"></td>
			</tr>	
			<tr>
				<td class="bdl_n">IL</td>
				<td class="ta_c"></td>
				<td class="ta_c">
					<a class="img_btn" href="#none" onclick="showPreView('br/pr/012/doc/co_doc_formula_pdf_print', 'Y', '3', 'Y')">
						<img src="${ImgPATH}common/img_print.png">
					</a>
				</td>
				<td class="ta_c">
					<a class="img_btn" href="#none" onclick="downloadPdf('br/pr/012/doc/co_doc_formula_excel.do', 'Y','3','Y');">
						<img src="${ImgPATH}common/img_xls.png">
					</a>
					<a class="img_btn" href="#none" onclick="download_PDF('S', '3', '', '', 'Y');">
						<img alt="" src="${ImgPATH}common/img_pdf.png">
					</a>
				</td>
			</tr>
			<tr>	
				<td class="bdl_n">SPEC</td>				
				<td class="ta_c"></td>
				<td class="ta_c" colspan='1'>
					<table class="sty_01 bd_gray">
						<tr>
							<th>완제품</th>
							<th>반제품</th>
						</tr>
						<tr>
							<td>
								<a class="img_btn" href="#" id="btn_preview_spec_f"> 
									<img src="${ImgPATH}common/img_print.png">
								</a>
							</td>
							<td>
								<a class="img_btn" href="#" id="btn_preview_spec_s"> 
									<img src="${ImgPATH}common/img_print.png">
								</a>
							</td>
						</tr>					
					</table>
				</td>
				<td class="ta_c" colspan='1'>
					<table class="sty_01 bd_gray">
						<tr>
							<th>완제품</th>
							<th>반제품</th>
						</tr>
						<tr>
							<td>
								<a class="img_btn" href="#" id="btn_excel_spec_f"> 
									<img src="${ImgPATH}common/img_xls.png">
								</a>
								<a class="img_btn" href="#none" id="btn_pdf_spec_f"> 
									<img alt="" src="${ImgPATH}common/img_pdf.png">
								</a>
							</td>
							<td>
								<a class="img_btn" href="#" id="btn_excel_spec_s"> 
									<img src="${ImgPATH}common/img_xls.png">
								</a>
								<a class="img_btn" href="#none" id="btn_pdf_spec_s"> 
									<img alt="" src="${ImgPATH}common/img_pdf.png">
								</a>
							</td>
						</tr>					
					</table>
				</td>
			</tr>
			<c:forEach items="${cmDocList }" var="vo">
				<tr style="${vo.COMM_CD eq 'FR022' or vo.COMM_CD eq 'FR023' ? 'display:none' : ''}">
					<td class="td_bold" style="position: relative;">
						${vo.COMM_CD_NM }
					</td>
					<td style="text-align: center;">
						<a href="#"  class="btnA bg_dark fileReq_btn" id="${vo.COMM_CD }" name="${vo.COMM_CD_NM}">
							<span>서류요청</span>
						</a>
					</td>
					<td class="last" colspan='2'>						
						<c:choose>
							<c:when test="${vo.COMM_CD eq 'FR021' }">
								<CmTagLib:cmAttachFile uploadCd="PSPEC" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" />
							</c:when>
							<c:when test="${vo.COMM_CD eq 'FR022' }">
								<CmTagLib:cmAttachFile uploadCd="MSDS" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" />
							</c:when>
							<c:when test="${vo.COMM_CD eq 'FR023' }">
								<CmTagLib:cmAttachFile uploadCd="PSTAB" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" />
							</c:when>
							<c:otherwise>
								<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="viewLog" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
			<c:forEach items="${docList }" var="vo">
				<tr>
					<td class="td_bold" style="position: relative;">
						${vo.COMM_CD_NM }
					</td>
					<td style="text-align: center;">
						<a href="#"  class="btnA bg_dark fileReq_btn" id="${vo.COMM_CD }" name="${vo.COMM_CD_NM}">
							<span>서류요청</span>
						</a>
					</td>
					<td class="last" colspan='2'>
						<c:choose>
							<c:when test="${vo.COMM_CD eq 'FR020' }">
								<CmTagLib:cmAttachFile uploadCd="PRD" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="viewLog" />							
							</c:when>
							<c:otherwise>
								<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="" formName="frm" type="viewLog" />
							</c:otherwise>
						</c:choose>
						
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<c:if test="${rvo.v_fast_track_yn ne 'Y' }">
		<div class="sec_cont">
	
			<h2 class="tit">
				국내 검토
			</h2>
			
			<table class="sty_02 table_review">
			<colgroup>
					<col width="120px" />
					<col width="100px" />
					<col width="200px" />
					<col width="200px" />
					<col width="150px" />
					<col width="150px" />
					<col width="150px" />
			</colgroup>
			<tbody>
				<tr>
					<th>국가명</th>
					<th>검토자</th>
					<th>검토의견</th>
					<th>수정항목</th>
					<th>검토상태</th>
					<th>검토요청일</th>
					<th>검토일</th>
				</tr>
				<c:forEach items="${nationReviewList}" var="revVo" varStatus="s">
						<tr id='tr_review_${revVo.v_nation_cd }'>
							<td class="ta_c">${revVo.v_nation_nm } <span name="reveiwIcon" nationCd="${revVo.v_nation_cd }" style="vertical-align: middle;" class="dxi dxi-chevron-down"></span></td>
							<td class="ta_c">${revVo.v_reviwer_nm }</td>						
							<c:choose>
								<c:when test="${fn:indexOf(reqVo.s_groupid,'ADMIN') > -1 or  (((rvo.v_final_rst eq 'RS040' or rvo.v_final_rst eq 'RS053' or rvo.v_final_rst eq 'RS055') and rvo.v_ck_domestic_yn ne 'Y') and ((empty revVo.v_reviwer_id or revVo.v_reviwer_id eq reqVo.s_userid) and revVo.v_review_status ne 'NS020'))}">
									<td>
										<textarea rows="2" class="textarea_sty01" name="i_sReveiwOp_${revVo.v_nation_cd }" id="i_sReveiwOp_${revVo.v_nation_cd }"class="inp_sty01" style="width: 98%;" >${revVo.v_review_op }</textarea>
									</td>								
									<td>
										<div name="divReqModi_${revVo.v_nation_cd }" style="display:none">
											<c:forEach items="${reqModiList }" var="rmVo">
												<span class="chk-style">
													<label for="i_sReqModi_${revVo.v_nation_cd }_${rmVo.COMM_CD}">
														<span>
															<input type="checkbox" name="i_arrReqModi_${revVo.v_nation_cd }" id="i_sReqModi_${revVo.v_nation_cd }_${rmVo.COMM_CD}" value="${rmVo.COMM_CD}"																	
															<c:forEach items="${rqInfo }" var="rqVo">			 
																${revVo.v_nation_cd eq rqVo.v_nation_cd and fn:indexOf(rqVo.v_req_item_cd, rmVo.COMM_CD) > -1 ? 'checked' : ''}																		
															</c:forEach>
															/>
														</span>&nbsp;${rmVo.COMM_CD_NM }&nbsp;
													</label>
												</span>
											</c:forEach>
										</div>
									</td>
									<td>
										<select id="i_sReveiwStatus_${revVo.v_nation_cd }" name="i_sReveiwStatus_${revVo.v_nation_cd }" class="select_sty01">
											<option value="">선택</option>
											<option value="MODI_REQ">수정요청</option>
											<c:forEach items="${reviewStList }" var="rsVo">
												<c:if test="${empty rsVo.BUFFER1 or (rsVo.BUFFER1 eq 'CN' and revVo.v_nation_cd eq rsVo.BUFFER1)}">
													<option value="${rsVo.COMM_CD }" <c:if test="${rsVo.COMM_CD eq  revVo.v_review_status}">selected</c:if>>${rsVo.COMM_CD_NM }</option>
												</c:if>
											</c:forEach>
										</select>
	<%-- 									<input type="button" id="btn_reviewSave_${revVo.v_nation_cd }" name="btn_reviewSave_${revVo.v_nation_cd }" style="cursor: pointer;" onclick="reviewSave('${revVo.v_nation_cd }','${revVo.v_nation_nm }');" value="저장"/> --%>
											<a href="javascript:;" class="btnA bg_dark" id="btn_reviewSave_${revVo.v_nation_cd }" name="btn_reviewSave_${revVo.v_nation_cd }"  style="cursor: pointer;"  onclick="reviewSave('${revVo.v_nation_cd }','${revVo.v_nation_nm }');">저장</a>
									</td>
								</c:when>
								<c:otherwise>						
									<td>${revVo.v_review_op }</td>
									<td></td>
									<td>${revVo.v_review_status_nm }</td>
								</c:otherwise>
							</c:choose>						
							<td class="ta_c">${cfn:getStrDateToString(revVo.v_request_dtm,'yyyy-MM-dd')}</td>
							<td class="ta_c">${cfn:getStrDateToString(revVo.v_review_dtm,'yyyy-MM-dd')}</td>
						</tr>
				</c:forEach>
			</tbody>
			</table>
		</div>
	</c:if>
		<ul class="btn_area">
			<li class="right">				
				<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
					<a id="req_file_list_btn" href="#" class="btnA bg_dark req_file_list_btn"><span>요청 서류 파일 목록</span></a>
					<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
				</c:if>
			</li>
		</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
