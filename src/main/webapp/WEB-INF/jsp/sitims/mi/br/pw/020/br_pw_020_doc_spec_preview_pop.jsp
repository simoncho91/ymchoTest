<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<script type="text/javascript">
	j$(function() {
		j$("#btn_close").click(function(event) {
			event.preventDefault();
			
			fn_popClose();
		});
	});
	
	function fnSpecPdf() {
		var frm = j$("form[name='frm']");
		
		var recordid = frm.find("input[name='i_sRecordId']").val();
		var contentcd = frm.find("input[name='i_sContentcd']").val();
		var deptCd = frm.find("input[name='i_sDeptcd']").val();
		
		parent.document.location.href = WebPATH + "tdd/doc/tdd_doc_spec_download.do?i_sRecordId=" + recordid + "&i_sContentcd=" + contentcd + "&i_sDeptcd=" + deptCd;
	}
	
	function fnSpecExcel(){
		var frm =document.frm;
		frm.target = "hiddenframe";						
		fnWordDownLoad(frm, "/tdd/doc/tdd_doc_spec_excel.do");
	}
</script>
<form id="frm" name="frm">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId}" />
	<input type="hidden" name="i_sContentcd" value="${reqVo.i_sContentcd}" />
	<input type="hidden" name="i_sDeptcd" value="${reqVo.i_sDeptcd }"/>
</form>
<table style="padding: 10px; margin: 10px;">
	<tr>
		<td colspan="2" style="text-align: left; padding-right:30px;">
			<c:choose>
				<c:when test="${reqVo.i_sLogo_flag eq 'N' }">
					<img src="${ImgPATH}no_img.jpg" width="203" height="74" >
				</c:when>
				<c:otherwise>
					<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" >
				</c:otherwise>
			</c:choose>
		</td>		
		<td colspan="2" style="text-align:right;font-size:0.9em;">
			<span>${rvo.v_company_addr1 }</span><br>
			<span>${rvo.v_company_addr2 }</span><br>
			<span>${rvo.v_company_addr3 }</span><br>
			<span>${rvo.v_company_nm_en}</span><br>
			<span>Tel : ${companyInfo.v_phone_no }</span><br>
			<span>Fax : ${companyInfo.v_fax }</span>
		</td>
<%-- 		<td width="300" colspan="2" style="text-align: right">${cfn:removeHTMLChangeBr(apprInfo.v_info_top) }</td> --%>
	</tr>
	<tr>
		<td colspan="4"><img src="${ImgPATH}line2.png" width="650"></td>
	</tr>
	<tr>
		<td height="40" colspan="4" style="text-align: center"><font size="2" style="font-weight:bold;">Product Specification</font></td>
	</tr>
	<tr>
		<td colspan="4"><img src="${ImgPATH}line2.png" width="650"></td>
	</tr>
	<tr>
		<td><br/><br/></td>
	</tr>
	<tr>
		<td height="20" colspan="4" style="text-align: left"><font size="2" style="font-weight:bold;">Product Name &nbsp;&nbsp;: &nbsp;${rvo.v_product_nm_en }</font></td>
	</tr>
	<tr>
		<td height="20" colspan="4" style="text-align: left"><font size="2" style="font-weight:bold;">Product Code &nbsp;&nbsp;: &nbsp;${rvo.v_product_cd }</font></td>
	</tr>
	<tr>
		<td><br/><br/><br/></td>
	</tr>
</table>
<c:if test="${!empty sSpecList }">
<table style="padding: 2px;">
	<colgroup>
		<col width="5%"/>
		<col width="19%"/>
		<col width="19%"/>
		<col width="19%"/>
		<col width="19%"/>
		<col width="19%"/>
	</colgroup>
	<tr height="20" style="border: 2px solid;" bordercolor="black">
		<th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">No.</th>
        <th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">Test item<br/>시험항목</th>
        <th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">Test procedure<br/>시험법</th>
        <th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">Equipment<br/>시험기기</th>
        <th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">Requirement<br/>시험규격</th>
        <th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">Unit<br/>단위</th>
        <th style="padding: 1px; border: 1px solid; background-color: #eeeeee;" bordercolor="black">Reference<br/>참고자료</th>
	</tr>
	<c:forEach items="${sSpecList }" var="vo" varStatus="status">
	<tr>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${status.count }</td>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${vo.v_spec_div_nm }</td>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${vo.v_method }</td>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${vo.v_equipment }</td>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${vo.v_specification }</td>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${vo.v_unit }</td>
		<td style="padding: 1px; border: 1px solid; text-align: center;" bordercolor="black">${vo.v_remark }</td>
	</tr>
	</c:forEach>
	<tr>
		<td><br/><br/><br/><br/><br/></td>
	</tr>
</table>
	
</c:if>

<c:if test="${empty sSpecList }"></c:if>
<table style="padding:10px; margin:10px; width:630px;">
<colgroup>
	<col width="55%"/>
	<col width="45%"/>
</colgroup>
<tr>
	<td colspan="2">
		<br/><br/>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br/><br/>
	</td>
</tr>
<tr>
	<td></td>
	<td style="float:right; text-align: right;">
		<p style="line-height:1em;">${ISSUED_DT }</p>
		<br/>
		Signature: <c:choose>
						<c:when test="${reqVo.i_sSign_flag eq 'N' }">
							<img src="${ImgPATH}no_img.jpg" width="120" height="60" >
						</c:when>
						<c:otherwise>
							<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
						</c:otherwise>
					</c:choose>
	</td>
</tr>
</table>
<div class="pd_top10"></div>
<ul class="btn_area">
	<li class="right">
		<a href="#" class="btn_themeB" id="btn_close"><span>닫기</span></a>
	</li>
</ul>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
