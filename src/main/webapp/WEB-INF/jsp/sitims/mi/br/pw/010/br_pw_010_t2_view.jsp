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
	 	$('.tab_common').find('#RESULT').click(function(e){
	 		var record_id = $('input[name=i_sRecordId]').val();
	 		var prodcut_cd = $('input[name=i_sProductCd]').val();
	 	});

		j$(".li_tab").unbind("click").click(function(event) {
			event.preventDefault();
			
			var frm = j$("form[name='frm']");
			var partno = j$(this).find('a').parent().attr('id');
			
			frm.find("input[name='i_sPartNo']").val(partno);
			frm.attr("action", "/br/pw/010/br_pw_010_t2_view.do").submit();
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
	 	$('.btn_list').click(function(e){
	 		location.href="/br/pw/010/init.do?openMenuCd=MIBRPW010";
	 	});
	 	
	 	j$("#btn_comp_ver").unbind("click").click(function(event){
			event.preventDefault();
			var recordid = j$("input[name='i_sRecordId']").val();
			var productcd = j$("input[name='i_sProductCd']").val();
			var partno = j$("input[name='i_sPartNo']").val();
			var path = "";
			
			path = WebPATH + "br/pw/010/br_pw_010_t2_pop.do?i_sRecordId="+recordid+"&i_sProductCd="+productcd+"&i_sPartNo="+partno;
			
			fn_pop({title:"최종 전성분표 이력", width:700, height: 600, url:path});
// 			fn_pop({title:"최종 전성분표 이력", width:1200, height: 600, url:path});
		});
		
		j$(".fileReq_btn").unbind("click").click(function(e){
			event.preventDefault();
			var recordid = j$("input[name='i_sRecordId']").val();
			var prodcutCd = j$("input[name='i_sProductCd']").val();
			var doccd = j$(this).attr("id");

			var path = WebPATH + "br/pw/020/br_pw_020_file_request_pop.do?i_sRecordId="+recordid+"&i_sProductCd="+prodcutCd+"&i_sDocCd="+doccd+"&i_sNationCd=GL";
			fn_pop({title:"서류 요청",width:800,height:500,url:path});	

		});
 });
 //배합제한
 function fn_popGloblim(id,version){
	var param ={
		i_sConCd: id
		, i_nVerSeq : version
// 		i_sConCd: '111'
// 		, i_nVerSeq : '2'
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
// 		i_sConCd: '111'
// 		, i_nVerSeq : '2'
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

<form id="frm" name="frm" method="post">

	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>	
	<input type="hidden" name="i_iVsn" value="${reqFileVo.n_vsn}" />	
	<input type="hidden" name="i_sReceipStatus" value="${rVo.v_receip_status }" />
	<input type="hidden" name="i_sFinalRst" value="${rVo.v_final_rst }" />
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo}" />	
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>
	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/010/br_pw_010_tab.jsp" %>	
		<div class='sec_cont mt_10'>
			<h2 class="tit">제품 SPEC</h2>
			<ul class="sty_tab ul_tab">
				<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab" id="${vo.n_part_no }">
						<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.n_part_no }</span></a>
					</li>
				</c:forEach>
			</ul>
			<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/cm/br_pw_cm_spec.jsp" %>
		</div>
		<div class="sec_cont mt_00">	
			<table class="sty_02 bdt_n">
				<colgroup>
					<col width="40%" />
<!-- 					<col width="10%" /> -->
					<col width="*" />
				</colgroup>
				<thead>
					<tr>
						<th class="ta_c">Document</th>
<!-- 						<th class="ta_c">서류요청</th> -->
						<th class="ta_c">Download</th>
					</tr>
				</thead>			
				<c:forEach items="${docList }" var="vo">
					<tr class="koDoc">
						<td class="td_bold" style="position: relative;">
							${vo.COMM_CD_NM }
						</td>
