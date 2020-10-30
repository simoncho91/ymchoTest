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
		addInputChkRadioEvent();
	 	$('.tab_common').find('#RESULT').click(function(e){
	 		var record_id = $('input[name=i_sRecordId]').val();
	 		var prodcut_cd = $('input[name=i_sProductCd]').val();
	 	});

	 	$('.btn_list').click(function(e){
	 		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
	 	});

		j$(".li_tab").unbind("click").click(function(event) {
			event.preventDefault();
			
			var id = this.id;
			j$("form[name='frm']").find("input[name='i_sPartNo']").val(id);
			
			j$(".li_tab a").removeClass("on");
			j$(this).find('a').addClass("on");

			j$("form[name='frm']").attr("action", "/br/pw/020/br_pw_020_t2_fomula_view.do").submit();
			//fnPartAjax("conf");
			//glbDocInciView.fn.fnPartAjax("conf");
		});
		
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
		
		j$(".btn_detail").unbind("click").click(function(event) {
			var id = j$(this).attr("id");
			var frm = j$("form[name='frm']");
			
			if(id == "tab1") {
				$('input[name=i_sDivision]').val('SINGLE');
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_fomula_view.do").submit();
			} else if(id == "tab2") {
				$('input[name=i_sDivision]').val('COMPLEX');
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_complex_fomula_view.do").submit();
			} else if(id == "tab3") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_ingrt_check_view.do").submit();
			}			
		});
		
// 		j$(".a_modi_func_his").unbind("click").click(function(event) {
// 			event.preventDefault();
// 			var recordid = $("input[name=i_sRecordId]").val();
// 			var productId = $("input[name=i_sProductCd]").val();
// 			var partno = $(".li_tab").find('.on').parent().attr("id");
// 			var div = $("input[name=i_sDivision]").val();

// 			var option = {
// 				url:"/br/pw/020/br_pw_020_func_log_pop.do?&i_sRecordId="+recordid+"&i_sProductCd="+productId+"&i_sPartNo="+partno+"&i_sDivision="+div
// 				, title : "수정이력"
// 				, width : "850"
// 				, height : "600"
// 			}
// 			fn_pop(option);	 
			
// 		});
// 		j$(".a_modi_func").unbind("click").click(function(event) {
// 			event.preventDefault();
// 			$('input[name=i_sPartNo]').val($(".li_tab").find('.on').parent().attr("id"));
// 			$.ajax({
// 				url:"/br/pw/020/br_pw_020_modify_func.do"
// 				,type:"POST"
// 				,data : j$("form[name='frm']").serialize()
// 				,success : function(data){
// 					location.reload();
// 				},error : function(jqXHR, textStatus, errorThrown){
// 			        fn_s_failMsg(jqXHR, textStatus, errorThrown);
// 			        if(option.fail) option.fail();				
// 				}
// 			});			
// 		});
		
		//j$(".a_modi_func_his").unbind("click").click(function(){
			
		//	var param ={
		//		i_sDivision: j$("input[name='i_sDivision']").val()
		//		, i_sGlbRecordid : j$("input[name='i_sGlbRecordid']").val()
		//		, i_sPartNo : j$(".li_tab.selected").attr("id")
		//	};
		//	var option = {
		//		url:"/cm/pop/cm_doc_function_history_pop.do?i_sCmFunction=setPopUpData&i_sInput=&param="+encodeURI(JSON.stringify(param))
		//		, title : "Function 수정 History"
		//		, width : "500"
		//		, height : "600"
		//	}
		//	fn_pop(option);
		//});
		
		j$("#a_update_CAS").unbind("click").click(function(event){
			event.preventDefault();
			var frm = j$("#frm");
			var param = {
				i_sProductCd : frm.find("input[name='i_sProductCd']").val()
				, i_iVerSeq : frm.find("input[name='i_iVsn']").val()
			};
			$.ajax({
				url:"/br/pw/020/br_pw_020_cas_update.do"
				,type:"POST"
				,data : param
				,success : function(data){
					console.log(data);
				},error : function(jqXHR, textStatus, errorThrown){
			        fn_s_failMsg(jqXHR, textStatus, errorThrown);
				}
			});
		});
 });

