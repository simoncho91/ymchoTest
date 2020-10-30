<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<head>
<style>
<!--

.paotable {
	border-collapse: collapse;
	word-break: break-all;
	border-top: 1px solid black;
	border-left: 1px solid black;
	width: 680px;
}

.paotable th {
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 5px;
	text-align: center;
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	color: white;
	background: #000080;
}

.paotable td {
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 5px;
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	text-align: center;
	font-size: 15px;
}

.paotable td.td_sub_category {
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 5px;
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	text-align: left;
}

.stabilitytable {
	border-collapse: collapse;
	border-top: 1px solid black;
	border-left: 1px solid black;
	width: 700px;
}

.stabilitytable th {
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 5px;
	text-align: center;
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	color: white;
	background: #000080;
}

.stabilitytable td {
	padding-top: 5px;
	padding-left: 5px;
	padding-right: 5px;
	padding-bottom: 5px;
	border-bottom: 1px solid black;
	border-right: 1px solid black;
	text-align: center;
}
-->
</style>
</head>
<body>
<c:set var="v_test_date" value="" />
<c:if test="${!empty list }">
	<c:forEach items="${list}" var="vo" varStatus="status">
		<c:set var="v_test_date" value="${vo.v_test_date }" />
	</c:forEach>
</c:if>

<form id="frm" name="frm" method="get">
	<input type="hidden" name="i_sRecordId" value="${reqVo.i_sRecordId }">
	<input type="hidden" name="i_sProductCd" value="${reqVo.i_sProductCode }">
		
		<div style="width: 90%; margin-left: 5%;">
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
									<img src="${RootFullPATH}showImg.do?i_sAttachId=${compVo.v_logo_attachid}" width="203" height="74" >
								</c:otherwise>
							</c:choose>
				 				
						</td>
						<td colspan="2" style="text-align:right;font-size:0.9em;">
								<span>${rvo.v_company_addr1 }</span><br>
								<span>${rvo.v_company_addr2 }</span><br>
								<span>${rvo.v_company_addr3 }</span><br>
								<span>${rvo.v_vendor_nm}</span><br>
								<span>Tel : ${compVo.v_phone_no}</span><br>
								<span>Fax : ${compVo.v_fax}</span>
						</td>
					</tr>
				</tbody>
			</table>
			<table style="padding: 10px; margin: 10px;">
			<c:if test="${!empty list }">
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<img src="${ImgPATH}line2.png" width="715">
					</td>
				</tr>
				<tr>
					<td height="30" colspan="4" style="text-align: center">
						<font size="5" style="font-weight: bold;">
							Stability certificate
						</font>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<img src="${ImgPATH}line2.png" width="715">
					</td>
				</tr>
				<tr>
					<td>
						<br/><br/>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" colspan="4" style="text-align: left">
						<font size="2" style="font-weight: bold;">
							PRODUCT NAME : ${rvo.v_product_nm_en}
						</font>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" colspan="4" style="text-align: left">
						<font size="2" style="font-weight: bold;">
							Category : ${rvo.v_category1_nm } / ${rvo.v_category2_nm }
						</font>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" colspan="4" style="text-align: left">
						<font size="2" style="font-weight: bold;">
							PRODUCT CODE : ${rvo.v_product_cd}
						</font>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" colspan="4" style="text-align: left">
						<font size="2" style="font-weight: bold;">
							Test Date : ${cfn:getStrDateToString(prodInfo.v_test_date,'yyyy-MM-dd')}
						</font>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" colspan="4" style="text-align: left">
						<font size="2" style="font-weight: bold;">
							Samples had been stored at 40 C / 75% RH for over 3 months. It was confirmed all the results were specification.
						</font>
					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td>
						<br/>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="padding-left: 10px;">
						<c:forEach items="${sCondition}" var="vo" varStatus="status">
