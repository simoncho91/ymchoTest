<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>

<script type="text/javascript">
	j$(function() {
		j$(".btn_ic_tab").unbind("click").click(function(event) {
			var id = j$(this).attr("id");
			var frm = j$("form[name='frm']");
			
			if(id == "REP") {
				var exp_div = j$("form[name='frm']").find("input[name='i_sExpDiv']").val();
				
				frm.attr("action", WebPATH + "br/pw/010/br_pw_010_t1_view.do").submit();
			}else if(id == "STAND") {
				frm.attr("action", WebPATH + "br/pw/010/br_pw_010_t2_view.do").submit();
			}else if(id == "FUNC") {
				frm.attr("action", WebPATH + "br/pw/010/br_pw_010_t3_view.do").submit();
			}else if(id == "REV") {
				
				frm.attr("action", WebPATH + "br/pw/010/br_pw_010_t4_view.do").submit();
			}	
		});
	});
</script>
		<ul class="sty_tab">
			<li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'REP' ? 'on' : '' }" id="REP"><span>기본정보</span></a></li>						
			<li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'STAND' ? 'on' : '' }" id="STAND"><span>표준서</span></a></li>			
			<c:if test="${reqVo.i_sFuncYn eq 'Y' }"><li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'FUNC' ? 'on' : '' }" id="FUNC"><span>기능성</span></a></li></c:if>			
			<li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'REV' ? 'on' : '' }" id="REV"><span>검토</span></a></li>
						
		</ul>		
