
var jfupload = {
	// 이미지 업로드 초기화
	initImageUpload : function (p_opt) {
		
		// 기본값 셋팅
		var _default = {
			target : undefined
			, uploadCd : undefined
			, formName : undefined
			, thumbnailWidth : ""
			, thumbnailHeight : ""
			, flagFixed : "N"
			, index : undefined	
			, url : WebPATH + "comm/comm_swfupload.do"
			, dropZone : undefined
			, dataType : "json"
			, isSelfMakeTag : false
			, fileDiv : undefined
			//, ext : /jpg|png|gif/i
			, ext : /(\.|\/)(gif|jpe?g|png)$/i
		};
		
		var options = jQuery.extend(_default, p_opt);
		
		if (options.target == undefined || options.uploadCd == undefined || options.formName == undefined) {
			return;
		}
		
		var target = options.target;
		
		target.fileupload({
			url : options.url
			, dataType : options.dataType
			, formData : {
				uploadCd : options.uploadCd
				, thumbnailWidth : options.thumbnailWidth
				, thumbnailHeight : options.thumbnailHeight
				, flagFixed : options.flagFixed
				, resultType : "json"
				, fileDiv : options.fileDiv
				, maxFileSize : 1024
			}
			, dropZone : options.dropZone
		}).on("fileuploadadd", function (e, data) {
			var uploadFile = data.files[0];
			
			// 이미지 선택시
			if (options.fileDiv == "IMG" && options.ext != undefined) {
				if (!(options.ext).test(uploadFile.name)) {
					alert("이미지 파일만 업로드 가능합니다.");
					return;
				}
			}
			
			if (typeof options.add_func == "function") {
				options.add_func(e, data);
			}
			
			data.submit()
				.success(function (result, textStatus, jqXHR) {
					if (result.status != "succ") {
						alert(result.message);
						return;
					}
					
					var imgData = result.data;
								
					//수정전 원본 
					if (!options.isSelfMakeTag) {
						jfupload.makeImageUploadTag(imgData, options.uploadCd, options.formName, "R", options.fileDiv);
					}
					if (typeof options.success == "function") {
						options.success(imgData, options.uploadCd, data, options.formName);
					}
				})
				.error(function (jqXHR, textStatus, errorThrown) {
					if (typeof options.error == "function") {
						options.error(jqXHR, textStatus, errorThrown);
					}
				})
				.complete(function (result, textStatus, jqXHR) {
					if (typeof options.complete == "function") {
						options.complete(result, textStatus, jqXHR);
					}
				});
			
		}).on('fileuploadprocessalways', function (e, data) {
			if (typeof options.always_func == "function") {
				options.always_func(e, data);
			}
			
		}).on('fileuploadprogress', function (e, data) {
			
			var progress = parseInt(data.loaded / data.total * 100, 10);
			
			if (typeof options.progress_func == "function") {
				options.progress_func(e, data, progress);
			}
			
		}).on('fileuploadprogressall', function (e, data) {
			
			var progress = parseInt(data.loaded / data.total * 100, 10);
			
			if(options.index != undefined) {
				var el_progress = jQuery(".div_progressBar").eq(options.index);
				if(el_progress.parent().attr("class").indexOf("dd_jfuploadProgress") > -1) {
					el_progress.parent().css("width", "155px");
				}
				
				el_progress.show();
				
				if(progress >= 15 && progress <=90) {
					el_progress.css({
						width : progress - 10 + "%"
					});
				}
			}
			
		}).prop('disabled', !jQuery.support.fileInput)
			.parent().addClass(jQuery.support.fileInput ? undefined : 'disabled');
		
		
		// drag & drop 기능 사용시
		if (options.dropZone != undefined) {
			
			jQuery(document).bind('dragover', function (e) {
				var dropZone = options.dropZone, 
					timeout = window.dropZoneTimeout;
				
				if (!timeout) {
					dropZone.addClass("in");
				}
				else {
					clearTimeout(timeout);
				}
				
				var found = false,
					node = e.target;
				
				do {
					if (node == dropZone[0]) {
						found = true;
						break;
					}
					node = node.parentNode;
				}
				while (node != null);
				
				if (found) {
					dropZone.addClass("hover");
				}
				else {
					dropZone.removeClass("hover");
				}
				window.dropZoneTimeout = setTimeout(function () {
					window.dropZoneTimeout = null;
					 dropZone.removeClass('in hover');
				}, 100);
			});
		}
		
	},
	initUpload : function (p_opt) {
		// 기본값 셋팅
		var _default = {
			target : undefined
			, uploadCd : undefined
			, formName : undefined
			, flagFixed : "N"
			, index : undefined	
			, url : WebPATH + "comm/comm_swfupload.do"
			, dropZone : undefined
			, dataType : "json"
			, isSelfMakeTag : false
			//, ext : /jpg|png|gif/i
			, ext : /(\.|\/)(gif|jpe?g|png)$/i
		};
		
		var options = jQuery.extend(_default, p_opt);
		
		if (options.target == undefined || options.uploadCd == undefined || options.formName == undefined) {
			return;
		}
		
		var target = options.target;
		
		target.fileupload({
			url : options.url
			, dataType : options.dataType
			, formData : {
				uploadCd : options.uploadCd
				, flagFixed : options.flagFixed
				, resultType : "json"
				, attachIdx : options.index
				, fileTypeCheck : "Y"
				, fileAttachType : "ALL"
			}
			, dropZone : options.dropZone
		}).on("fileuploadadd", function (e, data) {
			if (typeof options.add_func == "function") {
				options.add_func(e, data);
			}
			
			data.submit()
				.success(function (result, textStatus, jqXHR) {
					if (result.status != "succ") {
						alert(result.message);
						return;
					}
					var imgData = result.data;
								
					//수정전 원본 
					if (!options.isSelfMakeTag) {
						jfupload.makeImageUploadTag(imgData, options.uploadCd, options.formName, "R");
					}
					if (typeof options.success == "function") {
						options.success(imgData, options.uploadCd, data);
					}
				})
				.error(function (jqXHR, textStatus, errorThrown) {
					if (typeof options.error == "function") {
						options.error(jqXHR, textStatus, errorThrown);
					}
				})
				.complete(function (result, textStatus, jqXHR) {
					if (typeof options.complete == "function") {
						options.complete(result, textStatus, jqXHR);
					}
				});
			
		}).on('fileuploadprocessalways', function (e, data) {
			if (typeof options.always_func == "function") {
				options.always_func(e, data);
			}
			
		}).on('fileuploadprogress', function (e, data) {
			
			var progress = parseInt(data.loaded / data.total * 100, 10);
			
			if (typeof options.progress_func == "function") {
				options.progress_func(e, data, progress);
			}
			
		}).on('fileuploadprogressall', function (e, data) {
			
			var progress = parseInt(data.loaded / data.total * 100, 10);
			
			/*if(options.index != undefined) {
				var el_progress = jQuery(".div_progressBar").eq(options.index);
				if(el_progress.parent().attr("class").indexOf("dd_jfuploadProgress") > -1) {
					el_progress.parent().css("width", "155px");
				}
				
				el_progress.show();
				
				if(progress >= 15 && progress <=90) {
					el_progress.css({
						width : progress - 10 + "%"
					});
				}
			}*/
			
		}).prop('disabled', !jQuery.support.fileInput).parent().addClass(jQuery.support.fileInput ? undefined : 'disabled');
		
		// drag & drop 기능 사용시
		if (options.dropZone != undefined) {
			
			jQuery(document).bind('dragover', function (e) {
				var dropZone = options.dropZone, 
					timeout = window.dropZoneTimeout;
				
				if (!timeout) {
					dropZone.addClass("in");
				}
				else {
					clearTimeout(timeout);
				}
				
				var found = false,
					node = e.target;
				
				do {
					if (node == dropZone[0]) {
						found = true;
						break;
					}
					node = node.parentNode;
				}
				while (node != null);
				
				if (found) {
					dropZone.addClass("hover");
				}
				else {
					dropZone.removeClass("hover");
				}
				window.dropZoneTimeout = setTimeout(function () {
					window.dropZoneTimeout = null;
					 dropZone.removeClass('in hover');
				}, 100);
			});
		}
	},
	// HTML tag 생성해서  form 에 삽입
	makeImageUploadTag : function (imgData, uploadCd, formName, flagAction) {
		if(imgData.fileDiv == "IMG") {
			var div 	= jQuery("<div/>").prop("id", "div_" + imgData.v_thumbnail_id);
			var inp1 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_action_type"}).addClass("cls_action_type").val(flagAction);
			var inp2 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_id"}).addClass("cls_thumbnail_id").val(imgData.v_thumbnail_id);
			var inp3 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_path"}).addClass("cls_thumbnail_path").val(imgData.v_thumbnail_path);
			var inp4 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_full_path"}).addClass("cls_thumbnail_full_path").val(imgData.v_thumbnail_full_path);
			var inp5 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_ext"}).addClass("cls_thumbnail_ext").val(imgData.v_thumbnail_ext);
			var inp6 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_nm"}).addClass("cls_thumbnail_nm").val(imgData.v_thumbnail_nm);
			var inp7 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_size"}).addClass("cls_thumbnail_size").val(imgData.n_thumbnail_size);
			var inp8 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_width"}).addClass("cls_thumbnail_width").val(imgData.v_thumbnail_width);
			var inp9 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_div"}).val(imgData.fileDiv);
			
			div.appendTo(jQuery("form[name='"+formName+"']"));
			inp1.appendTo(div);
			inp2.appendTo(div);
			inp3.appendTo(div);
			inp4.appendTo(div);
			inp5.appendTo(div);
			inp6.appendTo(div);
			inp7.appendTo(div);
			inp8.appendTo(div);
			inp9.appendTo(div);
			
			div.addClass("div_thumbnail");
		} else {
			var div = jQuery("<div/>").prop("id", "div_" + imgData.attach_id);
			var inp0 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_action_type"}).addClass("cls_action_type").val(flagAction);
			var inp1 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_id"}).val(imgData.attach_id);
			var inp2 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_path"}).val(imgData.attach_path);
			var inp3 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_path2"}).val(imgData.attach_path2);
			var inp4 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_ext"}).val(imgData.attach_ext);
			var inp5 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_nm"}).val(imgData.attach_nm);
			var inp6 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_size"}).val(imgData.attach_size);
			var inp7 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_div"}).val(imgData.fileDiv);
			
			div.appendTo(jQuery("form[name='"+formName+"']"));
			inp0.appendTo(div);
			inp1.appendTo(div);
			inp2.appendTo(div);
			inp3.appendTo(div);
			inp4.appendTo(div);
			inp5.appendTo(div);
			inp6.appendTo(div);
			inp7.appendTo(div);
		}
		
		//jQuery("input", div).prop("type", "text");
	},
	
	deleteImage : function (thumbnailId, uploadCd, formName) {
		
		var div = jQuery("#div_" + thumbnailId);
		var flagAction = jQuery(".cls_action_type", div).val();
		
		if (flagAction != "R") {
			var inp = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_del_attach_id"}).addClass("cls_del_thumbnail_id").val(thumbnailId);
			inp.appendTo(jQuery("form[name='"+formName+"']"));
		}
		
		div.remove();
	}
};



