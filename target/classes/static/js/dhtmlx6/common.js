/*
 * dhtmlx공통 처리 함수
 */
function fn_etc1(){
//	var tabId=parent.mdiObj.tabbar.getActiveTab();
//	parent.mdiObj.pages[tabId].postParam=undefined;
	var id = $('#refMenuCd').text();
	delete localStorage[id];
	var url = location.href.split('&')[0];
	location.href=url;
}

/**
 * 페이지 초기화 함수
 * @param bEnterEvent : 조회폼(frmSearch)에 엔터키 입력 시 조회 이벤트 여부(기본값 : true)
 */


function fn_s_init(bEnterEvent) {
	if (typeof bEnterEvent == "undefined") bEnterEvent = true;
	/*
	layoutMain.setSizes();
	$(window).resize(function() { layoutMain.setSizes(); });
	
	layoutMain.setOffsets({
	    top: 0,
	    right: 5,
	    bottom: 5,
	    left: 5
	});
	*/
	
	/*
	//레이아웃 셋팅~
	layoutMain.forEachItem(function(cell) {
		
		var layoutObject = layoutMain.cells(cell.getId()).getAttachedObject();
		//console.log("cell.getId() -=" + cell.getId()); 
	
		// 레아웃의 구성요소가 form일때는 따로 초기화작업을 함
		if (layoutObject instanceof window.dhtmlXForm) {
			fn_initForm(layoutObject);
			
		// sub layout을 가질때..
		} else if (window.dhtmlXLayoutObject != null && layoutObject instanceof window.dhtmlXLayoutObject) {
			//console.log("추가 layout을 가질때..");

			// 헤드 더블클릭 이벤트 부여..
			layoutObject.attachEvent("onDblClick", function(name){
				//writeLog("event onDblClick called for mainLayout, cell "+name);
				fn_undockCell(this, name);
			});
			
			layoutObject.forEachItem(function(subCell) {

				var layoutObjectSub = layoutObject.cells(subCell.getId()).getAttachedObject();
				//console.log("subCell.getId() -=" + subCell.getId()); 
				
				// form일때.
				if (layoutObjectSub instanceof window.dhtmlXForm) {
					fn_initForm(layoutObjectSub);
				}
			});
		}
	});

	// 조회폼(frmSearch)에 enter key 입력 시 조회 이벤트 발생처리
	if (bEnterEvent) {
		if ((typeof frmSearch != "undefined") && (typeof fn_search != "undefined") ){
			// 평범한 input
			frmSearch.attachEvent('onKeyDown', function(inp, event, name){
				try{
					var nameSplit = name.split("_");
					
					var ent = true;
					
					if(nameSplit.length>1){
						if(nameSplit[nameSplit.length-1] == "POP"){
							ent = false;
						}
					}
					
					if(ent){
		            	if ( event.which == 13 ) {
		            		fn_search();
		            	} 
					}
				}catch (e) {
					//console.log("event.which  e = "+e);
				}
		    });
			
			// combo인 경우
			try{
				frmSearch.forEachItem(function(name) {
					if (frmSearch.getItemType(name)=="combo"){
						frmSearch.getCombo(name).attachEvent("onKeyPressed", function(keyCode){
				            if ( event.which == 13 ) {
				            	fn_search();
				            } 
						 });
					}				
				});
			}catch (e) {
				//console.log("set enter event e = "+e);
			}
		}
	}
	*/
}

/**
 * 해당 Form에서 서버에 전송하기 위한 데이터를 추출
 * @param form
 * @returns
 */
function fn_getPostParam(form) {
	var postParam = form.getValue();
	//console.log("dhtmlx6 : "+JSON.stringify(postParam));
	
	return postParam;
	
	form.forEachItem(function(name){
		//calendar 데이터 포멧 변경
		if(form.getItemType(name)=="calendar"){
			postParam[name] = form.getCalendar(name).getFormatedDate("%Y%m%d");
			
		//combo 데이터 포멧변경(checkCombo사용)
		//checkCombo의 경우, 체크가 우선 선택은 체크가 다 풀어졌을때 사용
		}else if(form.getItemType(name)=="combo"){
			var checked_array = form.getCombo(name).getChecked();
			var new_param = "";
			if(checked_array.length > 0) {
				for(var i=0; i<checked_array.length; i++) {
					if (i>0) new_param += ",";
					new_param = new_param + "'"+checked_array[i] + "'";
				}
				postParam[name] = new_param;
			}
		}
	});
    return postParam;
}

/**
 * dhtmlx메세지 처리
 * @param text : 내용
 * @param messageType : error로 지정여부
 */
