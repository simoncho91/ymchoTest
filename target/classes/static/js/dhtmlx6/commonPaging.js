/** 
 * 20200723 jjw
 * name : defined 되는 변수명 (필수)
 * layout : dhtmlx6 layout (필수)
 * gridData : dhtmlx6 그리드 데이터 (필수)
 * grid : dhtmlx6 그리드  (필수)
 * url : 그리드 조회 url (필수)
 * frmSearch : 그리드 검색조건 폼
 * postParam : 검색에 필요한 param
 * gridSub : 서브 그리드 배열
 * gridPageCell : page영역 layout Cell
 * denySortCol : 정렬하지 않을 항목배열 id
 	ex) ['n_num','v_concd']
 * pageSize : 최초 pageSize
 * pageSizeCombo : 페이지 combobox 값 배열
 	ex) [5,10,15]
 * 참조 )mainPage = new CmDhtmlx6Paging(opt)
 		 opt = {name,layout,gridData,grid,url}(필수항목)
 **/
var CmDhtmlx6Paging = function(opt){
	if(opt){
		this.name = fn_isNull(opt.name)?'cmDhtmlx6Paging':opt.name;
		this.layoutMain   = opt.layout;
		this.dcGrd        = opt.gridData;
		this.grid         = opt.grid;
		this.url          = opt.url;	
		this.frmSearch    = opt.frmSearch;
		this.postParam    = fn_isNull(opt.postParam)?{}:opt.postParam;
		this.gridSub		=opt.gridSub;	
		this.gridPageCell   = opt.gridPageCell;	
		this.denySortCol  = opt.denySortCol;
		this.dataPerPage = fn_isNull(opt.dataPerPage)?10:opt.dataPerPage;
		this.pageSize = fn_isNull(opt.pageSize)?50:opt.pageSize;
		this.pageSizeCombo = fn_isNull(opt.pageSizeCombo)?[50,100,150]:opt.pageSizeCombo;
		this.succSearchFunc = opt.succSearchFunc;
		this.noPaging 		= typeof opt.noPaging=='undefined'?false:opt.noPaging
		this.moveCallBack		= opt.moveCallBack;
	}
	this.totalPage = 0;
	this.menuCd		= $('#refMenuCd').text();
	
	CmDhtmlx6Paging.prototype.init= function(option){
		this.name			= option.name;
		this.layoutMain		= option.layout;
		this.dcGrd			= option.gridData;
		this.grid			= option.grid;
		this.url			= option.url;	
		this.frmSearch		= option.frmSearch;
		this.postParam		= fn_isNull(option.postParam)?{}:postParam;
		this.gridSub		= option.gridSub;	
		this.gridPageCell   = option.gridPageCell;	
		this.denySortCol  	= option.denySortCol;
		this.dataPerPage	= fn_isNull(option.dataPerPage)?10:option.dataPerPage;
		this.pageSize 		= fn_isNull(option.pageSize)?50:option.pageSize;
		this.pageSizeCombo 	= option.pageSizeCombo;
		this.succSearchFunc = option.succSearchFunc;
		this.noPaging 		= typeof option.noPaging=='undefined'?false:option.noPaging
		this.totalPage 		= 0;
		this.totalCount		= 0;
		this.moveCallBack		= option.moveCallBack;		
		
	}
	CmDhtmlx6Paging.prototype.initPaging= function(callback){
		this.initSorting();
//		if(typeof parent.mdiObj != 'undefined'){
//			var tabId=parent.mdiObj.tabbar.getActiveTab();
//			if(typeof parent.mdiObj.pages[tabId].postParam == 'object'){
//				var mdiPostParam={};
//				//parent.mdiObj.pages[tabId].postParam;
//				Object.keys(parent.mdiObj.pages[tabId].postParam).forEach(function(key,i,arr){
//					mdiPostParam[key]=parent.mdiObj.pages[tabId].postParam[key];
//				});
//				callback(mdiPostParam);				
//				var p = mdiPostParam.i_iNowPageNo;
//				this.search(p,mdiPostParam);
//			}else{
//				this.search(1);			
//			}
//		}else{
//			this.search(1);			
//		}			
		//var tabId=parent.mdiObj.tabbar.getActiveTab();
		var tabId=this.menuCd;
		if(fn_isNotNull(tabId) && typeof localStorage[tabId] == 'string'){
			var parentPostParam= JSON.parse(localStorage[tabId]);
			if(typeof parentPostParam == 'object'){
//				var mdiPostParam={};
				//parent.mdiObj.pages[tabId].postParam;
//				Object.keys(parentPostParam).forEach(function(key,i,arr){
//					mdiPostParam[key]=parentPostParam[key];
//				});
				if(typeof callback =='function') callback(parentPostParam);				
				var p = parentPostParam.i_iNowPageNo;
				this.search(p,parentPostParam);
			}else{
				this.search(1);			
			}			
		}else{
			this.search(1);			
		}
		setTimeout(function(){ $('#layoutObj div').removeClass('dhx_grid-content'); }, 100);
		
	}
	CmDhtmlx6Paging.prototype.changePageSize= function(){
		this.pageNo = 1;
		this.pageSize	= parseInt(jQuery("select[name="+ this.name +"_i_iPageSize]").val());
		this.search();	
	}
	CmDhtmlx6Paging.prototype.moveToPage= function(p){
		p = (p == 0 ? 1 : p);
		this.pageNo =p;
//		this.searchMove();
		if(typeof this.moveCallBack == 'function') this.moveCallBack();
		this.search();
	}
	CmDhtmlx6Paging.prototype.drawHtml= function(){
		var retStr = "";
		var goPage = this.pageNo;
		var	dataPerPage	= this.dataPerPage;
		var prevPage = parseInt((goPage - 1)/dataPerPage) * dataPerPage;
		var nextPage = ((parseInt((goPage - 1)/dataPerPage)) * dataPerPage) + dataPerPage + 1;
		prevPage = Math.max(0, prevPage);
		nextPage = Math.min(nextPage, this.totalPage);

		retStr += "<div class='paging_box'>";
		
		retStr	+= "<span class='view_number'>";
	//	retStr	+= '<div class="dhx_input__icon dxi dxi-menu-down" style="top: 56% !important;right: inherit !important;left: 42px !important;"></div>';
		
		retStr	+= "<select name='"+ this.name +"_i_iPageSize' onchange='"+ this.name +".changePageSize();' class='select_sty01'>";
		var _pageSize = this.pageSize;
		this.pageSizeCombo.forEach(function(val){
			retStr	+= "			<option value='"+val+"' " + (_pageSize == val ? " selected " : "") + ">"+val+"</option>";				
		})
		retStr	+= "		</select>";
		retStr	+= "</span>";
		
		retStr	+= "<div class='paging'>";
		retStr	+= "<button class='page_first' onclick='javascript:"+ this.name +".moveToPage(1)'><span class='blind'>처음페이지</span></button>";
		retStr	+= "<button class='page_prev' onclick='javascript:"+ this.name +".moveToPage("+ prevPage +")'><span class='blind'>처음페이지</span></button>";

		for (var i=(prevPage+1), len=(dataPerPage+prevPage); i<=len; i++) {
			if (goPage == i) {
				retStr += '<strong>'+i+'</strong>';
				
			} else {
				retStr += "<button onclick='javascript:"+ this.name +".moveToPage(" + i + ")'>";
				retStr += i;
				retStr += "</button>";
			}			
			if (i >= this.totalPage) {
				break;
			}
		}
		// 다음
		retStr	+= "<button class='page_next' onclick='javascript:"+ this.name +".moveToPage("+ nextPage +")'><span class='blind'>다음페이지</span></button>";

		// 마지막
		retStr	+= "<button class='page_last' onclick='javascript:"+ this.name +".moveToPage("+ this.totalPage +")'><span class='blind'>마지막페이지</span></button>";

		retStr	+= "</div>";
		retStr += '<p class="total_number">총 '+this.totalCount+' 건<em>'+this.totalPage+' 페이지</em></p><!-- //total_number -->';

		retStr += "</div>";
		if(!fn_isNull(this.gridPageCell)){
			this.gridPageCell.attachHTML('<div class="dhx_cell_statusbar_def">' + retStr + '</div>');
		}else{
	 		this.layoutMain.removeCell("dhx_paging");
	 		this.layoutMain.addCell({
	 			id : "dhx_paging",
	 			css: "dhx_layout-cell--border_bottom si_im_010_",
	 			html: '<div class="dhx_cell_statusbar_def">' + retStr + '</div>'
	 		});
		}
	}
	CmDhtmlx6Paging.prototype.searchMove = function (num) {
	//	if(fn_validateForm(CmDhtmlx6Paging.frmSearch)) {
		//(fn_isNull(this.frmSearch)?{}:fn_getPostParam(this.frmSearch))
		var postParam=fn_isNull(this.postParam)?{}:this.postParam;		
		if(!fn_isNull(num)) this.pageNo=num;
		postParam.i_iNowPageNo	= this.pageNo;
		postParam.i_iPageSize	= this.pageSize;
		postParam.i_sSortCol	= fn_isNull(this.i_sSortCol)?'':this.i_sSortCol;
		postParam.i_sSortDir	= fn_isNull(this.i_sSortDir)?'':this.i_sSortDir;
		this.postParam = postParam;
		//var tabId=parent.mdiObj.tabbar.getActiveTab();
		var tabId=this.menuCd;
		if(fn_isNotNull(tabId)) localStorage[tabId] = JSON.stringify(this.postParam);
//		if(typeof parent.mdiObj != 'undefined'){
//			var tabId=parent.mdiObj.tabbar.getActiveTab();
//			parent.mdiObj.pages[tabId].postParam=this.postParam;			
//		}
	//	}
		if(!fn_isNull(this.gridSub)) {
			this.gridSub.forEach(function(obj){
				obj.data.removeAll();
			});			
		}
		this.dcGrd.removeAll();
		var _obj = this;
		fn_ajax({
			 url            : this.url
			, postParam      : postParam
			, async:false
			, success             : function(responseData) {
				if (responseData.result) {					
					if(typeof _obj.succSearchFunc == 'function'){
						_obj.succSearchFunc(responseData.result.data);
					}
					_obj.totalPage	= responseData.result.total_page_count;
					_obj.totalCount	= responseData.result.total_count;
					if(!_obj.noPaging && _obj.pageNo > _obj.totalPage){
						_obj.search(1);
					}
					_obj.setRecordNumber();
					_obj.dcGrd.parse(responseData.result.data);
					if(!_obj.noPaging) _obj.drawHtml();
					
					setTimeout(function(){ 
						_obj.grid.scroll(0,0); //데이터변경시 그리드 스크롤 최상단 이동 
					}, 100);
				}
			}
		});
		
	}
	CmDhtmlx6Paging.prototype.search = function (num,mdiPostParam) {
	//	if(fn_validateForm(CmDhtmlx6Paging.frmSearch)) {
		//(fn_isNull(this.frmSearch)?{}:fn_getPostParam(this.frmSearch))
		if(!fn_isNull(num)) this.pageNo=num;
		if(fn_isNull(mdiPostParam)){
			var postParam=fn_isNull(this.postParam)?{}:this.postParam;
			if(!fn_isNull(this.frmSearch)) postParam= $.extend(postParam , fn_getPostParam(this.frmSearch));			
			postParam.i_iNowPageNo	= this.pageNo;
			postParam.i_iPageSize	= this.pageSize;
			postParam.i_sSortCol	= fn_isNull(this.i_sSortCol)?'':this.i_sSortCol;
			postParam.i_sSortDir	= fn_isNull(this.i_sSortDir)?'':this.i_sSortDir;			
		}else{
			postParam = mdiPostParam;
		}
		this.postParam = postParam;
//		if(typeof parent.mdiObj != 'undefined'){
//			var tabId=parent.mdiObj.tabbar.getActiveTab();
//			parent.mdiObj.pages[tabId].postParam=this.postParam;
//		}
		//var tabId=parent.mdiObj.tabbar.getActiveTab();
		var tabId=this.menuCd;
		if(fn_isNotNull(tabId)) localStorage[tabId] = JSON.stringify(this.postParam);
	//	}
		if(!fn_isNull(this.gridSub)) {
			this.gridSub.forEach(function(obj){
				obj.data.removeAll();
			});			
		}
		this.dcGrd.removeAll();
		var _obj = this;
		fn_ajax({
			 url            : this.url
			, postParam      : postParam
			, async:false
			, success             : function(responseData) {
				if (responseData.result) {					
					if(typeof _obj.succSearchFunc == 'function'){
						_obj.succSearchFunc(responseData.result.data);
					}
					_obj.totalPage	= responseData.result.total_page_count;
					_obj.totalCount	= responseData.result.total_count;
					if(!_obj.noPaging && _obj.totalPage != 0 && _obj.pageNo > _obj.totalPage){
						_obj.search(1);
					}
					_obj.setDataParse(responseData.result.data);
					
					if(!_obj.noPaging) _obj.drawHtml();
					
					setTimeout(function(){ 
						_obj.grid.scroll(0,0); //데이터변경시 그리드 스크롤 최상단 이동 
					}, 100);
				}
			}
		});
	}
	CmDhtmlx6Paging.prototype.setDataParse = function(data){
		if(data.length==0){
			var cols = this.grid.config.columns;
			var grid = this.grid;
			var disCols = [];
			var cSize = 0,cId,rId;
			
			cols.forEach(function(obj,i){
				if(obj.hidden == undefined || (typeof obj.hidden=='string' && obj.hidden =='false') || (typeof obj.hidden=='boolean' && !obj.hidden)){
					if(!(obj.id=='radio' || obj.id=='n_num' || obj.id=='check')){
						if(cSize==0) cId=obj.id;
						cSize++;						
					}else{
						disCols.push(obj.id);
						//cSize++;
					}
				}
			});
			var tempObj = {};
			tempObj[cId]='';
			this.dcGrd.add(tempObj);
			rId=grid.data.getId(0);
			disCols.forEach(function(colId){
				grid.addCellCss(rId,colId,'tc_h');
			});
			//console.log('cSize : '+cSize+' cId : '+cId+' rId: '+rId);
			grid.addSpan({ row: rId, column: cId, colspan : cSize  ,text:'No Records Found',css:'ta_c'});
		}else{
			this.setRecordNumber();
			this.dcGrd.parse(data);						
		}
		
	}
	CmDhtmlx6Paging.prototype.setRecordNumber = function(){
		var	obj			= this;
		var	gridData	= this.grid.data;
		var	bNumber		= false;
		
		this.grid.config.columns.forEach(function (s) {
			if ('n_num' == s.id) {
				bNumber	= true;
			}
		});

		if ( bNumber ) {
			this.grid.getColumn('n_num').template	= function(text, row, col) {
				var	i	= gridData.getIndex(row.id);
				var	num	= obj.totalCount - (obj.pageNo - 1) * obj.pageSize - i
				return num;
			}
		}
	}

	CmDhtmlx6Paging.prototype.initSorting = function(){
		var _obj = this;
		this.grid.events.detach("Sort");
		
		this.grid.events.on("HeaderCellClick", function(c,e){
			if (!$(e.target).hasClass('dhx_grid-sort-icon'))
				return ;

			var boolSort= true;
			if(_obj.denySortCol){
				_obj.denySortCol.forEach(function(obj,i){
					if(obj == c.id) boolSort = false;
				});					
			}
			if(!boolSort) return;
			
			var sortDir = _obj.i_sSortDir;
			_obj.pageNo = 1;
			
			if(_obj.i_sSortCol != c.id){
				_obj.i_sSortDir = 'desc';
				$('.dhx_grid-sort-icon--asc').remove();
				$('.dhx_grid-sort-icon--desc').remove();
			}else{
				_obj.i_sSortDir = fn_isNull(sortDir)?"desc":(sortDir=="desc"?"asc":"desc");
			}
			
			console.log(c.id + '       ' + _obj.i_sSortCol);
			
			_obj.i_sSortCol = c.id; 
			_obj.search();

			if (_obj.i_sSortDir == 'desc') {
				$(e.target).removeClass('dhx_grid-sort-icon--asc');
				$(e.target).addClass('dhx_grid-sort-icon--desc');
			} else {
				$(e.target).removeClass('dhx_grid-sort-icon--desc');
				$(e.target).addClass('dhx_grid-sort-icon--asc');
			}
			
			return;
		});
	}
};