(function ($, G) {
	function PPT() {
	}
	
	$.extend(PPT.prototype, {
		_attachJfupload : function (p_target, p_opt) {
			
			var _defaults = {
				uploadCd : undefined
				, file_size_limit : 102400
				, thumbnailWidth : ""
				, thumbnailHeight : ""
				, fileAttachType : "ALL"
				, rec_type : undefined
				, flagFixed : "N"
				, fileDiv : undefined
				, url : undefined
				, dropZone : undefined
				, dataType : "json"
				, ext_err_msg : "이미지 파일만 업로드 가능합니다."
				, ext : /(\.|\/)(gif|jpe?g|png)$/i
			};
			
			/*
			 * 이미지 : /(\.|\/)(gif|jpe?g|png)$/i
			 */
			
			var options = $.extend(_defaults, p_opt);
			
			if (options.url == undefined) {
				alert("options.url 값이 업습니다.");
				return;
			}

			$(p_target).fileupload({
				url : options.url
				, dataType : options.dataType
				, formData : {
					uploadCd : options.uploadCd
					, thumbnailWidth : options.thumbnailWidth
					, thumbnailHeight : options.thumbnailHeight
					, fileAttachType : options.fileAttachType
					, flagFixed : options.flagFixed
					, fileDiv : options.fileDiv
					, resultType : "json"
					, rec_type : options.rec_type
				}
				, dropZone : options.dropZone
			}).on("fileuploadadd", function (e, data) {
				var uploadFile = data.files[0];
				
				// 이미지 선택시
				if (options.ext != undefined) {
					if (!(options.ext).test(uploadFile.name)) {
						alert(options.ext_err_msg);
						return false;
					}
				}
				
				if(uploadFile.size > options.file_size_limit*1024){
					
					var maxSize = options.file_size_limit / 1024;
					alert("파일의 크기가 업로드 가능한 최대크기("+maxSize.toFixed(2)+"MB)를 초과하여\n업로드 되지 않습니다. 크기 변환 후 업로드해주세요.");
					return false;
				}
				
				if (typeof options.add_func == "function") {
					options.add_func(e, data);
				}
				
				data.submit()
					.success(function (result, textStatus, jqXHR) {
						if (result.status != "succ") {
							alert(result.message);
							return;
						}
						var uploadData = result.data;
						
						if (typeof options.success == "function") {
							options.success(uploadData, options.uploadCd, data, options.formName);
						}
					})
					.error(function (jqXHR, textStatus, errorThrown) {
						if (typeof options.error == "function") {
							options.error(jqXHR, textStatus, errorThrown);
						}
					})
					.complete(function (result, textStatus, jqXHR) {
						if (typeof options.complete == "function") {
							options.complete(result, textStatus, jqXHR);
						}
					});
				
			}).on('fileuploadprocessalways', function (e, data) {
				
				if (typeof options.always_func == "function") {
					options.always_func(e, data);
				}
				
			}).on('fileuploadprogress', function (e, data) {
				
				var progress = parseInt(data.loaded / data.total * 100, 10);
				
				if (typeof options.progress_func == "function") {
					options.progress_func(e, data, progress);
				}
				
			}).on('fileuploadprogressall', function (e, data) {
				
				var progress = parseInt(data.loaded / data.total * 100, 10);
				var el_pregress = jQuery("#jfupload_progress_" + options.uploadCd);
				
				if (el_pregress.html() == undefined) {
					var arrHtml = [];
					arrHtml.push("<span id='jfupload_progress_"+ options.uploadCd +"' class='jfupload_progress'>");
					arrHtml.push("	<span class='ifupload_progress_bar'></span>");
					arrHtml.push("</span>");
					
					el_pregress = jQuery(arrHtml.join("\n")).insertAfter(jQuery(e.target).parents(".dl_jfupload").eq(0));
				}
				
				el_pregress.show();
				jQuery(".ifupload_progress_bar", el_pregress).css({'width' : progress + '%'});
				
			}).prop('disabled', !jQuery.support.fileInput).parent().addClass(jQuery.support.fileInput ? undefined : 'disabled');
			
		}
	});
	
	$.fn.jfupload = function (a) {
		if (!this.length) return this;
		if (!$.jfupload.initialized) {
			$.jfupload.initialized = true;
		}
		return this.each(function () {
			$.jfupload._attachJfupload(this, a);
		});
	};
	
	$.jfupload = new PPT;
	$.jfupload.initialized = false;
	$.jfupload.version = "1.1";
	
})(jQuery);