<!-- 						<td style="text-align: center;"> -->
<%-- 							<a href="#"  class="btnA bg_dark fileReq_btn" id="${vo.COMM_CD }"> --%>
<!-- 								<span>서류요청</span> -->
<!-- 							</a> -->
<!-- 						</td> -->
						<td class="last">
							<c:choose>
								<c:when test="${vo.COMM_CD eq 'FR020' }">
									<CmTagLib:cmAttachFile uploadCd="PRD" recordId="${reqVo.i_sRecordId}" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="viewLog" />							
								</c:when>
								<c:otherwise>
									<CmTagLib:cmAttachFile uploadCd="${vo.COMM_CD }" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" formName="frm" type="viewLog" />
								</c:otherwise>
							</c:choose>
							
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div class='sec_cont mt_60'>
			<h2 class="tit">Ingredients List</h2>
			<!-- <div class="btn_area" style="text-align: right; margin: -44px 0 0;">
				<a class="btnA bg_dark" id="btn_comp_ver" style="cursor:pointer;"><span>최종 전성분표 이력</span></a>
			</div> -->
			<ul class="sty_tab ul_tab">
				<c:forEach items="${partList }" var="vo" varStatus="s">
					<li class="tab li_tab" id="${vo.n_part_no }">
						<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.n_part_no }</span></a>
					</li>
				</c:forEach>
			</ul>
			<table class="sty_02 table_matrcd" style="word-break:break-all;">
				<colgroup>
	              <!-- <col style="width:30px;display:none;"> -->
	              <col style="width:55px;">
	              <col style="width:100px;">
	              <col style="width:15%;">
	              <col style="width:15%;">
	              <col style="width:15%;">
	              <col style="width:15%;">
	              <col style="width:100px;">
	              <col style="width:100px;">
	              <col style="width:100px;">
	              <col style="width:100px;">
	              <col style="width:60px;">
	              <col style="width:60px;">
	            </colgroup>
				<thead>
					<tr> 
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
						<th class="ta_c">알러젠<br/>여부</th>
					</tr>
				</thead>
				<tbody id="tbody_single">
					<c:set var="sInPerAdd" value=""/>
					<c:set var="sumPerCon" value="0"/>
					<c:set var="cnt" value="1"/>
					<c:if test="${!empty list }">
						<c:set var="tempCnt" value="0"/>
						<c:forEach items="${list }" var="vo" varStatus="status">
							<c:if test="${vo.v_reg_allergenyn ne 'Y' and reqVo.i_sPartNo eq vo.n_part_no}">											
								<tr class="partno partno_${vo.n_part_no }" style="display: ${reqVo.i_sPartNo ne vo.n_part_no ? 'none' : ''}">
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
<!-- 										<div style="width:100%"> -->
<%-- 											<input class="inp_sty01" type="text" name="i_sModiFuncNm_etc_${vo.v_con_cd}_${vo.n_part_no }" value="" size="30" style="margin : 0 auto; margin-top: 10px; display: none;width:100%;"/> --%>
<%-- 											${vo.v_comp_origin_fc } --%>
<!-- 										</div> -->
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
							<td colspan="10"></td>
							<td colspan='2'>
								<span class="span_chk-style">
									<span class="${vo.v_inactive_all eq 'Y' ? 'on' : '' }"></span>&nbsp;Inactive all
								</span>
							</td>
						</tr>
						<tr>
							<th colspan="2"><font color="red"><b>조성함량 합계</b></font></th>
							<td>${sumPer }</td>
							<td colspan="9" style="text-align: left;" class="last td_sum">
								<fmt:formatNumber value="${sumPerCon}" type="number" var="sumPerCon" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>	
								${sumPerCon }
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>			
		</div>
		
		<div class="sec_cont mt_60">
			<h2 class="tit">향료 알러젠</h2><!-- 향료 알러젠 -->
			<table class="sty_02 table_matrcd" style="word-break:break-all;">
				<colgroup>
	              <col style="width:60px;">
	              <col style="width:100px;">
	              <col style="width:15%;">
	              <col style="width:15%;">
	              <col style="width:15%;">
	              <col style="width:15%;">
	              <col style="width:100px;">
	              <col style="width:100px;">
	              <col style="width:100px;">
	              <col style="width:60px;">
	              <col style="width:60px;">
				</colgroup>
				<thead>
					<tr class="start">
						<th class="ta_c">No.</th>
						<th class="ta_c">성분<br/>코드</th>
						<th class="ta_c">표시명<br/>(영문)</th>
						<th class="ta_c">조성<br/>함량</th>
						<th class="ta_c">Function</th>
						<th class="ta_c">Allergen Func.</th>
						<th class="ta_c">CAS No.</th>
						<th class="ta_c">사용금지</th>
						<th class="ta_c">배합제한</th>
						<th class="ta_c">Active/<br/>Inactive</th>
						<th class="ta_c">알러젠<br/>여부</th>
					</tr>
				</thead>
				<tbody id="tbody_single">
					<c:set var="sInPerAdd" value=""/>
					<c:set var="sumPer" value="0"/>
					<c:set var="cnt" value="1"/>
					<c:if test="${!empty list }">
						<c:forEach items="${list }" var="vo" varStatus="status">
							<c:if test="${vo.v_reg_allergenyn eq 'Y' and reqVo.i_sPartNo eq vo.n_part_no}">
								<tr class="partno partno_${vo.n_part_no }" style="display: ${reqVo.i_sPartNo ne vo.n_part_no ? 'none' : ''}">
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
<!-- 										<div> -->
<%-- 											${vo.v_comp_origin_fc } --%>
<!-- 										</div> -->
									</td>
									<td class="ta_c">
										${vo.v_allergen_func }
									</td>
									<td class="ta_c">${vo.v_odm_casno }</td>
									<td class="ta_c"><a href="javascript:fn_popGlob('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${vo.v_zglobal }</span></a></td>
									<td class="ta_c"><a href="javascript:fn_popGloblim('${vo.v_con_cd}', '${vo.n_concd_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${vo.v_zgllim  }</span></a></td>								
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
							<td colspan="9"></td>
							<td colspan="2">
								<span class="span_chk-style">
									<span class="${vo.v_inactive_all eq 'Y' ? 'on' : '' }"></span>&nbsp;Inactive all
								</span>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		
	</div>	
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
