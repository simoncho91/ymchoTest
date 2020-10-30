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

	 	$('.btn_list').click(function(e){
	 		location.href="/br/pw/020/init.do?openMenuCd=MIBRPW020";
	 	});

	 	$('.btn_process').click(function(e){
	 		fn_s_saveMessage("제품 기처리","해당 제품을 기처리하시겠습니까?",'/br/pw/010/updateAlreadyProcess.do',$('#frm').serialize()
	 			,function(){
	 				var frm = j$("form[name='frm']");
	 				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t1_view.do").submit();
	 			}
	 		);
	 	});

	 	$('.btn_del').click(function(e){
	 		fn_s_saveMessage("제품 기처리","해당 제품을 삭제하시겠습니까?",'/br/pw/010/updateDeleteProduct.do',$('#frm').serialize()
	 			,function(){
	 				location.href="/br/pw/010/init.do?openMenuCd=MIBRPW010";
	 				//var frm = j$("form[name='frm']");
	 				//frm.attr("action", WebPATH + "br/pw/010/init.do?openMenuCd=MIBRPW010").submit();
	 			}
	 		);
	 	});

	 	$('.btn_receipt').click(function(e){
	 		$('input[name=i_sFinalRst]').val('RS020');

	 		fn_s_saveMessage("RA접수","해당 제품을 접수하시겠습니까?",'/br/pw/020/updateProdFinalRst.do',$('#frm').serialize()
	 			,function(){
	 				var frm = j$("form[name='frm']");
	 				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t1_view.do").submit();
	 			}
	 		);
	 	});

	 	$('.btn_reject').click(function(e){
	 		$('input[name=i_sFinalRst]').val('RS025');

	 		fn_s_saveMessage("RA반려","해당 제품을 반려하시겠습니까?",'/br/pw/020/updateProdFinalRst.do',$('#frm').serialize()
	 			,function(){
	 				var frm = j$("form[name='frm']");
	 				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t1_view.do").submit();
	 			}
	 		);
	 	});
	 	
	 	$('.btn_reqItem_list').click(function(){			
			var _patn = WebPATH + "br/pw/020/br_pw_020_req_modi_list_pop.do?i_sRecordId=" + j$("input[name='i_sRecordId']").val() +"&i_sProductCd="+j$("input[name='i_sProductCd']").val()+"&i_sFlag=GL";
			fn_pop({title:"수정요청 목록",width:700,height:600,url:_patn});
	 	});
	 	$('.btn_modify_pop').click(function(e){
	 		var record_id = $('input[name=i_sRecordId]').val();
	 		var product_id = $('input[name=i_sProductCd]').val();
	 		fn_pop({url:'/br/pr/010/br_pr_010_modify_pop.do?i_sProductCd='+product_id+'&i_sRecordId='+record_id ,title:'수출검토[기본정보] 수정',width:'1000',height:'400'});
	 		
	 	});
	 	$('.btn_referProdPop').click(function(e){
	 		var record_id = $('input[name=i_sReferRecordId]').val();
	 		var product_id = $('input[name=i_sReferProductCd]').val();
	 		var url ='/br/pw/020/br_pw_020_t1_view.do?i_sRecordId='+record_id+'&i_sProductCd='+product_id+'&i_sPopUpYn=Y';
	 		window.open(url, "", "location=no, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, fullscreen=yes" );
	 	});
	 	
	 	
	 	$('#table_product_message_view').find('td a[name=btn_messageSave]').on('click',function(){
			var postParam = {
				i_sProductCd : $('input[name=i_sProductCd]').val()
				,i_sRecordId : $('input[name=i_sRecordId]').val()
				,i_sMessage : $('textarea[name=i_sMessage]').val()				
				,i_sMenuType : 'PROD'
				,i_sFlagType : 'R'
			};
			fn_s_saveMessage("메시지 등록","메시지를 등록하시겠습니까?","/br/pw/020/br_pw_020_t2_message_save.do",postParam
				,function(){
					//location.href="/br/pw/020/br_pw_020_t2_document_view.do";
					var frm = j$("form[name='frm']");
					frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t1_view.do").submit();
				}
				,function(){
					var message = $('textarea[name=i_sMessage]').val();
					if(fn_isNull(message)){
						fn_s_alertMsg("메시지를 입력후 저장해주세요.",function(){
							$('textarea[name=i_sMessage]').focus();
						});
						return true;
					}else if(fn_s_inputLengthChk(message,'메시지',2000)){						
						return true;
					}
				}
			);
	 	});
		
		$('tr[id=tr_message]').find('td span[name=messageIcon]').on('click',function(){
			var recordId = $('input[name=i_sRecordId]').val();
			var prodcutCd =$('input[name=i_sProductCd]').val();
			var message = "";
			if($(this).hasClass('dxi-chevron-down')){
				var _obj =$(this); 
				$(this).removeClass('dxi-chevron-down');
				$(this).addClass('dxi-chevron-up'); 
				$.ajax({
					url:"/br/pw/020/getMessage.do"
					,type:"post"
					,dataType: "json"
					,data:{
						i_sRecordId : recordId
						,i_sProductCd : prodcutCd
						,i_sMenuType : 'PROD'
					}
					,async:false
					,success:function(data){
						var _tr = $('<tr id="tr_message_compl"></tr>');
						var _td = $('<td class="last" colspan="2"></td>');
//	 					<span style="margin-left: 10px;">'+message+'</span>
						var rData = data.result.data;
						var html = '';
						html+='<ul class="mi_msg" style="max-width: 100%"><li class="mi_msg_gap"></li>';
						rData.forEach(function(obj,i){
							if(obj.v_flag_type=="B"){
								html+='<li class="mi_msg_to" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt">'+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
								html+=obj.v_message;
								html+='</dd><dd class="msg_bottom"></dd></dl></li>';
								html+='<li class="mi_msg_gap"></li>';
							}else if(obj.v_flag_type=="R"){
								html+='<li class="mi_msg_from" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt">'+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
								html+=obj.v_message;
								html+='</dd><dd class="msg_bottom"></dd></dl></li>';
							html+='<li class="mi_msg_gap"></li>';							
							}						
						});
//	 					float:right;
						html+='</ul>';
						_td.append(html);
						_tr.append(_td);
						_obj.parent().parent().after(_tr);
					},error : function(jqXHR, textStatus, errorThrown){
				        fn_s_failMsg(jqXHR, textStatus, errorThrown);
					}
				});
			}else{
				$(this).removeClass('dxi-chevron-up');
				$(this).addClass('dxi-chevron-down');
				$('#tr_message_compl').remove();
			}
		});
 });
 
