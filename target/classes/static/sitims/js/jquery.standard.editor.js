var standardEditor = {
	fnLoad : function(cd) {
		var arrPars	= [];
		var keyword =  j$("#i_sResourceNm").val();
		
		arrPars.push("i_sResourceCd=" + cd);
		arrPars.push("i_sKeyword=" + keyword);
		arrPars.push("i_sCallFunction=parent.setResult");
		
		cmDialogOpen("zm_std_standard_pop", {
	           title:"불러오기",
	           width:700,
	           height:600,
	           modal : true,
	           changeViewAutoSize : true, 
	           url:"./zm_std_standard_pop.do?" + arrPars.join("&")
		});
	}
};

