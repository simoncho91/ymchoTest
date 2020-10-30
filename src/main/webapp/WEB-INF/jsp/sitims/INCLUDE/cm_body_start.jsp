<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_html_start.jsp" %>

<body style="overflow:auto">
    <div id="wrapper">
        <div id="centerWrap">
            <div id="appAreaWrap">
                <div id="appRoleArea">
                    <div class="title_area_wrap">
                        <!-- div th:replace="fragments/roleFragment :: role_title"></div -->
                        <div class="role_title"><span id="title">${PAGE_NAVI}[${MENU_CD}]</span></div>
                        
                        <div th:replace="fragments/roleFragment :: role_btn_area"></div>
                    </div>
                </div>
                <div id="appArea" style="min-height: 0px;">
