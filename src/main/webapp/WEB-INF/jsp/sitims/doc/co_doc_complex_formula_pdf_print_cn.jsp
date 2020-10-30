<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>

<style type="text/css">
.doc_line{border-top: 3px solid #54575f;}
.doc_table{width: 100%;}
.doc_table tbody td{font-weight:600;font-size: 10pt;padding:10px 0px; margin:5px;}
.doc_table tbody .td_title{text-align: left;background-color: #eeeeee;}
.doc_table tbody .td_title_center{text-align: center;background-color: #eeeeee;}
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
.doc_sub_table2 thead tr th{border-right: 1px solid #54575f;padding:3px;text-align: center;font-weight: 500;font-size:6pt;}
.doc_sub_table2 thead tr th.last{border-right: 0px solid #54575f;}
.doc_sub_table2 tbody tr td{border-right:1px solid #54575f;border-top:1px solid #54575f;padding:3px;font-weight: 500;font-size:4pt;}
.doc_sub_table2 .no_border{border-top:none;}
.doc_sub_table2 tbody tr td.last{border-right: 0px solid #54575f;}
.doc_sub_table2 .inp_cli{font-size:8pt;height:20px;width:90%;}

.doc_sub_last{padding:0px 10px;}
.doc_sub_last .doc_content{line-height: 20px;margin-top: 5px;font-size:10pt;font-weight: 600;}
.doc_sub_last .doc_last{line-height: 20px;text-align:center;margin-top: 15px;font-size:10pt;}

.txt_bold {font-weight:bold;}

.pd_top10 {padding-top:10px;}
.pd_top20 {padding-top:20px;}
</style>

<%-- <div style="background: url('${Img2017PATH}emblem_resize.png'); background-position: center;"> --%>
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
				<span>${productInfo.v_company_nm_en}</span><br>
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
			<td colspan="2">
				<table class="doc_table">
					<tbody>
						<tr>
							<td>
								<div class="div_tb_sub">
									<table class="doc_sub_table">
										<colgroup>
											<col width="30%;">
											<col width="70%;">
										</colgroup>
										<tbody>
											<tr>
												<td class="td_title txt_bold">Product code</td>
												<td>${productInfo.v_product_cd }</td>
											</tr>
											<tr>
												<td class="td_title txt_bold">Product Name</td>
												<td>${productInfo.v_product_nm_en }</td>
											</tr>
											<tr>
												<td class="td_title txt_bold">Manufacture</td>
												<td>${productInfo.v_vendor_nm }</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>	
					</tbody>
				</table>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="doc_table">
					<tbody>
						<tr>
							<td>
								<div class="div_tb_sub">
									<table class="doc_sub_table2">
										<colgroup>
											<col width="25px"/><!-- no -->
											<col width="65px"/><!-- rawcode -->
											<col width="160px"/><!-- chinese name -->
											<col width="150px"/><!-- INCI name -->
											<col width="94px"/><!-- WT -->
											<col width="94px"/><!-- mixure ratio -->
											<col width="94px"/><!-- actual Wt -->
											<col width="120px"/><!-- chinese Function -->
											<col width="120px"/><!-- Function -->
											<col width="80px"/><!-- CAS no -->
											<col width="65px"/><!-- Remark -->
											<col width="65px"/><!-- Safety_AP standard -->
											<col width="100px"><!-- Safety_ODM standard  -->
											<col width="65px"/><!-- global forbid -->
											<col width="65px"/><!-- global limit -->
										</colgroup>
										<tbody>
											<tr>
												<td class="td_title_center txt_bold">No</td>
												<td class="td_title_center txt_bold">RawCode</td>
												<td class="td_title_center txt_bold">Chinese name</td>
												<td class="td_title_center txt_bold">INCI Name</td>
												<td class="td_title_center txt_bold">Wt(%)</td>
												<td class="td_title_center txt_bold">mixure ratio</td>
												<td class="td_title_center txt_bold">Actual Wt(%)</td>
												<td class="td_title_center txt_bold">Chinese Function</td>
												<td class="td_title_center txt_bold">Function</td>
												<td class="td_title_center txt_bold">CAS No.</td>
												<td class="td_title_center txt_bold">Remark</td>												
												<td class="td_title_center txt_bold">Safety_ODM standard </td>
												<td class="td_title_center txt_bold">Global<br>forbid</td>
												<td class="td_title_center txt_bold">Global<br>limit</td>
											</tr>
											<c:set value="1" var="cnt"/>
											<c:set value="" var="beforeRawCd"/>
											<c:set value="" var="beforePartNo"/>
											<c:set value="0" var="wt_total"/>
											<c:set value="0" var="actual_wt_total"/>
									<c:forEach items="${list }" var="vo" varStatus="s">
										<c:if test="${vo.v_allergen_yn ne 'Y' }">
											<c:set value="${actual_wt_total+vo.v_actual_wt*1000 }" var="actual_wt_total"/>
											<c:if test="${vo.v_con_nm_en eq 'FRAGRANCE'}">
												<c:set var="fragNo" value="${cnt}"/>
											</c:if>
											<tr class="con_tr partNo_tr_${vo.n_part_no }">
											<c:if test="${beforePartNo ne vo.n_part_no}">
												<c:set value="1" var="cnt"/>
											</c:if>
											<c:if test="${beforeRawCd ne vo.v_raw_cd}">
												<c:set value="${wt_total+vo.v_raw_per*1000 }" var="wt_total"/>
												<td class="pif_part3_pop_inci_td" rowspan="${vo.n_raw_cnt }">${cnt }</td>
												<td class="pif_part3_pop_inci_td" rowspan="${vo.n_raw_cnt }">${vo.v_raw_cd }${vo.v_type eq 'AP'? ' (AP)':'' }</td>
												<c:set var="cnt" value="${cnt+1 }"/>
											</c:if>
												<td class="pif_part3_pop_inci_td">${vo.v_con_nm_cn }</td>
												<td class="pif_part3_pop_inci_td">${vo.v_con_nm_en}</td>
											<c:if test="${beforeRawCd ne vo.v_raw_cd}">
												<td class="pif_part3_pop_inci_td" rowspan="${vo.n_raw_cnt }">${vo.v_raw_per}</td>
											</c:if>
												<td class="pif_part3_pop_inci_td">${vo.v_con_in_per }</td>
												<td class="pif_part3_pop_inci_td">${vo.v_actual_wt }</td>
											<c:if test="${beforeRawCd ne vo.v_raw_cd}">
												<td class="pif_part3_pop_inci_td" rowspan="${vo.n_raw_cnt }">${vo.v_comp_fc_nm_ch}</td>
												<td class="pif_part3_pop_inci_td" rowspan="${vo.n_raw_cnt }">${vo.v_comp_fc_nm}</td>
											</c:if>
												<td class="pif_part3_pop_inci_td">${vo.v_odm_casno}</td>
												<td class="pif_part3_pop_inci_td">${vo.v_comp_mixre}</td>
												<td class="pif_part3_pop_inci_td">${vo.v_remark}</td>
												<td class="pif_part3_pop_inci_td">${vo.v_zglobal}</td>
												<td class="pif_part3_pop_inci_td">${vo.v_zgllim}</td>
											</tr>
											<c:set var="beforeRawCd" value="${vo.v_raw_cd }"/>
											<c:set var="beforePartNo" value="${vo.n_part_no }"/>
										</c:if>
									</c:forEach>
											<tr>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td">
													<fmt:formatNumber value="${wt_total/1000}" type="number" var="wt_total" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>
													${wt_total}
												</td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td">
													<fmt:formatNumber value="${actual_wt_total/1000}" type="number" var="actual_wt_total" pattern="##.#######" maxFractionDigits="10" minFractionDigits="10"/>
													${actual_wt_total }
												</td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>												
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
												<td class="pif_part3_pop_inci_td"></td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
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
	<c:if test="${! empty allergenList}">
		<tr>
			<td colspan="4" style="text-align: left">
				<span style="text-align: left; font-size:0.7em;">(*) Additional Labeling Requirements in accordance with Article 19g of the Cosmetics reglement 1223/2009 (30th November of 2009) : </span><br/><br/>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="text-align: left">
				<span style="text-align: left; font-size:0.7em;"> * Allergens in fragrance : </span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table class="doc_sub_table" style="width:100%; margin: auto; border: 1px solid #000;">
					<colgroup>
						<col width="25%">
						<col width="25%">
						<col width="17.5%">
						<col width="15%">
						<col width="17.5%">
					</colgroup>
					<tbody>
					<tr>
						<td class="td_title txt_bold" style="background-color: #eeeeee;">INCI name</td>
						<td class="td_title txt_bold" style="background-color: #eeeeee;">W/W%</td>
						<td class="td_title txt_bold" style="background-color: #eeeeee;">Function</td>
						<td class="td_title txt_bold" style="background-color: #eeeeee;">CAS No.</td>
						<td class="td_title txt_bold" style="background-color: #eeeeee;">Chinese Name</td>
					</tr>
			<c:forEach items="${allergenList }" var="vo">
				<c:choose>
				<c:when test="${productInfo.v_part_leaveon_yn == 'Y'}">
					<tr style="background-color: ${vo.v_con_in_per > 0.001 ? 'rgb(152,247,145)' : '' }">
						<td class="pif_part3_pop_inci_td"><span>${vo.v_con_nm_en }</span></td>
						<td class="pif_part3_pop_inci_td"><span>${vo.v_con_in_per }</span></td>	
						<td class="pif_part3_pop_inci_td"><span>${vo.v_allergen_func }</span></td>
						<td class="pif_part3_pop_inci_td"><span>${vo.v_comp_cas_no }</span></td>
						<td class="pif_part3_pop_inci_td"><span>${vo.v_con_nm_cn }</span></td>	
					</tr>
				</c:when>
				<c:otherwise>
					<tr style="background-color: ${vo.v_con_in_per > 0.01 ? 'rgb(152,247,145)' : '' }">
						<td class="pif_part3_pop_inci_td"><span>${vo.v_con_nm_en }</span></td>
						<td class="pif_part3_pop_inci_td"><span>${vo.v_con_in_per }</span></td>	
						<td class="pif_part3_pop_inci_td"><span>${vo.v_allergen_func }</span></td>
						<td class="pif_part3_pop_inci_td"><span>${vo.v_comp_cas_no }</span></td>
						<td class="pif_part3_pop_inci_td"><span>${vo.v_con_nm_cn }</span></td>	
					</tr>
				</c:otherwise>
				</c:choose>
			</c:forEach>
					</tbody>
				</table>
			</td>
		</tr>
	</c:if>
		
		<tr>
			<td colspan="4">
				<br/><br/><br/>
			</td>
		</tr>
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
							<img src="${RootODMFullPATH }showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
						</c:otherwise>
					</c:choose>	            
				</div>
			</td>
		</tr>
	</tbody>
</table>	

<!-- </div> -->
