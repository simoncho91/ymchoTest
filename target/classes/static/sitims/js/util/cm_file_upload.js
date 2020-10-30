
function CmFileUpload ( frm, webRoot) 
{
	this.bUpload		= false;
	this.upForm			= frm;
	this.webRoot		= webRoot;
	this.tdFileName		= document.getElementsByName("td_fileName");
	this.tdFileSize		= document.getElementsByName("td_fileSize");
	this.fileObj		= document.getElementsByName("fileSearch_tmp");		// file 
	this.statusObj		= document.getElementsByName("i_arrAttStatus");		// 상태
	this.attIdObj		= document.getElementsByName("i_arrAttachId");		// 첨부파일 고유 아이디
	this.recordIdObj	= document.getElementsByName("i_arrRecordId");		// 레코드 아이디
	this.attNameObj		= document.getElementsByName("i_arrAttachName");	// 첨부파일명
	this.attPathObj		= document.getElementsByName("i_arrAttachPath");	// 첨부파일 위치
	this.attExtObj		= document.getElementsByName("i_arrAttachExt");		// 첨부파일 확장자
	this.attSizeObj		= document.getElementsByName("i_arrAttachSize");	// 첨부파일 size
}
CmFileUpload.prototype =
{
	CmfnFileFocus : function ( target_obj )
	{
	    var obj         = document.getElementsByName(target_obj.id);
	    var len         = obj.length;
	    var idx         = -1;

	    for (var i = 0; i < len ; i++ )
	    {
	        if (obj[i] == target_obj)
	        {
	            idx     = i;
	            break;
	        }
	    }

	    if (idx > -1)
	    {
	        if (!this.bUpload)
	        {
	            this.CmFileUpload ( idx );
	        }
	        else
	        {
	            alert("아직 파일 업로드가 진행중입니다.\n업로드 종료후 다시 시도해 주세요.");
	        }
	    }
	},

	CmFileUpload : function ( idx )
	{
	    var sCheckFileName  = this.fileObj[idx].value;

	    if (sCheckFileName == "")
	    {
	        alert("선택된 파일이 없습니다.");
	        this.CmFileUploadEnd(idx);
	        return;
	    }

	    sCheckFileName	= sCheckFileName.substr(sCheckFileName.lastIndexOf("/") + 1, sCheckFileName.length);

	    if (sCheckFileName.indexOf(',') > -1)
	    {
	        alert("특수문자 \",\" 가 포함된 파일은 첨부할 수 없습니다.");
	        return;
	    }
	    if (sCheckFileName.indexOf('\'') > -1)
	    {
	        alert ("특수문자 \"'\" 가 포함된 파일은 첨부할 수 없습니다.");
	        return;
	    }
	    
	    this.CmLoaddingImg(idx);

	    var oldTarget       = this.upForm.target;
	    var oldAction       = this.upForm.action;
	    var oldEncoding     = this.upForm.encoding;
	    var pars            = "?i_sAttachId=" + this.attIdObj[idx].value
	                        + "&i_iAttIndex=" + idx;
	    
	    this.bUpload		= true;

	    this.upForm.action		= this.webRoot + "upload/cm_file_upload.do" + pars ;
	    this.upForm.target		= "CmFileUploadIframe";
	    this.upForm.encoding	= "multipart/form-data";
	    this.upForm.submit();
	    this.upForm.target		= oldTarget;
	    this.upForm.action		= oldAction;
	    this.upForm.encoding	= oldEncoding;
	},
	// loaddingImg 변경
	CmLoaddingImg : function (idx)
	{
	    this.tdFileName[idx].innerHTML      = "<img src=\""+ this.webRoot +"IMG/CM/ajax-loader.gif\" />";
	},

	CmFileUploadEnd : function ( idx )
	{
	    this.tdFileName[idx].innerHTML      = this.attNameObj[idx].value;
	    this.tdFileSize[idx].innerHTML		= this.getFileSize(this.attSizeObj[idx].value);
	    this.bUpload						= false;
	},
	
	CmFileSetting : function  ( obj, idx )
	{
		var status			= "";
		
	    if (this.statusObj[idx].value   == "old")
	        status          = "modify";
	    else
	        status          = "add";

	    this.statusObj[idx].value	= status;
	    this.attIdObj[idx].value    = obj.attachId;
	    this.attNameObj[idx].value	= obj.attachName;
	    this.attPathObj[idx].value	= obj.attachPath;
	    this.attExtObj[idx].value	= obj.attachExt;
	    this.attSizeObj[idx].value	= obj.attachSize;

	    this.CmFileUploadEnd(idx);
	},
	CmFileDel : function  ( target_obj )
	{
	    var obj         = document.getElementsByName(target_obj.id);
	    var len         = obj.length;
	    var idx         = -1;

	    for (var i = 0; i < len ; i++ )
	    {
	        if (obj[i] == target_obj)
	        {
	            idx     = i;
	            break;
	        }
	    }

	    if (idx > -1)
	    {
	        this.CmFileDelPrc(idx);
	    }
	},
	CmFileDelPrc : function (idx)
	{
		var s_obj           = document.getElementsByName("i_arrAttStatus");   
		var recId_obj       = document.getElementsByName("i_arrRecordId");   
		var attId_obj       = document.getElementsByName("i_arrAttachId");   
		var attNm_obj       = document.getElementsByName("i_arrAttachName");   
		var attPath_obj     = document.getElementsByName("i_arrAttachPath");   
		var attExt_obj      = document.getElementsByName("i_arrAttachExt");   
	    var attSize_obj     = document.getElementsByName("i_arrAttachSize");
	    var status          = "";

	    if (this.statusObj[idx].value == "old")
	    {
	        var div_obj     = document.getElementById("cm_del_file");

	        var inHtml      = "<input type='hidden' name='i_arrDelRecordId' value='"+ recId_obj[idx].value +"'/>"
	        				+ "<input type='hidden' name='i_arrDelAttachId' value='"+ attId_obj[idx].value +"'/>"
	                        + "<input type='hidden' name='i_arrDelAttachFullPath' value='"+ attPath_obj[idx].value + attId_obj[idx].value + attExt_obj[idx].value +"'/>";

	        div_obj.innerHTML   += inHtml;
	    }

	    this.statusObj[idx].value	= "";
	    this.attIdObj[idx].value    = "";
	    this.attNameObj[idx].value	= "";
	    this.attPathObj[idx].value	= "";
	    this.attExtObj[idx].value	= "";
	    this.attSizeObj[idx].value	= "0";
	    
	    this.CmFileUploadEnd(idx);
	},
	fnUploadCntChange : function (  )
	{
		// 첨부파일 taglib 중복 사용을 위해서 ㅡ_ㅡ;;
		var sel_box		= document.getElementsByName("i_arrUpfileCount");	// 첨부파일 갯수 선택하는 <select />
		var attcnt_obj	= document.getElementsByName("i_arrAttachCnt");		// taglib 1개당 최대 첨부파일 갯수
		var sel_len		= sel_box.length;									// taglib 개수랑 동일
		var tr_obj		= document.getElementsByName("tr_fileUpload");		// 첨부파일 Tr
		var status_obj	= document.getElementsByName("i_arrAttStatus");		// 상태
		var tr_len		= tr_obj.length;
		var	count		= 0;
		var startCnt	= 0;
		var endCnt		= 0;
		
		for (var i = 0; i < sel_len; i++)
		{
			endCnt		+= parseInt(attcnt_obj[i].value, 10);
			count		= startCnt	+ parseInt(sel_box[i].value, 10);
			
			for (var j = startCnt; j < endCnt; j++)
			{
				if (j < count)
				{
					tr_obj[j].style.display		= "block";
				}
				else
				{
					tr_obj[j].style.display		= "none";
					this.CmFileDelPrc(j);
				}
			}
			startCnt	+= parseInt(attcnt_obj[i].value, 10);
		}
		
	},
	getFileSize : function ( size )
	{
		var	sReturn		= "";
		var fileSize	= parseInt(size, 10);
		
		if (fileSize >= (1 * 1024 * 1024 * 1024))
			sReturn	= this.setNumComma(Math.ceil(fileSize / 1024 / 1024 / 1024)) + "GB";
		else if (fileSize >= (1 * 1024 * 1024))
			sReturn	= this.setNumComma(Math.ceil(fileSize / 1024 / 1024)) + "MB";
		else if (fileSize >= (1 * 1024)) 
			sReturn	= this.setNumComma(Math.ceil(fileSize / 1024)) + "KB";
		else if (fileSize > 0 )
			sReturn	= "1KB";
		
		return sReturn;
	},
	setNumComma : function ( str )
	{
		 var tmpStr  = onlyNumber( "" + str );
		 var sResult = "";

	    if (tmpStr != "")
	    {
	        tmpStr  = "" + parseInt(tmpStr, 10);        // 0부터 시작할 경우 앞에 0 모두 제거

	        var len     = tmpStr.length;
	        var cnt     = 0;

	        for (i = len - 1; i >= 0; i--) 
	        {
	            if (cnt > 0 && cnt % 3 == 0 )
	            {
	                sResult  = "," + sResult;
	            }
	            sResult  = tmpStr.charAt(i) + sResult;
	            cnt++;
	        }
	    }

	    return sResult;
	}
};

