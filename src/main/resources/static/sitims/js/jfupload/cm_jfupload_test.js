
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
			, index : 0
			, flagFixed : "N"
			, url : GLOBAL_WEB_ROOT + "comm/comm_image_upload.do"
			, dropZone : undefined
			, dataType : "json"
			, isSelfMakeTag : false
			, forceIframeTransport: false
			, ext : /(\.|\/)(gif|jpe?g|png)$/i
		};
		
		var options = jQuery.extend(_default, p_opt);
		
		if (options.target == undefined || options.uploadCd == undefined || options.formName == undefined) {
			return;
		}
		
		var target = options.target;
		
		target.fileupload({
			url : options.url
			, forceIframeTransport: options.forceIframeTransport
			, dataType : options.dataType
			, formData : {
				uploadCd : options.uploadCd
				, thumbnailWidth : options.thumbnailWidth
				, thumbnailHeight : options.thumbnailHeight
				, flagFixed : options.flagFixed
			}
			, dropZone : options.dropZone
		}).on("fileuploadadd", function (e, data) {
			alert("업로드 전");
			
			jfupload.printLog("fileuploadadd");
			
			// 이미지 선택시
        	if (options.ext != undefined) {
        		var uploadFile = data.files[0];
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
	        		
	        		var el_progress = jQuery(".div_progressBar").eq(options.index);
	        		
	        		if(el_progress.size() > 0) {
	        			el_progress.css("width", "90%").delay(450).stop().animate({"width":"100%"},750);
	        			el_progress.fadeOut("slow", function() {
	        				if(el_progress.parent().attr("class").indexOf("dd_jfuploadProgress") > -1) {
	        					el_progress.parent().css("width", "0px");
	        				}
	        			});
	        		}
					
	        		if (result.status != "succ") {
	        			alert(result.message);
	        			return;
					}
	        		
	        		var imgData = result.object;
	        		
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
	            	
	            	alert("업로드 complete");
	            });
			
		}).on('fileuploadprocessalways', function (e, data) {
			
			jfupload.printLog("fileuploadprocessalways");
			
			if (typeof options.always_func == "function") {
        		options.always_func(e, data);
        	}
			
		}).on('fileuploadprogress', function (e, data) {
			
			var progress = parseInt(data.loaded / data.total * 100, 10);
			jfupload.printLog("!!" + progress);
			
			if (typeof options.progress_func == "function") {
				options.progress_func(e, data, progress);
			}
			
		}).on('fileuploadprogressall', function (e, data) {
			
			/*var progress = parseInt(data.loaded / data.total * 100, 10);
            var el_pregress = jQuery("#jfupload_progress_" + options.uploadCd);
            var
            
            if (el_pregress.html() == undefined) {
            	var arrHtml = [];
            	arrHtml.push("<span id='jfupload_progress_"+ options.uploadCd +"' class='ifupload_progress'>");
            	arrHtml.push("    <span class='ifupload_progress_bar'></span>");
            	arrHtml.push("</span>");
            	
            	el_pregress = jQuery(arrHtml.join("\n")).insertAfter(jQuery(e.target).parents(".dl_jfupload").eq(0));
            }
            
            el_pregress.show();
            jQuery(".ifupload_progress_bar", el_pregress).css({
            		'width' : progress + '%'
            	});*/
			var progress = parseInt(data.loaded / data.total * 100, 10);
			var el_progress = jQuery(".div_progressBar").eq(options.index);
			
			if(el_progress.size() > 0) {
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
	/*
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
			, url : GLOBAL_WEB_ROOT + "comm/comm_image_upload.do"
			, dropZone : undefined
			, dataType : "json"
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
			}
			, add : function (e, data) {
				// 이미지 선택시
	        	if (options.ext != undefined) {
	        		var uploadFile = data.files[0];
	        		if (!(options.ext).test(uploadFile.name)) {
	        			alert("이미지 파일만 업로드 가능합니다.");
	        			return;
	        		}
	        	}
	        	
	        	console.log("add");
	        	
	        	data.submit()
		        	.success(function (result, textStatus, jqXHR) {
		        		if (result.status != "succ") {
		        			alert(result.message);
		        			return;
						}
		        		
		        		var imgData = result.object;
		        		
		        		jfupload.makeImageUploadTag(imgData, options.uploadCd, options.formName, "R");
		        		
		        		if (typeof options.success == "function") {
		        			options.success(imgData, options.uploadCd);
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
	        }
			, done: function (e, data) {
	        	console.log("!!! " + e);
	        	setTimeout(function () {
	        		var el_pregress = jQuery("#jfupload_progress_" + options.uploadCd);
		            el_pregress.hide();
		            jQuery(".ifupload_progress_bar", el_pregress).css({
		            		'width' : '0%'
		            	});
	        	}, 1000);
	        	
	        	console.log("done");
	        }
			, progress : function (e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				console.log("!!" + progress);
			}
			, progressall: function (e, data) {
				
	            var progress = parseInt(data.loaded / data.total * 100, 10);
	            var el_pregress = jQuery("#jfupload_progress_" + options.uploadCd);
	            
	            if (el_pregress.html() == undefined) {
	            	var arrHtml = [];
	            	arrHtml.push("<span id='jfupload_progress_"+ options.uploadCd +"' class='ifupload_progress'>");
	            	arrHtml.push("    <span class='ifupload_progress_bar'></span>");
	            	arrHtml.push("</span>");
	            	
	            	el_pregress = jQuery(arrHtml.join("\n")).insertAfter(jQuery(e.target).parents(".dl_jfupload").eq(0));
	            }
	            
	            el_pregress.show();
	            jQuery(".ifupload_progress_bar", el_pregress).css({
	            		'width' : progress + '%'
	            	});
	            
	        	//console.log("!!! " + progress);
	        }
	    }).prop('disabled', !jQuery.support.fileInput)
	        .parent().addClass(jQuery.support.fileInput ? undefined : 'disabled');
	},
	*/
	// HTML tag 생성해서  form 에 삽입
	makeImageUploadTag : function (imgData, uploadCd, formName, flagAction) {
		
		var div 	= jQuery("<div/>").prop("id", "div_" + imgData.v_thumbnail_id);
		var inp1 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_action_type"}).addClass("cls_thumbnail_action_type").val(flagAction);
		var inp2 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_id"}).addClass("cls_thumbnail_id").val(imgData.v_thumbnail_id);
		var inp3 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_path"}).addClass("cls_thumbnail_path").val(imgData.v_thumbnail_path);
		var inp4 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_full_path"}).addClass("cls_thumbnail_full_path").val(imgData.v_thumbnail_full_path);
		var inp5 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_ext"}).addClass("cls_thumbnail_ext").val(imgData.v_thumbnail_ext);
		var inp6 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_nm"}).addClass("cls_thumbnail_nm").val(imgData.v_thumbnail_nm);
		var inp7 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_size"}).addClass("cls_thumbnail_size").val(imgData.n_thumbnail_size);
		var inp8 	= jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_thumbnail_width"}).addClass("cls_thumbnail_width").val(imgData.v_thumbnail_width);
		
		div.appendTo(jQuery("form[name='"+formName+"']"));
		inp1.appendTo(div);
		inp2.appendTo(div);
		inp3.appendTo(div);
		inp4.appendTo(div);
		inp5.appendTo(div);
		inp6.appendTo(div);
		inp7.appendTo(div);
		inp8.appendTo(div);
		
		//jQuery("input", div).prop("type", "text");
		div.addClass("div_thumbnail");
	},
	deleteImage : function (thumbnailId, uploadCd, formName) {
		
		var div = jQuery("#div_" + thumbnailId);
		var flagAction = jQuery(".cls_thumbnail_action_type", div).val();
		
		if (flagAction != "R") {
			var inp = jQuery("<input/>").prop({"type" : "hidden", "name" : uploadCd + "_del_thumbnail_id"}).addClass("cls_del_thumbnail_id").val(thumbnailId);
			inp.appendTo(jQuery("form[name='"+formName+"']"));
		}
		
		div.remove();
	},
	printLog : function (str) {
//		try{
//			console.log(str);
//		}catch(e){
//			
//		}
		
	},
	androidImageUpload : function (json, uploadcd, str_func) {
		
		var json2 = JSON.parse(json);
		var func = eval(str_func);
		
		func(json2.object, uploadcd);
	}
};


