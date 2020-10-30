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
	jfupload.initUpload({
		target : j$("#btn_cNFileAdd")
		, uploadCd : "CNR"
		, formName : "frmReviewCn"
		, success : function(attData, uploadCd){
			
			var attach_id = attData.v_attach_id;
			var attach_lnm = attData.v_attach_lnm;
			var attach_type = attData.v_attach_type;
			var attach_size = attData.v_attach_size;
			var attach_pnm = attData.v_attach_pnm;
			var attach_mgtid = attData.v_attach_mgtid;
			var pk1 = j$("input[name='i_sProductCd']").val();
			var pk2 = uploadCd;
			var table = j$("#table_" + uploadCd);
			
			var obj = {
				v_attach_id : attach_id
				, v_attach_lnm : attach_lnm
				, v_attach_type : attach_type
				, n_attach_size : attach_size
				, v_attach_pnm : attach_pnm
				, v_attach_mgtid : attach_mgtid
				, v_upload_cd : uploadCd
				, v_pk1 : pk1
				, v_pk2 : pk2
				, v_pk3 : ""
				, v_pk4 : ""
				, v_pk5 : ""
				, del_img_url : ImgPATH + "btn/btn_del_small.gif"
			};
			
			var pagefn = doT.template(document.getElementById("dot_upload").text, undefined,undefined);
			j$(pagefn(obj)).appendTo(table.find("tbody"));
//			j$(doTjs_Injection("dot_upload", obj)).appendTo(table.find("tbody"));
			
		}
		, isSelfMakeTag : true
		, attachDir : "CNR"
	});

	j$(".li_tab_part1").unbind("click").click(function(event) {
		event.preventDefault();
		j$(".li_tab_part1 a").removeClass("on");
		j$(this).find('a').addClass("on");
	});
	j$(".li_tab_part2").unbind("click").click(function(event) {
		event.preventDefault();
		j$(".li_tab_part2 a").removeClass("on");
		j$(this).find('a').addClass("on");
		
		var partno = j$(this).find('.on').parent().attr('id');
		j$("input[name='i_sPartNo']").val(partno);		
		//fn_part2ListView(partno,false);

	});
	
	j$(".li_tab_part3").unbind("click").click(function(event) {
		event.preventDefault();
		j$(".li_tab_part3 a").removeClass("on");
		j$(this).find('a').addClass("on");
		
		var partno = this.id;
		
		j$(".spec_file_div").hide();
		j$("#spec_file_div_"+partno).show();
	});
	j$(".btn_part_show").unbind("click").click(function(event) {
		event.preventDefault();
		
		var part = j$(this).attr("data-part");
		
		j$(".table_" + part).show();
		j$(".ul_tab_" + part).show();
	});
	
	j$(".btn_part_hide").unbind("click").click(function(event) {
		event.preventDefault();
		
		var part = j$(this).attr("data-part");
		
		j$(".table_" + part).hide();
		j$(".ul_tab_" + part).hide();
	});
	
	$('.btn_list').click(function(e){
		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
	});
// 	j$(".li_tab").unbind("click").click(function(event) {
// 		event.preventDefault();
		
// 		var frm = j$("form[name='frm']");
// 		var partno = j$(this).attr("id");
		
// 		frm.find("input[name='i_sClaimPartNo']").val(partno);
// 		frm.attr("action", "/br/pw/020/br_pw_020_t2_document_view.do").submit();
// 	});
		
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
// 					<span style="margin-left: 10px;">'+message+'</span>
					var rData = data.result.data;
					var html = '';
					html+='<ul class="mi_msg" style="max-width: 100%"><li class="mi_msg_gap"></li>';
					rData.forEach(function(obj,i){
						if(obj.v_flag_type=="P"){
							html+='<li class="mi_msg_to" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt">'+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
							html+=obj.v_message;
							html+='</dd><dd class="msg_bottom"></dd></dl></li>';
							html+='<li class="mi_msg_gap"></li>';
						}else if(obj.v_flag_type=="T"){
							html+='<li class="mi_msg_from" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt">'+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
							html+=obj.v_message;
							html+='</dd><dd class="msg_bottom"></dd></dl></li>';
						html+='<li class="mi_msg_gap"></li>';							
						}						
					});
