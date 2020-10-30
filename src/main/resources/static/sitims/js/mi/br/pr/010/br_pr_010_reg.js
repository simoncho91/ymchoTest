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
	//	var arrObj		= $('input[id='+obj.id+']');
		var arrObj		= $('[id='+obj.id+']');
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
		console.log(obj);
		var resultUrl;
		if(this.objNm =='prodSearch'){
			resultUrl=this.url+'?i_sCmFunction='+encodeURI(this.objNm)+'.setPopUpData&i_sInput=';			
		}else{
			resultUrl=this.url+'?i_sCmFunction='+encodeURI(this.objNm)+'.setPopUpGiData&i_sInput=';
		}
		if(!fn_isNull(this.searchInput)) resultUrl = resultUrl+encodeURI($('input[id='+this.searchInput+']')[this.idx].value);
		fn_pop({url:resultUrl,title:'제품코드',width:'820',height:'500'});
	}
	,keyPress : function(e,obj){
	 	if(e.keyCode=='13'){
	 		this.productPop(obj);
	 	}
	}		
	,setPopUpData : function(data){
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
		if(!fn_isNull(this.inputId))		$('input[id='+this.inputId+']')[this.idx].value=data.v_matnr;
		if(!fn_isNull(this.inputCode))	$('input[id='+this.inputCode+']')[this.idx].value=data.v_matnr;
		if(!fn_isNull(this.inputNameKo))	$('input[id='+this.inputNameKo+']')[this.idx].value=data.v_maktx;
		if(!fn_isNull(this.inputNameEn))	$('input[id='+this.inputNameEn+']')[this.idx].value=data.v_maktx_en;
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
		if(!fn_isNull(this.inputNameEn))	$('input[id='+this.inputNameEn+']')[this.idx].value=data.v_maktx_en;
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
		if(!fn_isNull(this.inputNameKo))	$('input[id='+this.inputNameKo+']')[this.idx].value='';
		if(!fn_isNull(this.inputNameEn))	$('input[id='+this.inputNameEn+']')[this.idx].value='';
		if(!fn_isNull(this.inputNameCn))	$('input[id='+this.inputNameCn+']')[this.idx].value='';
		if(this.objNm=='prodGiSearch'){
			OreqReg.chkBool[this.idx]=true;
			//$('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').closest('label').parent().addClass('chk-style');
			//$('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').attr('onclick','');
			$('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').prop('checked',false);
			$('.tbody_product_info').eq(this.idx).find('input[name^="i_arrExp"]').prop('disabled',false);
			divExpCountryShowHide2(this.idx,false);
		}
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

var OreqReg = {
	chkBool : {}
	,addEvent :{
		attachSuccEvent : function(attData, uploadCd){
			var attach_size = attData.v_attach_size;
			var attach_lnm = attData.v_attach_lnm;
			var attach_pnm = attData.v_attach_pnm;
			var attach_type = attData.v_attach_type;
			var attach_mgtid = attData.v_attach_mgtid;
			var attach_id = attData.v_attach_id;
			var attach_idx = attData.v_attach_idx;
//			var pk1 = j$("input[name='i_arrProduct_Refcd']").eq(attach_idx).val();
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
				, v_buffer1 : j$("input[name='i_arrProduct_Refcd']").eq(attach_idx).val()
				, v_buffer2 : ""
				, v_buffer3 : ""
				, v_buffer4 : ""
				, v_buffer5 : ""
				, del_img_url : ImgPATH + "btn/btn_del_small.gif"
			};
			var pagefn = doT.template(document.getElementById("dot_upload").text, undefined, undefined);
			j$(pagefn(obj)).appendTo(table.find("tbody"));
		}
		,divSetRadioShowHide : function(index,val){
			//console.log("divSetRadioShowHide");
			var obj = $('.div_set_radio')[index];
			var div		= $(".div_gi_ref")[index];
			if(val=='Y'){
				$(obj).hide();
				$(div).hide();
			}else{
				$(obj).show();		
				$(div).show();		
			}		
		}
		,divSetRadioNShowHide : function(index){
			console.log("divSetRadioNShowHide");
			var _div = j$(".div_set_radio").eq(index); 
			var ckVal	= _div.find("input[type='radio']:checked").val();
			var _div_setcode = _div.find(".div_set_inp");
			
			// OOD004 : 세트구성품
			if(ckVal == "OOD004"){
				_div_setcode.show();
			}
			else{
				_div_setcode.hide();
			}			
		}
		,radioFunc : function(index){	
			var ckVal = j$(".div_func_yn").eq(index).find("input[type='radio']:checked").val();
			var div = j$(".div_func_ck").eq(index);		
			var dot_func_ck = function(index){			
				var vo = { index : index };			
				var ckHtml = [];			
				ckHtml.push(doTjs_Injection("dot_func_ck", vo));			
				return ckHtml;
			}
			if(ckVal == "Y"){
				div.html(dot_func_ck(index));
				inputChkRadioAddEvent();
				div.show();
			}
			else{
				div.html("");
				div.hide();
			}			
		}
		,divExpCountryShowHide : function(index){
			if(typeof OreqReg.chkBool[index] =='boolean' && !OreqReg.chkBool[index]) return;
			var _div = j$(".div_exp_country").eq(index); 
			var ckVal	= _div.find("input[type='checkbox']:checked").val();
			var _div_prod_cn_nm = j$("input[id=i_arrProduct_RefNm_Cn]").eq(index);
			
			// OOD004 : 세트구성품
			if(ckVal == "CN"){
				_div_prod_cn_nm.parent().parent().show(); //제품명 중국 tr show
				//중문추가로인한 제품코드 컬럼 rowspan 조정
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('th').eq(0).attr('rowspan','2');
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('td').eq(0).attr('rowspan','2');
			}
			else{
				_div_prod_cn_nm.parent().parent().hide(); //제품명 중국 tr hide
				_div_prod_cn_nm.val("");
				//중문숨김으로인한 제품코드 컬럼 rowspan 조정
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('th').eq(0).attr('rowspan','1');
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('td').eq(0).attr('rowspan','1');
			}
		}
		,divExpCountryShowHide2 : function(index,chkBoolean){
			if(typeof OreqReg.chkBool[index] =='boolean' && !OreqReg.chkBool[index]) return;
			var _div = j$(".div_exp_country").eq(index); 
			var ckVal	= _div.find("input[type='checkbox']:checked").val();
			var _div_prod_cn_nm = j$("input[id=i_arrProduct_RefNm_Cn]").eq(index);
			
			// OOD004 : 세트구성품
			if(chkBoolean){
				_div_prod_cn_nm.parent().parent().show(); //제품명 중국 tr show
				//중문추가로인한 제품코드 컬럼 rowspan 조정
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('th').eq(0).attr('rowspan','2');
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('td').eq(0).attr('rowspan','2');
			}
			else{
				_div_prod_cn_nm.parent().parent().hide(); //제품명 중국 tr hide
				_div_prod_cn_nm.val("");
				//중문숨김으로인한 제품코드 컬럼 rowspan 조정
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('th').eq(0).attr('rowspan','1');
				$('input[id=i_arrProduct_Refcd]').eq(index).parent().parent().find('td').eq(0).attr('rowspan','1');
			}
		}
		,setCategory : function(){
			var select_cate = j$("select[name='i_arrType']");
			var select_cate1 = j$("select[name='i_arrCategory1']");
			var select_cate2 = j$("select[name='i_arrCategory2']");
			var select_cate3 = j$("select[name='i_arrCategory2']").next();
			var cate_len = select_cate.size();
			
			if(cate_len == 0){
				return;
			}
			
			for(var i = 0 ; i < cate_len ; i ++){
				var classcd = select_cate.eq(i).val();
				var cate1 = select_cate1.eq(i);
				var cate2 = select_cate3 .eq(i);
				if(classcd == "LG" || classcd == "DR" || classcd == "LC") {
					cate2.prev().css("display","none");
				}else if(cate1.val() != ""){
					OreqReg.addEvent.setCategory2List(cate1, cate2.val());
				}
			}
		}
		,setCategory1List : function(obj,cate1_val){
			var classcd = obj.val();
			var category1 = obj.closest('tbody').find('select[name="i_arrCategory1"]');
			var category2 = obj.closest('tbody').find('select[name="i_arrCategory2"]');
			if(classcd == "") {
				category2.css("display", "inline");
				var arrHtml = [];
				arrHtml.push("<option value=''>-- SELECT --</option>");
				category1.html("");
				category1.html(arrHtml.join(""));
				
				category2.html("");
				category2.html(arrHtml.join(""));
				return;
			} else if(classcd == "LG" || classcd == "DR" || classcd == "LC") {
				category2.css("display","none");
			} else {
				category2.css("display", "inline");
				var arrHtml = [];
				arrHtml.push("<option value=''>-- SELECT --</option>");
				category2.html("");
				category2.html(arrHtml.join(""));
			}
			
			fn_ajax({
				url:'/si/cm/020/selectSubList.do'
				,postParam:{ 'i_sClassCd' : 'S000001', 'i_sTopCd' : classcd  }
				,success: function(responseData){
					if(responseData.msgCode == "success") {					
						var list = responseData.result.data;
						var len = list.length;
						if(len == 0 ){return;}
						
						var arrHtml = [];
						arrHtml.push("<option value=''>-- SELECT --</option>");
						for(var i = 0 ; i < len ; i ++){
							var vo  = list[i];
							if(vo.v_class_cd !='S000040'){
								arrHtml.push("<option value='"+vo.v_class_cd+"' data-flag-pao='"+vo.v_pao_yn+"' data-pao='"+vo.n_pao +"' data-life='"+vo.n_life +"'>"+vo.v_class_nm+"</option>");								
							}
						}
						var category1 = obj.closest('tbody').find('select[name="i_arrCategory1"]');
						category1.html("");
						category1.html(arrHtml.join(""));
						
						if(cate1_val != undefined || cate1_val != ""){
							category1.val(cate1_val);
						}
					}
			}
			});
		}
		,setCategory2List : function(obj,cate2_val){
			var category1 = obj.val();
			
			var classcd = obj.closest('tbody').find('select[name="i_arrType"]').val();
			var category2 = obj.closest('tbody').find('select[name="i_arrCategory2"]');
			
			if(classcd == "LG" || classcd == "DR" || classcd == "LC" || category1 == "S000415") {
				category2.css("display","none");
				return;
			}  else {
				category2.css("display","inline");
			}
			 if(category1 == "") return;
			 fn_ajax({
				 url:'/si/cm/020/selectSubList.do'
				 ,postParam:{ 'i_sClassCd' : category1 }
				 ,success: function(responseData){
					 if(responseData.msgCode == "success") {					
						var list = responseData.result.data;
						var len = list.length;
						if(len == 0 ){
							return;
						}
						var arrHtml = [];
						arrHtml.push("<option value=''>-- SELECT --</option>");
						for(var i = 0 ; i < len ; i ++){
							var vo  = list[i];
							if(vo.v_class_cd !='S000040'){
								arrHtml.push("<option value='"+vo.v_class_cd+"' data-flag-pao='"+vo.v_pao_yn+"' data-pao='"+vo.n_pao +"' data-life='"+vo.n_life +"'>"+vo.v_class_nm+"</option>");								
							}
						}
						
						var category2 = obj.next();
						category2.html(arrHtml.join(""));
						
						if(cate2_val != undefined || cate2_val != ""){
							category2.val(cate2_val);
						}
					}
				 }
			 });
		 }
		
		,addProduct : function(index){
			
			//반제품시 담당자 ap 인지 아닌지 체크
			var displayYn = '';
			if(j$("input[name='i_sCompanyDist']:checked").val() == 'B'){
				displayYn = 'block';
			}else{
				displayYn = 'none';
			}
			var prodHtml = [];
			
			var vo = {index : index, ImgPATH : ImgPATH, displayYn : displayYn};
			
			prodHtml.push(doTjs_Injection("dot_product_info", vo));
			
			var productBox = j$(prodHtml.join(""));
			
			j$("#table_product_view").append(productBox);

			OreqReg.addEvent.addProductinfo(productBox);
			//OreqReg.fn.setProductDefaultValue(productBox);
			//OreqReg.fn.setOriginImgUnity();
			jfupload.initUpload({
				target : j$("#admst_upload_btn_" + index)
				, uploadCd : "PON"
				, index : index
				, formName : "frm"
				, success : OreqReg.addEvent.attachSuccEvent
				, isSelfMakeTag : true
				, attachDir : "PON"
				, buffer1 : j$("input[name='i_arrProduct_Refcd']").eq(index).val()
			});
			
		}
		,addProductinfo : function(wrap){

			wrap.find(".btn_product_del").click(function(event){
				event.preventDefault();
				j$(this).parents(".tbody_product_info").eq(0).remove();
			});

			// 본품여부 체크 이벤트
			wrap.find(".div_origin_yn").find("input[type='radio']").click(function(){
				var index = j$(".div_origin_yn").index(j$(this).parents(".div_origin_yn").eq(0));
				//var val = j$('.div_origin_yn input[type=radio]:checked')[index].value;
				var val = j$(this).parents(".div_origin_yn").find('input[type=radio]:checked').val();
				OreqReg.addEvent.divSetRadioShowHide(index,val);
			});
			
			// 본품여부 N 라디오 이벤트
			wrap.find(".div_set_radio").find("input[type='radio']").click(function(){
				var index = j$(".div_set_radio").index(j$(this).parents(".div_set_radio").eq(0));
				OreqReg.addEvent.divSetRadioNShowHide(index);
			});

			// 수출국가
			wrap.find(".div_exp_country").find("input[type='checkbox']").click(function(){
				var index = j$(".div_exp_country").index(j$(this).parents(".div_exp_country").eq(0));
				OreqReg.addEvent.divExpCountryShowHide(index);
			});
			// 기능성 라디오 체크 이벤트
			wrap.find(".div_func_yn").find("input[type='radio']").click(function(){
				var index = j$(".div_func_yn").index(j$(this).parents(".div_func_yn").eq(0));
				OreqReg.addEvent.radioFunc(index);
			});
			
			wrap.find("select[name='i_arrType']").change(function(){
				OreqReg.addEvent.setCategory1List($(this));
			});
			
			wrap.find("select[name='i_arrCategory1']").change(function(){
				OreqReg.addEvent.setCategory2List($(this));
			});
			
			wrap.find("select[name='i_arrCategory2']").change(function() {				
				var flag_pao = $(this).find("option:selected").attr("data-flag-pao");
				var pao = $(this).find("option:selected").attr("data-pao");
				var life = $(this).find("option:selected").attr("data-life");
				var idx = $("select[name='i_arrCategory2']").index($(this));
				$(this).parents("td:eq(0)").find("input[name='i_arrPao']").val(pao);
				$(this).parents("td:eq(0)").find("input[name='i_arrLife']").val(life);
			});
			
			wrap.find("select[name='i_arrCntrForm']").change(function(){
				var div_idx = j$("select[name='i_arrCntrForm']").index(j$(this));
				console.log(div_idx);
				if(j$(this).val() == 'OCT_1_21'){
					j$(".CntrForm_etc_div").eq(div_idx).show();
				}else{
					j$(".CntrForm_etc_div").eq(div_idx).hide();
					j$(".CntrForm_etc_div").find("input[name='i_arrCntrForm_etc']").val("");
				}
			});
			
			wrap.find("select[name='i_arrCntrMatr']").change(function(){
				var div_idx = j$("select[name='i_arrCntrMatr']").index(j$(this));
				
				if(j$(this).val() == 'OCT_2_04'){
					j$(".CntrMatr_etc_div").eq(div_idx).show();
				}else{
					j$(".CntrMatr_etc_div").eq(div_idx).hide();
					j$(".CntrMatr_etc_div").find("input[name='i_arrCntrMatr_etc']").val("");
				}
			});
			fn_calendarSet("i_arrStock_dt");
			inputChkRadioAddEvent(wrap);
		}
	}
	
}

