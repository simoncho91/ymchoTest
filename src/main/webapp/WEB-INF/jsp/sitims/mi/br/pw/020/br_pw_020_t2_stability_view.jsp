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
	j$(".li_tab_part1").unbind("click").click(function(event) {
		event.preventDefault();
		
		var frm = j$("form[name='frm']");
		var partno = j$(this).attr("id");
		
		frm.find("input[name='i_sPartNo']").val(partno);
		frm.attr("action", "/br/pw/020/br_pw_020_t2_stability_view.do").submit();
	});
	_w_table_rowspan('.grid_table', 1);
	
 });

function _w_table_rowspan(_w_table_id,_w_table_colnum){
	_w_table_firsttd = "";
	_w_table_currenttd = "";
	_w_table_SpanNum = 0;
	_w_table_Obj = jQuery(_w_table_id + " tr td:nth-child(" + _w_table_colnum + ")");
	_w_table_Obj.each(function(i){
		if(i==0){
			_w_table_firsttd = jQuery(this);
			_w_table_SpanNum = 1;
		}else{
			_w_table_currenttd = jQuery(this);
			if(_w_table_firsttd.text()==_w_table_currenttd.text()){
				_w_table_SpanNum++;
				_w_table_currenttd.hide(); //remove();
				_w_table_firsttd.attr("rowSpan",_w_table_SpanNum);
			}else{
				_w_table_firsttd = jQuery(this);
				_w_table_SpanNum = 1;
			}
		}
	});
}

</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_sPartNo" value="" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />		
    <input type="hidden" name="i_sGlbRecordid" value="${reqVo.i_sGlbRecordid }"/>
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
		</c:if>
	</div>
		<div class="pd_top10"></div>		
		<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
		<div class="pd_top10"></div>
				
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 				<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->

	<ul class="sty_tab ul_tab_part1">
		<c:forEach items="${partList }" var="vo" varStatus="s">
			<li class="tab li_tab_part1" id="${vo.n_part_no }">
				<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
			</li>
		</c:forEach>
	</ul>

    <div class="table_all">
		<div class="pd_top10"></div>
		<c:choose>
			<c:when test="${!empty prodInfo }">
				<table class="sty_03">
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tbody>
					<tr>
						<th>Product Name</th>
						<td>${prodInfo.v_product_nm_en }</td>
						<th>Category</th>
						<td>
							<span>${prodInfo.v_category1_nm }</span>
							<br />
							<span>${prodInfo.v_category2_nm }</span>
						</td>
					</tr>
					<tr>
						<th>Product Code</th>
						<td>${prodInfo.v_product_cd }</td>
						<th>Test Date</th>
						<td class="last">
							<span>${cfn:getStrDateToString(prodInfo.v_test_date,'yyyy-MM-dd')}</span>
						</td>
					</tr>
					</tbody>
				</table>

	<div class="pd_top20"></div>
	<c:forEach items="${sCondition}" var="vo" varStatus="status">
	<table class="sty_03 grid_table" style="text-align: center">
		<colgroup>
			<col width="20%">
			<col width="20%">
			<c:choose>
				<c:when test="${vo.BUFFER1 eq 'month' }">
					<col width="15%">
					<col width="15%">
					<col width="15%">
					<col width="15%">
				</c:when>
				<c:otherwise>
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
					<col width="12%">
				</c:otherwise>
			</c:choose>
			
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2" class="ta_c">Storage of condition</th>
				<th rowspan="2" class="ta_c">Type of Analysis</th>
				<th colspan="5" class="ta_c">Observation Time</th>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${vo.BUFFER1 eq 'month' }">
						<th class="ta_c">0 month</th>
						<th class="ta_c">4 month</th>
						<th class="ta_c">5 month</th>
						<th class="ta_c">6 month</th>
					</c:when>
					<c:otherwise>
						<th class="ta_c">1 week</th>
						<th class="ta_c">2 week</th>
						<th class="ta_c">4 week</th>
						<th class="ta_c">8 week</th>
						<th class="ta_c">12 week</th>
					</c:otherwise>
				</c:choose>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${sAnalysis}" var="rvo" varStatus="status">
				<c:if test="${vo.BUFFER1 eq rvo.BUFFER1 }">
				<tr>
					<td class="bdl_n"><input type="hidden" name="i_arrCondition" value="${vo.COMM_CD }">${vo.COMM_CD_NM }</td>
					<td><input type="hidden" name="i_arrAnalysis" value="${rvo.COMM_CD }">${rvo.COMM_CD_NM }</td>
						<c:forEach items="${list}" var="tvo" varStatus="status">

							<c:if test="${tvo.v_anal_cd eq rvo.COMM_CD && tvo.v_condi_cd eq vo.COMM_CD }">
							<c:set var="cssClass" />
							<c:choose>
								<c:when test="${rvo.BUFFER2 eq 'calendar' }">
									<c:set var="cssClass" value="calendar" />
								</c:when>
								<c:otherwise>
									<c:set var="cssClass" value="inp_sty01" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${rvo.BUFFER1 eq 'month' }">
									<td>${rvo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_1w,'yyyy-MM-dd') : tvo.v_observa_1w}</td>
									<td>${rvo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_2w,'yyyy-MM-dd') : tvo.v_observa_2w}</td>
									<td>${rvo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_4w,'yyyy-MM-dd') : tvo.v_observa_4w}</td>
									<td class="noline">
										${rvo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_8w,'yyyy-MM-dd') : tvo.v_observa_8w}
										<input type="hidden" name="i_arrWeek12" value="${rvo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_12w,'yyyy-MM-dd') : tvo.v_observa_12w}" class="${cssClass}">
									</td>
								</c:when>
								<c:otherwise>
									<td>${tvo.v_observa_1w }</td>
									<td>${tvo.v_observa_2w }</td>
									<td>${tvo.v_observa_4w }</td>
									<td>${tvo.v_observa_8w }</td>
									<td class="noline">
										<c:choose>
											<c:when test="${tvo.v_observa_12w == ''|| tvo.v_observa_12w == null}">
												
											</c:when>
											<c:otherwise>
												${tvo.v_observa_12w }
											</c:otherwise>
										</c:choose>
									</td>
								</c:otherwise>
							</c:choose>
							</c:if>
						</c:forEach>
				</tr>	
				</c:if>
			</c:forEach>
		</tbody>
	</table>
	<div class="pd_top10"></div>
	</c:forEach>


				<div class="pd_top10"></div>
				
				<table class="sty_03">
				<colgroup>
					<col width="15%"/>
					<col width="85%"/>
				</colgroup>
				<tbody>
				<tr>
					<th>첨부파일</th>
					<td class="last">
						<div class="pd_top5"></div>
						<CmTagLib:cmAttachFile uploadCd="PSTAB" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="view"/>
					</td>
				</tr>
				</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<span class="span_notice">
					<strong>* 안정성 정보가 작성되지 않았습니다.</strong>
				</span>
			</c:otherwise>
		</c:choose>		
	</div>
	
				
	<ul class="btn_area">
		<li class="right">
			<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
				<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
			</c:if>
		</li>
	</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
