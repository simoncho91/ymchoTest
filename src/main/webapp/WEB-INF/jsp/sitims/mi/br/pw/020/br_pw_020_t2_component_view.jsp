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
 		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
 	});

	j$(".li_tab").unbind("click").click(function(event) {
		event.preventDefault();
		
		var frm = j$("form[name='frm']");
		var partno = j$(this).attr("id");
		
		frm.find("input[name='i_sPartNo']").val(partno);
		frm.attr("action", "/br/pw/020/br_pw_020_t2_component_view.do").submit();
	});

	if(j$(".span_allergen_wt").length > 0) {
		var allergen_wt = 0;
		var T2 = Number('1e' + 10);
		j$(".span_allergen_wt").each(function() {
			allergen_wt += parseFloat(j$(this).text()) * 1;
		});
		
		var sum = Math.round(allergen_wt * T2) / T2;
		
		var leaveon_yn = j$("#span_leaveon_yn").text();
		var isBackColor = false;
		
		if(leaveon_yn == "Y" && sum.toFixed(10) < 0.0100000000) { //leave-on
			isBackColor = true;
		} else if(leaveon_yn == "N" && sum.toFixed(10) < 0.0010000000){ // wash-off
			isBackColor = true;
		}
		
		j$("#td_allergen_wt_sum").text(sum.toFixed(10));
		
		if(isBackColor) {
			j$("#td_allergen_wt_sum").css("background-color", "#ff4646");
		}
	}
	addInputChkRadioEvent();
 });

function fnAllergenOpenYn(flag) {
	j$("input[name='i_sAllergenOpen']").val(flag == "OPEN" ? "Y" : "N");
	
	cmAjax({
		url : WebPATH + "glb/doc/glb_doc_allergen_flag_save.do"
		, type : "POST"
		, dataType : "json"
		, data : j$("form[name='frm']").serialize()
		, success : function(data) {
			if(data.status == "succ") {
				showMessageBox({
					message : data.message
					, close : function() {
						var frm = j$("form[name='frm']");
						
						frm.attr("action", WebPATH + "glb/doc/glb_doc_component_view.do").submit();
					}
				});
			} else {
				showMessageBox({
					message : data.message
				});
			}
		}
	});
}
//배합제한
function fn_popGloblim(id,version){
	var param ={
		i_sConCd: id
		, i_nVerSeq : version
//		i_sConCd: '111'
//		, i_nVerSeq : '2'
		, i_sGubun : "L"
	};
	var option = {
		url:"/cm/pop/cm_doc_ban_desc_pop.do?i_sCmFunction=setPopUpData&i_sInput=&param="+encodeURI(JSON.stringify(param))
		, title : "배합제한"
		, width : "500"
		, height : "600"
	}
	fn_pop(option);	 
}
//사용금지
function fn_popGlob(id,version){
	var param ={
		i_sConCd: id
		, i_nVerSeq : version
//		i_sConCd: '111'
//		, i_nVerSeq : '2'
		, i_sGubun : "B"
	};
	var option = {
		url:"/cm/pop/cm_doc_ban_desc_pop.do?i_sCmFunction=setPopUpData&i_sInput=&param="+encodeURI(JSON.stringify(param))
		, title : "사용금지"
		, width : "500"
		, height : "600"
	}
	fn_pop(option);	 
}
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_sPartNo" value="" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />	
	<input type="hidden" name="i_sReceipStatus" value="" />
	<input type="hidden" name="i_sGlbRecordid" value="${rVo.v_refer_record_id}" />	
	<input type="hidden" name="i_sRecipeType" value="${reqVo.i_sRecipeType }" />	
		
	<span class="span_hide" id="span_leaveon_yn">${rVo.v_part_leaveon_yn }</span>
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">	
			<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
		</c:if>
	</div>
	<div class="table_all">
		<div class="pd_top10"></div>		
		<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
		<div class="pd_top10"></div>
		
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 				<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->
		
		<div class="pd_top10"></div>
		<ul class="sty_tab">
			<c:choose>
				<c:when test="${!empty partList }">
					<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab" id="${vo.n_part_no }">
						<a href="#" class='${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }'><span>${vo.v_content_nm }</span></a>
					</li>
					</c:forEach>
				</c:when>
			</c:choose>
		</ul>
	
		<table class="sty_02">
		<colgroup>
			<col width="100px"/>
			<col width="100px"/>
			<col width="15%"/>
			<col width="15%"/>
			<col width="15%"/>
			<col width="100px"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="10%"/>
			<col width="100px"/>
			<col width="100px"/>
			<col width="50px"/>
		</colgroup>
		<thead>
			<tr>
				<th>성분코드</th><!-- 성분코드  -->
				<th>CAS No</th><!-- CAS No  -->
				<th>표시명<br/>(한글)</th><!-- 표시명(한글)  -->
				<th>표시명<br/>(영어)</th><!-- 표시명(영어)  -->
				<th>표시명<br/>(중국어)</th><!-- 표시명(중국어)  -->
				<th>원료함량<br/>(미표기)</th><!-- 함량  -->
				<th>원료함량<br/>(표기)</th><!-- 함량  -->
				<th>FUNCTION</th><!-- 기능  -->
				<th>Allergen <br/>Func.</th><!-- 알러젠 func  -->
				<th>사용금지</th><!-- 글로벌 수출 금지여부  -->
				<th>배합제한</th><!-- 글로벌 수출 제한여부  -->
				<th class="last">알러젠<br/>여부</th>
			</tr>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${!empty ingrtList}">
				<c:forEach items="${ingrtList }" var="vo" varStatus="s">
