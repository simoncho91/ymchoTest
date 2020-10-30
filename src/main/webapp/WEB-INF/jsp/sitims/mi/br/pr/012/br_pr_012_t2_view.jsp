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
.trNone{
display:none;
}
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
 	$('.sty_tab').find('#INFO').click(function(e){
 		var record_id = $('input[name=i_sRecordId]').val();
 		var prodcut_cd = $('input[name=i_sProductCd]').val();
 		location.href="/br/pr/012/br_pr_012_t1_view.do?i_sRecordId="+record_id+'&i_sProductCd='+prodcut_cd;
 	});

 	$('.li_tab_part').click(function(e){
		event.preventDefault();
		j$(".li_tab_part a").removeClass("on");
		j$(this).find('a').addClass("on");
		
		var partno = this.id;
		
		j$(".spec_file_div").hide();
		j$("#spec_file_div_"+partno).show();
 	});
 	$('.btn_list').click(function(e){
 		location.href="/br/pr/012/init.do?openMenuCd=MIBRPR012";
 	});
	
	j$("#btn_claim_support_preview").unbind("click").click(function(event) {
		var groupId = $("input[name='i_sGroupId']").val();
		if(!(groupId.indexOf('ADMIN')>-1|| groupId.indexOf('RA')>-1) && groupId.indexOf('PDDOWN')<0 && groupId.indexOf('PDREG')<0){
			fn_s_alertMsg('다운로드권한이 없습니다.');
			return;
		}
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		j$(".li_tab_part1.selected").attr("id");
		var recordid = j$("input[name='i_sRecordId']").val();
		var _patn = WebPATH + "br/pw/020/doc/br_pw_020_doc_claim_pop.do?i_sRecordId=" + recordid + "&i_sClaimPartNo="+partno
				+"&i_sProductCd="+j$("input[name='i_sProductCd']").val();
		fn_pop({title:"미리보기",width:900,height:600,url:_patn});	
	});
	j$("#btn_claim_support_pdf").unbind("click").click(function(event) {	
		if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
			fn_s_alertMsg('다운로드권한이 없습니다.');
			return;
		}
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var recordid = j$("input[name='i_sRecordId']").val();
		
		window.open(WebPATH + "br/pw/020/doc/br_pw_020_doc_part1_claim_report.do?i_sRecordId=" + recordid + "&i_sClaimPartNo="+partno+"&i_sProductCd="+j$("input[name='i_sProductCd']").val());
	});
	
	j$("#btn_preview_process").unbind("click").click(function(event) {
		event.preventDefault();			
		if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
			fn_s_alertMsg('다운로드권한이 없습니다.');
			return;
		}
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
	j$("#btn_certi_fragrance_preview").unbind("click").click(function(event){
		
		if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
			fn_s_alertMsg('다운로드권한이 없습니다.');
			return;
		}
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		var url = WebPATH + "br/pw/020/doc/br_pw_020_doc_fragrance_pop.do?i_sRecordId="+recordid+"&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd;
		fn_pop({title:"서류 요청",width:900,height:600,url:url });		
	});
	
	j$("#btn_certi_fragrance_pdf").unbind("click").click(function(event) {	
		if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
			fn_s_alertMsg('다운로드권한이 없습니다.');
			return;
		}
	 	var partSize = $("input[name='i_sPartSize']").val();
	 	if(partSize == 0){
	 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
	 		return;
	 	}
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var productCd = j$("input[name='i_sProductCd']").val();
		var recordid = j$("input[name='i_sRecordId']").val();
		var seq = j$("input[name='i_iVerSeq']").val();
		
		window.open(WebPATH + "br/pw/020/doc/br_pw_020_doc_part1_fragrance_report.do?i_sRecordId=" + recordid + "&i_sPartNo="+partno+"&i_iVerSeq="+seq+"&i_sProductCd="+productCd);
	});
 });
 function showPreView(url, allergenYn, partNum, refYn){	
		if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
			fn_s_alertMsg('다운로드권한이 없습니다.');
			return;
		}
		var recordid = j$("input[name='i_sRecordId']").val();
		var partno = j$(".li_tab_part").find('.on').parent().attr('id');
		var verSeq = j$("input[name='i_iVerSeq']").val();
		var i_sMatrcd =  $("input[name='i_sProductCd']").val();
		var path = "";
		
		path = WebPATH + url + ".do?i_sRecordId="+recordid+"&i_sProductCd="+i_sMatrcd+"&i_sPartNo="+partno+"&i_iVerSeq="+verSeq+"&i_sRefYn="+refYn;
		fn_pop({title:"미리보기",width:900,height:600,url:path});
