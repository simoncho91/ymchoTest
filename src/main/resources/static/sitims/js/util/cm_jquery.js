
/**
 * Enter 이벤트 추가
 * 
 * @param el
 * @param func
 */
function setEnterKey(target, functionOrElement) {
	
	if ( typeof target != "object" || (typeof functionOrElement != "function" && typeof functionOrElement != "object") ) {
		return;
	}
	
	target.keydown(function (event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			
			if (typeof functionOrElement == "function") {
				functionOrElement();
			}
			else {
				functionOrElement.click();
			}
		}
	});
}

// 첨부파일 수 체크
function getSWFUploadFileCount(uploadId) {
	
	var wrap = jQuery(".progressWrapper", "#fsUploadProgress_" + uploadId);
	var size = wrap.size();
	var cnt = 0;
	for (var i = 0; i < size; i++) {
		if (wrap.eq(i).css("display") != "none") {
			cnt++;
		}
	}
	return cnt;
}

function findValue(li) {
	if( li == null ) return alert("No match!");

	// if coming from an AJAX call, let's use the CityId as the value
	if( !!li.extra ) var sValue = li.extra[0];

	// otherwise, let's just display the value in the text box
	else var sValue = li.selectValue;

	alert("The value you selected was: " + sValue);
}

function selectItem(li) {
	findValue(li);
}

function formatItem(row) {
	return row[0] + " (id: " + row[1] + ")";
}

function addEventAutocomplete() {
	jQuery.ui.autocomplete.prototype._renderItem = function( ul, item) {

	//	var re = new RegExp("^" + this.term, "i") ;
		var re = new RegExp(this.term, "gi") ;
		var t = item.label.replace(re,"<span style='font-weight:bold;color:orange;'>" + this.term + "</span>");
		return jQuery( "<li></li>" )
		    .data( "item.autocomplete", item )
		    .append( "<a>" + t + "</a>" )
		    .appendTo( ul );
	};
}




// rowspan 합치기
function addRowspanEvent(target) {
	if (target == undefined) {
		target = jQuery(".rowspan");
	}
	var size = target.size();
	var arr = [];
	var arr_cls = [];
	var cls = "";
	
	for (var i = 0; i < size; i++) {
		arr_cls = target.eq(i).attr("class").split(" ");
		cls = "";
		
		for (var j = 0; j < arr_cls.length; j++) {
			if (jQuery.trim(arr_cls[j]) == "") {
				continue;
			}
			else if (arr_cls[j].indexOf("rowspan_") == 0) {
				cls = arr_cls[j];
				break;
			}
		}
		
		if (cls == "" || arr.join(";").indexOf(cls + ";") > -1) {
			continue;
		}
		
		arr.push(cls);
		arr_td = jQuery("." + cls);
		
		if (arr_td.size() <= 1) {
			continue;
		}
		
		arr_td.each(function(n) {
			if (n == 0) {
				jQuery(this).attr("rowspan", arr_td.size());
			}
			else {
				jQuery(this).remove();
			}
		});
	}
}




