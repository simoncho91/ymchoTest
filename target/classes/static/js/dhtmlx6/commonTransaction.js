
/**
 * 기본적인 ajax통신용
 * @param option
 * @param bProgressOnEvent : 처리중 프로그래스바 표시(default:true)
 */
function fn_ajax(option, bProgressOnEvent) {
	
	// 기본 설정은 프로그레스바 표시
	if (typeof bProgressOnEvent == "undefined") bProgressOnEvent = true;
	//if (bProgressOnEvent) layoutMain.progressOn();
	
	//console.log("@@@ fn_ajax : async = " + async);
	/*
	$.each(option.postParam, function (key, data) {
	    console.log("@@@ fn_ajax : " + key + " = " + data);
	});
	*/
	if (typeof option.async == "undefined") option.async = true;
	
	$.ajax({
		    url      : option.url,
		    data     : option.postParam,
		    async    : option.async,
		    type     : "POST",
		    dataType : "json",
	        success  : function(responseData, textStatus, jqXHR){
				try{
				    fn_s_succMsg(option.postType);
				    if(option.success) option.success(responseData);
				} catch (e) {
					console.log(e);
				}
		    },error : function(jqXHR, textStatus, errorThrown) {
		    	//alert('fail');
		        fn_s_failMsg(jqXHR, textStatus, errorThrown);
		        if(option.fail) option.fail();
	        },
	        complete : function(data) {
	    	 	//if (bProgressOnEvent) layoutMain.progressOff();
	        }
		 });

	// 실행완료여부와 상관없이 지정되면 실행..
    if(option.loadCompleted) option.loadCompleted();
}

/**
 * 지정된 grid에 조회결과를 넣는다.
 * @param option
 */
function fn_ajaxGetGrid(option, bProgressOnEvent) {
	//if (typeof bProgressOnEvent == "undefined") bProgressOnEvent = true;
	if (bProgressOnEvent) layoutMain.progressOn();
	
	if (typeof option.async     == "undefined") option.async     = true;

	$.ajax({
	        url   : option.url,
	        data  : option.postParam,
	        async : option.async,
	        type  : "POST",
	        dataType: "json",
	        success: function(responseData, textStatus, jqXHR){
	        	try{
	        		// console.log(responseData);
	        		//option.gridName.clearAll();
		    		//if(option.gridName) option.gridName.parse(fn_toJsonText(responseData.result), option.parseType);
		    		option.gridName.data.parse(responseData.result.data);
		    		
		    		
			        if(option.success ) option.success(responseData);
	        	}catch (e) {
				}
	        },
		    error: function(jqXHR, textStatus, errorThrown) {
		        fn_s_failMsg(jqXHR, textStatus, errorThrown);
	            if(option.fail) option.fail();
	        },
	        complete: function(data) {
	        	//if (bProgressOnEvent) layoutMain.progressOff();
	        }
	});

    if(option.loadCompleted) option.loadCompleted();
}

/**
 * 콤보박스에 사용하기 위해 데이터를 한번에 가져옴  
 * (공통코드와 지정된 ID의 sql을 실행한 결과(컬럼명은 CD/NM으로)를 조회)
 * 
 *  - 지정된 ID : sqlid = "조회결과:실행SQL:(콤보사용추가항목);결과data:실행SQL:(콤보사용추가항목);결과data:실행SQL"
 *  - 공통코드   : grpcd = "결과data:공통코드:(사용여부추가항목):(콤보사용추가항목);결과data:공통코드:(사용여부추가항목):(콤보사용추가항목)"
 *  
 *  - 추가옵션(필수아님)
 *  	- 콤보사용추가항목 : Combo에 사용할때 맨 위에 전체/선택 항목을 추가
 *  					A = 전체
 *  					S = 선택
 *  	- 사용여부추가항목 : 공통코드를 가져올때 사용여부를 조건으로 추가
 *  					Y = 사용
 *  					N = 미사용
 *  
 *  - ex) var postParam = new Object();
		 	  postParam.sqlid = "dsUserGroup:CMSY0030.selectList:S";
		      postParam.grpcd = "dsStatus:CM003;dsUserType:CM017:Y:S";
				
			  fn_ajaxGetData({ postParam           : postParam
				             , success             : function(responseData) { 
							           	  cboUserGrp.load(responseData.dsUserGroup); 
							           	  cboStatus.load(responseData.dsStatus);
				                 }
			  });
 *  - 콤보박스 생성용으로 options이 포함되어 있지만 다른용도로 사용가능
 *  	ex) vEXIST_YN = responseData.dsCHK.options[0].EXIST_YN;
 *  
 * @param option
 */
