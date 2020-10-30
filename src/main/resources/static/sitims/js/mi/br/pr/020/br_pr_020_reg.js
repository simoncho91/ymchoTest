// 제품정보 팝업화면 컨트롤
var CmCdSearch = function(name,url,opt){
	this.idx = 0;
	this.objNm = name;
	this.url = url;
	this.inputCode = opt.inputCode;
	this.inputNameKo = opt.inputNameKo;
	this.inputNameEn = opt.inputNameEn;
	this.inputNameCn = opt.inputNameCn;
	this.searchInput = opt.searchInput;
	this.callback = opt.callback;
}
CmCdSearch.prototype ={
	getIndex : function(obj){
		var arrObj		= $('input[id='+obj.id+']');
		var len			= arrObj.length;
		
		var idx			= -1;			
		for (var i = 0; i < len; i++)
		{
			if (obj == arrObj[i])
			{
				this.idx = i;
				console.log(this.idx);
				return;
			}
		}
	}
	,productPop : function(obj){
		this.getIndex(obj);
		var resultUrl;
		if(this.objNm =='prodSearch'){
			resultUrl=this.url+'?i_sCmFunction='+encodeURI(this.objNm)+'.setPopUpData&i_sInput=';		
		}
		if(!fn_isNull(this.searchInput)) resultUrl = resultUrl+encodeURI($('input[id='+this.searchInput+']')[this.idx].value);
		fn_pop({url:resultUrl,title:'제품코드',width:'700',height:'500'});
	}
	,keyPress : function(e,obj){
	 	if(e.keyCode=='13'){
	 		this.productPop(obj);
	 	}
	}		
	,setPopUpData : function(data){
		//원화발송완료여부 체크 - 완료가 있을 시, 재문안등록 불가
		if(fn_oriCompleteCheck(data) != 0) {
			fn_s_alertMsg("해당 제품은 이미 문안 및 원화검토가 완료된 제품입니다.");
			return;
		}
		
		fn_popClose();
		var chkDuplKey=false;
		var _idx = this.idx;
		$('input[name='+this.inputCode+']').each(function(i,obj){			
			if(i != _idx && data.v_matnr == $(obj).val()){
				chkDuplKey = true;
				return;
			}
		});
		if(chkDuplKey){
			fn_s_alertMsg("중복된 제품코드입니다.");
			return;
		}
		//제품코드 데이터 셋팅
		if(!fn_isNull(this.inputId))		$('input[id='+this.inputId+']')[this.idx].value=data.v_matnr;
		if(!fn_isNull(this.inputCode))		$('input[id='+this.inputCode+']').val(data.v_product_cd);
		//if(!fn_isNull(this.inputNameKo))	$('input[id='+this.inputNameKo+']').val(data.v_product_nm_ko);
		
		
		console.log(data);
		//기타 데이터 셋팅
		$("#i_sProductNmText").html(data.v_product_nm_ko);
		$("#i_sProductNmMail").val(data.v_product_nm_ko);
		$("#i_sBrandCd").html(data.BRAND_NM);
		$("#i_sBottleType").html(data.CNTR_FORM);
		$("#i_sRecordCd").val(data.v_record_id);
		$("#i_sTou_use").html(data.v_shelf_life + '개월');	//제조일로부터 사용기한
		$("#i_sOpenTou_use").html(data.v_pao + '개월');		//개봉 후 사용기간
		$("#i_sVendorId").html(data.VENDOR_ID);
		$("#i_sPacketUnit").html(data.v_packet_unit);		//포장범위

		//소구데이터 셋팅
		var soguE = data.v_free_gn.indexOf("E") > -1 ? '무소구(' + data.v_musogu_cont + ')' : '';
		var soguD = data.v_free_gn.indexOf("D") > -1 ? '기타(' + data.v_sogu_cont + ')' : '';
		$("#i_sSogu").html(soguE + '<br>' + soguD);
		
		if("Y" == data.v_origin_yn){
			$("#i_sType").html("본품");
		}else{
			$("#i_sType").html(data.ORIGIN_DIV);
		}
		
		$("#i_sLaunch_dtm").html(data.v_release_dtm);
		
		try {
			if (typeof this.callback == "function")
			{
				this.callback(data,this);
			}
		} catch (e) {}
		 
	}
	,setPopUpGiData : function(data){
		fn_popClose();
		if(!fn_isNull(this.inputId))		$('input[id='+this.inputId+']')[this.idx].value=data.v_matnr;
		if(!fn_isNull(this.inputCode))	$('input[id='+this.inputCode+']')[this.idx].value=data.v_matnr;
		if(!fn_isNull(this.inputNameKo))	$('input[id='+this.inputNameKo+']')[this.idx].value=data.v_maktx;
		try {
			if (typeof this.callback == "function")
			{
				this.callback(data,this);
			}
		} catch (e) {}
		 
	}
	,delProduct : function(obj){
		this.getIndex(obj);
		if(!fn_isNull(this.inputId))		$('input[id='+this.inputId+']')[this.idx].value='';
		if(!fn_isNull(this.inputCode))		$('input[id='+this.inputCode+']')[this.idx].value='';
		//if(!fn_isNull(this.inputNameKo))	$('input[id='+this.inputNameKo+']')[this.idx].value='';
		
		$(".delY").val('');
		$(".delY").empty();
	} 
}