// 					float:right;
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

	
	j$("#btn_claim_support_preview").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part1").find('.on').parent().attr('id');
		j$(".li_tab_part1.selected").attr("id");
		var recordid = j$("input[name='i_sRecordId']").val();
		var _patn = WebPATH + "br/pw/020/doc/br_pw_020_doc_claim_pop.do?i_sRecordId=" + recordid + "&i_sClaimPartNo="+partno
				+"&i_sProductCd="+j$("input[name='i_sProductCd']").val();
		fn_pop({title:"미리보기",width:900,height:600,url:_patn});	
	});
	j$("#btn_claim_support_pdf").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part1").find('.on').parent().attr('id');
		var recordid = j$("input[name='i_sRecordId']").val();
		
		window.open(WebPATH + "br/pw/020/doc/br_pw_020_doc_part1_claim_report.do?i_sRecordId=" + recordid + "&i_sClaimPartNo="+partno+"&i_sProductCd="+j$("input[name='i_sProductCd']").val());
	});
	
	j$("#btn_preview_process").unbind("click").click(function(event) {
		event.preventDefault();		
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var param = {
				i_sRecordId		: j$("input[name='i_sRecordId']").val()
				, i_sProductCd		: j$("input[name='i_sProductCd']").val()
				, i_sPartNo		: j$(".li_tab_part3").find('.on').parent().attr('id')
				, i_sRecipeType	: "C"
				, i_sFrameid : "iFrameProcessPreview"
		};
		var url = WebPATH + "br/pr/012/doc/co_doc_process_preview_pdf_print.do?"+j$.param(param,true);
		fn_pop({title:"미리보기",width:900,height:600,url: url });
	});
	
	j$("#btn_excel_process").unbind("click").click(function(event) {
		event.preventDefault();
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
		var matrcd = j$("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH+"br/pr/012/doc/co_doc_process_preview_pop_excel.do"+"?i_sRecordId="+recordid+"&i_sProductCd="+matrcd+"&i_sPartNo="+partno + "&i_sRecipeType=C";
		window.open(path);
	});
	
	
	j$("#btn_certi_fragrance_preview").unbind("click").click(function(event){
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part1").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		var url = WebPATH + "br/pw/020/doc/br_pw_020_doc_fragrance_pop.do?i_sRecordId="+recordid+"&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd;
		fn_pop({title:"서류 요청",width:900,height:600,url:url });		
	});
	
	j$("#btn_certi_fragrance_pdf").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part1").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		
		window.open(WebPATH + "br/pw/020/doc/br_pw_020_doc_part1_fragrance_report.do?i_sRecordId=" + recordid + "&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd);
		
	});j$("#btn_preview_spec_f").unbind("click").click(function(event) {
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
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
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
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
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
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
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
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
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
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
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
		var matrcd = j$("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH+"br/pw/020/doc/br_pw_020_doc_spec_excel_download.do"+"?i_sRecordId="+recordid+"&i_sProductCd="+matrcd+"&i_sPartNo="+partno + "&i_sFlagImp=S";
		window.open(path);		
	});

	
	j$("#btn_preview_stability").unbind("click").click(function(event) {

		event.preventDefault();
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
		var matrcd = j$("input[name='i_sProductCd']").val();
		var path = WebPATH+"br/pr/012/doc/co_doc_stability_pdf_print.do"+"?i_sRecordId="+recordid+"&i_sProductCd="+matrcd+"&i_sPartNo="+partno;
		fn_pop({title:"제품 안정성",width:900,height:600,url: path });
	});
	
	j$("#btn_pdf_stability").unbind("click").click(function(event) {

		event.preventDefault();
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part3").find('.on').parent().attr('id');
		var matrcd = j$("input[name='i_sProductCd']").val();
		
		var url = WebPATH + "br/pr/012/doc/doc_pdf_download.do?"+"i_sRecordid="+recordid+"&i_sMatrcd="+matrcd+"&i_sPartNo="+partno+"&i_sDiv=ST";
		document.location.href = url;
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
	
	j$(".fileReq_btn").unbind("click").click(function(e){

		event.preventDefault();

		var recordid = j$("input[name='i_sRecordId']").val();
		var prodcutCd = j$("input[name='i_sProductCd']").val();
		var doccd = j$(this).attr("id");
		var docnm = encodeURI(j$(this).attr("docNm"));
		
		var path = WebPATH + "br/pw/020/br_pw_020_file_request_pop.do?i_sRecordId="+recordid+"&i_sProductCd="+prodcutCd+"&i_sDocCd="+doccd+"&i_sDocNm="+docnm+"&i_sNationCd=GL";
		fn_pop({title:"서류 요청",width:800,height:500,url:path});	

	});
	
	j$('.req_file_list_btn').unbind('click').click(function(){

		var recordid = j$("input[name='i_sRecordId']").val();
		var prodcutCd = j$("input[name='i_sProductCd']").val();
		
		var path = WebPATH + "br/pw/020/br_pw_020_req_file_list_pop.do?i_sRecordId="+recordid+"&i_sProductCd="+prodcutCd+"&i_sNationCd=GL";
		fn_pop({title:"요청서류 리스트",width:500,height:500,url:path});			
	}) ;
	if($('select[name=i_sReveiwStatus_CN]').val() == 'NS020' || $('input[name=i_sReveiwStatus_CN]').val() == 'NS020'){
		$('#divReviewCn').show();
		fn_specialYnChange($('#frmReviewCn').find("select[name=i_sSpecialYn]").val());
		j$('#frmReviewCn').find("select[name=i_sSpecialYn]").unbind("change").change(function(event) {
			var val = $(this).val();
			fn_specialYnChange(val);
		});
		fn_calendarSet("i_sChinaFinDtm",function(obj,date,oldDate,click){
			if(click){
				if($('select[name=i_sSpecialYn]').val() == 'Y'){					
					date.setFullYear(date.getFullYear()+5)
					$('#i_sChinaSpDtm').val(fn_getStrFromDate(date,'-'));
				}
			}
		});	
		$('.btn_reviewCnSave').unbind('click').click(function(){
			fn_s_saveMessage("중국의견","중국의견을 저장하시겠습니까?","/br/pw/020/saveChinaReview.do",$('#frmReviewCn').serialize()
			,function(){
				var frm = j$("form[name='frm']");
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_document_view.do").submit();
			},{});
		});
		$('.cnReviewExcel').unbind('click').click(function(){
			var path = "";
			
			path = WebPATH+"br/pw/020/doc/excelMlMgDownload.do"+"?i_sRecordId="+ $("input[name='i_sRecordId']").val()+"&i_sProductCd="+$("input[name='i_sProductCd']").val()+"&i_sMlMgStr="+$(this).attr('id');
			window.open(path);
		});
		$('.cnReviewPreView').unbind('click').click(function(){
			var path = WebPATH+"br/pw/020/doc/br_pw_020_cn_spec_mlmg_preview_pop.do"+"?i_sRecordId="+ $("input[name='i_sRecordId']").val()+"&i_sProductCd="+$("input[name='i_sProductCd']").val()+"&i_sMlMgStr="+$(this).attr('id');
			fn_pop({title:"중국 spec 미리보기",width:850,height:600,url:path});
		});
		
	}
	$('.btn_zipDownPart1').unbind('click').click(function(){

		var frm = j$("form[name='formZip']");
		frm.html('');
		frm.append('<input type="hidden" name="i_sRecordId" value="'+$("form[name='frm']").find("input[name='i_sRecordId']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sProductCd" value="'+$("form[name='frm']").find("input[name='i_sProductCd']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sPartNo" value="'+$("form[name='frm']").find("input[name='i_sPartNo']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sAdReqId" value="'+$("form[name='frm']").find("input[name='i_sAdReqId']").val()+'"\/>');
		
		frm.append('<input type="hidden" name="outputYn" value="Y"\/>');
		frm.attr("action", "/br/pw/020/part1ZipFileDownload.do");
		frm.submit();
	});

	
	$('.btn_zipDownPart2').unbind('click').click(function(){

		var frm = j$("form[name='formZip']");
		frm.html('');
		frm.append('<input type="hidden" name="i_sRecordId" value="'+$("form[name='frm']").find("input[name='i_sRecordId']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sProductCd" value="'+$("form[name='frm']").find("input[name='i_sProductCd']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_nVer" value="'+$("form[name='frm']").find("input[name='i_iVerSeq']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sPartNo" value="'+$(".li_tab_part2").find('.on').parent().attr("id")+'"\/>');

		frm.attr("action", "/br/pw/020/part2ZipFileDownload.do");
		frm.submit();		
	});
	$('.btn_zipDownPart3').unbind('click').click(function(){ 
		
		var frm = j$("form[name='formZip']");
		frm.html('');
		frm.append('<input type="hidden" name="i_sRecordId" value="'+$("form[name='frm']").find("input[name='i_sRecordId']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sProductCd" value="'+$("form[name='frm']").find("input[name='i_sProductCd']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_iVerSeq" value="'+$("form[name='frm']").find("input[name='i_iVerSeq']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sPartNo" value="'+$(".li_tab_part3").find('.on').parent().attr("id")+'"\/>');
		frm.append('<input type="hidden" name="outputYn" value="Y"\/>');
		frm.attr("action", "/br/pw/020/part3ZipFileDownload.do");
		frm.submit();
	});
	$('.btn_zipDownPart4').unbind('click').click(function(){

		var frm = j$("form[name='formZip']");
		frm.html('');
		frm.append('<input type="hidden" name="i_sRecordId" value="'+$("form[name='frm']").find("input[name='i_sRecordId']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sProductCd" value="'+$("form[name='frm']").find("input[name='i_sProductCd']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_iVerSeq" value="'+$("form[name='frm']").find("input[name='i_iVerSeq']").val()+'"\/>');
		frm.append('<input type="hidden" name="i_sPartNo" value="'+$("form[name='frm']").find("input[name='i_sPartNo']").val()+'"\/>');
		
		frm.attr("action", "/br/pw/020/part4ZipFileDownload.do");
		frm.submit();
	});
	$('#part2LoadCon').click(function(){
		var partno = $('.li_tab_part2').find('.on').parent().attr('id');
		fn_part2ListView(partno);
	});
	
	j$(".table_part1").hide();
	j$(".ul_tab_part1").hide();
	j$(".table_part2").hide();
	j$(".ul_tab_part2").hide();			
	j$(".table_part3").hide();
	j$(".ul_tab_part3").hide();
	j$(".table_part4").hide();
	j$(".ul_tab_part4").hide();
 });

function fn_specialYnChange(val){
	if(val == 'Y'){
		$('#tdChinaFinDtm').attr('colspan','1');
		$('#thChinaSpDtm').show();
		$('#tdChinaSpDtm').show();
		if(fn_isNotNull($('#i_sChinaFinDtm').val())){
			var date = new Date($('#i_sChinaFinDtm').val());
			date.setFullYear(date.getFullYear()+5)
			$('#i_sChinaSpDtm').val(fn_getStrFromDate(date,'-'));			
		}
	}else{
		$('#tdChinaFinDtm').attr('colspan','3');
		$('#thChinaSpDtm').hide();
		$('#tdChinaSpDtm').hide();				
		$('input[name=i_sChinaSpDtm]').val('');
	}
}

 function inputChkRadioAddEvent(target) {
 	
 	if (target == undefined) {
 		target = jQuery(document);
 		//target = jQuery(".chk-style, .rd-style").not(".not_select");
 	}
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
 function showPreViewForASN(url, asnDiv){
 	var partSize = $("input[name='i_sPartSize']").val();
 	if(partSize == 0){
 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
 		return;
 	}
	var recordid = j$("input[name='i_sRecordid']").val();
	var partno = j$(".li_tab_part1").find('.on').parent().attr('id');
	var path = WebPATH + url + ".do?i_sRecordid="+recordid+"&i_sPartNo="+partno+"&i_sAsnExp="+asnDiv;
	fn_pop({title:"미리보기",width:900,height:600,url:path});	
}
 function showPreView(url, allergenYn, partNum, refYn){
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part"+partNum+"").find('.on').parent().attr('id');
		var verSeq = j$("input[name='i_iVerSeq']").val();
		var i_sMatrcd =  $("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH + url + ".do?i_sRecordId="+recordid+"&i_sProductCd="+i_sMatrcd+"&i_sPartNo="+partno+"&i_iVerSeq="+verSeq+"&i_sRefYn="+refYn;
		fn_pop({title:"미리보기",width:900,height:600,url:path});
}
 function showPreViewC(url, allergenYn, partNum, refYn,nationCd){
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part"+partNum+"").find('.on').parent().attr('id');
		var verSeq = j$("input[name='i_iVerSeq']").val();
		var i_sMatrcd =  $("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH + url + ".do?i_sRecordId="+recordid+"&i_sProductCd="+i_sMatrcd+"&i_sPartNo="+partno+"&i_iVerSeq="+verSeq+"&i_sRefYn="+refYn+"&i_sLang="+nationCd;
		fn_pop({title:"미리보기",width:900,height:600,url:path});
}
 
 
 
function downloadPdfForASN(url, asnDiv){
 	var partSize = $("input[name='i_sPartSize']").val();
 	if(partSize == 0){
 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
 		return;
 	}
	var recordid = j$("input[name='i_sRecordId']").val();
	var partno = j$(".li_tab_part1").find('.on').parent().attr('id');
	var path = "";
	
	path = WebPATH+url+"?i_sRecordId="+recordid+"&i_sPartNo="+partno+"&i_sAsnExp="+asnDiv;
	window.open(path);
	
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
			, i_sPartNo : $(".li_tab_part"+partNum+"").find('.on').parent().attr("id")
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
			, i_sPartNo : $(".li_tab_part"+partNum+"").find('.on').parent().attr("id")
			, i_sDiv : div
			, i_sRecipeType : "C"
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
	document.location.href = path;
	
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
function fn_part2ListView(partno,init){
	$.ajax({
		url : "/br/pw/020/br_pw_020_t2_document_view_ajax.do"			
		, type : "POST"
		, data : {
			i_sProductCd : j$("input[name='i_sProductCd']").val(),
			i_sPartNo : partno,
			i_nVer : j$("input[name='i_iVerSeq']").val(),
			i_sTrnsctreqid : j$("input[name='i_sTrnsctreqid']").val()
		}
		, dataType : "html"
		, success : function(html) {
			j$("#part2_tbl tbody").html(html);
			if(init){
				j$(".table_part2").hide();
				j$(".ul_tab_part2").hide();				
			}
		}
	});
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
		,i_sNationNm : nationNm
		,i_sVendorLaborId : $('input[name=i_sVendorLaborId]').val()
	};
	fn_s_saveMessage("검토의견",nationNm+"의 검토의견을 등록하시겠습니까?","/br/pw/020/br_pw_020_t2_document_review_save.do",postParam
		,function(){
			//location.href="/br/pw/020/br_pw_020_t2_document_view.do";
			var frm = j$("form[name='frm']");
			frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_document_view.do").submit();
		}
		,fn_validation
		,{nationCd:nationCd,nationNm:nationNm
		}
	);
// 	function callback(){
// 		$.ajax({
// 			url : 
// 			,data : postParam
// 			,dataType : "json"
// 			,type : "POST"
// 			,success : function(data){
// 				fn_s_alertMsg("저장되었습니다.",function(){
// 					location.href="/br/pw/020/br_pw_020_t2_document_view.do";
// 				});
// 			},error : function(jqXHR, textStatus, errorThrown){
// 				fn_s_failMsg(jqXHR, textStatus, errorThrown);
// 			}		
// 		});
// 	}
}
</script>
<form id="formZip" name="formZip" method="post"></form>
<form id="frm" name="frm" method="post">		
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sVendorId" 	value="${rvo.v_vendor_id}"/>
	<input type="hidden" name="i_sVendorLaborId" 	value="${rvo.v_vendor_labor_id}"/>
	<input type="hidden" name="i_sAdReqId" 	value="${rvo.v_ad_req_id }"/>
			
	<input type="hidden" name="i_sAuthFlag" value="${reqVo.i_sAuthFlag }" />
	<input type="hidden" name="i_iVerSeq" value="${rvo.n_vsn }" />  
	<input type="hidden" name="i_sProductNm" value="${rvo.v_productnm_en }" />
	<input type="hidden" name="i_sTrnsctreqId" value="${rvo.v_vendor_id }" /> 
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo }" /> 
	<input type="hidden" name="i_sImportUseryn" value="" />
	<input type="hidden" name="i_sGmBmAuth" value="${reqVo.i_sGmBmAuth }" />
	<input type="hidden" name="i_sGlbRecordId" value="${reqVo.i_sGlbRecordid }" />
	<input type="hidden" name="i_sPartSize" value="${partList.size()}" />


	<input type="hidden" name="i_sLaunchDt" value="${rvo.v_release_dt }" />
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">	
			<a id="req_file_list_btn" href="#" class="btnA bg_dark req_file_list_btn"><span>요청 서류 파일 목록</span></a>				 
			<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
		</c:if>
	</div>
	
	<!-- <div class="pd_top10"></div> -->		
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
	<!-- <div class="pd_top10"></div> -->
	

	<div class="table_all">
		<div class="pd_top10"></div>
		<c:if test="${reqVo.i_sExpDiv eq 'all' }">
			<c:set var="globalRaFlag" value="N" />
			<c:forEach items="${opnVoList }" var="ovo">
				<c:if test="${ovo.v_flag_global_ra eq 'Y'}">
					<c:set var="globalRaFlag" value="Y" />
				</c:if>
			</c:forEach>
		</c:if>
		<c:forEach items="${globalMailList }" var="vo">
			<c:if test="${reqVo.s_userid eq vo.v_userid}">
				<c:set var="globalRaFlag" value="Y" />
			</c:if>
		</c:forEach>
		
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right">			 -->
<!-- 				<a id="req_file_list_btn" href="#" class="btnA bg_dark req_file_list_btn"><span>요청 서류 파일 목록</span></a> -->
<%-- <%-- 				<c:if test="${reqVo.i_sExpDiv ne 'all' and (opnVo.v_status ne 'OK' or opnVo.v_report_status eq 'GRS002') and (fn:indexOf(reqVo.s_groupid, 'S000129') > -1 or reqVo.s_sysadmin_yn eq 'Y')}"> --%> 
<%-- 				<c:if test="${reqVo.i_sExpDiv ne 'all' and (opnVo.v_status ne 'OK' or opnVo.v_report_status eq 'GRS002') and (fn:indexOf(reqVo.s_groupid, 'ADMIN') > -1)}"> --%>
<!-- 					<a href="#" class="btnA bg_dark btn_save" id="btn_save"><span>저장</span></a> -->
<%-- 				</c:if>  --%>
<%-- <%-- 				<c:if test="${reqVo.i_sExpDiv ne 'all' and opnVo.v_status eq 'OK' and (fn:indexOf(reqVo.s_groupid, 'S000129') > -1 or reqVo.s_sysadmin_yn eq 'Y')}"> --%> 
<%-- 				<c:if test="${reqVo.i_sExpDiv ne 'all' and opnVo.v_status eq 'OK' and (fn:indexOf(reqVo.s_groupid, 'ADMIN') > -1)}">				 --%>
<!-- 					<a href="#" class="btnA bg_dark btn_opn_save" id="btn_opn_save"><span>의견 등록</span></a> -->
<%-- 				</c:if>  --%>
<!-- 				<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->

<%-- 		<c:if test="${rvo.v_import_yn eq 'Y' }"> --%>
<!-- 			<table class="sty_02"> -->
<!-- 				<colgroup> -->
<!-- 					<col width="15%" /> -->
<!-- 					<col width="85%" /> -->
<!-- 				</colgroup> -->
<!-- 				<tbody> -->
<!-- 					<tr> -->
<!-- 						<th class="ta_c">수입ODM자료</th> -->
<%-- 						<td class="ta_c"><c:choose> --%>
<%-- <%-- 								<c:when test="${rvo.v_vendor_id eq 'AP' and rvo.v_reg_dtm > 20161206000000 and rvo.v_import_yn eq 'Y' and empty rvo.v_import_labor }">  --%> 
<%-- 								<c:when test="${rvo.v_vendor_id eq 'AP' and rvo.v_reg_dtm > 20161206000000 and rvo.v_import_yn eq 'Y' }"> --%>
<%-- 									<CmTagLib:cmAttachFile uploadCd="INC2" recordId="${reqVo.i_sProductCd }" formName="frm" type="view" division="TIUM" /> --%>
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<%-- 									<CmTagLib:cmAttachFile uploadCd="INC" recordId="${reqVo.i_sProductCd }" formName="frm" type="view" /> --%>
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
<!-- 				</tbody> -->
<!-- 			</table> -->
<%-- 		</c:if> --%>

		<!-- <div class="pd_top10"></div> -->
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 		</ul> -->
		<div class="sec_cont mt_20 sec_acco">
			<a href="#" class="btnA bg_dark btn_zipDownPart1" style="float:right;"><span>전체 파일다운로드</span></a>
			<h2 class="tit">
				PartⅠ
				<img src="${ImgPATH}common/ico_plus.jpg" alt="" class="btn_part_show" data-part="part1">
				<img src="${ImgPATH}common/ico_minus.jpg" alt="" class="btn_part_hide" data-part="part1"> 
			</h2>

			<ul class="sty_tab ul_tab_part1">
				<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab_part1" id="${vo.n_part_no }">
						<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
					</li>
				</c:forEach>
			</ul>

			<table class="sty_03 table_part1">
				<colgroup>
	                <col style="width:22%;">
	                <col style="width:130px;">
	                <col style="width:;">
	                <col style="width:;">
	                <col style="width:;">
	                <col style="width:;">
				</colgroup>
				<thead>
					<tr>
						<th class="ta_c">Document</th>
						<th class="ta_c">서류요청</th>
						<th colspan='2' class="ta_c">미리보기</th>
						<th colspan='2' class="ta_c">Download</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td rowspan='2' class="bdl_n">package images</td>		
						<td rowspan='2' class="ta_c">
							<c:if test="${revVo.v_rev_userid eq reqVo.s_userid}">
								<a href="#" class="btnA bg_dark a_package_img_upload"> <!-- 파일 업로드 -->
									<span><img alt="" src="${ImgPATH}icon_attachfile.gif">&nbsp;파일 업로드</span>
								</a>
							</c:if>
						</td>
						<th>단상자</th>
						<th>용기</th>
						<th>내지</th>						
						<td class="ta_c" rowspan="2">
<%-- 							<c:choose> --%>
<%-- 								<c:when test="${revVo.v_rev_userid ne reqVo.s_userid}"> --%>
<!-- 									파일요청 -->
<!-- 									<a href="#" class="btnA bg_dark a_package_img_request"> -->
<%-- 										<span><img alt="" src="${ImgPATH}icon_attachfile.gif">&nbsp;파일요청</span> --%>
<!-- 									</a> -->
<%-- 								</c:when> --%>
<%-- 								<c:otherwise> --%>
<!-- 									요청완료 -->
<!-- 									<a href="#" class="btnA bg_dark a_package_img_complete"> -->
<%-- 										<span><img alt="" src="${ImgPATH}icon_recommend.gif">&nbsp;요청완료</span> --%>
<!-- 									</a> -->
<%-- 								</c:otherwise> --%>
<%-- 							</c:choose> --%>
						</td>
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
						<tr>
							<td class="bdl_n">batch coding system</td>
							<td class="ta_c"></td>
							<td class="ta_c" colspan='2'>
								<a class="img_btn" href="#none" onclick="showPreView('br/pw/020/doc/br_pw_020_doc_batch_pop', '', '1');">
									<img alt="" src="${ImgPATH}common/img_print.png">
								</a>
							</td>
							<td class="ta_c" colspan='2'>
								<a class="img_btn" href="#none" onclick="downloadPdf('br/pw/020/doc/br_pw_020_doc_part1_batch_report.do', '','1');">
									<img alt="" src="${ImgPATH}common/img_pdf.png">
								</a>
							</td>
						</tr>
						<tr>
							<td class="bdl_n">undesirable health effects summary</td>
							<td class="ta_c"></td>
							<td class="ta_c" colspan='2'>
								<a class="img_btn" href="#none" onclick="showPreView('br/pw/020/doc/br_pw_020_doc_health_pop', '', '1');">
									<img alt="" src="${ImgPATH}common/img_print.png">
								</a>
							</td>
							<td class="ta_c" colspan='2'>
								<a class="img_btn" href="#none" onclick="downloadPdf('br/pw/020/doc/br_pw_020_doc_part1_health_report.do', '','1');">
									<img alt="" src="${ImgPATH}common/img_pdf.png">
								</a>
							</td>
						</tr>
						<tr>
							<td class="bdl_n">ingredients list</td>
							<td class="ta_c"></td>
							<td class="ta_c" colspan='2'>								
								<a class="img_btn" href="#none" onclick="showPreView('br/pw/020/doc/br_pw_020_doc_ingredient_pop', '', '1')">
									<img src="${ImgPATH}common/img_print.png">
								</a>								
							</td>
							<td class="ta_c" colspan="2"></td>
						</tr>
						<tr>
							<td class="bdl_n">claim support summary</td>
							<td class="ta_c"></td>
							<td class="ta_c" colspan='2'>								
								<a class="img_btn" href="#none" id="btn_claim_support_preview">
									<img alt="" src="${ImgPATH}common/img_print.png">
								</a>								
							</td>
							<td class="ta_c" colspan="2">								
								<a class="img_btn" href="#none" id="btn_claim_support_pdf"> <img alt="" src="${ImgPATH}common/img_pdf.png"></a>								
							</td>
						</tr>
						<tr>
							<td class="bdl_n">certi of fragrance</td>
							<td class="ta_c"></td>
							<td class="ta_c" colspan="2">								
								<a class="img_btn" href="#none" id="btn_certi_fragrance_preview"> <img alt="" src="${ImgPATH}common/img_print.png"></a>								
							</td>
							<td class="ta_c" colspan="2">
								<a class="img_btn" href="#none" id="btn_certi_fragrance_pdf"> <img alt="" src="${ImgPATH}common/img_pdf.png"></a>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td class="bdl_n">기능성 심사증</td> -->
<!-- 							기능성심사증 -->
<!-- 							<td class="ta_c"> -->
<!-- <!-- 								<a href="#" class="btn_themeB fileReq_btn" id="D000010"> --> 
<!-- <!-- 									<span>서류요청</span> --> 
<!-- <!-- 								</a> --> 
<!-- 							</td> -->
<!-- 							<td class="ta_c" colspan="4"> -->
<%-- 								<c:if test="${reqVo.i_sAuthFlag eq 'Y' or fn:indexOf(expCompleteDoc, 'D000010') > -1 or opnVo.v_flag_global_ra eq 'Y' or globalRaFlag eq 'Y'}"> --%>
<%-- 									<CmTagLib:cmAttachFile uploadCd="ADM" recordId="${rvo.v_product_cd }_ADM" formName="frm" type="view" division="TIUM" /> --%>
<%-- 								</c:if> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr> -->
<!-- 							<td class="bdl_n"> -->
<!-- 								외품 허가증 -->
<!-- 							</td> -->
<!-- 							외품허가증 -->
<!-- 							<td class="ta_c"> -->
<!-- <!-- 								<a href="#" class="btn_themeB fileReq_btn" id="D000011"> --> 
<!-- <!-- 									<span>서류요청</span> --> 
<!-- <!-- 								</a> --> 
<!-- 							</td> -->
<!-- 							<td class="ta_c" colspan="4"> -->
<%-- 								<c:if test="${reqVo.i_sAuthFlag eq 'Y' or fn:indexOf(expCompleteDoc, 'D000011') > -1 or opnVo.v_flag_global_ra eq 'Y' or globalRaFlag eq 'Y'}"> --%>
<%-- 									<CmTagLib:cmAttachFile uploadCd="AST" recordId="${rvo.v_product_cd }_AST" formName="frm" type="view" /> --%>
<%-- 								</c:if> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
				</tbody>
			</table>
		</div>
				<!-- <div class="pd_top10"></div> -->
		<div class="sec_cont mt_60 sec_acco">
			
			<a href="#" class="btnA bg_dark btn_zipDownPart2" style="float:right;"><span>전체 파일다운로드</span></a>
								
			<h2 class="tit">
				Part Ⅱ 
				<img src="${ImgPATH}common/ico_plus.jpg" class="btn_part_show" data-part="part2" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
				<img src="${ImgPATH}common/ico_minus.jpg" class="btn_part_hide" data-part="part2" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
<!-- 						<a href="#" class="btn_themeB" id="part2_request_btn"><span>서류요청</span></a> -->
			</h2>
			<ul class="sty_tab  ul_tab_part2">
				<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab_part2" id="${vo.n_part_no }">
						<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
					</li>
				</c:forEach>
			</ul>
			<table class="sty_03 table_part2" id="part2_tbl">
				<colgroup>
					<col width="80px">   
					<col width="80px">   
					<col width="80px">   
					<col width="80px">   
					<col width="80px">   
<!-- 							<col width="80px">    -->
<!-- 							<col width="80px">    -->
<!-- 							<col width="80px">    -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="120px">   -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="80px">    -->
<!-- 							<col width="80px">    -->
<!-- 							<col width="90px">    -->
<!-- 							<col width="90px">    -->
					<col width="90px">   
				</colgroup>
				<thead>
					<tr>
						<th>원료코드</th>
						<th>SPEC</th>
<!-- 								<th>MSDS</th> -->
						<th>COA</th>
<!-- 								<th>색소 COA</th> -->
<!-- 								<th>색소 SPEC</th> -->
						<th>CCPP</th>
						<th>Certi</th>
<!-- 								<th class="ta_c">전초 추출물<br/>사용부위 확인서</th> -->
<!-- 								<th class="ta_c">추출물 원료<br/>유래 확인서</th> -->
<!-- 								<th class="ta_c">잔류 용매<br/>보고서</th> -->
<!-- 								<th class="ta_c">유기용매 분석<br/>보고서</th> -->
<!-- 								<th class="ta_c">자차 주성분USP<br/>적합 확인서</th> -->
<!-- 								<th class="ta_c">잔류농약보고서</th> -->
<!-- 								<th class="ta_c">잔류 용매<br/> 보고서</th> -->
<!-- 								<th class="ta_c">API 등록<br/>업체 확인서</th> -->
<!-- 								<th class="ta_c">regulatory<br />product<br />information</th> -->
<!-- 								<th class="ta_c">원료 안전성 자료</th> -->
<!-- 								<th class="ta_c">효능자료</th> -->
						<th>알러젠 확인서</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="6" class="ta_c">
							<a href="#none" id="part2LoadCon">
								:: 원료 정보 불러오기 ::
							</a><!-- 원료 정보 불러오기 -->							
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="sec_cont mt_60 sec_acco">	
			<a href="#" class="btnA bg_dark btn_zipDownPart3" style="float:right;"><span>전체 파일다운로드</span></a>			
			<h2 class="tit">
				Part Ⅲ 
				<img src="${ImgPATH}common/ico_plus.jpg" class="btn_part_show" data-part="part3" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
				<img src="${ImgPATH}common/ico_minus.jpg" class="btn_part_hide" data-part="part3" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
			</h2>
			<ul class="sty_tab ul_tab_part3">
				<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab_part3" id="${vo.n_part_no }">
						<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
					</li>
				</c:forEach>
			</ul>
			<table class="sty_03 table_part3">
				<colgroup>
	              <col style="width:220px;">
	              <col style="width:200px;">
	              <col style="width:;">
	              <col style="width:;">
<!-- 			              <col style="width:;"> -->
<!-- 			              <col style="width:;"> -->
				</colgroup>
				<thead>
					<tr>
						<th>Document</th>
						<th>서류요청</th>
						<th colspan='1'>미리보기</th>
						<th colspan='1'>Download</th>
					</tr>
				</thead>
				<tbody>					
					<tr>
						<td rowspan='1' class="bdl_n">IL</td> <!-- 단일처방 -->
						<td rowspan='1' class="ta_c"></td>
<!-- 									<th>Allergen</th> -->
<!-- 									<th>Allergen(ASEAN)</th> -->
<!-- 									<th>Allergen(참고)</th> -->
<!-- 									<th>Allergen(ASEAN)</th> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
						<td class="ta_c">
							<a class="img_btn" href="#none" onclick="showPreView('br/pr/012/doc/co_doc_formula_pdf_print', 'Y', '3', 'Y')">
								<img src="${ImgPATH}common/img_print.png">
							</a>
						</td>
<!-- 															<td class="ta_c"> -->
<!-- 																<a class="img_btn" href="#none" onclick="showPreView('br/pr/012/doc/co_doc_formula_pdf_print', 'Y', '3', '')"> -->
<%-- 																	<img src="${ImgPATH}common/img_print.png"> --%>
<!-- 																</a> -->
<!-- 															</td> -->					
						<td class="ta_c">
							<a class="img_btn" href="#none" onclick="downloadPdf('br/pr/012/doc/co_doc_formula_excel.do', 'Y','3','Y');">
								<img src="${ImgPATH}common/img_xls.png">
							</a>
							<a class="img_btn" href="#none" onclick="download_PDF('S', '3', 'Y', '', 'Y');">
								<img alt="" src="${ImgPATH}common/img_pdf.png">
							</a>
						</td>
						<!--<td class="ta_c"> -->
						<!--	<a class="img_btn" href="#none" onclick="downloadPdf('br/pr/012/doc/co_doc_formula_excel.do', 'Y','3');"> -->
						<%--		<img src="${ImgPATH}common/img_xls.png"> --%>
						<!--	</a> -->
						<!--	<a class="img_btn" href="#none" onclick="download_PDF('S', '3', 'Y', '', '');"> -->
						<%--		<img alt="" src="${ImgPATH}common/img_pdf.png"> --%>
						<!--	</a> -->
						<!--</td> -->
					</tr>
					<tr>
						<td class="bdl_n">QQ</td>
						<!-- 복합처방  -->
						<td class="ta_c"></td>
						<td class="ta_c" colspan='1'>							
							<table class="sty_03" style="width: 98%; margin: 5px;">
								<tbody>
									<tr>
										<th class="bdl_n">Ko</th>
										<th>Jp</th>
										<th>Cn</th>
									</tr>
									<tr>
										<td class="ta_c bdl_n">
											<a class="img_btn" href="#none" onclick="showPreViewC('br/pr/012/doc/co_doc_complex_formula_pdf_print', 'Y', '3','','ko')">
												<img src="${ImgPATH}common/img_print.png">
											</a>
										</td>
										<td class="ta_c">
											<a class="img_btn" href="#none" onclick="showPreViewC('br/pr/012/doc/co_doc_complex_formula_pdf_print', 'Y', '3','','jp')">
												<img src="${ImgPATH}common/img_print.png">
											</a>
										</td>
										<td class="ta_c">
											<a class="img_btn" href="#none" onclick="showPreViewC('br/pr/012/doc/co_doc_complex_formula_pdf_print', 'Y', '3','','cn')">
												<img src="${ImgPATH}common/img_print.png">
											</a>
										</td>
									</tr>
								</tbody>
							</table>							
						</td>
						<td class="ta_c" colspan='1'>							
							<table class="sty_03" style="width: 98%; margin: 5px;">
								<tbody>
									<tr>
										<th class="bdl_n">Ko</th>
										<th>Jp</th>
										<th>Cn</th>
									</tr>
									<tr>
										<td class="ta_c bdl_n">
											<a class="img_btn" href="#none" onclick="downloadPdf('br/pr/012/doc/co_doc_complex_formula_excel_ko.do', 'Y', '3');">
												<img src="${ImgPATH}common/img_xls.png">
											</a>
											<a class="img_btn" href="#none" onclick="download_PDF('C', '3', 'Y', 'ko');">
												<img alt="" src="${ImgPATH}common/img_pdf.png">
											</a>
										</td>
										<td class="ta_c">
											<a class="img_btn" href="#none" onclick="downloadPdf('br/pr/012/doc/co_doc_complex_formula_excel_jp.do', 'Y','3');">
												<img src="${ImgPATH}common/img_xls.png">
											</a>
											<a class="img_btn" href="#none" onclick="download_PDF('C', '3', 'Y', 'jp');">
												<img alt="" src="${ImgPATH}common/img_pdf.png">
											</a>
										</td>
										<td class="ta_c">
											<a class="img_btn" href="#none" onclick="downloadPdf('br/pr/012/doc/co_doc_complex_formula_excel_cn.do', 'Y', '3');">
												<img src="${ImgPATH}common/img_xls.png">
											</a>
											<a class="img_btn" href="#none" onclick="download_PDF('C', '3', 'Y', 'cn');">
												<img alt="" src="${ImgPATH}common/img_pdf.png">
											</a>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="bdl_n">공정도</td>
						<!-- 복합처방 공정도 -->
						<td class="ta_c">
<!-- 										<a href="#" class="btn_themeB fileReq_btn" id="D000015"> -->
<!-- 											<span>서류요청</span> -->
<!-- 										</a> -->
						</td>
						<td class="ta_c" colspan='1'>								
							<a class="img_btn" href="#" id="btn_preview_process"> 
								<img src="${ImgPATH}common/img_print.png">
							</a>								
						</td>
						<td class="ta_c" colspan='1'>								
							<a class="img_btn" href="#" id="btn_excel_process"> 
								<img src="${ImgPATH}common/img_xls.png">
							</a>
							<a class="img_btn" href="#none" onclick="download_PDF('P', '3', '', '');"> 
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
					
<%-- 							<c:if test="${fn:indexOf(expDocList, 'D000017') > -1 or reqVo.i_sExpDiv eq 'all' }"> --%>
<!-- 								<tr> -->
<!-- 									<td class="bdl_n">stability</td> -->
<!-- 									<td class="ta_c"> -->
<!-- <!-- 										<a href="#" class="btn_themeB fileReq_btn" id="D000017"> --> 
<!-- <!-- 											<span>서류요청</span> -->
<!-- <!-- 										</a> --> 
<!-- 									</td> -->
<!-- 									<td colspan="2"> -->
<%-- 										<c:if test="${reqVo.i_sAuthFlag eq 'Y' or fn:indexOf(expCompleteDoc, 'D000017') > -1 or opnVo.v_flag_global_ra eq 'Y' or globalRaFlag eq 'Y'}"> --%>
<%-- 											<CmTagLib:cmAttachFile uploadCd="PSTAB" recordId="${reqVo.i_sRecordId}" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" /> --%>
<%-- 										</c:if> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:if> --%>
<%-- 							<c:if --%>
<%-- 								test="${fn:indexOf(expDocList, 'D000018') > -1 or reqVo.i_sExpDiv eq 'all' }"> --%>
<!-- 								<tr> -->
<!-- 									<td class="bdl_n">spec</td> -->
<!-- 									<td class="ta_c"> -->
<!-- <!-- 										<a href="#"class="btn_themeB fileReq_btn" id="D000018"> -->
<!-- <!-- 											<span>서류요청</span> -->
<!-- <!-- 										</a> --> 
<!-- 									</td> -->
<!-- 									<td class="last" colspan="2"> -->
<%-- 										<c:if test="${reqVo.i_sAuthFlag eq 'Y' or fn:indexOf(expCompleteDoc, 'D000018') > -1 or opnVo.v_flag_global_ra eq 'Y' or globalRaFlag eq 'Y'}"> --%>
<%-- 											<c:forEach items="${partList }" var="vo" varStatus="s"> --%>
<%-- 												<div class="spec_file_div" id="spec_file_div_${vo.n_part_no }" style="display:${reqVo.i_sPartNo eq vo.n_part_no? 'block':'none'}"> --%>
<%-- 													<CmTagLib:cmAttachFile uploadCd="PSPEC" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" /> --%>
<!-- 												</div> -->
<%-- 											</c:forEach> --%>
<%-- 										</c:if> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:if> --%>
					
						<tr>
							<td class="bdl_n">MSDS</td>
							<td class="ta_c">
<!-- 										<a href="#" class="btn_themeB fileReq_btn" id="D000019"> -->
<!-- 											<span>서류요청</span> -->
<!-- 										</a> -->
							</td>
							<td class="ta_c" colspan='1'>								
								<a class="img_btn" href="#none" onclick="showPreView('br/pr/012/doc/co_doc_msds_pdf_print', 'Y', '3')">
									<img src="${ImgPATH}common/img_print.png">
								</a>
							</td>
							<td class="ta_c" colspan='1'>								
<!-- 													<td> -->
<!-- 														<a onclick="downloadPdf('br/pr/012/doc/co_doc_msds_excel.do', 'N','3');" href="#none">  -->
<%-- 															<img src="${ImgPATH}common/img_xls.png"> --%>
<!-- 														</a> -->
<!-- 														 <a href="#none" onclick="download_PDF('M', '3', 'N', '');" href="#none">  -->
<%-- 															<img alt="" src="${ImgPATH}common/img_pdf.png"> --%>
<!-- 														</a> -->
<!-- 													</td> -->
								<a class="img_btn" onclick="downloadPdf('br/pr/012/doc/co_doc_msds_excel.do', 'Y','3');" href="#none"> 
									<img src="${ImgPATH}common/img_xls.png">
								</a> 
								<a class="img_btn" href="#none" onclick="download_PDF('M', '3', 'Y', '');" href="#none"> 
									<img alt="" src="${ImgPATH}common/img_pdf.png">
								</a>							
							</td>
						</tr>							
						<tr>
							<td class="bdl_n">안정성</td>
							<!-- 복합처방 공정도 -->
							<td class="ta_c"></td>
							<td class="ta_c" colspan='1'>								
								<a class="img_btn" href="#" id="btn_preview_stability"> 
									<img src="${ImgPATH}common/img_print.png">
								</a>								
							</td>
							<td class="ta_c" colspan='1'>
								<a class="img_btn" href="#none" id="btn_pdf_stability"> 
									<img alt="" src="${ImgPATH}common/img_pdf.png">
								</a>
							</td>
						</tr>		
					
<%-- 							<c:if test="${fn:indexOf(expDocList, 'D000021') > -1 or reqVo.i_sExpDiv eq 'all' }"> --%>
<!-- 								<tr> -->
<!-- 									<td class="bdl_n">시험성적서</td> -->
<!-- 									시험성적서 -->
<!-- 									<td class="ta_c"> -->
<!-- <!-- 										<a href="#" class="btn_themeB fileReq_btn" id="D000021"> -->
<!-- <!-- 											<span>서류요청</span> --> 
<!-- <!-- 										</a> -->
<!-- 									</td> -->
<!-- 									<td class="last" colspan="2"> -->
<%-- 									<c:forEach items="${revFileVo }" var="fvo"> --%>
<%-- 											<c:if test="${fvo.FILE_DIV eq 'LT' }"> --%>
<%-- 												<a class="link_btn" data-link="${fvo.ATT_FILE_FULL_PATH }"style="cursor: pointer;">  --%>
<%-- 													<c:if test="${!empty fvo.ATT_NO}">[${fvo.ATT_NO }]</c:if>  --%>
<!-- 													<font style="font-weight: bold;">[링크]</font> -->
<!-- 												</a> -->
<!-- 												<br /> -->
<%-- 											</c:if> --%>
<%-- 										</c:forEach> --%>
<!-- 									</td> -->
<!-- 								</tr> -->
<%-- 							</c:if> --%>
					
					<tr>
						<td class="bdl_n">제품표준서</td>									
						<td class="ta_c">
<!-- 										<a href="#" class="btn_themeB fileReq_btn" id="D000022">--> 
<!-- 											<span>서류요청</span>--> 
<!-- 										</a>  -->
						</td>
						<td class="last" colspan="2">
							<CmTagLib:cmAttachFile uploadCd="FR016" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" formName="frm" type="viewLog" />
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="bdl_n">포장재유해성 결과 보고서</td> -->
<!-- 						포장재유해성 -->
<!-- 						<td class="ta_c"> -->
<!-- 							<a href="#" class="btnA bg_dark fileReq_btn" id="D000026"> -->
<!-- 								<span>서류요청</span> -->
<!-- 							</a> -->
<!-- 						</td> -->
<!-- 						<td class="ta_c" colspan="2"> -->
<%-- 							<c:forEach items="${revFileVo }" var="fvo"> --%>
<%-- 								<c:if test="${fvo.FILE_DIV eq 'QM' }"> --%>
<%-- 									<a class="link_btn" data-link="${fvo.ATT_FILE_FULL_PATH }"style="cursor: pointer;">  --%>
<%-- 										<c:if test="${!empty fvo.ATT_NO}">[${fvo.ATT_NO }]</c:if>  --%>
<!-- 										<font style="font-weight: bold;">[링크]</font> -->
<!-- 									</a> -->
<!-- 									<br /> -->
<%-- 								</c:if> --%>
<%-- 							</c:forEach> --%>
<!-- 						</td> -->
<!-- 					</tr> -->
				</tbody>
			</table>
		</div>
		<!-- <div class="pd_top10"></div> -->
		
		<div class="sec_cont mt_60 sec_acco">				
			<a href="#" class="btnA bg_dark btn_zipDownPart4" style="float:right;"><span>전체 파일다운로드</span></a>
			<h2 class="tit">
				Part Ⅳ 
				<img src="${ImgPATH}common/ico_plus.jpg" class="btn_part_show" data-part="part4" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
				<img src="${ImgPATH}common/ico_minus.jpg" class="btn_part_hide" data-part="part4" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
			</h2>

			<table class="sty_02 table_part4">
				<colgroup>
					<col width="40%" />
					<col width="10%" />
					<col width="50%" />
				</colgroup>
				<thead>
					<tr>
						<th class="ta_c">Document</th>
						<th class="ta_c">서류요청</th>
						<th class="ta_c">Download</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cmDocList }" var="vo">
						<tr class="commonDoc">
							<td class="td_bold" style="position: relative;">
								${vo.COMM_CD_NM }
							</td>
							<td style="text-align: center;">
								<a href="#"  class="btnA bg_dark fileReq_btn" id="${vo.COMM_CD }" docNm="${vo.COMM_CD_NM }">
									<span>서류요청</span>
								</a>
							</td>
							<td class="last">
								<c:choose>
									<c:when test="${vo.COMM_CD eq 'FR021' }">
										<CmTagLib:cmAttachFile uploadCd="PSPEC" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" />
									</c:when>
									<c:when test="${vo.COMM_CD eq 'FR022' }">
										<CmTagLib:cmAttachFile uploadCd="MSDS" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" formName="frm" type="view" />
									</c:when>
									<c:when test="${vo.COMM_CD eq 'FR023' }">
										<CmTagLib:cmAttachFile uploadCd="PSTAB" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="view" />
									</c:when>
									<c:otherwise>
										<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="viewLog" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					<c:forEach items="${docList }" var="vo">						
						<c:if test="${vo.COMM_CD ne 'FR013' or (vo.COMM_CD eq 'FR013' and fn:indexOf(rvo.v_exp_country, 'CN')>-1)}">
							<tr class="glDoc">
								<td class="td_bold" style="position: relative;">
									${vo.COMM_CD_NM } 
									<c:if test="${vo.COMM_CD eq 'D000024'}">
										<!-- human patch test 외 안전성 보고서 -->
										<div style="position: absolute; bottom: 5px; font-size: 12px; color: red;">
											*임상기관 안전성 리포트, AP 안전성 리포트에 업로드된 자료만 수출용 안전성 자료로 제출가능<br />
											**ODM사 안전성 리포트로 증빙할 경우 안전성Lab에 문의
										</div>
									</c:if>
								</td>
								<td style="text-align: center;">
									<c:choose>
										<c:when test="${vo.COMM_CD eq 'FR013' }">
											
										</c:when>
										<c:otherwise>													
											<a href="#"  class="btnA bg_dark fileReq_btn" id="${vo.COMM_CD }">
												<span>서류요청</span>
											</a>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="ta_c last">
									<c:if test="${reqVo.i_sAuthFlag eq 'Y' or fn:indexOf(expCompleteDoc, vo.COMM_CD) > -1 or opnVo.v_flag_global_ra eq 'Y' or globalRaFlag eq 'Y'}">
									<c:choose>
										<c:when test="${vo.COMM_CD eq 'FR013' }">
												<a class="img_btn" href="#none" onclick="showPreView('br/pw/020/br_pw_020_doc_china_commission_product_pop', '4', '1');">
													<img alt="" src="${ImgPATH}common/img_print.png">
												</a>
												<a class="img_btn" href="#none" onclick="downloadPdf('br/pw/020/doc/br_pw_020_doc_china_commission_product.do', '4', '1');">
													<img alt="" src="${ImgPATH}common/img_word.png">
												</a>
										</c:when>
										<c:otherwise>
											<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" formName="frm" type="view" />
										</c:otherwise>
									</c:choose>
											
<%-- 												<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sProductCd }_${vo.COMM_CD }" formName="frm" type="view" /> --%>
									</c:if>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		
		<c:if test="${rvo.v_fast_track_yn ne 'Y' }">
			<!-- <div class="pd_top10"></div> -->
			<div class="sec_cont mt_60 sec_acco">
				<h2 class="tit">
					국가별 검토
					<img src="${ImgPATH}common/ico_plus.jpg" class="btn_part_show" data-part="review" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
					<img src="${ImgPATH}common/ico_minus.jpg" class="btn_part_hide" data-part="review" style="cursor: pointer; vertical-align: middle; width: 18px; height: 18px;" />
				</h2>
				
				<table class="sty_02 table_review">
				<colgroup>
					<col width="100px" />
					<col width="100px" />
					<col width="200px" />
					<col width="200px" />
					<col width="140px" />
					<col width="120px" />
					<col width="120px" />
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
						<c:if test="${revVo.v_nation_cd ne 'KO'}">
							<tr id='tr_review_${revVo.v_nation_cd }'>
								<td class="ta_c">${revVo.v_nation_nm } <span name="reveiwIcon" nationCd="${revVo.v_nation_cd }" style="vertical-align: middle;" class="dxi dxi-chevron-down"></span></td>
								<td class="ta_c">${revVo.v_reviwer_nm }</td>
								<c:choose>
	<%-- 								<c:when test="${(rvo.v_final_rst eq 'RS063' or rvo.v_final_rst eq 'RS040' or rvo.v_final_rst eq 'RS065') and ((empty revVo.v_reviwer_id or revVo.v_reviwer_id eq reqVo.s_userid) and revVo.v_review_status ne 'NS020')}"> --%>
									<c:when test="${fn:indexOf(reqVo.s_groupid,'ADMIN') > -1 or (( rvo.v_final_rst ne 'RS060') and ((empty revVo.v_reviwer_id or revVo.v_reviwer_id eq reqVo.s_userid) and revVo.v_review_status ne 'NS020'))}">
										<td>
											<textarea class="textarea_sty01" rows="2" name="i_sReveiwOp_${revVo.v_nation_cd }" id="i_sReveiwOp_${revVo.v_nation_cd }"class="inp_sty01" style="width: 98%;" >${revVo.v_review_op }</textarea>
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
											<select class="select_sty01" id="i_sReveiwStatus_${revVo.v_nation_cd }" name="i_sReveiwStatus_${revVo.v_nation_cd }">
												<option value="">선택</option>
												<option value="MODI_REQ">수정요청</option>
												<c:forEach items="${reviewStList }" var="rsVo">
													<c:if test="${empty rsVo.BUFFER1 or (rsVo.BUFFER1 eq 'CN' and revVo.v_nation_cd eq rsVo.BUFFER1)}">
														<option value="${rsVo.COMM_CD }" <c:if test="${rsVo.COMM_CD eq  revVo.v_review_status}">selected</c:if>>${rsVo.COMM_CD_NM }</option>
													</c:if>
												</c:forEach>
											</select>
											<a href="javascript:;" class="btnA bg_dark" id="btn_reviewSave_${revVo.v_nation_cd }" name="btn_reviewSave_${revVo.v_nation_cd }"  style="cursor: pointer;"  onclick="reviewSave('${revVo.v_nation_cd }','${revVo.v_nation_nm }');">저장</a>
											
			<%-- 								<img id="btn_reviewSave_${revVo.v_nation_cd }" name="btn_reviewSave_${revVo.v_nation_cd }" src="${ImgPATH}icon_attachfile.gif" style="cursor: pointer;" class="img" onclick="reviewSave('${revVo.v_nation_cd }');"> --%>
										</td>
									</c:when>
									<c:otherwise>						
										<td>${revVo.v_review_op }</td>
										<td></td>
										<td>${revVo.v_review_status_nm }<input type="hidden" value="${revVo.v_review_status }" id="i_sReveiwStatus_${revVo.v_nation_cd }" name="i_sReveiwStatus_${revVo.v_nation_cd }"/></td>
									</c:otherwise>
								</c:choose>
								<td class="ta_c">${cfn:getStrDateToString(revVo.v_request_dtm,'yyyy-MM-dd')}</td>
								<td class="ta_c">${cfn:getStrDateToString(revVo.v_review_dtm,'yyyy-MM-dd')}</td>
							</tr>
						</c:if>
		<%-- 			<tr id='tr_review_message_${s.index }' style="display:none;"></tr> --%>
					</c:forEach>
				</tbody>
				</table>
			</div>
		</c:if>
	</div>
</form>
<c:if test="${rvo.v_fast_track_yn ne 'Y' }">		
	<form id="frmReviewCn" name="frmReviewCn" method="post">	
		<div id="divReviewCn" style="display:none;" class="sec_cont">		
			<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
			<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
			<h2 class="tit">
				중국 의견
			</h2>
			<ul class="btn_area">
				<li class="right">
					<a href="#" class="btnA bg_dark btn_reviewCnSave" id="btn_reviewCnSave"><span>저장</span></a>
				</li>
			</ul>
			<table class="sty_02 table_review_cn">
				<colgroup>
						<col width="180px" />
						<col width=";" />
						<col width="180px" />
						<col width=";" />
				</colgroup>
				<tbody>
					<tr>
						<th>중국 화장품 분류</th>
						<td colspan='3'>
							<select id="i_sSpecialYn" name="i_sSpecialYn" class="select_sty01">
								<option value="">선택</option>
								<option value="Y" ${rvo.v_special_yn eq 'Y' ?'selected':''}>특수</option>
								<option value="N" ${rvo.v_special_yn eq 'N' ?'selected':''}>보통</option>
							</select> 
						</td>
					</tr>
					<tr>
						<th>중국 RA</th>
						<td colspan='3'>
							<select id="i_sChinaRaAgent" name="i_sChinaRaAgent" class="select_sty01">
								<option value="">선택</option>
								<c:forEach items="${cnRaVo }" var="raVo">
									<option value="${raVo.v_user_id }" ${rvo.v_china_ra_agent eq raVo.v_user_id? 'selected':''}>${raVo.v_user_nm }(${raVo.v_login_id })</option>
								</c:forEach>									
							</select> 
						</td>
					</tr>
					<tr>
						<th>중국 SPEC</th>
						<td colspan='3'>
							<table class="sty_02" style="width:100%;">
								<colgroup>
									<col width="200px">
									<col width=";">
									<col width=";">
									<col width="200px;">
									<col width=";">
									<col width=";">
								</colgroup>
								<tr>
									<th>COLOR</th>
									<td colspan='5'>
										<input type="text" id="i_sColorState" name="i_sColorState" value="${rvo.v_color_state }" class="inp_sty01" style="width: 300px;" />
									</td>
								</tr>
								<tr>
									<th>PHYSICAL STATE</th>
									<td colspan='5'>
										<input type="text" id="i_sPhysicalState" name="i_sPhysicalState" value="${rvo.v_physical_state }" class="inp_sty01" style="width: 300px;" />
									</td>
								</tr>
								<tr>
									<th>ODOR</th>
									<td colspan='5'>
										<input type="text" id="i_sOdorState" name="i_sOdorState" value="${rvo.v_odor_state }" class="inp_sty01" style="width: 300px;" />
									</td>
								</tr>
								<tr>
									<th>ml</th>
									<td colspan='1' class="ta_c">
										<a href="#none"id="ml" class="cnReviewPreView img_btn"><img src="${ImgPATH}common/img_print.png"></a>
									</td>
									<td colspan='1' class="ta_c">
										<a href="#none"id="ml" class="cnReviewExcel img_btn"><img src="${ImgPATH}common/img_xls.png"></a>
									</td>
									<th>mg</th>
									<td colspan='1' class="ta_c">
										<a href="#none"id="mg" class="cnReviewPreView img_btn"><img src="${ImgPATH}common/img_print.png"></a>
									</td>
									<td colspan='1' class="ta_c">
										<a href="#none"id="mg" class="cnReviewExcel img_btn"><img src="${ImgPATH}common/img_xls.png"></a>
									</td>
								</tr>
							</table>		
						</td>
					</tr>
					<tr>
						<th>허가 완료일</th>
						<td id="tdChinaFinDtm" colspan='3'>
							<input type="text" id="i_sChinaFinDtm" name="i_sChinaFinDtm" value="${cfn:getStrDateToString(rvo.v_china_fin_dtm,'yyyy-MM-dd')}" class="inp_sty01 calendar" alt="완료일" style="width: 152px; cursor: pointer;" readOnly />
															
						</td>
						<th id="thChinaSpDtm" style="display:none;">허가 만료일</th>
						<td id="tdChinaSpDtm" style="display:none;">
							<input type="text" id="i_sChinaSpDtm" name="i_sChinaSpDtm" value="${cfn:getStrDateToString(rvo.v_china_sp_dtm,'yyyy-MM-dd')}" class="inp_sty01" alt="만료일" style="width: 152px; cursor: pointer;" readOnly />
							
						</td>
					</tr>
					<tr>
						<th>중국 허가증</th>
						<td colspan="3">
							<span class="fileinput-button" style="margin: 5px;">
								<a href="#" class="btnA bg_dark"><span>파일첨부</span></a>
								<input id="btn_cNFileAdd" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
							</span>
							<CmTagLib:cmAttachFile uploadCd="CNR" type="reg" recordId="${rvo.v_product_cd}" formName="frmReviewCn"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</c:if>
<div class="pd_top10"></div>
<ul class="btn_area">
	<li class="right">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a id="req_file_list_btn" href="#" class="btnA bg_dark req_file_list_btn"><span>요청 서류 파일 목록</span></a>
			<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
		</c:if>
	</li>
</ul>
<script id="dot_upload" type="text/x-dot-templete">
	<tr>
		<td>
			{{=it.v_attach_lnm}}
			<span class="span_action_type span_hide">R</span>
			<div class="div_attach" id="div_{{=it.v_attach_id}}">
				<input type="hidden" id="inp_attach_id" name="{{=it.v_upload_cd}}_attach_id" value="{{=it.v_attach_id}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_type" value="{{=it.v_attach_type}}"/>
				<input type="hidden" id="inp_attach_lnm" name="{{=it.v_upload_cd}}_attach_lnm" value="{{=it.v_attach_lnm}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_pnm" value="{{=it.v_attach_pnm}}"/>
				<input type="hidden" id="inp_attach_size" name="{{=it.v_upload_cd}}_attach_size" value="{{=it.n_attach_size}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_mgtid" value="{{=it.v_attach_mgtid}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_pk1" value="{{=it.v_pk1}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_pk2" value="{{=it.v_pk2}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_pk3" value="{{=it.v_pk3}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_pk4" value="{{=it.v_pk4}}"/>
				<input type="hidden" name="{{=it.v_upload_cd}}_attach_pk5" value="{{=it.v_pk5}}"/>
			</div>
		</td>
		<td>{{=it.n_attach_size}} KB</td>
		<td class="last">
			<a href="javascript:this.onclick;" onclick="javascript:attachDel(j$(this), 'frm', '{{=it.v_upload_cd}}');" class="btn_attach_del" id="{{=it.v_attach_id}}"><img src="{{=it.del_img_url}}"/></a>
		</td>
	</tr>
</script>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