</script>

<form id="frm" name="frm" method="post">

	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />	
	<input type="hidden" name="i_sProductCd" 	value="${reqVo.i_sProductCd}"/>	
	<input type="hidden" name="i_sPopUpYn" 	value="${reqVo.i_sPopUpYn}"/>
	<input type="hidden" name="i_iVsn" value="${rVo.n_vsn}" />	
	<input type="hidden" name="i_sReceipStatus" value="${rVo.v_receip_status }" />
	<input type="hidden" name="i_sFinalRst" value="${rVo.v_final_rst }" />
	<input type="hidden" name="i_sReferProductCd" value="${rVo.v_refer_product_cd }" />
	<input type="hidden" name="i_sReferRecordId" value="${rVo.v_refer_record_id }" />
	
	<input type="hidden" name="i_sVendorLaborId" 	value="${rVo.v_vendor_labor_id}"/>

	<div class="top_btn_area" style="z-index:1;">
		<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
			<a href="#none" class="btnA bg_dark btn_del"><span>삭제</span></a>
		    <a href="#none" class="btnA bg_dark btn_modify_pop"><span>수정</span></a>	
			<a href="#none" class="btnA bg_dark btn_reqItem_list"><span>수정요청 목록</span></a>
			<c:if test="${'RS050' eq rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}">
				<a href="#none" class="btnA bg_dark btn_receipt"><span>접수</span></a>
				<a href="#none" class="btnA bg_dark btn_reject"><span>반려</span></a>
			</c:if>
	<%-- 		<c:if test="${!empty rVo.v_refer_product_cd and !empty rVo.v_refer_record_id and empty rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}"> --%>
			<c:if test="${!empty rVo.v_refer_product_cd and empty rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}">
				<a href="#none" class="btnA bg_dark btn_process"><span>기처리</span></a>					
				<a href="#none" class="btnA bg_dark btn_reject"><span>반려</span></a>
			</c:if>
			<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
		</c:if>
	</div>


	<div class="pd_top10"></div>	
	<%@ include file="/WEB-INF/jsp/sitims/mi/br/pw/020/br_pw_020_tab.jsp" %>	
	<div class="pd_top10"></div>
	<div class="sub_content">

