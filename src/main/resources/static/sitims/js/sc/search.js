/*******************************************************
* 프로그램명 : search.js   # 검색기능
* 설명             : 통합검색용 자바스크립트
* 작성일         :  2010.04.05
* 작성자         : 정민철
* 수정내역     :
  *****************************************************/


/**
* gnb검색창 Object 리턴
* @ param  
* @ return  searchForm 폼 객체 리턴
**/
function getGnbForm()
{
	var frm	=	document.searchForm;
	frm["category"].value = "TOTAL";
	
	return document.forms["searchForm"];
}

function getDetailSearchForm() {
	return document.forms["detailSearchForm"];
}

/**
* History폼 Object 리턴
* @ param  
* @ return  historyForm 폼 객체 리턴
**/
function getHistoryForm()
{
	return document.forms["historyForm"];
}

/**
* 상세검색 form Object 리턴
* @ param  
* @ return  detailSearchForm 폼 객체 리턴
**/
function getDetailForm()
{
	return document.forms["detailSearchForm"];
}


/**
* 검색어 체크 
* @ param	frm			- form Object
*
* @ return   true / false 		- 키워드 있음(true) , 없음(false)
**/
function seachKwd(frm)
{
	var kwd = CommonUtil.getValue(frm, "kwd");
	if (kwd == "")
	{
		alert("검색어를 입력해 주세요");
		return false;
	}
	else
		return true;
}
function detailSeachKwd(frm)
{
	seachKwd(frm);
}

/**
* 특정 kwd로 검색( historyForm사용)
* @ param	str			- 검색어
*
* @ return   void
**/
function goKwd(str)
{
	var frm = getHistoryForm();
	
	CommonUtil.setValue(frm,"kwd",str);
	CommonUtil.setValue(frm,"pageNum","1");
	//CommonUtil.setValue(frm,"category","TOTAL");
	CommonUtil.setValue(frm,"reSrchFlag",false);
	
	frm.submit();
}

/**
* 특정 카테고리로 이동 ( historyForm사용)
* @ param	str			- 카테고리명
*
* @ return   void
**/
function goCategory(str, flag)
{
	var frm = getHistoryForm();
	var url	= "";
	CommonUtil.setValue(frm,"pageNum","1");
	CommonUtil.setValue(frm,"category",str);
	
	if(frm["bproom"].value == "BP") {
		url	=	jsRootPATH + "por/sc/por_sc_search_bproom.do";
	} else {
		url	=	jsRootPATH + "por/sc/por_sc_search_main.do";
	}
	
	frm.action = url;
	frm.submit();
}

/**
* 정렬조건 변경 ( historyForm사용)
* @ param	str			- 정렬코드
*
* @ return   void
**/
function goSort(str, flag)
{
	var frm = getHistoryForm();
	
	CommonUtil.setValue(frm,"pageNum","1");
	CommonUtil.setValue(frm,"sort",str);
	
	if(frm["bproom"].value == "BP") {
		url	=	jsRootPATH + "por/sc/por_sc_search_bproom.do";
	} else {
		url	=	jsRootPATH + "por/sc/por_sc_search_main.do";
	}
	
	frm.action = url;
	frm.submit();
}

/**
* 페이지 이동 ( historyForm사용)
* @ param	pagenum		- 페이지 번호
*
* @ return   void
**/
function gotoPage(pagenum, flag)
{
	var frm = getHistoryForm();
	var url	= "";
	
	if(frm["bproom"].value == "BP") {
		url	=	jsRootPATH + "por/sc/por_sc_search_bproom.do";
	} else {
		url	=	jsRootPATH + "por/sc/por_sc_search_main.do";
	}
	
	CommonUtil.setValue(frm,"pageNum",pagenum);
	frm.action = url;
	frm.submit();
}

