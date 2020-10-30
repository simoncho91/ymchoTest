/** 
 * 20200715 jjw
 * grid : dhtmlx 그리드 
 * layoutCell : dhtmlx 그리드 선언된 layoutCell
 * pageSize : 페이지당 row 수
 * nowPageNo : 현재 페이지 번호
 * url : 그리드 조회 url
 * frmSearch : 그리드 검색조건 폼
 * denySortCol : 정렬하지 않을 항목배열 id or index
 	ex) ['n_num','v_concd'] || [0,1]
 * 참조 ) initDefault 	필요 param {grid,layoutCell,pageSize}
 		 initServerSide 필요 param {grid,layoutCell,pageSize,nowPageNo,url,frmSearch}
 **/

var	dhtmlxPaging	= {
		initDefault : function(opt) {		// 일반
			dhtmlxPaging.fn.attachPagingArea(opt);
		},
		initServerSide : function(opt) {	// Server Side 페이징
			dhtmlxPaging.opt=opt;
			dhtmlxPaging.fn.attachPagingArea(opt);
			dhtmlxPaging.fn.attachSorting(opt);

			opt.grid.setPagingWTMode(true,true,true,[50,100,150]);
			opt.grid.setPagingSkin("toolbar", dhtmlx_skin);
			opt.grid.attachEvent("onPageChanged", function(page, p1, p2){
				
				if ((dhtmlxPaging.opt.nowPageNo != page) || (dhtmlxPaging.opt.pageSize != opt.grid.rowsBufferOutSize)) {
					dhtmlxPaging.opt.nowPageNo = page;
					dhtmlxPaging.opt.pageSize = opt.grid.rowsBufferOutSize;


					dhtmlxPaging.fn.ajaxGridSet();					
				}
			});
		},
		fn : {
			attachPagingArea : function(opt) {
				opt.layoutCell.attachStatusBar({
					text: "<div><span id='pagingArea'></span>&nbsp;<span id='infoArea'></span></div>",
					paging: true
				});

				opt.grid.enablePaging(true,opt.pageSize,false,"pagingArea",true,"infoArea");
			},
			attachSorting : function(opt) {
				opt.grid.attachEvent("onBeforePageChanged",function(){
		    		if (!this.getRowsNum()) return false;
		    		return true;
		    	});
 				//opt.grid.attachEvent("onBeforeSorting",dhtmlxPaging.fn.customColumnSort);
				opt.grid.sortField_old=opt.grid.sortField;
				opt.grid.sortField=function(){
					if (dhtmlxPaging.fn.customColumnSort(arguments[0])) opt.grid.sortField_old.apply(this,arguments);
				};
				opt.grid.sortRows=function(col,type,order){};	
			},
			displayTotalCount : function(layoutCell, responseData) {
				layoutCell.setText('총 <span style="color: #fe0000">'+responseData.result.total_count+'</span> 건 총 <span style="color: #fe0000">'+responseData.result.total_page_count+'</span> 페이지');
			},
			customColumnSort : function(index){
				var boolSort= true;
				if(dhtmlxPaging.opt.denySortCol){
					dhtmlxPaging.opt.denySortCol.forEach(function(obj,i){
						if(typeof obj == 'number'){
							if(obj == index) boolSort = false;						
						}else if(typeof obj == 'string'){
							if(dhtmlxPaging.opt.grid.getColIndexById(obj) == index) boolSort = false;						
						}
					});					
				}
				if(!boolSort) return;
				var a_state = dhtmlxPaging.opt.grid.getSortingState();
				var colId = dhtmlxPaging.opt.grid.getColumnId(index);
				
				var orderBy = (a_state[1] == "asc")?"desc":((a_state[1] == "des")?"":"asc");
				if(colId != dhtmlxPaging.opt.colId) orderBy = "asc";
				
				dhtmlxPaging.opt.colId=colId;
				dhtmlxPaging.opt.colOrder=orderBy;				
				dhtmlxPaging.opt.colIndex=index;
				
				dhtmlxPaging.fn.ajaxGridSet('sort');
				return true;
			},
			ajaxGridSet : function(mode){
				var postParam = fn_getPostParam(dhtmlxPaging.opt.frmSearch);
				postParam.i_iNowPageNo	= dhtmlxPaging.opt.nowPageNo;
				postParam.i_iPageSize	= dhtmlxPaging.opt.pageSize;
				postParam.i_sSortCol	= dhtmlxPaging.opt.colId;
				postParam.i_sSortDir	= dhtmlxPaging.opt.colOrder;
				$.ajax({
					url: dhtmlxPaging.opt.url,
					data: postParam,
					type: "POST",
					dataType: "json",
					success: function(responseData){					
						dhtmlxPaging.opt.grid.clearAll();
						dhtmlxPaging.fn.displayTotalCount(dhtmlxPaging.opt.layoutCell, responseData);						
						dhtmlxPaging.opt.grid.parse(fn_toJsonText(responseData.result), 'js');
						
						if(mode == 'sort') dhtmlxPaging.opt.grid.changePage(1);
						if (dhtmlxPaging.opt.colOrder) dhtmlxPaging.opt.grid.setSortImgState(true,dhtmlxPaging.opt.colIndex,dhtmlxPaging.opt.colOrder);
				}});				
			}
		}
	};