function fnPartAjax(flag) {
	j$("form[name='frm']").find("input[name='i_sAjaxFlag']").val(flag);
	
	$.ajax({
		url :"/br/pw/020/br_pw_020_t2_component_ajax.do"
		, type : "POST"
		, data : j$("form[name='frm']").serialize()
		, dataType : "html"
		//, isBlock : true
		, success : function(html) {
			
			var claimHtml = j$("#div_claim_frag").html();

			j$("#div_ajax").html(html);
			
			if(flag == "div") {
				j$("#div_claim_frag").html(claimHtml);
				
				var division = j$("form[name='frm']").find("input[name='i_sDivision']").val();
				
				if(division == "SINGLE") {
					j$("#div_allergen_area").show();
				} else {
					j$("#div_allergen_area").hide();
				}
			}
			addInputChkRadioEvent();
			//glbDocInciView.fn.addBtnEvent();
		},error : function(jqXHR, textStatus, errorThrown){
	        fn_s_failMsg(jqXHR, textStatus, errorThrown);
		}
	});
}
 
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
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_iVsn" value="${rVo.n_vsn}" />	
	<input type="hidden" name="i_sReceipStatus" value="" />
	<input type="hidden" name="i_sGlbRecordid" value="${rVo.v_refer_record_id}" />	
	<input type="hidden" name="i_sPartNo" value="" />
	<input type="hidden" name="i_sAjaxFlag" value="" />	
	
	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>
	<div class="pd_top10"></div>	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
	<div class="pd_top10"></div>
	
	<ul class="sty_tab">
		<li class="tab"><a href="#" class="btn_detail ${reqVo.i_sDivision eq 'SINGLE' ? 'on' : '' }" id="tab1"><span>IL</span></a></li>
		<li class="tab"><a href="#" class="btn_detail ${reqVo.i_sDivision eq 'COMPLEX' ? 'on' : '' }" id="tab2"><span>QQ</span></a></li>
		<li class="tab"><a href="#" class="btn_detail" id="tab3"><span>성분검토</span></a></li>
	</ul>
<!-- 	<ul class="btn_area"> -->
<!-- 		<li class="right"> -->
<!-- 			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a> -->
<!-- 		</li> -->
<!-- 	</ul> -->
				
	<div id="div_ajax">			
		<c:choose>			
			<c:when test="${reqVo.i_sDivision eq 'COMPLEX' }">
				<div class='sec_cont'>
					<h2 class="tit">수출 검토</h2>
					
					<ul class=sty_tab>
						<c:forEach items="${partList }" var="vo">
							<li class="tab li_tab" id="${vo.n_part_no }">
								<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
							</li>
						</c:forEach>
					</ul>
					<table class="sty_02 table_matrcd" style="word-break:break-all;">
						<colgroup>
<!-- 							<col width="30"/>                                                       -->
<!-- 							<col width="30"/>                                                       -->
							<col width="60"/>                                                      
							<col width="60"/>                                                      
							<col width="200"/>                                                     
							<col width="100"/>                                                     
							<col width="100"/>                                                     
							<col width="100"/>                                                     
							<col width="200"/>                                                     
							<col width="50"/>                                                      
							<col width="50"/>                                                      
							<col width="50"/>
							<col width="150"/>                                                      
							<col width="40"/>
							<col width="40"/>
						</colgroup>
						<thead>
							<tr>
<!-- 								<th class="start"></th> -->
<!-- 								<th>No.</th> -->
								<th>원료코드</th>
								<th>성분 코드</th>
								<th>INCI Name</th>
								<th>Wt(%)</th>
								<th>mixure ratio</th>
								<th>Actual<br/> Wt(%)</th>
								<th>Function</th>
								<th>사용금지</th>
								<th>배합제한</th>							
								<th>Safety_ODM standard</th>
								<th>첨부파일</th>
								<th>COA</th>
								<th>SPEC</th>
							</tr>
						</thead>
						<tbody id="tbody_complex">
							<c:set var="sInPerAdd" value="0"/>
							<c:set var="sPerAdd" value="0"/>
							<fmt:formatNumber value="${sInPerAdd}" type="number" var="sInPerAdd" maxFractionDigits="7"/>
							<fmt:formatNumber value="${sPerAdd}" type="number" var="sPerAdd" maxFractionDigits="7"/>
							<c:set var="tempCd" value="#"/>
							<c:set var="tempCnt" value="0"/>
							<c:if test="${!empty conList }">
								<c:forEach items="${conList }" var="vo">
									<c:if test="${vo.v_allergen_yn ne 'Y' }">
										<c:if test="${tempCd ne vo.v_raw_cd}">
											<c:set var="tempCnt" value="${tempCnt + 1 }"/>
										</c:if>
										<tr class="comp_partno comp_partno_${vo.n_part_no }" style="${!empty vo.v_tdd_danger ? 'background-color:#CFE8D8;' : ''}">
										<c:if test="${tempCd ne vo.v_raw_cd}">