<%-- 						<c:if test="${vo.v_buffer3 ne 'hidden'}">	 --%>
							<table class="stabilitytable" style="text-align: center">
								<colgroup>
									<col width="20%">
									<col width="20%">
								<c:choose>
									<c:when test="${vo.BUFFER1 eq 'month'}">
										<col width="15%">
										<col width="15%">
										<col width="15%">
										<col width="15%">
									</c:when>
									<c:otherwise>
										<col width="12%">
										<col width="12%">
										<col width="12%">
										<col width="12%">
										<col width="12%">
									</c:otherwise>
								</c:choose>
								</colgroup>
								<thead>
									<tr>
										<td rowspan="2">Storage of condition</td>
										<td rowspan="2">Type of Analysis</td>
										<td colspan="5">Observation Time</td>
									</tr>
									<tr>
									<c:choose>
										<c:when test="${vo.BUFFER1 eq 'month'}">
											<td>0 month</td>
											<td>4 month</td>
											<td>5 month</td>
											<td>6 month</td>
										</c:when>
										<c:otherwise>
											<td>1 week</td>
											<td>2 week</td>
											<td>4 week</td>
											<td>8 week</td>
											<td>12 week</td>
										</c:otherwise>
									</c:choose>
									</tr>
								</thead>
								<tbody>								
								<c:set var="preCondiCd" value="" />
								<c:forEach items="${sAnalysis}" var="avo" varStatus="status">
									<c:if test="${avo.BUFFER1 eq vo.BUFFER1 }">
									<tr>
										<c:forEach items="${list}" var="tvo" varStatus="status">
										<c:if test="${tvo.v_anal_cd eq avo.COMM_CD && tvo.v_condi_cd eq vo.COMM_CD }">
											<c:if test="${tvo.v_condi_cd ne preCondiCd}">
												<c:set var="preCondiCd" value="${tvo.v_condi_cd }" />
												<td rowspan='${tvo.v_condi_row_cnt }'>
													${vo.COMM_CD_NM }
												</td>
											</c:if>
											<td>
												<input type="hidden" name="i_arrAnalysis" value="${avo.COMM_CD }">
												${avo.COMM_CD_NM }
											</td>
<%-- 											<c:if test="${tvo.v_analysis_cd eq avo.COMM_CD && tvo.v_condition_cd eq vo.COMM_CD }"> --%>
											
												<c:choose>
													<c:when test="${avo.BUFFER1 eq 'month' }">
														<td>${avo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_1w,'yyyy-MM-dd') : tvo.v_observa_1w}</td>
														<td>${avo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_2w,'yyyy-MM-dd') : tvo.v_observa_2w}</td>
														<td>${avo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_4w,'yyyy-MM-dd') : tvo.v_observa_4w}</td>
														<td class="noline">
															${avo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_8w,'yyyy-MM-dd') : tvo.v_observa_8w}
															${avo.BUFFER2 eq 'calendar'? cfn:getStrDateToString(tvo.v_observa_12w,'yyyy-MM-dd') : tvo.v_observa_12w}
														</td>
													</c:when>
													<c:otherwise>
														<td>${tvo.v_observa_1w }</td>
														<td>${tvo.v_observa_2w }</td>
														<td>${tvo.v_observa_4w }</td>
														<td>${tvo.v_observa_8w }</td>
														<td class="noline">
															<c:choose>
																<c:when test="${tvo.v_observa_12w == ''|| tvo.v_observa_12w == null}">
																	
																</c:when>
																<c:otherwise>
																	${tvo.v_observa_12w }
																</c:otherwise>
															</c:choose>
														</td>
													</c:otherwise>
												</c:choose>
											</c:if>
										</c:forEach>
									</tr>
									</c:if>
								</c:forEach>
								</tbody>
							</table>
<%-- 						</c:if> --%>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td colspan="4" style="text-align: right;">
<%-- 					<span style="line-height:1em;">${defaultApprInfo.v_issued_dt }</span><br/> --%>
<%-- 					<span style="line-height:1em;">${defaultApprInfo.v_usernm_en }</span><br/> --%>
<!-- 					<span style="line-height:1em;">AMOREPACIFIC CORPORATION</span><br/> -->
<!-- 					<span style="line-height:1em;">R&D Center</span><br/>  -->
<%-- 					<span style="line-height:1em;">${defaultApprInfo.v_deptnm }</span><br/> --%>
<%-- 					<span style="line-height:1em;">${defaultApprInfo.v_position }</span><br/> --%>
<%-- 					Signature: <img src="${defaultApprInfo.v_imgpath }" style="width:200px; height:60px;" onerror="fnNoImage(this);"/> --%>
				</td>
			</tr>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td>:: Stability document were not created ::</td>
			</tr>
			<tr>
				<td colspan="2"><br/></td>
				<td colspan="2" style="text-align: center; vertical-align: top; line-height: 17px; font-weight: bold; font-size: x-small;" width="354px;" height="196px;" background="${ImgPATH}footer_1.png">
						<br/>
						<br/>
						<br/>
						${reqVo.now_date}
						<br/>Global Regulatory Affairs & Development Team
						<br/>Manager.	Lim, Doo Hyeon
				</td>
			</tr>
		</c:if>
		</table>
		<table style="padding:20px; margin:20px; width:740px;">
			<colgroup>
				<col width="185px;">
				<col width="185px;">
				<col width="185px;">
				<col width="185px;">
			</colgroup>
			<tbody>	
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
			            
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
</body>