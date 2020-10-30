<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<script type="text/javascript">
j$(document).ready(function(){
    j$("#close_btn").unbind("click").click(function(event){
        event.preventDefault();
        fn_popClose();
    });
});
</script>

<form name="frm" id="frm" action="" method="post" >
	<div class="pd_top10"></div>
	<div class="subtitle">국문</div>
	
	<table class="table_view">
	<tbody>
		<tr>
			<td class="last">${cfn:removeHTMLChangeBr(ingredient.v_matrmemo) }</td>
		</tr>
	</tbody>
	</table>
	
	<div class="pd_top10"></div>
	<div class="subtitle">영문</div>
	
	<table class="table_view">
	<tbody>
		<tr>
			<td class="last">${cfn:removeHTMLChangeBr(ingredient.v_matrmemo_en) }</td>
		</tr>
	</tbody>
	</table>
	
	<div class="pd_top10"></div>
	<div class="subtitle">중국어</div>
	
	<table class="table_view">
	<tbody>
		<tr>
			<td class="last">${cfn:removeHTMLChangeBr(ingredient.v_matrmemo_cn) }</td>
		</tr>
	</tbody>
	</table>
	
	<div class="pd_top10"></div>
	<ul class="btn_area">
	    <li class="right">
	        <a class="btnA bg_dark" href="#none" id="close_btn"><span>닫기</span></a>
	    </li>
	</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>