<%-- 											<td class="start" rowspan="${vo.n_raw_cnt}"> --%>
<!-- 												<span class="chk-style"> -->
<%-- 													<label for="i_arrModifyChk_${tempCnt}_${vo.n_part_no }"> --%>
<!-- 														<span> -->
<%-- 															<input type="checkbox" name="i_arrModifyChk" id="i_arrModifyChk_${tempCnt}_${vo.n_part_no }" value="${vo.v_raw_cd}"/> --%>
<!-- 														</span> -->
<!-- 													</label> -->
<!-- 												</span> -->
<!-- 											</td> -->
<%-- 											<td class="tr_comp_count_${vo.n_part_no }" rowspan="${vo.n_raw_cnt}">${tempCnt}</td>	 --%>
										</c:if>
										<c:if test="${tempCd ne vo.v_raw_cd}">
											<td class="td_rawcd_${vo.n_part_no }" rowspan="${vo.n_raw_cnt}">
												${vo.v_raw_cd}
												<c:if test="${vo.v_type eq 'AP' }">
														<p style="padding-top:3px; padding-bottom:3px; color:#8c8c8c; font-weight: bold;">(AP 사급원료)</p>
												</c:if>
											</td>
										</c:if>
											<td>${vo.v_con_cd }</td>
											<td>${vo.v_con_nm_en }</td>
										<c:if test="${tempCd ne vo.v_raw_cd}">
											<td rowspan="${vo.n_raw_cnt }">
												${vo.v_raw_per}
												<span class="span_hide span_per">${vo.v_raw_per }</span>
												<c:set var="sInPerAdd" value="${sInPerAdd + vo.v_raw_per }"/>
											</td>
										</c:if>	
											<td>${vo.v_con_in_per }</td>
											<td>
												${vo.v_actual_wt }
												<span class="span_hide span_per_at">${vo.v_actual_wt }</span>
												<c:set var="sPerAdd" value="${sPerAdd + vo.v_actual_wt }"/>
											</td>
										<c:if test="${tempCd ne vo.v_raw_cd}">
											<td rowspan="${vo.n_raw_cnt }">
												<c:choose>
													<c:when test="${vo.v_subrawcd_yn ne 'Y'}">
														${vo.v_comp_fc_nm }
<%-- 														<select class="select_modiFunc select_sty01" name="i_sModiFuncNm_${vo.v_raw_cd}_${vo.n_part_no }" style="width:80%;"> --%>
<%-- 															<c:set  var="etc_yn" value="Y"/> --%>
<%-- 															<c:forEach items="${fcList }" var="fvo"> --%>
<%-- 																<c:if test="${!empty vo.v_raw_cd and fvo.v_raw_cd eq vo.v_raw_cd}"> --%>
<%-- 																	<option value="${fvo.v_raw_cd }" ${vo.v_comp_fc_nm eq fvo.v_func_id_en ? 'selected=\'selected\'' : '' }>${fvo.v_func_id_en }</option> --%>
<%-- 																	<c:if test="${vo.v_comp_fc_nm eq fvo.v_func_id_en }"> --%>
<%-- 																		<c:set  var="etc_yn" value="N"/> --%>
<%-- 																	</c:if> --%>
<%-- 																</c:if> --%>
<%-- 															</c:forEach> --%>
<%-- 															<option value="ETC" ${vo.v_etcyn eq 'Y' or etc_yn eq 'Y'? 'selected=\'selected\'':''}>기타</option> --%>
<!-- 														</select> -->
<%-- 														(${vo.v_comp_fc_nm_ch })<br/> --%>
<!-- 														<div style="text-align: center;width:100%;"> -->
<%-- 															<input type="text" class="inp_sty01" name="i_sModiFuncNm_etc_${vo.v_raw_cd}_${vo.n_part_no }" value="${vo.v_etc_yn eq 'Y' or etc_yn eq 'Y'? vo.v_comp_fc_nm:''}" size="30" style="margin : 0 auto; margin-top: 10px; display: ${vo.v_etc_yn eq 'Y' or etc_yn eq 'Y'? 'block':'none'};"/> --%>
<%-- 															<input type="hidden" name="i_sBeforeFuncNm_${vo.v_raw_cd}_${vo.n_part_no }" value="${vo.v_comp_fc_nm }"/> --%>
<!-- 														</div> -->
													</c:when>
													<c:otherwise>
														${vo.v_comp_fc_nm }
