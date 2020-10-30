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
		
		j$(".opBtn").on('click',function(){
			var opType	=	$(this).attr('id');
			var opinion	=	$(this).closest('td').find('.i_sMessages').val();
			fn_msgSave(opType, opinion);
		});
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
	
	//목록으로 돌아가기
	function fnList(){
		location.href="/br/er/010/init.do?openMenuCd=MIBRER010";
	}
	
	//의견등록
	function fn_msgSave(opType, opinion){
		var frm	=	j$("form[name='frm']");
		$("#i_sMsgType").val(opType);
		$("#i_sMessage").val(opinion);
		
		var postParam = $('#frm').serialize();
		
		$.ajax({
			  url		:	"/br/pr/020/br_pr_020_ori_opinion_reg_ajax.do"
			, type		:	"post"
			, data		:	postParam
			, dataType	:	"json"
			, success : function(data) {
				fn_s_alertMsg("의견이 등록되었습니다.",function(){
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
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sAdReqId"		name="i_sAdReqId"		value="${rvo.v_ad_req_id}"/>
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${rvo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${reqVo.i_sActionFlag}"/>
	<input type="hidden" id="i_sCancelFlag"		name="i_sCancelFlag"    value="N"/>
	<input type="hidden" id="i_sPicReqContent"	name="i_sPicReqContent" value="${rvo.v_pic_req_content}"/>
	<input type="hidden" id="i_sProductCd"		name="i_sProductCd"   	value="${rvo.v_product_cd}"/>
	<input type="hidden" id="i_sRecordId"		name="i_sRecordId"   	value="${rvo.v_record_id}"/>
	<input type="hidden" id="i_sFuncYn"			name="i_sFuncYn"   		value="${rvo.v_func_yn}"/> <!-- 기능성여부Y,N -->
	<input type="hidden" id="i_sReportNum" 		name="i_sReportNum" 	value="${rvo.n_func_no}"/> <!-- 보고서 번호(1,2,3) -->
	<input type="hidden" id="i_sMsgType" 		name="i_sMsgType" 		value=""/> 					<!-- 메세지타입 -->
	<input type="hidden" id="i_sMessage" 		name="i_sMessage" 		value=""/> 					<!-- 메세지타입 -->
	
	
	<div class="content" style="/*padding-left:15px;*/">
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
							<label style="margin-right:10px;" for="i_sVesselYn"><input type="checkbox" id="i_sVesselYn"	name="i_sVesselYn"	value="Y" disabled="disabled" <c:if test="${rvo.v_vessel_yn eq 'Y'}">checked</c:if>> 용기</label>
							<label style="margin-right:10px;" for="i_sBoxYn"><input 	type="checkbox" id="i_sBoxYn"		name="i_sBoxYn" 	value="Y" disabled="disabled" <c:if test="${rvo.v_box_yn eq 'Y' }">checked</c:if>> 단상자</label>
							<label style="margin-right:10px;" for="i_sPaperYn"><input 	type="checkbox" id="i_sPaperYn"		name="i_sPaperYn" 	value="Y" disabled="disabled" <c:if test="${rvo.v_paper_yn eq 'Y'}">checked</c:if>> 내지 설명서</label>
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
			
			<c:if test="${rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS08' || rvo.v_ad_req_status_cd eq 'AD_REQ_STATUS09'}">
				<br>
				
				<h2 class="tit">원화검토</h2>
				
				<table class="sty_02">
					<colgroup>
						<col width="15%">
						<col width="35%">
						<col width="15%">
						<col width="35%">
					</colgroup>
					<tbody>
						<tr>
							<th>패킹제작자</th>
							<td>
								<input type="text" 			id="i_sPackingNm"		name="i_sPackingNm" value="${rvo.PACKING_NM}" class="inp_sty01 required"/>
								<input type="hidden" 		id="i_sPackingId"		name="i_sPackingId" value="${rvo.v_packing_odm_id}" />
								<button type="button" class="img_btn">
									<img id="searchPacking" name="searchPacking"	src="${ImgPATH}common/ico_search.png">
								</button>
								<button type="button" class="img_btn">
									<img id="delPacking" 	name="delPacking"		src="${ImgPATH}common/ico_delete.png">
								</button>
							</td>	
						</tr>
					
						<!-- 등록완료원화(AD_REQ_STATUS08 - 원화등록완료) -->
						<c:if test="${rvo.v_vessel_yn eq 'Y'}">
							<tr class="tr_reg_origin_img">
								<th>등록완료원화(용기)</th>
								<td colspan="4">
									<CmTagLib:cmAttachFile uploadCd="ORI_VESSEL" type="viewLog" recordId="${rvo.v_ad_req_id}_ORI_VESSEL" />
								</td>
							</tr>
							<!-- 용기원화 관련 메세지 s-->
							<tr>
	    						<th>용기 원화 메세지</th>
	   						 	<td colspan="3">
	    							<c:if test="${!empty msgList}">
										<ul class="mi_msg">
											<c:forEach items="${msgList }" var="vo" varStatus="s">
												<c:if test="${vo.v_packing_type eq 'VESSEL'}">
												<c:choose>
													<c:when test="${reqVo.s_userid eq vo.v_reg_user_id }">	<!-- 본인 -->
														<li class="mi_msg_to">
															${vo.v_user_nm} - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<c:if test="${(vo.v_reg_user_id eq reqVo.s_userid || reqVo.s_sysadmin_yn eq 'Y') && rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
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
								<c:if test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
									<textarea name="i_sMessages" class="i_sMessages" style="height:40px; width:100%;" maxlength="2000" alt="" title="" onkeyup="fnMsgLength(this, 'span_comment_length', 2000);"></textarea>
									<div class="pd_top10"></div>
									<div class="btn_both">
										<div class="fl">
											<span id="span_comment_length">0</span>/2,000byte
										</div>
										<div class="btn_area">
											<a class="btnA bg_dark opBtn" id="ves" style="cursor: pointer;"><span>의견등록</span></a>
										</div>
									</div>
								</c:if>
							</td>
						</tr>
						<!-- 용기원화 관련 메세지 s-->
						</c:if>
						
						<c:if test="${rvo.v_box_yn eq 'Y'}">
							<tr class="tr_reg_origin_img">
								<th>등록완료원화(단상자)</th>
								<td colspan="4">
									<CmTagLib:cmAttachFile uploadCd="ORI_BOX" type="viewLog" recordId="${rvo.v_ad_req_id}_ORI_BOX" />
								</td>
							</tr>
							<!-- 단상자원화 관련 메세지 s-->
							<tr>
	    						<th>단상자 원화 메세지</th>
	   						 	<td colspan="3">
	    							<c:if test="${!empty msgList }">
										<ul class="mi_msg">
											<c:forEach items="${msgList }" var="vo" varStatus="s">
												<c:if test="${vo.v_packing_type eq 'BOX'}">
												<c:choose>
													<c:when test="${reqVo.s_userid eq vo.v_reg_user_id }">	<!-- 댓글등록자 eq 본인(로긴)-->
														<li class="mi_msg_to">
															${vo.v_user_nm } - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															<c:if test="${(vo.v_reg_user_id eq reqVo.s_userid || reqVo.s_sysadmin_yn eq 'Y') && rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
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
			
								<c:if test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
									<textarea name="i_sMessages" class="i_sMessages" style="height:40px; width:100%;" maxlength="2000" alt="" title="" onkeyup="fnMsgLength(this, 'span_comment_length', 2000);"></textarea>
									<div class="pd_top10"></div>
									<div class="btn_both">
										<div class="fl">
											<span id="span_comment_length">0</span>/2,000byte
										</div>
										<div class="btn_area">
												<a class="btnA bg_dark opBtn" id="box" style="cursor: pointer;"><span>의견등록</span></a>
										</div>
									</div>
								</c:if>
								</td>
							</tr>
							<!-- 단상자원화 관련 메세지 e-->
						</c:if>
						
						<c:if test="${rvo.v_paper_yn eq 'Y'}">
							<tr class="tr_reg_origin_img">
								<th>등록완료원화(내지 설명서)</th>
								<td colspan="4">
									<CmTagLib:cmAttachFile uploadCd="ORI_PAPER" type="viewLog" recordId="${rvo.v_ad_req_id}_ORI_PAPER" />
								</td>
							</tr>
							<!-- 내지원화 관련 메세지 s-->
							<tr>
	    						<th>내지 원화 메세지</th>
	   						 	<td colspan="3">
	    							<c:if test="${!empty msgList }">
										<ul class="mi_msg">
											<c:forEach items="${msgList }" var="vo" varStatus="s">
												<c:if test="${vo.v_packing_type eq 'PAPER'}">
												<c:choose>
													<c:when test="${reqVo.s_userid eq vo.v_reg_user_id }">	<!-- 본인 -->
														<li class="mi_msg_to">
															${vo.v_user_nm } - ${vo.v_dept_nm} (${vo.v_reg_dtm})
															
															<c:if test="${(vo.v_reg_user_id eq reqVo.s_userid || reqVo.s_sysadmin_yn eq 'Y') && rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
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
			
								<c:if test="${rvo.v_ad_req_status_cd ne 'AD_REQ_STATUS09'}">
									<textarea name="i_sMessages" class="i_sMessages" style="height:40px; width:100%;" maxlength="2000" alt="" title="" onkeyup="fnMsgLength(this, 'span_comment_length', 2000);"></textarea>
									<div class="pd_top10"></div>
									<div class="btn_both">
										<div class="fl">
											<span id="span_comment_length">0</span>/2,000byte
										</div>
										<div class="btn_area">
											<a class="btnA bg_dark opBtn" id="pap" style="cursor: pointer;"><span>의견등록</span></a>
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
					<h2 class="tit">기능성 보고서 데이터</h2>
					<table class="sty_02 funcN">
						<colgroup>
							<col width="35%">
						</colgroup>
						<tbody>
							<tr>
								<td>기능성 제품만 기능성 보고 데이터를 확인할 수 있습니다.</td>
							</tr>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
			
			<!-- 버튼이벤트 영역(하단) -->
			<div class="btn_area verR">
				<a href="javascript:fnList();"	class="btnA bg_dark"><span>목록</span></a>
			</div>
			<!-- //버튼이벤트 영역(하단) -->
		</div>
		<!-- //메인폼 영역 -->
	</div>
</form>