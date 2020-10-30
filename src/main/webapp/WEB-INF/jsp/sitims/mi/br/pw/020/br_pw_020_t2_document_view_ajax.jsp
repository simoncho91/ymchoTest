<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<c:forEach items="${rawcdList }" var="vo">
			<tr>
				<td>${vo.v_raw_cd }</td>
	<!-- SPEC -->
	<c:choose>
		<c:when test="${vo.v_type eq 'ODM'}">
				<td class="ta_c">
					<c:forEach items="${OdmAttachList }" var="svo">
						<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'SPE'}">
							<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');">
								<img alt="" src="${ImgPATH}common/icon_filedownload.gif">
							</a>
						</c:if>
					</c:forEach>
				</td>
		</c:when>
		<c:otherwise>
				<td></td>
		</c:otherwise>
	</c:choose>
	<!-- MSDS -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT03'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
	<!-- COA -->
	<c:choose>
		<c:when test="${vo.v_type eq 'ODM'}">
				<td class="ta_c">
			<c:if test="${!empty vo.v_coa_file }">
					<c:set var="arrCoaFile" value="${fn:split(vo.v_coa_file, '/') }"/>
				<c:forEach items="${arrCoaFile }" var="svo">
					<a href="javascript:attachDown('${svo }', 'ODM', 'frm')"><img src="${ImgPATH }common/icon_filedownload.gif"/> <font style="font-weight:bold;">(<fmt:message key="odm_inci.current_product"/>)</font></a>&nbsp;
				</c:forEach>
				<br/><br/>
			</c:if>
			<c:forEach items="${OdmAttachList }" var="svo">
				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'COA'}">
					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');">
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
						<img alt="" src="${ImgPATH}common/icon_filedownload.gif">						
					</a>
				</c:if>
			</c:forEach>
				</td>
		</c:when>
		<c:otherwise>
				<td></td>
		</c:otherwise>
	</c:choose>
	<!-- 색소 COA -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT06'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
	<!-- 색소 SPEC -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'SPE'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
	<!-- CCPP -->
	<c:choose>
		<c:when test="${vo.v_type eq 'ODM'}">
				<td class="ta_c">
			<c:forEach items="${OdmAttachList }" var="svo">
				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'CCP'}">
					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');">
						<img alt="" src="${ImgPATH}common/icon_filedownload.gif">
					</a>
				</c:if>
			</c:forEach>
				</td>
		</c:when>
		<c:otherwise>
				<td></td>
		</c:otherwise>
	</c:choose>
	<!-- Certi -->
	<c:choose>
		<c:when test="${vo.v_type eq 'ODM'}">
				<td class="ta_c">
			<c:forEach items="${OdmAttachList }" var="svo">
				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'CER'}">
					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');">
						<img alt="" src="${ImgPATH}common/icon_filedownload.gif">
					</a>
				</c:if>
			</c:forEach>
				</td>
		</c:when>
		<c:otherwise>
				<td></td>
		</c:otherwise>
	</c:choose>
	<!-- 전초 추출물 사용부위확인서 -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT04'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 추출물 원료 유래 확인서 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT13'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 잔류용매보고서 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT14'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 유기용매 분석 보고서(잔류 헥산 보고서) --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT05'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 자차 주성분 usp적합 확인서 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT15'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 잔류농약보고서/성적서 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT10'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 유기농 인증서 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT16'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- API 등록 업체 확인서 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT11'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- regulatory product information --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT12'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 원료 안전성 자료 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT17'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
<!-- 	<!-- 효능자료 --> -->
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${vo.v_type eq 'ODM'}"> --%>
<!-- 				<td> -->
<%-- 			<c:forEach items="${OdmAttachList }" var="svo"> --%>
<%-- 				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ATT18'}"> --%>
<%-- 					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');"> --%>
<%-- 						<img alt="" src="${ImgPATH}icon_filedownload.gif"> --%>
<!-- 					</a> -->
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 				</td> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<!-- 				<td></td> -->
<%-- 		</c:otherwise> --%>
<%-- 	</c:choose> --%>
	<!-- 알러젠 확인서 -->
	<c:choose>
		<c:when test="${vo.v_type eq 'ODM'}">
				<td class="last ta_c">
			<c:forEach items="${OdmAttachList }" var="svo">
				<c:if test="${svo.v_record_id eq vo.v_odmdb_id and svo.v_upload_id eq 'ARG'}">
					<a href="#none" onclick="javascript:attachDown('${svo.v_attach_id}', 'ODM', 'frm');">
						<img alt="" src="${ImgPATH}common/icon_filedownload.gif">
					</a>
				</c:if>
			</c:forEach>
				</td>
		</c:when>
		<c:otherwise>
				<td  class="last"></td>
		</c:otherwise>
	</c:choose>
			</tr>
</c:forEach>