function setUserPopUpData(data){
	fn_popClose();
	$('#i_sUserid').val(data.v_user_no);
	$('#i_sUsernm').val(data.v_user_nm);	
	
}
 
function setOdmUserPopUpData(data){
	fn_popClose();
	console.log(data);
	 $('.span_companynm').text(data.v_vendor_nm);
	 $('#vendornm').text(data.v_vendor_nm);
	 $('#i_sCompanycd').val(data.v_vendor_id);
	 $('#i_sCompanynm').val(data.v_vendor_nm);
	 
	 $('#i_sCompanyLabor').val(data.v_user_id);
	 $('#i_sCompanyLaborNm').val(data.v_user_nm);

	 $('#i_sEmail').val(data.v_email);
	 $('#i_sLaborPhone').val(data.v_phoneno);
}
function setProductPopUpData(data){
	fn_popClose();
	 $('#i_arrProduct_Refcd').val(data.v_matnr);
	 $('#i_arrProduct_RefNm').val(data.v_maktx);
	 $('#i_arrProduct_RefNm_En').val(data.v_maktx_en);

}

//기능성 데이터 셋팅
function setFuncReportData(recordCd, productCd){
	$.ajax({
		url			:	"/br/pr/020/br_pr_020_get_report_ajax.do",
		data		:	{"i_sRecordCd" : recordCd, "i_sProductCd" : productCd},
		async 		: 	true,
        type  		:	"POST",
        dataType	:	"json",
		success		:	function(data, textStatus, jqXHR) {
			if(data.status == 'ERROR'){
				$(".nFuncNoError").show();
				return;
			}
			
			var data 		=	data.data;
			var funcMatList =	data.funcMatList;
			var reportNum	=	data.n_func_no;	//보고서번호(1,2,3)
			
			console.log(funcMatList);
			
			//기능성 여부 판단
			switch(reportNum){
			case "1" :
				$(".nFuncNo1").show();
				$(".nFuncNo2").hide();
				$(".nFuncNo3").hide();
				$(".nFuncNoError").hide();
				break;
			case "2" :
				$(".nFuncNo1").hide();
				$(".nFuncNo2").show();
				$(".nFuncNo3").hide();
				$(".nFuncNoError").hide();
				break;
			case "3" :
				$(".nFuncNo1").hide();
				$(".nFuncNo2").hide();
				$(".nFuncNo3").show(); 
				$(".nFuncNoError").hide();
				break;
			default : 
				$(".nFuncNo1").hide();
				$(".nFuncNo2").hide();
				$(".nFuncNo3").hide();
				$(".nFuncNoError").hide();
				break;
			}
			
			//텍스트 셋팅(공통데이터 + 보고서 1호 데이터)
			$(".i_sReportNumText").html(reportNum);										//제품번호
			$("#i_sProductNm").html(data.v_product_nm_ko);								//제품명
			$("#i_sCautionText").html(data.v_add_caution);								//주의사항
			$("#i_sPh").html(data.v_ph);												//ph
			$("#i_sLabNum").html(data.v_lab_no);										//랩넘버
			$(".i_sStandTest").html(data.STAND_TEST);									//고시한기준 및 시험방법
			$("#i_sEffect").html(data.EFFECT);											//1호 효능효과
			$("#i_sFuncType").html(data.FUNC_TYPE);										//유형별구분
			$("#i_sFuncIngri").html(data.FUNC_INGRI);									//성분별구분
			$("#i_sVendorNmText").html(data.v_vendor_nm);								//제조사
			$("#i_sAddrText").html(data.v_addr1 + "<br>" + data.v_addr2);				//소재지
			$("#i_sNationalText").html('대한민국');										//제조국
		
			//기능성보고서 기능성원료함량 리스트 s
			var content = ""; 
			for(var i=0; i<funcMatList.length; i++){
				content +=	"<tr>"
				content +=		"<td class='i_sOdmdbNmText'>" + funcMatList[i].v_odmdb_nm_ko + "</td>";
				content +=		"<td class='i_sWeightUnitText'>" + funcMatList[i].v_weight +  " / " + funcMatList[i].v_unit +"</td>";
				content +=	"</tr>"
			}
			$("#i_sFuncMatListTh").attr("rowspan",funcMatList.length+1);
			$('#i_sFuncMatList').after(content);
			//기능성보고서 기능성원료함량 리스트 e
			
			$(".i_sHowToMethod").html(data.v_howto_method);								//용량용법
			$("#i_sAllMatr").html(data.MATRMEMO_AL);									//전성분
			
			//벨류 셋팅
			$("#i_sCaution").val(data.PRD_CAUTION);										//주의사항(데이터)
			$("#i_sHowTo").val(data.v_howto_method);									//용법용량(데이터)
			$("#i_sVendorNm").val(data.v_vendor_nm);									//제조사
			$("#i_sSellerNm").val("(주)신세계인터네셔널");									//판매사
			$("#i_sVendorAddr").val(data.v_addr1 + "<br>" + data.v_addr2);				//소재지
			
			//보고서 1,2,3호 개별 데이터 셋팅
			if(reportNum == 1){
				$("#i_sFuncMat").html(data.FUNC_MAT1 + (isEmpty(data.FUNC_MAT1) ? "" : " / ") + data.FUNC_MAT2);			//세부구성
			} else if(reportNum == 2 || reportNum == 3) {
				$(".i_sBfProductNm").html(data.v_bf_product_nm);						//기심사제품명
				$(".i_sCertiNo").html(data.v_certi_no);									//심사번호
				$(".i_sCertDtm").html(data.v_cert_dtm);									//이미심사받은품목의 결과 통지일
				$(".i_sEffectMatNm").html(data.v_effect_mat_nm);						//활성물질용량
				$(".i_sWaterProof").html(data.WATER_PROOF);								//내수성지수
				$("#i_sFuncMat").html(data.v_func_mat_etc);								//세부구성
				//효능효과 데이터 가져오기                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
				setEffectData(recordCd, productCd);
			} 
		},
		error: function(jqXHR, textStatus, errorThrown) {
			fn_s_failMsg(jqXHR, textStatus, errorThrown);
	    	if(fail) fail();
	    },
	    complete: function(data) {
	    //if (bProgressOnEvent) layoutMain.progressOff();
	    }
	});
}

