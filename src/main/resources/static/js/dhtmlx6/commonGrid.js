
/**
 * grid를 초기화 한다.
 * @param grid
 */
function fn_initGrid(grid) {
	console.log(grid.headerRowHeight);
}


/**
 * Grid의 현재 Row의 지정컬럼명 값을 가져옴 
 * @param grid
 * @param colName
 * @returns {String}
 */
function fn_getGridValue(grid, colName) {
	var selectedCell = grid.selection.getCell();
    return selectedCell.row[colName];
}

/**
 * Grid 의 cell선택시 edit모드로 변경한다. (그리드의 column 속정중 editable: false일 경우 적용X)
 * @param grid
 * @param editCellId
 * @returns {String}
 */
function fn_s_editCell(grd, editCellId){
	
	var edtCellId = editCellId;									// 선택된 컬럼 ID
	var id = grd.selection.getCell().row.id;					// row ID
    var columnObj = grd.getColumn(editCellId);					// 컬럼 오브젝트
	var editorType = columnObj["editorType"];					// 에디트 타입
    
    if((editorType == undefined || editorType != "combobox") && columnObj.editable == true){
    	grd.editCell(id, edtCellId);
    }
}

/**
 * Grid에서 변경된 데이터만 가져옴
 * @param grid
 * @param datacollection
 * @returns {Array}
 */
function fn_s_getPostParamFromGrid(dcGrd){
	var paramArray = new Array();
	var paramObj;
    
	var iRows  = dcGrd.getLength();
	var sState = "";
	
	var rowIdx = 0;
    
	dcGrd.forEach(function(element, index, array) {

		sState = element._STATUS;
		
		if(sState != undefined && sState != ''){
	        element["!nativeeditor_status"] = sState; // 상태값 저장.(inserted,updated ,deleted)
	    	paramArray[rowIdx] = element;

	    	rowIdx++;
		}
    });
	return paramArray;
}

/**
 * Grid 변경시 상태값 업데이트
 * @param grid
 * @param datacollection
 * @param 행 ID
 * @param 상태값 (U,D)
 * @returns {Array}
 */
function fn_s_girdStatusChg(grid, dcGrd, rowId, status){
	
	dcGrd.getItem(rowId)._STATUS = status;
	grid.paint();
}

/**
 * Grid Column Content 정렬
**/
function fn_s_gridColAlignL(cell,data,row,col){
	return "gt_l"
}
function fn_s_gridColAlignC(cell,data,row,col){
	return "gt_c"
}
function fn_s_gridColAlignR(cell,data,row,col){
	return "gt_r"
}