<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
	.con_table, .aller_table, .consumList, .attachTable{
		text-align: center;
	}
	
</style>
<script type="text/javascript" src="${ScriptPATH}mi/is/od/010/is_od_010_view.js"></script>
<script type="text/javascript">
	$(function() {
		odmMatrView.init();
	});

	//목록으로 돌아가기
	function fnList(){
		location.href="/is/od/010/init.do?openMenuCd=MIISOD010";
	}
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" name="vReturnUrl" 	value="${reqVo.vReturnUrl}"/>
	<input type="hidden" name="vReturnPars"	value="${reqVo.vReturnPars}"/>
	<input type="hidden" name="vActionFlag" value="${reqVo.vActionFlag}"/>
	<input type="hidden" name="vRawcd" 		value="${reqVo.vRawcd }"/>
	<input type="hidden" name="vOdmdbid" 	value="${regContent.v_odmdb_id }"/>
	<input type="hidden" id="vFlavorFlag" 	value="${regContent.vFlagFlavor eq 'Y'? 'Y' :'N'}"/>
	
	<span id="alergen_concd_span" class="span_hide">${allergenCd}</span>
	
	<div class="content">
		<!-- 버튼이벤트 영역(상단) -->
		<div class="top_btn_area">
			<a href="javascript:fnList();"	class="btnA bg_dark"><span>목록</span></a>
		</div>
		<!-- //버튼이벤트 영역(상단) -->
	
		<!-- 메인폼 영역 -->
		<div class="sec_cont">
			<h2 class="tit">원료DB</h2>
			<c:set value="${regContent}" var="vo"/>
			<table class="sty_02">
				<colgroup>
					<col style="width:8%">
                    <col style="width:28%">
                    <col style="width:7%">
                    <col style="width:13%">
                    <col style="width:9%">
                    <col style="width:13%">
                    <col style="width:9%">
                    <col style="width:12%">
				</colgroup>
				<tbody>
					<tr>
                  		<!-- 원료코드 -->
                 		<th>원료코드</th>
                 		<td>${vo.v_odmmat_cd}</td>
                 		<!-- 상태 -->
                 		<th><span>상태</span></th>
                 		<td>${vo.v_use_yn eq 'Y'? '사용중' : '사용금지'}</td>
                  		<!-- 향코드 여부 -->
                         <th>향코드 여부</th>
                         <td>${vo.v_flag_flavor}</td>
                 	</tr>
					<tr>
                   		<th>업체명</th>
                   		<td>${vo.v_vendor_nm}</td>
                    	<c:if test="${vo.v_flag_flavor eq 'Y'}">
	                  		<th>제조사</th>
	                  		<td>${vo.v_maker}</td>
							<th>향취</th>
							<td>${vo.v_flavor}</td>
                    	</c:if>
                    		<th>등록일</th>
							<td colspan="${vo.v_flag_flavor eq 'Y'? '' : '5'}">${vo.n_v_reg_dtm}</td>
                    	</tr>
                   <%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [S] 구성성분 ROW @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
					<tr>
                		<th>구성성분</th>
                		<td colspan="7">
   			<div class="board-area inner_table taC ">
      			<table id="con_table" class="con_table sty_02" style="width:100%">
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
                   <col style="width:6%">
                   <col style="width:4%">
               </colgroup>
                <thead>
                   <tr>
                       <th>성분코드</th>
                       <th>성분명(영문)</th>
                       <th>성분명</th>
                       <th>Cas no</th>
                       <th>사용금지</th>
                       <th>배합제한</th>
                       <th>함량비</th>
                       <th>식물 원료 사용 부위</th>
                       <!-- Carry over 여부 -->
                       <!-- RA202007 // carry over 여부 컬럼 -->
                       <th>CarryOver 여부</th>
                       <th>알러젠 여부</th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach items="${ingredList }" var="list" varStatus="s">
                   	<c:set var="row_num" value="_${s.index+1}"/>
                   	<tr class="con_tr">
             		<!-- 성분코드 v_con_cd -->
                   	<td>${list.v_con_cd}</td>
                   	<!-- 성분명(영문) -->
                   	<td>${list.v_con_nm_en }</td>
                   	<!-- 성분명 -->
					<td>${list.v_con_nm_ko }</td>
					<!-- Cas no -->
					<td>${list.v_con_cas_no }</td>
					<!-- 사용금지 -->
					<c:set var="banList" value="${fn:split(list.v_ban,',') }"></c:set>
					<c:set var="banLength" value="${fn:length(banList) }"></c:set>
					<c:set var="banNation" value="${banLength}개국"></c:set>
					<td>${banLength >= 2 ? banNation : list.v_ban }</td>
					<!-- 배합규제 -->
					<c:set var="limitList" value="${fn:split(list.v_limit,',') }"></c:set>
					<c:set var="limitLength" value="${fn:length(limitList) }"></c:set>
					<c:set var="limitNation" value="${limitLength}개국"></c:set>
					<td>${limitLength >= 2 ? limitNation : list.v_limit }</td>
					<!-- 함량비 -->
					<td class="conper_td">${list.v_con_in_per }</td>
					<!-- 식물 원료 사용 부위 -->
					<td>${list.v_remark}</td>
					<!-- Carry over 여부 -->
                   	<!-- RA202007 // carry over 여부 컬럼 -->
					<td class="carryover_td">
                      <div class="checkbox-area">
                          <span class="checkbox no_pd">
                          	<input type="checkbox" id="chkbx_co_yn${row_num}" name="con_co_yn" ${list.v_carryover_yn eq 'Y' ? 'checked=\"checked\"' : ''} disabled="disabled">
                          	<label for="chkbx_co_yn${row_num }"></label>
                          </span>
                      </div>
					</td>
					 <!-- 알러젠 여부 -->
                     <td>
                         <div class="radio-area1">
                             <span class="radio no_pd">
                                 <input type="radio" id="btn_alg_yn" disabled="disabled" ${list.v_allergen_yn eq 'Y' ? 'checked=\"checked\"' : '' } >
                                 <label for="btn_alg_yn"></label>
                             </span>
                         </div>
                     </td>
			         </tr>
			         </c:forEach>
		            	<tr id="conTotal" class="total">
                           <td colspan="6" class="bdl_n"><span>조성함량 합계</td>
                           <td id="conper_sum"><span>100</span></td>
                           <td colspan="3"></td>
                        </tr>  
                      </tbody>
               		</table>
               		</div>
                   	</td>
                	</tr>
					<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [E] 구성성분 ROW @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
					
					<%@ include file="/WEB-INF/jsp/sitims/mi/is/od/010/is_od_010_essential_include.jsp" %>	
					
					<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [S] 코멘트 ROW @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
					<tr>
                     	<th>코멘트</th>
	              		<td colspan="7">${vo.v_comment}</td>
                    </tr>
					<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [E] 코멘트 ROW @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>
				</tbody>
			</table>
		</div>
		 <c:if test="${vo.v_flag_flavor eq 'Y'}">
	    	<div style="padding:10px 0;">
				<p><font style="color:red; font-size: 14px; font-weight: bold;">※원료 구성 100% + 향 알러젠 α 의 형태</font></p>
				<p><font style="color:red; font-size: 14px; font-weight: bold;">※구성성분의 함량비를 고려한 알러젠 실제 햠량비 표기</font></p>
			</div>
	    	<div class="board-wrap">
	            <div class="board-unit">
	                <div class="board">
	                	<table class="sty_02 consumList">
	                		<colgroup>
								<col width="20%"/>
								<col width="20%"/>
								<col width="20%"/>
								<col width="20%"/>
								<col width="20%"/>
							</colgroup>
							<thead>
								<tr>
									<th>성분코드</th>
									<th>성분명(영문)</th>
									<th>성분명</th>
									<th>함량비</th>
									<th>알러젠 여부</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${consumList}" var="list" varStatus="s">
								<tr style="background-color:${list.v_allergen_yn eq 'Y' ? 'rgb(187, 189, 3)' : ''};">
									<td>${list.v_con_cd }</td>
									<td>${list.v_con_nm_en }</td>
									<td>${list.v_con_nm_ko }</td>
									<td>${list.v_real_con_in_per }</td>
									<td>
                                        <div class="radio-area1">
                                            <span class="radio no_pd">
                                                <input type="radio" id="btn_alg_yn" disabled="disabled" ${list.v_allergen_yn eq 'Y' ? 'checked=\"checked\"' : '' }>
                                                <label for="btn_alg_yn"></label>
                                            </span>
                                        </div>
                                    </td>
								</tr>
							</c:forEach>
							</tbody>
	                	</table>
	                </div>
                </div>
            </div>
	    </c:if>
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [S] 첨부파일 테이블 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>	        
	        <div class="board-wrap" style="margin-top:20px;">
	            <div class="board-unit">
	                <div id="attach_table" class="board">
		                <table class="sty_02 attachTable">
		                    <colgroup>
		                        <col style="width:8%">
		                        <col style="width:92%">
		                    </colgroup>
		                    <tbody>
		                    	<tr>
		                    		<th><span>SPEC</span></th>
		                    		<td>
		                    		<CmTagLib:cmAttachFile type="view" uploadCd="SPE" recordId="${vo.v_odmdb_id}" formName="frm"/>
		                    		</td>
		                    	</tr>
		                    	<tr>
	                                <th><span>COA</span></th>
	                                <td>
	                                <CmTagLib:cmAttachFile type="view" uploadCd="COA" recordId="${vo.v_odmdb_id}" formName="frm"/>
	                                </td>
	                            </tr>
                                <tr>
                                    <th><span>CCPP</span></th>
                                    <td>
                                    <CmTagLib:cmAttachFile type="view" uploadCd="CCP" recordId="${vo.v_odmdb_id}" formName="frm"/>
                                    </td>
                                </tr>
                                <tr>
                                    <th><span>Certi</span></th>
                                    <td>
                                    <CmTagLib:cmAttachFile type="view" uploadCd="CER" recordId="${vo.v_odmdb_id}" formName="frm"/>
                                    </td>
                                </tr>
                                <tr id="tr_aller_file_attach" style="display : ${vo.v_flag_flavor eq 'Y'? '': 'none'};">
                                    <th><span>알러젠 확인서</span></th>
                                    <td>
                                    <CmTagLib:cmAttachFile type="view" uploadCd="ARG" recordId="${vo.v_odmdb_id}" formName="frm"/>
                                    </td>
                                </tr>
		                    </tbody>
		                </table>
	                </div>
                </div>
            </div>
<%-- @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ [E] 첨부파일 테이블 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ --%>		
		<!-- //메인폼 영역 -->
	</div>
</form>