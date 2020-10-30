<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input name="i_iVerSeq" value="${reqVo.i_iVerSeq }" type="hidden">
	<input name="i_sPartno" value="${reqVo.i_sPartno }" type="hidden">

<c:choose>
	<c:when test="${!empty ingrtMemoList }">
		<ul class="sty_tab ul_tab">
			<c:forEach items="${ingrtMemoList }" var="vo" varStatus="s">
				<li class="tab li_tab">
					<a href="#" class="a_ver_tab ${reqVo.i_iVerSeq eq vo.n_ver_seq ? 'on' : '' }" data-verno = "${vo.n_ver_seq}"><span>Ver ${vo.n_num}</span></a>
				</li>
			</c:forEach>
		</ul>
		<c:forEach items="${ingrtMemoList }" var="vo" varStatus="s">
			<div class="div_ingred_memo div_ingred_memo_${vo.n_ver_seq }" style="display:${reqVo.i_iVerSeq eq vo.n_ver_seq ? 'block':'none'};" >
				<c:choose>
					<c:when test="${not empty vo.v_matrmemo }">
						<c:set var="IngrtTempRecordid" value="${vo.v_recordid }_${vo.n_ver_seq }"/>
						<c:forEach items="${ingrtMemoHisrotyList }" var="hVo" varStatus="hS">
							<c:if test="${hVo.v_recordid eq IngrtTempRecordid }">
								<p><a href="#" id="${hVo.v_recordid }_${hVo.v_reg_dtm}" class="a_ingert_ver_popup" style="cursor: pointer;"><span style="font-weight: bold; font-size: 15px;">${cfn:cmDatePatten(hVo.v_reg_dtm) } 제품유형 변경</span></a></p>
							</c:if>
						</c:forEach>
						<div class="pd_top10"></div>
						
						<div class="subtitle">국문(향료 알러젠 표기)</div>
						<table class="table_view">
							<tbody>
								<tr>
									<td class="last">${cfn:removeHTMLChangeBr(vo.v_matrmemo_al) }</td>
								</tr>
							</tbody>
						</table>
						
						<div class="pd_top10"></div>
						<div class="subtitle">영문(향료 알러젠 표기)</div>
						<table class="table_view">
							<tbody>
								<tr>
									<td class="last">${cfn:removeHTMLChangeBr(vo.v_matrmemo_en_al) }</td>
								</tr>
							</tbody>
						</table>
						
						<div class="pd_top10"></div>
						<div class="subtitle">중국어(향료 알러젠 표기)</div>
						<table class="table_view">
							<tbody>
								<tr>
									<td class="last">${cfn:removeHTMLChangeBr(vo.v_matrmemo_cn_al) }</td>
								</tr>
							</tbody>
						</table>
						
<!-- 						<div class="pd_top20"></div> -->
												
<!-- 						<div class="subtitle">국문(향료 알러젠 미표기)</div> -->
<!-- 						<table class="sty_02 table_matrcd"> -->
<!-- 							<tbody> -->
<!-- 								<tr> -->
<%-- 									<td class="last">${cfn:removeHTMLChangeBr(vo.v_matrmemo) }</td> --%>
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table> -->
						
<!-- 						<div class="pd_top10"></div> -->
<!-- 						<div class="subtitle">영문(향료 알러젠 미표기)</div> -->
<!-- 						<table class="sty_02 table_matrcd"> -->
<!-- 							<tbody> -->
<!-- 								<tr> -->
<%-- 									<td class="last">${cfn:removeHTMLChangeBr(vo.v_matrmemo_en) }</td> --%>
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table> -->
						
<!-- 						<div class="pd_top10"></div> -->
<!-- 						<div class="subtitle">중국어(향료 알러젠 미표기)</div> -->
<!-- 						<table class="sty_02 table_matrcd"> -->
<!-- 							<tbody> -->
<!-- 								<tr> -->
<%-- 									<td class="last">${cfn:removeHTMLChangeBr(vo.v_matrmemo_cn) }</td> --%>
<!-- 								</tr> -->
<!-- 							</tbody> -->
<!-- 						</table> -->
					</c:when>
					<c:otherwise>
						<div class="pd_top10"></div>
						<span style="font-size: 18px; font-weight: bold;">전성분표 미작성</span>
					</c:otherwise>
				</c:choose>
			</div>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p style="text-align: center;">전성분표가 없습니다</p>
	</c:otherwise>
</c:choose>
</form>
