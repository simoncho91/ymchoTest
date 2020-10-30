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
.table_line {border-top:1px solid #ddd; width: 100%; table-layout:fixed;}
.table_line th {text-align:lect; font-weight:bold; border-left:1px solid #ddd; padding:5px; margin:5; font-size: 15px;}
.table_line th.last {border-right:1px solid #ddd;}
.table_line td {text-align:left; border-left:1px solid #ddd; padding:5px; margin:5px; word-break:keep-all; font-size: 14px; line-height: 24px;}
.table_line td.lastRow {border-bottom:1px solid #ddd;}
.table_line td.last {border-right:1px solid #ddd;}
</style>
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/010/br_pr_010_reg.js"></script>
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
	 fn_init();
 });
 
 function fn_init(){	
	inputChkRadioAddEvent();
	
	var index = j$(".tbody_product_info").length;
	
	for(var i=0; i<index; i++){
		 jfupload.initUpload({
			target : j$("#admst_upload_btn_" + i)
			, uploadCd : "PON"
			, index : i
			, formName : "frm"
			, success : OreqReg.addEvent.attachSuccEvent
			, isSelfMakeTag : true
			, attachDir : "PON"
			, buffer1 : j$("input[name='i_arrProduct_Refcd']").eq(i).val()
		});
	}

	j$("input[name='i_sImportyn']").click(function(){
				
		// 수입반제품 여부 관련 function
		if(j$("input[name='i_sImportyn']").prop("checked")){
			j$("#span_ck_import").show();
			j$("#span_ck_import_ap_odm").show();
			//userSearch.setUserSearchType(j$("input[name='i_sCompanyDist']:checked").val());
			j$(".span_companynm").text("");
			j$("#table_import").show();
		}
		else{
			j$("#span_ck_import").hide();
			j$("#span_ck_import_ap_odm").hide();
			j$("#i_sCompanycd").parents("span").eq(0).show();
			j$("#i_sCompanyDist_S").attr("checked", true);
			j$("#odm_user_search_div").show();
			j$("#ap_user_search_div").hide();
			j$("#table_import").hide();
			//userSearch.setUserSearchType("S");
			
			if(isEmpty(j$("#i_sCompanycd").val())) {
				j$(".span_companynm").text("담당자를 선택해주세요.");
			}
		}
	});
		
	// 본품여부 체크 이벤트
	j$(".div_origin_yn").find("input[type='radio']").click(function(){
		var index = j$(".div_origin_yn").index(j$(this).parents(".div_origin_yn").eq(0));
		//var val = j$('.div_origin_yn input[type=radio]:checked')[index].value;
		var val = j$(this).parents(".div_origin_yn").find('input[type=radio]:checked').val();
		OreqReg.addEvent.divSetRadioShowHide(index,val);
	});
	
	// 본품여부 N 라디오 이벤트
	j$(".div_set_radio").find("input[type='radio']").click(function(){
		var index = j$(".div_set_radio").index(j$(this).parents(".div_set_radio").eq(0));
		OreqReg.addEvent.divSetRadioNShowHide(index);
		//OreqReg.addEvent.radio_origin_div_yn(index);
	});
	
	// 기능성 라디오 체크 이벤트
	j$(".div_func_yn").find("input[type='radio']").click(function(){
		var index = j$(".div_func_yn").index(j$(this).parents(".div_func_yn").eq(0));	
		OreqReg.addEvent.radioFunc(index);
	});
	
	j$(".div_exp_country").find("input[type='checkbox']").click(function(){
		var index = j$(".div_exp_country").index(j$(this).parents(".div_exp_country").eq(0));
		OreqReg.addEvent.divExpCountryShowHide(index);
	});
	
	// 상품정보 입력란 추가
	j$(".btn_add_product").click(function(event){
		event.preventDefault();		
		
		var index = j$(".tbody_product_info").length;
		OreqReg.addEvent.addProduct(index);
	});

	
	// 임시저장
	j$(".a_save_temp").click(function(event){
		event.preventDefault();
		fn_save("T");
	});
	
	// 요청시작
	j$(".a_appr").click(function(event){
		event.preventDefault();
		fn_save();
	});	
	// 목록가기
	j$(".a_btn_list").click(function(event){
		event.preventDefault();
		location.href="/br/pr/010/init.do?openMenuCd=MIBRPR010";
	});
		
	j$(".btn_product_del").click(function(event){
		event.preventDefault();
		j$(this).parents(".tbody_product_info").eq(0).remove();
	});
	
	j$("select[name='i_arrCntrForm']").change(function(){
		var div_idx = j$("select[name='i_arrCntrForm']").index(j$(this));
		console.log(div_idx);
		if(j$(this).val() == 'OCT_1_21'){
			j$(".CntrForm_etc_div").eq(div_idx).show();
		}else{
			j$(".CntrForm_etc_div").eq(div_idx).hide();
			j$(".CntrForm_etc_div").find("input[name='i_arrCntrForm_etc']").val("");
		}
	});
	
	j$("select[name='i_arrCntrMatr']").change(function(){
		var div_idx = j$("select[name='i_arrCntrMatr']").index(j$(this));
		
		if(j$(this).val() == 'OCT_2_04'){
			j$(".CntrMatr_etc_div").eq(div_idx).show();
		}else{
			j$(".CntrMatr_etc_div").eq(div_idx).hide();
			j$(".CntrMatr_etc_div").find("input[name='i_arrCntrMatr_etc']").val("");
		}
	});
	
	j$("input[name='i_sImport']").click(function(){
		if(j$(this).val() == "F"){
			j$(".maker_tr").eq(1).hide();
			j$("input[name='i_sL_mager_cd']").val("");
		}else{
			j$(".maker_tr").show();
		}
	});
	 
	//s :  제품담당자 팝업 컨트롤
	 j$('#search_bm').on('click',function(){	 
		 fn_pop({url:'/cm/pop/cm_user_list_pop.do?i_sCmFunction=setUserPopUpData&i_sInput='+encodeURI($('#i_sUsernm').val()),title:'제품담당자',width:'700',height:'500'});
	 });
	 j$('#i_sUsernm').on('dblclick',function(){	 
		 fn_pop({url:'/cm/pop/cm_user_list_pop.do?i_sCmFunction=setUserPopUpData&i_sInput='+encodeURI($('#i_sUsernm').val()),title:'제품담당자',width:'700',height:'500'});
	 });	 
	 j$('#i_sUsernm').on('keypress',function(e){
		 if(e.keyCode=='13'){
			 fn_pop({url:'/cm/pop/cm_user_list_pop.do?i_sCmFunction=setUserPopUpData&i_sInput='+encodeURI($('#i_sUsernm').val()),title:'제품담당자',width:'700',height:'500'});
		 }
	 });	 
	 j$('#del_bm').on('click',function(){
		 $('#i_sUserid').val('');
		 $('#i_sUsernm').val('');
	 });
	//e :  제품담당자 팝업 컨트롤
	 
	//s :  담당자 팝업 컨트롤
	 j$('#i_sCompanyLaborNm').on('dblclick',function(){	 
		 fn_pop({url:'/cm/pop/cm_odm_user_list_pop.do?i_sCmFunction=setOdmUserPopUpData&i_sInput='+encodeURI($('#i_sCompanyLaborNm').val()),title:'담당자',width:'700',height:'500'});
	 });
	 j$('#btn_odm_search').on('click',function(){	 
		 fn_pop({url:'/cm/pop/cm_odm_user_list_pop.do?i_sCmFunction=setOdmUserPopUpData&i_sInput='+encodeURI($('#i_sCompanyLaborNm').val()),title:'담당자',width:'700',height:'500'});
	 });	 
	 
	 j$('#i_sCompanyLaborNm').on('keypress',function(e){
		 if(e.keyCode=='13'){
			 fn_pop({url:'/cm/pop/cm_odm_user_list_pop.do?i_sCmFunction=setOdmUserPopUpData&i_sInput='+encodeURI($('#i_sCompanyLaborNm').val()),title:'담당자',width:'700',height:'500'});
		 }
	 });		 
	 j$('#btn_odm_del').on('click',function(){
		 $('#i_sCompanyLabor').val('');
		 $('#i_sCompanyLaborNm').val('');
		 $('#i_sEmail').val('');
		 $('#i_sLaborPhone').val('');
		 $('#i_sCompanynm').val('');
		 $('#i_sCompanycd').val('');
		 $(".span_companynm").text("");
	 });
	//e :  담당자 팝업 컨트롤
	 
	 fn_calendarSet("i_arrStock_dt",undefined,true);

	 prodSearch = new CmCdSearch('prodSearch','/br/pr/010/br_pr_010_prod_list_pop.do',{searchInput:'i_arrProduct_Refcd',inputCode:'i_arrProduct_Refcd',inputNameKo:'i_arrProduct_RefNm',inputNameEn:'i_arrProduct_RefNm_En',inputNameCn:'i_arrProduct_RefNm_Cn',callback:funcProdSearch});
	 prodGiSearch = new CmCdSearch('prodGiSearch','/br/pr/010/br_pr_010_refer_prod_list_pop.do',{searchInput:'i_arrGiReferProductNm',inputId:'i_arrGiReferRecordId',inputCode:'i_arrGiReferProductCd',inputNameKo:'i_arrGiReferProductNm',callback:funcProdGiSearch});
	 
	 function funcProdSearch(data,obj){
		 console.log("funcProdSearch : "+this.idx);
// 		 $('input[name=PON_attach_pk1]')[this.idx].value=data.v_matnr;
		 //$('input[name=PON_attach_buffer1]')[this.idx].value=data.v_matnr;
		 $('.tbody_product_info').eq(this.idx).find('input[name=PON_attach_buffer1]').val(data.v_matnr);
		 
	 }
	 function funcProdGiSearch(data,obj){
		 var nationCd = data.v_nation_cd;
		 OreqReg.chkBool[this.idx]=false;
		 var chkBox = $('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]');
		 $('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').prop('checked',false);
		 $('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').prop('disabled',true);
		 //$('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').closest('label').parent().removeClass('chk-style');
		 //$('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').attr('onclick','return false;');
		 $('.tbody_product_info').eq(this.idx).find('#'+this.inputNameKo).val('[' + data.v_matnr + ']' + data.v_maktx);

		if(nationCd.indexOf('CN')>-1 ){
			divExpCountryShowHide2(this.idx,true);
		}
		else{
			divExpCountryShowHide2(this.idx,false);
		}
		 var chkBoxkLen = chkBox.length;
		 chkBox.each(function(i,obj){
			 var val = $(obj).val();
			 if(nationCd.indexOf(val)>-1){
				 $(obj).prop('disabled',false);
				 $(obj).prop('checked',true);				 
			 }
		 });
		 inputChkRadioAddEvent($('.tbody_product_info').eq(this.idx));
	 }
	 $(document).find("select[name='i_arrType']").change(function(){
		 OreqReg.addEvent.setCategory1List($(this));
	});
	 $(document).find("select[name='i_arrCategory1']").change(function(){
		 OreqReg.addEvent.setCategory2List($(this));
	});
	 $(document).find("select[name='i_arrCategory2']").change(function() {
		var flag_pao = $(this).find("option:selected").attr("data-flag-pao");
		var pao = $(this).find("option:selected").attr("data-pao");
		var life = $(this).find("option:selected").attr("data-life");
		var idx = $("select[name='i_arrCategory2']").index($(this));
		$(this).parents("td:eq(0)").find("input[name='i_arrPao']").val(pao);
		$(this).parents("td:eq(0)").find("input[name='i_arrLife']").val(life);
	});

	if($("input[name='i_sActionFlag']").val() == "M"){
		OreqReg.addEvent.setCategory();
	}
 }
 
var prodSearch ,prodGiSearch;
function fn_validation(){
	var chkError=false;
	var errorMsg="";
	var regex = /[^0-9]/g
	if(fn_isNull($('#i_sUserid').val())){
		//alert("제품 담당자값을 입력해주세요.");
		fn_s_alertMsg("제품 담당자값을 입력해주세요.");
		
		return false;
	}
	if(fn_isNull($('#i_sCompanyLabor').val())){
		//alert("담당자값을 입력해주세요.");
		fn_s_alertMsg("협력업체 담당자값을 입력해주세요.");
		$('#i_sCompanyLabor').focus();
		return false;
	}
	if(fn_s_inputLengthChk($('input[name="i_sMusogucont_0"]').val(),'소구범위(무소구)',100)){
		return false;
	}
	if(fn_s_inputLengthChk($('input[name="i_sSogucont_0"]').val(),'소구범위(기타)',100)){
		return false;
	}
	if(fn_s_inputLengthChk($('input[name="i_arrPacketUnit"]').val(),'포장단위',50)){
		return false;
	}
	$('.tbody_product_info').each(function(i,obj){
		if(fn_isNull($(obj).find('select[name=i_arrBrand]').val())){
			chkError=true;
			errorMsg="브랜드를 입력해주세요.";
			$(obj).find('select[name=i_arrBrand]').focus();
			return;
		}
		if(fn_isNull($(obj).find('input[name=i_arrProduct_Refcd]').val())){
			chkError=true;
			errorMsg="제품코드를 입력해주세요.";
			$(obj).find('input[name=i_arrProduct_Refcd]').focus();
			return;
		}
		if(fn_isNull($(obj).find('input[name=i_arrProduct_RefNm]').val())){
			chkError=true;
			errorMsg="제품의 한글명을 입력해주세요.";
			$(obj).find('input[name=i_arrProduct_RefNm]').focus();
			return;
		}
		if(fn_isNull($(obj).find('input[name=i_arrProduct_RefNm_En]').val())){
			chkError=true;
			errorMsg="제품의 영문명을 입력해주세요.";
			$(obj).find('input[name=i_arrProduct_RefNm_En]').focus();
			return;
		}
		if($('input[name=i_arrExp_'+i+']:input[value=CN]:checked').val() == "CN"
				&& fn_isNull($(obj).find('input[name=i_arrProduct_RefNm_Cn]').val()) ){
			chkError=true;
			errorMsg="제품의 중문명을 입력해주세요.";
			$(obj).find('input[name=i_arrProduct_RefNm_Cn]').focus();
			return;
		}
		
		if(fn_isNull($(obj).find('select[name=i_arrType]').val())){
			chkError=true;
			errorMsg="대유형을 입력해주세요.";
			$(obj).find('select[name=i_arrType]').focus();
			return;
		}
		if(regex.test($(obj).find('input[name=i_arrCNnum]').val())){ 
			chkError=true;
			errorMsg="내용물 개수는 숫자만 입력가능합니다.";
			$(obj).find('input[name=i_arrCNnum]').focus();
			return;
		}
		
		if(fn_isNull($(obj).find('select[name=i_arrCategory1]').val())){
			chkError=true;
			errorMsg="제품유형을 입력해주세요.";
			$(obj).find('select[name=i_arrCategory1]').focus();
			return;
		}
		if($(obj).find('select[name=i_arrCategory2]').css('display') != 'none' && fn_isNull($(obj).find('select[name=i_arrCategory2]').val())){
			chkError=true;
			errorMsg="제품유형상세을 입력해주세요.";
			$(obj).find('select[name=i_arrCategory2]').focus();
			return;
		}
		
		if(!$(obj).find('input[name=i_arrLeaveonYn_'+i+']').is(":checked")){
			chkError=true;
			errorMsg="Leave On / Wash Off을 선택해주세요.";
			$(obj).find('input[name=i_arrLeaveonYn_'+i+']').focus();
			return;
		}

	});
	
	if(chkError){
		//alert(errorMsg);
		fn_s_alertMsg(errorMsg);
		return false;
	}
	return true;
}
function fn_save(mode){
	$.ajax({
		 url: "/br/pr/010/chkProductCdDuplication.do",
		 data: $('#frm').serialize(),
		 type: "POST",
		 dataType: "json",
		 success:function(data){
			 var data = data.result.data;
			 if(data.length > 0){
				var errMsg="";
				data.forEach(function(obj,i){					
					errMsg += obj.v_product_cd+' : '+obj.v_product_nm_ko+"<br/>";
				})
				fn_s_alertMsg(errMsg+"의 제품코드는 이미 등록된 제품코드입니다.");					
				 return;				 
			 }
			 fn_save_callBack(mode);
		},error : function(jqXHR, textStatus, errorThrown){
	        fn_s_failMsg(jqXHR, textStatus, errorThrown);
		}
	});	
}
function fn_save_callBack(mode){
	if(!fn_validation()) return;
	if(mode != 'T'){
		if(!$('input[name=i_sAgreeyn]').closest('label').hasClass('on')){
			fn_s_alertMsg("기술자료 제공 요청서를 읽고 동의해주세요.");
			$('input[name=i_sAgreeyn]').closest('label').focus();
			return;
		}
	}
	dhx.confirm({
		header: "제품등록",
		text: (mode=='T' ? '저장 하시겠습니까?' : '접수요청 하시겠습니까?'),
		buttons: ["예", "아니오"],
		buttonsAlignment:"center"
	}).then(function(answer){
		if (answer){
			var i_sReceipStatus = (mode=='T'?'RS000':'RS010');
			$('input[name="i_sReceipStatus"]').val(i_sReceipStatus);

			var postParam = $('#frm').serialize();
			$.ajax({
				 url: "/br/pr/010/br_pr_010_save.do",
				 data: postParam,
				 type: "POST",
				 dataType: "json",
				 success:function(data){
					if(data.status=="success"){
						fn_s_alertMsg('저장되었습니다.',function(){
							location.replace("/br/pr/010/br_pr_010_view.do?i_sRecordId=" + data.data.i_sRecordId);
						});
					//	location.href = "/br/pr/010/init.do?openMenuCd=MIBRPR010"
					}
				},error : function(jqXHR, textStatus, errorThrown){
			        fn_s_failMsg(jqXHR, textStatus, errorThrown);
				}
			});
		}
	});
}
function fn_SogugnChk(id){
	if(!$('#'+id).closest('label').hasClass('on')){
		$('#'+id).closest('div').children('input').attr("disabled",false);
	}else{
		$('#'+id).closest('div').children('input').val('');
		$('#'+id).closest('div').children('input').attr("disabled",true);
	}
}
// 숫자 범위제한
$(document).on("change", "input[name^=i_arrCNnum]", function() {
    var val= $(this).val();
    if(val < 1 || val > 10) {
        alert("10개 이하로 입력해 주세요.");
        $(this).val('');
    }
});
</script>
<form id="frm" name="frm" method="post">

	<input type="hidden" name="i_sActionFlag" value="${empty reqVo.i_sActionFlag ? 'R' : reqVo.i_sActionFlag}" />
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sRequestYn" value="N" />
	<input type="hidden" name="i_sIuserdist" value="${infoVo.v_companycd eq 'AP' ? 'B' : 'S'}" />
	<input type="hidden" name="i_sFastFlag" value="${empty reqVo.i_sActionFlag ? 'R' : reqVo.i_sActionFlag}" />
	<input type="hidden" name="i_iVsn" value="${reqFileVo.n_vsn}" />
	<input type="hidden" name="i_sRequestId" value="${reqFileVo.v_requestid}" />
	
	<input type="hidden" name="i_sReceipStatus" value="" />
		
	<c:choose>
		<c:when test="${reqVo.i_sActionFlag ne 'M' or infoVo.v_receip_status eq 'RS000' }">
			<span id="span_modiyn" class="span_hide">Y</span>
		</c:when>
		<c:otherwise>
			<span id="span_modiyn" class="span_hide">N</span>
		</c:otherwise>
	</c:choose>
	<div class="top_btn_area" style="z-index:1;">
		<a href="#none" class="btnA bg_dark a_save_temp"><span>임시저장</span></a>
		<a href="#none" class="btnA bg_dark a_appr"><span>접수요청</span></a>
		<a href="#none" class="btnA bg_dark a_btn_list"><span>목록</span></a>
	</div>

	<div class="sec_cont mt_00">
		<!-- div class="subtitle">
			제품등록
 			<span style="font-size: 12px; color: blue;"> 메뉴얼 :&nbsp; <a href="javascript:CmFileLinkDown('UPLOAD_EX/', 'ODM_TDD_manual_BM.pdf');"><img src="${ImgPATH}common/icon_filedownload.gif" /></a></span> 
		</div-->
				
		<table id="table_product_view" class="sty_03 bdw2">
			<caption></caption>
			<colgroup>
				<col width="10%" />
				<col width="10%" />
				<col width="30%" />
				<col width="15%" />
				<col width="*" />
			</colgroup>
			<c:choose>
				<c:when test="${reqVo.i_sActionFlag eq 'R'}">
					<tbody>
						<tr>
<!-- 							<th rowspan="1">문서정보</th> 문서 정보 -->
							<th colspan="2">* 제품담당자</th> <!-- 담당BM -->
							<td colspan='3'>
								<input type="text" id="i_sUsernm" name="i_sUsernm" value="${reqVo.s_usernm}" class="inp_sty01 required"  />
								<input type="hidden" id="i_sUserid" name="i_sUserid" value="${reqVo.s_userid}" />								
								<img id="search_bm" name="search_bm" src="${ImgPATH}common/ico_search.png" alt="" style="cursor: pointer;vertical-align: middle;">
								<img id="del_bm" name="del_bm" src="${ImgPATH}common/ico_delete.png" alt="" style="cursor: pointer;vertical-align: middle;">
<%-- 									<img id="search_bm" name="search_bm" src="${ImgPATH}icon/00cp_ic004.gif" class="img" style="cursor: pointer;vertical-align: middle;">  --%>
<%-- 								<img id="del_bm" name="del_bm" src="${ImgPATH}icon/00cp_ic003.gif" class="img" style="cursor: pointer;vertical-align: middle;" > --%>
							</td>
						</tr>
					</tbody>
				</c:when>
				<c:otherwise>
					<tbody>
						<tr>
<!-- 							<th rowspan="1">문서정보</th> 문서 정보 -->
<!-- 							<th rowspan="1">제품담당자</th> 담당BM -->
							<th colspan="2">* 제품담당자</th> <!-- 담당BM -->
							<td colspan='3'>
								<input type="text" id="i_sUsernm" name="i_sUsernm" value="${rVo.v_bm_nm}" class="inp_sty01 required"/>
								<input type="hidden" id="i_sUserid" name="i_sUserid" value="${rVo.v_bm_id}" />
								<img id="search_bm" name="search_bm" src="${ImgPATH}common/ico_search.png" alt="" style="cursor: pointer;vertical-align: middle;">
								<img id="del_bm" name="del_bm" src="${ImgPATH}common/ico_delete.png" alt="" style="cursor: pointer;vertical-align: middle;">
<%-- 								<img id="search_bm" name="search_bm" src="${ImgPATH}icon/00cp_ic004.gif" class="img" style="cursor: pointer;vertical-align: middle;"> --%>
<%-- 								<img id="del_bm" name="del_bm" src="${ImgPATH}icon/00cp_ic003.gif" class="img" style="cursor: pointer;vertical-align: middle;" onclick="userSearch2.delUser(this);"> --%>
							</td>
						</tr>
					</tbody>
				</c:otherwise>
			</c:choose>

			<tbody>
				<tr>
					<th rowspan="2">협력업체</th> <!-- 협력업체 -->
					<th>업체명</th> <!-- 업체명 -->
					<td>
						<div>
							<span class="chk-style">
								<label for="i_sImportyn">
									<span>
										<input type="checkbox" name="i_sImportyn" id="i_sImportyn" value="Y"${rVo.v_import_yn eq 'Y' ? 'checked' : '' } />
									</span>
									수입반제품/완제품<!-- 수입 반제품 -->
								</label>
							</span>
							
							<table class="sty_03 bd_gray" style="width: 100%; display: ${rVo.v_import_yn eq 'Y' ? 'table' : 'none' };" id="table_import">
								<colgroup>
									<col width="100px;">
									<col width="250px;">
								</colgroup>
								<tbody>
									<tr>
										<th colspan="2" class="bdl_n">
											<span class="rd-style">
												<label for="i_sImport_A">
													<span>
														<input type="radio" name="i_sImport" id="i_sImport_A" value="F" alt="수입 완제품" ${rVo.v_flag_imp eq 'F'? 'checked':'' }/>
													</span>
													수입 완제품
												</label>
												<label for="i_sImport_H">
													<span>
														<input type="radio" name="i_sImport" id="i_sImport_H" value="S" alt="수입 반제품" ${rVo.v_flag_imp eq 'S'? 'checked':'' }/>
													</span>
													수입 반제품
												</label>
											</span>
										</th>
									</tr>
									<tr class="maker_tr">
										<th class="bdl_n">
											해외 제조원
											<!-- <div class="pd_top10"></div> -->
<!-- 											<a class="btn_themeB" id="a_btn_fmk_add_req"><span>협력사 등록 요청</span></a> -->
										</th>
										<td>
											<input type="text" name="i_sF_mager_nm" value="${rVo.v_f_maker_nm}" class="inp_sty01" maxlength="100" style="width: 152px;" />
										</td>
									</tr>
									<tr class="maker_tr" style="display:${rVo.v_flag_imp eq 'S'? 'table-row':'none' };">
										<th class="bdl_n">최종 제조원</th>
										<td>
											<input type="text" name="i_sL_mager_nm" value="${rVo.v_l_maker_nm}" class="inp_sty01" maxlength="100" style="width: 152px;" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div id="div_odm_imoprt_y">
							<div class="pd_top10"></div>
							<span class="span_companynm" style="font-weight: bold;">${rVo.V_ODM_USER_NM }</span>
							<input type="hidden" name="i_sCompanynm" id="i_sCompanynm" class="inp_sty01" maxlength="100" readonly="readonly" value="${rVo.v_vendor_nm }" />
							<input type="hidden" name="i_sCompanycd" id="i_sCompanycd" class="inp_sty01" maxlength="100" value="${rVo.v_vendor_id }" />
							<input type="hidden" name="i_sImportcd" id="i_sImportcd" maxlength="100" readonly="readonly" value="" />
							<input type="hidden" name="i_sImportnm" id="i_sImportnm" maxlength="100" value="" />
							<div class="pd_top10"></div>
						</div>
<!-- 						<span style="float: right; color: blue;"> 메뉴얼&nbsp;:&nbsp; -->
<!-- 							<a href="javascript:CmFileLinkDown('UPLOAD_EX/', 'half_goods_manual_20161020.pdf');"> -->
<%-- 								<img src="${ImgPATH}common/icon_filedownload.gif" /> --%>
<!-- 							</a> -->
<!-- 						</span> -->
					</td>
					<th>* 담당자</th> <!-- 담당자 -->
					<td>
						<div id="odm_user_search_div" style="display:block"> <!-- odm사의 경우 -->
							<input type="text" id="i_sCompanyLaborNm" name="i_sCompanyLaborNm" value="${rVo.v_odm_user_nm}" class="inp_sty01 lobor_sc_input"/>
							<input type="hidden" id="i_sCompanyLabor" name="i_sCompanyLabor" value="${rVo.v_vendor_labor_id}" />
<%-- 							<img id="btn_odm_search" name="btn_odm_search" src="${ImgPATH}icon/00cp_ic004.gif" class="img lobor_sc_btn"> --%>
								<img id="btn_odm_search" name="btn_odm_search" src="${ImgPATH}common/ico_search.png" alt="" style="cursor: pointer;vertical-align: middle;" class="lobor_sc_btn">
							<c:choose>
								<c:when test="${reqVo.i_sActionFlag ne 'M' or rVo.v_receip_status eq 'RS000' }">
<%-- 									<img id="btn_odm_del" name="btn_odm_del" src="${ImgPATH}icon/00cp_ic003.gif" class="img lobor_sc_btn">									 --%>
									<img id="btn_odm_del" name="btn_odm_del" src="${ImgPATH}common/ico_delete.png" alt="" style="cursor: pointer;vertical-align: middle;" class="lobor_sc_btn">
								</c:when>
								<c:otherwise>
<%-- 									<img id="btn_odm_del" name="btn_odm_del" src="${ImgPATH}icon/00cp_ic003.gif" class="img lobor_sc_btn">	 --%> --%>
									<img id="btn_odm_del" name="btn_odm_del" src="${ImgPATH}common/ico_delete.png" alt="" style="cursor: pointer;vertical-align: middle;" class="lobor_sc_btn">
								</c:otherwise>
							</c:choose>
						</div>
						<span><font style="font-weight:bold;">자사 임직원이 담당자로 지정된 경우, esivan.sikorea.co.kr접속하셔서 작성하셔야 합니다.</font></span>
					</td>
				</tr>
				<tr>
					<th>담당자(이메일)</th> <!-- 담당자(이메일) -->
					<td>
						<input type="text" name="i_sEmail" id="i_sEmail" class="inp_sty01 search_email" maxlength="100" style="width: 98%;" readonly value="${rVo.v_odm_user_email }" class="inp_sty01" />
					</td>
					<th>연락처</th>
					<!-- 연락처 -->
					<td>
						<input type="text" name="i_sLaborPhone" id="i_sLaborPhone" class="inp_sty01" maxlength="100" style="width:98%; display: block;"readonly value="${rVo.v_odm_user_phon }" />
						<span class="inp_sty01 search_phone" style="width:98%; display: none"></span>
					</td>
				</tr>
			</tbody>

			<c:choose>
				<c:when test="${reqVo.i_sActionFlag eq 'M' }">
					<c:set var="prod_len" value="${fn:length(prodList)}" />
					<c:forEach items="${prodList}" var="vo" varStatus="s">
						<c:forEach items="${fdaList }" var="fvo">
							<c:if test="${fvo.v_product_cd eq vo.v_product_cd}">
								<c:set var="fda" value="${fvo }"/>
							</c:if>
						</c:forEach>
						<tbody class="tbody_product_info">
							<tr>
								<th rowspan="11"><c:choose>
										<c:when test="${s.count == 1}">
											제품정보 <!-- 제품정보 -->
											<c:if test="${rVo.v_receip_status eq 'RS000' }">
												<div style="margin-top: 5px; float: right; margin-right: 5px;">
													<a href="#" class="btn_add_product"><img src="${ImgPATH}btn/btn_add_small.gif" alt="추가" /></a>
												</div>
												<div style="clear: both;">동일 라인의 shade 제품인 경우 추가</div>
											</c:if>
										</c:when>
										<c:otherwise>
											<div style="margin-top: 5px; float: right; margin-right: 5px;">
												<a class="btn_product_del" href="#"><img src="${ImgPATH}btn/btn_del_small.gif" alt="<fmt:message key="pms.common.del"/>" /></a>
											</div>
										</c:otherwise>
									</c:choose></th>
								<th>* 브랜드</th> <!-- 브랜드 -->
								<td>
									<select name="i_arrBrand" class="select_sty01">
										<option value="">-- SELECT --</option>
										<c:forEach items="${BRAND_LIST}" var="tvo">
											<option value="${tvo.COMM_CD}"<c:if test="${tvo.COMM_CD eq vo.v_brand_cd}">selected</c:if>>${tvo.COMM_CD_NM}</option>
										</c:forEach>
									</select>
								</td>
								<th>* 한글명</th><!-- 한글명 -->
								<td>
									<input type="text" name="i_arrProduct_RefNm" id="i_arrProduct_RefNm" value="${vo.v_product_nm_ko}" class="inp_sty01" maxlength="100" style="width: 98%;" readonly/>
								</td>
							</tr>
							<tr>
								<th rowspan="${fn:indexOf(vo.v_exp_country, 'CN')>-1?'2':'1'}">제품코드</th> <!-- 제품코드 -->
								<td rowspan="${fn:indexOf(vo.v_exp_country, 'CN')>-1?'2':'1'}">
									<input type="text" name="i_arrProduct_Refcd" id="i_arrProduct_Refcd" value="${vo.v_product_cd}" class="inp_sty01 required" onkeypress="prodSearch.keyPress(event, this);" ondblclick="prodSearch.productPop(this);" alt="제품코드" style="width: 124px;" />
									<img id="btn_pjt_ref_pop" name="btn_pjt_ref_pop" src="${ImgPATH}common/ico_search.png" alt="" style="cursor: pointer;vertical-align: middle;" onclick="prodSearch.productPop(this);" />
									<img id="btn_pjt_ref_del" name="btn_pjt_ref_del" src="${ImgPATH}common/ico_delete.png" alt="" style="cursor: pointer;vertical-align: middle;" onclick="prodSearch.delProduct(this);" class="del_product" />
<%-- 									<img id="btn_pjt_ref_pop" name="btn_pjt_ref_pop" src="${ImgPATH}icon/00cp_ic004.gif" style="cursor: pointer;" class="img" onclick="prodSearch.productPop(this);"> --%>
<%-- 									<img id="btn_pjt_ref_del" name="btn_pjt_ref_del" src="${ImgPATH}icon/00cp_ic003.gif" style="cursor: pointer;" class="img del_product" onclick="prodSearch.delProduct(this);"> --%>
								</td>
								<th>영문명</th> 
								<td>
									<input type="text" name="i_arrProduct_RefNm_En" id="i_arrProduct_RefNm_En" value="${vo.v_product_nm_en}" class="inp_sty01" maxlength="100" style="width: 98%;" readonly/>
								</td>
							</tr>
							<tr style="display:${fn:indexOf(vo.v_exp_country, 'CN')>-1?'table-row':'none'};">
								<th>* 중문명</th> <!-- 대표홋수코드 -->
								<td>
									<input type="text" name="i_arrProduct_RefNm_Cn" id="i_arrProduct_RefNm_Cn" value="${vo.v_product_nm_cn}" class="inp_sty01" maxlength="100" style="width: 98%;" />
								</td>
							</tr>
							<tr>
								<th>* 본품 여부</th> <!-- 본품 여부 -->
								<td>
									<div class="div_origin_yn">
										<span class="rd-style">
											<label for="i_sOriginYn_Y_${s.index}" style="margin-right:10px;">
												<span>
													<input type="radio" name="i_arrOriginYn_${s.index}" id="i_sOriginYn_Y_${s.index}" value="Y" class="required" alt="본품 여부" ${vo.v_origin_yn eq 'Y'?'checked':'' } />
												</span> Y
											</label>
											<label for="i_sOriginYn_N_${s.index}"> 
												<span>
													<input type="radio" name="i_arrOriginYn_${s.index}" id="i_sOriginYn_N_${s.index}" value="N" class="required" alt="본품 여부" ${vo.v_origin_yn eq 'N'?'checked':'' } />
													</span> N
											</label>
										</span>
									</div>
									<div class="div_set_radio" style="${vo.v_origin_yn eq 'Y' ? 'display:none;' : ''}">
										<div>
											<span class="rd-style">
												<c:forEach items="${originDivList}" var="odVo" varStatus="odsVo">
														<label for="i_sOriginDiv${s.index}_${odsVo.index}">
															<span>
																<input type="radio" name="i_arrOriginDiv_${s.index}" id="i_sOriginDiv${s.index}_${odsVo.index}" value="${odVo.COMM_CD}" class="required" alt="본품 여부" ${vo.v_origin_div eq odVo.COMM_CD ? 'checked=\'checked\'' : '' }/>
															</span> ${odVo.COMM_CD_NM}
														</label>
													<br/>
												</c:forEach>
											</span>
										</div>
									</div>
								</td>								
								<th>수출국가</th>
								<td colspan="1">
									<div class="div_exp_country">
										<c:forEach items="${COUNTRY_LIST}" var="evo">
											<span class="chk-style" style="margin-right:10px;">
												<label for="i_arrExp_${s.index}_${evo.COMM_CD}">
													<span>
														<input type="checkbox" name="i_arrExp_${s.index}" id="i_arrExp_${s.index}_${evo.COMM_CD}" value="${evo.COMM_CD}" ${fn:indexOf(vo.v_exp_country, evo.COMM_CD) > -1 ? 'checked':'' }/>
													</span>&nbsp;${evo.COMM_CD_NM }
												</label>
											</span>
										</c:forEach>
									</div>
								</td>
							</tr>
							<tr class="content_nm_tr">
								<th>전성분 표시될<br/>내용물 개수</th>
								<td><input type="text" class="inp_sty01 onlyNumber i_arrCNnum" size="5" name="i_arrCNnum" value="${vo.v_content_num }" maxlength="2" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />&nbsp;개</td>								
								<th>연결된 본품코드<br/>(동일 내용물 제품)</th> <!-- 연결된 본품코드 -->
								<td>
									<div class="div_gi_ref" style="${vo.v_origin_yn eq 'Y' ? 'display:none;' : ''}">
										<input type="hidden" name="i_arrParentContentCd">
										<c:set var="v_refer_productnm" value="" />
										<c:if test="${!empty vo.v_refer_product_cd}">
											<c:set var="v_refer_productnm" value="[${vo.v_refer_product_cd}]${vo.v_refer_product_nm}" />
										</c:if>
										
										<input type="text" name="i_arrGiReferProductNm" id="i_arrGiReferProductNm" value="${v_refer_productnm}" class="inp_sty01 required"
											onkeypress="prodGiSearch.keyPress(event, this);" ondblclick="prodGiSearch.productPop(this);"
											alt="제품 코드" style="width:88%;" />
							
										<input type="hidden" name="i_arrGiReferProductCd" id="i_arrGiReferProductCd" value="${vo.v_refer_product_cd}" />
										<input type="hidden" name="i_arrGiReferRecordId" id="i_arrGiReferRecordId" value="${vo.v_refer_record_id}" />
										<img id="btn_origin_ref_pop" name="btn_origin_ref_pop" src="${ImgPATH}common/ico_search.png" alt="" style="cursor: pointer;vertical-align: middle;" onclick="prodGiSearch.productPop(this);" />
										<img id="btn_origin_ref_del" name="btn_origin_ref_del" src="${ImgPATH}common/ico_delete.png" alt="" style="cursor: pointer;vertical-align: middle;" onclick="prodGiSearch.delProduct(this);" class="delorigin_product" /> 
							
<%-- 										<img id="btn_origin_ref_pop" name="btn_origin_ref_pop" src="${ImgPATH}icon/00cp_ic004.gif" style="cursor: pointer;" class="img" onclick="prodGiSearch.productPop(this);"> --%>
<%-- 										<img id="btn_origin_ref_del" name="btn_origin_ref_del" src="${ImgPATH}icon/00cp_ic003.gif" style="cursor: pointer;" class="img delorigin_product" onclick="prodGiSearch.delProduct(this);"> --%>
										<br/>* 필독 ! <span style="color: red;text-decoration: underline;">동일 내용물의 제품</span>이 국내 검토 진행 중 또는 완료 이력이 있는 경우만 해당 제품코드 입력 바랍니다.
										
										<!--<br/>추후 입력한 동일 처방제품의 사전안전성 검토 결과를 근거로 연동평가 됩니다. -->
										<!--<br/>본품이 아닌경우, 연결된 본품을 먼저 등록해 주세요. 연결된 본품이 등록되지 않았을 경우, 진행이 불가합니다.</span> -->
									</div>
								</td>
							</tr>
							<tr>
								<th>예정 입고일</th> <!-- 입고일 -->
								<td>
									<input type="text" id="i_arrStock_dt" name="i_arrStock_dt" value="${cfn:getStrDateToString(vo.v_stock_dtm,'yyyy-MM-dd')}" class="inp_sty01 calendar" alt="입고일" style="width: 152px; cursor: pointer;" readonly />
									<c:if test="${s.count ne 1}">
										<input type="text" class="calendar2" value="${cfn:getDatePatten(vo.v_stock_dtm,'-')}" style="display: none; width: 152px; cursor: pointer;" readonly />
									</c:if>
								</td>
								<th>* Leave On / Wash Off</th> <!-- Leave On / Wash Off -->
								<td>
									<span class="rd-style">
										<label for="i_arrLeaveon_Y_${s.index}" style="margin-right:10px;">
											<span><input type="radio" name="i_arrLeaveonYn_${s.index}" id="i_arrLeaveon_Y_${s.index}" value="Y" alt="Leave On / Wash Off" class="required" ${vo.v_leaveon_yn eq 'Y' ? 'checked':'' }/></span>
											Leave On
										</label>
										<label for="i_arrLeaveon_N_${s.index}">
											<span><input type="radio" name="i_arrLeaveonYn_${s.index}" id="i_arrLeaveon_N_${s.index}" value="N" alt="Leave On / Wash Off" class="required" ${vo.v_leaveon_yn eq 'N' ? 'checked':'' }/></span>
											Wash Off
										</label>
									</span>
								</td>
							</tr>
							<tr>
								<th>* 대유형</th> <!-- 대유형 -->
								<td>
									<select name="i_arrType" class="select_sty01 required" alt="브랜드">
										<option value="">-- SELECT --</option>
										<c:forEach items="${ODM_PRODUCT_TYPE_LIST}" var="tvo">
											<option value="${tvo.COMM_CD}"<c:if test="${tvo.COMM_CD eq vo.v_type}">selected</c:if>>${tvo.COMM_CD_NM}</option>
										</c:forEach>
									</select>
								</td>
								<th>* 제품유형</th> <!-- 제품유형 -->
								<td>
									<select name="i_arrCategory1" class="select_sty01 required" alt="제품유형1">
										<option value="">-- SELECT --</option>
										<c:forEach items="${vo.cateList}" var="cateVo">
											<option value="${cateVo.v_class_cd}" ${vo.v_category1 eq cateVo.v_class_cd ? 'selected' : ''}>${cateVo.v_class_nm}</option>
										</c:forEach>
									</select>
									<select name="i_arrCategory2" class="select_sty01 required" alt="제품유형2">
										<option value="">-- SELECT --</option>
									</select>
									<input type="hidden" value="${vo.v_category2}" />
									<input type="hidden" name="i_arrPao" class="inp_sty01 onlyNumber" value="${vo.v_pao }" />
									<input type="hidden" name="i_arrLife" class="inp_sty01 onlyNumber" value="${vo.v_shelf_life }" />
								</td>
							</tr>
							<tr>
								<th>기능성 여부</th> <!-- 기능성 여부 -->
								<td>
									<div class="div_func_yn">
										<span class="rd-style">
											<label for="i_sFuncyn_Y_${s.index}" style="margin-right:10px;">
												<span>
													<input type="radio" name="i_arrFuncyn_${s.index}" id="i_sFuncyn_Y_${s.index}" value="Y" class="required" alt="기능성여부" ${vo.v_func_yn eq 'Y'?'checked':'' } />
												</span> Y
											</label>
											<label for="i_sFuncyn_N_${s.index}"> 
												<span>
													<input type="radio" name="i_arrFuncyn_${s.index}" id="i_sFuncyn_N_${s.index}" value="N" class="required" alt="기능성여부" ${vo.v_func_yn eq 'N'?'checked':'' } />
													</span> N
											</label>
										</span>
									</div>
									<div class="div_func_ck" style="padding: 10px 0px;${vo.v_func_yn eq 'Y'?'':'display: none' };">
										<c:if test="${vo.v_func_yn eq 'Y'}">
											<c:set var="func_value" value="" />
											
											<c:choose>
												<c:when test="${!empty vo.v_refer_product_cd }">
													<c:forEach items="${funcList}" var="fVo" varStatus="sf">
														<c:if test="${fVo.v_product_cd eq vo.v_refer_product_cd}">
															<c:set var="func_value" value="${func_value}/${fVo.v_func_code}" />
														</c:if>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach items="${funcList}" var="fVo" varStatus="sf">
														<c:if test="${fVo.v_product_cd eq vo.v_product_cd}">
															<c:set var="func_value" value="${func_value}/${fVo.v_func_code}" />
														</c:if>
													</c:forEach>
												</c:otherwise>
											</c:choose>
											<c:forEach items="${ODM_FUNC_LIST}" var="tvo" varStatus="sf">
												<span class="chk-style" style="margin-right:10px;">
													<label for="i_sFuncCode_${s.index}_${sf.index}">
														<span>
															<input type="checkbox" name="i_sFuncCode_${s.index}" id="i_sFuncCode_${s.index}_${sf.index}" value="${tvo.COMM_CD}" ${fn:indexOf(vo.v_func_code, tvo.COMM_CD) > -1 ? 'checked':'' } />
														</span>${tvo.COMM_CD_NM}
													</label>
												</span>
											</c:forEach>
										</c:if>
									</div>
								</td>
								<th>용량/용기유형</th> <!-- 용기형태/유형 -->
								<td>
									<div style="margin-bottom: 5px;">
										용량 : <input class="inp_sty01" type="text" maxlength="15" name="i_arrProductCapacity" id="i_arrProductCapacity" value="${vo.v_capacity }"/><br/>
										<span>* 용량 입력 시, 단위도 함께 작성해주시기 바랍니다.(ex. 10ml, 10g)</span>
									</div>
									<div>
										<select name="i_arrCntrForm" class="select_sty01 required" style="float:left; display: inline; margin-right: 5px;">
											<option value="">-- SELECT --</option>
											<c:forEach items="${CNTRFORM_LIST}" var="cntrFVo">
												<option value="${cntrFVo.COMM_CD}" ${vo.v_cntr_form eq cntrFVo.COMM_CD ? 'selected' : '' }>${cntrFVo.COMM_CD_NM}</option>
											</c:forEach>
										</select>										
										<div class="CntrForm_etc_div" style="float: left; display: ${vo.v_cntr_form eq "OCT_1_21" ? 'block' : 'none' };">
											(기타 : <input type="text" name="i_arrCntrForm_etc" value="${vo.v_cntr_form_etc}" class="inp_sty01" style="width: 100px" /> )&nbsp;
										</div>
										<select name="i_arrCntrMatr" class="select_sty01 required" style="float:left; display: inline; margin-right: 5px;">
											<option value="">-- SELECT --</option>
											<c:forEach items="${CNTRMATR_LIST}" var="cntrMVo">
												<option value="${cntrMVo.COMM_CD}" ${vo.v_cntr_matr eq cntrMVo.COMM_CD ? 'selected' : '' }>${cntrMVo.COMM_CD_NM}</option>
											</c:forEach>
										</select>										
										<div class="CntrMatr_etc_div" style="display: ${vo.v_cntr_matr eq "OCT_2_04" ? 'block' : 'none' };">
											(기타 : <input type="text" name="i_arrCntrMatr_etc" value="${vo.v_cntr_matr_etc}" class="inp_sty01" style="width: 100px" /> )
										</div>
									</div>
								</td>
							</tr>
							<tr><!-- style="display: none;" -->
								<th>영·유아 화장품 해당 여부</th>
								<td>
									<div class="div_kid_yn">
										<span class="rd-style">
											<label for="i_sKid_Y_${s.index}" style="margin-right:10px;">
												<span>
													<input type="radio" class="i_arrKid" name="i_arrKidYn_${s.index}" id="i_sKid_Y_${s.index}" value="Y" ${vo.v_kid_guide_yn eq 'Y' ? 'checked=\'checked\'' : ''} class="required" alt="영유아용 제품 여부" />
												</span> 유
											</label>
											<label for="i_sKid_N_${s.index}">
												<span>
													<input type="radio" class="i_arrKid" name="i_arrKidYn_${s.index }" id="i_sKid_N_${s.index}" value="N" ${vo.v_kid_guide_yn ne 'Y' ? 'checked=\'checked\'' : ''} class="required" alt="영유아용 제품 여부" />
												</span> 무
											</label>
										</span>
									</div>
								</td>
								<th>안정성 자료 필수 여부</th>
								<td>
									<div class="div_stabilityfile_yn">
										<span class="rd-style">
											<label for="i_arrStabilityFileYn_Y_${s.index}" style="margin-right:10px;">
												<span><input type="radio" name="i_arrStabilityFileYn_${s.index}" id="i_arrStabilityFileYn_Y_${s.index}" value="Y" ${vo.v_stability_file_yn eq 'Y' ? 'checked=\'checked\'' : ''  }></span>유											</label>								
											<label for="i_arrStabilityFileYn_N_${s.index}">
												<span><input type="radio" name="i_arrStabilityFileYn_${s.index }" id="i_arrStabilityFileYn_N_${s.index}" value="N" ${vo.v_stability_file_yn eq 'N' ? 'checked=\'checked\'' : ''  }></span>무
											</label>
										</span>
									</div>
									<br>* 레티놀(비타민A), 아스코빅애씨드(비타민C) 및 그 유도체, 토코페놀(비타민E), 과산화화합물, 효소 이 컨셉일 경우 기술자료 '유' 체크
								</td>
							</tr>
							<tr>
								<th>소구범위</th> <!-- 소구범위 -->
								<td>
									<div style="float: left; margin-right:10px;">
										<span class="chk-style">
											<label for="i_arrSogugn_E_${s.index}">
												<span>
													<input type="checkbox" name="i_arrSogugn_${s.index}" id="i_arrSogugn_E_${s.index}" value="E" ${fn:indexOf(vo.v_free_gn, 'E') > -1 ? 'checked' : ''} onclick="fn_SogugnChk(this.id)"/>
												</span>&nbsp; 무소구
											</label>
										</span> 
										( <input type=text name="i_sMusogucont_${s.index}" value="${vo.v_musogu_cont}" class="inp_sty01" style="width: 100px" ${fn:indexOf(vo.v_free_gn, 'E') > -1 ? '' : 'disabled'} /> )
									</div>
									<div style="float: left;">
										<span class="chk-style">
											<label for="i_arrSogugn_D_${s.index}">
												<span>
													<input type="checkbox" name="i_arrSogugn_${s.index}" id="i_arrSogugn_D_${s.index}" value="D" ${fn:indexOf(vo.v_free_gn, 'D') > -1 ? 'checked' : ''} onclick="fn_SogugnChk(this.id)"/>
												</span>&nbsp;기타
											</label>
										</span>
										<input type="text" name="i_sSogucont_${s.index}" value="${vo.v_sogu_cont}" class="inp_sty01" style="width: 100px" ${fn:indexOf(vo.v_free_gn, 'D') > -1 ? '' : 'disabled'}/>
									</div>
								</td>
								<th>포장단위</th>
								<td>
									<input type="text" name="i_arrPacketUnit" value="${vo.v_packet_unit}" class="inp_sty01" style="width: 100px">
								</td>
							</tr>
							
							<tr class="tr_origin_img">
								<th>사용방법</th>
								<td colspan="3">
									<span class="fileinput-button" style="margin: 5px;">
										<a href="#" class="btnA bg_dark"><span>파일첨부</span></a>
										<input id="admst_upload_btn_${s.index}" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
									</span>
									<CmTagLib:cmAttachFile uploadCd="PON" type="reg" recordId="${vo.v_record_id}" pk1="${vo.v_product_cd}"/>
									<textarea rows="4" style="width: 98%;" class="textarea_sty01" name="i_arrPonMsg">${vo.v_pon_msg }</textarea>
									<br/><span><font style="color: red;">(사용방법이 특수한 경우 기재.)</font></span>
								</td>
							</tr>
						</tbody>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tbody class="tbody_product_info">
						<tr>
							<th rowspan="11">
								제품정보
								<div style="">* 동일 라인의 shade 제품인 경우 
									<a href="#" class="btn_add_product">
										<img src="${ImgPATH}btn/btn_add_small.gif" alt="추가" />
									</a>
								</div>
							</th>
							<th>* 브랜드</th> <!-- 브랜드 -->
							<td>
								<select name="i_arrBrand" class="select_sty01 required" alt="브랜드">
									<option value="">-- SELECT --</option>
									<c:forEach items="${BRAND_LIST}" var="tvo">
										<%--<option value="${tvo.COMM_CD}">${tvo.COMM_CD_NM}</option> --%>
										<option value="${tvo.COMM_CD}"<c:if test="${tvo.COMM_CD eq reqVo.s_brand_cd}">selected</c:if>>${tvo.COMM_CD_NM}</option>
									</c:forEach>
								</select>
							</td>
							<th>* 한글명</th> <!-- 한글명 -->
							<td>
								<input type="text" name="i_arrProduct_RefNm" id="i_arrProduct_RefNm" value="" class="inp_sty01" maxlength="100" style="width: 98%;" readonly/>
							</td>
						</tr>
						<tr>
							<th rowspan='1'>* 제품코드</th> <!-- 제품코드 -->
							<td rowspan='1'>
								<font style="font-weight: bold; color: blue; display: none;" class="fast_font">(FAST)</font>
								<input name="i_arrFastFlag" value="N" type="hidden" />
								<input type="text" name="i_arrProduct_Refcd" id="i_arrProduct_Refcd" value="" class="inp_sty01 required"
									onkeypress="prodSearch.keyPress(event, this);"
									ondblclick="prodSearch.productPop(this);" 
								alt="제품코드" style="width: 124px; "/>
								
								<img id="btn_pjt_ref_pop" name="btn_pjt_ref_pop" src="${ImgPATH}common/ico_search.png" style="cursor: pointer;vertical-align: middle;" class="img" onclick="prodSearch.productPop(this);" />								
								<img id="btn_pjt_ref_del" name="btn_pjt_ref_del" src="${ImgPATH}common/ico_delete.png" style="cursor: pointer;vertical-align: middle;" class="img del_product" onclick="prodSearch.delProduct(this);" />
							</td>
							<th>영문명</th>
							<td>
								<input type="text" name="i_arrProduct_RefNm_En" id="i_arrProduct_RefNm_En" value="" class="inp_sty01" maxlength="100" style="width: 98%;" readonly/>
							</td>
						</tr>
						<tr style="display:  none;">
							<th>* 중문명</th> <!-- 대표홋수코드 -->
							<td>
								<input type="text" name="i_arrProduct_RefNm_Cn" id="i_arrProduct_RefNm_Cn" value="${vo.v_productnm_cn}" class="inp_sty01" maxlength="100" style="width: 98%;" />
							</td>
						</tr>
						<tr>
							<th>* 본품 여부</th> <!-- 본품 여부 -->
							<td>
								<div class="div_origin_yn">
									<span class="rd-style">
										<label for="i_sOriginYn_Y_0" style="margin-right:10px;">
											<span>
												<input type="radio" name="i_arrOriginYn_0" id="i_sOriginYn_Y_0" value="Y" class="required" alt="본품 여부" checked="checked" />
											</span> Y
										</label>
										<label for="i_sOriginYn_N_0"> 
											<span>
												<input type="radio" name="i_arrOriginYn_0" id="i_sOriginYn_N_0" value="N" class="required" alt="본품 여부" />
												</span> N
										</label>
									</span>
								</div>
								<div class="div_set_radio" style="display:none;">
									<div>
										<span class="rd-style">
											<c:forEach items="${originDivList}" var="odVo" varStatus="odsVo">
												<label for="i_sOriginDiv0_${odsVo.index}">
													<span>
														<input type="radio" name="i_arrOriginDiv_0" id="i_sOriginDiv0_${odsVo.index}" value="${odVo.COMM_CD}" class="required" alt="본품 여부" />
													</span> ${odVo.COMM_CD_NM}
												</label>
											<br/>
											</c:forEach>
										</span>
										<div class="div_set_inp" style="display:none">
											<div class="pd_top10"></div>
											<span style="color:gray;">* 세트코드</span>&nbsp;<input type="text" class="required" name="i_sSetCode0" value="">
										</div>
									</div>
								</div>
							</td>							
							<th>수출 국가</th>
							<td>
								<div class="div_exp_country">
									<c:forEach items="${COUNTRY_LIST }" var="evo">
										<span class="chk-style" style="margin-right:10px;">
											<label for="i_arrExp_0_${evo.COMM_CD}">
												<span>
													<input type="checkbox" name="i_arrExp_0" id="i_arrExp_0_${evo.COMM_CD}" value="${evo.COMM_CD}" />
												</span>&nbsp;${evo.COMM_CD_NM }
											</label>
										</span>
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr class="content_nm_tr">
							<th>전성분 표시될<br/>내용물 개수</th>
							<td><input type="text" class="inp_sty01 onlyNumber i_arrCNnum" size="5" name="i_arrCNnum" value="${vo.v_content_num }" maxlength="2" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />&nbsp;개</td>							
							<th>연결된 본품코드<br/>(동일 내용물 제품)</th> <!-- 연결된 본품코드 -->
							<td>
								<div class="div_gi_ref" style="display:none;">
									<input type="hidden" name="i_arrParentContentCd">
									<input type="text" name="i_arrGiReferProductNm" id="i_arrGiReferProductNm" value="" class="inp_sty01 required"
									ondblclick="prodGiSearch.productPop(this);" onkeypress="prodGiSearch.keyPress(event,this);"
									alt="제품코드" style="width:88%;" />
						
									<input type="hidden" name="i_arrGiReferProductCd" id="i_arrGiReferProductCd" value="" />
									<input type="hidden" name="i_arrGiReferRecordId" id="i_arrGiReferRecordId" value="" />
						
									<img id="btn_origin_ref_pop" name="btn_origin_ref_pop" src="${ImgPATH}common/ico_search.png" style="cursor: pointer;vertical-align: middle;" class="img" onclick="prodGiSearch.productPop(this);" />
									<img id="btn_origin_ref_del" name="btn_origin_ref_del" src="${ImgPATH}common/ico_delete.png" style="cursor: pointer;vertical-align: middle;" class="img delorigin_product" onclick="prodGiSearch.delProduct(this);" />
									<br/>* 필독 ! <span style="color: red;text-decoration: underline;">동일 내용물의 제품</span>이 국내 검토 진행 중 또는 완료 이력이 있는 경우만 해당 제품코드 입력 바랍니다.
								</div>
							</td>
						</tr>
						<tr>
							<th>예정 입고일</th> <!-- 입고일 -->
							<td>
								<input type="text" id="i_arrStock_dt" name="i_arrStock_dt" value="" class="inbox calendar" alt="<fmt:message key='pms.common.startdt'/>" style="width: 152px; cursor: pointer;" readonly />
							</td>
							<th>* Leave On / Wash Off </th><!-- Leave On / Wash Off -->
							<td>
								<span class="rd-style">
									<label for="i_arrLeaveon_Y_0">
										<span><input type="radio" name="i_arrLeaveonYn_0" id="i_arrLeaveon_Y_0" value="Y" alt="Leave On / Wash Off" class="required" /></span>
										Leave On
									</label>
									<label for="i_arrLeaveon_N_0">
										<span><input type="radio" name="i_arrLeaveonYn_0" id="i_arrLeaveon_N_0" value="N" alt="Leave On / Wash Off" class="required" checked='checked'/></span>
										Wash Off
									</label>
								</span>
							</td>
						</tr>
						<tr>
							<th>* 대유형</th> <!-- 대유형 -->
							<td>
								<select name="i_arrType" class="select_sty01 required" alt="브랜드">
									<option value="">-- SELECT --</option>
									<c:forEach items="${ODM_PRODUCT_TYPE_LIST}" var="tvo">
										<option value="${tvo.COMM_CD}">${tvo.COMM_CD_NM}</option>
									</c:forEach>
								</select>
							</td>
							<th>* 제품유형</th> <!-- 제품유형 -->
							<td>
								<select name="i_arrCategory1" class="select_sty01 required">
									<option value="">-- SELECT --</option>
									<c:forEach items="${cateList}" var="cateVo">
										<option value="${cateVo.v_class_cd}">${cateVo.v_class_nm}</option>
									</c:forEach>
								</select>
								<select name="i_arrCategory2" class="select_sty01 required">
									<option value="">-- SELECT --</option>
								</select>
								<input type="hidden" name="i_arrPao" class="inp_sty01 onlyNumber" value="" />
								<input type="hidden" name="i_arrLife" class="inp_sty01 onlyNumber" value="" />
							</td>
						</tr>
						<tr>
							<th>기능성여부</th> <!-- 기능성 여부 -->
							<td>
								<div class="div_func_yn">
									<span class="rd-style">
										<label for="i_sFuncyn_Y_0" style="margin-right:10px;">
											<span>
												<input type="radio" name="i_arrFuncyn_0" id="i_sFuncyn_Y_0" value="Y" class="required" alt="기능성여부" />
											</span>Y
										</label>
										<label for="i_sFuncyn_N_0">
											<span>
												<input type="radio" name="i_arrFuncyn_0" id="i_sFuncyn_N_0" value="N" class="required" alt="기능성여부" checked='checked' />
											</span> N
										</label>
									</span>
								</div>
								<div class="div_func_ck" style="padding: 10px 0px; display: none;"></div>
							</td>
							<th>용량/용기유형</th> <!-- 용기형태/유형 -->
							<td>
								<div style="margin-bottom: 5px;">
									용량 : <input class="inp_sty01" type="text" name="i_arrProductCapacity" id="i_arrProductCapacity" value="" maxlength="15"/><br/>
									<span>* 용량 입력 시, 단위도 함께 작성해주시기 바랍니다.(ex. 10ml, 10g)</span>
								</div>
								<div>
									<select name="i_arrCntrForm" class="select_sty01 required" style="float:left; display: inline; margin-right: 5px;">
										<option value="">-- SELECT --</option>
										<c:forEach items="${CNTRFORM_LIST}" var="cntrFVo">
											<option value="${cntrFVo.COMM_CD}">${cntrFVo.COMM_CD_NM}</option>
										</c:forEach>
									</select>
									<div class="CntrForm_etc_div" style="float: left; display: none;">
										(기타 : <input type="text" name="i_arrCntrForm_etc" value="" class="inp_sty01" style="width: 100px" /> )&nbsp;
									</div>
									<select name="i_arrCntrMatr" class="select_sty01 required" style="float:left; display: inline; margin-right: 5px;">
										<option value="">-- SELECT --</option>
										<c:forEach items="${CNTRMATR_LIST}" var="cntrMVo">
											<option value="${cntrMVo.COMM_CD}">${cntrMVo.COMM_CD_NM}</option>
										</c:forEach>
									</select>
									<div class="CntrMatr_etc_div" style="display: none;">
										(기타 : <input type="text" name="i_arrCntrMatr_etc" value="" class="inp_sty01" style="width: 100px" /> )
									</div>
								</div>
							</td>
						</tr>
						<tr> <!--  style="display: none;"-->
							<th>영유아용 제품</th>
							<td>
								<div class="div_kid_yn">
									<span class="rd-style">
										<label for="i_sKid_Y_0" style="margin-right:10px;">
											<span>
												<input type="radio" class="i_arrKid" name="i_arrKidYn_0" id="i_sKid_Y_0" value="Y" class="required" alt="영유아용 제품 여부" />
											</span> 유
										</label>
										<label for="i_sKid_N_0">
											<span>
												<input type="radio" class="i_arrKid" name="i_arrKidYn_0" id="i_sKid_N_0" value="N" class="required" alt="영유아용 제품 여부" checked='checked' />
											</span> 무
										</label>
									</span>
<!-- 									<input type="hidden" name="i_arrKidYn" /> -->
								</div>
							</td>							
							<th>안정성 자료 필수 여부</th>
							<td>
								<div class="div_stabilityfile_yn">
									<span class="rd-style">
										<label for="i_arrStabilityFileYn_Y_0" style="margin-right:10px;">
											<span><input type="radio" name="i_arrStabilityFileYn_0" id="i_arrStabilityFileYn_Y_0" value="Y"></span>유
										</label>								
										<label for="i_arrStabilityFileYn_N_0">
											<span><input type="radio" name="i_arrStabilityFileYn_0" id="i_arrStabilityFileYn_N_0" value="N" checked='checked'></span>무
										</label>
									</span>
								</div>
								<br>* 레티놀(비타민A), 아스코빅애씨드(비타민C) 및 그 유도체, 토코페놀(비타민E), 과산화화합물, 효소 이 컨셉일 경우 기술자료 '유' 체크
							</td>
						</tr>
						<tr>
							<th>소구범위<!-- 소구범위 --></th>
							<td>
								<div style="float: left;margin-right:10px;">
									<span class="chk-style">
										<label for="i_arrSogugn_E_0">
											<span>
												<input type="checkbox" name="i_arrSogugn_0" id="i_arrSogugn_E_0" value="E" ${fn:indexOf(rvo.v_sogugn, 'E') > -1 ? 'checked=\'checked\'' : '' } onclick="fn_SogugnChk(this.id)"/>
											</span>&nbsp;무소구
										</label>	
									</span>
									( <input type=text name="i_sMusogucont_0" value="" class="inp_sty01" style="width: 100px" ${fn:indexOf(rvo.v_sogugn, 'E') > -1 ? '' : 'disabled' }/> )
								</div>
								<div style="float: left;">
									<span class="chk-style">
										<label for="i_arrSogugn_D_0">
											<span>
												<input type="checkbox" name="i_arrSogugn_0" id="i_arrSogugn_D_0" value="D" ${fn:indexOf(rvo.v_sogugn, 'D') > -1 ? 'checked=\'checked\'' : '' } onclick="fn_SogugnChk(this.id)"/>
											</span>&nbsp;기타
										</label>
									</span>
									<input type="text" name="i_sSogucont_0" value="" class="inp_sty01" style="width: 100px" ${fn:indexOf(rvo.v_sogugn, 'D') > -1 ? '' : 'disabled' }/>
								</div>
							</td>
							<th>포장단위</th>
							<td>
								<input type="text" name="i_arrPacketUnit" value="" class="inp_sty01" style="width: 100px" />
							</td>
						</tr>
						<tr class="tr_origin_img">
							<th>사용방법</th>
							<td colspan="3">
								<span class="fileinput-button" style="margin: 5px;">
									<a href="#" class="btnA bg_dark">
										<span>파일첨부</span>
									</a>
									<input id="admst_upload_btn_0" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
								</span>
								<CmTagLib:cmAttachFile uploadCd="PON" type="reg" />
								<textarea rows="4" class="textarea_sty01" style="width: 98%;" name="i_arrPonMsg"></textarea>
								<br/><span><font style="color: red;">(사용방법이 특수한 경우 기재.)</font></span>
							</td>
						</tr>
						
					</tbody>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	
	<div class="pd_top20"></div>
		
	<table class="sty_02">
		<colgroup>
			<col width="100%">
		</colgroup>
		<tbody>
			<tr>
				<td style="text-align: center;" class="last">
					<p style="font-size: 27px; font-weight: bold; padding-bottom: 30px; padding-top: 10px;">기술자료 제공 요청서</p>
				</td>
			</tr>
			<tr>
				<td class="last">
					<font id="brandnm">
						(주)신세계인터내셔날
					</font>(이하, 원사업자)은 <font id="vendornm">${rVo.v_vendor_nm }</font>(이하, 수급사업자)간에 체결예정인 제조위탁계약과 관련한 업무를 수행하기 위해 수급사업자에 다음과 같이 기술자료 제공을 요청드립니다. 수급사업자는 원사업자의 기술자료 제공요청에 응할 의무가 없으나, 하기와 같은 사유로  제품의 거래개시를 위해 필요한 자료입니다.<br/><br/></td>
			</tr>
			<tr>
				<th class="last">제1조 기술자료 내역</th>
			</tr>
			<tr>
				<td class="last" >
					<table class="sty_02">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="50%">
						</colgroup>
						<tbody>
							<tr>
								<th>구분</th>
								<th>명칭</th>
								<th>수집목적</th>
							</tr>
							<%-- <c:forEach items="${reqContentList }" var="vo">
								<tr>
									<td>${vo.v_gubun }</td>
									<td>${vo.v_name }</td>
									<td>${vo.v_purpose }</td>
								</tr>
							</c:forEach> --%>
							<tr>
								<td>ODM</td>
								<td>처방(단일, 복합), 시험용 제품 내용물, SEPC, COA(로트별), 기능성 화장품기준 및 시험방법</td>
								<td>-제품 사용가능성 적부 판정 - 유해물질 관련 성적서 및 확인서 확보</td>
							</tr>
							<tr>
								<td>ODM</td>
								<td>처방(단일, 복합), 공정도, SEPC, COA(로트별),MSDS, CCPP, 기능성 화장품 기준 및 시험방법, Trade name</td>
								<td>국내외 허가 및 외부 기관 인증(단, 국내외 허가 및 인증 진행시에 한함)</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th class="last">제2조 비밀유지</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_keepsecrt }">
							<textarea class="textarea_sty01" name="i_sKeepSecrt"  rows="5" style="width: 98%" >
1) 원사업자는 서면 또는 방문을 통해 수급사업자로부터 취득한 기술자료를 제1조에 명시된 수집 목적에 부합하는 범위 내에서만 사용할 수 있으며, 수급사업자의 사전 서면 동의 없이 제3자에게 제공하지 않습니다.
2) 만일 원사업자가 수급사업자에게 제1조에 명시된 기술자료 목록 외 기술자료를 요구하는 경우, 원사업자는 수급사업자로부터 별도의 기술자료 요구 동의를 받을 것입니다. 
3) 수급사업자가 판단하기에 수급사업자의 기술자료를 원사업자가 기술자료 요구서 발급없이 요청하는 경우, 수급사업자는 이를 거절할 수 있으며 사전에 원사업자와 협의하여 기술자료 요구서를 서면 발급 받은 후 기술자료를 제공할 수 있습니다.
4) 원사업자는 제품의 사전안전성 검토 및 기본 계약 체결 여부 평가를 위하여 수급사업자의 기술자료를 ㈜신세계인터내셔날에 위탁할 수 있습니다. 다만 이 경우에도 원사업자는 ㈜신세계인터내셔날이 수급사업자의 기술자료를 제3자에 유출하거나 유용하지 않도록 이에 대한 관리감독을 해야하며, 문제 발생시 그에 대한 법적 책임을 부담합니다. 단, 이는 원사업자가 (주)신세계인터내셔날이 아닌 경우에 한합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea name="i_sKeepSecrt"  rows="5" style="width: 98%" >${reqFileVo.v_keepsecrt }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제3조 권리귀속관계</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_relation}">
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sRelation">
원사업자가 요구하는 기술자료에 관한 권리는 수급사업자에게 있으며, 수급사업자는 기술자료 제공 후 원사업자에게 기술자료의 사용을 중단할 것을 서면으로 요청할 수 있으며, 원사업자는 수급사업자의 요청이 있는 경우 즉시 사용을 중단합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sRelation">${reqFileVo.v_relation }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제4조 대가</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_reward}">
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sReward" >이 기술자료 제공은 수급사업자의 기본 계약 체결 여부 평가를 위한 제품 안전성 검토 및 업무능력 평가 등을 위한 것으로 별도의 대가는 없습니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sReward" >${reqFileVo.v_reward }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제5조 인도일 및 인도방법</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_transfer}">
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sTransfer">인도일은 양자간 기술자료 제공 합의 완료 후 원사업자의 요청일로부터 30일 이내로 하며, 인도방법은 별도 협의합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sTransfer">${reqFileVo.v_transfer }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제6조 기술자료 사용기간</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_using_period}">
							<textarea class="textarea_sty01" rows="5" style="width: 98%"  name="i_sUsing_preiod">