/**
* 현재 날짜 기준으로 날짜범위 계산 및 반영 ( detailSearchForm 사용)
* @ param	startname		- 시작일
* @ param    endname		- 종료일
* @ param    range		- 범위 (1일,(1) 1달(30), 3달(90)...)
*
* @ return   void
**/
function choiceDatebutton(startname, endname, range)
{
	var startDate = "";
	var endDate   = "";
	var frm = getDetailForm();
	
	if 		(range == 1)
	{
		startDate 	= CommonUtil.getToday();
		endDate		= CommonUtil.getToday();
	}
	else if (range == 30)
	{
		startDate 	= CommonUtil.calcDateMonth(CommonUtil.getToday(),-1);
		endDate		= CommonUtil.getToday();
	}
	else if (range == 90)
	{
		startDate 	= CommonUtil.calcDateMonth(CommonUtil.getToday(),-3);
		endDate		= CommonUtil.getToday();
	}
	else if (range == 180)
	{
		startDate 	= CommonUtil.calcDateMonth(CommonUtil.getToday(),-6);
		endDate		= CommonUtil.getToday();
	}
	else if (range == 365)
	{
		startDate 	= CommonUtil.calcDateYear(CommonUtil.getToday(),-1);
		endDate		= CommonUtil.getToday();
	}
	else if (range == 1095)
	{
		startDate 	= CommonUtil.calcDateYear(CommonUtil.getToday(),-3);
		endDate		= CommonUtil.getToday();
	}

	CommonUtil.setValue(frm,startname,  startDate);
	CommonUtil.setValue(frm,endname,	endDate);
}



/**
* GNB창의 레이어 selectBox 창 컨트롤 ( searchForm 사용)
* @ param	code			- 코드값
* @ param    name			- 이름
*
* @ return   void
*
* 참고사항
* selectedSearch , searchSelect 의 레이어를 사용하며 style의 class명을 이용한 컨트롤
**/
function selectSearch(code, name)
{
    var selecttxt_obj  = document.getElementById("selectedSearch");		// 셀렉트박스 선택이름이 바뀔 텍스트위치
	var viewselect_obj = document.getElementById("searchSelect");			// 셀렉트박스 목록
	var frm = getGnbForm();
	
	// 텍스트 변경
	if (typeof(selecttxt_obj) == "object" && typeof(viewselect_obj) == "object" )
	{
		selecttxt_obj.innerHTML = name;				//셀렉트 박스 이름 변경
		viewselect_obj.className = "searchSelect_off";	//셀렉트 박스 목록 제거
		
		CommonUtil.setValue(frm, "category", code);	//폼값 싱크맞춤			
	}
}


/**
* 미리보기 기능구현
* @ param	divid			-  내용을 넣어줄 div의 id
* @ param    rowid		-  미리보기할 대상 데이터의 $ROWID 값
* @ param    index		-  다중첨부일 경우 몇번째 첨부문서인지의 index값 (0~)
* @ return   void
*
* 참고사항 
* 1)  url의 내용을 divid의 레이어에 innerHTML해줌
* 2) 해당 div를 display none, block으로 토글해줌
* 3) 내부참조펑션 : konan_get_htmltext(Url)
**/
function konan_preview(divid, rowid, index, cate)
{
	//preview.xxx 를 자동으로 체크해서 이름을 넘겨줌 (하드코딩해도됨)
	var preview_html = "";
	if      (document.location.pathname.indexOf(".do") > 0)
		preview_html += "preview.jsp";
	else if (document.location.pathname.indexOf(".aspx") > 0)
		preview_html += "preview.aspx";
	else if (document.location.pathname.indexOf(".asp") > 0)
		preview_html += "preview.asp";	
	else if (document.location.pathname.indexOf(".php") > 0)
		preview_html += "preview.php";	
	else if (document.location.pathname.indexOf(".html") > 0)
		preview_html += "preview.html";	
		
	if (preview_html == "")
	{
		alert("미리보기할 URL 페이지가 등록되지 않았습니다");
		return;
	}
	
	
	var url = jsRootPATH + preview_html+"?rowid="+rowid+"&index="+index+"&cate1="+cate+"&kwds=";
	var divboxobj = document.getElementById(divid+"_box");	//외곽 box
	var divobj = document.getElementById(divid);		//내용들어가는곳
	var default_str = "";
	
	if (divobj == null || divboxobj==null )
	{
		alert("해당 div의 id를 찾을수 없습니다");
	}
	else
	{
		var kwds = "";
		kwds += CommonUtil.getValues(getGnbForm(), "kwd");
		kwds += " " + CommonUtil.getValues(getGnbForm(), "preKwd");
		url += " " + encodeURIComponent(kwds);
		
		//기본값과 같을경우에만 요청해서 값을 바꿔줌
		if (divobj.innerHTML == default_str)
		{
			var html = CommonUtil.UtltoHtml(url);
			if (CommonUtil.trim(html) == "")
			{
				alert("첨부파일에 해당 키워드로 검색되지 않았습니다");
				j$("#" + divid + "_box").slideUp("500");
				return;
			}
			else
			{
				divobj.innerHTML = html;
			}
		}
		/*j$.ajax({
			url : jsRootPATH + "por/sc/por_sc_rs_preview.do?rowid="+rowid+"&index="+index+"&cate1="+cate+"&kwds=" + encodeURIComponent(kwds)
			, dataType : "html"
			, success : function(html) {
				divobj.innerHTML = html;
			}
		});*/
		
		//미리보기 기능 토글
		if (typeof(divboxobj.style.display) != "undefined")
		{
			
			
			if (divboxobj.style.display == "none") {
				//divboxobj.style.display = "block";
				j$(".div_preview").hide();
				j$("#" + divid + "_box").slideDown("500");
			}
			else
				/*divboxobj.style.display = "none";*/
			j$("#" + divid + "_box").slideUp("500");
		}
	}
}

