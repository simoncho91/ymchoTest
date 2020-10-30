<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<script type="text/javascript">
j$(function(){
	j$(".btn_send").unbind("click").click(function(event){
		event.preventDefault();
		var result = true;
		var contentByte = j$("#span_content_length").text();
		contentByte = parseInt(contentByte.replace(',',''));
				
		if(fn_isNull($('input[name=i_sOdmUserEmail]').val())){
			fn_s_alertMsg("등록된 E-MAIL이 없습니다.");
			result = false;
		}
		if(parseInt(j$("#span_title_length").text()) <= 0){
			fn_s_alertMsg("제목을 입력해주세요.");			
			result = false;
		}
		if(contentByte <= 0){
			fn_s_alertMsg("내용을 입력해주세요.");			
			result = false;
		}
		if(parseInt(j$("#span_title_length").text()) > 300){
			fn_s_alertMsg("제목이 300byte가 넘었습니다.");			
			result = false;
		}
		
		if(contentByte > 4000){
			fn_s_alertMsg("내용이 4,000byte가 넘었습니다.");			
			result = false;
		}
		if(result){
			$.ajax({
			    url : "/br/pw/020/doc/requestSendMail.do"
			    , type : "POST"
			    , data : j$("form[name='frmMail']").serialize()
			    , dataType : "json"
			    
			    , success : function(data) {			    		
		    		fn_s_alertMsg("요청 메일을 발송하였습니다.",fn_popClose);			    	
			    } ,error : function(jqXHR, textStatus, errorThrown){
					fn_s_failMsg(jqXHR, textStatus, errorThrown);
				}		
			});
		}
	});
	
	j$(".btn_close").unbind("click").click(function(event){
		event.preventDefault();
		fn_popClose();
	});
	
});
</script>
<form name="frmMail" id="frmMail" action="" method="post" >
<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId }"/>
<input type="hidden" name="i_sProductCd" value="${reqVo.i_sProductCd }"/>
<input type="hidden" name="i_sDocCd" value="${reqVo.i_sDocCd}"/>
<input type="hidden" name="i_sDocNm" value="${reqVo.i_sDocNm}"/>

<input type="hidden" name="i_sNationCd" value="${reqVo.i_sNationCd}"/>

<input type="hidden" name="i_sVendorLaborId" value="${rVo.v_vendor_labor_id}"/>
<input type="hidden" name="i_sOdmUserEmail" value="${rVo.v_odm_user_email}"/>

    <div class="tb_line"></div>

    <table class="sty_03" style="table-layout:fixed;">
        <colgroup>
            <col width="100">
            <col width="*">
        </colgroup>
	    <tbody>
	        <tr>
	            <th class="ta_c">제목</th>
	            <td class="ta_l">
	                <input class="inp_sty01" type="text" name="i_sSubject" style="width: 95%;" onkeyup="fnMsgLength(this, 'span_title_length', 300);"/>
	                <br/><span id="span_title_length">0</span>/300byte
	            </td>
	        </tr>
	        <tr>
	            <th class="ta_c">수신자</th>
	            <td class="last">
	            	${rVo.v_odm_user_nm }
	            </td>
	        </tr>
		    <tr>
		        <th class="ta_c">내용</th>
		        <td class="last">
		            <textarea id="i_sContents" name="i_sContents" class="textarea_sty01" style="width: 95%;height:100px;" onkeyup="fnMsgLength(this, 'span_content_length', 4000);"></textarea>
		            <br/><span id="span_content_length">0</span>/4,000byte
		        </td>
		    </tr>
	    </tbody>
    </table>
    
    <div class="pd_top10"></div>
    
    <ul class="btn_area">
        <li class="right">
			<a class="btnA bg_dark btn_send" href="#" id="btn_send"><span>전송</span></a>
			<a class="btnA bg_dark btn_close" href="#" id="btn_close"><span>닫기</span></a>
        </li>
    </ul>

</form>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>