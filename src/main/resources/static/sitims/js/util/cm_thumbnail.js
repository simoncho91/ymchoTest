

function CmThumbnail ( frm, webRoot, thumWidth, thumHeight ) 
{
	this.bThumUpload	= false;
	this.preImgObj		= document.getElementsByName("thumbnailPreImg");		// 미리보기 이미지
	this.fileObj		= document.getElementsByName("thumFileSearch_tmp");		// file
	this.statusObj		= document.getElementsByName("i_arrThumbnailStatus");	// 썸네일 ID
	this.thumIdObj		= document.getElementsByName("i_arrThumbnailId");		// 썸네일 ID
	this.thumTempIdObj	= document.getElementsByName("i_arrThumbnailTempId");	// 썸네일 임시 아이디
	this.thumNmObj		= document.getElementsByName("i_arrThumbnailName");		// 썸네일 이름
	this.thumPathObj	= document.getElementsByName("i_arrThumbnailPath");		// 썸네일 경로
	this.thumExtObj		= document.getElementsByName("i_arrThumbnailExt");		// 썸네일 확장자
	this.thumWidthObj	= document.getElementsByName("i_arrThumbnailWidth");	// width
	this.thumHeightObj	= document.getElementsByName("i_arrThumbnailHeight");	// height
	this.smallImgObj	= document.getElementsByName("i_arrSmallImgPath");		// 작은 이미지 파일 경로
	this.thumForm		= frm;
	this.thumWebRoot	= webRoot;
	this.thumWidth		= thumWidth;
	this.thumHeight		= thumHeight;
}
CmThumbnail.prototype =
{
	// 값 변경시
	CmThumChange : function (inputNode)
	{
		var	arrNode		= document.getElementsByName(inputNode.id);
		var len			= arrNode.length;
		var index		= -1;
		
		for (var i = 0; i < len; i++)
		{
			if (arrNode[i] == inputNode)
			{
				index		= i;
				break;
			}
		}
		
		if (index > -1)
		{
			if (!this.bThumUpload)
				this.CmThumUpload(index);
			else
				alert("아직 파일 업로드가 진행중입니다.\n업로드 완료후 다시 시도해 주세요.");
		}	
	},
	CmThumUpload : function ( index )
	{
		var fileName	= this.fileObj[index].value;
		
		if (fileName == "")
		{
			alert("선택된 파일이 없습니다.");
			this.CmThumUploadEnd(index);
			return;
		}
		
		fileName	= fileName.substr(fileName.lastIndexOf("/") + 1, fileName.length);
		
		if (fileName.indexOf(',') > -1)
		{
			alert("특수문자 \",\" 가 포함된 파일은 첨부할 수 없습니다.");
			this.CmFileUploadEnd(index);
			return;
		}
		else if (fileName.indexOf("\'") > -1)
		{
			alert("특수문자 \"'\" 가 포함된 파일은 첨부할 수 없습니다.");
			this.CmFileUploadEnd(index);
			return;
		}
		
		var oldTarget	= this.thumForm.target;
		var oldAction	= this.thumForm.action;
		var oldEncoding	= this.thumForm.encoding;
		
		this.bThumUpload		= true;
		
		this.CmThumLoaddingImg(index);
	    
	    var pars		= "?i_iThumbnailIndex=" + index
	    				+ "&i_sThumbnailWidth=" + this.thumWidth
	    				+ "&i_sThumbnailHeight=" + this.thumHeight;
		
	    this.thumForm.action	= this.thumWebRoot + "upload/cm_thumbnail.car" + pars;
	    this.thumForm.target	= "CmThumbIframe";
	    this.thumForm.encoding	= "multipart/form-data";
	    this.thumForm.submit();
	    this.thumForm.target	= oldTarget;
	    this.thumForm.action	= oldAction;
	    this.thumForm.encoding	= oldEncoding;
	},
	CmThumLoaddingImg : function (index)
	{
		this.preImgObj[index].src = this.thumWebRoot + "IMG/ajax-loader1.gif";
	},
	CmThumUploadEnd : function (index)
	{
		this.preImgObj[index].src = this.smallImgObj[index].value;
		this.bThumUpload	= false;
		
		try {
			this.fileObj[index].outerHTML		= this.fileObj[index].outerHTML;
		} catch (e) {
		}
	},
	CmThumSetting : function (robj, index)
	{
		var status			= "";
		
		if (this.statusObj[index].value   == "old")
	        status          = "modify";
	    else
	        status          = "add";

		this.statusObj[index].value			= status;
		this.thumTempIdObj[index].value    	= robj.thumbnailTempId;
		this.thumNmObj[index].value    		= robj.thumbnailName;
		this.thumPathObj[index].value  		= robj.thumbnailPath;
		this.thumExtObj[index].value   		= robj.thumbnailExt;
		this.thumWidthObj[index].value   	= robj.thumbnailWidth;
		this.thumHeightObj[index].value   	= robj.thumbnailHeight;
		
		var width	= robj.thumbnailWidth.split(",")[0];
		var path	= robj.thumbnailPath.substring(robj.thumbnailPath.indexOf("FILE_UPLOAD"), robj.thumbnailPath.length) + "/";
		
		while (width.length < 3)
			width	= "0" + width;
		
		this.smallImgObj[index].value		= this.thumWebRoot + path + robj.thumbnailTempId + "_" + width + robj.thumbnailExt;
		
		this.CmThumUploadEnd(index);
	},
	CmThumDel : function ( inputNode )
	{
		var	arrNode		= document.getElementsByName(inputNode.id);
		var len			= arrNode.length;
		var index		= -1;
		
		for (var i = 0; i < len; i++)
		{
			if (arrNode[i] == inputNode)
			{
				index		= i;
				break;
			}
		}
		
		if (index > -1)
		{
			this.CmThumDelPrc(index);
		}
	},
	CmThumDelPrc : function (index)
	{
		if (this.statusObj[index].value == "old")
		{
			var delObj		= document.getElementById("cmThumbnailDel");
			
			var inHtml		= "<input type='hidden' name='i_arrDelThumbnailId' value='"+ this.thumIdObj[index].value +"' />"
							+ "<input type='hidden' name='i_arrDelThumbnailWidth' value='"+ this.thumWidthObj[index].value +"' />"
							+ "<input type='hidden' name='i_arrDelThumbnailPath' value='"+ this.thumPathObj[index].value +  "' />"
							+ "<input type='hidden' name='i_arrDelThumbnailExt' value='"+ this.thumExtObj[index].value +  "' />";
			
			delObj.innerHTML	+= inHtml;
		}
		
		this.preImgObj[index].src		= this.thumWebRoot + "IMG/pre_view.gif";
		this.statusObj[index].value		= "";
		this.thumIdObj[index].value		= "";
		this.thumTempIdObj[index].value	= "";
		this.thumNmObj[index].value		= "";
		this.thumPathObj[index].value	= "";
		this.thumExtObj[index].value	= "";
		this.thumWidthObj[index].value	= "";
		this.thumHeightObj[index].value	= "";
		this.smallImgObj[index].value	= this.thumWebRoot + "IMG/pre_view.gif";
	}
};

