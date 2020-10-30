<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>

<!-- 기능성 기재사항 s -->
<h2 class="tit">기능성 보고서 데이터</h2>
	
	<!-- 기능성 보고서 데이터 -->
	<table class="sty_03">
		<colgroup>
			<col width="15%">
			<col width="20%">
			<col width="15%">
			<col width="50%">
		</colgroup>
		<tbody>
			<!-- 기능성 보고서 1,2,3호 공통정보 s(1)-->
			<tr>
				<th rowspan="4">제조원</th>
				<th>제조회사</th>
				<td colspan="2" id="i_sVendorNmText"></td>
			</tr>
			<tr>
				<th>제조국</th>
				<td colspan="2" id="i_sNationalText"></td>
			</tr>
			<tr>
				<th>소재지</th>
				<td colspan="2" id="i_sAddrText"></td>
			</tr>
			<tr>
				<th>판매사</th>
				<td colspan="2">(주)신세계 인터네셔널</td>
			</tr>
			<tr>
				<th rowspan="4">보고정보</th>
				<th>제품명</th>
				<td colspan="2" id="i_sProductNm"></td>
			</tr>
			<tr>
				<th colspan="1">제품 PH 기준치</th>
				<td colspan="2" id="i_sPh">
				</td>
			</tr>
			<tr>
				<th colspan="1">랩넘버</th>
				<td colspan="2" id="i_sLabNum"></td>
			</tr>
			<tr>
				<th>대상구분</th>
				<td colspan="2">제10조 제1항 제<span class="i_sReportNumText"></span>호</td>
			</tr>
			<!-- 기능성 보고서 1,2,3호 공통정보 e(1)-->
			
			<!-- 보고서 호수별 개별정보 s -->
			<!-- *추후 데이터 변경 여부가 있는 경우를 가정하여 어느정도의 중복데이터들도 보고서별로 분류 -->
<%-- 					<c:choose> --%>
<%-- 						<c:when test="${rvo.n_func_no eq '1'}">	<!-- 보고서1호 --> --%>
						<tr class="nFuncNoError" style="display:none">
							<th rowspan="18" style="font-weight: bold;">데이터 오류<br>(기능성 여부(V_FUNC_YN)가  Y인 경우, 기능성 테이블에 데이터 필요)</th>
							<th></th>
							<td colspan="2">KFCC</td>
						</tr>
						<tr class="nFuncNo1" style="display:none">
							<th rowspan="18">제10조<br>제1항<br>제<span class="i_sReportNumText"></span>호</th>
							<th>효능효과를 나타내게 하는 원료의 규격</th>
							<td colspan="2">KFCC</td>
						</tr>
						<tr class="nFuncNo1" style="display:none">
							<th>고시한 기준 및 시험방법</th>
							<td colspan="2" id="i_sStandTest" class="i_sStandTest"></td>
						</tr>
						<tr class="nFuncNo1" style="display:none">
							<th>효능효과</th>
							<td colspan="2" id="i_sEffect" class="i_sEffect">
							</td>
						</tr>
						<tr class="nFuncNo1" style="display:none">
							<th>용법용량</th>
							<td colspan="2" id="i_sHowToMethod" class="i_sHowToMethod"></td>
						</tr>
<%-- 						</c:when> --%>
<%-- 						<c:when test="${rvo.n_func_no eq '2'}">	<!-- 보고서2호 --> --%>
						<tr class="nFuncNo2" style="display:none">
							<th rowspan="18">제10조<br>제1항<br>제<span class="i_sReportNumText"></span>호</th>
							<th rowspan="3">이미 심사받은 목록</th>
							<th>제품명</th>
							<td id="i_sBfProductNm" class="i_sBfProductNm"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>심사번호</th>
							<td id="i_sCertiNo" class="i_sCertiNo"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>이미 심사받은 품목의 결과 통지일</th>
							<td id="i_sCertDtm" class="i_sCertDtm"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>활성물질용량</th>
							<td colspan="2" id="i_sEffectMatNm" class="i_sEffectMatNm"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>효능효과</th>
							<td colspan="2" id="i_sEffect" class="i_sEffect"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>자외선차단지수(SPF)</th>
							<td colspan="2" id="i_sSpfLv" class="i_sSpfLv"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>자외선차단등급(PA)</th>
							<td colspan="2" id="i_sPaLv" class="i_sPaLv"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>내수성</th>
							<td colspan="2" id="i_sWaterProof" class="i_sWaterProof"></td>
						</tr>
						<tr class="nFuncNo2" style="display:none">
							<th>용법용량</th>
							<td colspan="2" id="i_sHowToMethod" class="i_sHowToMethod"></td>
						</tr>