// 		cmDialogOpen("preview_pop", {
// 			title : glbMsg.preview
// 			, url : path
// 			, width : 700
// 			, height : 600
// 			, changeViewAutoSize : true
// 		});
	}

function downloadPdf(url , allergenYn, partNum, refYn) {	
	if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
		fn_s_alertMsg('다운로드권한이 없습니다.');
		return;
	}
	var data = {
			i_sRecordId : $("input[name='i_sRecordId']").val()
			, i_sProductCd :  $("input[name='i_sProductCd']").val()
			, i_iVerSeq : j$("input[name='i_iVerSeq']").val()
			, i_sPartNo : $(".li_tab_part").find('.on').parent().attr('id')
			, i_sRefYn : refYn
	};
	
	if(allergenYn != ""){
		path = WebPATH+url+"?"+j$.param(data, true);
	}else{
		path = WebPATH+url+"?"+j$.param(data, true);
	}
	window.open(path);
}

function download_PDF(div, partNum, allergenYn, lang, refYn){
	
	if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
		fn_s_alertMsg('다운로드권한이 없습니다.');
		return;
	}
	var data = {
			i_sRecordid : $("input[name='i_sRecordId']").val()
			, i_sMatrcd :  $("input[name='i_sProductCd']").val()
			, i_iVerSeq : j$("input[name='i_iVerSeq']").val()
			, i_sPartNo : $(".li_tab_part").find('.on').parent().attr('id')
			, i_sDiv : div
			, i_sRecipeType : "C"
			, i_sRefYn : refYn
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
function showPreViewC(url, allergenYn, partNum, refYn,nationCd){	
	if(!($("input[name='i_sGroupId']").val() == 'ADMIN' || $("input[name='i_sGroupId']").val() == 'RA') && $("input[name='i_sGroupId']").val() != 'PDDOWN' && $("input[name='i_sGroupId']").val() != 'PDREG'){
		fn_s_alertMsg('다운로드권한이 없습니다.');
		return;
	}
 	var partSize = $("input[name='i_sPartSize']").val();
 	if(partSize == 0){
 		fn_s_alertMsg("Fomula 작성이 완료되지않았습니다.");
 		return;
 	}
	var recordid = j$("input[name='i_sRecordId']").val();
	var partno = j$(".li_tab_part").find('.on').parent().attr('id');
	var verSeq = j$("input[name='i_iVerSeq']").val();
	var i_sMatrcd =  $("input[name='i_sProductCd']").val();
	var path = "";
	
	path = WebPATH + url + ".do?i_sRecordId="+recordid+"&i_sProductCd="+i_sMatrcd+"&i_sPartNo="+partno+"&i_iVerSeq="+verSeq+"&i_sRefYn="+refYn+"&i_sLang="+nationCd;
	fn_pop({title:"미리보기",width:900,height:600,url:path});
}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_sGlbRecordid" value="${reqVo.i_sGlbRecordid }" />
	<input type="hidden" name="i_iVerSeq" value="${rVo.n_vsn }" /> 
	<input type="hidden" name="i_sMatrcd" value="${rVo.v_product_cd }" /> 
	<input type="hidden" name="i_sMatrnm" value="${rVo.v_productnm_en }" /> 
	<input type="hidden" name="i_sTrnsctreqid" value="${rVo.v_companycd }" /> 
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo }" />
	
	<input type="hidden" name="i_sDeptCd" value="${reqVo.s_deptcd }" />
	<input type="hidden" name="i_sGroupId" value="${reqVo.s_groupid }" />
	
	
	<div class="top_btn_area" style="z-index:1;">
		<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
	</div>
	<div class="pd_top10"></div>
	<div class="sec_cont">	
		<ul class="sty_tab">
			<li class="tab "><a href="#" class="btn_ic_tab" id="INFO"><span>기본정보</span></a></li>
			<li class="tab"><a href="#" class="btn_ic_tab on" id="RESULT"><span>검토결과</span></a></li>		
		</ul>

		<ul class="sty_tab ul_tab_part3">
			<c:forEach items="${partList }" var="vo" varStatus="s">
				<li class="tab li_tab_part" id="${vo.n_part_no }"><a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
				</li>
			</c:forEach>
		</ul>
		<table class="sty_03 table_part">
		<colgroup>
			  <col style="width:22%;">
              <col style="width:;">
              <col style="width:;">
              <col style="width:;">
              <col style="width:;">
            </colgroup>
		<thead>
			<tr>
				<th>Document</th>
				<th colspan='2'>미리보기</th>
				<th colspan='2'>Download</th>
			</tr>
		</thead>
		<tbody>		
			<tr class="trNone">
				<td rowspan='2' class="bdl_n">package images</td>		
				<th>단상자</th>
				<th>용기</th>
				<th>내지</th>						
				<td class="ta_c" rowspan="2">
				</td>
			</tr>
			<tr class="trNone">
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
			<tr class="trNone">
				<td class="bdl_n">batch coding system</td>
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
			<tr class="trNone">
				<td class="bdl_n">undesirable health effects summary</td>
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
			<tr class="trNone">
				<td class="bdl_n">ingredients list</td>
				<td class="ta_c" colspan='2'>
					<a class="img_btn" href="#none" onclick="showPreView('br/pw/020/doc/br_pw_020_doc_ingredient_pop', '', '1')">
						<img src="${ImgPATH}common/img_print.png">
					</a>
				</td>
				<td class="ta_c" colspan="2"></td>
			</tr>
				<tr class="trNone">
					<td class="bdl_n">claim support summary</td>
					<td class="ta_c" colspan='2'>
						<a class="img_btn" href="#none" id="btn_claim_support_preview">
							<img alt="" src="${ImgPATH}common/img_print.png">
						</a>
					</td>
					<td class="ta_c" colspan="2">
						<a class="img_btn" href="#none" id="btn_claim_support_pdf"> <img alt="" src="${ImgPATH}common/img_pdf.png"></a>
					</td>
				</tr>
				<tr class="trNone">
					<td class="bdl_n">certi of fragrance</td>
					<td class="ta_c" colspan="2">						
						<a class="img_btn" href="#none" id="btn_certi_fragrance_preview"> <img alt="" src="${ImgPATH}common/img_print.png"></a>
					</td>
					<td class="ta_c" colspan="2">						
						<a class="img_btn" href="#none" id="btn_certi_fragrance_pdf"> <img alt="" src="${ImgPATH}common/img_pdf.png"></a>
					</td>
				</tr>
				
				<tr>
					<td rowspan='1' class="bdl_n">IL</td> <!-- 단일처방 -->
					<td class="ta_c" colspan='2'>
						<a class="img_btn" href="#none" onclick="showPreView('br/pr/012/doc/co_doc_formula_pdf_print', 'Y', '3', 'Y')">
							<img src="${ImgPATH}common/img_print.png">
						</a>
					</td>						
					<td class="ta_c" colspan='2'>
						<a class="img_btn" href="#none" onclick="download_PDF('S', '3', 'Y', '', 'Y');">
							<img alt="" src="${ImgPATH}common/img_pdf.png">
						</a>
					</td>
				</tr>				
				<tr class="trNone">
					<td class="bdl_n">QQ</td> <!-- 복합처방  -->
					<td class="ta_c" colspan='2'>						
							<table class="sty_03" style="width: 98%; margin: 5px;">
								<tbody>
									<tr>
										<th class="bdl_n">Ko</th>
										<th>Jp</th>
										<th>Ch</th>
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
					<td class="ta_c" colspan='2'>						
						<table class="sty_03" style="width: 98%; margin: 5px;">
							<tbody>
								<tr>
									<th class="bdl_n">Ko</th>
									<th>Jp</th>
									<th>Ch</th>
								</tr>
								<tr>
									<td class="ta_c bdl_n">
										<a class="img_btn" href="#none" onclick="download_PDF('C', '3', 'Y', 'ko');">
											<img alt="" src="${ImgPATH}common/img_pdf.png">
										</a>
									</td>
									<td class="ta_c">
										<a class="img_btn" href="#none" onclick="download_PDF('C', '3', 'Y', 'jp');">
											<img alt="" src="${ImgPATH}common/img_pdf.png">
										</a>
									</td>
									<td class="ta_c">
										<a class="img_btn" href="#none" onclick="download_PDF('C', '3', 'Y', 'cn');">
											<img alt="" src="${ImgPATH}common/img_pdf.png">
										</a>
									</td>
								</tr>
							</tbody>
						</table>						
					</td>
				</tr>
				<tr class="trNone">
					<td class="bdl_n">공정도</td> <!-- 복합처방 공정도 -->					
					<td class="ta_c" colspan='2'>						
						<a class="img_btn" href="#" id="btn_preview_process"> 
							<img src="${ImgPATH}common/img_print.png">
						</a>						
					</td>
					<td class="ta_c" colspan='2'>
						<a class="img_btn" href="#none" onclick="download_PDF('P', '3', '', '');"> 
							<img alt="" src="${ImgPATH}common/img_pdf.png">
						</a>						
					</td>
				</tr>
				<tr>
					<td rowspan='2' class="bdl_n">MSDS</td>
				</tr>
				<tr>
					<td colspan='2' class="ta_c">
						<a href="#none" onclick="showPreView('br/pr/012/doc/co_doc_msds_pdf_print', 'Y', '3')">
							<img src="${ImgPATH}common/img_print.png">
						</a>
					</td>
					<td colspan='2' class="ta_c">
						<a href="#none" onclick="download_PDF('M', '3', 'Y', '');" href="#none"> 
							<img alt="" src="${ImgPATH}common/img_pdf.png">
						</a>
					</td>
				</tr>
				<tr class="trNone">
					<td class="bdl_n">제품표준서</td>
					<td class="last" colspan="4">
						<CmTagLib:cmAttachFile uploadCd="FR016" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="viewLog" />
					</td>
				</tr>
				<c:forEach items="${cmDocList }" var="vo">
					<tr class="commonDoc trNone">
						<td class="td_bold" style="position: relative;">
							${vo.COMM_CD_NM }
						</td>
						<td class="last" colspan='4'>
							<c:choose>
								<c:when test="${vo.COMM_CD eq 'FR021' }">
									<CmTagLib:cmAttachFile uploadCd="PSPEC" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="viewLog" />
								</c:when>
								<c:when test="${vo.COMM_CD eq 'FR022' }">
									<CmTagLib:cmAttachFile uploadCd="MSDS" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="viewLog" />
								</c:when>
								<c:when test="${vo.COMM_CD eq 'FR023' }">
									<CmTagLib:cmAttachFile uploadCd="PSTAB" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo}" formName="frm" type="viewLog" />
								</c:when>
								<c:otherwise>
									<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="viewLog" />
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>		
	
		<ul class="btn_area">
			<li class="right">
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</li>
		</ul>
	</div>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
