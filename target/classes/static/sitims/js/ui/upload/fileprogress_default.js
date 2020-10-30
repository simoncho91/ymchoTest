/*
	A simple class for displaying file information and progress
	Note: This is a demonstration only and not part of SWFUpload.
	Note: Some have had problems adapting this class in IE7. It may not be suitable for your application.
*/

// Constructor
// file is a SWFUpload file object
// targetID is the HTML element id attribute that the FileProgress HTML structure will be added to.
// Instantiating a new FileProgress object with an existing file will reuse/update the existing DOM elements
function FileProgress(file, targetID) {
	this.fileProgressID = file.id;
	
	this.opacity = 100;
	this.height = 0;
	
	this.fileProgressWrapper	= jQuery("#" + this.fileProgressID);
	
	if (this.fileProgressWrapper.html() == null) {
		var arrHtml		= [];
		
		arrHtml.push("<div id=\""+this.fileProgressID+"\" class=\"progressWrapper\">");
		arrHtml.push("    <div class=\"progressContainer\">");
		arrHtml.push("        <table>");
		arrHtml.push("        <colgroup>");
		arrHtml.push("            <col/>");
		arrHtml.push("            <col width=\"150px\"/>");
		arrHtml.push("            <col width=\"40px\"/>");
		arrHtml.push("        </colgroup>");
		arrHtml.push("        <tbody>");
	    arrHtml.push("        <tr>");
	    arrHtml.push("            <td class=\"upfileName\">"+ file.name +"</td>");
	    arrHtml.push("            <td class=\"upfileStatus\">");
	    arrHtml.push("        	      <div class=\"upfileStatusBar\"></div>");
	    arrHtml.push("        	      <div class=\"upfileStatusName\"></div>");
	    arrHtml.push("            </td>");
	    arrHtml.push("            <td class=\"upfileBtn\"><a style=\"visibility: visible\" class=\"progressCancel\" href=\"#\"><img src='/IMG/KO/icon/00cp_ic003.gif' /> </a></td>");
	    arrHtml.push("        </tr>");
	    arrHtml.push("        </tbody>");
		arrHtml.push("        </table>");
		arrHtml.push("    </div>");
		arrHtml.push("</div>");
		
		jQuery(arrHtml.join("\n")).appendTo(jQuery("#" + targetID));
		
		this.fileProgressWrapper	= jQuery("#" + this.fileProgressID);
		this.fileProgressElement 	= jQuery(".progressContainer", this.fileProgressWrapper);
	} else {
		this.fileProgressElement 	= jQuery(".progressContainer", this.fileProgressWrapper);
		this.reset();
	}
	
	this.height = this.fileProgressWrapper.height();
	this.setTimer(null);
}

FileProgress.prototype.setTimer = function (timer) {
	this.fileProgressElement.attr("FP_TIMER", timer);
};
FileProgress.prototype.getTimer = function (timer) {
	return this.fileProgressElement.attr("FP_TIMER") || null;
};

FileProgress.prototype.reset = function () {
	this.fileProgressElement.attr("class", "progressContainer");
	
	jQuery(".upfileStatusName", this.fileProgressElement).html("&nbsp;").attr("class", "upfileStatusName");
	jQuery(".upfileStatusBar", this.fileProgressElement).attr("class", "upfileStatusBar").css("width","0%");
	
	this.appear();	
};

FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.removeClass("red").removeClass("blue");
	this.fileProgressElement.addClass("green");
	jQuery(".upfileStatusBar", this.fileProgressElement).css("width", percentage + "%");

	this.appear();	
};
FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.removeClass("red").removeClass("green");
	this.fileProgressElement.addClass("blue");
	jQuery(".progressBarComplete", this.fileProgressElement).css("width", "");
	jQuery(".upfileStatusBar", this.fileProgressElement).css("width", "100%");
};
FileProgress.prototype.setError = function () {
	this.fileProgressElement.removeClass("blue").removeClass("green");
	this.fileProgressElement.addClass("red");
	jQuery(".progressBarError", this.fileProgressElement).css("width", "");
	
	var oSelf = this;
	this.setTimer(setTimeout(function () {
		oSelf.disappear();
	}, 5000));
};
FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.removeClass("blue").removeClass("green").removeClass("red");
	jQuery(".progressBarError", this.fileProgressElement).css("width", "");
	
	var oSelf = this;
	this.setTimer(setTimeout(function () {
		oSelf.disappear();
	}, 2000));
};
FileProgress.prototype.setStatus = function (status) {
	jQuery(".upfileStatusName", this.fileProgressElement).html(status);
};

// Show/Hide the cancel button
FileProgress.prototype.toggleCancel = function (show, swfUploadInstance) {
	var el_progressCancel	= jQuery(".progressCancel", this.fileProgressElement);
	
	if (show) {
		el_progressCancel.show();
	}
	else {
		el_progressCancel.hide();
	}
	
	if (swfUploadInstance) {
		var fileID = this.fileProgressID;
		el_progressCancel.unbind("click").bind("click", function () {
			swfUploadInstance.cancelUpload(fileID);
			return false;
		});
	}
};

FileProgress.prototype.appear = function () {
	if (this.getTimer() !== null) {
		clearTimeout(this.getTimer());
		this.setTimer(null);
	}
	
	this.fileProgressWrapper.css("opacity", 1);
	this.fileProgressWrapper.css("height", "");
	
	this.height = this.fileProgressWrapper.height();
	this.opacity = 100;
	this.fileProgressWrapper.show();
};

// Fades out and clips away the FileProgress box.
FileProgress.prototype.disappear = function () {
	var reduceOpacityBy = 15;
	var reduceHeightBy = 4;
	var rate = 30;	// 15 fps

	if (this.opacity > 0) {
		this.opacity -= reduceOpacityBy;
		if (this.opacity < 0) {
			this.opacity = 0;
		}
		
		this.fileProgressWrapper.css("opacity", this.opacity);
	}

	if (this.height > 0) {
		this.height -= reduceHeightBy;
		if (this.height < 0) {
			this.height = 0;
		}

		this.fileProgressWrapper.css("height", this.height + "px");
	}

	if (this.height > 0 || this.opacity > 0) {
		var oSelf = this;
		this.setTimer(setTimeout(function () {
			oSelf.disappear();
		}, rate));
	} else {
		this.fileProgressWrapper.hide();
		this.setTimer(null);
	}
};