<%-- 														(${vo.v_comp_fc_nm_ch }) --%>
													</c:otherwise>
												</c:choose>
											</td>
										</c:if>
											<td>
												<a href="javascript:fn_popGlob('${vo.v_con_cd}', '${vo.n_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${vo.v_zglobal}</span></a>													
											</td>
											<td>
												<a href="javascript:fn_popGloblim('${vo.v_con_cd}', '${vo.n_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${vo.v_zgllim}</span></a>
											</td>
											<td> ${vo.v_remark }</td>
											
										<c:if test="${tempCd ne vo.v_raw_cd}">
											<td rowspan="${vo.n_raw_cnt}">
											<c:if test="${!empty vo.v_atta_file }">
												<c:set var="arrAttaFile" value="${fn:split(vo.v_atta_file, '/') }"/>
												<c:forEach items="${arrAttaFile}" var="cvo">
													<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH}common/icon_filedownload.gif"/> <font style="font-weight:bold;"></font></a>
												</c:forEach>
											</c:if>
											</td>
											<td rowspan="${vo.n_raw_cnt}">
											<c:if test="${!empty vo.v_coa_file }">
													<c:set var="arrCoaFile" value="${fn:split(vo.v_coa_file, '/') }"/>
												<c:forEach items="${arrCoaFile }" var="cvo">
													<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH }common/icon_filedownload.gif"/></a>&nbsp;
												</c:forEach>
											</c:if>
											</td>
<%-- 											<td rowspan="${vo.n_raw_cnt }" class="last">
											<c:if test="${!empty vo.v_ccpp_file }">
													<c:set var="arrCcppFile" value="${fn:split(vo.v_ccpp_file, '/') }"/>
												<c:forEach items="${arrCcppFile }" var="cvo">
													<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH }common/icon_filedownload.gif"/></a>&nbsp;
												</c:forEach>
											</c:if>
											</td> --%>

											<td rowspan="${vo.n_raw_cnt}" class="last">
												<c:if test="${!empty vo.v_spec_file}">
														<c:set var="arrSpecFile" value="${fn:split(ivo.v_spec_file, ',')}"/>
													<c:forEach items="${arrSpecFile}" var="cvo">
														<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH }common/icon_filedownload.gif"/></a>&nbsp;
													</c:forEach>
												</c:if>
											</td>


										</c:if>
										</tr>
									</c:if>
									<c:set var="tempCd" value="${vo.v_raw_cd}"/>
								</c:forEach>
							</c:if>
						<tr>
							<th colspan="4"><font color="red"><b>조성함량 합계</b></font></th>
							<td colspan="2" style="text-align: right;" class="comp_td_sum">
								<fmt:formatNumber value="${sInPerAdd}" type="number" var="sInPerAdd" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>	
								${sInPerAdd }
							</td>
							<td colspan="2" style="text-align: right;" class="comp_at_td_sum">
								<fmt:formatNumber value="${sPerAdd}" type="number" var="sPerAdd" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>	
								${sPerAdd }
							</td>
<%-- 							<td colspan="${reqVo.i_sExpDiv eq 'CHN'? '8':'6'}" class="last"> --%>
							<td colspan="4" class="last">							
							</td>
						</tr>
						</tbody>
					</table>
				</div>
			<div class="sec_cont mt_60">
				<h2 class="tit">향료 알러젠</h2><!-- 향료 알러젠 -->
			
				<table class="sty_02 table_matrcd" style="word-break:break-all;">
					<colgroup>                                             
<!-- 						<col width="30"/>                                                       -->
						<col width="60"/>                                                      
						<col width="60"/>                                                      
						<col width="200"/>                                                     
						<col width="100"/>                                                     
						<col width="100"/>                                                     
						<col width="100"/>                                                     
						<col width="200"/>                                                     
						<col width="50"/>                                                      
						<col width="50"/>                                                      
						<col width="50"/>                                                      
						<col width="40"/>                                                  
						<col width="40"/>
						<col width="40"/>
					</colgroup>
					<thead>
						<tr>