function addInputMessageEvent(i_label) {
	if (i_label == undefined) {
		i_label = jQuery('.table_view_2013 .i_label, .table_search .i_label, .table_view .i_label');
	}
    var i_text = i_label.next('.i_label_text');
    i_label
    	.css('position','absolute')
    	.click(function(event) {
    		jQuery(this).next(".i_label_text").focus();
    	});
    
    i_text
    	.focus(function(){
    		jQuery(this).prev('.i_label').css('visibility','hidden');
    	}).blur(function(){
	    	if(jQuery(this).val() == ''){
	    		jQuery(this).prev('.i_label').css('visibility','visible');
	        } else {
	        	jQuery(this).prev('.i_label').css('visibility','hidden');        	
	        }
	    }).change(function(){
	        if(jQuery(this).val() == ''){
	        	jQuery(this).prev('.i_label').css('visibility','visible');
	        } else {
	        	jQuery(this).prev('.i_label').css('visibility','hidden');   
	        }
	    });
    
    try {
    	i_text.blur();
    } catch (e){}
    
    var i_textarea_label = jQuery('.i_textarea_label');
    var i_textarea_text = i_textarea_label.next('.i_textarea_label_text');
    
    i_textarea_label
	    .css('position','absolute')
	    .click(function(event) {
	    	jQuery(this).next(".i_textarea_label_text").focus();
	    });
    
    i_textarea_text
	    .focus(function(){
	    	jQuery(this).prev('.i_textarea_label').css('visibility','hidden');    
	    }).blur(function(){
	    	if(jQuery(this).val() == ''){
	    		jQuery(this).prev('.i_textarea_label').css('visibility','visible');
	    	} else {
	    		jQuery(this).prev('.i_textarea_label').css('visibility','hidden');        	
	    	}
	    }).change(function(){
	    	if(jQuery(this).val() == ''){
	    		jQuery(this).prev('.i_textarea_label').css('visibility','visible');
	    	} else {
	    		jQuery(this).prev('.i_textarea_label').css('visibility','hidden');   
	    	}
	    });
    
    try {
    	i_textarea_text.blur();
    } catch (e){}
    
}

var chk_radio = {
	checkBox : function(event) {
		if(jQuery(this).find("input").attr("id").indexOf("chk") != -1){
			return;
		}	
		var chkBoxOn = jQuery(this).find("input").is("input:checked");
		if (chkBoxOn == true){
			jQuery(this).addClass("on");
		} else {
			jQuery(this).removeClass("on");
		}
	}
	, inputRadio : function (event) {
		var input = jQuery(this).find("input");
		var chkOn = input.is(":checked");
		var hidden = input.attr("disabled");
		if (hidden != "disable"){
			if (chkOn == true){
				jQuery(this).parent().find("label").removeClass("on");
				jQuery(this).addClass("on");	
			}
			else {
				jQuery(this).parent().find("label").removeClass("on");
				jQuery(this).removeClass("on");
			}
		}
		
		//20200803 해당부분 refresh_rdbox undefined error
		//jQuery("input[name='"+ input.attr("name") + "']").refresh_rdbox();
	}
};

jQuery.fn.refresh_checkbox = function(e){
	var commonClass = 'chk-style';
	return this.each(function() {
		var chk = jQuery(this).parents('.' + commonClass);
		var chkBoxOn = jQuery(this).is("input:checked");
		var chkBoxDis = jQuery(this).prop("disabled");
		
		if (chkBoxOn) {
			chk.find("label").addClass("on");
		}
		else {
			chk.find("label").removeClass("on");
		}
		
		if (chkBoxDis){
			chk.find("label").addClass("inp-disabled");
		}else{
			chk.find("label").removeClass("inp-disabled");
		}
	});
};

jQuery.fn.refresh_rdbox = function(e){
	var commonClass = 'rd-style';
	return this.each(function() {
		var chk = jQuery(this).parents('.' + commonClass);
		var chkBoxOn = jQuery(this).is("input:checked");
		
		if (chkBoxOn) {
			chk.find("label").addClass("on");
		}
		else {
			chk.find("label").removeClass("on");
		}
	});
};

function addInputChkRadioEvent(target) {
	
	if (target == undefined) {
		target = jQuery(".chk-style, .rd-style").not(".not_select");
	}
	
	var chk_label = jQuery("label", target.filter(".chk-style"));
	var rd_label = jQuery("label", target.filter(".rd-style"));
	
	chk_label.click(chk_radio.checkBox);
	rd_label.click(chk_radio.inputRadio);
	
	chk_label.each(function() {
		var chkBoxOn = jQuery(this).find("input").is("input:checked");
		if (chkBoxOn == true){
			jQuery(this).addClass("on");
		} else {
			jQuery(this).removeClass("on");
		}
	});
	
	chk_label.fadeIn(function(){
		var chkBoxDis = jQuery(this).find("input").attr('disabled');
		if(chkBoxDis == 'disabled'){
			jQuery(this).addClass('inp-disabled');
		}else{
			jQuery(this).removeClass('inp-disabled');
		}
	});
	
	rd_label.each(function() {
		var chkBoxOn = jQuery(this).find("input").is("input:checked");
		if (chkBoxOn == true){
			jQuery(this).addClass("on");
		} else {
			jQuery(this).removeClass("on");
		}
	});
	
}

