/*
 * mdi 지원 
 */

var mdiObj   = {};

mdiObj.pages = {}; // 열린 탭 저장
mdiObj.pageCount = 0;
mdiObj.pageLimit = 10; // 최대 열수 있는 탭 수

// page_id : 로딩된 프로그램을 구분할 거임
mdiObj.create_tab = function(id, title, url, extra,callback) {

	//	page_id = page_id || id;
	var page_id = id;
	
	if (!mdiObj.pages[page_id]) {
		titleSize = fn_getLength(title);

		if (mdiObj.pageCount < mdiObj.pageLimit) {
			delete localStorage[page_id];
			
			//mdiObj.tabbar.addTab(page_id, title, (titleSize < 10)?100:titleSize*11.5 );
//			mdiObj.tabbar.addTab(page_id, title, 150 );
			mdiObj.tabbar.addTab(page_id, title);
			mdiObj.tabbar.tabs(page_id).attachURL(url);
			mdiObj.tabbar.cells(page_id).setActive();
	    	
			var win = mdiObj.tabbar.cells(page_id);
			mdiObj.pages[page_id] = win;
			win.extra = extra;
			
			mdiObj.pageCount++;
		} else {
			alert("최대 " + mdiObj.pageLimit + "개의 탭이 사용 가능합니다.");
		}
	} else {
		mdiObj.tabbar.cells(page_id).setActive();
	}
	if(typeof callback == 'function'){
		callback();
	}
};

function mdiInit(layout) {
	
	mdiObj.tabbar = layout.attachTabbar("mdi menu tab");
	
	mdiObj.tabbar.addTab("home","Home","100");
	mdiObj.tabbar.cells("home").attachURL("/home/init.do");
	//mdiObj.tabbar.cells("home").attachHTMLString("여기에 메인화면이 나타남.");
	mdiObj.tabbar.cells("home").setActive();
	mdiObj.tabbar.enableTabCloseButton(true);
	mdiObj.tabbar.enableAutoReSize(true);
	mdiObj.tabbar.setArrowsMode("auto");
	
	mdiObj.tabbar.attachEvent("onTabClose",function(id) {
		console.log('dhtmlx5 : mdiInit : onTabClose');
    	delete mdiObj.pages[id];
    	mdiObj.pageCount--;
		return true;
	});
	
		// 탭 클릭 시 선택한 탭에 해당하는 메뉴로 이동 처리
	mdiObj.tabbar.attachEvent("onTabClick", function(id, lastId){
		console.log('dhtmlx5 : mdiInit : onTabClick');
		if( id != "home" ){
			if( menuTreeView.items.hasOwnProperty(id) ) {
				menuTreeView.openItem(menuTreeView.getParentId(id));
			}else{
				var obj = fn_getParMenuCd(id);
				fn_makeLnb(obj.gnbMenuCd);
				menuTreeView.openItem(obj.parMenuCd);
			}
			menuTreeView.selectItem(id);
		}
	    return true;
	});	
	
	
	
	// mdi context menu 초기화
	mdiContextMenuInit();
}

function mdiMobileInit(layout) {
    mdiObj.tabbar = layout.attachTabbar("mdi menu tab");

    mdiObj.tabbar.addTab("home","테스트앱관리","100");
    mdiObj.tabbar.cells("home").attachURL("/testApp/testAppMng/init.do");
    //mdiObj.tabbar.cells("home").attachHTMLString("여기에 메인화면이 나타남.");
    mdiObj.tabbar.cells("home").setActive();
    mdiObj.tabbar.enableTabCloseButton(true);
    mdiObj.tabbar.enableAutoReSize(true);
    mdiObj.tabbar.setArrowsMode("auto");

    mdiObj.tabbar.attachEvent("onTabClose",function(id) {
		console.log('dhtmlx5 : mdiMobileInit : onTabClose');
        delete mdiObj.pages[id];
        mdiObj.pageCount--;
        return true;
    });
}

function mdiContextMenuInit() {
    // context menu 객체 생성
    var contextMenu = new dhtmlXMenuObject({
        parent: "contextArea",
        context: true,
        items:[
           {id:"cm1", text:"모두 닫기"},
           {id:"cm2", text:"현재 화면 남기고 닫기"},
           {id:"cm3", text:"현재 화면 새로고침"}
           // , {id:"cm2", text:"왼쪽에 있는 tab 모두 닫기"}
           // , {id:"cm3", text:"오른쪽에 있는 tab 모두 닫기"}           
        ]
    }); 
    
    // context menu 이벤트 생성
    contextMenu.attachEvent("onClick", function(id, zoneId, cas) {
        // home tab id
        var homeTabId = "home";        
        // active tab id
        var actvTabId = mdiObj.tabbar.getActiveTab();
        
        if (id == "cm1") {
            mdiObj.tabbar.forEachTab(function(tab) {
                // tab id
                var tabId = tab.getId();
                
                if (tabId != homeTabId) {
                    tab.close();
                    delete mdiObj.pages[tabId];
                    mdiObj.pageCount--;
                }
            });
        } else if (id == "cm2") {
            mdiObj.tabbar.forEachTab(function(tab) {
                // tab id
                var tabId = tab.getId();
                
                if (tabId != homeTabId && tabId != actvTabId) {
                    tab.close();
                    delete mdiObj.pages[tabId];
                    mdiObj.pageCount--;
                }
            });            
        } else if (id == "cm3") {
            mdiObj.tabbar.tabs(actvTabId).reloadURL();
        }
    }); 
    
    // tab 우클릭 이벤트 생성
    $(".dhxtabbar_tabs_cont_left").mousedown(function(e) {
        // 1:좌클릭, 2:휠클릭, 3:우클릭
        if (e.which == 3) {
            // fire click
            $(document.elementFromPoint(e.clientX, e.clientY)).click();
            
            // show context menu
            contextMenu.showContextMenu(e.clientX, e.clientY);
        }
    });
}
