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
var tree;
 j$(function(){

 	$('.btn_list').click(function(e){
 		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
 	});
 	
	j$(".li_tab").unbind("click").click(function(event) {
		event.preventDefault();
		
		var frm = j$("form[name='frm']");
		var partno = j$(this).attr("id");
		
		frm.find("input[name='i_sPartNo']").val(partno);
		frm.attr("action", "/br/pw/020/br_pw_020_t2_process_view.do").submit();
	});
	
	j$(".btn_detail").unbind("click").click(function(event) {
		var id = j$(this).attr("id");
		var frm = j$("form[name='frm']");
		
		if(id == "tab1") {
			$('input[name=i_sDivision]').val('SINGLE');
			$('input[name=i_sRecipeType]').val('S');
			frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_process_view.do").submit();
		} else if(id == "tab2") {
			$('input[name=i_sDivision]').val('COMPLEX');
			$('input[name=i_sRecipeType]').val('C');
			frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_complex_process_view.do").submit();
		}			
	});

	j$(".a_preview").unbind("click").click(function(event) {
		event.preventDefault();		
		var param = {
				i_sRecordId		: j$("input[name='i_sRecordId']").val()
				, i_sProductCd		: j$("input[name='i_sProductCd']").val()
				, i_sPartNo		: j$(".li_tab .on").parent().attr('id')
				, i_sRecipeType : $('input[name=i_sRecipeType]').val()
				, i_sFrameid : "iFrameProcessPreview"
		};
		var url = WebPATH + "br/pr/012/doc/co_doc_process_preview_pdf_print.do?"+j$.param(param,true);
		fn_pop({title:"미리보기",width:900,height:600,url: url });
	});
	addInputChkRadioEvent();
	//fn_initTree();
	fn_initTreeData();
 });
 function fn_initTreeData(){
	 $.ajax({
			url:"/br/pw/020/getProcessTreeData.do"
			,type:"post"
			,dataType: "json"
			,data:{
				i_sRecordId : $('input[name=i_sRecordId]').val()
				,i_sProductCd : $('input[name=i_sProductCd]').val()
				,i_sPartNo : $('input[name=i_sPartNo]').val()
				,i_sRecipeType : $('input[name=i_sRecipeType]').val()
				,i_iVerSeq : $('input[name=i_iVsn]').val()
			}
			,async:false
			,success:function(data){
				console.log(data);
				fn_initTree(data.data);
			},error : function(jqXHR, textStatus, errorThrown){
		        fn_s_failMsg(jqXHR, textStatus, errorThrown);
			}
	 });
 }
  function fn_initTree(data){
	  if(typeof data == 'undefined'){
		  return;
	  }
	  treeData = new dhx.TreeCollection();
	  treeData.parse(data);
	  tree = new dhx.Tree("bpRss", {
		    checkbox: true
		    ,data : treeData
	});
	tree.events.on("ItemDblClick", function(id, e){
		tree.toggle(id);
	});
  }
</script>

<form id="frm" name="frm" method="post">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sDivision" 	value="${reqVo.i_sDivision}"/>	
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo }" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>
	<input type="hidden" name="i_iVsn" value="${reqVo.i_iVerSeq}" />	
	<input type="hidden" name="i_sReceipStatus" value="" />
	<input type="hidden" name="i_sGlbRecordid" value="${rVo.v_refer_record_id}" />	
	<input type="hidden" name="i_sRecipeType" value="${reqVo.i_sRecipeType }" />	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<div class="top_btn_area" style="z-index:1;">
		<a href="#none" class="btnA bg_dark a_preview"><span>미리보기</span></a>
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>
	
	<div class="pd_top10"></div>	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>
	<div class="pd_top10"></div>
	<ul class="sty_tab">
		<li class="tab"><a href="#" class=" ${reqVo.i_sRecipeType eq 'S' ? 'on' : '' } btn_detail" id="tab1"><span>IL</span></a></li>
		<li class="tab"><a href="#" class=" ${reqVo.i_sRecipeType eq 'C' ? 'on' : '' } btn_detail" id="tab2"><span>QQ</span></a></li>
	</ul>	
	<div class="pd_top10"></div>