// 공틍오로 쓸 함수
var cfn_jfupload = {
	uploadSuccesTp01 : function (fileData, uploadCd, formName, uploadLimit) {
		
		var ul = jQuery("#ul_jfupload_" + uploadCd);
		var len = ul.find(".li_left").length;
		
		if(len >= uploadLimit) {
			alert("파일 업로드 개수가 초과되었습니다.");
			return;
		}
		
		var div = cfn_jfupload.makeFileUploadTag(fileData, uploadCd, formName, "R");
		
		var html = [];
		
		html.push("<li class='li_left progressWrapper' id='li_jfupload_"+ fileData.attach_id +"'>");
		html.push("    <dl>");
		html.push("    <dt class='jf_f_name'>" + fileData.attach_nm + "</dt>");
		html.push("    <dd><a href=\"javascript:cfn_jfupload.deleteFile('" + fileData.attach_id + "', '"+ uploadCd +"', '"+ formName +"' );\" class='jfupload_icon_btn'><span class='jfupload_icon_btn_delete'></span></a></dd>");
		html.push("    </dl>");
		html.push("</li>");
		
		var li = jQuery(html.join("")).appendTo(ul);
		
		div.appendTo(li);
	}
	, uploadSuccesMultipleTp01 : function (fileData, uploadCd, formName, ul) {
	
		var div = cfn_jfupload.makeMultipleRecidFileUploadTag(fileData, uploadCd, formName, "R");
		var html = [];
		
		html.push("<li class='li_left progressWrapper' id='li_jfupload_"+ fileData.attach_id +"'>");
		html.push("    <dl>");
		html.push("    <dt class='jf_f_name'>" + fileData.attach_nm + "</dt>");
		html.push("    <dd><a href=\"javascript:cfn_jfupload.deleteFile('" + fileData.attach_id + "', '"+ uploadCd +"', '"+ formName +"' );\" class='jfupload_icon_btn'><span class='jfupload_icon_btn_delete'></span></a></dd>");
		html.push("    </dl>");
		html.push("</li>");
		
		var li = jQuery(html.join("")).appendTo(ul);
		
		div.appendTo(li);
	}
	, uploadSuccessImgTp01 : function(fileData, uploadCd, formName, uploadLimit) {
		
		var ul = jQuery("#ul_jfupload_" + uploadCd);
		var len = ul.find(".li_img_left").length;
		
		if(len >= uploadLimit) {
			alert("파일 업로드 개수가 초과되었습니다.");
			return;
		}
		
		var div = cfn_jfupload.makeImageUploadTag(fileData, uploadCd, formName, "R");
		
		var html = [];
		
		html.push("<li class='li_img_left progressWrapper' id='li_jfupload_"+ fileData.v_thumbnail_id +"'>");
		/*html.push("    <dl>");
		html.push("    <dt class='jf_f_name'>" + fileData.attach_nm + "</dt>");
		html.push("    <dd><a href=\"javascript:cfn_jfupload.deleteFile('" + fileData.attach_id + "', '"+ uploadCd +"', '"+ formName +"' );\" class='jfupload_icon_btn'><span class='jfupload_icon_btn_delete'></span></a></dd>");
		html.push("    </dl>");*/
		html.push("		<div class='Img_Upload'>");
		html.push("			<img src='"+WebPATH+fileData.v_thumbnail_path.substring(1)+"/"+fileData.v_thumbnail_id+fileData.v_thumbnail_ext+"'/>");
		html.push("			<a href=\"javascript:cfn_jfupload.deleteImage('" + fileData.v_thumbnail_id + "', '"+ uploadCd +"', '"+ formName +"' );\"><img src=\""+Img2013PATH+"common/chip_box_close_black.png\"/></a>");
		html.push("		</div>");
		html.push("</li>");
		
		var li = jQuery(html.join("")).appendTo(ul);
		
		div.appendTo(li);
	}
	, makeFileUploadTag : function (fileData, uploadCd, formName, flagAction) {
		
		var html = [];
		
		if(fileData.rec_type == null || fileData.rec_type == ""){
			html.push("<div id='div_" + fileData.attach_id + "'>");
			html.push("    <input type='hidden' name='"+ uploadCd +"_attach_act_type' value='' class='cls_attach_act_type' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_attach_id' value='' class='cls_attach_id' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_attach_ext' value='' class='cls_attach_ext' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_attach_nm' value='' class='cls_attach_nm' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_attach_size' value='' class='cls_attach_size' /> ");
			html.push("</div>");
		}
		else{
			html.push("<div id='div_" + fileData.attach_id + "'>");
			html.push("    <input type='hidden' name='"+ uploadCd +"_"+fileData.rec_type+"_attach_act_type' value='' class='cls_attach_act_type' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_"+fileData.rec_type+"_attach_id' value='' class='cls_attach_id' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_"+fileData.rec_type+"_attach_ext' value='' class='cls_attach_ext' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_"+fileData.rec_type+"_attach_nm' value='' class='cls_attach_nm' /> ");
			html.push("    <input type='hidden' name='"+ uploadCd +"_"+fileData.rec_type+"_attach_size' value='' class='cls_attach_size' /> ");
			html.push("</div>");
		}
		
		var frm = jQuery("form[name='"+formName+"']");
		var div = jQuery(html.join("")).appendTo(frm);
		
		div.find(".cls_attach_act_type").val(flagAction);
		div.find(".cls_attach_id").val(fileData.attach_id);
		div.find(".cls_attach_ext").val(fileData.attach_ext);
		div.find(".cls_attach_nm").val(fileData.attach_nm);
		div.find(".cls_attach_size").val(fileData.attach_size);
		
		return div;
		
	}
	, makeMultipleRecidFileUploadTag : function (fileData, uploadCd, formName, flagAction) {
		
		var html = [];
		
		html.push("<div id='div_" + fileData.attach_id + "'>");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_act_type' value='' class='cls_attach_act_type' /> ");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_id' value='' class='cls_attach_id' /> ");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_ext' value='' class='cls_attach_ext' /> ");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_nm' value='' class='cls_attach_nm' /> ");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_size' value='' class='cls_attach_size' /> ");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_recordid' value='' class='cls_attach_recordid' /> ");
		html.push("    <input type='hidden' name='"+ uploadCd +"_attach_rec_type' value='' class='cls_attach_rec_type' /> ");
		html.push("</div>");
		
		var frm = jQuery("form[name='"+formName+"']");
		var div = jQuery(html.join("")).appendTo(frm);
		
		div.find(".cls_attach_act_type").val(flagAction);
		div.find(".cls_attach_id").val(fileData.attach_id);
		div.find(".cls_attach_ext").val(fileData.attach_ext);
		div.find(".cls_attach_nm").val(fileData.attach_nm);
		div.find(".cls_attach_size").val(fileData.attach_size);
		div.find(".cls_attach_recordid").val(fileData.attach_recordid);
		div.find(".cls_attach_rec_type").val(fileData.attach_rec_type);
		
		return div;
	}
	, deleteFile : function (attId, uploadCd, formName) {
		var li = jQuery("#li_jfupload_" + attId);
		var flagAction = li.find(".cls_attach_act_type").val();
		
		if (flagAction != "R") {
			var frm = jQuery("form[name='"+formName+"']");
			var inp = jQuery("<input type='hidden' name='"+ uploadCd +"_del_attach_id' value='' />");
			
			inp.val(attId).appendTo(frm);
		}
		
		li.remove();
	}
	// HTML tag 생성해서  form 에 삽입
	, makeImageUploadTag : function (imgData, uploadCd, formName, flagAction) {
		
		var div 	= jQuery("<div/>").prop("id", "div_" + imgData.v_thumbnail_id);
		var inp1 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_action_type"}).addClass("cls_thumbnail_action_type").val(flagAction);
		var inp2 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_id"}).addClass("cls_thumbnail_id").val(imgData.v_thumbnail_id);
		var inp3 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_path"}).addClass("cls_thumbnail_path").val(imgData.v_thumbnail_path);
		var inp4 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_full_path"}).addClass("cls_thumbnail_full_path").val(imgData.v_thumbnail_full_path);
		var inp5 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_ext"}).addClass("cls_thumbnail_ext").val(imgData.v_thumbnail_ext);
		var inp6 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_nm"}).addClass("cls_thumbnail_nm").val(imgData.v_thumbnail_nm);
		var inp7 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_size"}).addClass("cls_thumbnail_size").val(imgData.n_thumbnail_size);
		var inp8 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_width"}).addClass("cls_thumbnail_width").val(imgData.v_thumbnail_width);
		var inp9 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_div"}).addClass("cls_attach_div").val(imgData.fileDiv);
		
		div.appendTo(jQuery("form[name='"+formName+"']"));
		inp1.appendTo(div);
		inp2.appendTo(div);
		inp3.appendTo(div);
		inp4.appendTo(div);
		inp5.appendTo(div);
		inp6.appendTo(div);
		inp7.appendTo(div);
		inp8.appendTo(div);
		inp9.appendTo(div);
		
		//jQuery("input", div).prop("type", "text");
		div.addClass("div_thumbnail");
		return div;
	},
	deleteImage : function (thumbnailId, uploadCd, formName) {
		
		var div = jQuery("#div_" + thumbnailId);
		var flagAction = jQuery(".cls_thumbnail_action_type", div).val();
		
		if (flagAction != "R") {
			var inp = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_del_thumbnail_id"}).addClass("cls_del_thumbnail_id").val(thumbnailId);
			var inp2 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_div"}).addClass("cls_del_attach_div").val("IMG");
			inp.appendTo(jQuery("form[name='"+formName+"']"));
			inp2.appendTo(jQuery("form[name='"+formName+"']"));
		}
		
		j$("#li_jfupload_" + thumbnailId).remove();
		div.remove();
	}
};