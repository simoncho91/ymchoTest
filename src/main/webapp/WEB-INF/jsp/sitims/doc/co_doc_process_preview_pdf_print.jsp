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


.doc_sub_table2{width: 100%;}
.doc_sub_table2 tbody tr th {padding:3px;text-align: center;font-weight: 500;font-size:8pt; font-weight: bolder;}
.doc_sub_table2 tbody tr td {padding:3px;text-align: center;font-weight: 500;font-size:8pt;}
.doc_sub_table2 .all_line {border: 1px solid #54575f;}



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
			<c:choose>
				<c:when test="${reqVo.i_sLogo_flag eq 'N' }">
					<img src="${ImgPATH}no_img.jpg" width="203" height="74" >
				</c:when>
				<c:otherwise>
					<img src="${RootFullPATH}showImg.do?i_sAttachId=${compVo.v_logo_attachid}" width="203" height="74" >
				</c:otherwise>
			</c:choose>
			</td>
			<td colspan="2" style="text-align:right;font-size:0.9em;">
				<span>${prodVo.v_company_addr1 }</span><br>
				<span>${prodVo.v_company_addr2 }</span><br>
				<span>${prodVo.v_company_addr3 }</span><br>
				<span>${prodVo.v_company_nm_en}</span><br
				<span>Tel : ${compVo.v_phone_no}</span><br>
				<span>Fax : ${compVo.v_fax}</span>
			</td>
		</tr>
		<tr>
			<td colspan="4"><img src="${ImgPATH}line2.png" width="740px;"></td>
		</tr>
		<tr>
			<td colspan="4"><p style="font-size:1.2em;text-align: center;font-weight: 600;">PRODUCTION FLOW CHART</p></td>
		</tr>
		<tr>
			<td colspan="4"><br/><br/></td>
		</tr>
		<tr>
			<td colspan="4" style="text-align: left"><span style="font-size:0.9em; font-weight:bold;">PRODUCT NAME : </span><span style="font-size:0.9em;">${prodVo.v_product_nm_en}</span></td>
		</tr>
		<tr>
			<td colspan="4" style="text-align: left"><span style="font-size:0.9em; font-weight:bold;">PRODUCT CODE : </span><span style="font-size:0.9em;">${prodVo.v_product_cd}</span></td>
		</tr>
		<tr>
			<td colspan="4"><br/></td>
		</tr>
		<tr>
			<td colspan="4"><span style="font-size:0.9em; font-weight:bold;">Brief Description</span></td>
		</tr>
		<tr>
			<td colspan="4"><img src="${ImgPATH}line2.png" width="100%"></td>
		</tr>
		<tr>
			<td><br/></td>
		</tr>
		<tr>
			<td colspan="4"><span style="font-size:0.9em;">${processVo.v_brief_desc}</span></td>
		</tr>
		<tr>
			<td><br/><br/></td>
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
											<col width="200">
											<col width="200">
											<col width="10">
											<col width="5">
											<col width="200">
											<col width="5">
											<col width="5">
											<col width="100">
											<col width="5">
										</colgroup>
										<tbody>
											<tr>
												<th class ="all_line">INCI NAME</th>
												<th class ="all_line">Chinese NAME</th>
												<th> </th>
												<th> </th>
												<th> </th>
												<th> </th>
												<th> </th>
												<th> </th>
												<th> </th>
											</tr>
									<c:forEach items="${folderList}" var="vo" varStatus="status">
											<tr>
												<th colspan="2" style="background-color: #FFFF66; border: 1px solid; text-align:center;" bordercolor="black;">${vo.v_part_nm}</th>
												<th></th>
												<th style="border-bottom : 1px solid;<c:if test="${!status.first}">border-top: 1px solid;</c:if>"></th>
												<th></th>
												<th></th>
												<th <c:if test="${!status.first}">style="border-left: 1px solid;"</c:if>></th>
												<th></th>
												<th></th>
												<th></th>
											</tr>
										<c:set var="cnt" value="0"/>
										<c:forEach items="${conList}" var="rvo" varStatus="vs">
											<c:if test="${vo.n_prc_part_seq eq rvo.n_prc_part_seq}">
											<c:set var="cnt" value="${cnt+1}"/>
											<tr class="tr_desc">
												<td class="td_comp" style="border: 1px solid; text-align:center; word-break:break-all" bordercolor="black;">${fn:replace(fn:replace(rvo.v_con_nm_en,'/','<br/>'),'*','<br/>')}</td>
												<td class="td_comp" style="border: 1px solid; text-align:center; word-break:break-all" bordercolor="black;">${fn:replace(fn:replace(rvo.v_con_nm_cn,'/','<br/>'),'*','<br/>')}</td>
												<td></td>
												<td  style="border-right: 1px solid; <c:if test="${vs.first}">border-top: 1px solid;</c:if><c:if test="${vs.last}">border-bottom: 1px solid;</c:if>" bordercolor="black;"></td>
												<td  style="<c:if test="${cnt eq 1}">border-top: 1px solid;</c:if>" bordercolor="black;" align="center">
												<c:if test="${cnt eq 1}">
													<table style="vertical-align: top;">
														<tr>
															<td class="td_desc" style="table-layout: fixed; vertical-align: top; word-break:break-all">${vo.v_part_desc}</td>
															<td></td>
														</tr>
													</table>
												</c:if>
												</td>
												<td style="<c:if test="${cnt eq 1}">border-top: 1px solid;border-right: 1px solid;</c:if><c:if test="${vs.index> 0 }">border-right: 1px solid;</c:if>" bordercolor="black;" align="center">
												</td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
											</c:if>
										</c:forEach>
									</c:forEach>		
											<tr>
												<td>&nbsp;</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td style = "border-right: 1px solid;"></td>
												<td></td>
												<td></td>
												<td></td>
											</tr>
										<c:if test="${processVo.v_degassing_ck eq 'Y' }">
											<tr>
												<td>&nbsp;</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td style = "border-right: 1px solid;"></td>
												<td></td>
												<td>Degassing</td>
												<td></td>
											</tr>
										</c:if>
										<c:if test="${processVo.v_cooling_ck eq 'Y' }">
											<tr>
												<td>&nbsp;</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td style = "border-right: 1px solid;"></td>
												<td></td>
												<td>Cooling (${processVo.v_cooling} ℃) </td>
												<td></td>
											</tr>
										</c:if>
										<c:if test="${processVo.v_filtration_ck eq 'Y' }">
											<tr>
												<td>&nbsp;</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td style = "border-right: 1px solid;"></td>
												<td></td>
												<td>Filtration</td>
												<td></td>
											</tr>
										</c:if>
										<c:if test="${processVo.v_storage_ck eq 'Y' }">
											<tr>
												<td>&nbsp;</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td style = "border-right: 1px solid;"></td>
												<td></td>
												<td>Storage</td>
												<td></td>
											</tr>
										</c:if>
											<tr>
												<td>&nbsp;</td>
												<td></td>
												<td></td>
												<td></td>
												<td></td>
												<td style = "border-right: 1px solid;"></td>
												<td></td>
												<td></td>
												<td></td>
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
						<img src="${RootFullPATH}showImg.do?i_sAttachId=${compVo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
					</c:otherwise>
				</c:choose>
<%-- 	            <img src="${RootODMFullPATH }showImg.do?i_sAttachId=${compVo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);"> --%>
			</div>
			</td>
		</tr>
	</tbody>
</table>