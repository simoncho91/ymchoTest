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
                </td>
            </tr>
            <tr style="height: 100px;">
                <td colspan="2" style="text-align:center; font-size: 24px;font-weight:bold; line-height:100%;" >
                    <img src="${ImgPATH}line2.png" width="100%">
                    LETTER OF AGREEMENT<br>
                    <img src="${ImgPATH}line2.png" width="100%" style="margin-top: 15px;">
                </td>
            </tr>
            <tr style="height:100px;">
            </tr>
            <tr>
                <td colspan="2" style="font-size: 17px; line-height:200%; font-weight:bold;">
                    <span>We, undersigned ${companyInfo.v_vendor_nm_en } do hereby certify that we have </span><br>
                    <span>been authorized by Shinsegae International Inc to manufacture respective product：</span>
                </td>
            </tr>
            <tr style="height:120px;">
                <td colspan="2" style="font-size: 17px; line-height:200%; font-weight:bold;">
                    <span>${productInfo.v_product_nm_en }</span>
				</td>                   
            </tr>
            <tr style="height:200px;">
				<td style="font-size: 17px;font-weight:bold;text-align: left;">
					<span>Authorized by：Shinsegae International Inc</span><br>
					Signature :    __________				
				</td>				
				<td></td>
            </tr>
            <tr>
				<td style="font-size: 17px;font-weight:bold;text-align: left;">
					<span>Authorized by：${companyInfo.v_vendor_nm_en }</span><br>
					Signature : 
					<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
				</td>				
				<td></td>
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