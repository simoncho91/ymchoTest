<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>
<script type='text/javascript'>
var _duplicateChk = false;
 $(function(){
	$('.btn_list').on('click',function(){
		window.location.href='/si/im/010/init.do?openMenuCd=MISIIM010';
	});
	$('.tab_button').on('click',function(){
		window.location.href='/si/im/010/si_im_010_reg.do?i_sConCd='+$('#i_sConCd').val()+"&i_nVerSeq="+(Number($('#i_nVerSeq').val()))+"&i_sFlagNewwVer=Y";
	});

	$('.tab_view').on('click',function(){
		var tabId=$(this).attr('id').split('_')[1];		
		window.location.href='/si/im/010/si_im_010_view.do?i_sConCd='+$('#i_sConCd').val()+"&i_nVerSeq="+tabId;
	});
 });
</script>

<form name="frm" id="frm">
	<input type="hidden" name="i_sFlagBanB" id ="i_sFlagBanB" value="${rVo.v_flag_ban}" />
	<input type="hidden" name="i_sFlagBanL" id ="i_sFlagBanL" value="${rVo.v_flag_permit}" />
	<input type="hidden" name="i_sConfirmYn" id ="i_sConfirmYn" value="${rVo.v_confirm_yn}" />
	<input type="hidden" name="i_nVerSeq" id ="i_nVerSeq" value="${rVo.n_ver_seq}" />
	<input type="hidden" name="i_nMaxVerSeq" id ="i_nMaxVerSeq" value="${rVo.n_max_ver_seq}" />
	<input type="hidden" name="i_sFlagNewwVer" id ="i_sFlagNewwVer" />	
	
	<!-- 버전 탭 뷰 -->
	<div class="pd_top10"></div>
	<c:if test="${rVo.v_del_yn eq 'Y' }">
		<h1 class="tit"><font color='#FF0000'>*삭제된 버전입니다.</font></h1>
	</c:if>
	<c:if test="${!empty rVo.v_con_cd }">	
		<div id='tab_view'>
			<ul id="ul_tab" class="sty_tab">
				<c:forEach var="i" begin="1" end="${rVo.n_max_ver_seq }">
					<li id='tab_${i}' class="tab tab_view"><a href="#none" class=" <c:if test="${rVo.n_ver_seq eq i }">on</c:if>"><span>${i}</span></a></li>
				</c:forEach>
				<c:if test="${rVo.v_max_confirm_yn eq 'Y' }">
	        		<li id='tab_versionAdd' class="tab tab_button"><a href="#none"><span>버전추가</span></a></li>
	        	</c:if>
			</ul>
		</div>
	</c:if>
	<!-- 버전 탭 뷰 -->	
	<div class="top_btn_area" style="z-index:1;">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
	</div>
	
	<table class="sty_02">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<tr>
			<th>성분코드</th>
			<td>
				<input type="hidden" name="i_sConCd" id ="i_sConCd" value="${rVo.v_con_cd}"></input>
				<span>${rVo.v_con_cd}</span>
			</td>
			<th>알러젠 여부</th>
			<td class="last">			
				<span>${rVo.v_allergen_yn}</span>
			</td>			
		</tr>
		<tr>
			<th>요청구분</th>
			<td>
				<span>${rVo.v_flag eq 'O' ? 'ODM' : 'SI'}</span>
			</td>
			<th>배합한도</th>
			<td>
				<span>${rVo.n_max_allow_wt}</span>
			</td>
		</tr>
		<tr>
			<th>색소여부</th>			
			<td>
				<span>${rVo.v_ci_yn}</span>
			</td>			
			<th>표시성분 여부</th>
			<td><span>${rVo.v_display_yn}</span></td>

<%-- 			<th>필수 서류여부</th>			
			<td><span>${rVo.v_zcert}</span></td> --%>
		</tr>		
		<tr>