1) 이 요구서에 따른 기술자료의 사용기간은 제품의 마지막 생산일로부터 10년으로 합니다.
2) 제품의 사전안전성 검토 및 업무능력 평가 결과, 제품화하지 않기로 결정한 날로부터 3년간 보관합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea class="textarea_sty01" rows="5" style="width: 98%"  name="i_sUsing_preiod">${reqFileVo.v_using_period }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제7조 반환 또는 폐기방법</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_discard}">
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sDiscard">
1) 원사업자는 제6조의 사용기간의 만료, 또는 수급사업자의 요청이 있는 경우 수급사업자의 기술자료를 반환 또는 폐기합니다.
2) 수급사업자는 언제든지 제공한 기술자료의 삭제를 원사업자에게 요청할 수 있습니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sDiscard">${reqFileVo.v_discard }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제8조 반환 또는 폐기일</th>
			</tr>
			<tr>
				<td class="last lastRow">
					<c:choose>
						<c:when test="${empty reqFileVo.v_discard_period}">
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sDiscard_period">원사업자는 폐기 사유의 발생일로부터 30일 이내에 반환 또는 폐기합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea class="textarea_sty01" rows="5" style="width: 98%" name="i_sDiscard_period">${reqFileVo.v_discard_period }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>* 동의</th> <!-- 담당BM -->
			</tr>
			<tr>
				<td>
					<span class="chk-style" style="display: flex;justify-content: center;">
						<label for="i_sAgreeyn">
							<span>
								<input type="checkbox" name="i_sAgreeyn" id="i_sAgreeyn"/>
							</span>
							위와 같은 사항을 인지하고 이에 동의합니다.
						</label>
					</span>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="pd_top20"></div>
	
		<ul class="btn_area">
			<li class="right">
				<a href="#none" class="btnA bg_dark a_save_temp"><span>임시저장</span></a>
				<a href="#none" class="btnA bg_dark a_appr"><span>접수요청</span></a>
				<a href="#none" class="btnA bg_dark a_btn_list"><span>목록</span></a>
			</li>
		</ul>
