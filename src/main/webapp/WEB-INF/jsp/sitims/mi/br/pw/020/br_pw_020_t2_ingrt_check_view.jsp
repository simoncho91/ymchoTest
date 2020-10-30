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
		
		j$(".btn_detail").unbind("click").click(function(event) {
			var id = j$(this).attr("id");
			var frm = j$("form[name='frm']");
			
			if(id == "tab1") {
				$('input[name=i_sDivision]').val('SINGLE');
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_fomula_view.do").submit();
			} else if(id == "tab2") {
				$('input[name=i_sDivision]').val('COMPLEX');
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_fomula_view.do").submit();
			} else if(id == "tab3") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_ingrt_check_view.do").submit();
			}			
		});

	 	$('.btn_list').click(function(e){
	 		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
	 	});
				
		j$(".i_sChkItem").unbind("click").click(function() {
			var id = this.value;
			var tableWidth = j$(".table_ingrt_view").width();
			var td_width = j$(".td_" + id).width();
			
			var all = j$("input:checkbox[class=i_sChkItem]").length;
			var checked = j$("input:checkbox[class=i_sChkItem]:checked").length;
			var unchecked = all - checked + 1;
	
			if(j$(this).is(":checked")) {
				
				var new_width = 0;
				new_width = tableWidth + td_width;
				j$(".td_" + id).css("width", td_width + "px");
				j$(".td_" + id).show();
				
				j$(".space").attr("colspan",unchecked);
				
// 				j$(".table_ingrt_view").css("width", new_width + "px");
			} else {
				var new_width = 0;
				new_width = tableWidth - td_width;
				j$(".td_" + id).hide();
	
				j$(".space").attr("colspan",unchecked);
				
// 				j$(".table_ingrt_view").css("width", new_width + "px");
			}
		});
		
		j$(".i_sChkNation").unbind("click").click(function() {
			var id = this.value;
			
			if(j$(this).is(":checked")) {
				j$(".span_forbid_" + id).show();
				j$(".span_limit_" + id).show();
				j$(".span_lmDetail_" + id).show();
				j$(".span_limitCt_" + id).show();
				j$(".span_warning_" + id).show();
				j$(".span_warningKo_" + id).show();
				j$(".span_detailTrans_" + id).show();
				j$(".span_NHPID_" + id).show();
				j$(".span_useRange_" + id).show();
				j$(".span_remark_" + id).show();
			} else {
				j$(".span_forbid_" + id).hide();
				j$(".span_limit_" + id).hide();
				j$(".span_lmDetail_" + id).hide();
				j$(".span_limitCt_" + id).hide();
				j$(".span_warning_" + id).hide();
				j$(".span_warningKo_" + id).hide();
				j$(".span_detailTrans_" + id).hide();
				j$(".span_NHPID_" + id).hide();
				j$(".span_useRange_" + id).hide();
				j$(".span_remark_" + id).hide();
			}
		});
		addInputChkRadioEvent();
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
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_sPartNo" value="" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_iVsn" value="${reqVo.n_vsn}" />	
	<input type="hidden" name="i_sReceipStatus" value="" />
	<input type="hidden" name="i_sGlbRecordid" value="${rVo.v_refer_record_id}" />	
	
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>
	<div class="pd_top10"></div>	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
	<div class="pd_top10"></div>
		
	<ul class="sty_tab">
		<li class="tab"><a href="#" class="btn_detail" id="tab1"><span>IL</span></a></li>
		<li class="tab"><a href="#" class="btn_detail" id="tab2"><span>QQ</span></a></li>
		<li class="tab"><a href="#" class="btn_detail on" id="tab3"><span>성분검토</span></a></li>
	</ul>
	<div class="sec_cont mt_00">

