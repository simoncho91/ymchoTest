<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<!-- 파일업로드 관련  s-->
<link rel="stylesheet" href="${ScriptPATH}jfupload/css/jquery.fileupload.css"></link>
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
<!-- 파일업로드 관련 e -->

<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_reg.js"></script>
<script type="text/javascript">
	$(function(){
		fn_buttonEvent();
	});
	
	//버튼이벤트
	function fn_buttonEvent(){
		//내용물 탭 클릭
		j$(".li_tab").unbind("click").click(function(event) {
			event.preventDefault();
			
			var id = this.id;
			j$("form[name='frm']").find("input[name='i_sPartNo']").val(id);
			
			j$(".li_tab a").removeClass("on");
			j$(this).find('a').addClass("on");

			j$("form[name='frm']").attr("action", "/br/pr/020/br_pr_020_review_view.do").submit();
		});
		
		//단일성분 Function 셀렉트 변경
		j$(".select_modiFunc").unbind("change").change(function(event){
			event.preventDefault();
			var val = j$(this).val();
			var inp_name = j$(this).attr("name");
			
			inp_name = inp_name.replace("i_sModiFuncNm", "i_sModiFuncNm_etc");
			
			if(val == 'ETC'){
				j$("input[name='"+inp_name+"']").show();
			}else{
				j$("input[name='"+inp_name+"']").hide();
			}
		});
	}

	//이전(상세)으로 돌아가기
	function fnPrev(){
		location.href='/br/pr/020/br_pr_020_view.do?&i_sAdReqId=' + $("#i_sAdReqId").val();
	}
	
	//실증자료 등록 화면 이동
	function fn_move(url){
		location.href="/br/pr/020/" + url + '?i_sRecordId=' + $("#i_sRecordId").val() + '&i_sProductCd=' + $("#i_sProductCd").val() + '&i_sAdReqId=' + $("#i_sAdReqId").val();
	}
	
	
	//배합제한
	function fn_popGloblim(id,version){
		var param ={
			i_sConCd: id
			, i_nVerSeq : version
			, i_sGubun : "L"
		};
		var option = {
			url:"/cm/pop/cm_doc_ban_desc_pop.do?i_sCmFunction=setPopUpData&i_sInput=&param="+encodeURI(JSON.stringify(param))
			, title : "배합제한"
			, width : "500"
			, height : "600"
		}
		
		fn_pop(option);	 
	}
	
	//사용금지
	function fn_popGlob(id,version){
		var param ={
	 		i_sConCd: id
	 		, i_nVerSeq : version
			, i_sGubun : "B"
		};
		var option = {
			url:"/cm/pop/cm_doc_ban_desc_pop.do?i_sCmFunction=setPopUpData&i_sInput=&param="+encodeURI(JSON.stringify(param))
			, title : "사용금지"
			, width : "500"
			, height : "600"
		}

		fn_pop(option);	 
	}
</script>
	
