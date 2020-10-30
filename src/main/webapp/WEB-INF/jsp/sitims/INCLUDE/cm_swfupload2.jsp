<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>

<c:choose>
	<c:when test="${resMap.fileDiv eq 'IMG' }">
		<input type="hidden" name="${resMap.v_upload_cd }_attach_div" value="${resMap.fileDiv }"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_action_type"class="cls_thumbnail_action_type" value="R"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_id"         class="cls_thumbnail_id"        	value="${resMap.v_thumbnail_id}"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_path"       class="cls_thumbnail_path"      	value="${resMap.v_thumbnail_path}"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_full_path"  class="cls_thumbnail_full_path" 	value="${resMap.v_thumbnail_full_path}"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_ext"        class="cls_thumbnail_ext"       	value="${resMap.v_thumbnail_ext}"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_nm"         class="cls_thumbnail_nm"        	value="${resMap.v_thumbnail_nm}"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_size"       class="cls_thumbnail_size"      	value="${resMap.n_thumbnail_size}"/>
		<input type="hidden" size="100" name="${resMap.v_upload_cd}_thumbnail_width"      class="cls_thumbnail_width"     	value="${resMap.v_thumbnail_width}"/>
	</c:when>
	<c:otherwise>
		<input type="hidden" name="${resMap.v_upload_cd }_attach_div" value="${resMap.fileDiv }"/>
		<input type="hidden" size="100" name="${resMap.upload_id}_attach_id"    value="${resMap.attach_id}"/>
		<input type="hidden" size="100" name="${resMap.upload_id}_attach_path"  value="${resMap.attach_path}"/>
		<input type="hidden" size="100" name="${resMap.upload_id}_attach_path2"  value="${resMap.attach_path2}"/>
		<input type="hidden" size="100" name="${resMap.upload_id}_attach_ext"   value="${resMap.attach_ext}"/>
		<input type="hidden" size="100" name="${resMap.upload_id}_attach_nm"    value="${resMap.attach_nm}"/>
		<input type="hidden" size="100" name="${resMap.upload_id}_attach_size"  value="${resMap.attach_size}"/>
		<input type="hidden" size="100" id="i_sAttach_url" name="i_sAttach_url"  value="${resMap.webUploadUrl}"/>
		<input type="hidden" size="100" id="i_sAttch_filenm" name="i_sAttch_filenm"  value="${resMap.attach_filenm}"/>
		<input type="hidden" size="100" id="i_sAttch_ext" name="i_sAttch_ext"  value="${resMap.attach_ext}"/>
		<input type="hidden" size="100" id="i_sAttch_thumbfilenm" name="i_sAttch_thumbfilenm"  value="${resMap.attach_thumb}"/>
		<input type="hidden" size="100" id="i_attach_nm" name="i_attach_nm"  value="${resMap.attach_nm}"/>
	</c:otherwise>
</c:choose>