function fn_s_message(text, messageType) {
	if (typeof messageType == "undefined") messageType = "";
	if (messageType == "error") {
		dhx.message({ 
		    text:text, 
		    icon:"dxi-clock", 
		    css:"expire", 
		    expire:1000
		});
	} else {
		dhx.message({ 
			text:text, 
		    icon:"dxi-clock", 
		    css:"expire", 
		    expire:1000
		});
	}
}

/**
 * dhtmlx6 form 내부에 selectBox 데이터 바인딩
 * @param frm : from id
 * @param selectBoxIds : ID 배열
 * @param dsVal : 데이터셋
 */
function fn_s_selectboxPaint(frm, selectBoxIds, dsVal){
	for(i=0 ; i < selectBoxIds.length ; i++){
		frm.getItem(selectBoxIds[i]).config.options = dsVal.options;
		frm.getItem(selectBoxIds[i]).config.disabled = true;
		frm.paint();
		frm.getItem(selectBoxIds[i]).config.disabled = false
		frm.getItem(selectBoxIds[i]).clear();
	}
}

/**
 * dhtmlx6 Grid 내부에 selectBox 데이터 바인딩
 * @param grd : grid id
 * @param selectBoxId : ID
 * @param dsVal : 데이터셋
 */
function fn_s_selectboxPaintForGrid(grd, selectBoxId, dsVal){
    var dsGridsel = [];                                                
    
    for(var i = 0; i < dsVal.options.length; i++ ){
        dsGridsel[i] =dsVal.options[i].content;   
    } 
    grd.getColumn(selectBoxId).options = dsGridsel;
}

/**
 * dhtmlx6 grid 데이터 form바인딩 함수
 * @param grid : grid 객체
 * @param frm : form객체
 */
function fn_s_gridToForm(grid, frm){
	var selectedCell = grid.selection.getCell();
	if (selectedCell != undefined){
	
		frm.clear();
		frm.forEach(function(item, index, array) {
		    var itemId = item.config.id;
		    frm.getItem(itemId).setValue(selectedCell.row[itemId]);
	    
		});
	
	}
}

/**
 * dhtmlx6 grid 데이터 form바인딩 함수
 * @param grid : grid 객체
 * @param frm : form객체
 */
function fn_s_gridToForm_bool(grid, frm){
	var selectedCell = grid.selection.getCell();
	var data = '';
	if (selectedCell != undefined){
	
		frm.clear();
		frm.forEach(function(item, index, array) {
		    var itemId = item.config.id;
			if(itemId.indexOf('use_yn') > -1 || itemId.indexOf('pao_yn') > -1){
				data=selectedCell.row[itemId]=='Y'?true:false;
			}else{
				data=selectedCell.row[itemId];
			}
		    frm.getItem(itemId).setValue(data);
	    
		});
	
	}
}
/**
 * dhtmlx 알림창 처리
 * @param text
 */
function fn_s_alertMsg(text,callback,callBackParam){
	dhx.alert({
	    header:"알림",
	    text:text,
	    buttonsAlignment:"center",
	    buttons:["확인"]
	}).then(function(i){
		if(typeof callback == 'function'){
			callback(callBackParam);
		}
	});
}


function fn_s_getComboVal(dsVal, content){
	if(dsVal != undefined){
		for(var idx = 0 ; idx < dsVal.options.length ; idx++ ){
	        if(content == dsVal.options[idx].content){
	            return dsVal.options[idx].value;
	        }
	    }
	}
    
}
/**
 * dhtmlx6 Form 내부 comboBox에 데이터 바인딩
 * @param frm : grid id
 * @param comboBoxId : comboBox ID
 * @param dsVal : 데이터셋
 */
function fn_s_comboBoxPaint(frm, comboBoxId, dsVal){
	frm.getItem(comboBoxId).config.disabled = true;
	frm.getItem(comboBoxId).combobox.data.parse(dsVal);
	frm.paint();
	frm.getItem(comboBoxId).config.disabled = false;
}
/**
 * dhtmlx6 comboBox Search Filter (value 검색)
 * @param item 
 * @param target 
 */
function fn_comboBoxSearch(item, target) {
	  const source = item.value.toLowerCase();
	  target = target.toLowerCase();
	  const sourceLen = source.length;
	  const targetLen = target.length;
	  if (targetLen > sourceLen) {
	    return false;
	  }
	  let sourceIndex = 0;
	  let targetIndex = 0;
	  while (sourceIndex < sourceLen && targetIndex < targetLen) {
	    if (source[sourceIndex] === target[targetIndex]) {
	      targetIndex++;
	    }
	    sourceIndex++;
	  }
	  return targetIndex === targetLen;
}