function getProductIndex(obj){
	var tbody = jQuery(obj).closest('.tbody_product_info');
	var result;
	$('.tbody_product_info').each(function(i,obj){
		if(obj==tbody[0]){
			result = i;
			return;
		}
	});
	return result;
}


function inputChkRadioAddEvent(target) {
	
	if (target == undefined) {
		target = jQuery(document);
		//target = jQuery(".chk-style, .rd-style").not(".not_select");
	}
	target.find('.rd-style input[type=radio]').each(function(i,obj){
		var bol = $(obj).is(":checked");
		if(bol){
			$(obj).parent().parent().addClass("on");			
		}else{
			$(obj).parent().parent().removeClass("on");			
		}
	});
	target.find('.chk-style input[type=checkbox]').each(function(i,obj){
		var bol = $(obj).is(":checked");
		if(bol){
			$(obj).parent().parent().addClass("on");			
		}else{
			$(obj).parent().parent().removeClass("on");			
		}
	});
	
	$('.chk-style label').click(function(event) {
		if(jQuery(this).find("input").attr("id").indexOf("chk") != -1){
			return;
		}
		var index = getProductIndex(jQuery(this).find("input"));
		if(typeof OreqReg.chkBool[index] =='boolean' && !OreqReg.chkBool[index]){
			if(jQuery(this).hasClass("on")){
				jQuery(this).find("input").prop('checked',true);
			}else{
				jQuery(this).find("input").prop('checked',false);				
			}
			return;
		}
		var chkBoxOn = jQuery(this).find("input").is("input:checked");
		if (chkBoxOn == true){
			jQuery(this).addClass("on");
		} else {
			jQuery(this).removeClass("on");
		}
	});
	$('.rd-style label').click(chk_radio.inputRadio);
	
}
