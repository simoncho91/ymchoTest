var originData = null;
var allergyData = null;
var arrOriControl = new Array();
var rowCnt = 0;
var imagePath = null;
var controlWidth = "";

var bfBom = {
	init : function(param) {
		
		jQuery("a").css("cursor", "pointer");
		imagePath = param.imgPath;
		 jQuery("#mixed_rows").find("tr").css("background-color", "#f3f3f3");
		//원산지 코드를 가지고 온다.
		cmAjax({
			url	: "/sm/cm/sm_cm_subcode_list_json.do?mstCode=ORIGIN_AREA"
			, type : "get"
			, dataType : "json"
			, isBlock : true
			, success : function (json) {
				if (json.status == "succ") {
					originData = json.data;
				}
				else {
					showMessageBox({message : json.message});
				}
			}
			, error : function() {
				showMessageBox({message : "Data not found"});
			}
		});
		
		if (parseInt(jQuery(window).width()) <= 1280) {
			controlWidth = "60%";
		} else {
			controlWidth = "70%"
		}
		
		//알레르기 관련 코드를 가지고 온다.
		cmAjax({
			url	: "/sm/cm/sm_cm_subcode_list_json.do?mstCode=BF_ALLERGY_TYPE"
			, type : "get"
			, dataType : "json"
			, isBlock : true
			, success : function (json) {
				if (json.status == "succ") {
					allergyData = json.data;
				}
				else {
					showMessageBox({message : json.message});
				}
			}
			, error : function() {
				showMessageBox({message : "Data not found"});
			}
		});
		if (jQuery("#mixed_actionflag").val() != "view") {
			this.calcAmount();
		}
		
		jQuery(document).on("keyup", "input[name*='mi_sResourceNm_']", function(e) {
			if (event.keyCode == 13) {
				jQuery(this).parent().find("a").click();
				event.keyCode = 0;
			}
		});
		
		jQuery(document).on("keyup", "input[name*='mi_sFoodTypeNm_']", function(e) {
			if (event.keyCode == 13) {
				jQuery(this).parent().find("a").click();
				event.keyCode = 0;
			}
		});
		
	},
	fnCotrolIdReName: function() {
		var arrlength = jQuery("#mixed_rows").find("tr").length;
		jQuery("#mixed_rows").find("tr").each(function(index, item) {
			var newId = index + 1;
			jQuery(item).find("[id*='mi_sMaterialNm_']").attr("name", "mi_sMaterialNm_" + index);
			jQuery(item).find("[id*='mi_iIdx_']").attr("name", "mi_iIdx_" + index);
			jQuery(item).find("[id*='mi_iPidx_']").attr("name", "mi_iPidx_" + index);
			jQuery(item).find("[id*='mi_iStep_']").attr("name", "mi_iStep_" + index);
			
			//아이디 바꾼다.
			//현재값
			var oldVal = jQuery(item).find("[id*='mi_iIdx_']").val();
			jQuery(item).find("[id*='mi_iIdx_']").val(newId);
			//현재값을 Parent로 쓰고 있는 아이디들을 모두 새로운 인덱스로 바꾼다.
			jQuery("#mixed_rows").find("[id*='mi_iPidx_'][value='" + oldVal + "']").each(function(index, item) {
				if (parseInt(jQuery(item).parent().find("[id*='mi_iStep_']").val()) > 1 && jQuery(item).parent().find("[id*='isModify_']").val() == "N") {
					jQuery(item).parent().find("[id*='isModify_']").val("Y");
					jQuery("#mixed_rows").find("[id*='mi_iPidx_'][value='" + oldVal + "']").val(newId);
				}
			});
			
			jQuery(item).find("[id*='mi_iVersion_']").attr("name", "mi_iVersion_" + index);
			jQuery(item).find("[id*='mi_sResourceNm_']").attr("name", "mi_sResourceNm_" + index);
			jQuery(item).find("[id*='mi_sResourceCd_']").attr("name", "mi_sResourceCd_" + index);
			jQuery(item).find("[id*='mi_percentage_']").attr("name", "mi_percentage_" + index);
			jQuery(item).find("[id*='mi_flag_functional_']").attr("name", "mi_flag_functional_" + index);
			jQuery(item).find("[id*='mi_functional_desc_']").attr("name", "mi_functional_desc_" + index);
			jQuery(item).find("[id*='mi_iAmount_']").attr("name", "mi_iAmount_" + index);
			jQuery(item).find("[id*='mi_arrOriCdArea_']").attr("name", "mi_arrOriCdArea_" + index);
			jQuery(item).find("[id*='mi_sAllergyCd_']").attr("name", "mi_sAllergyCd_" + index);
			jQuery(item).find("[id*='mi_sFlagRadiation_']").attr("name", "mi_sFlagRadiation_" + index);
			jQuery(item).find("[id*='mi_sFlagGMO_']").attr("name", "mi_sFlagGMO_" + index);
			jQuery(item).find("[id*='mi_sFoodTypeNm_']").attr("name", "mi_sFoodTypeNm_" + index);
			jQuery(item).find("[id*='mi_sFoodTypeCd_']").attr("name", "mi_sFoodTypeCd_" + index);
			
			jQuery(item).find("[id*='mi_sMaterialNm_']").attr("id", "mi_sMaterialNm_" + index);
			jQuery(item).find("[id*='mi_iIdx_']").attr("id", "mi_iIdx_" + index);
			jQuery(item).find("[id*='mi_iPidx_']").attr("id", "mi_iPidx_" + index);
			jQuery(item).find("[id*='mi_iStep_']").attr("id", "mi_iStep_" + index);
			jQuery(item).find("[id*='mi_iVersion_']").attr("id", "mi_iVersion_" + index);
			jQuery(item).find("[id*='mi_sResourceNm_']").attr("id", "mi_sResourceNm_" + index);
			jQuery(item).find("[id*='mi_sResourceCd_']").attr("id", "mi_sResourceCd_" + index);
			jQuery(item).find("[id*='mi_sFoodTypeNm_']").attr("id", "mi_sFoodTypeNm_" + index);
			jQuery(item).find("[id*='mi_sFoodTypeCd_']").attr("id", "mi_sFoodTypeCd_" + index);
			jQuery(item).find("[id*='mi_percentage_']").attr("id", "mi_percentage_" + index);
			jQuery(item).find("[id*='mi_flag_functional_']").attr("id", "mi_flag_functional_" + index);
			jQuery(item).find("[id*='mi_functional_desc_']").attr("id", "mi_functional_desc_" + index);
			jQuery(item).find("[id*='mi_iAmount_']").attr("id", "mi_iAmount_" + index);
			jQuery(item).find("[id*='mi_arrOriCdArea_']").attr("id", "mi_arrOriCdArea_" + index);
			jQuery(item).find("[id*='mi_sAllergyCd_']").attr("id", "mi_sAllergyCd_" + index);
			jQuery(item).find("[id*='mi_sFlagRadiation_']").attr("id", "mi_sFlagRadiation_" + index);
			jQuery(item).find("[id*='mi_sFlagGMO_']").attr("id", "mi_sFlagGMO_" + index);
		});
	    
	},
	fnMaterialSearch : function(obj) {
		var nmText = "#" + jQuery(obj).parent().find("input[name*=mi_sMaterialNm]").attr("id");
		var title;
		var arrPars = [];
		
		title	= "BeutyFood 원료";
		//arrPars.push("i_sKeyword=" + encodeURIComponent(j$(nmText).val()));
		arrPars.push("mi_sKeyword=" + encodeURI(jQuery(nmText).val()));
		
		arrPars.push("targetCd=" + jQuery(obj).parent().find("input[name*=mi_sMaterialCd]").attr("id"));
		arrPars.push("targetNm=" + jQuery(obj).parent().find("input[name*=mi_sMaterialNm]").attr("id"));
		//발주완료된 정보만 가지고 온다.
		arrPars.push("statusCd=MIS040");
		arrPars.push("i_sCallFunction=parent.bfBom.setMaterial");

		cmDialogOpen("zm_bf_material_list_pop", {
			title : title
	        , width : 700
	        , height: 600
	        , modal : true
	        , changeViewAutoSize : true 
	        , url:"/zm/bf/zm_bf_material_list_pop.do?" + arrPars.join("&")
		});
	},
	fnKeywordReset : function(obj) {
		j$(obj).parent().find("input[name*=mi_sMaterialCd]").val("");
		j$(obj).parent().find("input[name*=mi_sMaterialNm]").val("");
	},
	fnAddRow : function() {
		var fType = jQuery("#mixed_foodTypeCd").val();
//		if (jQuery("#mixed_rows").find("tr").length > rowCnt) 
//			rowCnt = jQuery("#mixed_rows").find("tr").length;
//		else 
//			rowCnt += 1;
		
		rowCnt = bfBom.getMaxIdx();
		
		var newRow = "";
		
		var itemIdx = bfBom.getMaxIdx();
		
		newRow += "<tr id=\"mixed_rows_" + rowCnt +"\">";
		newRow += "    <td style='text-align:left; padding-left:10px;'>"
		newRow += "        <input type=\"text\" id=\"mi_sMaterialNm_" + rowCnt + "\" name=\"mi_sMaterialNm_" + rowCnt + "\" />";
		newRow += "        <input type=\"hidden\" id=\"mi_iIdx_" + rowCnt + "\" name=\"mi_iIdx_" + rowCnt + "\" value=\"" + itemIdx + "\" /> \n";
		newRow += "        <input type=\"hidden\" id=\"mi_iPidx_" + rowCnt + "\" name=\"mi_iPidx_" + rowCnt + "\" value=\"" + itemIdx + "\" /> \n";
		newRow += "        <input type=\"hidden\" id=\"mi_iStep_" + rowCnt + "\" name=\"mi_iStep_" + rowCnt + "\" value=\"1\" />\n";
		newRow += "        <input type=\"hidden\" id=\"isModify_" + rowCnt + "\" name=\"isModify_" + rowCnt + "\" value=\"N\" />\n";
		newRow += "        <input type=\"hidden\" id=\"mi_iVersion_" + rowCnt + "\" name=\"mi_iVersion_" + rowCnt + "\" value=\"1\" />";
		newRow += "    <td>";
		newRow += "    		<input type=\"text\" id=\"mi_sResourceNm_" + rowCnt + "\" name=\"mi_sResourceNm_" + rowCnt + "\" style=\"width:" + controlWidth + ";\" onfocus=\"bfBom.fnSearchStdName(this)\"/>";
		newRow += "    		<input type=\"hidden\" id=\"mi_sResourceCd_" + rowCnt + "\" name=\"mi_sResourceCd_" + rowCnt + "\" />";
		newRow += "    		<a onclick=\"bfBom.fnSearchStdName(this)\"><img src='" + imagePath  + "/icon/00cp_ic004.gif' /></a>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "    		<input type=\"text\"  id=\"mi_sFoodTypeNm_" + rowCnt + "\" name=\"mi_sFoodTypeNm_" + rowCnt + "\" style=\"width:" + controlWidth + ";\" onfocus=\"bfBom.getFoodType(this)\" />";
		newRow += "    		<input type=\"hidden\" id=\"mi_sFoodTypeCd_" + rowCnt + "\" name=\"mi_sFoodTypeCd_" + rowCnt + "\" />";
		newRow += "    		<a onclick=\"bfBom.getFoodType(this)\"><img src='" + imagePath  + "/icon/00cp_ic004.gif' /></a>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <label id=\"mi_percentage_" + rowCnt + "\" name=\"mi_percentage_" + rowCnt + "\"></label>";
		newRow += "    </td>";
		newRow += "    <td>\n";
		newRow += "    		<input type=\"checkbox\" id=\"mi_flag_functional_" + rowCnt + "\" name=\"mi_flag_functional_" + rowCnt + "\" onclick=\"bfBom.chkDesc(this)\" />\n";
		newRow += "    		<input type=\"textbox\" id=\"mi_functional_desc_" + rowCnt + "\" name=\"mi_functional_desc_" + rowCnt + "\" style=\"display:none\" />\n";
		newRow += "    </td>\n";
		newRow += "    <td>";
		newRow += "        <input type=\"text\" id=\"mi_iAmount_" + rowCnt + "\" name=\"mi_iAmount_" + rowCnt + "\" class=\"onlyNumber\" onchange=\"bfBom.calcAmount()\" style=\"width:80%\" />";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <input type=\"text\" name=\"mi_sOriNmArea_" + rowCnt + "\" id=\"mi_sOriNmArea_" + rowCnt + "\" value=\"\" class=\"chooseBox\" style=\"width:74px;\" readonly=\"readonly\"/>";
		newRow += "        <ul id=\"oriBox_row" + rowCnt + "\" class=\"ul_chooseBox\">";
		var sCount = 0;
		jQuery(originData).each(function(index, item) {
			var obj = originData[index];
			newRow += "    			<li>";
			newRow += "    				<span class=\"chk-style\">";
			newRow += "    					<label for=\"mi_arrOriCdArea_" + rowCnt + "_" + index +  "\">";
			newRow += "    						<span><input type=\"checkbox\" name=\"mi_arrOriCdArea_"+ rowCnt + "\" id=\"mi_arrOriCdArea_" + rowCnt + "_" + index +  "\" value=\"" + obj.v_sub_code  + "\" alt=\"" + obj.v_sub_codenm  + "\" ></span>";
			newRow += obj.v_sub_codenm;
			newRow += "    					</label>";
			newRow += "    				</span>";
			newRow += "    			</li>";
		});
		
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "	   		<select id=\"mi_sAllergyCd_" + rowCnt + "\" name=\"mi_sAllergyCd_" + rowCnt + "\" onchange=\"bfBom.getAllergyList();\">";
		newRow += "             <option value=\"\">::선택::</option>";
		jQuery(allergyData).each(function(index, item) {
			var objAllergy = allergyData[index];
			newRow += "<option value=\"" + objAllergy.v_sub_code + "\" title=\"" + objAllergy.v_buffer3 + "\">" + objAllergy.v_sub_codenm + "</option>";
		});
		newRow += "			</select>";
		newRow += "    </td>";
		
		newRow += "    <td>";
		newRow += "        <select id=\"mi_sFlagRadiation_" + rowCnt + "\" name=\"mi_sFlagRadiation_" + rowCnt + "\">";
		newRow += "            <option value=\"Y\">Y</option>";
		newRow += "            <option value=\"N\">N</option>";
		newRow += "        </select>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <a onclick=\"bfBom.fnRowDelete(this)\"><img src=\"" + imagePath + "btn/btn_del_small.gif\" /></a>";
		newRow += "        <a onclick=\"bfBom.fnAddBottom(this)\"><img src=\"" + imagePath + "btn/btn_add_bottom.gif\" /></a>";
		newRow += "    </td>";
		newRow += "</tr>";
		
		jQuery("#mixed_rows").append(newRow);
		
		jQuery("#mixed_total_rowcount").val(jQuery("#mixed_rows").find("tr").length);
		jQuery("#mixed_total_rows").val(jQuery("#mixed_rows").find("tr").length);
						
		arrOriControl[rowCnt - 1] = new MultiChooseBox({
			inputText : "mi_sOriNmArea"
			, inputTextID : "mi_sOriNmArea_" + rowCnt
			, inputChk : "mi_arrOriCdArea_" + rowCnt
			, enableAll : false
			, boxId : "oriBox_row" + rowCnt});
		
		arrOriControl[rowCnt - 1].init();
	},
	fnRowDelete : function(obj) {
		//하위 아이템이 있을 경우 하위 아이템을 모두 지운다.
		var idx = jQuery(obj).parent().parent().find("input[name*='mi_iIdx_']").val();
		var childNode = jQuery("#mixed_rows").find("[name*='mi_iPidx_'][value='" + idx + "']");
		var parentIdx = jQuery(obj).parent().parent().find("input[name*='mi_iPidx_']").val();
		
		jQuery(childNode).parent().parent().remove();
		jQuery("#" + jQuery(obj).parent().parent().attr("id")).remove();
		jQuery("#mixed_total_rowcount").val(jQuery("#mixed_rows").find("tr").length);
		if (jQuery("#mixed_rows").find("tr").length == 0)
				rowCnt = 0;
		this.calcAmount();
		this.calcSubAmount(obj);
		this.getAllergyList();
	},
	calcAmount : 
		function () {
		var totalAmount = 0.0;
		var oldVal = jQuery("#mixed_total_amount").html();
		var T = Number('1e'+12);
		
		jQuery("input[name*='mi_iAmount']").each(function(index, item) {
			
			if(jQuery(item).parent().parent().find("input[name*='mi_iStep']").val() == "1") {
				if(jQuery(item).val() != "") {
					var amt = jQuery(item).val() == "" ? 0 : parseFloat(jQuery(item).val());
					totalAmount = Math.round((totalAmount + amt)*T)/T;
					
					if(totalAmount > 100) {
						alert("총 함량은 100%를 넘을 수 없습니다.");
						jQuery(item).val("0");
						totalAmount = oldVal;
					}
				}
			}
		});
		
		if (Math.round(totalAmount) == 100) {
			jQuery("#mixed_complete").val("Y");
		} else {
			jQuery("#mixed_complete").val("N");
		}
		jQuery("#mixed_total_amount").html(totalAmount);
		
	},
	setMaterial : function(targetCd, targetNm, cd, nm) {
		
		//이미 추가한적이 있는지 검사한다.
		var checkCnt =0; 
		jQuery("#mixed_rows").find("input[name*='mi_sMaterialCd_']").each(function(index, item) {
			if (jQuery(item).val() == cd) 
				checkCnt += 1;			
		});
		
		if (checkCnt > 0) {
			showMessageBox({message: "이미 추가된 원재료 입니다."});
			return;
		}
		
		//jQuery("#" + targetCd).val(cd);
		//jQuery("#" + targetNm).val(nm);
		
		var arrIndex = targetNm.split('_');
		var index = parseInt(arrIndex[arrIndex.length -1]);
		
	    var pRow = jQuery("#" + targetNm).parent().parent();
	    var targetArea = "mi_arrOriCdArea_" + index;
	    
	    cmAjax({
			url	: "/zm/bf/zm_bf_material_view_json.do?cd=" + cd
			, type : "get"
			, dataType : "json"
			, async:false
			, isBlock : true
			, success : function (json) {
				if (json.status == "succ") {
					var item = json.data;
					jQuery(pRow).find("#mi_sMaterialNm_" + index).val(item.v_material_nm);
					jQuery(pRow).find("#mi_sMaterialCd_" + index).val(item.v_material_cd);
					jQuery(pRow).find("#mi_sResourceNm_" + index).val(item.v_resource_nm);
					jQuery(pRow).find("#mi_sResourceCd_" + index).val(item.v_resource_cd);
					if (item.v_flag_functional == "Y")
						jQuery(pRow).find("#mi_flag_functional_" + index).prop("checked", true);
					else
						jQuery(pRow).find("#mi_flag_functional_" + index).prop("checked", false);
					
					jQuery(pRow).find("#mi_sAllergyCd_" + index).val(item.v_allergy_cd);
					jQuery(pRow).find("#mi_iVersion_" + index).val(item.n_version);
					//jQuery(pRow).find("#mi_iAmount_" + index).val(item.n_amount);
					jQuery(pRow).find("#mi_sFlagRadiation_" + index).val(item.v_flag_radiation);
					jQuery(pRow).find("#mi_sFlagGMO_" + index).val(item.v_flag_gmo);
					
					if (json.data.mixedList.length > 0) {
						for (var i = 0; i < json.data.mixedList.length; i++) {
							
							var sitem = json.data.mixedList[i];
							var thisObj = jQuery(pRow).find("#mi_sMaterialNm_" + index);
							bfBom.fnAddChildren(thisObj, sitem);					
							bfBom.calcAmount();
						}
					}
					
					
					//jQuery(pRow).find("#mi_sIsNew_" + index).prop("checked", false);
					
					//bfBom.getOriginArea(index, cd, item.n_version, item.n_item_idx);
				}
				else {
					showMessageBox({message : json.message});
				}
			}
			, error : function() {
				showMessageBox({message : "<fmt:message key='pms.materialInfo.servererror'/>"});
			}
		});
	},
	fnAddBottom : function(obj) {
		
		if (jQuery(obj).parent().parent().find("input[name*='mi_sMaterialNm']").val() == "") {
			showMessageBox({message:"원재료명을 입력해 주세요."});
			return;
		}
		
		//상위 인덱스
		var pIdx = jQuery(obj).parent().parent().find("input[name*='mi_iIdx_']").val();
		
		var pRow = "#" + jQuery(obj).parent().parent().attr("id");
		
		rowCnt = bfBom.getMaxIdx();
		
		var marginWidth = 0;
		
 		if (jQuery(obj).parent().parent().find(".treeImg").length > 0) {
 			marginWidth = parseInt(jQuery(obj).parent().parent().find(".treeImg").css("margin-left"));
 		}		

		marginWidth += 25;
		
		var step = 0;
		step = parseInt(jQuery(obj).parent().parent().find("input[name*='mi_iStep']").val()) + 1;
		
		if (step > 5) {
			alert("더이상 하위 추가할 수 없습니다.");
			return;
		}
 		
		var newRow = "";
		newRow += "<tr id=\"mixed_rows_" + rowCnt +"\">";
		newRow += "    <td style='text-align:left;'>"
		newRow += "		   <img class=\"treeImg\" src=\"" + imagePath + "TREE/tree_joinbottom.gif\" style=\"margin-left:" + marginWidth + "px;\" />";
		newRow += "        <input type=\"text\" id=\"mi_sMaterialNm_" + rowCnt + "\" name=\"mi_sMaterialNm_" + rowCnt + "\" />";
		newRow += "        <input type=\"hidden\" id=\"mi_iIdx_" + rowCnt + "\" name=\"mi_iIdx_" + rowCnt + "\" value=\"" + rowCnt + "\" /> \n";
		newRow += "        <input type=\"hidden\" id=\"mi_iPidx_" + rowCnt + "\" name=\"mi_iPidx_" + rowCnt + "\" value=\"" + pIdx + "\" /> \n";
		newRow += "        <input type=\"hidden\" id=\"mi_iStep_" + rowCnt + "\" name=\"mi_iStep_" + rowCnt + "\" value=\"" + step + "\" />\n";
		newRow += "        <input type=\"hidden\" id=\"isModify_" + rowCnt + "\" name=\"isModify_" + rowCnt + "\" value=\"N\" />\n";
		newRow += "        <input type=\"hidden\" id=\"mi_iVersion_" + rowCnt + "\" name=\"mi_iVersion_" + rowCnt + "\" value=\"1\" />";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += " 		<input type=\"text\" id=\"mi_sResourceNm_" + rowCnt + "\" name=\"mi_sResourceNm_" + rowCnt + "\" style=\"width:" + controlWidth + ";\" />";
		newRow += " 		<input type=\"hidden\" id=\"mi_sResourceCd_" + rowCnt + "\" name=\"mi_sResourceCd_" + rowCnt + "\" />";
		newRow += "    		<a onclick=\"bfBom.fnSearchStdName(this)\"><img src='" + imagePath  + "/icon/00cp_ic004.gif' /></a>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += " 		<input type=\"text\"  id=\"mi_sFoodTypeNm_" + rowCnt + "\" name=\"mi_sFoodTypeNm_" + rowCnt + "\" style=\"width:" + controlWidth + ";\" onfocus=\"bfBom.getFoodType(this)\"/>";
		newRow += "    		<input type=\"hidden\" id=\"mi_sFoodTypeCd_" + rowCnt + "\" name=\"mi_sFoodTypeCd_" + rowCnt + "\" />";
		newRow += "    		<a onclick=\"bfBom.getFoodType(this)\"><img src='" + imagePath  + "/icon/00cp_ic004.gif' /></a>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <label id=\"mi_percentage_" + rowCnt + "\" name=\"mi_percentage_" + rowCnt + "\"></label>";
		newRow += "    </td>";
		newRow += "    <td>\n";
		newRow += "    		<input type=\"checkbox\" id=\"mi_flag_functional_" + rowCnt + "\" name=\"mi_flag_functional_" + rowCnt + "\" onclick=\"bfBom.chkDesc(this)\" />\n";
		newRow += "    		<input type=\"textbox\" id=\"mi_functional_desc_" + rowCnt + "\" name=\"mi_functional_desc_" + rowCnt + "\" style=\"display:none\" />\n";
		newRow += "    </td>\n";
		newRow += "    <td>";
		newRow += "        <input type=\"text\" id=\"mi_iAmount_" + rowCnt + "\" name=\"mi_iAmount_" + rowCnt + "\" class=\"" + jQuery(obj).parent().parent().attr("id") + " onlyNumber\" style=\"width:" + controlWidth + ";\" onchange=\"bfBom.calcSubAmount(this)\" />";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <input type=\"text\" name=\"mi_sOriNmArea_" + rowCnt + "\" id=\"mi_sOriNmArea_" + rowCnt + "\" value=\"\" class=\"chooseBox\" style=\"width:74px;\" readonly=\"readonly\"/>";
		newRow += "        <ul id=\"oriBox_row" + rowCnt + "\" class=\"ul_chooseBox\">";
		jQuery(originData).each(function(index, item) {
			var obj = originData[index];
			newRow += "    			<li>";
			newRow += "    				<span class=\"chk-style\">";
			newRow += "    					<label for=\"mi_arrOriCdArea_" + rowCnt + "_" + index +  "\">";
			newRow += "    						<span><input type=\"checkbox\" name=\"mi_arrOriCdArea_" + rowCnt + "\" id=\"mi_arrOriCdArea_" + rowCnt + "_" + index +  "\" value=\"" + obj.v_sub_code  + "\" alt=\"" + obj.v_sub_codenm  + "\" style=\"width:74px;\" ></span>";
			newRow += obj.v_sub_codenm;
			newRow += "    					</label>";
			newRow += "    				</span>";
			newRow += "    			</li>";
		});
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "	   		<select id=\"mi_sAllergyCd_" + rowCnt + "\" name=\"mi_sAllergyCd_" + rowCnt + "\" onchange=\"bfBom.getAllergyList();\">";
		newRow += "             <option value=\"\">::선택::</option>";
		jQuery(allergyData).each(function(index, item) {
			var objAllergy = allergyData[index];
			newRow += "<option value=\"" + objAllergy.v_sub_code + "\" title=\"" + objAllergy.v_buffer3 + "\">" + objAllergy.v_sub_codenm + "</option>";
		});
		newRow += "			</select>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <select id=\"mi_sFlagRadiation_" + rowCnt + "\" name=\"mi_sFlagRadiation_" + rowCnt + "\">";
		newRow += "            <option value=\"Y\">Y</option>";
		newRow += "            <option value=\"N\">N</option>";
		newRow += "        </select>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <a onclick=\"bfBom.fnRowDelete(this)\"><img src=\"" + imagePath + "btn/btn_del_small.gif\" /></a>";
		newRow += "        <a onclick=\"bfBom.fnAddBottom(this)\"><img src=\"" + imagePath + "btn/btn_add_bottom.gif\" /></a>";
		newRow += "    </td>";
		newRow += "</tr>";
		jQuery(newRow).insertAfter(pRow);
		jQuery("#mixed_total_rowcount").val(jQuery("#mixed_rows").find("tr").length);
		jQuery("#mixed_total_rows").val(jQuery("#mixed_rows").find("tr").length);
		
		
		arrOriControl[rowCnt - 1] = new MultiChooseBox({
			inputText : "mi_sOriNmArea"
			, inputTextID : "mi_sOriNmArea_" + rowCnt
			, inputChk : "mi_arrOriCdArea_" + rowCnt
			, enableAll : false
			, boxId : "oriBox_row" + rowCnt});
		
		arrOriControl[rowCnt - 1].init();
	},
	fnAddChildren : function(obj, vItem) {
		
		if (jQuery(obj).parent().parent().find("input[name*='mi_sMaterialNm']").val() == "") {
			showMessageBox({message:"원재료명을 입력해 주세요."});
			return;
		}
		
		//상위 인덱스
		var pIdx = jQuery(obj).parent().parent().find("input[name*='mi_iPidx_']").val();
		var uMaterialCd;
		
		uMaterialCd = jQuery(obj).parent().parent().find("input[name*='mi_iIdx']").val();
		
		var parentIdx = jQuery(obj).parent().parent().index();
		
		if (jQuery("#mixed_rows").find("tr").length > rowCnt) 
			rowCnt = jQuery("#mixed_rows").find("tr").length;
		else 
			rowCnt += 1;
		
		var pRow = "#" + jQuery(obj).parent().parent().attr("id");
		
		var marginWidth = 0;
		
 		if (jQuery(obj).parent().parent().find(".treeImg").length > 0) {
 			marginWidth = parseInt(jQuery(obj).parent().parent().find(".treeImg").css("margin-left"));
 		}		

 		marginWidth += 25;
		
 		var step = 0;
		step = parseInt(jQuery(obj).parent().parent().find("input[name*='mi_iStep']").val()) + 1;
		
 		
		var newRow = "";
		newRow += "<tr id=\"mixed_rows_" + rowCnt +"\">";
		newRow += "    <td>"
		newRow += "		   <img class=\"treeImg\" src=\"" + imagePath + "TREE/tree_joinbottom.gif\" style=\"margin-left:" + marginWidth + "px;\" />";
		newRow += "        <input type=\"text\" id=\"mi_sMaterialNm_" + rowCnt + "\" name=\"mi_sMaterialNm_" + rowCnt + "\" value=\"" + vItem.v_material_nm + "\" />";
		newRow += "        <input type=\"hidden\" id=\"mi_iIdx_" + rowCnt + "\" name=\"mi_iIdx_" + rowCnt  + "\" value=\"" + vItem.n_item_idx + "\" /> \n";
		newRow += "        <input type=\"hidden\" id=\"mi_iPidx_" + rowCnt + "\" name=\"mi_iPidx_" + rowCnt + "\" value=\"" + vItem.n_parent_idx + "\" /> \n";
		newRow += "        <input type=\"hidden\" id=\"mi_iStep_" + rowCnt + "\" name=\"mi_iStep_" + rowCnt + "\" value=\"" + vItem.n_step + "\" />\n";
		newRow += "        <input type=\"hidden\" id=\"mi_iVersion_" + rowCnt + "\" name=\"mi_iVersion_" + vItem.n_version + "\" value=\"1\" />";
		newRow += "    </td>";
		newRow += "    <td class=\"left\">";
		newRow += "  		<input type=\"text\" id=\"mi_sResourceNm_" + rowCnt + "\" name=\"mi_sResourceNm_" + rowCnt + "\" value=\"" + vItem.v_resource_nm + "\" style=\"width:" + controlWidth + ";\" readonly />";
		newRow += "  		<input type=\"hidden\" id=\"mi_sResourceCd_" + rowCnt + "\" name=\"mi_sResourceCd_" + rowCnt + "\" value=\"" + vItem.v_resource_cd + "\" />";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <label id=\"mi_percentage_" + rowCnt + "\" name=\"mi_percentage_" + rowCnt + "\"></label>";
		newRow += "    </td>";
		newRow += "    <td>\n";
		newRow += "    		<input type=\"checkbox\" id=\"mi_flag_functional_" + rowCnt + "\" name=\"mi_flag_functional_" + rowCnt + "\" onclick=\"bfBom.chkDesc(this)\"";
		if (vItem.v_flag_functional == "Y") newRow += " checked";
		newRow += "/>\n";
		newRow += "    		<input type=\"textbox\" id=\"mi_functional_desc_" + rowCnt + "\" name=\"mi_functional_desc_" + rowCnt + "\" value=\"" + vItem.v_functional_desc + "\"";
		if (vItem.v_flag_functional == "N") newRow += "style=\"display:none\" ";
		newRow += "/>\n";
		newRow += "    </td>\n";
		newRow += "    <td>";
		newRow += "        <input type=\"text\" id=\"mi_iAmount_" + rowCnt + "\" name=\"mi_iAmount_" + rowCnt + "\" class=\"" + jQuery(obj).parent().parent().attr("id") + " onlyNumber\" value=\"" + vItem.n_amount + "\" onchange=\"bfBom.calcSubAmount(this)\" />";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <input type=\"text\" name=\"mi_sOriNmArea_" + rowCnt + "\" id=\"mi_sOriNmArea_" + rowCnt + "\" value=\"\" class=\"chooseBox\" style=\"width:74px;\" readonly=\"readonly\"/>";
		newRow += "        <ul id=\"oriBox_row" + rowCnt + "\" class=\"ul_chooseBox\">";
		jQuery(originData).each(function(index, item) {
			var obj = originData[index];
			newRow += "    			<li>";
			newRow += "    				<span class=\"chk-style\">";
			newRow += "    					<label for=\"mi_arrOriCdArea_" + rowCnt + "_" + index +  "\">";
			newRow += "    						<span><input type=\"checkbox\" name=\"mi_arrOriCdArea_" + rowCnt + "\" id=\"mi_arrOriCdArea_" + rowCnt + "_" + index +  "\" value=\"" + obj.v_sub_code  + "\" alt=\"" + obj.v_sub_codenm  + "\" ></span>";
			newRow += obj.v_sub_codenm;
			newRow += "    					</label>";
			newRow += "    				</span>";
			newRow += "    			</li>";
		});
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <select id=\"mi_sAllergyCd_" + rowCnt + "\" name=\"mi_sAllergyCd_" + rowCnt + "\">";
		newRow += "        <option value=\"\">::선택::</option>";
		jQuery(allergyData).each(function(index, item) {
			var obj = allergyData[index];
			if (vItem.v_allergy_cd == obj.v_sub_code)
				newRow += "<option value=\"" + obj.v_sub_code  + "\" selected>" + obj.v_sub_codenm  + "</option>";
			else
				newRow += "<option value=\"" + obj.v_sub_code  + "\">" + obj.v_sub_codenm  + "</option>";
		});
		newRow += "        </select>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <select id=\"mi_sFlagRadiation_" + rowCnt + "\" name=\"mi_sFlagRadiation_" + rowCnt + "\">";
		
		newRow += "            <option value=\"Y\"";
	    if (vItem.v_flag_radiation == "Y") newRow += " selected"; 
	    newRow += ">Y</option>";
		newRow += "            <option value=\"N\"";
		if (vItem.v_flag_radiation == "N") newRow += " selected";
		newRow += ">N</option>";
		newRow += "        </select>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <select id=\"mi_sFlagGMO_" + rowCnt + "\" name=\"mi_sFlagGMO_" + rowCnt + "\">";
		newRow += "            <option value=\"Y\"";
		if (vItem.v_flag_gmo == "Y") newRow += " selected";
		newRow += ">Y</option>";
		newRow += "            <option value=\"N\"";
		if (vItem.v_flag_gmo == "N") newRow += " selected";
		newRow += ">N</option>";
		newRow += "        </select>";
		newRow += "    </td>";
		newRow += "    <td>";
		newRow += "        <a onclick=\"bfBom.fnRowDelete(this)\"><img src=\"" + imagePath + "btn/btn_del_small.gif\" /></a>";
		newRow += "        <a onclick=\"bfBom.fnAddBottom(this)\"><img src=\"" + imagePath + "btn/btn_add_bottom.gif\" /></a>";
		newRow += "    </td>";
		newRow += "</tr>";
		jQuery(newRow).insertAfter(pRow);
		jQuery("#mixed_total_rowcount").val(jQuery("#mixed_rows").find("tr").length);
		jQuery("#mixed_total_rows").val(jQuery("#mixed_rows").find("tr").length);
		
		
		arrOriControl[rowCnt - 1] = new MultiChooseBox({
			inputText : "mi_sOriNmArea"
			, inputTextID : "mi_sOriNmArea_" + rowCnt
			, inputChk : "mi_arrOriCdArea_" + rowCnt
			, enableAll : false
			, boxId : "oriBox_row" + rowCnt});
		
		arrOriControl[rowCnt - 1].init();
		bfBom.calcSubAmount(jQuery("#mi_iAmount_" + rowCnt));
	},
	calcSubAmount : function(obj) {
		// 상위 열명을 가지고 온다.
		var totalAmount = 0;
		//모드에 따라 바꿔준다.
		var flag = jQuery("#mixed_actionflag").val();
		var T = Number('1e'+12);
		
		var parentIdx = jQuery(obj).parent().parent().find("[name*='mi_iPidx']").val();
		var thisStep = jQuery(obj).parent().parent().find("[name*='mi_iStep_']").val();
		var thisVal = jQuery(obj).val();
		
		jQuery("#mixed_rows").find("[name*='mi_iPidx_'][value='" + parentIdx + "']").each(function (index, item) {
			if (jQuery(item).parent().find("[name*='mi_iStep']").val() == thisStep) {
				var amt = jQuery(item).parent().parent().find("input[name*='mi_iAmount_']").val() == "" ? 0 : parseFloat(jQuery(item).parent().parent().find("input[name*='mi_iAmount_']").val());
				totalAmount = Math.round((totalAmount + amt)*T)/T;
			}
		});	

		var oldVal = jQuery("#mixed_rows").find("[name*='mi_iIdx_'][value='" + parentIdx + "']").parent().parent().find("[id*='mi_percentage_']").html();
		jQuery("#mixed_rows").find("[name*='mi_iIdx_'][value='" + parentIdx + "']").parent().parent().find("[id*='mi_percentage_']").html(totalAmount);
		
		if (Math.round(totalAmount) > 100) {
			jQuery("#mixed_complete").val("N");
			showMessageBox({message : "총 함량은 100%를 넘을 수 없습니다."});
			jQuery("#mixed_rows").find("[name*='mi_iIdx_'][value='" + parentIdx + "']").parent().parent().find("[id*='mi_percentage_']").html(oldVal);
			jQuery(obj).val("0");
			bfBom.calcSubAmount(obj);
		} else if (totalAmount == 0) {
			jQuery("#mixed_rows").find("[name*='mi_iIdx_'][value='" + parentIdx + "']").parent().parent().find("[id*='mi_percentage_']").html("");
		} else {jQuery("#mixed_complete").val("Y");
		}
		
	},
	getOriginArea : function(index, cd, ver, idx) {
		//원산지 셋팅.. 불러오고 셋팅할 수 없다면.. 다시 만들면 된다.
		var retObj = new Object();
		var area = jQuery("#oriBox_row" + index);
		var textBox = jQuery("#mi_sOriNmArea_" + index);
		
		//기존 선택 사항을 클리어
		jQuery(area).find("input[type=checkbox]").prop("checked", false);
		jQuery(area).find("label").removeClass("on");
		jQuery(textBox).val("선택");
		
		cmAjax({
			url	: "/zm/bf/zm_bf_material_origin_area_json.do?cd=" + cd + "&ver=" + ver + "&idx=" + idx
			, type : "get"
			, dataType : "json"
			, async : false
			, isBlock : true
			, success : function (json) {
				if (json.status == "succ") {
					retObj = json.data;
					if (retObj.length > 0) {
						for (i = 0; i < retObj.length; i++) {
							var areaNm = retObj[i]["v_area_nm"];
							var areaCd = retObj[i]["v_area_cd"];
							
							var checkbox = jQuery(area).find("input[value='" + areaCd + "']");
							jQuery(checkbox).prop("checked", true);
							jQuery(checkbox).parent().parent().addClass("on");
							
						}
						
					
						//한개일 경우
						if (retObj.length == 1) {
							jQuery(textBox).val(retObj[0]["v_area_nm"]);
						} else {
							jQuery(textBox).val(retObj.length + "개 선택");
						}
					}
					
//					jQuery(inputText).parent().html(areaText);
				}
				else {
					showMessageBox({message : json.message});
				}
			}
			, error : function() {
				showMessageBox({message : "<fmt:message key='pms.materialInfo.servererror'/>"});
			}
		});
		
	},
	fnSearchStdName : function(obj) {
		var arrPars = [];
		var nmText = jQuery(obj).parent().parent().find("input[name*='mi_sResourceNm']"); 
		var nmVal = jQuery(obj).parent().parent().find("input[name*='mi_sResourceCd']");
		title	= "표준원재료명 검색";
		arrPars.push("keyword=" + encodeURI(jQuery(nmText).val()));
		arrPars.push("targetCd=" + jQuery(nmVal).attr("id"));
		arrPars.push("targetNm=" + jQuery(nmText).attr("id"));
		arrPars.push("i_sCallFunction=parent.bfBom.setStdName");

		cmDialogOpen("zm_bf_mi_std_name_list_pop", {
			title : title
	        , width : 700
	        , height: 600
	        , modal : true
	        , changeViewAutoSize : true 
	        , url:"/zm/bf/zm_bf_mi_std_name_list_pop.do?" + arrPars.join("&")
		});
	},
	setStdName : function (targetCd, targetNm, cd, nm) {
		
		//이미 추가한적이 있는지 검사한다.
//		var checkCnt = jQuery("#mixed_rows").find("input[value='" + cd + "']").length;
//		
//		if (checkCnt > 0) {
//			showMessageBox({message: "이미 추가된 원재료 입니다."});
//			return;
//		}

		jQuery("#" + targetCd).val(cd);
		jQuery("#" + targetNm).val(nm);
	},
	validate : function() {
		var rowCount = parseInt(jQuery("#mixed_rows").find("tr").length);
		//모드에 따라 바꿔준다.
		var flag = jQuery("#mixed_actionflag").val();
		//구성 함량 체크
		var maxStep = 1;
		//return value
		var retVal = true;
		
		//컨트롤 이름을 정렬해 준다.
		bfBom.fnCotrolIdReName();
		
		//아무것도 입력하지 않았다면 그대로 True를 리턴한다.
		if (jQuery("#mixed_rows").find("tr").length <= 1) {
			if (jQuery("#mixed_rows").find("tr").length == 0) {
				retVal = true;
				return true;
			} else {
				if (jQuery("#mixed_rows").find("tr:eq(0)").find("input[name*='mi_sMaterialNm']").val() == "") {
					retVal = true;
					return true;
				}
			}
		}
		
		if (rowCount > 0) {
			//필수 입력값 체크
			for(var i = 0; i < rowCount; i++) {
				//이름입력 체크
				if (jQuery("input[name=mi_sMaterialNm_" + i).length > 0) {
					if (jQuery("#mi_sMaterialNm_" + i).val() == "") {
						showMessageBox({message: "원재료명1을 입력해 주세요."});
						jQuery("#mi_sMaterialNm_" + i).focus();
						retVal =  false;
						return false;
					}
				}
				
				if(jQuery("#mi_sResourceCd_" + i).val() == "") {
					showMessageBox({message: "표준원재료명을 입력해 주세요."});
					jQuery("#mi_sResourceNm_" + i).focus();
					retVal =  false;
					return false;
				}
				
				if(jQuery("#mi_iAmount_" + i).val() == "") {
					showMessageBox({message: "함량을 입력해 주세요."});
					jQuery("#mi_sResourceNm_" + i).focus();
					retVal =  false;
					return false;
				}
			}
		}
		
		//배합비율 체크
		var mixedChk = 0;
		jQuery("label[id*='mi_percentage_']").each(function(index, item) {
			if (jQuery(item).html() != "" && jQuery(item).html() != "100") {
				mixedChk += 1;
			}
		});
		
		if (mixedChk != 0) {
			showMessageBox({message : "배합비율이 정확하지 않습니다. 다시 확인해 주세요."});
			retVal =  false;
		}
		
		
		
		//총 구성비율 체크
		if (parseInt(jQuery("#mixed_total_amount").html()) != 100) {
			var confirmBox = confirm("구성비율이 정확하지 않습니다. 저장하시겠습니까?");
			if (!confirmBox) {
				retVal = false;
			}
		}
		
		return retVal;
	},
	chkDesc:function(obj) {
		if (jQuery(obj).prop("checked")) {
			jQuery(obj).parent().find("input[name*='mi_functional_desc_']").css("display", "block");
		} else {
			jQuery(obj).parent().find("input[name*='mi_functional_desc_']").css("display", "none");
		}
	},
	getMaxIdx:function() {
		var maxIdx = 1;
		jQuery("#mixed_rows").find("[id*='mi_iIdx']").each(function(index, item) {
			if (maxIdx < parseInt(jQuery(item).val()))
					maxIdx = parseInt(jQuery(item).val());
		});
		
		maxIdx += 1;
		return maxIdx;
	},
	getFoodType: function(obj) {
		var arrPars = [];
		var targetNm = jQuery(obj).parent().parent().find("[name*='mi_sFoodTypeNm_']").attr("id");
		var targetCd = jQuery(obj).parent().parent().find("[name*='mi_sFoodTypeCd_']").attr("id");

		title	= "식품유형 검색";
		arrPars.push("keyword=" + encodeURI(jQuery("#" + targetNm).val()));
		
		arrPars.push("targetCd=" + targetCd);
		arrPars.push("targetNm=" + targetNm);
		arrPars.push("i_sCallFunction=parent.bfBom.setVal");

		cmDialogOpen("zm_bf_food_type_list_pop", {
			title : title
	        , width : 700
	        , height: 600
	        , modal : true
	        , changeViewAutoSize : true 
	        , url:"/zm/bf/zm_bf_food_type_list_pop.do?" + arrPars.join("&")
		});
	},
	setVal: function(targetCd, targetNm, cd, nm) {
		jQuery("#" + targetCd).val(cd);
		jQuery("#" + targetNm).val(nm);
	},
	exportExcel: function(materialCd, version) {
		//엑셀출력
		fncExcelDownload("/zm/bf/zm_bf_material_mixed_info_excel.do");
	},
	getAllergyList: function() {
		var allergyList = [];
		var actionFlag = jQuery("#mixed_actionflag").val();
		
		if (actionFlag != "view") {
			jQuery("select[name*='mi_sAllergyCd_']").each(function(index, item) {
				if (jQuery(item).find(":selected").val() != "") {
					if (jQuery.inArray(jQuery(item).find(":selected").html(), allergyList) < 0) {
						allergyList.push(jQuery(item).find(":selected").html());
					}
				}
			});
			
			if (jQuery("#i_sAllergyCd").length > 0) {
				jQuery("#i_sAllergyCd").hide();
			}
			jQuery("#lbAllergyCd").html(bfBom.arrayToString(allergyList));
		} else {
			jQuery("#mixed_rows").find(".allergy").each(function(index, item) {
				if (jQuery(item).html() != "") {
					if (jQuery.inArray(jQuery(item).html(), allergyList) < 0) {
						allergyList.push(jQuery(item).html());
					}
				}
			});
		}
		
		if (jQuery("#i_sAllergyCd").length > 0) {
			if (allergyList.length > 0) {
				jQuery("#i_sAllergyCd").hide();
			} else {
				jQuery("#i_sAllergyCd").show();
			}
		} 
		return allergyList;
	},
	arrayToString: function(arr) {
		var retStr = "";
		for (var i=0; i < arr.length; i++) {
			if ((i + 1) != arr.length) {
				retStr += arr[i] + ", ";
			} else {
				retStr += arr[i];
			}
			
		}
		retStr = retStr.replace(/(^\s*)|(\s*$)/gi, "");
		if (retStr.substr((retStr.length -1), 1) == ",") {
			retStr = retStr.substr(0, retStr.length - 1);
		}
		return retStr;
	},
	parseDouble: function(value){
		var sInPerAdd		= 0;
		var T2=Number('1e'+10);
		sInPerAdd = Math.round((value)*T2)/T2;
		return sInPerAdd;
		
	}

};