function fn_s_confirmMessage_Layer(title,text,callback){
	var html ="<div class=\"layer_dialog layer_alert\">"       
        +"<p class=\"msg\" style=\"text-align: center;padding: 30px 0;color: #707070;font-weight: 700;font-size: 20px;\">"+text+"</p>"
        +"<div class=\"layer_btns\" style=\"margin-top: 25px;text-align: center;\">"
          +"<a href=\"javascript:;\" class=\"btnB bg_gray btn_no \" style=\" margin: 0 1px; \">아니오</a>"
          +"<a href=\"javascript:;\" class=\"btnB bg_green btn_yes \" style=\" margin: 0 1px; \">예</a>"
        +"</div>"
        +"<button type=\"button\" data-pop=\"btn-close-pop\" data-target=\"#alert_001\" class=\"btn_pop_close\"><span class=\"blind\">팝업 닫기</span></button>"
        +"</div>"
	dhxWindow = new dhx.Window({
	    modal: true	    
	    ,title : title
	    ,width: "500"
	    ,height: "300"
	    ,resizable: true
	    ,movable: true
	    ,header: true
	    ,footer: false
	    ,html : html
	});
	dhxWindow.events.on("AfterShow", function(){
		var excelWin = $(dhxWindow.getContainer());
		dhx.awaitRedraw().then(function(){
			excelWin.find('.btn_yes').click(function(){
				if(typeof callback == "function") callback();
				dhxWindow.hide();
				dhxWindow.destructor();
			});
			excelWin.find('.btn_no').click(function(){
				fn_excel_download_exe(url, jsonParams);
				dhxWindow.hide();
				dhxWindow.destructor();
			});
		});
	});
	dhxWindow.paint();
	dhxWindow.show();
}

function fn_s_confirmMessage(title,text,callback){
	dhx.confirm({
		header: title,
		text: text,
		buttons: ["예", "아니오"],
		buttonsAlignment : "center"
	}).then(function(answer){
		if(answer){
			if(typeof callback == "function") callback(answer);
		}
	});	
}

function fn_s_saveMessage_Layer(title,text,url,postParam,callback,validationCallBack,vaildParam){
	if(typeof validationCallBack =="function"){
		if(validationCallBack(vaildParam)){return false;}
	}
	var html ="<div class=\"layer_dialog layer_alert\">"       
        +"<p class=\"msg\" style=\"text-align: center;padding: 30px 0;color: #707070;font-weight: 700;font-size: 20px;\">"+text+"</p>"
        +"<div class=\"layer_btns\" style=\"margin-top: 25px;text-align: center;\">"
          +"<a href=\"javascript:;\" class=\"btnB bg_gray btn_no \" style=\" margin: 0 1px; \">아니오</a>"
          +"<a href=\"javascript:;\" class=\"btnB bg_green btn_yes \" style=\" margin: 0 1px; \">예</a>"
        +"</div>"
        +"<button type=\"button\" data-pop=\"btn-close-pop\" data-target=\"#alert_001\" class=\"btn_pop_close\"><span class=\"blind\">팝업 닫기</span></button>"
        +"</div>"
	dhxWindow = new dhx.Window({
	    modal: true	    
	    ,title : title
	    ,width: "500"
	    ,height: "300"
	    ,resizable: true
	    ,movable: true
	    ,header: true
	    ,footer: false
	    ,html : html
	});
	dhxWindow.events.on("AfterShow", function(){
		var excelWin = $(dhxWindow.getContainer());
		dhx.awaitRedraw().then(function(){
			excelWin.find('.btn_yes').click(function(){
				$.ajax({
					url : url
					,data : postParam
					,dataType : "JSON"
					,type : "POST"
					,success : function(data){
						fn_s_alertMsg("저장되었습니다.",callback);
					},error : function(jqXHR, textStatus, errorThrown){
						fn_s_failMsg(jqXHR, textStatus, errorThrown);
					}		
				});
				dhxWindow.hide();
				dhxWindow.destructor();
			});
			excelWin.find('.btn_no').click(function(){				
				dhxWindow.hide();
				dhxWindow.destructor();
			});
		});
	});
	dhxWindow.paint();
	dhxWindow.show();
}

function fn_s_saveMessage(title,text,url,postParam,callback,validationCallBack,vaildParam){
	if(typeof validationCallBack =="function"){
		if(validationCallBack(vaildParam)){return false;}
	}
	
	dhx.confirm({
		header: title,
		text: text,
		buttons: ["예","아니오"],
		buttonsAlignment : "center"
	}).then(function(answer){
		if(answer){
			$.ajax({
				url : url
				,data : postParam
				,dataType : "JSON"
				,type : "POST"
				,success : function(data){
					fn_s_alertMsg("저장되었습니다.",callback,data);
				},error : function(jqXHR, textStatus, errorThrown){
					fn_s_failMsg(jqXHR, textStatus, errorThrown);
				}		
			});
		}
	});	
}
