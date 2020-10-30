/*
 * 팝업처리
 */

var dhxWins;

/**
 * 팝업을 생성하는 내부용 함수
 * @returns {Boolean}
 */
function fn_dhxWinInIt() {
	//윈도우 수 제한
	var p = 0;
	try{
		dhxWins.forEachWindow(function(){p++;});
		if (p>0) {
			//이벤트 없음 그냥 한개만 떠있게 요구상항 있을 경우 수정
			//alert("Too many windows");
			return false;
		}
	}catch (e) {
		//윈도우 셋팅
		dhxWins = new dhtmlXWindows();
		dhxWins.attachViewportTo("layoutObj");
	}
	return true;
}


/* 
*  URL기반으로 팝업을 생성
*  popObject : 팝업정보
*       필수요소  
*  		이름 : POP_NM
*  		주소 : URL
*  		가로 : POP_SIZE_X
*  		세로 : POP_SIZE_Y
*/
function fn_dhxWinUrlCreate(popObject){

	// 팝업 윈도우 생성 및 체크
	if (!fn_dhxWinInIt()) return;
	
	//윈도우 위치 셋팅
	var dw = dhxWins.createWindow("dhxWin", 68, 66, popObject.POP_SIZE_X, popObject.POP_SIZE_Y);
	dhxWins.window("dhxWin").setText(popObject.POP_NM);
	
	var returnObj = dw.attachURL(popObject.URL);
	console.log("returnObj = " + returnObj);
}


/* 공통코드를 이용한 팝업 윈도우 생성
*  	fmName : 폼네임
*  	codeName : 코드 값이 들어갈 히든 네임 
*  	name : 코드명이 셋팅 될 인풋 네임
*  	grpCd : 공통 코드값(FI012)
*  	func : 함수명 (fn_test())
*  	paramObj : commCd 코드명 , commNm 코드 네임순으로 (ex code:SE001, name:명)
*/

/**
 * 공통코드를 조회하기 위한 팝업 윈도우 생성
 * @param reqObject : Form
 * @param resCd     : 코드 값이 들어갈 Input 네임 
 * @param resNm     : 코드 명이 들어갈 Input 네임 
 * @param grpCd     : 공통 코드값(FI012)
 * 
 * @param searchKeyword  : 검색어 
 * @param func      : 콜백 함수명 (fn_test())
 */
