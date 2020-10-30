
// 페이지 이동 1,2, ... 하는 HTML 코드를 생성해서 돌려준다.
//	funcName : 실제 페이지 이동을 위한 함수이름 (예: gotoPage)
//	pageNum : 현재 페이지 번호
//	pageSize : 한 페이지당 결과 갯수
//	total : 전체 결과 갯수

function navAnchor( funcName, pageNo, anchorText )
{
    var font_class = "<a href=\"javascript:{" + funcName + "(" + pageNo + ")}\">" + anchorText + "</a>";
    //var font_class = "<a href=# onclick=\"javascript:{" + funcName + "(" + pageNo + ")}\">" + anchorText + "</a>";
	return font_class;
}

function pageNav( funcName, pageNum, pageSize, total )
{
	if( total < 1 )
		return "";

	var ret = "";
	var PAGEBLOCK=10;
	var totalPages = Math.floor((total-1)/pageSize) + 1;

	var firstPage = Math.floor((pageNum-1)/PAGEBLOCK) * PAGEBLOCK + 1;
	if( firstPage <= 0 ) // ?
		firstPage = 1;

	var lastPage = firstPage-1 + PAGEBLOCK;
	if( lastPage > totalPages )
		lastPage = totalPages;

	if( firstPage > PAGEBLOCK )
	{
        ret += navAnchor(funcName, 1, "<img src='/IMG/KO/sc/btn_pre_02.gif' border='0' align='absmiddle'>");
		ret += navAnchor(funcName, firstPage-1, "<img src='/IMG/KO/sc/btn_pre_01.gif' border='0' align='absmiddle'>") ;
	}

	for( i=firstPage; i<=lastPage; i++ )
	{
		if( pageNum == i )
			ret += "<strong>" + i + "</strong>";
		else
			ret += "" + navAnchor(funcName, i, i) + "";
	}

	if( lastPage < totalPages )
	{
		ret += navAnchor(funcName, lastPage+1, "<img src='/IMG/KO/sc/btn_next_01.gif' border='0'>") + "";
		ret += "" + navAnchor(funcName, totalPages, "<img src='/IMG/KO/sc/btn_next_02.gif' border='0'>") + "\n";
	}
	return ret;
}