<!-- 							<th class="start">No.</th> -->
							<th>원료성분</th>
							<th>성분코드</th>
							<th>INCI Name</th>
							<th>Wt(%)</th>
							<th>mixure ratio</th>
							<th>Actual<br/> Wt(%)</th>
							<th>Function</th>
							<th>사용금지</th>
							<th>배합제한</th>
							<th>Safety_ODM standard</th>
							<th>첨부파일</th>
							<th>COA</th>
							<th class="last">SPEC</th>
						</tr>
					</thead>
					<tbody id="tbody_complex">
						<c:set var="sInPerAdd" value="0"/>
						<c:set var="sPerAdd" value="0"/>
						<fmt:formatNumber value="${sInPerAdd}" type="number" var="sInPerAdd" maxFractionDigits="7"/>
						<fmt:formatNumber value="${sPerAdd}" type="number" var="sPerAdd" maxFractionDigits="7"/>
						<c:set var="tempCd" value="#"/>
						<c:set var="tempCnt" value="0"/>
						<c:if test="${!empty conList }">
							<c:forEach items="${conList }" var="vo">
								<c:if test="${vo.v_allergen_yn eq 'Y' }">
									<c:if test="${tempCd ne vo.v_raw_cd}">
										<c:set var="tempCnt" value="${tempCnt + 1 }"/>
									</c:if>
									<tr class="comp_partno comp_partno_${vo.n_part_no }" style="${!empty vo.v_tdd_danger ? 'background-color:#CFE8D8;' : ''}">
									<c:if test="${tempCd ne vo.v_raw_cd}">
<%-- 										<td class="start tr_comp_count_${vo.n_part_no }" rowspan="${vo.n_raw_cnt}">${tempCnt}</td>	 --%>
									</c:if>
									<c:if test="${tempCd ne vo.v_raw_cd}">
										<td class="td_rawcd_${vo.n_part_no }" rowspan="${vo.n_raw_cnt}">
											${vo.v_raw_cd}
										</td>
									</c:if>
										<td>${vo.v_con_cd }</td>
										<td>${vo.v_con_nm_en }</td>
									<c:if test="${tempCd ne vo.v_raw_cd}">
										<td rowspan="${vo.n_raw_cnt }">
											${vo.v_raw_per}
											<span class="span_hide span_per">${vo.v_raw_per }</span>
											<c:set var="sInPerAdd" value="${sInPerAdd + vo.v_raw_per }"/>
										</td>
									</c:if>	
										<td>${vo.v_con_in_per }</td>
										<td>
											${vo.v_actual_wt }
											<span class="span_hide span_per_at">${vo.v_actual_wt }</span>
											<c:set var="sPerAdd" value="${sPerAdd + vo.v_actual_wt }"/>
										</td>
									<c:if test="${tempCd ne vo.v_raw_cd}">
										<td rowspan="${vo.n_raw_cnt }">
											${vo.v_comp_fc_nm } 