function fn_openCodePopup(reqObject, resCd, resNm, grpCd, searchKeyword, func ) {
	
	var postParam = new Object();

	// 팝업 윈도우 생성 및 체크
	if (!fn_dhxWinInIt()) return;
	
	//윈도우 위치 셋팅
	var dw = dhxWins.createWindow("codeWin", 68, 66, 700, 500);
	dhxWins.window("codeWin").setText("공통코드");

	// 레이아웃 셋팅
	var dhxWinsLayout = dw.attachLayout("2E");
	
	//검색 부분 셋팅
	var a = dhxWinsLayout.cells('a');
	a.setText('검색조건');
	a.setHeight('92');
	var str =  [ { type:'settings', position:'label-left', labelWidth:'120', inputWidth:'100', offsetLeft:'20', offsetTop:'14' },
	             { type:"input" , name:"COMM_CD_NM", label:"코드/명" , value:searchKeyword},
	             { type:"newcolumn"   },
	             { type: "combo", label: "사용구분", name: "USE_YN"},
	             { type:"newcolumn"   },
	             { type: "button", value: "검색", name: "dhxWinsSearch" , offsetTop:'8'} 
	          ];
	var dhxWinsSearchForm = a.attachForm(str);
  
	//검색 버튼 클릭시 
	dhxWinsSearchForm.attachEvent("onButtonClick", function(name){
		//검색
		if(name=="dhxWinsSearch"){
			dhxWinsLayout.progressOn();
			postParam = fn_getPostParam(dhxWinsSearchForm);
			postParam.GRP_CD = grpCd;
			fn_ajaxGetGrid({ gridName            : dhxWinsGrd
				, url                 : "/dhtmlx/admin/code/selectCodeList.do"
  	            , postParam           : postParam
  	            , success             : function() { dhxWinsLayout.progressOff(); 	}
				, fail             	  : function() { dhxWinsLayout.progressOff();   }
			});
		}
	});
  
	//검색 input 엔터키 이벤트
	dhxWinsSearchForm.attachEvent('onKeyDown', function(inp, event, name){
		if ( event.which == 13 ) {
			//요걸 함수로 ?
			dhxWinsLayout.progressOn();
			postParam = fn_getPostParam(dhxWinsSearchForm);
			postParam.GRP_CD = grpCd;
			fn_ajaxGetGrid({ gridName            : dhxWinsGrd
				, url                 : "/dhtmlx/admin/code/selectCodeList.do"
  	            , postParam           : postParam
  	            , success             : function() { dhxWinsLayout.progressOff(); }
				, fail             	  : function() { dhxWinsLayout.progressOff(); }
			});
		} 
	});
  
	//그리드 셋팅
	var b = dhxWinsLayout.cells('b');
	b.setText('코드');
	var dhxWinsGrd = b.attachGrid();
	dhxWinsGrd.setHeader(["NO","코드","코드명","사용여부"]);
	dhxWinsGrd.setColumnIds("NO,COMM_CD,COMM_CD_NM,USE_YN");
	dhxWinsGrd.setColTypes("ro,ro,ro,ro,ro,ro,ro");
	dhxWinsGrd.setInitWidths('40,*,*,*');
  
	fn_initGrid(dhxWinsGrd);
	
	//그리트 더블 클릭시 이벤트 -> 값을 리턴받고자 하는곳에 보냄
	dhxWinsGrd.attachEvent("onRowDblClicked",function(id,ind) {
		/* 그리드에서 호출하는 경우의 처리하는 부분 제외
		// 리턴 대상이 그리드일 때는 dp처리도 필요함
		if (window.dhtmlXGridObject != null && fmName instanceof window.dhtmlXGridObject) {
			try{
				var codeNameArray = name.split(",");
				for(var i=0;i<codeNameArray.length;i++){
	        		var setCellValArray = codeNameArray[i].split(":");
	        			//fmName.selectCell(codeName, setCellValArray[1]);
		        		//fmName.editCell();
	        			dpName.setUpdated(codeName,"updated");
						fmName.cells(codeName, setCellValArray[1]).setValue(setCellValArray[0]=="name"?dhxWinsGrd.cells(id,2).getValue():dhxWinsGrd.cells(id,1).getValue());
						//fmName.editStop();	
				}
			}catch (e) {
				console.log("e = "+e);
			}
		// 리턴 대상이 폼일때는 지정된곳에 값만 리턴하고 끝
		}else{
		*/
		//폼
		reqObject.setItemValue(resCd, dhxWinsGrd.cells(id,1).getValue());
		reqObject.setItemValue(resNm, dhxWinsGrd.cells(id,2).getValue());
		//}
	  	
		eval(func);
  		dhxWins.unload();
  		
	});
  
	//윈도우 오픈시 그리드 표시 
	dhxWinsLayout.progressOn();
	//postParam = fn_getPostParam(dhxWinsSearchForm);
	postParam.GRP_CD     = grpCd;
	postParam.COMM_CD_NM = searchKeyword;
	fn_ajaxGetGrid({ gridName            : dhxWinsGrd
			, url                 : "/dhtmlx/admin/code/selectCodeList.do"
			, postParam           : postParam
	        , success             : function() { dhxWinsLayout.progressOff(); }
			, fail             	  : function() { dhxWinsLayout.progressOff(); }
	});
}


// 이전버전 레이어팝업기능 추가 - 수정 필요 

