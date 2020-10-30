<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
    $("#close_btn").unbind("click").click(function(event){
        event.preventDefault();
        fn_popClose();
    });
});
</script>
<form name="frm" id="frm" action="" method="post" >
    <table style="width: 100%">
        <colgroup>
            <col width="50%">
            <col width="50%">
        </colgroup>
        <tbody>
            <tr>
                <td style="text-align: left;">
<%-- 				<c:choose> --%>
<%-- 					<c:when test="${reqVo.i_sLogo_flag eq 'N'}"> --%>
<%-- 							<img alt="" src="${ImgPATH }${companyInfo.v_logo}" width="203" height="74" onerror="fnNoImage(this);"> --%>
<%-- 					</c:when> --%>
<%-- 					<c:otherwise> --%>
<%-- 							<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" > --%>
<%-- 					</c:otherwise> --%>
<%-- 				</c:choose> --%>
					<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" >
                </td>
                <td style="text-align: right; font-size: 13px;">
					<span>${productInfo.v_company_addr1 }</span><br>
                    <span>${productInfo.v_company_addr2}</span><br>
                    <span>${productInfo.v_company_addr3}</span><br>
                    <span>${productInfo.v_company_nm_en}</span><br>
					<span>Tel : ${companyInfo.v_phone_no}</span><br>
					<span>Fax : ${companyInfo.v_fax}</span>
                </td>
            </tr>
            <tr style="height: 100px;">
                <td colspan="2" style="text-align:center; font-size: 24px;font-weight:bold; line-height:200%;" >
                    <img src="${ImgPATH}line2.png" width="100%"><br>
                    Undesirable Health Effects Summary<br>
                    <img src="${ImgPATH}line2.png" width="100%">
                </td>
            </tr>
            <tr style="height:50px;">
            </tr>
            <tr>
                <td colspan="2" style="font-size: 17px; line-height:200%; font-weight:bold;">
                    <span>Product Name : ${productInfo.v_product_nm_en }</span><br>
                    <span>Product Code : ${productInfo.v_product_cd }</span>
                </td>
            </tr>
			<c:if test="${!empty reqVo.i_sAsnExp}">
					<tr>
						<td colspan="2">
							<table style="border: 1px solid #000; width:100%;">
								<colgroup>
									<col width="50%">
									<col width="50%">
								</colgroup>
								<tr style="height: 30px;">
									<th class="pif_pop_th asn_tbl_td">Countries where product being sold</th>
									<td class="pif_pop_td asn_tbl_td">${aseanExpSub[0].v_sub_codenm_en }</td>
								</tr>
								<tr style="height: 30px;">
									<th class="pif_pop_th">Total Number of Undesirable Health Effect</th>
									<td class="pif_pop_td">0</td>
								</tr>
							</table>
						</td>
					</tr>
			</c:if>
            <tr>
                <td colspan="2" style="font-size: 17px; height:220px;line-height:200%; font-weight:bold;">
				<c:choose>
					<c:when test="${!empty reqVo.i_sAsnExp}">
								<span>This letter purpose is to confirm that this products does not have any serious undesirable effects to human health in ${aseanExpSub[0].v_sub_codenm_en }.</span><br>
					</c:when>
					<c:otherwise>
	                    <span>This letter purpose is to confirm that this products does not have any serious undesirable effects to human health in Republic of Korea.</span><br>
					</c:otherwise>
				</c:choose>
                    <span>It is assessed not to cause damage to human health when being applied under normal or reasonably foreseeable conditions of use.</span><br>
                </td>
            </tr>
            <tr style="height:120px;">
            </tr>
            <tr>
                <td></td>
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${reqVo.i_sSign_flag eq 'N'}"> --%>
<!-- 				<td style="font-size: 13px;"> -->
<!-- 					<div style="position: relative;"> -->
<%-- 						<img src="${ImgPATH }${companyInfo.v_sign}" width="320" height="240" onerror="fnNoImage(this);"> --%>
<%-- 						<p style="margin-top: -171px;margin-left: 100px;color: black;">${reqVo.v_now_date }</p> --%>
<%-- 						<p style="margin-top: 87px;margin-left: 92px;color: black;margin-bottom: 80px;">Signature : <span style="text-decoration: underline;">${empty productInfo.v_repr_usernm_en? companyInfo.v_repr_usernm_en:productInfo.v_repr_usernm_en}</span></p> --%>
<!-- 					</div> -->
<!-- 				</td> -->
<%-- 			</c:when> --%>
<%-- 			<c:otherwise> --%>
<!--                 <td style="font-size: 17px;"> -->
<%-- 					<span>${reqVo.v_now_date }</span><br> --%>
<%-- 					Signature : <span style="text-decoration: underline; size: 5;">${empty productInfo.v_repr_usernm_en? companyInfo.v_repr_usernm_en:productInfo.v_repr_usernm_en}</span> --%>
<%-- 					<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);"> --%>
<!--                 </td> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
			<td style="font-size: 17px;">
				<span>${reqVo.v_now_date }</span><br>
				Signature : <span style="text-decoration: underline; size: 5;">${empty productInfo.v_repr_usernm_en? companyInfo.v_repr_usernm_en:productInfo.v_repr_usernm_en}</span>
				<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
			</td>
            </tr>
            <tr style="height:100px;"></tr>
        </tbody>
    </table>
    <div class="pd_top10"></div>
    <ul class="btn_area">
        <li class="right">
            <a class="btnA bg_dark" href="#none" id="close_btn"><span>닫기</span></a>
        </li>
    </ul>
</form>

<style>
.asn_tbl_td {border-bottom:1px solid;}
.pif_pop_th {padding: 1px;  bordercolor:black;background-color: #eeeeee;text-align:center;font-weight:bold;}
.pif_pop_td {padding: 1px; border-left:1px solid; bordercolor:black;text-align: center;}
</style>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>