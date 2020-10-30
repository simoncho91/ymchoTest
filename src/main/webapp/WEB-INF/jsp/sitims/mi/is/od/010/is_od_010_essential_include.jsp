<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- ######################################################################################################## --%>
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [S] '상세보기'인 경우 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
<%-- ######################################################################################################## --%>
		<c:choose>
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [S] 향코드 여부가 'Y'인 경우 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
			<c:when test="${vo.v_flag_flavor eq 'Y'}">
				<c:if test="${!empty allergenMap}">
					<c:forEach items="${ingredList }" var="list" varStatus="s">	
						<c:set var="key" value="${list.v_key}"/>
						<c:choose>
							<c:when test="${!empty allergenMap[key]}">
	<tr id="allergen_tr${s.index eq 0 ? '' : '_'}${s.index eq 0 ? '' : s.index}">
		<th>
			<span class="span_allergen_txt" style="color:blue;">[${list.v_con_cd}]</span>
			성분 100%에 함유된 향알러젠 함량 입력
		</th>
		<td colspan="7">
			<div class="board-area inner_table taC ">
				<table class="aller_table sty_02">
					<colgroup>
		               <col style="width:14%">
	                   <col style="width:14%">
	                   <col style="width:12%">
	                   <col style="width:12%">
	                   <col style="width:7%">
	                   <col style="width:7%">
	                   <col style="width:10%">
	                   <col style="width:14%">
	                   <!-- Carry over 여부 -->
	                   <!-- RA202007 // carry over 여부 컬럼 -->
<!-- 	                   <col style="width:6%"> -->
	                   <col style="width:4%">
		            </colgroup>
		            <thead>
	                    <tr>
	                        <th class="bdl_n">성분코드</th>
	                        <th><span>성분명(영문)</th>
	                        <th>성분명</th>
	                        <th>Cas no</th>
	                        <th>사용금지</th>
	                        <th>배합제한</th>
	                        <th>함량비</th>
	                        <!-- Carry over 여부 -->
	                        <!-- RA202007 // carry over 여부 컬럼 -->
<!-- 	                        <th><span><fmt:message key='odm_matr.caryyover_yn'/></span></th> -->
	                        <th>식물 원료 사용 부위</th>
	                        <th>알러젠 여부</th>
	                    </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${allergenMap[key]}" var="avo" >
                    	<tr>
                    		<!-- 성분코드 -->
	                        <td class="bdl_n">
	                            <div class="con">${avo.v_con_cd }</div>
	                        </td>
	                        <!-- 성분명(영문) -->
	                        <td>
                                <div class="con">${avo.v_con_nm_en }</div>
                            </td>
                            <!-- 성분명 -->
                            <td>
                                <div class="con">${avo.v_con_nm_ko }</div>
                            </td>
                            <!-- Cas no -->
                            <td>
                                <div class="con">${avo.v_con_cas_no }</div>
                            </td>
                            <!-- 사용금지 -->
                            <c:set var="banAlgList" value="${fn:split(avo.v_ban,',') }"></c:set>
							<c:set var="banAlgLength" value="${fn:length(banAlgList) }"></c:set>
							<c:set var="banAlgNation" value="${banAlgLength}개국"></c:set>
                            <td>${banAlgLength >= 2 ? banAlgNation : avo.v_ban }</td>
                            <!-- 배합규제 -->
                            <c:set var="limitAlgList" value="${fn:split(avo.v_limit,',') }"></c:set>
							<c:set var="limitAlgLength" value="${fn:length(limitAlgList) }"></c:set>
							<c:set var="limitAlgNation" value="${limitAlgLength}개국"></c:set>
                            <td>${banAlgLength >= 2 ? limitAlgNation : avo.v_limit }</td>
                            <c:set var="row_num" value=""/>
							<c:if test="${s.index ne 0}">
								<c:set var="row_num" value="_${s.index}"/>
							</c:if>
                            <!-- 함량비 -->
                            <td class="arrAllergenPer${row_num}">
                            	${avo.v_con_in_per}
                            </td>
                            <!-- Carry over 여부 -->
	                        <!-- RA202007 // carry over 여부 컬럼 -->
                            <%-- <td>
                                <div class="checkbox-area">
                                    <span class="checkbox no_pd">
                                        <input type="checkbox" id="chkbx_co_yn" value="Y" ${avo.vCarryoverYn eq 'Y' ? 'checked=\"checked\"' : '' } readonly="readonly">
                                        <label for="chkbx_co_yn"></label>
                                    </span>
                                    <input type="checkbox" value="Y" ${avo.vCarryoverYn eq 'Y' ? 'checked=\"checked\"' : '' } readonly="readonly">
                                </div>
                            </td> --%>
                            <!-- 식물 원료 사용 부위 -->
                            <td>
                                <div class="con">${avo.v_remark }</div>
                            </td>
                            <!-- 알러젠 여부 -->
                            <td>
                                <div class="radio-area1">
                                    <span class="radio no_pd">
                                        <input type="radio" id="btn_alg_yn" class="rb_aller_yn" ${avo.v_allergen_yn eq 'Y' ? 'checked=\"checked\"' : '' } readonly="readonly">
                                        <label for="btn_alg_yn"></label>
                                    </span>
                                </div>
                            </td>
                    	</tr>
                    	</c:forEach>
                    	<tr id="conTotal" class="total">
					        <td colspan="6" class="bdl_n">조성함량 합계</td>
					        <td class="allergen_sum_td">0</td>
					        <td colspan="2"></td>
					    </tr> 
                    </tbody>
				</table>
			</div>
		</td>
	</tr>			
							</c:when>
							<c:otherwise>
							</c:otherwise>						
						</c:choose>
					</c:forEach>					
				</c:if>		
			</c:when>
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [E] 향코드 여부가 'Y'인 경우 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>			
			
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [S] 향코드 여부가 'N'인 경우 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>			
			<c:otherwise>
			</c:otherwise>
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [E] 향코드 여부가 'N'인 경우 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>			
		</c:choose>		
<%-- ######################################################################################################## --%>	
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [E] '상세보기'인 경우 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