// select box 변경
function changeSelectbox(target) {
	if (target == undefined) {
		target = jQuery("select").not("*[name='i_iPageSize']");
	}
	target.selectbox();
}

//
function MultiChooseBox ( p_opt ) {
	var _defaults = {
		inputText : ""
		, inputAllChk : ""
		, inputChk : ""
		, boxId : ""
		, inputTextID : ""
		, enableAll : true
		, disabled : false
	};
	this.opt	= jQuery.extend(_defaults, p_opt);
}
MultiChooseBox.prototype = {
	init : function() {
		var multiChooseBox = this;
		var opt = this.opt;
		var target;
		
		if(opt.inputTextID.length == 0) 
			target = jQuery("input[name='" + opt.inputText +"']");
		else
			target = jQuery("#" + opt.inputTextID);
		
		/**=========================================================
			Data / Writer : 2015-03-27 김기윤
			Description	: 
				cmDialog와 충돌 문제 발생 (스크립트 오류는 없음)
		 		show를 toggle로 변경
		==========================================================*/
		jQuery(target).click(function (event) {
			jQuery("#" + opt.boxId).css({
				left : jQuery(this).offset().left
				//, top : jQuery(this).offset().top + jQuery(this).outerHeight() 
			})
			//.show();
			.toggle();
		});
		
		
		jQuery("input[name='" + opt.inputAllChk + "']", "#" + opt.boxId).click(function (event) {
			var chk 	= jQuery("input[name='"+ opt.inputChk +"']", "#" + opt.boxId);
			var chk_all = jQuery(this);
			if (chk_all.attr("checked")) {
				chk.attr("checked", true);
				chk.refresh_checkbox();
			}
			else {
				chk.attr("checked", false);
				chk.refresh_checkbox();
			}
			multiChooseBox.setChooseNm();
		});
		
		jQuery("input[name='"+ opt.inputChk +"']", "#" + opt.boxId).click(function (event) {
			var chk 	= jQuery("input[name='"+ opt.inputChk +"']", "#" + opt.boxId);
			var chk_all = jQuery("input[name='"+ opt.inputAllChk +"']", "#" + opt.boxId);
			
			//2015.04.02 김기윤 추가
			//disable 시킬 경우 이벤트를 리턴 시킨다.
			if(opt.disabled) {
				return;
			}
			
			
			//2015.03.24 김기윤 추가
			//클릭시 Label의 클래스가 on이 되지 않는 오류 발견
			
			if (jQuery(this).parent().parent().parent().find("label").length > 0) {
				var label = jQuery(this).parent().parent().parent().find("label");
				
				if (jQuery(this).prop("checked")) {
					jQuery(label).addClass("on");
				} else {
					jQuery(label).removeClass("on");
				}
			}
			//======================================================================
			
									
			if (chk.filter(":checked").size() == chk.size()) {
				chk_all.attr("checked", true);
				chk_all.refresh_checkbox();
			}
			else {
				chk_all.attr("checked", false);
				chk_all.refresh_checkbox();
			}
			multiChooseBox.setChooseNm();
		});
		
		jQuery(document).mousedown(function(event) {
			var target = jQuery(event.target);

			target[0].id != opt.boxId
				&& target.parents("#" + opt.boxId).size() == 0
				&& target[0].id != opt.inputText
				&& multiChooseBox.hideBox();
		});
		
		multiChooseBox.setChooseNm();
	}
	, hideBox : function () {
		var opt = this.opt;
		jQuery("#" + opt.boxId).hide();
	}
	, setChooseNm : function () {
		var opt = this.opt;
		var chk 	= jQuery("input[name='"+ opt.inputChk +"']", "#" + opt.boxId);
		var chk_all = jQuery("input[name='"+ opt.inputAllChk +"']", "#" + opt.boxId);
		var target;
		
		if (opt.inputTextID.length > 0)
			target	= jQuery("#" + opt.inputTextID);
		else
			target	= jQuery("input[name='"+ opt.inputText +"']");
		
		if (chk_all.attr("checked")) {
			target.val("전체");
		}
		else {
			var chk_target	= chk.filter(":checked");

			if (chk_target.size() == 0) {
				if (opt.enableAll) {
					target.val("전체");
				} else {
					target.val("선택");
				}
				
			}
			else if (chk_target.size() == 1) {
				target.val(chk_target.eq(0).attr("alt"));
			}
			else {
				target.val( chk_target.size() + "개 선택");
			}
		}
	}	
};