<!-- 			<th>함량표시 여부</th>			 -->
<%-- 			<td colspan='3'><span>${rVo.v_mateview_yn}</span></td> --%>
<%-- 			<th>표시성분 여부</th>
			<td colspan='3'><span>${rVo.v_display_yn}</span></td> --%>
		</tr>
		<tr>
			<th>성분명</th>
			<td>
				<table>
					<colgroup>
						<col width="7%"/>
						<col width="43%"/>
					</colgroup>
					<tr>
						<th>영어</th>						
						<td><span>${rVo.v_con_nm_en}</span></td>
					</tr>
					<tr>
						<th>한글</th>
						<td><span>${rVo.v_con_nm_ko}</span></td>
					</tr>
					<tr>
						<th>중문</th>
						<td><span>${rVo.v_con_nm_cn}</span></td>
					</tr>
					<tr>
						<th>유럽</th>
						<td><span>${rVo.v_con_nm_eu}</span></td>
					</tr>
					<tr>
						<th>일문</th>
						<td><span>${rVo.v_con_nm_jp}</span></td>
					</tr>
				</table>
			</td>
			<th>Cas No</th>
			<td>
				<table id='subCasTable' style="width:100%;">
					<colgroup>
						<col width="90%"/>
					</colgroup>
					<tr class='header'>
						<th><div>CAS NO.</div></th>
					</tr>
					<c:forEach items="${casNoList}" var="casVo" varStatus="s">
						<tr <c:if test="${s.index == 0}">class='first'</c:if> >
							<td><span>${casVo.v_cas_no}</span></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<th>사용금지</th>
			<td>
				<table>
					<colgroup>
						<col width="10%"/>
						<col width="40%"/>
					</colgroup>
					<tr>
						<th>국가</th>
						<th>상세</th>
					</tr>
					<c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'KO') >=0}">
						<tr>
							<td>국내</td>
							<td><span>${rVo.v_r_b_ko_comment}</span></td>		
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'CN') >=0}">
						<tr>
							<td>중국</td>
							<td><span>${rVo.v_r_b_cn_comment}</span></td>
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'AE') >=0}">
						<tr>
							<td>아세안</td>						
							<td><span>${rVo.v_r_b_ae_comment}</span></td>
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'EU') >=0}">
						<tr>
							<td>유럽</td>						
							<td><span>${rVo.v_r_b_eu_comment}</span></td>							
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_b_cd, 'US') >=0}">
						<tr>
							<td>미국</td>						
							<td><span>${rVo.v_r_b_us_comment}</span></td>							
						</tr>
					</c:if>
				</table>
			</td>
			<th>배합제한</th>
			<td>
				<table>
					<colgroup>
						<col width="10%"/>
						<col width="40%"/>
					</colgroup>
					<tr>
						<th>국가</th>
						<th>상세</th>
					</tr>
					<c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'KO') >=0}">
						<tr>
							<td>국내</td>
							<td><span>${rVo.v_r_l_ko_comment}</span></td>
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'CN') >=0}">
						<tr>
							<td>중국</td>
							<td><span>${rVo.v_r_l_cn_comment}</span></td>							
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'AE') >=0}">
						<tr>						
							<td>아세안</td>
							<td><span>${rVo.v_r_l_ae_comment}</span></td>							
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'EU') >=0}">
						<tr>						
							<td>유럽</td>
							<td><span>${rVo.v_r_l_eu_comment}</span></td>							
						</tr>
					</c:if>
					<c:if test="${fn:indexOf(banVo.v_gl_l_cd, 'US') >=0}">
						<tr>
							<td>미국</td>
							<td><span>${rVo.v_r_l_us_comment}</span></td>							
						</tr>
					</c:if>
				</table>
			</td>
		</tr>
		<tr>
			<th>비고</th>
			<td colspan='3'>
				<span>${rVo.v_r_comment}</span>
			</td>
		</tr>
		<tr>
			<td colspan='4'>
				<table id='subFuncTbl' style="width:100%;">
					<colgroup>
						<col width="30%"/>
						<col width="30%"/>
						<col width="30%"/>
					</colgroup>
					<tr class='header'>
						<th>기능(영문)</th>
						<th>기능(국문)</th>
						<th>기능(중문)</th>
					</tr>
					<c:forEach items="${funcList}" var="funcVo" varStatus="s">
						<tr class='<c:if test="${s.index == 0}">first</c:if> tr_value' >
							<td>
								<span>${funcVo.v_func_nm_en}</span>
							</td>
							<td>
								<span>${funcVo.v_func_nm_ko}</span>
							</td>
							<td>
								<span>${funcVo.v_func_nm_cn}</span>
							</td>
						</tr>
					</c:forEach>
				</table>
			<td>
		</tr>
	</table>
	
	<div class="pd_top10"></div>
	<ul class="btn_area">
		<li class="right">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</li>
	</ul>
</form>
</body>
</html>