<%-- 					<tr style="display:${vo.v_allergen_reg_yn eq 'Y' ? 'none' : ''};"> --%>
					<tr style="display:${vo.v_allergen_reg_yn eq '' ? 'none' : ''};">
						<td>${vo.v_con_cd }</td>
						<td>${vo.v_odm_casno }</td>
						<td>${vo.v_con_nm_ko}</td>
						<td>${vo.v_con_nm_en}</td>
						<td>${vo.v_con_nm_cn}</td>
						<td>${vo.v_rcont}</td>
						<td>${vo.v_real_rcont}</td>
						<td>${vo.v_comp_origin_fc}</td>
						<td>${vo.v_allergen_func}</td>				
						<td class="ta_c"><a href="javascript:fn_popGlob('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${vo.v_zglobal }</span></a></td>
						<td class="ta_c"><a href="javascript:fn_popGloblim('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${vo.v_zgllim  }</span></a></td>
						<%-- <td>${vo.v_zglobal}</td>
						<td>${vo.v_zgllim}</td> --%>
						<td class="last">
							<span class="span_rd-style">
								<span class="${vo.v_allergen_reg_yn eq 'Y' ? 'on' : '' }"></span>
							</span>
							<c:if test="${vo.v_allergen_reg_yn eq 'Y' }">
							<span class="span_hide span_allergen_wt">
								${vo.v_rcont}
							</span>
							</c:if>
						</td>
					</tr>
				</c:forEach>			
			</c:when>
			<c:otherwise>
					<tr>
						<td colspan="11" style="padding:30px 0px;" class="last"><strong>:: 등록된 Formula Data가 없습니다 ::</strong></td>
					</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
		</table>
		<div class="pd_top20"></div>
		<div class='sec_cont mt_60'>
			<h2 class="tit">Allergen 성분</h2>
			<div class="pd_top10"></div>
			<table class="sty_02">
				<colgroup>
					<col width="15%"/>
					<col width="35%"/>
					<col width="15%"/>
					<col width="35%"/>
				</colgroup>
				<tbody>
				<tr>
					<th>제품유형</th>
					<td>
						<span class="span_chk-style">
							<span class="${rVo.v_part_leaveon_yn eq 'Y' ? 'on' : '' }"></span>&nbsp;leave-on
						</span>
						<span class="span_chk-style">
							<span class="${rVo.v_part_leaveon_yn eq 'N' ? 'on' : '' }"></span>&nbsp;wash-off
						</span>
					</td>
					<th>알러젠 성분 함량 합계</th>
					<td class="last" id="td_allergen_wt_sum"></td>
				</tr>
				</tbody>
			</table>
			<div class="pd_top10"></div>
			
			<table class="sty_02">
				<colgroup>
					<col width="7%"/>
					<col width="10%"/>
					<col width="7%"/>
					<col width="23%"/>
					<col width="23%"/>
					<col width="15%"/>
					<col width="15%"/>
				</colgroup>
				<thead>
					<tr>
						<th>원료코드</th>
						<th>원료함량</th>
						<th>성분코드</th>
						<th>INCI Name</th>
						<th>Allergen Func.</th>
						<th>구성성분 함량</th>
						<th class="last">실제함량</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty allergenList }">
						<c:forEach items="${allergenList }" var="vo">
							<tr>
							<c:if test="${vo.n_rnum eq '1' }">
								<td rowspan="${vo.n_rowcnt }">
								<c:if test="${(!empty vo.v_flag_oil and vo.v_flag_oil ne 'NOT')}">
									<span class="span_txt_info_type01">에센셜 오일</span>
								</c:if>				
								${vo.v_raw_cd }
								</td>
							</c:if>
							<c:if test="${vo.n_rnum eq '1' }">
								<td rowspan="${vo.n_rowcnt }">${vo.n_cont }</td>
							</c:if>
								<td style="${vo.v_allergen_yn eq 'Y' ? 'background-color: rgb(187, 189, 3)' : ''}">
								<c:if test="${(!empty vo.v_flag_oil and vo.v_flag_oil ne 'NOT') and !empty vo.v_ucon_cd}">
									<span class="span_txt_info_type01">${vo.v_ucon_cd}</span><br/>
								</c:if>			
									${vo.v_con_cd }
								</td>
								<td style="${vo.v_allergen_yn eq 'Y' ? 'background-color: rgb(187, 189, 3)' : ''}">${vo.v_con_nm_en }</td>
								<td style="${vo.v_allergen_yn eq 'Y' ? 'background-color: rgb(187, 189, 3)' : ''}">${vo.v_allergen_func }</td>
								<td style="${vo.v_allergen_yn eq 'Y' ? 'background-color: rgb(187, 189, 3)' : ''}">${vo.n_con_in_per }</td>
								<td class="last" style="${vo.v_allergen_yn eq 'Y' ? 'background-color: rgb(187, 189, 3)' : ''}">${vo.v_actual_wt }</td>
							</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="last" colspan="7">:: Allergen 성분이 없습니다. ::</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		
		<div class="pd_top10"></div>
		<div class="pd_top20"></div>
				
		<div class='sec_cont mt_60'>
			<h2 class="tit">국문</h2>
		
			<table class="sty_03">
			<tbody>
				<tr>
					<td class="last">${cfn:removeHTMLChangeBr(IngrtMemoVo.v_matrmemo) }</td>
				</tr>
			</tbody>
			</table>
		
			<div class="pd_top10"></div>
			<h2 class="tit">영문</h2>			
			<table class="sty_03">
			<tbody>
				<tr>
					<td class="last">${cfn:removeHTMLChangeBr(IngrtMemoVo.v_matrmemo_en) }</td>
				</tr>
			</tbody>
			</table>
	<!-- 		<div style="padding-top: 10px;"> -->
	<!-- 			<p style="color:red; margin-bottom: 5px;">상기 영문 전성분표에는 향 알러젠이 반영되지 않습니다. EU 전성분표만 알러젠 성분 비노출/노출 버튼에 따라 전성분표에 반영됨.</p> -->
	<!-- 			<p style="color:red;">협력업체에서 잘못 입력하여 알러젠이 노출이 된 경우 수기로 전성분표를 다시 받으시기 바랍니다.</p> -->
	<!-- 		</div> -->
			
			<div class="pd_top10"></div>
			<h2 class="tit">중국어</h2>
			
			<table class="sty_03">
			<tbody>
				<tr>
					<td class="last">${cfn:removeHTMLChangeBr(IngrtMemoVo.v_matrmemo_cn) }</td>
				</tr>
			</tbody>
			</table>
	<!-- 		<div style="padding-top: 10px;"> -->
	<!-- 			<p style="color:red; margin-bottom: 5px;">상기 중문 전성분표에는 향 알러젠이 반영되지 않습니다. EU 전성분표만 알러젠 성분 비노출/노출 버튼에 따라 전성분표에 반영됨.</p> -->
	<!-- 			<p style="color:red;">협력업체에서 잘못 입력하여 알러젠이 노출이 된 경우 수기로 전성분표를 다시 받으시기 바랍니다.</p> -->
	<!-- 		</div> -->
			
	<!-- 		<div class="pd_top10"></div> -->
	<!-- 		<div class="subtitle">캐나다(불어 병행표기)</div> -->
			
	<!-- 		<table class="table_view"> -->
	<!-- 		<tbody> -->
	<!-- 			<tr> -->
	<%-- 				<td class="last">${cfn:removeHTMLChangeBr(IngrtMemoVo.v_matrmemo_fr) }</td> --%>
	<!-- 			</tr> -->
	<!-- 		</tbody> -->
	<!-- 		</table> -->
	<!-- 		<div style="padding-top: 10px;"> -->
	<!-- 			<p style="color:red; margin-bottom: 5px;">상기 영문 전성분표에는 향 알러젠이 반영되지 않습니다. EU 전성분표만 알러젠 성분 비노출/노출 버튼에 따라 전성분표에 반영됨.</p> -->
	<!-- 			<p style="color:red;">협력업체에서 잘못 입력하여 알러젠이 노출이 된 경우 수기로 전성분표를 다시 받으시기 바랍니다.</p> -->
	<!-- 		</div> -->
			
			<div class="pd_top10"></div>
			<h2 class="tit">EU<h2>
			
			<table class="sty_03">
			<tbody>
				<tr>
					<td class="last">${cfn:removeHTMLChangeBr(IngrtMemoVo.v_matrmemo_eu) }</td>
				</tr>
			</tbody>
			</table>
<!-- 		<div style="padding-top: 10px;"> -->
<!-- 			<p style="color:red; margin-bottom: 5px;">EU 전성분표만 알러젠 성분 비노출/노출 버튼에 따라 전성분표에 반영됨.</p> -->
<!-- 			<p style="color:red;">협력업체에서 잘못 입력하여 알러젠 비노출하기 버튼을 눌러도 노출이 되는 경우 수기로 전성분표를 다시 받으시기 바랍니다.</p> -->
<!-- 		</div> -->
		</div>
		
		<ul class="btn_area">
			<li class="right">
				<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
					<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
				</c:if>
			</li>
		</ul>
	</div>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
