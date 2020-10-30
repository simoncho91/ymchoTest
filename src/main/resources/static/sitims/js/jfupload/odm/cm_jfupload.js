
var jfupload = {

	initUpload : function (p_opt) {
		
		// 기본값 셋팅
		var _default = {
			target : undefined
			, uploadCd : undefined
			, formName : undefined
			, flagFixed : "N"
			, index : undefined	
			, url : "/comm/comm_upload.do"
			, attachDir : "COM"
			, dropZone : undefined
			, dataType : "json"
			, isSelfMakeTag : false
			, buffer1 : undefined
			, buffer2 : undefined
			, buffer3 : undefined
			, buffer4 : undefined
			, buffer5 : undefined
			//, ext : /jpg|png|gif/i
			, ext : /(\.|\/)(gif|jpe?g|png)$/i
		};
		
		var options = jQuery.extend(_default, p_opt);
		
		if (options.target == undefined || options.uploadCd == undefined || options.formName == undefined) {
			return;
		}
		
		var target = jQuery(options.target);
		
		target.fileupload({
			url : options.url
			, dataType : options.dataType
			, formData : {
				uploadCd : options.uploadCd
				, flagFixed : options.flagFixed
				, resultType : "json"
				, attachDir : options.attachDir
				, attachIdx : options.index
				, fileTypeCheck : "Y"
				, buffer1 : options.buffer1 
				, buffer2 : options.buffer2 
				, buffer3 : options.buffer3 
				, buffer4 : options.buffer4 
				, buffer5 : options.buffer5 
			}
			, dropZone : options.dropZone
		}).on("fileuploadadd", function (e, data) {
					
			//jfupload.printLog("fileuploadadd");
        	if (typeof options.add_func == "function") {
        		options.add_func(e, data);
        	}
        	
        	data.submit()
        		.success(function (result, textStatus, jqXHR) {
	        		if (result.status != "succ") {
	        			alert(result.message);
	        			return;
					}
	        		var imgData = result.result.data;
	        	        		
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
			
			//jfupload.printLog("fileuploadprocessalways");
			
			if (typeof options.always_func == "function") {
        		options.always_func(e, data);
        	}
			
		}).on('fileuploadprogress', function (e, data) {
			
			var progress = parseInt(data.loaded / data.total * 100, 10);
			//jfupload.printLog("!!" + progress);
			
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
	// HTML tag 생성해서  form 에 삽입
	makeImageUploadTag : function (attData, uploadCd, formName, flagAction) {
			var div = jQuery("<div/>").prop("id", "div_" + attData.v_attach_id);
			var inp0 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_action_type"}).addClass("cls_action_type").val(flagAction);
			var inp1 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_id"}).val(attData.v_attach_id);
			var inp2 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_type"}).val(attData.v_attach_type);
			var inp3 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_lnm"}).val(attData.v_attach_lnm);
			var inp4 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_pnm"}).val(attData.v_attach_pnm);
			var inp5 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_size"}).val(attData.v_attach_size);
			var inp6 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_mgtid"}).val(attData.v_attach_mgtid);
			var inp7 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_pk1"});
			var inp8 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_pk2"});
			var inp9 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_pk3"});
			var inp10 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_pk4"});
			var inp11 = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_attach_pk5"});
			
			div.appendTo(jQuery("form[name='"+formName+"']"));
			inp0.appendTo(div);
			inp1.appendTo(div);
			inp2.appendTo(div);
			inp3.appendTo(div);
			inp4.appendTo(div);
			inp5.appendTo(div);
			inp6.appendTo(div);
			inp7.appendTo(div);
			inp8.appendTo(div);
			inp9.appendTo(div);
			inp10.appendTo(div);
			inp11.appendTo(div);
			
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
	}/*,
	printLog : function (str) {
		console.log(str);
	}*/
};



(function ($, G) {
	
	function PPT() {
		
		var _defaults = {
			uploadCd : undefined
			, thumbnailWidth : ""
			, thumbnailHeight : ""
			, flagFixed : "N"
			, url : undefined
			, dropZone : undefined
			, dataType : "json"
			, ext_err_msg : "이미지 파일만 업로드 가능합니다."
			, ext : /(\.|\/)(gif|jpe?g|png)$/i
		};
		
		/*
		 * 이미지 : /(\.|\/)(gif|jpe?g|png)$/i
		 */
		
		this._options = $.extend(_defaults, null);
	}
	
	$.extend(PPT.prototype, {
		_attachJfupload : function (p_target, p_opt) {
			
			$.extend(this._options, p_opt);
			
			var options = this._options;
			
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
					, flagFixed : options.flagFixed
					, resultType : "json"
				}
				, dropZone : options.dropZone
			}).on("fileuploadadd", function (e, data) {
				
				//$.jfupload._printLog("fileuploadadd");
				// 이미지 선택시
	        	if (options.ext != undefined) {
	        		var uploadFile = data.files[0];
	        		if (!(options.ext).test(uploadFile.name)) {
	        			alert(options.ext_err_msg);
	        			return false;
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
		        		var imgData = result.object;
		        		alert("3535353535");
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
				
				//$.jfupload._printLog("fileuploadprocessalways");
				
				if (typeof options.always_func == "function") {
	        		options.always_func(e, data);
	        	}
				
			}).on('fileuploadprogress', function (e, data) {
				
				var progress = parseInt(data.loaded / data.total * 100, 10);
				//$.jfupload._printLog("!!" + progress);
				
				if (typeof options.progress_func == "function") {
					options.progress_func(e, data, progress);
				}
				
			}).on('fileuploadprogressall', function (e, data) {
				
				var progress = parseInt(data.loaded / data.total * 100, 10);
	            var el_pregress = jQuery("#jfupload_progress_" + options.uploadCd);
	            
	            if (el_pregress.html() == undefined) {
	            	var arrHtml = [];
	            	arrHtml.push("<span id='jfupload_progress_"+ options.uploadCd +"' class='jfupload_progress'>");
	            	arrHtml.push("    <span class='ifupload_progress_bar'></span>");
	            	arrHtml.push("</span>");
	            	
	            	el_pregress = jQuery(arrHtml.join("\n")).insertAfter(jQuery(e.target).parents(".dl_jfupload").eq(0));
	            }
	            
	            el_pregress.show();
	            jQuery(".ifupload_progress_bar", el_pregress).css({
	            		'width' : progress + '%'
	            	});
				
		    }).prop('disabled', !jQuery.support.fileInput)
		        .parent().addClass(jQuery.support.fileInput ? undefined : 'disabled');
			
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
	$.jfupload.version = "1.0";
	
})(jQuery);