function changeSpaceVal(target, type) {
	
	if (type == "encode") {
		j$("option", target).each(function() {
			var val	=	j$(this).attr("value");
			
			if (val != "" ) {
				var arrVal = val.split(/\s+/);
				j$(this).attr("value", arrVal.join("dAAb"));
			}
		});
	}
	else if (type == "decode") {
		j$("option", target).each(function() {
			var val	=	j$(this).attr("value");
			
			if (val != "" ) {
				var arrVal = val.split("dAAb");
				j$(this).attr("value", arrVal.join(" "));
			}
		});
	}
}

/**
 * 체크박스 전체 선택
 * @param chkbox_all
 * @param arr_chkbox
 */
function addCheckboxEvent(chkbox_all, arr_chkbox, callback) {
	
	var len = arr_chkbox.not(":disabled").length;
	
	if (len > 0 && arr_chkbox.not(":disabled").filter(":checked").length == len) {
		
		var is_new = chkbox_all.hasClass("jgeeseInput_init");
		
		if (is_new) {
			chkbox_all.attr("checked", true).jgeeseInput("refresh");
		}
		else {
			chkbox_all.attr("checked", true).refresh_checkbox();
		}
	}
	
	chkbox_all.click(function (event) {
		var t = jQuery(this);
		var checked = t.attr("checked");
		var is_new = t.hasClass("jgeeseInput_init");
		
		if (checked) {
			arr_chkbox.not(":disabled").attr("checked", true);
		}
		else {
			arr_chkbox.not(":disabled").attr("checked", false);
		}
		
		if (is_new) {
			arr_chkbox.jgeeseInput("refresh");
		}
		else {
			arr_chkbox.refresh_checkbox();
		}
		
		if (callback != undefined) {
			callback();
		}
	});
	
	arr_chkbox.click(function (event) {
		var t = jQuery(this);
		var size1 = arr_chkbox.size();
		var size2 = arr_chkbox.filter(":checked").size();
		var is_new = t.hasClass("jgeeseInput_init");
		
		if (size1 == size2) {
			chkbox_all.attr("checked", true);
		}
		else {
			chkbox_all.attr("checked", false);
		}
		
		if (is_new) {
			chkbox_all.jgeeseInput("refresh");
		}
		else {
			chkbox_all.refresh_checkbox();
		}
		
		if (callback != undefined) {
			callback();
		}
		
	});
}


function fnTargetFocus(target) {
	
	if (target == undefined) {
		return;
	}
	
	var t = 0;
	
	if (target.is("select")) {
		t = target.parents(".jquery-selectbox").eq(0).offset().top;
	} 
	else if (target.is("input[type='radio']")) {
		t = target.parents(".rd-style").eq(0).offset().top;
	}
	else if (target.is("input[type='checkbox']")) {
		t = target.parents(".chk-style").eq(0).offset().top;
	}
	else {
		target.focus();
		return;
	}
	
	if (t <= 100) {
		jQuery(window).scrollTop(0);
	}
	else {
		jQuery(window).scrollTop(t - 100);
	}
	
}

function fnPrecedeProjectView(pjtCd){
	document.location.href	= "/pts/project2020/pts_project_link_view.do?i_sPjtCd=" + pjtCd;
}