var body;
var fRowCnt = 0; 
var nRowCnt = 0;
var funcTypeList = null;
var originData = null;
var arrOriControl = new Array();
var targetParent = null;
var water_content_nm = "반제품명";
var water_content_cd = "반제품 코드"

var funcBom = {
	init : function(param) {
		
		j$(".tr_limit").each(function(i){
			j$(this).find("td").css("background-color", "#FF6C6C");
		});
		
		imagePath = param.imgPath;
		jQuery(document).find(".mixed_rows").each(function(index, item) {
			jQuery(item).attr("id", "mixed_rows_" + index);
		});
		
		body = jQuery(".mixed_rows");
		jQuery(".mixed_rows").find("a").css("color", "blue");
		jQuery(".mixed_rows").find("a").mouseover(function() { jQuery(this).css("text-decoration", "underline") });
		jQuery(".mixed_rows").find("a").mouseout(function() { jQuery(this).css("text-decoration", "none") });
				
		jQuery(document).on("keyup", "input[name*='mi_sMaterialNm_']", function(e) {
			e.preventDefault();
			if (event.keyCode == 13) {
				jQuery(this).parent().find(".img").click();
			}
		});
		
		jQuery(document).on("focus", "input[name='arrContentNm']", function(e) {
			if(jQuery(this).val() == water_content_nm) {
				jQuery(this).val('');
				jQuery(this).removeClass("water");
			}
		});
		jQuery(document).on("blur", "input[name='arrContentNm']", function(e) {
			if(jQuery(this).val() == "") {
				jQuery(this).val(water_content_nm);
				jQuery(this).addClass("water");
			}
		});		
		
		
		jQuery(document).on("focus", "input[name='arrContentCd']", function(e) {
			if(jQuery(this).val() == water_content_cd) {
				jQuery(this).val('');
				jQuery(this).removeClass("water");
			}
		});
		jQuery(document).on("blur", "input[name='arrContentCd']", function(e) {
			if(jQuery(this).val() == "") {
				jQuery(this).val(water_content_cd);
				jQuery(this).addClass("water");
			}
		});	
				
		jQuery(document).on("click", ".row_delete", function(e) {
			e.preventDefault();
			var parent = jQuery(this).parent().parent();
			funcBom.fnRowDelete(parent);
		});
		
		jQuery(document).on("change", "input[name='arrAmount']", function(e) {
			funcBom.calcAmount(jQuery(this));
		});
		
		jQuery("#btnLoadPreFomula").click(function(e) {
			e.preventDefault();
			targetParent = jQuery(this).parent().parent().parent().parent().parent().attr("id");
			if (targetParent == null) {
				alert("열에 아이디가 지정되어 있지 않습니다.");
				return;
			}
			funcBom.preLoad(jQuery(this));
		});
		
		
		jQuery(document).on("click", ".btn_matnr_ref_pop", function(e) {
			e.preventDefault();
			
			funcBom.fnMaterialSearch(jQuery(this).parent().parent());
		});
	},
	fnAddContentChild: function(obj) {
		var parent = jQuery(obj).parent().parent();
		var itemRow = funcBom.itemRow("content");
		var flag	= jQuery(parent).find("input[name='arrFlag']").val();
		itemRow = jQuery(itemRow).clone();
		
		try {
			if (jQuery(parent).find("input[name='arrContentNm']").val() == water_content_nm) {
				showMessageBox({message: "내용물명을 입력해 주세요."});
				jQuery(parent).find("input[name='arrContentNm']").focus();
				return;
			}
			
//			if (jQuery(parent).find("input[name='arrContentCd']").val() == "") {
//				showMessageBox({message: "내용물코드를 입력해 주세요."});
//				return;
//			}
			itemRow.find("input[name='arrFlag']").val(flag);
			itemRow.find("input[name='arrContentNm']").val(jQuery(parent).find("input[name='arrContentNm']").val());
			if (jQuery(parent).find("input[name='arrContentCd']").val() != water_content_cd) {
				itemRow.find("input[name='arrContentCd']").val(jQuery(parent).find("input[name='arrContentCd']").val());
			}
			itemRow.find("input[name='arrStep']").val("1");
							
			jQuery(parent).after(itemRow);

			addOnlyNumberEvent();
		} catch(e) {
			window.alert(e.message);
		}
	},
	fnMaterialSearch : function(obj) {
		
		var index = jQuery(".mixed_rows").find("tr").index(obj); 
		var keyword = jQuery(obj).find("input[name='arrMaterialNm']").val();
		var title;
		var arrPars = [];
		
		title	= "식품 원료";
		arrPars.push("keyword=" + encodeURI(keyword));
		//arrPars.push("complete='Y'");
		arrPars.push("i_sFoodTypeCd=" + jQuery("#functional_mixed_foodTypeCd").val());
		arrPars.push("i_sCallFunction=parent.funcBom.setMaterial");
		arrPars.push("targetRow=" + index);

		cmDialogOpen("zm_bf_material_list_pop", {
			title : title
	        , width : 700
	        , height: 600
	        , modal : true
	        , changeViewAutoSize : true 
	        , url:"/zm/bf/zm_bf_material_list_pop.do?" + arrPars.join("&")
		});
	},
	setMaterial : function(index, cd, nm) {
		//이미 추가한적이 있는지 검사한다.
		var checkCnt =0; 
		if (cd.length > 0) {
			jQuery(".mixed_rows").find("input[name='arrMaterialCd']").each(function(index, item) {
				if (jQuery(item).val() == cd)
					checkCnt += 1;			
			});
			
			if (checkCnt > 0) {
				showMessageBox({message: "이미 추가된 원재료 입니다."});
				return;
			}

			var parent = jQuery(".mixed_rows").find("tr").eq(index);
		    			
		    cmAjax({
				url	: "/zm/bf/zm_bf_material_view_json.do?cd=" + cd
				, type : "get"
				, dataType : "json"
				, async:false
				, isBlock : true
				, success : function (json) {
					if (json.status == "succ") {
						var item = json.data;
						jQuery(parent).find("input[name='arrMaterialNm']").val(item.v_material_nm);
						jQuery(parent).find("input[name='arrMaterialCd']").val(item.v_material_cd);
						jQuery(parent).find("input[name='arrVersion']").val(item.n_version);
						jQuery(parent).find(".resourceNm").html(item.v_resource_nm);
						jQuery(parent).find("input[name='arrResourceNm']").val(item.v_resource_nm);
						jQuery(parent).find(".functionalYN").html(item.v_spec_function_flag);
						jQuery(parent).find("input[name='arrFlagFunctional']").val(item.v_spec_function_flag);
						jQuery(parent).find("[name='arrVersion']").val(item.n_version);
						jQuery(parent).find(".radiation").html(item.v_flag_radiation);
						jQuery(parent).find("input[name='arrFlagRadiation']").val(item.v_flag_radiation);
						jQuery(parent).find(".allergy").html(item.v_allergy_nm);
						jQuery(parent).find("input[name='arrAllergy']").val(item.v_allergy_nm);
						
						jQuery(parent).find("#arrFoodAdditive").val(item.v_food_additive_yn);
						jQuery(parent).find("#arrFlagLimit").val(item.v_flag_limit);
						
						if(item.v_food_additive_yn == "Y" || item.v_flag_limit == "Y"){
							jQuery(parent).find("td").css("background-color", "#FF6C6C");
							if(item.v_flag_limit == "Y" && item.v_food_additive_yn == "Y"){
								jQuery(parent).attr("title", "제한적사용원료/품목별사용기준설정 원료포함");
							} else if (item.v_flag_limit == "Y"){
								jQuery(parent).attr("title", "제한적사용원료 포함");
							} else if (item.v_food_additive_yn == "Y"){
								jQuery(parent).attr("title", "품목별사용기준설정원료 포함");
							}
						}
						
						/**
						 * 기능성원료 여부 v_spec_function_flag DB에서 가져올때 구별함.
						 * 1. 개별인정형 기능성원료 포함여부가 Y 또는
						 * 2. 건강식품 기능성 원료
						 */
						if(item.v_spec_function_flag == 'Y'){ // 기능성 성분 함량 입력받는다.
							jQuery(parent).find("input[name='arrFuncAmount']").show();
						}
						
						var originList = "";
						for(var i=0; i < item.originList.length; i++) {
							originList += item.originList[i].v_area_nm;
							if (i < item.originList.length - 1) {
								originList += ", ";
							}
						}
						jQuery(parent).find(".originArea").html(originList);
						jQuery(parent).find("input[name='arrArea']").val(originList);
						
						// 하위원료
						if (item.mixedList.length > 0) {
							for (var i=(item.mixedList.length - 1); i >=0; i--) {
								funcBom.fnAddChildren(jQuery(parent), item.v_material_cd, item.mixedList[i]);
							}
						}
					}
					else {
						showMessageBox({message : json.message});
					}
				}
				, error : function() {
					showMessageBox({message : "<fmt:message key='pms.materialInfo.servererror'/>"});
				}
			});
		}
		
		addOnlyNumberEvent();
	},
	fnAddChildren : function(obj, cd, vItem) {
		var itemRow = funcBom.itemRow("child");
		var marginLeft = 0;
		var contentNm = "";
		var contentCd = "";
		var flag      = jQuery(obj).find("input[name='arrFlag']").val();
				
		itemRow = jQuery(itemRow).clone();
		marginLeft = parseInt(vItem.n_step) * 25;
		
		contentNm = jQuery(obj).find("input[name='arrContentNm']").val();
		
		contentCd = jQuery(obj).find("input[name='arrContentCd']").val();
		
		
		jQuery(itemRow).find("input[name='arrContentNm']").val(contentNm);
		jQuery(itemRow).find("input[name='arrContentCd']").val(contentCd);
		jQuery(itemRow).find("input[name='arrFlag']").val(flag);
		
		if (jQuery(obj).find("input[name='arrStep']").val() != "0") {
			marginLeft += 25;
		}

		itemRow.find(".treeImg").css("margin-left", (marginLeft) + "px");
		
		itemRow.find("input[name='arrMaterialCd']").val(vItem.v_material_cd);
		if (jQuery(obj).find("input[name='arrStep']").val() == "1") {
			itemRow.find("input[name='arrStep']").val((vItem.n_step) + 1);
			
		} else {
			itemRow.find("input[name='arrStep']").val(vItem.n_step);	
			
		}
		
		if (vItem.n_step > 1) {
			itemRow.find(".materialNm").html(vItem.v_material_nm + " (" + vItem.n_amount + "%)");
		} else {
			itemRow.find(".materialNm").html(vItem.v_material_nm);
		}
		
		itemRow.find("input[name='arrMaterialNm']").val(vItem.v_material_nm)
		itemRow.find("input[name='arrVersion']").val(vItem.n_version);
		itemRow.find(".resourceNm").html(vItem.v_resource_nm);
		itemRow.find("input[name='arrResourceNm']").val(vItem.v_resource_nm);
		
		if (parseInt(vItem.n_step) == 1) {
			itemRow.find(".percentage").html(vItem.n_amount + "%");
		} 
		
		itemRow.find("input[name='arrAmount']").val(vItem.n_amount);
		
		itemRow.find(".functionalYN").html(vItem.v_flag_functional);
		itemRow.find("input[name='arrFlagFunctional']").val(vItem.v_flag_functional);
		itemRow.find("input[name='arrFunctionalDesc']").val(vItem.v_functional_desc);
		itemRow.find(".radiation").html(vItem.v_flag_radiation);
		itemRow.find("input[name='arrFlagRadiation']").val(vItem.v_flag_radiation);
		itemRow.find(".allergy").html(vItem.v_allergy_nm);
		itemRow.find("input[name='arrAllergy']").val(vItem.v_allergy_nm);
		var originList = "";
		for(var i=0; i < vItem.originList.length; i++) {
			originList += vItem.originList[i].v_area_nm;
			if (i < vItem.originList.length - 1) {
				originList += ", ";
			}
		}
		itemRow.find(".originArea").html(originList);
		itemRow.find("input[name='arrArea']").val(originList);
		
		if(vItem.v_flag_functional == "Y"){
			itemRow.find("input[name='arrFuncAmount']").show();
		}
		
		jQuery(obj).after(itemRow);

		addOnlyNumberEvent();
		//funcBom.calcSubAmount(jQuery("#mi_iAmount_" + rowCnt));
	},
	fnAddContentRow: function(obj) {
		var tBody = jQuery(obj).parent().parent().parent().parent().find(".mixed_rows");
		var flag = jQuery(obj).parent().find("input[name='flag']").val();
		var contentRow = funcBom.contentRow();
		contentRow = jQuery(contentRow).clone();
		jQuery(contentRow).find("input[name='arrFlag']").val(flag);
		
		jQuery(tBody).append(contentRow);
		addOnlyNumberEvent();
	},
	fnAddRow : function(obj) {
		var itemRow = funcBom.itemRow("row");
		var tBody = jQuery(obj).parent().parent().parent().parent().find(".mixed_rows");
		var flag = jQuery(obj).parent().find("input[name='flag']").val();
		itemRow = jQuery(itemRow).clone();
		itemRow.find("input[name='arrStep']").val("0");
		itemRow.find("input[name='arrFlag']").val(flag);
		jQuery(tBody).append(itemRow);
		addOnlyNumberEvent();
	},
	fnRowDelete : function(parent) {
		var rowIdx = parseInt(jQuery(".mixed_rows").find("tr").index(parent));
		var oStep = parseInt(jQuery(parent).find("input[name='arrStep']").val());
		var arrDelete = [];
		var startIdx = rowIdx + 1;
		var endIdx = jQuery(".mixed_rows").find("tr").length;
		var isComplete = false;
		
		jQuery(parent).find("input[name='arrAmount']").val("0");
		
		try {
			//하위 아이템이 있을 경우 하위 아이템을 모두 지운다.\
			while (!isComplete) {
				var item = jQuery(".mixed_rows").find("tr").eq(startIdx);
				var iStep = parseInt(jQuery(item).find("input[name='arrStep']").val());
				if (oStep < iStep) {
					jQuery(item).remove();
					isComplete = false;
				} else {
					isComplete = true;
				}
			}
			
			funcBom.calcAmount(jQuery(parent).find("input[name='arrAmount']"));
			jQuery(parent).remove();
						
			if (jQuery(".mixed_rows").find("tr").length == 0) {
				this.fnAddRow();
			}
		} catch(e) {
			alert(e.message);
		}
		
	},
	calcAmount : 
		function (obj) {
		var totalAmount = 0;
		var T = Number('1e'+12);
		var parent = jQuery(obj).parent().parent();
		var step = parseInt(jQuery(parent).find("input[name='arrStep']").val());
		var parentID = jQuery(obj).parent().parent().parent().attr("id");
		var oldVal = jQuery("#" + parentID).parent().find(".mixed_total_amount").html();
		
		if (step == 0) {
			jQuery("#" + parentID).find("input[name='arrAmount']").each(function(index, item) {
				var parent = jQuery(item).parent().parent();
				if (jQuery(parent).find("input[name='arrStep']").val() == "0") {
					var nAmount = jQuery(item).val() == "" ? 0 : parseFloat(jQuery(item).val());
					totalAmount = Math.round((totalAmount + nAmount)*T)/T;
				}
			});
			
			if(totalAmount > 100) {
				alert("총 함량은 100%를 넘을 수 없습니다.");
				jQuery(item).val("0");
				return;
			}
			jQuery("#" + parentID).parent().find(".mixed_total_amount").html(totalAmount);
			
			
		} else if (step == 1) {
			var parentIdx = funcBom.getParentIndex(parent);
			var isComplete = false;
			var chkIndex = parentIdx + 1;
			
			for (var i=chkIndex; i < jQuery(".mixed_rows").find("tr").length; i++) {
				var item = jQuery(".mixed_rows").find("tr").eq(i);
				var iStep = parseInt(jQuery(item).find("input[name='arrStep']").val());
				
				if(iStep == 0) {
					return false;
				} else {
					if (iStep == 1) {
						var nAmount = jQuery(item).find("input[name='arrAmount']").val() == "" ? 0 : parseFloat(jQuery(item).find("input[name='arrAmount']").val());
						totalAmount = Math.round((totalAmount + nAmount)*T)/T;
					}
				}
			}
			jQuery(".mixed_rows").find("tr").eq(parentIdx).find(".percentage").html(totalAmount);
			
			
		}
	},
	validate : function() {
		var totalAmt = 0;
		var retVal = true;
		
//		jQuery(".mixed_rows").find("input[name='arrAmount']").each(function (index, item) {
//			if (jQuery(item).val() == "") {
//				retVal = false;
//				return false;
//			}
//		});
		
		
		//반제품 코드가 워터마크 텍스트면 값을 없앤다.
		jQuery(".mixed_rows").find("input[name='arrContentCd']").each(function(index, item) {
			if (jQuery(item).val() == water_content_cd) {
				jQuery(item).val('');
			}
		});
		return retVal;
	},
	getMaxIdx:function() {
		var maxIdx = 1;
		jQuery(".mixed_rows").find("[id*='mi_iIdx']").each(function(index, item) {
			if (maxIdx < parseInt(jQuery(item).val()))
					maxIdx = parseInt(jQuery(item).val());
		});
		
		maxIdx += 1;
		return maxIdx;
	},
	preLoad:function(obj) {
		var arrPars = [];
		arrPars.push("i_sStatusCd=MIS030");
		arrPars.push("i_sDivisionCd=" + jQuery("input[name='i_sDivisionCd']:checked").val());
		arrPars.push("i_sCallFunction=parent.funcBom.setProduct");
		cmDialogOpen("supo_bf_product_pool_list_pop", {
	           title:"식품규격서",
	           width:580,
	           height:450,
	           modal : true,
	           url:"/supo/bf/supo_bf_product_pool_list_pop.do?" + arrPars.join("&")
		});
	},	
	setProduct:function(obj) {
		//원료 및 성분함량 taglib 다시 요청
		cmAjax({
			url : "/zm/fp/zm_fp_product_mixedinfo_ajax.do" ,
			data : {
				i_sRecordId : obj.i_sProductCd ,
				i_sActionFlag : "modify"
			} ,
			dataType : "html" ,
			success : function(html){
				j$("#" + targetParent).html(html);
			} ,
			error : function(){
				showMessageBox({
					title : ""
					, message : "<fmt:message key='pms.materialInfo.servererror'/>"
				});
			}
		});
	},
	viewMaterial:function(cd) {
		var arrPars = [];
		//var title = "식품원료 기본정보";
		arrPars.push("i_sMaterialCd="+cd);
		arrPars.push("i_sViewStatusCd=MIS010");
		var flag = arrPars.join("&");
		var popWin = window.open("/zm/bf/zm_bf_material_info_mis010_only_view.do?"+flag , "materialInfo", "width=700,height=600,menubar=no,resizable=yes, scrollbars=yes, toolbar=no, location=no");
		popWin.focus();
		
//		cmDialogOpen("zm_bf_material_info_mis010_only_view", {
//			title : title
//	        , width : 700
//	        , height: 600
//	        , modal : true
//	        , changeViewAutoSize : true 
//	        , url:"/zm/bf/zm_bf_material_info_mis010_only_view.do?" + arrPars.join("&")
//		});
	},
	exportExcel: function(recordId, version, flag) {
		//엑셀출력
		var arrPars = [];
		arrPars.push("i_sRecordId=" + recordId);
		arrPars.push("i_sProductCd=" + recordId);
		arrPars.push("i_iVersion=" + version);
		//arrPars.push("i_sFlag=" + flag);
		//fncExcelDownload("/supo/bf/supo_bf_product_mixed_info_excel.do?" + arrPars.join("&"));
		document.location.href = "/supo/bf/supo_bf_product_mixed_info_excel.do?" + arrPars.join("&");
	},
	contentRow: function() {
		var retRow = "";
		
		retRow += "\n" + "<tr>";
		retRow += "\n" + "\t<td>";
		retRow += "\n" + "\t\t<input type='text' name='arrContentNm' value='" + water_content_nm + "' class='water' style='width:90%;' />";
		retRow += "\n" + "\t\t<input type='hidden' name='arrStep' value='0' />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrMaterialCd\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrMaterialNm\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrItemIdx\" /> ";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrVersion\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFlagFunctional\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFunctionalDesc\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrArea\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrAllergy\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFlagRadiation\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrResourceNm\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFlag\" />";
		retRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFuncAmount\" />";
		retRow += "\n" + "\t</td>";
		retRow += "\n" + "\t<td>";
		retRow += "\n" + "\t\t<input type='text' name='arrContentCd' value='" + water_content_cd + "' class='water' style='width:90%;' maxlength='20'  />";
		retRow += "\n" + "\t</td>";
		retRow += "\n" + "\t<td>";
		retRow += "\n" + "\t\t<input type='text' name='arrAmount' class='i_text onlyNumber' style='width:90%;' />";
		retRow += "\n" + "\t</td>";
		retRow += "\n" + "\t<td><label class='percentage'></label></td>";
		retRow += "\n" + "\t<td colspan=\"5\">";
		retRow += "\n" + "\t</td>";
		retRow += "\n" + "\t<td>";
		retRow += "\n" + "\t\t<a href=\"javascript:\" onclick=\"funcBom.changeRow(this, 'up')\"><img src=\""+ imagePath + "common/btn_up.gif\" /></a>";
		retRow += "\n" + "\t\t<a href=\"javascript:\" onclick=\"funcBom.changeRow(this, 'down')\"><img src=\""+ imagePath + "common/btn_down.gif\" /></a>";
		retRow += "\n" + "\t</td>";
		retRow += "\n" + "\t<td>";
		retRow += "\n" + "\t\t<a class='row_delete'><img src=\""+ imagePath + "btn/btn_del_small.gif\" /></a>";
		retRow += "\n" + "\t\t<a href='javascript:;' onclick='funcBom.fnAddContentChild(this)'><img src='" + imagePath + "btn/btn_add_bottom.gif' /></a>";
		retRow += "\n" + "\t</td>";
		retRow += "\n" + "</tr>";
		
		return retRow;
	},
	itemRow: function(flag) {
		
		var newRow = "";
		newRow += "\n" + "<tr>";
		newRow += "\n" + "\t<td class=\"left\">";
		
		switch (flag) {
			case "child":
				newRow += "\n" + "\t\t<img class=\"treeImg\" src=\"" + imagePath + "TREE/tree_joinbottom.gif\" style=\"margin-left:25px;\" />";
				newRow += "\n" + "\t\t<label class='materialNm'></label>";
				newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrMaterialNm\" />";
				break;
			case "content":
				newRow += "\n" + "\t\t<img class=\"treeImg\" src=\"" + imagePath + "TREE/tree_joinbottom.gif\" style=\"margin-left:25px;\" />";
				newRow += "\n" + "\t\t<input type=\"text\" name=\"arrMaterialNm\" />";
				newRow += "\n" + "\t\t<img class=\"btn_matnr_ref_pop\" src=\""+ imagePath + "icon/00cp_ic004.gif\" style=\"cursor:pointer;\" class=\"img\" >";
				break;
			case "row":
				newRow += "\n" + "\t\t<input type=\"text\" name=\"arrMaterialNm\" />";
				newRow += "\n" + "\t\t<img class=\"btn_matnr_ref_pop\" src=\""+ imagePath + "icon/00cp_ic004.gif\" style=\"cursor:pointer;\" class=\"img\" >";
				break;
		}
		
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrMaterialCd\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrContentNm\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrContentCd\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrItemIdx\" /> ";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrStep\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrVersion\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFlagFunctional\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFunctionalDesc\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrArea\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrAllergy\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFlagRadiation\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrResourceNm\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrFlag\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" id=\"arrFlagLimit\" />";
		newRow += "\n" + "\t\t<input type=\"hidden\" id=\"arrFoodAdditive\" />";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td class=\"left\">";
		newRow += "\n" + "\t<label class=\"resourceNm\"></label>";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		switch (flag) {
			case "content":
			case "row":
				newRow += "\n" + "\t\t<input type=\"text\" name=\"arrAmount\" class=\"onlyNumber\" style=\"width:90%\" />";
				break;
			case "child":
				newRow += "\n" + "\t\t<input type=\"hidden\" name=\"arrAmount\" />";
				break;
		}
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<label class=\"percentage\"></label>";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<label class=\"functionalYN\"></label>"
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<input type=\"text\" name=\"arrFuncAmount\" class=\"onlyNumber\" style=\"width:95%;display:none;\" /> ";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<label class=\"originArea\"></label>";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<label class=\"allergy\"></label>";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<label class=\"radiation\"></label>";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		newRow += "\n" + "\t\t<a href=\"javascript:\" onclick=\"funcBom.changeRow(this, 'up')\"><img src=\""+ imagePath + "common/btn_up.gif\" /></a>";
		newRow += "\n" + "\t\t<a href=\"javascript:\" onclick=\"funcBom.changeRow(this, 'down')\"><img src=\""+ imagePath + "common/btn_down.gif\" /></a>";
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "\t<td>";
		switch (flag) {
		case "content":
		case "row":
			newRow += "\n" + "\t\t<a class=\"row_delete\"><img src=\""+ imagePath + "btn/btn_del_small.gif\" /></a>";
			break;
		}
		
		newRow += "\n" + "\t</td>";
		newRow += "\n" + "</tr>";
		
		return newRow;
	},
	getParentIndex: function(obj) {
		var retVal = 0;
		var thisIndex = jQuery(".mixed_rows").find("tr").index(obj);
		thisIndex -= 1;
		var isLoop = false;
		
		while (!isLoop) {
			if (jQuery(".mixed_rows").find("tr").eq(thisIndex).find("input[name='arrStep']").val() == 0) {
				retVal = thisIndex;
				isLoop = true;
			} else {
				isLoop = false;
				thisIndex -= 1;
			}
		}
		return retVal;		
	},
	changeRow: function(obj, dir) {
		var thisRow = jQuery(obj).parent().parent();
		var tBody = jQuery(thisRow).parent();
		var thisIndex = jQuery(tBody).find("tr").index(thisRow);
		var clone = jQuery(thisRow).clone();
		
		if (dir == "up") {
			if (thisIndex == 0) {
				return;
			}
			var preIndex = thisIndex - 1;
			var preRow = jQuery(tBody).find("tr").eq(preIndex);
			jQuery(preRow).before(clone);
		} else {
			if ((thisIndex + 1) == jQuery(tBody).find("tr").length) {
				return;
			}
			var nextIndex = thisIndex + 1;
			var nextRow = jQuery(tBody).find("tr").eq(nextIndex);
			var clone = jQuery(thisRow).clone();
			jQuery(nextRow).after(clone);
		}
		
		jQuery(thisRow).remove();
	}
}