<form name="frm" id="frm" method="post">
	<input type="hidden" id="i_sAdReqId"		name="i_sAdReqId"		value="${reqVo.i_sAdReqId}"/>
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${rvo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${i_sActionFlag}"/>
	<input type="hidden" id="i_sRecordId"		name="i_sRecordId"   	value="${reqVo.i_sRecordId}"/>
	<input type="hidden" id="i_sUserNm"			name="i_sUserNm"		value="${reqVo.s_usernm}"/>
	<input type="hidden" id="i_sProductCd"		name="i_sProductCd"   	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" id="i_sPartNo"			name="i_sPartNo" 		value="" />
	
	<div class="sec_cont">
		<ul class="btn_area">
			<li class="right">
				<a href="javascript:fn_move('br_pr_020_review_reg.do');" class="btnA bg_dark"><span>실증자료 등록</span></a>
				<a href="javascript:fnPrev();" class="btnA bg_dark"><span>이전</span></a>
			</li>
		</ul>
	
		<h2 class="tit">제품 단일성분</h2>
		<ul class="sty_tab">
			<c:forEach items="${partList }" var="vo">
				<li class="tab li_tab" id="${vo.n_part_no }">
					<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.n_part_no }</span></a>
				</li>
			</c:forEach>
		</ul>
	</div>
	
	<div class="content" style="padding-left:15px;">
		<!-- 단일성분 리스트 s -->
		<div class='sec_cont mt_60'>
			<table class="sty_02 table_matrcd" style="word-break:break-all;">
				<colgroup>
					<col style="width:30px;">
					<col style="width:50px;">
					<col style="width:60px;">
					<col style="width:150px;">
					<col style="width:200px;">
					<col style="width:220px;">
					<col style="width:220px;">
					<col style="width:100px;">
					<col style="width:100px;">
					<col style="width:100px;">
					<col style="width:120px;">
					<col style="width:60px;">
					<col style="width:60px;">
	            </colgroup>
				<thead>
					<tr>
						<th class="ta_c"></th>       
						<th class="ta_c">No.</th>                                     
						<th class="ta_c">성분<br/>코드</th>                           
						<th class="ta_c">표시명<br/>(영문)</th>                       
						<th class="ta_c">조성<br/>함량</th>                           
						<th class="ta_c">Function</th>                                
						<th class="ta_c">Allergen Func.</th>                          
						<th class="ta_c">CAS No.</th>                                 
						<th class="ta_c">사용금지</th>                                
						<th class="ta_c">배합제한</th>                                
						<th class="ta_c">Safety_ODM<br/> standard</th>                     
						<th class="ta_c">Active/<br/>Inactive</th>                         
						<th class="ta_c">알러젠여부</th>
					</tr>
				</thead>
				<tbody id="tbody_single">
					<c:set var="sInPerAdd" value=""/>
					<c:set var="sumPerCon" value="0"/>
					<c:set var="cnt" value="1"/>
					<c:if test="${!empty singleList }">
						<c:set var="tempCnt" value="0"/>
						<c:forEach items="${singleList }" var="vo" varStatus="status">
							<c:if test="${vo.v_reg_allergenyn ne 'Y' and reqVo.i_sPartNo eq vo.n_part_no}">											
								<tr class="partno partno_${vo.n_part_no }" style="display: ${reqVo.i_sPartNo ne vo.n_part_no ? 'none' : ''}">
									<td class="ta_c"></td>
									<td class="ta_c tr_count">${cnt}</td>
									<td class="ta_c">
										${vo.v_con_cd }
										<input type="hidden" name="i_arrConcd" value="${vo.v_con_cd }"/>
									</td>
									<td class="ta_c">${vo.v_con_nm_en }</td>
									<td class="ta_c">
										${vo.v_con_in_per }
										<c:set var="sumPerCon" value="${sumPerCon + vo.v_con_in_per }"/>													
										<span class="span_hide span_per">${vo.v_con_in_per }</span>
									</td>
									<td class="ta_c">
										${vo.v_comp_origin_fc}
										<div>
											<input type="text" name="i_sModiFuncNm_etc_${vo.v_con_cd}_${vo.n_part_no }" value="" size="30" style="margin : 0 auto; margin-top: 10px; display: none;"/>
											<input type="hidden" name="i_sBeforeFuncNm_${vo.v_con_cd}_${vo.n_part_no}" value="${vo.v_comp_origin_fc }"/>
										</div>
									</td>
									<td class="ta_c">
										${vo.v_allergen_func }
									</td>
									<td class="ta_c">${vo.v_odm_casno }</td>
									<td class="ta_c"><a href="javascript:fn_popGlob('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${vo.v_zglobal }</span></a></td>
									<td class="ta_c"><a href="javascript:fn_popGloblim('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${vo.v_zgllim  }</span></a></td>								
									<td class="ta_c">${vo.v_odm_remark }</td>
									<td class="ta_c">
										<span class="span_chk-style">
											<span class="${vo.v_active_yn eq 'Y' ? 'on' : ''}"></span>
										</span>
									</td>
									<td class="ta_c">
										<span class="span_chk-style">
											<span class="${vo.v_allergen_yn eq 'Y' ? 'on' : '' }"></span>
										</span>
									</td>
								</tr>
								<c:set var="cnt" value="${cnt+1}"/>
								<c:set var="tempCnt" value="${tempCnt+1 }"/>
							</c:if>
						</c:forEach>
						<tr>
							<th colspan="2"><font color="red"><b>조성함량 합계</b></font></th>
							<td>${sumPer }</td>
							<td colspan="10" style="text-align: left;" class="last td_sum">
								<fmt:formatNumber value="${sumPerCon}" type="number" var="sumPer" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>	
								${sumPerCon }
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<!-- 단일성분 리스트 e -->
		
		<!-- 향료알러젠 s -->
		<div class="sec_cont mt_60">
			<h2 class="tit">향료 알러젠</h2><!-- 향료 알러젠 -->
			<table class="sty_02 table_matrcd" style="word-break:break-all;">
				<colgroup>
					<col style="width:30px;">
					<col style="width:50px;">
					<col style="width:60px;">
					<col style="width:150px;">
					<col style="width:200px;">
					<col style="width:220px;">
					<col style="width:220px;">
					<col style="width:100px;">
					<col style="width:100px;">
					<col style="width:100px;">
					<col style="width:120px;">
					<col style="width:60px;">
					<col style="width:60px;">
	            </colgroup>
				<thead>
					<tr class="start">
						<th class="ta_c"></th>
						<th class="ta_c">No.</th>
						<th class="ta_c">성분<br/>코드</th>
						<th class="ta_c">표시명<br/>(영문)</th>
						<th class="ta_c">조성<br/>함량</th>
						<th class="ta_c">Function</th>
						<th class="ta_c">Allergen Func.</th>
						<th class="ta_c">CAS No.</th>
						<th class="ta_c">사용금지</th>
						<th class="ta_c">배합제한</th>
						<th class="ta_c">Active/Inactive</th>
						<th class="ta_c">알러젠여부</th>
						<th></th>
					</tr>
				</thead>
				<tbody id="tbody_single">
					<c:set var="sInPerAdd" value=""/>
					<c:set var="sumPer" value="0"/>
					<c:set var="cnt" value="1"/>
					<c:if test="${!empty singleList }">
						<c:forEach items="${singleList }" var="vo" varStatus="status">
							<c:if test="${vo.v_reg_allergenyn eq 'Y' and reqVo.i_sPartNo eq vo.n_part_no}">
								<tr class="partno partno_${vo.n_part_no }" style="display: ${reqVo.i_sPartNo ne vo.n_part_no ? 'none' : ''}">
									<td class="ta_c"></td>
									<td class="ta_c tr_count">${cnt}</td>
									<td class="ta_c">
										${vo.v_con_cd }
										<input type="hidden" name="i_arrConcd" value="${vo.v_con_cd }"/>
									</td>
									<td>${vo.v_con_nm_en }</td>
									<td class="ta_c">
										${vo.v_con_in_per }
										<span class="span_hide span_per">${vo.v_con_in_per }</span>
										<c:set var="sumPer" value="${sumPer + vo.v_con_in_per }"/>
									</td>
									<td class="ta_c">
										${vo.v_comp_origin_fc}
										<div>
											<input type="text" name="i_sModiFuncNm_etc_${vo.v_con_cd}_${vo.n_part_no }" value="" size="30" style="margin : 0 auto; margin-top: 10px; display: none;"/>
											<input type="hidden" name="i_sBeforeFuncNm_${vo.v_con_cd}_${vo.n_part_no}" value="${vo.v_comp_origin_fc }"/>
										</div>
									</td>
									<td class="ta_c">
										${vo.v_allergen_func }
									</td>
									<td class="ta_c">${vo.v_odm_casno }</td>
									<td class="ta_c"><a href="javascript:fn_popGlob('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${vo.v_zglobal }</span></a></td>
									<td class="ta_c"><a href="javascript:fn_popGloblim('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${vo.v_zgllim  }</span></a></td>									
									<td class="ta_c">
										<span class="span_chk-style">
											<span class="${vo.v_activeyn eq 'Y' ? 'on' : ''}"></span>
										</span>
									</td>
									<td class="ta_c">
										<span class="span_chk-style">
											<span class="${vo.v_allergen_yn eq 'Y' ? 'on' : '' }"></span>
										</span>
									</td>
									<td></td>
								</tr>
								<c:set var="cnt" value="${cnt+1}"/>
								<c:set var="tempCnt" value="${tempCnt+1 }"/>
							</c:if>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
		<!-- 향료알러젠 e -->
		
		<!-- 실증자료항목 s -->
		<div class="sec_cont">
			<h2 class="tit">실증자료항목</h2>
			<table class="sty_02">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody class="tbody_product_info">
					<c:forEach items="${attachType}" var="at" varStatus="s">
						<tr class="tr_origin_img">
							<th>${at.COMM_CD_NM}</th>
							<td colspan="4">
								<CmTagLib:cmAttachFile uploadCd="REVIEW" type="viewLog" recordId="${reqVo.i_sAdReqId}" pk1="${s.index}" />
							</td>
						</tr>   
						<tr>
							<th>${at.COMM_CD_NM} 코멘트</th>
							<td colspan="4">${commentList[s.index].v_content}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<ul class="btn_area">
				<li class="right">
					<a href="javascript:fn_move('br_pr_020_review_reg.do');" class="btnA bg_dark"><span>실증자료 등록</span></a>
					<a href="javascript:fnPrev();" class="btnA bg_dark"><span>이전</span></a>
				</li>
			</ul>
		</div>
	</div>
</form>