<%-- 											(${vo.v_comp_fc_nm_ch }) --%>
										</td>
									</c:if>
										<td>
											<a href="javascript:fn_popGlob('${vo.v_con_cd}', '${vo.n_ver_seq }');"><span style="color: red; font-weight: bold; text-decoration: underline;">${vo.v_zglobal}</span></a>
										</td>
										<td>
											<a href="javascript:fn_popGloblim('${vo.v_con_cd}', '${vo.n_ver_seq }');"><span style="color: blue; font-weight: bold; text-decoration: underline;">${vo.v_zgllim}</span></a>
										</td>									
										<td> ${vo.v_remark }</td>
									<c:if test="${tempCd ne vo.v_raw_cd}">
										<td rowspan="${vo.n_raw_cnt}">
										<c:if test="${!empty vo.v_atta_file }">
											<c:set var="arrAttaFile" value="${fn:split(vo.v_atta_file, '/') }"/>
											<c:forEach items="${arrAttaFile}" var="cvo">
												<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH}common/icon_filedownload.gif"/> <font style="font-weight:bold;"></font></a>
											</c:forEach>
										</c:if>
										</td>
										<td rowspan="${vo.n_raw_cnt}">
										<c:if test="${!empty vo.v_coa_file }">
												<c:set var="arrCoaFile" value="${fn:split(vo.v_coa_file, '/') }"/>
											<c:forEach items="${arrCoaFile }" var="cvo">
												<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH }common/icon_filedownload.gif"/></a>&nbsp;
											</c:forEach>
										</c:if>
										</td>
										<td rowspan="${vo.n_raw_cnt }" class="last">
										<c:if test="${!empty vo.v_ccpp_file }">
												<c:set var="arrCcppFile" value="${fn:split(vo.v_ccpp_file, '/') }"/>
											<c:forEach items="${arrCcppFile }" var="cvo">
												<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH }common/icon_filedownload.gif"/></a>&nbsp;
											</c:forEach>
										</c:if>
										</td>
									</c:if>
									</tr>
									<c:set var="tempCd" value="${vo.v_raw_cd}"/>
								</c:if>
							</c:forEach>
						</c:if>
					</tbody>
				</table>	
				</div>
			</c:when>
			<c:otherwise>
				<div class='sec_cont'>
					<h2 class="tit">수출 검토</h2>
					<ul class=sty_tab>
						<c:forEach items="${partList }" var="vo">
							<li class="tab li_tab" id="${vo.n_part_no }">
								<a href="#" class="${reqVo.i_sPartNo eq vo.n_part_no ? 'on' : '' }"><span>${vo.v_content_nm }</span></a>
							</li>
						</c:forEach>
					</ul>
					
					<table class="sty_02 table_fileop" style="word-break:break-all;">					
						<colgroup>
			              <col style="width:;">
			              <col style="width:;">
		              	</colgroup>
						<thead>
							<th class="ta_c">필수서류 및 확인사항</th>
							<th class="ta_c">배합한도 초과 성분</th>
						</thead>
						<tbody>
			              	<tr>
				              	<td>									
									<table class="sty_02 table_required_file" style="word-break:break-all; min-width: 750px;">
										<colgroup>
							              <col style="width:;">
							              <col style="width:;">
							              <col style="width:;">
							              <col style="width:;">
						              	</colgroup>
										<thead>
											<th class="ta_c">성분코드<br/>(원료코드)</th>
											<th class="ta_c">성분코드<br/>(영문)</th>
											<th class="ta_c">필수서류 및 <br/>확인사항</th>
											<th class="ta_c">ODM사 <br/>의견</th>
										</thead>
										<tbody>
											<c:if test="${!empty falcList }">
												<c:forEach items="${falcList }" var="ivo">
													<tr class="tr_required_file">
														<td id="td_${ivo.v_con_cd }" class="ta_c">
															${ivo.v_con_cd}(${ivo.v_rawcd_list })
														</td>
														<td class="ta_c">
															${ivo.v_con_nm_en}
														</td>
														<td class="ta_c">
															${ivo.v_comment}
														</td>
														<td class="ta_c">
															${ivo.v_file_opinion}
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
					              	</table>
				              	</td>
				              	<td>
									<table class="sty_02 table_limit" style="min-width: 750px;">
										<colgroup>
											<col width="70px"/>
											<col width=""/>
											<col width="100px"/>
											<col width="100px"/>
											<col width="100px"/>
										</colgroup>
										<thead>
											<tr>
												<th class="ta_c">성분코드</th>
												<th class="ta_c">성분명<br/>(영문)</th>
												<th class="ta_c">내용물 내 <br/>함량(%)</th>
												<th class="ta_c">안전성 <br/>허용량(%)</th>
												<th class="ta_c">법적(분석) <br/>허용량(%)</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${falcList }" var="ivo">
												<c:if test="${ivo.nPartNo eq vo.nPartNo and !empty ivo.vLimitColor}">
													<tr class="tr_limit">
														<td id="td_${ivo.v_con_cd }">${ivo.v_con_cd }</td>
														<td>${ivo.v_con_nm_en}</td>
														<td class="ta_r"><p style="font-weight: bold;color: ${ivo.v_limit_color}">${ivo.n_actual_wt}</p></td>
														<td class="ta_r">${ivo.n_safety_limit}</td>
														<td class="ta_r last">${ivo.n_legal_limit}</td>
													</tr>
												</c:if>
											</c:forEach>
										</tbody>
									</table>
				              	</td>
			              	</tr>
						</tbody>
					</table>
				</div>
				
				<div class="sec_cont mt_60">
<!-- 					<h2 class="tit"></h2>					 -->
					<table class="sty_02 table_matrcd" style="word-break:break-all;">
						<colgroup>
<!-- 			              <col style="width:30px;"> -->
<!-- 			              <col style="width:50px;"> -->
			              <col style="width:60px;">
			              <col style="width:150px;">
			              <col style="width:15%;">
			              <col style="width:15%;">
			              <col style="width:15%;">
			              <col style="width:100px;">
			              <col style="width:100px;">
			              <col style="width:100px;">
			              <col style="width:100px;">
			              <col style="width:60px;">
			              <col style="width:60px;">
			              <col style="width:120px;">
			            </colgroup>
						<thead>
							<tr>