//공통팝업
function fn_layerPopGrid(obj, paramForm){

	var rtnWinsObj = new Object();

	var dhxWins;			// 레이어팝업 객체
	var dhxWinsGrd;			// 레이어팝업 그리드 객체
	var dhxWinsBtns;		// 레이어팝업 버튼 객체


//	console.log("parentId     		: "+obj.parentId);			// 부모 레이아웃 ID
//	console.log("searchUse      	: "+obj.searchUse);			// 검색창 사용여부
//	console.log("searchYnUse      	: "+obj.searchYnUse);		// 검색창 사용여부 셀렉트박스
//	console.log("searchColumnNm		: "+obj.searchColumnNm);	// 검색 필드명
//	console.log("searchColumnCd		: "+obj.searchColumnCd);	// 검색 필드코드
//	console.log("searchEtcParamNm	: "+obj.searchEtcParamNm);	// 검색 기타 파라미터 명
//	console.log("searchEtcParamCd	: "+obj.searchEtcParamCd);	// 검색 기타 파라미터 코드
//	console.log("btnType      		: "+obj.btnType);			// 버튼영역 타입 (0:사용X, 1: 확인, 2: 확인/취소, 3: 닫기)
//	console.log("title        		: "+obj.title);				// 팝업 타이틀
//	console.log("modal        		: "+obj.modal);				// 모달 여부
//	console.log("option    			: "+obj.option);			// [팝업 ID, 팝업 넓이, 팝업 높이, x좌표, y좌표]
//	console.log("grid.title	  		: "+obj.gridTitle);			// 그리드 타이틀
//	console.log("grid.width	  		: "+obj.gridWidth);			// 그리드 넓이
//	console.log("grid.colHeader		: "+obj.gridColHeader);		// 그리드 컬럼헤더
//	console.log("grid.colId	  		: "+obj.gridColId);			// 그리드 컬럼ID
//	console.log("grid.colType		: "+obj.gridColType);		// 그리드 컬럼Type
//	console.log("grid.colWidth		: "+obj.gridColWidth);		// 그리드 컬럼width
//	console.log("grid.useMultiSelect: "+obj.gridUseMultiSelect);// 그리드 멀티셀렉트 사용여부
//	console.log("grid.searchUrl		: "+obj.gridSearchUrl);		// 조회 URL


	var btnType 		= '0';
	var modal 			= true;
	var posX 			= 0;
	var posY 			= 0;
	var searchUse		= false;
	var searchYnUse		= false;
	var useMultiSelect 	= false;
	var parentId		= 'layoutObj';

	// 버튼타입 default 사용안함.
	if(obj.btnType != undefined){
		btnType = obj.btnType;
	}

	// 모달여부 default 사용
	if(obj.modal != undefined){
		modal = obj.modal;
	}

	// Parent ID default layoutObj
	if(obj.parentId != undefined){
		parentId = obj.parentId;
	}

	// 팝업 positon default셋팅
	if(obj.option[1] != undefined){
		posX = window.innerWidth/2 - obj.option[1]/2;
	}
	if(obj.option[2] != undefined){
		posY = window.innerHeight/2 - obj.option[2]/2;
	}

	// 팝업 position 사용자 지정
	if(obj.option[3] != undefined){
		posX = obj.option[3];
	}
	if(obj.option[4] != undefined){
		posY = obj.option[4];
	}

	// 검색영역 사용여부
	if(obj.searchUse != undefined){
		searchUse = obj.searchUse;
	}

	// 검색영역 사용여부 셀렉트박스
	if(obj.searchYnUse != undefined){
		searchYnUse = obj.searchYnUse;
	}

	// 그리드 ROW 멀티선택 가능여부
	if(obj.gridUseMultiSelect != undefined){
		useMultiSelect = obj.gridUseMultiSelect;
	}

	// 팝업 윈도우 생성
 dhxWins = new dhtmlXWindows();
 dhxWins.attachViewportTo(parentId);

 // 윈도우 위치 셋팅, modal여부 지정
 var dw = dhxWins.createWindow(obj.option[0], posX, posY, obj.option[1], obj.option[2]);
 dhxWins.window(obj.option[0]).setText(obj.title);
 dhxWins.window(obj.option[0]).setModal(modal);

 var layoutSearch;
 var layoutGrid;
 var layoutBtn;

 var frmLayoutSearch;

 // 레이아웃 셋팅 (1C, 2E, 3E)
 if(btnType == '0' && !searchUse){		// 그리드
     dhxWinsLayout = dw.attachLayout("1C");
     layoutGrid = dhxWinsLayout.cells('a');
 }else if(btnType == '0' && searchUse){	// 검색, 그리드
     dhxWinsLayout = dw.attachLayout("2E");
     layoutSearch = dhxWinsLayout.cells('a');
     layoutGrid = dhxWinsLayout.cells('b');
 }else if(btnType != '0' && !searchUse){	// 그리드, 버튼
     dhxWinsLayout = dw.attachLayout("2E");
     layoutGrid = dhxWinsLayout.cells('a');
     layoutBtn = dhxWinsLayout.cells('b');
 }else if(btnType != '0' && searchUse){	// 검색, 그리드, 버튼
     dhxWinsLayout = dw.attachLayout("3E");
     layoutSearch = dhxWinsLayout.cells('a');
     layoutGrid = dhxWinsLayout.cells('b');
     layoutBtn = dhxWinsLayout.cells('c');
 }

 // 그리드 생성
 //var pa = dhxWinsLayout.cells('a');
 layoutGrid.setText(obj.gridTitle);
 dhxWinsGrd = layoutGrid.attachGrid();
 dhxWinsGrd.setHeader(obj.gridColHeader);
 dhxWinsGrd.setColumnIds(obj.gridColId);
 dhxWinsGrd.setColTypes(obj.gridColType);
 dhxWinsGrd.setInitWidths(obj.gridColWidth);
 dhxWinsGrd.enableMultiselect(useMultiSelect);
 fn_initGrid(dhxWinsGrd);

 // 검색 영역 생성
 if(searchUse){

     layoutSearch.setText('검색조건');
     layoutSearch.setHeight(SearchFormHeight[1]);

     var strLabel =  [
									                	/*{ type:"combo" , name:"USE_YN" , label: "사용여부", readonly:true}*/
						];

     // 버튼
     strLabel.unshift({ type:"button"  , name:"search"    	, value:"검색", width:100, height:10, offsetTop:"7"});
     // 사용여부
     if(searchYnUse){
 		strLabel.unshift({ type:"newcolumn"   });
     	strLabel.unshift({ type:"combo" , name:"USE_YN" , label: "사용여부", readonly:true, offsetTop:"7"});
     }

    	// 조회조건 영역
 	var colCnt = obj.searchColumnNm.length;
 	var colVal = '';

     for(var z=0; z<colCnt; z++) {
     	colVal = paramForm.getItemValue(obj.searchColumnCd[z]);

     	strLabel.unshift({ type:"newcolumn"   });
     	if(colVal.length > 0){
     		strLabel.unshift({ type:"input" , name:obj.searchColumnCd[z] , label:obj.searchColumnNm[z], offsetTop:"7", value: colVal});
     	}else{
     		strLabel.unshift({ type:"input" , name:obj.searchColumnCd[z] , label:obj.searchColumnNm[z], offsetTop:"7"});
     	}


     }

     strLabel.unshift(SearchFormStyle4);
     frmLayoutSearch = layoutSearch.attachForm(strLabel);
     if(searchYnUse){
     	frmLayoutSearch.getCombo("USE_YN").load("/view/dhtmlx/data/comboYNa.json");
     }

     // 검색 버튼
     frmLayoutSearch.attachEvent("onButtonClick", function(name){
     	if(name == 'search'){	// 검색
     		if(frmLayoutSearch != undefined){
     	    	paramForm = fn_addSearchParam(obj, paramForm, frmLayoutSearch);
     	    }
     		fn_ajaxLayerPopGrid(obj, paramForm, dhxWinsGrd);
     	}
     });
 }

 // 버튼 영역 생성
 if(btnType != '0'){

     var strLabel;
     //var btnPos = obj.option[1] - 300;
     layoutBtn.setHeight(78);

     if(btnType == '1'){
     	strLabel = [
                     {type: "label", list: [
                         { type:"button"  , name:"ok"    , value:"확인", width:120, height:10, className:"offsetRight"}
                     ]}
 	            ];
     }else if(btnType == '2'){
     	strLabel = [
                     {type: "label", list: [
                         { type:"button"  , name:"ok"    	, value:"확인", width:100, height:10},
                         { type:"newcolumn"   },
                         { type:"button"  , name:"cancel"    , value:"취소", width:100, height:10}
                     ]}
 	            ];
     }else if(btnType == '3'){
     	strLabel = [
                     {type: "label", list: [
                         { type:"button"  , name:"close"    , value:"닫기", width:120, height:10, className:"offsetRight"}
                     ]}
 	            ];
     }

     strLabel.unshift(RegisterFormStyle1);
     dhxWinsBtns = layoutBtn.attachForm(strLabel);
 }

 if(frmLayoutSearch != undefined){
 	paramForm = fn_addSearchParam(obj, paramForm, frmLayoutSearch);
 }
 fn_ajaxLayerPopGrid(obj, paramForm, dhxWinsGrd, layoutSearch);

 rtnWinsObj.dhxWins 		= dhxWins;
 rtnWinsObj.dhxWinsGrd 	= dhxWinsGrd;
 rtnWinsObj.dhxWinsBtns 	= dhxWinsBtns;
 return rtnWinsObj;
}

