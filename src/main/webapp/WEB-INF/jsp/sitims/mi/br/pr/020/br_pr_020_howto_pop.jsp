<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<script type="text/javascript">
$(function(){
	//용량용법 선택 완료
	$("#select_btn").unbind("click").click(function(e){
		e.preventDefault();
		var data = $(".result").text();
		parent.fn_selectHowtoMethod_CallBack(data);
		fn_popClose();
	});
	
	//용량용법 팝업창 닫기
	$("#close_btn").unbind("click").click(function(e){
		e.preventDefault();
		fn_popClose();
	});
	
	//용량용법 선택 셋팅
	$(".selectBoard table tr").unbind("click").click(function(e){
		e.preventDefault();
		var selectText = $(this).find('td').eq(2).text();
		$(".result").text(selectText);
	});
});
</script>

<form name="frm" method="post">
	<input type="hidden" name="vReturnUrl" 	value="${reqVo.v_page_url}"/>
	<div class="content selectBoard" style="overflow-y:auto;height:370px;">
		<table class="sty_03" style="border-collapse:inherit;">
			<colgroup>
				<col width="9%">
				<col width="31%">
				<col width="60%">
			</colgroup>
			<tbody>
				<tr>
					<th style="position:sticky;top:0px;z-index:1;padding-left:0;text-align:center;"><span>순번</span></th>
					<th style="position:sticky;top:0px;z-index:1;padding-left:0;text-align:center;"><span>분류명</span></th>
					<th style="position:sticky;top:0px;z-index:1;padding-left:0;text-align:center;"><span>용법용량</span></th>
				</tr>
			<c:forEach items="${popupList}" var="vo">
				<tr>
					<td style="text-align:center;"><a>${vo.ROW_NUM}</a></td>
					<td><a>${vo.CATEGORY_NM }</a></td>
					<td><a>${vo.HOWTO_METHOD }</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="content">
		<table class="sty_03" style="border-collapse:inherit;">
			<colgroup>
				<col width="20%">
				<col width="80%">
			</colgroup>
			<tbody>
				<tr>
					<th style="text-align:left;"><span>용법용량</span></th>
					<td class="result"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="btn_area verR">
		<a class="btnA bg_dark" id="select_btn">선택</a>
		<a class="btnA bg_dark" id="close_btn">닫기</a>
	</div>
</form>