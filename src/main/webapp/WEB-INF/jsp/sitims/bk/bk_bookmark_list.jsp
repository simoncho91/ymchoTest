<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_html_start.jsp" %>
<!-- css -->
<link rel="stylesheet" type="text/css" href="/sitims/CSS/common.css">
<!--[if lt IE 9]>
<script type="text/javascript" src="/sitims/js/lib/html5.js"></script>
<![endif]-->
<script th:src="@{/js/includeDhtmlx6.js?20200402}"></script>
<script type="text/javascript">
/* $(function(){
	$('.home').click(function(){
		
	});
})
*/
function editBookMark(i_sMenuId){
	var chkBool = $('#'+i_sMenuId).hasClass('is_check');
	var i_sSaveMode = new Object();
 	if(chkBool){
		i_sSaveMode = 'N';
	}else{
		i_sSaveMode = 'Y';
	} 
 	var i_iSeqNo = $('#'+i_sMenuId).data("seq");
 	
 	var postParam = {
 			i_sMenuId : i_sMenuId,
 			i_sSaveMode : i_sSaveMode,
 			i_iSeqNo : i_iSeqNo
 	}
 	
 	$.ajax({
		url : "/tiims/bookMarkEdit.do",
		Type : "POST",
		data : postParam,
		dataType : "JSON",
		success : function(result){
			fn_s_alertMsg(result.message);
			if(result.data == "ins"){
				$('#'+i_sMenuId).attr('class','btn_check_fav is_check');
			}else{
				$('#'+i_sMenuId).attr('class','btn_check_fav');
			}
		},
		error : function(){
			fn_s_alertMsg("DB 저장 중 오류 발생");
		}
	}) 
}
function fn_goHome() {
	location.href	= '/tiims/main/init.do';
} 
</script>

<body>
<div class="container">
      <div>
      	  <h2 class="sec_tit"  style="float: left;">
	      	<i class="icon_star"></i>Favorites
	      </h2>
      <ul class="btn_area">
      	<li>
      	</li>
	  	<li class="right">
		   <a href="#none" class="btnA bg_dark home" onclick="fn_goHome()"><span>HOME</span></a>
	 	</li>
	  </ul>
	  </div>
      <section class="sec_fav">
        <div class="inner_fav clearfix">
         <c:forEach items="${bookMarkTopList}" var="vo">
	          <dl>
		         <dt>${vo.MENU_NM}</dt>
		          <c:forEach items="${bookMarkList}" var="lo">
		          	<c:if test ="${vo.MENU_CD eq lo.TOP_ID}">
		            <dd>
					<ul class="menulist">
						<li>
							<div class="in">
								<span class="thumb"><img
									src="${lo.IMG_URL}" alt=""></span>
								<p>${lo.MENU_NM}</p>
								<c:set var="loop_flag" value="false" />
								<c:forEach items="${userBookmarkList}" var="userVo" varStatus="index">
										<c:if test="${userVo.v_menu_id eq lo.MENU_CD}">
											<button id="${lo.MENU_CD}" type="button"
												onclick="editBookMark('${lo.MENU_CD}')"
												class="btn_check_fav is_check"
												data-seq="${vo.SORT_SEQ}"></button>
											<c:set var="loop_flag" value="true" />
										</c:if>
								</c:forEach>
								<c:if test="${not loop_flag}">
								<button id="${lo.MENU_CD}" type="button"
									onclick="editBookMark('${lo.MENU_CD}')"
									class="btn_check_fav"
									data-seq="${vo.SORT_SEQ}"></button>
								</c:if>
							</div>
						</li>
					</ul>
				</dd>
		
		            </c:if>
		           </c:forEach>
	          </dl>
         </c:forEach>
        </div>
      </section>
    </div><!-- //container -->


</body>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_body_end.jsp" %>