</form>


<script id="dot_content_nm_div" type="text/x-dot-templete">
<div class="content_nm_div" style="padding-bottom:5px;">
	<input type="text" class="inp_sty01 onlyNumber required i_arrContentName" size="15" name="i_arrContentName_{{=it.index}}" value="{{=it.val}}" alt="내용물명"/>
	<img alt="" src="${ImgPATH }icon/00cp_ic003.gif" style="cursor: pointer;" class="contentNm_del_btn" />
</div>
</script>

<script id="dot_product_info" type="text/x-dot-templete">
<tbody class="tbody_product_info">
						<tr>
							<th rowspan="11">
								제품정보
								<div style="margin-top: 5px; float: right; margin-right: 5px;">
									<a href="#" class="btn_product_del">
										<img src="${ImgPATH}btn/btn_del_small.gif" alt="삭제" />
									</a>
								</div>
							</th>
							<th>* 브랜드</th> <!-- 브랜드 -->
							<td>
								<select name="i_arrBrand" class="select_sty01 required" alt="브랜드">
									<option value="">-- SELECT --</option>
									<c:forEach items="${BRAND_LIST}" var="tvo">
										<option value="${tvo.COMM_CD}"<c:if test="${tvo.COMM_CD eq reqVo.s_brand_cd}">selected</c:if>>${tvo.COMM_CD_NM}</option>
									</c:forEach>
								</select>
							</td>
							<th>* 한글명</th> <!-- 한글명 -->
							<td>
								<input type="text" name="i_arrProduct_RefNm" id="i_arrProduct_RefNm" value="" class="inp_sty01" maxlength="100" style="width: 98%;" readonly/>
							</td>
						</tr>
						<tr>
							<th rowspan='1'>제품코드</th> <!-- 제품코드 -->
							<td rowspan='1'>
								<font style="font-weight: bold; color: blue; display: none;" class="fast_font">(FAST)</font>
								<input name="i_arrFastFlag" value="N" type="hidden" />
								<input type="text" name="i_arrProduct_Refcd" id="i_arrProduct_Refcd" value="" class="inp_sty01 required"
									onkeypress="prodSearch.keyPress(event, this);"
									ondblclick="prodSearch.productPop(this);" 
								alt="제품코드" style="width: 124px;" />
								
								<img id="btn_pjt_ref_pop" name="btn_pjt_ref_pop" src="${ImgPATH}common/ico_search.png" style="cursor: pointer;vertical-align: middle;" class="img"
								onclick="prodSearch.productPop(this);">								
								<img id="btn_pjt_ref_del" name="btn_pjt_ref_del" src="${ImgPATH}common/ico_delete.png" style="cursor: pointer;vertical-align: middle;" class="img del_product"
								onclick="prodSearch.delProduct(this);">
							</td>
							<th>영문명</th>
							<td>
								<input type="text" name="i_arrProduct_RefNm_En" id="i_arrProduct_RefNm_En" value="" class="inp_sty01" maxlength="100" style="width: 98%;" readonly/>
							</td>
						</tr>
						<tr style="display: none;">
							<th>* 중문명</th> <!-- 대표홋수코드 -->
							<td>
								<input type="text" name="i_arrProduct_RefNm_Cn" id="i_arrProduct_RefNm_Cn" value="${vo.v_productnm_cn}" class="inp_sty01" maxlength="100" style="width: 98%;" />
							</td>
						</tr>
						<tr>
							<th>* 본품 여부</th> <!-- 본품 여부 -->
							<td>
								<div class="div_origin_yn">
									<span class="rd-style">
										<label for="i_sOriginYn_Y_{{=it.index}}" style="margin-right:10px;">
											<span>
												<input type="radio" name="i_arrOriginYn_{{=it.index}}" id="i_sOriginYn_Y_{{=it.index}}" value="Y" class="required" alt="본품 여부" checked="checked"/>
											</span> Y
										</label>
										<label for="i_sOriginYn_N_{{=it.index}}"> 
											<span>
												<input type="radio" name="i_arrOriginYn_{{=it.index}}" id="i_sOriginYn_N_{{=it.index}}" value="N" class="required" alt="본품 여부" />
												</span> N
										</label>
									</span>
								</div>
								<div class="div_set_radio" style="display:none;">
									<div>
										<span class="rd-style">
											<c:forEach items="${originDivList}" var="odVo" varStatus="odsVo">
												<label for="i_sOriginDiv{{=it.index}}_${odsVo.index}">
													<span>
														<input type="radio" name="i_arrOriginDiv_{{=it.index}}" id="i_sOriginDiv{{=it.index}}_${odsVo.index}" value="${odVo.COMM_CD}" class="required" alt="본품 여부" />
													</span> ${odVo.COMM_CD_NM}
												</label>
											<br/>
											</c:forEach>
										</span>
									</div>
								</div>
							</td>							
							<th>수출 국가</th>
							<td>
								<div class="div_exp_country">
									<c:forEach items="${COUNTRY_LIST }" var="evo">
										<span class="chk-style" style="margin-right:10px;">
											<label for="i_arrExp_{{=it.index}}_${evo.COMM_CD}">
												<span>
													<input type="checkbox" name="i_arrExp_{{=it.index}}" id="i_arrExp_{{=it.index}}_${evo.COMM_CD}" value="${evo.COMM_CD}" />
												</span>&nbsp;${evo.COMM_CD_NM }
											</label>
										</span>
									</c:forEach>
								</div>
							</td>
						</tr>
						<tr class="content_nm_tr">
							<th>전성분 표시될<br/>내용물 개수</th>
							<td><input type="text" class="inp_sty01 onlyNumber i_arrCNnum" size="5" name="i_arrCNnum" value="${vo.v_content_num }" maxlength="2" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />&nbsp;개</td>							
							<th>연결된 본품코드<br/>(동일 내용물 제품)</th> <!-- 연결된 본품코드 -->
							<td>
								<div class="div_gi_ref" style="display:none;">
									<input type="hidden" name="i_arrParentContentCd">
									<input type="text" name="i_arrGiReferProductNm" id="i_arrGiReferProductNm" value="" class="inp_sty01 required"
									ondblclick="prodGiSearch.productPop(this);" onkeypress="prodGiSearch.keyPress(event,this);"
									alt="제품코드" style="width:88%;" />
						
									<input type="hidden" name="i_arrGiReferProductCd" id="i_arrGiReferProductCd" value="" />
									<input type="hidden" name="i_arrGiReferRecordId" id="i_arrGiReferRecordId" value="" />
						
									<img id="btn_origin_ref_pop" name="btn_origin_ref_pop" src="${ImgPATH}common/ico_search.png" style="cursor: pointer;vertical-align: middle;" class="img" 
									onclick="prodGiSearch.productPop(this);">
									<img id="btn_origin_ref_del" name="btn_origin_ref_del" src="${ImgPATH}common/ico_delete.png" style="cursor: pointer;vertical-align: middle;" class="img delorigin_product" 
									onclick="prodGiSearch.delProduct(this);">
									<br/>* 필독 ! <span style="color: red;text-decoration: underline;">동일 내용물의 제품</span>이 국내 검토 진행 중 또는 완료 이력이 있는 경우만 해당 제품코드 입력 바랍니다.
									
								</div>
							</td>
						</tr>
						<tr>
							<th>예정 입고일</th> <!-- 입고일 -->
							<td>
								<input type="text" id="i_arrStock_dt" name="i_arrStock_dt" value="" class="inbox calendar" alt="입고일" style="width: 152px; cursor: pointer;" readonly />
							</td>
							<th>* Leave On / Wash Off </th> <!-- Leave On / Wash Off -->
							<td>
								<span class="rd-style">
									<label for="i_arrLeaveon_Y_{{=it.index}}" style="margin-right:10px;">
										<span><input type="radio" name="i_arrLeaveonYn_{{=it.index}}" id="i_arrLeaveon_Y_{{=it.index}}" value="Y" alt="Leave On / Wash Off" class="required" /></span>
										Leave On
									</label>
									<label for="i_arrLeaveon_N_{{=it.index}}">
										<span><input type="radio" name="i_arrLeaveonYn_{{=it.index}}" id="i_arrLeaveon_N_{{=it.index}}" value="N" alt="Leave On / Wash Off" class="required" /></span>
										Wash Off
									</label>
								</span>
							</td>
						</tr>
						<tr>
							<th>* 대유형</th> <!-- 대유형 -->
							<td>
								<select name="i_arrType" class="select_sty01 required" alt="브랜드">
									<option value="">-- SELECT --</option>
									<c:forEach items="${ODM_PRODUCT_TYPE_LIST}" var="tvo">
										<option value="${tvo.COMM_CD}">${tvo.COMM_CD_NM}</option>
									</c:forEach>
								</select>
							</td>
							<th>* 제품유형</th> <!-- 제품유형 -->
							<td>
								<select name="i_arrCategory1" class="select_sty01 required">
									<option value="">-- SELECT --</option>
									<c:forEach items="${cateList}" var="cateVo">
										<option value="${cateVo.v_class_cd}">${cateVo.v_class_nm}</option>
									</c:forEach>
								</select>
								<select name="i_arrCategory2" class="select_sty01 required">
									<option value="">-- SELECT --</option>
								</select>
								<input type="hidden" name="i_arrPao" class="inp_sty01 onlyNumber" value="" />
								<input type="hidden" name="i_arrLife" class="inp_sty01 onlyNumber" value="" />
							</td>
						</tr>
						<tr>
							<th>기능성여부</th> <!-- 기능성 여부 -->
							<td>
								<div class="div_func_yn">
									<span class="rd-style">
										<label for="i_sFuncyn_Y_{{=it.index}}" style="margin-right:10px;">
											<span>
												<input type="radio" name="i_arrFuncyn_{{=it.index}}" id="i_sFuncyn_Y_{{=it.index}}" value="Y" class="required" alt="기능성여부" />
											</span>Y
										</label>
										<label for="i_sFuncyn_N_{{=it.index}}">
											<span>
												<input type="radio" name="i_arrFuncyn_{{=it.index}}" id="i_sFuncyn_N_{{=it.index}}" value="N" class="required" alt="기능성여부" checked='checked' />
											</span> N
										</label>
									</span>
								</div>
								<div class="div_func_ck" style="padding: 10px 0px; display: none;"></div>
							</td>
							<th>용량/용기유형</th> <!-- 용기형태/유형 -->
							<td>
								<div style="margin-bottom: 5px;">
									용량 : <input class="inp_sty01" type="text" name="i_arrProductCapacity" id="i_arrProductCapacity" value="" maxlength="15"/><br/>
									<span>* 용량 입력 시, 단위도 함께 작성해주시기 바랍니다.(ex. 10ml, 10g)</span>
								</div>
								<div>
									<select name="i_arrCntrForm" class="select_sty01 required" style="float:left; display: inline; margin-right: 5px;">
										<option value="">-- SELECT --</option>
										<c:forEach items="${CNTRFORM_LIST}" var="cntrFVo">
											<option value="${cntrFVo.COMM_CD}">${cntrFVo.COMM_CD_NM}</option>
										</c:forEach>
									</select>
									<div class="CntrForm_etc_div" style="float: left; display: none;">
										(기타 : <input type="text" name="i_arrCntrForm_etc" value="" class="inp_sty01" style="width: 100px" /> )&nbsp;
									</div>
									<select name="i_arrCntrMatr" class="select_sty01 required" style="float:left; display: inline; margin-right: 5px;">
										<option value="">-- SELECT --</option>
										<c:forEach items="${CNTRMATR_LIST}" var="cntrMVo">
											<option value="${cntrMVo.COMM_CD}">${cntrMVo.COMM_CD_NM}</option>
										</c:forEach>
									</select>
									<div class="CntrMatr_etc_div" style="display: none;">
										(기타 : <input type="text" name="i_arrCntrMatr_etc" value="" class="inp_sty01" style="width: 100px" /> )
									</div>
								</div>
							</td>
						</tr>
						<tr> <!--  style="display: none;"-->
							<th>영유아용 제품</th>
							<td>
								<div class="div_kid_yn">
									<span class="rd-style">
										<label for="i_sKid_Y_{{=it.index}}" style="margin-right:10px;">
											<span>
												<input type="radio" class="i_arrKid" name="i_arrKidYn_{{=it.index}}" id="i_sKid_Y_{{=it.index}}" value="Y" class="required" alt="영유아용 제품 여부" />
											</span> 유
										</label>
										<label for="i_sKid_N_{{=it.index}}">
											<span>
												<input type="radio" class="i_arrKid" name="i_arrKidYn_{{=it.index}}" id="i_sKid_N_{{=it.index}}" value="N" class="required" alt="영유아용 제품 여부" checked='checked' />
											</span> 무
										</label>
									</span>
								</div>
							</td>							
							<th>안정성 자료 필수 여부</th>
							<td>
								<div class="div_stabilityfile_yn">
									<span class="rd-style">
										<label for="i_arrStabilityFileYn_Y_{{=it.index}}" style="margin-right:10px;">
											<span><input type="radio" name="i_arrStabilityFileYn_{{=it.index}}" id="i_arrStabilityFileYn_Y_{{=it.index}}" value="Y"></span>유
										</label>								
										<label for="i_arrStabilityFileYn_N_{{=it.index}}">
											<span><input type="radio" name="i_arrStabilityFileYn_{{=it.index}}" id="i_arrStabilityFileYn_N_{{=it.index}}" value="N" checked='checked'></span>무
										</label>
									</span>
								</div>
								<br>* 레티놀(비타민A), 아스코빅애씨드(비타민C) 및 그 유도체, 토코페놀(비타민E), 과산화화합물, 효소 이 컨셉일 경우 기술자료 '유' 체크
							</td>
						</tr>
						<tr>
							<th>소구범위<!-- 소구범위 --></th>
							<td>
								<div style="float: left;margin-right:10px;">
									<span class="chk-style">
										<label for="i_arrSogugn_E_{{=it.index}}">
											<span>
												<input type="checkbox" name="i_arrSogugn_{{=it.index}}" id="i_arrSogugn_E_{{=it.index}}" value="E" ${fn:indexOf(rvo.v_sogugn, 'E') > -1 ? 'checked=\'checked\'' : '' } onclick="fn_SogugnChk(this.id)"/>
											</span>&nbsp;무소구
										</label>	
									</span>
									( <input type=text name="i_sMusogucont_{{=it.index}}" value="" class="inp_sty01" style="width: 100px" ${fn:indexOf(rvo.v_sogugn, 'E') > -1 ? '' : 'disabled' }/> )
								</div>
								<div style="float: left;">
									<span class="chk-style">
										<label for="i_arrSogugn_D_{{=it.index}}">
											<span>
												<input type="checkbox" name="i_arrSogugn_{{=it.index}}" id="i_arrSogugn_D_{{=it.index}}" value="D" ${fn:indexOf(rvo.v_sogugn, 'D') > -1 ? 'checked=\'checked\'' : '' } onclick="fn_SogugnChk(this.id)"/>
											</span>&nbsp;기타
										</label>
									</span>
									<input type="text" name="i_sSogucont_{{=it.index}}" value="" class="inp_sty01" style="width: 100px" ${fn:indexOf(rvo.v_sogugn, 'D') > -1 ? '' : 'disabled' }/>
								</div>
							</td>
							<th>포장단위</th>
							<td>
								<input type="text" name="i_arrPacketUnit" value="" class="inp_sty01" style="width: 100px" />
							</td>
						</tr>
						<tr class="tr_origin_img">
							<th>사용방법</th>
							<td colspan="3">
								<span class="fileinput-button" style="margin: 5px;">
									<a href="#" class="btnA bg_dark">
										<span>파일첨부</span>
									</a>
									<input id="admst_upload_btn_{{=it.index}}" type="file" name="files[]" multiple style="height:35px; width: 100%; top:1px" />
								</span>
								<CmTagLib:cmAttachFile uploadCd="PON" type="reg" />
								<textarea class="textarea_sty01" rows="4" style="width: 98%;" name="i_arrPonMsg"></textarea>
								<br/><span><font style="color: red;">(사용방법이 특수한 경우 기재.)</font></span>
							</td>
						</tr>
						
					</tbody>
</script>

<script id="dot_func_ck" type="text/x-dot-templete">

<c:forEach items="${ODM_FUNC_LIST}" var="tvo" varStatus="sf">
	<span class="chk-style" style="margin-right:10px;">
		<label for="i_sFuncCode_{{=it.index}}_${sf.index}">
			<span>
				<input type="checkbox" name="i_sFuncCode_{{=it.index}}" id="i_sFuncCode_{{=it.index}}_${sf.index}" value="${tvo.COMM_CD}"/>
			</span>${tvo.COMM_CD_NM}
		</label>
	</span>
</c:forEach>

</script>

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

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
