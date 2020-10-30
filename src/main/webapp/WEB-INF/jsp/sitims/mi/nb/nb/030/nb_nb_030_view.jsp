<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_start.jsp" %>

<style>
	div.dhx_window__inner-html-content{
		width:100%;
		height:100%;
	}
</style>
<script src="/ckeditor/ckeditor.js"></script><!-- ckeditor 사용시 -->
<script type="text/javascript" src="${ScriptPATH}mi/br/pr/020/br_pr_020_reg.js"></script>
<script type="text/javascript">
	var prodSearch;

	j$(function(){
		fn_init();
	});
	
	function fn_init(){
		addButtonEvent();
		
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
		
		var date = ${rvo.v_update_dtm};
		var sdate = fn_getFormatDate(date.toString());
		
		$("#i_sReg_dtm").html(sdate);
	}
	
	function addButtonEvent(){
		// 목록
		$('.btn_list').click(function(e){
	 		location.href="/nb/nb/030/init.do";
	 	});
		
	}
	
	function fnSave(status){
		var frm 	=	document.frm;
		
		$("#i_sReqText").val(reqText);
		
		//등록, 수정화면 플래그 처리
		if($('#i_sActionFlag').val() == ""){
			frm["i_sActionFlag"].value = "R";
		}
		
		var frm 				=	document.frm;
		frm["i_sStatus"].value  = 	status;
		frm.action 				= 	"/nb/nb/030/nb_nb_030_save.do";
		frm.submit();
	}
	
	
	function fnCancel(status){
		//검토요청취소용 추가 플래그
		$("#i_sCancelFlag").val("Y");
		$("#i_sActionFlag").val("M");
		
		var frm						=	document.frm;
		frm["i_sStatus"].value		=	status;
		frm.action					=	"/nb/nb/030/nb_nb_030_save.do";
		frm.submit();
	}
	
	//수정
	function fnModify(){
		var frm			= document.frm;
		
		frm["i_sActionFlag"].value = "M";
			
		frm.action		= "/nb/nb/030/nb_nb_030_reg.do";
		frm.submit();
	}
	
	//삭제
	function fnDelete(){
		var frm 				=	document.frm;
		var postParam = $('#frm').serialize();
		
		dhx.confirm({
			header: "공지글 삭제",
			text: '삭제하시겠습니까?',
			buttons: ["예", "아니오"],
			buttonsAlignment:"center"
		}).then(function(answer){
			if(answer){
				frm["i_sStatus"].value  = 	status;
		$.ajax({
		     url: "/nb/nb/030/nb_nb_030_del.do",
		     data: postParam,
		     type: "POST",
		     dataType: "json",
		     success:function(data){
		    	if(data.status=="success"){
			 		//location.href='/si/im/010/init.do?openMenuCd=MISIIM010';
			 		fn_s_alertMsg('삭제되었습니다.',function(){
			 			location.href='/nb/nb/030/init.do';
			 		});
		    	}
	    	 },error : function(jqXHR, textStatus, errorThrown){
		        fn_s_failMsg(jqXHR, textStatus, errorThrown);
			}
	     });
		}
	});
}
	
</script>
	
<form name="frm" id="frm" action="${reqVo.i_sPageUrl}" method="post">
	<input type="hidden" id="i_sStatus"			name="i_sStatus"		value="${rvo.v_ad_req_status_cd}"/>
	<input type="hidden" id="i_sActionFlag"		name="i_sActionFlag"    value="${i_sActionFlag}"/>
	<input type="hidden" id="i_sCancelFlag"		name="i_sCancelFlag"    value="N"/>
	<input type="hidden" id="i_sUserNm"			name="i_sUserNm"		value="${reqVo.s_usernm}"/>
	<input type="hidden" id="i_sRecordId"			name="i_sRecordId"		value="${rvo.v_record_id}"/>
	<div class="content" style="/*padding-left:15px;*/">
		<div id="layer_simple_view" style="position:absolute;width:360px;display:none;z-index:10000;">
			<div class="div_status" style="padding:12px;border:2px solid #FAED7D;background:#FAED7D;">
				<span style="line-height: 1.5em;font-weight: bold; color:black;" class="span_text"></span>
			</div>
		</div>
		
		  <div class="sec_cont">
	        <h2 class="tit">공지사항 상세</h2>
	                <!-- 버튼이벤트 영역(상단) -->
	            <div class="top_btn_area">
	                <c:if test="${rvo.v_update_user_id eq reqVo.s_usernm}">	<!-- 임시저장인 경우 -->
	                    <a href="javascript:fnModify();" class="btnA bg_dark"><span>수정</span></a>
	                    <a href="javascript:fnDelete();" class="btnA bg_dark"><span>삭제</span></a>
	                </c:if>
	            <a href="../030/init.do?openMenuCd=MINBNB030"	class="btnA bg_dark btn_list"><span>목록</span></a>
	    
	            </div>
	        <table class="sty_02">
	            <colgroup>
	                <col width="15%">
	                <col width="35%">
	                <col width="15%">
	                <col width="35%">
	            </colgroup>
	            <tbody>
	                <tr>
	                    <th>* 제목</th>
	                    <td class="last" colspan="3">${rvo.v_title}</td>
	                </tr>
	                <tr>
	                    <th>* 등록자</th>
	                    <td class="TouTr">${rvo.v_reg_user_nm}</td>
	                    <th>등록일자</th>
	                    <td class="last TouTr" id="i_sReg_dtm"></td>
	                </tr>
	                <!-- 에디터 s -->
	                <tr>
	                    <th>내용</th> 
	                    <td colspan="3" style="height: auto;">
	                        <div id="">${rvo.c_content}</div>
	                    </td>
	                </tr>
	                <!-- 에디터 e -->
	                <tr class="tr_origin_img">
	                    <th>첨부파일</th>
	                    <td colspan="4">
	                        <CmTagLib:cmAttachFile uploadCd="NB" type="viewLog" recordId="${rvo.v_record_id}" />
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	        <div class="btn_area verR">
	                <c:if test="${rvo.v_update_user_id eq reqVo.s_userid}">	<!-- 본인인 경우 -->
	                    <a href="javascript:fnModify();" class="btnA bg_dark"><span>수정</span></a>
	                    <a href="javascript:fnDelete();" class="btnA bg_dark"><span>삭제</span></a>
	                </c:if>
	            <a href="../030/init.do?openMenuCd=MINBNB030"	class="btnA bg_dark btn_list"><span>목록</span></a>
	    
	        </div>
	    </div>
    </div>
</form>