<!-- 								<th></th>                                         -->
<!-- 								<th class="ta_c">No.</th>                                      -->
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
								<th class="ta_c">첨부파일</th>
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
<!-- 											<td class='ta_c'> -->
<!-- 												<span class="chk-style"> -->
<%-- 													<label for="i_arrModifyChk_${tempCnt}_${vo.n_part_no }"> --%>
<!-- 														<span> -->
<%-- 															<input type="checkbox" name="i_arrModifyChk" id="i_arrModifyChk_${tempCnt}_${vo.n_part_no }" value="${vo.v_con_cd}"/> --%>
<!-- 														</span> -->
<!-- 													</label> -->
<!-- 												</span> -->
<!-- 											</td> -->
<%-- 											<td class="ta_c tr_count">${cnt}</td> --%>
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
<%-- 												<select class="select_sty01 select_modiFunc" name="i_sModiFuncNm_${vo.v_con_cd}_${vo.n_part_no}" style="width:100%"> --%>
<%-- 													<c:forEach items="${fcList }" var="fvo"> --%>
<%-- 														<c:if test="${fvo.v_con_cd eq vo.v_con_cd}"> --%>
<%-- 															<option value="${fvo.v_fcname }" ${vo.v_comp_origin_fc eq fvo.v_fcname ? 'selected=\'selected\'' : '' }>${fvo.v_fcname }</option> --%>
<%-- 														</c:if> --%>
<%-- 													</c:forEach> --%>
<!-- 													<option value="ETC">기타</option> -->
<!-- 												</select> -->
<!-- 												<div style="width:100%"> -->
<%-- 													<input class="inp_sty01" type="text" name="i_sModiFuncNm_etc_${vo.v_con_cd}_${vo.n_part_no }" value="" size="30" style="margin : 0 auto; margin-top: 10px; display: none;width:100%;"/> --%>
<%-- 													<input type="hidden" name="i_sBeforeFuncNm_${vo.v_con_cd}_${vo.n_part_no}" value="${vo.v_comp_origin_fc }"/> --%>
<!-- 												</div> -->
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
											<td>
												<c:if test="${!empty vo.v_atta_file }">
													<c:set var="arrAttaFile" value="${fn:split(vo.v_atta_file, '/') }"/>
													<c:forEach items="${arrAttaFile}" var="cvo">
														<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH}common/icon_filedownload.gif"/> <font style="font-weight:bold;"></font></a>
													</c:forEach>
												</c:if>
											</td>
										</tr>
										<c:set var="cnt" value="${cnt+1}"/>
										<c:set var="tempCnt" value="${tempCnt+1 }"/>
									</c:if>
								</c:forEach>
								<tr>
<!-- 									<td colspan="10"></td> -->
									<td colspan="8"></td>
									<td colspan='2'>
										<span class="span_chk-style">
											<span class="${vo.v_inactive_all eq 'Y' ? 'on' : '' }"></span>&nbsp;Inactive all
										</span>
									</td>
								</tr>
								<tr>
									<th colspan="2"><font color="red"><b>조성함량 합계</b></font></th>
									<td></td>
<!-- 									<td colspan="10" style="text-align: left;" class="last td_sum"> -->
									<td colspan="8" style="text-align: left;" class="last td_sum">
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
<!-- 							<col width="30"/> -->
<!-- 							<col width="30"/> -->
							<col width="80"/>
							<col width="120"/>
							<col width="120"/>
							<col width="15%"/>
							<col width="15%"/>
							<col width="15%"/>
							<col width="100"/>
							<col width="100"/>
							<col width="50"/>
							<col width="50"/>
							<col width="100"/>
						</colgroup>
						<thead>
							<tr class="start">
