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

.doc_sub_table2{border: 1px solid #54575f;width: 100%;text-align: center;}
.doc_sub_table2 thead tr th{border-right: 1px solid #54575f;padding:3px;text-align: center;font-weight: 500;font-size:8pt;}
.doc_sub_table2 thead tr th.last{border-right: 0px solid #54575f;}
.doc_sub_table2 tbody tr td{border-right:1px solid #54575f;border-top:1px solid #54575f;padding:3px;font-weight: 500;font-size:8pt;}
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
<%--  				<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_logo_attachid}" width="203" height="74" > --%>
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
				<span>Tel : ${companyInfo.v_phone_no }</span><br>
				<span>Fax : ${companyInfo.v_fax }</span>
			</td>
		</tr>
		<tr>
		<tr>
			<td colspan="4"><img src="${ImgPATH}line2.png" width="740px;"></td>
		</tr>
		<tr>
			<td colspan="4"><p style="font-size:1.2em;text-align: center;font-weight: 600;">Product Safety Data Sheet</p></td>
		</tr>
			<tr>
			<td colspan="4"><img src="${ImgPATH}line2.png" width="740px;"></td>
		</tr>
		<tr>
			<td colspan="4">
				<br/><br/><br/><br/>
			</td>
		</tr>
<c:forEach items="${sectionList}" var="vo">
	<c:choose>
		<c:when test="${vo.v_type eq 'R'}">
			<tr>
				<td colspan="4" style="font-size: 17px;font-weight: bold; line-height: 1.5;">${vo.v_class_nm }</td>
			</tr>
			<c:forEach items="${sectionList_sub }" var="svo">
				<c:if test="${vo.v_class_cd eq svo.v_uclass_cd }">
					<tr>
						<td colspan="4" style="font-size:13px; font-weight: bold; line-height: 1.5;">${svo.v_class_nm }</td>
					</tr>
					<tr>
						<td colspan="4" style="font-size:13px; line-height: 1.5;">${cfn:removeHTMLChangeBr(svo.v_msds_val)}</td>
					</tr>
					<tr>
						<td colspan="4"><br/></td>
					</tr>
				</c:if>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="4" style="font-weight: bold; font-size:13px; line-height: 1.5;">${vo.v_class_nm }</td>
			</tr>
			<tr>
				<td colspan="4" style="font-size:13px; line-height: 1.5;">
					${cfn:removeHTMLChangeBr(vo.v_msds_val)}
					<c:if test="${vo.v_class_cd eq 'S000047'}"><!-- SECTION 3 -->
						<p>Propellant gas : ${rvo.v_msds_val }</p><br/>
					</c:if>
					<c:if test="${vo.v_class_cd eq 'S000048'}"><!-- SECTION 9 -->
						<p>Flash point : ${rvo.v_msds_val }</p>
					</c:if>
					<c:if test="${vo.v_class_cd eq 'S000045' and !empty vo.v_reg_dtm}"><!-- SECTION 16 -->
						<p>- issuing date : ${cfn:getStrDateToString(vo.v_reg_dtm, 'yyyy-MM-dd')}</p>
						<p>- revised date : ${cfn:getStrDateToString(vo.v_update_dtm, 'yyyy-MM-dd')}</p>
					</c:if>
				</td>
			</tr>
			<c:if test="${vo.v_class_cd eq 'S000047' }">
				<tr>
					<td colspan="4">
						<table class="doc_table">
							<tbody>
								<tr>
									<td>
										<div class="div_tb_sub">
											<table class="doc_sub_table2">
												<colgroup>
													<col width="40%">
													<col width="20%">
													<col width="30%">
													<col width="10%">
												</colgroup>
												<tbody>
													<tr>
														<td class="td_title_center txt_bold">Ingredient name</th>
														<td class="td_title_center txt_bold">CAS NUMBER</th>
														<td class="td_title_center txt_bold">Composition</th>
														<td class="td_title_center txt_bold last">Part no</th>
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
			</c:if>
		<tr>
			<td colspan="4"><br/></td>
		</tr>
		</c:otherwise>
	</c:choose>
</c:forEach>
		<tr>
			<td colspan="2"></td>
			<td colspan="2" style="font-size: 13px; text-align: right;">
				<div>
	            	<span style="margin-right: 64px;">${reqVo.v_now_date }</span><br/>
		            Signature :
					<c:choose>
						<c:when test="${reqVo.i_sSign_flag eq 'N' }">
							<img src="${ImgPATH}no_img.jpg" width="120" height="60" >
						</c:when>
						<c:otherwise>
							<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);">
						</c:otherwise>
					</c:choose>
<%-- 	            	<img src="${RootFullPATH}showImg.do?i_sAttachId=${companyInfo.v_sign_attachid}" width="120" height="60"  onerror="fnNoImage(this);"> --%>
				</div>
            </td>
		</tr>
	</tbody>
</table>