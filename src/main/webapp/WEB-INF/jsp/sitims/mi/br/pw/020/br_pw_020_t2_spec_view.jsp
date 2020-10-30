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
			var partno = j$(this).find('a').parent().attr('id');
			
			frm.find("input[name='i_sPartNo']").val(partno);
			frm.attr("action", "/br/pw/020/br_pw_020_t2_spec_view.do").submit();
		});
		j$(".li_flagImp").unbind("click").click(function(event) {
			$('.li_flagImp a').removeClass('on');
			$(this).find('a').addClass('on');
			var val = $(this).find('a').attr('value');
			if(val == 'S'){
				$('.trSpecFlag_S').show();
				$('.trSpecFlag_F').hide();				
			}else{
				$('.trSpecFlag_S').hide();
				$('.trSpecFlag_F').show();				
			}
		});
		
 });
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>	
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo}" />	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />		
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>
	<div class="pd_top10"></div>		
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
	<div class="pd_top10"></div>
	
<!--    	<ul class="btn_area"> -->
<!-- 		<li class="right"> -->
<!-- 			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a> -->
<!-- 		</li> -->
<!-- 	</ul>	 -->
	<div class="sec_cont">
	    <ul class="sty_tab">
			<c:forEach items="${partList }" var="vo" varStatus="s">
				<li class="tab li_tab" id="${vo.n_part_no }">
					<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
				</li>
			</c:forEach>
<%-- 		    <c:forEach begin="1" end="${partNoMax }" var="cnt" step="1"> --%>
<%-- 			     <li class="tab li_tab" id="${cnt }"> --%>
<%-- 			         <a href="#none" class="${reqVo.i_sPartNo eq cnt? 'on':''}"><span>${cnt}</span></a> --%>
<!-- 			     </li> -->
<%-- 		    </c:forEach> --%>
	    </ul>
		
	    <%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/cm/br_pw_cm_spec.jsp" %>
    </div>
   	<ul class="btn_area">
		<li class="right">
			<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</c:if>
		</li>
	</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