<!-- 								<th class="ta_c"></th> -->
<!-- 								<th class="ta_c">No.</th> -->
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
								<th class="ta_c">첨부파일</th>
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
<!-- 											<td class="ta_c"> -->
<!-- 												<span class="chk-style"> -->
<%-- 													<label for="i_arrModifyChk_${tempCnt}_${vo.n_part_no }"> --%>
<!-- 														<span> -->
<%-- 															<input type="checkbox" name="i_arrModifyChk" id="i_arrModifyChk_${tempCnt}_${vo.n_part_no }" value="${vo.v_con_cd}"/> --%>
<!-- 														</span> -->
<!-- 													</label> -->
<!-- 												</span> -->
<!-- 											</td> -->
<%-- 											<td class="ta_c tr_count">${cnt}</td> --%>
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
<%-- 												<select class="select_sty01 select_modiFunc" name="i_sModiFuncNm_${vo.v_con_cd}_${vo.n_part_no}" style="width="100%"> --%>
<%-- 													<c:forEach items="${fcList }" var="fvo"> --%>
<%-- 														<c:if test="${fvo.v_con_cd eq vo.v_con_cd}"> --%>
<%-- 															<option value="${fvo.v_fcname }" ${vo.v_comp_origin_fc eq fvo.v_fcname ? 'selected=\'selected\'' : '' }>${fvo.v_fcname }</option> --%>
<%-- 														</c:if> --%>
<%-- 													</c:forEach> --%>
<!-- 													<option value="ETC">기타</option> -->
<!-- 												</select> -->
<!-- 												<div> -->
<%-- 													<input type="text" name="i_sModiFuncNm_etc_${vo.v_con_cd}_${vo.n_part_no }" value="" size="30" style="margin : 0 auto; margin-top: 10px; display: none;"/> --%>
<%-- 													<input type="hidden" name="i_sBeforeFuncNm_${vo.v_con_cd}_${vo.n_part_no}" value="${vo.v_comp_origin_fc }"/> --%>
<!-- 												</div> -->
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
											<td>
												<c:if test="${!empty vo.v_atta_file }">
													<c:set var="arrAttaFile" value="${fn:split(vo.v_atta_file, '/') }"/>
													<c:forEach items="${arrAttaFile}" var="cvo">
														<a href="javascript:attachDown('${cvo }', 'ODM', 'frm')"><img src="${ImgPATH}common/icon_filedownload.gif"/> <font style="font-weight:bold;"></font></a>
													</c:forEach>
												</c:if>
											</td>
										</tr>
										<c:set var="cnt" value="${cnt+1}"/>
										<c:set var="tempCnt" value="${tempCnt+1 }"/>
									</c:if>
								</c:forEach>
								<tr>
<!-- 									<td colspan="9"></td> -->
									<td colspan="7"></td>
									<td colspan="3">
										<span class="span_chk-style">
											<span class="${vo.v_inactive_all eq 'Y' ? 'on' : '' }"></span>&nbsp;Inactive all
										</span>
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="pd_top10"></div>
	
<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 					<a href="#none" class="btnA bg_dark" id="a_update_CAS"><span>Cas No. 갱신</span></a>Function 수정 -->
<!-- 				<a href="#none" class="btnA bg_dark a_modi_func_his"><span>Function 수정 History</span></a> -->
<!-- 				<a href="#none" class="btnA bg_dark a_modi_func"><span>Function 수정</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->
		
		<div id="div_claim_frag">
			<div class = "sec_cont mt_60">
			<h2 class="tit">Claim Support</h2>
				<table class="sty_02">
					<colgroup>
						<col width="10%"/>
						<col width="40%"/>
						<col width="10%"/>
						<col width="20%"/>
						<col width="20%"/>
					</colgroup>
					<thead>
						<tr>
							<th class="ta_c">성분 코드</th>
							<th class="ta_c">성분 표시명 (영문)</th>
							<th class="ta_c">Active Ing. %</th>
							<th class="ta_c">효능</th>
							<th class="ta_c">Report No.</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${!empty claimList }">
							<c:forEach items="${claimList }" var="vo">
								<tr>
									<td class="ta_c">${vo.v_con_cd }</td>
									<td class="ta_c">${vo.v_con_nm_en }</td>
									<td class="ta_c">${vo.v_claim_active_ing }</td>
									<td class="ta_c">${vo.v_claim_effect eq 'ETC' ? vo.v_claim_effect_etc : vo.v_claim_effect }</td>
									<td class="ta_c">${vo.v_claim_report_no }</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty claimList }">
							<tr>
								<td class="ta_c" colspan="5"> :: 해당하는/등록된 내용이 없습니다. ::</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>	
			<div class = "sec_cont mt_60">
			<h2 class="tit">향료 관리</h2>
				<table class="sty_02">
					<colgroup>
						<col width="20%"/>
						<col width="20%"/>
						<col width="60%"/>
					</colgroup>
					<thead>
						<tr>
							<th class="ta_c">향료 코드</th>
							<th class="ta_c">제조사</th>
							<th class="ta_c">향취</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${!empty fragranceList }">
							<c:forEach items="${fragranceList }" var="vo">
								<tr>
									<td class="ta_c">${vo.v_fragrance }</td>
									<td class="ta_c">${vo.v_manufacture }</td>
									<td class="ta_c">${vo.v_fragrance_smell }</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty fragranceList }">
							<tr>
								<td class="ta_c" colspan="3">::해당하는/등록된 내용이 없습니다. ::</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
		
	<div class="pd_top10"></div>
		
	<ul class="btn_area">
		<li class="right">
			<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</c:if>
		</li>
	</ul>
</form>



<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
