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
			frm.attr("action", "/br/pw/020/br_pw_020_t2_spec_view.do").submit();
		});
 });
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>	
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_sPartNo" value="" />	
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
    
<!-- 	<ul class="btn_area"> -->
<!-- 		<li class="right"> -->
<%-- 			<c:if test="${GlbExp.v_report_status eq 'GRS007' }"> --%>
<!-- 				<a href="#" class="btnA bg_dark" id="btn_save"><span>저장</span></a> -->
<%-- 			</c:if> --%>
<!-- 			<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a> -->
<!-- 		</li> -->
<!-- 	</ul> -->
	<c:forEach items="${sectionList }" var="vo" varStatus="s">
	<div class="sec_cont mt_60">
		<h2 class="tit">${vo.v_class_nm }</h2>
		<div class="pd_top10"></div>
		<c:if test="${vo.v_class_cd eq 'S000033'}"><br><div style="margin-left: 1%;font-weight: bold;color: gray;"> -This is a personal care product and it is safe if consumed as intended and reasonably foreseeable use.</div></c:if>
		<c:if test="${vo.v_type ne 'R'}">
			<div id="input_${s.index}">
			<table class="sty_02">
				<tbody>
					<tr>
						<td class="ta_l">
							<input type="hidden" name="i_arrClassCd" value="${vo.v_class_cd }">
							<textarea rows="3" style="width: 100%;" name="i_arrMsdsVal" class='textarea_sty01' readOnly>${vo.v_msds_val}</textarea>
							<c:if test="${vo.v_class_cd eq 'S000007' and !empty rVo.v_fill_gas}">
								<p>Propellant gas : <input type="text" name="i_sFillGas" value="${rVo.v_fill_gas }" readOnly></p>
								<%-- <p>Propellant gas : ${rVo.v_fill_gas }</p><br/> --%>
							</c:if>
							<c:if test="${vo.v_class_cd eq 'S000027' and !empty  rVo.v_flash_point}"><!-- SECTION 9 -->
								<%-- <p>Flash point : ${rVo.v_flash_point }</p> --%>
								<p>Flash point : <input type="text" name="i_sFlashPoint" value="${rVo.v_flash_point}" readOnly></p>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</c:if>
		<c:if test="${vo.v_type eq 'R'}">
			<div id="input_${s.index}">
				<table class="sty_02">
					<colgroup>
						<col width="300px;">
						<col width=";">
					</colgroup>
					<tbody>
						<c:forEach items="${sectionList_sub }" var="svo" varStatus="ss">
							<c:if test="${svo.v_uclass_cd eq vo.v_class_cd }">
								<c:if test="${svo.v_type eq 'W'}">
								<tr>
									<th class="ta_c">${svo.v_class_nm }</th>
									<td>
										<input type="hidden" name="i_arrClassCd" value="${svo.v_class_cd }">
										<textarea rows="3" style="width: 100%;" name="i_arrMsdsVal" class='textarea_sty01' readOnly>${svo.v_msds_val}</textarea>
									</td>
								</tr>
								</c:if>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		</div>
		<div class="pd_top10"></div>
	</c:forEach>
	<div class="pd_top10"></div>
		
    
    <div class="pd_top10"></div>
        
    <table class="sty_02">
	    <colgroup>
	        <col width="15%"/>
	        <col width="85%"/>
	    </colgroup>
	    <tbody>
	        <tr>
	            <th class="ta_c">첨부파일</th>
	            <td class="last">
	                <CmTagLib:cmAttachFile uploadCd="MSDS" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" formName="frm" type="view"/>          
	            </td>
	        </tr>
	    </tbody>
    </table>
	    
	<ul class="btn_area">
		<li class="right">
			<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
				<a href="#" class="btnA bg_dark btn_list" id="btn_list"><span>목록</span></a>
			</c:if>
		</li>
	</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
