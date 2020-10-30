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
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/010/br_pr_010_reg.js"></script>
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
<script type='text/javascript' src='/sitims/js/util/cm_function.js'></script> 
<script type='text/javascript'>
 j$(function(){
	 	$('.btn_modify').click(function(e){
	 		var record_id = $('input[name=i_sRecordId]').val();
	 		location.href="/br/pr/010/br_pr_010_reg.do?i_sActionFlag=M&i_sRecordId="+record_id;
	 	});
	 	$('.btn_PermitReqStatus').click(function(e){
	 		var record_id = $('input[name=i_sRecordId]').val();
	 		var product_id = $('input[name=i_sProductCd]').val();
	 		fn_pop({url:'/br/pr/010/br_pr_010_modify_pop.do?i_sProductCd='+product_id+'&i_sRecordId='+record_id ,title:'제품정보 수정',width:'1000',height:'400'});
	 		
	 	});

	 	$('.btn_list').click(function(e){
	 		location.href="/br/pr/010/init.do?openMenuCd=MIBRPR010";
	 	});

	 	$('#table_product_message_view').find('td a[name=btn_messageSave]').on('click',function(){
			var postParam = {
				i_sProductCd : $('input[name=i_sProductCd_real]').val()
				,i_sRecordId : $('input[name=i_sRecordId]').val()
				,i_sMessage : $('textarea[name=i_sMessage]').val()				
				,i_sMenuType : 'PROD'
				,i_sFlagType : 'B'
			};
			fn_s_saveMessage("메시지 등록","메시지를 등록하시겠습니까?","/br/pw/020/br_pw_020_t2_message_save.do",postParam
				,function(){
					//location.href="/br/pw/020/br_pw_020_t2_document_view.do";
					var frm = j$("form[name='frm']");
					frm.attr("action", WebPATH + "br/pr/010/br_pr_010_view.do").submit();
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
		
	 	$('#table_product_message_view').find('tr[id=tr_message]').find('td span[name=messageIcon]').on('click',function(){
			var recordId = $('input[name=i_sRecordId]').val();
			var prodcutCd =$('input[name=i_sProductCd_real]').val();
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
							if(obj.v_flag_type=="R"){
								html+='<li class="mi_msg_to" style="max-width: 100%"><strong>'+obj.v_reg_usernm+'</strong><span class="date_txt">'+fn_getFormatDate(obj.v_reg_dtm)+'</span><dl><dd class="msg_cont">';
								html+=obj.v_message;
								html+='</dd><dd class="msg_bottom"></dd></dl></li>';
								html+='<li class="mi_msg_gap"></li>';
							}else if(obj.v_flag_type=="B"){
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

	<input type="hidden" name="i_sActionFlag" value="${empty reqVo.i_sActionFlag ? 'R' : reqVo.i_sActionFlag}" />
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sRequestYn" value="N" />
	<input type="hidden" name="i_sIuserdist" value="${infoVo.v_companycd eq 'AP' ? 'B' : 'S'}" />
	<input type="hidden" name="i_sFastFlag" value="${empty reqVo.i_sActionFlag ? 'R' : reqVo.i_sActionFlag}" />
	<input type="hidden" name="i_iVsn" value="${reqFileVo.n_vsn}" />
	<input type="hidden" name="i_sRequestId" value="${reqFileVo.v_requestid}" />
	<input type="hidden" name="i_sReceipStatus" value="" />
	<input type="hidden" name="i_sProductCd" value="${reqVo.i_sProductCd}" />
	<input type="hidden" name="i_sGroupId" value="${reqVo.s_groupid}" />

	<div class="sub_content">

		<ul class="btn_area">
			<li class="right">			
				<c:if test="${fn:indexOf(reqVo.s_groupid,'PDVIEW') < 0 && fn:indexOf(reqVo.s_groupid,'PDDOWN') < 0 && rVo.v_receip_status eq 'RS000'}"><!-- 접수대기(임시저장 상태) -->
					<a href="#none" class="btnA bg_dark btn_modify"><span>수정</span></a>
				</c:if>
				<c:if test="${fn:indexOf(reqVo.s_groupid,'PDVIEW') < 0 && fn:indexOf(reqVo.s_groupid,'PDDOWN') < 0 && rVo.v_receip_status eq 'RS010'}"><!-- BM 허가 요청 -->
					<a href="#none" class="btnA bg_dark btn_PermitReqStatus"><span>수정</span></a>
				</c:if>
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</li>
		</ul>
		<!--
		<div class="subtitle">
			제품등록
		</div>
		-->
		<div class="pd_top10"></div>
		
		<table id="table_product_view" class="sty_03">
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
									<col width="100px;">
									<col width="250px;">
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
			<c:set var="prod_len" value="${fn:length(prodList)}" />
			<c:forEach items="${prodList}" var="vo" varStatus="s">
				<tbody class="tbody_product_info">
					<tr>
						<th rowspan="11">
							제품정보 
						</th>
						<th>브랜드</th> <!-- 브랜드 -->
						<td>
							<span>${vo.v_brand_nm }</span>
						</td>
						<th>한글명</th><!-- 한글명 -->
						<td>
							<span>${vo.v_product_nm_ko }</span>
						</td>
					</tr>
					<tr>
						<th rowspan="${fn:indexOf(vo.v_exp_country, 'CN')>-1?'2':'1'}">제품코드</th> <!-- 제품코드 -->
						<td rowspan="${fn:indexOf(vo.v_exp_country, 'CN')>-1?'2':'1'}">
						<input type="hidden" name="i_sProductCd_real" value="${vo.v_product_cd}" />
							<span>${vo.v_product_cd }</span>
						</td>
						<th>영문명</th> 
						<td>
							<span>${vo.v_product_nm_en }</span>
						</td>
					</tr>
					<tr style="display:${fn:indexOf(vo.v_exp_country, 'CN')>-1?'':'none'};">
						<th>중문명</th> <!-- 중문명 -->
						<td>
							<span>${vo.v_product_nm_cn }</span>
						</td>
					</tr>
					<tr>
						<th>본품 여부</th> <!-- 본품 여부 -->
						<td>
							<span>${vo.v_origin_yn}</span>					
							<br/>							
							<span>${vo.v_origin_div_nm}</span>
						</td>								
						<th>수출국가</th>
						<td colspan="1">
							<span>${vo.v_exp_country_nm }</span>
						</td>
					</tr>
					<tr class="content_nm_tr">
						<th>전성분 표시될<br/>내용물 개수</th>
						<td>
							<c:if test="${!empty vo.v_content_num }">
								${vo.v_content_num }&nbsp;개
							</c:if>
						</td>
						<th>연결된 본품코드<br/>(동일 내용물 제품)</th> <!-- 연결된 본품코드 -->
						<td>
							<c:if test="${!empty vo.v_refer_product_cd }">
								<span>[${vo.v_refer_product_cd}]${vo.v_refer_product_nm}</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>예정 입고일</th> <!-- 입고일 -->
						<td>
							<span>${cfn:getStrDateToString(vo.v_stock_dtm,'yyyy-MM-dd')}</span>
						</td>
						<th>Leave On / Wash Off </th> <!-- Leave On / Wash Off -->
						<td>
							<span>${vo.v_leaveon_yn eq 'Y' ? 'Leave On' : 'Wash Off' }</span>
						</td>
					</tr>
					<tr>
						<th>대유형</th> <!-- 대유형 -->
						<td>
							<span>${vo.v_type_nm }</span>
						</td>
						<th>제품유형</th> <!-- 제품유형 -->
						<td>
							<span>${vo.v_category1_nm }</span>${vo.v_category2_nm ne '' ? ' > ' : ''} <span>${vo.v_category2_nm }</span>
						</td>
					</tr>
					<tr>
						<th>기능성 여부</th> <!-- 기능성 여부 -->
						<td>
							<span>${vo.v_func_yn }</span>					
							<br/>
							<c:if test="${vo.v_func_yn eq 'Y'}">
								<span>${vo.v_func_nm }</span>
							</c:if>
						</td>
						<th>용량/용기유형</th> <!-- 용기형태/유형 -->
						<td>
							<div style="margin-bottom: 5px;">
								용량 : <span>${vo.v_capacity }</span>
								<!-- span>* 용량 입력 시, 단위도 함께 작성해주시기 바랍니다.(ex. 10ml, 10g)</span -->
							</div>
							<div>
								<span style="float: left;">${vo.v_cntr_form_nm }</span>							
								<div class="CntrForm_etc_div" style="float: left; display: ${vo.v_cntr_form eq "OCT_1_21" ? 'block' : 'none' };">
									(기타 : <span>${vo.v_cntr_form_etc }</span>)&nbsp;
								</div>
								 / <span>${vo.v_cntr_matr_nm }</span>
								<div class="CntrMatr_etc_div" style="display: ${vo.v_cntr_matr eq "OCT_2_04" ? 'block' : 'none' };">
									(기타 : <span>${vo.v_cntr_matr_etc }</span> )
								</div>
							</div>
						</td>
					</tr>
					<tr><!-- style="display: none;" -->
						<th>영·유아 화장품 해당 여부</th>
						<td>
							<span>${vo.v_kid_guide_yn }</span>
						</td>
						<th>안정성 자료 필수 여부</th>
						<td>
							<span>${vo.v_stability_file_yn }</span>
							<!-- br>* 레티놀(비타민A), 아스코빅애씨드(비타민C) 및 그 유도체, 토코페놀(비타민E), 과산화화합물, 효소 이 컨셉일 경우 기술자료 '유' 체크-->
						</td>
					</tr>
					<tr>
						<th>소구범위</th> <!-- 소구범위 -->
						<td>
							<div style="float: left;display: ${fn:indexOf(vo.v_free_gn, 'E') > -1 ? 'block' : 'none'};">								
								<span style="float: left;">
									${fn:indexOf(vo.v_free_gn, 'E') > -1 ? '무소구' : ''}
								( <span>${vo.v_musogu_cont }</span> )
								</span>								
							</div>					
							<br/>		
							<div style="float: left;display: ${fn:indexOf(vo.v_free_gn, 'D') > -1 ? 'block' : 'none'};">
								<span style="float: left;">
									${fn:indexOf(vo.v_free_gn, 'D') > -1 ? '기타' : ''}
								( <span >${vo.v_sogu_cont }</span> )
								</span> 
							</div>
						</td>
						<th>포장단위</th>
						<td>
							<span>${vo.v_packet_unit }</span>
						</td>
					</tr>
					
					<tr class="tr_origin_img">
						<th>사용방법</th>
						<td colspan="3">
							<CmTagLib:cmAttachFile uploadCd="PON" type="viewLog" recordId="${vo.v_record_id}" pk1="${vo.v_product_cd }" />
							<span rows="4" style="width: 98%;" name="i_arrPonMsg" readonly='readonly'><pre>${vo.v_pon_msg }</pre></span>
							<!-- span><font style="color: red;">(사용방법이 특수한 경우 기재.)</font></span -->
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	<c:if test="${rVo.v_receip_status eq 'RS010' }">
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
	</c:if>
	<!--
	<div class="pd_top20"></div>
		
	<table class="table_line">
		<colgroup>
			<col width="100%">
		</colgroup>
		<tbody>
			<tr>
				<td style="text-align: center;" class="last">
					<p style="font-size: 27px; font-weight: bold; padding-bottom: 30px; padding-top: 10px;">기술자료 제공 요청서</p>
				</td>
			</tr>
			<tr>
				<td class="last">
					<font id="brandnm">
						(주)신세계인터내셔날
					</font>(이하, 원사업자)은 <font id="vendornm">${rVo.v_vendor_nm }</font>(이하, 수급사업자)간에 체결예정인 제조위탁계약과 관련한 업무를 수행하기 위해 수급사업자에 다음과 같이 기술자료 제공을 요청드립니다. 수급사업자는 원사업자의 기술자료 제공요청에 응할 의무가 없으나, 하기와 같은 사유로  제품의 거래개시를 위해 필요한 자료입니다.<br/><br/></td>
			</tr>
			<tr>
				<th class="last">제1조 기술자료 내역</th>
			</tr>
			<tr>
				<td class="last" >
					<table class="sty_02">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="50%">
						</colgroup>
						<tbody>
							<tr>
								<th>구분</th>
								<th>명칭</th>
								<th>수집목적</th>
							</tr>
							<c:forEach items="${reqContentList }" var="vo">
								<tr>
									<td>${vo.v_gubun }</td>
									<td>${vo.v_name }</td>
									<td>${vo.v_purpose }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<th class="last">제2조 비밀유지</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_keepsecrt }">
							<textarea name="i_sKeepSecrt"  rows="5" style="width: 98%" >
1) 원사업자는 서면 또는 방문을 통해 수급사업자로부터 취득한 기술자료를 제1조에 명시된 수집 목적에 부합하는 범위 내에서만 사용할 수 있으며, 수급사업자의 사전 서면 동의 없이 제3자에게 제공하지 않습니다.
2) 만일 원사업자가 수급사업자에게 제1조에 명시된 기술자료 목록 외 기술자료를 요구하는 경우, 원사업자는 수급사업자로부터 별도의 기술자료 요구 동의를 받을 것입니다. 
3) 수급사업자가 판단하기에 수급사업자의 기술자료를 원사업자가 기술자료 요구서 발급없이 요청하는 경우, 수급사업자는 이를 거절할 수 있으며 사전에 원사업자와 협의하여 기술자료 요구서를 서면 발급 받은 후 기술자료를 제공할 수 있습니다.
4) 원사업자는 제품의 사전안전성 검토 및 기본 계약 체결 여부 평가를 위하여 수급사업자의 기술자료를 ㈜신세계인터내셔날에 위탁할 수 있습니다. 다만 이 경우에도 원사업자는 ㈜신세계인터내셔날이 수급사업자의 기술자료를 제3자에 유출하거나 유용하지 않도록 이에 대한 관리감독을 해야하며, 문제 발생시 그에 대한 법적 책임을 부담합니다. 단, 이는 원사업자가 (주)신세계인터내셔날이 아닌 경우에 한합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea name="i_sKeepSecrt"  rows="5" style="width: 98%" >${reqFileVo.v_keepsecrt }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제3조 권리귀속관계</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_relation}">
							<textarea rows="5" style="width: 98%" name="i_sRelation">
원사업자가 요구하는 기술자료에 관한 권리는 수급사업자에게 있으며, 수급사업자는 기술자료 제공 후 원사업자에게 기술자료의 사용을 중단할 것을 서면으로 요청할 수 있으며, 원사업자는 수급사업자의 요청이 있는 경우 즉시 사용을 중단합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sRelation">${reqFileVo.v_relation }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제4조 대가</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_reward}">
							<textarea rows="5" style="width: 98%" name="i_sReward" >이 기술자료 제공은 수급사업자의 기본 계약 체결 여부 평가를 위한 제품 안전성 검토 및 업무능력 평가 등을 위한 것으로 별도의 대가는 없습니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sReward" >${reqFileVo.v_reward }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제5조 인도일 및 인도방법</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_transfer}">
							<textarea rows="5" style="width: 98%" name="i_sTransfer">인도일은 양자간 기술자료 제공 합의 완료 후 원사업자의 요청일로부터 30일 이내로 하며, 인도방법은 별도 협의합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sTransfer">${reqFileVo.v_transfer }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제6조 기술자료 사용기간</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_using_period}">
							<textarea rows="5" style="width: 98%"  name="i_sUsing_preiod">
1) 이 요구서에 따른 기술자료의 사용기간은 제품의 마지막 생산일로부터 10년으로 합니다.
2) 제품의 사전안전성 검토 및 업무능력 평가 결과, 제품화하지 않기로 결정한 날로부터 3년간 보관합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%"  name="i_sUsing_preiod">${reqFileVo.v_using_period }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제7조 반환 또는 폐기방법</th>
			</tr>
			<tr>
				<td class="last">
					<c:choose>
						<c:when test="${empty reqFileVo.v_discard}">
							<textarea rows="5" style="width: 98%" name="i_sDiscard">
1) 원사업자는 제6조의 사용기간의 만료, 또는 수급사업자의 요청이 있는 경우 수급사업자의 기술자료를 반환 또는 폐기합니다.
2) 수급사업자는 언제든지 제공한 기술자료의 삭제를 원사업자에게 요청할 수 있습니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sDiscard">${reqFileVo.v_discard }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th class="last">제8조 반환 또는 폐기일</th>
			</tr>
			<tr>
				<td class="last lastRow">
					<c:choose>
						<c:when test="${empty reqFileVo.v_discard_period}">
							<textarea rows="5" style="width: 98%" name="i_sDiscard_period">원사업자는 폐기 사유의 발생일로부터 30일 이내에 반환 또는 폐기합니다.</textarea>
						</c:when>
						<c:otherwise>
							<textarea rows="5" style="width: 98%" name="i_sDiscard_period">${reqFileVo.v_discard_period }</textarea>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</tbody>
	</table>
	-->
	<div class="pd_top20"></div>
		<ul class="btn_area">
			<li class="right">						
				<c:if test="${reqVo.s_groupid ne 'PDVIEW' && reqVo.s_groupid ne 'PDDOWN' && rVo.v_receip_status eq 'RS000'}"><!-- 접수대기(임시저장 상태) -->
					<a href="#none" class="btnA bg_dark btn_modify"><span>수정</span></a>
				</c:if>
				<c:if test="${reqVo.s_groupid ne 'PDVIEW' && reqVo.s_groupid ne 'PDDOWN' && rVo.v_receip_status eq 'RS010'}"><!-- BM 허가 요청 -->
					<a href="#none" class="btnA bg_dark btn_PermitReqStatus"><span>수정</span></a>
				</c:if>
				<a href="#none" class="btnA bg_dark btn_list"><span>목록</span></a>
			</li>
		</ul>
</form>

<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
