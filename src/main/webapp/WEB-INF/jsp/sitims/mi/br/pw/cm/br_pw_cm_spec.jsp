<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
		
			<ul class="sty_tab">
			    <li class="tab li_flagImp">
			        <a href="#none" class="on" value="F"><span>완제품</span></a>
			    </li>
			    <li class="tab li_flagImp">
			        <a href="#none" class="" value="S"><span>반제품</span></a>
			    </li>
			</ul>
	    
	    <table class="sty_02" style="table-layout: fixed;">
	        <colgroup>
	            <col width="100px;" style="display:none;" >
	            <col width="200px;">
	            <col width="300px;">
	            <col width="300px;">
	        </colgroup>
	        <thead>
	        <tr>
	            <th class="ta_c" style="display:none;" >No.</th>
	            <th class="ta_c">Test item<br/>시험항목</th>
	            <th class="ta_c">Test standard<br/>시험기준</th>
	            <th class="ta_c">Test result<br/>시험결과</th>
	        </tr>
	        </thead>
	        <tbody>
			    <c:forEach items="${specList }" var="vo" varStatus="s">
			    	<c:set var="specIndex" value="0"/>
			    	<c:set var="specSIndex" value="0"/>
			    	<c:set var="specFIndex" value="0"/>
			    	<c:choose>
				    	<c:when test = "${!empty vo.v_spec_div_cd}">
					        <tr class="trSpec">					        		
					            <td class="ta_c" style="display:none;" >${vo.v_rownum }</td>
					            <td class="ta_c">
					            	${vo.v_spec_div_nm }
					            	<input type="hidden" name="i_arrSpecDivcd" value="${vo.v_spec_div_cd }"/>
					            </td>
					            <td class="ta_c"><span>${vo.v_specification }</span></td>
					            <td class="ta_c"><span>${vo.v_remark }</span></td>
					        </tr>
				        </c:when>
				    	<c:when test = "${empty vo.v_spec_div_cd and vo.v_flag_imp eq 'S'}">
					        <tr class="trSpecFlag_S" style="display:none;">	
					            <td class="ta_c" style="display:none;" >${vo.v_rownum }</td>
					            <td class="ta_c">
					            	${vo.v_spec_div_nm }
					            	<input type="hidden" name="i_arrSpecDivcd" value="${vo.v_spec_div_cd }"/>
					            </td>
					            <td class="ta_c"><span>${vo.v_specification }</span></td>
					            <td class="ta_c"><span>${vo.v_remark }</span></td>
					        </tr>
				        </c:when>
				    	<c:when test = "${empty vo.v_spec_div_cd and vo.v_flag_imp eq 'S'}">
					        <tr class="trSpecFlag_F">
					            <td class="ta_c" style="display:none;" >${vo.v_rownum }</td>
					            <td class="ta_c">
					            	${vo.v_spec_div_nm }
					            	<input type="hidden" name="i_arrSpecDivcd" value="${vo.v_spec_div_cd }"/>
					            </td>
					            <td class="ta_c"><span>${vo.v_specification }</span></td>
					            <td class="ta_c"><span>${vo.v_remark }</span></td>
					        </tr>
				        </c:when>
			        </c:choose>
			    </c:forEach>
	        </tbody>
	    </table>
	    
	    <div class="pd_top10"></div>
	        
<!-- 	    <table class="sty_02"> -->
<!-- 		    <colgroup> -->
<!-- 		        <col width="15%"/> -->
<!-- 		        <col width="85%"/> -->
<!-- 		    </colgroup> -->
<!-- 		    <tbody> -->
<!-- 		        <tr> -->
<!-- 		            <th>첨부파일</th> -->
<!-- 		            <td class="last"> -->
<%-- 		                <CmTagLib:cmAttachFile uploadCd="PSPEC" recordId="${reqVo.i_sRecordId }" pk1="${reqVo.i_sProductCd }" pk2="${reqVo.i_sPartNo }" formName="frm" type="viewLog"/>           --%>
<!-- 		            </td> -->
<!-- 		        </tr> -->
<!-- 		    </tbody> -->
<!-- 	    </table> -->