//보고서 2,3호용 효능효과 데이터 셋팅
function setEffectData(recordCd, productCd){
	$.ajax({
		url			:	"/br/pr/020/br_pr_020_get_effect_ajax.do",
		data		:	{"i_sRecordCd" : recordCd, "i_sProductCd" : productCd},
		async 		: 	true,
        type  		:	"POST",
        dataType	:	"json",
		success		:	function(data, textStatus, jqXHR) {
			var effectList	=	data.data;
			var str			=	"";
			
			//멀티체킹된 효능효과 셋팅
			for(let i=0; i<effectList.length; i++){
				str += effectList[i].EFFECT_NM;
				str += '<br>';
				
				//자외선 차단 효능이 있는경우 자외선 데이터 셋팅
				if(effectList[i].v_effect_cd == 'EFFECT09'){
					$(".i_sSpfLv").html(effectList[i].SPF_LV);
					$(".i_sPaLv").html(effectList[i].PA_LV);
				}
			}
			
			$(".i_sEffect").html(str);
		}
	});	
	
}

//디자이너 한명 셋팅
function setDesignerPopUpData(data){
	fn_popClose();
	$('#i_sDesignerId').val(data.v_login_id);
	$('#i_sDesignerNm').val(data.v_user_nm);	
}

//외주디자이너 한명 셋팅
function setOdmDesignerPopUpData(data){
	console.log(data);
	fn_popClose();
	$('#i_sOdmDesignerId').val(data.v_user_id);
	$('#i_sOdmDesignerNm').val(data.v_user_nm);	
	$('#i_sOdmDesignerVendorId').val(data.v_vendor_id);	
	
}