//공통팝업 그리드 데이터 조회
function fn_ajaxLayerPopGrid(obj, paramForm, dhxWinsGrd, layoutSearch){
 var postParam = fn_getPostParam(paramForm);

 // 검색내용 파라미터 셋팅 (기타) 공통코드조회팝업 등 form영역에 표시되지않는 파라미터 값이 필요할 경우 postParam에추가한다.
 if(obj.searchEtcParamNm != undefined){
 	var etcParamCnt = obj.searchEtcParamNm.length;
		for(var z=0; z<etcParamCnt; z++) {
			eval('postParam.'+obj.searchEtcParamNm[z]+' = obj.searchEtcParamCd[z]');

		}
 }
	//console.log("paramForm : "+JSON.stringify(paramForm));

 fn_ajaxGetGrid({ gridName         : dhxWinsGrd
          , url                 : obj.gridSearchUrl
          , postParam           : postParam
          , success             : function() {

								                }
 });
}



//공통팝업 TextArea
function fn_layerPopTextArea(obj, paramForm){

	var rtnWinsObj = new Object();

	var dhxWins;			// 레이어팝업 객체
	var dhxWinsFrm;			// 레이어팝업 폼 객체
	var dhxWinsBtns;		// 레이어팝업 버튼 객체


//	console.log("parentId     		: "+obj.parentId);			// 부모 레이아웃 ID
//	console.log("searchEtcParamNm	: "+obj.searchEtcParamNm);	// 검색 기타 파라미터 명
//	console.log("searchEtcParamCd	: "+obj.searchEtcParamCd);	// 검색 기타 파라미터 코드
//	console.log("btnType      		: "+obj.btnType);			// 버튼영역 타입 (0:사용X, 1: 확인, 2: 확인/취소, 3: 닫기)
//	console.log("title        		: "+obj.title);				// 팝업 타이틀
//	console.log("modal        		: "+obj.modal);				// 모달 여부
//	console.log("option    			: "+obj.option);			// [팝업 ID, 팝업 넓이, 팝업 높이, x좌표, y좌표]
//	console.log("formTitle	  		: "+obj.formTitle);			// 폼 타이틀
//	console.log("formValue	  		: "+obj.formValue);			// 폼 타이틀
//	console.log("formSearchUrl		: "+obj.formSearchUrl);		// 조회 URL


	var btnType 		= '0';
	var modal 			= true;
	var posX 			= 0;
	var posY 			= 0;
	var parentId		= 'layoutObj';

	// 버튼타입 default 사용안함.
	if(obj.btnType != undefined){
		btnType = obj.btnType;
	}

	// 모달여부 default 사용
	if(obj.modal != undefined){
		modal = obj.modal;
	}

	// Parent ID default layoutObj
	if(obj.parentId != undefined){
		parentId = obj.parentId;
	}

	// 팝업 positon default셋팅
	if(obj.option[1] != undefined){
		posX = window.innerWidth/2 - obj.option[1]/2;
	}
	if(obj.option[2] != undefined){
		posY = window.innerHeight/2 - obj.option[2]/2;
	}

	// 팝업 position 사용자 지정
	if(obj.option[3] != undefined){
		posX = obj.option[3];
	}
	if(obj.option[4] != undefined){
		posY = obj.option[4];
	}

	// 팝업 윈도우 생성
 dhxWins = new dhtmlXWindows();
 dhxWins.attachViewportTo(parentId);

 // 윈도우 위치 셋팅, modal여부 지정
 var dw = dhxWins.createWindow(obj.option[0], posX, posY, obj.option[1], obj.option[2]);
 dhxWins.window(obj.option[0]).setText(obj.title);
 dhxWins.window(obj.option[0]).setModal(modal);

 //var layoutSearch;
 var layoutFrm;
 var layoutBtn;

 //var frmLayoutSearch;

 // 레이아웃 셋팅 (1C)
 dhxWinsLayout = dw.attachLayout("1C");
 layoutFrm = dhxWinsLayout.cells('a');

 // Form 데이터 조회
 var rtnData = fn_ajaxLayerPopTextArea(obj, paramForm);

 /* Form-마스터 */
	layoutFrm.setText(eval('rtnData.'+obj.formTitle));
	layoutFrm.setWidth(RegisterFormWidth[1]);
	layoutFrm.setHeight(SearchFormHeight[11]);
	var str = [
           {type: "label", list: [
                               		{ type: "editor", 		name: "POP_TEXTAREA_VAL", value: eval('rtnData.'+obj.formValue), inputWidth: obj.option[1]-80, inputHeight: obj.option[2]-90, toolbar: false }
    	  	  ]}
         ];
	str.unshift(RegisterFormStyle2);
	dhxWinsFrm = layoutFrm.attachForm(str);

 rtnWinsObj.dhxWins 		= dhxWins;
 rtnWinsObj.dhxWinsFrm 	= dhxWinsFrm;
 return rtnWinsObj;
}

//공통팝업 TEXTAREA 데이터 조회
function fn_ajaxLayerPopTextArea(obj, paramForm){
  var postParam = fn_getPostParam(paramForm);
  var rtnResData = null;

  fn_ajax({  url                 : obj.formSearchUrl
	  			,async: false
		         , postParam           : postParam
		         , success             : function(responseData) {
		        		rtnResData = responseData.result.data;
		         }
  });
  return rtnResData;
}
// 이전버전 레이어팝업기능 추가 - 수정 필요 

