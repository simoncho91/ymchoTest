<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
</style>
<script src="/ckeditor/ckeditor.js"></script><!-- ckeditor 사용시 -->
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_reg.js"></script>
<script type="text/javascript">
	var prodSearch;

	j$(function(){
		fn_init();
		CKEDITOR.replace( 'editor', {height : '400', readOnly: 'true'} );// ckeditor 사용시
	});
	
	function fn_init(){
		prodSearch = new CmCdSearch('prodSearch','/br/pr/020/br_pr_020_prod_list_pop.do',
				{searchInput:'i_sProductCd',
			inputCode:'i_sProductCd',
			inputNameKo:'i_sProductNm',
			inputNameEn:'i_arrProduct_RefNm_En',
			inputNameCn:'i_arrProduct_RefNm_Cn',
			callback:funcProdSearch});
		
		function funcProdSearch(data,obj){
			console.log("funcProdSearch : "+this.idx);
		 	$('input[name=PON_attach_pk1]')[this.idx].value=data.v_matnr;
		}
	}
	
	function fnSave(status){
		var frm 	=	document.frm;
		
		if(fn_isNull($("#i_sAdContentRs").val())) {
			fn_s_alertMsg("검토의견을 입력해주세요.");
			return;
		}
		
		dhx.confirm({
			header: "문안검토",
			text: '검토를 완료하시겠습니까?',
			buttons: ["예", "아니오"],
			buttonsAlignment:"center"
		}).then(function(answer){
			if(answer){
				var frm 					=	document.frm;
				frm["i_sActionFlag"].value	=	"M";
				frm["i_sStatus"].value		= 	status;
				$.ajax({
					url:"/br/pr/020/br_pr_020_save.do"
					,data : $(frm).serialize()
					,dataType : "json"
					,type : "POST"
					,success : function(){						
						location.href="/br/er/010/br_er_010_view.do?openMenuCd=MIBRER010&i_sAdReqId="+$('input[name=i_sAdReqId]').val();
					},error : function(jqXHR, textStatus, errorThrown){
						fn_failMsg(jqXHR, textStatus, errorThrown);
					}
					
				});
			}
		});
	}
	
	//수정
	function fnModify(){
		var frm			= document.frm;
		
		frm["i_sActionFlag"].value = "M";
			
		frm.action		= "/br/pr/020/br_pr_020_reg.do";
		frm.submit();
	}
	
	function goTabView(flag){
		var frm = j$("form[name='frm']");
		
		if (flag == "editional") {
			frm.attr("action",	"/br/pr/020/br_pr_020_view.do");
		} else if(flag == "original") {
			frm.attr("action",  "/br/pr/020/br_pr_020_ori_view.do");		
		}
			frm.submit();
	}
	
	//목록으로 돌아가기
	function fnList(){
		location.href="/br/er/010/init.do?openMenuCd=MIBRER010";
	}
	
	//실증자료 확인 이동
	function fn_move(url){
		location.href="/br/pr/020/" + url + '?i_sRecordId=' + $("#i_sRecordCd").val() + '&i_sProductCd=' + $("#i_sProductCd").val() + '&i_sAdReqId=' + $("#i_sAdReqId").val();
	}
	
	function goTabView(flag){
		var frm = j$("form[name='frm']");
		
		if (flag == "editional") {
			frm.attr("action",	"/br/er/010/br_er_010_view.do");
			
		} else if(flag == "original") {
			frm.attr("action",  "/br/er/010/br_er_010_ori_view.do");		
		}
		
		frm.submit();
	}
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sAdReqId"		name="i_sAdReqId"		value="${rvo.v_ad_req_id}"/>
	<input type="hidden" id="i_sProductCd"		name="i_sProductCd"   	value="${rvo.v_product_cd}"/>
	<input type="hidden" id="i_sRecordCd"		name="i_sRecordCd"   	value="${rvo.v_record_id}"/>
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${rvo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${i_sActionFlag}"/>
	<input type="hidden" id="i_sCancelFlag"		name="i_sCancelFlag"    value="N"/>
	<input type="hidden" id="i_sVesselYn"		name="i_sVesselYn"		value="${rvo.v_vessel_yn}"/>
	<input type="hidden" id="i_sBoxYn"			name="i_sBoxYn"			value="${rvo.v_box_yn}"/>
	<input type="hidden" id="i_sPaperYn"		name="i_sPaperYn"		value="${rvo.v_paper_yn}"/>
	<input type="hidden" id="i_sUserNm"			name="i_sUserNm"		value="${reqVo.s_usernm}"/>
	<input type="hidden" id="i_sBmEmail"		name="i_sBmEmail"		value="${rvo.v_bm_email}"/>
	<input type="hidden" id="i_sProductNmMail"	name="i_sProductNmMail" value="${rvo.v_product_nm_ko}"/> <!-- 제품명(이메일용) -->

	<div class="content">
		<!-- 탭 영역 -->
		<ul class="sty_tab">
			<li>
				<a href="javascript:goTabView('editional');"  class="on">
					<span>문안검토</span>
				</a>
			</li>
			<c:if test="${ rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS04' 
						|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS05' 
						|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS06'
						|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS07'
						|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS08'
						|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09'
						|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS10'
						}">
				<li>
					<a href="javascript:goTabView('original');">
						<span>원화등록요청</span>
					</a>
				</li>
			</c:if>
		</ul>
		<!-- //탭 영역 -->
	
		<!-- 버튼이벤트 영역(상단) -->
		<div class="top_btn_area" style="z-index:1;">
			<!-- <a href="javascript:fn_move('br_pr_020_review_view.do');" class="btnA bg_dark"><span>실증자료 확인</span></a> -->
			<!-- 	임시저장일 때(01)	:[수정],		[목록]		-->
			<!-- 	검토요청중일 때(02)	:[요청 취소],	[목록] 		-->
			<!-- 	검토완료일 때		:[목록] 					-->
			<!-- 	반려일 때			:[수정],		[목록] 		-->
			<c:choose>
				<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS01'}">	<!-- 임시저장인 경우 -->
					<a href="javascript:fnModify();" 				class="btnA bg_dark"><span>수정</span></a>
				</c:when>
				<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS02'}">	<!-- 검토요청상태인 경우 -->
					<a href="javascript:fnSave('AD_REQ_STATUS04');" class="btnA bg_dark"><span>승인</span></a>
					<a href="javascript:fnSave('AD_REQ_STATUS03');" class="btnA bg_dark"><span>반려</span></a>
				</c:when>
			</c:choose>
			<a href="javascript:fnList();"							class="btnA bg_dark"><span>목록</span></a>
		</div>
		<!-- //버튼이벤트 영역(상단) -->
	
		<!-- 메인폼 영역 -->
		<div class="sec_cont" style="/*padding-left:15px;*/">
			<h2 class="tit">문안검토 기본 기재사항</h2>
			<table class="sty_02">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td class="last" colspan="3">
							${rvo.v_title}
						</td>
					</tr>
					<tr>
						<th>제품코드</th>
						<td>
							${rvo.v_product_cd}
						</td>
						<th>제품명</th>
						<td>
							${rvo.v_product_nm_ko}
						</td>
					</tr>
					<tr>
						<th>제품연구원</th>
						<td id="i_sVendorId">
							${rvo.VENDOR_ID}
						</td>
						<th>등록일</th>
						<td class="last" >
							${cfn:getStrDateToString(rvo.v_reg_dtm,'yyyy-MM-dd')}
						</td>
					</tr>
					<tr>
						<th>브랜드</th>
						<td>
							${rvo.BRAND_NM}
						</td>
						<th>출시시기</th>
						<td class="last">
							${cfn:getStrDateToString(rvo.v_release_dtm,'yyyy-MM-dd')}
						</td>
					</tr>
					<tr>
						<th>마케터(등록자)</th>
						<td class="TouTr">
							${rvo.V_REG_USER_NM}
						</td>
						<th>용기타입</th>
						<td class="TouTr" id="i_sBottleType">
						</td>
					</tr>
					<tr class="TouTr">
						<th>제조일로부터 사용기한</th>
						<td id="i_sTou_use">
							${rvo.v_shelf_life}개월
					 	</td>	
						<th>개봉 후 사용기간</th>
						<td id="i_sOpenTou_use">
							${rvo.v_pao}개월
						</td>
					</tr>
					<tr>
						<th>종류</th>
						<td id="i_sType">
							${rvo.ORIGIN_NM}
						</td>
						<th>담당 RA</th>
						<td>
							${rvo.v_ra_nm}
						</td>
					</tr>
					<tr>
						<th>소구범위</th> <!-- 소구범위 -->
						<td>
							<div style="float: left;display: ${fn:indexOf(rvo.v_free_gn, 'E') > -1 ? 'block' : 'none'};">								
								<span style="float: left;">
									${fn:indexOf(rvo.v_free_gn, 'E') > -1 ? '무소구' : ''}
								( <span>${rvo.v_musogu_cont }</span> )
								</span>								
							</div>					
							<br/>		
							<div style="float: left;display: ${fn:indexOf(rvo.v_free_gn, 'D') > -1 ? 'block' : 'none'};">
								<span style="float: left;">
									${fn:indexOf(rvo.v_free_gn, 'D') > -1 ? '기타' : ''}
								( <span >${rvo.v_sogu_cont }</span> )
								</span> 
							</div>
						</td>
						<th>포장단위</th>
						<td>
							<span>${rvo.v_packet_unit }</span>
						</td>
					</tr>
					<tr>
						<!-- 에디터 s -->
						<th>의뢰내용</th>
						<td colspan="3">
							<div id="editor">
								${rvo.v_ad_content}
							</div>
						</td>
						<!-- 에디터 e -->
					<tr class="tr_origin_img">
						<th>기타첨부</th>
						<td colspan="4">
							<CmTagLib:cmAttachFile uploadCd="AD" type="viewLog" recordId="${rvo.v_ad_req_id}" />
						</td>
					</tr>
					<tr id="tr_filtTxt" class="C020_need">
						<th>금지어 필터링</th>
						<td colspan="3" id="td_filt_Txt">
							${rvo.v_filtering_text}
						</td>
					</tr>
					<tr>
						<td colspan="4">
							${introText}
						</td>
					</tr>
					<tr>
						<td class="last sub_table_area" colspan="4" id="td_forbid_sum">
							<c:choose>
								<c:when test="${not empty rvo.forbidList}">
									<table class="sty_03" style="table-layout:fixed">
										<colgroup>
											<col width="25%;">
											<col width="25%;">
											<col width="25%;">
											<col width="25%;">
										</colgroup>
										<tbody>
										<tr>
											<th class="bdl_n">금지어</th>
											<th>유사 금지표현</th>
											<th>대체표현/실증표현</th>
											<th>유사 처분사례</th>
										</tr>
									<c:forEach items="${rvo.forbidList}" var="vo">
										<tr>
											<td class="ta_c bdl_n">
												<font style="color:${vo.v_color};font-weight:bold;">${vo.v_word }</font>
											</td>
											<td class="ta_c">${cfn:removeHTMLChangeBr(vo.v_similar_word)}</td>
											<td class="ta_c">${cfn:removeHTMLChangeBr(vo.v_explain)}</td>
											<td class="ta_c">${cfn:removeHTMLChangeBr(vo.v_example)}</td>
										</tr>
									</c:forEach>
										</tbody>
									</table>
								</c:when>
								<c:otherwise>
									::금지어 요약이 없습니다.::
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>
							실증대상
						</th>
						<td class="last" id="td_proof_sum" colspan="3">
							<c:choose>
								<c:when test="${not empty rvo.proofList}">
									<c:forEach items="${rvo.proofList }" var="vo" varStatus="s"><c:if test="${s.count ne 1}">, </c:if><font style="color:${vo.v_color};font-weight:bold;">${vo.v_word}</font></c:forEach>
								</c:when>
								<c:otherwise>
									::실증대상 요약이 없습니다.::
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th>검토의견</th>
						<td colspan="3">
							<c:choose>
								<c:when test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS02'}"> <!-- 요청일 때를 제외하고는 text로만 보여주기 -->
									${rvo.v_ad_content_rs}
								</c:when>
								<c:otherwise>
									<input type="text" id="i_sAdContentRs" name="i_sAdContentRs" class="inp_sty01" style="width:99%;">
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 버튼이벤트 영역(하단) -->
			<div class="btn_area verR">
				<!-- <a href="javascript:fn_move('br_pr_020_review_view.do');" class="btnA bg_dark"><span>실증자료 확인</span></a> -->
				<!-- 	임시저장일 때(01)	:[수정],		[목록]		-->
				<!-- 	검토요청중일 때(02)	:[요청 취소],	[목록] 		-->
				<!-- 	검토완료일 때		:[목록] 					-->
				<!-- 	반려일 때			:[수정],		[목록] 		-->
				<c:choose>
					<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS01'}">	<!-- 임시저장인 경우 -->
						<a href="javascript:fnModify();" 				class="btnA bg_dark"><span>수정</span></a>
					</c:when>
					<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS02'}">	<!-- 검토요청상태인 경우 -->
						<a href="javascript:fnSave('AD_REQ_STATUS04');" class="btnA bg_dark"><span>승인</span></a>
						<a href="javascript:fnSave('AD_REQ_STATUS03');" class="btnA bg_dark"><span>반려</span></a>
					</c:when>
				</c:choose>
				<a href="javascript:fnList();"							class="btnA bg_dark"><span>목록</span></a>
			</div>
			<!-- //버튼이벤트 영역(하단) -->
		</div>
		<!-- //메인폼 영역 -->
	</div>
</form>