<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->
		<!-- <div class="pd_top10"></div> -->
		<div class="table_all">			
			<ul class="sty_tab">
				<c:forEach items="${partList }" var="vo">
					<li class="tab li_tab" id="${vo.v_partno }">
						<a href="#" class="${reqVo.i_sPartNo eq vo.v_partno ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
					</li>
				</c:forEach>
			</ul>
			
			<!-- <div class="pd_top10"></div> -->
			
			<table class="sty_03">
				<colgroup>
					<col width="15%"/>
					<col width="85%"/>
				</colgroup>
				<tbody>
				<tr>
					<th class="bdl_n">항목명</th>
					<td class="ta_l">
						<span class="chk-style">
							<label for="i_sChkItem_concd">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_concd" value="concd" checked="checked"/></span>&nbsp;성분 코드
							</label>
						</span>
						<span class="chk-style">
							<label for="i_sChkItem_connm">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_connm" value="connm" checked="checked"/></span>&nbsp;INCI Name
							</label>
						</span>
						<span class="chk-style">
							<label for="i_sChkItem_con_per">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_con_per" value="con_per" checked="checked"/></span>&nbsp;조성함량
							</label>
						</span>
						<span class="chk-style">
							<label for="i_sChkItem_fc">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_fc" value="fc" checked="checked"/></span>&nbsp;Function
							</label>
						</span>
						<span class="chk-style">
							<label for="i_sChkItem_allergen_fc">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_allergen_fc" value="allergen_fc" checked="checked"/></span>&nbsp;Allergen Func.
							</label>
						</span>
						<span class="chk-style">
							<label for="i_sChkItem_casno">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_casno" value="casno" checked="checked"/></span>&nbsp;CAS No.
							</label>
						</span><br/>
						<span class="chk-style">
							<label for="i_sChkItem_activeyn">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_activeyn" value="activeyn" checked="checked"/></span>&nbsp;Active/Inactive
							</label>
						</span>
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_mipn"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_mipn" value="mipn" checked="checked"/></span>&nbsp;Medicinal ingredient preferred name -->
<!-- 							</label> -->
<!-- 						</span> -->
						<span class="chk-style">
							<label for="i_sChkItem_forbid">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_forbid" value="forbid" checked="checked"/></span>&nbsp;금지
							</label>
						</span>
						<span class="chk-style">
							<label for="i_sChkItem_limit">
								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_limit" value="limit" checked="checked"/></span>&nbsp;제한
							</label>
						</span>
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_lmDetail"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_lmDetail" value="lmDetail" checked="checked"/></span>&nbsp;제한 상세 -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_lmCt"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_lmCt" value="limitCt" checked="checked"/></span>&nbsp;제한함량 -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_warning"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_warning" value="warning" checked="checked"/></span>&nbsp;Warning -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_warningKo"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_warningKo" value="warningKo" checked="checked"/></span>&nbsp;Warning 번역 -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_detailTrans"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_detailTrans" value="detailTrans" checked="checked"/></span>&nbsp;Detail 번역 -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_remark"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_remark" value="remark" checked="checked"/></span>&nbsp;비고 -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_NHPID"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_NHPID" value="NHPID" checked="checked"/></span>&nbsp;NHPID -->
<!-- 							</label> -->
<!-- 						</span> -->
<!-- 						<span class="chk-style"> -->
<!-- 							<label for="i_sChkItem_useRange"> -->
<!-- 								<span><input type="checkbox" class="i_sChkItem" id="i_sChkItem_useRange" value="useRange" checked="checked"/></span>&nbsp;사용범위 -->
<!-- 							</label> -->
<!-- 						</span> -->
					</td>
				</tr>
				<tr style="display:none">
					<th>국가</th>
					<td class="last">
						<c:forEach items="${expList }" var="vo" varStatus="s">
							<span class="chk-style">
								<label for="i_sChkNation_${vo.v_sub_code }">
									<span><input type="checkbox" class="i_sChkNation" id="i_sChkNation_${vo.v_sub_code }" value="${vo.v_sub_code }"  ${(reqVo.i_sExpDiv eq vo.v_sub_code)or(reqVo.i_sExpDiv eq '')? 'checked':''}/></span>&nbsp;${vo.v_sub_codenm } <!-- 캐나다 -->
								</label>
							</span>
						</c:forEach>
					</td>
				</tr>
				</tbody>
			</table>
			
			<!-- <div class="pd_top10"></div> -->
			
			<div class="sec_cont mt_20" style="width:100%;overflow-x: auto;">
				<table class="sty_02 table_ingrt_view" style="">
					<colgroup>
