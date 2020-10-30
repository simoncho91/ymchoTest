<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/view/INCLUDE/2013/cm_body_pop_start.jsp" %>

<script type="text/javascript">
j$(document).ready(function(){
	j$("#excel_down_btn").unbind("click").click(function(event){
		event.preventDefault();
		var frm = document.frm;
		frm.action = "pif_doc_asean_formula_excel.do";
		frm.submit();
	});
	j$("#close_btn").unbind("click").click(function(event){
		event.preventDefault();
		parent.cmDialogClose("preview_pop");
	});
});

</script>

<form name="frm" id="frm" action="" method="post" >
	<input type="hidden" name="i_sMatrcd" value="${reqVo.i_sMatrcd }">
	<input type="hidden" name="i_sRecordid" value="${reqVo.i_sRecordid }">
	<input type="hidden" name="i_sPartNo" value="${reqVo.i_sPartNo }">
	<input type="hidden" name="i_iVerSeq" value="${reqVo.i_iVerSeq }">
	<input type="hidden" name="i_sAllergenFlag" value="${reqVo.i_sAllergenFlag }">
	<table  style="padding: 10px;">
		<tr>
			<td width="600">
	<c:choose>
		<c:when test="${reqVo.i_sLogo_flag eq 'N'}">
				<img alt="" src="${ImgAmorePATH }${companyInfo.v_logo}" width="203" height="74" onerror="fnNoImage(this);">
		</c:when>
		<c:otherwise>
 				<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" >
		</c:otherwise>
	</c:choose>
			</td>
			<td width="600" style="text-align: right;">
				<span>${productInfo.v_company_addr1 }</span><br>
				<span>${productInfo.v_company_addr2 }</span><br>
				<span>${productInfo.v_company_addr3 }</span><br>
				<span>${productInfo.v_company_nm_en}</span><br>
				<span>Tel : ${productInfo.v_company_phone}</span><br>
				<span>Fax : ${productInfo.v_company_fax}</span>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align: center;">
				<img src="${ImgAmorePATH}line2.png" width="100%"><br><br><br>
				<span style="font-size: 24px;font-weight:bold; ">Product Formula</span><br><br><br>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table class="pif_part3_pop_pro" style="border: 1px solid #000;">
					<colgroup>
						<col width="150px">
						<col width="200px">
					</colgroup>
					<tbody>
						<tr>
							<th class="pif_part3_pop_inci_th">Product Code</th>
							<td>${productInfo.v_product_cd }</td>
						</tr>
						<tr>
							<th class="pif_part3_pop_inci_th">Product Name</th>
							<td>${productInfo.v_productnm_en }</td>
						</tr>
						
						<tr>
							<th class="pif_part3_pop_inci_th">Manufacture</th>
							<td>${productInfo.v_company_nm_en }</td>
						</tr>
					</tbody>
				</table>
				<br>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table  style="border: 1px solid #000; width:100%;">
					<colgroup>
						<col width="38%"/>
						<col width="12%"/>
						<col width="30%"/>
						<col width="20%"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="pif_part3_pop_inci_th">INCI name</th>
							<th class="pif_part3_pop_inci_th">W/W%</th>
							<th class="pif_part3_pop_inci_th">Function</th>
							<th class="pif_part3_pop_inci_th">CAS NO.</th>
						</tr>
<c:set var="total" value="0"/>
<c:choose>
    <c:when test="${reqVo.i_sAllergenFlag eq 'Y'}">
		<c:forEach items="${list }" var="vo" varStatus="s">
			<c:set var="total" value="${total+ vo.v_con_in_per}"/>
			<c:if test="${vo.v_con_nm_en eq 'FRAGRANCE'}">
				<c:set var="fragNo" value="${fragNo}${not empty fragNo ? ', ':''}${s.index+1}" />
			</c:if>
						<tr class="partNo_tr_${vo.v_partno } con_tr">
							<td class="pif_part3_pop_inci_td">${vo.v_con_nm_en }</td>
							<td class="pif_part3_pop_inci_td">${vo.v_con_in_per }</td>
							<td class="pif_part3_pop_inci_td">${vo.v_allergen_func}</td>
							<td class="pif_part3_pop_inci_td">${vo.v_odm_casno }</td>
						</tr>
			<c:set var="beforeRawCd" value="${vo.v_raw_cd }"/>
		</c:forEach>
    </c:when>
    <c:otherwise>
        <c:forEach items="${list }" var="vo" varStatus="s">
        	<c:set var="total" value="${total+ vo.v_con_in_per}"/>
            <c:if test="${vo.v_con_nm_en eq 'FRAGRANCE'}">
                <c:set var="fragNo" value="${s.index+1 }"/>
            </c:if>
                        <tr class="partNo_tr_${vo.v_partno } con_tr">
                            <td class="pif_part3_pop_inci_td">${vo.v_con_nm_en }</td>
                            <td class="pif_part3_pop_inci_td">${vo.v_con_in_per }</td>
						<td class="pif_part3_pop_inci_td">${vo.v_comp_origin_fc}</td>
                            <td class="pif_part3_pop_inci_td">${vo.v_odm_casno }</td>
                        </tr>
            <c:set var="beforeRawCd" value="${vo.v_raw_cd }"/>
        </c:forEach>
    </c:otherwise>