/**
* 상세검색창 보기/숨기기  (토글처리)
* @ return   void
*
* 참고사항 
* "advanced_search" 레이어의 style class명을 체크하여 변경해줌
**/
function detailview()
{
	var divobj = document.getElementById("advanced_search");
	if (typeof(divobj.className) == "string")
	{
		if (divobj.className == "") {
			j$("#advanced_search").stop().slideUp(500);
			divobj.className = "hidden";
		}
		else {
			j$("#advanced_search").stop().slideDown(500);
			divobj.className = "";
		}
	}
}

/**
* GNB 영역 카테고리 선택
* @ return   void
*
* 참고사항 
* "searchSelectList" 레이어의 style.display 를 변경해줌
**/
function categotyView(flag)
{
	var divobj = document.getElementById("searchSelectList");
	
	if (flag == true)
		divobj.style.display = "block";
	else
		divobj.style.display = "none";

}

/**
* 파일다운로드 기능 (샘플)
* @ param   str		- 파일명
* @ return   void
*
**/
function fileDownload(str)
{
	alert("파일명 : " + str );
}

/**
* 첨부파일 새창으로 보기 (샘플)
* @ param   rowid		- 미리보기할 대상 데이터의 $ROWID 값
* @ param   index            -  다중첨부일 경우 몇번째 첨부문서인지의 index값 (0~)
* @ return   void
*
**/
function fileView(rowid, index)
{
	alert("새창으로 첨부파일 열기\nROWID="  + rowid + ", index=" + index );
}

/**
* 달력기능 호출
* @ param   checkname         - 미리보기할 대상 데이터의 $ROWID 값
* @ param   textid                    -  달력에서 선택된 날짜가 출력될 <input type="text" ../> 의 ID
* @ param   divid                     -  달력이 그려질 레이어 <div ...> 의  ID
* @ return   void
*
**/
function showCalendar(checkname,textid,divid)
{
	var data = CommonUtil.getValue(getGnbForm(), checkname);
    var target = document.getElementById(textid);
	
	var obj_x = CommonUtil.getElementX(target);
	var obj_y = CommonUtil.getElementY(target) - 100;

    if (data == "select")
        konanCalendar.loadCalendar(textid, divid, obj_x, obj_y);
    else
        alert("기간입력을 눌러주세요");
}








/*******************************************************
* 프로그램명 : search.js   # 공통기능
* 설명             : 통합검색용  범용 코드 구현 js Class (CommonUtil)
* 작성일         :  2010.04.05
* 작성자         : 정민철
* 수정내역     :
*
* 2010.03.25 - 첨부파일미리보기 펑션수정
* 2010.03.24 - trim, replaceAll 추가
* 2010.03.23 - getValues 기능추가
* 2010.03.17 - getValue의 checkbox 리턴값 버그 수정
  *****************************************************/