<!-- 		<ul class="btn_area"> -->
<!-- 			<li class="right"> -->
<!-- 				<a href="#none" class="btnA bg_dark btn_reqItem_list"><span>수정요청 목록</span></a> -->
<%-- 				<c:if test="${empty rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}"> --%>
<!-- 					<a href="#none" class="btnA bg_dark btn_receipt"><span>접수</span></a> -->
<!-- 					<a href="#none" class="btnA bg_dark btn_reject"><span>반려</span></a> -->
<%-- 				</c:if> --%>
<%-- 				<c:if test="${!empty rVo.v_refer_product_cd and !empty rVo.v_refer_record_id and empty rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}"> --%>
<!-- 					<a href="#none" class="btnA bg_dark btn_process"><span>기처리</span></a> -->
<%-- 				</c:if> --%>
<!-- 				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a> -->
<!-- 			</li> -->
<!-- 		</ul> -->

		<div class="pd_top10"></div>
		
		<table id="table_product_view" class="sty_03 bdw2">
			<caption></caption>
			<colgroup>
				<col width="10%" />
				<col width="10%" />
				<col width="30%" />
				<col width="15%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2">제품담당자</th> <!-- 담당BM -->
					<td colspan='3'>
						<span>${rVo.v_bm_nm}</span>
						<input type="hidden" id="i_sUserid" name="i_sUserid" value="${rVo.v_bm_id}" />
					</td>
				</tr>
			</tbody>

			<tbody>
				<tr>
					<th rowspan="2">협력업체</th> <!-- 협력업체 -->
					<th>업체명</th> <!-- 업체명 -->
					<td>
						<div>
							<span class="chk-style">
								<label for="i_sImportyn" class="${rVo.v_import_yn eq 'Y' ? 'on':'' }">
									<span>
										<input type="checkbox" name="i_sImportyn" id="i_sImportyn" value="${rVo.v_import_yn}" ${rVo.v_import_yn eq 'Y' ? 'checked':'' } disabled/>
									</span>
									수입반제품/완제품<!-- 수입 반제품 -->
								</label>
							</span>
							
							<table class="sty_03 bd_gray" style="width: 100%; display: ${rVo.v_import_yn eq 'Y' ? 'table' : 'none' };" id="table_import">
								<colgroup>
									<col width="20%">
									<col width="*"/>
								</colgroup>
								<tbody>
									<tr>
										<th colspan="2" class="bdl_n">
											<span>
												${rVo.v_flag_imp eq 'F'? '수입완제품':'수입반제품' }
											</span>
										</th>
									</tr>
									<tr class="maker_tr">
										<th class="bdl_n">
											해외 제조원<!-- <div class="pd_top10"></div> -->
										</th>
										<td>
											<span>${rVo.v_f_maker_nm}</span>
										</td>
									</tr>
									<tr class="maker_tr" style="display:${rVo.v_flag_imp eq 'S'? 'table-row':'none' };">
										<th class="bdl_n">최종 제조원</th>
										<td>
											<span>${rVo.v_l_maker_nm}</span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</td>
					<th>담당자</th> <!-- 담당자 -->
					<td>
						<div id="odm_user_search_div" style="display:block"> <!-- odm사의 경우 -->
							<span>${rVo.v_odm_user_nm}</span>
						</div>
					</td>
				</tr>
				<tr>
					<th>담당자(이메일)</th> <!-- 담당자(이메일) -->
					<td>
						<span>${rVo.v_odm_user_email }</span>
					</td>
					<th>연락처</th>
					<td>
						<span>${rVo.v_odm_user_phon }</span>
					</td>
				</tr>
			</tbody>
				<tbody class="tbody_product_info">
					<tr>
						<th rowspan="11">
							제품정보 
						</th>
						<th>브랜드</th> <!-- 브랜드 -->
						<td>
							<span>${rVo.v_brand_nm }</span>
						</td>						
						<th rowspan='1'>제품코드</th> <!-- 제품코드 -->
						<td rowspan='1'>
							<span>${rVo.v_product_cd }</span>
						</td>
					</tr>
					<tr>
						<th>한글명</th><!-- 한글명 -->
						<td>
							<span>${rVo.v_product_nm_ko }</span>
						</td>
						<th>영문명</th> 
						<td>
							<span>${rVo.v_product_nm_en }</span>
						</td>
					</tr>
					<tr>
						<th>중문명</th> <!-- 대표홋수코드 -->
						<td>
							<span>${rVo.v_product_nm_cn }</span>
						</td>						
						<th>수출국가</th>
						<td colspan="1">
							<span>${rVo.v_exp_country_nm }</span>
						</td>
					</tr>
					<tr>
						<th>Shelf Life</th> <!-- 대표홋수코드 -->
						<td>
							<c:if test="${rVo.v_shelf_life eq 'ETC' }">
								<span>${rVo.v_shelf_life_etc }</span>
							</c:if>
							<c:if test="${rVo.v_shelf_life ne 'ETC' }">
								<span>${rVo.v_shelf_life_nm }</span>
							</c:if>
						</td>						
						<th>PAO</th>
						<td colspan="1">
							<c:if test="${rVo.v_pao eq 'ETC' }">
								<span>${rVo.v_pao_etc }</span>
							</c:if>
							<c:if test="${rVo.v_pao ne 'ETC' }">
								<span>${rVo.v_pao_nm }</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>본품 여부</th> <!-- 본품 여부 -->
						<td>
							<span>${rVo.v_origin_yn}</span>					
							<br/>							
							<span>${rVo.v_origin_div_nm}</span>
						</td>				
						<th>연결된 본품코드<br/>(동일 내용물 제품)</th> <!-- 연결된 본품코드 -->
						<td>
							<c:if test="${!empty rVo.v_refer_product_cd }">
								<span>[${rVo.v_refer_product_cd}]${rVo.v_refer_product_nm}</span>
							</c:if>							
							<c:if test="${!empty rVo.v_refer_product_cd }">
								<a href="#none" class="btnA bg_dark btn_referProdPop"><span>새창</span></a>
							</c:if>
						</td>
					</tr>						
					<tr class="content_nm_tr">
						<th>전성분 표시될<br/>내용물 개수</th>
						<td>${rVo.v_content_num }&nbsp;개</td>	
						<th>제품유형</th> <!-- 제품유형 -->
						<td>
							<span>${rVo.v_category1_nm }</span>
							<br />
							<span>${rVo.v_category2_nm }</span>
						</td>		
					</tr>
					<tr>
						<th>대유형</th> <!-- 대유형 -->
						<td>
							<span>${rVo.v_type_nm }</span>
						</td>						
						<th>용량/용기유형</th> <!-- 용기형태/유형 -->
						<td>
							<div style="margin-bottom: 5px;">
								용량 : ${rVo.v_capacity }
							</div>
							<div>
								<span style="float: left;">${rVo.v_cntr_form_nm }</span>							
								<div class="CntrForm_etc_div" style="float: left; display: ${rVo.v_cntr_form eq 'OCT_1_21' ? 'block' : 'none' };">
									(기타 : <span>${rVo.v_cntr_form_etc }</span>)&nbsp;
								</div>
								<span style="float: left;">${rVo.v_cntr_matr_nm }</span>
								<div class="CntrMatr_etc_div" style="display: ${rVo.v_cntr_matr eq 'OCT_2_04' ? 'block' : 'none' };">
									(기타 : <span>${rVo.v_cntr_matr_etc }</span> )
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th>출고일</th>
						<td colspan='1'>
							<span>${cfn:getStrDateToString(rVo.v_release_dtm,'yyyy-MM-dd')}</span>
						</td>
						<th>예정 입고일</th> <!-- 입고일 -->
						<td colspan='1'>
							<span>${cfn:getStrDateToString(rVo.v_stock_dtm,'yyyy-MM-dd')}</span>
						</td>
					</tr>
					<tr>
						<th>기능성 여부</th> <!-- 기능성 여부 -->
						<td>
							<span>${rVo.v_func_yn }</span>					
							<br/>
							<c:if test="${rVo.v_func_yn eq 'Y'}">
								<span>${rVo.v_func_nm }</span>
							</c:if>
						</td>
						<th>영·유아 화장품 해당 여부</th>
						<td>
							<span>${rVo.v_kid_guide_yn }</span>
						</td>
					</tr>
					<tr><!-- style="display: none;" -->
						<th>안정성 자료 필수 여부</th>
						<td>
							<span>${rVo.v_stability_file_yn }</span>
						</td>
						<th>포장단위</th>
						<td>
							<span>${rVo.v_packet_unit }</span>
						</td>
					</tr>
					<tr>
						<th>소구범위</th> <!-- 소구범위 -->
						<td>
							<div style="float: left;display: ${fn:indexOf(rVo.v_free_gn, 'D') > -1 ? 'block' : 'none'};">								
								<span style="float: left;">
									${fn:indexOf(rVo.v_free_gn, 'D') > -1 ? '무소구' : ''}
								( <span>${rVo.v_musogu_cont }</span> )
								</span>								
							</div>					
							<br/>		
							<div style="float: left;display: ${fn:indexOf(rVo.v_free_gn, 'E') > -1 ? 'block' : 'none'};">
								<span style="float: left;">
									${fn:indexOf(rVo.v_free_gn, 'E') > -1 ? '기타' : ''}
								( <span >${rVo.v_sogu_cont }</span> )
								</span> 
							</div>
						</td>
						<th>Leave On / Wash Off</th>
						<td><span>${rVo.v_leaveon_yn eq 'Y' ? 'Leave On' : 'Wash Off' }</span></td>
					</tr>					
					<tr class="tr_origin_img">
						<th>사용방법</th>
						<td colspan="4">
							<CmTagLib:cmAttachFile uploadCd="PON" type="viewLog" recordId="${rVo.v_record_id}" pk1="${rVo.v_product_cd}" />
							<pre>${rVo.v_pon_msg }</pre>
						</td>
					</tr>
					<tr>
						<th>Package Images</th>
						<td colspan="4">
							<table class="sty_03 bd_gray" id="table_PON" style="width:98%;margin:5px;">
								<colgroup>
									<col width="33%">
									<col width="33%">
									<col width="33%">
								</colgroup>
								<thead>
									<tr>
										<th>단상자</th>
										<th>용기</th>
										<th>내지</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="ta_c">
											<CmTagLib:cmAttachFile uploadCd="ORI_BOX" recordId="${rVo.v_ad_req_id }" type="listDown"/>
										</td>
										<td class="ta_c">
											<CmTagLib:cmAttachFile uploadCd="ORI_VESSEL" recordId="${rVo.v_ad_req_id }" type="listDown"/>
										</td>
										<td class="ta_c">
											<CmTagLib:cmAttachFile uploadCd="ORI_PAPER" recordId="${rVo.v_ad_req_id }" type="listDown"/>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
		</table>		
	</div>	<div class="pd_top10"></div>
	<div class="sub_content">
		<table id="table_product_message_view" class="sty_03 mt_10">
			<caption></caption>
			<colgroup>
				<col width="10%" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th>
						작성자
					</th>
					<th>
						메시지
					</th>
				</tr>
			</thead>
			<tbody>
				<tr id="tr_message">
					<td>
						${reqVo.s_usernm }
						<span name="messageIcon" style="vertical-align: middle;" class="dxi dxi-chevron-down"></span>
					</td>
					<td>
						<textarea class="textarea_sty01" rows="2" name="i_sMessage" id="i_sMessage"class="inp_sty01" style="width: 90%;" ></textarea>
						<a href="javascript:;" class="btnA bg_dark" id="btn_messageSave" name="btn_messageSave"  style="cursor: pointer;float: right;margin-top: 10px;">저장</a>					
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<ul class="btn_area">
		<li class="right">
			<c:if test="${reqVo.i_sPopUpYn ne 'Y' }">
				<a href="#none" class="btnA bg_dark btn_del"><span>삭제</span></a>
				<a href="#none" class="btnA bg_dark btn_modify_pop"><span>수정</span></a>	
				<a href="#none" class="btnA bg_dark btn_reqItem_list"><span>수정요청 목록</span></a>
				<c:if test="${'RS050' eq rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}">
					<a href="#none" class="btnA bg_dark btn_receipt"><span>접수</span></a>
					<a href="#none" class="btnA bg_dark btn_reject"><span>반려</span></a>
				</c:if>
				<%-- <c:if test="${!empty rVo.v_refer_product_cd and !empty rVo.v_refer_record_id and empty rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}"> --%>
				<c:if test="${!empty rVo.v_refer_product_cd and empty rVo.v_final_rst and rVo.v_receip_status eq 'RS010'}">
					<a href="#none" class="btnA bg_dark btn_process"><span>기처리</span></a>					
					<a href="#none" class="btnA bg_dark btn_reject"><span>반려</span></a>
				</c:if>
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</c:if>
		</li>
	</ul>
</form>



<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