<!-- 						<col width="36px"/>            							                                      -->
						<col width="85px"/>            							                                         
						<col width="15%"/>           							                                     
						<col width="130px"/>									                                         	
						<col width="15%"/>           							                                     
						<col width="15%"/>           							                                     
						<col width="130px"/>									                                     	
						<col width="130px"/>           							                                     
<!-- 						<col width="185px"/>                         -->
						<col width="185px"/>									                                       	
						<col width="185px"/>           							                                       
<!-- 						<col width="260px"/>     -->
<!-- 						<col width="120px"/>     -->
<!-- 						<col width="260px"/>     -->
<!-- 						<col width="260px"/>     -->
<!-- 						<col width="260px"/>     -->
<!-- 						<col width="260px"/>     -->
<!-- 						<col width="160px"/>     -->
<!-- 						<col width="160px"/>	 -->
					</colgroup>
					<thead>
						<tr class="start">
<!-- 							<th>No.</th> -->
							<th class="ta_c td_concd">성분<br/>코드</th>
							<th class="ta_c td_connm">INCI Name</th>
							<th class="ta_c td_con_per">조성<br/>함량</th>
							<th class="ta_c td_fc">Function</th>
							<th class="ta_c td_allergen_fc">Allergen Func.</th>
							<th class="ta_c td_casno">CAS No.</th>
							<th class="ta_c td_activeyn">Active<br/>/Inactive</th>
<!-- 							<th class="ta_c td_mipn">Medicinal<br/>ingredient<br/>preferred name</th> -->
							<th class="ta_c td_forbid">금지</th>
							<th class="ta_c td_limit">제한</th>
							<th class="ta_c space"></th>
<!-- 							<th class="ta_c td_lmDetail">제한 상세</th> -->
<!-- 							<th class="ta_c td_limitCt">제한 함량</th> -->
<!-- 							<th class="ta_c td_warning">Warning</th> -->
<!-- 							<th class="ta_c td_warningKo">Warning번역</th> -->
<!-- 							<th class="ta_c td_detailTrans">Detail번역</th> -->
<!-- 							<th class="ta_c td_NHPID">NHPID</th> -->
<!-- 							<th class="ta_c td_useRange">사용범위</th> -->
<!-- 							<th class="ta_c td_remark last">비고</th> -->
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty ingrtList }">
								<c:set var="cnt" value="1"/>
								<c:forEach items="${ingrtList }" var="vo" varStatus="s">
									<c:if test="${vo.v_allergen_yn ne 'Y' }">
										<tr>
<%-- 											<td>${cnt }</td> --%>
											<td class="ta_c td_concd">${vo.v_con_cd }</td>
											<td class="ta_c td_connm">${vo.v_con_nm_en }</td>
											<td class="ta_c td_con_per">${vo.v_con_in_per }</td>
											<td class="ta_c td_fc">${vo.v_comp_origin_fc }</td>
											<td class="ta_c td_allergen_fc">${vo.v_allergen_func }</td>
											<td class="ta_c td_casno">${vo.v_odm_casno }</td>
											<td class="ta_c td_activeyn">
												<span class="span_chk-style">
													<span class="${vo.v_active_yn eq 'Y' ? 'on' : '' }"></span>
												</span>
											</td>