var CommonUtil = {
	
	/**
	* URL을 받아서 해당 결과를 String으로 리턴해줌
	* @ param   url		- 읽어올 페이지의 주소
	* 
	* @ return   str		-  url에서 보여지는 페이지 결과의 string
	*
	**/
	UtltoHtml : function (url) {
		var str = "";

		var xmlhttp = null;

		if(window.XMLHttpRequest) {
		   // FF 로 객체선언
		   xmlhttp = new XMLHttpRequest();
		} else {
		   // IE 경우 객체선언
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if ( xmlhttp ) 
		{//비동기로 전송
			xmlhttp.open('GET', url, false);
			xmlhttp.send(null);
			str = xmlhttp.responseText;
		}
		return str;
	},
	
	/**
	* form 의 특정 name에 값을 세팅해줌 (라디오버튼, input,hidden, 셀렉트 박스) 알아서 처리해줌
	* @ param   frmobj		- 폼오브젝트
	* @ param	name			- 해당 데이터의 name
	* @ param	value			- 세팅될 값
	*
	* @ return   void
	* 
	* 주의사항
	* name이 복수개일경우 첫번째에 값을 세팅해줌
	**/
	setValue : function (frmobj, name, value) {
		if ( typeof(frmobj) == "object" && typeof(frmobj.length) == "number");
		{
			for (var i=0; i< frmobj.length; i++)
			{
				if (frmobj[i].name == name)
				{
					if (frmobj[i].type=="text" || frmobj[i].type=="hidden" )
					{// hidden , text
						frmobj[i].value = value;
						break;
					}//--end: hidden, text
					else if (frmobj[i].type=="radio" && frmobj[i].value == value )
					{// radio 버튼
						 frmobj[i].checked = true;
						 break;
					}//--end:radio
					else if (frmobj[i].type=="checkbox")
					{//checkbox박스
						if (value == true)
							frmobj[i].checked = true;
						else
							frmobj[i].checked = false;
						
						break;
					}//--end:checkbox
					else if (frmobj[i].type=="select-one" && typeof(frmobj[i].options) == "object" && typeof(frmobj[i].length) == "number")
					{//select박스
						var selectidx = 0;
						for(var j=0; j<frmobj[i].length; j++)
						{
							if (value == frmobj[i].options[j].value)
							{
								selectidx = j;
								break;
							}
						}
						frmobj[i].selectedIndex = selectidx;
					}//--end:select
					
				}
				
			}
		}
	},
	
	/**
	* form 의 특정 name에 값을 가져옴 (라디오버튼, input,hidden, 셀렉트 박스 알아서 처리됨  )
	* @ param   frmobj		- 폼오브젝트
	* @ param	name			- 해당 데이터의 name
	*
	* @ return   해당 frmobj의 특정 name에 있는 값(value)
	* 
	* 주의사항
	* name이 복수개일경우 첫번째에 값을 리턴
	**/
	getValue : function (frmobj, name)	{
		var result = null;
		
		if ( typeof(frmobj) == "object" && typeof(frmobj.length) == "number");
		{
			for (var i=0; i< frmobj.length; i++)
			{
				if (frmobj[i].name == name)
				{
					if (frmobj[i].type=="text" || frmobj[i].type=="hidden" )
					{// hidden , text
						result = frmobj[i].value;
						break;
					}//--end: hidden, text
					else if (frmobj[i].type=="radio" && frmobj[i].checked == true)
					{// radio 버튼
						 result = frmobj[i].value;
						 break;
					}//--end:radio
					else if (frmobj[i].type=="checkbox")
					{//checkbox박스
						result = frmobj[i].checked;
						break;
					}//--end:checkbox
					else if (frmobj[i].type=="select-one" && typeof(frmobj[i].options) == "object" && typeof(frmobj[i].length) == "number")
					{//select박스
						var idx = frmobj[i].selectedIndex;
						result = frmobj[idx].value;
						break;
					}
				}
			}
		}
		return result;
	},
	
	/**
	* form 의 특정 name에 값을 가져옴(라디오버튼, input,hidden, 셀렉트 박스 알아서 처리됨  )
	*
	* @ param   frmobj		- 폼오브젝트
	* @ param	name			- 해당 데이터의 name
	*
	* @ return   해당 frmobj의 특정 name에 있는 값(value)
	* 
	* 주의사항
	* name이 복수개일경우 공백(space)을 넣어 나열된 값을 리턴
	**/
	getValues : function (frmobj, name)	{
		var result = "";

		if ( typeof(frmobj) == "object" && typeof(frmobj.length) == "number");
		{
			for (var i=0; i< frmobj.length; i++)
			{
				if (frmobj[i].name == name)
				{
					if (frmobj[i].type=="text" || frmobj[i].type=="hidden" )
					{// hidden , text
						result += frmobj[i].value;
					}//--end: hidden, text
					else if (frmobj[i].type=="radio" && frmobj[i].checked == true)
					{// radio 버튼
						 result += frmobj[i].value;
					}//--end:radio
					else if (frmobj[i].type=="checkbox")
					{//checkbox박스
						result += frmobj[i].checked;
					}//--end:checkbox
					else if (frmobj[i].type=="select-one" && typeof(frmobj[i].options) == "object" && typeof(frmobj[i].length) == "number")
					{//select박스
						var idx = frmobj[i].selectedIndex;
						result += frmobj[idx].value;
					}
					
					result += " ";
				}
			}
		}
		return result;
	},
	
	/**
	* YYYYMMDD를 DATE() 형으로 변환
	*
	* @ param   str			- YYYYMMDD형 스트링 형태의 날짜값
	*
	* @ return   			- Date() 형 날짜값
	**/
	string2date : function (str)
	{
		var year = "";
        var month = "";
        var day = "";

        if (typeof (str) == "string") {
            if (str.length < 8)
			{
				alert("[error - search.js] : string2date() 8자리 날짜가 아닙니다");
                return null;
			}
            year = parseInt(str.substring(0, 4));
            month = parseInt(str.substring(4, 6));
            day = parseInt(str.substring(6, 8));

            return Date(year, month - 1, day);

        }
	},
	
	/**
	* DATE() 형을 YYYYMMDD String형으로 리턴
	*
	* @ param   date			- Date()형 값
	*
	* @ return   			- "YYYYMMDD" string형 날짜데이터
	**/	
	date2string : function (date)
	{
		var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();

        if (month < 10)
            month = "0" + month;
        if (day < 10)
            day = "0" + day;

        return year + "" + month + "" + day;
	},
	
	/**
	* 오늘 날짜 리턴 
	*
	* @ param   
	*
	* @ return   			- YYYYMMDD 오늘날짜
	**/	
	getToday : function () {
			if (typeof(this.todaystr) == "undefined")
			{
				this.todaystr = this.date2string(new Date());
				
			}
			return this.todaystr;
	},
	
	/**
	* 날짜계산 (일단위)
	*
	* @ param   p_strdate		- YYYYMMDD 
	* @ param   p_agoday		- 0 : 오늘 ,    음수 : 과거 ,   양수: 미래       (일(Day)단위)
	*
	* @ return   			- YYYYMMDD 에서 p_agoday일 전후 
	**/	
	calcDateDay : function (p_strdate, p_agoday) {
		var result = "";
		var year,month,day;
		var tmp_strdate = ""+p_strdate;	//string형으로 변환
		
        if (typeof (tmp_strdate) == "string") {
            if (tmp_strdate.length == 8)
			{
				year = parseInt(tmp_strdate.substring(0, 4));
				month = parseInt(tmp_strdate.substring(4, 6));
				day = parseInt(tmp_strdate.substring(6, 8));
				
				result = new Date(year, month-1, day + p_agoday);
			}	
		}
		return this.date2string(result);
	},
	
	/**
	* 날짜계산 (주단위)
	*
	* @ param   p_strdate		- YYYYMMDD 
	* @ param   p_agoweek		- 0 : 오늘 ,    음수 : 과거 ,   양수: 미래       (주(Week)단위)
	*
	* @ return   			- YYYYMMDD 에서 p_agoweek 주 전후 
	**/	
	calcDateWeek : function (p_strdate, p_agoweek) {
		var agoDay = p_agoweek * 7;
		
		return this.calcDateDay(p_strdate, agoDay );
	},
	
	/**
	* 날짜계산 (월단위)
	*
	* @ param   p_strdate		- YYYYMMDD 
	* @ param   agoMonth		- 0 : 오늘 ,    음수 : 과거 ,   양수: 미래       (월(Month)단위)
	*
	* @ return   			- YYYYMMDD 에서 agoMonth 월 전후 
	**/	
	calcDateMonth : function (p_strdate, agoMonth) {
		var result = "";
		var year,month,day;
		var tmp_strdate = ""+p_strdate;	//string형으로 변환
		
        if (typeof (tmp_strdate) == "string") {
            if (tmp_strdate.length == 8)
			{
				year = parseInt(tmp_strdate.substring(0, 4));
				month = parseInt(tmp_strdate.substring(4, 6));
				day = parseInt(tmp_strdate.substring(6, 8));
				
				result = new Date(year, month-1+agoMonth, day);
			}	
		}
		return this.date2string(result);
	},
	
	/**
	* 날짜계산 (년단위)
	*
	* @ param   p_strdate		- YYYYMMDD 
	* @ param   agoYear		- 0 : 오늘 ,    음수 : 과거 ,   양수: 미래       (년(Year)단위)
	*
	* @ return   			- YYYYMMDD 에서 agoYear 년 전후 
	**/	
	calcDateYear : function (p_strdate, agoYear) {
		var result = "";
		var agoMonth = (agoYear + 0) * 12
		var tmp_strdate = ""+p_strdate;	//string형으로 변환
		
        result = this.calcDateMonth(p_strdate,agoMonth);
		
		return result;
	},
	
	/**
	* 문자열 치환
	*
	* @ param   target		- 원본 text
	* @ param   oldstr		- 변경 대상 string
	* @ param   newstr	- 변경될 string
	*
	* @ return   		- 치환된 text
	**/	
	replaceAll : function (target, oldstr, newstr)
	{
		var result = target;
		if (target != null)
		{
			result = target.split(oldstr).join(newstr);
		}
		return result;
	},
	
	/**
	* white Space제거
	*
	* @ param   str		- 문자열
	*
	* @ return   		- 제거된 문자열
	**/	
	trim : function (str)
	{
		var result = str;
		if (str != null)
		{
			result = result.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
		}
		return result;
	},
	
	//엘레먼트의 절대값 y픽셀을 구함
	getElementY : function(element)
	{
		var targetTop = 0;

		if (element.offsetParent)
		{
			while (element.offsetParent)
			{
				targetTop += element.offsetTop;
	            element = element.offsetParent;
			}
		}
		else if(element.y)
		{
			targetTop += element.y;
	    }

		return targetTop;
	},
	//엘레먼트의 절대값 x픽셀을 구함
	getElementX : function(element)
	{
		var targetTop = 0;

		if (element.offsetParent)
		{
			while (element.offsetParent)
			{
				targetTop += element.offsetLeft;
	            element = element.offsetParent;
			}
		}
		else if(element.x)
		{
			targetTop += element.x;
		}

		return targetTop;
	}
}

function fnSummery(code) {
	
	/*cmDialogOpen('search_preview',  {
		url : jsRootPATH + "por/sc/por_sc_preview_pop.do?i_sTitle=" + encodeURI(title) + "&i_sContent=" + encodeURI(content)
		, width : 600
		, height : 600
		, changeViewAutoSize : true
		, modal : true
		, title : "요약보기"
	});*/
	if(j$("." + code).css("display") == "none") {
		j$(".div_summery").hide();
		j$("." + code).stop().slideDown("500");
	} else {
		j$("." + code).stop().slideUp("500");
	}
}

function fnSearchMap() {
	cmDialogOpen('search_map',  {
		url : jsRootPATH + "por/sc/por_sc_category_pop.do"
		, width : 600
		, height : 600
		, changeViewAutoSize : true
		, modal : true
		, title : "검색 MAP"
	});
}

function fnCategoryDel() {
	var frm	=	j$("form[name='searchForm']");
	
	j$("input[name='colname']", frm).val("");
	j$("input[name='colvalue']", frm).val("");
	j$("input[name='category_name']", frm).val("");
	
	j$("#map_btn").hide();
}

