<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>

<style type="text/css">
.doc_line{border-top: 3px solid #54575f;}
.doc_table{width: 100%;}
.doc_table tbody td{font-weight:600;font-size: 10pt;padding:10px 0px; margin:5px;}
.doc_table tbody .td_title{text-align: left;background-color: #eeeeee;}
.doc_table tbody .div_tb_sub div{margin: 7px 0px;}
.doc_table tbody .div_tb_sub .div_fir{width:15%;float:left;}
.doc_table tbody .div_tb_sub .div_sec{width:85%;float:left;}
.doc_sub_table{border: 1px solid #54575f;width: 100%;}
.doc_sub_table thead tr th{border-right: 1px solid #54575f;padding:3px;text-align: center;font-weight: 500;font-size:8pt;}
.doc_sub_table thead tr th.last{border-right: 0px solid #54575f;}
.doc_sub_table tbody tr td{border-right:1px solid #54575f;border-top:1px solid #54575f;padding:3px;font-weight: 500;font-size:8pt;}
.doc_sub_table .no_border{border-top:none;}
.doc_sub_table tbody tr td.last{border-right: 0px solid #54575f;}
.doc_sub_table .inp_cli{font-size:8pt;height:20px;width:90%;}

.doc_sub_table2{border: 1px solid #54575f;width: 100%;}
.doc_sub_table2 tbody tr td{border-right:1px solid #54575f;border-top:1px solid #54575f;padding:3px;font-weight: 500;text-align: center;}
.doc_sub_table2 tbody tr td.td_bold{font-weight: 600;}
.doc_sub_table2 .no_border{border-top:none;}
.doc_sub_table2 tbody tr td.last{border-right: 0px solid #54575f;}

.doc_sub_last{padding:0px 10px;}
.doc_sub_last .doc_content{line-height: 20px;margin-top: 5px;font-size:10pt;font-weight: 600;}
.doc_sub_last .doc_last{line-height: 20px;text-align:center;margin-top: 15px;font-size:10pt;}

.txt_bold {font-weight:bold;}

.pd_top10 {padding-top:10px;}
.pd_top20 {padding-top:20px;}
</style>
<table style="padding:20px; margin:20px; width:740px;">
	<colgroup>
		<col width="185px;">
		<col width="185px;">
		<col width="185px;">
		<col width="185px;">
	</colgroup>
	<tbody>
	<tr>
		<td colspan="2" style="text-align: left; padding-right:30px;">
<%-- 			<img alt="" src="${ImgPATH }${companyInfo.v_logo !=null ? companyInfo.v_logo:'logo.png'}" width="203" height="74" onerror="fnNoImage(this);"> --%>
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
				<span>${productInfo.v_company_addr1 }</span><br>
				<span>${productInfo.v_company_addr2 }</span><br>
				<span>${productInfo.v_company_addr3 }</span><br>
				<span>${productInfo.v_vendor_nm}</span><br>
				<span>Tel : ${companyInfo.v_phone_no}</span><br>
				<span>Fax : ${companyInfo.v_fax}</span>
		</td>
	</tr>
	<tr>
		<td colspan="4"><img src="${ImgPATH}line2.png" width="740px;"></td>
	</tr>
	<tr>
		<td colspan="4"><p style="font-size:1.2em;text-align: center;font-weight: 600;">Product Formula</p></td>
	</tr>
	<tr>
		<td colspan="3">
			<table class="doc_table">
				<tbody>
					<tr>
						<div class="div_tb_sub">
							<table class="doc_sub_table">
								<colgroup>
									<col width="20%;">
									<col width="80%;">
								</colgroup>
								<tbody>
								<tr>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">Product code</td>
									<td>${productInfo.v_product_cd }</td>
								</tr>
								<tr>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">Product Name</td>
									<td>${productInfo.v_product_nm_en }</td>
								</tr>
								<tr>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">Manufacture</td>
									<td>${productInfo.v_vendor_nm }</td>
								</tr>
								</tbody>
								
							</table>
						</div>
				</tbody>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="4"><br/></td>
	</tr>
	<tr>
		<td colspan="4">
		
			<table class="doc_table">
				<tbody>
					<tr>
						<div class="div_tb_sub">
							<table class="doc_sub_table">
								<colgroup>
									<col width="30%;">
									<col width="20%;">
									<col width="30%;">
									<col width="20%;">
								</colgroup>
								<tbody>
								<tr>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">INCI name</td> <!-- 성분명 --> 
									<td class="td_title txt_bold" style="background-color: #eeeeee;">W/W%</td>	<!-- 함량 -->
									<td class="td_title txt_bold" style="background-color: #eeeeee;">Function</td> <!-- func-->
									<td class="td_title txt_bold" style="background-color: #eeeeee;">CAS NO.</td> <!-- casno -->
								</tr>
																
								<c:set var="total" value="0"/>
								<c:forEach items="${list }" var="vo" varStatus="s">
									<c:set var="total" value="${total+ vo.v_con_in_per}"/>
									<c:if test="${vo.v_con_nm_en eq 'FRAGRANCE'}">
										<c:set var="fragNo" value="${fragNo}${not empty fragNo ? ', ':''}${s.index+1}" />
									</c:if>
												<tr>
													<td >${vo.v_con_nm_en }</td>
													<td >${vo.v_con_in_per }</td>
													<td >${vo.v_allergen_func}</td>
													<td >${vo.v_odm_casno }</td>
												</tr>
									<c:set var="beforeRawCd" value="${vo.v_raw_cd }"/>
								</c:forEach>
								<tr>
									<td ></td>
									<td >
										<fmt:formatNumber value="${total}" type="number" var="total" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>
										${total }
									</td>
									<td ></td>
									<td ></td>
								</tr>
								
								</tbody>
								
							</table>
						</div>
				</tbody>
			</table>
		
		</td>
	</tr>
	
	<c:if test="${!empty fragList }">
		<tr><td><br/></td></tr>
		<tr>
			<td colspan="4" style="text-align: left">
				<span style="font-size:0.7em;">
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
	
<%-- 	<c:if test="${! empty allergenList && reqVo.i_sAllergenFlag eq 'Y'}"> --%>
	<tr>
		<td colspan="4">
			<span style="font-size:0.7em;">(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) : </span><br/>
			<span style="font-size:0.7em;"> * Allergens in fragrance : </span>
			<table class="doc_table">
				<tbody>
					<tr>
						
						<div class="div_tb_sub">
							<table class="doc_sub_table">
								<colgroup>
									<col width="40%">
									<col width="20%">
									<col width="20%">
									<col width="20%">
								</colgroup>
								<tbody>
								<tr>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">INCI name</th>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">W/W%</th>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">Function</th>
									<td class="td_title txt_bold" style="background-color: #eeeeee;">CAS No.</th>
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
								</tbody>
							</table>
						</div>
					</tr>
				</tbody>
			</table>
		
		</td>
	</tr>
<%-- 	</c:if> --%>
	
	<c:if test="${!empty maycontain.v_may_contain }">
		<tr><td colspan="4"><br/></td></tr>
		<tr>
			<td colspan="4" style="text-align: left">
				<span style="font-size:0.7em;">* The ingredients following after "MAY CONTAIN" : ${maycontain.v_may_contain }</span>
			</td>
		</tr>
	</c:if>
		
	<tr><td colspan="4"><br/><br/><br/></td></tr>
	
	<tr>
		<td colspan="2"></td>
		<td colspan="2" style="font-size:0.9em;position: relative;text-align: left;">
			<div>
				<span style="font-size: 1.1em">${reqVo.i_sSignDate}</span>
				<br/>
				<span style="margin-right: 64px;">${reqVo.v_now_date }</span><br>
	            Signature :
				<c:choose>
					<c:when test="${reqVo.i_sSign_flag eq 'N' }">
						<img src="${ImgPATH}no_img.jpg" width="120" height="60" >
					</c:when>
					<c:otherwise>
						<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
					</c:otherwise>
				</c:choose>
            
			</div>
		</td>
	</tr>
	</tbody>
	
	
	
</table>	

<!-- </div> -->