//패킹제작업체 한명 셋팅
function setPackingPopUpData(data){
	fn_popClose();
	$('#i_sPackingId').val(data.v_user_id);
	$('#i_sPackingNm').val(data.v_user_nm);	
}

//원화승인 체크
function fn_oriCompleteCheck(data){
	var completeCount;
	$.ajax({
		url			:	"/br/pr/020/br_pr_020_ori_complete_check_ajax.do",
		data		:	{"i_sRecordId" : data.v_record_id, "i_sProductCd" : data.v_product_cd},
		async 		: 	false,
        type  		:	"POST",
        dataType	:	"json",
		success		:	function(data, textStatus, jqXHR) {
			completeCount	=	data.data;	//원화발송 완료 제품 개수
		}
	});
	return completeCount;
}

//업로드관련
var OreqReg = {
	addEvent :{
		attachSuccEvent : function(attData, uploadCd){
			var attach_size = attData.v_attach_size;
			var attach_lnm = attData.v_attach_lnm;
			var attach_pnm = attData.v_attach_pnm;
			var attach_type = attData.v_attach_type;
			var attach_mgtid = attData.v_attach_mgtid;
			var attach_id = attData.v_attach_id;
			var attach_idx = attData.v_attach_idx;
			//var pk1 = j$("input[name='i_sAdReqId']").val();	//문안검토등록 제품코드는 하나만 등록되기 때문에 배열name이 아닌 단일 name 사용
			var table = j$(".table_" + uploadCd).eq(attach_idx);
			
			var obj = {
				v_attach_id : attach_id
				, v_attach_lnm : attach_lnm
				, v_attach_type : attach_type
				, n_attach_size : attach_size
				, v_attach_pnm : attach_pnm
				, v_attach_mgtid : attach_mgtid
				, v_upload_cd : uploadCd
				, v_pk1 : ""
				, v_pk2 : uploadCd
				, v_pk3 : ""
				, v_pk4 : ""
				, v_pk5 : ""
				, v_buffer1 : ""
				, v_buffer2 : ""
				, v_buffer3 : ""
				, v_buffer4 : ""
				, v_buffer5 : ""
				, del_img_url : ImgPATH + "btn/btn_del_small.gif"
			};
			var pagefn = doT.template(document.getElementById("dot_upload").text, undefined, undefined);
			j$(pagefn(obj)).appendTo(table.find("tbody"));
		}
	}
}