function fn_s_ajaxGetDsForCombo(option) {
	if (typeof option.async == "undefined") option.async = true;

	option.url      = "/dhtmlx6/common/selectDsForCombo.do";
	option.postType = ""; // 처리결과 메세지는 필요없음
	
	fn_ajax(option, true);
}

/**
 * Form의 데이터를 저장하기 위해 전송
 * @param option
 * @param bProgressOnEvent
 */
function fn_s_ajaxSaveForm(option, bProgressOnEvent) {
	if (typeof bProgressOnEvent == "undefined") bProgressOnEvent = true;
	//if (bProgressOnEvent) layoutMain.progressOn();
	if (typeof option.async == "undefined") option.async = true;

	$.ajax({
		     url: option.url,
		     data: option.postParam,
		     async: option.async,
		     type: "POST",
		     dataType: "json",
		     success: function(responseData, textStatus, jqXHR){
				try{
					 // msgCode fail일때 처리 추가 20180907 (사용자등록 validation체크에서 사용)
		     		 if(responseData == 'fail'){
		     			 //alert(responseData.msg);
		     			 return;
		     		 }
					fn_s_succMsg(option.postType);
				    if(option.success) option.success(responseData);
				} catch (e) {
					console.log(e);
				}
		     },
		     error : function(jqXHR, textStatus, errorThrown) {
		         fn_s_failMsg(jqXHR, textStatus, errorThrown);
		         if(option.fail) option.fail();
		     },
	         complete: function(data) {
	        	 //if (bProgressOnEvent) layoutMain.progressOff();
	         }
	});

	if(option.loadCompleted) option.loadCompleted();
}

/**
*
* Transaction 처리 후 성공 메세지
*/
function fn_s_succMsg(postType){
	 //console.log("postType = " + postType);
	 switch(postType) {
	 	case 'save': 
	 		fn_s_message('저장 되었습니다.');
	 		break;
	 	case 'exec': 
	 		fn_s_message('처리 되었습니다.');
	 		break;
	 	case 'del': 
	 		fn_s_message('삭제 되었습니다.');
	 		break;
	 	case 'mail': 
	 		fn_s_message('발송 되었습니다.');
	 		break;
	 	default:
	 		break;
	 }
}

/**
 * Grid의 데이터를 저장하기 위해 전송
 *  - contentType : "application/json"
 * @param option
 * @param bProgressOnEvent
 */
function fn_s_ajaxSaveGrid(option, bProgressOnEvent) {
	if (typeof bProgressOnEvent == "undefined") bProgressOnEvent = true;
	//if (bProgressOnEvent) layoutMain.progressOn();
	if (typeof option.async == "undefined") option.async = true;

	// json 변환처리
	option.postParam = fn_toJsonText(option.postParam);

	$.ajax({
		url: option.url,
        data: option.postParam,
		async: option.async,
		type: "POST",
		contentType : "application/json",
		dataType: "json",
		success: function(responseData, textStatus, jqXHR){
			try{
				fn_s_succMsg(option.postType);
			    if(option.success) option.success(responseData);
			} catch (e) {
				console.log(e);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			fn_s_failMsg(jqXHR, textStatus, errorThrown);
			if(option.fail) option.fail();
		},
        complete: function(data) {
        	//if (bProgressOnEvent) layoutMain.progressOff();
        }
	});

	if(option.loadCompleted) option.loadCompleted();
}


/*
 * Transaction 처리 후 에러 메세지
 */
function fn_s_failMsg(jqXHR, textStatus, errorThrown){

	// return 코드 확인
	if (jqXHR.status == 403) {
		alert("세션이 만료 되었습니다.");
		$(location).attr('href',"/");
	} else if (jqXHR.status == 404) {
		alert("요청페이지를 찾을 수 없습니다.");
	} else {
		console.log("jqXHR.responseText = " + jqXHR.responseText)
		var vErrorMessage = fn_toJsonObject(jqXHR.responseText).ErrorMsg;
		if (typeof layoutMain != "undefined") {
			dhx.alert({
			    header:"알림",
			    text:text,
			    buttonsAlignment:"center",
			    buttons:["확인"]
			});
		} else {
			alert(vErrorMessage);
		}
	}
}