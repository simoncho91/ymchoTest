/*
 * 첨부파일 처리
 */

var attachObj;		

var uploadUrl = "/attach/dhtmlxUpload.do"
	
/**
 * 첨부파일 컴포넌트 초기화
 * @param layout
 * @param isDisable
 */
function fn_attachInit(layout, isMultiple, isEditmode) {
	var multiple = false;
	if(isMultiple) multiple = isMultiple;		
	
	var myLayout = layout;
	myLayout.setText('첨부파일 (변경시 즉시 반영됨)');
	
	if (isEditmode) {
		attachObj = myLayout.attachVault({
		    uploadUrl	: uploadUrl,							// uploadUrl
		    swfPath		: "/dhtmlx/codebase/dhxvault.swf",		// path to flash uploader
		    multiple	: isMultiple							// 다중 첨부 여부
		});
	}else{
		attachObj = myLayout.attachVault({
		    uploadUrl	: uploadUrl,							// uploadUrl
		    swfPath		: "/dhtmlx/codebase/dhxvault.swf",		// path to flash uploader
		    multiple	: false,									// 다중 첨부 여부
		    buttonClear  : false,
		    buttonUpload  : false,
		    autoStart    : false,
		    autoRemove   : false
		});
		
		$("div.dhx_vault_button").css("display", "none");
	}
	
	
	// 업로드 URL 지정( 신규가 아닐경우 atchNo를 붙여준다)
	if(atchNo != ''){
		attachObj.setURL(uploadUrl+"?atchNo="+atchNo);
	}
	
	// 파일 업로드 전 권한 체크
	attachObj.attachEvent("onBeforeFileAdd",function(realName){
	    if(!isEditmode){
	    	alert('첨부 권한이 없습니다.');
	    	return false;
	    }
	    return true;
	});
	
	// 파일 삭제 전 권한 체크
	attachObj.attachEvent("onBeforeFileRemove", function(realName, serverName){
		if(!isEditmode){
	    	alert('첨부 삭제 권한이 없습니다.');
	    	return false;
	    }
	    return true;
	})
	
	//if(isDisable)	attachObj.disable();
	if(isEditmode == undefined)
		isEditmode = true;
	
	// 첨부파일 업로드 완료 후 이벤트(업로드된 파일을 참조하기 위한 키를 업데이트한다.)
	attachObj.attachEvent("onUploadFile", function(file, extra){
		
		atchNo = extra.atchNo;
		
		// 업로드 URL 지정(신규가 아닐경우 atchNo를 붙여준다)	*** 최초 업로드 이후 에 첨부키 값을 파라미터로 지정해준다.***
		if(atchNo != ''){
			attachObj.setURL(uploadUrl+"?atchNo="+atchNo);
		}
		
	});

	// 첨부파일 삭제버튼 클릭 이벤트(서버에 저장된 물리 파일과 DB의 데이터를 삭제한다.)
	attachObj.attachEvent("onFileRemove", function(file){
		
		//console.log('onFileRemove : '+JSON.stringify(file));
		
		var postParam = new Object();
	    postParam.atchNoSeq 	= file.serverName;
	    
	    fn_ajax({  url                 : "/attach/deleteAttach.do"
	             , postParam           : postParam
	             , success             : function(responseData) {
	            							//alert('"'+file.name + '" 파일이 삭제되었습니다.');
									     }
				 , error			   : function(jqXHR, textStatus, errorThrown) {
									        //fn_failMsg(jqXHR, textStatus, errorThrown);
	            							alert('"'+file.name + '" 파일 삭제에 실패하였습니다.');
									        fn_attachSearch();
									     }
	    });
	    
	});
	
}

/**
 * 첨부파일 목록 조회
 */
function fn_attachSearch(){
	attachObj.clear();
	
	var postParam = new Object();
    //postParam.atchTpCd 		= atchTpCd;
    //postParam.atchCd 		= atchCd;
    postParam.atchNo 		= atchNo;
    
    fn_ajax({  url                 : "/attach/selectAttachList.do"
             , postParam           : postParam
             , success             : function(responseData) {
            	 					 	fn_drawAttachList(responseData);
								     }
    });
}

/**
 * 조회 결과를 이용한 첨부파일 목록 생성
 * @param responseData
 */
function fn_drawAttachList(responseData){
	var data =  responseData.result.data;
    var cnt =  data.length;

    var strAttach;
    for(var i = 0; i < cnt; i++){
    	 	strAttach =  {
			        name		: data[i].ORG_FILE_NM,
			        serverName	: data[i].ATCH_NO_SEQ,
			        size		: data[i].FILE_SIZE
			  };
    	 	
    	attachObj.addFileRecord(strAttach, "uploaded");
    }
    
    attachObj.setDownloadURL("/attach/fileDhtmlxDownload.do?fileName={serverName}");
	
}