<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/sitims/INCLUDE/cm_server_common.jsp" %>

<script type="text/javascript">
	j$(function() {
		j$(".btn_ic_tab").unbind("click").click(function(event) {
			var id = j$(this).attr("id");
			var frm = j$("form[name='frm']");
			
			if(id == "REP") {
				var exp_div = j$("form[name='frm']").find("input[name='i_sExpDiv']").val();
				
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t1_view.do").submit();
			} else if(id == "EXP") {
				var groupid = "${reqVo.s_groupid}";

				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_fomula_view.do").submit();
			} else if(id == "FUNC") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t3_view.do").submit();
			}			
		});
		j$(".btn_step").unbind("click").click(function(event) {
			event.preventDefault();			
			var id = j$(this).attr("id");
			var frm = j$("form[name='frm']");			
			if(id == "tab1") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_fomula_view.do").submit();
			} else if(id == "tab2") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_process_view.do").submit();
			} else if(id == "tab3") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_component_view.do").submit();
			} else if(id == "tab4") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_claim_view.do").submit();
			} else if(id == "tab5") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_spec_view.do").submit();
			} else if(id == "tab6") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_msds_view.do").submit();
			} else if(id == "tab7") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_stability_view.do").submit();
			} else if(id == "tab8") {
				frm.attr("action", WebPATH + "br/pw/020/br_pw_020_t2_document_view.do").submit();
			}
		});
	});
</script>
		<ul class="sty_tab">
			<li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'REP' ? 'on' : '' }" id="REP"><span>기본정보</span></a></li>						
			<li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'EXP' ? 'on' : '' }" id="EXP"><span>Technical Information</span></a></li>
			<c:if test="${reqVo.i_sFuncYn eq 'Y' }"><li class="tab"><a href="#" class="btn_ic_tab ${reqVo.i_sBigTab eq 'FUNC' ? 'on' : '' }" id="FUNC"><span>기능성</span></a></li></c:if>						
		</ul>		
		<c:if test="${reqVo.i_sBigTab eq 'EXP'}">			
			<ul class="sty_tab">
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab1' ? 'on' : '' }" id="tab1"><span>Formula</span></a></li>
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab2' ? 'on' : '' }" id="tab2"><span>공정도</span></a></li>
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab3' ? 'on' : '' }" id="tab3"><span>전성분</span></a></li>
<%-- 				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab4' ? 'on' : '' }" id="tab4"><span>Claim Support</span></a></li> --%>
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab5' ? 'on' : '' }" id="tab5"><span>제품 Spec</span></a></li>
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab6' ? 'on' : '' }" id="tab6"><span>제품 MSDS</span></a></li>
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab7' ? 'on' : '' }" id="tab7"><span>안정성</span></a></li>
				<li class="tab"><a href="#" class="btn_step ${reqVo.i_sTab eq 'tab8' ? 'on' : '' }" id="tab8"><span>검토</span></a></li>
			</ul>
		</c:if>