<!-- 											<td class="td_mipn"></td> -->
											<td class="ta_c td_forbid">
												<c:forEach items="${ingrtSubList }" var="svo">
													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_glb_forbid }">
														<%-- <span class="span_forbid_${svo.v_gl_cd }" style="cursor: pointer; margin-bottom: 5px; display: block">${svo.v_glb_forbid }<br/></span> --%>
														<a href="javascript:fn_popGlob('${svo.v_con_cd}', '${svo.n_concd_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${svo.v_glb_forbid}</span></a>
													</c:if>
												</c:forEach>
											</td>
											<td class="ta_c td_limit left2">
												<c:forEach items="${ingrtSubList }" var="svo">
													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_glb_limit }">
														<%-- <p class="span_limit_${svo.v_gl_cd }" style="cursor: pointer; margin-bottom: 5px; display: block"><font style="font-weight: bold;">${svo.v_glb_limit }</font><br/></p> --%>
														<a href="javascript:fn_popGloblim('${svo.v_con_cd}', '${svo.n_concd_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${svo.v_glb_limit}</span></a>
													</c:if>
												</c:forEach>	
											</td>
<!-- 											<td class="ta_c td_lmDetail left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_glb_lm_detail }"> --%>
<%-- 														<span class="span_lmDetail_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_glb_lm_detail }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_limitCt left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_limit_ct }"> --%>
<%-- 														<span class="span_limitCt_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_limit_ct }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_warning left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_warning }"> --%>
<%-- 														<span class="span_warning_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_warning }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_warningKo left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_warning_ko }"> --%>
<%-- 														<span class="span_warningKo_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_warning_ko }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_detailTrans left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_detail_trans }"> --%>
<%-- 														<span class="span_detailTrans_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_detail_trans }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_NHPID left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_nhpid }"> --%>
<%-- 														<span class="span_NHPID_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_nhpid }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_useRange left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_usage }"> --%>
<%-- 														<span class="span_useRange_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_usage }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_remark left2 last"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_remark }"> --%>
<%-- 														<span class="span_remark_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_remark }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
										<td class="ta_c space"></td>
										</tr>
										<c:set var="cnt" value="${cnt+1 }"/>
									</c:if>
								</c:forEach>
								<tr>
									<td></td>
									<td class="ta_c td_concd"></td>
									<td class="ta_c td_connm"></td>
									<td class="ta_c td_con_per"></td>
									<td class="ta_c td_fc"></td>
									<td class="ta_c td_allergen_fc"></td>
									<td class="ta_c td_casno"></td>
									<td class="ta_c td_activeyn">
										<span class="span_chk-style">
											<span class="${rvo.v_inactive_all eq 'Y' ? 'on' : '' }"></span>&nbsp;<font style="font-weight: bold;">Inactive all</font>
										</span>
									</td>
<!-- 									<td class="ta_c td_mipn"></td> -->
									<td class="ta_c td_forbid"></td>
									<td class="ta_c td_limit"></td>
<!-- 									<td class="ta_c td_lmDetail"></td> -->
<!-- 									<td class="ta_c td_limitCt"></td> -->
<!-- 									<td class="ta_c td_warning"></td> -->
<!-- 									<td class="ta_c td_warningKo"></td> -->
<!-- 									<td class="ta_c td_detailTrans"></td> -->
<!-- 									<td class="ta_c td_NHPID"></td> -->
<!-- 									<td class="ta_c td_useRange"></td> -->
<!-- 									<td class="ta_c td_remark last"></td> -->
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="10" class="ta_c">:: 해당하는/등록된 내용이 없습니다.::</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>		      
			</div>
			
			<!-- <div class="pd_top10"></div> -->
		
			<!-- <div class="subtitle">향료 알러젠</div> --><!-- 향료 알러젠 -->
			
			<div class="sec_cont mt_60" style="width:100%;overflow-x: auto;">
				<h2 class="tit">향료 알러젠</h2>
				<table class="sty_02 table_ingrt_view" style="">
					<colgroup>