</c:choose>
						<tr>
							<td class="pif_part3_pop_inci_td"></td>
							<td class="pif_part3_pop_inci_td">
								<fmt:formatNumber value="${total}" type="number" var="total" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>
								${total }
							</td>
							<td class="pif_part3_pop_inci_td"></td>
							<td class="pif_part3_pop_inci_td"></td>
						</tr>
					</tbody>
				</table>
				<br>
			</td>
		</tr>
		
	<c:if test="${!empty fragList }">
		<tr><td><br/><br/></td></tr>
		<tr>
			<td colspan="4" style="text-align: left">
				<span style="text-align: center;">
					* The Supplier of the FRAGRANCE (
					<font color="red">${fragNo}</font>
					) is(
			<c:forEach items="${fragList }" var="vo" varStatus="s">
					<font color="red">${vo.v_fragrance}, ${vo.v_fragrance_smell }</font>
					[<font color="red">${vo.v_manufacture }</font>]${s.last? ' ' : ', ' }
			</c:forEach>
					).
				</span>
			</td>
		</tr>
		<tr><td><br/></td></tr>
	</c:if>
		
	<c:if test="${! empty allergenList && reqVo.i_sAllergenFlag eq 'Y'}">
		<tr>
			<td colspan="2">
				<span>(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) : </span><br/><br/>
				<span> * Allergens in fragrance : </span><br/>
				<table style="border: 1px solid #000; width:100%;">	
					<colgroup>
						<col width="40%">
						<col width="20%">
						<col width="20%">
						<col width="20%">
					</colgroup>
					<tr>
						<th class="pif_part3_pop_inci_th">INCI name</th>
						<th class="pif_part3_pop_inci_th">W/W%</th>
						<th class="pif_part3_pop_inci_th">Function</th>
						<th class="pif_part3_pop_inci_th">CAS No.</th>
					</tr>
				<c:forEach items="${allergenList }" var="vo">
					<c:choose>
					<c:when test="${productInfo.v_part_leaveon_yn == 'Y'}">
						<tr style="background-color: ${vo.v_con_in_per > 0.001 ? 'rgb(152,247,145)' : '' }">
							<td class="pif_part3_pop_inci_td"><span>${vo.v_con_nm_en }</span></td>
							<td class="pif_part3_pop_inci_td"><span>${vo.v_con_in_per }</span></td>	
							<td class="pif_part3_pop_inci_td"><span>${vo.v_allergen_func }</span></td>
							<td class="pif_part3_pop_inci_td"><span>${vo.v_comp_cas_no }</span></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr style="background-color: ${vo.v_con_in_per > 0.01 ? 'rgb(152,247,145)' : '' }">
							<td class="pif_part3_pop_inci_td"><span>${vo.v_con_nm_en }</span></td>
							<td class="pif_part3_pop_inci_td"><span>${vo.v_con_in_per }</span></td>	
							<td class="pif_part3_pop_inci_td"><span>${vo.v_allergen_func }</span></td>
							<td class="pif_part3_pop_inci_td"><span>${vo.v_comp_cas_no }</span></td>
						</tr>
					</c:otherwise>
					</c:choose>
				</c:forEach>
				</table>
			</td>
		</tr>
		<tr style="height: 20px;"></tr>
	</c:if>
	<c:if test="${!empty maycontain.v_may_contain }">
		<tr>
			<td colspan="2" style="text-align: left">
				<p>* The ingredients following after "MAY CONTAIN" : ${maycontain.v_may_contain }</p>
			</td>
		</tr>
	</c:if>
		
		<tr>
			<td colspan="2"><br/><br/><br/><br/></td>
		</tr>
		<tr>
			<td width="600" style="text-align: right;">
			</td>
			<td width="600" style="text-align: left;position: relative;">
				<div>
					<span style="font-size: 1.1em">${reqVo.i_sSignDate}</span>
					<br/>
					<br/>
					<span>Signature : 
					<img src="${RootODMFullPATH }/showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60" onerror="fnNoImage(this);">
				</div>
			</td>
		</tr>
		
	</table>
	
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	
	<ul class="btn_area">
		<li class="right">
			<a class="btnA bg_dark" href="#none" id="excel_down_btn"><span>EXCEL</span></a>
			<a class="btnA bg_dark" href="#none" id="close_btn">닫기</span></a>
		</li>
	</ul>
</form>

<style>
.pif_part3_pop_pro th, .pif_part3_pop_pro td{padding:3px; border:1px solid;bordercolor:black; text-align: left;}
.pif_part3_pop_inci_th {padding: 1px; border-left:1px solid;border-top:1px solid;border-bottom:1px solid; bordercolor:black;background-color: #eeeeee;text-align:center;font-weight:bold;}
.pif_part3_pop_inci_td {padding: 1px; border-left:1px solid;border-bottom:1px solid; bordercolor:black;text-align: center;}
</style>
<%@ include file="/WEB-INF/view/INCLUDE/2013/cm_body_pop_end.jsp" %>