<%-- 						</c:when> --%>
<%-- 						<c:when test="${rvo.n_func_no eq '3'}">	<!-- 보고서3호 --> --%>
						<tr class="nFuncNo3" style="display:none">
							<th rowspan="19">제10조<br>제1항<br>제<span class="i_sReportNumText"></span>호</th>
							<th>효능효과를 나타내게하는 원료의 규격</th>
							<td colspan="2">KFCC</td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th rowspan="3">이미 심사받은 목록</th>
							<th>제품명</th>
							<td id="i_sBfProductNm" class="i_sBfProductNm"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>심사번호</th>
							<td id="i_sCertiNo" class="i_sCertiNo"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>이미 심사받은 품목의 결과 통지일</th>
							<td id="i_sCertDtm" class="i_sCertDtm"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>고시한 기준 및 시험방법</th>
							<td colspan="2" id="i_sStandTest" class="i_sStandTest"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>활성물질용량</th>
							<td colspan="2" id="i_sEffectMatNm" class="i_sEffectMatNm"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>효능효과</th>
							<td colspan="2" id="i_sEffect" class="i_sEffect"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>자외선차단지수(SPF)</th>
							<td colspan="2" id="i_sSpfLv" class="i_sSpfLv"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>자외선차단등급(PA)</th>
							<td colspan="2" id="i_sPaLv" class="i_sPaLv"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>내수성</th>
							<td colspan="2" id="i_sWaterProof" class="i_sWaterProof"></td>
						</tr>
						<tr class="nFuncNo3" style="display:none">
							<th>용법용량</th>
							<td colspan="2" id="i_sHowToMethod" class="i_sHowToMethod"></td>
						</tr>
<%-- 						</c:when> --%>
<%-- 					</c:choose> --%>
			<!-- 보고서 호수별 개별정보 e -->

			<!-- 기능성 보고서 1,2,3호 공통정보 s(2)-->
			<tr>
				<th rowspan="3">사용시 주의 사항</th>
				<td colspan="2" id="i_sCautionText"></td>
			</tr>
			<tr>
				<th>유형별 성분</th>
				<td colspan="1" id="i_sFuncType"></td>
			</tr>
			<tr>
				<th>성분별 구분</th>
				<td colspan="1" id="i_sFuncIngri"></td>
			</tr>
			<tr>
				<th rowspan="2">총량관리</th>
				<th>전체단위</th>
				<th>세부구성</th>
			</tr>
			<tr>
				<td>본품 100g 중</td>
				<td id="i_sFuncMat"></td>
			</tr>
			<tr id="i_sFuncMatList">
				<th id="i_sFuncMatListTh" rowspan="3">원료성분 및 배합 비율</th>
				<th>원료</th>
				<th>분량/단위</th>
			</tr>
			<tr>
				<th>전성분</th>
				<td id="i_sAllMatr" colspan="3"></td>
			</tr>
			<tr>
				<th><span>첨부파일</span></th>
				<td colspan="4">
					<CmTagLib:cmAttachFile uploadCd="FUNC" type="viewLog" recordId="${rvo.v_product_cd }" formName="frm"/>
				</td>
			</tr>
			<!-- 기능성 보고서 1,2,3호 공통정보 e(2)-->
		</tbody>
	</table>
	