<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_poBody_start.jsp" %>

<script type="text/javascript">
j$(document).ready(function(){
    j$("#close_btn").unbind("click").click(function(event){
        event.preventDefault();
        fn_popClose();
    });
});
</script>

<form name="frm" id="frm" action="" method="post" >
    <table style="width: 100%">
        <colgroup>
            <col width="50%">
            <col width="*">
        </colgroup>
        <tbody>
            <tr>
                <td style="text-align: left;">
		<c:choose>
			<c:when test="${reqVo.i_sLogo_flag eq 'N'}">
					<img alt="" src="${ImgPATH }${companyInfo.v_logo}" width="203" height="74" onerror="fnNoImage(this);">
			</c:when>
			<c:otherwise>
					<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" >
			</c:otherwise>
		</c:choose>
                	</td>
                <td style="text-align: right; font-size: 13px;">
					<span>${productInfo.v_company_addr1 }</span><br>
                    <span>${productInfo.v_company_addr2}</span><br>
                    <span>${productInfo.v_company_addr3}</span><br>
                    <span>${productInfo.v_company_nm_en}</span><br>
					<span>Tel : ${companyInfo.v_phone_no}</span><br>
					<span>Fax : ${companyInfo.v_fax}</span>
            </tr>
            <tr style="height: 100px;">
                <td colspan="2" style="text-align:center; font-size: 24px;font-weight:bold; line-height:200%;" >
                    <img src="${ImgPATH}line2.png" width="100%"><br>
                    Claim Support<br>
                    <img src="${ImgPATH}line2.png" width="100%">
                </td>
            </tr>
            <!-- <tr style="height:50px;"></tr> -->
            <tr>
                <td colspan="2" style="font-size: 17px; line-height:200%; font-weight:bold; height:200px;">
                    <span>Product Name : ${productInfo.v_product_nm_en }</span><br>
                    <span>Product Code : ${productInfo.v_product_cd }</span><br>
                    <span>Part No : ${productInfo.v_partno }</span>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="sub_table_area">
                    <table class="pif_part3_pop_pro" style="width:100%; border: 1px solid #000;">
                        <colgroup>
                            <col width="40%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                        </colgroup>
                        <tr>
                            <th>ACTIVE INGREDIENT NAME</th>
                            <th>Active Ing. %</th>
                            <th>WT%</th>
                            <th>EFFECT</th>
                        </tr>
				        <c:choose>
				            <c:when test="${!empty claimList }">
				                <c:forEach items="${claimList }" var="cvo">
				                        <tr>
				                            <td>${cvo.v_con_nm_en}</td>
				                            <td>${cvo.v_claim_active_ing}</td>
				                            <td>${cvo.v_rcont}</td>
				                            <td>${cvo.v_claim_txt}</td>
				                        </tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
				                        <tr>
				                            <td>N/A</td>
				                            <td></td>
				                            <td>N/A</td>
				                            <td>N/A</td>
				                        </tr>
				            </c:otherwise>
				        </c:choose>
                    </table>
                </td>
            </tr>
            <tr style="height:30px;"></tr>
            <tr>
                <td colspan="2" class="sub_table_area">
                    <table class="pif_part3_pop_pro" style="width:100%; border: 1px solid #000;">
                        <colgroup>
<!--                             <col width="30%"> -->
<!--                             <col width="40%"> -->
<!--                             <col width="30%"> -->
							<col width="49%">
							<col width="49%">
                        </colgroup>
                        <tr>
                            <th>Claim of product</th>
<!--                             <th>Reference</th> -->
                            <th>Clinical Report No.</th>
                        </tr>
				        <c:choose>
				            <c:when test="${!empty supportList }">
				                <c:forEach items="${supportList }" var="svo">
				                        <tr>
				                            <td>${svo.v_claim_txt }</td>
<%-- 				                            <td>${svo.v_claim_support}</td> --%>
				                    <c:choose>
					                    <c:when test="${svo.v_claim_effect eq 'ETC' }">
					                            <td>${svo.v_claim_reportno}</td>
					                    </c:when>
					                    <c:otherwise>
					                       <c:forEach items="${reportList }" var="rvo">
						                       <c:if test="${rvo.v_claim_effect eq svo.v_claim_effect }">
						                        <td>${rvo.v_claim_reportno }</td>
						                       </c:if>
						                   </c:forEach>
					                    </c:otherwise>
				                    </c:choose>
				                        </tr>
				                </c:forEach>
				            </c:when>
				            <c:otherwise>
		                        <tr>
		                            <td>N/A</td>
<!-- 		                            <td></td> -->
		                            <td></td>
		                        </tr>
				            </c:otherwise>
				        </c:choose>
                    </table>
                </td>
            </tr>
            <tr style="height:120px;"></tr>
            <tr>
                <td></td>
		<c:choose>
			<c:when test="${reqVo.i_sSign_flag eq 'N'}">
				<td style="font-size: 13px;">
					<div style="position: relative;">
						<img src="${ImgPATH }${companyInfo.v_sign}" width="320" height="240" onerror="fnNoImage(this);">
						<p style="margin-top: -171px;margin-left: 100px;color: black;">${reqVo.v_now_date }</p>
						<p style="margin-top: 87px;margin-left: 92px;color: black;margin-bottom: 80px;">Signature : <span style="text-decoration: underline;">${empty productInfo.v_repr_usernm_en? companyInfo.v_repr_usernm_en:productInfo.v_repr_usernm_en}</span></p>
					</div>
				</td>
			</c:when>
			<c:otherwise>
                <td style="font-size: 17px;">
					<span>${reqVo.v_now_date }</span><br>
					Signature : <span style="text-decoration: underline;">${!empty productInfo.v_repr_usernm_en? productInfo.v_repr_usernm_en:companyInfo.v_repr_usernm_en}</span>
					<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
                </td>
			</c:otherwise>
		</c:choose>
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
.pif_part3_pop_pro td{padding:3px; border:1px solid;bordercolor:black; text-align: center; font-size: 17px;}
.pif_part3_pop_pro th {padding:3px; border:1px solid;bordercolor:black; text-align: center; background-color: #eeeeee; font-weight:bold;font-size: 17px;}
</style>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>