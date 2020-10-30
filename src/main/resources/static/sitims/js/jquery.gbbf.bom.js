var	x				=	2;
var	y				=	1;
var	rowCnt		=	0;
var	lv				=	0;
var	item;
var	pIdxTmp;

var arrAllergyControl	=	new Array();
var arrOriginControl		=	new Array();
var bfBom = {

	fnAddRow : function() {																												//추가

		var	pId			=	j$("#mixed_tbody tr:last").attr("id");																//선택한 row ID
		var	iIdx		=		pId.substring(pId.lastIndexOf("_")+1 , pId.length);

		if (x < iIdx) {		
			x	=	parseInt(iIdx)+1;																											//수정화면 last tr count  + 1
		}

		if(j$("#mixed_tbody tr:last").find("td	input[name='i_sItemCd']").val() == ""){
				alert("성분코드를 검색해주세요");
				return;
		}

		rowCnt				=	j$("#mixed_tbody tr:last").attr("class").replace("tree_", "");
		var	newItem		=	j$("#mixed_tbody tr:eq(0)").clone();
		var	setLevel		=	parseInt(rowCnt)+1;	
		
		newItem.removeClass();
		newItem.addClass("tree_"	+	x);

		newItem.attr("id", "rId_" + x);
		bfBom.clearElementLv1(newItem);	

		lv				=	1;
		newItem.find("td .hiddenLv").val(lv);																						//최상위
		newItem.find("td .i_removeFlag_H").val("A");																				//삭제버튼 FLAG /	F : 삭제불가(첫번째tr시) ,	A:	속해있는 ROW 전체삭제	,	O	:	1ROW삭제 

		newItem.find("td	input[name*='i_sAllergyNm']").attr("id","i_sAllergyNm_" + x);
		newItem.find("td	input[name*='i_sAllergyNm']").attr("name","i_sAllergyNm_" + x);
		newItem.find("td	input[name*='i_arrAllergyCd_']").attr("name","i_arrAllergyCd_" + x);
		
		newItem.find("td	input[name*='i_sOriginNm']").attr("id","i_sOriginNm_" + x);
		newItem.find("td	input[name*='i_sOriginNm']").attr("name","i_sOriginNm_" + x);
		newItem.find("td	input[name*='i_arrOriginCd_']").attr("name","i_arrOriginCd_" + x);
		
		newItem.find("td	input[name*='i_sFlagRadiation_']").attr("id","i_sFlagRadiation_" + x);
		newItem.find("td	input[name*='i_sFlagRadiation_']").attr("name","i_sFlagRadiation_" + x);
		
		newItem.find(".Allergy").attr("id","mibBox_" + x);
		newItem.find(".Origin").attr("id","originBox_" + x);
		
		j$("#mixed_tbody").append(newItem);
		
		var chkAllId = "";
		var chkOriId = "";
		
		newItem.find("label[for*='i_arrAllergyCd_']").each(function (index, item) {
			chkAllId = j$(this).find("input[name*='i_arrAllergyCd_']").attr("id");
			j$(this).attr("for","i_arrAllergyCd_" + x + chkAllId.substring(chkAllId.lastIndexOf("_")));
			j$(this).find("input[name*='i_arrAllergyCd_']").attr("id", "i_arrAllergyCd_" + x + chkAllId.substring(chkAllId.lastIndexOf("_")));		
			j$(this).find("input[name*='i_arrAllergyCd_']").attr("name", "i_arrAllergyCd_" + x);
		});
		
		newItem.find("label[for*='i_arrOriginCd_']").each(function (index, item) {
			chkOriId = j$(this).find("input[name*='i_arrOriginCd_']").attr("id");
			j$(this).attr("for","i_arrOriginCd_" + x + chkOriId.substring(chkOriId.lastIndexOf("_")));
			j$(this).find("input[name*='i_arrOriginCd_']").attr("id", "i_arrOriginCd_" + x + chkOriId.substring(chkOriId.lastIndexOf("_")));		
			j$(this).find("input[name*='i_arrOriginCd_']").attr("name", "i_arrOriginCd_" + x);
		});
		
		arrAllergyControl[x] = new MultiChooseBox({
			inputText : "i_sAllergyNm_" + x
			,inputTextID : "i_sAllergyNm_" + x
			,inputChk : "i_arrAllergyCd_" + x
			,enableAll : false
			,boxId : "mibBox_" + x});
 		
 		arrOriginControl[x] = new MultiChooseBox({
 			inputText : "i_sOriginNm_" + x
			,inputTextID : "i_sOriginNm_" + x
			,inputChk : "i_arrOriginCd_" + x
			,enableAll : false
			,boxId : "originBox_" + x});
 		
		arrAllergyControl[x].init();
		arrOriginControl[x].init();
		
		x++;
		
	},
	fnAddBottom : function(obj, vItem) {																							//하위추가

		var	pRow			=	j$(obj).parent().parent();																			//선택한 row Obj
		var	pRowClass	=	pRow.attr("class");																					//선택한 row CLASS
		var	pId			=	pRow.attr("id");																						//선택한 row ID

		var	iIdx		=	pId.substring(pId.lastIndexOf("_")+1 , pId.length);
		var	cIdx		=	pRowClass.substring(pRowClass.lastIndexOf("_")+1 , pRowClass.length);
		/*
		if (pRow.find("td	input[name='i_sItemCd']").val() == "") {
			showMessageBox({message : "성분코드를 입력해주세요."});
			pRow.find("td	input[name='i_sItemCd']").focus();
			return false;
		}
		if (pRow.find("td	input[name='i_sAmount']").val() == "") {
			showMessageBox({message : "함량을 입력해주세요."});
			pRow.find("td	input[name='i_sItemCd']").focus();
			return false;
		}*/

		var	cRow		=	pRow.clone();

		/*if (pRow.find("td	input[name='i_sRlv_h']").val() == "5") {
			showMessageBox({message : "더이상 하위를 추가 할 수 없습니다."});
			return false;
		}*/

		bfBom.clearElement(cRow);											// input text 초기화

		if(pIdxTmp != pId){														//[s]	하위추가 버튼 클릭시 동일한 row클릭 체크
			pIdxTmp = pId;														// 		이전 아이디값 저장
			y	=	1;																	//[e]	하위추가 버튼 클릭시 동일한 row클릭 체크
		}

		if(parseInt(iIdx) > x){													//수정화면에서 x값 을 tr최대값 +1 로 맞춰줌
			x	=	parseInt(iIdx) + 1;
		}

		cRow.removeClass();													//class remove
		cRow.addClass(pRowClass + "_" +	y);							//class set		(2레벨 : tree_1_1 , tree_1_2	/	3레벨	:	tree_1_1_1 , tree_1_1_2
		cRow.attr("id", "rId_" + x);												//ID set

		cRow.find("td	input[name*='i_sFlagRadiation_']").attr("id","i_sFlagRadiation_" + x);
		cRow.find("td	input[name*='i_sFlagRadiation_']").attr("name","i_sFlagRadiation_" + x);
		
		cRow.insertAfter(j$("#mixed_tbody #"+ pId + ":last"));
		
		/*cRow.find("td	input[name='i_sAmount']").attr("onchange","bfBom.calcSubAmount(this)");*/
		cRow.find(".hiddenUcd").val(pRow.find("td	input[name='i_sItemCd']").val());		//상위성분코드
		
		//cRow.find("td .hiddenSNo").val(y);									//정렬순서 

		lv 	=	parseInt(cRow.find("td .hiddenLv").val()) + 1 ;				//[s]	현재	Lv
		cRow.find("td .hiddenLv").val(lv);										//[e]	현재	Lv
	
		cRow.find(".treeImg").css("margin-left",lv + "0px");					//[s]	tree image
		cRow.find(".treeImg").show();												//[e]	tree image

		cRow.find("td .i_removeFlag_H").val("O");								//삭제버튼 FLAG /	F : 삭제불가(첫번째tr시) ,	A:	속해있는 ROW 전체삭제	,	O	:	1ROW삭제
		jQuery("#mixed_complete").val("N");

		cRow.find("td	input[name*='i_sAllergyNm']").attr("id","i_sAllergyNm_" + x);
		cRow.find("td	input[name*='i_sAllergyNm']").attr("name","i_sAllergyNm_" + x);
		
		cRow.find("td	input[name*='i_sOriginNm']").attr("id","i_sOriginNm_" + x);
		cRow.find("td	input[name*='i_sOriginNm']").attr("name","i_sOriginNm_" + x);
		
		cRow.find(".Allergy").attr("id","mibBox_" + x);
		cRow.find(".Origin").attr("id","originBox_" + x);
		
		var chkAllId = "";
		var chkOriId = "";
		
		cRow.find("label[for*='i_arrAllergyCd_']").each(function (index, item) {
			chkAllId = j$(this).find("input[name*='i_arrAllergyCd_']").attr("id");
			j$(this).attr("for","i_arrAllergyCd_" + x + chkAllId.substring(chkAllId.lastIndexOf("_")));
			j$(this).find("input[name*='i_arrAllergyCd_']").attr("id", "i_arrAllergyCd_" + x + chkAllId.substring(chkAllId.lastIndexOf("_")));		
			j$(this).find("input[name*='i_arrAllergyCd_']").attr("name", "i_arrAllergyCd_" + x);
		});
		
		cRow.find("label[for*='i_arrOriginCd_']").each(function (index, item) {
			chkOriId = j$(this).find("input[name*='i_arrOriginCd_']").attr("id");
			j$(this).attr("for","i_arrOriginCd_" + x + chkOriId.substring(chkOriId.lastIndexOf("_")));
			j$(this).find("input[name*='i_arrOriginCd_']").attr("id", "i_arrOriginCd_" + x + chkOriId.substring(chkOriId.lastIndexOf("_")));		
			j$(this).find("input[name*='i_arrOriginCd_']").attr("name", "i_arrOriginCd_" + x);
		});
		
		arrAllergyControl[x] = new MultiChooseBox({
			inputText : "i_sAllergyNm_" + x
			,inputTextID : "i_sAllergyNm_" + x
			,inputChk : "i_arrAllergyCd_" + x
			,enableAll : false
			,boxId : "mibBox_" + x});
		
		arrAllergyControl[x].init();
		
 		arrOriginControl[x] = new MultiChooseBox({
 			inputText : "i_sOriginNm_" + x
			,inputTextID : "i_sOriginNm_" + x
			,inputChk : "i_arrOriginCd_" + x
			,enableAll : false
			,boxId : "originBox_" + x});
 		
		
		arrOriginControl[x].init();

		x++;
		y++;	//CLASS & 정렬순서 증가값
	},

	fnRowDelete : function(obj) {																										//삭제
		
		var	pRow				=	jQuery(obj).parent().parent();	
		var	removeFlag		=	pRow.find("td .i_removeFlag_H").val();	
		var	pLevel			=	pRow.attr("class").substring(5,6);

		var	cNode	=	pRow.next();
		
		var	treeItemCd	=	pRow.find("input[name='i_sItemCd']").val();
		var	cUcd		=	cNode.find("input[name='i_sUitemCd_h']").val();

		if (cNode != null && treeItemCd	==	cUcd	) {														//하위 있으면 삭제 막음
			alert("하위성분먼저 삭제바랍니다.");
			return;
		}
		
		if (removeFlag == "F") {
			alert("삭제 할 수 없습니다.");
			return;
		}
		if (removeFlag == "A") {
				j$("#mixed_tbody").find("[class*=tree_" + pLevel + "]").remove();
			}else if(removeFlag	==	"O"){
				pRow.remove();
			}

		/*bfBom.calcSubAmount(obj);*/

	
	},
	modifyFnRowDelete : function(obj) {																										//삭제
		
		var	pRow				=	jQuery(obj).parent().parent();	
		var	removeFlag		=	pRow.find("td .i_removeFlag_H").val();	
		
		var	rClass				=	pRow.find("input[name='i_sPitemCd_h']").val();
		var	sClass			=	pRow.attr("class").replace("tree_", "");		
		
		var	cNode	=	pRow.next();
		
		var	treeItemCd	=	pRow.find("input[name='i_sItemCd']").val();
		var	cUcd		=	cNode.find("input[name='i_sUitemCd_h']").val();

		if (cNode != null && treeItemCd	==	cUcd	) {																							//하위 있으면 삭제 막음
			alert("하위 성분할 수 없습니다.");
			return;
		}
		
		if (removeFlag == "F") {
			alert("삭제 할 수 없습니다.");
			return;
		}
		
		if (removeFlag == "A" && rClass != "") {
			if(sClass.length ==	7){
				j$("#mixed_tbody").find("[class*=tree_" + rClass + "]").remove();
			}else{
				j$("#mixed_tbody").find("[class*=tree_" + sClass + "]").remove();
			}
		
		}else{
				pRow.remove();
			}

	},
		fnSearchStdName : function(obj) {

			var	pRow			=	jQuery(obj).parent().parent();
			var	itemCd		=	pRow.find("input[name='i_sItemCd']").val();
			var	title			=	"성분코드 검색";
			item					=	pRow;

			var arrPars = [];
			title	= "표준원재료명 검색";
			arrPars.push("keyword=" + encodeURI(itemCd));
			arrPars.push("i_sCallFunction=parent.bfBom.setStdName");

			cmDialogOpen("supo_gbbf_bf_item_list_pop", {
				 title : title
				, width : 700
				, height: 600
				, modal : true
				, changeViewAutoSize : true 
				, url:"/supo/gbbf/supo_gbbf_bf_item_list_pop.do?" + arrPars.join("&")
			});
		},
		setStdName : function (cd, nm , nm_en) {

			var	treeLv	=	item.find("td .hiddenLv").val();			//최상위 성분코드 SET 위해 lv 구함
			
			if (treeLv	==		"1") {
				item.find("td .hiddenPcd").val(cd);							//최상위 성분코드
				item.find("td .hiddenUcd").val(cd);							//상위 성분코드
			}
			
			var	cNode	=	item.next();
			
			var	treeItemCd	=	item.find("input[name='i_sItemCd']").val();
			var	cUcd		=	cNode.find("input[name='i_sUitemCd_h']").val();

			if (cNode != null && treeItemCd	==	cUcd	) {
				cNode.find("input[name='i_sUitemCd_h']").val(cd);
			}

			var chkItem	=	0;
			j$.each(j$("#mixed_tbody").find("[name*='i_sItemCd']"), function() {
				if (j$(this).val()	==		cd) {
					chkItem	+= 1;
				}
			});
			
				item.find("input[name='i_sItemCd']").val(cd);					//성분코드
				item.find("input[name='i_sItemNm']").val(nm);				//성분명
				item.find("input[name='i_sItemNm_en']").val(nm_en);				//성분명
				item.find("input[name*='i_sAllergyNm_']").attr("name","i_sAllergyNm_"		+	cd);
				item.find("input[name*='i_sAllergyNm_']").attr("id","i_sAllergyNm_"		+	cd);
				
				item.find("input[name*='i_sOriginNm_']").attr("name","i_sOriginNm_"		+	cd);//
				item.find("input[name*='i_sOriginNm_']").attr("id","i_sOriginNm_"		+	cd);//
				item.find("input[name*='itemcd_h']").val(cd);				

				if (item.find("input[name='mFlag']").val()	==	"M") {													//수정시

					var	indexId=	item.find("td	input[name*='i_sAllergyNm']").attr("id");
					
					item.find("td	input[name*='i_arrAllergyCd_']").attr("name","i_arrAllergyCd_" + cd);
					item.find("td	input[name*='i_arrOriginCd_']").attr("name","i_arrOriginCd_" + cd);

				}

			/*	docCheckArea();*/
			

		},
		validate : function () {												//저장전에 정렬순서 

			var matInfoNmChk = 0;
			j$.each(j$("#table_cm_mat").find("[name*='i_sMatInfoNm']"), function() {
				if (j$(this).val()	==		"") {
					matInfoNmChk	=	1;
					alert("원료명을 입력해주세요");
					j$(this).focus();
					return false;
				}
			});
			
			if (matInfoNmChk > 0) {
				return false;
			}
			
			var sapChk	=	0;
			if (j$("#table_cm_mat").find("[name*='i_sSapCd']").val()		==		"") {
				alert("SAP CODE를 입력해주세요");
				sapChk	=	1;
				j$("#table_cm_mat").find("[name*='i_sSapCd']")	.focus();
			}

			if (j$("#table_cm_mat").find("[name='i_sSapCd']").val().length != 7) {
				alert("SAP CODE는 7자 입니다.");
				sapChk	=	1;
				j$("#table_cm_mat").find("[name*='i_sSapCd']")	.focus();
			}
			
			if (sapChk > 0) {
				return false;
			}
			
			if(j$("#sapChk_h").val()	==	"N"){
				alert("중복된 SAP CODE 입니다. 재입력바랍니다.");
				return false;
			}
			
			
			var	itemChk	=	0;
			j$.each(j$("#mixed_tbody").find("[name*='i_sItemCd']"), function() {
				if (j$(this).val()	==		"") {
					itemChk	=	1;
					alert("성분코드를  검색해주세요");
					return false;
				}
			});
			if (itemChk > 0) {
				return false;
			}

			
			j$.each(j$("#mixed_tbody").find("[name='i_sAmount']"), function(index, item){
				if (j$(this).val()	==		"") {
					itemChk	=	1;
					alert("함량을  입력해주세요");
					j$(this).focus();
					return false;
				}
				
				if (j$(this).val()	>	100) {
					itemChk	=	1;
					alert("함량은 100% 이상 입력할수 없습니다.");
					j$(this).focus();
					return false;
				}
			});
			
			if (itemChk > 0) {
				return false;
			}
			
	/*		if(jQuery("#mixed_completeA").val() != "Y"){
				alert("함량비율이 정확하지 않습니다.확인해주세요");
				return false;
			};
			if(jQuery("#mixed_complete").val() != "Y"){
				alert("함량비율이 정확하지 않습니다.확인해주세요");
				return false;
			};*/
			
			var	i	=	1;
			j$.each(j$("#mixed_tbody").find("tr"), function() {				//순서 부여
				j$(this).find("td .hiddenSNo").val(i);
				j$(this).find("td input[name*='i_arrAllergyCd_']").attr("name","i_arrAllergyCd_" + i);
				i++;
			});
			
			var	a	=	1;
			j$.each(j$("#mixed_tbody").find("tr"), function() {				//순서 부여
				j$(this).find("td .hiddenSNo").val(a);
				j$(this).find("td input[name*='i_arrOriginCd_']").attr("name","i_arrOriginCd_" + a);
				a++;
			});

			return true;	
		},
		clearElementLv1 : function(obj) {
			
			obj.find("input[name='i_sItemCd']").val("");
			obj.find("input[name='i_sItemNm']").val("");
			obj.find("input[name='i_sItemNm_en']").val("");
			obj.find("input[name='i_sAmount']").val("");
			obj.find("input[name='i_sFlagGmo']").val("");
			obj.find("input[name='i_sIngredientElement']").val("");
			obj.find("input[name='i_sPitemCd_h']").val("");
			obj.find("input[name='i_sUitemCd_h']").val("");
			
			obj.find(".i_sOri_UitemCd").val("");
			obj.find(".i_sOri_ItemCd").val("");
			
			obj.find("input[name*='i_sAllergyNm_']").val("");
			obj.find("input[name*='i_sAllergyNm_']").attr("id","");
			obj.find("input[name*='i_sOriginNm_']").attr("");
			obj.find("input[name*='i_sOriginNm_']").attr("id","");
			obj.find("input[name='itemcd_h']").val("");
			obj.find("input[name='itemcd_a']").val("");
			
			

			obj.find("input[type=checkbox]").prop("checked", false);
			obj.find("label").removeClass("on");
			/*obj.find("input[type=checkbox]").val("선택");*/
			
			
		},
		clearElement : function(obj) {
			
			obj.find("input[name='i_sItemCd']").val("");
			obj.find("input[name='i_sItemNm']").val("");
			obj.find("input[name='i_sItemNm_en']").val("");
			obj.find("input[name='i_sAmount']").val("");
			obj.find("input[name='i_sFlagGmo']").val("");
			obj.find("input[name='i_sIngredientElement']").val("");
			obj.find(".i_sOri_UitemCd").val("");
			obj.find(".i_sOri_ItemCd").val("");

			obj.find("input[type=checkbox]").prop("checked", false);
			obj.find("label").removeClass("on");
		},
		calcAmount : function(obj){

	/*		var	lv	=	"";
			
			jQuery("input[name*='i_sAmount']").each(function(index, item) {
				lv	=	"";
				lv	=	jQuery(item).parent().parent().find("input[name*='i_sRlv_h']").val();

				if(lv == "1") {
					if (j$(this).val()	>	100) {
						itemChk	=	1;
						alert("함량은 100% 이상 입력할수 없습니다.");
						j$(this).val("0");
						j$(this).focus();
					}
				}
			});*/
		},
		calcSubAmount : function(obj){
			
	/*	var pCd	=	 jQuery(obj).parent().parent().find("input[name*='i_sPitemCd_h']").val();
			var uCd	=	 jQuery(obj).parent().parent().find("input[name*='i_sUitemCd_h']").val();
			var cLv	=	jQuery(obj).parent().parent().find("input[name*='i_sRlv_h']").val();
			var totalAmount = 0;
			jQuery("#mixed_tbody").find("[name*='i_sUitemCd_h'][value='" + uCd + "']").each(function (index, item) {
				if (jQuery(item).parent().find("[name*='i_sRlv_h']").val() == cLv) {
					var amt = jQuery(item).parent().parent().find("input[name*='i_sAmount']").val() == "" ? 0 : parseFloat(jQuery(item).parent().parent().find("input[name*='i_sAmount']").val());
					totalAmount += amt;
				}
			});

			if (totalAmount < 100) {
				jQuery("#mixed_complete").val("N");
			} else if (totalAmount > 100) {
				jQuery("#mixed_complete").val("N");
				showMessageBox({message : "총 함량은 100%를 넘을 수 없습니다."});
				jQuery(obj).val("0");
			}else if (totalAmount == 100) {
				jQuery("#mixed_complete").val("Y");
			}*/
		},
		ajaxSapCdCheck : function(obj){									//SAP 중복체크
			
			if(j$(obj).val().length != 7	){
				j$("#sapChk").text("");
				return false;
			}

			if(j$(obj).val().length == 7	){

				var	sapCd	=	j$(obj).val();	

				cmAjax({
					url : "./supo_gbbf_bf_sap_ajax.do"
					, type : "post"
					, dataType : "json"
					, isBlock : false
					, data : {
						i_sSapCd	:	sapCd
					}
					, success : function(data){
						if(data.data == "Y"){
							j$("#sapChk").text(data.message);
							j$("#sapChk").css("color","green");					//사용가능
							j$("#sapChk_h").val("Y");
						}else if(data.data == "N"){
							j$("#sapChk").text(data.message);					//사용불가
							j$("#sapChk").css("color","red");
							j$("#sapChk_h").val("N");
						}
					}
				});
			}
		}
};
