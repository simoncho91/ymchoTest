<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
</style>
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_reg.js"></script>
<script type="text/javascript">
	var prodSearch;

	j$(function(){
		fn_init();
		fn_buttonEvent();
		//기능성데이터로딩
		if("Y" == $("#i_sFuncYn").val()){
			setFuncReportData($("#i_sRecordId").val(), $("#i_sProductCd").val());
		}
		//추가요청 활성화
		$(".i_sPacking").each(function(item,i){
			if(!this.checked){
				$(".btn_add_packing").css("display","inline-block");
			}
		})
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
	
	function fn_buttonEvent(){
		//패킹제작자 검색 s
		j$('#searchPacking').on('click',function(){	 
			fn_pop({url:'/br/pr/020/br_pr_020_odm_designer_list_pop.do?i_sCmFunction=setPackingPopUpData&i_sInput='+encodeURI($('#i_sPackingNm').val()),		title:'패킹제작업체',width:'700',height:'500'});
		});
		j$('#i_sPackingNm').on('dblclick',function(){	 
			fn_pop({url:'/br/pr/020/br_pr_020_odm_designer_list_pop.do?i_sCmFunction=setPackingPopUpData&i_sInput='+encodeURI($('#i_sPackingNm').val()),		title:'패킹제작업체',width:'700',height:'500'});
		});	 
		j$('#i_sPackingNm').on('keypress',function(e){
			if(e.keyCode=='13'){
				fn_pop({url:'/br/pr/020/br_pr_020_odm_designer_list_pop.do?i_sCmFunction=setPackingPopUpData&i_sInput='+encodeURI($('#i_sPackingNm').val()),	title:'패킹제작업체',width:'700',height:'500'});
		}
		});	 
		j$('#delPacking').on('click',function(){
			$('#i_sPackingId').val('');
			$('#i_sPackingNm').val('');
		});
		//패킹제작자 검색 e
		
		//등록완료 원화 의견등록
		j$(".opBtn").on('click',function(){
			var opType	=	$(this).attr('id');
			var opinion	=	$(this).closest('td').find('.i_sMessages').val();
			fn_oriMsgSave("NON_STATUS",opType, opinion);
		});
		
		//등록완료 원화 승인
		j$(".apprBtn").on('click',function(){
			var opType	=	$(this).attr('id');
			var opinion	=	$(this).closest('td').find('.i_sMessages').val();
			fn_oriMsgSave("PIC_REQ_STATUS04",opType, opinion);
		});
		
		//등록완료 원화 반려
		j$(".rjtBtn").on('click',function(){
			var opType	=	$(this).attr('id');
			var opinion	=	$(this).closest('td').find('.i_sMessages').val();
			fn_oriMsgSave("PIC_REQ_STATUS05",opType, opinion);
		});
		
		//원화등록추가요청
		$(".btn_add_packing").on('click',function(){
			fnPackingDisplayYn(false);
			
			$(this).css("display","none");
			$(".btn_add_packing_complete").css("display","inline-block");
			$(".btn_add_packing_cancel").css("display","inline-block");
		});
		
		//원화등록추가요청 취소
		$(".btn_add_packing_cancel").on('click',function(){
			fnPackingDisplayYn(true);
			
			$(this).css("display","none");
			$(".btn_add_packing_complete").css("display","none");
			$(".btn_add_packing").css("display","inline-block");
		});
		
		//원화등록 추가요청 완료
		$(".btn_add_packing_complete").on('click',function(){
			dhx.confirm({
				header			:	"원화등록추가요청",
				text			:	"원화등록 추가 요청을 하시겠습니까?",
				buttons			:	["예", "아니오"],
				buttonsAlignment:	"center"
			}).then(function(answer){
				$(".i_sPacking").attr("disabled",false);
				
				if(answer){
					$.ajax({
						url			:	"/br/pr/020/br_pr_020_add_packing_save.do"
						,data		:	$(frm).serialize()
						,dataType	:	"json"
						,type		:	"POST"
						,success	:	function(data){
							location.href="/br/pr/020/br_pr_020_ori_view.do?openMenuCd=MIBRPR021&i_sAdReqId="+$("#i_sAdReqId").val() + "&i_sProductCd=" + $("#i_sProductCd").val();
						},error		:	function(jqXHR, textStatus, errorThrown){
						}
					});
				}
			});
		});
	}
	
	//수정페이지 이동
	function fnModify(){
		var frm						=	document.frm;
		frm["i_sActionFlag"].value	=	"M";
		frm.action					=	"/br/pr/020/br_pr_020_ori_reg.do";
		
		frm.submit();
	}
	
	//요청
	function fnAppr(status){
		if(status == 'AD_REQ_STATUS05'){	//결재요청 취소
			$("#i_sCancelFlag").val("Y");
			$("#i_sActionFlag").val("M");
			
			fnSave(status,"원화등록요청 취소","원화등록 요청을 취소하시겠습니까?");
		} else if(status == 'AD_REQ_STATUS10'){	//원화발송 요청
			if(fn_isNull($("#i_sPackingId").val())){
				fn_s_alertMsg("패킹제작자를 지정해주세요.");
				return;
			}
		
			fnSave(status,"원화발송요청","원화를 발송하시겠습니까?");
		}
	}
	
	//요청 저장
	function fnSave(status,header,text){
		dhx.confirm({
			header			:	header,
			text			:	text,
			buttons			:	["예", "아니오"],
			buttonsAlignment:	"center"
		}).then(function(answer){
			if(answer){
				var frm 				=	document.frm;
				frm["i_sStatus"].value  = 	status;
				
				$.ajax({
					url			:	"/br/pr/020/br_pr_020_ori_save.do"
					,data		:	$(frm).serialize()
					,dataType	:	"json"
					,type		:	"POST"
					,success	:	function(data){
						location.href="/br/pr/020/br_pr_020_ori_view.do?openMenuCd=MIBRPR021&i_sAdReqId="+$("#i_sAdReqId").val() + "&i_sProductCd=" + $("#i_sProductCd").val();
					},error		:	function(jqXHR, textStatus, errorThrown){
					}
				});
			}
		});
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
		location.href="/br/pr/020/init.do?openMenuCd=MIBRPR020";
	}
	
	//의견등록, 승인, 반려
	function fn_oriMsgSave(status, opType, opinion){
		var msg;
		
		if(status == 'NON_STATUS'){
			msg	=	"의견이 등록되었습니다.";
		}else if(status == 'PIC_REQ_STATUS04') {
			msg =	"승인처리가 완료되었습니다.";
		}else if(status == 'PIC_REQ_STATUS05'){
			msg	=	"반려처리가 완료되었습니다.";
		}
		
		if(fn_isNull(opinion)){
			fn_s_alertMsg("의견을 입력해주세요.");
			
			return false;
		}
		
		$("#i_sMsgType").val(opType);
		$("#i_sMessage").val(opinion);
		$("#i_sApprStatus").val(status);
		
		$("#i_sVesselYn").attr("disabled",false);
		$("#i_sBoxYn").attr("disabled",false);
		$("#i_sPaperYn").attr("disabled",false);

		var frm			=	j$("form[name='frm']");
		var postParam	=	$('#frm').serialize();
		var url;
		
		
		$.ajax({
			  url		:	"/br/pr/020/br_pr_020_ori_opinion_reg_ajax.do"
			, type		:	"post"
			, data		:	postParam
			, dataType	:	"json"
			, success : function(data) {
				fn_s_alertMsg(msg, function(){
					location.reload();
				});
			}
		});
	}
	
	//의견 수정
	function fnMessageModify(messageNo,opinion) {
		console.log(opinion);
		fn_pop(
			{url:'/br/pr/020/br_pr_020_opinion_modify_pop.do?i_sMessageNo=' + messageNo + '&i_sOpinion=' + encodeURIComponent(opinion)
			,title:'의견수정'
			,width:'700'
			,height:'500'
			}
		);
	}
	
	//의견 삭제
	function fnMessageDelete(messageNo) {
		$.ajax({
			  url		:	"/br/pr/020/br_pr_020_ori_opinion_delete_ajax.do"
			, type		:	"post"
			, data		:	{'i_sMessageNo' : messageNo}
			, dataType	:	"json"
			, success : function(data) {
				fn_s_alertMsg("의견이 삭제되었습니다.",function(){
					location.reload();
				});
			}
		});
	}
	
	function fnPackingDisplayYn(displayFlag){
		$(".packingCheck").each(function(item,i){
			if($(this).val() != 'Y'){
				$("#i_s" + $(this).attr('id').replace("i_sCheck","") + "Yn").attr("disabled",displayFlag);
				if(displayFlag == true){
					$("#i_s" + $(this).attr('id').replace("i_sCheck","") + "Yn").prop("checked",!displayFlag);
				}
			}
		});
	}
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sAdReqId"		name="i_sAdReqId"		value="${rvo.v_ad_req_id}"/>
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${rvo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${reqVo.i_sActionFlag}"/>
	<input type="hidden" id="i_sCancelFlag"		name="i_sCancelFlag"    value="N"/>
	<input type="hidden" id="i_sPicReqContent"	name="i_sPicReqContent" value="${rvo.v_pic_req_content}"/>
	<input type="hidden" id="i_sProductCd"		name="i_sProductCd"   	value="${rvo.v_product_cd}"/>
	<input type="hidden" id="i_sRecordId"		name="i_sRecordId"   	value="${rvo.v_record_id}"/>
	<input type="hidden" id="i_sFuncYn"			name="i_sFuncYn"   		value="${rvo.v_func_yn}"/>	<!-- 기능성여부Y,N -->
	<input type="hidden" id="i_sReportNum" 		name="i_sReportNum" 	value="${rvo.n_func_no}"/>	<!-- 보고서 번호(1,2,3) -->
	<input type="hidden" id="i_sMsgType" 		name="i_sMsgType" 		value=""/> 					<!-- 메세지타입 -->
	<input type="hidden" id="i_sMessage" 		name="i_sMessage" 		value=""/> 					<!-- 메세지타입 -->
	<input type="hidden" id="i_sApprStatus" 	name="i_sApprStatus" 	value=""/> 					<!-- 원화등록 의견 상태값 -->
	
	<input type="hidden" id="i_sCheckVessel" 	name="i_sCheckVessel" 	class="packingCheck" value="${rvo.v_vessel_yn}"/> 					<!-- 원화등록 의견 상태값 -->
	<input type="hidden" id="i_sCheckBox" 		name="i_sCheckBox" 		class="packingCheck" value="${rvo.v_box_yn}"/> 					<!-- 원화등록 의견 상태값 -->
	<input type="hidden" id="i_sCheckPaper" 	name="i_sCheckPaper" 	class="packingCheck" value="${rvo.v_paper_yn}"/> 					<!-- 원화등록 의견 상태값 -->
	
	
	<div class="content">
		<!-- 탭 영역 -->
		<ul class="sty_tab">
			<li>
				<a href="javascript:goTabView('editional');">
					<span>문안검토</span>
				</a>
			</li>
			<li>
				<a href="javascript:goTabView('original');" class="on">
					<span>원화등록요청</span>
				</a>
			</li>
		</ul>
		<!-- //탭 영역 -->
		
		<!-- 버튼이벤트 영역(상단) -->
		<div class="top_btn_area" style="z-index:1;">
			<!-- 	임시저장일 때(05)	:[수정],		[목록]		-->
			<!-- 	원화등록요청(06)	:[요청 취소],	[목록] 		-->
			<!-- 	원화등록완료(08)	:[목록] 					-->
			<!-- 	원화등록반려(07)	:[수정],		[목록] 		-->
			<!-- 	원화검토승인(09)	:[원화발송요청]		 		-->
			<!-- 	원화발송(10)						 		-->
			<c:if test="${fn:indexOf(reqVo.s_groupid,'ADMIN') > -1 || (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 && reqVo.s_userid eq rvo.v_reg_user_id)}">
				<c:if test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS06' || rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS07' ||  rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS08' ||  rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09'}">
					<a href="javascript:;" class="btnA bg_dark btn_add_packing" style="display:none;"><span>원화등록 추가요청</span></a>
					<a href="javascript:;" class="btnA bg_dark btn_add_packing_complete" style="display:none;"><span>원화등록 추가요청 </span></a>
					<a href="javascript:;" class="btnA bg_dark btn_add_packing_cancel" style="display:none"><span>원화등록 추가요청 취소</span></a>
				</c:if>
				<c:choose>
					<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS05'}">	<!-- 임시저장 상태인 경우 -->
						<a href="javascript:fnModify();" class="btnA bg_dark"><span>수정</span></a>
					</c:when>
					<%-- <c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS06'}">	<!-- 원화등록요청 상태인 경우 -->	<!--201008 비활성화 -->
						<a href="javascript:fnAppr('AD_REQ_STATUS05');" class="btnA bg_dark"><span>원화등록요청 취소</span></a>
					</c:when> --%>
					<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09' && rvo.picCheck eq 'Y'}">	<!-- 원화등록완료 상태인 경우(09(원화승인)일 때로 바꿔야함)-->
						<a href="javascript:fnAppr('AD_REQ_STATUS10');" class="btnA bg_dark"><span>원화발송요청</span></a>
					</c:when>
				</c:choose>
			</c:if>
			<a href="javascript:fnList();"	class="btnA bg_dark"><span>목록</span></a>
		</div>
		<!-- //버튼이벤트 영역(상단) -->
		
		<!-- 메인폼 영역 -->
		<div class="sec_cont">
			<h2 class="tit">원화등록요청 기재사항</h2>

			<table class="sty_02">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<tbody>
					<tr>
						<th>패킹구분</th>
						<td class="last" colspan="3">
							<label style="margin-right:10px;" for="i_sVesselYn"><input type="checkbox" id="i_sVesselYn"	name="i_sVesselYn"	class="i_sPacking" value="Y" disabled="disabled" <c:if test="${rvo.v_vessel_yn eq 'Y'}">checked</c:if>> 용기</label>
							<label style="margin-right:10px;" for="i_sBoxYn"><input 	type="checkbox" id="i_sBoxYn"		name="i_sBoxYn" 	class="i_sPacking" value="Y" disabled="disabled" <c:if test="${rvo.v_box_yn eq 'Y' }">checked</c:if>> 단상자</label>
							<label style="margin-right:10px;" for="i_sPaperYn"><input 	type="checkbox" id="i_sPaperYn"		name="i_sPaperYn" 	class="i_sPacking"  value="Y" disabled="disabled" <c:if test="${rvo.v_paper_yn eq 'Y'}">checked</c:if>> 내지 설명서</label>
						</td>
					</tr>
					<tr>
						<th>공정위 보상 문구</th>
						<td>${rvo.v_pay_tx}</td>
						<th>고객상담실</th>
						<td>${rvo.v_cs_phone_no}</td>
					</tr>
					<tr>
						<th>디자이너 아이디</th>
						<td id="">${rvo.DESIGNER_NM}</td>
						<th>외주용역 디자이너 아이디</th>
						<td id="">${rvo.ODM_DESIGNER_NM}</td>
					</tr>
					<tr>
						<th>원화 작성 요청 내역</th>
						<td class="last" colspan="3">
							${rvo.v_pic_req_content}
						</td>
					</tr>
					<tr class="tr_origin_img">
						<th>기타첨부</th>
						<td colspan="4">
							<CmTagLib:cmAttachFile uploadCd="ORI" type="viewLog" recordId="${rvo.v_ad_req_id}" />
						</td>
					</tr>
					
				</tbody>
			</table>
			
			<c:if test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS06' || rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS07' || rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS08'|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09'|| rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS10'}">
				<br>

				<h2 class="tit">원화검토</h2>
				
				<table class="sty_03">
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
						<col width="35%">
					</colgroup>
					<tbody>
						<c:if test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09' || rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS10'}">
							<tr>
								<th>패킹제작자</th>
								<c:choose>
									<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS10'}">
										<td>${rvo.PACKING_NM}</td>
									</c:when>
									<c:otherwise>
										<td>
											<input type="text" 			id="i_sPackingNm"		name="i_sPackingNm" value="${rvo.PACKING_NM}" class="inp_sty01 required"/>
											<input type="hidden" 		id="i_sPackingId"		name="i_sPackingId" value="${rvo.v_packing_odm_id}" />
											<a class="img_btn" style="cursor:pointer;"><img id="searchPacking" name="searchPacking"	src="${ImgPATH}common/ico_search.png"></a>
											<a class="img_btn" style="cursor:pointer;"><img id="delPacking" 	name="delPacking"		src="${ImgPATH}common/ico_delete.png"></a>
										</td>	
									</c:otherwise>
								</c:choose>
							</tr>
						</c:if>
						<!-- 등록완료원화(AD_REQ_STATUS08 - 원화등록완료) -->
						<c:if test="${rvo.v_vessel_yn eq 'Y' && (rvo.v_vessel_pic_status_cd eq 'PIC_REQ_STATUS03' || rvo.v_vessel_pic_status_cd eq 'PIC_REQ_STATUS04' || rvo.v_vessel_pic_status_cd eq 'PIC_REQ_STATUS05')}">
							<tr class="tr_reg_origin_img">
								<th>등록완료원화(용기)</th>
								<td colspan="4">
									<CmTagLib:cmAttachFile uploadCd="ORI_VESSEL" type="viewLog" recordId="${rvo.v_ad_req_id}" />
								</td>
							</tr>
							<!-- 용기원화 관련 메세지 s-->
							<tr>
	    						<th>용기 원화 메세지</th>
	   						 	<td colspan="3">
	    							<c:if test="${!empty msgList}"> 
	    								<ul class="mi_msg">
											<c:forEach items="${msgList }" var="vo" varStatus="s">
												<c:if test="${vo.v_packing_type eq 'VESSEL' and vo.v_del_yn eq 'N'}">
												<c:choose>
													<c:when test="${reqVo.s_userid eq vo.v_reg_user_id }">	<!-- 본인 -->
														<li class="mi_msg_to">
															${vo.v_user_nm} - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<c:if test="${(vo.v_reg_user_id eq reqVo.s_userid || fn:indexOf(reqVo.s_groupid,'ADMIN') > -1) && rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
																<a href="javascript:fnMessageModify('${vo.v_opinion_no}', '${vo.v_ra_opinion}')"><img src="${ImgPATH}btn/btn_change_small.gif" alt="modify"/></a>
																<a href="javascript:fnMessageDelete('${vo.v_opinion_no}')"><img src="${ImgPATH}btn/btn_del_small.gif" alt="delete"/></a>
															</c:if>
															
															<dl>
																<dd class="msg_cont">
																	${ cfn:removeHTMLChangeBr(vo.v_ra_opinion)}
																</dd>
																<dd class="msg_bottom"></dd>
															</dl>
														</li>
													</c:when>
													<c:otherwise>	<!-- 본인이외 -->
														<li class="mi_msg_from">
															${vo.v_user_nm} - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<dl>
																<dd class="msg_cont">
																	${cfn:removeHTMLChangeBr(vo.v_ra_opinion)}
																</dd>
																<dd class="msg_bottom"></dd>
															</dl>
														</li>
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:forEach>
									</ul>
								</c:if>
								<div class="pd_top10"></div>
					
								<c:if test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS10'}">
								<textarea name="i_sMessages" class="i_sMessages" style="height:40px; width:100%;" maxlength="2000" title="" onkeyup="fnMsgLength(this, 'span_comment_length1', 2000);"></textarea>
								<div class="pd_top10"></div>
								<div class="btn_both">
									<div class="fl">
										<span id="span_comment_length1">0</span>/2,000byte
									</div>
									<div class="btn_area">
										<a class="btnA bg_dark opBtn" id="VESSEL" style="cursor: pointer;"><span>의견등록</span></a>
										<c:if test="${rvo.v_vessel_pic_status_cd ne 'PIC_REQ_STATUS04' && rvo.v_vessel_pic_status_cd ne 'PIC_REQ_STATUS05' && (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 || fn:indexOf(reqVo.s_groupid,'ADMIN') > -1)}">
											<a class="btnA bg_dark apprBtn" id="VESSEL" style="cursor: pointer;"><span>승인</span></a>
											<a class="btnA bg_dark rjtBtn" id="VESSEL" style="cursor: pointer;"><span>반려</span></a>
										</c:if>
									</div>
								</div>
								</c:if>
							</td>
						</tr>
						<!-- 용기원화 관련 메세지 s-->
						</c:if>
						
						<c:if test="${rvo.v_box_yn eq 'Y' && (rvo.v_box_pic_status_cd eq 'PIC_REQ_STATUS03' || rvo.v_box_pic_status_cd eq 'PIC_REQ_STATUS04' || rvo.v_box_pic_status_cd eq 'PIC_REQ_STATUS05')}">
							<tr class="tr_reg_origin_img">
								<th>등록완료원화(단상자)</th>
								<td colspan="4">
									<CmTagLib:cmAttachFile uploadCd="ORI_BOX" type="viewLog" recordId="${rvo.v_ad_req_id}" />
								</td>
							</tr>
							<!-- 단상자원화 관련 메세지 s-->
							<tr>
	    						<th>단상자 원화 메세지</th>
	   						 	<td colspan="3">
	    							<c:if test="${!empty msgList }">
										<ul class="mi_msg">
											<c:forEach items="${msgList }" var="vo" varStatus="s">
												<c:if test="${vo.v_packing_type eq 'BOX' and vo.v_del_yn eq 'N'}">
												<c:choose>
													<c:when test="${reqVo.s_userid eq vo.v_reg_user_id }">	<!-- 댓글등록자 eq 본인(로긴)-->
														<li class="mi_msg_to">
															${vo.v_user_nm } - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<c:if test="${(vo.v_reg_user_id eq reqVo.s_userid || fn:indexOf(reqVo.s_groupid,'ADMIN') > -1) && rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09' }">
																<a href="javascript:fnMessageModify('${vo.v_opinion_no}', '${vo.v_ra_opinion}')"><img src="${ImgPATH}btn/btn_change_small.gif" alt="modify"/></a>
																<a href="javascript:fnMessageDelete('${vo.v_opinion_no}')"><img src="${ImgPATH}btn/btn_del_small.gif" alt="delete"/></a>
															</c:if>
															
															<dl>
																<dd class="msg_cont">
																	${ cfn:removeHTMLChangeBr(vo.v_ra_opinion)}
																</dd>
																<dd class="msg_bottom"></dd>
															</dl>
														</li>
													</c:when>
													<c:otherwise>	<!-- 본인이외 -->
														<li class="mi_msg_from">
															${vo.v_user_nm} - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<dl>
																<dd class="msg_cont">
																	${cfn:removeHTMLChangeBr(vo.v_ra_opinion) }
																</dd>
																<dd class="msg_bottom"></dd>
															</dl>
														</li>
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:forEach>
									</ul>
								</c:if>
								<div class="pd_top10"></div>
			
								<c:if test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS10'}">
									<textarea name="i_sMessages" class="i_sMessages" style="height:40px; width:100%;" maxlength="2000" title="" onkeyup="fnMsgLength(this, 'span_comment_length2', 2000);"></textarea>
									<div class="pd_top10"></div>
									<div class="btn_both">
										<div class="fl">
											<span id="span_comment_length2">0</span>/2,000byte
										</div>
										<div class="btn_area">
												<a class="btnA bg_dark opBtn" id="BOX" style="cursor: pointer;"><span>의견등록</span></a>
												<c:if test="${rvo.v_box_pic_status_cd ne 'PIC_REQ_STATUS04' && rvo.v_box_pic_status_cd ne 'PIC_REQ_STATUS05' && (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 || fn:indexOf(reqVo.s_groupid,'ADMIN') > -1)}">
													<a class="btnA bg_dark apprBtn" id="BOX" style="cursor: pointer;"><span>승인</span></a>
													<a class="btnA bg_dark rjtBtn" id="BOX" style="cursor: pointer;"><span>반려</span></a>
												</c:if>
										</div>
									</div>
								</c:if>
								</td>
							</tr>
							<!-- 단상자원화 관련 메세지 e-->
						</c:if>
						
						<c:if test="${rvo.v_paper_yn eq 'Y' && (rvo.v_paper_pic_status_cd eq 'PIC_REQ_STATUS03' || rvo.v_paper_pic_status_cd eq 'PIC_REQ_STATUS04' || rvo.v_paper_pic_status_cd eq 'PIC_REQ_STATUS05')}">
							<tr class="tr_reg_origin_img">
								<th>등록완료원화(내지 설명서)</th>
								<td colspan="3">
									<CmTagLib:cmAttachFile uploadCd="ORI_PAPER" type="viewLog" recordId="${rvo.v_ad_req_id}" />
								</td>
							</tr>
							<!-- 내지원화 관련 메세지 s-->
							<tr>
	    						<th>내지 원화 메세지</th>
	   						 	<td colspan="3">
	    							<c:if test="${!empty msgList }">
										<ul class="mi_msg">
											<c:forEach items="${msgList }" var="vo" varStatus="s">
												<c:if test="${vo.v_packing_type eq 'PAPER' and vo.v_del_yn eq 'N'}">
												<c:choose>
													<c:when test="${reqVo.s_userid eq vo.v_reg_user_id }">	<!-- 본인 -->
														<li class="mi_msg_to">
															${vo.v_user_nm } - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<c:if test="${(vo.v_reg_user_id eq reqVo.s_userid || fn:indexOf(reqVo.s_groupid,'ADMIN') > -1) && rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
																<a href="javascript:fnMessageModify('${vo.v_opinion_no}', '${vo.v_ra_opinion}')"><img src="${ImgPATH}btn/btn_change_small.gif" alt="modify"/></a>
																<a href="javascript:fnMessageDelete('${vo.v_opinion_no}')"><img src="${ImgPATH}btn/btn_del_small.gif" alt="delete"/></a>
															</c:if>
															
															<dl>
																<dd class="msg_cont">
																	${ cfn:removeHTMLChangeBr(vo.v_ra_opinion)}
																</dd>
																<dd class="msg_bottom"></dd>
															</dl>
														</li>
													</c:when>
													<c:otherwise>	<!-- 본인이외 -->
														<li class="mi_msg_from">
															${vo.v_user_nm} - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<dl>
																<dd class="msg_cont">
																	${cfn:removeHTMLChangeBr(vo.v_ra_opinion)}
																</dd>
																<dd class="msg_bottom"></dd>
															</dl>
														</li>
													</c:otherwise>
												</c:choose>
												</c:if>
										</c:forEach>
									</ul>
								</c:if>
								<div class="pd_top10"></div>
			
								<c:if test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS10'}">
									<textarea name="i_sMessages" class="i_sMessages" style="height:40px; width:100%;" maxlength="2000" title="" onkeyup="fnMsgLength(this, 'span_comment_length3', 2000);"></textarea>
									<div class="pd_top10"></div>
									<div class="btn_both">
										<div class="fl">
											<span id="span_comment_length3">0</span>/2,000byte
										</div>
										<div class="btn_area">
											<a class="btnA bg_dark opBtn" id="PAPER" style="cursor: pointer;"><span>의견등록</span></a>
											<c:if test="${rvo.v_paper_pic_status_cd ne 'PIC_REQ_STATUS04' && rvo.v_paper_pic_status_cd ne 'PIC_REQ_STATUS05' && (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 || fn:indexOf(reqVo.s_groupid,'ADMIN') > -1)}">
												<a class="btnA bg_dark apprBtn" id="PAPER" style="cursor: pointer;"><span>승인</span></a>
												<a class="btnA bg_dark rjtBtn" id="PAPER" style="cursor: pointer;"><span>반려</span></a>
											</c:if>
										</div>
									</div>
								</c:if>
								</td>
							</tr>
							<!-- 내지원화 관련 메세지 e-->
						</c:if>
						<!-- //등록완료원화(AD_REQ_STATUS08 - 원화등록완료) -->
					</tbody>
				</table>
			</c:if>
				
			<br>
		
			<c:choose>
				<c:when test="${rvo.v_func_yn eq 'Y'}">
					<!-- 기능성 데이터  -->
					<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_func_cosmetics.jsp" %>
				</c:when>
				<c:otherwise>
					<h2 class="tit">기타 기재사항</h2>
					<!-- 기능성 데이터 N 기재사항 -->
					<table class="sty_03">
						<colgroup>
							<col width="15%">
							<col width="20%">
							<col width="15%">
							<col width="50%">
						</colgroup>
						<tbody>
							<!-- 기능성 보고서 1,2,3호 공통정보 s(1)-->
							<tr>
								<th rowspan="4">제조원</th>
								<th>제조회사</th>
								<td colspan="2">${rvo.v_vendor_nm}</td>
							</tr>
							<tr>
								<th>제조국</th>
								<td colspan="2">대한민국</td>
							</tr>
							<tr>
								<th>소재지</th>
								<td colspan="2">${rvo.v_addr1}<br>${rvo.v_addr2}</td>
							</tr>
							<tr>
								<th>판매사</th>
								<td colspan="2">(주)신세계 인터네셔널</td>
							</tr>
							<tr>
								<th rowspan="3">사용시 주의 사항</th>
								<td colspan="3">
									${cfn:removeHTMLChangeBr(rvo.v_add_caution)}
								</td>
							</tr>
							<tr>
								<th><span>유형별 구분</span></th>
								<td colspan="3">
									<div style="width: 100%;">${rvo.v_func_type_nm}</div>
								</td>
							</tr>
							<tr>
								<th><span>성분별 구분</span></th>
								<td colspan="3">
									<div style="width: 100%;">${rvo.v_func_ingri_nm}</div>
								</td>
							</tr>
							<tr>
								<th><span>용법용량</span></th>
								<td colspan="4">
									${cfn:removeHTMLChangeBr(rvo.v_howto_method)}
								</td>
							</tr>
							<tr>
								<th>전성분</th>
								<td id="i_sAllMatr" colspan="3">${rvo.v_matrmemo}</td>
							</tr>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
			
			<!-- 버튼이벤트 영역(하단) -->
			<div class="btn_area verR">
				<!-- 	임시저장일 때(05)	:[수정],		[목록]		-->
				<!-- 	원화등록요청(06)	:[요청 취소],	[목록] 		-->
				<!-- 	원화등록완료(08)	:[목록] 					-->
				<!-- 	원화등록반려(07)	:[수정],		[목록] 		-->
				<!-- 	원화검토승인(09)	:[원화발송요청]		 		-->
				<!-- 	원화발송(10)						 		-->
				<c:if test="${fn:indexOf(reqVo.s_groupid,'ADMIN') > -1 || (fn:indexOf(reqVo.s_deptcd,'36000012') > -1 && reqVo.s_userid eq rvo.v_reg_user_id)}">
					<c:choose>
						<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS05'}">	<!-- 임시저장 상태인 경우 -->
							<a href="javascript:fnModify();" class="btnA bg_dark"><span>수정</span></a>
						</c:when>
						<%-- <c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS06'}">	<!-- 원화등록요청 상태인 경우 --> <!--201008 비활성화 -->
							<a href="javascript:fnAppr('AD_REQ_STATUS05');" class="btnA bg_dark"><span>원화등록요청 취소</span></a>
						</c:when> --%>
						<c:when test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09' && rvo.picCheck eq 'Y'}">	<!-- 원화등록완료 상태인 경우 -->
							<a href="javascript:fnAppr('AD_REQ_STATUS10');" class="btnA bg_dark"><span>원화발송요청</span></a>
						</c:when>
					</c:choose>
				</c:if>
				<a href="javascript:fnList();"	class="btnA bg_dark"><span>목록</span></a>
			</div>
			<!-- //버튼이벤트 영역(하단) -->
		</div>
		<!-- //메인폼 영역 -->
	</div>
</form>