<!-- 	<ul class="btn_area"> -->
<!-- 		<li class="right"> -->
<%-- 			<c:if test="${GlbExp.v_report_status eq 'GRS007' }"> --%>
<!-- 				<a href="#none" id="a_save" class="btnA bg_dark"><span>저장</span></a>저장 -->
<%-- 			</c:if> --%>
<!-- 			<a href="#none" class="btnA bg_dark a_preview"><span>미리보기</span></a> -->
<!-- 			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a> -->
<!-- 		</li> -->
<!-- 	</ul> -->
	
	<div class="div_tree">	
		<ul id="ul_tab" class="sty_tab">
			<c:forEach items="${partList}" var="vo">
				<li class="tab li_tab" id="${vo.n_part_no}">
					<a href="#none" class="${vo.n_part_no eq reqVo.i_sPartNo ? 'on' : ''} a_tab_part" data-partno="${vo.n_part_no}"><span>${vo.v_content_nm}</span></a>
				</li>
			</c:forEach>
		</ul>		
		<div class="pd_top10"></div>
		<c:choose>
			<c:when test="${precessCompYn eq 'Y' }">
			
				<span class="span_notice">
					<strong>
						<c:choose>
						<c:when test="${reqVo.i_sRecipeType eq 'S'}">
							<!-- * 해당 제품에 대한 단일처방 공정도가 작성되지 않았거나 임시 저장 상태 입니다. -->
							* 해당 제품에 대한 <span class="span_none_color">단일처방</span> 공정도가 작성되지 않았거나 임시저장 상태입니다.
						</c:when>
						<c:otherwise>
							<!-- * 해당 제품에 대한 복합처방 공정도가 작성되지 않았거나 임시 저장 상태 입니다. -->
							* 해당 제품에 대한 <span class="span_none_color">복합처방</span> 공정도가 작성되지 않았거나 임시저장 상태입니다.
						</c:otherwise>
						</c:choose>
					</strong>
				</span>
				
			</c:when>
			<c:otherwise>
				
				<div class="pd_top10"></div>
				
				<div class="div_ajax_area">
					<div id="bpRss" style="height: 340px; ;overflow-y: scroll; border: #90aaca 1px solid;"></div>
	<!-- 				<div id="bpRss" style="height: 340px; ;overflow-y: scroll; border: #90aaca 1px solid;"> -->
	<!-- 					<div class="right" style="width:350px;"> -->
	<!-- 						<ul id="browser" class="filetree treeview-famfamfam"> -->
	<%-- 							<c:forEach items="${folderList}" var="fvo" varStatus="status"> --%>
	<%-- 								<li class="li_folder" data-folder="${fvo.n_prc_part_seq }"> --%>
	<!-- 									<span class="folder"> -->
	<%-- 										<input type="hidden" value="${fvo.v_part_nm }" name="i_arrFoldernm">${fvo.v_part_nm } --%>
	<!-- 									</span> -->
	<!-- 									<ul class="ul_con"> -->
											
	<%-- 										<c:forEach items="${list_saved}" var="cvo" varStatus="sc"> --%>
	<%-- 											<c:if test="${fvo.n_prc_part_seq eq cvo.n_prc_part_seq}"> --%>
	<%-- 												<li data-concd="${cvo.v_con_cd}" data-rawcd="${cvo.v_raw_cd}"> --%>
	<!-- 													<span> -->
	<!-- 														<span> -->
	<%-- 															<c:choose> --%>
	<%-- 																<c:when test="${reqVo.i_sRecipeType eq 'S'}"> --%>
	<%-- 																	${cvo.v_con_nm_en} --%>
	<%-- 																</c:when> --%>
	<%-- 																<c:otherwise>  --%>
	<%-- 																	[${cvo.v_raw_cd }] ${cvo.v_con_nm_en} --%>
	<%-- 																</c:otherwise> --%>
	<%-- 															</c:choose> --%>
	<!-- 														</span> -->
	<%-- 														<input type="hidden" name="i_arrPartItem${cvo.n_prc_part_seq}" value="${cvo.v_con_cd}"/> --%>
	<%-- 														<input type="hidden" name="i_arrRawcd${cvo.n_prc_part_seq}" value="${cvo.v_raw_cd}"/> --%>
	<!-- 													</span> -->
	<!-- 												</li> -->
	<%-- 											</c:if> --%>
											
	<%-- 										</c:forEach> --%>
											
	<!-- 									</ul> -->
	<!-- 								</li> -->
	<%-- 							</c:forEach> --%>
	<!-- 						</ul> -->
	<!-- 					</div>					 -->
	<!-- 				</div> -->
					
					<div class="pd_top20"></div>
					
					<table id="memo" class="sty_02" style="position: center;">
						<colgroup>
							<col width="20%">
							<col width="80%">
						</colgroup>
							
						<tbody>
							<c:forEach items="${folderList}" var="fvo" varStatus="status">
								
								<tr id="memo_${fvo.n_prc_part_seq }">
									<th>${fvo.v_part_nm }</th>
									<td class="last">
	<%-- 									<textarea name="i_arrPartDesc" rows="4" style="width:98%">${fvo.v_part_desc}</textarea> --%>
										<span name="i_arrPartDesc" style="width:98%">${fvo.v_part_desc}</span>
										<input type="hidden" name="i_arrPartSeq" value="${fvo.n_prc_part_seq }"/>
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
					<c:if test="${reqVo.i_sRecipeType eq 'C'}">
						
						<div class="pd_top20"></div>
						
						<table class="sty_02" style="position: center;">
							<colgroup>
								<col width="20%">
								<col width="80%">
							</colgroup>
							<tbody>
								<tr>
									<th>DEGASSING
										<span class="span_chk-style">
											<span class="span_func_ck ${processVo.v_degassing_ck eq 'Y' ? 'on' : ''}" id="span_i_sDegassingCk"></span>
										</span>
									</th>
									<td></td>
								</tr>
								<tr>
									<th>COOLING
										<span class="span_chk-style">
											<span class="span_func_ck ${processVo.v_cooling_ck eq 'Y' ? 'on' : ''}" id="span_i_sCoolingCk"></span>
										</span>
									</th>
									<td>
										<input type="text" class="inputBox onlyNumber" name="i_sCooling" id="i_sCooling" style="width:10%" value="${processVo.v_cooling}" ${processVo.v_cooling_ck ne 'Y' ? 'disabled' : ''}/> ℃
									</td>
								</tr>
								<tr>
									<th>FILTRATION
										<span class="span_chk-style">
											<span class="span_func_ck ${processVo.v_filtration_ck eq 'Y' ? 'on' : ''}" id="span_i_sFiltration_ck"></span>
										</span>
									</th>
									<td></td>
								</tr>
								<tr>
									<th>STORAGE
										<span class="span_chk-style">
											<span class="span_func_ck ${processVo.v_storage_ck eq 'Y' ? 'on' : ''}" id="span_i_sStorageCk"></span>
										</span>
									</th>
									<td></td>
								</tr>
							</tbody>
						</table>
						
					</c:if>
					
				</div>
				
				<div class="pd_top10"></div>
			
			</c:otherwise>
		</c:choose>
	</div>
	
	<ul class="btn_area">
		<li class="right">
			<a href="#none" class="btnA bg_dark a_preview"><span>미리보기</span></a>
			<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</c:if>
		</li>
	</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