<!-- 						<col width="36px"/>             							                    -->
						<col width="85px"/>             							                       
						<col width="15%"/>            							                   
						<col width="122px"/>										                       	
						<col width="15%"/>            							                   
						<col width="15%"/>            							                   
						<col width="122px"/>										                   	
						<col width="122px"/>            							                   
<!-- 						<col width="185px"/>                     -->
						<col width="185px"/>										                     	
						<col width="185px"/>            							                     
<!-- 						<col width="260px"/>                                                       -->
<!-- 						<col width="120px"/>              -->
<!-- 						<col width="260px"/>             -->
<!-- 						<col width="260px"/>             -->
<!-- 						<col width="260px"/>             -->
<!-- 						<col width="260px"/>             -->
<!-- 						<col width="160px"/>              -->
<!-- 						<col width="160px"/>											 -->
					</colgroup>		
					<thead>
						<tr class="start">
<!-- 							<th>No.</th> -->
							<th class="ta_c td_concd">성분<br/>코드</th>
							<th class="ta_c td_connm">INCI Name</th>
							<th class="ta_c td_con_per">조성<br/>함량</th>
							<th class="ta_c td_fc">Function</th>
							<th class="ta_c td_allergen_fc">Allergen Func.</th>
							<th class="ta_c td_casno">CAS No.</th>
							<th class="ta_c td_activeyn">Active<br/>/Inactive</th>
<!-- 							<th class="ta_c td_mipn">Medicinal<br/>ingredient<br/>preferred name</th> -->
							<th class="ta_c td_forbid">금지</th>
							<th class="ta_c td_limit">제한</th>
							<th class="ta_c space"></th>
<!-- 							<th class="ta_c td_lmDetail">제한 상세</th> -->
<!-- 							<th class="ta_c td_limitCt">제한 함량</th> -->
<!-- 							<th class="ta_c td_warning">Warning</th> -->
<!-- 							<th class="ta_c td_warningKo">Warning번역</th> -->
<!-- 							<th class="ta_c td_detailTrans">Detail번역</th> -->
<!-- 							<th class="ta_c td_NHPID">NHPID</th> -->
<!-- 							<th class="ta_c td_useRange">사용범위</th> -->
<!-- 							<th class="ta_c td_remark last">비고</th> -->
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${!empty ingrtList }">
								<c:set var="cnt" value="1"/>
								<c:forEach items="${ingrtList }" var="vo" varStatus="s">
									<c:if test="${vo.v_allergen_yn eq 'Y' }">
										<tr>
<%-- 											<td>${cnt }</td> --%>
											<td class="ta_c td_concd">${vo.v_con_cd }</td>
											<td class="ta_c td_connm">${vo.v_con_nm_en }</td>
											<td class="ta_c td_con_per">${vo.v_con_in_per }</td>
											<td class="ta_c td_fc">${vo.v_comp_origin_fc }</td>
											<td class="ta_c td_allergen_fc">${vo.v_allergen_func }</td>
											<td class="ta_c td_casno">${vo.v_odm_casno}</td>
											<td class="ta_c td_activeyn">
												<span class="span_chk-style">
													<span class="${vo.v_active_yn eq 'Y' ? 'on' : '' }"></span>
												</span>
											</td>
<!-- 											<td class="ta_c td_mipn"></td> -->
											<td class="ta_c td_forbid">
												<c:forEach items="${ingrtSubList }" var="svo">
													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_glb_forbid }">
														<%-- <span class="span_forbid_${svo.v_gl_cd }" style="display: block">${svo.v_glb_forbid }<br/></span> --%>
														<a href="javascript:fn_popGlob('${svo.v_con_cd}', '${svo.n_concd_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${svo.v_glb_forbid}</span></a>
													</c:if>
												</c:forEach>
											</td>
											<td class="ta_c td_limit left2">
												<c:forEach items="${ingrtSubList }" var="svo">
													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_glb_limit }">
														<%-- <p class="span_limit_${svo.v_gl_cd }" style="cursor: pointer; margin-bottom: 5px; display: block"><font style="font-weight: bold;">${svo.v_glb_limit }</font><br/></p> --%>
														<a href="javascript:fn_popGloblim('${svo.v_con_cd}', '${svo.n_concd_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${svo.v_glb_limit}</span></a>
													</c:if>
												</c:forEach>	
											</td>
