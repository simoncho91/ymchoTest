<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

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
div.sec_cont {
    position: relative;
     margin-top: 0px;
}
ul.btn_area {
    margin-top: 0px;
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
<script type='text/javascript' src='/sitims/js/util/cm_function.js'></script> 
<script type='text/javascript'>
var orginVal = '';
 j$(function(){
	 fn_init();
	 orginVal = $('#frm').serialize();
 });

	 function attachSuccEvent(attData, uploadCd){
		var attach_size = attData.v_attach_size;
		var attach_lnm = attData.v_attach_lnm;
		var attach_pnm = attData.v_attach_pnm;
		var attach_type = attData.v_attach_type;
		var attach_mgtid = attData.v_attach_mgtid;
		var attach_id = attData.v_attach_id;
		var attach_idx = attData.v_attach_idx;
		var pk1 = j$("input[name='i_sRecordId']").val();
		var table = j$(".table_" + uploadCd).eq(attach_idx);
		
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
			, del_img_url : ImgPATH + "btn/btn_del_small.gif"
			, v_buffer1 : j$("input[name='i_sProductCd']").val()
			, v_buffer2 : ""
			, v_buffer3 : ""
			, v_buffer4 : ""
			, v_buffer5 : ""
		};
		var pagefn = doT.template(document.getElementById("dot_upload").text, undefined, undefined);
		j$(pagefn(obj)).appendTo(table.find("tbody"));
	}
 function fn_init(){	
	inputChkRadioAddEvent();
	
	 jfupload.initUpload({
			target : j$("#admst_upload_btn")
			, uploadCd : "PON"
			, index : 0
			, formName : "frm"
			, success : attachSuccEvent
			, isSelfMakeTag : true
			, attachDir : "PON"
		});
		

	 $("#i_sExpCode_CN").is(":checked") ? cn_chk() : cn_deChk();
	
	// 수정
	j$("a[id=save_btn]").click(function(event){
		event.preventDefault();
		fn_save();
	});	
	j$("a[id=receipt_btn]").click(function(event){
		event.preventDefault();
		fn_save("R");
	});	
		
	// 본품여부 체크 이벤트
	j$(".div_origin_yn").find("input[type='radio']").click(function(){
		var index = j$(".div_origin_yn").index(j$(this).parents(".div_origin_yn").eq(0));
		var val = j$(this).parents(".div_origin_yn").find('input[type=radio]:checked').val();
		OreqReg.addEvent.divSetRadioShowHide(index,val);
	});
	
	// 본품여부 N 라디오 이벤트
	j$(".div_set_radio").find("input[type='radio']").click(function(){
		var index = j$(".div_set_radio").index(j$(this).parents(".div_set_radio").eq(0));
		OreqReg.addEvent.divSetRadioNShowHide(index);
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
	
	j$("select[name='i_sCntrForm']").change(function(){
		var div_idx = j$("select[name='i_sCntrForm']").index(j$(this));
		if(j$(this).val() == 'OCT_1_21'){
			j$(".CntrForm_etc_div").eq(div_idx).show();
		}else{
			j$(".CntrForm_etc_div").eq(div_idx).hide();
			j$(".CntrForm_etc_div").find("input[name='i_sCntrForm_etc']").val("");
		}
	});
	
	j$("select[name='i_sCntrMatr']").change(function(){
		var div_idx = j$("select[name='i_sCntrMatr']").index(j$(this));
		
		if(j$(this).val() == 'OCT_2_04'){
			j$(".CntrMatr_etc_div").eq(div_idx).show();
		}else{
			j$(".CntrMatr_etc_div").eq(div_idx).hide();
			j$(".CntrMatr_etc_div").find("input[name='i_sCntrMatr_etc']").val("");
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
 }
 
function fn_validation(){
	var chkError=false;
	var errorMsg="";
	var regex = /[^0-9]/g
	if(fn_isNull($('#i_sUserid').val())){
		fn_s_alertMsg("제품 담당자를 입력해주세요.");
		return false;
	}
	$('.tbody_product_info').each(function(i,obj){
		var capacityVal = $(obj).find('input[name=i_sProductCapacity]').val();
		
/* 		if(fn_isNull($(obj).find('input[name=i_sCNnum]').val())){
			chkError=true;
			errorMsg="전성분 표시될 내용물 개수(숫자) 입력해주세요.";
			$(obj).find('input[name=i_sCNnum]').focus();
			return;
		} */
		if(regex.test($(obj).find('input[name=i_sCNnum]').val())){
			chkError=true;
			errorMsg="내용물 개수는 숫자만 입력해주세요.";
			$(obj).find('input[name=i_sCNnum]').focus();
			return;
		}
		
		if($("#i_sExpCode_CN").is(":checked") == true){
			if(fn_isNull($(obj).find('input[name=i_sProduct_CnNm]').val())){
				chkError=true;
				errorMsg="중국 체크시 제품의 중문명을 입력해주세요.";
				$(obj).find('input[name=i_sProduct_CnNm]').focus();
				return;
			}
		}
		if(fn_isNull($(obj).find('input[name=i_sProduct_KoNm]').val())){
			chkError=true;
			errorMsg="제품의 한글명을 입력해주세요.";
			$(obj).find('input[name=i_sProduct_KoNm]').focus();
			return;
		}
		if(fn_isNull($(obj).find('input[name=i_sProduct_EnNm]').val())){
			chkError=true;
			errorMsg="제품의 영문명을 입력해주세요.";
			$(obj).find('input[name=i_sProduct_EnNm]').focus();
			return;
		}
		/* if(capacityVal.substr((capacityVal.length - 1)) != 'g' && capacityVal.substr((capacityVal.length - 2)) != 'ml'){
			chkError=true;
			errorMsg="용량 마지막에 g 이나 ml를 포함 시켜주세요";
			$(obj).find('input[name=i_sProductCapacity]').focus();
			return;
		} */
		if($(obj).find('.div_func_yn label.on input[name=i_sFuncyn]').val() == 'Y'){
			if($('.div_func_ck').find('input[type="checkbox"]:checked').length == 0){
				chkError=true;
				errorMsg="기능을 체크해주세요.";
				return;
			}
		}
	});
	if(chkError){
		fn_s_alertMsg(errorMsg);
		return false;
	}
	return true;
}
function fn_byteChk(){
	var chkError=false;
	var errorMsg="";

	$('.tbody_product_info').each(function(i,obj){
		
		if(!check_length($(obj).find('input[name=i_sProduct_CnNm]').val(),500)){
			chkError=true;
			errorMsg="제품 중문명이 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sProduct_CnNm]').focus();
			return;
		}
		
		if(!check_length($(obj).find('input[name=i_sProduct_KoNm]').val(),500)){
			chkError=true;
			errorMsg="제품 한글명이 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sProduct_KoNm]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sProduct_EnNm]').val(),500)){
			chkError=true;
			errorMsg="제품 영문명이 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sProduct_EnNm]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sCntrForm_etc]').val(),30)){
			chkError=true;
			errorMsg="용기형태 기타가 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sCntrForm_etc]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sCntrMatr_etc]').val(),30)){
			chkError=true;
			errorMsg="용기재질 기타가 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sCntrMatr_etc]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sProductCapacity]').val(),30)){
			chkError=true;
			errorMsg="용량이 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sProductCapacity]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sMusogucont]').val(),100)){
			chkError=true;
			errorMsg="무소구의 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sMusogucont]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sSogucont]').val(),100)){
			chkError=true;
			errorMsg="소구의 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sSogucont]').focus();
			return;
		}
		if(!check_length($(obj).find('input[name=i_sPonMsg]').val(),1000)){
			chkError=true;
			errorMsg="사용방법이 최대 제한글자수를 초과했습니다.";
			$(obj).find('input[name=i_sPonMsg]').focus();
			return;
		}
	
	});
	if(chkError){
		fn_s_alertMsg(errorMsg);
		return false;
	}
	return true;
}

function fn_save(mode){
	if(orginVal == $('#frm').serialize()){
		parent.fn_s_alertMsg("수정할 사항이 없습니다.");
		fn_popClose();
		return;
	}
	if(!fn_validation()) return;
	if(!fn_byteChk()) return;

	var _obj = parent;
	dhx.confirm({
		header: "제품정보 수정",
		text: '저장 하시겠습니까?',
		buttons: ["예", "아니오"],
		buttonsAlignment:"center"
	}).then(function(answer){
		if (answer){
			var i_sReceipStatus = 'RS010';
			$('input[name="i_sReceipStatus"]').val(i_sReceipStatus);
			if(mode =='R') $('input[name="i_sFinalRst"]').val("");

			var postParam = $('#frm').serialize();
			$.ajax({
				 url: "/br/pr/010/br_pr_010_modifyForPermitReqStatus.do",
				 data: postParam,
				 type: "POST",
				 dataType: "json",
				 success:function(data){
					 if(data.status=="success"){
							//var pCd = $("input[name='i_sProductCd']").val();
							//var pId = $("input[name='i_sRecordId']").val();
							fn_popClose(function(){
								var url = _obj.location.href.split('?')[0]
								_obj.$('#frm').attr("action", url).submit();
							});
// 							console.log(parent);
// 						 parent.$("#frm").submit();
					 }else{
						parent.fn_s_alertMsg(data.message);
						fn_popClose();
					 }
				}
			});
		}
	});
}
function fn_cnChk(){
	if($("#i_sExpCode_CN").is(":checked")){
		cn_chk();
	}else{
		cn_deChk();
	}
}
function cn_chk(){
	$('input[name=i_sProduct_CnNm]').attr('disabled', false);
	$("#prodNmCnTr").show();
}
function cn_deChk(){
	$('input[name=i_sProduct_CnNm]').attr('disabled', true);
	$("#prodNmCnTr").hide();
}

</script>
<form name="frm" id="frm" action="" method="post">
	 <input type="hidden" name="i_sReceipStatus" value=""/>
	 <input type="hidden" name="i_sFinalRst" value="${prodList.get(0).v_final_rst}"/>
	 
	
	<ul class="btn_area">
		<li class="right">
			<a class="btnA bg_dark" href="#none" id="save_btn"><span>저장</span></a>
			<c:if test="${prodList.get(0).v_final_rst eq 'RS025'}">
				<a class="btnA bg_dark" href="#none" id="receipt_btn"><span>접수요청</span></a>
			</c:if>
			<a class="btnA bg_dark" href="#none" id="close_btn" onclick="fn_popClose()"><span>닫기</span></a>
		</li>
	</ul>
	<table class="sty_02" style="width: 100%">
		<colgroup>
			<col width="20%">
			<col width="*">
		</colgroup>
		<tbody class="tbody_product_info">
		<c:forEach items="${prodList}" var="prodVo" varStatus="s">
			<input type="hidden" name="i_sRecordId" value="${prodVo.v_record_id}" />
			<input type="hidden" name="i_sProductCd" value="${prodVo.v_product_cd}">
			<input type="hidden" name="i_sBrandCd" value="${prodVo.v_brand_cd}">
			<input type="hidden" name="i_sCompanycd" value="${prodVo.v_vendor_id}" />
			<input type="hidden" name="i_sCompanyLabor" value="${prodVo.v_vendor_labor_id}" />
			<input type="hidden" name="i_sImportyn" value="${prodVo.v_import_yn}" />
			<input type="hidden" name="i_sImport" value="${prodVo.v_flag_imp}" />
			<input type="hidden" name="i_sF_mager_nm" value="${prodVo.v_f_maker_nm}" />
			<input type="hidden" name="i_sL_mager_nm" value="${prodVo.v_l_maker_nm}" />
			<input type="hidden" name="i_sDisabled" value="${prodVo.v_final_rst eq '' ?  'N' :'Y'}" />
			<tr style="display:none;">
				<th>브랜드</th> <!-- 브랜드 -->
				<td style="background-color: #F5F5F5;">${prodVo.v_brand_nm}</td>
			</tr>
			<tr>
				<th>
					한글명
				</th>
				<td>
					<input type="text" name="i_sProduct_KoNm" value="${prodVo.v_product_nm_ko}" class="inp_sty01" maxlength="100" style="width: 98%;" />
				</td>
			</tr>
			<tr>
				<th>
					영문명
				</th>
				<td>
					<input type="text" name="i_sProduct_EnNm" value="${prodVo.v_product_nm_en}" class="inp_sty01" maxlength="100" style="width: 98%;" />
				</td>
			</tr>
			<tr id="prodNmCnTr" style="display:none;">
				<th>
					중문명
				</th>
				<td>
					<input type="text" name="i_sProduct_CnNm" value="${prodVo.v_product_nm_cn}" class="inp_sty01" maxlength="100" style="width: 98%;" disabled/>
				</td>
			</tr>
			<tr style="display:none;">
				<th>제품코드</th> <!-- 제품코드 -->
				<td style="background-color: #F5F5F5;">${prodVo.v_product_cd}</td>
			</tr>
			<tr style="display:none;">
				<th>제품담당자</th> <!-- 담당BM -->
				<td colspan='1'>
					<input type="text" id="i_sUsernm" name="i_sUsernm" value="${rVo.v_bm_nm}" class="inp_sty01 required"  />
					<input type="hidden" id="i_sUserid" name="i_sUserid" value="${rVo.v_bm_id}" />
					<img id="search_bm" name="search_bm" src="${ImgPATH}icon/00cp_ic004.gif" class="img" style="cursor: pointer;vertical-align: middle;">
					<img id="del_bm" name="del_bm" src="${ImgPATH}icon/00cp_ic003.gif" class="img" style="cursor: pointer;vertical-align: middle;" >
				</td>
			</tr>
<%-- 			<tr>
				<th>출시일</th> <!-- 출시일 -->
				<td>
					<input type="text" name="i_sRelease_dt" value="${cfn:cmDatePatten(prodVo.v_release_dt)}" class="inbox calendar"alt="<fmt:message key='pms.common.startdt'/>" style="width: 152px; cursor: pointer;" />
				</td>
			</tr>
			<tr>
				<th>입고일</th> <!-- 입고일 -->
				<td>
					<input type="text" name="i_sStock_dt" value="${cfn:cmDatePatten(prodVo.v_stock_dt)}" class="inbox calendar" alt="<fmt:message key='pms.common.startdt'/>" style="width: 152px; cursor: pointer;" />
				</td>
			</tr> --%>
			<tr style="display:none;">
				<th>대유형</th> <!-- 대유형 -->
				<td style="background-color: #F5F5F5;">${prodVo.v_type_nm}</td>
			</tr>
			<tr style="display:none;">
				<th>제품유형</th> <!-- 제품유형 -->
				<td style="background-color: #F5F5F5;">
					<span>${prodVo.v_category1_nm}</span> > <span>${prodVo.v_category2_nm}</span>
				</td>
			</tr>
			<tr style="display:none;">
				<th>전성분 표시될<br/>내용물 개수</th>
				<td>
					<input type="text" class="inp_sty01 onlyNumber" size="5" name="i_sCNnum" value="${prodVo.v_content_num}"/>&nbsp;개
				</td>
			</tr>
			<tr style="display:none;">
				<th>본품 여부</th> <!-- 본품 여부 -->
				<td ${prodVo.v_origin_yn ne 'N'? 'style="background-color: #F5F5F5;"' : ''}>
					<input type="hidden" name="i_sOriginYn" value="${prodVo.v_origin_yn}"/>
					<c:choose>
						<c:when test="${prodVo.v_origin_yn eq 'N' }">
							<div style="float:left;width: 20%;">
								<span id="span_originyn">N</span>
							</div>
							<div class="div_set_radio" style="float: left;width: 75%;">
								<span class="rd-style">
								<c:forEach items="${originDivList}" var ="odVo" varStatus="odsVo">
									<label for="i_sOriginDiv_${odsVo.index}" style="margin-right:10px;">
										<span>
											<input type="radio" id="i_sOriginDiv_${odsVo.index}" name="i_sOriginDiv" value="${odVo.COMM_CD}" class="required"
											 ${prodVo.v_origin_div eq odVo.COMM_CD ? 'checked' : ''}
											 
											 />
										 </span>${odVo.COMM_CD_NM}
									</label>
								</c:forEach>
								</span>	
							</div>
						</c:when>
						<c:otherwise>
							<span id="span_originyn">Y</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr style="display:none;">
				<th>기능성 여부</th><!-- 기능성 여부 -->
				<td style="background-color: #F5F5F5;">
					<div class="div_func_yn">
					<c:choose>
						<c:when test="${prodVo.v_func_yn eq 'Y'}">
						<span>Y</span>
						</c:when>
						<c:otherwise>
						<span>N</span>
						</c:otherwise>
					</c:choose>
						<%-- <span class="rd-style">
							<label for="i_sFuncyn_Y" style="margin-right:10px;">
								<span>
									<input type="radio" name="i_sFuncyn" id="i_sFuncyn_Y" value="Y" class="required" alt="기능성여부" ${prodVo.v_func_yn eq 'Y' ? 'checked':'' } disabled/>
								</span> Y
							</label>
							<label for="i_sFuncyn_N"> 
								<span>
									<input type="radio" name="i_sFuncyn" id="i_sFuncyn_N" value="N" class="required" alt="기능성여부" ${prodVo.v_func_yn eq 'N' ? 'checked':'' } disabled/>
									</span> N
							</label>
						</span> --%>
					</div>
					<div class="div_func_ck" style="padding: 10px 0px;${prodVo.v_func_yn eq 'Y'?'':'display: none' };">
						<c:if test="${prodVo.v_func_yn eq 'Y'}">
							<c:set var="func_value" value="" />
							<c:choose>
								<c:when test="${!empty prodVo.v_refer_product_cd }">
									<c:forEach items="${funcList}" var="fVo" varStatus="sf">
										<c:if test="${fVo.v_product_cd eq prodVo.v_refer_product_cd}">
											<c:set var="func_value" value="${func_value}/${fVo.v_func_code}" />
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${funcList}" var="fVo" varStatus="sf">
										<c:if test="${fVo.v_product_cd eq prodVo.v_product_cd}">
											<c:set var="func_value" value="${func_value}/${fVo.v_func_code}" />
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
							<c:forEach items="${ODM_FUNC_LIST}" var="tvo" varStatus="sf">
								<c:if test="${fn:indexOf(prodVo.v_func_code, tvo.COMM_CD) > -1}">
									<span class="chk-style">
										<label for="i_sFuncCd_${sf.index}" style="margin-right:10px;">
											<span>
												<input type="checkbox" name="i_sFuncCd" id="i_sFuncCd_${sf.index}" value="${tvo.COMM_CD}" ${fn:indexOf(prodVo.v_func_code, tvo.COMM_CD) > -1 ? 'checked':'' } disabled/>
											</span>${tvo.COMM_CD_NM}
										</label>
									</span>
								</c:if>	
							</c:forEach>
						</c:if>
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<th>Leave On / Wash Off</th> <!-- Leave On / Wash Off -->
				<td>
					<span class="rd-style">
						<label for="i_sLeaveon_Y" style="margin-right:10px;">
							<span><input type="radio" name="i_sLeaveonYn" id="i_sLeaveon_Y" value="Y" alt="Leave On / Wash Off" class="required" ${prodVo.v_leaveon_yn eq 'Y' ? 'checked':'' }/></span>
							Leave On
						</label>
						<label for="i_sLeaveon_N">
							<span><input type="radio" name="i_sLeaveonYn" id="i_sLeaveon_N" value="N" alt="Leave On / Wash Off" class="required" ${prodVo.v_leaveon_yn eq 'N' ? 'checked':'' }/></span>
							Wash Off
						</label>
					</span>
				</td>
			</tr>
			<tr style="display:none;">
				<th>용량/용기유형</th><!-- 용량/용기유형 -->
				<td>
					<div style="margin-bottom: 5px;">
						용량 : <input class="inp_sty01" type="text" name="i_sProductCapacity" id="i_sProductCapacity" value="${prodVo.v_capacity }"/><br/>
						<span>* 용량 입력 시, 단위도 함께 작성해주시기 바랍니다.(ex. 10ml, 10g)</span>
					</div>
					<select name="i_sCntrForm" style="float:left; display: inline; margin-right: 5px;" class="required select_sty01">
						<option value="">-- SELECT --</option>
						<c:forEach items="${CNTRFORM_LIST}" var="cntrFVo">
							<option value="${cntrFVo.COMM_CD}" ${prodVo.v_cntr_form eq cntrFVo.COMM_CD ? 'selected' : '' }>${cntrFVo.COMM_CD_NM}</option>
						</c:forEach>
					</select>
					<div class="CntrForm_etc_div" style="float: left; display: ${prodVo.v_cntr_form eq 'OCT_1_21' ? 'block' : 'none' };">
						(기타 : <input type="text" name="i_sCntrForm_etc" value="${prodVo.v_cntr_form_etc}" class="inp_sty01" style="width: 100px" /> )&nbsp;
					</div>
					<select name=i_sCntrMatr style="float:left; display: inline; margin-right: 5px;" class="required select_sty01">
						<option value="">-- SELECT --</option>
						<c:forEach items="${CNTRMATR_LIST}" var="cntrMVo">
							<option value="${cntrMVo.COMM_CD}" ${prodVo.v_cntr_matr eq cntrMVo.COMM_CD ? 'selected' : '' }>${cntrMVo.COMM_CD_NM}</option>
						</c:forEach>
					</select>
					<div class="CntrMatr_etc_div" style="display: ${prodVo.v_cntr_matr eq 'OCT_2_04' ? 'block' : 'none' };">
						(기타 : <input type="text" name="i_sCntrMatr_etc" value="${prodVo.v_cntr_matr_etc}" class="inp_sty01" style="width: 100px" /> )
					</div>
				</td>
			</tr>
			
			<tr style="display:none;">
				<th>소구범위</th><!-- 소구범위 -->
				<td>
					<div style="float: left;">
						<span class="chk-style">
							<label for="i_sSogugn_E">
								<span>
									<input style="" type="checkbox" name="i_sSogugn" id="i_sSogugn_E" value="E" ${fn:indexOf(prodVo.v_free_gn, 'E') > -1 ? 'checked' : ''} />
								</span>
								무소구<!-- 무소구 -->
							</label>
						</span>
						( <input type=text name="i_sMusogucont" value="${prodVo.v_musogu_cont}" class="inp_sty01" style="width: 100px" /> )
					</div>
					<div style="float: left; padding-left: 20px;">
						<span class="chk-style">
							<label for="i_sSogugn_D">
								<span>
									<input type="checkbox" name="i_sSogugn" id="i_sSogugn_D" value="D" ${fn:indexOf(prodVo.v_free_gn, 'D') > -1 ? 'checked' : ''} />
								</span>
								기타<!-- 기타 -->
							</label>
						</span>
						( <input type="text" name="i_sSogucont" value="${prodVo.v_sogu_cont}" class="inp_sty01" style="width: 100px" /> )
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<th>수출국가</th>  <!-- 예상 수출국가 -->
				<td colspan="1">
					<div class="div_exp_country">
						<c:forEach items="${NATIONRA_LIST}" var="evo" varStatus="s">
							<span class="chk-style" style="margin-right:10px;">
								<label for="i_sExpCode_${evo.COMM_CD}">
									<span>
										<input type="checkbox" name="i_sExpCode" id="i_sExpCode_${evo.COMM_CD}" value="${evo.COMM_CD}" 
											${fn:indexOf(prodVo.v_exp_country, evo.COMM_CD) > -1 ? 'checked':''}
											${(fn:indexOf(prodVo.v_exp_country, evo.COMM_CD) > -1 and prodVo.v_final_rst ne '') ? 'disabled':''}
											${evo.COMM_CD == 'CN' ? 'onclick="fn_cnChk()"':''}
											/>
									</span>&nbsp;${evo.COMM_CD_NM }
								</label>
							</span>
						</c:forEach>
						<div>
							<span>* 서류 진행중인 국가는 변경할 수 없습니다.</span>
						</div>
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<th>사용방법</th>
				<td>
					<span class="fileinput-button" style="margin: 5px;">
						<a href="#" class="btnA bg_dark">
							<span>파일첨부</span>
						</a>
						<input id="admst_upload_btn" type="file" name="files[]" multiple style="height: 35px; width: 100%; top: 1px;" />
					</span>
					<CmTagLib:cmAttachFile uploadCd="PON" type="reg" recordId="${prodVo.v_record_id}" pk1="${prodVo.v_product_cd}"/>
					<textarea rows="4" style="width: 98%; margin-left: 5px;" name="i_sPonMsg" class="textarea_sty01">${prodVo.v_pon_msg}</textarea>
					<span><font style="color: red;">(사용방법이 특수한 경우 기재.)</font></span>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pd_top10"></div>
	<ul class="btn_area">
		<li class="right">
			<a class="btnA bg_dark" href="#none" id="save_btn"><span>저장</span></a>
			<c:if test="${prodList.get(0).v_final_rst eq 'RS025'}">
				<a class="btnA bg_dark" href="#none" id="receipt_btn"><span>접수요청</span></a>
			</c:if>
			<a class="btnA bg_dark" href="#none" id="close_btn" onclick="fn_popClose()"><span>닫기</span></a>
		</li>
	</ul>
</form>
<%-- <script type="text/javascript" src="${ScriptPATH}page_js/pif/oreq/pif_oreq_mod_pop.js"></script> --%>
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
    </td>
    <td>{{=it.n_attach_size}} KB</td>
    <td class="last">
		<a href="javascript:this.onclick;" onclick="javascript:attachDel(j$(this), 'frm', '{{=it.v_upload_cd}}');" class="btn_attach_del" id="{{=it.v_attach_id}}">
			<img src="{{=it.del_img_url}}"/>
		</a>
    </td>
</tr>
</script>
<script id="dot_func_ck" type="text/x-dot-templete">

<c:forEach items="${ODM_FUNC_LIST}" var="tvo" varStatus="sf">
	<span class="chk-style" style="margin-right:10px;">
		<label for="i_sFuncCd_${sf.index}">
			<span>
				<input type="checkbox" name="i_sFuncCd" id="i_sFuncCd_${sf.index}" value="${tvo.COMM_CD}"/>
			</span>${tvo.COMM_CD_NM}
		</label>
	</span>
</c:forEach>

</script>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
