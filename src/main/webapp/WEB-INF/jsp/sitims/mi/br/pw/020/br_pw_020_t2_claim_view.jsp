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
			
			frm.find("input[name='i_sClaimPartNo']").val(partno);
			frm.attr("action", "/br/pw/020/br_pw_020_t2_claim_view.do").submit();
		});
 });
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_sPartNo" value="" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />		
	<input type="hidden" name="i_sClaimPartNo"	value="${reqVo.i_sClaimPartNo }"/>
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
<%-- 				<c:if test="${GlbExp.v_report_status eq 'GRS007' }"> --%>
<!-- 					<a href="#" class="btnA bg_dark" id="btn_save"><span>저장</span></a> -->
<%-- 				</c:if> --%>
<!-- 				<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->
	
		<ul class="sty_tab">
			<c:choose>
				<c:when test="${!empty partList }">
					<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab" id="${vo.n_part_no }">
						<a href="#" class="${reqVo.i_sClaimPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
					</li>
					</c:forEach>
				</c:when>
			</c:choose>
		</ul>
		
		<div class="pd_top10"></div>
		
		<table class="sty_02">
			<colgroup>
				<col width="*"/>
				<col width="200px"/>
				<col width="200px"/>
				<col width="200px"/>
			</colgroup>
			<thead>
			<tr>
				<th class='ta_c'>Active Ingredient name</th>
		     	<th class='ta_c'>Active Ing. %</th>
		     	<th class='ta_c'>WT%</th>
		     	<th class="ta_c">Effect</th>
			</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty claimList }">
						<c:forEach items="${claimList }" var="vo" varStatus="s">
							<tr>
								<td>${vo.v_con_nm_en }</td>
								<td>${vo.v_claim_active_ing }</td>
								<td>${vo.v_rcont }</td>
								<td class="last">${vo.v_claim_txt }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
							<tr class="no-data">
								<td class="ta_c" colspan="4">:: 해당하는/등록된 내용이 없습니다. ::</td>
							</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		
		<div class="pd_top20"></div>		
		<table class="sty_02">
			<colgroup>
				<col width="150px;"/>
				<col width=";"/>
				<col width="150px;"/>
			</colgroup>
			<thead>
			<tr>
				<th class="ta_c">Claims of product</th>
				<th class="ta_c">Support</th>
				<th class="ta_c">Clinical Report No.</th>
			</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty supportList }">
						<c:forEach items="${supportList }" var="vo" varStatus="s">
							<tr>
								<td>
									${vo.v_claim_txt }
								</td>
								<td>
									<textarea name="i_arrClaimSupport" rows="4" style="width:98%">${cfn:removeHTMLChangeBr(vo.v_claim_support) }</textarea>
									<input type="hidden" name="i_arrClaimEffect" value="${vo.v_claim_effect }">
								</td>
								<td class="last">
							<c:choose>
								<c:when test="${vo.v_claim_effect eq 'ETC' }">
									${vo.v_claim_report_no }
								</c:when>
								<c:otherwise>
									<c:forEach items="${reportList }" var="repVo">
										<c:if test="${repVo.v_claim_effect eq vo.v_claim_effect }">
											${repVo.v_claim_reportno }
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
							<tr class="no-data"><td class="ta_c" colspan="3">:: 해당하는/등록된 내용이 없습니다. ::</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		
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