<!-- 											<td class="ta_c td_lmDetail left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_glb_lm_detail }"> --%>
<%-- 														<span class="span_lmDetail_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_glb_lm_detail }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_limitCt left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_limit_ct }"> --%>
<%-- 														<span class="span_limitCt_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_limit_ct }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_warning left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_warning }"> --%>
<%-- 														<span class="span_warning_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_warning }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_warningKo left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_warning_ko }"> --%>
<%-- 														<span class="span_warningKo_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_warning_ko }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_detailTrans left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_detail_trans }"> --%>
<%-- 														<span class="span_detailTrans_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_detail_trans }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_NHPID left2"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_nhpid }"> --%>
<%-- 														<span class="span_NHPID_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_nhpid }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_useRange left2">  -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_usage }"> --%>
<%-- 														<span class="span_useRange_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_usage }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
<!-- 											<td class="ta_c td_remark left2 last"> -->
<%-- 												<c:forEach items="${ingrtSubList }" var="svo"> --%>
<%-- 													<c:if test="${vo.v_con_cd eq svo.v_con_cd and !empty svo.v_remark }"> --%>
<%-- 														<span class="span_remark_${svo.v_gl_cd }" style="display: ${reqVo.i_sExpDiv eq svo.v_gl_cd or reqVo.i_sExpDiv eq ''? 'block':'none'}"><font style="font-weight: bold;">[${svo.v_gl_cdnm }]</font> ${svo.v_remark }<br/></span> --%>
<%-- 													</c:if> --%>
<%-- 												</c:forEach>	 --%>
<!-- 											</td> -->
												<td class="space"></td>
										</tr>
										<c:set var="cnt" value="${cnt+1 }"/>
									</c:if>
								</c:forEach>
									<tr>
<!-- 										<td></td> -->
										<td class="ta_c td_concd"></td>
										<td class="ta_c td_connm"></td>
										<td class="ta_c td_con_per"></td>
										<td class="ta_c td_fc"></td>
										<td class="ta_c td_allergen_fc"></td>
										<td class="ta_c td_casno"></td>
										<td class="ta_c td_activeyn">
											<span class="span_chk-style">
												<span class="${rvo.v_inactive_all eq 'Y' ? 'on' : '' }"></span>&nbsp;<font style="font-weight: bold;">Inactive all</font>
											</span>
										</td>
<!-- 										<td class="ta_c td_mipn"></td> -->
										<td class="ta_c td_forbid"></td>
										<td class="ta_c td_limit"></td>
<!-- 										<td class="ta_c td_lmDetail"></td> -->
<!-- 										<td class="ta_c td_limitCt"></td> -->
<!-- 										<td class="ta_c td_warning"></td> -->
<!-- 										<td class="ta_c td_warningKo"></td> -->
<!-- 										<td class="ta_c td_detailTrans"></td> -->
<!-- 										<td class="ta_c td_NHPID"></td> -->
<!-- 										<td class="ta_c td_useRange"></td> -->
<!-- 										<td class="ta_c td_remark last"></td> -->
									</tr>
							</c:when>
							<c:otherwise>
									<tr>
										<td colspan="10" class="ta_c">:: 해당하는/등록된 내용이 없습니다.::</td>
									</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		
		<ul class="btn_area">
			<li class="right">
				<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
					<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
				</c:if>
			</li>
		</ul>
	</div>
</form>



<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
