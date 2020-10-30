var iRowCount;
jQuery(function() {
	jQuery(document).on("click", ".btnSearchItem", function(e) {
		e.preventDefault();
		var arrPars = [];
		var data;
		var parent = jQuery(this).parent().parent().parent().parent().parent().parent(); 
		title	= "시험항목 검색";
		
		arrPars.push("i_iIdx=" + jQuery(document).find(".btnSearchItem").index(this));
		arrPars.push("testNm=" + encodeURI(j$(this).parent().parent().find("input[name='i_sTestNm']").val()));        
		arrPars.push("i_sCallFunction=parent.setResult");

		cmDialogOpen("zm_std_testcode_pop", {
			title : title
	        , width : 700
	        , height: 600
	        , modal : true
	        , changeViewAutoSize : true 
	        , url:"./zm_std_testcode_pop.do?" + arrPars.join("&")
		});
	});
	
	jQuery(document).on("click", "input[name='i_sFlagMicrobeYN']", function(e) {
		if (jQuery(this).prop("checked")) {
			jQuery("input[name='i_sFlagMicrobe']").val("Y");
		} else {
			jQuery("input[name='i_sFlagMicrobe']").val("N");
		}
	});
	
	jQuery(document).on("click", ".btnDel", function(e) {
		e.preventDefault();
		jQuery(this).parent().parent().remove();
		jQuery(".rownum").each(function (index, item) {
			jQuery(item).html(index + 1);
		});
	});
	
	jQuery(document).on("keyup", "input[name='i_sTestNm']", function(e) {
		if (event.keyCode == 13) {
			e.preventDefault();
			jQuery(this).parent().parent().find(".btnSearchItem").click();
		}
	});
});

jQuery(document).ready(function() {
	iRowCount = jQuery("#addTargetABody").find("tr").length;
	
	jQuery(".btnAdd").click(function(e) {
		e.preventDefault();
		if (jQuery("#addTargetABody").find("tr").length != 0) {
			var cloneRow = jQuery("#addTargetABody").find("tr").eq(0).clone();
			jQuery(cloneRow).find("input").val("");
			jQuery(cloneRow).find("input[type='checkbox']").prop("checked", false);
			jQuery("#addTargetABody").append(cloneRow);
			jQuery(".rownum").each(function (index, item) {
				jQuery(item).html(index + 1);
			});
		}
	});
	
	jQuery(".btn_save").click(function(e) {
		e.preventDefault();
		var frm = document.frm;
//		jQuery("#frm").validate();	
		
		//alert(j$("#frm").serialize());
		frm.action = "/zm/std/zm_std_common_ap_save.do";
		frm.submit();
	});
});

function setResult(index, cd, nm, mcb) {
	jQuery(document).find("input[name='i_sTestNm']").eq(index).val(nm);
	jQuery(document).find("input[name='i_sTestCategory']").eq(index).val(cd);
	if (mcb == "Y") {
		jQuery(document).find("input[name='i_sFlagMicrobe']").eq(index).val("Y");
		jQuery(document).find("input[name='i_sFlagMicrobeYN']").eq(index).prop("checked", true);
	} else {
		jQuery(document).find("input[name='i_sFlagMicrobe']").eq(index).val("N");
		jQuery(document).find("input[name='i_sFlagMicrobeYN']").eq(index).prop("checked", false);
	}
}